package weblogic.servlet.http2;

public class StreamException extends HTTP2Exception {
   private static final long serialVersionUID = 1L;
   private final int streamId;

   public StreamException(String msg, int errorCode, int streamId) {
      super(msg, errorCode);
      this.streamId = streamId;
   }

   public int getStreamId() {
      return this.streamId;
   }
}
