package org.python.netty.handler.ssl;

import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufHolder;

interface PemEncoded extends ByteBufHolder {
   boolean isSensitive();

   PemEncoded copy();

   PemEncoded duplicate();

   PemEncoded retainedDuplicate();

   PemEncoded replace(ByteBuf var1);

   PemEncoded retain();

   PemEncoded retain(int var1);

   PemEncoded touch();

   PemEncoded touch(Object var1);
}
