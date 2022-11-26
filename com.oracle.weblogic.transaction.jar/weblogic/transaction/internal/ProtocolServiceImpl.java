package weblogic.transaction.internal;

import java.lang.annotation.Annotation;
import java.rmi.UnknownHostException;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannelManager;
import weblogic.protocol.ServerIdentity;
import weblogic.protocol.URLManagerService;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.server.GlobalServiceLocator;

public class ProtocolServiceImpl implements ProtocolService {
   private ProtocolService protocolService = null;

   public ProtocolService getProtocolService() {
      if (this.protocolService == null) {
         this.protocolService = new ProtocolServiceImpl();
      }

      return this.protocolService;
   }

   public void setProtocolService(ProtocolService protocolService) {
      this.protocolService = protocolService;
   }

   public Protocol getDefaultSecureProtocol() {
      return ProtocolManager.getDefaultSecureProtocol();
   }

   public Protocol getDefaultProtocol() {
      return ProtocolManager.getDefaultProtocol();
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   public String findURL(String serverName, Protocol protocol) throws UnknownHostException {
      return getURLManagerService().findURL(serverName, protocol);
   }

   public String findAdministrationURL(String serverName) throws UnknownHostException {
      return getURLManagerService().findAdministrationURL(serverName);
   }

   public Object findServerChannel(Object server, Object protocol) {
      return ServerChannelManager.findServerChannel((ServerIdentity)server, (Protocol)protocol);
   }

   public boolean isLocalAdminChannelEnabled() {
      return ChannelHelper.isLocalAdminChannelEnabled();
   }
}
