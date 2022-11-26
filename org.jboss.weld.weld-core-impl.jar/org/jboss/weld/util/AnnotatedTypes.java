package org.jboss.weld.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.decorator.Decorator;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.NormalScope;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Stereotype;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.AnnotatedCallable;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMember;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.inject.Scope;
import javax.interceptor.Interceptor;
import org.jboss.weld.logging.MetadataLogger;
import org.jboss.weld.security.GetDeclaredMethodsAction;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Reflections;

public class AnnotatedTypes {
   private static Set BEAN_DEFINING_ANNOTATIONS = ImmutableSet.of(Dependent.class, RequestScoped.class, ConversationScoped.class, SessionScoped.class, ApplicationScoped.class, Interceptor.class, Decorator.class, Model.class);
   private static Set META_ANNOTATIONS = ImmutableSet.of(Stereotype.class, NormalScope.class);
   public static final Set TRIM_META_ANNOTATIONS = ImmutableSet.of(Stereotype.class, NormalScope.class, Scope.class);
   private static final char SEPARATOR = ';';

   public static String createTypeId(AnnotatedType annotatedType) {
      String id = createTypeId(annotatedType.getJavaClass(), annotatedType.getAnnotations(), annotatedType.getMethods(), annotatedType.getFields(), annotatedType.getConstructors());
      String hash = hash(id);
      MetadataLogger.LOG.tracef("Generated AnnotatedType id hash for %s: %s", id, hash);
      return hash;
   }

   public static String hash(String id) {
      try {
         MessageDigest md = MessageDigest.getInstance("SHA-256");
         return Base64.getEncoder().encodeToString(md.digest(id.getBytes("UTF-8")));
      } catch (NoSuchAlgorithmException var2) {
         throw new IllegalStateException("SHA-256 not supported", var2);
      } catch (UnsupportedEncodingException var3) {
         throw new IllegalStateException(var3);
      }
   }

   private static String createTypeId(Class clazz, Collection annotations, Collection methods, Collection fields, Collection constructors) {
      StringBuilder builder = new StringBuilder();
      builder.append(clazz.getName());
      builder.append(createAnnotationCollectionId(annotations));
      builder.append("{");
      List sortedFields = new ArrayList();
      sortedFields.addAll(fields);
      Collections.sort(sortedFields, AnnotatedTypes.AnnotatedFieldComparator.instance());
      Iterator var7 = sortedFields.iterator();

      while(var7.hasNext()) {
         AnnotatedField field = (AnnotatedField)var7.next();
         if (!field.getAnnotations().isEmpty()) {
            builder.append(createFieldId(field));
            builder.append(';');
         }
      }

      List sortedMethods = new ArrayList();
      sortedMethods.addAll(methods);
      Collections.sort(sortedMethods, AnnotatedTypes.AnnotatedMethodComparator.instance());
      Iterator var12 = sortedMethods.iterator();

      while(true) {
         AnnotatedMethod method;
         do {
            if (!var12.hasNext()) {
               List sortedConstructors = new ArrayList();
               sortedConstructors.addAll(constructors);
               Collections.sort(sortedConstructors, AnnotatedTypes.AnnotatedConstructorComparator.instance());
               Iterator var14 = sortedConstructors.iterator();

               while(true) {
                  AnnotatedConstructor constructor;
                  do {
                     if (!var14.hasNext()) {
                        builder.append("}");
                        return builder.toString();
                     }

                     constructor = (AnnotatedConstructor)var14.next();
                  } while(constructor.getAnnotations().isEmpty() && !hasMethodParameters(constructor));

                  builder.append(createCallableId(constructor));
                  builder.append(';');
               }
            }

            method = (AnnotatedMethod)var12.next();
         } while(method.getAnnotations().isEmpty() && !hasMethodParameters(method));

         builder.append(createCallableId(method));
         builder.append(';');
      }
   }

   private static boolean hasMethodParameters(AnnotatedCallable callable) {
      Iterator var1 = callable.getParameters().iterator();

      AnnotatedParameter parameter;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         parameter = (AnnotatedParameter)var1.next();
      } while(parameter.getAnnotations().isEmpty());

