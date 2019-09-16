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

package org.yes.cart.domain.dto.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.yes.cart.domain.dto.ProductSearchResultDTO;
import org.yes.cart.domain.dto.ProductSkuSearchResultDTO;
import org.yes.cart.domain.dto.StoredAttributesDTO;
import org.yes.cart.domain.entity.SkuWarehouse;
import org.yes.cart.domain.i18n.I18NModel;
import org.yes.cart.domain.i18n.impl.StringI18NModel;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implement search result dto item.
 * User: igor
 * Date: 01.06.13
 * Time: 13:15
 */
public class ProductSearchResultDTOImpl implements ProductSearchResultDTO {

    private long id;
    private String code;
    private String manufacturerCode;
    private String fulfilmentCentreCode;
    private String defaultSkuCode;
    private String name;
    private String displayName;
    private String description;
    private String displayDescription;
    private String type;
    private String displayType;
    private int typeMask;
    private String tag;
    private String brand;
    private String defaultImage;

    private Map<Long, ProductSkuSearchResultDTO> baseSkus;

    // This is dependent of FT search, so not part of the copy()
    private List<ProductSkuSearchResultDTO> searchSkus;

    private I18NModel i18NModelName;
    private I18NModel i18NModelDescription;
    private I18NModel i18NModelType;

    private StoredAttributesDTO attributes;

    private Instant createdTimestamp;
    private Instant updatedTimestamp;


    /** {@inheritDoc} */
    @Override
    public String getDefaultImage() {
        if (searchSkus != null && !searchSkus.isEmpty()) {
            // if this is multi SKU and we have relevancy list - use it
            return searchSkus.get(0).getDefaultImage();
        }
        return defaultImage;
    }

    /** {@inheritDoc} */
    @Override
    public void setDefaultImage(final String defaultImage) {
        this.defaultImage = defaultImage;
    }

    /** {@inheritDoc} */
    @Override
    @JsonIgnore
    public Map<String, BigDecimal> getQtyOnWarehouse(final long shopId) {
        final Map<String, BigDecimal> qty = new HashMap<>();
        if (this.searchSkus != null) {
            for (final ProductSkuSearchResultDTO sku : searchSkus) {
                qty.put(sku.getCode(), sku.getQtyOnWarehouse(shopId));
            }
        }
        return qty;
    }

