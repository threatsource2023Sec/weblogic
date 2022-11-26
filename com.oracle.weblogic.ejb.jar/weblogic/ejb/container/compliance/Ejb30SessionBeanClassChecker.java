package weblogic.ejb.container.compliance;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import javax.ejb.AfterBegin;
import javax.ejb.AfterCompletion;
import javax.ejb.BeforeCompletion;
import javax.ejb.SessionBean;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.interfaces.MethodInfo;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.interfaces.StatefulSessionBeanInfo;
import weblogic.ejb.container.utils.EJBMethodsUtil;
import weblogic.j2ee.descriptor.AccessTimeoutBean;
import weblogic.j2ee.descriptor.ConcurrentMethodBean;
import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.j2ee.descriptor.LifecycleCallbackBean;
import weblogic.j2ee.descriptor.SessionBeanBean;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.StringUtils;

public final class Ejb30SessionBeanClassChecker extends SessionBeanClassChecker {
   private final SessionBeanInfo sbi;
   private final EjbJarBean ejbJarBean;
   private SessionBeanBean currentSessionBean;

   public Ejb30SessionBeanClassChecker(SessionBeanInfo sbi) {
      super(sbi);
      this.sbi = sbi;
      this.ejbJarBean = sbi.getDeploymentInfo().getEjbDescriptorBean().getEjbJarBean();
   }

   public void checkSBInterfaceConstraints() throws ComplianceException {
      LifecycleCallbackBean[] preDestroyCallbackBeans = null;
      LifecycleCallbackBean[] postActivateCallbackBeans = null;
      LifecycleCallbackBean[] prePassivateCallbackBeans = null;
      LifecycleCallbackBean[] postConsctructCallbackBeans = null;
      SessionBeanBean[] var5 = this.ejbJarBean.getEnterpriseBeans().getSessions();
      int var6 = var5.length;

      int var7;
      for(var7 = 0; var7 < var6; ++var7) {
         SessionBeanBean sbb = var5[var7];
         if (sbb.getEjbClass().equals(this.sbi.getBeanClassName())) {
            postConsctructCallbackBeans = sbb.getPostConstructs();
            preDestroyCallbackBeans = sbb.getPreDestroys();
            if (this.sbi.isStateful()) {
               postActivateCallbackBeans = sbb.getPostActivates();
               prePassivateCallbackBeans = sbb.getPrePassivates();
            }
            break;
         }
      }

      Class beanClazz = this.sbi.getBeanClass();
      LifecycleCallbackBean ppcb;
      LifecycleCallbackBean[] var12;
      int var13;
      if (this.sbi.isStateless()) {
         try {
            beanClazz.getMethod("ejbCreate");
            if (postConsctructCallbackBeans != null) {
               var12 = postConsctructCallbackBeans;
               var7 = postConsctructCallbackBeans.length;

               for(var13 = 0; var13 < var7; ++var13) {
                  ppcb = var12[var13];
                  if (ppcb.getLifecycleCallbackClass().equals(beanClazz.getName()) && !ppcb.getLifecycleCallbackMethod().equals("ejbCreate")) {
                     throw new ComplianceException(this.getFmt().SLSB_POSTCONSTRUCT_NOT_APPLY_EJBCREATE(this.sbi.getEJBName()));
                  }
               }
            }
         } catch (NoSuchMethodException var10) {
         }
      }

      if (SessionBean.class.isAssignableFrom(beanClazz)) {
         if (preDestroyCallbackBeans != null) {
            var12 = preDestroyCallbackBeans;
            var7 = preDestroyCallbackBeans.length;

            for(var13 = 0; var13 < var7; ++var13) {
               ppcb = var12[var13];
               if (ppcb.getLifecycleCallbackClass().equals(beanClazz.getName()) && !ppcb.getLifecycleCallbackMethod().equals("ejbRemove")) {
                  throw new ComplianceException(this.getFmt().SESSION_BEAN_PREDESTROY_NOT_APPLY_EJBREMOVE(this.sbi.getEJBName()));
               }
            }
         }

         if (postActivateCallbackBeans != null) {
            var12 = postActivateCallbackBeans;
            var7 = postActivateCallbackBeans.length;

            for(var13 = 0; var13 < var7; ++var13) {
               ppcb = var12[var13];
               if (ppcb.getLifecycleCallbackClass().equals(beanClazz.getName()) && !ppcb.getLifecycleCallbackMethod().equals("ejbActivate")) {
                  throw new ComplianceException(this.getFmt().SESSION_BEAN_POSTACTIVATE_NOT_APPLY_EJBACTIVE(this.sbi.getEJBName()));
               }
            }
         }

         if (prePassivateCallbackBeans != null) {
            var12 = prePassivateCallbackBeans;
            var7 = prePassivateCallbackBeans.length;

            for(var13 = 0; var13 < var7; ++var13) {
               ppcb = var12[var13];
               if (ppcb.getLifecycleCallbackClass().equals(beanClazz.getName()) && !ppcb.getLifecycleCallbackMethod().equals("ejbPassivate")) {
                  throw new ComplianceException(this.getFmt().SESSION_BEAN_PREPASSIVATE_NOT_APPLY_EJBPASSIVATE(this.sbi.getEJBName()));
               }
            }
         }
      }

      if (this.sbi.isStateful()) {
         this.validateStatefulTimeoutValue();
         this.validatePassivationCapableMetaData();
      }

      this.validateConcurrentMethods();
      this.validateAsyncWebserviceClientView();
      this.validateAsyncMehtodNotInEjb2xRemoteAndLocal();
      this.validateNoClientViewExceptionType();
      this.validateAutocreatedClusteredTimerCount();
   }

