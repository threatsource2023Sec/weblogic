package com.bea.core.repackaged.springframework.aop.aspectj;

import com.bea.core.repackaged.aspectj.weaver.tools.PointcutParser;
import com.bea.core.repackaged.aspectj.weaver.tools.TypePatternMatcher;
import com.bea.core.repackaged.springframework.aop.ClassFilter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;

public class TypePatternClassFilter implements ClassFilter {
   private String typePattern = "";
   @Nullable
   private TypePatternMatcher aspectJTypePatternMatcher;

   public TypePatternClassFilter() {
   }

   public TypePatternClassFilter(String typePattern) {
      this.setTypePattern(typePattern);
   }

   public void setTypePattern(String typePattern) {
      Assert.notNull(typePattern, (String)"Type pattern must not be null");
      this.typePattern = typePattern;
      this.aspectJTypePatternMatcher = PointcutParser.getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution().parseTypePattern(this.replaceBooleanOperators(typePattern));
   }

   public String getTypePattern() {
      return this.typePattern;
   }

   public boolean matches(Class clazz) {
      Assert.state(this.aspectJTypePatternMatcher != null, "No type pattern has been set");
      return this.aspectJTypePatternMatcher.matches(clazz);
   }

   private String replaceBooleanOperators(String pcExpr) {
      String result = StringUtils.replace(pcExpr, " and ", " && ");
      result = StringUtils.replace(result, " or ", " || ");
      return StringUtils.replace(result, " not ", " ! ");
   }

   public boolean equals(Object other) {
      return this == other || other instanceof TypePatternClassFilter && ObjectUtils.nullSafeEquals(this.typePattern, ((TypePatternClassFilter)other).typePattern);
   }

   public int hashCode() {
      return ObjectUtils.nullSafeHashCode((Object)this.typePattern);
   }

   public String toString() {
      return this.getClass().getName() + ": " + this.typePattern;
   }
}
