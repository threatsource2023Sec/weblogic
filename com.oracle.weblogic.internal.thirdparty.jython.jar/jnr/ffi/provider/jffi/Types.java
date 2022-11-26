package jnr.ffi.provider.jffi;

import java.lang.annotation.Annotation;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.nio.Buffer;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import jnr.ffi.Address;
import jnr.ffi.NativeType;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.Type;
import jnr.ffi.annotations.TypeDefinition;

class Types {
   private static Reference typeCacheReference;

   static Type getType(Runtime runtime, Class javaType, Collection annotations) {
      Map cache = typeCacheReference != null ? (Map)typeCacheReference.get() : null;
      Map aliasCache = cache != null ? (Map)cache.get(javaType) : null;
      Type type = aliasCache != null ? (Type)aliasCache.get(annotations) : null;
      return type != null ? type : lookupAndCacheType(runtime, javaType, annotations);
   }

   private static synchronized Type lookupAndCacheType(Runtime runtime, Class javaType, Collection annotations) {
      Map cache = typeCacheReference != null ? (Map)typeCacheReference.get() : null;
      Map aliasCache = cache != null ? (Map)cache.get(javaType) : null;
      Type type = aliasCache != null ? (Type)aliasCache.get(annotations) : null;
      if (type != null) {
         return type;
      } else {
         Map cache = new HashMap(cache != null ? cache : Collections.EMPTY_MAP);
         Map aliasCache = new HashMap(aliasCache != null ? aliasCache : Collections.EMPTY_MAP);
         aliasCache.put(annotations, type = lookupType(runtime, javaType, annotations));
         cache.put(javaType, Collections.unmodifiableMap(aliasCache));
         typeCacheReference = new SoftReference(Collections.unmodifiableMap(new IdentityHashMap(cache)));
         return type;
      }
   }

   private static Type lookupAliasedType(Runtime runtime, Collection annotations) {
      Iterator var2 = annotations.iterator();

      TypeDefinition typedef;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         Annotation a = (Annotation)var2.next();
         typedef = (TypeDefinition)a.annotationType().getAnnotation(TypeDefinition.class);
      } while(typedef == null);

      return runtime.findType(typedef.alias());
   }

   static Type lookupType(Runtime runtime, Class type, Collection annotations) {
      Type aliasedType = type.isArray() ? null : lookupAliasedType(runtime, annotations);
      if (aliasedType != null) {
         return aliasedType;
      } else if (!Void.class.isAssignableFrom(type) && Void.TYPE != type) {
         if (!Boolean.class.isAssignableFrom(type) && Boolean.TYPE != type) {
            if (!Byte.class.isAssignableFrom(type) && Byte.TYPE != type) {
               if (!Short.class.isAssignableFrom(type) && Short.TYPE != type) {
                  if (!Integer.class.isAssignableFrom(type) && Integer.TYPE != type) {
                     if (!Long.class.isAssignableFrom(type) && Long.TYPE != type) {
                        if (!Float.class.isAssignableFrom(type) && Float.TYPE != type) {
                           if (!Double.class.isAssignableFrom(type) && Double.TYPE != type) {
                              if (Pointer.class.isAssignableFrom(type)) {
                                 return runtime.findType(NativeType.ADDRESS);
                              } else if (Address.class.isAssignableFrom(type)) {
                                 return runtime.findType(NativeType.ADDRESS);
                              } else if (Buffer.class.isAssignableFrom(type)) {
                                 return runtime.findType(NativeType.ADDRESS);
                              } else if (CharSequence.class.isAssignableFrom(type)) {
                                 return runtime.findType(NativeType.ADDRESS);
                              } else if (type.isArray()) {
                                 return runtime.findType(NativeType.ADDRESS);
                              } else {
                                 throw new IllegalArgumentException("unsupported type: " + type);
                              }
                           } else {
                              return runtime.findType(NativeType.DOUBLE);
                           }
                        } else {
                           return runtime.findType(NativeType.FLOAT);
                        }
                     } else {
                        return runtime.findType(NativeType.SLONG);
                     }
                  } else {
                     return runtime.findType(NativeType.SINT);
                  }
               } else {
                  return runtime.findType(NativeType.SSHORT);
               }
            } else {
               return runtime.findType(NativeType.SCHAR);
            }
         } else {
            return runtime.findType(NativeType.SINT);
         }
      } else {
         return runtime.findType(NativeType.VOID);
      }
   }
}
