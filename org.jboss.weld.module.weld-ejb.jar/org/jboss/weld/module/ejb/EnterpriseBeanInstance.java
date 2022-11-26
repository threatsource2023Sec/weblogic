package org.jboss.weld.module.ejb;

import javax.enterprise.context.spi.CreationalContext;
import org.jboss.weld.bean.SessionBean;
import org.jboss.weld.bean.proxy.Marker;

public interface EnterpriseBeanInstance {
   void destroy(Marker var1, SessionBean var2, CreationalContext var3);
}
