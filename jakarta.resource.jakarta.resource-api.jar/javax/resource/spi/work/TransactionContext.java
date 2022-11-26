package javax.resource.spi.work;

public class TransactionContext extends ExecutionContext implements WorkContext {
   private static final long serialVersionUID = 6205067498708597824L;

   public String getDescription() {
      return "Transaction Context";
   }

   public String getName() {
      return "TransactionContext";
   }
}
