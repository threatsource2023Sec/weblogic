package org.python.netty.handler.codec.marshalling;

import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.MarshallingConfiguration;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.util.concurrent.FastThreadLocal;

public class ThreadLocalMarshallerProvider implements MarshallerProvider {
   private final FastThreadLocal marshallers = new FastThreadLocal();
   private final MarshallerFactory factory;
   private final MarshallingConfiguration config;

   public ThreadLocalMarshallerProvider(MarshallerFactory factory, MarshallingConfiguration config) {
      this.factory = factory;
      this.config = config;
   }

   public Marshaller getMarshaller(ChannelHandlerContext ctx) throws Exception {
      Marshaller marshaller = (Marshaller)this.marshallers.get();
      if (marshaller == null) {
         marshaller = this.factory.createMarshaller(this.config);
         this.marshallers.set(marshaller);
      }

      return marshaller;
   }
}
