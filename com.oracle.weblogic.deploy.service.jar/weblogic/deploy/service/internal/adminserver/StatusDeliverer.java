package weblogic.deploy.service.internal.adminserver;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.StatusListener;
import weblogic.deploy.service.StatusListenerManager;

public final class StatusDeliverer implements StatusListenerManager {
   private static final StatusDeliverer SINGLETON = new StatusDeliverer();
   private final Map statusListeners = new HashMap();

   private StatusDeliverer() {
   }

   public static StatusDeliverer getInstance() {
      return SINGLETON;
   }

   public void registerStatusListener(String channelId, StatusListener listener) {
      synchronized(this.statusListeners) {
         this.statusListeners.put(channelId, listener);
         if (Debug.isServiceStatusDebugEnabled()) {
            Debug.serviceStatusDebug("added status listener for channel '" + channelId + "'");
         }

      }
   }

   public void unregisterStatusListener(String channelId) {
      synchronized(this.statusListeners) {
         this.statusListeners.remove(channelId);
         if (Debug.isServiceStatusDebugEnabled()) {
            Debug.serviceStatusDebug("removed status listener for channel '" + channelId + "'");
         }

      }
   }

   public void deliverStatus(String channelId, Serializable statusObject, String serverName) {
      StatusListener listener;
      synchronized(this.statusListeners) {
         listener = (StatusListener)this.statusListeners.get(channelId);
      }

      if (listener != null) {
         listener.statusReceived(statusObject, serverName);
         if (Debug.isServiceStatusDebugEnabled()) {
            Debug.serviceStatusDebug("delivered status object '" + statusObject + "' received on channel id '" + channelId + "' from server '" + serverName + "'");
         }
      }

   }

   public void deliverStatus(long sessionId, String channelId, Serializable statusObject, String serverName) {
      StatusListener listener;
      synchronized(this.statusListeners) {
         listener = (StatusListener)this.statusListeners.get(channelId);
      }

      if (listener != null) {
         listener.statusReceived(sessionId, statusObject, serverName);
         if (Debug.isServiceStatusDebugEnabled()) {
            Debug.serviceStatusDebug("delivered status object '" + statusObject + "' with session id '" + sessionId + "' and channelId '" + channelId + "' from server '" + serverName + "'");
         }
      }

   }
}
