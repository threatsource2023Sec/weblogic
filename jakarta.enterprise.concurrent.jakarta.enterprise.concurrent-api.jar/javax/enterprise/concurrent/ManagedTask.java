package javax.enterprise.concurrent;

import java.util.Map;

public interface ManagedTask {
   String LONGRUNNING_HINT = "javax.enterprise.concurrent.LONGRUNNING_HINT";
   String TRANSACTION = "javax.enterprise.concurrent.TRANSACTION";
   String SUSPEND = "SUSPEND";
   String USE_TRANSACTION_OF_EXECUTION_THREAD = "USE_TRANSACTION_OF_EXECUTION_THREAD";
   String IDENTITY_NAME = "javax.enterprise.concurrent.IDENTITY_NAME";

   ManagedTaskListener getManagedTaskListener();

   Map getExecutionProperties();
}
