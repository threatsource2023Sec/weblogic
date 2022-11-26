package weblogic.nodemanager.rest.resources;

import java.util.Iterator;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.glassfish.admin.rest.model.RestCollectionResponseBody;
import weblogic.nodemanager.rest.model.Server;
import weblogic.nodemanager.rest.utils.CommonUtils;
import weblogic.nodemanager.server.NMServer;
import weblogic.nodemanager.util.DomainInfo;
import weblogic.nodemanager.util.ServerInfo;

public class DomainServersResource extends NMBaseResource {
   @GET
   @Produces({"application/json"})
   public RestCollectionResponseBody getDomainServers(@PathParam("domain-name") String domainName) throws Exception {
      RestCollectionResponseBody rb = this.restCollectionResponseBody(Server.class, "items.name", this.getParentUri());
      rb.addSelfResourceLinks(this.getSubUri(new String[0]));
      List serverInfos = null;

      try {
         DomainInfo domainInfo = NMServer.getInstance().getDomainInfo(domainName);
         serverInfos = domainInfo.getAllServerInfo();
      } catch (Exception var7) {
         throw this.badRequest(CommonUtils.GENERIC_ERROR_MESSAGE);
      }

      if (serverInfos != null) {
         Iterator var8 = serverInfos.iterator();

         while(var8.hasNext()) {
            ServerInfo serverInfo = (ServerInfo)var8.next();
            Server server = new Server();
            server.setName(serverInfo.getName());
            server.setState(serverInfo.getState() != null ? serverInfo.getState() : "UNKNOWN");
            server.setRegistered(serverInfo.isConfigComplete());
            server.allFieldsSet();
            rb.addItem(server, server.getName());
         }
      }

      return rb;
   }

   @Path("{server-name}")
   public DomainServerResource getServerResource() throws Exception {
      return (DomainServerResource)this.getSubResource(DomainServerResource.class);
   }
}
