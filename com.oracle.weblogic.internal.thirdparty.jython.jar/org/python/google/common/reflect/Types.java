package org.python.google.common.reflect;

import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.security.AccessControlException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Function;
import org.python.google.common.base.Joiner;
import org.python.google.common.base.Objects;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Predicates;
import org.python.google.common.collect.ImmutableList;
import org.python.google.common.collect.ImmutableMap;
import org.python.google.common.collect.Iterables;
import org.python.google.common.collect.UnmodifiableIterator;

final class Types {
   private static final Function TYPE_NAME = new Function() {
      public String apply(Type from) {
         return Types.JavaVersion.CURRENT.typeName(from);
      }
   };
   private static final Joiner COMMA_JOINER = Joiner.on(", ").useForNull("null");

   static Type newArrayType(Type componentType) {
      if (componentType instanceof WildcardType) {
         WildcardType wildcard = (WildcardType)componentType;
         Type[] lowerBounds = wildcard.getLowerBounds();
         Preconditions.checkArgument(lowerBounds.length <= 1, "Wildcard cannot have more than one lower bounds.");
         if (lowerBounds.length == 1) {
            return supertypeOf(newArrayType(lowerBounds[0]));
         } else {
            Type[] upperBounds = wildcard.getUpperBounds();
            Preconditions.checkArgument(upperBounds.length == 1, "Wildcard should have only one upper bound.");
            return subtypeOf(newArrayType(upperBounds[0]));
         }
      } else {
         return Types.JavaVersion.CURRENT.newArrayType(componentType);
      }
   }

   static ParameterizedType newParameterizedTypeWithOwner(@Nullable Type ownerType, Class rawType, Type... arguments) {
      if (ownerType == null) {
         return newParameterizedType(rawType, arguments);
      } else {
         Preconditions.checkNotNull(arguments);
         Preconditions.checkArgument(rawType.getEnclosingClass() != null, "Owner type for unenclosed %s", (Object)rawType);
         return new ParameterizedTypeImpl(ownerType, rawType, arguments);
      }
   }

   static ParameterizedType newParameterizedType(Class rawType, Type... arguments) {
      return new ParameterizedTypeImpl(Types.ClassOwnership.JVM_BEHAVIOR.getOwnerType(rawType), rawType, arguments);
   }

   static TypeVariable newArtificialTypeVariable(GenericDeclaration declaration, String name, Type... bounds) {
      return newTypeVariableImpl(declaration, name, bounds.length == 0 ? new Type[]{Object.class} : bounds);
   }

   @VisibleForTesting
   static WildcardType subtypeOf(Type upperBound) {
      return new WildcardTypeImpl(new Type[0], new Type[]{upperBound});
   }

   @VisibleForTesting
   static WildcardType supertypeOf(Type lowerBound) {
      return new WildcardTypeImpl(new Type[]{lowerBound}, new Type[]{Object.class});
   }

   static String toString(Type type) {
      return type instanceof Class ? ((Class)type).getName() : type.toString();
   }

   @Nullable
   static Type getComponentType(Type type) {
      Preconditions.checkNotNull(type);
      final AtomicReference result = new AtomicReference();
      (new TypeVisitor() {
         void visitTypeVariable(TypeVariable t) {
            result.set(Types.subtypeOfComponentType(t.getBounds()));
         }

         void visitWildcardType(WildcardType t) {
            result.set(Types.subtypeOfComponentType(t.getUpperBounds()));
         }

         void visitGenericArrayType(GenericArrayType t) {
            result.set(t.getGenericComponentType());
         }

         void visitClass(Class t) {
            result.set(t.getComponentType());
         }
      }).visit(new Type[]{type});
      return (Type)result.get();
   }

   @Nullable
   private static Type subtypeOfComponentType(Type[] bounds) {
      Type[] var1 = bounds;
      int var2 = bounds.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Type bound = var1[var3];
         Type componentType = getComponentType(bound);
         if (componentType != null) {
            if (componentType instanceof Class) {
               Class componentClass = (Class)componentType;
               if (componentClass.isPrimitive()) {
                  return componentClass;
               }
            }

            return subtypeOf(componentType);
         }
      }

