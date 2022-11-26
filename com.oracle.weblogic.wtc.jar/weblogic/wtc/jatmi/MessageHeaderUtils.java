package weblogic.wtc.jatmi;

class MessageHeaderUtils {
   static int getIntBigEndian(byte[] buf, int pos) {
      int i = buf[pos] << 24 & -16777216;
      int j = buf[pos + 1] << 16 & 16711680;
      int k = buf[pos + 2] << 8 & '\uff00';
      int l = buf[pos + 3] & 255;
      return i | j | k | l;
   }

   static int getIntLittleEndian(byte[] buf, int pos) {
      int i = buf[pos + 3] << 24 & -16777216;
      int j = buf[pos + 2] << 16 & 16711680;
      int k = buf[pos + 1] << 8 & '\uff00';
      int l = buf[pos] & 255;
      return i | j | k | l;
   }
}
