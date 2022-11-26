package weblogic.kodo.event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.datacache.DataCache;
import org.apache.openjpa.event.AbstractRemoteCommitProvider;
import org.apache.openjpa.event.RemoteCommitEvent;
import org.apache.openjpa.event.RemoteCommitEventManager;
import org.apache.openjpa.event.RemoteCommitProvider;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.concurrent.ConcurrentReferenceHashSet;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.UserException;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.GroupMessage;
import weblogic.cluster.MulticastSession;
import weblogic.cluster.RecoverListener;
import weblogic.cluster.ClusterServicesActivator.Locator;
import weblogic.rmi.spi.HostID;

public final class ClusterRemoteCommitProvider extends AbstractRemoteCommitProvider implements RecoverListener, Configurable {
   private static final String RECOVERY_DO_NOTHING = "none";
   private static final String RECOVERY_CLEAR_ALL = "clear";
   private static Localizer _loc = Localizer.forPackage(ClusterRemoteCommitProvider.class);
   protected Configuration conf;
   private int bufferSize = 10;
   private String recoverAction = "none";
   private String topics = null;
   private MulticastSession multicastSession;
   private static Set registry = new ConcurrentReferenceHashSet(0);

   public void broadcast(RemoteCommitEvent event) {
      ClusterRemoteCommitEvent message = new ClusterRemoteCommitEvent(this.getCacheTopics(), event);

      try {
         this.multicastSession.send(message);
      } catch (IOException var4) {
         throw new InternalException(_loc.get("transmission-error"), var4);
      }
   }

   public void setConfiguration(Configuration conf) {
      this.conf = conf;
   }

   public void endConfiguration() {
      ClusterServices transport = Locator.locateClusterServices();
      if (transport == null) {
         throw new InternalException(_loc.get("no-transport"));
      } else {
         this.multicastSession = transport.createMulticastSession(this, this.bufferSize);
         if (this.multicastSession == null) {
            throw new InternalException(_loc.get("no-multicast-session"));
         } else {
            registry.add(this.conf);
         }
      }
   }

   public void close() {
      registry.remove(this.conf);
   }

   public GroupMessage createRecoverMessage() {
      String topic = "none".equalsIgnoreCase(this.recoverAction) ? null : this.getCacheTopics();
      return new ClusterRemoteCommitEvent(topic, (RemoteCommitEvent)null);
   }

   public int getBufferSize() {
      return this.bufferSize;
   }

   public void setBufferSize(int bufferSize) {
      this.bufferSize = bufferSize;
   }

   public void setRecoverAction(String action) {
      if ("none".equalsIgnoreCase(action)) {
         this.recoverAction = action;
      } else {
         if (!"clear".equalsIgnoreCase(action)) {
            throw new UserException(_loc.get("bad-recover-action", action));
         }

         this.recoverAction = action;
      }

   }

   public String getRecoverAction() {
      return this.recoverAction;
   }

   public void setCacheTopics(String t) {
      this.topics = t;
   }

   public String getCacheTopics() {
      if (this.topics == null) {
         this.topics = this.conf == null ? null : this.conf.getId();
      }

      return this.topics;
   }

   public static class ClusterRemoteCommitEvent implements GroupMessage {
      protected final Collection topics;
      protected final RemoteCommitEvent event;

      public ClusterRemoteCommitEvent(String topic, RemoteCommitEvent event) {
         this.topics = this.split(topic);
         this.event = event;
      }

      public void execute(HostID sender) {
         if (this.topics != null && !this.topics.isEmpty()) {
            if (!this.isEcho(sender)) {
               Iterator confs = ClusterRemoteCommitProvider.registry.iterator();

               while(confs.hasNext()) {
                  Object c = confs.next();
                  if (c instanceof OpenJPAConfiguration) {
                     RemoteCommitEventManager eventManager = ((OpenJPAConfiguration)c).getRemoteCommitEventManager();
                     if (eventManager != null) {
                        RemoteCommitProvider rcp = eventManager.getRemoteCommitProvider();
                        if (rcp instanceof ClusterRemoteCommitProvider && this.intersects(this.topics, this.split(((ClusterRemoteCommitProvider)rcp).getCacheTopics()))) {
                           if (this.isClearAll()) {
                              this.clearAll(eventManager.getListeners());
                           } else {
                              eventManager.fireEvent(this.event);
                           }
                        }
                     }
                  }
               }

            }
         }
      }

      private boolean isClearAll() {
         return this.event == null;
      }

      private void clearAll(Collection objs) {
         if (objs != null && !objs.isEmpty()) {
            Iterator caches = objs.iterator();

            while(caches.hasNext()) {
               Object cache = caches.next();
               if (cache instanceof DataCache) {
                  ((DataCache)cache).clear();
               }
            }

         }
      }

      boolean isEcho(HostID sender) {
         return false;
      }

      Collection split(String s) {
         if (s == null) {
            return null;
         } else {
            String[] members = s.split(",");
            Collection list = new ArrayList();
            String[] var4 = members;
            int var5 = members.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               String member = var4[var6];
               if (member != null) {
                  list.add(member.trim());
               }
            }

            return list;
         }
      }

      boolean intersects(Collection a, Collection b) {
         if (a == null && b == null) {
            return true;
         } else if (a != null && b != null) {
            Iterator var3 = a.iterator();

            String as;
            do {
               if (!var3.hasNext()) {
                  return false;
               }

               as = (String)var3.next();
            } while(!b.contains(as));

            return true;
         } else {
            return false;
         }
      }
   }
}
