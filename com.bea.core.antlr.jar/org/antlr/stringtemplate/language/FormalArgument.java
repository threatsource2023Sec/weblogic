package org.antlr.stringtemplate.language;

import java.util.LinkedHashMap;
import org.antlr.stringtemplate.StringTemplate;

public class FormalArgument {
   public static final int OPTIONAL = 1;
   public static final int REQUIRED = 2;
   public static final int ZERO_OR_MORE = 4;
   public static final int ONE_OR_MORE = 8;
   public static final String[] suffixes = new String[]{null, "?", "", null, "*", null, null, null, "+"};
   public static final LinkedHashMap UNKNOWN = new LinkedHashMap();
   public String name;
   public StringTemplate defaultValueST;

   public FormalArgument(String name) {
      this.name = name;
   }

   public FormalArgument(String name, StringTemplate defaultValueST) {
      this.name = name;
      this.defaultValueST = defaultValueST;
   }

   public static String getCardinalityName(int cardinality) {
      switch (cardinality) {
         case 1:
            return "optional";
         case 2:
            return "exactly one";
         case 3:
         case 5:
         case 6:
         case 7:
         default:
            return "unknown";
         case 4:
            return "zero-or-more";
         case 8:
            return "one-or-more";
      }
   }

   public boolean equals(Object o) {
      if (o != null && o instanceof FormalArgument) {
         FormalArgument other = (FormalArgument)o;
         if (!this.name.equals(other.name)) {
            return false;
         } else {
            return (this.defaultValueST == null || other.defaultValueST != null) && (this.defaultValueST != null || other.defaultValueST == null);
         }
      } else {
         return false;
      }
   }

   public String toString() {
      return this.defaultValueST != null ? this.name + "=" + this.defaultValueST : this.name;
   }
}
