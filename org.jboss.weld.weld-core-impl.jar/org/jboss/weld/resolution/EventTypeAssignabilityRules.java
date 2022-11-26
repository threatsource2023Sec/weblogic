package org.jboss.weld.resolution;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Iterator;
import org.jboss.weld.logging.ReflectionLogger;
import org.jboss.weld.util.Types;
import org.jboss.weld.util.reflection.HierarchyDiscovery;
import org.jboss.weld.util.reflection.Reflections;

public class EventTypeAssignabilityRules extends AbstractAssignabilityRules {
   private static final AssignabilityRules INSTANCE = new EventTypeAssignabilityRules();

   public static AssignabilityRules instance() {
      return INSTANCE;
   }

   private EventTypeAssignabilityRules() {
   }

   public boolean matches(Type observedType, Type eventType) {
      return this.matchesNoBoxing(Types.boxedType(observedType), Types.boxedType(eventType));
   }

   public boolean matchesNoBoxing(Type observedType, Type eventType) {
      if (Types.isArray(observedType) && Types.isArray(eventType)) {
         Type observedComponentType = Types.getArrayComponentType(observedType);
         Iterator var4 = (new HierarchyDiscovery(Types.getArrayComponentType(eventType))).getTypeClosure().iterator();

         Type type;
         do {
            if (!var4.hasNext()) {
               return false;
            }

            type = (Type)var4.next();
         } while(!this.matchesNoBoxing(observedComponentType, type));

         return true;
      } else if (observedType instanceof TypeVariable) {
         return this.matches((TypeVariable)observedType, eventType);
      } else if (observedType instanceof Class && eventType instanceof ParameterizedType) {
         return observedType.equals(Reflections.getRawType(eventType));
      } else if (observedType instanceof ParameterizedType && eventType instanceof ParameterizedType) {
         return this.matches((ParameterizedType)observedType, (ParameterizedType)eventType);
      } else {
         return observedType instanceof Class && eventType instanceof Class ? observedType.equals(eventType) : false;
      }
   }

   private boolean matches(TypeVariable observedType, Type eventType) {
      Type[] var3 = this.getUppermostTypeVariableBounds(observedType);
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Type bound = var3[var5];
         if (!CovariantTypes.isAssignableFrom(bound, eventType)) {
            return false;
         }
      }

      return true;
   }

   private boolean matches(ParameterizedType observedType, ParameterizedType eventType) {
      if (!observedType.getRawType().equals(eventType.getRawType())) {
         return false;
      } else if (observedType.getActualTypeArguments().length != eventType.getActualTypeArguments().length) {
         throw ReflectionLogger.LOG.invalidTypeArgumentCombination(observedType, eventType);
      } else {
         for(int i = 0; i < observedType.getActualTypeArguments().length; ++i) {
            if (!this.parametersMatch(observedType.getActualTypeArguments()[i], eventType.getActualTypeArguments()[i])) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean parametersMatch(Type observedParameter, Type eventParameter) {
      if (Types.isActualType(observedParameter) && Types.isActualType(eventParameter)) {
         return this.matches(observedParameter, eventParameter);
      } else if (observedParameter instanceof WildcardType && eventParameter instanceof WildcardType) {
         return CovariantTypes.isAssignableFrom(observedParameter, eventParameter);
      } else if (observedParameter instanceof WildcardType) {
         return this.parametersMatch((WildcardType)observedParameter, eventParameter);
      } else {
         return observedParameter instanceof TypeVariable ? this.parametersMatch((TypeVariable)observedParameter, eventParameter) : false;
      }
   }

   private boolean parametersMatch(TypeVariable observedParameter, Type eventParameter) {
      Type[] var3 = this.getUppermostTypeVariableBounds(observedParameter);
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Type bound = var3[var5];
         if (!CovariantTypes.isAssignableFrom(bound, eventParameter)) {
            return false;
         }
      }

      return true;
   }

   private boolean parametersMatch(WildcardType observedParameter, Type eventParameter) {
      return this.lowerBoundsOfWildcardMatch(eventParameter, observedParameter) && this.upperBoundsOfWildcardMatch(observedParameter, eventParameter);
   }
}
