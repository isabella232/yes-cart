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

package org.yes.cart.web.service.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: denispavlov
 * Date: 15/06/2019
 * Time: 19:28
 */
@Controller
@ApiIgnore
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping(value = "/404")
    public void error404(final HttpServletRequest request, final HttpServletResponse response) throws IOException {

        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        final StringBuilder error = new StringBuilder();
        error.append("{\"error\":\"Path not found").append("\"}");
        response.getWriter().write(error.toString());

    }

    @RequestMapping(value = "/400")
    public void error400(final HttpServletRequest request, final HttpServletResponse response) throws IOException {

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        final StringBuilder error = new StringBuilder();
        error.append("{\"error\":\"Bad input\"}");
        response.getWriter().write(error.toString());

    }

    @RequestMapping(value = "/401")
    public void error401(final HttpServletRequest request, final HttpServletResponse response) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        final StringBuilder error = new StringBuilder();
        error.append("{\"error\":\"Unauthorized\"}");
        response.getWriter().write(error.toString());

    }

    @RequestMapping(value = "/403")
    public void error403(final HttpServletRequest request, final HttpServletResponse response) throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        final StringBuilder error = new StringBuilder();
        error.append("{\"error\":\"Forbidden\"}");
        response.getWriter().write(error.toString());

    }

    @RequestMapping(value = "/500")
    public void error500(final HttpServletRequest request, final HttpServletResponse response) throws IOException {

        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        final StringBuilder error = new StringBuilder();
        error.append("{\"error\":\"Server error\"}");
        response.getWriter().write(error.toString());

    }

}
