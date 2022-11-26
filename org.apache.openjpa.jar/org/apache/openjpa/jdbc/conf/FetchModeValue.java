package org.apache.openjpa.jdbc.conf;

import org.apache.openjpa.lib.conf.IntValue;

public class FetchModeValue extends IntValue {
   public static final String EAGER_NONE = "none";
   public static final String EAGER_JOIN = "join";
   public static final String EAGER_PARALLEL = "parallel";
   private static String[] ALIASES = new String[]{"parallel", String.valueOf(2), "join", String.valueOf(1), "none", String.valueOf(0), "multiple", String.valueOf(2), "single", String.valueOf(1)};

   public FetchModeValue(String prop) {
      super(prop);
      this.setAliases(ALIASES);
      this.setAliasListComprehensive(true);
   }
}
