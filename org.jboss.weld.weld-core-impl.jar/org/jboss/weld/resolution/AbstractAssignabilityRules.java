package org.jboss.weld.resolution;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Iterator;
import java.util.Set;

public abstract class AbstractAssignabilityRules implements AssignabilityRules {
   public boolean matches(Set requiredTypes, Set beanTypes) {
      Iterator var3 = requiredTypes.iterator();

      Type requiredType;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         requiredType = (Type)var3.next();
      } while(!this.matches(requiredType, beanTypes));

      return true;
   }

   public boolean matches(Type requiredType, Set beanTypes) {
      Iterator var3 = beanTypes.iterator();

      Type beanType;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         beanType = (Type)var3.next();
      } while(!this.matches((Type)requiredType, (Type)beanType));

      return true;
   }

   protected Type[] getUppermostTypeVariableBounds(TypeVariable bound) {
      return bound.getBounds()[0] instanceof TypeVariable ? this.getUppermostTypeVariableBounds((TypeVariable)bound.getBounds()[0]) : bound.getBounds();
   }

   private Type[] getUppermostBounds(Type[] bounds) {
      return bounds[0] instanceof TypeVariable ? this.getUppermostTypeVariableBounds((TypeVariable)bounds[0]) : bounds;
   }

   protected boolean boundsMatch(Type[] upperBounds, Type[] stricterUpperBounds) {
      upperBounds = this.getUppermostBounds(upperBounds);
      stricterUpperBounds = this.getUppermostBounds(stricterUpperBounds);
      Type[] var3 = upperBounds;
      int var4 = upperBounds.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Type upperBound = var3[var5];
         if (!CovariantTypes.isAssignableFromAtLeastOne(upperBound, stricterUpperBounds)) {
            return false;
         }
      }

      return true;
   }

   protected boolean lowerBoundsOfWildcardMatch(Type parameter, WildcardType requiredParameter) {
      return this.lowerBoundsOfWildcardMatch(new Type[]{parameter}, requiredParameter);
   }

   protected boolean lowerBoundsOfWildcardMatch(Type[] beanParameterBounds, WildcardType requiredParameter) {
      if (requiredParameter.getLowerBounds().length > 0) {
         Type[] lowerBounds = requiredParameter.getLowerBounds();
         if (!this.boundsMatch(beanParameterBounds, lowerBounds)) {
            return false;
         }
      }

      return true;
   }

   protected boolean upperBoundsOfWildcardMatch(WildcardType requiredParameter, Type parameter) {
      return this.boundsMatch(requiredParameter.getUpperBounds(), new Type[]{parameter});
   }
}
