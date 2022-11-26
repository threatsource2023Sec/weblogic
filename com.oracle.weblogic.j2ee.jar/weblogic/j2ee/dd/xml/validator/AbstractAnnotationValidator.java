package weblogic.j2ee.dd.xml.validator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import weblogic.descriptor.DescriptorBean;
import weblogic.utils.ErrorCollectionException;

public abstract class AbstractAnnotationValidator implements AnnotationValidator {
   public void validate(DescriptorBean bean, ClassLoader cl) throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      Class clazz = null;
      Field field = null;
      Method method = null;

      try {
         this.checkBean(bean, errors);
         clazz = this.getClass(bean, cl);
         field = this.getField(bean, clazz);
         if (field != null) {
            this.checkField(field, errors);
         }

         method = this.getMethod(bean, clazz);
         if (method != null) {
            this.checkMethod(method, errors);
         }

         if (field != null && method != null) {
            this.checkAnnotation(method, field, errors);
         }

         if (field == null || method == null) {
            this.checkUndefinedMethodField(bean, field, method, errors);
         }
      } catch (Throwable var8) {
         errors.add(var8);
      }

      if (errors.size() != 0) {
         throw errors;
      }
   }

   protected void checkBean(DescriptorBean bean, ErrorCollectionException errors) {
   }

   protected void checkField(Field field, ErrorCollectionException errors) {
      this.checkAnnotation(field, errors);
      this.checkModifier(field, errors);
   }

   protected void checkMethod(Method method, ErrorCollectionException errors) {
      this.checkAnnotation(method, errors);
      this.checkReturnType(method, errors);
      this.checkException(method, errors);
      this.checkParameters(method, errors);
      this.checkModifier(method, errors);
   }

   protected Method getMethod(DescriptorBean bean, Class clazz) {
      return null;
   }

   protected Field getField(DescriptorBean bean, Class clazz) {
      return null;
   }

   protected Class getClass(DescriptorBean bean, ClassLoader cl) throws ClassNotFoundException {
      return null;
   }

   protected void checkReturnType(Method method, ErrorCollectionException errors) {
   }

   protected void checkException(Method method, ErrorCollectionException errors) {
   }

   protected void checkParameters(Method method, ErrorCollectionException errors) {
   }

   protected void checkModifier(Method method, ErrorCollectionException errors) {
   }

   protected void checkModifier(Field field, ErrorCollectionException errors) {
   }

   protected void checkAnnotation(Method method, ErrorCollectionException errors) {
   }

   protected void checkAnnotation(Field field, ErrorCollectionException errors) {
   }

   protected void checkAnnotation(Method method, Field field, ErrorCollectionException errors) {
   }

   protected void checkUndefinedMethodField(DescriptorBean bean, Field field, Method method, ErrorCollectionException errors) {
   }
}
