package com.bea.xbean.regex;

import com.bea.xbean.common.XMLChar;
import java.util.HashMap;
import java.util.Map;

public class SchemaRegularExpression extends RegularExpression {
   static final Map knownPatterns = buildKnownPatternMap();

   private SchemaRegularExpression(String pattern) {
      super(pattern, "X");
   }

   public static RegularExpression forPattern(String s) {
      SchemaRegularExpression tre = (SchemaRegularExpression)knownPatterns.get(s);
      return (RegularExpression)(tre != null ? tre : new RegularExpression(s, "X"));
   }

   private static Map buildKnownPatternMap() {
      Map result = new HashMap();
      result.put("\\c+", new SchemaRegularExpression("\\c+") {
         public boolean matches(String s) {
            return XMLChar.isValidNmtoken(s);
         }
      });
      result.put("\\i\\c*", new SchemaRegularExpression("\\i\\c*") {
         public boolean matches(String s) {
            return XMLChar.isValidName(s);
         }
      });
      result.put("[\\i-[:]][\\c-[:]]*", new SchemaRegularExpression("[\\i-[:]][\\c-[:]]*") {
         public boolean matches(String s) {
            return XMLChar.isValidNCName(s);
         }
      });
      return result;
   }

   // $FF: synthetic method
   SchemaRegularExpression(String x0, Object x1) {
      this(x0);
   }
}
