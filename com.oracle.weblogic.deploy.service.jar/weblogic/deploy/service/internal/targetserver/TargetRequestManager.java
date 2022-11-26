package weblogic.deploy.service.internal.targetserver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.jvnet.hk2.annotations.Service;
import weblogic.deploy.service.internal.RequestManager;
import weblogic.utils.LocatorUtilities;

@Service
public class TargetRequestManager extends RequestManager {
   private final Map requestsMap = new HashMap();
   private final Set pendingCancels = new HashSet();

   private TargetRequestManager() {
      super("TargetRequestManager");
   }

   /** @deprecated */
   @Deprecated
   public static TargetRequestManager getInstance() {
      return TargetRequestManager.Maker.SINGLETON;
   }

   public final synchronized void addToRequestTable(TargetRequestImpl newRequest) {
      if (this.isDebugEnabled()) {
         this.debug("adding request '" + newRequest.getId() + "' to target request table");
      }

      this.requestsMap.put(new Long(newRequest.getId()), newRequest);
   }

   public final synchronized Set getRequests() {
      return this.requestsMap.entrySet();
   }

   public final synchronized TargetRequestImpl getRequest(long requestId) {
      return (TargetRequestImpl)this.requestsMap.get(new Long(requestId));
   }

   public final synchronized void removeRequest(long requestId) {
      if (this.isDebugEnabled()) {
         this.debug("removing request '" + requestId + "' from target request table");
      }

      this.requestsMap.remove(new Long(requestId));
   }

   public final synchronized void addToPendingCancels(long requestId) {
      if (this.isDebugEnabled()) {
         this.debug("adding request '" + requestId + "' to target pending cancel set");
      }

      this.pendingCancels.add(new Long(requestId));
   }

   public final synchronized void removePendingCancelFor(long requestId) {
      if (this.isDebugEnabled()) {
         this.debug("removing request '" + requestId + "' from target pending cancel set");
      }

      this.pendingCancels.remove(new Long(requestId));
   }

   public final synchronized boolean hasAPendingCancelFor(long requestId) {
      return this.pendingCancels.contains(new Long(requestId));
   }

   public final synchronized boolean handlingRequests() {
      return this.requestsMap.size() > 0;
   }

   private static class Maker {
      private static final TargetRequestManager SINGLETON = (TargetRequestManager)LocatorUtilities.getService(TargetRequestManager.class);
   }
}
