package weblogic.ejb.container.compliance;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.SessionSynchronization;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.interfaces.SingletonSessionBeanInfo;
import weblogic.ejb.container.utils.ClassUtils;
import weblogic.ejb20.dd.DescriptorErrorInfo;
import weblogic.j2ee.descriptor.EjbLocalRefBean;
import weblogic.j2ee.descriptor.EjbRefBean;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.annotation.BeaSynthetic.Helper;

abstract class BeanClassChecker extends BaseComplianceChecker {
   protected final Class beanClass;
   protected final int beanClassMod;
   private final Class remoteClass;
   private final Class localClass;
   protected final ClientDrivenBeanInfo beanInfo;
   protected final String ejbName;

   BeanClassChecker(ClientDrivenBeanInfo cbi) {
      this.beanClass = cbi.getBeanClass();
      this.beanClassMod = this.beanClass.getModifiers();
      this.beanInfo = cbi;
      this.remoteClass = this.beanInfo.getRemoteInterfaceClass();
      this.localClass = this.beanInfo.getLocalInterfaceClass();
      this.ejbName = this.beanInfo.getEJBName();
   }

   protected String section(String sessionBeanSection, String entityBeanSection) {
      return this.beanInfo.isEntityBean() ? entityBeanSection : sessionBeanSection;
   }

   protected List getCreateMethods() {
      List c = new ArrayList();
      Method[] var2 = this.beanClass.getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method m = var2[var4];
         if (m.getName().startsWith("ejbCreate")) {
            c.add(m);
         }
      }

