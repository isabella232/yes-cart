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

package org.yes.cart.search.query.impl;

import org.apache.lucene.search.Query;
import org.yes.cart.search.query.ProductSearchQueryBuilder;

/**
 * User: denispavlov
 * Date: 18/02/2016
 * Time: 08:21
 */
public class ProductShopInStockSearchQueryBuilder extends AbstractNumericStrictFieldSearchQueryBuilder implements ProductSearchQueryBuilder<Query> {

    public ProductShopInStockSearchQueryBuilder() {
        super(PRODUCT_SHOP_INSTOCK_FIELD);
    }

}