package weblogic.messaging.kernel;

import java.util.Collection;
import java.util.Comparator;

public interface Destination extends Configurable, Sink {
   String PROP_QUOTA = "Quota";
   String PROP_MAX_MSG_SIZE = "MaximumMessageSize";
   String PROP_DURABLE = "Durable";
   String PROP_REDIR = "RedirectionListener";
   String PROP_IGNORE = "IgnoreExpiration";
   String PROP_LOGGING = "Logging";
   String PROP_SEQUENCE = "DefaultAssignSequence";
   String PROP_STATISTICS_MODE = "StatisticsMode";
   String PROP_SUBSCRIPTION_MESSAGES_LIMIT = "SubscriptionMessagesLimit";
   String PROP_ALTERNATIVE_SAFQUEUE_NAME = "AlternativeSAFQueueName";
   String STATISTICS_MODE_FULL = "Full";
   String STATISTICS_MODE_BYPASS = "Bypass";
   String UOO_SEQUENCE_PREFIX = "%uV1@";
   int LOG_ADD = 1;
   int LOG_REMOVE = 2;
   int LOG_REDELIVERY = 4;
   int LOG_EXPIRATION = 8;
   int LOG_GROUP = 16;
   int SEND = 1;
   int RECEIVE = 2;
   int VISIBILITY = 4;
   int ACTIVATION = 16384;

   Kernel getKernel();

   Statistics getStatistics();

   void setComparator(Comparator var1);

   Comparator getComparator();

   void setFilter(Filter var1);

   Filter getFilter();

   void delete(KernelRequest var1) throws KernelException;

   void suspend(int var1) throws KernelException;

   void resume(int var1) throws KernelException;

   boolean isSuspended(int var1);

   int getMask();

   void addListener(EventListener var1);

   boolean removeListener(EventListener var1);

   Sequence createSequence(String var1, int var2) throws KernelException;

   Sequence findOrCreateSequence(String var1, int var2) throws KernelException;

   Sequence findSequence(String var1);

   Collection getSequences();

   boolean isCreated();

   void setSAFImportedDestination(boolean var1);

   boolean isSAFImportedDestination();

   String getHashedBasedName(String var1, String var2);
}
