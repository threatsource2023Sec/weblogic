package weblogic.transaction.internal;

import java.rmi.UnknownHostException;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolHandlerAdmin;

public interface ProtocolService {
   Protocol PROTOCOL_ADMIN = ProtocolHandlerAdmin.PROTOCOL_ADMIN;
   int QOS_ADMIN = 103;

   ProtocolService getProtocolService();

   void setProtocolService(ProtocolService var1);

   Protocol getDefaultSecureProtocol();

   Protocol getDefaultProtocol();

   String findURL(String var1, Protocol var2) throws UnknownHostException;

   String findAdministrationURL(String var1) throws UnknownHostException;

   Object findServerChannel(Object var1, Object var2);

   boolean isLocalAdminChannelEnabled();
}
