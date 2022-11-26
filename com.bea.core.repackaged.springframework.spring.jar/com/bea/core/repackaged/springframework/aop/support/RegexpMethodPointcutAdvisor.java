package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.springframework.aop.Pointcut;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.io.Serializable;

public class RegexpMethodPointcutAdvisor extends AbstractGenericPointcutAdvisor {
   @Nullable
   private String[] patterns;
   @Nullable
   private AbstractRegexpMethodPointcut pointcut;
   private final Object pointcutMonitor = new SerializableMonitor();

   public RegexpMethodPointcutAdvisor() {
   }

   public RegexpMethodPointcutAdvisor(Advice advice) {
      this.setAdvice(advice);
   }

   public RegexpMethodPointcutAdvisor(String pattern, Advice advice) {
      this.setPattern(pattern);
      this.setAdvice(advice);
   }

   public RegexpMethodPointcutAdvisor(String[] patterns, Advice advice) {
      this.setPatterns(patterns);
      this.setAdvice(advice);
   }

   public void setPattern(String pattern) {
      this.setPatterns(pattern);
   }

   public void setPatterns(String... patterns) {
      this.patterns = patterns;
   }

   public Pointcut getPointcut() {
      synchronized(this.pointcutMonitor) {
         if (this.pointcut == null) {
            this.pointcut = this.createPointcut();
            if (this.patterns != null) {
               this.pointcut.setPatterns(this.patterns);
            }
         }

         return this.pointcut;
      }
   }

   protected AbstractRegexpMethodPointcut createPointcut() {
      return new JdkRegexpMethodPointcut();
   }

   public String toString() {
      return this.getClass().getName() + ": advice [" + this.getAdvice() + "], pointcut patterns " + ObjectUtils.nullSafeToString((Object[])this.patterns);
   }

   private static class SerializableMonitor implements Serializable {
      private SerializableMonitor() {
      }

      // $FF: synthetic method
      SerializableMonitor(Object x0) {
         this();
      }
   }
}
