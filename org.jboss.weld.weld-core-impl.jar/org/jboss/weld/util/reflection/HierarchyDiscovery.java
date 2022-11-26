package org.jboss.weld.util.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jboss.weld.util.Types;
import org.jboss.weld.util.collections.ImmutableSet;

public class HierarchyDiscovery {
   private final Map types;
   private final Map resolvedTypeVariables;
   private final TypeResolver resolver;
   private final Set typeClosure;

   public static HierarchyDiscovery forNormalizedType(Type type) {
      return new HierarchyDiscovery(Types.getCanonicalType(type));
   }

   public HierarchyDiscovery(Type type) {
      this(type, new TypeResolver(new HashMap()));
   }

   public HierarchyDiscovery(Type type, TypeResolver resolver) {
      this.types = new HashMap();
      this.resolver = resolver;
      this.resolvedTypeVariables = resolver.getResolvedTypeVariables();
      this.discoverTypes(type, false);
      this.typeClosure = ImmutableSet.copyOf(this.types.values());
   }

   public Set getTypeClosure() {
      return this.typeClosure;
   }

   public Map getTypeMap() {
      return this.types;
   }

   protected void discoverTypes(Type type, boolean rawGeneric) {
      if (!rawGeneric) {
         rawGeneric = Types.isRawGenericType(type);
      }

      if (type instanceof Class) {
         Class clazz = (Class)type;
         this.types.put(clazz, clazz);
         this.discoverFromClass(clazz, rawGeneric);
      } else if (rawGeneric) {
         this.discoverTypes(Reflections.getRawType(type), rawGeneric);
      } else {
         Type rawType;
         Class clazz;
         if (type instanceof GenericArrayType) {
            GenericArrayType arrayType = (GenericArrayType)type;
            rawType = arrayType.getGenericComponentType();
            clazz = Reflections.getRawType(rawType);
            if (clazz != null) {
               Class arrayClass = Array.newInstance(clazz, 0).getClass();
               this.types.put(arrayClass, type);
               this.discoverFromClass(arrayClass, rawGeneric);
            }
         } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            rawType = parameterizedType.getRawType();
            if (rawType instanceof Class) {
               clazz = (Class)rawType;
               this.processTypeVariables(clazz.getTypeParameters(), parameterizedType.getActualTypeArguments());
               this.types.put(clazz, type);
               this.discoverFromClass(clazz, rawGeneric);
            }
         }
      }

   }

   protected void discoverFromClass(Class clazz, boolean rawGeneric) {
      if (clazz.getSuperclass() != null) {
         this.discoverTypes(this.processAndResolveType(clazz.getGenericSuperclass(), clazz.getSuperclass()), rawGeneric);
      }

      this.discoverInterfaces(clazz, rawGeneric);
   }

   protected void discoverInterfaces(Class clazz, boolean rawGeneric) {
      Type[] genericInterfaces = clazz.getGenericInterfaces();
      Class[] interfaces = clazz.getInterfaces();
      if (genericInterfaces.length == interfaces.length) {
         for(int i = 0; i < interfaces.length; ++i) {
            this.discoverTypes(this.processAndResolveType(genericInterfaces[i], interfaces[i]), rawGeneric);
         }
      }

   }

   protected Type processAndResolveType(Type superclass, Class rawSuperclass) {
      if (superclass instanceof ParameterizedType) {
         ParameterizedType parameterizedSuperclass = (ParameterizedType)superclass;
         this.processTypeVariables(rawSuperclass.getTypeParameters(), parameterizedSuperclass.getActualTypeArguments());
         return this.resolveType(parameterizedSuperclass);
      } else if (superclass instanceof Class) {
         return superclass;
      } else {
         throw new RuntimeException("Unexpected type: " + superclass);
      }
   }

   private void processTypeVariables(TypeVariable[] variables, Type[] values) {
      for(int i = 0; i < variables.length; ++i) {
         this.processTypeVariable(variables[i], values[i]);
      }

   }

   private void processTypeVariable(TypeVariable variable, Type value) {
      if (value instanceof TypeVariable) {
         value = this.resolveType(value);
      }

      this.resolvedTypeVariables.put(variable, value);
   }

   public Type resolveType(Type type) {
      if (type instanceof Class) {
         Type resolvedType = (Type)this.types.get(type);
         if (resolvedType != null) {
            return resolvedType;
         }
      }

      return this.resolver.resolveType(type);
   }

   public TypeResolver getResolver() {
      return this.resolver;
   }
}
