package weblogic.iiop.server.ior;

import weblogic.iiop.ProtocolHandlerIIOP;
import weblogic.iiop.ior.CodebaseComponent;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.iiop.protocol.ListenPoint;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.protocol.ServerIdentity;
import weblogic.protocol.configuration.ProtocolHelper;
import weblogic.utils.http.HttpParsing;

public class ServerCodebaseComponent extends CodebaseComponent {
   private String applicationName;
   private ListenPoint listenPoint;
   private ServerIdentity target;

   public ServerCodebaseComponent(ServerIdentity id, String applicationName) {
      this.applicationName = applicationName;
      ServerChannel ch = ServerChannelManager.findServerChannel(id, ProtocolHandlerIIOP.PROTOCOL_IIOP);
      this.listenPoint = ch != null ? new ListenPoint(ch.getAddress(), ch.getPort()) : ListenPoint.NULL_KEY;
      this.target = id;
   }

   protected void writeCodebase(CorbaOutputStream out) {
      out.write_string(HttpParsing.escape(this.getCodebase(this.listenPoint.getReplacement(out, this.target), out.isSecure())));
   }

   private String getCodebase(ListenPoint listenPoint, boolean secure) {
      return ProtocolHelper.getCodebase(secure, listenPoint.getAddress(), listenPoint.getPort(), this.applicationName);
   }
}
