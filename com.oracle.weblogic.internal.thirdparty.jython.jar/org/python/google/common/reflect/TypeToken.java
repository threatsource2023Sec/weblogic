package org.python.google.common.reflect;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Joiner;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Predicate;
import org.python.google.common.collect.FluentIterable;
import org.python.google.common.collect.ForwardingSet;
import org.python.google.common.collect.ImmutableList;
import org.python.google.common.collect.ImmutableMap;
import org.python.google.common.collect.ImmutableSet;
import org.python.google.common.collect.Maps;
import org.python.google.common.collect.Ordering;
import org.python.google.common.collect.UnmodifiableIterator;
import org.python.google.common.primitives.Primitives;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
public abstract class TypeToken extends TypeCapture implements Serializable {
   private final Type runtimeType;
   private transient TypeResolver typeResolver;

   protected TypeToken() {
      this.runtimeType = this.capture();
      Preconditions.checkState(!(this.runtimeType instanceof TypeVariable), "Cannot construct a TypeToken for a type variable.\nYou probably meant to call new TypeToken<%s>(getClass()) that can resolve the type variable for you.\nIf you do need to create a TypeToken of a type variable, please use TypeToken.of() instead.", (Object)this.runtimeType);
   }

   protected TypeToken(Class declaringClass) {
      Type captured = super.capture();
      if (captured instanceof Class) {
         this.runtimeType = captured;
      } else {
         this.runtimeType = of(declaringClass).resolveType(captured).runtimeType;
      }

   }

   private TypeToken(Type type) {
      this.runtimeType = (Type)Preconditions.checkNotNull(type);
   }

   public static TypeToken of(Class type) {
      return new SimpleTypeToken(type);
   }

   public static TypeToken of(Type type) {
      return new SimpleTypeToken(type);
   }

   public final Class getRawType() {
      Class rawType = (Class)this.getRawTypes().iterator().next();
      return rawType;
   }

   public final Type getType() {
      return this.runtimeType;
   }

   public final TypeToken where(TypeParameter typeParam, TypeToken typeArg) {
      TypeResolver resolver = (new TypeResolver()).where(ImmutableMap.of(new TypeResolver.TypeVariableKey(typeParam.typeVariable), typeArg.runtimeType));
      return new SimpleTypeToken(resolver.resolveType(this.runtimeType));
   }

   public final TypeToken where(TypeParameter typeParam, Class typeArg) {
      return this.where(typeParam, of(typeArg));
   }

   public final TypeToken resolveType(Type type) {
      Preconditions.checkNotNull(type);
      TypeResolver resolver = this.typeResolver;
      if (resolver == null) {
         resolver = this.typeResolver = TypeResolver.accordingTo(this.runtimeType);
      }

      return of(resolver.resolveType(type));
   }

   private Type[] resolveInPlace(Type[] types) {
      for(int i = 0; i < types.length; ++i) {
         types[i] = this.resolveType(types[i]).getType();
      }

      return types;
   }

   private TypeToken resolveSupertype(Type type) {
      TypeToken supertype = this.resolveType(type);
      supertype.typeResolver = this.typeResolver;
      return supertype;
   }

   @Nullable
   final TypeToken getGenericSuperclass() {
      if (this.runtimeType instanceof TypeVariable) {
         return this.boundAsSuperclass(((TypeVariable)this.runtimeType).getBounds()[0]);
      } else if (this.runtimeType instanceof WildcardType) {
         return this.boundAsSuperclass(((WildcardType)this.runtimeType).getUpperBounds()[0]);
      } else {
         Type superclass = this.getRawType().getGenericSuperclass();
         if (superclass == null) {
            return null;
         } else {
            TypeToken superToken = this.resolveSupertype(superclass);
            return superToken;
         }
      }
   }

   @Nullable
   private TypeToken boundAsSuperclass(Type bound) {
      TypeToken token = of(bound);
      return token.getRawType().isInterface() ? null : token;
   }

