package weblogic.security.spi;

import javax.servlet.Filter;

public interface ServletAuthenticationFilter {
   Filter[] getServletAuthenticationFilters();
}
