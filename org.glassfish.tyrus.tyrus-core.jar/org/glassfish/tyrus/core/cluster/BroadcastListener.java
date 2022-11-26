package org.glassfish.tyrus.core.cluster;

public interface BroadcastListener {
   void onBroadcast(String var1);

   void onBroadcast(byte[] var1);
}
