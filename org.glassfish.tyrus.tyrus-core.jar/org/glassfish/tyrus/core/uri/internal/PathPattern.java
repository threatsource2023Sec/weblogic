package org.glassfish.tyrus.core.uri.internal;

import java.util.Comparator;

public final class PathPattern extends PatternWithGroups {
   public static final PathPattern EMPTY_PATTERN = new PathPattern();
   public static final PathPattern END_OF_PATH_PATTERN;
   public static final PathPattern OPEN_ROOT_PATH_PATTERN;
   public static final Comparator COMPARATOR;
   private final UriTemplate template;

   public static PathPattern asClosed(PathPattern pattern) {
      return new PathPattern(pattern.getTemplate().getTemplate(), PathPattern.RightHandPath.capturingZeroSegments);
   }

   private PathPattern() {
      this.template = UriTemplate.EMPTY;
   }

   public PathPattern(String template) {
      this(new PathTemplate(template));
   }

   public PathPattern(PathTemplate template) {
      super(postfixWithCapturingGroup(template.getPattern().getRegex()), addIndexForRightHandPathCapturingGroup(template.getNumberOfRegexGroups(), template.getPattern().getGroupIndexes()));
      this.template = template;
   }

   public PathPattern(String template, RightHandPath rhpp) {
      this(new PathTemplate(template), rhpp);
   }

   public PathPattern(PathTemplate template, RightHandPath rhpp) {
      super(postfixWithCapturingGroup(template.getPattern().getRegex(), rhpp), addIndexForRightHandPathCapturingGroup(template.getNumberOfRegexGroups(), template.getPattern().getGroupIndexes()));
      this.template = template;
   }

   public UriTemplate getTemplate() {
      return this.template;
   }

   private static String postfixWithCapturingGroup(String regex) {
      return postfixWithCapturingGroup(regex, PathPattern.RightHandPath.capturingZeroOrMoreSegments);
   }

   private static String postfixWithCapturingGroup(String regex, RightHandPath rhpp) {
      if (regex.endsWith("/")) {
         regex = regex.substring(0, regex.length() - 1);
      }

      return regex + rhpp.getRegex();
   }

   private static int[] addIndexForRightHandPathCapturingGroup(int numberOfGroups, int[] indexes) {
      if (indexes.length == 0) {
         return indexes;
      } else {
         int[] cgIndexes = new int[indexes.length + 1];
         System.arraycopy(indexes, 0, cgIndexes, 0, indexes.length);
         cgIndexes[indexes.length] = numberOfGroups + 1;
         return cgIndexes;
      }
   }

   static {
      END_OF_PATH_PATTERN = new PathPattern("", PathPattern.RightHandPath.capturingZeroSegments);
      OPEN_ROOT_PATH_PATTERN = new PathPattern("", PathPattern.RightHandPath.capturingZeroOrMoreSegments);
      COMPARATOR = new Comparator() {
         public int compare(PathPattern o1, PathPattern o2) {
            return UriTemplate.COMPARATOR.compare(o1.template, o2.template);
         }
      };
   }

   public static enum RightHandPath {
      capturingZeroOrMoreSegments("(/.*)?"),
      capturingZeroSegments("(/)?");

      private final String regex;

      private RightHandPath(String regex) {
         this.regex = regex;
      }

      private String getRegex() {
         return this.regex;
      }
   }
}
