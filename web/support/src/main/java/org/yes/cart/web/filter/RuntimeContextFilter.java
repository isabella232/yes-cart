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

package org.yes.cart.web.filter;

import org.yes.cart.search.utils.SearchUtil;
import org.yes.cart.utils.TimeContext;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * User: denispavlov
 * Date: 09/02/2018
 * Time: 08:14
 */
public class RuntimeContextFilter extends AbstractFilter implements Filter {

    @Override
    public ServletRequest doBefore(final ServletRequest servletRequest,
                                   final ServletResponse servletResponse) throws IOException, ServletException {
        TimeContext.setNow();  // TODO: Time Machine
        return servletRequest;
    }

    @Override
    public void doAfter(final ServletRequest servletRequest,
                        final ServletResponse servletResponse) throws IOException, ServletException {
        TimeContext.clear();
    }

    @Override
    public void destroy() {
        TimeContext.destroy();
        SearchUtil.destroy(); // Ensure we clear our the threadlocal analysers
        super.destroy();
    }
}
