package weblogic.transaction;

import java.util.Map;

public interface Synchronization extends javax.transaction.Synchronization {
   String IS_LOCAL_CONNECTION_COMMIT_OR_ROLLBACK_ALLOWED_IN_BEFORECOMPLETION = "IS_LOCAL_CONNECTION_COMMIT_OR_ROLLBACK_ALLOWED_IN_BEFORECOMPLETION";
   String SQL_STRING_TO_EXECUTE_LAST_IN_BEFORECOMPLETION = "SQL_STRING_TO_EXECUTE_LAST_IN_BEFORECOMPLETION";

   boolean beforeCompletion(Map var1);
}
