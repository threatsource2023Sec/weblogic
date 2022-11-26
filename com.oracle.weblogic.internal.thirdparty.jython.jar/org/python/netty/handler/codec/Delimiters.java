package org.python.netty.handler.codec;

import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.Unpooled;

public final class Delimiters {
   public static ByteBuf[] nulDelimiter() {
      return new ByteBuf[]{Unpooled.wrappedBuffer(new byte[]{0})};
   }

   public static ByteBuf[] lineDelimiter() {
      return new ByteBuf[]{Unpooled.wrappedBuffer(new byte[]{13, 10}), Unpooled.wrappedBuffer(new byte[]{10})};
   }

   private Delimiters() {
   }
}
