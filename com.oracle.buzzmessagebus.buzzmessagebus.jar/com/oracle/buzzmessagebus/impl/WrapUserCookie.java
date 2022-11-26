package com.oracle.buzzmessagebus.impl;

import com.oracle.buzzmessagebus.api.BuzzSubprotocolReceiver;
import com.oracle.common.io.BufferSequence;

class WrapUserCookie {
   final BufferSequence header;
   final BuzzSubprotocolReceiver receiver;
   final Object userCookie;

   WrapUserCookie(BufferSequence header, BuzzSubprotocolReceiver receiver, Object userCookie) {
      this.header = header;
      this.receiver = receiver;
      this.userCookie = userCookie;
   }

   public String toString() {
      return "WrapUserCookie{" + this.receiver + "; " + this.userCookie + "; " + this.header + "}";
   }
}
