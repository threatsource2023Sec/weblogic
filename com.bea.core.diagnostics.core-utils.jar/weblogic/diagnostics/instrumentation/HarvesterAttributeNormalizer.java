package weblogic.diagnostics.instrumentation;

import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import weblogic.diagnostics.harvester.AttributeNameNormalizer;

public class HarvesterAttributeNormalizer implements AttributeNameNormalizer {
   private static final String METHOD_INVOCATION_STATS_ATTR = "MethodInvocationStatistics";
   private static final String METHOD_ALLOCATION_STATS_ATTR = "MethodMemoryAllocationStatistics";
   private static final String REGEX_PATTERN = "(\\((.)*?\\))";
   private static final int KEY_COUNT = 4;

   public String getNormalizedAttributeName(String rawAttributeSpec) {
      boolean isAlloc = false;
      if (rawAttributeSpec.startsWith("MethodInvocationStatistics")) {
         rawAttributeSpec = rawAttributeSpec.substring("MethodInvocationStatistics".length());
      } else if (rawAttributeSpec.startsWith("MethodMemoryAllocationStatistics")) {
         isAlloc = true;
         rawAttributeSpec = rawAttributeSpec.substring("MethodMemoryAllocationStatistics".length());
      }

      rawAttributeSpec = ensureRegexGroups(rawAttributeSpec);
      HarvesterAttributeNormalizerLexer l = new HarvesterAttributeNormalizerLexer(new StringReader(rawAttributeSpec));
      HarvesterAttributeNormalizerParser p = new HarvesterAttributeNormalizerParser(l);

      String s;
      try {
         s = p.normalizeAttributeSpec();
      } catch (Exception var7) {
         throw new IllegalArgumentException("Invalid attribute spec " + var7.getMessage(), var7);
      }

      return isAlloc ? "MethodMemoryAllocationStatistics" + s : "MethodInvocationStatistics" + s;
   }

   private static String ensureRegexGroups(String expr) {
      if (expr == null) {
         expr = "";
      }

      Pattern p = Pattern.compile("(\\((.)*?\\))");
      Matcher m = p.matcher(expr);
      StringBuilder result = new StringBuilder();

      for(int i = 0; i < 4; ++i) {
         if (m.find()) {
            result.append(m.group());
         } else {
            result.append("(*)");
         }
      }

      return result.toString();
   }

   public String getPartiallyNormalizedAllocationAttributeName(String rawAttributeSpec) {
      return this.getPartiallyNormalizedAttributeName("MethodMemoryAllocationStatistics", rawAttributeSpec);
   }

   public String getPartiallyNormalizedInvocationAttributeName(String rawAttributeSpec) {
      return this.getPartiallyNormalizedAttributeName("MethodInvocationStatistics", rawAttributeSpec);
   }

   private String getPartiallyNormalizedAttributeName(String baseAttribute, String rawAttributeSpec) {
      String expr = rawAttributeSpec;
      if (rawAttributeSpec == null) {
         expr = "";
      }

      if (expr.startsWith(baseAttribute)) {
         expr = expr.substring(baseAttribute.length());
      }

      Pattern p = Pattern.compile("(\\((.)*?\\))");
      Matcher m = p.matcher(expr);
      StringBuilder result = new StringBuilder();

      for(int i = 0; i < 4 && m.find(); ++i) {
         String s = m.group();
         switch (i) {
            case 0:
               s = this.normalizeClassName(s);
               break;
            case 1:
               s = this.normalizeMethodName(s);
               break;
            case 2:
               s = this.normalizeMethodParams(s);
               break;
            case 3:
               s = this.normalizeMethodStats(s);
         }

         result.append(s);
      }

      return baseAttribute + result.toString();
   }

   private String normalizeClassName(String className) {
      HarvesterAttributeNormalizerLexer l = new HarvesterAttributeNormalizerLexer(new StringReader(className));
      HarvesterAttributeNormalizerParser p = new HarvesterAttributeNormalizerParser(l);

      try {
         String s = p.classNameKey();
         return s;
      } catch (Exception var6) {
         throw new IllegalArgumentException("Invalid attribute spec " + var6.getMessage(), var6);
      }
   }

   private String normalizeMethodName(String methodName) {
      HarvesterAttributeNormalizerLexer l = new HarvesterAttributeNormalizerLexer(new StringReader(methodName));
      HarvesterAttributeNormalizerParser p = new HarvesterAttributeNormalizerParser(l);

      try {
         String s = p.methodNameKey();
         return s;
      } catch (Exception var6) {
         throw new IllegalArgumentException("Invalid attribute spec " + var6.getMessage(), var6);
      }
   }

   private String normalizeMethodParams(String methodParams) {
      HarvesterAttributeNormalizerLexer l = new HarvesterAttributeNormalizerLexer(new StringReader(methodParams));
      HarvesterAttributeNormalizerParser p = new HarvesterAttributeNormalizerParser(l);

      try {
         String s = p.methodParamsKey();
         return s;
      } catch (Exception var6) {
         throw new IllegalArgumentException("Invalid attribute spec " + var6.getMessage(), var6);
      }
   }

   private String normalizeMethodStats(String methodStats) {
      HarvesterAttributeNormalizerLexer l = new HarvesterAttributeNormalizerLexer(new StringReader(methodStats));
      HarvesterAttributeNormalizerParser p = new HarvesterAttributeNormalizerParser(l);

      try {
         String s = p.methodStatsKey();
         return s;
      } catch (Exception var6) {
         throw new IllegalArgumentException("Invalid attribute spec " + var6.getMessage(), var6);
      }
   }

   public static void main(String[] args) {
      HarvesterAttributeNormalizer a = new HarvesterAttributeNormalizer();
      String s = a.getPartiallyNormalizedInvocationAttributeName(args[0]);
      System.out.println(s);
   }
}
