package weblogic.jms.client;

import javax.jms.JMSException;
import weblogic.common.internal.PeerInfo;

interface Reconnectable {
   String CLOSE_OPERATION = "close";
   String STOP_OPERATION = "stop";

   boolean isClosed();

   void publicCheckClosed() throws JMSException;

   ReconnectController getReconnectController();

   Reconnectable getReconnectState(int var1) throws CloneNotSupportedException;

   Reconnectable preCreateReplacement(Reconnectable var1) throws JMSException;

   void postCreateReplacement();

   boolean isReconnectControllerClosed();

   void close() throws JMSException;

   void isCloseAllowed(String var1) throws JMSException;

   void forgetReconnectState();

   PeerInfo getFEPeerInfo();
}
