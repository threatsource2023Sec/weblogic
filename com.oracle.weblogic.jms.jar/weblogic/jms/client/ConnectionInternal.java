package weblogic.jms.client;

import javax.jms.ConnectionMetaData;
import javax.jms.JMSException;
import weblogic.common.internal.PeerInfo;
import weblogic.jms.common.JMSID;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.extensions.WLConnection;

public interface ConnectionInternal extends WLConnection, ConnectionMetaData, ClientRuntimeInfo {
   int RECONNECT_POLICY_OFF = 0;
   int RECONNECT_POLICY_CONNECTION = 1;
   int RECONNECT_POLICY_SESSION = 2;
   int RECONNECT_POLICY_PRODUCER = 4;
   int RECONNECT_POLICY_CONSUMER = 8;
   int RECONNECT_PRODUCER_SESSION_CONNECTION = 7;
   int RECONNECT_ALL = 15;
   int NEVER_RECONNECTS = -256;
   int STATE_NO_RETRY = -2304;
   int STATE_USER_CLOSED = -1280;
   int STATE_CONNECTED = 0;
   int STATE_HAVE_RECON_INFO_PHYSICAL_CLOSE_DONE = 512;
   int NOTIFY_BEFORE_PROGRESS = 1024;
   int STATE_HAVE_RECON_INFO_PEERGONEE_IN_PROGRESS = 1028;
   int STATE_HAVE_RECON_INFO_CLOSE_IN_PROGRESS = 1040;
   int STATE_RECON_SCHEDULED = 1280;
   int STATE_RECON_IN_PROGRESS = 1536;

   PeerInfo getFEPeerInfo();

   void setAllowCloseInOnMessage(boolean var1);

   JMSID getJMSID();

   JMSDispatcher getFrontEndDispatcher() throws JMSException;

   boolean getUserTransactionsEnabled();

   boolean isXAServerEnabled();

   String getPartitionName() throws JMSException;
}
