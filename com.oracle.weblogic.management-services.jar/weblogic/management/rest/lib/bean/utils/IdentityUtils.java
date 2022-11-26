package weblogic.management.rest.lib.bean.utils;

import java.net.URL;
import java.security.AccessController;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Invocation;
import weblogic.management.rest.lib.utils.SecurityUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.RemoteResource;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityTokenServiceManager;
import weblogic.security.service.SecurityService.ServiceType;

public class IdentityUtils {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static Invocation.Builder addIdentityHeader(Invocation.Builder builder, HttpServletRequest request, String method, String url) throws Exception {
      return builder.header("weblogic-jwt-token", "Bearer " + getIdentityToken(request, method, url));
   }

   private static String getIdentityToken(HttpServletRequest request, String method, String url) throws Exception {
      SecurityUtils.checkPermission();
      Principal p = request.getUserPrincipal();
      Set ps = new HashSet();
      ps.add(request.getUserPrincipal());
      AuthenticatedSubject init = new AuthenticatedSubject(true, ps);
      URL u = new URL(url);
      RemoteResource resource = new RemoteResource(u.getProtocol(), u.getHost(), "" + u.getPort(), u.getPath(), method);
      SecurityTokenServiceManager stsm = (SecurityTokenServiceManager)SecurityServiceManager.getSecurityService(kernelId, "weblogicDEFAULT", ServiceType.STSMANAGER);
      return (String)stsm.issueToken("weblogic.oauth2.jwt.access.token", kernelId, init, resource, (ContextHandler)null);
   }
}
