package netscape.ldap.util;

public final class MimeBase64Decoder extends MimeEncoder {
   static final long serialVersionUID = 797397585345375903L;
   private byte[] token = new byte[4];
   private byte[] bytes = new byte[3];
   private int token_length = 0;
   private static final byte NUL = 127;
   private static final byte EOF = 126;
   private static final byte[] map = new byte[]{127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 62, 127, 127, 127, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 127, 127, 127, 126, 127, 127, 127, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 127, 127, 127, 127, 127, 127, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127};

   private final void decode_token(ByteBuf var1) {
      int var2 = map[this.token[0]] << 18 | map[this.token[1]] << 12 | map[this.token[2]] << 6 | map[this.token[3]];
      this.bytes[0] = (byte)(255 & var2 >> 16);
      this.bytes[1] = (byte)(255 & var2 >> 8);
      this.bytes[2] = (byte)(255 & var2);
      var1.append(this.bytes);
   }

   private final void decode_final_token(ByteBuf var1) {
      byte var2 = map[this.token[0]];
      byte var3 = map[this.token[1]];
      byte var4 = map[this.token[2]];
      byte var5 = map[this.token[3]];
      int var6 = 0;
      if (var2 == 126) {
         var2 = 0;
         ++var6;
      }

      if (var3 == 126) {
         var3 = 0;
         ++var6;
      }

      if (var4 == 126) {
         var4 = 0;
         ++var6;
      }

      if (var5 == 126) {
         var5 = 0;
         ++var6;
      }

      int var7 = var2 << 18 | var3 << 12 | var4 << 6 | var5;
      var1.append((byte)(var7 >> 16));
      if (var6 <= 1) {
         var1.append((byte)(var7 >> 8 & 255));
         if (var6 == 0) {
            var1.append((byte)(var7 & 255));
         }
      }

   }

   public final void translate(ByteBuf var1, ByteBuf var2) {
      if (this.token != null) {
         byte[] var3 = var1.toBytes();
         int var4 = var1.length();

         for(int var5 = 0; var5 < var4; ++var5) {
            byte var6 = var3[var5];
            int var7 = var6 & 255;
            byte var8 = map[var7];
            if (var8 != 127) {
               this.token[this.token_length++] = var6;
            }

            if (var8 == 126) {
               this.eof(var2);
               return;
            }

            if (this.token_length == 4) {
               this.decode_token(var2);
               this.token_length = 0;
            }
         }

      }
   }

   public final void eof(ByteBuf var1) {
      if (this.token != null && this.token_length != 0) {
         while(this.token_length < 4) {
            this.token[this.token_length++] = 61;
         }

         this.decode_final_token(var1);
      }

      this.token_length = 0;
      this.token = new byte[4];
      this.bytes = new byte[3];
   }
}
