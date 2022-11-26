package monfox.toolkit.snmp.metadata.html;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.LineNumberReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import monfox.toolkit.snmp.SnmpException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class Mib2Html {
   public static void main(String[] var0) throws Exception {
      Mib2Html var1 = new Mib2Html();
      var1.process(var0[0], var0[1], var0[2]);
   }

   public void process(String var1, String var2, String var3) throws Exception {
      final LineNumberReader var4 = new LineNumberReader(new FileReader(var2));
      TransformerFactory var5 = TransformerFactory.newInstance();
      var5.setErrorListener(new ErrorListener() {
         public void error(TransformerException var1) {
            boolean var2 = HTMLMIBOutputter.h;
            System.out.println(a("F:a2}9H") + var1);
            System.out.println(a("O'p<{J'}G\u000f") + var1.getLocationAsString());
            System.out.println(a("N-`.nD-\t]") + var1.getMessageAndLocation());
            System.out.println(a("@)f.j9H") + var1.getCause());
            System.out.println(a("F0\t]") + var1.getException());
            System.out.println(a("O'p<{L:\t]") + var1.getLocator());
            System.out.println(a("O!}8\u0015#") + var4.getLineNumber());
            if (var2) {
               SnmpException.b = !SnmpException.b;
            }

         }

         public void fatalError(TransformerException var1) {
            boolean var2 = HTMLMIBOutputter.h;
            System.out.println(a("E)g<c#-a/`QR\u0013") + var1);
            System.out.println(a("O'p<{J'}G\u000f") + var1.getLocationAsString());
            System.out.println(a("N-`.nD-\t]") + var1.getMessageAndLocation());
            System.out.println(a("@)f.j9H") + var1.getCause());
            System.out.println(a("F0\t]") + var1.getException());
            System.out.println(a("O'p<{L:\t]") + var1.getLocator());
            System.out.println(a("O!}8\u0015#") + var4.getLineNumber());
            if (SnmpException.b) {
               HTMLMIBOutputter.h = !var2;
            }

         }

         public void warning(TransformerException var1) {
            System.out.println(a("T)a3fM/\t]") + var1);
         }

         private static String a(String var0) {
            char[] var1 = var0.toCharArray();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
               char var10002 = var1[var3];
               byte var10003;
               switch (var3 % 5) {
                  case 0:
                     var10003 = 3;
                     break;
                  case 1:
                     var10003 = 104;
                     break;
                  case 2:
                     var10003 = 51;
                     break;
                  case 3:
                     var10003 = 125;
                     break;
                  default:
                     var10003 = 47;
               }

               var1[var3] = (char)(var10002 ^ var10003);
            }

            return new String(var1);
         }
      });

      try {
         Templates var6 = var5.newTemplates(new StreamSource(var4));
         Transformer var7 = var6.newTransformer();
         StreamResult var8 = new StreamResult(new FileOutputStream(var3));
         DocumentBuilderFactory var9 = DocumentBuilderFactory.newInstance();
         var9.setNamespaceAware(true);
         DocumentBuilder var10 = var9.newDocumentBuilder();
         var10.setEntityResolver(new a());
         Document var11 = var10.parse(new InputSource(var1));
         var7.transform(new DOMSource(var11), var8);
      } catch (TransformerException var12) {
         System.out.println(a("T\u000e\u007f\\0Q\u000er'D") + var12.getLocationAsString());
         System.out.println(a("U\u0004oN%_\u0004\u0006=") + var12.getMessageAndLocation());
         System.out.println(a("[\u0000iN!\"a") + var12.getCause());
         System.out.println(a("]\u0019\u0006=") + var12.getException());
         System.out.println(a("T\u000e\u007f\\0W\u0013\u0006=") + var12.getLocator());
         throw var12;
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
               var10003 = 24;
               break;
            case 1:
               var10003 = 65;
               break;
            case 2:
               var10003 = 60;
               break;
            case 3:
               var10003 = 29;
               break;
            default:
               var10003 = 100;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
