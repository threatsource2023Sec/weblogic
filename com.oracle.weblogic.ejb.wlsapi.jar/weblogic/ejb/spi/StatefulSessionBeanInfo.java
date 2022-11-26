package weblogic.ejb.spi;

import java.util.Collection;

public interface StatefulSessionBeanInfo extends SessionBeanInfo {
   Collection getRemoveMethods();

   boolean isPassivationCapable();
}
