package com.oracle.buzzmessagebus.api;

import com.oracle.common.io.BufferSequence;
import com.oracle.common.net.exabus.EndPoint;

public interface BuzzMessageToken {
   EndPoint getEndPoint();

   byte getSubprotocol();

   Object setSubprotocolCookie(Object var1);

   Object getSubprotocolCookie();

   BuzzTypes.BuzzMessageState getMessageState();

   BufferSequence getBufferSequence();

   boolean containsSubprotocolHeaderLength(BufferSequence var1);
}
