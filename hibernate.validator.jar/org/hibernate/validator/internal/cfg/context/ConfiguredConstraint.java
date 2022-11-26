package org.hibernate.validator.internal.cfg.context;

import java.lang.annotation.ElementType;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.validation.ValidationException;
import org.hibernate.validator.cfg.AnnotationDef;
import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.util.ExecutableHelper;
import org.hibernate.validator.internal.util.annotation.AnnotationDescriptor;
import org.hibernate.validator.internal.util.annotation.ConstraintAnnotationDescriptor;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredMethodHandle;

class ConfiguredConstraint {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final MethodHandle CREATE_ANNOTATION_DESCRIPTOR_METHOD_HANDLE = (MethodHandle)run(GetDeclaredMethodHandle.andMakeAccessible(MethodHandles.lookup(), AnnotationDef.class, "createAnnotationDescriptor"));
   private final ConstraintDef constraint;
   private final ConstraintLocation location;
   private final ElementType elementType;

   private ConfiguredConstraint(ConstraintDef constraint, ConstraintLocation location, ElementType elementType) {
      this.constraint = constraint;
      this.location = location;
      this.elementType = elementType;
   }

   static ConfiguredConstraint forType(ConstraintDef constraint, Class beanType) {
      return new ConfiguredConstraint(constraint, ConstraintLocation.forClass(beanType), ElementType.TYPE);
   }

   static ConfiguredConstraint forProperty(ConstraintDef constraint, Member member) {
      return member instanceof Field ? new ConfiguredConstraint(constraint, ConstraintLocation.forField((Field)member), ElementType.FIELD) : new ConfiguredConstraint(constraint, ConstraintLocation.forGetter((Method)member), ElementType.METHOD);
   }

   public static ConfiguredConstraint forParameter(ConstraintDef constraint, Executable executable, int parameterIndex) {
      return new ConfiguredConstraint(constraint, ConstraintLocation.forParameter(executable, parameterIndex), ExecutableHelper.getElementType(executable));
   }

   public static ConfiguredConstraint forExecutable(ConstraintDef constraint, Executable executable) {
      return new ConfiguredConstraint(constraint, ConstraintLocation.forReturnValue(executable), ExecutableHelper.getElementType(executable));
   }

   public static ConfiguredConstraint forCrossParameter(ConstraintDef constraint, Executable executable) {
      return new ConfiguredConstraint(constraint, ConstraintLocation.forCrossParameter(executable), ExecutableHelper.getElementType(executable));
   }

   public static ConfiguredConstraint forTypeArgument(ConstraintDef constraint, ConstraintLocation delegate, TypeVariable typeArgument, Type typeOfAnnotatedElement) {
      return new ConfiguredConstraint(constraint, ConstraintLocation.forTypeArgument(delegate, typeArgument, typeOfAnnotatedElement), ElementType.TYPE_USE);
   }

   public ConstraintDef getConstraint() {
      return this.constraint;
   }

   public ConstraintLocation getLocation() {
      return this.location;
   }

   public ConstraintAnnotationDescriptor createAnnotationDescriptor() {
      try {
         AnnotationDescriptor annotationDescriptor = CREATE_ANNOTATION_DESCRIPTOR_METHOD_HANDLE.invoke(this.constraint);
         return new ConstraintAnnotationDescriptor(annotationDescriptor);
      } catch (Throwable var2) {
         if (var2 instanceof ValidationException) {
            throw (ValidationException)var2;
         } else {
            throw LOG.getUnableToCreateAnnotationDescriptor(this.constraint.getClass(), var2);
         }
      }
   }

   public String toString() {
      return this.constraint.toString();
   }

   public ElementType getElementType() {
      return this.elementType;
   }

   private static Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }
}
