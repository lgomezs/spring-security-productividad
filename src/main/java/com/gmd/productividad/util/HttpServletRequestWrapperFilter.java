/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.gmd.productividad.util;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.AbstractConfigurationFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;

/**
 * Implementation of a filter that wraps the normal HttpServletRequest with a
 * wrapper that overrides the following methods to provide data from the
 * CAS Assertion:
 * <ul>
 * <li>{@link HttpServletRequest#getUserPrincipal()}</li>
 * <li>{@link HttpServletRequest#getRemoteUser()}</li>
 * <li>{@link HttpServletRequest#isUserInRole(String)}</li>
 * </ul>
 * <p/>
 * This filter needs to be configured in the chain so that it executes after
 * both the authentication and the validation filters.
 *
 * @author Scott Battaglia
 * @author Marvin S. Addison
 * @version $Revision: 11729 $ $Date: 2007-09-26 14:22:30 -0400 (Tue, 26 Sep 2007) $
 * @since 3.0
 */
public final class HttpServletRequestWrapperFilter extends AbstractConfigurationFilter {

    /** Name of the attribute used to answer role membership queries */
    private String roleAttribute;

    /** Whether or not to ignore case in role membership queries */
    private boolean ignoreCase;

    public void destroy() {
        // nothing to do
    }

    /**
     * Wraps the HttpServletRequest in a wrapper class that delegates
     * <code>request.getRemoteUser</code> to the underlying Assertion object
     * stored in the user session.
     */
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        final AttributePrincipal principal = retrievePrincipalFromSessionOrRequest(servletRequest);

        filterChain.doFilter(new CasHttpServletRequestWrapper((HttpServletRequest) servletRequest, principal), servletResponse);
    }

    protected AttributePrincipal retrievePrincipalFromSessionOrRequest(final ServletRequest servletRequest) {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpSession session = request.getSession(false);
        final Assertion assertion = (Assertion) (session == null ? request.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION) : session.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION));

        return assertion == null ? null : assertion.getPrincipal();
    }

    public void init(final FilterConfig filterConfig) throws ServletException {
        this.roleAttribute = getPropertyFromInitParams(filterConfig, "roleAttribute", null);
        this.ignoreCase = Boolean.parseBoolean(getPropertyFromInitParams(filterConfig, "ignoreCase", "false"));
    }

    final class CasHttpServletRequestWrapper extends HttpServletRequestWrapper {

        private final AttributePrincipal principal;

        CasHttpServletRequestWrapper(final HttpServletRequest request, final AttributePrincipal principal) {
            super(request);
            this.principal = principal;
        }

        public Principal getUserPrincipal() {
            return this.principal;
        }

        public String getRemoteUser() {
            return principal != null ? this.principal.getName() : null;
        }

        public boolean isUserInRole(final String role) {
            try{
                return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).isUserInRole(role);
            }catch(Exception e){
                return false;
            }
        }

    }
}
