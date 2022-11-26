package weblogic.ejb.container.internal;

import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.Ejb3LocalHome;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.spi.SessionBeanReference;
import weblogic.logging.Loggable;

public class SessionBeanReferenceImpl implements SessionBeanReference {
   private final SessionBeanInfo sbi;
   private final Ejb3LocalHome home;

   public SessionBeanReferenceImpl(SessionBeanInfo sbi, Ejb3LocalHome home) {
      this.sbi = sbi;
      this.home = home;
   }

   public Object getBusinessObject(Class businessInterfaceType) {
      if (!this.sbi.isLocalClientView(businessInterfaceType)) {
         Loggable l = EJBLogger.logInvalidLocalClientViewArgumentLoggable(businessInterfaceType.getName(), this.sbi.getDisplayName());
         throw new IllegalArgumentException(l.getMessage());
      } else {
         return this.home.getBusinessImpl((Object)null, businessInterfaceType);
      }
   }
}
