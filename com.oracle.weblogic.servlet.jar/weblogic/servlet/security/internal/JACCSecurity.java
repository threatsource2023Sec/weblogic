package weblogic.servlet.security.internal;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.Principal;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;
import java.util.Collections;
import java.util.Map;
import javax.security.jacc.PolicyConfiguration;
import javax.security.jacc.PolicyConfigurationFactory;
import javax.security.jacc.PolicyContext;
import javax.security.jacc.PolicyContextException;
import javax.security.jacc.WebResourcePermission;
import javax.security.jacc.WebRoleRefPermission;
import javax.security.jacc.WebUserDataPermission;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.application.ApplicationContext;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.security.jacc.PolicyContextHandlerData;
import weblogic.security.jacc.RoleMapper;
import weblogic.security.jacc.RoleMapperFactory;
import weblogic.servlet.spi.JaccApplicationSecurity;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.utils.collections.SoftHashMap;

public class JACCSecurity extends AbstractAppSecurity implements JaccApplicationSecurity {
   private static final boolean CACHE = true;
   private static final char DELIMITER = '_';
   private final RoleMapper roleMapper;
   private PolicyConfiguration policyConfig;
   private final CodeSource codeSource;
   private final ProtectionDomain protectionDomain;
   private final String contextId;
   private Map pdCache = Collections.synchronizedMap(new SoftHashMap());
   private SoftHashMap udPermCache = new SoftHashMap();
   private SoftHashMap rrPermCache = new SoftHashMap();
   private SoftHashMap resPermCache = new SoftHashMap();

   public JACCSecurity(ServletSecurityServices securityServices, AppDeploymentMBean mbean, String contextRoot, String appId, ApplicationContext appCtx, String serverName, String docRoot) throws DeploymentException {
      super(securityServices, mbean, contextRoot, appCtx == null ? null : appCtx.getApplicationSecurityRealmName());
      contextRoot = contextRoot.replace('/', '_');
      String app = ApplicationVersionUtils.replaceDelimiter(appId, '_');
      this.contextId = serverName + '_' + app + '_' + contextRoot;

      try {
         PolicyConfigurationFactory pcf = PolicyConfigurationFactory.getPolicyConfigurationFactory();
         this.policyConfig = pcf.getPolicyConfiguration(this.contextId, true);
         this.roleMapper = RoleMapperFactory.getRoleMapperFactory().getRoleMapper(appId, this.contextId, false);
      } catch (ClassNotFoundException var10) {
         throw new DeploymentException(var10);
      } catch (PolicyContextException var11) {
         throw new DeploymentException(var11);
      }

      this.codeSource = this.initializeCodeSource(docRoot);
      this.protectionDomain = new ProtectionDomain(this.codeSource, (PermissionCollection)null);
      appCtx.addJACCPolicyConfiguration(this.policyConfig);
   }

   private CodeSource initializeCodeSource(String docRoot) throws DeploymentException {
      URL url;
      try {
         URI uri = new URI("file:///" + docRoot.replace('\\', '/'));
         url = new URL(uri.toString());
      } catch (URISyntaxException var4) {
         throw new DeploymentException(var4);
      } catch (MalformedURLException var5) {
         throw new DeploymentException(var5);
      }

      return new CodeSource(url, (Certificate[])null);
   }

   public boolean isFullSecurityDelegationRequired() {
      return true;
   }

   public void deployUncheckedPolicy(String resourceId, String method) throws DeploymentException {
      WebResourcePermission webResPerm = new WebResourcePermission(this.encodeColon(resourceId), method);

      try {
         this.policyConfig.addToUncheckedPolicy(webResPerm);
      } catch (PolicyContextException var5) {
         throw new DeploymentException(var5);
      }
   }

   public void deployUncheckedPolicy(Permission permission) throws DeploymentException {
      try {
         this.policyConfig.addToUncheckedPolicy(permission);
      } catch (PolicyContextException var3) {
         throw new DeploymentException(var3);
      }
   }

