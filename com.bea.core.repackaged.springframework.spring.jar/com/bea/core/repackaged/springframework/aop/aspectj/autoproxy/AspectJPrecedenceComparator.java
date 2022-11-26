package com.bea.core.repackaged.springframework.aop.aspectj.autoproxy;

import com.bea.core.repackaged.springframework.aop.Advisor;
import com.bea.core.repackaged.springframework.aop.aspectj.AspectJAopUtils;
import com.bea.core.repackaged.springframework.aop.aspectj.AspectJPrecedenceInformation;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAwareOrderComparator;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.Comparator;

class AspectJPrecedenceComparator implements Comparator {
   private static final int HIGHER_PRECEDENCE = -1;
   private static final int SAME_PRECEDENCE = 0;
   private static final int LOWER_PRECEDENCE = 1;
   private final Comparator advisorComparator;

   public AspectJPrecedenceComparator() {
      this.advisorComparator = AnnotationAwareOrderComparator.INSTANCE;
   }

   public AspectJPrecedenceComparator(Comparator advisorComparator) {
      Assert.notNull(advisorComparator, (String)"Advisor comparator must not be null");
      this.advisorComparator = advisorComparator;
   }

   public int compare(Advisor o1, Advisor o2) {
      int advisorPrecedence = this.advisorComparator.compare(o1, o2);
      if (advisorPrecedence == 0 && this.declaredInSameAspect(o1, o2)) {
         advisorPrecedence = this.comparePrecedenceWithinAspect(o1, o2);
      }

      return advisorPrecedence;
   }

   private int comparePrecedenceWithinAspect(Advisor advisor1, Advisor advisor2) {
      boolean oneOrOtherIsAfterAdvice = AspectJAopUtils.isAfterAdvice(advisor1) || AspectJAopUtils.isAfterAdvice(advisor2);
      int adviceDeclarationOrderDelta = this.getAspectDeclarationOrder(advisor1) - this.getAspectDeclarationOrder(advisor2);
      if (oneOrOtherIsAfterAdvice) {
         if (adviceDeclarationOrderDelta < 0) {
            return 1;
         } else {
            return adviceDeclarationOrderDelta == 0 ? 0 : -1;
         }
      } else if (adviceDeclarationOrderDelta < 0) {
         return -1;
      } else {
         return adviceDeclarationOrderDelta == 0 ? 0 : 1;
      }
   }

   private boolean declaredInSameAspect(Advisor advisor1, Advisor advisor2) {
      return this.hasAspectName(advisor1) && this.hasAspectName(advisor2) && this.getAspectName(advisor1).equals(this.getAspectName(advisor2));
   }

   private boolean hasAspectName(Advisor anAdvisor) {
      return anAdvisor instanceof AspectJPrecedenceInformation || anAdvisor.getAdvice() instanceof AspectJPrecedenceInformation;
   }

   private String getAspectName(Advisor anAdvisor) {
      AspectJPrecedenceInformation pi = AspectJAopUtils.getAspectJPrecedenceInformationFor(anAdvisor);
      Assert.state(pi != null, "Unresolvable precedence information");
      return pi.getAspectName();
   }

   private int getAspectDeclarationOrder(Advisor anAdvisor) {
      AspectJPrecedenceInformation precedenceInfo = AspectJAopUtils.getAspectJPrecedenceInformationFor(anAdvisor);
      return precedenceInfo != null ? precedenceInfo.getDeclarationOrder() : 0;
   }
}
