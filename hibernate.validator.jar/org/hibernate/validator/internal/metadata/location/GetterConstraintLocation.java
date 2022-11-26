package org.hibernate.validator.internal.metadata.location;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.hibernate.validator.HibernateValidatorPermission;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.StringHelper;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredMethod;

public class GetterConstraintLocation implements ConstraintLocation {
   private final Method method;
   private final Method accessibleMethod;
   private final String propertyName;
   private final Type typeForValidatorResolution;
   private final Class declaringClass;

   GetterConstraintLocation(Class declaringClass, Method method) {
      this.method = method;
      this.accessibleMethod = getAccessible(method);
      this.propertyName = ReflectionHelper.getPropertyName(method);
      this.typeForValidatorResolution = ReflectionHelper.boxedType(ReflectionHelper.typeOf(method));
      this.declaringClass = declaringClass;
   }

   public Class getDeclaringClass() {
      return this.declaringClass;
   }

   public Method getMember() {
      return this.method;
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
      return ReflectionHelper.getValue(this.accessibleMethod, parent);
   }

   public String toString() {
      return "GetterConstraintLocation [method=" + StringHelper.toShortString((Member)this.method) + ", typeForValidatorResolution=" + StringHelper.toShortString(this.typeForValidatorResolution) + "]";
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         GetterConstraintLocation that = (GetterConstraintLocation)o;
         if (this.method != null) {
            if (this.method.equals(that.method)) {
               return this.typeForValidatorResolution.equals(that.typeForValidatorResolution);
            }
         } else if (that.method == null) {
            return this.typeForValidatorResolution.equals(that.typeForValidatorResolution);
         }

         return false;
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.method.hashCode();
      result = 31 * result + this.typeForValidatorResolution.hashCode();
      return result;
   }

   private static Method getAccessible(Method original) {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(HibernateValidatorPermission.ACCESS_PRIVATE_MEMBERS);
      }

      Class clazz = original.getDeclaringClass();
      Method accessibleMethod = (Method)run(GetDeclaredMethod.andMakeAccessible(clazz, original.getName()));
      return accessibleMethod;
   }

   private static Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }
}
