package monfox.toolkit.snmp.metadata.html;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class ValidateXMLInput {
   public static void main(String[] var0) throws Exception {
      ValidateXMLInput var1 = new ValidateXMLInput();
      var1.a();
   }

   void a() throws Exception {
      TransformerFactory var1 = TransformerFactory.newInstance();
      if (var1.getFeature(a("]\u0001)A\u0003\u001aZ7POT\rsITY[)CX[\u0006;^KX[.PA\u001b&\u001cijZ\u0000/R\\\u001a\u00138PM@\u00078"))) {
         SAXParserFactory var2 = SAXParserFactory.newInstance();
         var2.setNamespaceAware(true);
         var2.setValidating(true);
         XMLReader var3 = var2.newSAXParser().getXMLReader();
         Handler var4 = new Handler();
         var3.setErrorHandler(var4);
         Transformer var5 = var1.newTransformer(new StreamSource(a("W\u001c/UJ\u001b\r.]")));
         SAXSource var6 = new SAXSource(var3, new InputSource(a("W\u001c/UJ\u001b\r0]")));

         try {
            var5.transform(var6, new StreamResult(a("W\u001c/UJ\u001b\u001a(E")));
         } catch (TransformerException var8) {
            System.out.println(a("{\u001a)\u0011X\u0015&\u001ciiT\u0007.T|M\u00168AM\\\u001a3\u0011NT\u00073XWRU2C\u0019P\u0007/^K\u000fU") + var8.getMessage());
         }

         System.out.println(a("\bH`\f\u0004q\u001a3T\u0004\bH`\f"));
         if (!HTMLMIBOutputter.h) {
            return;
         }
      }

      System.out.println(a("A\u0013<RMZ\u0007$\u0011]Z\u0010.\u0011WZ\u0001}BLE\u00052CM\u0015&\u001ci\u0019S\u0010<ELG\u0010.\u0010"));
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 53;
               break;
            case 1:
               var10003 = 117;
               break;
            case 2:
               var10003 = 93;
               break;
            case 3:
               var10003 = 49;
               break;
            default:
               var10003 = 57;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   class Handler extends DefaultHandler {
      public void warning(SAXParseException var1) throws SAXException {
         System.out.println(a("?O\u0017a?\u001e}*t&\u000fk?E7\u0003`oF?\u001e`&_9V.") + var1.getMessage());
      }

      public void error(SAXParseException var1) throws SAXException {
         System.out.println(a("?O\u0017a?\u001e}*t&\u000fk?E7\u0003`oT,\u001ea=\u000b~") + var1.getMessage());
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 108;
                  break;
               case 1:
                  var10003 = 14;
                  break;
               case 2:
                  var10003 = 79;
                  break;
               case 3:
                  var10003 = 49;
                  break;
               default:
                  var10003 = 94;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }
}
