package weblogic.ejb.container.compliance;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.MessageDrivenBean;
import javax.jms.Message;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.MessageDrivenBeanInfo;
import weblogic.ejb.container.interfaces.MethodInfo;
import weblogic.ejb.container.utils.ClassUtils;
import weblogic.ejb20.dd.DescriptorErrorInfo;
import weblogic.utils.ErrorCollectionException;

public class MessageDrivenBeanClassChecker extends BaseComplianceChecker {
   private final Class beanClass;
   private final String ejbName;
   private final MessageDrivenBeanInfo beanInfo;
   private final Class messagingTypeClass;

   public MessageDrivenBeanClassChecker(BeanInfo bi) {
      this.beanInfo = (MessageDrivenBeanInfo)bi;
      this.beanClass = bi.getBeanClass();
      this.ejbName = bi.getEJBName();
      this.messagingTypeClass = this.beanInfo.getMessagingTypeInterfaceClass();
   }

   public void checkMessageDrivenImplementsMDB() throws ComplianceException {
      if (!this.beanInfo.isEJB30() && !MessageDrivenBean.class.isAssignableFrom(this.beanClass)) {
         throw new ComplianceException(this.getFmt().BEAN_CLASS_IMPLEMENTS_MESSAGE_DRIVEN(this.ejbName));
      }
   }

   public void checkMessageDrivenImplementsMessageListener() throws ComplianceException {
      if (!this.beanInfo.isEJB30()) {
         if (!this.messagingTypeClass.isAssignableFrom(this.beanClass)) {
            throw new ComplianceException(this.getFmt().BEAN_CLASS_IMPLEMENTS_MESSAGE_LISTENER(this.ejbName, this.beanInfo.getMessagingTypeInterfaceName()));
         }
      }
   }

   public void checkMessageDrivenBeanClassIsPublic() throws ComplianceException {
      if (!Modifier.isPublic(this.beanClass.getModifiers())) {
         throw new ComplianceException(this.getFmt().PUBLIC_BEAN_CLASS(this.ejbName));
      }
   }

   public void checkMessageDrivenBeanClassIsNotFinal() throws ComplianceException {
      if (Modifier.isFinal(this.beanClass.getModifiers())) {
         throw new ComplianceException(this.getFmt().FINAL_BEAN_CLASS(this.ejbName));
      }
   }

   public void checkMessageDrivenBeanClassIsNotAbstract() throws ComplianceException {
      if (Modifier.isAbstract(this.beanClass.getModifiers())) {
         throw new ComplianceException(this.getFmt().ABSTRACT_BEAN_CLASS(this.ejbName));
      }
   }

   public void checkBeanClassDoesNotDefineFinalize() throws ComplianceException {
      try {
         this.beanClass.getMethod("finalize", (Class[])null);
         throw new ComplianceException(this.getFmt().NO_FINALIZE_IN_BEAN(this.ejbName));
      } catch (NoSuchMethodException var2) {
      }
   }

   public void checkBeanClassHasPublicNoArgCtor() throws ComplianceException {
      if (!ComplianceUtils.classHasPublicNoArgCtor(this.beanClass)) {
         throw new ComplianceException(this.getFmt().PUBLIC_NOARG_BEAN_CTOR(this.ejbName));
      }
   }

   private void validateOnMessageMethod(Method onMessageMethod) throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      int mod = onMessageMethod.getModifiers();
      if (!Modifier.isPublic(mod)) {
         errors.add(new ComplianceException(this.getFmt().PUBLIC_ONMESSAGE(this.ejbName)));
      }

      if (Modifier.isFinal(mod)) {
         errors.add(new ComplianceException(this.getFmt().FINAL_ONMESSAGE(this.ejbName)));
      }

      if (Modifier.isStatic(mod)) {
         errors.add(new ComplianceException(this.getFmt().STATIC_ONMESSAGE(this.ejbName)));
      }

      Class[] args = onMessageMethod.getParameterTypes();
      if (args != null && args.length == 1) {
         if (!Message.class.isAssignableFrom(args[0])) {
            errors.add(new ComplianceException(this.getFmt().ONMESSAGE_TAKES_MESSAGE(this.ejbName)));
         }
      } else {
         errors.add(new ComplianceException(this.getFmt().SINGLE_ONMESSAGE_REQUIRED(this.ejbName)));
      }

