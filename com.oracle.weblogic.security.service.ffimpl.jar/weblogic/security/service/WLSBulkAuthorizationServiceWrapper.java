package weblogic.security.service;

import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.BulkAuthorizationService;
import com.bea.common.security.service.Identity;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.spi.Direction;
import weblogic.security.spi.Resource;

class WLSBulkAuthorizationServiceWrapper implements BulkAuthorizationService {
   private LoggerSpi logger;
   private BulkAuthorizationService baseService;

   public WLSBulkAuthorizationServiceWrapper(BulkAuthorizationService baseService, LoggerService loggerService) {
      this.logger = loggerService.getLogger("SecurityAtz");
      this.baseService = baseService;
   }

   public Set isAccessAllowed(Identity identity, Map roles, List resources, ContextHandler contextHandler, Direction direction) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".isAccessAllowed" : null;
      if (debug) {
         this.logger.debug(method);
      }

      AuthenticatedSubject aSubject = IdentityUtility.identityToAuthenticatedSubject(identity);
      return (Set)(SecurityServiceManager.isKernelIdentity(aSubject) ? new ResourceSet(resources) : this.baseService.isAccessAllowed(identity, roles, resources, contextHandler, direction));
   }

   public boolean isAccessAllowed(Identity identity, Map roles, Resource resource, ContextHandler contextHandler, Direction direction) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".isAccessAllowed" : null;
      if (debug) {
         this.logger.debug(method);
      }

      AuthenticatedSubject aSubject = IdentityUtility.identityToAuthenticatedSubject(identity);
      return SecurityServiceManager.isKernelIdentity(aSubject) ? true : this.baseService.isAccessAllowed(identity, roles, resource, contextHandler, direction);
   }

   static class ResourceSet implements Set {
      private List resource;

      public ResourceSet(List resources) {
         this.resource = resources;
      }

      public int size() {
         return this.resource.size();
      }

      public boolean isEmpty() {
         return this.resource.isEmpty();
      }

      public boolean contains(Object o) {
         return this.resource.contains(o);
      }

      public Iterator iterator() {
         return this.resource.iterator();
      }

      public Object[] toArray() {
         return this.resource.toArray();
      }

      public Object[] toArray(Object[] a) {
         return this.resource.toArray(a);
      }

      public boolean add(Resource o) {
         throw new UnsupportedOperationException();
      }

      public boolean remove(Object o) {
         throw new UnsupportedOperationException();
      }

      public boolean containsAll(Collection c) {
         return this.resource.containsAll(c);
      }

      public boolean addAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public boolean retainAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public boolean removeAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public void clear() {
         throw new UnsupportedOperationException();
      }
   }
}
