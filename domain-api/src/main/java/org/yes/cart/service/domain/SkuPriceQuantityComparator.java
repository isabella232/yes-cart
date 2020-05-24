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

package org.yes.cart.service.domain;

import org.yes.cart.domain.entity.SkuPrice;
import org.yes.cart.utils.MoneyUtils;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 * Igor Azarny iazarny@yahoo.com
 * Date: 14-Sep-2011
 * Time: 14:21:21
 */
public class SkuPriceQuantityComparator implements Comparator<SkuPrice> {
    @Override
    public int compare(final SkuPrice skuPrice1, final SkuPrice skuPrice2) {
        final int byTier = skuPrice1.getQuantity().compareTo(skuPrice2.getQuantity());
        if (byTier == 0) {
            final BigDecimal minPrice1 = MoneyUtils.minPositive(skuPrice1.getSalePriceForCalculation());
            final BigDecimal minPrice2 = MoneyUtils.minPositive(skuPrice2.getSalePriceForCalculation());
            return minPrice1.compareTo(minPrice2);
        }
        return byTier;
    }
}

