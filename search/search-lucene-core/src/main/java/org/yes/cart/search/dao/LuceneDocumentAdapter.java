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

package org.yes.cart.search.dao;

import org.apache.lucene.document.Document;
import org.yes.cart.domain.misc.Pair;

import java.io.Serializable;

/**
 * User: denispavlov
 * Date: 06/04/2017
 * Time: 17:08
 */
public interface LuceneDocumentAdapter<T, PK extends Serializable> {

    /**
     * Adapt entity to Lucene document.
     *
     * @param entity entity to adapt
     *
     * @return lucene document
     */
    Pair<PK, Document[]> toDocument(T entity);

}
