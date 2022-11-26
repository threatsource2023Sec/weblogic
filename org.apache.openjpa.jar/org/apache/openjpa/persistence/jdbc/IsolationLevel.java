package org.apache.openjpa.persistence.jdbc;

public enum IsolationLevel {
   DEFAULT(-1),
   NONE(0),
   READ_UNCOMMITTED(1),
   READ_COMMITTED(2),
   REPEATABLE_READ(4),
   SERIALIZABLE(8);

   private final int _connectionConstant;

   private IsolationLevel(int connectionConstant) {
      this._connectionConstant = connectionConstant;
   }

   public int getConnectionConstant() {
      return this._connectionConstant;
   }

   public static IsolationLevel fromConnectionConstant(int constant) {
      switch (constant) {
         case -99:
         case -1:
            return DEFAULT;
         case 0:
            return NONE;
         case 1:
            return READ_UNCOMMITTED;
         case 2:
            return READ_COMMITTED;
         case 4:
            return REPEATABLE_READ;
         case 8:
            return SERIALIZABLE;
         default:
            throw new IllegalArgumentException(Integer.valueOf(constant).toString());
      }
   }
}
