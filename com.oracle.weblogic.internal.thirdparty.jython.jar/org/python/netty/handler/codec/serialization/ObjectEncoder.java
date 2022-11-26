package org.python.netty.handler.codec.serialization;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufOutputStream;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.MessageToByteEncoder;

@ChannelHandler.Sharable
public class ObjectEncoder extends MessageToByteEncoder {
   private static final byte[] LENGTH_PLACEHOLDER = new byte[4];

   protected void encode(ChannelHandlerContext ctx, Serializable msg, ByteBuf out) throws Exception {
      int startIdx = out.writerIndex();
      ByteBufOutputStream bout = new ByteBufOutputStream(out);
      ObjectOutputStream oout = null;

      try {
         bout.write(LENGTH_PLACEHOLDER);
         oout = new CompactObjectOutputStream(bout);
         oout.writeObject(msg);
         oout.flush();
      } finally {
         if (oout != null) {
            oout.close();
         } else {
            bout.close();
         }

      }

      int endIdx = out.writerIndex();
      out.setInt(startIdx, endIdx - startIdx - 4);
   }
}
