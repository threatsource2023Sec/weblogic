package weblogic.ejb.container.compliance;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBContext;
import javax.ejb.SessionBean;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.interfaces.StatefulSessionBeanInfo;
import weblogic.ejb.container.swap.BeanStateDiffChecker;
import weblogic.utils.ErrorCollectionException;

public class SessionBeanClassChecker extends BeanClassChecker {
   private final SessionBeanInfo sbi;

   public SessionBeanClassChecker(SessionBeanInfo sbi) {
      super(sbi);
      this.sbi = sbi;
   }

   public void checkEJBContextIsNotTransient() throws ComplianceException {
      for(Class c = this.beanClass; c != null && !c.equals(Object.class); c = c.getSuperclass()) {
         Field[] var2 = c.getDeclaredFields();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Field f = var2[var4];
            Class t = f.getType();
            if (EJBContext.class.isAssignableFrom(t)) {
               if (Modifier.isTransient(f.getModifiers())) {
                  throw new ComplianceException(this.getFmt().EJBCONTEXT_IS_TRANSIENT(this.ejbName));
               }

               if (!ComplianceUtils.isLegalRMIIIOPType(t)) {
                  throw new ComplianceException(this.getFmt().NOT_RMIIIOP_LEGAL_TYPE_20(this.ejbName));
               }
            }
         }
      }

   }

   public void checkClassImplementsSessionBean() throws ComplianceException {
      if (!this.beanInfo.isEJB30() && !SessionBean.class.isAssignableFrom(this.beanClass)) {
         throw new ComplianceException(this.getFmt().BEAN_IMPLEMENT_SESSIONBEAN(this.ejbName));
      }
   }

   public void checkStatelessEjbCreate() throws ComplianceException, ErrorCollectionException {
      if (!this.beanInfo.isEJB30() && this.sbi.isStateless()) {
         if (this.beanInfo.hasDeclaredRemoteHome() || this.beanInfo.hasDeclaredLocalHome()) {
            if (this.getCreateMethods().size() != 1) {
               throw new ComplianceException(this.getFmt().STATELESS_NOARG_EJBCREATE(this.ejbName));
            } else {
               try {
                  List al = new ArrayList();
                  al.add(this.beanClass.getMethod("ejbCreate", (Class[])null));
                  this.validateEjbCreates(al);
               } catch (NoSuchMethodException var2) {
                  throw new ComplianceException(this.getFmt().STATELESS_NOARG_EJBCREATE(this.ejbName));
               }
            }
         }
      }
   }

   public void checkBeanClassIsNotAbstract() throws ComplianceException {
      if (Modifier.isAbstract(this.beanClassMod)) {
         throw new ComplianceException(this.getFmt().ABSTRACT_BEAN_CLASS(this.ejbName));
      }
   }

   public void checkStatefulEjbCreate() throws ErrorCollectionException, ComplianceException {
      if (!this.beanInfo.isEJB30() && this.sbi.isStateful()) {
         if (this.beanInfo.hasDeclaredRemoteHome() || this.beanInfo.hasDeclaredLocalHome()) {
            List createMethods = this.getCreateMethods();
            if (createMethods.size() == 0) {
               throw new ComplianceException(this.getFmt().STATEFUL_DEFINE_EJBCREATE(this.ejbName));
            } else {
               this.validateEjbCreates(createMethods);
            }
         }
      }
   }

   public void checkClientViewExists() throws ComplianceException {
      SessionBeanInfo si = (SessionBeanInfo)this.beanInfo;
      if (!si.isEndpointView()) {
         if (si.getLocalInterfaceName() == null && si.getRemoteInterfaceName() == null && si.getServiceEndpointName() == null) {
            if (!si.isEJB30()) {
               throw new ComplianceException(this.getFmt().COMPONENT_INTERFACE_NOT_FOUND_IN_SESSION_BEAN(this.ejbName));
            }

            if ((si.getBusinessLocals() == null || si.getBusinessLocals().isEmpty()) && (si.getBusinessRemotes() == null || si.getBusinessRemotes().isEmpty()) && !si.hasNoIntfView()) {
               throw new ComplianceException(this.getFmt().BUSINESS_INTERFACE_NOT_FOUND_IN_SESSION_BEAN(this.ejbName));
            }
         }

      }
   }

   protected void validateCreateReturnType(Method createMethod) throws ComplianceException {
      if (!createMethod.getReturnType().isAssignableFrom(Void.TYPE)) {
         throw new ComplianceException(this.getFmt().EJBCREATE_RETURNS_VOID(this.ejbName));
      }
   }

   public void checkStatefulBeanEligibleForStateReplication() throws ComplianceException {
      if (this.sbi.isStateful() && this.sbi instanceof StatefulSessionBeanInfo) {
         StatefulSessionBeanInfo sfsb = (StatefulSessionBeanInfo)this.sbi;
         if (sfsb.isReplicated() && sfsb.getCalculateDeltaUsingReflection() && !BeanStateDiffChecker.hasReplicatableState(this.beanClass)) {
            throw new ComplianceException(this.getFmt().STATEFUL_BEAN_CANNOT_REPLICATE_IN_MEMORY_STATE(this.ejbName));
         }
      }

   }
}
