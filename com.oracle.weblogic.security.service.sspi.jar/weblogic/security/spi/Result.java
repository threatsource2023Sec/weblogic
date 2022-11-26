package weblogic.security.spi;

public class Result {
   public static final int ABSTAIN_VALUE = 1;
   public static final int DENY_VALUE = 2;
   public static final int PERMIT_VALUE = 3;
   private int value;
   public static final Result ABSTAIN = new Result(1);
   public static final Result DENY = new Result(2);
   public static final Result PERMIT = new Result(3);

   protected Result(int value) {
      this.value = value;
   }

   public String toString() {
      if (1 == this.value) {
         return "ABSTAIN";
      } else {
         return 2 == this.value ? "DENY" : "PERMIT";
      }
   }

   public int getEnumValue() {
      return this.value;
   }

   public boolean isAbstain() {
      return this.value == 1;
   }

   public boolean isDeny() {
      return this.value == 2;
   }

   public boolean isPermit() {
      return this.value == 3;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Result)) {
         return false;
      } else {
         Result ro = (Result)other;
         return this.value == ro.value;
      }
   }

   public int hashCode() {
      int result;
      switch (this.value) {
         case 1:
            result = "ABSTAIN".hashCode();
            break;
         case 2:
            result = "DENY".hashCode();
            break;
         case 3:
            result = "PERMIT".hashCode();
            break;
         default:
            result = "UNKNOWN".hashCode();
      }

      return result;
   }

   public Result narrow() {
      return this;
   }
}
