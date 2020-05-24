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

package org.yes.cart.domain.entity;

/**
 * Alias for shop (e.g. external reference for integrations)
 */
public interface ShopAlias extends Auditable {

    /**
     * @return primary key value.
     */
    long getStoreAliasId();

    void setStoreAliasId(long storeAliasId);

    /**
     * @return shop url.
     */
    String getAlias();

    void setAlias(String alias);

    /**
     * @return {@link Shop}
     */
    Shop getShop();

    void setShop(Shop shop);

}


