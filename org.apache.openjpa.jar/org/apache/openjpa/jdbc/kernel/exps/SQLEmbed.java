package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UnsupportedException;
import org.apache.openjpa.util.UserException;

public class SQLEmbed implements JDBCFilterListener {
   public static String TAG = "sql";
   private static final Localizer _loc = Localizer.forPackage(SQLEmbed.class);

   public String getTag() {
      return TAG;
   }

   public boolean expectsArguments() {
      return true;
   }

   public boolean expectsTarget() {
      return false;
   }

   public Object evaluate(Object target, Class targetClass, Object[] args, Class[] argClasses, Object candidate, StoreContext ctx) {
      throw new UnsupportedException(_loc.get("no-in-mem", (Object)TAG));
   }

   public void appendTo(SQLBuffer buf, FilterValue target, FilterValue[] args, ClassMapping type, JDBCStore store) {
      if (!args[0].isConstant()) {
         throw new UserException(_loc.get("const-only", (Object)TAG));
      } else {
         buf.append(args[0].getValue().toString());
      }
   }

   public Class getType(Class targetClass, Class[] argClasses) {
      return Object.class;
   }
}
