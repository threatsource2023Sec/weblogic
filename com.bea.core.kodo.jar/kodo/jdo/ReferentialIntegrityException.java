package kodo.jdo;

public class ReferentialIntegrityException extends UserException {
   public static final int IV_UNKNOWN = 0;
   public static final int IV_DUPLICATE_OID = 1;
   public static final int IV_UNIQUE = 2;
   public static final int IV_REFERENCE = 3;
   public static final int IV_MIXED = 4;
   private final int _iv;

   public ReferentialIntegrityException(String msg, Throwable[] nested, Object failed, int iv) {
      super(msg, nested, failed);
      this._iv = iv;
   }

   public int getIntegrityViolation() {
      return this._iv;
   }

   public int getType() {
      return 2;
   }

   public int getSubtype() {
      return 4;
   }

   protected UserException newSerializableInstance(String msg, Throwable[] nested, Object failed) {
      return new ReferentialIntegrityException(msg, nested, failed, this._iv);
   }
}
