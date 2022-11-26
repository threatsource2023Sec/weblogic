package weblogic.ejb.spi;

import java.util.Set;

public interface SessionBeanInfo extends ClientDrivenBeanInfo {
   boolean isStateful();

   boolean isStateless();

   boolean isSingleton();

   WSObjectFactory getWSObjectFactory();

   Set getBusinessLocals();

   boolean hasNoIntfView();

   SessionBeanReference getSessionBeanReference();

   Set getBusinessRemotes();
}
