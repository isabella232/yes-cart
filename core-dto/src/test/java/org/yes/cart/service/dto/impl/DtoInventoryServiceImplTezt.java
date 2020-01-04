/*
 * Copyright 2009 Denys Pavlov, Igor Azarnyi
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

package org.yes.cart.service.dto.impl;

import org.junit.Before;
import org.junit.Test;
import org.yes.cart.BaseCoreDBTestCase;
import org.yes.cart.constants.DtoServiceSpringKeys;
import org.yes.cart.domain.dto.InventoryDTO;
import org.yes.cart.domain.dto.WarehouseDTO;
import org.yes.cart.domain.dto.factory.DtoFactory;
import org.yes.cart.domain.misc.SearchContext;
import org.yes.cart.domain.misc.SearchResult;
import org.yes.cart.service.dto.DtoInventoryService;
import org.yes.cart.service.dto.DtoWarehouseService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * User: denispavlov
 * Date: 29/05/2015
 * Time: 16:25
 */
public class DtoInventoryServiceImplTezt extends BaseCoreDBTestCase {

    private DtoWarehouseService dtoWarehouseService;
    private DtoInventoryService dtoService;
    private DtoFactory dtoFactory;

    @Before
    public void setUp() {
        dtoFactory = (DtoFactory) ctx().getBean(DtoServiceSpringKeys.DTO_FACTORY);
        dtoService = (DtoInventoryService) ctx().getBean("dtoInventoryService");
        dtoWarehouseService = (DtoWarehouseService) ctx().getBean("dtoWarehouseService");
        super.setUp();
    }




    @Test
    public void testFindInventory() throws Exception {

        // Test no stock when no warehouse
        final SearchContext filterNone = new SearchContext(Collections.emptyMap(), 0, 10, "skuCode", false, "filter");
        SearchResult<InventoryDTO> stock = dtoService.findInventory(0L, filterNone);
        assertTrue(stock.getTotal() == 0);

        // Has stock when warehouse selected
        stock = dtoService.findInventory(1L, filterNone);
        assertFalse(stock.getTotal() == 0);

        // Test partial SKU match
        final SearchContext filterPartial = new SearchContext(Collections.singletonMap("filter", Collections.singletonList("CC_TEST")), 0, 10, "skuCode", false, "filter");
        stock = dtoService.findInventory(1L, filterPartial);
        assertFalse(stock.getTotal() == 0);
        assertEquals(1, stock.getItems().stream().filter(
                inv -> "CC_TEST1".equals(inv.getSkuCode())).count());

        Set<String> sku = new HashSet<String>();
        for (final InventoryDTO inventory : stock.getItems()) {
            sku.add(inventory.getSkuCode());
        }

        assertTrue(sku.contains("CC_TEST1"));
        assertTrue(sku.contains("CC_TEST2"));
        assertTrue(sku.contains("CC_TEST3"));

        // Test name SKU match
        final SearchContext filterNamePartial = new SearchContext(Collections.singletonMap("filter", Collections.singletonList("cc test 11")), 0, 10, "skuCode", false, "filter");
        stock = dtoService.findInventory(1L, filterNamePartial);
        assertEquals(1, stock.getTotal());
        assertEquals("CC_TEST11", stock.getItems().get(0).getSkuCode());

        // Test exact SKU match
        final SearchContext filterCodeExact = new SearchContext(Collections.singletonMap("filter", Collections.singletonList("!CC_TEST1")), 0, 10, "skuCode", false, "filter");
        stock = dtoService.findInventory(1L, filterCodeExact);
        assertEquals(1, stock.getTotal());
        assertEquals("CC_TEST1", stock.getItems().get(0).getSkuCode());

        // Test SKU no match
        final SearchContext filterNoMatch = new SearchContext(Collections.singletonMap("filter", Collections.singletonList("!something really weird not matching")), 0, 10, "skuCode", false, "filter");
        stock = dtoService.findInventory(1L, filterNoMatch);
        assertEquals(0, stock.getTotal());

        // Test high reserved stock reserved >= X
        final SearchContext filterReserved = new SearchContext(Collections.singletonMap("filter", Collections.singletonList("+1")), 0, 10, "skuCode", false, "filter");
        stock = dtoService.findInventory(1L, filterReserved);
        assertEquals(0, stock.getTotal());
        // Test low stock qty <= X
        final SearchContext filterLowStock = new SearchContext(Collections.singletonMap("filter", Collections.singletonList("-5")), 0, 10, "skuCode", false, "filter");
        stock = dtoService.findInventory(1L, filterLowStock);
        assertFalse(stock.getTotal() == 0);

        // Dates
        final SearchContext filterDates = new SearchContext(Collections.singletonMap("filter", Collections.singletonList("2010-04-00<2040-04-09")), 0, 10, "skuCode", false, "filter");
        stock = dtoService.findInventory(2L, filterDates);
        assertEquals(1, stock.getTotal());
        assertEquals("BENDER-ua", stock.getItems().get(0).getSkuCode());


    }

