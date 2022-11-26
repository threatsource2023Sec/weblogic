package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.SimpleRegex;
import serp.util.Strings;

class MatchesExpression extends CompareExpression {
   private static final Localizer _loc = Localizer.forPackage(MatchesExpression.class);
   private final String _single;
   private final String _multi;
   private final boolean _affirmation;

   public MatchesExpression(Val val1, Val val2, String single, String multi, String escape, boolean affirmation) {
      super(val1, val2);
      this._single = single;
      this._multi = multi;
      if (escape != null) {
         throw new IllegalArgumentException(_loc.get("escape-for-inmem-query-not-supported").getMessage());
      } else {
         this._affirmation = affirmation;
      }
   }

   protected boolean compare(Object o1, Object o2) {
      if (o1 != null && o2 != null) {
         String str = o2.toString();
         int idx = str.indexOf("(?i)");
         boolean uncase = false;
         if (idx != -1) {
            uncase = true;
            if (idx + 4 < str.length()) {
               str = str.substring(0, idx) + str.substring(idx + 4);
            } else {
               str = str.substring(0, idx);
            }
         }

         str = Strings.replace(str, this._multi, ".*");
         str = Strings.replace(str, this._single, ".");
         SimpleRegex re = new SimpleRegex(str, uncase);
         boolean matches = re.matches(o1.toString());
         return this._affirmation ? matches : !matches;
      } else {
         return false;
      }
   }
}
