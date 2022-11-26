package weblogic.jms.safclient.agent.internal;

import weblogic.jms.forwarder.Forwarder;
import weblogic.jms.forwarder.RuntimeHandler;
import weblogic.messaging.kernel.Queue;
import weblogic.store.xa.PersistentStoreXA;
import weblogic.work.WorkManager;

public final class RemoteContext {
   private String name;
   private Forwarder forwarder;

   public RemoteContext(String paramName) {
      this.name = paramName;
      this.forwarder = new Forwarder(false, new ReplyHandlerImpl(), new ClientEnvironmentFactoryImpl());
   }

   public String getName() {
      return this.name;
   }

   public void setCompressionThreshold(int limit) {
      this.forwarder.setCompressionThreshold(limit);
   }

   public void setLoginURL(String url) {
      this.forwarder.setLoginURL(url);
   }

   public void setUsername(String name) {
      this.forwarder.setUsername(name);
   }

   public void setPassword(String password) {
      this.forwarder.setPassword(password);
   }

   public void setRetryDelayBase(long base) {
      this.forwarder.setRetryDelayBase(base);
   }

   public void setRetryDelayMaximum(long max) {
      this.forwarder.setRetryDelayMaximum(max);
   }

   public void setRetryDelayMultiplier(double x) {
      this.forwarder.setRetryDelayMultiplier(x);
   }

   public void setWindowSize(int size) {
      this.forwarder.setWindowSize(size);
   }

   public void setWindowInterval(int interval) {
      this.forwarder.setWindowInterval((long)interval);
   }

   public void addForwarder(PersistentStoreXA persistentStore, WorkManager workManager, RuntimeHandler remoteEndpoint, Queue sourceQueue, String targetJNDI, int nonPersistentQos, String exactlyOnceLoadBalancingPolicy) {
      this.forwarder.addSubforwarder(persistentStore, workManager, remoteEndpoint, sourceQueue, targetJNDI, nonPersistentQos, exactlyOnceLoadBalancingPolicy);
   }

   public void shutdown() {
      this.forwarder.stop();
      this.forwarder = null;
   }

   public String toString() {
      return "RemoteContext(" + this.name + ")";
   }
}
