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

package org.yes.cart.domain.misc;

import java.util.List;

/**
 * User: denispavlov
 * Date: 27/10/2019
 * Time: 10:59
 */
public class SearchResult<T> {

    private final SearchContext searchContext;
    private final List<T> items;
    private final int total;

    public SearchResult(final SearchContext searchContext,
                        final List<T> items,
                        final int total) {
        this.searchContext = searchContext;
        this.items = items;
        this.total = total;
    }

    public SearchContext getSearchContext() {
        return searchContext;
    }

    public List<T> getItems() {
        return items;
    }

    public int getTotal() {
        return total;
    }
    
}
