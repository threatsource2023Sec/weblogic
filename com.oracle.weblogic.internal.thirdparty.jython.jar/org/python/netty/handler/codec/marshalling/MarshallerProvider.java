package org.python.netty.handler.codec.marshalling;

import org.jboss.marshalling.Marshaller;
import org.python.netty.channel.ChannelHandlerContext;

public interface MarshallerProvider {
   Marshaller getMarshaller(ChannelHandlerContext var1) throws Exception;
}
