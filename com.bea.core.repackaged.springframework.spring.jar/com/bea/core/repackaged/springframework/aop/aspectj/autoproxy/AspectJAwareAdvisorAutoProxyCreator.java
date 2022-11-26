package com.bea.core.repackaged.springframework.aop.aspectj.autoproxy;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.aspectj.util.PartialOrder;
import com.bea.core.repackaged.springframework.aop.Advisor;
import com.bea.core.repackaged.springframework.aop.aspectj.AbstractAspectJAdvice;
import com.bea.core.repackaged.springframework.aop.aspectj.AspectJPointcutAdvisor;
import com.bea.core.repackaged.springframework.aop.aspectj.AspectJProxyUtils;
import com.bea.core.repackaged.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import com.bea.core.repackaged.springframework.core.Ordered;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class AspectJAwareAdvisorAutoProxyCreator extends AbstractAdvisorAutoProxyCreator {
   private static final Comparator DEFAULT_PRECEDENCE_COMPARATOR = new AspectJPrecedenceComparator();

   protected List sortAdvisors(List advisors) {
      List partiallyComparableAdvisors = new ArrayList(advisors.size());
      Iterator var3 = advisors.iterator();

      while(var3.hasNext()) {
         Advisor element = (Advisor)var3.next();
         partiallyComparableAdvisors.add(new PartiallyComparableAdvisorHolder(element, DEFAULT_PRECEDENCE_COMPARATOR));
      }

      List sorted = PartialOrder.sort(partiallyComparableAdvisors);
      if (sorted == null) {
         return super.sortAdvisors(advisors);
      } else {
         List result = new ArrayList(advisors.size());
         Iterator var5 = sorted.iterator();

         while(var5.hasNext()) {
            PartiallyComparableAdvisorHolder pcAdvisor = (PartiallyComparableAdvisorHolder)var5.next();
            result.add(pcAdvisor.getAdvisor());
         }

         return result;
      }
   }

   protected void extendAdvisors(List candidateAdvisors) {
      AspectJProxyUtils.makeAdvisorChainAspectJCapableIfNecessary(candidateAdvisors);
   }

   protected boolean shouldSkip(Class beanClass, String beanName) {
      List candidateAdvisors = this.findCandidateAdvisors();
      Iterator var4 = candidateAdvisors.iterator();

      Advisor advisor;
      do {
         if (!var4.hasNext()) {
            return super.shouldSkip(beanClass, beanName);
         }

         advisor = (Advisor)var4.next();
      } while(!(advisor instanceof AspectJPointcutAdvisor) || !((AspectJPointcutAdvisor)advisor).getAspectName().equals(beanName));

      return true;
   }

   private static class PartiallyComparableAdvisorHolder implements PartialOrder.PartialComparable {
      private final Advisor advisor;
      private final Comparator comparator;

      public PartiallyComparableAdvisorHolder(Advisor advisor, Comparator comparator) {
         this.advisor = advisor;
         this.comparator = comparator;
      }

      public int compareTo(Object obj) {
         Advisor otherAdvisor = ((PartiallyComparableAdvisorHolder)obj).advisor;
         return this.comparator.compare(this.advisor, otherAdvisor);
      }

      public int fallbackCompareTo(Object obj) {
         return 0;
      }

      public Advisor getAdvisor() {
         return this.advisor;
      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         Advice advice = this.advisor.getAdvice();
         sb.append(ClassUtils.getShortName(advice.getClass()));
         sb.append(": ");
         if (this.advisor instanceof Ordered) {
            sb.append("order ").append(((Ordered)this.advisor).getOrder()).append(", ");
         }

         if (advice instanceof AbstractAspectJAdvice) {
            AbstractAspectJAdvice ajAdvice = (AbstractAspectJAdvice)advice;
            sb.append(ajAdvice.getAspectName());
            sb.append(", declaration order ");
            sb.append(ajAdvice.getDeclarationOrder());
         }

         return sb.toString();
      }
   }
}
