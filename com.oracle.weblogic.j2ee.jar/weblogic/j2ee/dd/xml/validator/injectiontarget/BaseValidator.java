package weblogic.j2ee.dd.xml.validator.injectiontarget;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.xml.ws.WebServiceRef;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.dd.xml.validator.AbstractAnnotationValidator;
import weblogic.j2ee.dd.xml.validator.AnnotationValidatorHelper;
import weblogic.j2ee.descriptor.InjectionTargetBean;
import weblogic.utils.ErrorCollectionException;

abstract class BaseValidator extends AbstractAnnotationValidator {
   private static Class[] ANNOTATION_CLASSES = new Class[]{Resource.class, EJB.class, PersistenceContext.class, PersistenceUnit.class, WebServiceRef.class};

   protected Class getClass(DescriptorBean bean, ClassLoader cl) throws ClassNotFoundException {
      String className = ((InjectionTargetBean)bean).getInjectionTargetClass();
      return AnnotationValidatorHelper.getClass(className, cl);
   }

   protected final Method getMethod(DescriptorBean bean, Class clazz) {
      String propertyName = ((InjectionTargetBean)bean).getInjectionTargetName();
      String setterName = AnnotationValidatorHelper.getSetterName(propertyName);
      List methods = AnnotationValidatorHelper.getMethods(clazz, setterName);
      Field field = AnnotationValidatorHelper.getField(clazz, propertyName);
      if (methods.size() == 0) {
         return null;
      } else if (methods.size() == 1) {
         return (Method)methods.get(0);
      } else {
         if (field != null) {
            Iterator var7 = methods.iterator();

            while(var7.hasNext()) {
               Method method = (Method)var7.next();
               Class[] paramsTypes = method.getParameterTypes();
               if (paramsTypes.length == 1 && paramsTypes[0].equals(field.getType())) {
                  return method;
               }
            }
         }

         return (Method)methods.get(0);
      }
   }

   protected final Field getField(DescriptorBean bean, Class clazz) {
      String propertyName = ((InjectionTargetBean)bean).getInjectionTargetName();
      return AnnotationValidatorHelper.getField(clazz, propertyName);
   }

   protected final void checkUndefinedMethodField(DescriptorBean bean, Field field, Method method, ErrorCollectionException errors) {
      if (field == null && method == null) {
         String className = ((InjectionTargetBean)bean).getInjectionTargetClass();
         String fieldName = ((InjectionTargetBean)bean).getInjectionTargetName();
         String methodName = AnnotationValidatorHelper.getSetterName(fieldName);
         errors.add(this.error("\"" + fieldName + "\" is defined as injection target in descriptor file for class \"" + className + "\", but either field \"" + fieldName + "\" or method \"" + methodName + "\" cannot be found within the class."));
      }

   }

   protected final void checkAnnotation(Method method, Field field, ErrorCollectionException errors) {
      Class[] var4 = ANNOTATION_CLASSES;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Class annotationClass = var4[var6];
         this.checkNameMethodOnAnnotation(method, field, annotationClass, errors);
      }

   }

   private void checkNameMethodOnAnnotation(Method method, Field field, Class annotationClass, ErrorCollectionException errors) {
      if (method.isAnnotationPresent(annotationClass)) {
         if (field.isAnnotationPresent(annotationClass)) {
            String methodName = "name";
            Annotation a1 = method.getAnnotation(annotationClass);
            Annotation a2 = field.getAnnotation(annotationClass);

            try {
               Method m1 = a1.getClass().getDeclaredMethod(methodName);
               Method m2 = a2.getClass().getDeclaredMethod(methodName);
               Object result1 = m1.invoke(a1);
               Object result2 = m2.invoke(a2);
               if (result1 != null && result2 != null && !result1.equals(result2)) {
                  errors.add(this.error("Annotation @" + annotationClass.getSimpleName() + " is defined on both method \"" + method.getName() + "\" and field \"" + field.getName() + "\", but they are inconsistent."));
               }
            } catch (NoSuchMethodException var12) {
            } catch (IllegalAccessException var13) {
            } catch (InvocationTargetException var14) {
            }

         }
      }
   }

   protected final Exception error(String cause) {
      return new IllegalArgumentException(cause);
   }

   protected final Exception error(Field field, String cause) {
      StringBuffer message = new StringBuffer();
      if (field == null) {
         return this.error(cause);
      } else {
         Class clazz = field.getDeclaringClass();
         String fieldName = field.getName();
         message.append("Field \"").append(fieldName).append(this.getMessageBody(clazz, fieldName, cause));
         return this.error(message.toString());
      }
   }

   protected final Exception error(Method method, String cause) {
      StringBuffer message = new StringBuffer();
      if (method == null) {
         return this.error(cause);
      } else {
         Class clazz = method.getDeclaringClass();
         String methodName = method.getName();
         String fieldName = AnnotationValidatorHelper.getFieldName(methodName);
         message.append("Method \"").append(methodName).append(this.getMessageBody(clazz, fieldName, cause));
         return this.error(message.toString());
      }
   }

   private String getMessageBody(Class clazz, String fieldName, String cause) {
      StringBuffer message = new StringBuffer();
      message.append("\" in class \"").append(clazz.getName()).append("\" is defined as injection target ");
      String annotations = this.getInjectionTargetAnnotation(clazz, fieldName);
      if (annotations != null && annotations.length() != 0) {
         message.append("with annotation ").append(annotations);
      } else {
         message.append("in deployment descriptor file, ");
      }

      message.append("but ").append(cause).append(".");
      return message.toString();
   }

   private String getInjectionTargetAnnotation(Class clazz, String fieldName) {
      Set annotationTypes = new HashSet();
      Field field = AnnotationValidatorHelper.getField(clazz, fieldName);
      if (field != null) {
         Annotation[] var5 = field.getDeclaredAnnotations();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Annotation a = var5[var7];
            annotationTypes.add(a.annotationType());
         }
      }

      String methodName = AnnotationValidatorHelper.getSetterName(fieldName);
      List methods = AnnotationValidatorHelper.getMethods(clazz, methodName);
      Iterator var15 = methods.iterator();

      while(var15.hasNext()) {
         Method m = (Method)var15.next();
         Annotation[] var9 = m.getDeclaredAnnotations();
         int var10 = var9.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            Annotation a = var9[var11];
            annotationTypes.add(a.annotationType());
         }
      }

      return this.getInjectionTargetAnnotation(annotationTypes);
   }

   private String getInjectionTargetAnnotation(Set annotationTypes) {
      String resultStr = "";
      Iterator var3 = annotationTypes.iterator();

      while(var3.hasNext()) {
         Class type = (Class)var3.next();
         if (annotationTypes.contains(type)) {
            resultStr = resultStr + "@" + type.getSimpleName() + ", ";
         }
      }

      return resultStr;
   }
}
