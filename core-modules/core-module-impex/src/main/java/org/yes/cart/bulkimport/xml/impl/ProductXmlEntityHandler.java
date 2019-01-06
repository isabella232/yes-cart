/*
 * Copyright 2009 Denys Pavlov, Igor Azarnyi
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.yes.cart.bulkimport.xml.impl;

import org.yes.cart.bulkcommon.model.ImpExTuple;
import org.yes.cart.bulkimport.xml.XmlEntityImportHandler;
import org.yes.cart.bulkimport.xml.internal.EntityImportModeType;
import org.yes.cart.bulkimport.xml.internal.ProductCategoriesCodeType;
import org.yes.cart.bulkimport.xml.internal.ProductLinksCodeType;
import org.yes.cart.bulkimport.xml.internal.SkuType;
import org.yes.cart.domain.entity.AttrValueProduct;
import org.yes.cart.domain.entity.Brand;
import org.yes.cart.domain.entity.Product;
import org.yes.cart.domain.entity.ProductType;
import org.yes.cart.service.domain.*;

/**
 * User: denispavlov
 * Date: 05/11/2018
 * Time: 22:23
 */
public class ProductXmlEntityHandler extends AbstractAttributableXmlEntityHandler<org.yes.cart.bulkimport.xml.internal.ProductType, Product, Product, AttrValueProduct> implements XmlEntityImportHandler<org.yes.cart.bulkimport.xml.internal.ProductType> {

    private BrandService brandService;
    private ProductTypeService productTypeService;
    private ProductService productService;

    private XmlEntityImportHandler<SkuType> skuXmlEntityImportHandler;
    private XmlEntityImportHandler<ProductCategoriesCodeType> productCategoriesCodeXmlEntityImportHandler;
    private XmlEntityImportHandler<ProductLinksCodeType> productLinksCodeXmlEntityImportHandler;

    public ProductXmlEntityHandler() {
        super("product");
    }

    @Override
    protected void delete(final Product product) {
        this.productService.delete(product);
        this.productService.getGenericDao().flush();
    }

    @Override
    protected void saveOrUpdate(final Product domain, final org.yes.cart.bulkimport.xml.internal.ProductType xmlType, final EntityImportModeType mode) {

        if (xmlType.getManufacturer() != null) {
            domain.setManufacturerCode(xmlType.getManufacturer().getManufacturerCode());
            domain.setManufacturerPartCode(xmlType.getManufacturer().getManufacturerPartCode());
        }

        if (xmlType.getSupplier() != null) {
            domain.setSupplierCode(xmlType.getSupplier().getSupplierCode());
            domain.setSupplierCatalogCode(xmlType.getSupplier().getSupplierCatalogCode());
        }

        if (xmlType.getPim() != null) {
            domain.setPimCode(xmlType.getPim().getPimCode());
            domain.setPimDisabled(xmlType.getPim().isDisabled());
        }

        if (xmlType.getBrand() != null) {
            Brand brand = this.brandService.findByNameOrGuid(xmlType.getBrand());
            if (brand == null) {
                brand = this.brandService.getGenericDao().getEntityFactory().getByIface(Brand.class);
                brand.setName(xmlType.getBrand());
                this.brandService.create(brand);
            }
            domain.setBrand(brand);
        }

        if (xmlType.getAvailability() != null) {
            domain.setDisabled(xmlType.getAvailability().isDisabled());
            domain.setAvailablefrom(processLDT(xmlType.getAvailability().getAvailableFrom()));
            domain.setAvailableto(processLDT(xmlType.getAvailability().getAvailableTo()));
        }

        if (xmlType.getInventoryConfig() != null) {
            domain.setAvailability(xmlType.getInventoryConfig().getType());
            domain.setMinOrderQuantity(xmlType.getInventoryConfig().getMin());
            domain.setMaxOrderQuantity(xmlType.getInventoryConfig().getMax());
            domain.setStepOrderQuantity(xmlType.getInventoryConfig().getStep());
        }

        if (xmlType.getProductType() != null) {
            ProductType productType = this.productTypeService.findSingleByCriteria(" where e.guid = ?1", xmlType.getProductType().getGuid());
            if (productType == null) {
                productType = this.productTypeService.getGenericDao().getEntityFactory().getByIface(ProductType.class);
                productType.setGuid(xmlType.getProductType().getGuid());
                productType.setName(xmlType.getProductType().getGuid());
                productType.setShippable(true);
                this.productTypeService.create(productType);
            }
            domain.setProducttype(productType);
        }

        updateSeo(xmlType.getSeo(), domain.getSeo());
        updateExt(xmlType.getCustomAttributes(), domain, domain.getAttributes());

        domain.setName(xmlType.getName());
        domain.setDisplayName(processI18n(xmlType.getDisplayName(), domain.getDisplayName()));
        domain.setDescription(xmlType.getDescription());
        domain.setTag(xmlType.getTags());
        if (domain.getProductId() == 0L) {
            this.productService.create(domain);
        } else {
            this.productService.update(domain);
        }
        this.productService.getGenericDao().flush();
        this.productService.getGenericDao().evict(domain);

        processCategories(domain, xmlType);
        processProductAssociations(domain, xmlType);

        if (xmlType.getProductSku() != null) {
            for (final SkuType xmlSkuType : xmlType.getProductSku().getSku()) {

                xmlSkuType.setProductCode(domain.getCode());
                skuXmlEntityImportHandler.handle(null, null, (ImpExTuple) new XmlImportTupleImpl(xmlSkuType.getCode(), xmlSkuType), null, null);

            }
        }

    }

