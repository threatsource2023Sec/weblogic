package org.jboss.weld.annotated.slim.backed;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import org.jboss.weld.annotated.slim.AnnotatedTypeIdentifier;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.exceptions.InvalidObjectException;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.resources.ReflectionCache;
import org.jboss.weld.resources.SharedObjectCache;
import org.jboss.weld.util.LazyValueHolder;
import org.jboss.weld.util.Types;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

@SuppressFBWarnings(
   value = {"SE_BAD_FIELD", "SE_NO_SUITABLE_CONSTRUCTOR", "SE_BAD_FIELD_STORE", "SE_NO_SERIALVERSIONID"},
   justification = "False positive from FindBugs - serialization is handled by SerializationProxy."
)
public class BackedAnnotatedType extends BackedAnnotated implements SlimAnnotatedType, Serializable {
   private final Class javaClass;
   private final LazyValueHolder constructors;
   private final LazyValueHolder methods;
   private final LazyValueHolder fields;
   private final SharedObjectCache sharedObjectCache;
   private final ReflectionCache reflectionCache;
   private final AnnotatedTypeIdentifier identifier;

   public static BackedAnnotatedType of(Class javaClass, SharedObjectCache sharedObjectCache, ReflectionCache reflectionCache, String contextId, String bdaId) {
      return of(javaClass, javaClass, sharedObjectCache, reflectionCache, contextId, bdaId);
   }

   public static BackedAnnotatedType of(Class javaClass, Type baseType, SharedObjectCache sharedObjectCache, ReflectionCache reflectionCache, String contextId, String bdaId) {
      return of(javaClass, baseType, sharedObjectCache, reflectionCache, contextId, bdaId, (String)null);
   }

   public static BackedAnnotatedType of(Class javaClass, Type baseType, SharedObjectCache sharedObjectCache, ReflectionCache reflectionCache, String contextId, String bdaId, String suffix) {
      return new BackedAnnotatedType(javaClass, baseType, sharedObjectCache, reflectionCache, contextId, bdaId, suffix);
   }

   private BackedAnnotatedType(Class rawType, Type baseType, SharedObjectCache sharedObjectCache, ReflectionCache reflectionCache, String contextId, String bdaId, String suffix) {
      super(baseType, sharedObjectCache);
      this.javaClass = rawType;
      this.sharedObjectCache = sharedObjectCache;
      this.reflectionCache = reflectionCache;
      this.constructors = new BackedAnnotatedConstructors();
      this.fields = new BackedAnnotatedFields();
      this.methods = new BackedAnnotatedMethods();
      this.identifier = AnnotatedTypeIdentifier.forBackedAnnotatedType(contextId, rawType, baseType, bdaId, suffix);
   }

   protected LazyValueHolder initTypeClosure(Type baseType, SharedObjectCache cache) {
      return cache.getTypeClosureHolder(Types.getCanonicalType(baseType));
   }

   protected AnnotatedElement getAnnotatedElement() {
      return this.javaClass;
   }

   public Class getJavaClass() {
      return this.javaClass;
   }

   public Set getConstructors() {
      return (Set)this.constructors.get();
   }

   public Set getMethods() {
      return (Set)this.methods.get();
   }

   public Set getFields() {
      return (Set)this.fields.get();
   }

   public Annotation getAnnotation(Class annotationType) {
      Iterator var2 = this.getAnnotations().iterator();

      Annotation annotation;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         annotation = (Annotation)var2.next();
      } while(!annotation.annotationType().equals(annotationType));

