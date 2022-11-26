package kodo.jdbc.meta.strats;

import java.sql.Timestamp;

public class LockGroupTimestampVersionStrategy extends ColumnPerLockGroupVersionStrategy {
   public static final String ALIAS = "timestamp";

   public String getAlias() {
      return "timestamp";
   }

   protected int getJavaType() {
      return 1011;
   }

   protected Object nextVersion(Object version) {
      return new Timestamp(System.currentTimeMillis());
   }
}
