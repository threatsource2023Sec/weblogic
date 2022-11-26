package com.bea.core.repackaged.springframework.aop.support;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class JdkRegexpMethodPointcut extends AbstractRegexpMethodPointcut {
   private Pattern[] compiledPatterns = new Pattern[0];
   private Pattern[] compiledExclusionPatterns = new Pattern[0];

   protected void initPatternRepresentation(String[] patterns) throws PatternSyntaxException {
      this.compiledPatterns = this.compilePatterns(patterns);
   }

   protected void initExcludedPatternRepresentation(String[] excludedPatterns) throws PatternSyntaxException {
      this.compiledExclusionPatterns = this.compilePatterns(excludedPatterns);
   }

   protected boolean matches(String pattern, int patternIndex) {
      Matcher matcher = this.compiledPatterns[patternIndex].matcher(pattern);
      return matcher.matches();
   }

   protected boolean matchesExclusion(String candidate, int patternIndex) {
      Matcher matcher = this.compiledExclusionPatterns[patternIndex].matcher(candidate);
      return matcher.matches();
   }

   private Pattern[] compilePatterns(String[] source) throws PatternSyntaxException {
      Pattern[] destination = new Pattern[source.length];

      for(int i = 0; i < source.length; ++i) {
         destination[i] = Pattern.compile(source[i]);
      }

      return destination;
   }
}
