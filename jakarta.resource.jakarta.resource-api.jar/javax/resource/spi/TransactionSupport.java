package javax.resource.spi;

import java.io.Serializable;

public interface TransactionSupport extends Serializable {
   TransactionSupportLevel getTransactionSupport();

   public static enum TransactionSupportLevel {
      NoTransaction,
      LocalTransaction,
      XATransaction;
   }
}
