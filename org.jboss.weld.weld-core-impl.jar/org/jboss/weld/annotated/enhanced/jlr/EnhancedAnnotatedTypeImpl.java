package org.jboss.weld.annotated.enhanced.jlr;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.inject.Inject;
import org.jboss.weld.annotated.enhanced.ConstructorSignature;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedConstructor;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedField;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.enhanced.MethodSignature;
import org.jboss.weld.annotated.slim.AnnotatedTypeIdentifier;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.annotated.slim.backed.BackedAnnotatedType;
import org.jboss.weld.interceptor.spi.model.InterceptionType;
import org.jboss.weld.interceptor.util.InterceptionTypeRegistry;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.collections.ListMultimap;
import org.jboss.weld.util.collections.Multimap;
import org.jboss.weld.util.collections.Multimaps;
import org.jboss.weld.util.collections.SetMultimap;
import org.jboss.weld.util.collections.Sets;
import org.jboss.weld.util.collections.WeldCollections;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

public class EnhancedAnnotatedTypeImpl extends AbstractEnhancedAnnotated implements EnhancedAnnotatedType {
   private static final Set MAPPED_METHOD_ANNOTATIONS;
   @SuppressFBWarnings({"unchecked"})
   private static final Set MAPPED_METHOD_PARAMETER_ANNOTATIONS;
   @SuppressFBWarnings({"unchecked"})
   private static final Set MAPPED_DECLARED_METHOD_PARAMETER_ANNOTATIONS;
   private final EnhancedAnnotatedType superclass;
   private final Set fields;
   private final Multimap annotatedFields;
   private final Set declaredFields;
   private final Multimap declaredAnnotatedFields;
   private final Set methods;
   private final Multimap annotatedMethods;
   private Set overriddenMethods;
   private final Multimap annotatedMethodsByAnnotatedParameters;
   private final Set declaredMethods;
   private final Multimap declaredAnnotatedMethods;
   private final Multimap declaredMethodsByAnnotatedParameters;
   private final Set constructors;
   private final Map declaredConstructorsBySignature;
   private final Multimap declaredMetaAnnotationMap;
   private final boolean discovered;
   private final SlimAnnotatedType slim;

   public static EnhancedAnnotatedType of(SlimAnnotatedType annotatedType, ClassTransformer classTransformer) {
      return annotatedType instanceof BackedAnnotatedType ? new EnhancedAnnotatedTypeImpl(annotatedType, buildAnnotationMap(annotatedType.getAnnotations()), buildAnnotationMap(classTransformer.getReflectionCache().getDeclaredAnnotations(annotatedType.getJavaClass())), classTransformer) : new EnhancedAnnotatedTypeImpl(annotatedType, buildAnnotationMap(annotatedType.getAnnotations()), buildAnnotationMap(annotatedType.getAnnotations()), classTransformer);
   }