    @Test
    public void testGetWarehouses() throws Exception {

        final List<WarehouseDTO> warehouseDTOs = dtoWarehouseService.getAll();
        assertFalse(warehouseDTOs.isEmpty());

        final Set<String> codes = new HashSet<String>();
        for (final WarehouseDTO warehouseDTO : warehouseDTOs) {
            codes.add(warehouseDTO.getCode());
        }

        assertTrue(codes.contains("WAREHOUSE_1"));
        assertTrue(codes.contains("WAREHOUSE_2"));
        assertTrue(codes.contains("WAREHOUSE_3"));

    }

    @Test
    public void testRemoveInventory() throws Exception {

        InventoryDTO dto = dtoService.getInventory(10L);
        assertNotNull(dto);

        dtoService.removeInventory(dto.getSkuWarehouseId());

        dto = dtoService.getInventory(10L);

        assertEquals(BigDecimal.ZERO.setScale(2), dto.getQuantity().setScale(2));
        assertEquals(BigDecimal.ONE.setScale(2), dto.getReserved().setScale(2)); // make sure we are not removing reserved

    }

    @Test
    public void testCreateInventory() throws Exception {

        final SearchContext filterCodeExact = new SearchContext(Collections.singletonMap("filter", Collections.singletonList("!ABC-0002")), 0, 10, "skuCode", false, "filter");

        final WarehouseDTO wh = dtoWarehouseService.getAll().get(0);

        assertTrue(dtoService.findInventory(wh.getWarehouseId(), filterCodeExact).getItems().isEmpty());

        InventoryDTO dto = getDto("ABC-0002", wh.getCode());
        dto = dtoService.createInventory(dto);
        assertTrue(dto.getSkuWarehouseId() > 0L);

        final List<InventoryDTO> stock = dtoService.findInventory(wh.getWarehouseId(), filterCodeExact).getItems();
        assertFalse(stock.isEmpty());

        assertEquals("ABC-0002", stock.get(0).getSkuCode());
        assertEquals(BigDecimal.TEN.setScale(2), stock.get(0).getQuantity());

    }

    @Test
    public void testUpdateInventory() throws Exception {

        final SearchContext filterCodeExact = new SearchContext(Collections.singletonMap("filter", Collections.singletonList("!ABC-0003")), 0, 10, "skuCode", false, "filter");

        final WarehouseDTO wh = dtoWarehouseService.getAll().get(0);

        assertTrue(dtoService.findInventory(wh.getWarehouseId(), filterCodeExact).getItems().isEmpty());

        InventoryDTO dto = getDto("ABC-0003", wh.getCode());
        dto = dtoService.createInventory(dto);
        assertTrue(dto.getSkuWarehouseId() > 0L);

        List<InventoryDTO> stock = dtoService.findInventory(wh.getWarehouseId(), filterCodeExact).getItems();
        assertFalse(stock.isEmpty());

        assertEquals("ABC-0003", stock.get(0).getSkuCode());
        assertEquals(BigDecimal.TEN.setScale(2), stock.get(0).getQuantity());

        dto.setQuantity(BigDecimal.ONE);
        dto = dtoService.updateInventory(dto);

        stock = dtoService.findInventory(wh.getWarehouseId(), filterCodeExact).getItems();
        assertFalse(stock.isEmpty());

        assertEquals("ABC-0003", stock.get(0).getSkuCode());
        assertEquals(BigDecimal.ONE.setScale(2), stock.get(0).getQuantity());


    }


    private InventoryDTO getDto(String sku, String warehouse) {
        InventoryDTO dto = dtoFactory.getByIface(InventoryDTO.class);
        dto.setSkuCode(sku);
        dto.setQuantity(BigDecimal.TEN);
        dto.setWarehouseCode(warehouse);
        return dto;
    }

}
