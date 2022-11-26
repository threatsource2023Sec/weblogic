package weblogic.servlet.http2;

import java.text.FieldPosition;
import java.text.MessageFormat;
import java.util.HashMap;
import weblogic.servlet.http2.frame.FrameType;

public class MessageManager {
   private static final HashMap MESSAGE_MAP = new HashMap();

   public static String getMessage(String key, Object... args) {
      MessageFormat messageFormat = (MessageFormat)MESSAGE_MAP.get(key);
      return messageFormat == null ? null : messageFormat.format(args, new StringBuffer(), (FieldPosition)null).toString();
   }

   public static void main(String[] args) {
      MessageManager mm = new MessageManager();
      Thread t1 = new Thread(mm.new TestRun(), "t1");
      Thread t2 = new Thread(mm.new TestRun(), "t2");
      t1.start();
      t2.start();
   }

   static {
      MESSAGE_MAP.put("frameType.checkPayloadSize", new MessageFormat("Frame type [{0}] should not have a Payload size of [{1}]"));
      MESSAGE_MAP.put("processFrame.unexpectedType", new MessageFormat("Expected frame type [{0}] but received frame type [{1}]"));
      MESSAGE_MAP.put("frameType.checkFrameType", new MessageFormat("Invalid frame type [{0}]"));
      MESSAGE_MAP.put("processFrame.dependency.invalid", new MessageFormat("For connection [{0}], Stream [{1}] should not depend on itself"));
      MESSAGE_MAP.put("streamStateManager.invalidFrame", new MessageFormat("Invalid Frame type [{0}] received by Stream [{1}] with State [{2}]"));
      MESSAGE_MAP.put("streamProcessor.error.stream", new MessageFormat("An error occurred when processing the stream [{0}]"));
      MESSAGE_MAP.put("stream.reset.fail", new MessageFormat("Failed to reset the Stream [{0}]"));
      MESSAGE_MAP.put("streamProcessor.error.connection", new MessageFormat("An error occurred during processing the connection"));
      MESSAGE_MAP.put("stream.trailer.noEndOfStream", new MessageFormat("The HEADERS frame starting the trailers header block should have the END_STREAM flag set"));
      MESSAGE_MAP.put("stream.header.pseudoHeaderInRegular", new MessageFormat("All pseudo-header fields MUST appear in the header block before regular header fields."));
      MESSAGE_MAP.put("stream.header.pseudoHeaderInTrailer", new MessageFormat("Pseudo-header fields MUST NOT appear in trailers"));
      MESSAGE_MAP.put("stream.header.duplicate", new MessageFormat("Stream [{0}], received multiple [{1}] headers"));
      MESSAGE_MAP.put("stream.header.emptyPath", new MessageFormat("Stream [{0}], The [:path] pseudo header was empty"));
      MESSAGE_MAP.put("stream.header.invalidPath", new MessageFormat("Stream [{0}], The [:path] pseudo header was invalid"));
      MESSAGE_MAP.put("stream.header.unknownPseudoHeader", new MessageFormat("Stream [{0}], Unknown pseudo header [{2}] received"));
      MESSAGE_MAP.put("stream.id.old", new MessageFormat("The identifier of a newly established stream [{0}] MUST be numerically greater than all streams that the initiating endpoint has opened or reserved, the current max stream id is [{1}]"));
      MESSAGE_MAP.put("stream.header.ContentLength", new MessageFormat("For Stream [{0}] and Connection [{1}], [{2}] bytes payload is received, but the content-length set in header is [{3}]."));
      MESSAGE_MAP.put("stream.idle.reset", new MessageFormat("Stream [{0}] in Connection[{1}] is idle, it MUST NOT receive RST_Frame."));
      MESSAGE_MAP.put("stream.remoteStream.old", new MessageFormat("Failed to create remote stream, the id [{0}] is old than current max stream id [{1}]"));
      MESSAGE_MAP.put("stream.remoteStream.even", new MessageFormat("Failed to create remote stream, the id [{0}] must be even"));
      MESSAGE_MAP.put("connectionSettings.enablePushInvalid", new MessageFormat("The enable push value[{0}] should not bigger than 1"));
      MESSAGE_MAP.put("connectionSettings.windowSizeTooBig", new MessageFormat("The window size [{0}] is too bigg. The maximum valid should be[{1}]"));
      MESSAGE_MAP.put("connectionSettings.maxFrameSizeInvalid", new MessageFormat("The maximum frame size of [{0}] is not between [{1} - {2}]"));
      MESSAGE_MAP.put("connectionSettings.headerTableSizeLimit", new MessageFormat("Could not set a header table size of [{0}]. The limit is 16x1024"));
      MESSAGE_MAP.put("http2Parser.processFramePriority.invalidParent", new MessageFormat("Stream [{0}] is depend on itself"));
      MESSAGE_MAP.put("http2Parser.processFrame.tooMuchPadding", new MessageFormat("Stream [{0}], The padding length [{1}] should not bigger than the payload [{2}]"));
      MESSAGE_MAP.put("http2Parser.processFrame.unsupported_push_promise_frame", new MessageFormat("Stream [{0}], unsupported push promise freame with flag [{1}]"));
      MESSAGE_MAP.put("http2Parser.processFrameSettings.ackWithNonZeroPayload", new MessageFormat("Settings frame received with the ACK flag should not have a payload bigger than 0"));
      MESSAGE_MAP.put("http2Parser.processFrame.invalid_push_promise_frame", new MessageFormat("Stream [{0}], invalide push promise freame with padding[{1}], payload [{2}]"));
      MESSAGE_MAP.put("http2Parser.processFrame.invalid_headers_frame", new MessageFormat("Stream [{0}], invalide headers frame with length [{1}]"));
      MESSAGE_MAP.put("http2Parser.payloadTooBig", new MessageFormat("The payload is [{0}] bytes long but the maximum frame size is [{1}]"));
      MESSAGE_MAP.put("http2Parser.processContinuationFrame.noHeaders", new MessageFormat("Stream [{0}], A CONTINUATION frame MUST be preceded by a HEADERS, PUSH_PROMISE or CONTINUATION frame without the END_HEADERS flag set."));
      MESSAGE_MAP.put("http2Parser.processHeadersFrame.decodingFailed", new MessageFormat("Decode headers failed"));
      MESSAGE_MAP.put("http2Parser.processHeadersFrame.dataLeft", new MessageFormat("Headers can not be decoded completely"));
      MESSAGE_MAP.put("http2Parser.headers.wrongStream", new MessageFormat("For Connection [{0}] the expected Stream is [{1}], but the Streamid in Frame is [{2}]"));
      MESSAGE_MAP.put("http2Parser.headers.wrongFrameType", new MessageFormat("For Connection [{0}] and Stream [{1}] the expected Frame Type is [{2}], but the received Frame Type is [{3}]"));
      MESSAGE_MAP.put("http2Parser.processFrameWindowUpdate.invalidIncrement", new MessageFormat("For Connection [{0}] and Stream [{1}] the window update size [{2}] is invalid."));
   }

   class TestRun implements Runnable {
      public void run() {
         System.out.println(MessageManager.getMessage("processFrame.unexpectedType", FrameType.DATA, FrameType.GOAWAY));
         System.out.println(MessageManager.getMessage("processFrame.unexpectedType"));
         System.out.println(MessageManager.getMessage("processFrame.unexpectedType", FrameType.HEADERS, null));
         System.out.println(MessageManager.getMessage("processFrame.unexpectedType", null, FrameType.HEADERS));
         System.out.println(MessageManager.getMessage("processFrame.unexpectedType", FrameType.CONTINUATION, FrameType.PRIORITY));
         System.out.println(MessageManager.getMessage("processFrame.unexpectedType"));
      }
   }
}
