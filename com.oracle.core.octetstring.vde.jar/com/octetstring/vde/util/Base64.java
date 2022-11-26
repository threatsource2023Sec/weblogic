package com.octetstring.vde.util;

public class Base64 {
   private static final int fillchar = 61;
   private static final String cvt = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

   public static String encode(byte[] data) {
      int len = data.length;
      StringBuffer ret = new StringBuffer((len / 3 + 1) * 4);

      for(int i = 0; i < len; ++i) {
         int c = data[i] >> 2 & 63;
         ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
         c = data[i] << 4 & 63;
         ++i;
         if (i < len) {
            c |= data[i] >> 4 & 15;
         }

         ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
         if (i < len) {
            c = data[i] << 2 & 63;
            ++i;
            if (i < len) {
               c |= data[i] >> 6 & 3;
            }

            ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
         } else {
            ++i;
            ret.append('=');
         }

         if (i < len) {
            c = data[i] & 63;
            ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
         } else {
            ret.append('=');
         }
      }

      return ret.toString();
   }

   public static byte[] decode(String strdata) {
      byte[] data = strdata.getBytes();
      int len = data.length;
      StringBuffer ret = new StringBuffer(len * 3 / 4);

      for(int i = 0; i < len; ++i) {
         int c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(data[i]);
         ++i;
         int c1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(data[i]);
         c = c << 2 | c1 >> 4 & 3;
         ret.append((char)c);
         ++i;
         if (i < len) {
            int c = data[i];
            if (61 == c) {
               break;
            }

            c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf((char)c);
            c1 = c1 << 4 & 240 | c >> 2 & 15;
            ret.append((char)c1);
         }

         ++i;
         if (i < len) {
            int c1 = data[i];
            if (61 == c1) {
               break;
            }

            c1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf((char)c1);
            c = c << 6 & 192 | c1;
            ret.append((char)c);
         }
      }

      return getBinaryBytes(ret.toString());
   }

   private static String getString(byte[] arr) {
      StringBuffer buf = new StringBuffer();

      for(int i = 0; i < arr.length; ++i) {
         buf.append((char)arr[i]);
      }

      return buf.toString();
   }

   private static byte[] getBinaryBytes(String str) {
      byte[] b = new byte[str.length()];

      for(int i = 0; i < b.length; ++i) {
         b[i] = (byte)str.charAt(i);
      }

      return b;
   }
}
