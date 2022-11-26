package org.python.netty.channel;

import java.net.SocketAddress;
import org.python.netty.util.ReferenceCountUtil;
import org.python.netty.util.ReferenceCounted;
import org.python.netty.util.internal.StringUtil;

public class DefaultAddressedEnvelope implements AddressedEnvelope {
   private final Object message;
   private final SocketAddress sender;
   private final SocketAddress recipient;

   public DefaultAddressedEnvelope(Object message, SocketAddress recipient, SocketAddress sender) {
      if (message == null) {
         throw new NullPointerException("message");
      } else if (recipient == null && sender == null) {
         throw new NullPointerException("recipient and sender");
      } else {
         this.message = message;
         this.sender = sender;
         this.recipient = recipient;
      }
   }

   public DefaultAddressedEnvelope(Object message, SocketAddress recipient) {
      this(message, recipient, (SocketAddress)null);
   }

   public Object content() {
      return this.message;
   }

   public SocketAddress sender() {
      return this.sender;
   }

   public SocketAddress recipient() {
      return this.recipient;
   }

   public int refCnt() {
      return this.message instanceof ReferenceCounted ? ((ReferenceCounted)this.message).refCnt() : 1;
   }

   public AddressedEnvelope retain() {
      ReferenceCountUtil.retain(this.message);
      return this;
   }

   public AddressedEnvelope retain(int increment) {
      ReferenceCountUtil.retain(this.message, increment);
      return this;
   }

   public boolean release() {
      return ReferenceCountUtil.release(this.message);
   }

   public boolean release(int decrement) {
      return ReferenceCountUtil.release(this.message, decrement);
   }

   public AddressedEnvelope touch() {
      ReferenceCountUtil.touch(this.message);
      return this;
   }

   public AddressedEnvelope touch(Object hint) {
      ReferenceCountUtil.touch(this.message, hint);
      return this;
   }

   public String toString() {
      return this.sender != null ? StringUtil.simpleClassName((Object)this) + '(' + this.sender + " => " + this.recipient + ", " + this.message + ')' : StringUtil.simpleClassName((Object)this) + "(=> " + this.recipient + ", " + this.message + ')';
   }
}
