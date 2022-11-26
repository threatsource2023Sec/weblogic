package weblogic.j2ee.dd.xml.validator.injectiontarget;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.xml.ws.WebServiceRef;
import weblogic.utils.ErrorCollectionException;

class J2EEValidator extends BaseValidator {
   protected void checkModifier(Method method, ErrorCollectionException errors) {
      if (Modifier.isStatic(method.getModifiers())) {
         errors.add(this.error(method, "\"" + method.getName() + "\" cannot be declared as static method"));
      }

   }

   protected void checkModifier(Field field, ErrorCollectionException errors) {
      if (Modifier.isStatic(field.getModifiers()) && this.doesFieldHaveInjectionAnnotation(field)) {
         errors.add(this.error(field, "\"" + field.getName() + "\" cannot be declared as static field"));
      }

   }

   private boolean doesFieldHaveInjectionAnnotation(Field field) {
      return field.getAnnotation(Inject.class) != null || field.getAnnotation(Resource.class) != null || field.getAnnotation(EJB.class) != null || field.getAnnotation(PersistenceContext.class) != null || field.getAnnotation(PersistenceUnit.class) != null || field.getAnnotation(WebServiceRef.class) != null;
   }
}
