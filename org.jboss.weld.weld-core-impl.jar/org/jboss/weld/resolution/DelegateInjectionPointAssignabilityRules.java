package org.jboss.weld.resolution;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import org.jboss.weld.util.Types;

public class DelegateInjectionPointAssignabilityRules extends BeanTypeAssignabilityRules {
   private static final AssignabilityRules INSTANCE = new DelegateInjectionPointAssignabilityRules();

   public static AssignabilityRules instance() {
      return INSTANCE;
   }

   protected boolean parametersMatch(Type delegateParameter, Type beanParameter) {
      if (Types.isActualType(delegateParameter) && Types.isActualType(beanParameter)) {
         return this.matches(delegateParameter, beanParameter);
      } else if (delegateParameter instanceof WildcardType && Types.isActualType(beanParameter)) {
         return this.parametersMatch((WildcardType)((WildcardType)delegateParameter), (Type)beanParameter);
      } else if (delegateParameter instanceof WildcardType && beanParameter instanceof TypeVariable) {
         return this.parametersMatch((WildcardType)delegateParameter, (TypeVariable)beanParameter);
      } else if (delegateParameter instanceof TypeVariable && beanParameter instanceof TypeVariable) {
         return this.parametersMatch((TypeVariable)delegateParameter, (TypeVariable)beanParameter);
      } else if (delegateParameter instanceof TypeVariable && Types.isActualType(beanParameter)) {
         return this.parametersMatch((TypeVariable)delegateParameter, beanParameter);
      } else if (Object.class.equals(delegateParameter) && beanParameter instanceof TypeVariable) {
         TypeVariable beanParameterVariable = (TypeVariable)beanParameter;
         return Object.class.equals(beanParameterVariable.getBounds()[0]);
      } else {
         return false;
      }
   }

   protected boolean parametersMatch(WildcardType delegateParameter, TypeVariable beanParameter) {
      Type[] beanParameterBounds = this.getUppermostTypeVariableBounds(beanParameter);
      if (!this.lowerBoundsOfWildcardMatch(beanParameterBounds, delegateParameter)) {
         return false;
      } else {
         Type[] requiredUpperBounds = delegateParameter.getUpperBounds();
         return this.boundsMatch(requiredUpperBounds, beanParameterBounds);
      }
   }

   protected boolean parametersMatch(TypeVariable delegateParameter, TypeVariable beanParameter) {
      return this.boundsMatch(this.getUppermostTypeVariableBounds(delegateParameter), this.getUppermostTypeVariableBounds(beanParameter));
   }

   protected boolean parametersMatch(TypeVariable delegateParameter, Type beanParameter) {
      Type[] var3 = this.getUppermostTypeVariableBounds(delegateParameter);
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Type type = var3[var5];
         if (!CovariantTypes.isAssignableFrom(type, beanParameter)) {
            return false;
         }
      }

      return true;
   }
}