   final ImmutableList getGenericInterfaces() {
      if (this.runtimeType instanceof TypeVariable) {
         return this.boundsAsInterfaces(((TypeVariable)this.runtimeType).getBounds());
      } else if (this.runtimeType instanceof WildcardType) {
         return this.boundsAsInterfaces(((WildcardType)this.runtimeType).getUpperBounds());
      } else {
         ImmutableList.Builder builder = ImmutableList.builder();
         Type[] var2 = this.getRawType().getGenericInterfaces();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Type interfaceType = var2[var4];
            TypeToken resolvedInterface = this.resolveSupertype(interfaceType);
            builder.add((Object)resolvedInterface);
         }

         return builder.build();
      }
   }

   private ImmutableList boundsAsInterfaces(Type[] bounds) {
      ImmutableList.Builder builder = ImmutableList.builder();
      Type[] var3 = bounds;
      int var4 = bounds.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Type bound = var3[var5];
         TypeToken boundType = of(bound);
         if (boundType.getRawType().isInterface()) {
            builder.add((Object)boundType);
         }
      }

      return builder.build();
   }

   public final TypeSet getTypes() {
      return new TypeSet();
   }

   public final TypeToken getSupertype(Class superclass) {
      Preconditions.checkArgument(this.someRawTypeIsSubclassOf(superclass), "%s is not a super class of %s", superclass, this);
      if (this.runtimeType instanceof TypeVariable) {
         return this.getSupertypeFromUpperBounds(superclass, ((TypeVariable)this.runtimeType).getBounds());
      } else if (this.runtimeType instanceof WildcardType) {
         return this.getSupertypeFromUpperBounds(superclass, ((WildcardType)this.runtimeType).getUpperBounds());
      } else if (superclass.isArray()) {
         return this.getArraySupertype(superclass);
      } else {
         TypeToken supertype = this.resolveSupertype(toGenericType(superclass).runtimeType);
         return supertype;
      }
   }

   public final TypeToken getSubtype(Class subclass) {
      Preconditions.checkArgument(!(this.runtimeType instanceof TypeVariable), "Cannot get subtype of type variable <%s>", (Object)this);
      if (this.runtimeType instanceof WildcardType) {
         return this.getSubtypeFromLowerBounds(subclass, ((WildcardType)this.runtimeType).getLowerBounds());
      } else if (this.isArray()) {
         return this.getArraySubtype(subclass);
      } else {
         Preconditions.checkArgument(this.getRawType().isAssignableFrom(subclass), "%s isn't a subclass of %s", subclass, this);
         Type resolvedTypeArgs = this.resolveTypeArgsForSubclass(subclass);
         TypeToken subtype = of(resolvedTypeArgs);
         return subtype;
      }
   }

   public final boolean isSupertypeOf(TypeToken type) {
      return type.isSubtypeOf(this.getType());
   }

   public final boolean isSupertypeOf(Type type) {
      return of(type).isSubtypeOf(this.getType());
   }

   public final boolean isSubtypeOf(TypeToken type) {
      return this.isSubtypeOf(type.getType());
   }

   public final boolean isSubtypeOf(Type supertype) {
      Preconditions.checkNotNull(supertype);
      if (supertype instanceof WildcardType) {
         return any(((WildcardType)supertype).getLowerBounds()).isSupertypeOf(this.runtimeType);
      } else if (this.runtimeType instanceof WildcardType) {
         return any(((WildcardType)this.runtimeType).getUpperBounds()).isSubtypeOf(supertype);
      } else if (!(this.runtimeType instanceof TypeVariable)) {
         if (this.runtimeType instanceof GenericArrayType) {
            return of(supertype).isSupertypeOfArray((GenericArrayType)this.runtimeType);
         } else if (supertype instanceof Class) {
            return this.someRawTypeIsSubclassOf((Class)supertype);
         } else if (supertype instanceof ParameterizedType) {
            return this.isSubtypeOfParameterizedType((ParameterizedType)supertype);
         } else {
            return supertype instanceof GenericArrayType ? this.isSubtypeOfArrayType((GenericArrayType)supertype) : false;
         }
      } else {
         return this.runtimeType.equals(supertype) || any(((TypeVariable)this.runtimeType).getBounds()).isSubtypeOf(supertype);
      }
   }

   public final boolean isArray() {
      return this.getComponentType() != null;
   }

   public final boolean isPrimitive() {
      return this.runtimeType instanceof Class && ((Class)this.runtimeType).isPrimitive();
   }

   public final TypeToken wrap() {
      if (this.isPrimitive()) {
         Class type = (Class)this.runtimeType;
         return of(Primitives.wrap(type));
      } else {
         return this;
      }
   }

   private boolean isWrapper() {
      return Primitives.allWrapperTypes().contains(this.runtimeType);
   }

   public final TypeToken unwrap() {
      if (this.isWrapper()) {
         Class type = (Class)this.runtimeType;
         return of(Primitives.unwrap(type));
      } else {
         return this;
      }
   }

   @Nullable
   public final TypeToken getComponentType() {
      Type componentType = Types.getComponentType(this.runtimeType);
      return componentType == null ? null : of(componentType);
   }

   public final Invokable method(Method method) {
      Preconditions.checkArgument(this.someRawTypeIsSubclassOf(method.getDeclaringClass()), "%s not declared by %s", method, this);
      return new Invokable.MethodInvokable(method) {
         Type getGenericReturnType() {
            return TypeToken.this.resolveType(super.getGenericReturnType()).getType();
         }

         Type[] getGenericParameterTypes() {
            return TypeToken.this.resolveInPlace(super.getGenericParameterTypes());
         }

         Type[] getGenericExceptionTypes() {
            return TypeToken.this.resolveInPlace(super.getGenericExceptionTypes());
         }

         public TypeToken getOwnerType() {
            return TypeToken.this;
         }

         public String toString() {
            return this.getOwnerType() + "." + super.toString();
         }
      };
   }

   public final Invokable constructor(Constructor constructor) {
      Preconditions.checkArgument(constructor.getDeclaringClass() == this.getRawType(), "%s not declared by %s", constructor, this.getRawType());
      return new Invokable.ConstructorInvokable(constructor) {
         Type getGenericReturnType() {
            return TypeToken.this.resolveType(super.getGenericReturnType()).getType();
         }

         Type[] getGenericParameterTypes() {
            return TypeToken.this.resolveInPlace(super.getGenericParameterTypes());
         }

         Type[] getGenericExceptionTypes() {
            return TypeToken.this.resolveInPlace(super.getGenericExceptionTypes());
         }

         public TypeToken getOwnerType() {
            return TypeToken.this;
         }

         public String toString() {
            return this.getOwnerType() + "(" + Joiner.on(", ").join((Object[])this.getGenericParameterTypes()) + ")";
         }
      };
   }

   public boolean equals(@Nullable Object o) {
      if (o instanceof TypeToken) {
         TypeToken that = (TypeToken)o;
         return this.runtimeType.equals(that.runtimeType);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.runtimeType.hashCode();
   }

   public String toString() {
      return Types.toString(this.runtimeType);
   }

   protected Object writeReplace() {
      return of((new TypeResolver()).resolveType(this.runtimeType));
   }

   @CanIgnoreReturnValue
   final TypeToken rejectTypeVariables() {
      (new TypeVisitor() {
         void visitTypeVariable(TypeVariable type) {
            throw new IllegalArgumentException(TypeToken.this.runtimeType + "contains a type variable and is not safe for the operation");
         }

         void visitWildcardType(WildcardType type) {
            this.visit(type.getLowerBounds());
            this.visit(type.getUpperBounds());
         }

         void visitParameterizedType(ParameterizedType type) {
            this.visit(type.getActualTypeArguments());
            this.visit(new Type[]{type.getOwnerType()});
         }

         void visitGenericArrayType(GenericArrayType type) {
            this.visit(new Type[]{type.getGenericComponentType()});
         }
      }).visit(new Type[]{this.runtimeType});
      return this;
   }

   private boolean someRawTypeIsSubclassOf(Class superclass) {
      UnmodifiableIterator var2 = this.getRawTypes().iterator();

      Class rawType;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         rawType = (Class)var2.next();
      } while(!superclass.isAssignableFrom(rawType));

      return true;
   }

   private boolean isSubtypeOfParameterizedType(ParameterizedType supertype) {
      Class matchedClass = of((Type)supertype).getRawType();
      if (!this.someRawTypeIsSubclassOf(matchedClass)) {
         return false;
      } else {
         Type[] typeParams = matchedClass.getTypeParameters();
         Type[] toTypeArgs = supertype.getActualTypeArguments();

         for(int i = 0; i < typeParams.length; ++i) {
            if (!this.resolveType(typeParams[i]).is(toTypeArgs[i])) {
               return false;
            }
         }

         return Modifier.isStatic(((Class)supertype.getRawType()).getModifiers()) || supertype.getOwnerType() == null || this.isOwnedBySubtypeOf(supertype.getOwnerType());
      }
   }

   private boolean isSubtypeOfArrayType(GenericArrayType supertype) {
      if (this.runtimeType instanceof Class) {
         Class fromClass = (Class)this.runtimeType;
         return !fromClass.isArray() ? false : of(fromClass.getComponentType()).isSubtypeOf(supertype.getGenericComponentType());
      } else if (this.runtimeType instanceof GenericArrayType) {
         GenericArrayType fromArrayType = (GenericArrayType)this.runtimeType;
         return of(fromArrayType.getGenericComponentType()).isSubtypeOf(supertype.getGenericComponentType());
      } else {
         return false;
      }
   }

   private boolean isSupertypeOfArray(GenericArrayType subtype) {
      if (this.runtimeType instanceof Class) {
         Class thisClass = (Class)this.runtimeType;
         return !thisClass.isArray() ? thisClass.isAssignableFrom(Object[].class) : of(subtype.getGenericComponentType()).isSubtypeOf((Type)thisClass.getComponentType());
      } else {
         return this.runtimeType instanceof GenericArrayType ? of(subtype.getGenericComponentType()).isSubtypeOf(((GenericArrayType)this.runtimeType).getGenericComponentType()) : false;
      }
   }

   private boolean is(Type formalType) {
      if (this.runtimeType.equals(formalType)) {
         return true;
      } else if (!(formalType instanceof WildcardType)) {
         return false;
      } else {
         return every(((WildcardType)formalType).getUpperBounds()).isSupertypeOf(this.runtimeType) && every(((WildcardType)formalType).getLowerBounds()).isSubtypeOf(this.runtimeType);
      }
   }

   private static Bounds every(Type[] bounds) {
      return new Bounds(bounds, false);
   }

   private static Bounds any(Type[] bounds) {
      return new Bounds(bounds, true);
   }

   private ImmutableSet getRawTypes() {
      final ImmutableSet.Builder builder = ImmutableSet.builder();
      (new TypeVisitor() {
         void visitTypeVariable(TypeVariable t) {
            this.visit(t.getBounds());
         }

         void visitWildcardType(WildcardType t) {
            this.visit(t.getUpperBounds());
         }

         void visitParameterizedType(ParameterizedType t) {
            builder.add((Object)((Class)t.getRawType()));
         }

         void visitClass(Class t) {
            builder.add((Object)t);
         }

         void visitGenericArrayType(GenericArrayType t) {
            builder.add((Object)Types.getArrayClass(TypeToken.of(t.getGenericComponentType()).getRawType()));
         }
      }).visit(new Type[]{this.runtimeType});
      ImmutableSet result = builder.build();
      return result;
   }

   private boolean isOwnedBySubtypeOf(Type supertype) {
      Iterator var2 = this.getTypes().iterator();

      Type ownerType;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         TypeToken type = (TypeToken)var2.next();
         ownerType = type.getOwnerTypeIfPresent();
      } while(ownerType == null || !of(ownerType).isSubtypeOf(supertype));

      return true;
   }

   @Nullable
   private Type getOwnerTypeIfPresent() {
      if (this.runtimeType instanceof ParameterizedType) {
         return ((ParameterizedType)this.runtimeType).getOwnerType();
      } else {
         return this.runtimeType instanceof Class ? ((Class)this.runtimeType).getEnclosingClass() : null;
      }
   }

   @VisibleForTesting
   static TypeToken toGenericType(Class cls) {
      if (cls.isArray()) {
         Type arrayOfGenericType = Types.newArrayType(toGenericType(cls.getComponentType()).runtimeType);
         TypeToken result = of(arrayOfGenericType);
         return result;
      } else {
         TypeVariable[] typeParams = cls.getTypeParameters();
         Type ownerType = cls.isMemberClass() && !Modifier.isStatic(cls.getModifiers()) ? toGenericType(cls.getEnclosingClass()).runtimeType : null;
         if (typeParams.length <= 0 && (ownerType == null || ownerType == cls.getEnclosingClass())) {
            return of(cls);
         } else {
            TypeToken type = of((Type)Types.newParameterizedTypeWithOwner(ownerType, cls, typeParams));
            return type;
         }
      }
   }

   private TypeToken getSupertypeFromUpperBounds(Class supertype, Type[] upperBounds) {
      Type[] var3 = upperBounds;
      int var4 = upperBounds.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Type upperBound = var3[var5];
         TypeToken bound = of(upperBound);
         if (bound.isSubtypeOf((Type)supertype)) {
            TypeToken result = bound.getSupertype(supertype);
            return result;
         }
      }

      throw new IllegalArgumentException(supertype + " isn't a super type of " + this);
   }

   private TypeToken getSubtypeFromLowerBounds(Class subclass, Type[] lowerBounds) {
      int var4 = lowerBounds.length;
      byte var5 = 0;
      if (var5 < var4) {
         Type lowerBound = lowerBounds[var5];
         TypeToken bound = of(lowerBound);
         return bound.getSubtype(subclass);
      } else {
         throw new IllegalArgumentException(subclass + " isn't a subclass of " + this);
      }
   }

   private TypeToken getArraySupertype(Class supertype) {
      TypeToken componentType = (TypeToken)Preconditions.checkNotNull(this.getComponentType(), "%s isn't a super type of %s", supertype, this);
      TypeToken componentSupertype = componentType.getSupertype(supertype.getComponentType());
      TypeToken result = of(newArrayClassOrGenericArrayType(componentSupertype.runtimeType));
      return result;
   }

   private TypeToken getArraySubtype(Class subclass) {
      TypeToken componentSubtype = this.getComponentType().getSubtype(subclass.getComponentType());
      TypeToken result = of(newArrayClassOrGenericArrayType(componentSubtype.runtimeType));
      return result;
   }

   private Type resolveTypeArgsForSubclass(Class subclass) {
      if (!(this.runtimeType instanceof Class) || subclass.getTypeParameters().length != 0 && this.getRawType().getTypeParameters().length == 0) {
         TypeToken genericSubtype = toGenericType(subclass);
         Type supertypeWithArgsFromSubtype = genericSubtype.getSupertype(this.getRawType()).runtimeType;
         return (new TypeResolver()).where(supertypeWithArgsFromSubtype, this.runtimeType).resolveType(genericSubtype.runtimeType);
      } else {
         return subclass;
      }
   }

   private static Type newArrayClassOrGenericArrayType(Type componentType) {
      return Types.JavaVersion.JAVA7.newArrayType(componentType);
   }

   // $FF: synthetic method
   TypeToken(Type x0, Object x1) {
      this(x0);
   }

   private abstract static class TypeCollector {
      static final TypeCollector FOR_GENERIC_TYPE = new TypeCollector() {
         Class getRawType(TypeToken type) {
            return type.getRawType();
         }

         Iterable getInterfaces(TypeToken type) {
            return type.getGenericInterfaces();
         }

         @Nullable
         TypeToken getSuperclass(TypeToken type) {
            return type.getGenericSuperclass();
         }
      };
      static final TypeCollector FOR_RAW_TYPE = new TypeCollector() {
         Class getRawType(Class type) {
            return type;
         }

         Iterable getInterfaces(Class type) {
            return Arrays.asList(type.getInterfaces());
         }

         @Nullable
         Class getSuperclass(Class type) {
            return type.getSuperclass();
         }
      };

      private TypeCollector() {
      }

      final TypeCollector classesOnly() {
         return new ForwardingTypeCollector(this) {
            Iterable getInterfaces(Object type) {
               return ImmutableSet.of();
            }

            ImmutableList collectTypes(Iterable types) {
               ImmutableList.Builder builder = ImmutableList.builder();
               Iterator var3 = types.iterator();

               while(var3.hasNext()) {
                  Object type = var3.next();
                  if (!this.getRawType(type).isInterface()) {
                     builder.add(type);
                  }
               }

               return super.collectTypes(builder.build());
            }
         };
      }

      final ImmutableList collectTypes(Object type) {
         return this.collectTypes((Iterable)ImmutableList.of(type));
      }

      ImmutableList collectTypes(Iterable types) {
         Map map = Maps.newHashMap();
         Iterator var3 = types.iterator();

         while(var3.hasNext()) {
            Object type = var3.next();
            this.collectTypes(type, map);
         }

         return sortKeysByValue(map, Ordering.natural().reverse());
      }

      @CanIgnoreReturnValue
      private int collectTypes(Object type, Map map) {
         Integer existing = (Integer)map.get(type);
         if (existing != null) {
            return existing;
         } else {
            int aboveMe = this.getRawType(type).isInterface() ? 1 : 0;

            Object interfaceType;
            for(Iterator var5 = this.getInterfaces(type).iterator(); var5.hasNext(); aboveMe = Math.max(aboveMe, this.collectTypes(interfaceType, map))) {
               interfaceType = var5.next();
            }

            Object superclass = this.getSuperclass(type);
            if (superclass != null) {
               aboveMe = Math.max(aboveMe, this.collectTypes(superclass, map));
            }

            map.put(type, aboveMe + 1);
            return aboveMe + 1;
         }
      }

      private static ImmutableList sortKeysByValue(final Map map, final Comparator valueComparator) {
         Ordering keyOrdering = new Ordering() {
            public int compare(Object left, Object right) {
               return valueComparator.compare(map.get(left), map.get(right));
            }
         };
         return keyOrdering.immutableSortedCopy(map.keySet());
      }

      abstract Class getRawType(Object var1);

      abstract Iterable getInterfaces(Object var1);

      @Nullable
      abstract Object getSuperclass(Object var1);

      // $FF: synthetic method
      TypeCollector(Object x0) {
         this();
      }

      private static class ForwardingTypeCollector extends TypeCollector {
         private final TypeCollector delegate;

         ForwardingTypeCollector(TypeCollector delegate) {
            super(null);
            this.delegate = delegate;
         }

         Class getRawType(Object type) {
            return this.delegate.getRawType(type);
         }

         Iterable getInterfaces(Object type) {
            return this.delegate.getInterfaces(type);
         }

         Object getSuperclass(Object type) {
            return this.delegate.getSuperclass(type);
         }
      }
   }

   private static final class SimpleTypeToken extends TypeToken {
      private static final long serialVersionUID = 0L;

      SimpleTypeToken(Type type) {
         super(type, null);
      }
   }

   private static class Bounds {
      private final Type[] bounds;
      private final boolean target;

      Bounds(Type[] bounds, boolean target) {
         this.bounds = bounds;
         this.target = target;
      }

      boolean isSubtypeOf(Type supertype) {
         Type[] var2 = this.bounds;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Type bound = var2[var4];
            if (TypeToken.of(bound).isSubtypeOf(supertype) == this.target) {
               return this.target;
            }
         }

         return !this.target;
      }

      boolean isSupertypeOf(Type subtype) {
         TypeToken type = TypeToken.of(subtype);
         Type[] var3 = this.bounds;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Type bound = var3[var5];
            if (type.isSubtypeOf(bound) == this.target) {
               return this.target;
            }
         }

         return !this.target;
      }
   }

   private static enum TypeFilter implements Predicate {
      IGNORE_TYPE_VARIABLE_OR_WILDCARD {
         public boolean apply(TypeToken type) {
            return !(type.runtimeType instanceof TypeVariable) && !(type.runtimeType instanceof WildcardType);
         }
      },
      INTERFACE_ONLY {
         public boolean apply(TypeToken type) {
            return type.getRawType().isInterface();
         }
      };

      private TypeFilter() {
      }

      // $FF: synthetic method
      TypeFilter(Object x2) {
         this();
      }
   }

   private final class ClassSet extends TypeSet {
      private transient ImmutableSet classes;
      private static final long serialVersionUID = 0L;

      private ClassSet() {
         super();
      }

      protected Set delegate() {
         ImmutableSet result = this.classes;
         if (result == null) {
            ImmutableList collectedTypes = TypeToken.TypeCollector.FOR_GENERIC_TYPE.classesOnly().collectTypes((Object)TypeToken.this);
            return this.classes = FluentIterable.from((Iterable)collectedTypes).filter((Predicate)TypeToken.TypeFilter.IGNORE_TYPE_VARIABLE_OR_WILDCARD).toSet();
         } else {
            return result;
         }
      }

      public TypeSet classes() {
         return this;
      }

      public Set rawTypes() {
         ImmutableList collectedTypes = TypeToken.TypeCollector.FOR_RAW_TYPE.classesOnly().collectTypes((Iterable)TypeToken.this.getRawTypes());
         return ImmutableSet.copyOf((Collection)collectedTypes);
      }

      public TypeSet interfaces() {
         throw new UnsupportedOperationException("classes().interfaces() not supported.");
      }

      private Object readResolve() {
         return TypeToken.this.getTypes().classes();
      }

      // $FF: synthetic method
      ClassSet(Object x1) {
         this();
      }
   }

   private final class InterfaceSet extends TypeSet {
      private final transient TypeSet allTypes;
      private transient ImmutableSet interfaces;
      private static final long serialVersionUID = 0L;

      InterfaceSet(TypeSet allTypes) {
         super();
         this.allTypes = allTypes;
      }

      protected Set delegate() {
         ImmutableSet result = this.interfaces;
         return result == null ? (this.interfaces = FluentIterable.from((Iterable)this.allTypes).filter((Predicate)TypeToken.TypeFilter.INTERFACE_ONLY).toSet()) : result;
      }

      public TypeSet interfaces() {
         return this;
      }

      public Set rawTypes() {
         ImmutableList collectedTypes = TypeToken.TypeCollector.FOR_RAW_TYPE.collectTypes((Iterable)TypeToken.this.getRawTypes());
         return FluentIterable.from((Iterable)collectedTypes).filter(new Predicate() {
            public boolean apply(Class type) {
               return type.isInterface();
            }
         }).toSet();
      }

      public TypeSet classes() {
         throw new UnsupportedOperationException("interfaces().classes() not supported.");
      }

      private Object readResolve() {
         return TypeToken.this.getTypes().interfaces();
      }
   }

   public class TypeSet extends ForwardingSet implements Serializable {
      private transient ImmutableSet types;
      private static final long serialVersionUID = 0L;

      TypeSet() {
      }

      public TypeSet interfaces() {
         return TypeToken.this.new InterfaceSet(this);
      }

      public TypeSet classes() {
         return TypeToken.this.new ClassSet();
      }

      protected Set delegate() {
         ImmutableSet filteredTypes = this.types;
         if (filteredTypes == null) {
            ImmutableList collectedTypes = TypeToken.TypeCollector.FOR_GENERIC_TYPE.collectTypes((Object)TypeToken.this);
            return this.types = FluentIterable.from((Iterable)collectedTypes).filter((Predicate)TypeToken.TypeFilter.IGNORE_TYPE_VARIABLE_OR_WILDCARD).toSet();
         } else {
            return filteredTypes;
         }
      }

      public Set rawTypes() {
         ImmutableList collectedTypes = TypeToken.TypeCollector.FOR_RAW_TYPE.collectTypes((Iterable)TypeToken.this.getRawTypes());
         return ImmutableSet.copyOf((Collection)collectedTypes);
      }
   }
}
