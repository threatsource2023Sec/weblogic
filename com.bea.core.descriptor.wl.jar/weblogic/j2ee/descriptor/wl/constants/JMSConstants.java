package weblogic.j2ee.descriptor.wl.constants;

public final class JMSConstants {
   public static final String PERSISTENT = "Persistent";
   public static final String NON_PERSISTENT = "Non-Persistent";
   public static final String NO_DELIVERY = "No-Delivery";
   public static final String THREAD_BASED = "ThreadBased";
   public static final String OBJECT_BASED_DELEGATED = "ObjectBasedDelegated";
   public static final String OBJECT_BASED_ANONYMOUS = "ObjectBasedAnonymous";
   public static final String OBJECT_BASED_THREAD = "ObjectBasedThread";
   public static final String KEEP_OLD = "KeepOld";
   public static final String KEEP_NEW = "KeepNew";
   public static final int PRIORITY_MAXIMUM = 9;
   public static final int PRIORITY_MINIMUM = 0;
   public static final int PRIORITY_DEFAULT = 4;
   public static final String DESTINATION_TYPE_QUEUE = "Queue";
   public static final String DESTINATION_TYPE_TOPIC = "Topic";
   public static final String STORE_TYPE_FILE = "File";
   public static final String STORE_TYPE_JDBC = "JDBC";
   public static final String ACKNOWLEDGE_MODE_AUTO = "Auto";
   public static final String ACKNOWLEDGE_MODE_CLIENT = "Client";
   public static final String ACKNOWLEDGE_MODE_DUPS_OK = "Dups-Ok";
   public static final String ACKNOWLEDGE_MODE_NONE = "None";
   public static final String KEY_DIRECTION_ASCENDING = "Ascending";
   public static final String KEY_DIRECTION_DESCENDING = "Descending";
   public static final String KEY_TYPE_BOOLEAN = "Boolean";
   public static final String KEY_TYPE_BYTE = "Byte";
   public static final String KEY_TYPE_SHORT = "Short";
   public static final String KEY_TYPE_INT = "Int";
   public static final String KEY_TYPE_LONG = "Long";
   public static final String KEY_TYPE_FLOAT = "Float";
   public static final String KEY_TYPE_DOUBLE = "Double";
   public static final String KEY_TYPE_STRING = "String";
   public static final String TRANSACTION_MODE_XA = "XA";
   public static final String TRANSACTION_MODE_51 = "5.1";
   public static final String TRANSACTION_MODE_NONE = "None";
   public static final String STORE_ENABLED_TRUE = "true";
   public static final String STORE_ENABLED_FALSE = "false";
   public static final String STORE_ENABLED_DEFAULT = "default";
   public static final String ENABLED_TRUE = "true";
   public static final String ENABLED_FALSE = "false";
   public static final String ENABLED_DEFAULT = "default";
   public static final String ACKNOWLEDGE_ALL = "All";
   public static final String ACKNOWLEDGE_PREVIOUS = "Previous";
   public static final String ACKNOWLEDGE_ONE = "One";
   public static final String ROUND_ROBIN = "Round-Robin";
   public static final String RANDOM = "Random";
   public static final String DISCARD = "Discard";
   public static final String LOGGING = "Log";
   public static final String ERRORDESTINATION = "Redirect";
   public static final String ALWAYSFORWARD = "Always-Forward";
   public static final String SYNCWRITE_DISABLED = "Disabled";
   public static final String SYNCWRITE_CACHEFLUSH = "Cache-Flush";
   public static final String SYNCWRITE_DIRECTWRITE = "Direct-Write";
   public static final long SEND_QUOTA_BLOCKING_TIMEOUT = 10L;
   public static final String FIFO = "FIFO";
   public static final String PREEMPTIVE = "Preemptive";
   public static final String SUPPORTS = "supports";
   public static final String NEVER = "never";
   public static final String AUTH_PRINS = "always";
   public static final String PRODUCTION_ENABLED = "Production-Enabled";
   public static final String PRODUCTION_PAUSING = "Production-Pausing";
   public static final String PRODUCTION_PAUSED = "Production-Paused";
   public static final String INSERTION_ENABLED = "Insertion-Enabled";
   public static final String INSERTION_PAUSING = "Insertion-Pausing";
   public static final String INSERTION_PAUSED = "Insertion-Paused";
   public static final String CONSUMPTION_ENABLED = "Consumption-Enabled";
   public static final String CONSUMPTION_PAUSING = "Consumption-Pausing";
   public static final String CONSUMPTION_PAUSED = "Consumption-Paused";
   public static final String UNITOFORDER_STANDARD = ".Standard";
   public static final String UNITOFORDER_SYSTEM = ".System";
   public static final long DEFAULT_MESSAGE_BUFFER_SIZE = -1L;
   public static final String EXACTLY_ONCE = "Exactly-Once";
   public static final String AT_LEAST_ONCE = "At-Least-Once";
   public static final String AT_MOST_ONCE = "At-Most-Once";
   public static final String INITIALCONTEXTFACTORY = "weblogic.jndi.WLInitialContextFactory";
   public static final String XML = "xml";
   public static final String XMLALL = "xmlAll";
   public static final String NON_XML = "non-xml";
   public static final String EXPORT_TO_ALL = "All";
   public static final String EXPORT_TO_NONE = "None";
   public static final String PAUSED_AT_STARTUP_TRUE = "true";
   public static final String PAUSED_AT_STARTUP_FALSE = "false";
   public static final String PAUSED_AT_STARTUP_DEFAULT = "default";
   public static final String ENABLED = "enabled";
   public static final String DISABLED = "disabled";
   public static final String TOPIC_SUBSCRIBER_ONLY = "topicSubscriberOnly";
   public static final String TOPIC_ONLY = "topicOnly";
   public static final String PASS_THROUGH = "PassThrough";
   public static final String SINGLE_MESSAGE = "SingleMessageDelivery";
   public static final String RECONNECT_POLICY_NONE = "none".intern();
   public static final String RECONNECT_POLICY_PRODUCER = "producer".intern();
   public static final String RECONNECT_POLICY_ALL = "all".intern();
   public static final long DEFAULT_RECONNECT_BLOCKING_MILLIS = 60000L;
   public static final long DEFAULT_TOTAL_RECONNECT_PERIOD = -1L;
   public static final String FORWARDING_POLICY_PARTITIONED = "Partitioned".intern();
   public static final String FORWARDING_POLICY_REPLICATED = "Replicated".intern();
   public static final String CLIENT_ID_POLICY_RESTRICTED = "Restricted".intern();
   public static final String CLIENT_ID_POLICY_UNRESTRICTED = "Unrestricted".intern();
   public static final String SUBSCRIPTION_EXCLUSIVE = "Exclusive".intern();
   public static final String SUBSCRIPTION_SHARABLE = "Sharable".intern();
   public static final String SAF_EXACTLY_ONCE_LB_POLICY = "weblogic.jms.saf.ExactlyOnceLoadBalancingPolicy".intern();
   public static final String SAF_EXACTLY_ONCE_LB_POLICY_PER_MEMBER = "Per-Member".intern();
   public static final String SAF_EXACTLY_ONCE_LB_POLICY_PER_JVM = "Per-JVM".intern();
   public static final String PRODUCER_LB_POLICY_DEFAULT = "Per-Member".intern();
   public static final String PRODUCER_LB_POLICY_PER_MEMBER = "Per-Member".intern();
   public static final String PRODUCER_LB_POLICY_PER_JVM = "Per-JVM".intern();

   private JMSConstants() {
   }
}
