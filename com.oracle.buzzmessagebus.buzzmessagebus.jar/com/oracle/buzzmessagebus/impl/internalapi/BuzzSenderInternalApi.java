package com.oracle.buzzmessagebus.impl.internalapi;

import com.oracle.buzzmessagebus.api.BuzzMessageToken;
import com.oracle.buzzmessagebus.api.BuzzSender;
import com.oracle.common.io.BufferSequence;
import com.oracle.common.net.exabus.EndPoint;
import com.oracle.common.net.exabus.MessageBus;

public interface BuzzSenderInternalApi extends BuzzSender {
   BuzzMessageToken getTokenWithId(EndPoint var1, byte var2, long var3);

   void sendInternal(BuzzMessageToken var1, byte var2, short var3, BufferSequence var4, Object var5, boolean var6);

   MessageBus getBus();

   String getStats();
}
