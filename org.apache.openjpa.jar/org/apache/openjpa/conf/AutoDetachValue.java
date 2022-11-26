package org.apache.openjpa.conf;

import java.util.ArrayList;
import java.util.List;
import org.apache.openjpa.lib.conf.StringListValue;

class AutoDetachValue extends StringListValue {
   public static final String DETACH_CLOSE = "close";
   public static final String DETACH_COMMIT = "commit";
   public static final String DETACH_ROLLBACK = "rollback";
   public static final String DETACH_NONTXREAD = "nontx-read";
   private static String[] ALIASES = new String[]{"close", String.valueOf(2), "commit", String.valueOf(4), "nontx-read", String.valueOf(8), "rollback", String.valueOf(16), "true", String.valueOf(4), "false", "0"};
   private int _flags;
   private boolean _flagsSet;

   public AutoDetachValue() {
      super("AutoDetach");
      this.setAliases(ALIASES);
      this.setAliasListComprehensive(true);
   }

   public Class getValueType() {
      return String[].class;
   }

   public void setConstant(int flags) {
      this._flags = flags;
   }

   public int getConstant() {
      if (!this._flagsSet) {
         String[] vals = this.get();

         for(int i = 0; i < vals.length; ++i) {
            this._flags |= Integer.parseInt(this.unalias(vals[i]));
         }

         this._flagsSet = true;
      }

      return this._flags;
   }

   protected List getAliasList() {
      ArrayList list = new ArrayList();

      for(int x = 0; x < ALIASES.length; x += 2) {
         list.add(ALIASES[x]);
      }

      return list;
   }
}
