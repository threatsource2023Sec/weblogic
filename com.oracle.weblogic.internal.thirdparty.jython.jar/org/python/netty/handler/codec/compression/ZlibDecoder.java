package org.python.netty.handler.codec.compression;

import org.python.netty.handler.codec.ByteToMessageDecoder;

public abstract class ZlibDecoder extends ByteToMessageDecoder {
   public abstract boolean isClosed();
}
