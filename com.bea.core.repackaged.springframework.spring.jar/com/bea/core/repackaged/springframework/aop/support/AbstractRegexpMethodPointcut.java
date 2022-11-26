package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

public abstract class AbstractRegexpMethodPointcut extends StaticMethodMatcherPointcut implements Serializable {
   private String[] patterns = new String[0];
   private String[] excludedPatterns = new String[0];

   public void setPattern(String pattern) {
      this.setPatterns(pattern);
   }

   public void setPatterns(String... patterns) {
      Assert.notEmpty((Object[])patterns, (String)"'patterns' must not be empty");
      this.patterns = new String[patterns.length];

      for(int i = 0; i < patterns.length; ++i) {
         this.patterns[i] = StringUtils.trimWhitespace(patterns[i]);
      }

      this.initPatternRepresentation(this.patterns);
   }

   public String[] getPatterns() {
      return this.patterns;
   }

   public void setExcludedPattern(String excludedPattern) {
      this.setExcludedPatterns(excludedPattern);
   }

   public void setExcludedPatterns(String... excludedPatterns) {
      Assert.notEmpty((Object[])excludedPatterns, (String)"'excludedPatterns' must not be empty");
      this.excludedPatterns = new String[excludedPatterns.length];

      for(int i = 0; i < excludedPatterns.length; ++i) {
         this.excludedPatterns[i] = StringUtils.trimWhitespace(excludedPatterns[i]);
      }

      this.initExcludedPatternRepresentation(this.excludedPatterns);
   }

   public String[] getExcludedPatterns() {
      return this.excludedPatterns;
   }

   public boolean matches(Method method, Class targetClass) {
      return this.matchesPattern(ClassUtils.getQualifiedMethodName(method, targetClass)) || targetClass != method.getDeclaringClass() && this.matchesPattern(ClassUtils.getQualifiedMethodName(method, method.getDeclaringClass()));
   }

   protected boolean matchesPattern(String signatureString) {
      for(int i = 0; i < this.patterns.length; ++i) {
         boolean matched = this.matches(signatureString, i);
         if (matched) {
            for(int j = 0; j < this.excludedPatterns.length; ++j) {
               boolean excluded = this.matchesExclusion(signatureString, j);
               if (excluded) {
                  return false;
               }
            }

            return true;
         }
      }

      return false;
   }

   protected abstract void initPatternRepresentation(String[] var1) throws IllegalArgumentException;

   protected abstract void initExcludedPatternRepresentation(String[] var1) throws IllegalArgumentException;

   protected abstract boolean matches(String var1, int var2);

   protected abstract boolean matchesExclusion(String var1, int var2);

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof AbstractRegexpMethodPointcut)) {
         return false;
      } else {
         AbstractRegexpMethodPointcut otherPointcut = (AbstractRegexpMethodPointcut)other;
         return Arrays.equals(this.patterns, otherPointcut.patterns) && Arrays.equals(this.excludedPatterns, otherPointcut.excludedPatterns);
      }
   }

   public int hashCode() {
      int result = 27;
      String[] var2 = this.patterns;
      int var3 = var2.length;

      int var4;
      String excludedPattern;
      for(var4 = 0; var4 < var3; ++var4) {
         excludedPattern = var2[var4];
         result = 13 * result + excludedPattern.hashCode();
      }

      var2 = this.excludedPatterns;
      var3 = var2.length;

      for(var4 = 0; var4 < var3; ++var4) {
         excludedPattern = var2[var4];
         result = 13 * result + excludedPattern.hashCode();
      }

      return result;
   }

   public String toString() {
      return this.getClass().getName() + ": patterns " + ObjectUtils.nullSafeToString((Object[])this.patterns) + ", excluded patterns " + ObjectUtils.nullSafeToString((Object[])this.excludedPatterns);
   }
}
