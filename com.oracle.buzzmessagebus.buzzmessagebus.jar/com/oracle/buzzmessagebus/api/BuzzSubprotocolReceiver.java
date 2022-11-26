package com.oracle.buzzmessagebus.api;

import com.oracle.common.net.exabus.EndPoint;

public interface BuzzSubprotocolReceiver {
   void receipt(EndPoint var1, Object var2);

   void receive(byte var1, BuzzMessageToken var2);

   void connect(EndPoint var1);

   void release(EndPoint var1);

   void flush();

   void backlog(boolean var1, EndPoint var2);

   void closeMyId(BuzzMessageToken var1);

   void closeYourId(BuzzMessageToken var1);

   byte getSubprotocolId();

   void setBuzzSender(BuzzSender var1);
}
