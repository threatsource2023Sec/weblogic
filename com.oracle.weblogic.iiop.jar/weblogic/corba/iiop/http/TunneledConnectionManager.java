package weblogic.corba.iiop.http;

import java.util.Iterator;
import java.util.Map;
import weblogic.iiop.IIOPLogger;
import weblogic.protocol.ServerChannel;
import weblogic.server.channels.ServerChannelImpl;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.collections.ConcurrentHashMap;
import weblogic.work.WorkManagerFactory;

public class TunneledConnectionManager {
   private static long idCount = 0L;
   private static final Map channelOpenSocksMap = new ConcurrentHashMap();

   static ServerConnection findByID(String id) {
      Iterator var1 = channelOpenSocksMap.values().iterator();

      Map openSockets;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         openSockets = (Map)var1.next();
      } while(!openSockets.containsKey(id));

      return (ServerConnection)openSockets.get(id);
   }

   static synchronized String getNextID() {
      return String.valueOf((long)(idCount++));
   }

   static Map getOpenSocksMap(ServerChannel networkChannel) {
      return (Map)channelOpenSocksMap.get(networkChannel);
   }

   static void scheduleTunnelScavenging(ServerChannel networkChannel) {
      try {
         TimerManager tm = TimerManagerFactory.getTimerManagerFactory().getTimerManager(TunneledConnectionManager.class.getName(), WorkManagerFactory.getInstance().getSystem());
         tm.scheduleAtFixedRate(new TunnelScavenger(networkChannel), 0L, (long)(networkChannel.getTunnelingClientTimeoutSecs() * 1000));
      } catch (IllegalStateException | IllegalArgumentException var2) {
         IIOPLogger.logScavengeCreateFailure(var2);
      }

   }

   static Map getOpenSocketsMap(ServerChannel networkChannel) {
      Map openSocks = getOpenSocksMap(networkChannel);
      if (openSocks == null) {
         synchronized(channelOpenSocksMap) {
            openSocks = getOpenSocksMap(networkChannel);
            if (openSocks == null) {
               openSocks = new ConcurrentHashMap();
               channelOpenSocksMap.put(networkChannel, openSocks);
               scheduleTunnelScavenging(networkChannel);
            }
         }
      }

      return (Map)openSocks;
   }

   static ServerConnection removeFromActiveConnections(String sockID, ServerChannel networkChannel) {
      Map openSocks = getOpenSocksMap(networkChannel);
      return (ServerConnection)openSocks.remove(sockID);
   }

   static final class TunnelScavenger implements TimerListener {
      ServerChannel networkChannel;
      ServerChannelImpl networkChannelimpl;

      TunnelScavenger(ServerChannel networkChannel) {
         this.networkChannel = networkChannel;
         if (networkChannel instanceof ServerChannelImpl) {
            this.networkChannelimpl = (ServerChannelImpl)networkChannel;
         }

      }

      public void timerExpired(Timer timer) {
         Map openSocks = TunneledConnectionManager.getOpenSocksMap(this.networkChannel);
         Iterator var3 = openSocks.values().iterator();

         while(var3.hasNext()) {
            ServerConnection connection = (ServerConnection)var3.next();
            connection.checkIsDead();
         }

         if (openSocks.isEmpty() && this.networkChannelimpl != null && this.networkChannelimpl.getRuntime() == null) {
            TunneledConnectionManager.channelOpenSocksMap.remove(this.networkChannel);
            this.networkChannel = null;
            this.networkChannelimpl = null;
            timer.cancel();
         }

      }
   }
}
