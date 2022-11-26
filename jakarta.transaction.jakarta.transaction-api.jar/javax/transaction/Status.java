package javax.transaction;

public interface Status {
   int STATUS_ACTIVE = 0;
   int STATUS_MARKED_ROLLBACK = 1;
   int STATUS_PREPARED = 2;
   int STATUS_COMMITTED = 3;
   int STATUS_ROLLEDBACK = 4;
   int STATUS_UNKNOWN = 5;
   int STATUS_NO_TRANSACTION = 6;
   int STATUS_PREPARING = 7;
   int STATUS_COMMITTING = 8;
   int STATUS_ROLLING_BACK = 9;
}
