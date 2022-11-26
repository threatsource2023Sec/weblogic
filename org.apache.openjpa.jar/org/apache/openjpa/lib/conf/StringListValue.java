package org.apache.openjpa.lib.conf;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.ParseException;
import serp.util.Strings;

public class StringListValue extends Value {
   public static final String[] EMPTY = new String[0];
   private static final Localizer s_loc = Localizer.forPackage(StringListValue.class);
   private String[] _values;

   public StringListValue(String prop) {
      super(prop);
      this._values = EMPTY;
   }

   public void set(String[] values) {
      this.assertChangeable();
      this._values = values == null ? EMPTY : values;
      this.valueChanged();
   }

   public String[] get() {
      return this._values;
   }

   public Class getValueType() {
      return String[].class;
   }

   public String unalias(String str) {
      String[] aliases = this.getAliases();
      if (aliases.length > 0 && str != null) {
         str = str.trim();
         if (str.length() <= 0) {
            return super.unalias(str);
         } else if (str.equals(",")) {
            throw new ParseException(s_loc.get("invalid-list-config", this.getProperty(), str, this.getAliasList()));
         } else {
            StringBuffer retv = new StringBuffer();
            String[] vals = str.split(",", 0);

            for(int i = 0; i < vals.length; ++i) {
               String iString = vals[i] = vals[i].trim();
               boolean found = false;
               if (i > 0) {
                  retv.append(',');
               }

               for(int x = 0; x < aliases.length; x += 2) {
                  if (StringUtils.equals(iString, aliases[x]) || StringUtils.equals(iString, aliases[x + 1])) {
                     retv.append(aliases[x + 1]);
                     found = true;
                     break;
                  }
               }

               if (!found) {
                  if (this.isAliasListComprehensive()) {
                     throw new ParseException(s_loc.get("invalid-list-config", this.getProperty(), str, this.getAliasList()));
                  }

                  retv.append(iString);
               }
            }

            return retv.toString();
         }
      } else {
         return super.unalias(str);
      }
   }

   protected String getInternalString() {
      return Strings.join(this._values, ", ");
   }

   protected void setInternalString(String val) {
      String[] vals = Strings.split(val, ",", 0);
      if (vals != null) {
         for(int i = 0; i < vals.length; ++i) {
            vals[i] = vals[i].trim();
         }
      }

      this.set(vals);
   }

   protected void setInternalObject(Object obj) {
      this.set((String[])((String[])obj));
   }

   protected List getAliasList() {
      return Arrays.asList(this.getAliases());
   }
}
