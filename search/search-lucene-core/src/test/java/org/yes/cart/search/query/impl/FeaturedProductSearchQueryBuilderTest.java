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
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * User: denispavlov
 * Date: 15/11/2014
 * Time: 23:49
 */
public class FeaturedProductSearchQueryBuilderTest {

    @Test
    public void testCreateQueryChain() throws Exception {

        final List<Query> query = new FeaturedProductSearchQueryBuilder().createQueryChain(null, "any", "any");
        assertNotNull(query);
        assertEquals(1, query.size());
        assertEquals("featured:true", query.get(0).toString());

    }

}
