package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.kernel.exps.StringContains;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UserException;

/** @deprecated */
public class JDBCStringContains extends StringContains implements JDBCFilterListener {
   private static final Localizer _loc = Localizer.forPackage(JDBCStringContains.class);

   public void appendTo(SQLBuffer buf, FilterValue target, FilterValue[] args, ClassMapping type, JDBCStore store) {
      if (!args[0].isConstant()) {
         throw new UserException(_loc.get("const-only", (Object)"stringContains"));
      } else {
         Object val = args[0].getValue();
         target.appendTo(buf);
         if (val == null) {
            buf.append(" IS ").appendValue((Object)null);
         } else {
            buf.append(" LIKE ").appendValue("%" + val + "%");
         }

      }
   }
}