   public void deployExcludedPolicy(String resourceId, String method) throws DeploymentException {
      String encodedResourceId = this.encodeColon(resourceId);
      WebResourcePermission webResPerm = new WebResourcePermission(encodedResourceId, method);
      WebUserDataPermission userDataPerm = new WebUserDataPermission(encodedResourceId, method);

      try {
         this.policyConfig.addToExcludedPolicy(webResPerm);
         this.policyConfig.addToExcludedPolicy(userDataPerm);
      } catch (PolicyContextException var7) {
         throw new DeploymentException(var7);
      }
   }

   public void deployRole(String roleName, String[] mappings) throws DeploymentException {
      WebRoleRefPermission perm = new WebRoleRefPermission("", roleName);

      try {
         this.policyConfig.addToRole(roleName, perm);
      } catch (PolicyContextException var5) {
         throw new DeploymentException(var5);
      }
   }

   public void deployRole(String roleName, String resourceId, String method) throws DeploymentException {
      WebResourcePermission webResPerm = new WebResourcePermission(this.encodeColon(resourceId), method);

      try {
         this.policyConfig.addToRole(roleName, webResPerm);
      } catch (PolicyContextException var6) {
         throw new DeploymentException(var6);
      }
   }

   public void deployRoleLink(String roleName, String resName, String refName) throws DeploymentException {
      WebRoleRefPermission roleRefPerm = new WebRoleRefPermission(resName, refName);

      try {
         this.policyConfig.addToRole(roleName, roleRefPerm);
      } catch (PolicyContextException var6) {
         throw new DeploymentException(var6);
      }
   }

   public void startRoleAndPolicyDeployments() throws DeploymentException {
      try {
         PolicyConfigurationFactory pcf = PolicyConfigurationFactory.getPolicyConfigurationFactory();
         this.policyConfig = pcf.getPolicyConfiguration(this.contextId, false);
      } catch (ClassNotFoundException var2) {
         throw new DeploymentException(var2);
      } catch (PolicyContextException var3) {
         throw new DeploymentException(var3);
      }
   }

   public void endRoleAndPolicyDeployments(Map roleMapping) throws DeploymentException {
      if (roleMapping != null && !roleMapping.isEmpty()) {
         this.roleMapper.addAppRolesToPrincipalMap(roleMapping);
      }

      try {
         this.policyConfig.commit();
         Policy.getPolicy().refresh();
      } catch (PolicyContextException var3) {
         throw new DeploymentException(var3);
      }
   }

   public void unregisterPolicies() throws DeploymentException {
      try {
         this.policyConfig.delete();
      } catch (PolicyContextException var2) {
         throw new DeploymentException(var2);
      }
   }

   public void unregisterRoles() throws DeploymentException {
      throw new UnsupportedOperationException("Unimplemented");
   }

   public boolean isSubjectInRole(SubjectHandle subject, String roleName, HttpServletRequest request, HttpServletResponse response, String servletName) {
      ProtectionDomain pd = this.getProtectionDomainForSubject(subject);
      WebRoleRefPermission perm = this.getWebRoleRefPermission(servletName, roleName);

      try {
         return this.implies(perm, pd);
      } catch (SecurityException var9) {
         return false;
      }
   }

   public boolean hasPermission(SubjectHandle subject, HttpServletRequest request, HttpServletResponse response, String relativeRequestPath) {
      try {
         return this.implies(this.getWebResourcePermission(request), this.getProtectionDomainForSubject(subject));
      } catch (SecurityException var6) {
         return false;
      }
   }

   public boolean checkTransport(String uri, String action) {
      WebUserDataPermission perm = this.getWebUserDataPermission(uri, action);
      return this.implies(perm, this.protectionDomain);
   }

   public PolicyContextHandlerData createContextHandlerData(HttpServletRequest request) {
      return this.getSecurityServices().createContextHandlerData(request);
   }

