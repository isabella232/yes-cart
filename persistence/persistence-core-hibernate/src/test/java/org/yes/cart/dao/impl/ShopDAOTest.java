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

package org.yes.cart.dao.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.yes.cart.dao.GenericDAO;
import org.yes.cart.dao.constants.DaoServiceBeanKeys;
import org.yes.cart.domain.entity.Shop;
import org.yes.cart.domain.entity.ShopUrl;
import org.yes.cart.domain.entity.impl.ShopEntity;
import org.yes.cart.domain.entity.impl.ShopUrlEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * User: Igor Azarny iazarny@yahoo.com
 * Date: 08-May-2011
 * Time: 11:12:54
 */
public class ShopDAOTest extends AbstractTestDAO {

    private GenericDAO<Shop, Long> shopDao;
    private final Set<Long> cleanupPks = new HashSet<>();
    private static final String URL1 = "www.shop1.yescart.org";
    private static final String URL2 = "shop1.yescart.org";

    @Override
    @Before
    public void setUp()  {
        shopDao = (GenericDAO<Shop, Long>) ctx().getBean(DaoServiceBeanKeys.SHOP_DAO);
        super.setUp();
    }

    @After
    public void cleanUp() {
        getTx().execute(new TransactionCallbackWithoutResult() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                for (Long pk : cleanupPks) {
                    Shop shop = shopDao.findById(pk);
                    shopDao.delete(shop);
                    assertNull(shopDao.findById(pk));
                }
            }
        });

    }

    @Test
    public void testShopDao() {

        getTx().execute(new TransactionCallbackWithoutResult() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {

                Shop shop = new ShopEntity();
                shop.setCode("TESTSHOP");
                shop.setName("test shop");
                shop.setDescription("test shop description");
                shop.setFspointer("/yescart/data");
                ShopUrl url;
                url = new ShopUrlEntity();
                url.setUrl(URL1);
                url.setShop(shop);
                shop.getShopUrl().add(url);
                url = new ShopUrlEntity();
                url.setUrl(URL2);
                url.setShop(shop);
                shop.getShopUrl().add(url);
                shop = shopDao.create(shop);
                assertNotNull(shop);
                assertEquals(2, shop.getShopUrl().size());
                cleanupPks.add(shop.getShopId());
                updateShop();
                resolveShopByURL();

                status.setRollbackOnly();

            }
        });


    }

    public void updateShop() {
        for (Long pk : cleanupPks) {
            Shop shop = shopDao.findById(pk);
            assertNotNull(shop);
            String newName = shop.getName() + "1";
            shop.setName(newName);
            shopDao.update(shop);
            shop = shopDao.findById(pk);
            assertEquals(newName, shop.getName());
        }
    }

    /**
     * Test, that we are able resolve shop by his domain name
     */
    public void resolveShopByURL() {
        List<Shop> shopList0 = shopDao.findAll();
        List<Shop> shopList = shopDao.findByNamedQuery("SHOP.BY.URL", "gadget.yescart.org");
        assertEquals(1, shopList.size());
        shopList = shopDao.findByNamedQuery("SHOP.BY.URL", URL2);
        assertEquals(1, shopList.size());
    }
}