      return c;
   }

   protected List getBusMethods() {
      List methodsList = new ArrayList();
      Method[] var2;
      int var3;
      int var4;
      Method m;
      if (this.remoteClass != null) {
         var2 = this.remoteClass.getMethods();
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            m = var2[var4];
            if (!this.isEJBObjectMethod(m) && !Helper.isBeaSyntheticMethod(m)) {
               methodsList.add(m);
            }
         }
      }

      if (this.localClass != null) {
         var2 = this.localClass.getMethods();
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            m = var2[var4];
            if (!this.isEJBObjectMethod(m) && !Helper.isBeaSyntheticMethod(m)) {
               methodsList.add(m);
            }
         }
      }

      return methodsList;
   }

   private boolean isEJBObjectMethod(Method md) {
      Class c = md.getDeclaringClass();
      return EJBObject.class.equals(c) || EJBLocalObject.class.equals(c);
   }

   abstract void validateCreateReturnType(Method var1) throws ComplianceException;

   protected void validateEjbCreates(List createMethods) throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      Iterator var3 = createMethods.iterator();

      while(var3.hasNext()) {
         Method createMethod = (Method)var3.next();
         int mod = createMethod.getModifiers();
         if (!Modifier.isPublic(mod)) {
            errors.add(new ComplianceException(this.getFmt().PUBLIC_EJBCREATE(this.ejbName)));
         }

         if (Modifier.isFinal(mod)) {
            errors.add(new ComplianceException(this.getFmt().FINAL_EJBCREATE(this.ejbName)));
         }

         if (Modifier.isStatic(mod)) {
            errors.add(new ComplianceException(this.getFmt().STATIC_EJBCREATE(this.ejbName)));
         }

         try {
            this.validateCreateReturnType(createMethod);
         } catch (ComplianceException var7) {
            errors.add(var7);
         }
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   public void checkRemoteView() throws ComplianceException {
      if (this.beanInfo.getHomeInterfaceName() != null && this.beanInfo.getRemoteInterfaceName() == null || this.beanInfo.getHomeInterfaceName() == null && this.beanInfo.getRemoteInterfaceName() != null) {
         throw new ComplianceException(this.getFmt().INCONSISTENT_REMOTE_VIEW(this.ejbName));
      }
   }

   public void checkLocalView() throws ComplianceException {
      if (this.beanInfo.getLocalHomeInterfaceName() != null && this.beanInfo.getLocalInterfaceName() == null || this.beanInfo.getLocalHomeInterfaceName() == null && this.beanInfo.getLocalInterfaceName() != null) {
         throw new ComplianceException(this.getFmt().INCONSISTENT_LOCAL_VIEW(this.ejbName));
      }
   }

   public void checkClientView() throws ComplianceException {
      if (!this.beanInfo.isEJB30()) {
         if (!this.beanInfo.hasRemoteClientView()) {
            if (!this.beanInfo.hasLocalClientView()) {
               if (!this.beanInfo.isSessionBean() || !((SessionBeanInfo)this.beanInfo).hasWebserviceClientView()) {
                  throw new ComplianceException(this.getFmt().NO_CLIENT_VIEW(this.ejbName));
               }
            }
         }
      }
   }

   public void checkSessionSynchronization() throws ComplianceException {
      if (SessionSynchronization.class.isAssignableFrom(this.beanClass)) {
         if (this.beanInfo.isEntityBean()) {
            throw new ComplianceException(this.getFmt().ENTITY_IMPLEMENT_SESSIONSYNCHRONIZATION(this.ejbName));
         }

         SessionBeanInfo sbi = (SessionBeanInfo)this.beanInfo;
         if (sbi.isStateless()) {
            throw new ComplianceException(this.getFmt().STATELESS_IMPLEMENT_SESSIONSYNCHRONIZATION(this.ejbName));
         }

         if (sbi.isSingleton()) {
            throw new ComplianceException(this.getFmt().SINGLETON_IMPLEMENT_SESSIONSYNCHRONIZATION(this.ejbName));
         }

         if (sbi.isStateful() && sbi.usesBeanManagedTx()) {
            throw new ComplianceException(this.getFmt().BEAN_MANAGED_IMPLEMENT_SESSIONSYNCHRONIZATION(this.ejbName));
         }
      }

   }

   public void checkTransactionAttribute() throws ComplianceException {
      TimeoutCheckHelper.validateTimeoutMethodsTransactionType(this.beanInfo);
   }

   public void checkBeanClassIsPublic() throws ComplianceException {
      if (!Modifier.isPublic(this.beanClassMod)) {
         throw new ComplianceException(this.getFmt().PUBLIC_BEAN_CLASS(this.ejbName));
      }
   }

   public void checkBeanClassIsNotFinal() throws ComplianceException {
      if (Modifier.isFinal(this.beanClassMod)) {
         throw new ComplianceException(this.getFmt().FINAL_BEAN_CLASS(this.ejbName));
      }
   }

   public void checkBeanClassHasPublicNoArgCtor() throws ComplianceException {
      if (!ComplianceUtils.classHasPublicNoArgCtor(this.beanClass)) {
         throw new ComplianceException(this.getFmt().PUBLIC_NOARG_BEAN_CTOR(this.ejbName));
      }
   }

   public void checkBeanClassDoesNotDefineFinalize() throws ComplianceException {
      try {
         this.beanClass.getMethod("finalize", (Class[])null);
         throw new ComplianceException(this.getFmt().NO_FINALIZE_IN_BEAN(this.ejbName));
      } catch (NoSuchMethodException var2) {
      }
   }

   public void checkBeanMethodsAreSynchronized() throws ErrorCollectionException {
      if (!this.beanInfo.isSessionBean() || !((SessionBeanInfo)this.beanInfo).isSingleton() || ((SingletonSessionBeanInfo)this.beanInfo).usesContainerManagedConcurrency()) {
         ErrorCollectionException errors = new ErrorCollectionException();
         Method[] var2 = this.beanClass.getMethods();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method m = var2[var4];
            if (Modifier.isSynchronized(m.getModifiers())) {
               errors.add(new ComplianceException(this.getFmt().NO_SYNCHRONIZED_METHODS(this.ejbName, this.methodSig(m))));
            }
         }

         if (!errors.isEmpty()) {
            throw errors;
         }
      }
   }

   public void checkLocalReferences() throws ErrorCollectionException {
      if (!this.beanInfo.isEJB30()) {
         ErrorCollectionException errors = new ErrorCollectionException();
         Map lJndiNames = this.beanInfo.getAllEJBLocalReferenceJNDINames();
         Iterator var3 = this.beanInfo.getAllEJBLocalReferences().iterator();

         while(true) {
            EjbLocalRefBean ejbLocalRef;
            String jndiName;
            do {
               String ejbLink;
               do {
                  if (!var3.hasNext()) {
                     if (!errors.isEmpty()) {
                        throw errors;
                     }

                     return;
                  }

                  ejbLocalRef = (EjbLocalRefBean)var3.next();
                  ejbLink = ejbLocalRef.getEjbLink();
               } while(ejbLink != null && ejbLink.length() > 0);

               jndiName = (String)lJndiNames.get(ejbLocalRef.getEjbRefName());
            } while(jndiName != null && jndiName.length() != 0);

            errors.add(new ComplianceException(this.getFmt().BEAN_MISSING_LREF_JNDI_NAME(this.ejbName, ejbLocalRef.getEjbRefName()), new DescriptorErrorInfo("<jndi-name>", this.ejbName, jndiName)));
         }
      }
   }

   public void checkReferences() throws ErrorCollectionException {
      if (!this.beanInfo.isEJB30()) {
         ErrorCollectionException errors = new ErrorCollectionException();
         Map lJndiNames = this.beanInfo.getAllEJBReferenceJNDINames();
         Iterator var3 = this.beanInfo.getAllEJBReferences().iterator();

         while(true) {
            EjbRefBean ejbRef;
            String jndiName;
            do {
               String ejbLink;
               do {
                  if (!var3.hasNext()) {
                     if (!errors.isEmpty()) {
                        throw errors;
                     }

                     return;
                  }

                  ejbRef = (EjbRefBean)var3.next();
                  ejbLink = ejbRef.getEjbLink();
               } while(ejbLink != null && ejbLink.length() > 0);

               jndiName = (String)lJndiNames.get(ejbRef.getEjbRefName());
            } while(jndiName != null && jndiName.length() != 0);

            errors.add(new ComplianceException(this.getFmt().BEAN_MISSING_REF_JNDI_NAME(this.ejbName, ejbRef.getEjbRefName())));
         }
      }
   }

   public void checkCallByReference() {
      if (this.beanInfo.hasRemoteClientView() && !this.beanInfo.useCallByReference() && !this.beanInfo.isWarningDisabled("BEA-010202")) {
         EJBLogger.logCallByReferenceNotEnabled(this.ejbName);
      }

   }

   public void checkBusinessMethods() throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      Iterator var2 = this.getBusMethods().iterator();

      while(var2.hasNext()) {
         Method eachMethod = (Method)var2.next();
         Method busMethod = null;

         try {
            busMethod = ClassUtils.getDeclaredMethod(this.beanClass, eachMethod.getName(), eachMethod.getParameterTypes());
            int mod = busMethod.getModifiers();
            if (!Modifier.isPublic(mod)) {
               errors.add(new ComplianceException(this.getFmt().BUS_METHOD_NOT_PUBLIC(this.ejbName, busMethod.getName())));
            }

            if (Modifier.isFinal(mod)) {
               errors.add(new ComplianceException(this.getFmt().BUS_METHOD_MUST_NOT_FINAL(this.ejbName, busMethod.getName())));
            }

            if (Modifier.isStatic(mod)) {
               errors.add(new ComplianceException(this.getFmt().BUS_METHOD_MUST_NOT_STATIC(this.ejbName, busMethod.getName())));
            }
         } catch (NoSuchMethodException var6) {
            if (EJBObject.class.isAssignableFrom(eachMethod.getDeclaringClass())) {
               errors.add(new ComplianceException(this.getFmt().EO_METHOD_DOESNT_EXIST_IN_BEAN(this.ejbName, DDUtils.getMethodSignature(eachMethod))));
            } else {
               errors.add(new ComplianceException(this.getFmt().ELO_METHOD_DOESNT_EXIST_IN_BEAN(this.ejbName, DDUtils.getMethodSignature(eachMethod))));
            }
         }
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   public void checkTimeoutMethods() throws ErrorCollectionException, ComplianceException {
      TimeoutCheckHelper.validateTimeoutMethod(this.beanInfo);
   }

   public void checkAppExceptions() throws ErrorCollectionException {
      if (this.beanInfo.isEJB30()) {
         if (this.beanInfo.getDeploymentInfo().getApplicationExceptions() != null) {
            ClassLoader loader = this.beanInfo.getClassLoader();
            Class eClass = null;
            ErrorCollectionException errors = new ErrorCollectionException();
            Iterator var4 = this.beanInfo.getDeploymentInfo().getApplicationExceptions().keySet().iterator();

            while(var4.hasNext()) {
               String eName = (String)var4.next();

               try {
                  eClass = loader == null ? Class.forName(eName) : loader.loadClass(eName);
               } catch (ClassNotFoundException var7) {
                  errors.add(var7);
                  continue;
               }

               if (RemoteException.class.isAssignableFrom(eClass)) {
                  errors.add(new ComplianceException(this.getFmt().EXCEPTION_CANNOT_EXTEND_REMOTEEXCEPTION(this.ejbName)));
               }
            }

            if (!errors.isEmpty()) {
               throw errors;
            }
         }
      }
   }
}
