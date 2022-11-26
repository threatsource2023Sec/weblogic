package weblogic.servlet.http2;

class StreamStates {
   static final StreamState IDLE = new IdleState();
   static final StreamState OPEN = new OpenState();
   static final StreamState RESERVED_LOCAL = new ReservedLocalState();
   static final StreamState RESERVED_REMOTE = new ReservedRemoteState();
   static final StreamState HALF_CLOSED_LOCAL = new HalfClosedLocalState();
   static final StreamState HALF_CLOSED_REMOTE = new HalfClosedRemoteState();
   static final StreamState CLOSED_ON_SENDING_ES = new SendingEndOfStreamState();
   static final StreamState CLOSED_ON_RECEIVING_ES = new ReceivingEndOfStreamState();
   static final StreamState CLOSED_ON_SENDING_RESET = new SendingResetState();
   static final StreamState CLOSED = new ClosedState();
}
