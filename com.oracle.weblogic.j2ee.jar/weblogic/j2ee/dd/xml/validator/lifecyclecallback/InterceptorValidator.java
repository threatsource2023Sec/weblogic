package weblogic.j2ee.dd.xml.validator.lifecyclecallback;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import javax.interceptor.InvocationContext;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.dd.xml.validator.AnnotationValidatorHelper;
import weblogic.j2ee.descriptor.InterceptorBean;
import weblogic.j2ee.descriptor.LifecycleCallbackBean;
import weblogic.utils.ErrorCollectionException;

class InterceptorValidator extends EJBValidator {
   protected Class getClass(DescriptorBean bean, ClassLoader cl) throws ClassNotFoundException {
      String c = ((LifecycleCallbackBean)bean).getLifecycleCallbackClass();
      if (c == null) {
         InterceptorBean parent = (InterceptorBean)bean.getParentBean();
         c = parent.getInterceptorClass();
      }

      return AnnotationValidatorHelper.getClass(c, cl);
   }

   protected Method guessMethodFromSignature(List methods) {
      Iterator var2 = methods.iterator();

      Method method;
      Class[] paramTypes;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         method = (Method)var2.next();
         paramTypes = method.getParameterTypes();
      } while(method.getReturnType() != Void.TYPE || paramTypes.length != 1 || !InvocationContext.class.isAssignableFrom(paramTypes[0]));

      return method;
   }

   protected void checkParameters(Method method, ErrorCollectionException errors) {
      Class[] paramTypes = method.getParameterTypes();
      if (paramTypes.length != 1 || !InvocationContext.class.isAssignableFrom(paramTypes[0])) {
         errors.add(this.error(method, "it should take an InvocationContext object as parameter in the case of EJB interceptor"));
      }
   }
}
