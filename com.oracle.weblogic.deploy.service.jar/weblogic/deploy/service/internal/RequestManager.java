package weblogic.deploy.service.internal;

import org.jvnet.hk2.annotations.Contract;
import weblogic.deploy.common.Debug;
import weblogic.utils.UnsyncCircularQueue;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

@Contract
public abstract class RequestManager {
   private ServiceRequestQueue incomingRequestQueue = null;

   public RequestManager(String queueIdentifier) {
      this.incomingRequestQueue = new ServiceRequestQueue(queueIdentifier);
   }

   public final synchronized void scheduleNextRequest() {
      this.serviceNextRequest();
   }

   public final synchronized void addRequest(ServiceRequest request) {
      this.incomingRequestQueue.add(request);
      this.serviceNextRequest();
   }

   protected final void debug(String message) {
      Debug.serviceDebug(message);
   }

   protected final boolean isDebugEnabled() {
      return Debug.isServiceDebugEnabled();
   }

   private void serviceNextRequest() {
      ServiceRequest nextRequest = this.incomingRequestQueue.getNextQueuedRequest();
      if (nextRequest != null) {
         if (WorkManagerFactory.isInitialized()) {
            WorkManager wm = WorkManagerFactory.getInstance().getSystem();
            wm.schedule(nextRequest);
         } else {
            nextRequest.run();
         }
      }

   }

   private class ServiceRequestQueue {
      private UnsyncCircularQueue q = null;
      private String queueName;

      ServiceRequestQueue(String qName) {
         this.q = new UnsyncCircularQueue();
         this.queueName = qName;
      }

      void add(ServiceRequest request) {
         this.q.put(request);
         if (RequestManager.this.isDebugEnabled()) {
            RequestManager.this.debug("Adding request '" + request.toString() + "' to '" + this.queueName + "' queue whose size is now '" + this.size() + "'");
         }

      }

      ServiceRequest getNextQueuedRequest() {
         if (this.q.empty()) {
            return null;
         } else {
            ServiceRequest request = (ServiceRequest)this.q.get();
            if (RequestManager.this.isDebugEnabled()) {
               RequestManager.this.debug("ServiceRequest on queue '" + this.queueName + "' returning request '" + request.toString() + "' to be serviced - queue's size now is '" + this.size() + "'");
            }

            return request;
         }
      }

      String getName() {
         return this.queueName;
      }

      int size() {
         return this.q.size();
      }
   }
}
