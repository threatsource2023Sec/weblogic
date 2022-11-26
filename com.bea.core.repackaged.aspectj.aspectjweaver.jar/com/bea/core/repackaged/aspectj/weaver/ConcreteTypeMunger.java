package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.util.PartialOrder;
import java.util.Map;

public abstract class ConcreteTypeMunger implements PartialOrder.PartialComparable {
   protected ResolvedTypeMunger munger;
   protected ResolvedType aspectType;

   public ConcreteTypeMunger(ResolvedTypeMunger munger, ResolvedType aspectType) {
      this.munger = munger;
      this.aspectType = aspectType;
   }

   public boolean equivalentTo(Object other) {
      if (!(other instanceof ConcreteTypeMunger)) {
         return false;
      } else {
         ConcreteTypeMunger o = (ConcreteTypeMunger)other;
         ResolvedTypeMunger otherTypeMunger = o.getMunger();
         ResolvedTypeMunger thisTypeMunger = this.getMunger();
         boolean var10000;
         if (thisTypeMunger instanceof NewConstructorTypeMunger && otherTypeMunger instanceof NewConstructorTypeMunger) {
            label64: {
               if (((NewConstructorTypeMunger)otherTypeMunger).equivalentTo(thisTypeMunger)) {
                  if (o.getAspectType() == null) {
                     if (this.getAspectType() == null) {
                        break label64;
                     }
                  } else if (o.getAspectType().equals(this.getAspectType())) {
                     break label64;
                  }
               }

               var10000 = false;
               return var10000;
            }

            var10000 = true;
            return var10000;
         } else {
            label66: {
               label49: {
                  if (otherTypeMunger == null) {
                     if (thisTypeMunger != null) {
                        break label49;
                     }
                  } else if (!otherTypeMunger.equals(thisTypeMunger)) {
                     break label49;
                  }

                  if (o.getAspectType() == null) {
                     if (this.getAspectType() == null) {
                        break label66;
                     }
                  } else if (o.getAspectType().equals(this.getAspectType())) {
                     break label66;
                  }
               }

               var10000 = false;
               return var10000;
            }

            var10000 = true;
            return var10000;
         }
      }
   }

   public ResolvedTypeMunger getMunger() {
      return this.munger;
   }

   public ResolvedType getAspectType() {
      return this.aspectType;
   }

   public ResolvedMember getSignature() {
      return this.munger.getSignature();
   }

   public World getWorld() {
      return this.aspectType.getWorld();
   }

   public ISourceLocation getSourceLocation() {
      return this.munger == null ? null : this.munger.getSourceLocation();
   }

   public boolean matches(ResolvedType onType) {
      if (this.munger == null) {
         throw new RuntimeException("huh: " + this);
      } else {
         return this.munger.matches(onType, this.aspectType);
      }
   }

   public ResolvedMember getMatchingSyntheticMember(Member member) {
      return this.munger.getMatchingSyntheticMember(member, this.aspectType);
   }

   public int compareTo(Object other) {
      ConcreteTypeMunger o = (ConcreteTypeMunger)other;
      ResolvedType otherAspect = o.aspectType;
      if (this.aspectType.equals(otherAspect)) {
         return this.getSignature().getStart() < o.getSignature().getStart() ? -1 : 1;
      } else if (this.aspectType.isAssignableFrom(o.aspectType)) {
         return 1;
      } else {
         return o.aspectType.isAssignableFrom(this.aspectType) ? -1 : 0;
      }
   }

   public int fallbackCompareTo(Object other) {
      return 0;
   }

   public boolean isTargetTypeParameterized() {
      return this.munger == null ? false : this.munger.sharesTypeVariablesWithGenericType();
   }

   public abstract ConcreteTypeMunger parameterizedFor(ResolvedType var1);

   public boolean isLateMunger() {
      return this.munger == null ? false : this.munger.isLateMunger();
   }

   public abstract ConcreteTypeMunger parameterizeWith(Map var1, World var2);

   public boolean existsToSupportShadowMunging() {
      return this.munger != null ? this.munger.existsToSupportShadowMunging() : false;
   }

   public boolean shouldOverwrite() {
      return true;
   }
}
