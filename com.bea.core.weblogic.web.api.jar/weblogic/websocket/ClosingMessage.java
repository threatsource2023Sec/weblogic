package weblogic.websocket;

/** @deprecated */
@Deprecated
public interface ClosingMessage {
   int NO_STATUS_CODE = -1;
   int SC_NORMAL_CLOSURE = 1000;
   int SC_GOING_AWAY = 1001;
   int SC_PROTOCOL_ERROR = 1002;
   int SC_UNSUPPORTED_DATA = 1003;
   int SC_INVALID_FRAME_PAYLOAD_DATA = 1007;
   int SC_POLICY_VOILATION = 1008;
   int SC_MESSAGE_TOO_BIG = 1009;
   int SC_INTERNAL_SERVER_ERROR = 1011;

   int getStatusCode();

   String getReason();
}
