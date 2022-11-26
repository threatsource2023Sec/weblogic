package org.jboss.weld.bootstrap;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.ObserverMethod;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import org.jboss.weld.bootstrap.api.helpers.AbstractBootstrapService;
import org.jboss.weld.event.ContainerLifecycleEventObserverMethod;
import org.jboss.weld.resources.spi.ClassFileInfo;
import org.jboss.weld.resources.spi.ClassFileServices;
import org.jboss.weld.util.Types;
import org.jboss.weld.util.reflection.Reflections;

public class FastProcessAnnotatedTypeResolver extends AbstractBootstrapService {
   private final Set catchAllObservers = new HashSet();
   private final Map observers = new LinkedHashMap();

   public FastProcessAnnotatedTypeResolver(Iterable observers) throws UnsupportedObserverMethodException {
      Iterator var2 = observers.iterator();

      while(true) {
         ObserverMethod o;
         Set qualifiers;
         do {
            do {
               if (!var2.hasNext()) {
                  return;
               }

               o = (ObserverMethod)var2.next();
            } while(!(o instanceof ContainerLifecycleEventObserverMethod));

            qualifiers = o.getObservedQualifiers();
         } while(!qualifiers.isEmpty() && (qualifiers.size() != 1 || !Any.class.equals(((Annotation)qualifiers.iterator().next()).annotationType())));

         this.process((ContainerLifecycleEventObserverMethod)o, o.getObservedType());
      }
   }

   private void process(ContainerLifecycleEventObserverMethod observer, Type observedType) throws UnsupportedObserverMethodException {
      if (Object.class.equals(observedType)) {
         this.catchAllObservers.add(observer);
      } else if (ProcessAnnotatedType.class.equals(observedType)) {
         this.catchAllObservers.add(observer);
      } else if (observedType instanceof ParameterizedType) {
         ParameterizedType type = (ParameterizedType)observedType;
         if (ProcessAnnotatedType.class.equals(type.getRawType())) {
            Type typeParameter = type.getActualTypeArguments()[0];
            if (typeParameter instanceof Class) {
               this.observers.put(observer, new ExactTypePredicate(Reflections.getRawType(typeParameter)));
            } else {
               if (typeParameter instanceof ParameterizedType) {
                  return;
               }

               if (typeParameter instanceof WildcardType) {
                  WildcardType wildCard = (WildcardType)typeParameter;
                  this.checkBounds(observer, wildCard.getUpperBounds());
                  this.observers.put(observer, FastProcessAnnotatedTypeResolver.CompositePredicate.assignable(Types.getRawTypes(wildCard.getUpperBounds())));
               } else if (typeParameter instanceof TypeVariable) {
                  TypeVariable variable = (TypeVariable)typeParameter;
                  this.checkBounds(observer, variable.getBounds());
                  this.observers.put(observer, FastProcessAnnotatedTypeResolver.CompositePredicate.assignable(Types.getRawTypes(variable.getBounds())));
               }
            }
         }
      } else if (observedType instanceof TypeVariable) {
         this.defaultRules(observer, observedType);
      }

   }

   private void checkBounds(ContainerLifecycleEventObserverMethod observer, Type[] bounds) throws UnsupportedObserverMethodException {
      Type[] var3 = bounds;
      int var4 = bounds.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Type type = var3[var5];
         if (!(type instanceof Class)) {
            throw new UnsupportedObserverMethodException(observer);
         }
      }

   }

   private void defaultRules(ContainerLifecycleEventObserverMethod observer, Type observedType) throws UnsupportedObserverMethodException {
      if (ProcessAnnotatedType.class.equals(observedType)) {
         this.catchAllObservers.add(observer);
      } else if (observedType instanceof ParameterizedType) {
         ParameterizedType parameterizedType = (ParameterizedType)observedType;
         if (ProcessAnnotatedType.class.equals(parameterizedType.getRawType())) {
            Type argument = parameterizedType.getActualTypeArguments()[0];
            if (!(argument instanceof Class)) {
               throw new UnsupportedObserverMethodException(observer);
            }

            this.observers.put(observer, new ExactTypePredicate(Reflections.getRawType(argument)));
         }
      } else if (observedType instanceof TypeVariable) {
         TypeVariable typeVariable = (TypeVariable)observedType;
         if (Reflections.isUnboundedTypeVariable(observedType)) {
            this.catchAllObservers.add(observer);
         } else if (typeVariable.getBounds().length == 1) {
            this.defaultRules(observer, typeVariable.getBounds()[0]);
         }
      }

   }

   public Set resolveProcessAnnotatedTypeObservers(ClassFileServices classFileServices, String className) {
      Set result = new HashSet();
      result.addAll(this.catchAllObservers);
      ClassFileInfo classInfo = classFileServices.getClassFileInfo(className);
      Iterator var5 = this.observers.entrySet().iterator();

      while(var5.hasNext()) {
         Map.Entry entry = (Map.Entry)var5.next();
         ContainerLifecycleEventObserverMethod observer = (ContainerLifecycleEventObserverMethod)entry.getKey();
         if (this.containsRequiredAnnotation(classInfo, observer) && ((Predicate)entry.getValue()).test(classInfo)) {
            result.add(observer);
         }
      }

      return result;
   }

   private boolean containsRequiredAnnotation(ClassFileInfo classInfo, ContainerLifecycleEventObserverMethod observer) {
      if (observer.getRequiredAnnotations().isEmpty()) {
         return true;
      } else {
         Iterator var3 = observer.getRequiredAnnotations().iterator();

         Class annotation;
         do {
            if (!var3.hasNext()) {
               return false;
            }

            annotation = (Class)var3.next();
         } while(!classInfo.containsAnnotation(annotation));

         return true;
      }
   }

   public void cleanupAfterBoot() {
      this.catchAllObservers.clear();
      this.observers.clear();
   }

   private static class CompositePredicate implements Predicate {
      private final Predicate[] predicates;

      private static CompositePredicate assignable(Class[] classes) {
         Predicate[] predicates = new Predicate[classes.length];

         for(int i = 0; i < classes.length; ++i) {
            predicates[i] = new AssignableToPredicate(classes[i]);
         }

         return new CompositePredicate(predicates);
      }

      public CompositePredicate(Predicate[] predicates) {
         this.predicates = predicates;
      }

      public boolean test(ClassFileInfo input) {
         Predicate[] var2 = this.predicates;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Predicate predicate = var2[var4];
            if (!predicate.test(input)) {
               return false;
            }
         }

         return true;
      }
   }

   private static class AssignableToPredicate implements Predicate {
      private final Class type;

      public AssignableToPredicate(Class type) {
         this.type = type;
      }

      public boolean test(ClassFileInfo input) {
         return input.isAssignableTo(this.type);
      }
   }

   private static class ExactTypePredicate implements Predicate {
      private final Class type;

      public ExactTypePredicate(Class type) {
         this.type = type;
      }

      public boolean test(ClassFileInfo input) {
         return this.type.getName().equals(input.getClassName());
      }
   }
}
