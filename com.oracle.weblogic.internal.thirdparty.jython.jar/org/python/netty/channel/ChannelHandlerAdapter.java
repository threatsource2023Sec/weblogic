package org.python.netty.channel;

import java.util.Map;
import org.python.netty.util.internal.InternalThreadLocalMap;

public abstract class ChannelHandlerAdapter implements ChannelHandler {
   boolean added;

   protected void ensureNotSharable() {
      if (this.isSharable()) {
         throw new IllegalStateException("ChannelHandler " + this.getClass().getName() + " is not allowed to be shared");
      }
   }

   public boolean isSharable() {
      Class clazz = this.getClass();
      Map cache = InternalThreadLocalMap.get().handlerSharableCache();
      Boolean sharable = (Boolean)cache.get(clazz);
      if (sharable == null) {
         sharable = clazz.isAnnotationPresent(ChannelHandler.Sharable.class);
         cache.put(clazz, sharable);
      }

      return sharable;
   }

   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
   }

   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
   }

   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      ctx.fireExceptionCaught(cause);
   }
}
