package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UnsupportedException;
import org.apache.openjpa.util.UserException;

public class GetColumn implements JDBCFilterListener {
   public static final String TAG = "getColumn";
   private static final Localizer _loc = Localizer.forPackage(GetColumn.class);

   public String getTag() {
      return "getColumn";
   }

   public boolean expectsArguments() {
      return true;
   }

   public boolean expectsTarget() {
      return true;
   }

   public Object evaluate(Object target, Class targetClass, Object[] args, Class[] argClasses, Object candidate, StoreContext ctx) {
      throw new UnsupportedException(_loc.get("no-in-mem", (Object)"getColumn"));
   }

   public void appendTo(SQLBuffer buf, FilterValue target, FilterValue[] args, ClassMapping type, JDBCStore store) {
      if (!args[0].isConstant()) {
         throw new UserException(_loc.get("const-only", (Object)"getColumn"));
      } else if (!target.isPath()) {
         throw new UserException(_loc.get("path-only", (Object)"getColumn"));
      } else {
         ClassMapping mapping = target.getClassMapping();
         String colName = args[0].getValue().toString();
         buf.append(target.getColumnAlias(colName, mapping.getTable()));
      }
   }

   public Class getType(Class targetClass, Class[] argClasses) {
      return Object.class;
   }
}
