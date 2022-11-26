package weblogic.nodemanager.rest.providers;

import java.io.IOException;
import java.util.Base64;
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
@Priority(1000)
public class AuthFilter implements ContainerRequestFilter {
   private static final NodeManagerRestTextFormatter nmRestText = NodeManagerRestTextFormatter.getInstance();
   private static final String AUTHENTICATION_FAILED;

   public void filter(ContainerRequestContext requestContext) throws IOException {
      if (NMServerInstanceManager.getInstance().getConfig().isAuthenticationEnabled()) {
         String authHeader = requestContext.getHeaderString("Authorization");
         if (authHeader == null) {
            throw new WebApplicationException(Response.status(Status.UNAUTHORIZED).entity(AUTHENTICATION_FAILED).build());
         } else {
            String user;
            String pass;
            try {
               String token = authHeader.split(" ")[1].trim();
               String decodedToken = new String(Base64.getDecoder().decode(token));
               String[] credentials = decodedToken.split(":");
               user = credentials[0];
               pass = credentials[1];
            } catch (Exception var9) {
               throw new WebApplicationException(var9, Response.status(Status.UNAUTHORIZED).entity(AUTHENTICATION_FAILED).build());
            }

            MultivaluedMap pathParams = requestContext.getUriInfo().getPathParameters();
            List authDomainParamList = (List)pathParams.get("auth-domain-name");
            if (authDomainParamList != null && authDomainParamList.size() >= 1) {
               String authDomainName = (String)authDomainParamList.get(0);

               try {
                  DomainInfo domainInfo = NMServer.getInstance().getDomainInfo(authDomainName);
                  if (domainInfo == null) {
                     throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(nmRestText.msgDomainNotRegistered(authDomainName)).build());
                  }

                  if (domainInfo.isAuthorized(user, pass)) {
                     return;
                  }
               } catch (Exception var10) {
                  if (var10 instanceof WebApplicationException) {
                     throw var10;
                  }

                  throw new WebApplicationException(var10, Response.status(Status.BAD_REQUEST).entity(nmRestText.msgErrorProcessingReq()).build());
               }

               throw new WebApplicationException(Response.status(Status.UNAUTHORIZED).entity(AUTHENTICATION_FAILED).build());
            } else {
               throw new WebApplicationException(Response.status(Status.UNAUTHORIZED).entity(AUTHENTICATION_FAILED).build());
            }
         }
      }
   }

   static {
      AUTHENTICATION_FAILED = nmRestText.msgAuthenticationFailed();
   }
}
