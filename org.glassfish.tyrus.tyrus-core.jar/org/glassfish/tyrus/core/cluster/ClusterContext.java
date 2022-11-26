package org.glassfish.tyrus.core.cluster;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import javax.websocket.CloseReason;
import javax.websocket.SendHandler;

public abstract class ClusterContext {
   public static final String CLUSTER_CONTEXT = "org.glassfish.tyrus.core.cluster.ClusterContext";

   public abstract Future sendText(String var1, String var2);

   public abstract Future sendText(String var1, String var2, boolean var3);

   public abstract Future sendBinary(String var1, byte[] var2);

   public abstract Future sendBinary(String var1, byte[] var2, boolean var3);

   public abstract Future sendPing(String var1, byte[] var2);

   public abstract Future sendPong(String var1, byte[] var2);

   public abstract void sendText(String var1, String var2, SendHandler var3);

   public abstract void sendBinary(String var1, byte[] var2, SendHandler var3);

   public abstract void broadcastText(String var1, String var2);

   public abstract void broadcastBinary(String var1, byte[] var2);

   public abstract boolean isSessionOpen(String var1, String var2);

   public abstract Future close(String var1);

   public abstract Future close(String var1, CloseReason var2);

   public abstract Set getRemoteSessionIds(String var1);

   public abstract String createSessionId();

   public abstract String createConnectionId();

   public abstract void registerSession(String var1, String var2, SessionEventListener var3);

   public abstract void registerSessionListener(String var1, SessionListener var2);

   public abstract void registerBroadcastListener(String var1, BroadcastListener var2);

   public abstract Map getDistributedSessionProperties(String var1);

   public abstract Map getDistributedUserProperties(String var1);

   public abstract void destroyDistributedUserProperties(String var1);

   public abstract void removeSession(String var1, String var2);

   public abstract void shutdown();
}
