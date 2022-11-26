package weblogic.work.concurrent.spi;

public class ExceedThreadLimitException extends RejectException {
   private final int limit;
   private final String limitType;
   private static final long serialVersionUID = 1L;

   public ExceedThreadLimitException(int originalLevel, String limitType) {
      this.limit = originalLevel;
      this.limitType = limitType;
   }

   public int getLimit() {
      return this.limit;
   }

   public String getLimitType() {
      return this.limitType;
   }
}
