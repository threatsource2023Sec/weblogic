package weblogic.wtc.jatmi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public final class TypedMBString extends MBStringTypes implements TypedBuffer, Serializable {
   private static final long serialVersionUID = 3469855909867685738L;
   private StringBuffer tstring;
   private byte[] bytes = null;

   public TypedMBString() {
      super("MBSTRING", 26);
      this.tstring = new StringBuffer();
   }

   public TypedMBString(int capacity) {
      super("MBSTRING", 26);
      this.tstring = new StringBuffer(capacity);
   }

   public TypedMBString(String str) {
      super("MBSTRING", 26);
      this.tstring = new StringBuffer(str);
   }

   public TypedMBString(TypedMBString copyFrom) {
      super("MBSTRING", 26);
      this.setMBEncoding(copyFrom.mbencoding);
      this.tstring = copyFrom.tstring == null ? null : new StringBuffer(copyFrom.tstring);
      if (copyFrom.bytes == null) {
         this.bytes = null;
      } else {
         this.bytes = new byte[copyFrom.bytes.length];
         System.arraycopy(copyFrom.bytes, 0, this.bytes, 0, this.bytes.length);
      }

   }

   public StringBuffer getStringBuffer() {
      return this.tstring;
   }

   public byte[] getBytes() {
      return this.bytes;
   }

   public void setBytes(byte[] bytes) {
      this.bytes = bytes;
   }

   public void _tmpresend(DataOutputStream encoder) throws TPException, IOException {
      this.convertToBytes((String)null);
      encoder.write(this.bytes);
   }

   public void _tmpostrecv(DataInputStream decoder, int recv_size) throws TPException, IOException {
      if (recv_size == 0) {
         throw new TPException(4, "Invalid recieved size when receiving MBSTRING (0)");
      } else {
         this.bytes = new byte[recv_size];
         decoder.read(this.bytes);
      }
   }

   public String toString() {
      return this.tstring == null ? null : this.tstring.toString();
   }

   void convertFromBytes(String fml32bufmbenc) throws UnsupportedEncodingException {
      if (this.bytes == null) {
         this.tstring = new StringBuffer();
      } else {
         String charset = MBEncoding.mapTuxedoToJava(this.mbencoding);
         if (charset == null) {
            charset = MBEncoding.mapTuxedoToJava(fml32bufmbenc);
         }

         if (charset == null) {
            charset = MBEncoding.mapTuxedoToJava(MBEncoding.getDefaultMBEncoding());
         }

         String string = new String(this.bytes, charset);
         this.tstring = new StringBuffer(string);
      }
   }

   void convertToBytes(String fml32bufmbenc) throws UnsupportedEncodingException {
      if (this.tstring == null) {
         this.bytes = null;
      } else {
         String charset = MBEncoding.mapTuxedoToJava(this.mbencoding);
         if (charset == null) {
            charset = MBEncoding.mapTuxedoToJava(fml32bufmbenc);
         }

         if (charset == null) {
            charset = MBEncoding.mapTuxedoToJava(MBEncoding.getDefaultMBEncoding());
         }

         this.bytes = this.tstring.toString().getBytes(charset);
      }
   }
}
