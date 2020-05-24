/*
 * Copyright 2009 Inspire-Software.com
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

package org.yes.cart.service.federation.impl;

import org.apache.commons.lang.StringUtils;
import org.yes.cart.domain.entity.Product;
import org.yes.cart.service.federation.FederationFilter;
import org.yes.cart.service.federation.ShopFederationStrategy;

import java.util.List;

/**
 * User: denispavlov
 * Date: 16/09/2014
 * Time: 14:27
 */
public class ProductImpexFederationFilterImpl extends AbstractImpexFederationFilterImpl implements FederationFilter {

    public ProductImpexFederationFilterImpl(final ShopFederationStrategy shopFederationStrategy,
                                            final List<String> roles) {
        super(shopFederationStrategy, roles);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isManageable(final Object object, final Class objectType) {

        if (!hasAccessRole()) {
            return false;
        }

        final Product product = (Product) object;

        return StringUtils.isBlank(product.getSupplierCatalogCode()) ||
                shopFederationStrategy.getAccessibleSupplierCatalogCodesByCurrentManager().contains(product.getSupplierCatalogCode());
    }

}
