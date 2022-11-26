package weblogic.j2ee.dd.xml.validator.lifecyclecallback;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.interceptor.AroundConstruct;
import javax.interceptor.InvocationContext;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.dd.xml.validator.AbstractAnnotationValidator;
import weblogic.j2ee.dd.xml.validator.AnnotationValidatorHelper;
import weblogic.j2ee.descriptor.EjbCallbackBean;
import weblogic.j2ee.descriptor.J2eeClientEnvironmentBean;
import weblogic.j2ee.descriptor.LifecycleCallbackBean;
import weblogic.utils.ErrorCollectionException;

abstract class BaseValidator extends AbstractAnnotationValidator {
   protected Class getClass(DescriptorBean bean, ClassLoader cl) throws ClassNotFoundException {
      String c = ((LifecycleCallbackBean)bean).getLifecycleCallbackClass();
      return AnnotationValidatorHelper.getClass(c, cl);
   }

   protected final Field getField(DescriptorBean bean, Class clazz) {
      return null;
   }

   protected final void checkField(Field field, ErrorCollectionException errors) {
   }

   protected final void checkAnnotation(Method method, Field field, ErrorCollectionException errors) {
   }

   protected final Method getMethod(DescriptorBean bean, Class clazz) {
      String method = ((LifecycleCallbackBean)bean).getLifecycleCallbackMethod();
      List methods = AnnotationValidatorHelper.getMethods(clazz, method);
      if (methods.size() == 0) {
         return null;
      } else {
         return methods.size() == 1 ? (Method)methods.get(0) : this.guessMethod(methods);
      }
   }

   private Method guessMethod(List methods) {
      Method method = this.guessMethodFromAnnotation(methods);
      if (method != null) {
         return method;
      } else {
         method = this.guessMethodFromSignature(methods);
         return method != null ? method : (Method)methods.get(0);
      }
   }

   protected Method guessMethodFromAnnotation(List methods) {
      Iterator var2 = methods.iterator();

      Method method;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         method = (Method)var2.next();
      } while(!method.isAnnotationPresent(PreDestroy.class) && !method.isAnnotationPresent(PostConstruct.class));

