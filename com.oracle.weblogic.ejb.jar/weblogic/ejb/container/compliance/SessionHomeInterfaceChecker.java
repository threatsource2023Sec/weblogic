package weblogic.ejb.container.compliance;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import weblogic.ejb.container.interfaces.SessionBeanInfo;

public final class SessionHomeInterfaceChecker extends HomeInterfaceChecker {
   private final SessionBeanInfo sbi;

   public SessionHomeInterfaceChecker(Class homeInterface, Class compInterface, Class beanClass, SessionBeanInfo sbi, Class ejbHomeClass) {
      super(homeInterface, compInterface, beanClass, sbi, ejbHomeClass);
      this.sbi = sbi;
   }

   public void checkStatefulCreates() throws ComplianceException {
      if (this.sbi.isStateful()) {
         if (this.getCreateMethods().isEmpty()) {
            if (this.checkingRemoteClientView()) {
               throw new ComplianceException(this.getFmt().STATEFUL_HOME_CREATE(this.ejbName));
            } else {
               throw new ComplianceException(this.getFmt().STATEFUL_LOCAL_HOME_CREATE(this.ejbName));
            }
         }
      }
   }

   public void checkStatelessNoArgCreate() throws ComplianceException {
      if (this.sbi.isStateless()) {
         List creates = this.getCreateMethods();
         if (creates.size() != 1) {
            if (this.checkingRemoteClientView()) {
               throw new ComplianceException(this.getFmt().STATELESS_HOME_NOARG_CREATE(this.ejbName));
            } else {
               throw new ComplianceException(this.getFmt().STATELESS_LOCAL_HOME_NOARG_CREATE(this.ejbName));
            }
         } else {
            Method m = (Method)creates.get(0);
            if (!"create".equals(m.getName())) {
               if (this.checkingRemoteClientView()) {
                  throw new ComplianceException(this.getFmt().STATELESS_HOME_NOARG_CREATE(this.ejbName));
               } else {
                  throw new ComplianceException(this.getFmt().STATELESS_LOCAL_HOME_NOARG_CREATE(this.ejbName));
               }
            } else if (!ComplianceUtils.methodTakesNoArgs(m)) {
               if (this.checkingRemoteClientView()) {
                  throw new ComplianceException(this.getFmt().STATELESS_HOME_NOARG_CREATE(this.ejbName));
               } else {
                  throw new ComplianceException(this.getFmt().STATELESS_LOCAL_HOME_NOARG_CREATE(this.ejbName));
               }
            }
         }
      }
   }

   public void checkNoHomeMethods() throws ComplianceException {
      List h = this.getHomeMethods();
      h.addAll(this.getHomeInterfaceHomeMethods());
      if (h.size() > 0) {
         String methodName = ((Method)h.iterator().next()).getName();
         throw new ComplianceException(this.getFmt().HOME_METHODS_NOT_ALLOWED_ON_SESSION_20(this.ejbName, this.methodSig(methodName)));
      }
   }

   protected List getHomeInterfaceHomeMethods() {
      List result = super.getHomeInterfaceHomeMethods();
      Iterator it = result.iterator();

      while(it.hasNext()) {
         if (((Method)it.next()).getName().equals("remove")) {
            it.remove();
         }
      }

      return result;
   }
}
