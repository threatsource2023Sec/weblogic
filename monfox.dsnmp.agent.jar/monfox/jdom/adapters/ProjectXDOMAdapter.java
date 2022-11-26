package monfox.jdom.adapters;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.w3c.dom.Document;
import org.xml.sax.SAXParseException;

public class ProjectXDOMAdapter extends AbstractDOMAdapter {
   public Document getDocument(InputStream var1, boolean var2) throws IOException {
      try {
         Class[] var3 = new Class[]{Class.forName(a(",H\u000e]v/FVu66\\\fo,4L\u0019Q")), Boolean.TYPE};
         Object[] var10 = new Object[]{var1, new Boolean(false)};
         Class var11 = Class.forName(a("%F\u0015\u0012+3GVD5*\u0007\fN=#\u0007 Q4\u0002F\u001bI5#G\f"));
         Method var6 = var11.getMethod(a("%[\u001d],#q\u0015P\u001c)J\rQ=(]"), var3);
         Document var7 = (Document)var6.invoke((Object)null, var10);
         return var7;
      } catch (InvocationTargetException var8) {
         Throwable var4 = var8.getTargetException();
         if (var4 instanceof SAXParseException) {
            SAXParseException var5 = (SAXParseException)var4;
            throw new IOException(a("\u0003[\nS*fF\u0016\u001c4/G\u001d\u001c") + var5.getLineNumber() + a("fF\u001e\u001c\u0000\u000beXX7%\\\u0015Y62\u0013X") + var5.getMessage());
         } else {
            throw new IOException(var4.getMessage());
         }
      } catch (Exception var9) {
         throw new IOException(var9.getClass().getName() + a("|\t") + var9.getMessage());
      }
   }

   public Document createDocument() throws IOException {
      try {
         return (Document)Class.forName(a("%F\u0015\u0012+3GVD5*\u0007\fN=#\u0007 Q4\u0002F\u001bI5#G\f")).newInstance();
      } catch (Exception var2) {
         throw new IOException(var2.getClass().getName() + a("|\t") + var2.getMessage());
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
               var10003 = 70;
               break;
            case 1:
               var10003 = 41;
               break;
            case 2:
               var10003 = 120;
               break;
            case 3:
               var10003 = 60;
               break;
            default:
               var10003 = 88;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