   protected EnhancedAnnotatedTypeImpl(SlimAnnotatedType annotatedType, Map annotationMap, Map declaredAnnotationMap, ClassTransformer classTransformer) {
      super(annotatedType, annotationMap, declaredAnnotationMap, classTransformer);
      this.slim = annotatedType;
      this.discovered = annotatedType instanceof BackedAnnotatedType;
      if (this.discovered) {
         Class superclass = annotatedType.getJavaClass().getSuperclass();
         if (superclass == null) {
            this.superclass = null;
         } else {
            this.superclass = classTransformer.getEnhancedAnnotatedType(superclass, ((AnnotatedTypeIdentifier)this.slim.getIdentifier()).getBdaId());
         }
      } else {
         this.superclass = classTransformer.getEnhancedAnnotatedType(Object.class, AnnotatedTypeIdentifier.NULL_BDA_ID);
      }

      Multimap declaredAnnotatedFields = new ListMultimap();
      Set fieldsTemp = null;
      ArrayList declaredFieldsTemp = new ArrayList();
      Class javaClass = annotatedType.getJavaClass();
      Iterator var9;
      ListMultimap declaredAnnotatedMethods;
      Iterator var28;
      if (this.discovered) {
         this.annotatedFields = null;
         if (javaClass != Object.class) {
            var9 = annotatedType.getFields().iterator();

            label185:
            while(true) {
               AnnotatedField field;
               do {
                  if (!var9.hasNext()) {
                     fieldsTemp = new HashSet(declaredFieldsTemp);
                     if (this.superclass != null && this.superclass.getJavaClass() != Object.class) {
                        fieldsTemp = Sets.union((Set)fieldsTemp, (Set)Reflections.cast(this.superclass.getFields()));
                     }
                     break label185;
                  }

                  field = (AnnotatedField)var9.next();
               } while(!field.getJavaMember().getDeclaringClass().equals(javaClass));

               EnhancedAnnotatedField annotatedField = EnhancedAnnotatedFieldImpl.of(field, this, classTransformer);
               declaredFieldsTemp.add(annotatedField);
               Iterator var12 = annotatedField.getAnnotations().iterator();

               while(var12.hasNext()) {
                  Annotation annotation = (Annotation)var12.next();
                  declaredAnnotatedFields.put(annotation.annotationType(), annotatedField);
               }
            }
         }

         this.declaredFields = new HashSet(declaredFieldsTemp);
      } else {
         declaredAnnotatedMethods = new ListMultimap();
         fieldsTemp = new HashSet();
         Iterator var20 = annotatedType.getFields().iterator();

         while(var20.hasNext()) {
            AnnotatedField annotatedField = (AnnotatedField)var20.next();
            EnhancedAnnotatedField weldField = EnhancedAnnotatedFieldImpl.of(annotatedField, this, classTransformer);
            ((Set)fieldsTemp).add(weldField);
            if (annotatedField.getDeclaringType().getJavaClass().equals(javaClass)) {
               declaredFieldsTemp.add(weldField);
            }

            var28 = weldField.getAnnotations().iterator();

            while(var28.hasNext()) {
               Annotation annotation = (Annotation)var28.next();
               declaredAnnotatedMethods.put(annotation.annotationType(), weldField);
               if (annotatedField.getDeclaringType().getJavaClass().equals(javaClass)) {
                  declaredAnnotatedFields.put(annotation.annotationType(), weldField);
               }
            }
         }

         this.annotatedFields = Multimaps.unmodifiableMultimap(declaredAnnotatedMethods);
         this.declaredFields = new HashSet(declaredFieldsTemp);
      }

      this.fields = (Set)fieldsTemp;
      this.declaredAnnotatedFields = Multimaps.unmodifiableMultimap(declaredAnnotatedFields);
      this.constructors = new HashSet();
      this.declaredConstructorsBySignature = new HashMap();
      var9 = annotatedType.getConstructors().iterator();

      while(var9.hasNext()) {
         AnnotatedConstructor constructor = (AnnotatedConstructor)var9.next();
         EnhancedAnnotatedConstructor weldConstructor = EnhancedAnnotatedConstructorImpl.of(constructor, this, classTransformer);
         this.constructors.add(weldConstructor);
         this.declaredConstructorsBySignature.put(weldConstructor.getSignature(), weldConstructor);
      }

      declaredAnnotatedMethods = new ListMultimap();
      Multimap declaredMethodsByAnnotatedParameters = new ListMultimap();
      Set methodsTemp = new HashSet();
      ArrayList declaredMethodsTemp = new ArrayList();
      EnhancedAnnotatedMethodImpl weldMethod;
      Iterator var16;
      Annotation annotation;
      AnnotatedMethod method;
      Class annotationType;
      if (this.discovered) {
         if (!javaClass.equals(Object.class)) {
            var28 = annotatedType.getMethods().iterator();

            label149:
            while(true) {
               do {
                  if (!var28.hasNext()) {
                     methodsTemp.addAll(declaredMethodsTemp);
                     if (this.superclass != null) {
                        for(EnhancedAnnotatedType current = this.superclass; current.getJavaClass() != Object.class; current = current.getEnhancedSuperclass()) {
                           Set superClassMethods = (Set)Reflections.cast(current.getDeclaredEnhancedMethods());
                           methodsTemp.addAll(superClassMethods);
                        }
                     }

                     var28 = Reflections.getInterfaceClosure(javaClass).iterator();

                     while(true) {
                        if (!var28.hasNext()) {
                           break label149;
                        }

                        Class interfaceClazz = (Class)var28.next();
                        EnhancedAnnotatedType interfaceType = classTransformer.getEnhancedAnnotatedType(interfaceClazz, ((AnnotatedTypeIdentifier)this.slim.getIdentifier()).getBdaId());
                        var16 = interfaceType.getEnhancedMethods().iterator();

                        while(var16.hasNext()) {
                           EnhancedAnnotatedMethod interfaceMethod = (EnhancedAnnotatedMethod)var16.next();
                           if (Reflections.isDefault(interfaceMethod.getJavaMember())) {
                              methodsTemp.add(interfaceMethod);
                           }
                        }
                     }
                  }

                  method = (AnnotatedMethod)var28.next();
               } while(!method.getJavaMember().getDeclaringClass().equals(javaClass));

               weldMethod = EnhancedAnnotatedMethodImpl.of(method, this, classTransformer);
               declaredMethodsTemp.add(weldMethod);
               var16 = weldMethod.getAnnotations().iterator();

               while(var16.hasNext()) {
                  annotation = (Annotation)var16.next();
                  declaredAnnotatedMethods.put(annotation.annotationType(), weldMethod);
               }

               var16 = MAPPED_DECLARED_METHOD_PARAMETER_ANNOTATIONS.iterator();

               while(var16.hasNext()) {
                  annotationType = (Class)var16.next();
                  if (weldMethod.getEnhancedParameters(annotationType).size() > 0) {
                     declaredMethodsByAnnotatedParameters.put(annotationType, weldMethod);
                  }
               }
            }
         }

         this.declaredMethods = new HashSet(declaredMethodsTemp);
      } else {
         var28 = annotatedType.getMethods().iterator();

         while(var28.hasNext()) {
            method = (AnnotatedMethod)var28.next();
            weldMethod = EnhancedAnnotatedMethodImpl.of(method, this, classTransformer);
            methodsTemp.add(weldMethod);
            if (method.getJavaMember().getDeclaringClass().equals(javaClass)) {
               declaredMethodsTemp.add(weldMethod);
            }

            var16 = weldMethod.getAnnotations().iterator();

            while(var16.hasNext()) {
               annotation = (Annotation)var16.next();
               if (method.getJavaMember().getDeclaringClass().equals(javaClass)) {
                  declaredAnnotatedMethods.put(annotation.annotationType(), weldMethod);
               }
            }

            var16 = MAPPED_DECLARED_METHOD_PARAMETER_ANNOTATIONS.iterator();

            while(var16.hasNext()) {
               annotationType = (Class)var16.next();
               if (weldMethod.getEnhancedParameters(annotationType).size() > 0 && method.getJavaMember().getDeclaringClass().equals(javaClass)) {
                  declaredMethodsByAnnotatedParameters.put(annotationType, weldMethod);
               }
            }
         }

         this.declaredMethods = ImmutableSet.copyOf(declaredMethodsTemp);
      }

      this.declaredAnnotatedMethods = Multimaps.unmodifiableMultimap(declaredAnnotatedMethods);
      this.declaredMethodsByAnnotatedParameters = Multimaps.unmodifiableMultimap(declaredMethodsByAnnotatedParameters);
      SetMultimap declaredMetaAnnotationMap = SetMultimap.newSetMultimap();
      this.processMetaAnnotations(declaredMetaAnnotationMap, declaredAnnotationMap.values(), classTransformer, true);
      this.declaredMetaAnnotationMap = Multimaps.unmodifiableMultimap(declaredMetaAnnotationMap);
      this.overriddenMethods = this.getOverriddenMethods(this, methodsTemp);
      methodsTemp.removeAll(this.getOverriddenMethods(this, methodsTemp, true));
      this.methods = methodsTemp;
      this.annotatedMethods = this.buildAnnotatedMethodMultimap(this.methods);
      this.annotatedMethodsByAnnotatedParameters = this.buildAnnotatedParameterMethodMultimap(this.methods);
   }

