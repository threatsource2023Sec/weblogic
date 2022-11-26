package com.oracle.buzzmessagebus.api;

import com.oracle.common.io.BufferSequence;
import com.oracle.common.net.exabus.EndPoint;

public interface BuzzSender extends AutoCloseable {
   BuzzAdmin getBuzzAdmin();

   EndPoint resolveEndPoint(String var1);

   void connect(EndPoint var1);

   void release(EndPoint var1);

   void flush();

   BuzzMessageToken getToken(EndPoint var1, byte var2);

   BuzzMessageToken getToken(BuzzMessageToken var1);

   void send(BuzzMessageToken var1, byte var2, short var3, BufferSequence var4);

   void send(BuzzMessageToken var1, byte var2, short var3, BufferSequence var4, Object var5);

   void closeMyId(BuzzMessageToken var1, String... var2);

   void closeYourId(BuzzMessageToken var1, String... var2);
}
