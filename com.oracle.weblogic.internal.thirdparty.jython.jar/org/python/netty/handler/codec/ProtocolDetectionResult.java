package org.python.netty.handler.codec;

import org.python.netty.util.internal.ObjectUtil;

public final class ProtocolDetectionResult {
   private static final ProtocolDetectionResult NEEDS_MORE_DATE;
   private static final ProtocolDetectionResult INVALID;
   private final ProtocolDetectionState state;
   private final Object result;

   public static ProtocolDetectionResult needsMoreData() {
      return NEEDS_MORE_DATE;
   }

   public static ProtocolDetectionResult invalid() {
      return INVALID;
   }

   public static ProtocolDetectionResult detected(Object protocol) {
      return new ProtocolDetectionResult(ProtocolDetectionState.DETECTED, ObjectUtil.checkNotNull(protocol, "protocol"));
   }

   private ProtocolDetectionResult(ProtocolDetectionState state, Object result) {
      this.state = state;
      this.result = result;
   }

   public ProtocolDetectionState state() {
      return this.state;
   }

   public Object detectedProtocol() {
      return this.result;
   }

   static {
      NEEDS_MORE_DATE = new ProtocolDetectionResult(ProtocolDetectionState.NEEDS_MORE_DATA, (Object)null);
      INVALID = new ProtocolDetectionResult(ProtocolDetectionState.INVALID, (Object)null);
   }
}
