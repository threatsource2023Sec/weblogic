package com.oracle.buzzmessagebus.impl.internalapi;

import com.oracle.buzzmessagebus.api.BuzzMessageToken;
import com.oracle.buzzmessagebus.api.BuzzSender;
import com.oracle.buzzmessagebus.api.BuzzSubprotocolReceiver;
import com.oracle.common.net.exabus.EndPoint;

public interface BuzzSubprotocolReceiverInternalApi extends BuzzSubprotocolReceiver {
   void receiveRstInternal(BuzzSender var1, EndPoint var2, long var3, int var5, BuzzMessageToken var6);
}