      return (Annotation)annotationType.cast(annotation);
   }

   public boolean isAnnotationPresent(Class annotationType) {
      return this.getAnnotation(annotationType) != null;
   }

   public Set getAnnotations() {
      return this.reflectionCache.getBackedAnnotatedTypeAnnotationSet(this.javaClass);
   }

   public int hashCode() {
      return this.identifier.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj instanceof BackedAnnotatedType) {
         BackedAnnotatedType that = (BackedAnnotatedType)Reflections.cast(obj);
         return Objects.equals(this.identifier, that.identifier);
      } else {
         return false;
      }
   }

   public String toString() {
      return Formats.formatAnnotatedType(this);
   }

   public void clear() {
      this.constructors.clear();
      this.fields.clear();
      this.methods.clear();
   }

   private Object writeReplace() throws ObjectStreamException {
      return new SlimAnnotatedType.SerializationProxy(this.getIdentifier());
   }

   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
      throw BeanLogger.LOG.serializationProxyRequired();
   }

   public ReflectionCache getReflectionCache() {
      return this.reflectionCache;
   }

   public AnnotatedTypeIdentifier getIdentifier() {
      return this.identifier;
   }

   private class BackedAnnotatedMethods extends EagerlyInitializedLazyValueHolder {
      private BackedAnnotatedMethods() {
         super();
      }

      protected Set computeValue() {
         ImmutableSet.Builder methods = ImmutableSet.builder();

         for(Class clazz = BackedAnnotatedType.this.javaClass; clazz != Object.class && clazz != null; clazz = clazz.getSuperclass()) {
            Method[] var3 = SecurityActions.getDeclaredMethods(clazz);
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Method method = var3[var5];
               methods.add(BackedAnnotatedMethod.of(method, BackedAnnotatedType.this, BackedAnnotatedType.this.sharedObjectCache));
            }
         }

         Iterator var9 = Reflections.getInterfaceClosure(BackedAnnotatedType.this.javaClass).iterator();

         while(var9.hasNext()) {
            Class interfaceClazz = (Class)var9.next();
            Method[] var11 = SecurityActions.getDeclaredMethods(interfaceClazz);
            int var12 = var11.length;

            for(int var7 = 0; var7 < var12; ++var7) {
               Method methodx = var11[var7];
               if (Reflections.isDefault(methodx)) {
                  methods.add(BackedAnnotatedMethod.of(methodx, BackedAnnotatedType.this, BackedAnnotatedType.this.sharedObjectCache));
               }
            }
         }

         return methods.build();
      }

      // $FF: synthetic method
      BackedAnnotatedMethods(Object x1) {
         this();
      }
   }

   private class BackedAnnotatedFields extends EagerlyInitializedLazyValueHolder {
      private BackedAnnotatedFields() {
         super();
      }

      protected Set computeValue() {
         ImmutableSet.Builder fields = ImmutableSet.builder();

         for(Class clazz = BackedAnnotatedType.this.javaClass; clazz != Object.class && clazz != null; clazz = clazz.getSuperclass()) {
            Field[] var3 = SecurityActions.getDeclaredFields(clazz);
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Field field = var3[var5];
               fields.add(BackedAnnotatedField.of(field, BackedAnnotatedType.this, BackedAnnotatedType.this.sharedObjectCache));
            }
         }

         return fields.build();
      }

      // $FF: synthetic method
      BackedAnnotatedFields(Object x1) {
         this();
      }
   }

   private class BackedAnnotatedConstructors extends EagerlyInitializedLazyValueHolder {
      private BackedAnnotatedConstructors() {
         super();
      }

      protected Set computeValue() {
         Constructor[] declaredConstructors = SecurityActions.getDeclaredConstructors(BackedAnnotatedType.this.javaClass);
         ImmutableSet.Builder constructors = ImmutableSet.builder();
         Constructor[] var3 = declaredConstructors;
         int var4 = declaredConstructors.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Constructor constructor = var3[var5];
            Constructor c = (Constructor)Reflections.cast(constructor);
            constructors.add(BackedAnnotatedConstructor.of(c, BackedAnnotatedType.this, BackedAnnotatedType.this.sharedObjectCache));
         }

         return constructors.build();
      }

      // $FF: synthetic method
      BackedAnnotatedConstructors(Object x1) {
         this();
      }
   }

   private abstract class EagerlyInitializedLazyValueHolder extends LazyValueHolder {
      public EagerlyInitializedLazyValueHolder() {
         this.get();
      }
   }
}
