package com.bea.httppubsub.internal;

import com.bea.httppubsub.descriptor.ChannelBean;
import com.bea.httppubsub.descriptor.ChannelPersistenceBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class ChannelPersistenceManagerBuilder {
   private Map idToBean;
   private Map idToManager;

   public ChannelPersistenceManagerBuilder(ChannelBean[] chBeans) {
      if (chBeans != null && chBeans.length != 0) {
         this.idToBean = new HashMap(chBeans.length);
         this.idToManager = new HashMap(chBeans.length);
         this.init(chBeans);
      }
   }

   public ChannelPersistenceManager getChannelPersistenceManager(String channelUrl) {
      if (channelUrl != null && this.idToManager != null) {
         ChannelId cid = ChannelId.newInstance(channelUrl);
         Iterator var3 = this.idToManager.keySet().iterator();

         ChannelId chID;
         do {
            if (!var3.hasNext()) {
               return NullChannelPersistenceManager.INSTANCE;
            }

            chID = (ChannelId)var3.next();
         } while(!chID.contains(cid));

         return (ChannelPersistenceManager)this.idToManager.get(chID);
      } else {
         return NullChannelPersistenceManager.INSTANCE;
      }
   }

   public List getContainedCPMList(String channelUrl) {
      if (channelUrl != null && this.idToManager != null) {
         ChannelId cid = ChannelId.newInstance(channelUrl);
         List result = new ArrayList();
         Iterator var4 = this.idToManager.keySet().iterator();

         while(var4.hasNext()) {
            ChannelId chID = (ChannelId)var4.next();
            if (cid.contains(chID)) {
               result.add(this.idToManager.get(chID));
            }
         }

         return result;
      } else {
         return Collections.EMPTY_LIST;
      }
   }

   public boolean hasPersistenceChannel() {
      return this.idToManager != null && !this.idToManager.isEmpty();
   }

   public boolean isPersistenceChannel(String channelUrl) {
      if (this.idToManager == null) {
         return false;
      } else {
         ChannelId cid = ChannelId.newInstance(channelUrl);
         Iterator var3 = this.idToManager.keySet().iterator();

         ChannelId id;
         do {
            if (!var3.hasNext()) {
               return false;
            }

            id = (ChannelId)var3.next();
         } while(!id.contains(cid));

         return true;
      }
   }

   public boolean containsPersistenceChannel(String channelUrl) {
      if (this.idToManager == null) {
         return false;
      } else {
         ChannelId subId = ChannelId.newInstance(channelUrl);
         Iterator var3 = this.idToManager.keySet().iterator();

         ChannelId id;
         do {
            if (!var3.hasNext()) {
               return false;
            }

            id = (ChannelId)var3.next();
         } while(!subId.contains(id));

         return true;
      }
   }

   public void destroyAllPersistenceManagers() {
      if (this.idToManager != null) {
         for(Iterator it = this.idToManager.keySet().iterator(); it.hasNext(); it.remove()) {
            ChannelId id = (ChannelId)it.next();
            if (id != null) {
               ChannelPersistenceManager manager = (ChannelPersistenceManager)this.idToManager.get(id);
               if (manager != null) {
                  manager.destory();
               }
            }
         }

      }
   }

   private void init(ChannelBean[] chBeans) {
      ChannelBean[] var2 = chBeans;
      int var3 = chBeans.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ChannelBean channel = var2[var4];
         ChannelId id = ChannelId.newInstance(channel.getChannelPattern());
         ChannelPersistenceBean cpb = channel.getChannelPersistence();
         if (cpb != null) {
            this.idToBean.put(id, cpb);
         }
      }

      this.checkOverlapConfig();
      Iterator var8 = this.idToBean.keySet().iterator();

      while(var8.hasNext()) {
         ChannelId cid = (ChannelId)var8.next();
         ChannelPersistenceManager cpm = new DefaultChannelPersistenceManager(cid, (ChannelPersistenceBean)this.idToBean.get(cid));
         cpm.init();
         this.idToManager.put(cid, cpm);
      }

   }

   private void checkOverlapConfig() {
      ChannelId[] chIDs = (ChannelId[])this.idToBean.keySet().toArray(new ChannelId[this.idToBean.size()]);

      for(int i = 0; i < chIDs.length; ++i) {
         for(int j = i + 1; j < chIDs.length; ++j) {
            if (chIDs[i].contains(chIDs[j]) || chIDs[j].contains(chIDs[i])) {
               System.out.println("chIDs[i]: " + chIDs[i] + ", chIDs[j]: " + chIDs[j]);
               throw new IllegalArgumentException("channel-pattern of channel-persistence can not be overlapped");
            }
         }
      }

   }
}