   protected Set getOverriddenMethods(EnhancedAnnotatedType annotatedType, Set methods) {
      return this.getOverriddenMethods(annotatedType, methods, false);
   }

   protected Set getOverriddenMethods(EnhancedAnnotatedType annotatedType, Set methods, boolean skipOverridingBridgeMethods) {
      Set overriddenMethods = new HashSet();
      Multimap seenMethods = SetMultimap.newSetMultimap();
      Class clazz = annotatedType.getJavaClass();

      label40:
      while(clazz != null && clazz != Object.class) {
         Iterator var7 = methods.iterator();

         while(true) {
            EnhancedAnnotatedMethod method;
            do {
               do {
                  if (!var7.hasNext()) {
                     clazz = clazz.getSuperclass();
                     continue label40;
                  }

                  method = (EnhancedAnnotatedMethod)var7.next();
               } while(!method.getJavaMember().getDeclaringClass().equals(clazz));
            } while(skipOverridingBridgeMethods && method.getJavaMember().isBridge());

            if (isOverridden(method, seenMethods)) {
               overriddenMethods.add(method);
            }

            seenMethods.put(method.getSignature(), method.getPackage());
         }
      }

      return WeldCollections.immutableSetView(overriddenMethods);
   }

   protected Multimap buildAnnotatedMethodMultimap(Set effectiveMethods) {
      Multimap result = SetMultimap.newSetMultimap();
      Iterator var3 = effectiveMethods.iterator();

      while(var3.hasNext()) {
         EnhancedAnnotatedMethod method = (EnhancedAnnotatedMethod)var3.next();
         Iterator var5 = MAPPED_METHOD_ANNOTATIONS.iterator();

         while(var5.hasNext()) {
            Class annotation = (Class)var5.next();
            if (method.isAnnotationPresent(annotation)) {
               result.put(annotation, method);
            }
         }
      }

      return Multimaps.unmodifiableMultimap(result);
   }

