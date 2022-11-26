package com.bea.core.repackaged.springframework.core.env;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Predicate;

final class ProfilesParser {
   private ProfilesParser() {
   }

   static Profiles parse(String... expressions) {
      Assert.notEmpty((Object[])expressions, (String)"Must specify at least one profile");
      Profiles[] parsed = new Profiles[expressions.length];

      for(int i = 0; i < expressions.length; ++i) {
         parsed[i] = parseExpression(expressions[i]);
      }

      return new ParsedProfiles(expressions, parsed);
   }

   private static Profiles parseExpression(String expression) {
      Assert.hasText(expression, () -> {
         return "Invalid profile expression [" + expression + "]: must contain text";
      });
      StringTokenizer tokens = new StringTokenizer(expression, "()&|!", true);
      return parseTokens(expression, tokens);
   }

   private static Profiles parseTokens(String expression, StringTokenizer tokens) {
      return parseTokens(expression, tokens, ProfilesParser.Context.NONE);
   }

   private static Profiles parseTokens(String expression, StringTokenizer tokens, Context context) {
      List elements = new ArrayList();
      Operator operator = null;

      while(true) {
         while(true) {
            String token;
            do {
               if (!tokens.hasMoreTokens()) {
                  return merge(expression, elements, operator);
               }

               token = tokens.nextToken().trim();
            } while(token.isEmpty());

            switch (token) {
               case "(":
                  Profiles contents = parseTokens(expression, tokens, ProfilesParser.Context.BRACKET);
                  if (context == ProfilesParser.Context.INVERT) {
                     return contents;
                  }

                  elements.add(contents);
                  break;
               case "&":
                  assertWellFormed(expression, operator == null || operator == ProfilesParser.Operator.AND);
                  operator = ProfilesParser.Operator.AND;
                  break;
               case "|":
                  assertWellFormed(expression, operator == null || operator == ProfilesParser.Operator.OR);
                  operator = ProfilesParser.Operator.OR;
                  break;
               case "!":
                  elements.add(not(parseTokens(expression, tokens, ProfilesParser.Context.INVERT)));
                  break;
               case ")":
                  Profiles merged = merge(expression, elements, operator);
                  if (context == ProfilesParser.Context.BRACKET) {
                     return merged;
                  }

                  elements.clear();
                  elements.add(merged);
                  operator = null;
                  break;
               default:
                  Profiles value = equals(token);
                  if (context == ProfilesParser.Context.INVERT) {
                     return value;
                  }

                  elements.add(value);
            }
         }
      }
   }

   private static Profiles merge(String expression, List elements, @Nullable Operator operator) {
      assertWellFormed(expression, !elements.isEmpty());
      if (elements.size() == 1) {
         return (Profiles)elements.get(0);
      } else {
         Profiles[] profiles = (Profiles[])elements.toArray(new Profiles[0]);
         return operator == ProfilesParser.Operator.AND ? and(profiles) : or(profiles);
      }
   }

   private static void assertWellFormed(String expression, boolean wellFormed) {
      Assert.isTrue(wellFormed, () -> {
         return "Malformed profile expression [" + expression + "]";
      });
   }

   private static Profiles or(Profiles... profiles) {
      return (activeProfile) -> {
         return Arrays.stream(profiles).anyMatch(isMatch(activeProfile));
      };
   }

   private static Profiles and(Profiles... profiles) {
      return (activeProfile) -> {
         return Arrays.stream(profiles).allMatch(isMatch(activeProfile));
      };
   }

   private static Profiles not(Profiles profiles) {
      return (activeProfile) -> {
         return !profiles.matches(activeProfile);
      };
   }

   private static Profiles equals(String profile) {
      return (activeProfile) -> {
         return activeProfile.test(profile);
      };
   }

   private static Predicate isMatch(Predicate activeProfile) {
      return (profiles) -> {
         return profiles.matches(activeProfile);
      };
   }

   private static class ParsedProfiles implements Profiles {
      private final String[] expressions;
      private final Profiles[] parsed;

      ParsedProfiles(String[] expressions, Profiles[] parsed) {
         this.expressions = expressions;
         this.parsed = parsed;
      }

      public boolean matches(Predicate activeProfiles) {
         Profiles[] var2 = this.parsed;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Profiles candidate = var2[var4];
            if (candidate.matches(activeProfiles)) {
               return true;
            }
         }

         return false;
      }

      public String toString() {
         return StringUtils.arrayToDelimitedString(this.expressions, " or ");
      }
   }

   private static enum Context {
      NONE,
      INVERT,
      BRACKET;
   }

   private static enum Operator {
      AND,
      OR;
   }
}
