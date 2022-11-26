package org.apache.jcp.xml.dsig.internal.dom;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.xml.crypto.XMLCryptoContext;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public final class Utils {
   private Utils() {
   }

   public static byte[] readBytesFromStream(InputStream is) throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      Throwable var2 = null;

      try {
         byte[] buf = new byte[1024];

         while(true) {
            int read = is.read(buf);
            if (read != -1) {
               baos.write(buf, 0, read);
               if (read >= 1024) {
                  continue;
               }
            }

            byte[] var15 = baos.toByteArray();
            return var15;
         }
      } catch (Throwable var13) {
         var2 = var13;
         throw var13;
      } finally {
         if (var2 != null) {
            try {
               baos.close();
            } catch (Throwable var12) {
               var2.addSuppressed(var12);
            }
         } else {
            baos.close();
         }

      }
   }

   static Set toNodeSet(Iterator i) {
      Set nodeSet = new HashSet();

      while(true) {
         Node n;
         do {
            if (!i.hasNext()) {
               return nodeSet;
            }

            n = (Node)i.next();
            nodeSet.add(n);
         } while(n.getNodeType() != 1);

         NamedNodeMap nnm = n.getAttributes();
         int j = 0;

         for(int length = nnm.getLength(); j < length; ++j) {
            nodeSet.add(nnm.item(j));
         }
      }
   }

   public static String parseIdFromSameDocumentURI(String uri) {
      if (uri.length() == 0) {
         return null;
      } else {
         String id = uri.substring(1);
         if (id != null && id.startsWith("xpointer(id(")) {
            int i1 = id.indexOf(39);
            int i2 = id.indexOf(39, i1 + 1);
            id = id.substring(i1 + 1, i2);
         }

         return id;
      }
   }

   public static boolean sameDocumentURI(String uri) {
      return uri != null && (uri.length() == 0 || uri.charAt(0) == '#');
   }

   static boolean secureValidation(XMLCryptoContext xc) {
      return xc == null ? false : getBoolean(xc, "org.apache.jcp.xml.dsig.secureValidation");
   }

   private static boolean getBoolean(XMLCryptoContext xc, String name) {
      Boolean value = (Boolean)xc.getProperty(name);
      return value != null && value;
   }
}
