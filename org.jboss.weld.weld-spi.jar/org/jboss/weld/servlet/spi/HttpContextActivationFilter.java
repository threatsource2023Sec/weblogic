package org.jboss.weld.servlet.spi;

import javax.servlet.http.HttpServletRequest;
import org.jboss.weld.bootstrap.api.Service;

public interface HttpContextActivationFilter extends Service {
   boolean accepts(HttpServletRequest var1);
}
