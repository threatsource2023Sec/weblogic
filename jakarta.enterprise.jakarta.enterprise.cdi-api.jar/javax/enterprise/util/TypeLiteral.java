package javax.enterprise.util;

import java.io.Serializable;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeLiteral implements Serializable {
   private static final long serialVersionUID = 1L;
   private transient Type actualType;

   protected TypeLiteral() {
   }

   public final Type getType() {
      if (this.actualType == null) {
         Class typeLiteralSubclass = getTypeLiteralSubclass(this.getClass());
         if (typeLiteralSubclass == null) {
            throw new RuntimeException(this.getClass() + " is not a subclass of TypeLiteral");
         }

         this.actualType = getTypeParameter(typeLiteralSubclass);
         if (this.actualType == null) {
            throw new RuntimeException(this.getClass() + " does not specify the type parameter T of TypeLiteral<T>");
         }
      }

      return this.actualType;
   }

   public final Class getRawType() {
      Type type = this.getType();
      if (type instanceof Class) {
         return (Class)type;
      } else if (type instanceof ParameterizedType) {
         return (Class)((ParameterizedType)type).getRawType();
      } else if (type instanceof GenericArrayType) {
         return Object[].class;
      } else {
         throw new RuntimeException("Illegal type");
      }
   }

   private static Class getTypeLiteralSubclass(Class clazz) {
      Class superclass = clazz.getSuperclass();
      if (superclass.equals(TypeLiteral.class)) {
         return clazz;
      } else {
         return superclass.equals(Object.class) ? null : getTypeLiteralSubclass(superclass);
      }
   }

   private static Type getTypeParameter(Class superclass) {
      Type type = superclass.getGenericSuperclass();
      if (type instanceof ParameterizedType) {
         ParameterizedType parameterizedType = (ParameterizedType)type;
         if (parameterizedType.getActualTypeArguments().length == 1) {
            return parameterizedType.getActualTypeArguments()[0];
         }
      }

      return null;
   }

   public boolean equals(Object obj) {
      if (obj instanceof TypeLiteral) {
         TypeLiteral that = (TypeLiteral)obj;
         return this.getType().equals(that.getType());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.getType().hashCode();
   }

   public String toString() {
      return this.getType().toString();
   }
}
