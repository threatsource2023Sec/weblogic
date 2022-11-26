package org.glassfish.tyrus.core;

import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;

public enum CloseReasons {
   NORMAL_CLOSURE(CloseCodes.NORMAL_CLOSURE, "Normal closure."),
   GOING_AWAY(CloseCodes.GOING_AWAY, "Going away."),
   PROTOCOL_ERROR(CloseCodes.PROTOCOL_ERROR, "Protocol error."),
   CANNOT_ACCEPT(CloseCodes.CANNOT_ACCEPT, "Cannot accept."),
   RESERVED(CloseCodes.RESERVED, "Reserved."),
   NO_STATUS_CODE(CloseCodes.NO_STATUS_CODE, "No status code."),
   CLOSED_ABNORMALLY(CloseCodes.CLOSED_ABNORMALLY, "Closed abnormally."),
   NOT_CONSISTENT(CloseCodes.NOT_CONSISTENT, "Not consistent."),
   VIOLATED_POLICY(CloseCodes.VIOLATED_POLICY, "Violated policy."),
   TOO_BIG(CloseCodes.TOO_BIG, "Too big."),
   NO_EXTENSION(CloseCodes.NO_EXTENSION, "No extension."),
   UNEXPECTED_CONDITION(CloseCodes.UNEXPECTED_CONDITION, "Unexpected condition."),
   SERVICE_RESTART(CloseCodes.SERVICE_RESTART, "Service restart."),
   TRY_AGAIN_LATER(CloseCodes.TRY_AGAIN_LATER, "Try again later."),
   TLS_HANDSHAKE_FAILURE(CloseCodes.TLS_HANDSHAKE_FAILURE, "TLS handshake failure.");

   private final CloseReason closeReason;

   private CloseReasons(CloseReason.CloseCode closeCode, String reasonPhrase) {
      this.closeReason = new CloseReason(closeCode, reasonPhrase);
   }

   public CloseReason getCloseReason() {
      return this.closeReason;
   }
}
