package weblogic.security.service;

import com.bea.core.security.managers.CEO;
import com.bea.core.security.managers.Manager;
import com.bea.core.security.managers.NotInitializedException;
import com.bea.core.security.managers.NotSupportedException;
import java.util.Vector;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.spi.Resource;

public class CredentialManager implements SecurityService {
   public void initialize(String realmName) {
      throw new NotSupportedException();
   }

   public Object[] getCredentials(AuthenticatedSubject requestor, AuthenticatedSubject initiator, Resource resource, ContextHandler handler, String credType) {
      Manager manager;
      try {
         manager = CEO.getManager();
      } catch (NotInitializedException var8) {
         throw new NotYetInitializedException(var8);
      }

      return manager.getCredentials(requestor, initiator, resource, handler, credType);
   }

   public Object[] getCredentials(AuthenticatedSubject requestor, String initiator, Resource resource, ContextHandler handler, String credType) {
      Manager manager;
      try {
         manager = CEO.getManager();
      } catch (NotInitializedException var8) {
         throw new NotYetInitializedException(var8);
      }

      return manager.getCredentials(requestor, initiator, resource, handler, credType);
   }

   /** @deprecated */
   @Deprecated
   public Vector getCredentials(AuthenticatedSubject requestor, AuthenticatedSubject initiator, Resource resource, String[] credentialTypes) throws NotYetInitializedException, InvalidParameterException {
      throw new NotSupportedException();
   }

   /** @deprecated */
   @Deprecated
   public Vector getCredentials(AuthenticatedSubject requestor, String initiator, Resource resource, String[] credentialTypes) {
      throw new NotSupportedException();
   }

   public void shutdown() {
      throw new NotSupportedException();
   }

   public void start() {
      throw new NotSupportedException();
   }

   public void suspend() {
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
}
