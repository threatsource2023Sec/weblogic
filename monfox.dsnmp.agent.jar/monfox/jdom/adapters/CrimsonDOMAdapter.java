package monfox.jdom.adapters;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.w3c.dom.Document;
import org.xml.sax.SAXParseException;

public class CrimsonDOMAdapter extends AbstractDOMAdapter {
   public Document getDocument(InputStream var1, boolean var2) throws IOException {
      try {
         Class[] var3 = new Class[]{Class.forName(a("H(+ODK&sg\u0004R<)}\u001eP,<C")), Boolean.TYPE};
         Object[] var10 = new Object[]{var1, new Boolean(false)};
         Class var11 = Class.forName(a("M;:\u0000\u000bR(>F\u000f\f*/G\u0007Q&3\u0000\u001eP,8\u00002O%\u0019A\tW$8@\u001e"));
         Method var6 = var11.getMethod(a("A;8O\u001eG\u00110B.M*(C\u000fL="), var3);
         Document var7 = (Document)var6.invoke((Object)null, var10);
         return var7;
      } catch (InvocationTargetException var8) {
         Throwable var4 = var8.getTargetException();
         if (var4 instanceof SAXParseException) {
            SAXParseException var5 = (SAXParseException)var4;
            throw new IOException(a("g;/A\u0018\u0002&3\u000e\u0006K'8\u000e") + var5.getLineNumber() + a("\u0002&;\u000e2o\u0005}J\u0005A<0K\u0004Vs}") + var5.getMessage());
         } else {
            throw new IOException(var4.getMessage());
         }
      } catch (Exception var9) {
         throw new IOException(var9.getClass().getName() + a("\u0018i") + var9.getMessage());
      }
   }

   public Document createDocument() throws IOException {
      try {
         return (Document)Class.forName(a("M;:\u0000\u000bR(>F\u000f\f*/G\u0007Q&3\u0000\u001eP,8\u00002O%\u0019A\tW$8@\u001e")).newInstance();
      } catch (Exception var2) {
         throw new IOException(var2.getClass().getName() + a("\u0018i") + var2.getMessage());
      }
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 34;
               break;
            case 1:
               var10003 = 73;
               break;
            case 2:
               var10003 = 93;
               break;
            case 3:
               var10003 = 46;
               break;
            default:
               var10003 = 106;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
