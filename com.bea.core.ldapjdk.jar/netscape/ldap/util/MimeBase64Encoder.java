package netscape.ldap.util;

public final class MimeBase64Encoder extends MimeEncoder {
   static final long serialVersionUID = 8781620079813078315L;
   private int buf = 0;
   private int buf_bytes = 0;
   private byte[] line = new byte[74];
   private int line_length = 0;
   private static final byte[] crlf = "\r\n".getBytes();
   private static final char[] map = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

   private final void encode_token() {
      int var1 = this.line_length;
      this.line[var1] = (byte)map[63 & this.buf >> 18];
      this.line[var1 + 1] = (byte)map[63 & this.buf >> 12];
      this.line[var1 + 2] = (byte)map[63 & this.buf >> 6];
      this.line[var1 + 3] = (byte)map[63 & this.buf];
      this.line_length += 4;
      this.buf = 0;
      this.buf_bytes = 0;
   }

   private final void encode_partial_token() {
      int var1 = this.line_length;
      this.line[var1] = (byte)map[63 & this.buf >> 18];
      this.line[var1 + 1] = (byte)map[63 & this.buf >> 12];
      if (this.buf_bytes == 1) {
         this.line[var1 + 2] = 61;
      } else {
         this.line[var1 + 2] = (byte)map[63 & this.buf >> 6];
      }

      if (this.buf_bytes <= 2) {
         this.line[var1 + 3] = 61;
      } else {
         this.line[var1 + 3] = (byte)map[63 & this.buf];
      }

      this.line_length += 4;
      this.buf = 0;
      this.buf_bytes = 0;
   }

   private final void flush_line(ByteBuf var1) {
      var1.append(this.line, 0, this.line_length);
      this.line_length = 0;
   }

   public final void translate(ByteBuf var1, ByteBuf var2) {
      byte[] var3 = var1.toBytes();
      int var4 = var1.length();

      int var5;
      for(var5 = 0; var5 < var4; ++var5) {
         if (this.buf_bytes == 0) {
            this.buf = this.buf & '\uffff' | var3[var5] << 16;
         } else if (this.buf_bytes == 1) {
            this.buf = this.buf & 16711935 | var3[var5] << 8 & '\uffff';
         } else {
            this.buf = this.buf & 16776960 | var3[var5] & 255;
         }

         if (++this.buf_bytes == 3) {
            this.encode_token();
            if (this.line_length >= 72) {
               this.flush_line(var2);
            }
         }

         if (var5 == var4 - 1) {
            if (this.buf_bytes > 0 && this.buf_bytes < 3) {
               this.encode_partial_token();
            }

            if (this.line_length > 0) {
               this.flush_line(var2);
            }
         }
      }

      for(var5 = 0; var5 < this.line.length; ++var5) {
         this.line[var5] = 0;
      }

   }

   public final void eof(ByteBuf var1) {
      if (this.buf_bytes != 0) {
         this.encode_partial_token();
      }

      this.flush_line(var1);

      for(int var2 = 0; var2 < this.line.length; ++var2) {
         this.line[var2] = 0;
      }

   }
}
