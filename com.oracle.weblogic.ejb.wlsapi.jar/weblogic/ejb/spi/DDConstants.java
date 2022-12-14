package weblogic.ejb.spi;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.ejb.ApplicationException;
import javax.ejb.MessageDriven;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.interceptor.Interceptor;

public interface DDConstants {
   String HOME = "Home";
   String REMOTE = "Remote";
   String LOCALHOME = "LocalHome";
   String LOCAL = "Local";
   String TIMER = "Timer";
   String MESSAGEENDPOINT = "MessageEndpoint";
   String CALLBACK = "LifecycleCallback";
   String WEBSERVICE = "ServiceEndpoint";
   String ACP_KEY_MESSAGE_SELECTOR = "MESSAGESELECTOR";
   String ACP_KEY_ACKNOWLEDGE_MODE = "ACKNOWLEDGEMODE";
   String ACP_KEY_DESTINATION_TYPE = "DESTINATIONTYPE";
   String ACP_KEY_SUBSCRIPTION_DURABILITY = "SUBSCRIPTIONDURABILITY";
   String ACP_KEY_CONNECTION_FACTORY_JNDI_NAME = "CONNECTIONFACTORYJNDINAME";
   String ACP_KEY_DESTINATION_JNDI_NAME = "DESTINATIONJNDINAME";
   String ACP_KEY_RESOURCE_ADAPTER_JNDI_NAME = "RESOURCEADAPTERJNDINAME";
   String ACP_KEY_DESTINATION_RESOURCE_LINK = "DESTINATIONRESOURCELINK";
   String ACP_KEY_CONNECTION_FACTORY_RESOURCE_LINK = "CONNECTIONFACTORYRESOURCELINK";
   String ACP_KEY_DISTRIBUTED_DESTINATION_CONNECTION = "DISTRIBUTEDDESTINATIONCONNECTION";
   String ACP_KEY_JMS_CLIENT_ID = "JMSCLIENTID";
   String ACP_KEY_DURABLE_SUBSCRIPTION_DELETION = "DURABLESUBSCRIPTIONDELETION";
   String ACP_KEY_JMS_POLLING_INTERVAL_SECONDS = "JMSPOLLINGINTERVALSECONDS";
   String ACP_KEY_INITIAL_CONTEXT_FACTORY = "INITIALCONTEXTFACTORY";
   String ACP_KEY_PROVIDER_URL = "PROVIDERURL";
   String ACP_KEY_MAX_MESSAGES_IN_TRANSACTION = "MAXMESSAGESINTRANSACTION";
   String ACP_KEY_INIT_SUSPEND_SECONDS = "INITSUSPENDSECONDS";
   String ACP_KEY_MAX_SUSPEND_SECONDS = "MAXSUSPENDSECONDS";
   String ACP_KEY_USE_81_STYLE_POLLING = "USE81STYLEPOLLING";
   String ACP_KEY_MINIMIZE_AQ_SESSIONS = "MINIMIZEAQSESSIONS";
   String ACP_KEY_DESTINATION_POLL_INTERVAL_PROPERTY = "MDBDESTINATIONPOLLINTERVALMILLIS";
   String ACP_KEY_AQMDB_RECEIVE_NOWAIT = "AQMDBRECEIVENOWAIT";
   String ACP_KEY_TOPIC_MESSAGES_DISTRIBUTION_MODE = "TOPICMESSAGESDISTRIBUTIONMODE";
   String ACP_KEY_INACTIVE = "INACTIVE";
   String ACP_KEY_MESSAGES_MAXIMUM = "MESSAGESMAXIMUM";
   String ACP_KEY_CONNECTION_FACTORY_LOOKUP = "CONNECTIONFACTORYLOOKUP";
   String ACP_KEY_DESTINATION_LOOKUP = "DESTINATIONLOOKUP";
   String ACP_KEY_SUBSCRIPTION_NAME = "SUBSCRIPTIONNAME";
   String ACP_KEY_CLIENT_ID = "CLIENTID";
   String ACP_KEY_TOPIC_MESSAGE_PARTITION_MODE = "TOPICMESSAGEPARTITIONMODE";
   int DEFAULT_DESTINATION_POLL_INTERVAL_MILLIS = 0;
   String BEA_010054 = "BEA-010054";
   String BEA_010202 = "BEA-010202";
   String BEA_010001 = "BEA-010001";
   String BEA_010200 = "BEA-010200";
   String BEA_012034 = "BEA-012034";
   String DROPANDCREATE = "DropAndCreate";
   String DROPANDCREATEALWAYS = "DropAndCreateAlways";
   String ALTERORCREATE = "AlterOrCreate";
   String CREATEONLY = "CreateOnly";
   String DISABLED = "Disabled";
   Class[] COMPONENT_DEFINING_ANNOS_ARRAY = new Class[]{Stateful.class, Stateless.class, Singleton.class, MessageDriven.class};
   List COMPONENT_DEFINING_ANNOS = Collections.unmodifiableList(Arrays.asList(COMPONENT_DEFINING_ANNOS_ARRAY));
   Class[] TOP_LEVEL_ANNOS_ARRAY = new Class[]{Stateful.class, Stateless.class, Singleton.class, MessageDriven.class, ApplicationException.class, Interceptor.class};
   List TOP_LEVEL_ANNOS = Collections.unmodifiableList(Arrays.asList(TOP_LEVEL_ANNOS_ARRAY));
}
