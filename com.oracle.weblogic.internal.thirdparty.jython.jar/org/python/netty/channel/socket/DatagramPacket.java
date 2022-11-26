package org.python.netty.channel.socket;

import java.net.InetSocketAddress;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufHolder;
import org.python.netty.channel.DefaultAddressedEnvelope;

public final class DatagramPacket extends DefaultAddressedEnvelope implements ByteBufHolder {
   public DatagramPacket(ByteBuf data, InetSocketAddress recipient) {
      super(data, recipient);
   }

   public DatagramPacket(ByteBuf data, InetSocketAddress recipient, InetSocketAddress sender) {
      super(data, recipient, sender);
   }

   public DatagramPacket copy() {
      return this.replace(((ByteBuf)this.content()).copy());
   }

   public DatagramPacket duplicate() {
      return this.replace(((ByteBuf)this.content()).duplicate());
   }

   public DatagramPacket retainedDuplicate() {
      return this.replace(((ByteBuf)this.content()).retainedDuplicate());
   }

   public DatagramPacket replace(ByteBuf content) {
      return new DatagramPacket(content, (InetSocketAddress)this.recipient(), (InetSocketAddress)this.sender());
   }

   public DatagramPacket retain() {
      super.retain();
      return this;
   }

   public DatagramPacket retain(int increment) {
      super.retain(increment);
      return this;
   }

   public DatagramPacket touch() {
      super.touch();
      return this;
   }

   public DatagramPacket touch(Object hint) {
      super.touch(hint);
      return this;
   }
}
