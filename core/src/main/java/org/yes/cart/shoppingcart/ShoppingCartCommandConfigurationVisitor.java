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

package org.yes.cart.shoppingcart;

/**
 * User: denispavlov
 * Date: 24/01/2017
 * Time: 07:30
 */
public interface ShoppingCartCommandConfigurationVisitor<T extends ShoppingCart> {

    /**
     * Visitor's id to call by id.
     *
     * @return visitor ID
     */
    String getId();

    /**
     * Visit the cart and set configurations necessary.
     *
     * @param cart cart
     * @param args additional arguments
     */
    void visit(T cart, Object ... args);

}