      return method;
   }

   protected Method guessMethodFromSignature(List methods) {
      Iterator var2 = methods.iterator();

      Method method;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         method = (Method)var2.next();
      } while(!method.isAnnotationPresent(PreDestroy.class) && !method.isAnnotationPresent(PostConstruct.class));

      return method;
   }

   protected final void checkBean(DescriptorBean bean, ErrorCollectionException errors) {
      DescriptorBean parent = bean.getParentBean();
      Set set = new HashSet();
      LifecycleCallbackBean[] callbacks;
      String clazzName;
      LifecycleCallbackBean[] var8;
      int var9;
      int var10;
      LifecycleCallbackBean postActivate;
      if (parent instanceof J2eeClientEnvironmentBean) {
         J2eeClientEnvironmentBean clientBean = (J2eeClientEnvironmentBean)parent;
         callbacks = clientBean.getPostConstructs();
         var8 = callbacks;
         var9 = callbacks.length;

         for(var10 = 0; var10 < var9; ++var10) {
            postActivate = var8[var10];
            clazzName = postActivate.getLifecycleCallbackClass();
            if (postActivate.getBeanSource() == 1 && !set.add(clazzName)) {
               errors.add(this.error("Cannot define multiple post-construct lifecycle callback methods on class \"" + clazzName + "\""));
               break;
            }
         }

         set.clear();
         callbacks = clientBean.getPreDestroys();
         var8 = callbacks;
         var9 = callbacks.length;

         for(var10 = 0; var10 < var9; ++var10) {
            postActivate = var8[var10];
            clazzName = postActivate.getLifecycleCallbackClass();
            if (postActivate.getBeanSource() == 1 && !set.add(clazzName)) {
               errors.add(this.error("Cannot define multiple pre-destroy lifecycle callback methods on class \"" + clazzName + "\""));
               break;
            }
         }
      }

      set.clear();
      if (parent instanceof EjbCallbackBean) {
         EjbCallbackBean ejbCallbackBean = (EjbCallbackBean)parent;
         callbacks = ejbCallbackBean.getPrePassivates();
         var8 = callbacks;
         var9 = callbacks.length;

         for(var10 = 0; var10 < var9; ++var10) {
            postActivate = var8[var10];
            clazzName = postActivate.getLifecycleCallbackClass();
            if (postActivate.getBeanSource() == 1 && !set.add(clazzName)) {
               errors.add(this.error("Cannot define multiple pre-passivate lifecycle callback methods on class \"" + clazzName + "\""));
               break;
            }
         }

         set.clear();
         callbacks = ejbCallbackBean.getPostActivates();
         var8 = callbacks;
         var9 = callbacks.length;

         for(var10 = 0; var10 < var9; ++var10) {
            postActivate = var8[var10];
            clazzName = postActivate.getLifecycleCallbackClass();
            if (postActivate.getBeanSource() == 1 && !set.add(clazzName)) {
               errors.add(this.error("Cannot define multiple post-activate lifecycle callback methods on class \"" + clazzName + "\""));
               break;
            }
         }
      }

   }

   protected void checkReturnType(Method method, ErrorCollectionException errors) {
      Class[] paramTypes = method.getParameterTypes();
      if (paramTypes != null && paramTypes.length > 0 && paramTypes[0] == InvocationContext.class) {
         if (method.getReturnType() != Void.TYPE && method.getReturnType() != Object.class) {
            errors.add(this.error(method, "its return type must be void or Object"));
         }
      } else if (method.getReturnType() != Void.TYPE) {
         errors.add(this.error(method, "its return type must be void"));
      }

   }

   protected void checkException(Method method, ErrorCollectionException errors) {
      boolean aroundConstuctAnnotation = method.isAnnotationPresent(AroundConstruct.class);
      boolean otherLifecycleAnnotation = method.isAnnotationPresent(PreDestroy.class) || method.isAnnotationPresent(PostConstruct.class) || method.isAnnotationPresent(PrePassivate.class) || method.isAnnotationPresent(PostActivate.class);
      if (!aroundConstuctAnnotation || otherLifecycleAnnotation) {
         Class[] var5 = method.getExceptionTypes();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Class c = var5[var7];
            if (c.isAssignableFrom(Exception.class)) {
               return;
            }

            if (!RuntimeException.class.isAssignableFrom(c) && !Error.class.isAssignableFrom(c)) {
               errors.add(this.error(method, "it cannot be declared to throw checked exception"));
            }
         }

      }
   }

   protected void checkUndefinedMethodField(DescriptorBean bean, Field field, Method method, ErrorCollectionException errors) {
      LifecycleCallbackBean callbackBean = (LifecycleCallbackBean)bean;
      String clazzName = callbackBean.getLifecycleCallbackClass();
      String methodName = callbackBean.getLifecycleCallbackMethod();
      if (method == null) {
         errors.add(this.error("Method \"" + methodName + "\" is defined in deployment descriptor as lifecycle callback method, but it is not defined in class \"" + clazzName + "\"."));
      }

   }

   protected void checkParameters(Method method, ErrorCollectionException errors) {
      Class[] params = method.getParameterTypes();
      if (params != null && params.length > 0) {
         errors.add(this.error(method, "it must not have any parameter"));
      }

   }

   protected final Exception error(String cause) {
      return new IllegalArgumentException(cause);
   }

   protected final Exception error(Method method, String cause) {
      StringBuffer message = new StringBuffer();
      if (method == null) {
         return this.error(cause);
      } else {
         message.append("Method \"").append(method.getName()).append("\" in class \"").append(method.getDeclaringClass().getName()).append("\" is defined as lifecycle callback method ");
         String annotations = this.getLifecycleAnnotations(method);
         if (annotations != null && annotations.length() != 0) {
            message.append("with annotation ").append(annotations);
         } else {
            message.append("in deployment descriptor file, ");
         }

         message.append("but ").append(cause).append(".");
         return this.error(message.toString());
      }
   }

   protected String getLifecycleAnnotations(Method method) {
      String annotations = "";
      Annotation[] var3 = method.getAnnotations();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Annotation annotation = var3[var5];
         if (annotation.annotationType() == PostConstruct.class) {
            annotations = annotations + "@PostConstruct, ";
         }

         if (annotation.annotationType() == PreDestroy.class) {
            annotations = annotations + "@PreDestroy, ";
         }
      }

      return annotations;
   }
}