      if (!Void.TYPE.equals(onMessageMethod.getReturnType())) {
         errors.add(new ComplianceException(this.getFmt().ONMESSAGE_RETURNS_VOID(this.ejbName)));
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   private void validateMessagingTypeMethod(Method method) throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      String methodName = method.getName();
      int mod = method.getModifiers();
      if (!Modifier.isPublic(mod)) {
         errors.add(new ComplianceException(this.getFmt().BUS_METHOD_NOT_PUBLIC(this.ejbName, methodName)));
      }

      if (Modifier.isFinal(mod)) {
         errors.add(new ComplianceException(this.getFmt().BUS_METHOD_MUST_NOT_FINAL(this.ejbName, methodName)));
      }

      if (Modifier.isStatic(mod)) {
         errors.add(new ComplianceException(this.getFmt().BUS_METHOD_MUST_NOT_STATIC(this.ejbName, methodName)));
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   protected List getMessagingTypeMethods() {
      List methods = new ArrayList();
      if (this.messagingTypeClass != null) {
         Method[] var2 = this.messagingTypeClass.getMethods();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method m = var2[var4];
            methods.add(m);
         }
      }

      return methods;
   }

   public void checkMessagingTypeMethods() throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      if (this.beanInfo.getIsJMSBased()) {
         try {
            this.validateOnMessageMethod(this.beanClass.getMethod("onMessage", Message.class));
         } catch (ErrorCollectionException var8) {
            errors.add(var8);
         } catch (NoSuchMethodException var9) {
            errors.add(new ComplianceException(this.getFmt().BEAN_MUST_HAVE_ONMESSAGE(this.ejbName)));
         }
      } else {
         Iterator var2 = this.getMessagingTypeMethods().iterator();

         while(var2.hasNext()) {
            Method eachMethod = (Method)var2.next();
            Method busMethod = null;

            try {
               busMethod = ClassUtils.getDeclaredMethod(this.beanClass, eachMethod.getName(), eachMethod.getParameterTypes());
               this.validateMessagingTypeMethod(busMethod);
            } catch (ErrorCollectionException var6) {
               errors.add(var6);
            } catch (NoSuchMethodException var7) {
               errors.add(new ComplianceException(this.getFmt().MT_METHOD_DOESNT_EXIST_IN_BEAN(this.ejbName, DDUtils.getMethodSignature(eachMethod))));
            }
         }
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   private void validateCreateMethod(Method createMethod) throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
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

      if (!Void.TYPE.equals(createMethod.getReturnType())) {
         errors.add(new ComplianceException(this.getFmt().EJBCREATE_RETURNS_VOID(this.ejbName)));
      }

      if (!ComplianceUtils.methodTakesNoArgs(createMethod)) {
         errors.add(new ComplianceException(this.getFmt().MESSAGE_NOARG_EJBCREATE(this.ejbName)));
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   public void checkEjbCreateMethod() throws ErrorCollectionException, ComplianceException {
      if (!this.beanInfo.isEJB30()) {
         try {
            this.validateCreateMethod(this.beanClass.getMethod("ejbCreate", (Class[])null));
         } catch (NoSuchMethodException var2) {
            throw new ComplianceException(this.getFmt().MESSAGE_DEFINES_EJBCREATE(this.ejbName));
         }
      }
   }

   private void validateRemoveMethod(Method removeMethod) throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      int mod = removeMethod.getModifiers();
      if (!Modifier.isPublic(mod)) {
         errors.add(new ComplianceException(this.getFmt().PUBLIC_EJBREMOVE(this.ejbName)));
      }

      if (Modifier.isFinal(mod)) {
         errors.add(new ComplianceException(this.getFmt().FINAL_EJBREMOVE(this.ejbName)));
      }

      if (Modifier.isStatic(mod)) {
         errors.add(new ComplianceException(this.getFmt().STATIC_EJBREMOVE(this.ejbName)));
      }

      if (!Void.TYPE.equals(removeMethod.getReturnType())) {
         errors.add(new ComplianceException(this.getFmt().EJBREMOVE_RETURNS_VOID(this.ejbName)));
      }

      if (!ComplianceUtils.methodTakesNoArgs(removeMethod)) {
         errors.add(new ComplianceException(this.getFmt().MESSAGE_NOARG_EJBREMOVE(this.ejbName)));
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   public void checkEjbRemoveMethod() throws ErrorCollectionException, ComplianceException {
      if (!this.beanInfo.isEJB30()) {
         try {
            this.validateRemoveMethod(this.beanClass.getMethod("ejbRemove", (Class[])null));
         } catch (NoSuchMethodException var2) {
            throw new ComplianceException(this.getFmt().MESSAGE_DEFINES_EJBREMOVE(this.ejbName));
         }
      }
   }

   public void checkTimeoutMethods() throws ErrorCollectionException, ComplianceException {
      TimeoutCheckHelper.validateTimeoutMethod(this.beanInfo);
   }

   public void checkTransactionAttribute() throws ComplianceException {
      Iterator var1 = this.beanInfo.getAllMessagingTypeMethodInfos().iterator();

      short txAttribute;
      do {
         if (!var1.hasNext()) {
            TimeoutCheckHelper.validateTimeoutMethodsTransactionType(this.beanInfo);
            return;
         }

         MethodInfo mi = (MethodInfo)var1.next();
         txAttribute = mi.getTransactionAttribute();
      } while(1 == txAttribute || 0 == txAttribute);

      throw new ComplianceException(this.getFmt().MESSAGE_BAD_TX_ATTRIBUTE(this.ejbName));
   }

   public void checkMaxBeansInFreePoolGreaterThanZero() throws ComplianceException {
      int m = this.beanInfo.getCachingDescriptor().getMaxBeansInFreePool();
      if (m <= 0) {
         throw new ComplianceException(this.getFmt().MESSAGE_ILLEGAL_MAX_BEANS_IN_FREE_POOL(this.ejbName, m), new DescriptorErrorInfo("<max-beans-in-free-pool>", this.ejbName, (Object)null));
      }
   }
}
