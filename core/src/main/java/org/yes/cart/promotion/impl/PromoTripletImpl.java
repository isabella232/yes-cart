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

package org.yes.cart.promotion.impl;

import org.yes.cart.domain.entity.Promotion;
import org.yes.cart.promotion.PromotionAction;
import org.yes.cart.promotion.PromotionCondition;

/**
 * User: denispavlov
 * Date: 06/06/2014
 * Time: 08:38
 */
public class PromoTripletImpl implements org.yes.cart.promotion.PromoTriplet {

    private final Promotion promotion;
    private final PromotionCondition condition;
    private final PromotionAction action;

    PromoTripletImpl(final Promotion promotion,
                     final PromotionCondition condition,
                     final PromotionAction action) {
        this.promotion = promotion;
        this.condition = condition;
        this.action = action;
    }

    @Override
    public Promotion getPromotion() {
        return promotion;
    }

    @Override
    public PromotionCondition getCondition() {
        return condition;
    }

    @Override
    public PromotionAction getAction() {
        return action;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final PromoTripletImpl that = (PromoTripletImpl) o;

        return promotion.getPromotionId() == that.promotion.getPromotionId();
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        final long id = promotion.getPromotionId();
        return (int) (id ^ (id >>> 32));
    }

}