   protected Multimap buildAnnotatedParameterMethodMultimap(Set effectiveMethods) {
      Multimap result = SetMultimap.newSetMultimap();
      Iterator var3 = effectiveMethods.iterator();

      while(var3.hasNext()) {
         EnhancedAnnotatedMethod method = (EnhancedAnnotatedMethod)var3.next();
         Iterator var5 = MAPPED_METHOD_PARAMETER_ANNOTATIONS.iterator();

         while(var5.hasNext()) {
            Class annotation = (Class)var5.next();
            if (!method.getEnhancedParameters(annotation).isEmpty()) {
               result.put(annotation, method);
            }
         }
      }

      return Multimaps.unmodifiableMultimap(result);
   }

   private static boolean isOverridden(EnhancedAnnotatedMethod method, Multimap seenMethods) {
      if (method.isPrivate()) {
         return false;
      } else {
         return method.isPackagePrivate() && seenMethods.containsKey(method.getSignature()) ? seenMethods.get(method.getSignature()).contains(method.getPackage()) : seenMethods.containsKey(method.getSignature());
      }
   }

   public Class getAnnotatedClass() {
      return this.getJavaClass();
   }

   public Class getDelegate() {
      return this.getJavaClass();
   }

   public Collection getEnhancedFields() {
      return Collections.unmodifiableCollection(this.fields);
   }

