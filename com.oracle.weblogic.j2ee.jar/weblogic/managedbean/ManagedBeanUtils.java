package weblogic.managedbean;

import java.lang.reflect.Modifier;
import java.util.Set;
import javax.annotation.ManagedBean;
import weblogic.j2ee.dd.xml.AnnotationProcessException;
import weblogic.utils.ErrorCollectionException;

public final class ManagedBeanUtils {
   private ManagedBeanUtils() {
   }

   public static boolean isManagedBean(Class beanClass) {
      return beanClass.getAnnotation(ManagedBean.class) != null;
   }

   public static String calculateManagedBeanName(Class managedBean) {
      ManagedBean annotation = (ManagedBean)managedBean.getAnnotation(ManagedBean.class);
      return annotation.value().isEmpty() ? managedBean.getSimpleName() : annotation.value();
   }

   public static String calculateJavaModuleLookupName(Class managedBean) {
      return "java:module/" + calculateManagedBeanName(managedBean);
   }

   public static void validateManagedBeanName(String beanName, Set duplicates, ErrorCollectionException errors) {
      if (duplicates.contains(beanName)) {
         errors.add(new AnnotationProcessException("Managed Bean names must be unique within a Java EE module. Duplicated managed bean name: " + beanName));
      } else {
         duplicates.add(beanName);
      }

   }

   public static void validateManagedBeanClass(Class clazz, ErrorCollectionException errors) {
      int mod = clazz.getModifiers();
      if (Modifier.isFinal(mod) || Modifier.isAbstract(mod) || clazz.isMemberClass() && !Modifier.isStatic(mod)) {
         errors.add(new AnnotationProcessException("Managed bean class " + clazz.getName() + " must not  be a final class, an abstract class, a non-static inner class."));
      }

      try {
         clazz.getConstructor();
      } catch (NoSuchMethodException var4) {
         errors.add(new AnnotationProcessException("Managed bean class " + clazz.getName() + " must have a no-argument constructor"));
      }

   }
}