   private WebUserDataPermission getWebUserDataPermission(String relUri, String action) {
      String encodedRelUri = this.encodeColon(relUri);
      PermKey key = new PermKey(encodedRelUri, action);
      WebUserDataPermission perm = (WebUserDataPermission)this.udPermCache.get(key);
      if (perm != null) {
         return perm;
      } else {
         perm = new WebUserDataPermission(encodedRelUri, action);
         this.udPermCache.put(key, perm);
         return perm;
      }
   }

   private WebRoleRefPermission getWebRoleRefPermission(String name, String rolename) {
      PermKey key = new PermKey(name, rolename);
      WebRoleRefPermission perm = (WebRoleRefPermission)this.rrPermCache.get(key);
      if (perm != null) {
         return perm;
      } else {
         perm = new WebRoleRefPermission(name, rolename);
         this.rrPermCache.put(key, perm);
         return perm;
      }
   }

   private WebResourcePermission getWebResourcePermission(HttpServletRequest request) {
      String uri = request.getServletPath();
      String encodedUri = this.encodeColon(uri);
      if (encodedUri.length() == 1 && encodedUri.charAt(0) == '/') {
         encodedUri = "";
      }

      String action = request.getMethod();
      PermKey key = new PermKey(encodedUri, action);
      WebResourcePermission perm = (WebResourcePermission)this.resPermCache.get(key);
      if (perm != null) {
         return perm;
      } else {
         perm = new WebResourcePermission(encodedUri, action);
         this.resPermCache.put(key, perm);
         return perm;
      }
   }

   private ProtectionDomain getProtectionDomainForSubject(SubjectHandle handle) {
      Principal[] principals = this.getSecurityServices().getPrincipals(handle);
      ProtectionDomain pd = (ProtectionDomain)this.pdCache.get(handle);
      if (pd != null) {
         return pd;
      } else {
         pd = new ProtectionDomain(this.codeSource, (PermissionCollection)null, (ClassLoader)null, principals);
         this.pdCache.put(handle, pd);
         return pd;
      }
   }

   private boolean implies(Permission perm, ProtectionDomain pd) {
      String oldCtx = PolicyContext.getContextID();
      this.setPolicyContext(this.contextId);

      boolean var4;
      try {
         var4 = Policy.getPolicy().implies(pd, perm);
      } finally {
         this.setPolicyContext(oldCtx);
      }

      return var4;
   }

   public String getContextID() {
      return this.contextId;
   }

   private void setPolicyContext(final String ctxID) {
      String oldCtx = PolicyContext.getContextID();
      if (oldCtx != ctxID && (oldCtx == null || ctxID == null || !oldCtx.equals(ctxID))) {
         try {
            AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  PolicyContext.setContextID(ctxID);
                  return null;
               }
            });
         } catch (PrivilegedActionException var5) {
            Throwable t = var5;
            if (var5.getCause() != null) {
               t = var5.getCause();
            }

            throw new SecurityException(((Throwable)t).getMessage());
         }
      }

   }

   private String encodeColon(String input) {
      return input.replaceAll(":", "%3A");
   }

   private class PermKey {
      private String key1;
      private String key2;

      private PermKey(String k1, String k2) {
         this.key1 = k1;
         this.key2 = k2;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (!(o instanceof PermKey)) {
            return false;
         } else {
            PermKey permKey = (PermKey)o;
            if (this.key2 != null) {
               if (!this.key2.equals(permKey.key2)) {
                  return false;
               }
            } else if (permKey.key2 != null) {
               return false;
            }

            if (this.key1 != null) {
               if (!this.key1.equals(permKey.key1)) {
                  return false;
               }
            } else if (permKey.key1 != null) {
               return false;
            }

            return true;
         }
      }

      public int hashCode() {
         int result = this.key1 != null ? this.key1.hashCode() : 0;
         result = 29 * result + (this.key2 != null ? this.key2.hashCode() : 0);
         return result;
      }

      // $FF: synthetic method
      PermKey(String x1, String x2, Object x3) {
         this(x1, x2);
      }
   }
}
