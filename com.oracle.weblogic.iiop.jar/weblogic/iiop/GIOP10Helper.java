package weblogic.iiop;

import java.io.UnsupportedEncodingException;
import org.omg.CORBA.CODESET_INCOMPATIBLE;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

final class GIOP10Helper {
   static void write_wstring(String value, OutputStream out, int wchar_codeset, boolean littleEndian) {
      if (value == null) {
         throw new MARSHAL("null value passes to write_wstring(String)");
      } else {
         byte[] buf = null;

         try {
            switch (wchar_codeset) {
               case 65792:
                  if (littleEndian) {
                     buf = value.getBytes("utf-16le");
                  } else {
                     buf = value.getBytes("utf-16be");
                  }
                  break;
               case 65801:
                  buf = value.getBytes("utf-16");
                  break;
               case 83951617:
                  buf = value.getBytes("utf-8");
            }
         } catch (UnsupportedEncodingException var6) {
            throw new CODESET_INCOMPATIBLE(var6.getMessage());
         }

         int length = buf.length / 2 + 1;
         out.write_ulong(length);
         out.write_octet_array(buf, 0, buf.length);
         out.write_octet((byte)0);
         out.write_octet((byte)0);
      }
   }

   static final String read_wstring(InputStream in, int wchar_codeset, boolean littleEndian, int len) {
      len *= 2;
      len -= 2;
      byte[] buf = new byte[len];
      in.read_octet_array(buf, 0, buf.length);
      String ret = null;

      try {
         switch (wchar_codeset) {
            case 65792:
               if (littleEndian) {
                  ret = new String(buf, "utf-16le");
               } else {
                  ret = new String(buf, "utf-16be");
               }
               break;
            case 65801:
               if (len <= 1 || (buf[0] != -1 || buf[1] != -2) && (buf[0] != -2 || buf[1] != -1)) {
                  ret = new String(buf, "utf-16be");
               } else {
                  ret = new String(buf, "utf-16");
               }
               break;
            default:
               throw new CODESET_INCOMPATIBLE();
         }
      } catch (UnsupportedEncodingException var7) {
         throw new CODESET_INCOMPATIBLE(var7.getMessage());
      }

      in.read_octet();
      in.read_octet();
      return ret;
   }
}