   private void validatePassivationCapableMetaData() throws ComplianceException {
      SessionBeanBean sbb = this.getCurrentSessionBeanBean();
      if (!sbb.isPassivationCapable()) {
         if (sbb.getPostActivates().length > 0 || sbb.getPrePassivates().length > 0) {
            throw new ComplianceException(this.getFmt().WRONG_STATEFUL_LIFECYCLE_NONPASSIVATABLE(this.sbi.getEJBName()));
         }

         if (((StatefulSessionBeanInfo)this.sbi).isReplicated()) {
            throw new ComplianceException(this.getFmt().WRONG_STATEFUL_REPLICATETYPE_NONPASSIVATABLE(this.sbi.getEJBName()));
         }
      }

   }

   private void validateStatefulTimeoutValue() throws ComplianceException {
      SessionBeanBean sbb = this.getCurrentSessionBeanBean();
      if (sbb != null && sbb.getStatefulTimeout() != null && sbb.getStatefulTimeout().getTimeout() < -1L) {
         throw new ComplianceException(this.getFmt().WRONG_STATEFUL_TIMEOUT_VALUE(this.sbi.getBeanClassName()));
      }
   }

   private void validateNoClientViewExceptionType() throws ComplianceException {
      if (this.sbi.hasNoIntfView()) {
         Method[] var1 = EJBMethodsUtil.getNoInterfaceViewBusinessMethods(this.sbi.getBeanClass());
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Method bm = var1[var3];
            Class[] var5 = bm.getExceptionTypes();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Class e = var5[var7];
               if (RemoteException.class == e) {
                  throw new ComplianceException(this.getFmt().NO_INTERFACE_VIEW_METHOD_CANNOT_THROW_REMOTEEXCEPTION(bm.getName(), this.sbi.getBeanClassName()));
               }
            }
         }
      }

   }

   private void validateAsyncWebserviceClientView() throws ComplianceException {
      Iterator var1 = this.sbi.getAllWebserviceMethodInfos().iterator();

      MethodInfo each;
      do {
         if (!var1.hasNext()) {
            return;
         }

         each = (MethodInfo)var1.next();
      } while(!this.sbi.isAsyncMethod(each.getMethod()));

      throw new ComplianceException(this.getFmt().ASYNC_METHOD_CANNOT_IN_WEBSERVICE_CLIENT_VIEW(this.sbi.getBeanClassName(), each.getMethod().getName()));
   }

   private void validateConcurrentMethods() throws ComplianceException {
      SessionBeanBean sessionBeanBean = this.getCurrentSessionBeanBean();
      if (sessionBeanBean != null) {
         ConcurrentMethodBean[] concurrentMethodBeans = sessionBeanBean.getConcurrentMethods();
         if (concurrentMethodBeans != null) {
            ConcurrentMethodBean[] var3 = concurrentMethodBeans;
            int var4 = concurrentMethodBeans.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               ConcurrentMethodBean concurrentMethodBean = var3[var5];
               this.validateAccessTimeoutValue(concurrentMethodBean);
               this.validateStatefulSessionBeanLockMethod(concurrentMethodBean);
            }

         }
      }
   }

   private void validateAccessTimeoutValue(ConcurrentMethodBean concurrentMethodBean) throws ComplianceException {
      AccessTimeoutBean accessTimeoutBean = concurrentMethodBean.getAccessTimeout();
      if (accessTimeoutBean != null) {
         if (accessTimeoutBean.getTimeout() < -1L) {
            throw new ComplianceException(this.getFmt().WRONG_ACCESS_TIMEOUT_VALUE(concurrentMethodBean.getMethod().getMethodName(), this.sbi.getBeanClassName()));
         }
      }
   }

   private void validateStatefulSessionBeanLockMethod(ConcurrentMethodBean concurrentMethodBean) throws ComplianceException {
      if (!StringUtils.isEmptyString(concurrentMethodBean.getConcurrentLockType())) {
         if (this.sbi.isStateful()) {
            throw new ComplianceException(this.getFmt().STATEFUL_SESSION_BEAN_MUST_NOT_HAVE_LOCKING_METADATA(concurrentMethodBean.getMethod().getMethodName(), this.sbi.getBeanClassName()));
         }
      }
   }

   private SessionBeanBean getCurrentSessionBeanBean() {
      if (this.currentSessionBean != null) {
         return this.currentSessionBean;
      } else {
         SessionBeanBean[] var1 = this.ejbJarBean.getEnterpriseBeans().getSessions();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            SessionBeanBean bean = var1[var3];
            if (this.sbi.getBeanClassName().equals(bean.getEjbClass())) {
               this.currentSessionBean = bean;
               return this.currentSessionBean;
            }
         }

         return null;
      }
   }

   public void checkBusinessMethods() throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      Set businessMethods = this.getInterfaceViewsBusinessMethods();
      Iterator var3 = businessMethods.iterator();

      while(var3.hasNext()) {
         Method businessMethod = (Method)var3.next();

         try {
            this.validateBusinessMethod(businessMethod, false);
         } catch (ErrorCollectionException var8) {
            errors.add(var8);
         }
      }

      if (this.sbi.hasNoIntfView()) {
         List bizMethodsFromNoIntfView = this.getNoInterfaceViewBusinessMethods();
         bizMethodsFromNoIntfView.removeAll(businessMethods);
         Iterator var10 = bizMethodsFromNoIntfView.iterator();

         while(var10.hasNext()) {
            Method businessMethod = (Method)var10.next();

            try {
               this.validateBusinessMethod(businessMethod, true);
            } catch (ErrorCollectionException var7) {
               errors.add(var7);
            }
         }
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   private void validateBusinessMethod(Method businessMethod, boolean fromNoIntfView) throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      int mod = businessMethod.getModifiers();
      if (!fromNoIntfView && businessMethod.getName().startsWith("ejb")) {
         errors.add(new ComplianceException(this.getFmt().METHOD_CANNOT_START_WITH_EJB(businessMethod.getName(), businessMethod.getDeclaringClass().getName())));
      }

      if (!fromNoIntfView && !Modifier.isPublic(mod)) {
         errors.add(new ComplianceException(this.getFmt().BUSINESS_METHOD_MUST_BE_PUBLIC(businessMethod.getName(), businessMethod.getDeclaringClass().getName())));
      }

      if (Modifier.isFinal(mod)) {
         errors.add(new ComplianceException(this.getFmt().BUSINESS_METHOD_MUST_NOT_BE_FINAL(businessMethod.getName(), businessMethod.getDeclaringClass().getName())));
      }

      if (Modifier.isStatic(mod)) {
         errors.add(new ComplianceException(this.getFmt().BUSINESS_METHOD_MUST_NOT_BE_STATIC(businessMethod.getName(), businessMethod.getDeclaringClass().getName())));
      }

      if (this.sbi.isAsyncMethod(businessMethod)) {
         this.validateAsyncMethodReturnValue(businessMethod, errors);
         this.validateAsyncMethodDeclaredException(businessMethod, errors);
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   private void validateAsyncMehtodNotInEjb2xRemoteAndLocal() throws ComplianceException {
      if (this.sbi.hasAsyncMethods()) {
         Iterator var1 = this.getAllEjb2xLocalAndRemoteMethods().iterator();

         Method each;
         do {
            if (!var1.hasNext()) {
               return;
            }

            each = (Method)var1.next();
         } while(!this.sbi.isAsyncMethod(each));

         throw new ComplianceException(this.getFmt().ASYNC_METHOD_CANNOT_IN_EJB2X_CLIENT_VIEW(this.sbi.getBeanClassName(), each.getName()));
      }
   }

   private Set getAllEjb2xLocalAndRemoteMethods() {
      Set result = new HashSet();
      Class remote = this.sbi.getRemoteInterfaceClass();
      if (remote != null) {
         Collections.addAll(result, remote.getMethods());
      }

      Class local = this.sbi.getLocalInterfaceClass();
      if (local != null) {
         Collections.addAll(result, local.getMethods());
      }

      return result;
   }

   private void validateAsyncMethodReturnValue(Method method, ErrorCollectionException errors) {
      Class returnType = method.getReturnType();
      if (Void.TYPE != returnType && !Future.class.isAssignableFrom(returnType)) {
         errors.add(new ComplianceException(this.getFmt().ASYNC_METHOD_RETURN_ERROR_VALUE_TYPE(method.getName(), this.sbi.getBeanClassName(), returnType.toString())));
      }
   }

   private void validateAsyncMethodDeclaredException(Method method, ErrorCollectionException errors) {
      if (Void.TYPE == method.getReturnType()) {
         Class[] var3 = method.getExceptionTypes();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Class clazz = var3[var5];
            if (ComplianceUtils.isApplicationException(this.sbi, method, clazz)) {
               errors.add(new ComplianceException(this.getFmt().ASYNC_METHOD_DECLARED_ERROR_EXCEPTION_TYPE(method.getName(), this.sbi.getBeanClassName(), clazz.toString())));
               break;
            }
         }

      }
   }

   private List getNoInterfaceViewBusinessMethods() {
      Method[] allMethods = EJBMethodsUtil.getNoInterfaceViewBusinessMethods(this.sbi.getBeanClass());
      List result = new ArrayList();
      if (!this.sbi.hasDeclaredLocalHome() && !this.sbi.hasDeclaredRemoteHome()) {
         Collections.addAll(result, allMethods);
      } else {
         Method[] var3 = allMethods;
         int var4 = allMethods.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Method each = var3[var5];
            if (!this.isEjbCreateMethod(each)) {
               result.add(each);
            }
         }
      }

      return result;
   }

   private boolean isEjbCreateMethod(Method method) {
      return method.getName().startsWith("ejbCreate");
   }

   private Set getInterfaceViewsBusinessMethods() {
      Set bMethods = new HashSet();
      Map msToMethods = new HashMap();
      Method[] beanMethods = this.sbi.getBeanClass().getMethods();
      String[] bmSignatures = new String[beanMethods.length];

      for(int i = 0; i < beanMethods.length; ++i) {
         bmSignatures[i] = DDUtils.getMethodSignature(beanMethods[i]);
         msToMethods.put(bmSignatures[i], beanMethods[i]);
      }

      Iterator var12 = this.sbi.getBusinessLocals().iterator();

      Class iface;
      Method[] var7;
      int var8;
      int var9;
      Method biMethod;
      Method businessMethod;
      while(var12.hasNext()) {
         iface = (Class)var12.next();
         var7 = iface.getMethods();
         var8 = var7.length;

         for(var9 = 0; var9 < var8; ++var9) {
            biMethod = var7[var9];
            businessMethod = (Method)msToMethods.get(DDUtils.getMethodSignature(biMethod));
            if (businessMethod != null) {
               bMethods.add(businessMethod);
            }
         }
      }

      var12 = this.sbi.getBusinessRemotes().iterator();

      while(var12.hasNext()) {
         iface = (Class)var12.next();
         var7 = iface.getMethods();
         var8 = var7.length;

         for(var9 = 0; var9 < var8; ++var9) {
            biMethod = var7[var9];
            businessMethod = (Method)msToMethods.get(DDUtils.getMethodSignature(biMethod));
            if (businessMethod != null) {
               bMethods.add(businessMethod);
            }
         }
      }

      return bMethods;
   }

   public void checkCommonMethods() throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      Method[] var2 = this.sbi.getBeanClass().getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method method = var2[var4];
         this.validateCommonMethod(method, errors);
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   private void validateCommonMethod(Method method, ErrorCollectionException errors) {
      if (this.sbi.isSingleton() && (method.isAnnotationPresent(AfterBegin.class) || method.isAnnotationPresent(AfterCompletion.class) || method.isAnnotationPresent(BeforeCompletion.class))) {
         errors.add(new ComplianceException(this.getFmt().SINGLETON_METHOD_CANNOT_USE_SESSIONSYNCHRONIZATION_ANNOTATION(method.getName(), this.sbi.getBeanClassName())));
      }

   }

   public static void validateRemoveMethodToBeBusinessMethod(SessionBeanBean sessionBeanBean, MethodInfo mi, String signature) throws ComplianceException {
      if (mi == null) {
         throw new ComplianceException(EJBComplianceTextFormatter.getInstance().REMOVE_METHOD_NOT_BE_BUSINESS_METHOD(signature.toString(), sessionBeanBean.getEjbName()));
      }
   }

   private void validateAutocreatedClusteredTimerCount() throws ComplianceException {
      if (this.sbi.isClusteredTimers()) {
         SessionBeanBean sbb = this.getCurrentSessionBeanBean();
         if (sbb != null) {
            TimeoutCheckHelper.validateAutocreatedClusteredTimerCount(sbb.getTimers(), this.sbi.getBeanClass());
         }
      }

   }
}
