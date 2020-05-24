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

package org.yes.cart.shoppingcart.support.impl;

import org.yes.cart.shoppingcart.ShoppingCart;
import org.yes.cart.shoppingcart.support.ShoppingCartPersister;

/**
 * User: denispavlov
 * Date: 22/08/2014
 * Time: 00:25
 */
public class ShoppingCartPersisterDecoratorImpl<S, T> implements ShoppingCartPersister<S, T> {

    private final ShoppingCartPersister<S, T> shoppingCartPersister;

    public ShoppingCartPersisterDecoratorImpl(final ShoppingCartPersister<S, T> shoppingCartPersister) {
        this.shoppingCartPersister = shoppingCartPersister;
    }

    /** {@inheritDoc} */
    @Override
    public void persistShoppingCart(final S source, final T target, final ShoppingCart shoppingCart) {
        shoppingCartPersister.persistShoppingCart(source, target, shoppingCart);
    }

}