   public Collection getDeclaredEnhancedFields() {
      return Collections.unmodifiableCollection(this.declaredFields);
   }

   public EnhancedAnnotatedField getDeclaredEnhancedField(String fieldName) {
      Iterator var2 = this.declaredFields.iterator();

      EnhancedAnnotatedField field;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         field = (EnhancedAnnotatedField)var2.next();
      } while(!field.getName().equals(fieldName));

      return (EnhancedAnnotatedField)this.cast(field);
   }

   public Collection getDeclaredEnhancedFields(Class annotationType) {
      return this.declaredAnnotatedFields.get(annotationType);
   }

   public EnhancedAnnotatedConstructor getDeclaredEnhancedConstructor(ConstructorSignature signature) {
      return (EnhancedAnnotatedConstructor)this.cast(this.declaredConstructorsBySignature.get(signature));
   }

   public Collection getEnhancedFields(Class annotationType) {
      if (this.annotatedFields == null) {
         ArrayList aggregatedFields = new ArrayList(this.declaredAnnotatedFields.get(annotationType));
         if (this.superclass != null && this.superclass.getJavaClass() != Object.class) {
            aggregatedFields.addAll(this.superclass.getEnhancedFields(annotationType));
         }

         return Collections.unmodifiableCollection(aggregatedFields);
      } else {
         return this.annotatedFields.get(annotationType);
      }
   }

   public boolean isLocalClass() {
      return this.getJavaClass().isLocalClass();
   }

   public boolean isAnonymousClass() {
      return this.getJavaClass().isAnonymousClass();
   }

   public boolean isMemberClass() {
      return this.getJavaClass().isMemberClass();
   }

   public boolean isAbstract() {
      return Modifier.isAbstract(this.getJavaClass().getModifiers());
   }

   public boolean isEnum() {
      return this.getJavaClass().isEnum();
   }

   public boolean isSerializable() {
      return Reflections.isSerializable(this.getJavaClass());
   }

   public Collection getEnhancedMethods(Class annotationType) {
      return Collections.unmodifiableCollection(this.annotatedMethods.get(annotationType));
   }

   public Collection getDeclaredEnhancedMethods(Class annotationType) {
      return Collections.unmodifiableCollection(this.declaredAnnotatedMethods.get(annotationType));
   }

   public Collection getEnhancedConstructors() {
      return Collections.unmodifiableCollection(this.constructors);
   }

   public Collection getEnhancedConstructors(Class annotationType) {
      Set ret = new HashSet();
      Iterator var3 = this.constructors.iterator();

      while(var3.hasNext()) {
         EnhancedAnnotatedConstructor constructor = (EnhancedAnnotatedConstructor)var3.next();
         if (constructor.isAnnotationPresent(annotationType)) {
            ret.add(constructor);
         }
      }

      return ret;
   }

   public EnhancedAnnotatedConstructor getNoArgsEnhancedConstructor() {
      return (EnhancedAnnotatedConstructor)this.cast(this.declaredConstructorsBySignature.get(ConstructorSignatureImpl.NO_ARGS_SIGNATURE));
   }

   public Collection getDeclaredEnhancedMethodsWithAnnotatedParameters(Class annotationType) {
      return Collections.unmodifiableCollection(this.declaredMethodsByAnnotatedParameters.get(annotationType));
   }

   public Collection getEnhancedMethods() {
      return this.methods;
   }

   public Collection getDeclaredEnhancedMethods() {
      return Collections.unmodifiableSet(this.declaredMethods);
   }

   public EnhancedAnnotatedMethod getDeclaredEnhancedMethod(MethodSignature signature) {
      Iterator var2 = this.declaredMethods.iterator();

      EnhancedAnnotatedMethod method;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         method = (EnhancedAnnotatedMethod)var2.next();
      } while(!method.getSignature().equals(signature));

      return (EnhancedAnnotatedMethod)this.cast(method);
   }

   public EnhancedAnnotatedMethod getEnhancedMethod(MethodSignature signature) {
      EnhancedAnnotatedMethod method = (EnhancedAnnotatedMethod)this.cast(this.getDeclaredEnhancedMethod(signature));
      if (method == null && this.superclass != null && this.superclass.getJavaClass() != Object.class) {
         method = this.superclass.getEnhancedMethod(signature);
      }

      return method;
   }

   public String toString() {
      return Formats.formatAnnotatedType(this);
   }

   public String getSimpleName() {
      return this.getJavaClass().getSimpleName();
   }

   public boolean isStatic() {
      return Reflections.isStatic(this.getDelegate());
   }

   public boolean isFinal() {
      return Reflections.isFinal(this.getDelegate());
   }

   public boolean isPublic() {
      return Modifier.isFinal(this.getJavaClass().getModifiers());
   }

   public boolean isGeneric() {
      return this.getJavaClass().getTypeParameters().length > 0;
   }

   public String getName() {
      return this.getJavaClass().getName();
   }

   public EnhancedAnnotatedType getEnhancedSuperclass() {
      return this.superclass;
   }

   public boolean isEquivalent(Class clazz) {
      return this.getDelegate().equals(clazz);
   }

   public boolean isPrivate() {
      return Modifier.isPrivate(this.getJavaClass().getModifiers());
   }

   public boolean isPackagePrivate() {
      return Reflections.isPackagePrivate(this.getJavaClass().getModifiers());
   }

   public Package getPackage() {
      return this.getJavaClass().getPackage();
   }

   public EnhancedAnnotatedType asEnhancedSubclass(EnhancedAnnotatedType clazz) {
      return (EnhancedAnnotatedType)this.cast(this);
   }

   public Object cast(Object object) {
      return Reflections.cast(object);
   }

   public Set getConstructors() {
      return Collections.unmodifiableSet((Set)Reflections.cast(this.constructors));
   }

   public Set getFields() {
      return (Set)this.cast(this.fields);
   }

   public Set getMethods() {
      return (Set)this.cast(Sets.union(this.methods, this.overriddenMethods));
   }

   public Set getDeclaredMetaAnnotations(Class metaAnnotationType) {
      return this.declaredMetaAnnotationMap.containsKey(metaAnnotationType) ? ImmutableSet.copyOf(this.declaredMetaAnnotationMap.get(metaAnnotationType)) : Collections.emptySet();
   }

   public boolean isDiscovered() {
      return this.discovered;
   }

   public SlimAnnotatedType slim() {
      return this.slim;
   }

   public Collection getEnhancedMethodsWithAnnotatedParameters(Class annotationType) {
      return this.annotatedMethodsByAnnotatedParameters.get(annotationType);
   }

   public int hashCode() {
      return this.slim.hashCode();
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this == obj) {
         return true;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         EnhancedAnnotatedTypeImpl that = (EnhancedAnnotatedTypeImpl)this.cast(obj);
         return this.slim.equals(that.slim);
      }
   }

   static {
      Set annotations = new HashSet();
      Iterator var1 = InterceptionTypeRegistry.getSupportedInterceptionTypes().iterator();

      while(var1.hasNext()) {
         InterceptionType interceptionType = (InterceptionType)var1.next();
         annotations.add(InterceptionTypeRegistry.getAnnotationClass(interceptionType));
      }

      annotations.add(Inject.class);
      MAPPED_METHOD_ANNOTATIONS = ImmutableSet.copyOf(annotations);
      MAPPED_METHOD_PARAMETER_ANNOTATIONS = ImmutableSet.of(Observes.class, ObservesAsync.class);
      MAPPED_DECLARED_METHOD_PARAMETER_ANNOTATIONS = ImmutableSet.of(Disposes.class);
   }
}
