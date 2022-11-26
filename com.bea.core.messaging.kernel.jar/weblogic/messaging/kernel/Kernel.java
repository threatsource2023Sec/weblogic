package weblogic.messaging.kernel;

import java.util.Collection;
import java.util.Map;
import javax.transaction.xa.Xid;
import weblogic.store.gxa.GXALocalTransaction;

public interface Kernel extends Configurable {
   String PROP_STORE = "Store";
   String PROP_PAGEDIR = "PagingDirectory";
   String PROP_PAGEFILE_PARAMS = "PagingParams";
   String PROP_OBJ_HNDLR = "ObjectHandler";
   String PROP_WORK_MGR = "WorkManager";
   String PROP_LTD_WORK_MGR = "LimitedWorkManager";
   String PROP_LTD_TM_MGR_NAME = "LimitedTimerManagerName";
   String PROP_DIRECT_TM_MGR_NAME = "DirectTimerManagerName";
   String PROP_MSG_BUF = "MessageBufferSize";
   String PROP_MAX_MSG = "MaximumMessageSize";
   String PROP_ALTERNATIVE_KERNEL_NAME = "AlternativeKernelName";
   String PROP_PFX = "weblogic.messaging.kernel";

   void open() throws KernelException;

   void close() throws KernelException;

   KernelStatistics getStatistics();

   Queue findQueue(String var1);

   Queue createQueue(String var1, Map var2) throws KernelException;

   Topic findTopic(String var1);

   Topic createTopic(String var1, Map var2) throws KernelException;

   Quota findQuota(String var1);

   Quota createQuota(String var1) throws KernelException;

   void deleteQuota(String var1);

   Cursor createCursor(Xid var1) throws KernelException;

   Cursor createCursor(Collection var1, Expression var2, int var3) throws KernelException;

   Collection getQueues();

   Collection getTopics();

   Collection getDestinations();

   GXALocalTransaction startLocalGXATransaction() throws KernelException;

   boolean useAlternativeName();
}
