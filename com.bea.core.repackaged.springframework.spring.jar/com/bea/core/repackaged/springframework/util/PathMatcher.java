package com.bea.core.repackaged.springframework.util;

import java.util.Comparator;
import java.util.Map;

public interface PathMatcher {
   boolean isPattern(String var1);

   boolean match(String var1, String var2);

   boolean matchStart(String var1, String var2);

   String extractPathWithinPattern(String var1, String var2);

   Map extractUriTemplateVariables(String var1, String var2);

   Comparator getPatternComparator(String var1);

   String combine(String var1, String var2);
}