    private void processProductAssociations(final Product domain, final org.yes.cart.bulkimport.xml.internal.ProductType xmlType) {

        if (xmlType.getProductLinks() != null) {

            final ProductLinksCodeType subXmlType = new ProductLinksCodeType();
            subXmlType.setProductCode(domain.getCode());
            subXmlType.setImportMode(xmlType.getProductLinks().getImportMode());
            subXmlType.getProductLink().addAll(xmlType.getProductLinks().getProductLink());

            productLinksCodeXmlEntityImportHandler.handle(null, null, (ImpExTuple) new XmlImportTupleImpl(subXmlType.getProductCode(), subXmlType), null, null);


        }


    }

    private void processCategories(final Product domain, final org.yes.cart.bulkimport.xml.internal.ProductType xmlType) {

        if (xmlType.getProductCategories() != null) {

            final ProductCategoriesCodeType subXmlType = new ProductCategoriesCodeType();
            subXmlType.setProductCode(domain.getCode());
            subXmlType.setImportMode(xmlType.getProductCategories().getImportMode());
            subXmlType.getProductCategory().addAll(xmlType.getProductCategories().getProductCategory());

            productCategoriesCodeXmlEntityImportHandler.handle(null, null, (ImpExTuple) new XmlImportTupleImpl(subXmlType.getProductCode(), subXmlType), null, null);

        }

    }

    @Override
    protected Product getOrCreate(final org.yes.cart.bulkimport.xml.internal.ProductType xmlType) {
        Product product = this.productService.findSingleByCriteria(" where e.code = ?1", xmlType.getCode());
        if (product != null) {
            return product;
        }
        product = this.productService.getGenericDao().getEntityFactory().getByIface(Product.class);
        product.setCode(xmlType.getCode());
        product.setGuid(xmlType.getCode());
        product.setAvailability(Product.AVAILABILITY_STANDARD);
        return product;
    }

    @Override
    protected EntityImportModeType determineImportMode(final org.yes.cart.bulkimport.xml.internal.ProductType xmlType) {
        return xmlType.getImportMode() != null ? xmlType.getImportMode() : EntityImportModeType.MERGE;
    }

    @Override
    protected boolean isNew(final Product domain) {
        return domain.getProductId() == 0L;
    }

    @Override
    protected void setMaster(final Product master, final AttrValueProduct av) {
        av.setProduct(master);
    }

    @Override
    protected Class<AttrValueProduct> getAvInterface() {
        return AttrValueProduct.class;
    }

    /**
     * Spring IoC.
     *
     * @param productTypeService product type service
     */
    public void setProductTypeService(final ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    /**
     * Spring IoC.
     *
     * @param productService product service
     */
    public void setProductService(final ProductService productService) {
        this.productService = productService;
    }

    /**
     * Spring IoC.
     *
     * @param brandService brand service
     */
    public void setBrandService(final BrandService brandService) {
        this.brandService = brandService;
    }

    /**
     * Spring IoC.
     *
     * @param skuXmlEntityImportHandler SKU handler
     */
    public void setSkuXmlEntityImportHandler(final XmlEntityImportHandler<SkuType> skuXmlEntityImportHandler) {
        this.skuXmlEntityImportHandler = skuXmlEntityImportHandler;
    }

    /**
     * Spring IoC.
     *
     * @param productCategoriesCodeXmlEntityImportHandler categories handler
     */
    public void setProductCategoriesCodeXmlEntityImportHandler(final XmlEntityImportHandler<ProductCategoriesCodeType> productCategoriesCodeXmlEntityImportHandler) {
        this.productCategoriesCodeXmlEntityImportHandler = productCategoriesCodeXmlEntityImportHandler;
    }

    /**
     * Spring IoC.
     *
     * @param productLinksCodeXmlEntityImportHandler links hadler
     */
    public void setProductLinksCodeXmlEntityImportHandler(final XmlEntityImportHandler<ProductLinksCodeType> productLinksCodeXmlEntityImportHandler) {
        this.productLinksCodeXmlEntityImportHandler = productLinksCodeXmlEntityImportHandler;
    }
}
