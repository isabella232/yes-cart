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

package org.yes.cart.service.domain.impl;

import org.yes.cart.dao.GenericDAO;
import org.yes.cart.domain.entity.Mail;
import org.yes.cart.service.domain.MailService;

/**
 * User: denispavlov
 * Date: 10/11/2013
 * Time: 12:32
 */
public class MailServiceImpl extends BaseGenericServiceImpl<Mail> implements MailService {

    public MailServiceImpl(final GenericDAO<Mail, Long> genericDao) {
        super(genericDao);
    }

    /** {@inheritDoc} */
    @Override
    public Mail findOldestMail(Long lastFailedMailId) {
        if (lastFailedMailId == null) {
            return getGenericDao().findSingleByNamedQuery("OLDEST.MAIL");
        }
        return getGenericDao().findSingleByNamedQuery("OLDEST.MAIL.AFTER", lastFailedMailId);
    }
}
