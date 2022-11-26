package org.glassfish.tyrus.core.uri.internal;

import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class PatternWithGroups {
   private static final int[] EMPTY_INT_ARRAY = new int[0];
   public static final PatternWithGroups EMPTY = new PatternWithGroups();
   private final String regex;
   private final Pattern regexPattern;
   private final int[] groupIndexes;
   private static final EmptyStringMatchResult EMPTY_STRING_MATCH_RESULT = new EmptyStringMatchResult();

   protected PatternWithGroups() {
      this.regex = "";
      this.regexPattern = null;
      this.groupIndexes = EMPTY_INT_ARRAY;
   }

   public PatternWithGroups(String regex) throws PatternSyntaxException {
      this(regex, EMPTY_INT_ARRAY);
   }

   public PatternWithGroups(String regex, int[] groupIndexes) throws PatternSyntaxException {
      this(compile(regex), groupIndexes);
   }

   private static Pattern compile(String regex) throws PatternSyntaxException {
      return regex != null && !regex.isEmpty() ? Pattern.compile(regex) : null;
   }

   public PatternWithGroups(Pattern regexPattern) throws IllegalArgumentException {
      this(regexPattern, EMPTY_INT_ARRAY);
   }

   public PatternWithGroups(Pattern regexPattern, int[] groupIndexes) throws IllegalArgumentException {
      if (regexPattern == null) {
         throw new IllegalArgumentException();
      } else {
         this.regex = regexPattern.toString();
         this.regexPattern = regexPattern;
         this.groupIndexes = (int[])groupIndexes.clone();
      }
   }

   public final String getRegex() {
      return this.regex;
   }

   public final int[] getGroupIndexes() {
      return (int[])this.groupIndexes.clone();
   }

   public final MatchResult match(CharSequence cs) {
      if (cs == null) {
         return this.regexPattern == null ? EMPTY_STRING_MATCH_RESULT : null;
      } else if (this.regexPattern == null) {
         return null;
      } else {
         Matcher m = this.regexPattern.matcher(cs);
         if (!m.matches()) {
            return null;
         } else if (cs.length() == 0) {
            return EMPTY_STRING_MATCH_RESULT;
         } else {
            return (MatchResult)(this.groupIndexes.length > 0 ? new GroupIndexMatchResult(m) : m);
         }
      }
   }

   public final boolean match(CharSequence cs, List groupValues) throws IllegalArgumentException {
      if (groupValues == null) {
         throw new IllegalArgumentException();
      } else if (cs != null && cs.length() != 0) {
         if (this.regexPattern == null) {
            return false;
         } else {
            Matcher m = this.regexPattern.matcher(cs);
            if (!m.matches()) {
               return false;
            } else {
               groupValues.clear();
               int i;
               if (this.groupIndexes.length > 0) {
                  for(i = 0; i < this.groupIndexes.length; ++i) {
                     groupValues.add(m.group(this.groupIndexes[i]));
                  }
               } else {
                  for(i = 1; i <= m.groupCount(); ++i) {
                     groupValues.add(m.group(i));
                  }
               }

               return true;
            }
         }
      } else {
         return this.regexPattern == null;
      }
   }

   public final boolean match(CharSequence cs, List groupNames, Map groupValues) throws IllegalArgumentException {
      if (groupValues == null) {
         throw new IllegalArgumentException();
      } else if (cs != null && cs.length() != 0) {
         if (this.regexPattern == null) {
            return false;
         } else {
            Matcher m = this.regexPattern.matcher(cs);
            if (!m.matches()) {
               return false;
            } else {
               groupValues.clear();

               for(int i = 0; i < groupNames.size(); ++i) {
                  String name = (String)groupNames.get(i);
                  String currentValue = m.group(this.groupIndexes.length > 0 ? this.groupIndexes[i] : i + 1);
                  String previousValue = (String)groupValues.get(name);
                  if (previousValue != null && !previousValue.equals(currentValue)) {
                     return false;
                  }

                  groupValues.put(name, currentValue);
               }

               return true;
            }
         }
      } else {
         return this.regexPattern == null;
      }
   }

   public final int hashCode() {
      return this.regex.hashCode();
   }

   public final boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         PatternWithGroups that = (PatternWithGroups)obj;
         return this.regex == that.regex || this.regex != null && this.regex.equals(that.regex);
      }
   }

   public final String toString() {
      return this.regex;
   }

   private final class GroupIndexMatchResult implements MatchResult {
      private final MatchResult result;

      GroupIndexMatchResult(MatchResult r) {
         this.result = r;
      }

      public int start() {
         return this.result.start();
      }

      public int start(int group) {
         if (group > this.groupCount()) {
            throw new IndexOutOfBoundsException();
         } else {
            return group > 0 ? this.result.start(PatternWithGroups.this.groupIndexes[group - 1]) : this.result.start();
         }
      }

      public int end() {
         return this.result.end();
      }

      public int end(int group) {
         if (group > this.groupCount()) {
            throw new IndexOutOfBoundsException();
         } else {
            return group > 0 ? this.result.end(PatternWithGroups.this.groupIndexes[group - 1]) : this.result.end();
         }
      }

      public String group() {
         return this.result.group();
      }

      public String group(int group) {
         if (group > this.groupCount()) {
            throw new IndexOutOfBoundsException();
         } else {
            return group > 0 ? this.result.group(PatternWithGroups.this.groupIndexes[group - 1]) : this.result.group();
         }
      }

      public int groupCount() {
         return PatternWithGroups.this.groupIndexes.length;
      }
   }

   private static final class EmptyStringMatchResult implements MatchResult {
      private EmptyStringMatchResult() {
      }

      public int start() {
         return 0;
      }

      public int start(int group) {
         if (group != 0) {
            throw new IndexOutOfBoundsException();
         } else {
            return this.start();
         }
      }

      public int end() {
         return 0;
      }

      public int end(int group) {
         if (group != 0) {
            throw new IndexOutOfBoundsException();
         } else {
            return this.end();
         }
      }

      public String group() {
         return "";
      }

      public String group(int group) {
         if (group != 0) {
            throw new IndexOutOfBoundsException();
         } else {
            return this.group();
         }
      }

      public int groupCount() {
         return 0;
      }

      // $FF: synthetic method
      EmptyStringMatchResult(Object x0) {
         this();
      }
   }
}
