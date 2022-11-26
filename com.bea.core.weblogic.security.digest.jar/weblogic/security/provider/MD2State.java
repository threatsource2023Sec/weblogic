package weblogic.security.provider;

class MD2State {
   static byte[] S = new byte[]{41, 46, 67, -55, -94, -40, 124, 1, 61, 54, 84, -95, -20, -16, 6, 19, 98, -89, 5, -13, -64, -57, 115, -116, -104, -109, 43, -39, -68, 76, -126, -54, 30, -101, 87, 60, -3, -44, -32, 22, 103, 66, 111, 24, -118, 23, -27, 18, -66, 78, -60, -42, -38, -98, -34, 73, -96, -5, -11, -114, -69, 47, -18, 122, -87, 104, 121, -111, 21, -78, 7, 63, -108, -62, 16, -119, 11, 34, 95, 33, -128, 127, 93, -102, 90, -112, 50, 39, 53, 62, -52, -25, -65, -9, -105, 3, -1, 25, 48, -77, 72, -91, -75, -47, -41, 94, -110, 42, -84, 86, -86, -58, 79, -72, 56, -46, -106, -92, 125, -74, 118, -4, 107, -30, -100, 116, 4, -15, 69, -99, 112, 89, 100, 113, -121, 32, -122, 91, -49, 101, -26, 45, -88, 2, 27, 96, 37, -83, -82, -80, -71, -10, 28, 70, 97, 105, 52, 64, 126, 15, 85, 71, -93, 35, -35, 81, -81, 58, -61, 92, -7, -50, -70, -59, -22, 38, 44, 83, 13, 110, -123, 40, -124, 9, -45, -33, -51, -12, 65, -127, 77, 82, 106, -36, 55, -56, 108, -63, -85, -6, 36, -31, 123, 8, 12, -67, -79, 74, 120, -120, -107, -117, -29, 99, -24, 109, -23, -53, -43, -2, 59, 0, 29, 57, -14, -17, -73, 14, 102, 88, -48, -28, -90, 119, 114, -8, -21, 117, 75, 10, 49, 68, 80, -76, -113, -19, 31, 26, -37, -103, -115, 51, -97, 17, -125, 20};
   public byte[] state = new byte[16];
   public byte[] checksum = new byte[16];
   public int count;
   public byte[] buffer;
   private byte[] tmpState = new byte[48];

   public MD2State() {
      this.setArray(this.state, (byte)0);
      this.setArray(this.checksum, (byte)0);
      this.count = 0;
      this.buffer = new byte[16];
   }

   private void setArray(byte[] arr, byte b) {
      if (arr != null) {
         for(int i = 0; i < arr.length; ++i) {
            arr[i] = b;
         }

      }
   }

   public Object clone() {
      MD2State n = new MD2State();
      n.count = this.count;
      System.arraycopy(this.state, 0, n.state, 0, 16);
      System.arraycopy(this.checksum, 0, n.checksum, 0, 16);
      System.arraycopy(this.buffer, 0, n.buffer, 0, 16);
      return n;
   }

   public void update(byte b) {
      this.buffer[this.count++] = b;
      if (this.count == 16) {
         this.block();
         this.count = 0;
      }

   }

   public void block() {
      int i = 0;

      byte[] var10000;
      byte t;
      for(byte s = this.checksum[15]; i < 16; ++i) {
         this.tmpState[i] = this.state[i];
         this.tmpState[i + 16] = t = this.buffer[i];
         this.tmpState[i + 32] = (byte)(t ^ this.state[i]);
         var10000 = this.checksum;
         s = var10000[i] ^= S[(t ^ s) & 255];
      }

      t = 0;

      for(i = 0; i < 18; ++i) {
         for(int j = 0; j < 48; j += 8) {
            var10000 = this.tmpState;
            t = var10000[j + 0] ^= S[t & 255];
            var10000 = this.tmpState;
            t = var10000[j + 1] ^= S[t & 255];
            var10000 = this.tmpState;
            t = var10000[j + 2] ^= S[t & 255];
            var10000 = this.tmpState;
            t = var10000[j + 3] ^= S[t & 255];
            var10000 = this.tmpState;
            t = var10000[j + 4] ^= S[t & 255];
            var10000 = this.tmpState;
            t = var10000[j + 5] ^= S[t & 255];
            var10000 = this.tmpState;
            t = var10000[j + 6] ^= S[t & 255];
            var10000 = this.tmpState;
            t = var10000[j + 7] ^= S[t & 255];
         }

         t = (byte)(t + i & 255);
      }

      System.arraycopy(this.tmpState, 0, this.state, 0, 16);
   }

   void computeCurrent(byte[] digestBits) {
      byte v = (byte)(16 - this.count);

      for(int i = this.count; i < 16; ++i) {
         this.buffer[i] = v;
      }

      this.block();
      System.arraycopy(this.checksum, 0, this.buffer, 0, 16);
      this.block();
      System.arraycopy(this.state, 0, digestBits, 0, 16);
   }
}
