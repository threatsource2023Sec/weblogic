package weblogic.security;

public class MessageDigestUtils {
   public static void update(MessageDigest md, short aValue) {
      md.update(shortToBytes(aValue));
   }

   public static void update(MessageDigest md, int aValue) {
      md.update(wordToBytes(aValue));
   }

   public static void update(MessageDigest md, long aValue) {
      md.update(longToBytes(aValue));
   }

   public static void update(MessageDigest md, String input) {
      char[] c = new char[input.length()];
      byte[] b = new byte[c.length << 1];
      input.getChars(0, c.length, c, 0);
      charsToBytes(c, b);
      md.update(b);
   }

   public static void updateASCII(MessageDigest md, String input) {
      byte[] b = new byte[input.length()];
      input.getBytes(0, b.length, b, 0);
      md.update(b);
   }

   private static byte[] longToBytes(long l) {
      byte[] b = new byte[]{(byte)((int)(l >> 56)), (byte)((int)(l >> 48)), (byte)((int)(l >> 40)), (byte)((int)(l >> 32)), (byte)((int)(l >> 24)), (byte)((int)(l >> 16)), (byte)((int)(l >> 8)), (byte)((int)l)};
      return b;
   }

   private static byte[] wordToBytes(int w) {
      byte[] b = new byte[]{(byte)(w >> 24), (byte)(w >> 16), (byte)(w >> 8), (byte)w};
      return b;
   }

   private static byte[] shortToBytes(short s) {
      byte[] b = new byte[]{(byte)(s >> 8), (byte)s};
      return b;
   }

   private static void charsToBytes(char[] c, byte[] b) {
      int i = 0;

      for(int j = 0; i < c.length; ++i) {
         b[j++] = (byte)(c[i] >> 8);
         b[j++] = (byte)c[i];
      }

   }
}
