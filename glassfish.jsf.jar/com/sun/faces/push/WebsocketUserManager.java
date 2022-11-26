package com.sun.faces.push;

import com.sun.faces.cdi.CdiUtils;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WebsocketUserManager {
   private static final int ESTIMATED_USER_CHANNELS_PER_APPLICATION = 1;
   private static final int ESTIMATED_USER_CHANNELS_PER_SESSION = 1;
   private static final int ESTIMATED_SESSIONS_PER_USER = 2;
   private static final int ESTIMATED_CHANNELS_IDS_PER_USER = 2;
   private final ConcurrentMap userChannels = new ConcurrentHashMap();
   private final ConcurrentMap applicationUsers = new ConcurrentHashMap();

   protected void register(Serializable user, String userId) {
      synchronized(this.applicationUsers) {
         if (!this.applicationUsers.containsKey(user)) {
            this.applicationUsers.putIfAbsent(user, Collections.synchronizedSet(new HashSet(2)));
         }

         ((Set)this.applicationUsers.get(user)).add(userId);
      }
   }

   protected void addChannelId(String userId, String channel, String channelId) {
      if (!this.userChannels.containsKey(userId)) {
         this.userChannels.putIfAbsent(userId, new ConcurrentHashMap(1));
      }

      ConcurrentMap channelIds = (ConcurrentMap)this.userChannels.get(userId);
      if (!channelIds.containsKey(channel)) {
         channelIds.putIfAbsent(channel, Collections.synchronizedSet(new HashSet(1)));
      }

      ((Set)channelIds.get(channel)).add(channelId);
   }

   protected Serializable getUser(String channel, String channelId) {
      Iterator var3 = this.applicationUsers.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry applicationUser = (Map.Entry)var3.next();
         Iterator var5 = ((Set)applicationUser.getValue()).iterator();

         while(var5.hasNext()) {
            String userId = (String)var5.next();
            if (this.getApplicationUserChannelIds(userId, channel).contains(channelId)) {
               return (Serializable)applicationUser.getKey();
            }
         }
      }

      return null;
   }

   protected Set getChannelIds(Serializable user, String channel) {
      Set channelIds = new HashSet(2);
      Set userIds = (Set)this.applicationUsers.get(user);
      if (userIds != null) {
         Iterator var5 = userIds.iterator();

         while(var5.hasNext()) {
            String userId = (String)var5.next();
            channelIds.addAll(this.getApplicationUserChannelIds(userId, channel));
         }
      }

      return channelIds;
   }

   protected void deregister(Serializable user, String userId) {
      this.userChannels.remove(userId);
      synchronized(this.applicationUsers) {
         Set userIds = (Set)this.applicationUsers.get(user);
         userIds.remove(userId);
         if (userIds.isEmpty()) {
            this.applicationUsers.remove(user);
         }

      }
   }

   static ConcurrentMap getUserChannels() {
      return ((WebsocketUserManager)CdiUtils.getBeanInstance(WebsocketUserManager.class, true)).userChannels;
   }

   private Set getApplicationUserChannelIds(String userId, String channel) {
      Map channels = (Map)this.userChannels.get(userId);
      if (channels != null) {
         Set channelIds = (Set)channels.get(channel);
         if (channelIds != null) {
            return channelIds;
         }
      }

      return Collections.emptySet();
   }
}
