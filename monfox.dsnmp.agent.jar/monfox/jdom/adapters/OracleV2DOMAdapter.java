package monfox.jdom.adapters;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

public class OracleV2DOMAdapter extends AbstractDOMAdapter {
   // $FF: synthetic field
   static Class a;

   public Document getDocument(InputStream var1, boolean var2) throws IOException {
      try {
         Class var3 = Class.forName(b("\u001aU81U\u0010\t!?U[W8 J\u0010Uw$\u000b[c\u0016\u001fi\u0014U*7K"));
         Object var10 = var3.newInstance();
         Method var11 = var3.getMethod(b("\u0005F+!\\"), a == null ? (a = a(b("\u001aU>|A\u0018Kw!X\r\t\u0010<I\u0000S\n=L\u0007D<"))) : a);
         var11.invoke(var10, new InputSource(var1));
         Method var6 = var3.getMethod(b("\u0012B-\u0016V\u0016R47W\u0001"), (Class[])null);
         Document var7 = (Document)var6.invoke(var10, (Object[])null);
         return var7;
      } catch (InvocationTargetException var8) {
         Throwable var4 = var8.getTargetException();
         if (var4 instanceof SAXParseException) {
            SAXParseException var5 = (SAXParseException)var4;
            throw new IOException(b("0U+=KUH7rU\u001cI<r") + var5.getLineNumber() + b("UH?ra8ky6V\u0016R47W\u0001\u001dy") + var5.getMessage());
         } else {
            throw new IOException(var4.getMessage());
         }
      } catch (Exception var9) {
         throw new IOException(var9.getClass().getName() + b("O\u0007") + var9.getMessage());
      }
   }

   public Document createDocument() throws IOException {
      try {
         return (Document)Class.forName(b("\u001aU81U\u0010\t!?U[W8 J\u0010Uw$\u000b[\u007f\u0014\u001e}\u001aD,?\\\u001bS")).newInstance();
      } catch (Exception var2) {
         throw new IOException(var2.getClass().getName() + b("O\u0007") + var2.getMessage());
      }
   }

   // $FF: synthetic method
   static Class a(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw (new NoClassDefFoundError()).initCause(var2);
      }
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 117;
               break;
            case 1:
               var10003 = 39;
               break;
            case 2:
               var10003 = 89;
               break;
            case 3:
               var10003 = 82;
               break;
            default:
               var10003 = 57;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
