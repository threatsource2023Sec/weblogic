package org.glassfish.grizzly.http;

import org.glassfish.grizzly.filterchain.FilterChainEvent;

public class HttpEvents {
   public static IncomingHttpUpgradeEvent createIncomingUpgradeEvent(HttpHeader httpHeader) {
      return new IncomingHttpUpgradeEvent(httpHeader);
   }

   public static OutgoingHttpUpgradeEvent createOutgoingUpgradeEvent(HttpHeader httpHeader) {
      return new OutgoingHttpUpgradeEvent(httpHeader);
   }

   public static ChangePacketInProgressEvent createChangePacketInProgressEvent(HttpHeader packet) {
      return new ChangePacketInProgressEvent(packet);
   }

   private abstract static class HttpUpgradeEvent implements FilterChainEvent {
      private final HttpHeader httpHeader;

      private HttpUpgradeEvent(HttpHeader httpHeader) {
         this.httpHeader = httpHeader;
      }

      public HttpHeader getHttpHeader() {
         return this.httpHeader;
      }

      // $FF: synthetic method
      HttpUpgradeEvent(HttpHeader x0, Object x1) {
         this(x0);
      }
   }

   public static final class OutgoingHttpUpgradeEvent extends HttpUpgradeEvent {
      public static final Object TYPE = OutgoingHttpUpgradeEvent.class.getName();

      private OutgoingHttpUpgradeEvent(HttpHeader httpHeader) {
         super(httpHeader, null);
      }

      public Object type() {
         return TYPE;
      }

      // $FF: synthetic method
      OutgoingHttpUpgradeEvent(HttpHeader x0, Object x1) {
         this(x0);
      }
   }

   public static final class IncomingHttpUpgradeEvent extends HttpUpgradeEvent {
      public static final Object TYPE = IncomingHttpUpgradeEvent.class.getName();

      private IncomingHttpUpgradeEvent(HttpHeader httpHeader) {
         super(httpHeader, null);
      }

      public Object type() {
         return TYPE;
      }

      // $FF: synthetic method
      IncomingHttpUpgradeEvent(HttpHeader x0, Object x1) {
         this(x0);
      }
   }

   public static final class ResponseCompleteEvent implements FilterChainEvent {
      public static final Object TYPE = ResponseCompleteEvent.class.getName();

      public Object type() {
         return TYPE;
      }
   }

   public static final class ChangePacketInProgressEvent implements FilterChainEvent {
      public static final Object TYPE = ChangePacketInProgressEvent.class.getName();
      private final HttpHeader httpHeader;

      private ChangePacketInProgressEvent(HttpHeader httpHeader) {
         this.httpHeader = httpHeader;
      }

      public HttpHeader getPacket() {
         return this.httpHeader;
      }

      public Object type() {
         return TYPE;
      }

      // $FF: synthetic method
      ChangePacketInProgressEvent(HttpHeader x0, Object x1) {
         this(x0);
      }
   }
}