      return true;
   }

   protected static String createAnnotationCollectionId(Collection annotations) {
      if (annotations.isEmpty()) {
         return "";
      } else {
         StringBuilder builder = new StringBuilder();
         builder.append('[');
         List annotationList = new ArrayList(annotations.size());
         annotationList.addAll(annotations);
         Collections.sort(annotationList, AnnotatedTypes.AnnotationComparator.INSTANCE);
         Iterator var3 = annotationList.iterator();

         while(var3.hasNext()) {
            Annotation a = (Annotation)var3.next();
            builder.append('@');
            builder.append(a.annotationType().getName());
            builder.append('(');
            Method[] declaredMethods = (Method[])AccessController.doPrivileged(new GetDeclaredMethodsAction(a.annotationType()));
            List methods = new ArrayList(declaredMethods.length);
            Collections.addAll(methods, declaredMethods);
            Collections.sort(methods, AnnotatedTypes.MethodComparator.INSTANCE);

            for(int i = 0; i < methods.size(); ++i) {
               Method method = (Method)methods.get(i);

               try {
                  Object value = method.invoke(a);
                  builder.append(method.getName());
                  builder.append('=');
                  builder.append(value.toString());
               } catch (NullPointerException var10) {
                  throwRE(a, method, var10);
               } catch (IllegalArgumentException var11) {
                  throwRE(a, method, var11);
               } catch (IllegalAccessException var12) {
                  throwRE(a, method, var12);
               } catch (InvocationTargetException var13) {
                  throwRE(a, method, var13);
               }

               if (i + 1 != methods.size()) {
                  builder.append(',');
               }
            }

            builder.append(')');
         }

         builder.append(']');
         return builder.toString();
      }
   }

   private static void throwRE(Annotation a, Method method, Throwable e) {
      throw new RuntimeException(e.getClass().getSimpleName() + " accessing annotation member, annotation: " + a.annotationType().getName() + " member: " + method.getName(), e);
   }

   public static String createFieldId(AnnotatedField field) {
      return createFieldId(field.getJavaMember(), field.getAnnotations());
   }

   public static String createFieldId(Field field, Collection annotations) {
      StringBuilder builder = new StringBuilder();
      builder.append(field.getDeclaringClass().getName());
      builder.append('.');
      builder.append(field.getName());
      builder.append(createAnnotationCollectionId(annotations));
      return builder.toString();
   }

   public static String createCallableId(AnnotatedCallable method) {
      StringBuilder builder = new StringBuilder();
      builder.append(method.getJavaMember().getDeclaringClass().getName());
      builder.append('.');
      builder.append(method.getJavaMember().getName());
      builder.append(createAnnotationCollectionId(method.getAnnotations()));
      builder.append(createParameterListId(method.getParameters()));
      return builder.toString();
   }

   public static String createMethodId(Method method, Set annotations, List parameters) {
      StringBuilder builder = new StringBuilder();
      builder.append(method.getDeclaringClass().getName());
      builder.append('.');
      builder.append(method.getName());
      builder.append(createAnnotationCollectionId(annotations));
      builder.append(createParameterListId(parameters));
      return builder.toString();
   }

   public static String createConstructorId(Constructor constructor, Set annotations, List parameters) {
      StringBuilder builder = new StringBuilder();
      builder.append(constructor.getDeclaringClass().getName());
      builder.append('.');
      builder.append(constructor.getName());
      builder.append(createAnnotationCollectionId(annotations));
      builder.append(createParameterListId(parameters));
      return builder.toString();
   }

   public static String createParameterListId(List parameters) {
      StringBuilder builder = new StringBuilder();
      builder.append("(");

      for(int i = 0; i < parameters.size(); ++i) {
         AnnotatedParameter ap = (AnnotatedParameter)parameters.get(i);
         builder.append(createParameterId(ap));
         if (i + 1 != parameters.size()) {
            builder.append(',');
         }
      }

      builder.append(")");
      return builder.toString();
   }

   public static String createParameterId(AnnotatedParameter annotatedParameter) {
      return createParameterId(annotatedParameter.getBaseType(), annotatedParameter.getAnnotations());
   }

   public static String createParameterId(Type type, Set annotations) {
      StringBuilder builder = new StringBuilder();
      if (type instanceof Class) {
         Class c = (Class)type;
         builder.append(c.getName());
      } else {
         builder.append(type.toString());
      }

      builder.append(createAnnotationCollectionId(annotations));
      return builder.toString();
   }

   private static boolean compareAnnotated(Annotated a1, Annotated a2) {
      return a1.getAnnotations().equals(a2.getAnnotations());
   }

   private static boolean compareAnnotatedParameters(List p1, List p2) {
      if (p1.size() != p2.size()) {
         return false;
      } else {
         for(int i = 0; i < p1.size(); ++i) {
            if (!compareAnnotated((Annotated)p1.get(i), (Annotated)p2.get(i))) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean compareAnnotatedParameters(AnnotatedParameter p1, AnnotatedParameter p2) {
      return compareAnnotatedCallable(p1.getDeclaringCallable(), p2.getDeclaringCallable()) && p1.getPosition() == p2.getPosition() && compareAnnotated(p1, p2);
   }

   public static boolean compareAnnotatedField(AnnotatedField f1, AnnotatedField f2) {
      return !f1.getJavaMember().equals(f2.getJavaMember()) ? false : compareAnnotated(f1, f2);
   }

   public static boolean compareAnnotatedCallable(AnnotatedCallable m1, AnnotatedCallable m2) {
      if (!m1.getJavaMember().equals(m2.getJavaMember())) {
         return false;
      } else {
         return !compareAnnotated(m1, m2) ? false : compareAnnotatedParameters(m1.getParameters(), m2.getParameters());
      }
   }

   public static boolean compareAnnotatedTypes(AnnotatedType t1, AnnotatedType t2) {
      if (!t1.getJavaClass().equals(t2.getJavaClass())) {
         return false;
      } else if (!compareAnnotated(t1, t2)) {
         return false;
      } else if (t1.getFields().size() != t2.getFields().size()) {
         return false;
      } else {
         Map fields = new HashMap();
         Iterator var3 = t2.getFields().iterator();

         AnnotatedField f;
         while(var3.hasNext()) {
            f = (AnnotatedField)var3.next();
            fields.put(f.getJavaMember(), f);
         }

         var3 = t1.getFields().iterator();

         while(var3.hasNext()) {
            f = (AnnotatedField)var3.next();
            if (!fields.containsKey(f.getJavaMember())) {
               return false;
            }

            if (!compareAnnotatedField(f, (AnnotatedField)fields.get(f.getJavaMember()))) {
               return false;
            }
         }

         if (t1.getMethods().size() != t2.getMethods().size()) {
            return false;
         } else {
            Map methods = new HashMap();
            Iterator var8 = t2.getMethods().iterator();

            AnnotatedMethod f;
            while(var8.hasNext()) {
               f = (AnnotatedMethod)var8.next();
               methods.put(f.getJavaMember(), f);
            }

            var8 = t1.getMethods().iterator();

            while(var8.hasNext()) {
               f = (AnnotatedMethod)var8.next();
               if (!methods.containsKey(f.getJavaMember())) {
                  return false;
               }

               if (!compareAnnotatedCallable(f, (AnnotatedCallable)methods.get(f.getJavaMember()))) {
                  return false;
               }
            }

            if (t1.getConstructors().size() != t2.getConstructors().size()) {
               return false;
            } else {
               Map constructors = new HashMap();
               Iterator var10 = t2.getConstructors().iterator();

               AnnotatedConstructor f;
               while(var10.hasNext()) {
                  f = (AnnotatedConstructor)var10.next();
                  constructors.put(f.getJavaMember(), f);
               }

               var10 = t1.getConstructors().iterator();

               do {
                  if (!var10.hasNext()) {
                     return true;
                  }

                  f = (AnnotatedConstructor)var10.next();
                  if (!constructors.containsKey(f.getJavaMember())) {
                     return false;
                  }
               } while(compareAnnotatedCallable(f, (AnnotatedCallable)constructors.get(f.getJavaMember())));

               return false;
            }
         }
      }
   }

   public static AnnotatedType getDeclaringAnnotatedType(Annotated annotated) {
      if (annotated == null) {
         throw new IllegalArgumentException("Annotated cannot be null");
      } else if (annotated instanceof AnnotatedType) {
         return (AnnotatedType)Reflections.cast(annotated);
      } else if (annotated instanceof AnnotatedMember) {
         return ((AnnotatedMember)Reflections.cast(annotated)).getDeclaringType();
      } else if (annotated instanceof AnnotatedParameter) {
         return getDeclaringAnnotatedType(((AnnotatedParameter)Reflections.cast(annotated)).getDeclaringCallable());
      } else {
         throw new IllegalArgumentException("Unrecognized annotated " + annotated);
      }
   }

   private AnnotatedTypes() {
   }

   public static boolean hasBeanDefiningAnnotation(AnnotatedType annotatedType) {
      return hasBeanDefiningAnnotation(annotatedType, META_ANNOTATIONS);
   }

   public static boolean hasBeanDefiningAnnotation(AnnotatedType annotatedType, Set metaAnnotations) {
      Iterator var2 = BEAN_DEFINING_ANNOTATIONS.iterator();

      Class metaAnnotation;
      do {
         if (!var2.hasNext()) {
            var2 = metaAnnotations.iterator();

            do {
               if (!var2.hasNext()) {
                  return false;
               }

               metaAnnotation = (Class)var2.next();
            } while(!hasBeanDefiningMetaAnnotationSpecified(annotatedType.getAnnotations(), metaAnnotation));

            return true;
         }

         metaAnnotation = (Class)var2.next();
      } while(!annotatedType.isAnnotationPresent(metaAnnotation));

      return true;
   }

   private static boolean hasBeanDefiningMetaAnnotationSpecified(Set annotations, Class metaAnnotationType) {
      Iterator var2 = annotations.iterator();

      Annotation annotation;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         annotation = (Annotation)var2.next();
      } while(!annotation.annotationType().isAnnotationPresent(metaAnnotationType));

      return true;
   }

   @SuppressFBWarnings({"SE_COMPARATOR_SHOULD_BE_SERIALIZABLE"})
   private static class MethodComparator implements Comparator {
      public static final Comparator INSTANCE = new MethodComparator();

      public int compare(Method arg0, Method arg1) {
         return arg0.getName().compareTo(arg1.getName());
      }
   }

   @SuppressFBWarnings({"SE_COMPARATOR_SHOULD_BE_SERIALIZABLE"})
   private static class AnnotationComparator implements Comparator {
      public static final Comparator INSTANCE = new AnnotationComparator();

      public int compare(Annotation arg0, Annotation arg1) {
         return arg0.annotationType().getName().compareTo(arg1.annotationType().getName());
      }
   }

   @SuppressFBWarnings({"SE_COMPARATOR_SHOULD_BE_SERIALIZABLE"})
   private static class AnnotatedFieldComparator implements Comparator {
      public static Comparator instance() {
         return new AnnotatedFieldComparator();
      }

      public int compare(AnnotatedField arg0, AnnotatedField arg1) {
         return arg0.getJavaMember().getName().equals(arg1.getJavaMember().getName()) ? arg0.getJavaMember().getDeclaringClass().getName().compareTo(arg1.getJavaMember().getDeclaringClass().getName()) : arg0.getJavaMember().getName().compareTo(arg1.getJavaMember().getName());
      }
   }

   @SuppressFBWarnings({"SE_COMPARATOR_SHOULD_BE_SERIALIZABLE"})
   private static class AnnotatedConstructorComparator implements Comparator {
      private AnnotatedCallableComparator callableComparator = new AnnotatedCallableComparator();

      public static Comparator instance() {
         return new AnnotatedConstructorComparator();
      }

      public int compare(AnnotatedConstructor arg0, AnnotatedConstructor arg1) {
         int result = this.callableComparator.compare((AnnotatedCallable)arg0, (AnnotatedCallable)arg1);
         if (result != 0) {
            return result;
         } else {
            result = arg0.getJavaMember().getParameterTypes().length - arg1.getJavaMember().getParameterTypes().length;
            if (result != 0) {
               return result;
            } else {
               for(int i = 0; i < arg0.getJavaMember().getParameterTypes().length; ++i) {
                  Class p0 = arg0.getJavaMember().getParameterTypes()[i];
                  Class p1 = arg1.getJavaMember().getParameterTypes()[i];
                  result = p0.getName().compareTo(p1.getName());
                  if (result != 0) {
                     return result;
                  }
               }

               return 0;
            }
         }
      }
   }

   @SuppressFBWarnings({"SE_COMPARATOR_SHOULD_BE_SERIALIZABLE"})
   private static class AnnotatedMethodComparator implements Comparator {
      private AnnotatedCallableComparator callableComparator = new AnnotatedCallableComparator();

      public static Comparator instance() {
         return new AnnotatedMethodComparator();
      }

      public int compare(AnnotatedMethod arg0, AnnotatedMethod arg1) {
         int result = this.callableComparator.compare((AnnotatedCallable)arg0, (AnnotatedCallable)arg1);
         if (result != 0) {
            return result;
         } else {
            result = arg0.getJavaMember().getParameterTypes().length - arg1.getJavaMember().getParameterTypes().length;
            if (result != 0) {
               return result;
            } else {
               for(int i = 0; i < arg0.getJavaMember().getParameterTypes().length; ++i) {
                  Class p0 = arg0.getJavaMember().getParameterTypes()[i];
                  Class p1 = arg1.getJavaMember().getParameterTypes()[i];
                  result = p0.getName().compareTo(p1.getName());
                  if (result != 0) {
                     return result;
                  }
               }

               return 0;
            }
         }
      }
   }

   @SuppressFBWarnings({"SE_COMPARATOR_SHOULD_BE_SERIALIZABLE"})
   private static class AnnotatedCallableComparator implements Comparator {
      private AnnotatedCallableComparator() {
      }

      public int compare(AnnotatedCallable arg0, AnnotatedCallable arg1) {
         int result = arg0.getJavaMember().getName().compareTo(arg1.getJavaMember().getName());
         if (result != 0) {
            return result;
         } else {
            result = arg0.getJavaMember().getDeclaringClass().getName().compareTo(arg1.getJavaMember().getDeclaringClass().getName());
            if (result != 0) {
               return result;
            } else {
               result = arg0.getParameters().size() - arg1.getParameters().size();
               return result;
            }
         }
      }

      // $FF: synthetic method
      AnnotatedCallableComparator(Object x0) {
         this();
      }
   }
}
