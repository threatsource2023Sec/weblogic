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

public class XML4JDOMAdapter extends AbstractDOMAdapter {
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
         Class var3 = Class.forName(b("-A7 _2R3f[lK5|]'@~~_0@5|Mlw\u001fCn#A#kL"));
         Object var12 = var3.newInstance();
         Method var13 = var3.getMethod(b("1V$H[#G%|["), a == null ? (a = a(b("(R&o\u0010.R>i\u0010\u0011G\"gP%"))) : a, Boolean.TYPE);
         var13.invoke(var12, b("*G$~\u0004m\u001c(cRl\\\"i\u00111R(!X'R${L'@\u007fx_.Z4oJ+\\>"), new Boolean(var2));
         var13.invoke(var12, b("*G$~\u0004m\u001c(cRl\\\"i\u00111R(!X'R${L'@\u007f`_/V#~_!V#"), new Boolean(false));
         Method var6;
         if (var2) {
            var6 = var3.getMethod(b("1V$KL0\\\"F_,W<kL"), b == null ? (b = a(b("-A7 F/_~}_:\u001d\u0015|L-A\u0018oP&_5|"))) : b);
            var6.invoke(var12, new BuilderErrorHandler());
         }

         var6 = var3.getMethod(b("2R\"}["), c == null ? (c = a(b("-A7 F/_~}_:\u001d\u0019`N7G\u0003aK0P5"))) : c);
         var6.invoke(var12, new InputSource(var1));
         Method var7 = var3.getMethod(b("%V$JQ!F=kP6"), (Class[])null);
         Document var8 = (Document)var7.invoke(var12, (Object[])null);
         var10000 = var8;
      } catch (InvocationTargetException var10) {
         Throwable var4 = var10.getTargetException();
         if (var4 instanceof SAXParseException) {
            SAXParseException var5 = (SAXParseException)var4;
            throw new IOException(b("\u0007A\"aLb\\>.R+]5.") + var5.getLineNumber() + b("b\\6.f\u000f\u007fpjQ!F=kP6\tp") + var5.getMessage());
         }

         throw new IOException(var4.getMessage());
      } catch (Exception var11) {
         throw new IOException(var11.getClass().getName() + b("x\u0013") + var11.getMessage());
      }

      if (var9) {
         SnmpException.b = !SnmpException.b;
      }

      return var10000;
   }

   public Document createDocument() throws IOException {
      try {
         return (Document)Class.forName(b("-A7 _2R3f[lK5|]'@~jQ/\u001d\u0014a]7^5`J\u000b^ b")).newInstance();
      } catch (Exception var2) {
         throw new IOException(var2.getClass().getName() + b("x\u0013") + var2.getMessage());
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
               var10003 = 66;
               break;
            case 1:
               var10003 = 51;
               break;
            case 2:
               var10003 = 80;
               break;
            case 3:
               var10003 = 14;
               break;
            default:
               var10003 = 62;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
