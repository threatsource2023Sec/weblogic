package org.glassfish.tyrus.core;

import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

public class Utf8DecodingException extends WebSocketException {
   private static final CloseReason CLOSE_REASON;
   private static final long serialVersionUID = 7766051445796057L;

   public Utf8DecodingException() {
      super(CLOSE_REASON.getReasonPhrase());
   }

   public CloseReason getCloseReason() {
      return CLOSE_REASON;
   }

   static {
      CLOSE_REASON = new CloseReason(CloseCodes.NOT_CONSISTENT, LocalizationMessages.ILLEGAL_UTF_8_SEQUENCE());
   }
}
