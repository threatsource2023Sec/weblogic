package monfox.jdom.adapters;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import monfox.jdom.input.BuilderErrorHandler;
import monfox.toolkit.snmp.SnmpException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

public class XercesDOMAdapter extends AbstractDOMAdapter {
   // $FF: synthetic field
   static Class a;
   // $FF: synthetic field
   static Class b;
   // $FF: synthetic field
   static Class c;

   public Document getDocument(InputStream var1, boolean var2) throws IOException {
      boolean var9 = AbstractDOMAdapter.a;

      Document var10000;
      try {
         Class var3 = Class.forName(b("iB&PNvQ\"\u0016J(H$\fLcCo\u000eNtC$\f\\(t\u000e3\u007fgB2\u001b]"));
         Object var12 = var3.newInstance();
         Method var13 = var3.getMethod(b("uU58JgD4\fJ"), a == null ? (a = a(b("lQ7\u001f\u0001jQ/\u0019\u0001UD3\u0017Aa"))) : a, Boolean.TYPE);
         var13.invoke(var12, b("nD5\u000e\u0015)\u001f9\u0013C(_3\u0019\u0000uQ9QIcQ5\u000b]cCn\bNjY%\u001f[o_/"), new Boolean(var2));
         var13.invoke(var12, b("nD5\u000e\u0015)\u001f9\u0013C(_3\u0019\u0000uQ9QIcQ5\u000b]cCn\u0010NkU2\u000eNeU2"), new Boolean(true));
         Method var6;
         if (var2) {
            var6 = var3.getMethod(b("uU5;]t_36NhT-\u001b]"), b == null ? (b = a(b("iB&PWk\\o\rN~\u001e\u0004\f]iB\t\u001fAb\\$\f"))) : b);
            var6.invoke(var12, new BuilderErrorHandler());
         }

         var6 = var3.getMethod(b("vQ3\rJ"), c == null ? (c = a(b("iB&PWk\\o\rN~\u001e\b\u0010_sD\u0012\u0011ZtS$"))) : c);
         var6.invoke(var12, new InputSource(var1));
         Method var7 = var3.getMethod(b("aU5:@eE,\u001bAr"), (Class[])null);
         Document var8 = (Document)var7.invoke(var12, (Object[])null);
         var10000 = var8;
      } catch (InvocationTargetException var10) {
         Throwable var4 = var10.getTargetException();
         if (var4 instanceof SAXParseException) {
            SAXParseException var5 = (SAXParseException)var4;
            throw new IOException(b("CB3\u0011]&_/^Co^$^") + var5.getLineNumber() + b("&_'^wK|a\u001a@eE,\u001bAr\na") + var5.getMessage());
         }

         throw new IOException(var4.getMessage());
      } catch (Exception var11) {
         throw new IOException(var11.getClass().getName() + b("<\u0010") + var11.getMessage());
      }

      if (SnmpException.b) {
         AbstractDOMAdapter.a = !var9;
      }

      return var10000;
   }

   public Document createDocument() throws IOException {
      try {
         return (Document)Class.forName(b("iB&PNvQ\"\u0016J(H$\fLcCo\u001a@k\u001e\u0005\u0011Ls]$\u0010[O]1\u0012")).newInstance();
      } catch (Exception var2) {
         throw new IOException(var2.getClass().getName() + b("<\u0010") + var2.getMessage());
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
               var10003 = 6;
               break;
            case 1:
               var10003 = 48;
               break;
            case 2:
               var10003 = 65;
               break;
            case 3:
               var10003 = 126;
               break;
            default:
               var10003 = 47;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
