package weblogic.security.provider;

import weblogic.security.MessageDigest;

public final class JavaSHA extends MessageDigest implements Cloneable {
   private static int DIGESTSIZE = 20;
   private static int DATASIZE = 64;
   private SHAState state;
   private byte[] digestBits;
   private boolean digestValid;
   private static int K1 = 1518500249;
   private static int K2 = 1859775393;
   private static int K3 = -1894007588;
   private static int K4 = -899497514;
   private static int h0init = 1732584193;
   private static int h1init = -271733879;
   private static int h2init = -1732584194;
   private static int h3init = 271733878;
   private static int h4init = -1009589776;

   public JavaSHA() {
      super("SHA");
      this.init();
   }

   public Object clone() {
      JavaSHA sha = new JavaSHA();
      sha.state = (SHAState)this.state.clone();
      if (this.digestBits != null) {
         sha.digestBits = new byte[20];
         System.arraycopy(this.digestBits, 0, sha.digestBits, 0, 20);
      }

      return sha;
   }

   public void update(byte[] input, int off, int len) {
      int tmp = this.state.countLo;
      if ((this.state.countLo = tmp + (len << 3)) < tmp) {
         ++this.state.countHi;
      }

      SHAState var10000 = this.state;
      var10000.countHi += len >>> 29;
      int num = tmp >>> 3 & 63;
      if (num != 0) {
         if (num + len < DATASIZE) {
            System.arraycopy(input, off, this.state.bdata, num, len);
            return;
         }

         System.arraycopy(input, off, this.state.bdata, num, DATASIZE - num);
         this.do_block(this.state.bdata, 0);
         off += DATASIZE - num;
         len -= DATASIZE - num;
         int num = false;
      }

      if (len >= DATASIZE) {
         do {
            this.do_block(input, off);
            off += DATASIZE;
            len -= DATASIZE;
         } while(len >= DATASIZE);
      }

      System.arraycopy(input, off, this.state.bdata, 0, len);
   }

   public void update(byte aValue) {
      byte[] a = new byte[]{aValue};
      this.update(a);
   }

   public byte[] digest() {
      if (!this.digestValid) {
         this.computeCurrent();
      }

      return this.digestBits;
   }

   public void reset() {
      this.init();
   }

   private void init() {
      this.state = new SHAState();
      this.state.digest = new int[DIGESTSIZE / 4];
      this.state.data = new int[DATASIZE / 4];
      this.state.bdata = new byte[DATASIZE];
      this.state.digest[0] = h0init;
      this.state.digest[1] = h1init;
      this.state.digest[2] = h2init;
      this.state.digest[3] = h3init;
      this.state.digest[4] = h4init;
      this.state.countLo = this.state.countHi = 0;
      this.digestValid = false;
   }

   private int f1(int x, int y, int z) {
      return z ^ x & (y ^ z);
   }

   private int f2(int x, int y, int z) {
      return x ^ y ^ z;
   }

   private int f3(int x, int y, int z) {
      return x & y | z & (x | y);
   }

   private int f4(int x, int y, int z) {
      return x ^ y ^ z;
   }

   private int ROTL(int n, int X) {
      return X << n | X >>> 32 - n;
   }

   private int expand(int[] W, int i) {
      int val = W[i & 15] = this.ROTL(1, W[i & 15] ^ W[i - 14 & 15] ^ W[i - 8 & 15] ^ W[i - 3 & 15]);
      return val;
   }

   private int subRound(int a, int e, int f, int k, int data) {
      return e + this.ROTL(5, a) + f + k + data;
   }

