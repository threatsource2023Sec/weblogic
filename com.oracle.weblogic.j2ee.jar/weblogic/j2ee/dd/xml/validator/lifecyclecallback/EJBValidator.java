package weblogic.j2ee.dd.xml.validator.lifecyclecallback;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.dd.xml.validator.AnnotationValidatorHelper;
import weblogic.j2ee.descriptor.EnterpriseBeanBean;
import weblogic.j2ee.descriptor.LifecycleCallbackBean;
import weblogic.utils.ErrorCollectionException;

class EJBValidator extends J2EEValidator {
   protected Class getClass(DescriptorBean bean, ClassLoader cl) throws ClassNotFoundException {
      String c = ((LifecycleCallbackBean)bean).getLifecycleCallbackClass();
      if (c == null) {
         EnterpriseBeanBean parent = (EnterpriseBeanBean)bean.getParentBean();
         c = parent.getEjbClass();
      }

      return AnnotationValidatorHelper.getClass(c, cl);
   }

   protected Method guessMethodFromAnnotation(List methods) {
      Iterator var2 = methods.iterator();

      Method method;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         method = (Method)var2.next();
      } while(!method.isAnnotationPresent(PreDestroy.class) && !method.isAnnotationPresent(PostConstruct.class) && !method.isAnnotationPresent(PrePassivate.class) && !method.isAnnotationPresent(PostActivate.class));

      return method;
   }

   protected void checkModifier(Method method, ErrorCollectionException errors) {
      StringBuffer message = null;
      if (Modifier.isStatic(method.getModifiers())) {
         message = (new StringBuffer()).append("it cannot be declared as static");
      }

      if (Modifier.isFinal(method.getModifiers())) {
         if (message == null) {
            message = (new StringBuffer()).append("it cannot be declared as final");
         } else {
            message.append("and final");
         }
      }

      if (message != null) {
         errors.add(this.error(method, message.toString()));
      }

   }

   protected String getLifecycleAnnotations(Method method) {
      String annotations = super.getLifecycleAnnotations(method);
      Annotation[] var3 = method.getAnnotations();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Annotation annotation = var3[var5];
         if (annotation.annotationType() == PrePassivate.class) {
            annotations = annotations + "@PrePassivate, ";
         }

         if (annotation.annotationType() == PostActivate.class) {
            annotations = annotations + "@PostActivate, ";
         }
      }

      return annotations;
   }
}
