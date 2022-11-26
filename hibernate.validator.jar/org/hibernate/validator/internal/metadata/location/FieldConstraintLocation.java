package org.hibernate.validator.internal.metadata.location;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.hibernate.validator.HibernateValidatorPermission;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.StringHelper;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredField;

public class FieldConstraintLocation implements ConstraintLocation {
   private final Field field;
   private final Field accessibleField;
   private final String propertyName;
   private final Type typeForValidatorResolution;

   FieldConstraintLocation(Field field) {
      this.field = field;
      this.accessibleField = getAccessible(field);
      this.propertyName = ReflectionHelper.getPropertyName(field);
      this.typeForValidatorResolution = ReflectionHelper.boxedType(ReflectionHelper.typeOf(field));
   }

   public Class getDeclaringClass() {
      return this.field.getDeclaringClass();
   }

   public Member getMember() {
      return this.field;
   }

   public String getPropertyName() {
      return this.propertyName;
   }

   public Type getTypeForValidatorResolution() {
      return this.typeForValidatorResolution;
   }

   public void appendTo(ExecutableParameterNameProvider parameterNameProvider, PathImpl path) {
      path.addPropertyNode(this.propertyName);
   }

   public Object getValue(Object parent) {
      return ReflectionHelper.getValue(this.accessibleField, parent);
   }

   public String toString() {
      return "FieldConstraintLocation [member=" + StringHelper.toShortString((Member)this.field) + ", typeForValidatorResolution=" + StringHelper.toShortString(this.typeForValidatorResolution) + "]";
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         FieldConstraintLocation that = (FieldConstraintLocation)o;
         if (this.field != null) {
            if (this.field.equals(that.field)) {
               return this.typeForValidatorResolution.equals(that.typeForValidatorResolution);
            }
         } else if (that.field == null) {
            return this.typeForValidatorResolution.equals(that.typeForValidatorResolution);
         }

         return false;
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.field != null ? this.field.hashCode() : 0;
      result = 31 * result + this.typeForValidatorResolution.hashCode();
      return result;
   }

   private static Field getAccessible(Field original) {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(HibernateValidatorPermission.ACCESS_PRIVATE_MEMBERS);
      }

      Class clazz = original.getDeclaringClass();
      return (Field)run(GetDeclaredField.andMakeAccessible(clazz, original.getName()));
   }

   private static Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }
}
