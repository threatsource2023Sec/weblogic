package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.patterns.PerTypeWithin;
import com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut;
import java.io.IOException;

public class PerTypeWithinTargetTypeMunger extends ResolvedTypeMunger {
   private UnresolvedType aspectType;
   private PerTypeWithin testPointcut;
   private volatile int hashCode = 0;

   public PerTypeWithinTargetTypeMunger(UnresolvedType aspectType, PerTypeWithin testPointcut) {
      super(PerTypeWithinInterface, (ResolvedMember)null);
      this.aspectType = aspectType;
      this.testPointcut = testPointcut;
   }

   public boolean equals(Object other) {
      if (!(other instanceof PerTypeWithinTargetTypeMunger)) {
         return false;
      } else {
         boolean var10000;
         label38: {
            label27: {
               PerTypeWithinTargetTypeMunger o = (PerTypeWithinTargetTypeMunger)other;
               if (o.testPointcut == null) {
                  if (this.testPointcut != null) {
                     break label27;
                  }
               } else if (!this.testPointcut.equals(o.testPointcut)) {
                  break label27;
               }

               if (o.aspectType == null) {
                  if (this.aspectType == null) {
                     break label38;
                  }
               } else if (this.aspectType.equals(o.aspectType)) {
                  break label38;
               }
            }

            var10000 = false;
            return var10000;
         }

         var10000 = true;
         return var10000;
      }
   }

   public int hashCode() {
      if (this.hashCode == 0) {
         int result = 17;
         result = 37 * result + (this.testPointcut == null ? 0 : this.testPointcut.hashCode());
         result = 37 * result + (this.aspectType == null ? 0 : this.aspectType.hashCode());
         this.hashCode = result;
      }

      return this.hashCode;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      throw new RuntimeException("shouldn't be serialized");
   }

   public UnresolvedType getAspectType() {
      return this.aspectType;
   }

   public Pointcut getTestPointcut() {
      return this.testPointcut;
   }

   public boolean matches(ResolvedType matchType, ResolvedType aspectType) {
      return this.isWithinType(matchType).alwaysTrue() && !matchType.isInterface();
   }

   private FuzzyBoolean isWithinType(ResolvedType type) {
      while(type != null) {
         if (this.testPointcut.getTypePattern().matchesStatically(type)) {
            return FuzzyBoolean.YES;
         }

         type = type.getDeclaringType();
      }

      return FuzzyBoolean.NO;
   }
}
