package weblogic.jms.dotnet.transport.t3client;

public class T3Header {
   private final byte command;
   private final byte qos;
   private final byte flags;
   private final int responseId;
   private final int invokeId;
   private int len;
   private int offset;
   static final int SIZE = 19;

   T3Header(MarshalReaderImpl mri) throws Exception {
      this.len = mri.readInt();
      this.command = mri.readByte();
      this.qos = mri.readByte();
      this.flags = mri.readByte();
      this.responseId = mri.readInt();
      this.invokeId = mri.readInt();
      this.offset = mri.readInt();
   }

   T3Header(byte cmd, byte qos, byte flags, int responseId, int invokeId) {
      this.command = cmd;
      this.qos = qos;
      this.flags = flags;
      this.responseId = responseId;
      this.invokeId = invokeId;
   }

   synchronized void setMessageLength(int len) {
      this.len = len;
   }

   synchronized void setOffset(int offset) {
      this.offset = offset;
   }

   byte getCommand() {
      return this.command;
   }

   byte getQOS() {
      return this.qos;
   }

   byte getFlags() {
      return this.flags;
   }

   int getResponseId() {
      return this.responseId;
   }

   int getInvokeId() {
      return this.invokeId;
   }

   synchronized int getMessageLength() {
      return this.len;
   }

   synchronized int getOffset() {
      return this.offset;
   }

   public void write(MarshalWriterImpl mwi) throws Exception {
      mwi.writeInt(this.getMessageLength());
      mwi.writeByte(this.command);
      mwi.writeByte(this.qos);
      mwi.writeByte(this.flags);
      mwi.writeInt(this.responseId);
      mwi.writeInt(this.invokeId);
      mwi.writeInt(this.getOffset());
   }

   public String toString() {
      return "{  *****  header  *****" + "\tlength:     " + this.len + "\tcommand:    " + this.command + "\tqos:        " + this.qos + "\tflags:      " + this.flags + "\tresponseId: " + this.responseId + "\tinvokeId:   " + this.invokeId + "\toffset:     " + this.offset + "}";
   }
}
