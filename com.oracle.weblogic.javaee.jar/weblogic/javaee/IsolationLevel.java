package weblogic.javaee;

public enum IsolationLevel {
   READ_UNCOMMITTED(1, "TransactionReadUncommitted"),
   READ_COMMITTED(2, "TransactionReadCommitted"),
   REPEATABLE_READ(4, "TransactionRepeatableRead"),
   SERIALIZABLE(8, "TransactionSerializable");

   private final int _connectionConstant;
   private final String _weblogicIsolationString;

   private IsolationLevel(int connectionConstant, String weblogicName) {
      this._connectionConstant = connectionConstant;
      this._weblogicIsolationString = weblogicName;
   }

   public int getConnectionConstant() {
      return this._connectionConstant;
   }

   public static IsolationLevel fromConnectionConstant(int constant) {
      switch (constant) {
         case 1:
            return READ_UNCOMMITTED;
         case 2:
            return READ_COMMITTED;
         case 3:
         case 5:
         case 6:
         case 7:
         default:
            throw new IllegalArgumentException("Invalid Connection Constant: " + constant);
         case 4:
            return REPEATABLE_READ;
         case 8:
            return SERIALIZABLE;
      }
   }

   public String getWeblogicIsolationString() {
      return this._weblogicIsolationString;
   }
}
