package weblogic.ejbgen;

public interface Constants {
   public static enum ConcurrencyStrategy {
      UNSPECIFIED,
      READ_ONLY,
      EXCLUSIVE,
      DATABASE,
      OPTIMISTIC;
   }

   public static enum Interface {
      UNSPECIFIED,
      HOME,
      REMOTE,
      LOCAL_HOME,
      LOCAL;
   }

   public static enum HomeLoadAlgorithm {
      UNSPECIFIED,
      ROUND_ROBIN,
      RANDOM,
      WEIGHT_BASED,
      ROUND_ROBIN_AFFINITY,
      RANDOM_AFFINITY,
      WEIGHT_BASED_AFFINITY;
   }

   public static enum RefType {
      UNSPECIFIED,
      ENTITY,
      SESSION;
   }

   public static enum IsolationLevel {
      UNSPECIFIED,
      TRANSACTION_SERIALIZABLE,
      TRANSACTION_READ_COMMITTED,
      TRANSACTION_READ_UNCOMMITTED,
      TRANSACTION_REPEATABLE_READ,
      TRANSACTION_READ_COMMITTED_FOR_UPDATE;
   }

   public static enum TransactionAttribute {
      UNSPECIFIED,
      NOT_SUPPORTED,
      SUPPORTS,
      REQUIRED,
      REQUIRES_NEW,
      MANDATORY,
      NEVER;
   }

   public static enum Bool {
      UNSPECIFIED,
      TRUE,
      FALSE;
   }
}
