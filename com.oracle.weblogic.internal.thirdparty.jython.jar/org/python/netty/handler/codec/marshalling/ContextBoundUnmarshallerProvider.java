package org.python.netty.handler.codec.marshalling;

import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.util.Attribute;
import org.python.netty.util.AttributeKey;

public class ContextBoundUnmarshallerProvider extends DefaultUnmarshallerProvider {
   private static final AttributeKey UNMARSHALLER = AttributeKey.valueOf(ContextBoundUnmarshallerProvider.class, "UNMARSHALLER");

   public ContextBoundUnmarshallerProvider(MarshallerFactory factory, MarshallingConfiguration config) {
      super(factory, config);
   }

   public Unmarshaller getUnmarshaller(ChannelHandlerContext ctx) throws Exception {
      Attribute attr = ctx.attr(UNMARSHALLER);
      Unmarshaller unmarshaller = (Unmarshaller)attr.get();
      if (unmarshaller == null) {
         unmarshaller = super.getUnmarshaller(ctx);
         attr.set(unmarshaller);
      }

      return unmarshaller;
   }
}
