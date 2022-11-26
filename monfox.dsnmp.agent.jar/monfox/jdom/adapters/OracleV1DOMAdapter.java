package monfox.jdom.adapters;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

public class OracleV1DOMAdapter extends AbstractDOMAdapter {
   // $FF: synthetic field
   static Class a;

   public Document getDocument(InputStream var1, boolean var2) throws IOException {
      try {
         Class var3 = Class.forName(b("\u001d1\u0011H\u0002\u0017m\bF\u0002\\3\u0011Y\u001d\u00171^s#>\u0013\u0011Y\u001d\u00171"));
         Object var10 = var3.newInstance();
         Method var11 = var3.getMethod(b("\u0002\"\u0002X\u000b"), a == null ? (a = a(b("\u001d1\u0017\u0005\u0016\u001f/^X\u000f\nm9E\u001e\u00077#D\u001b\u0000 \u0015"))) : a);
         var11.invoke(var10, new InputSource(var1));
         Method var6 = var3.getMethod(b("\u0015&\u0004o\u0001\u00116\u001dN\u0000\u0006"), (Class[])null);
         Document var7 = (Document)var6.invoke(var10, (Object[])null);
         return var7;
      } catch (InvocationTargetException var8) {
         Throwable var4 = var8.getTargetException();
         if (var4 instanceof SAXParseException) {
            SAXParseException var5 = (SAXParseException)var4;
            throw new IOException(b("71\u0002D\u001cR,\u001e\u000b\u0002\u001b-\u0015\u000b") + var5.getLineNumber() + b("R,\u0016\u000b6?\u000fPO\u0001\u00116\u001dN\u0000\u0006yP") + var5.getMessage());
         } else {
            throw new IOException(var4.getMessage());
         }
      } catch (Exception var9) {
         throw new IOException(var9.getClass().getName() + b("Hc") + var9.getMessage());
      }
   }

   public Document createDocument() throws IOException {
      try {
         return (Document)Class.forName(b("\u001d1\u0011H\u0002\u0017m\bF\u0002\\3\u0011Y\u001d\u00171^s#>\u0007\u001fH\u001b\u001f&\u001e_")).newInstance();
      } catch (Exception var2) {
         throw new IOException(var2.getClass().getName() + b("Hc") + var2.getMessage());
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
               var10003 = 114;
               break;
            case 1:
               var10003 = 67;
               break;
            case 2:
               var10003 = 112;
               break;
            case 3:
               var10003 = 43;
               break;
            default:
               var10003 = 110;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
