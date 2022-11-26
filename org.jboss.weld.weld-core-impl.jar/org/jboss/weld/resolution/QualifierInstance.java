package org.jboss.weld.resolution;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.util.Nonbinding;
import javax.inject.Named;
import org.jboss.weld.bean.RIBean;
import org.jboss.weld.logging.ResolutionLogger;
import org.jboss.weld.metadata.cache.MetaAnnotationStore;
import org.jboss.weld.metadata.cache.QualifierModel;
import org.jboss.weld.security.GetDeclaredMethodsAction;
import org.jboss.weld.security.SetAccessibleAction;
import org.jboss.weld.util.collections.ImmutableMap;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Formats;

public class QualifierInstance {
   public static final QualifierInstance ANY = new QualifierInstance(Any.class);
   public static final QualifierInstance DEFAULT = new QualifierInstance(Default.class);
   private final Class annotationClass;
   private final Map values;
   private final int hashCode;

   public static Set of(Set qualifiers, MetaAnnotationStore store) {
      if (qualifiers.isEmpty()) {
         return Collections.emptySet();
      } else {
         ImmutableSet.Builder ret = ImmutableSet.builder();
         Iterator var3 = qualifiers.iterator();

         while(var3.hasNext()) {
            Annotation a = (Annotation)var3.next();
            ret.add(of(a, store));
         }

         return ret.build();
      }
   }

   public static Set of(Bean bean, MetaAnnotationStore store) {
      return bean instanceof RIBean ? ((RIBean)bean).getQualifierInstances() : of(bean.getQualifiers(), store);
   }

   public static QualifierInstance of(Annotation annotation, MetaAnnotationStore store) {
      Class annotationType = annotation.annotationType();
      if (Any.class == annotationType) {
         return ANY;
      } else if (Default.class == annotationType) {
         return DEFAULT;
      } else if (Named.class == annotationType) {
         Named named = (Named)annotation;
         return new QualifierInstance(annotationType, ImmutableMap.of("value", named.value()));
      } else {
         return new QualifierInstance(annotationType, createValues(annotation, store));
      }
   }

   private QualifierInstance(Class annotationClass) {
      this(annotationClass, Collections.emptyMap());
   }

   public QualifierInstance(Class annotationClass, Map values) {
      this.annotationClass = annotationClass;
      this.values = values;
      this.hashCode = Objects.hash(new Object[]{annotationClass, values});
   }

   private static Map createValues(Annotation instance, MetaAnnotationStore store) {
      Class annotationClass = instance.annotationType();
      QualifierModel model = store.getBindingTypeModel(annotationClass);
      if (model.getAnnotatedAnnotation().getMethods().size() == 0) {
         return Collections.emptyMap();
      } else {
         ImmutableMap.Builder builder = ImmutableMap.builder();
         Iterator var5 = model.getAnnotatedAnnotation().getMethods().iterator();

         while(true) {
            AnnotatedMethod method;
            do {
               if (!var5.hasNext()) {
                  return builder.build();
               }

               method = (AnnotatedMethod)var5.next();
            } while(model.getNonBindingMembers().contains(method));

            try {
               if (System.getSecurityManager() != null) {
                  AccessController.doPrivileged(SetAccessibleAction.of(method.getJavaMember()));
               } else {
                  method.getJavaMember().setAccessible(true);
               }

               builder.put(method.getJavaMember().getName(), method.getJavaMember().invoke(instance));
            } catch (IllegalArgumentException var15) {
               builder = ImmutableMap.builder();
               Method[] methods;
               if (System.getSecurityManager() != null) {
                  methods = (Method[])AccessController.doPrivileged(new GetDeclaredMethodsAction(annotationClass));
               } else {
                  methods = annotationClass.getDeclaredMethods();
               }

               Method[] var9 = methods;
               int var10 = methods.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  Method m = var9[var11];
                  if (m.getAnnotation(Nonbinding.class) == null) {
                     try {
                        builder.put(m.getName(), m.invoke(instance));
                     } catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException var14) {
                        throw ResolutionLogger.LOG.cannotCreateQualifierInstanceValues(instance, Formats.formatAsStackTraceElement((Member)method.getJavaMember()), var14);
                     }
                  }
               }
            } catch (IllegalAccessException | InvocationTargetException var16) {
               throw ResolutionLogger.LOG.cannotCreateQualifierInstanceValues(instance, Formats.formatAsStackTraceElement((Member)method.getJavaMember()), var16);
            }
         }
      }
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         QualifierInstance that = (QualifierInstance)o;
         if (!this.annotationClass.equals(that.annotationClass)) {
            return false;
         } else {
            return this.values.equals(that.values);
         }
      } else {
         return false;
      }
   }

   public Class getAnnotationClass() {
      return this.annotationClass;
   }

   public Object getValue(String name) {
      return this.values.get(name);
   }

   public int hashCode() {
      return this.hashCode;
   }

   public String toString() {
      return "QualifierInstance {annotationClass=" + this.annotationClass + ", values=" + this.values + '}';
   }
}
