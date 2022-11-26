package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.weaver.patterns.PerFromSuper;
import com.bea.core.repackaged.aspectj.weaver.patterns.PerObject;
import com.bea.core.repackaged.aspectj.weaver.patterns.PerThisOrTargetPointcutVisitor;
import com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.TypePattern;
import java.io.IOException;

public class PerObjectInterfaceTypeMunger extends ResolvedTypeMunger {
   private final UnresolvedType interfaceType;
   private final Pointcut testPointcut;
   private TypePattern lazyTestTypePattern;
   private volatile int hashCode = 0;

   public boolean equals(Object other) {
      if (other != null && other instanceof PerObjectInterfaceTypeMunger) {
         boolean var10000;
         label38: {
            label27: {
               PerObjectInterfaceTypeMunger o = (PerObjectInterfaceTypeMunger)other;
               if (this.testPointcut == null) {
                  if (o.testPointcut != null) {
                     break label27;
                  }
               } else if (!this.testPointcut.equals(o.testPointcut)) {
                  break label27;
               }

               if (this.lazyTestTypePattern == null) {
                  if (o.lazyTestTypePattern == null) {
                     break label38;
                  }
               } else if (this.lazyTestTypePattern.equals(o.lazyTestTypePattern)) {
                  break label38;
               }
            }

            var10000 = false;
            return var10000;
         }

         var10000 = true;
         return var10000;
      } else {
         return false;
      }
   }

   public int hashCode() {
      if (this.hashCode == 0) {
         int result = 17;
         result = 37 * result + (this.testPointcut == null ? 0 : this.testPointcut.hashCode());
         result = 37 * result + (this.lazyTestTypePattern == null ? 0 : this.lazyTestTypePattern.hashCode());
         this.hashCode = result;
      }

      return this.hashCode;
   }

   public PerObjectInterfaceTypeMunger(UnresolvedType aspectType, Pointcut testPointcut) {
      super(PerObjectInterface, (ResolvedMember)null);
      this.testPointcut = testPointcut;
      this.interfaceType = AjcMemberMaker.perObjectInterfaceType(aspectType);
   }

   private TypePattern getTestTypePattern(ResolvedType aspectType) {
      if (this.lazyTestTypePattern == null) {
         boolean isPerThis;
         if (aspectType.getPerClause() instanceof PerFromSuper) {
            PerFromSuper ps = (PerFromSuper)aspectType.getPerClause();
            isPerThis = ((PerObject)ps.lookupConcretePerClause(aspectType)).isThis();
         } else {
            isPerThis = ((PerObject)aspectType.getPerClause()).isThis();
         }

         PerThisOrTargetPointcutVisitor v = new PerThisOrTargetPointcutVisitor(!isPerThis, aspectType);
         this.lazyTestTypePattern = v.getPerTypePointcut(this.testPointcut);
         this.hashCode = 0;
      }

      return this.lazyTestTypePattern;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      throw new RuntimeException("shouldn't be serialized");
   }

   public UnresolvedType getInterfaceType() {
      return this.interfaceType;
   }

   public Pointcut getTestPointcut() {
      return this.testPointcut;
   }

   public boolean matches(ResolvedType matchType, ResolvedType aspectType) {
      return matchType.isInterface() ? false : this.getTestTypePattern(aspectType).matchesStatically(matchType);
   }

   public boolean isLateMunger() {
      return true;
   }
}
