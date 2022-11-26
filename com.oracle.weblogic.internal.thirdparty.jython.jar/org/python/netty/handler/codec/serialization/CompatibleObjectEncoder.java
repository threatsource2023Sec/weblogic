package org.python.netty.handler.codec.serialization;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufOutputStream;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.MessageToByteEncoder;

public class CompatibleObjectEncoder extends MessageToByteEncoder {
   private final int resetInterval;
   private int writtenObjects;

   public CompatibleObjectEncoder() {
      this(16);
   }

   public CompatibleObjectEncoder(int resetInterval) {
      if (resetInterval < 0) {
         throw new IllegalArgumentException("resetInterval: " + resetInterval);
      } else {
         this.resetInterval = resetInterval;
      }
   }

   protected ObjectOutputStream newObjectOutputStream(OutputStream out) throws Exception {
      return new ObjectOutputStream(out);
   }

   protected void encode(ChannelHandlerContext ctx, Serializable msg, ByteBuf out) throws Exception {
      ObjectOutputStream oos = this.newObjectOutputStream(new ByteBufOutputStream(out));

      try {
         if (this.resetInterval != 0) {
            ++this.writtenObjects;
            if (this.writtenObjects % this.resetInterval == 0) {
               oos.reset();
            }
         }

         oos.writeObject(msg);
         oos.flush();
      } finally {
         oos.close();
      }

   }
}
