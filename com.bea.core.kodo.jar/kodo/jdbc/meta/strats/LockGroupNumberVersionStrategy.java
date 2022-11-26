package kodo.jdbc.meta.strats;

import serp.util.Numbers;

public class LockGroupNumberVersionStrategy extends ColumnPerLockGroupVersionStrategy {
   public static final String ALIAS = "version-number";
   private Number _initial = Numbers.valueOf(1);

   public void setInitialValue(int initial) {
      this._initial = Numbers.valueOf(initial);
   }

   public int getInitialValue() {
      return this._initial.intValue();
   }

   public String getAlias() {
      return "version-number";
   }

   protected int getJavaType() {
      return 5;
   }

   protected Object nextVersion(Object version) {
      return version == null ? this._initial : Numbers.valueOf(((Number)version).intValue() + 1);
   }
}
