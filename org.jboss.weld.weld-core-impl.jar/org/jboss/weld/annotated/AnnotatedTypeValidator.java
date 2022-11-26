package org.jboss.weld.annotated;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.AnnotatedMember;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.AnnotatedType;
import org.jboss.weld.logging.MetadataLogger;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

public class AnnotatedTypeValidator {
   private AnnotatedTypeValidator() {
   }

   public static void validateAnnotated(Annotated annotated) {
      checkNotNull(annotated.getAnnotations(), "getAnnotations()", annotated);
      checkNotNull(annotated.getBaseType(), "getBaseType()", annotated);
      checkNotNull(annotated.getTypeClosure(), "getTypeClosure()", annotated);
   }

   public static void validateAnnotatedParameter(AnnotatedParameter parameter) {
      validateAnnotated(parameter);
      if (parameter.getPosition() < 0) {
         throw MetadataLogger.LOG.invalidParameterPosition(parameter.getPosition(), parameter);
      } else {
         checkNotNull(parameter.getDeclaringCallable(), "getDeclaringCallable()", parameter);
      }
   }

   public static void validateAnnotatedMember(AnnotatedMember member) {
      validateAnnotated(member);
      checkNotNull(member.getJavaMember(), "getJavaMember()", member);
      checkNotNull(member.getDeclaringType(), "getDeclaringType()", member);
   }

   public static void validateAnnotatedType(AnnotatedType type) {
      validateAnnotated(type);
      checkNotNull(type.getJavaClass(), "getJavaClass()", type);
      checkNotNull(type.getFields(), "getFields()", type);
      checkNotNull(type.getConstructors(), "getConstructors()", type);
      checkNotNull(type.getMethods(), "getMethods()", type);
      checkSensibility(type);
   }

   private static void checkNotNull(Object expression, String methodName, Object target) {
      if (expression == null) {
         throw MetadataLogger.LOG.metadataSourceReturnedNull(methodName, target);
      }
   }

   private static void checkSensibility(AnnotatedType type) {
      if (type.getConstructors().isEmpty() && !type.getJavaClass().isInterface()) {
         MetadataLogger.LOG.noConstructor(type);
      }

      Set hierarchy = new HashSet();

      for(Class clazz = type.getJavaClass(); clazz != null; clazz = clazz.getSuperclass()) {
         hierarchy.add(clazz);
         hierarchy.addAll(Reflections.getInterfaceClosure(clazz));
      }

      checkMembersBelongToHierarchy(type.getConstructors(), hierarchy, type);
      checkMembersBelongToHierarchy(type.getMethods(), hierarchy, type);
      checkMembersBelongToHierarchy(type.getFields(), hierarchy, type);
   }

   private static void checkMembersBelongToHierarchy(Iterable members, Set hierarchy, AnnotatedType type) {
      Iterator var3 = members.iterator();

      while(var3.hasNext()) {
         AnnotatedMember member = (AnnotatedMember)var3.next();
         if (!hierarchy.contains(member.getJavaMember().getDeclaringClass())) {
            MetadataLogger.LOG.notInHierarchy(member.getJavaMember().getName(), member.toString(), type.getJavaClass().getName(), type.toString(), Formats.formatAsStackTraceElement(member.getJavaMember()));
         }
      }

   }
}
