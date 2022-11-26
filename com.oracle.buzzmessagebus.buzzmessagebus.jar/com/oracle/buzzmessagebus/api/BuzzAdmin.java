package com.oracle.buzzmessagebus.api;

import com.oracle.common.net.exabus.EndPoint;
import java.util.concurrent.TimeUnit;

public interface BuzzAdmin {
   int getMaxBuzzVersion();

   int getMinBuzzVersion();

   void setLogger(BuzzLogger var1);

   BuzzLogger getLogger();

   String getName();

   String getAddress();

   EndPoint getLocalEndPoint();

   void setApiErrorCheckEnabled(boolean var1);

   void setWireErrorCheckEnabled(boolean var1);

   void close();

   public interface Builder {
      BuzzSender build();

      Builder name(String var1);

      Builder address(String var1);

      Builder addSubprotocolReceiver(BuzzSubprotocolReceiver var1);

      Builder addAdminReceiver(BuzzAdminReceiver var1);

      Builder logger(BuzzLogger var1);

      Builder closeWaitTimeout(long var1, TimeUnit var3);
   }
}
