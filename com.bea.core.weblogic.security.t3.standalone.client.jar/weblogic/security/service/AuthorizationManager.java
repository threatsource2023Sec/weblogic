package weblogic.security.service;

import com.bea.core.security.managers.CEO;
import com.bea.core.security.managers.Manager;
import com.bea.core.security.managers.NotInitializedException;
import com.bea.core.security.managers.NotSupportedException;
import java.util.Map;
import javax.security.auth.Subject;
import weblogic.management.security.ProviderMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.spi.Direction;
import weblogic.security.spi.Resource;

public class AuthorizationManager implements SecurityService {
   public AuthorizationManager() {
   }

   public AuthorizationManager(String realmName, ProviderMBean[] configuration) {
      throw new NotSupportedException();
   }

   public void initialize(String realmName) {
      throw new NotSupportedException();
   }

   public void start() {
      throw new NotSupportedException();
   }

   public void suspend() {
      throw new NotSupportedException();
   }

   public void shutdown() {
      throw new NotSupportedException();
   }

   public boolean isAccessAllowed(AuthenticatedSubject aSubject, Map roles, Resource resource, ContextHandler handler, Direction direction) {
      Manager manager;
      try {
         manager = CEO.getManager();
      } catch (NotInitializedException var8) {
         throw new NotYetInitializedException(var8);
      }

      return manager.isAccessAllowed(aSubject, roles, resource, handler, direction);
   }

   public boolean isAccessAllowed(AuthenticatedSubject aSubject, Resource resource, ContextHandler handler) {
      Manager manager;
      try {
         manager = CEO.getManager();
      } catch (NotInitializedException var6) {
         throw new NotYetInitializedException(var6);
      }

      Map roles = manager.getRoles(aSubject, resource, handler);
      return this.isAccessAllowed(aSubject, roles, resource, handler, Direction.PRIOR);
   }

   public boolean isProtectedResource(Subject subject, Resource resource) {
      throw new NotSupportedException();
   }

   public boolean isProtectedResource(AuthenticatedSubject aSubject, Resource resource) {
      throw new NotSupportedException();
   }

   public AuthorizationManagerDeployHandle startDeployPolicies(SecurityApplicationInfo appInfo) throws DeployHandleCreationException {
      throw new NotSupportedException();
   }

   public void deployPolicy(AuthorizationManagerDeployHandle handle, Resource resource, String[] roleNames) throws ResourceCreationException {
      throw new NotSupportedException();
   }

   public void deployUncheckedPolicy(AuthorizationManagerDeployHandle handle, Resource resource) throws ResourceCreationException {
      throw new NotSupportedException();
   }

   public void deployExcludedPolicy(AuthorizationManagerDeployHandle handle, Resource resource) throws ResourceCreationException {
      throw new NotSupportedException();
   }

   public void endDeployPolicies(AuthorizationManagerDeployHandle handle) throws ResourceCreationException {
      throw new NotSupportedException();
   }

   public void undeployAllPolicies(AuthorizationManagerDeployHandle handle) throws ResourceRemovalException {
      throw new NotSupportedException();
   }

   public void deleteApplicationPolicies(SecurityApplicationInfo appInfo) throws ResourceRemovalException {
      throw new NotSupportedException();
   }

   public boolean isVersionableApplicationSupported() {
      throw new NotSupportedException();
   }

   public void createApplicationVersion(String appIdentifier, String sourceAppIdentifier) throws ApplicationVersionCreationException {
      throw new NotSupportedException();
   }

   public void deleteApplicationVersion(String appIdentifier) throws ApplicationVersionRemovalException {
      throw new NotSupportedException();
   }

   public void deleteApplication(String appName) throws ApplicationRemovalException {
      throw new NotSupportedException();
   }

   public AuthorizationPolicyHandler getAuthorizationPolicyHandler(String name, String version, String timeStamp, Resource[] resources) throws ConsumptionException {
      throw new NotSupportedException();
   }
}
