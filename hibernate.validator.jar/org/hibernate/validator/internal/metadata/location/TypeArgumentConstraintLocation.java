package org.hibernate.validator.internal.metadata.location;

import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.StringHelper;
import org.hibernate.validator.internal.util.TypeHelper;

public class TypeArgumentConstraintLocation implements ConstraintLocation {
   private final ConstraintLocation delegate;
   private final TypeVariable typeParameter;
   private final Type typeForValidatorResolution;
   private final Class containerClass;
   private final ConstraintLocation outerDelegate;
   private final int hashCode;

   TypeArgumentConstraintLocation(ConstraintLocation delegate, TypeVariable typeParameter, Type typeOfAnnotatedElement) {
      this.delegate = delegate;
      this.typeParameter = typeParameter;
      this.typeForValidatorResolution = ReflectionHelper.boxedType(typeOfAnnotatedElement);
      this.containerClass = TypeHelper.getErasedReferenceType(delegate.getTypeForValidatorResolution());

      ConstraintLocation outerDelegate;
      for(outerDelegate = delegate; outerDelegate instanceof TypeArgumentConstraintLocation; outerDelegate = ((TypeArgumentConstraintLocation)outerDelegate).delegate) {
      }

      this.outerDelegate = outerDelegate;
      this.hashCode = buildHashCode(delegate, typeParameter);
   }

   public Class getDeclaringClass() {
      return this.delegate.getDeclaringClass();
   }

   public Member getMember() {
      return this.delegate.getMember();
   }

   public TypeVariable getTypeParameter() {
      return this.typeParameter;
   }

   public Type getTypeForValidatorResolution() {
      return this.typeForValidatorResolution;
   }

   public Class getContainerClass() {
      return this.containerClass;
   }

   public void appendTo(ExecutableParameterNameProvider parameterNameProvider, PathImpl path) {
      this.delegate.appendTo(parameterNameProvider, path);
   }

   public Object getValue(Object parent) {
      return this.delegate.getValue(parent);
   }

   public ConstraintLocation getDelegate() {
      return this.delegate;
   }

   public ConstraintLocation getOuterDelegate() {
      return this.outerDelegate;
   }

   public String toString() {
      return "TypeArgumentValueConstraintLocation [typeForValidatorResolution=" + StringHelper.toShortString(this.typeForValidatorResolution) + ", delegate=" + this.delegate + "]";
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         TypeArgumentConstraintLocation that = (TypeArgumentConstraintLocation)o;
         if (!this.typeParameter.equals(that.typeParameter)) {
            return false;
         } else {
            return this.delegate.equals(that.delegate);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.hashCode;
   }

   private static int buildHashCode(ConstraintLocation delegate, TypeVariable typeParameter) {
      int result = delegate.hashCode();
      result = 31 * result + typeParameter.hashCode();
      return result;
   }
}
