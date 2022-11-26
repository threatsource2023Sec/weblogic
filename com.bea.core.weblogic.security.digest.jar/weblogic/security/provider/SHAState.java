package weblogic.security.provider;

class SHAState {
   int[] digest;
   int[] data;
   byte[] bdata;
   int countLo;
   int countHi;

   public Object clone() {
      SHAState s = new SHAState();
      s.countLo = this.countLo;
      s.countHi = this.countHi;
      s.data = new int[this.data.length];
      s.bdata = new byte[this.bdata.length];
      s.digest = new int[this.digest.length];
      System.arraycopy(this.data, 0, s.data, 0, this.data.length);
      System.arraycopy(this.bdata, 0, s.bdata, 0, this.bdata.length);
      System.arraycopy(this.digest, 0, s.digest, 0, this.digest.length);
      return s;
   }
}
