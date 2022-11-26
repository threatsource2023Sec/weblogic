package weblogic.ejb.container.internal;

import javax.ejb.EJBException;
import javax.ejb.NoSuchEJBException;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.manager.StatefulSessionManager;
import weblogic.ejb.spi.StatefulSessionBeanReference;
import weblogic.logging.Loggable;

public class StatefulSessionBeanReferenceImpl implements StatefulSessionBeanReference {
   private final SessionBeanInfo sbi;
   private final StatefulEJBLocalHomeImpl localHome;
   private final StatefulSessionManager beanManager;
   private Object pk = null;
   private boolean isRemoved = false;

   public StatefulSessionBeanReferenceImpl(SessionBeanInfo sbi, StatefulEJBLocalHomeImpl localHome) {
      this.sbi = sbi;
      this.beanManager = (StatefulSessionManager)sbi.getBeanManager();
      this.localHome = localHome;
   }

   public Object getBusinessObject(Class businessInterfaceType) {
      this.ensureNotAlreadyRemoved();
      if (!this.sbi.isLocalClientView(businessInterfaceType)) {
         Loggable l = EJBLogger.logInvalidLocalClientViewArgumentLoggable(businessInterfaceType.getName(), this.sbi.getDisplayName());
         throw new IllegalArgumentException(l.getMessage());
      } else {
         try {
            if (this.pk == null) {
               this.pk = this.beanManager.createBean();
            }

            return this.localHome.getBusinessImpl(this.pk, businessInterfaceType);
         } catch (InternalException var4) {
            Loggable l = EJBLogger.logErrorCreatingEJBReferenceLoggable(var4);
            throw new EJBException(l.getMessage());
         }
      }
   }

   public void remove() {
      this.ensureNotAlreadyRemoved();
      if (this.pk != null) {
         try {
            this.beanManager.remove(this.pk);
         } catch (InternalException var3) {
            Loggable l = EJBLogger.logErrorRemovingStatefulSessionBeanLoggable(var3);
            throw new EJBException(l.getMessage());
         }
      }

      this.isRemoved = true;
   }

   public boolean isRemoved() {
      if (this.pk != null) {
         this.beanManager.isRemoved(this.pk);
      }

      return this.isRemoved;
   }

   private void ensureNotAlreadyRemoved() throws NoSuchEJBException {
      if (this.isRemoved) {
         throw new NoSuchEJBException(EJBLogger.logErrorInvokingAlreadyRemovedEJBLoggable().getMessage());
      }
   }
}
