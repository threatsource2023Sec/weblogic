package weblogic.security.service;

import com.bea.core.security.managers.CEO;
import com.bea.core.security.managers.Manager;
import com.bea.core.security.managers.NotInitializedException;
import com.bea.core.security.managers.NotSupportedException;
import java.util.Map;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.spi.Resource;

public class RoleManager implements SecurityService {
   public Map getRoles(AuthenticatedSubject subject, Resource resource, ContextHandler handler) {
      Manager manager;
      try {
         manager = CEO.getManager();
      } catch (NotInitializedException var6) {
         throw new NotYetInitializedException(var6);
      }

      return manager.getRoles(subject, resource, handler);
   }

   public void initialize(String realmName) {
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
}
