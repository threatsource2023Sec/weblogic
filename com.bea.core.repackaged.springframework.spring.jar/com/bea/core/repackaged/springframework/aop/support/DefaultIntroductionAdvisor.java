package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.springframework.aop.ClassFilter;
import com.bea.core.repackaged.springframework.aop.DynamicIntroductionAdvice;
import com.bea.core.repackaged.springframework.aop.IntroductionAdvisor;
import com.bea.core.repackaged.springframework.aop.IntroductionInfo;
import com.bea.core.repackaged.springframework.core.Ordered;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class DefaultIntroductionAdvisor implements IntroductionAdvisor, ClassFilter, Ordered, Serializable {
   private final Advice advice;
   private final Set interfaces;
   private int order;

   public DefaultIntroductionAdvisor(Advice advice) {
      this(advice, advice instanceof IntroductionInfo ? (IntroductionInfo)advice : null);
   }

   public DefaultIntroductionAdvisor(Advice advice, @Nullable IntroductionInfo introductionInfo) {
      this.interfaces = new LinkedHashSet();
      this.order = Integer.MAX_VALUE;
      Assert.notNull(advice, (String)"Advice must not be null");
      this.advice = advice;
      if (introductionInfo != null) {
         Class[] introducedInterfaces = introductionInfo.getInterfaces();
         if (introducedInterfaces.length == 0) {
            throw new IllegalArgumentException("IntroductionAdviceSupport implements no interfaces");
         }

         Class[] var4 = introducedInterfaces;
         int var5 = introducedInterfaces.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Class ifc = var4[var6];
            this.addInterface(ifc);
         }
      }

   }

   public DefaultIntroductionAdvisor(DynamicIntroductionAdvice advice, Class ifc) {
      this.interfaces = new LinkedHashSet();
      this.order = Integer.MAX_VALUE;
      Assert.notNull(advice, (String)"Advice must not be null");
      this.advice = advice;
      this.addInterface(ifc);
   }

   public void addInterface(Class ifc) {
      Assert.notNull(ifc, (String)"Interface must not be null");
      if (!ifc.isInterface()) {
         throw new IllegalArgumentException("Specified class [" + ifc.getName() + "] must be an interface");
      } else {
         this.interfaces.add(ifc);
      }
   }

   public Class[] getInterfaces() {
      return ClassUtils.toClassArray(this.interfaces);
   }

   public void validateInterfaces() throws IllegalArgumentException {
      Iterator var1 = this.interfaces.iterator();

      Class ifc;
      do {
         if (!var1.hasNext()) {
            return;
         }

         ifc = (Class)var1.next();
      } while(!(this.advice instanceof DynamicIntroductionAdvice) || ((DynamicIntroductionAdvice)this.advice).implementsInterface(ifc));

      throw new IllegalArgumentException("DynamicIntroductionAdvice [" + this.advice + "] does not implement interface [" + ifc.getName() + "] specified for introduction");
   }

   public void setOrder(int order) {
      this.order = order;
   }

   public int getOrder() {
      return this.order;
   }

   public Advice getAdvice() {
      return this.advice;
   }

   public boolean isPerInstance() {
      return true;
   }

   public ClassFilter getClassFilter() {
      return this;
   }

   public boolean matches(Class clazz) {
      return true;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof DefaultIntroductionAdvisor)) {
         return false;
      } else {
         DefaultIntroductionAdvisor otherAdvisor = (DefaultIntroductionAdvisor)other;
         return this.advice.equals(otherAdvisor.advice) && this.interfaces.equals(otherAdvisor.interfaces);
      }
   }

   public int hashCode() {
      return this.advice.hashCode() * 13 + this.interfaces.hashCode();
   }

   public String toString() {
      return this.getClass().getName() + ": advice [" + this.advice + "]; interfaces " + ClassUtils.classNamesToString((Collection)this.interfaces);
   }
}
