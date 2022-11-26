package org.python.netty.util.internal;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

class UnpaddedInternalThreadLocalMap {
   static final ThreadLocal slowThreadLocalMap = new ThreadLocal();
   static final AtomicInteger nextIndex = new AtomicInteger();
   Object[] indexedVariables;
   int futureListenerStackDepth;
   int localChannelReaderStackDepth;
   Map handlerSharableCache;
   IntegerHolder counterHashCode;
   ThreadLocalRandom random;
   Map typeParameterMatcherGetCache;
   Map typeParameterMatcherFindCache;
   StringBuilder stringBuilder;
   Map charsetEncoderCache;
   Map charsetDecoderCache;
   ArrayList arrayList;

   UnpaddedInternalThreadLocalMap(Object[] indexedVariables) {
      this.indexedVariables = indexedVariables;
   }
}