   private void Transform(int[] digest, int[] data, int off) {
      int[] eData = new int[DATASIZE / 4];
      System.arraycopy(data, off, eData, 0, DATASIZE / 4);
      int A = digest[0];
      int B = digest[1];
      int C = digest[2];
      int D = digest[3];
      int E = digest[4];
      E = this.subRound(A, E, this.f1(B, C, D), K1, eData[0]);
      B = this.ROTL(30, B);
      D = this.subRound(E, D, this.f1(A, B, C), K1, eData[1]);
      A = this.ROTL(30, A);
      C = this.subRound(D, C, this.f1(E, A, B), K1, eData[2]);
      E = this.ROTL(30, E);
      B = this.subRound(C, B, this.f1(D, E, A), K1, eData[3]);
      D = this.ROTL(30, D);
      A = this.subRound(B, A, this.f1(C, D, E), K1, eData[4]);
      C = this.ROTL(30, C);
      E = this.subRound(A, E, this.f1(B, C, D), K1, eData[5]);
      B = this.ROTL(30, B);
      D = this.subRound(E, D, this.f1(A, B, C), K1, eData[6]);
      A = this.ROTL(30, A);
      C = this.subRound(D, C, this.f1(E, A, B), K1, eData[7]);
      E = this.ROTL(30, E);
      B = this.subRound(C, B, this.f1(D, E, A), K1, eData[8]);
      D = this.ROTL(30, D);
      A = this.subRound(B, A, this.f1(C, D, E), K1, eData[9]);
      C = this.ROTL(30, C);
      E = this.subRound(A, E, this.f1(B, C, D), K1, eData[10]);
      B = this.ROTL(30, B);
      D = this.subRound(E, D, this.f1(A, B, C), K1, eData[11]);
      A = this.ROTL(30, A);
      C = this.subRound(D, C, this.f1(E, A, B), K1, eData[12]);
      E = this.ROTL(30, E);
      B = this.subRound(C, B, this.f1(D, E, A), K1, eData[13]);
      D = this.ROTL(30, D);
      A = this.subRound(B, A, this.f1(C, D, E), K1, eData[14]);
      C = this.ROTL(30, C);
      E = this.subRound(A, E, this.f1(B, C, D), K1, eData[15]);
      B = this.ROTL(30, B);
      D = this.subRound(E, D, this.f1(A, B, C), K1, this.expand(eData, 16));
      A = this.ROTL(30, A);
      C = this.subRound(D, C, this.f1(E, A, B), K1, this.expand(eData, 17));
      E = this.ROTL(30, E);
      B = this.subRound(C, B, this.f1(D, E, A), K1, this.expand(eData, 18));
      D = this.ROTL(30, D);
      A = this.subRound(B, A, this.f1(C, D, E), K1, this.expand(eData, 19));
      C = this.ROTL(30, C);
      E = this.subRound(A, E, this.f2(B, C, D), K2, this.expand(eData, 20));
      B = this.ROTL(30, B);
      D = this.subRound(E, D, this.f2(A, B, C), K2, this.expand(eData, 21));
      A = this.ROTL(30, A);
      C = this.subRound(D, C, this.f2(E, A, B), K2, this.expand(eData, 22));
      E = this.ROTL(30, E);
      B = this.subRound(C, B, this.f2(D, E, A), K2, this.expand(eData, 23));
      D = this.ROTL(30, D);
      A = this.subRound(B, A, this.f2(C, D, E), K2, this.expand(eData, 24));
      C = this.ROTL(30, C);
      E = this.subRound(A, E, this.f2(B, C, D), K2, this.expand(eData, 25));
      B = this.ROTL(30, B);
      D = this.subRound(E, D, this.f2(A, B, C), K2, this.expand(eData, 26));
      A = this.ROTL(30, A);
      C = this.subRound(D, C, this.f2(E, A, B), K2, this.expand(eData, 27));
      E = this.ROTL(30, E);
      B = this.subRound(C, B, this.f2(D, E, A), K2, this.expand(eData, 28));
      D = this.ROTL(30, D);
      A = this.subRound(B, A, this.f2(C, D, E), K2, this.expand(eData, 29));
      C = this.ROTL(30, C);
      E = this.subRound(A, E, this.f2(B, C, D), K2, this.expand(eData, 30));
      B = this.ROTL(30, B);
      D = this.subRound(E, D, this.f2(A, B, C), K2, this.expand(eData, 31));
      A = this.ROTL(30, A);
      C = this.subRound(D, C, this.f2(E, A, B), K2, this.expand(eData, 32));
      E = this.ROTL(30, E);
      B = this.subRound(C, B, this.f2(D, E, A), K2, this.expand(eData, 33));
      D = this.ROTL(30, D);
      A = this.subRound(B, A, this.f2(C, D, E), K2, this.expand(eData, 34));
      C = this.ROTL(30, C);
      E = this.subRound(A, E, this.f2(B, C, D), K2, this.expand(eData, 35));
      B = this.ROTL(30, B);
      D = this.subRound(E, D, this.f2(A, B, C), K2, this.expand(eData, 36));
      A = this.ROTL(30, A);
      C = this.subRound(D, C, this.f2(E, A, B), K2, this.expand(eData, 37));
      E = this.ROTL(30, E);
      B = this.subRound(C, B, this.f2(D, E, A), K2, this.expand(eData, 38));
      D = this.ROTL(30, D);
      A = this.subRound(B, A, this.f2(C, D, E), K2, this.expand(eData, 39));
      C = this.ROTL(30, C);
      E = this.subRound(A, E, this.f3(B, C, D), K3, this.expand(eData, 40));
      B = this.ROTL(30, B);
      D = this.subRound(E, D, this.f3(A, B, C), K3, this.expand(eData, 41));
      A = this.ROTL(30, A);
      C = this.subRound(D, C, this.f3(E, A, B), K3, this.expand(eData, 42));
      E = this.ROTL(30, E);
      B = this.subRound(C, B, this.f3(D, E, A), K3, this.expand(eData, 43));
      D = this.ROTL(30, D);
      A = this.subRound(B, A, this.f3(C, D, E), K3, this.expand(eData, 44));
      C = this.ROTL(30, C);
      E = this.subRound(A, E, this.f3(B, C, D), K3, this.expand(eData, 45));
      B = this.ROTL(30, B);
      D = this.subRound(E, D, this.f3(A, B, C), K3, this.expand(eData, 46));
      A = this.ROTL(30, A);
      C = this.subRound(D, C, this.f3(E, A, B), K3, this.expand(eData, 47));
      E = this.ROTL(30, E);
      B = this.subRound(C, B, this.f3(D, E, A), K3, this.expand(eData, 48));
      D = this.ROTL(30, D);
      A = this.subRound(B, A, this.f3(C, D, E), K3, this.expand(eData, 49));
      C = this.ROTL(30, C);
      E = this.subRound(A, E, this.f3(B, C, D), K3, this.expand(eData, 50));
      B = this.ROTL(30, B);
      D = this.subRound(E, D, this.f3(A, B, C), K3, this.expand(eData, 51));
      A = this.ROTL(30, A);
      C = this.subRound(D, C, this.f3(E, A, B), K3, this.expand(eData, 52));
      E = this.ROTL(30, E);
      B = this.subRound(C, B, this.f3(D, E, A), K3, this.expand(eData, 53));
      D = this.ROTL(30, D);
      A = this.subRound(B, A, this.f3(C, D, E), K3, this.expand(eData, 54));
      C = this.ROTL(30, C);
      E = this.subRound(A, E, this.f3(B, C, D), K3, this.expand(eData, 55));
      B = this.ROTL(30, B);
      D = this.subRound(E, D, this.f3(A, B, C), K3, this.expand(eData, 56));
      A = this.ROTL(30, A);
      C = this.subRound(D, C, this.f3(E, A, B), K3, this.expand(eData, 57));
      E = this.ROTL(30, E);
      B = this.subRound(C, B, this.f3(D, E, A), K3, this.expand(eData, 58));
      D = this.ROTL(30, D);
      A = this.subRound(B, A, this.f3(C, D, E), K3, this.expand(eData, 59));
      C = this.ROTL(30, C);
      E = this.subRound(A, E, this.f4(B, C, D), K4, this.expand(eData, 60));
      B = this.ROTL(30, B);
      D = this.subRound(E, D, this.f4(A, B, C), K4, this.expand(eData, 61));
      A = this.ROTL(30, A);
      C = this.subRound(D, C, this.f4(E, A, B), K4, this.expand(eData, 62));
      E = this.ROTL(30, E);
      B = this.subRound(C, B, this.f4(D, E, A), K4, this.expand(eData, 63));
      D = this.ROTL(30, D);
      A = this.subRound(B, A, this.f4(C, D, E), K4, this.expand(eData, 64));
      C = this.ROTL(30, C);
      E = this.subRound(A, E, this.f4(B, C, D), K4, this.expand(eData, 65));
      B = this.ROTL(30, B);
      D = this.subRound(E, D, this.f4(A, B, C), K4, this.expand(eData, 66));
      A = this.ROTL(30, A);
      C = this.subRound(D, C, this.f4(E, A, B), K4, this.expand(eData, 67));
      E = this.ROTL(30, E);
      B = this.subRound(C, B, this.f4(D, E, A), K4, this.expand(eData, 68));
      D = this.ROTL(30, D);
      A = this.subRound(B, A, this.f4(C, D, E), K4, this.expand(eData, 69));
      C = this.ROTL(30, C);
      E = this.subRound(A, E, this.f4(B, C, D), K4, this.expand(eData, 70));
      B = this.ROTL(30, B);
      D = this.subRound(E, D, this.f4(A, B, C), K4, this.expand(eData, 71));
      A = this.ROTL(30, A);
      C = this.subRound(D, C, this.f4(E, A, B), K4, this.expand(eData, 72));
      E = this.ROTL(30, E);
      B = this.subRound(C, B, this.f4(D, E, A), K4, this.expand(eData, 73));
      D = this.ROTL(30, D);
      A = this.subRound(B, A, this.f4(C, D, E), K4, this.expand(eData, 74));
      C = this.ROTL(30, C);
      E = this.subRound(A, E, this.f4(B, C, D), K4, this.expand(eData, 75));
      B = this.ROTL(30, B);
      D = this.subRound(E, D, this.f4(A, B, C), K4, this.expand(eData, 76));
      A = this.ROTL(30, A);
      C = this.subRound(D, C, this.f4(E, A, B), K4, this.expand(eData, 77));
      E = this.ROTL(30, E);
      B = this.subRound(C, B, this.f4(D, E, A), K4, this.expand(eData, 78));
      D = this.ROTL(30, D);
      A = this.subRound(B, A, this.f4(C, D, E), K4, this.expand(eData, 79));
      C = this.ROTL(30, C);
      digest[0] += A;
      digest[1] += B;
      digest[2] += C;
      digest[3] += D;
      digest[4] += E;
   }

