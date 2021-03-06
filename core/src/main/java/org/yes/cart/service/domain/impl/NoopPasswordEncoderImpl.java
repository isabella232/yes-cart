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

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Date: 19/09/2020
 * Time: 18:40
 */
public class NoopPasswordEncoderImpl implements PasswordEncoder {

    /** {@inheritDoc} */
    @Override
    public String encode(final CharSequence rawPassword) {
        return rawPassword.toString();
    }

    /** {@inheritDoc} */
    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
        return rawPassword.equals(encodedPassword);
    }

}
