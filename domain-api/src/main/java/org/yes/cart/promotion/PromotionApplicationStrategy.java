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

package org.yes.cart.promotion;

import java.util.List;
import java.util.Map;

/**
 * User: denispavlov
 * Date: 06/06/2014
 * Time: 08:58
 */
public interface PromotionApplicationStrategy {

    /**
     * Test all applicable promotions for best cumulative discount value and apply it
     * to given context.
     *
     * @param promoBuckets promotion buckets containing groups of promotions that can be
     *                     combined
     * @param context all variables necessary for the application of promotions.
     */
    void applyPromotions(List<List<PromoTriplet>> promoBuckets,
                         Map<String, Object> context);

}
