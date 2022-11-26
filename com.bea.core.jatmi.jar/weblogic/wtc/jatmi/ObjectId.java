package weblogic.wtc.jatmi;

public final class ObjectId {
   private byte[] oidData;
   private int oidLength;

   public ObjectId(byte[] buf, int len) {
      this.oidData = new byte[len];

      for(int i = 0; i < len; ++i) {
         this.oidData[i] = buf[i];
      }

      this.oidLength = len;
   }

   public byte[] getData() {
      return this.oidData;
   }

   public int getLength() {
      return this.oidLength;
   }
}