    /** {@inheritDoc} */
    @Override
    @JsonIgnore
    public LocalDateTime getAvailableto() {
        if (searchSkus != null && !searchSkus.isEmpty()) {
            return searchSkus.get(0).getAvailableto();
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    @JsonIgnore
    public LocalDateTime getAvailablefrom() {
        if (searchSkus != null && !searchSkus.isEmpty()) {
            return searchSkus.get(0).getAvailablefrom();
        }
        return null;
    }

    @Override
    @JsonIgnore
    public LocalDateTime getReleaseDate() {
        if (searchSkus != null && !searchSkus.isEmpty()) {
            return searchSkus.get(0).getReleaseDate();
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    @JsonIgnore
    public int getAvailability() {
        if (searchSkus != null && !searchSkus.isEmpty()) {
            return searchSkus.get(0).getAvailability();
        }
        return SkuWarehouse.AVAILABILITY_STANDARD;
    }

    /** {@inheritDoc} */
    @Override
    public long getId() {
        return id;
    }

    /** {@inheritDoc} */
    @Override
    public void setId(final long id) {
        this.id = id;
    }

    /** {@inheritDoc} */
    @Override
    public String getCode() {
        return code;
    }

    /** {@inheritDoc} */
    @Override
    public void setCode(final String code) {
        this.code = code;
    }

    /** {@inheritDoc} */
    @Override
    public String getManufacturerCode() {
        return manufacturerCode;
    }

    /** {@inheritDoc} */
    @Override
    public void setManufacturerCode(final String manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    /** {@inheritDoc} */
    @Override
    public String getFulfilmentCentreCode() {
        return fulfilmentCentreCode;
    }

    /** {@inheritDoc} */
    @Override
    public void setFulfilmentCentreCode(final String fulfilmentCentreCode) {
        this.fulfilmentCentreCode = fulfilmentCentreCode;
    }

    /** {@inheritDoc} */
    @Override
    @JsonIgnore
    public boolean isMultisku() {
        return searchSkus != null && searchSkus.size() > 1;
    }

    /** {@inheritDoc} */
    @Override
    @JsonIgnore
    public String getDefaultSkuCode() {
        if (searchSkus != null && !searchSkus.isEmpty()) {
            return searchSkus.get(0).getCode();
        }
        return defaultSkuCode;
    }

    /** {@inheritDoc} */
    @Override
    public void setDefaultSkuCode(final String defaultSkuCode) {
        this.defaultSkuCode = defaultSkuCode;
    }

    /** {@inheritDoc} */
    @Override
    public String getDisplayName() {
        return displayName;
    }

    /** {@inheritDoc} */
    @Override
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
        this.i18NModelName = new StringI18NModel(this.displayName);
    }


    /** {@inheritDoc} */
    @Override
    public String getName(final String locale) {
        if (this.i18NModelName != null) {
            final String name = this.i18NModelName.getValue(locale);
            if (name != null) {
                return name;
            }
        }
        return this.name;
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return name;
    }

    /** {@inheritDoc} */
    @Override
    public void setName(final String name) {
        this.name = name;
    }
    
    

    /** {@inheritDoc} */
    @Override
    public String getDescription() {
        return description;
    }

    /** {@inheritDoc} */
    @Override
    public void setDescription(final String description) {
        this.description = description;
    }

    /** {@inheritDoc} */
    @Override
    public String getDescription(final String locale) {
        if (this.i18NModelDescription != null) {
            final String desc = this.i18NModelDescription.getValue(locale);
            if (desc != null) {
                return desc;
            }
        }
        return this.description;
    }


    /** {@inheritDoc} */
    @Override
    public String getDisplayDescription() {
        return displayDescription;
    }

    /** {@inheritDoc} */
    @Override
    public void setDisplayDescription(final String displayDescription) {
        
        this.displayDescription = displayDescription;

        i18NModelDescription = new StringI18NModel(this.displayDescription);
    }

    /** {@inheritDoc} */
    @Override
    public String getType(final String locale) {
        if (this.i18NModelType != null) {
            final String type = this.i18NModelType.getValue(locale);
            if (type != null) {
                return type;
            }
        }
        return this.type;
    }

    /** {@inheritDoc} */
    @Override
    public String getType() {
        return type;
    }

    /** {@inheritDoc} */
    @Override
    public void setType(final String type) {
        this.type = type;
    }

    /** {@inheritDoc} */
    @Override
    public String getDisplayType() {
        return displayType;
    }

    /** {@inheritDoc} */
    @Override
    public void setDisplayType(final String displayType) {

        this.displayType = displayType;

        i18NModelType = new StringI18NModel(this.displayType);

    }

    // JSON accessor
    public int getTypeMask() {
        return typeMask;
    }

    public void setTypeMask(final int typeMask) {
        this.typeMask = typeMask;
    }

    /** {@inheritDoc} */
    @Override
    @JsonIgnore
    public boolean isService() {
        return (this.typeMask & 2) != 0;
    }

    /** {@inheritDoc} */
    @Override
    public void setService(final boolean service) {
        this.typeMask = service ? this.typeMask | 2 : this.typeMask & ~ 2;
    }

    /** {@inheritDoc} */
    @Override
    @JsonIgnore
    public boolean isEnsemble() {
        return (this.typeMask & 4) != 0;
    }

    /** {@inheritDoc} */
    @Override
    public void setEnsemble(final boolean ensemble) {
        this.typeMask = ensemble ? this.typeMask | 4 : this.typeMask & ~ 4;
    }

    /** {@inheritDoc} */
    @Override
    @JsonIgnore
    public boolean isShippable() {
        return (this.typeMask & 8) != 0;
    }

    /** {@inheritDoc} */
    @Override
    public void setShippable(final boolean shippable) {
        this.typeMask = shippable ? this.typeMask | 8 : this.typeMask & ~ 8;
    }

    /** {@inheritDoc} */
    @Override
    @JsonIgnore
    public boolean isDigital() {
        return (this.typeMask & 16) != 0;
    }

    /** {@inheritDoc} */
    @Override
    public void setDigital(final boolean digital) {
        this.typeMask = digital ? this.typeMask | 16 : this.typeMask & ~ 16;
    }

    /** {@inheritDoc} */
    @Override
    @JsonIgnore
    public boolean isDownloadable() {
        return (this.typeMask & 32) != 0;
    }

    /** {@inheritDoc} */
    @Override
    public void setDownloadable(final boolean downloadable) {
        this.typeMask = downloadable ? this.typeMask | 32 : this.typeMask & ~ 32;
    }

    /** {@inheritDoc} */
    @Override
    public String getTag() {
        return tag;
    }

    /** {@inheritDoc} */
    @Override
    public void setTag(final String tag) {
        this.tag = tag;
    }

    /** {@inheritDoc} */
    @Override
    public String getBrand() {
        return brand;
    }

    /** {@inheritDoc} */
    @Override
    public void setBrand(final String brand) {
        this.brand = brand;
    }

    /** {@inheritDoc} */
    @Override
    @JsonIgnore
    public boolean isFeatured() {
        if (searchSkus != null && !searchSkus.isEmpty()) {
            return searchSkus.get(0).isFeatured();
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    @JsonIgnore
    public BigDecimal getMinOrderQuantity() {
        if (searchSkus != null && !searchSkus.isEmpty()) {
            return searchSkus.get(0).getMinOrderQuantity();
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    @JsonIgnore
    public BigDecimal getMaxOrderQuantity() {
        if (searchSkus != null && !searchSkus.isEmpty()) {
            return searchSkus.get(0).getMaxOrderQuantity();
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    @JsonIgnore
    public BigDecimal getStepOrderQuantity() {
        if (searchSkus != null && !searchSkus.isEmpty()) {
            return searchSkus.get(0).getStepOrderQuantity();
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    @JsonIgnore
    public ProductSkuSearchResultDTO getBaseSku(final long skuId) {
        return baseSkus != null ? baseSkus.get(skuId) : null;
    }

    /** {@inheritDoc} */
    @Override
    public Map<Long, ProductSkuSearchResultDTO> getBaseSkus() {
        return baseSkus;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseSkus(final Map<Long, ProductSkuSearchResultDTO> baseSkus) {
        this.baseSkus = baseSkus;
    }

    /** {@inheritDoc} */
    @Override
    @JsonIgnore
    public List<ProductSkuSearchResultDTO> getSearchSkus() {
        return searchSkus;
    }

    /** {@inheritDoc} */
    @Override
    @JsonIgnore
    public void setSearchSkus(final List<ProductSkuSearchResultDTO> searchSkus) {
        this.searchSkus = searchSkus;
    }

    /** {@inheritDoc} */
    @Override
    public StoredAttributesDTO getAttributes() {
        return attributes;
    }

    /** {@inheritDoc} */
    @Override
    public void setAttributes(final StoredAttributesDTO attributes) {
        this.attributes = attributes;
    }

    /** {@inheritDoc} */
    @Override
    public Instant getCreatedTimestamp() {
        return createdTimestamp;
    }

    /** {@inheritDoc} */
    @Override
    public void setCreatedTimestamp(final Instant createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    /** {@inheritDoc} */
    @Override
    public Instant getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    /** {@inheritDoc} */
    @Override
    public void setUpdatedTimestamp(final Instant updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    /** {@inheritDoc} */
    @Override
    public ProductSearchResultDTO copy() {
        /*
            DO NOT copy skus as this must be set through FT search - we need them to be
            sorted by relevancy
         */
        final ProductSearchResultDTOImpl copy = new ProductSearchResultDTOImpl();
        copy.setId(this.id);
        copy.setCode(this.code);
        copy.setManufacturerCode(this.manufacturerCode);
        copy.setFulfilmentCentreCode(this.fulfilmentCentreCode);
        copy.setDefaultSkuCode(this.defaultSkuCode);
        copy.setName(this.name);
        copy.setDisplayName(this.displayName);
        copy.setDescription(this.description);
        copy.setDisplayDescription(this.displayDescription);
        copy.setType(this.type);
        copy.setDisplayType(this.displayType);
        copy.setTypeMask(this.typeMask);
        copy.setTag(this.tag);
        copy.setBrand(this.brand);
        copy.setDefaultImage(this.defaultImage);
        copy.setCreatedTimestamp(this.createdTimestamp);
        copy.setUpdatedTimestamp(this.updatedTimestamp);
        final Map<Long, ProductSkuSearchResultDTO> copyBaseSku = new HashMap<>();
        if (this.baseSkus != null) {
            for (final Map.Entry<Long, ProductSkuSearchResultDTO> skuDTO : this.baseSkus.entrySet()) {
                copyBaseSku.put(skuDTO.getKey(), skuDTO.getValue().copy());
            }
        }
        copy.setBaseSkus(copyBaseSku);
        if (this.attributes != null) {
            copy.setAttributes(new StoredAttributesDTOImpl(this.attributes.toString()));
        }
        return copy;
    }

    @Override
    public String toString() {
        return "ProductSearchResultDTOImpl{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", fulfilmentCentreCode='" + fulfilmentCentreCode + '\'' +
                ", defaultSkuCode='" + defaultSkuCode + '\'' +
                '}';
    }
}
