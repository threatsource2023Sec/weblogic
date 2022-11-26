package weblogic.servlet.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import weblogic.utils.Hex;

final class ProxyUtils {
   static final int readChunkSize(InputStream in) throws IOException {
      StringBuilder sb = new StringBuilder();

      int b;
      int size;
      while((b = in.read()) != -1 && (b != 13 || (b = in.read()) != 10)) {
         size = (char)b;
         if (Hex.isHexChar(size)) {
            sb.append((char)size);
         }
      }

      size = 0;
      if (sb.length() > 0) {
         size = Integer.parseInt(sb.toString(), 16);
         if (size == 0) {
            in.read();
            in.read();
         }
      }

      return size;
   }

   static final String readHTTPHeader(PushbackInputStream pin) throws IOException {
      char[] buf = new char[128];
      char[] lineBuffer = new char[128];
      int room = buf.length;
      int offset = 0;
      int c = 0;
      boolean breakOut = false;

      while(!breakOut) {
         switch (c = pin.read()) {
            case -1:
            case 10:
               breakOut = true;
               break;
            case 13:
               int c2 = pin.read();
               if (c2 != 10 && c2 != -1) {
                  pin.unread(c2);
               }

               breakOut = true;
               break;
            default:
               --room;
               if (room < 0) {
                  buf = new char[offset + 128];
                  room = buf.length - offset - 1;
                  System.arraycopy(lineBuffer, 0, buf, 0, offset);
                  lineBuffer = buf;
               }

               buf[offset++] = (char)c;
         }
      }

      if (c == -1 && offset == 0) {
         return null;
      } else if (offset == 0) {
         return new String();
      } else {
         return new String(buf, 0, offset);
      }
   }
}
