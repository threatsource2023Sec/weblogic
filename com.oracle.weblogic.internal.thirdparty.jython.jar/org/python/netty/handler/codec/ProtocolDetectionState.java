package org.python.netty.handler.codec;

public enum ProtocolDetectionState {
   NEEDS_MORE_DATA,
   INVALID,
   DETECTED;
}
