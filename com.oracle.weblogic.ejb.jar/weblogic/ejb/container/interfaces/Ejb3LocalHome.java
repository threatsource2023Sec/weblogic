package weblogic.ejb.container.interfaces;

import weblogic.ejb.spi.SessionBeanReference;

public interface Ejb3LocalHome {
   Object getBusinessImpl(Object var1, Class var2);

   SessionBeanReference getSessionBeanReference();
}
