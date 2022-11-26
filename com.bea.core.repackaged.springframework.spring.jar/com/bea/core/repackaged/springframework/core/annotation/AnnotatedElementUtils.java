package com.bea.core.repackaged.springframework.core.annotation;

import com.bea.core.repackaged.springframework.core.BridgeMethodResolver;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import com.bea.core.repackaged.springframework.util.LinkedMultiValueMap;
import com.bea.core.repackaged.springframework.util.MultiValueMap;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class AnnotatedElementUtils {
   @Nullable
   private static final Boolean CONTINUE = null;
   private static final Annotation[] EMPTY_ANNOTATION_ARRAY = new Annotation[0];
   private static final Processor alwaysTrueAnnotationProcessor = new AlwaysTrueBooleanAnnotationProcessor();

   public static AnnotatedElement forAnnotations(final Annotation... annotations) {
      return new AnnotatedElement() {
         @Nullable
         public Annotation getAnnotation(Class annotationClass) {
            Annotation[] var2 = annotations;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               Annotation ann = var2[var4];
               if (ann.annotationType() == annotationClass) {
                  return ann;
               }
            }

            return null;
         }

         public Annotation[] getAnnotations() {
            return annotations;
         }

         public Annotation[] getDeclaredAnnotations() {
            return annotations;
         }
      };
   }

   public static Set getMetaAnnotationTypes(AnnotatedElement element, Class annotationType) {
      return getMetaAnnotationTypes(element, element.getAnnotation(annotationType));
   }

   public static Set getMetaAnnotationTypes(AnnotatedElement element, String annotationName) {
      return getMetaAnnotationTypes(element, AnnotationUtils.getAnnotation(element, annotationName));
   }

   private static Set getMetaAnnotationTypes(AnnotatedElement element, @Nullable Annotation composed) {
      if (composed == null) {
         return Collections.emptySet();
      } else {
         try {
            final Set types = new LinkedHashSet();
            searchWithGetSemantics(composed.annotationType(), Collections.emptySet(), (String)null, (Class)null, new SimpleAnnotationProcessor(true) {
               @Nullable
               public Object process(@Nullable AnnotatedElement annotatedElement, Annotation annotation, int metaDepth) {
                  types.add(annotation.annotationType().getName());
                  return AnnotatedElementUtils.CONTINUE;
               }
            }, new HashSet(), 1);
            return types;
         } catch (Throwable var3) {
            AnnotationUtils.rethrowAnnotationConfigurationException(var3);
            throw new IllegalStateException("Failed to introspect annotations on " + element, var3);
         }
      }
   }

   public static boolean hasMetaAnnotationTypes(AnnotatedElement element, Class annotationType) {
      return hasMetaAnnotationTypes(element, annotationType, (String)null);
   }

   public static boolean hasMetaAnnotationTypes(AnnotatedElement element, String annotationName) {
      return hasMetaAnnotationTypes(element, (Class)null, annotationName);
   }

   private static boolean hasMetaAnnotationTypes(AnnotatedElement element, @Nullable Class annotationType, @Nullable String annotationName) {
      return Boolean.TRUE.equals(searchWithGetSemantics(element, annotationType, annotationName, new SimpleAnnotationProcessor() {
         @Nullable
         public Boolean process(@Nullable AnnotatedElement annotatedElement, Annotation annotation, int metaDepth) {
            return metaDepth > 0 ? Boolean.TRUE : AnnotatedElementUtils.CONTINUE;
         }
      }));
   }

   public static boolean isAnnotated(AnnotatedElement element, Class annotationType) {
      return element.isAnnotationPresent(annotationType) ? true : Boolean.TRUE.equals(searchWithGetSemantics(element, annotationType, (String)null, alwaysTrueAnnotationProcessor));
   }

   public static boolean isAnnotated(AnnotatedElement element, String annotationName) {
      return Boolean.TRUE.equals(searchWithGetSemantics(element, (Class)null, annotationName, alwaysTrueAnnotationProcessor));
   }

   @Nullable
   public static AnnotationAttributes getMergedAnnotationAttributes(AnnotatedElement element, Class annotationType) {
      AnnotationAttributes attributes = (AnnotationAttributes)searchWithGetSemantics(element, annotationType, (String)null, new MergedAnnotationAttributesProcessor());
      AnnotationUtils.postProcessAnnotationAttributes(element, attributes, false, false);
      return attributes;
   }

   @Nullable
   public static AnnotationAttributes getMergedAnnotationAttributes(AnnotatedElement element, String annotationName) {
      return getMergedAnnotationAttributes(element, annotationName, false, false);
   }

   @Nullable
   public static AnnotationAttributes getMergedAnnotationAttributes(AnnotatedElement element, String annotationName, boolean classValuesAsString, boolean nestedAnnotationsAsMap) {
      AnnotationAttributes attributes = (AnnotationAttributes)searchWithGetSemantics(element, (Class)null, annotationName, new MergedAnnotationAttributesProcessor(classValuesAsString, nestedAnnotationsAsMap));
      AnnotationUtils.postProcessAnnotationAttributes(element, attributes, classValuesAsString, nestedAnnotationsAsMap);
      return attributes;
   }

   @Nullable
   public static Annotation getMergedAnnotation(AnnotatedElement element, Class annotationType) {
      Annotation annotation = element.getDeclaredAnnotation(annotationType);
      if (annotation != null) {
         return AnnotationUtils.synthesizeAnnotation(annotation, element);
      } else if (AnnotationUtils.hasPlainJavaAnnotationsOnly(element)) {
         return null;
      } else {
         AnnotationAttributes attributes = getMergedAnnotationAttributes(element, annotationType);
         return attributes != null ? AnnotationUtils.synthesizeAnnotation(attributes, annotationType, element) : null;
      }
   }

   public static Set getAllMergedAnnotations(AnnotatedElement element, Class annotationType) {
      MergedAnnotationAttributesProcessor processor = new MergedAnnotationAttributesProcessor(false, false, true);
      searchWithGetSemantics(element, annotationType, (String)null, processor);
      return postProcessAndSynthesizeAggregatedResults(element, processor.getAggregatedResults());
   }

   public static Set getAllMergedAnnotations(AnnotatedElement element, Set annotationTypes) {
      MergedAnnotationAttributesProcessor processor = new MergedAnnotationAttributesProcessor(false, false, true);
      searchWithGetSemantics(element, annotationTypes, (String)null, (Class)null, processor);
      return postProcessAndSynthesizeAggregatedResults(element, processor.getAggregatedResults());
   }

   public static Set getMergedRepeatableAnnotations(AnnotatedElement element, Class annotationType) {
      return getMergedRepeatableAnnotations(element, annotationType, (Class)null);
   }

   public static Set getMergedRepeatableAnnotations(AnnotatedElement element, Class annotationType, @Nullable Class containerType) {
      if (containerType == null) {
         containerType = resolveContainerType(annotationType);
      } else {
         validateContainerType(annotationType, containerType);
      }

      MergedAnnotationAttributesProcessor processor = new MergedAnnotationAttributesProcessor(false, false, true);
      searchWithGetSemantics(element, Collections.singleton(annotationType), (String)null, containerType, processor);
      return postProcessAndSynthesizeAggregatedResults(element, processor.getAggregatedResults());
   }

   @Nullable
   public static MultiValueMap getAllAnnotationAttributes(AnnotatedElement element, String annotationName) {
      return getAllAnnotationAttributes(element, annotationName, false, false);
   }

   @Nullable
   public static MultiValueMap getAllAnnotationAttributes(AnnotatedElement element, String annotationName, final boolean classValuesAsString, final boolean nestedAnnotationsAsMap) {
      final MultiValueMap attributesMap = new LinkedMultiValueMap();
      searchWithGetSemantics(element, (Class)null, annotationName, new SimpleAnnotationProcessor() {
         @Nullable
         public Object process(@Nullable AnnotatedElement annotatedElement, Annotation annotation, int metaDepth) {
            AnnotationAttributes annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation, classValuesAsString, nestedAnnotationsAsMap);
            MultiValueMap var10001 = attributesMap;
            annotationAttributes.forEach(var10001::add);
            return AnnotatedElementUtils.CONTINUE;
         }
      });
      return !attributesMap.isEmpty() ? attributesMap : null;
   }

   public static boolean hasAnnotation(AnnotatedElement element, Class annotationType) {
      return element.isAnnotationPresent(annotationType) ? true : Boolean.TRUE.equals(searchWithFindSemantics(element, annotationType, (String)null, alwaysTrueAnnotationProcessor));
   }

   @Nullable
   public static AnnotationAttributes findMergedAnnotationAttributes(AnnotatedElement element, Class annotationType, boolean classValuesAsString, boolean nestedAnnotationsAsMap) {
      AnnotationAttributes attributes = (AnnotationAttributes)searchWithFindSemantics(element, annotationType, (String)null, new MergedAnnotationAttributesProcessor(classValuesAsString, nestedAnnotationsAsMap));
      AnnotationUtils.postProcessAnnotationAttributes(element, attributes, classValuesAsString, nestedAnnotationsAsMap);
      return attributes;
   }

   @Nullable
   public static AnnotationAttributes findMergedAnnotationAttributes(AnnotatedElement element, String annotationName, boolean classValuesAsString, boolean nestedAnnotationsAsMap) {
      AnnotationAttributes attributes = (AnnotationAttributes)searchWithFindSemantics(element, (Class)null, annotationName, new MergedAnnotationAttributesProcessor(classValuesAsString, nestedAnnotationsAsMap));
      AnnotationUtils.postProcessAnnotationAttributes(element, attributes, classValuesAsString, nestedAnnotationsAsMap);
      return attributes;
   }

   @Nullable
   public static Annotation findMergedAnnotation(AnnotatedElement element, Class annotationType) {
      Annotation annotation = element.getDeclaredAnnotation(annotationType);
      if (annotation != null) {
         return AnnotationUtils.synthesizeAnnotation(annotation, element);
      } else if (AnnotationUtils.hasPlainJavaAnnotationsOnly(element)) {
         return null;
      } else {
         AnnotationAttributes attributes = findMergedAnnotationAttributes(element, annotationType, false, false);
         return attributes != null ? AnnotationUtils.synthesizeAnnotation(attributes, annotationType, element) : null;
      }
   }

   public static Set findAllMergedAnnotations(AnnotatedElement element, Class annotationType) {
      MergedAnnotationAttributesProcessor processor = new MergedAnnotationAttributesProcessor(false, false, true);
      searchWithFindSemantics(element, annotationType, (String)null, processor);
      return postProcessAndSynthesizeAggregatedResults(element, processor.getAggregatedResults());
   }

   public static Set findAllMergedAnnotations(AnnotatedElement element, Set annotationTypes) {
      MergedAnnotationAttributesProcessor processor = new MergedAnnotationAttributesProcessor(false, false, true);
      searchWithFindSemantics(element, annotationTypes, (String)null, (Class)null, processor);
      return postProcessAndSynthesizeAggregatedResults(element, processor.getAggregatedResults());
   }

   public static Set findMergedRepeatableAnnotations(AnnotatedElement element, Class annotationType) {
      return findMergedRepeatableAnnotations(element, annotationType, (Class)null);
   }

   public static Set findMergedRepeatableAnnotations(AnnotatedElement element, Class annotationType, @Nullable Class containerType) {
      if (containerType == null) {
         containerType = resolveContainerType(annotationType);
      } else {
         validateContainerType(annotationType, containerType);
      }

      MergedAnnotationAttributesProcessor processor = new MergedAnnotationAttributesProcessor(false, false, true);
      searchWithFindSemantics(element, Collections.singleton(annotationType), (String)null, containerType, processor);
      return postProcessAndSynthesizeAggregatedResults(element, processor.getAggregatedResults());
   }

   @Nullable
   private static Object searchWithGetSemantics(AnnotatedElement element, @Nullable Class annotationType, @Nullable String annotationName, Processor processor) {
      return searchWithGetSemantics(element, annotationType != null ? Collections.singleton(annotationType) : Collections.emptySet(), annotationName, (Class)null, processor);
   }

   @Nullable
   private static Object searchWithGetSemantics(AnnotatedElement element, Set annotationTypes, @Nullable String annotationName, @Nullable Class containerType, Processor processor) {
      try {
         return searchWithGetSemantics(element, annotationTypes, annotationName, containerType, processor, new HashSet(), 0);
      } catch (Throwable var6) {
         AnnotationUtils.rethrowAnnotationConfigurationException(var6);
         throw new IllegalStateException("Failed to introspect annotations on " + element, var6);
      }
   }

   @Nullable
   private static Object searchWithGetSemantics(AnnotatedElement element, Set annotationTypes, @Nullable String annotationName, @Nullable Class containerType, Processor processor, Set visited, int metaDepth) {
      if (visited.add(element)) {
         try {
            List declaredAnnotations = Arrays.asList(AnnotationUtils.getDeclaredAnnotations(element));
            Object result = searchWithGetSemanticsInAnnotations(element, declaredAnnotations, annotationTypes, annotationName, containerType, processor, visited, metaDepth);
            if (result != null) {
               return result;
            }

            if (element instanceof Class) {
               Class superclass = ((Class)element).getSuperclass();
               if (superclass != null && superclass != Object.class) {
                  List inheritedAnnotations = new LinkedList();
                  Annotation[] var11 = element.getAnnotations();
                  int var12 = var11.length;

                  for(int var13 = 0; var13 < var12; ++var13) {
                     Annotation annotation = var11[var13];
                     if (!declaredAnnotations.contains(annotation)) {
                        inheritedAnnotations.add(annotation);
                     }
                  }

                  result = searchWithGetSemanticsInAnnotations(element, inheritedAnnotations, annotationTypes, annotationName, containerType, processor, visited, metaDepth);
                  if (result != null) {
                     return result;
                  }
               }
            }
         } catch (Throwable var15) {
            AnnotationUtils.handleIntrospectionFailure(element, var15);
         }
      }

      return null;
   }

   @Nullable
   private static Object searchWithGetSemanticsInAnnotations(@Nullable AnnotatedElement element, List annotations, Set annotationTypes, @Nullable String annotationName, @Nullable Class containerType, Processor processor, Set visited, int metaDepth) {
      Iterator var8 = annotations.iterator();

      while(true) {
         Object result;
         do {
            while(true) {
               Annotation annotation;
               Class currentAnnotationType;
               do {
                  if (!var8.hasNext()) {
                     var8 = annotations.iterator();

                     while(true) {
                        do {
                           do {
                              if (!var8.hasNext()) {
                                 return null;
                              }

                              annotation = (Annotation)var8.next();
                              currentAnnotationType = annotation.annotationType();
                           } while(AnnotationUtils.hasPlainJavaAnnotationsOnly(currentAnnotationType));

                           result = searchWithGetSemantics(currentAnnotationType, annotationTypes, annotationName, containerType, processor, visited, metaDepth + 1);
                        } while(result == null);

                        processor.postProcess(element, annotation, result);
                        if (!processor.aggregates() || metaDepth != 0) {
                           return result;
                        }

                        processor.getAggregatedResults().add(result);
                     }
                  }

                  annotation = (Annotation)var8.next();
                  currentAnnotationType = annotation.annotationType();
               } while(AnnotationUtils.isInJavaLangAnnotationPackage(currentAnnotationType));

               if (annotationTypes.contains(currentAnnotationType) || currentAnnotationType.getName().equals(annotationName) || processor.alwaysProcesses()) {
                  result = processor.process(element, annotation, metaDepth);
                  break;
               }

               if (currentAnnotationType == containerType) {
                  Annotation[] var11 = getRawAnnotationsFromContainer(element, annotation);
                  int var12 = var11.length;

                  for(int var13 = 0; var13 < var12; ++var13) {
                     Annotation contained = var11[var13];
                     Object result = processor.process(element, contained, metaDepth);
                     if (result != null) {
                        processor.getAggregatedResults().add(result);
                     }
                  }
               }
            }
         } while(result == null);

         if (!processor.aggregates() || metaDepth != 0) {
            return result;
         }

         processor.getAggregatedResults().add(result);
      }
   }

   @Nullable
   private static Object searchWithFindSemantics(AnnotatedElement element, @Nullable Class annotationType, @Nullable String annotationName, Processor processor) {
      return searchWithFindSemantics(element, annotationType != null ? Collections.singleton(annotationType) : Collections.emptySet(), annotationName, (Class)null, processor);
   }

   @Nullable
   private static Object searchWithFindSemantics(AnnotatedElement element, Set annotationTypes, @Nullable String annotationName, @Nullable Class containerType, Processor processor) {
      if (containerType != null && !processor.aggregates()) {
         throw new IllegalArgumentException("Searches for repeatable annotations must supply an aggregating Processor");
      } else {
         try {
            return searchWithFindSemantics(element, annotationTypes, annotationName, containerType, processor, new HashSet(), 0);
         } catch (Throwable var6) {
            AnnotationUtils.rethrowAnnotationConfigurationException(var6);
            throw new IllegalStateException("Failed to introspect annotations on " + element, var6);
         }
      }
   }

   @Nullable
   private static Object searchWithFindSemantics(AnnotatedElement element, Set annotationTypes, @Nullable String annotationName, @Nullable Class containerType, Processor processor, Set visited, int metaDepth) {
      if (visited.add(element)) {
         try {
            Annotation[] annotations = AnnotationUtils.getDeclaredAnnotations(element);
            int var10;
            int var11;
            if (annotations.length > 0) {
               List aggregatedResults = processor.aggregates() ? new ArrayList() : null;
               Annotation[] var9 = annotations;
               var10 = annotations.length;
               var11 = 0;

               while(true) {
                  Annotation annotation;
                  Class currentAnnotationType;
                  Object result;
                  if (var11 >= var10) {
                     var9 = annotations;
                     var10 = annotations.length;

                     for(var11 = 0; var11 < var10; ++var11) {
                        annotation = var9[var11];
                        currentAnnotationType = annotation.annotationType();
                        if (!AnnotationUtils.hasPlainJavaAnnotationsOnly(currentAnnotationType)) {
                           result = searchWithFindSemantics(currentAnnotationType, annotationTypes, annotationName, containerType, processor, visited, metaDepth + 1);
                           if (result != null) {
                              processor.postProcess(currentAnnotationType, annotation, result);
                              if (aggregatedResults == null || metaDepth != 0) {
                                 return result;
                              }

                              aggregatedResults.add(result);
                           }
                        }
                     }

                     if (!CollectionUtils.isEmpty((Collection)aggregatedResults)) {
                        processor.getAggregatedResults().addAll(0, aggregatedResults);
                     }
                     break;
                  }

                  annotation = var9[var11];
                  currentAnnotationType = annotation.annotationType();
                  if (!AnnotationUtils.isInJavaLangAnnotationPackage(currentAnnotationType)) {
                     if (!annotationTypes.contains(currentAnnotationType) && !currentAnnotationType.getName().equals(annotationName) && !processor.alwaysProcesses()) {
                        if (currentAnnotationType == containerType) {
                           Annotation[] var31 = getRawAnnotationsFromContainer(element, annotation);
                           int var15 = var31.length;

                           for(int var16 = 0; var16 < var15; ++var16) {
                              Annotation contained = var31[var16];
                              Object result = processor.process(element, contained, metaDepth);
                              if (aggregatedResults != null && result != null) {
                                 aggregatedResults.add(result);
                              }
                           }
                        }
                     } else {
                        result = processor.process(element, annotation, metaDepth);
                        if (result != null) {
                           if (aggregatedResults == null || metaDepth != 0) {
                              return result;
                           }

                           aggregatedResults.add(result);
                        }
                     }
                  }

                  ++var11;
               }
            }

            Class clazz;
            if (element instanceof Method) {
               Method method = (Method)element;
               Method resolvedMethod = BridgeMethodResolver.findBridgedMethod(method);
               Object result;
               if (resolvedMethod != method) {
                  result = searchWithFindSemantics(resolvedMethod, annotationTypes, annotationName, containerType, processor, visited, metaDepth);
                  if (result != null) {
                     return result;
                  }
               }

               Class[] ifcs = method.getDeclaringClass().getInterfaces();
               if (ifcs.length > 0) {
                  result = searchOnInterfaces(method, annotationTypes, annotationName, containerType, processor, visited, metaDepth, ifcs);
                  if (result != null) {
                     return result;
                  }
               }

               clazz = method.getDeclaringClass();

               do {
                  clazz = clazz.getSuperclass();
                  if (clazz == null || clazz == Object.class) {
                     return null;
                  }

                  Set annotatedMethods = AnnotationUtils.getAnnotatedMethodsInBaseType(clazz);
                  if (!annotatedMethods.isEmpty()) {
                     Iterator var32 = annotatedMethods.iterator();

                     while(var32.hasNext()) {
                        Method annotatedMethod = (Method)var32.next();
                        if (AnnotationUtils.isOverride(method, annotatedMethod)) {
                           Method resolvedSuperMethod = BridgeMethodResolver.findBridgedMethod(annotatedMethod);
                           result = searchWithFindSemantics(resolvedSuperMethod, annotationTypes, annotationName, containerType, processor, visited, metaDepth);
                           if (result != null) {
                              return result;
                           }
                        }
                     }
                  }

                  result = searchOnInterfaces(method, annotationTypes, annotationName, containerType, processor, visited, metaDepth, clazz.getInterfaces());
               } while(result == null);

               return result;
            } else if (element instanceof Class) {
               Class clazz = (Class)element;
               if (!Annotation.class.isAssignableFrom(clazz)) {
                  Class[] var24 = clazz.getInterfaces();
                  var10 = var24.length;

                  for(var11 = 0; var11 < var10; ++var11) {
                     clazz = var24[var11];
                     Object result = searchWithFindSemantics(clazz, annotationTypes, annotationName, containerType, processor, visited, metaDepth);
                     if (result != null) {
                        return result;
                     }
                  }

                  Class superclass = clazz.getSuperclass();
                  if (superclass != null && superclass != Object.class) {
                     Object result = searchWithFindSemantics(superclass, annotationTypes, annotationName, containerType, processor, visited, metaDepth);
                     if (result != null) {
                        return result;
                     }
                  }
               }
            }
         } catch (Throwable var19) {
            AnnotationUtils.handleIntrospectionFailure(element, var19);
         }
      }

      return null;
   }

   @Nullable
   private static Object searchOnInterfaces(Method method, Set annotationTypes, @Nullable String annotationName, @Nullable Class containerType, Processor processor, Set visited, int metaDepth, Class[] ifcs) {
      Class[] var8 = ifcs;
      int var9 = ifcs.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         Class ifc = var8[var10];
         Set annotatedMethods = AnnotationUtils.getAnnotatedMethodsInBaseType(ifc);
         if (!annotatedMethods.isEmpty()) {
            Iterator var13 = annotatedMethods.iterator();

            while(var13.hasNext()) {
               Method annotatedMethod = (Method)var13.next();
               if (AnnotationUtils.isOverride(method, annotatedMethod)) {
                  Object result = searchWithFindSemantics(annotatedMethod, annotationTypes, annotationName, containerType, processor, visited, metaDepth);
                  if (result != null) {
                     return result;
                  }
               }
            }
         }
      }

      return null;
   }

   private static Annotation[] getRawAnnotationsFromContainer(@Nullable AnnotatedElement element, Annotation container) {
      try {
         Annotation[] value = (Annotation[])((Annotation[])AnnotationUtils.getValue(container));
         if (value != null) {
            return value;
         }
      } catch (Throwable var3) {
         AnnotationUtils.handleIntrospectionFailure(element, var3);
      }

      return (Annotation[])EMPTY_ANNOTATION_ARRAY;
   }

   private static Class resolveContainerType(Class annotationType) {
      Class containerType = AnnotationUtils.resolveContainerAnnotationType(annotationType);
      if (containerType == null) {
         throw new IllegalArgumentException("Annotation type must be a repeatable annotation: failed to resolve container type for " + annotationType.getName());
      } else {
         return containerType;
      }
   }

   private static void validateContainerType(Class annotationType, Class containerType) {
      try {
         Method method = containerType.getDeclaredMethod("value");
         Class returnType = method.getReturnType();
         if (!returnType.isArray() || returnType.getComponentType() != annotationType) {
            String msg = String.format("Container type [%s] must declare a 'value' attribute for an array of type [%s]", containerType.getName(), annotationType.getName());
            throw new AnnotationConfigurationException(msg);
         }
      } catch (Throwable var5) {
         AnnotationUtils.rethrowAnnotationConfigurationException(var5);
         String msg = String.format("Invalid declaration of container type [%s] for repeatable annotation [%s]", containerType.getName(), annotationType.getName());
         throw new AnnotationConfigurationException(msg, var5);
      }
   }

   private static Set postProcessAndSynthesizeAggregatedResults(AnnotatedElement element, List aggregatedResults) {
      Set annotations = new LinkedHashSet();
      Iterator var3 = aggregatedResults.iterator();

      while(var3.hasNext()) {
         AnnotationAttributes attributes = (AnnotationAttributes)var3.next();
         AnnotationUtils.postProcessAnnotationAttributes(element, attributes, false, false);
         Class annType = attributes.annotationType();
         if (annType != null) {
            annotations.add(AnnotationUtils.synthesizeAnnotation(attributes, annType, element));
         }
      }

      return annotations;
   }

   private static class MergedAnnotationAttributesProcessor implements Processor {
      private final boolean classValuesAsString;
      private final boolean nestedAnnotationsAsMap;
      private final boolean aggregates;
      private final List aggregatedResults;

      MergedAnnotationAttributesProcessor() {
         this(false, false, false);
      }

      MergedAnnotationAttributesProcessor(boolean classValuesAsString, boolean nestedAnnotationsAsMap) {
         this(classValuesAsString, nestedAnnotationsAsMap, false);
      }

      MergedAnnotationAttributesProcessor(boolean classValuesAsString, boolean nestedAnnotationsAsMap, boolean aggregates) {
         this.classValuesAsString = classValuesAsString;
         this.nestedAnnotationsAsMap = nestedAnnotationsAsMap;
         this.aggregates = aggregates;
         this.aggregatedResults = (List)(aggregates ? new ArrayList() : Collections.emptyList());
      }

      public boolean alwaysProcesses() {
         return false;
      }

      public boolean aggregates() {
         return this.aggregates;
      }

      public List getAggregatedResults() {
         return this.aggregatedResults;
      }

      @Nullable
      public AnnotationAttributes process(@Nullable AnnotatedElement annotatedElement, Annotation annotation, int metaDepth) {
         return AnnotationUtils.retrieveAnnotationAttributes(annotatedElement, annotation, this.classValuesAsString, this.nestedAnnotationsAsMap);
      }

      public void postProcess(@Nullable AnnotatedElement element, Annotation annotation, AnnotationAttributes attributes) {
         annotation = AnnotationUtils.synthesizeAnnotation(annotation, element);
         Class targetAnnotationType = attributes.annotationType();
         Set valuesAlreadyReplaced = new HashSet();
         Iterator var6 = AnnotationUtils.getAttributeMethods(annotation.annotationType()).iterator();

         while(true) {
            String attributeName;
            String attributeOverrideName;
            label33:
            do {
               while(var6.hasNext()) {
                  Method attributeMethod = (Method)var6.next();
                  attributeName = attributeMethod.getName();
                  attributeOverrideName = AnnotationUtils.getAttributeOverrideName(attributeMethod, targetAnnotationType);
                  if (attributeOverrideName != null) {
                     continue label33;
                  }

                  if (!"value".equals(attributeName) && attributes.containsKey(attributeName)) {
                     this.overrideAttribute(element, annotation, attributes, attributeName, attributeName);
                  }
               }

               return;
            } while(valuesAlreadyReplaced.contains(attributeOverrideName));

            List targetAttributeNames = new ArrayList();
            targetAttributeNames.add(attributeOverrideName);
            valuesAlreadyReplaced.add(attributeOverrideName);
            List aliases = (List)AnnotationUtils.getAttributeAliasMap(targetAnnotationType).get(attributeOverrideName);
            if (aliases != null) {
               Iterator var12 = aliases.iterator();

               while(var12.hasNext()) {
                  String alias = (String)var12.next();
                  if (!valuesAlreadyReplaced.contains(alias)) {
                     targetAttributeNames.add(alias);
                     valuesAlreadyReplaced.add(alias);
                  }
               }
            }

            this.overrideAttributes(element, annotation, attributes, attributeName, targetAttributeNames);
         }
      }

      private void overrideAttributes(@Nullable AnnotatedElement element, Annotation annotation, AnnotationAttributes attributes, String sourceAttributeName, List targetAttributeNames) {
         Object adaptedValue = this.getAdaptedValue(element, annotation, sourceAttributeName);
         Iterator var7 = targetAttributeNames.iterator();

         while(var7.hasNext()) {
            String targetAttributeName = (String)var7.next();
            attributes.put(targetAttributeName, adaptedValue);
         }

      }

      private void overrideAttribute(@Nullable AnnotatedElement element, Annotation annotation, AnnotationAttributes attributes, String sourceAttributeName, String targetAttributeName) {
         attributes.put(targetAttributeName, this.getAdaptedValue(element, annotation, sourceAttributeName));
      }

      @Nullable
      private Object getAdaptedValue(@Nullable AnnotatedElement element, Annotation annotation, String sourceAttributeName) {
         Object value = AnnotationUtils.getValue(annotation, sourceAttributeName);
         return AnnotationUtils.adaptValue(element, value, this.classValuesAsString, this.nestedAnnotationsAsMap);
      }
   }

   static class AlwaysTrueBooleanAnnotationProcessor extends SimpleAnnotationProcessor {
      public final Boolean process(@Nullable AnnotatedElement annotatedElement, Annotation annotation, int metaDepth) {
         return Boolean.TRUE;
      }
   }

   private abstract static class SimpleAnnotationProcessor implements Processor {
      private final boolean alwaysProcesses;

      public SimpleAnnotationProcessor() {
         this(false);
      }

      public SimpleAnnotationProcessor(boolean alwaysProcesses) {
         this.alwaysProcesses = alwaysProcesses;
      }

      public final boolean alwaysProcesses() {
         return this.alwaysProcesses;
      }

      public final void postProcess(@Nullable AnnotatedElement annotatedElement, Annotation annotation, Object result) {
      }

      public final boolean aggregates() {
         return false;
      }

      public final List getAggregatedResults() {
         throw new UnsupportedOperationException("SimpleAnnotationProcessor does not support aggregated results");
      }
   }

   private interface Processor {
      @Nullable
      Object process(@Nullable AnnotatedElement var1, Annotation var2, int var3);

      void postProcess(@Nullable AnnotatedElement var1, Annotation var2, Object var3);

      boolean alwaysProcesses();

      boolean aggregates();

      List getAggregatedResults();
   }
}