   private void do_block(byte[] input, int off) {
      BArrToArrBig(input, off, this.state.data, 0, DATASIZE);
      this.Transform(this.state.digest, this.state.data, 0);
   }

   private void computeCurrent() {
      SHAState state2 = (SHAState)this.state.clone();
      int count = this.state.countLo;
      count = count >>> 3 & 63;
      int dataOff = count + 1;
      this.state.bdata[count] = -128;
      count = DATASIZE - 1 - count;
      if (count < 8) {
         arrayset(this.state.bdata, dataOff, count, (byte)0);
         this.do_block(this.state.bdata, 0);
         arrayset(this.state.bdata, 0, DATASIZE - 8, (byte)0);
      } else {
         arrayset(this.state.bdata, dataOff, count - 8, (byte)0);
      }

      BArrToArrBig(this.state.bdata, 0, this.state.data, 0, DATASIZE);
      this.state.data[14] = this.state.countHi;
      this.state.data[15] = this.state.countLo;
      this.Transform(this.state.digest, this.state.data, 0);
      this.digestBits = new byte[DIGESTSIZE];
      ArrToBArrBig(this.state.digest, 0, this.digestBits, 0, DIGESTSIZE);
      this.state = state2;
      this.digestValid = true;
   }

   private static void BArrToArrBig(byte[] bArr, int inOff, int[] iArr, int outOff, int len) {
      int j = outOff;

      for(int i = inOff; i < inOff + len; i += 4) {
         iArr[j++] = (bArr[i + 0] & 255) << 24 | (bArr[i + 1] & 255) << 16 | (bArr[i + 2] & 255) << 8 | (bArr[i + 3] & 255) << 0;
      }

   }

   private static void ArrToBArrBig(int[] iArr, int inOff, byte[] bArr, int outOff, int len) {
      int j = outOff;
      len >>= 2;

      for(int i = 0; i < len; ++i) {
         int in = iArr[inOff + i];
         bArr[j++] = (byte)(in >> 24);
         bArr[j++] = (byte)(in >> 16);
         bArr[j++] = (byte)(in >> 8);
         bArr[j++] = (byte)(in >> 0);
      }

   }

   private static void arrayset(byte[] arr, int off, int len, byte val) {
      int imax = off + len;

      for(int i = off; i < imax; ++i) {
         arr[i] = val;
      }

   }
}
