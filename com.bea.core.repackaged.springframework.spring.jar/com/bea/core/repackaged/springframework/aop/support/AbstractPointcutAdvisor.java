package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.springframework.aop.PointcutAdvisor;
import com.bea.core.repackaged.springframework.core.Ordered;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.io.Serializable;

public abstract class AbstractPointcutAdvisor implements PointcutAdvisor, Ordered, Serializable {
   @Nullable
   private Integer order;

   public void setOrder(int order) {
      this.order = order;
   }

   public int getOrder() {
      if (this.order != null) {
         return this.order;
      } else {
         Advice advice = this.getAdvice();
         return advice instanceof Ordered ? ((Ordered)advice).getOrder() : Integer.MAX_VALUE;
      }
   }

   public boolean isPerInstance() {
      return true;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof PointcutAdvisor)) {
         return false;
      } else {
         PointcutAdvisor otherAdvisor = (PointcutAdvisor)other;
         return ObjectUtils.nullSafeEquals(this.getAdvice(), otherAdvisor.getAdvice()) && ObjectUtils.nullSafeEquals(this.getPointcut(), otherAdvisor.getPointcut());
      }
   }

   public int hashCode() {
      return PointcutAdvisor.class.hashCode();
   }
}
