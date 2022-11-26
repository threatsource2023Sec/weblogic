package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.kernel.exps.WildcardMatch;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UserException;

/** @deprecated */
public class JDBCWildcardMatch extends WildcardMatch implements JDBCFilterListener {
   private static final Localizer _loc = Localizer.forPackage(JDBCWildcardMatch.class);

   public void appendTo(SQLBuffer sql, FilterValue target, FilterValue[] args, ClassMapping type, JDBCStore store) {
      if (!args[0].isConstant()) {
         throw new UserException(_loc.get("const-only", (Object)"wildcardMatch"));
      } else {
         Object val = args[0].getValue();
         target.appendTo(sql);
         if (val == null) {
            sql.append(" IS ").appendValue((Object)null);
         } else {
            String wild = val.toString().replace('*', '%').replace('?', '_');
            sql.append(" LIKE ").appendValue(wild);
         }

      }
   }
}
