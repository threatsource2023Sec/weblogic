package org.python.netty.util.internal;

import java.nio.ByteBuffer;

interface Cleaner {
   void freeDirectBuffer(ByteBuffer var1);
}
