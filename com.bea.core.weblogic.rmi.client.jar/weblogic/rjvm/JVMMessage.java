package weblogic.rjvm;

import java.io.IOException;
import weblogic.utils.io.ChunkedDataInputStream;
import weblogic.utils.io.ChunkedDataOutputStream;

public final class JVMMessage {
   public static final int MESSAGE_LENGTH_OFFSET = 4;
   public static final int HEADER_LENGTH = 19;
   static final int CONTEXT_JVMID_FLAG = 1;
   static final int CONTEXT_TX_FLAG = 2;
   static final int CONTEXT_TRACE_FLAG = 4;
   static final int CONTEXT_EXTENDED_FLAG = 8;
   static final int CONTEXT_EXTENDED_USER_FLAG = 16;
   Command cmd;
   private int flags;
   byte QOS;
   boolean hasJVMIDs;
   boolean hasTX;
   boolean hasTrace;
   int responseId;
   int invokableId;
   int abbrevOffset;
   JVMID src;
   JVMID dest;

   JVMMessage() {
      this.cmd = JVMMessage.Command.CMD_UNDEFINED;
      this.QOS = 101;
      this.responseId = -1;
      this.invokableId = -1;
   }

   void init(JVMID dest, byte QOS, Command cmd) {
      this.cmd = cmd;
      this.QOS = QOS;
      this.dest = dest;
      this.src = JVMID.localID();
   }

   void init(JVMMessage other) {
      this.cmd = other.cmd;
      this.QOS = other.QOS;
      this.hasJVMIDs = other.hasJVMIDs;
      this.hasTX = other.hasTX;
      this.hasTrace = other.hasTrace;
      this.flags = other.flags;
      this.responseId = other.responseId;
      this.invokableId = other.invokableId;
      this.src = other.src;
      this.dest = other.dest;
      this.abbrevOffset = other.abbrevOffset;
   }

   final void reset() {
      this.hasJVMIDs = false;
      this.hasTX = false;
      this.hasTrace = false;
      this.responseId = 0;
      this.invokableId = 0;
      this.abbrevOffset = 0;
      this.flags = 0;
   }

   public void setFlag(int f) {
      this.flags |= f;
   }

   public boolean getFlag(int f) {
      return (this.flags & f) != 0;
   }

   void readHeader(ChunkedDataInputStream is, int remoteHeaderLen) {
      try {
         this.cmd = JVMMessage.Command.getByValue(is.readByte());
         this.QOS = is.readByte();
         this.flags = is.readByte() & 255;
         this.hasJVMIDs = this.getFlag(1);
         this.hasTX = this.getFlag(2);
         this.hasTrace = this.getFlag(4);
         this.responseId = is.readInt();
         this.invokableId = is.readInt();
         this.abbrevOffset = is.readInt();
         int skip = remoteHeaderLen - 19;
         if (skip > 0) {
            is.skip((long)skip);
         }

      } catch (IOException var4) {
         throw new AssertionError("Exception reading message header", var4);
      }
   }

   void writeHeader(ChunkedDataOutputStream out) {
      out.writeByte(this.cmd.value);
      out.writeByte(this.QOS);
      this.flags |= this.hasJVMIDs ? 1 : 0;
      out.writeByte(this.flags);
      out.writeInt(this.responseId);
      out.writeInt(this.invokableId);
      out.writeInt(this.abbrevOffset);
   }

   private String getFlags() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.hasJVMIDs ? "JVMIDs Sent, " : "JVMIDs Not Sent, ");
      sb.append(this.hasTX ? "TX Context Sent, " : "TX Context Not Sent, ");
      sb.append("0x").append(Integer.toHexString(this.flags));
      return sb.toString();
   }

   public String toString() {
      return "JVMMessage from: '" + this.src + "' to: '" + this.dest + "' cmd: '" + this.cmd.name() + "', QOS: '" + this.QOS + "', responseId: '" + this.responseId + "', invokableId: '" + this.invokableId + "', flags: '" + this.getFlags() + "', abbrev offset: '" + this.abbrevOffset + "'";
   }

   static enum Command {
      CMD_UNDEFINED((byte)0),
      CMD_IDENTIFY_REQUEST((byte)1),
      CMD_IDENTIFY_RESPONSE((byte)2),
      CMD_PEER_GONE((byte)3),
      CMD_ONE_WAY((byte)4),
      CMD_REQUEST((byte)5),
      CMD_RESPONSE((byte)6),
      CMD_ERROR_RESPONSE((byte)7),
      CMD_INTERNAL((byte)8),
      CMD_NO_ROUTE_IDENTIFY_REQUEST((byte)9),
      CMD_TRANSLATED_IDENTIFY_RESPONSE((byte)10),
      CMD_REQUEST_CLOSE((byte)11),
      CMD_IDENTIFY_REQUEST_CSHARP((byte)12),
      CMD_IDENTIFY_RESPONSE_CSHARP((byte)13);

      private byte value;

      private Command(byte val) {
         this.value = val;
      }

      private static Command getByValue(byte value) {
         return values()[value];
      }

      byte getValue() {
         return this.value;
      }
   }
}
