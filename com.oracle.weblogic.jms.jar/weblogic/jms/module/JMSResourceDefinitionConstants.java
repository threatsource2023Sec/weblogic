package weblogic.jms.module;

public class JMSResourceDefinitionConstants {
   static final String TYPE_INT = "int";
   static final String TYPE_LONG = "long";
   static final String TYPE_BOOLEAN = "boolean";
   static final String JMS_SERVER_NAME = "jms-server-name";
   static final String DEFAULT_TARGETING_ENABLED = "default-targeting-enabled";
   static final String JNDI_NAME = "jndi-name";
   static final String PARTITIONED = "partitioned";
   static final String CONNECTION_FACTORY_ELEMENT = "connection-factory";
   static final String NAME = "name";
   static final String UNIFORM_DISTRIBUTED_QUEUE = "uniform-distributed-queue";
   static final String UNIFORM_DISTRIBUTED_TOPIC = "uniform-distributed-topic";
   static final String JMS_DESCRIPTOR_PREFIX = "resource";
   static final String JMS_DESCRIPTOR_SUFFIX = "-jms.xml";
   static final String UNDERSCORE_DELIMITER = "_";
   static final String DEFAULT_CF_SUFFIX = "_cf";
   static final String DEFAULT_QUEUE_SUFFIX = "_queue";
   static final String DEFAULT_TOPIC_SUFFIX = "_topic";
   static final String UNRECOGNIZED_PROPERTY_LOG_LEVEL = "unrecognized-property-log-level";
   static final String LOG_WARNING = "LogWarning";
   static final String LOG_NONE = "LogNone";
   static final String JAVA_NAMESPACE_PREFIX = "java:";
   static final String JAVA_COMP_ENV_NAMESPACE = "java:comp/env/";
   static final String XA_TRANSACTION = "XATransaction";
   static final String NO_TRANSACTION = "NoTransaction";
   static final String CLIENT_ID_PROPERTY = "clientId";
   static final String USER = "user";
   static final String PASSWORD = "password";
   static final String JMS_ROOT = "weblogic-jms";
   static final String XML_SCHEMA_INSTANCE = "http://www.w3.org/2001/XMLSchema-instance";
   static final String WEBLOGIC_JMS_XSD = " http://xmlns.oracle.com/weblogic/weblogic-jms/1.1/weblogic-jms.xsd";
   static final String WL_DESCRIPTOR_PACKAGE = "weblogic.j2ee.descriptor.wl";
   static final String[] ACCEPTED_CF_PROPERTIES = new String[]{"acknowledge-policy", "allow-close-in-onMessage", "attach-jmsx-user-id", "client-id-policy", "default-compression-threshold", "default-delivery-mode", "default-priority", "default-redelivery-delay", "default-time-to-deliver", "default-time-to-live", "default-unit-of-order", "flow-control-enabled", "flow-interval", "flow-maximum", "flow-minimum", "flow-steps", "load-balancing-enabled", "messages-maximum", "multicast-overrun-policy", "one-way-send-mode", "one-way-send-window-size", "reconnect-blocking-millis", "reconnect-policy", "send-timeout", "server-affinity-enabled", "subscription-sharing-policy", "synchronous-prefetch-mode", "total-reconnect-period-millis", "transaction-timeout"};
   static final String[] ACCEPTED_UD_QUEUE_PROPERTIES = new String[]{"attach-sender", "bytes-high", "bytes-low", "consumption-paused-at-startup", "default-unit-of-order", "delivery-mode", "expiration-logging-policy", "expiration-policy", "forward-delay", "incomplete-work-expiration-time", "insertion-paused-at-startup", "jms-create-destination-identifier", "load-balancing-policy", "maximum-message-size", "message-logging-enabled", "message-logging-format", "messages-high", "messages-low", "messaging-performance-preference", "priority", "production-paused-at-startup", "redelivery-delay", "redelivery-limit", "reset-delivery-count-on-forward", "saf-export-policy", "time-to-deliver", "time-to-live", "unit-of-order-routing", "unit-of-work-handling-policy"};
   static final String[] ACCEPTED_UD_TOPIC_PROPERTIES = new String[]{"attach-sender", "bytes-high", "bytes-low", "consumption-paused-at-startup", "default-unit-of-order", "delivery-mode", "expiration-logging-policy", "expiration-policy", "forwarding-policy", "incomplete-work-expiration-time", "insertion-paused-at-startup", "jms-create-destination-identifier", "load-balancing-policy", "maximum-message-size", "message-logging-enabled", "message-logging-format", "messages-high", "messages-low", "messaging-performance-preference", "multicast-address", "multicast-port", "multicast-time-to-live", "priority", "production-paused-at-startup", "redelivery-delay", "redelivery-limit, saf-export-policy", "time-to-deliver", "time-to-live", "unit-of-order-routing", "unit-of-work-handling-policy"};
   static final char[] INVALID_CHARS_IN_JMS_MODULE = new char[]{'%', '*', ',', ':', '=', '?'};
}
