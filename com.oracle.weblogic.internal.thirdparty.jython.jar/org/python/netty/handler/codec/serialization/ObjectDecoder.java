package org.python.netty.handler.codec.serialization;

import java.io.ObjectInputStream;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufInputStream;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class ObjectDecoder extends LengthFieldBasedFrameDecoder {
   private final ClassResolver classResolver;

   public ObjectDecoder(ClassResolver classResolver) {
      this(1048576, classResolver);
   }

   public ObjectDecoder(int maxObjectSize, ClassResolver classResolver) {
      super(maxObjectSize, 0, 4, 0, 4);
      this.classResolver = classResolver;
   }

   protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
      ByteBuf frame = (ByteBuf)super.decode(ctx, in);
      if (frame == null) {
         return null;
      } else {
         ObjectInputStream ois = new CompactObjectInputStream(new ByteBufInputStream(frame, true), this.classResolver);

         Object var5;
         try {
            var5 = ois.readObject();
         } finally {
            ois.close();
         }

         return var5;
      }
   }
}
