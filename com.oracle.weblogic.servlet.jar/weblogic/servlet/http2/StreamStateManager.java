package weblogic.servlet.http2;

import weblogic.servlet.http2.frame.FrameType;

public class StreamStateManager {
   private StreamState state = null;
   private final Integer streamId;

   public StreamStateManager(Integer streamId) {
      this.state = StreamStates.IDLE;
      this.streamId = streamId;
   }

   public synchronized void changeState(StreamState newState) {
      this.state = newState;
   }

   public synchronized void sendHeaders() {
      if (this.state == StreamStates.IDLE) {
         this.state = StreamStates.OPEN;
      }

      if (this.state == StreamStates.RESERVED_LOCAL) {
         this.state = StreamStates.HALF_CLOSED_REMOTE;
      }

   }

   public synchronized void receivedHeaders() {
      if (this.state == StreamStates.IDLE) {
         this.state = StreamStates.OPEN;
      }

      if (this.state == StreamStates.RESERVED_REMOTE) {
         this.state = StreamStates.HALF_CLOSED_LOCAL;
      }

   }

   public synchronized void promisedBySendingPP() {
      this.state = StreamStates.RESERVED_LOCAL;
   }

   public synchronized void promisedByReceivingPP() {
      this.state = StreamStates.RESERVED_REMOTE;
   }

   public synchronized void sendEndOfStream() {
      if (this.state == StreamStates.OPEN) {
         this.state = StreamStates.HALF_CLOSED_LOCAL;
      }

      if (this.state == StreamStates.HALF_CLOSED_REMOTE) {
         this.state = StreamStates.CLOSED_ON_SENDING_ES;
      }

   }

   public synchronized void receivedEndOfStream() {
      if (this.state == StreamStates.OPEN) {
         this.state = StreamStates.HALF_CLOSED_REMOTE;
      }

      if (this.state == StreamStates.HALF_CLOSED_LOCAL) {
         this.state = StreamStates.CLOSED_ON_RECEIVING_ES;
      }

   }

   public synchronized void sendReset() {
      if (this.state == StreamStates.IDLE) {
         throw new IllegalStateException(this.streamId + "streamStateManager.stateChange" + this.state.toString());
      } else {
         if (!(this.state instanceof ClosedState)) {
            this.state = StreamStates.CLOSED_ON_SENDING_RESET;
         }

      }
   }

   public synchronized boolean isFrameTypeAllowedToReceive(FrameType frameType) {
      return this.state.isFrameTypeAllowedToReceive(frameType);
   }

   public synchronized boolean isFrameTypeAllowedToSend(FrameType frameType) {
      return this.state.isFrameTypeAllowedToSend(frameType);
   }

   public synchronized void receivedReset() {
      this.state = StreamStates.CLOSED;
   }

   public synchronized void checkOnReceiving(FrameType frameType) throws HTTP2Exception {
      if (!this.state.isFrameTypeAllowedToReceive(frameType)) {
         if (this.state.getErrorCode() != 1 && this.state != StreamStates.CLOSED_ON_RECEIVING_ES) {
            throw new StreamException(MessageManager.getMessage("streamStateManager.invalidFrame", frameType, this.streamId, this.state), this.state.getErrorCode(), this.streamId);
         } else {
            throw new ConnectionException(MessageManager.getMessage("streamStateManager.invalidFrame", frameType, this.streamId, this.state), this.state.getErrorCode());
         }
      }
   }

   public synchronized void checkOnSending(FrameType frameType) throws HTTP2Exception {
   }

   public synchronized void closeIfIdle() {
      if (this.state == StreamStates.IDLE) {
         this.state = StreamStates.CLOSED;
      }

   }

   public synchronized boolean isClosed() {
      return this.state == StreamStates.CLOSED || this.state == StreamStates.CLOSED_ON_SENDING_ES || this.state == StreamStates.CLOSED_ON_SENDING_RESET;
   }

   public String toString() {
      return "[StreamStateManager] Stream State is " + this.state + ", Stream Id is " + this.streamId;
   }
}
