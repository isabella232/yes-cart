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

package org.yes.cart.service.order;

import org.yes.cart.domain.entity.CustomerOrder;
import org.yes.cart.shoppingcart.ShoppingCart;

/**
 * Assemble {@link CustomerOrder} from {@link org.yes.cart.shoppingcart.ShoppingCart}.
 * <p/>
 * User: Igor Azarny iazarny@yahoo.com
 * Date: 07-May-2011
 * Time: 11:13:01
 */
public interface OrderAssembler {


    /**
     * Create and fill {@link CustomerOrder} from given {@link ShoppingCart}.
     *
     * @param shoppingCart given shopping cart
     * @param orderNumber order number
     *
     * @return order
     */
    CustomerOrder assembleCustomerOrder(ShoppingCart shoppingCart, String orderNumber) throws OrderAssemblyException;

    /**
     * Create and fill {@link CustomerOrder} from given {@link ShoppingCart}.
     *
     * @param shoppingCart given shopping cart
     * @param orderNumber order number
     * @param temp         if set to true then order number is not generated and coupon usage is not created
     *
     * @return order
     */
    CustomerOrder assembleCustomerOrder(ShoppingCart shoppingCart, String orderNumber, boolean temp) throws OrderAssemblyException;


}
