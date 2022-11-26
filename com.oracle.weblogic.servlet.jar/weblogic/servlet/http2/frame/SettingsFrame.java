package weblogic.servlet.http2.frame;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.servlet.http2.ConnectionException;
import weblogic.servlet.http2.Flags;
import weblogic.servlet.http2.HTTP2Exception;
import weblogic.servlet.http2.MessageManager;

public class SettingsFrame extends Frame {
   private Map settings;
   private boolean isAck;

   public SettingsFrame(int payloadSize, int flags, int streamId) {
      super(FrameType.SETTINGS, payloadSize, flags, streamId);
      this.streamRequired = false;
   }

   public SettingsFrame(int flags, int streamId, Map settings, boolean isAck) {
      this(settings.size() * 6, flags, streamId);
      this.settings = settings;
      this.isAck = isAck;
   }

   public Map getSettings() {
      return this.settings;
   }

   public void setSettings(Map settings) {
      this.settings = settings;
   }

   public boolean isAck() {
      return this.isAck;
   }

   public void setAck(boolean isAck) {
      this.isAck = isAck;
   }

   public boolean parseBody(ByteBuffer buffer) throws HTTP2Exception {
      this.isAck = Flags.isAck(this.flags);
      if (this.payloadSize > 0 && this.isAck) {
         throw new ConnectionException(MessageManager.getMessage("http2Parser.processFrameSettings.ackWithNonZeroPayload"), 6);
      } else {
         if (this.payloadSize != 0) {
            if (this.settings == null) {
               this.settings = new HashMap();
            }

            int i = 0;

            for(int j = this.payloadSize / 6; i < j; ++i) {
               int id = buffer.getShort() & 255;
               long value = (long)buffer.getInt() & 4294967295L;
               this.settings.put(id, value);
            }
         }

         return true;
      }
   }

   public byte[] toBytes() {
      if (this.payloadSize > this.maxFrameSize) {
         throw new IllegalArgumentException("Invalid settings, too big");
      } else {
         int flags = 0;
         if (this.isAck) {
            flags |= 1;
         }

         ByteBuffer buffer = ByteBuffer.allocate(this.payloadSize + 9);
         generateHeader(buffer, FrameType.SETTINGS, this.payloadSize, flags, 0);
         Iterator var3 = this.settings.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            int key = (Integer)entry.getKey();
            buffer.put((byte)((key & '\uff00') >> 8));
            buffer.put((byte)(key & 255));
            long value = (Long)entry.getValue();
            buffer.put((byte)((int)((value & -16777216L) >> 24)));
            buffer.put((byte)((int)((value & 16711680L) >> 16)));
            buffer.put((byte)((int)((value & 65280L) >> 8)));
            buffer.put((byte)((int)(value & 255L)));
         }

         return buffer.array();
      }
   }
}
