package weblogic.nodemanager.rest.providers;

import java.io.IOException;
import java.util.List;
import javax.annotation.Priority;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import weblogic.nodemanager.NodeManagerRestTextFormatter;
import weblogic.nodemanager.grizzly.NMServerInstanceManager;
import weblogic.nodemanager.server.NMServer;
import weblogic.nodemanager.util.DomainInfo;

@Provider
@Priority(6000)
public class ValidationFilter implements ContainerRequestFilter {
   private static final NodeManagerRestTextFormatter nmRestText = NodeManagerRestTextFormatter.getInstance();

   public void filter(ContainerRequestContext requestContext) throws IOException {
      MultivaluedMap pathParams = requestContext.getUriInfo().getPathParameters();
      List authDomainParamList = (List)pathParams.get("auth-domain-name");
      String authDomainName;
      if (!NMServerInstanceManager.getInstance().getConfig().isAuthenticationEnabled()) {
         if (authDomainParamList == null || authDomainParamList.size() < 1) {
            throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(nmRestText.msgUnknownErr()).build());
         }

         authDomainName = (String)authDomainParamList.get(0);
         this.getDomainInfo(authDomainName);
      }

      if (pathParams.containsKey("domain-name")) {
         List domainParamList = (List)pathParams.get("domain-name");
         if (domainParamList == null || domainParamList.size() < 1) {
            throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(nmRestText.msgDomainNotFound("")).build());
         }

         String domainName = (String)domainParamList.get(0);
         authDomainName = (String)authDomainParamList.get(0);
         if (!authDomainName.equals(domainName)) {
            throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(nmRestText.msgDomainNotFound(domainName)).build());
         }

         if (pathParams.containsKey("server-name")) {
            String serverName = (String)((List)pathParams.get("server-name")).get(0);
            if (!this.getDomainInfo(domainName).isServerRegistered(serverName, "WebLogic")) {
               throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(nmRestText.msgServerNotRegistered(serverName, domainName)).build());
            }
         }
      }

   }

   private DomainInfo getDomainInfo(String domainName) {
      DomainInfo domainInfo = null;
      if (domainName != null) {
         domainInfo = NMServer.getInstance().getDomainInfo(domainName);
      }

      if (domainInfo == null) {
         throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(nmRestText.msgDomainNotRegistered(domainName)).build());
      } else {
         return domainInfo;
      }
   }
}