      return null;
   }

   private static TypeVariable newTypeVariableImpl(GenericDeclaration genericDeclaration, String name, Type[] bounds) {
      TypeVariableImpl typeVariableImpl = new TypeVariableImpl(genericDeclaration, name, bounds);
      TypeVariable typeVariable = (TypeVariable)Reflection.newProxy(TypeVariable.class, new TypeVariableInvocationHandler(typeVariableImpl));
      return typeVariable;
   }

   private static Type[] toArray(Collection types) {
      return (Type[])types.toArray(new Type[types.size()]);
   }

   private static Iterable filterUpperBounds(Iterable bounds) {
      return Iterables.filter(bounds, Predicates.not(Predicates.equalTo(Object.class)));
   }

   private static void disallowPrimitiveType(Type[] types, String usedAs) {
      Type[] var2 = types;
      int var3 = types.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Type type = var2[var4];
         if (type instanceof Class) {
            Class cls = (Class)type;
            Preconditions.checkArgument(!cls.isPrimitive(), "Primitive type '%s' used as %s", cls, usedAs);
         }
      }

   }

   static Class getArrayClass(Class componentType) {
      return Array.newInstance(componentType, 0).getClass();
   }

   private Types() {
   }

   static final class NativeTypeVariableEquals {
      static final boolean NATIVE_TYPE_VARIABLE_ONLY = !NativeTypeVariableEquals.class.getTypeParameters()[0].equals(Types.newArtificialTypeVariable(NativeTypeVariableEquals.class, "X"));
   }

   static enum JavaVersion {
      JAVA6 {
         GenericArrayType newArrayType(Type componentType) {
            return new GenericArrayTypeImpl(componentType);
         }

         Type usedInGenericType(Type type) {
            Preconditions.checkNotNull(type);
            if (type instanceof Class) {
               Class cls = (Class)type;
               if (cls.isArray()) {
                  return new GenericArrayTypeImpl(cls.getComponentType());
               }
            }

            return type;
         }
      },
      JAVA7 {
         Type newArrayType(Type componentType) {
            return (Type)(componentType instanceof Class ? Types.getArrayClass((Class)componentType) : new GenericArrayTypeImpl(componentType));
         }

         Type usedInGenericType(Type type) {
            return (Type)Preconditions.checkNotNull(type);
         }
      },
      JAVA8 {
         Type newArrayType(Type componentType) {
            return JAVA7.newArrayType(componentType);
         }

         Type usedInGenericType(Type type) {
            return JAVA7.usedInGenericType(type);
         }

         String typeName(Type type) {
            try {
               Method getTypeName = Type.class.getMethod("getTypeName");
               return (String)getTypeName.invoke(type);
            } catch (NoSuchMethodException var3) {
               throw new AssertionError("Type.getTypeName should be available in Java 8");
            } catch (InvocationTargetException var4) {
               throw new RuntimeException(var4);
            } catch (IllegalAccessException var5) {
               throw new RuntimeException(var5);
            }
         }
      };

      static final JavaVersion CURRENT;

      private JavaVersion() {
      }

      abstract Type newArrayType(Type var1);

      abstract Type usedInGenericType(Type var1);

      String typeName(Type type) {
         return Types.toString(type);
      }

      final ImmutableList usedInGenericType(Type[] types) {
         ImmutableList.Builder builder = ImmutableList.builder();
         Type[] var3 = types;
         int var4 = types.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Type type = var3[var5];
            builder.add((Object)this.usedInGenericType(type));
         }

         return builder.build();
      }

      // $FF: synthetic method
      JavaVersion(Object x2) {
         this();
      }

      static {
         if (AnnotatedElement.class.isAssignableFrom(TypeVariable.class)) {
            CURRENT = JAVA8;
         } else if ((new TypeCapture() {
         }).capture() instanceof Class) {
            CURRENT = JAVA7;
         } else {
            CURRENT = JAVA6;
         }

      }
   }

   static final class WildcardTypeImpl implements WildcardType, Serializable {
      private final ImmutableList lowerBounds;
      private final ImmutableList upperBounds;
      private static final long serialVersionUID = 0L;

      WildcardTypeImpl(Type[] lowerBounds, Type[] upperBounds) {
         Types.disallowPrimitiveType(lowerBounds, "lower bound for wildcard");
         Types.disallowPrimitiveType(upperBounds, "upper bound for wildcard");
         this.lowerBounds = Types.JavaVersion.CURRENT.usedInGenericType(lowerBounds);
         this.upperBounds = Types.JavaVersion.CURRENT.usedInGenericType(upperBounds);
      }

      public Type[] getLowerBounds() {
         return Types.toArray(this.lowerBounds);
      }

      public Type[] getUpperBounds() {
         return Types.toArray(this.upperBounds);
      }

      public boolean equals(Object obj) {
         if (!(obj instanceof WildcardType)) {
            return false;
         } else {
            WildcardType that = (WildcardType)obj;
            return this.lowerBounds.equals(Arrays.asList(that.getLowerBounds())) && this.upperBounds.equals(Arrays.asList(that.getUpperBounds()));
         }
      }

      public int hashCode() {
         return this.lowerBounds.hashCode() ^ this.upperBounds.hashCode();
      }

      public String toString() {
         StringBuilder builder = new StringBuilder("?");
         UnmodifiableIterator var2 = this.lowerBounds.iterator();

         Type upperBound;
         while(var2.hasNext()) {
            upperBound = (Type)var2.next();
            builder.append(" super ").append(Types.JavaVersion.CURRENT.typeName(upperBound));
         }

         Iterator var4 = Types.filterUpperBounds(this.upperBounds).iterator();

         while(var4.hasNext()) {
            upperBound = (Type)var4.next();
            builder.append(" extends ").append(Types.JavaVersion.CURRENT.typeName(upperBound));
         }

         return builder.toString();
      }
   }

   private static final class TypeVariableImpl {
      private final GenericDeclaration genericDeclaration;
      private final String name;
      private final ImmutableList bounds;

      TypeVariableImpl(GenericDeclaration genericDeclaration, String name, Type[] bounds) {
         Types.disallowPrimitiveType(bounds, "bound for type variable");
         this.genericDeclaration = (GenericDeclaration)Preconditions.checkNotNull(genericDeclaration);
         this.name = (String)Preconditions.checkNotNull(name);
         this.bounds = ImmutableList.copyOf((Object[])bounds);
      }

      public Type[] getBounds() {
         return Types.toArray(this.bounds);
      }

      public GenericDeclaration getGenericDeclaration() {
         return this.genericDeclaration;
      }

      public String getName() {
         return this.name;
      }

      public String getTypeName() {
         return this.name;
      }

      public String toString() {
         return this.name;
      }

      public int hashCode() {
         return this.genericDeclaration.hashCode() ^ this.name.hashCode();
      }

      public boolean equals(Object obj) {
         if (Types.NativeTypeVariableEquals.NATIVE_TYPE_VARIABLE_ONLY) {
            if (obj != null && Proxy.isProxyClass(obj.getClass()) && Proxy.getInvocationHandler(obj) instanceof TypeVariableInvocationHandler) {
               TypeVariableInvocationHandler typeVariableInvocationHandler = (TypeVariableInvocationHandler)Proxy.getInvocationHandler(obj);
               TypeVariableImpl that = typeVariableInvocationHandler.typeVariableImpl;
               return this.name.equals(that.getName()) && this.genericDeclaration.equals(that.getGenericDeclaration()) && this.bounds.equals(that.bounds);
            } else {
               return false;
            }
         } else if (!(obj instanceof TypeVariable)) {
            return false;
         } else {
            TypeVariable that = (TypeVariable)obj;
            return this.name.equals(that.getName()) && this.genericDeclaration.equals(that.getGenericDeclaration());
         }
      }
   }

   private static final class TypeVariableInvocationHandler implements InvocationHandler {
      private static final ImmutableMap typeVariableMethods;
      private final TypeVariableImpl typeVariableImpl;

      TypeVariableInvocationHandler(TypeVariableImpl typeVariableImpl) {
         this.typeVariableImpl = typeVariableImpl;
      }

      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
         String methodName = method.getName();
         Method typeVariableMethod = (Method)typeVariableMethods.get(methodName);
         if (typeVariableMethod == null) {
            throw new UnsupportedOperationException(methodName);
         } else {
            try {
               return typeVariableMethod.invoke(this.typeVariableImpl, args);
            } catch (InvocationTargetException var7) {
               throw var7.getCause();
            }
         }
      }

      static {
         ImmutableMap.Builder builder = ImmutableMap.builder();
         Method[] var1 = TypeVariableImpl.class.getMethods();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Method method = var1[var3];
            if (method.getDeclaringClass().equals(TypeVariableImpl.class)) {
               try {
                  method.setAccessible(true);
               } catch (AccessControlException var6) {
               }

               builder.put(method.getName(), method);
            }
         }

         typeVariableMethods = builder.build();
      }
   }

   private static final class ParameterizedTypeImpl implements ParameterizedType, Serializable {
      private final Type ownerType;
      private final ImmutableList argumentsList;
      private final Class rawType;
      private static final long serialVersionUID = 0L;

      ParameterizedTypeImpl(@Nullable Type ownerType, Class rawType, Type[] typeArguments) {
         Preconditions.checkNotNull(rawType);
         Preconditions.checkArgument(typeArguments.length == rawType.getTypeParameters().length);
         Types.disallowPrimitiveType(typeArguments, "type parameter");
         this.ownerType = ownerType;
         this.rawType = rawType;
         this.argumentsList = Types.JavaVersion.CURRENT.usedInGenericType(typeArguments);
      }

      public Type[] getActualTypeArguments() {
         return Types.toArray(this.argumentsList);
      }

      public Type getRawType() {
         return this.rawType;
      }

      public Type getOwnerType() {
         return this.ownerType;
      }

      public String toString() {
         StringBuilder builder = new StringBuilder();
         if (this.ownerType != null) {
            builder.append(Types.JavaVersion.CURRENT.typeName(this.ownerType)).append('.');
         }

         return builder.append(this.rawType.getName()).append('<').append(Types.COMMA_JOINER.join(Iterables.transform(this.argumentsList, Types.TYPE_NAME))).append('>').toString();
      }

      public int hashCode() {
         return (this.ownerType == null ? 0 : this.ownerType.hashCode()) ^ this.argumentsList.hashCode() ^ this.rawType.hashCode();
      }

      public boolean equals(Object other) {
         if (!(other instanceof ParameterizedType)) {
            return false;
         } else {
            ParameterizedType that = (ParameterizedType)other;
            return this.getRawType().equals(that.getRawType()) && Objects.equal(this.getOwnerType(), that.getOwnerType()) && Arrays.equals(this.getActualTypeArguments(), that.getActualTypeArguments());
         }
      }
   }

   private static final class GenericArrayTypeImpl implements GenericArrayType, Serializable {
      private final Type componentType;
      private static final long serialVersionUID = 0L;

      GenericArrayTypeImpl(Type componentType) {
         this.componentType = Types.JavaVersion.CURRENT.usedInGenericType(componentType);
      }

      public Type getGenericComponentType() {
         return this.componentType;
      }

      public String toString() {
         return Types.toString(this.componentType) + "[]";
      }

      public int hashCode() {
         return this.componentType.hashCode();
      }

      public boolean equals(Object obj) {
         if (obj instanceof GenericArrayType) {
            GenericArrayType that = (GenericArrayType)obj;
            return Objects.equal(this.getGenericComponentType(), that.getGenericComponentType());
         } else {
            return false;
         }
      }
   }

   private static enum ClassOwnership {
      OWNED_BY_ENCLOSING_CLASS {
         @Nullable
         Class getOwnerType(Class rawType) {
            return rawType.getEnclosingClass();
         }
      },
      LOCAL_CLASS_HAS_NO_OWNER {
         @Nullable
         Class getOwnerType(Class rawType) {
            return rawType.isLocalClass() ? null : rawType.getEnclosingClass();
         }
      };

      static final ClassOwnership JVM_BEHAVIOR = detectJvmBehavior();

      private ClassOwnership() {
      }

      @Nullable
      abstract Class getOwnerType(Class var1);

      private static ClassOwnership detectJvmBehavior() {
         class LocalClass {
         }

         Class subclass = (new LocalClass() {
         }).getClass();
         ParameterizedType parameterizedType = (ParameterizedType)subclass.getGenericSuperclass();
         ClassOwnership[] var2 = values();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ClassOwnership behavior = var2[var4];
            if (behavior.getOwnerType(LocalClass.class) == parameterizedType.getOwnerType()) {
               return behavior;
            }
         }

         throw new AssertionError();
      }

      // $FF: synthetic method
      ClassOwnership(Object x2) {
         this();
      }
   }
}
