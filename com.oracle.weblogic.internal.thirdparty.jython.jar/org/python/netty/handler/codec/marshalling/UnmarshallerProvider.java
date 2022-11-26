package org.python.netty.handler.codec.marshalling;

import org.jboss.marshalling.Unmarshaller;
import org.python.netty.channel.ChannelHandlerContext;

public interface UnmarshallerProvider {
   Unmarshaller getUnmarshaller(ChannelHandlerContext var1) throws Exception;
}
