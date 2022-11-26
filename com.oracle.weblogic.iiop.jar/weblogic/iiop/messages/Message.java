package weblogic.iiop.messages;

import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.SystemException;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.iiop.protocol.CorbaStream;

public abstract class Message implements weblogic.iiop.spi.Message {
   private final MessageHeader msgHdr;
   private CorbaInputStream inputStream;
   private CorbaOutputStream outputStream;
   private boolean marshaled;
   private byte maxFormatVersion;

   protected Message(MessageHeader msgHdr) {
      this.marshaled = false;
      this.maxFormatVersion = 1;
      this.msgHdr = msgHdr;
   }

   protected Message(MessageHeader msgHdr, CorbaInputStream inputStream) {
      this(msgHdr);
      this.inputStream = inputStream;
   }

   public final int getMsgType() {
      return this.msgHdr.getMsgType();
   }

   public final int getMsgSize() {
      return this.msgHdr.getMsgSize();
   }

   public final boolean isFragmented() {
      return this.msgHdr.isFragmented();
   }

   public final int getMinorVersion() {
      int minorVersion = this.msgHdr.getMinorVersion();
      switch (minorVersion) {
         case 0:
         case 1:
         case 2:
            return minorVersion;
         default:
            throw new MARSHAL("Unsupported GIOP minor version.");
      }
   }

   public final byte getMaxStreamFormatVersion() {
      return this.maxFormatVersion;
   }

   final void setMaxStreamFormatVersion(byte sfv) {
      this.maxFormatVersion = sfv;
   }

   final void alignOnEightByteBoundry(CorbaStream stream) {
      switch (this.getMinorVersion()) {
         case 2:
            stream.setNeedEightByteAlignment();
         case 0:
         case 1:
         default:
      }
   }

   public CorbaOutputStream marshalTo(CorbaOutputStream outputStream) {
      assert this.outputStream == null : "Message has already be written to the stream!";

      outputStream.setMaxStreamFormatVersion(this.maxFormatVersion);
      this.write(outputStream);
      outputStream.setMessage(this);
      this.outputStream = outputStream;
      return outputStream;
   }

   public final CorbaOutputStream getOutputStream() {
      return this.outputStream;
   }

   public void write(CorbaOutputStream os) throws SystemException {
      this.msgHdr.write(os);
   }

   public void read(CorbaInputStream is) throws SystemException {
   }

   public void unmarshal() {
      assert this.inputStream != null;

      if (!this.marshaled) {
         this.read(this.inputStream);
         this.marshaled = true;
      }

   }

   public final CorbaInputStream getInputStream() {
      return this.inputStream;
   }

   protected static void p(String m) {
      System.err.println("<Message> " + m);
   }
}
