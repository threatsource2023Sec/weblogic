package weblogic.j2ee.dd.xml.validator.lifecyclecallback;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import weblogic.utils.ErrorCollectionException;

class J2EEValidator extends BaseValidator {
   protected void checkModifier(Method method, ErrorCollectionException errors) {
      if (Modifier.isStatic(method.getModifiers())) {
         errors.add(this.error(method, "it cannot be declared as static"));
      }

   }
}
