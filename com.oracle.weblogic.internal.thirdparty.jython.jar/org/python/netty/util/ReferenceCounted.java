package org.python.netty.util;

public interface ReferenceCounted {
   int refCnt();

   ReferenceCounted retain();

   ReferenceCounted retain(int var1);

   ReferenceCounted touch();

   ReferenceCounted touch(Object var1);

   boolean release();

   boolean release(int var1);
}
