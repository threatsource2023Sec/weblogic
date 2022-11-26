package org.python.core.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import org.python.core.BaseBytes;
import org.python.core.Py;
import org.python.core.PyBuffer;

public class StringUtil {
   public static byte[] toBytes(String string) {
      try {
         return string.getBytes("ISO-8859-1");
      } catch (UnsupportedEncodingException var2) {
         throw Py.SystemError("Java couldn't find the ISO-8859-1 encoding");
      }
   }

   public static String fromBytes(byte[] buf, int off, int len) {
      return new String(buf, 0, off, len);
   }

   public static String fromBytes(byte[] buf) {
      return fromBytes(buf, 0, buf.length);
   }

   public static String fromBytes(ByteBuffer buf) {
      return fromBytes(buf.array(), buf.arrayOffset() + buf.position(), buf.arrayOffset() + buf.limit());
   }

   public static String fromBytes(PyBuffer buf) {
      return buf.toString();
   }

   public static String fromBytes(BaseBytes b) {
      int size = b.__len__();
      StringBuilder buf = new StringBuilder(size);

      for(int j = 0; j < size; ++j) {
         buf.append((char)b.intAt(j));
      }

      return buf.toString();
   }

   public static String decapitalize(String string) {
      char c0 = string.charAt(0);
      if (!Character.isUpperCase(c0)) {
         return string;
      } else if (string.length() > 1 && Character.isUpperCase(string.charAt(1))) {
         return string;
      } else {
         char[] chars = string.toCharArray();
         chars[0] = Character.toLowerCase(c0);
         return new String(chars);
      }
   }
}
