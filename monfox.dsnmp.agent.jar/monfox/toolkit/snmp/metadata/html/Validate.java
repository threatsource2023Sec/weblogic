package monfox.toolkit.snmp.metadata.html;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Date;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

public class Validate {
   static int a = 0;
   static int b = 0;
   static int c = 0;
   static int d = 0;
   static int e = 0;
   static boolean f = false;
   static StringBuffer g = new StringBuffer();

   public static void main(String[] var0) throws FileNotFoundException, IOException, ParserConfigurationException, SAXException {
      if (var0.length != 0 && var0.length <= 2) {
         if (var0[0].toLowerCase().equals(a("&H\u001e9G"))) {
            String var8 = a("\u0001\u001dFh\n6\u001dFh\n6\u001dFh\n6\u001dFh\n6\u001dFh\n6\u001dFh\n6\u001dFh\n6\u001dFh\n6\u001dFh\n6\u001dFh\n6\u001dF_");
            String var2 = a("]A\u0017<SjT\u001euBxE\buonR\u00180D+T\u0014uGjR\b0\u0017\u007fH\u001euOfL[3^gE\bu^e\u0000\u000f=R+D\u0012'RhT\u0014'N+Y\u0014 \u0017xP\u001e6^mY[:E+T\u00130\u0017bN\u001f<AbD\u000e4[+X\u00169\u0017mI\u00170\u0017rO\u000euD{E\u0018<Qr\u000e[\u0001_n\u0000\u000b4ExE\tuAjL\u00121V\u007fE\buRjC\u0013uSdC\u000e8ReT[}TcE\u0018>D+T\u00134C+I\u000fuTdN\u001d:EfS[!X+I\u000f&\u0017Oo8\u0001n[eR{=");
            String var3 = a("NA\u0018=\u0017sM\u0017uQbL\u001euDcO\u000e9S+C\u0014;CjI\u0015uV+d4\u0016cRp>uSnC\u00174EjT\u0012:Y%*q");
            String var4 = a("]A\u0017<SjT\u001euCjK\u001e&\u0017:\u0000\u0014'\u00179\u0000\u001a'P~M\u001e;Cx\u001aq");
            String var5 = a("+a\t2BfE\u0015!\u0017:\u0000\b%RhI\u001d<Rx\u0000\u001auSbR\u001e6CdR\u0002uXy\u0000\u001a;\u0017bN\u001f<AbD\u000e4[+X\u00169\u0017mI\u00170\u0019\u0001");
            String var6 = a("+a\t2BfE\u0015!\u00179\u0000\b%RhI\u001d<Rx\u0000\u001au[dG[3^gEUu~m\u0000\u0002:B+I\u00156[~D\u001euCcI\buVyG\u000e8ReTWuajL\u00121V\u007fE[4G{E\u00151D+M\u001e&DjG\u001e&\u0017\u007fO[!_bS[3^gEUu~m\u0000\u0002:B+D\u0014uYdTWuajL\u00121V\u007fE[\"EbT\u001e&\u0017fE\b&VlE\buCd\u0000\u000f=R+S\u0018'RnNU_");
            System.out.println(var8 + var2 + var3 + var4 + var5 + var6 + var8);
         } else {
            try {
               Validate var1 = new Validate();
               var1.a(var0);
            } catch (Exception var7) {
               var7.printStackTrace();
            }

         }
      } else {
         System.out.println(a("\u0001e\u0015!Ry\u0000\\?V}A[#VgI\u001f4Cn\u0000V=RgP\\uQdR[<YmO\t8V\u007fI\u0014;\u0017jB\u0014 C+R\u000e;YbN\u001cuajL\u00121V\u007fE"));
      }
   }

   void a(String[] var1) throws FileNotFoundException, IOException, ParserConfigurationException, SAXException {
      int var10000;
      boolean var6;
      label95: {
         label94: {
            label99: {
               var6 = HTMLMIBOutputter.h;
               File var2 = new File(var1[0]);
               f = var1.length == 2;
               if (var2.isFile()) {
                  this.a((String)null, var1[0]);
                  if (!var6) {
                     break label99;
                  }
               }

               if (!var2.isDirectory()) {
                  break label94;
               }

               XMLFileFilter var3 = new XMLFileFilter();
               String[] var4 = var2.list(var3);
               int var5 = 0;

               while(var5 < var4.length) {
                  this.a(var2.toString(), var4[var5]);
                  var10000 = f;
                  if (var6) {
                     break label95;
                  }

                  if (var10000 == 0) {
                     System.out.print(g.toString());
                     g = new StringBuffer();
                  }

                  ++var5;
                  if (var6) {
                     break;
                  }
               }

               if (var6) {
                  break label94;
               }
            }

            g.append(a("6\u001dFh\n6\u001dFh\n6\u001dFh\n6s.\u0018zJr\"h\n6\u001dFh\n6\u001dFh\n6\u001dFh\n6\u001dFh\n6\u001dFh\n6\u001dq"));
            var10000 = a;
            break label95;
         }

         System.out.println(var1[0] + a("+N\u0014!\u0017mO\u000e;S*"));
         return;
      }

      if (var10000 > 1) {
         g.append(a("[A\t&Ro\u0000") + a + a("+\u000e\u00038[+F\u00129Rx\u0000\u0012;\u0017") + var1[0] + a("%*"));
      }

      label71: {
         if (b > 1) {
            g.append(b + a("+F\u00129Rx\u0000\u001a'R+V\u001a9^o\u000eq"));
            if (!var6) {
               break label71;
            }
         }

         if (b == 1) {
            g.append(b + a("+F\u00129R+I\buAjL\u00121\u0019\u0001"));
         }
      }

      label66: {
         if (c > 1) {
            g.append(c + a("+F\u00129Rx\u0000\u001a'R+N\u0014!\u0017}A\u0017<S%*"));
            if (!var6) {
               break label66;
            }
         }

         if (c == 1) {
            g.append(c + a("+F\u00129R+I\buYdT[#VgI\u001f{="));
         }
      }

      label61: {
         if (e > 1) {
            g.append(e + a("+F\u00129Rx\u0000\u001a'R+N\u0014!\u0017|E\u00179\u001amO\t8Ro\u000eq"));
            if (!var6) {
               break label61;
            }
         }

         if (e == 1) {
            g.append(e + a("+F\u00129R+I\buYdT[\"RgLV3XyM\u001e1\u0019\u0001"));
         }
      }

      label56: {
         if (d > 1) {
            g.append(d + a("+F\u00129Rx\u0000\u001f:\u0017eO\u000fuTdN\u000f4^e\u0000\u001ausDc/\fgN\u0000\u001f0TgA\t4CbO\u0015{="));
            if (!var6) {
               break label56;
            }
         }

         if (d == 1) {
            g.append(d + a("+F\u00129R+D\u00140D+N\u0014!\u0017hO\u0015!VbN[4\u0017Oo8\u0001n[e[1RhL\u001a'V\u007fI\u0014;\u0019\u0001"));
         }
      }

      if (!f) {
         System.out.print(g.toString());
         if (!var6) {
            return;
         }
      }

      FileWriter var7 = new FileWriter(var1[1], true);
      var7.write((new Date()).toString() + "\n");
      var7.write(g.toString());
      var7.close();
      System.out.println(a("OO\u00150\u0017|I\u000f=\u0017}A\u0017<SjT\u0012:Y%\u0000(0R+") + var1[1] + ".");
   }

   void a(String var1, String var2) throws FileNotFoundException, IOException, ParserConfigurationException, SAXException {
      boolean var11 = HTMLMIBOutputter.h;

      try {
         File var18 = new File(var1, var2);
         StringBuffer var19 = new StringBuffer();
         InputSource var5 = new InputSource(new FileInputStream(var18));
         var5.setSystemId(var18.toString());
         SAXParserFactory var6 = SAXParserFactory.newInstance();
         var6.setValidating(true);
         var6.setNamespaceAware(true);
         SAXParser var7 = var6.newSAXParser();
         XMLReader var8 = var7.getXMLReader();
         Handler var9 = new Handler(var2, var19);
         var8.setProperty(a("cT\u000f%\r$\u000f\u00038[%O\t2\u0018xA\u0003zGyO\u000b0E\u007fI\u001e&\u0018gE\u0003<TjLV=VeD\u00170E"), var9);
         var7.parse(var5, var9);
         if (var9.b && !var9.a) {
            g.append(a("]a7\u001cs+") + var2 + "\n");
            ++b;
            if (!var11) {
               return;
            }
         }

         if (var9.b) {
            g.append(a("Eo/uaJl2\u0011\u0017") + var2 + "\n");
            g.append(var19.toString());
            ++c;
            if (!var11) {
               return;
            }
         }

         g.append(a("Eo[\u0011xHt\"\u0005r+d>\u0016{Jr:\u0001~Dn[") + var2 + "\n");
         ++d;
      } catch (Exception var16) {
         Exception var3 = var16;

         try {
            SAXParseException var4 = (SAXParseException)var3;
            System.out.println("[" + var4.getLineNumber() + "]");
         } catch (Exception var15) {
         }

         g.append(a("Eo/u`Nl7xqDr6\u0010s+") + var2 + a("%\u0000") + var16.getMessage() + "\n");
         ++e;
      } finally {
         ++a;
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
               var10003 = 11;
               break;
            case 1:
               var10003 = 32;
               break;
            case 2:
               var10003 = 123;
               break;
            case 3:
               var10003 = 85;
               break;
            default:
               var10003 = 55;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   class Handler extends DefaultHandler implements LexicalHandler {
      boolean a;
      boolean b;
      String c;
      StringBuffer d;

      Handler(String var2, StringBuffer var3) {
         this.c = var2;
         this.d = var3;
         this.a = false;
         this.b = false;
      }

      public void error(SAXParseException var1) {
         this.d.append(this.c + a("\u0013+\u0018^\u0006ATJ") + var1.getMessage() + "\n");
         this.a = true;
      }

      public void warning(SAXParseException var1) {
         this.d.append(this.c + a("\u00139\u000b^\u0007Z\u0000\r\u0016") + var1.getMessage() + "\n");
         this.a = true;
      }

      public void startDTD(String var1, String var2, String var3) throws SAXException {
         this.b = true;
      }

      public void endDTD() throws SAXException {
      }

      public void startEntity(String var1) throws SAXException {
      }

      public void endEntity(String var1) throws SAXException {
      }

      public void startCDATA() throws SAXException {
      }

      public void endCDATA() throws SAXException {
      }

      public void comment(char[] var1, int var2, int var3) throws SAXException {
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 51;
                  break;
               case 1:
                  var10003 = 110;
                  break;
               case 2:
                  var10003 = 106;
                  break;
               case 3:
                  var10003 = 44;
                  break;
               default:
                  var10003 = 105;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   class XMLFileFilter implements FilenameFilter {
      public boolean accept(File var1, String var2) {
         return var2.toLowerCase().endsWith(a("#>'x")) && (new File(var1.toString(), var2)).isFile();
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 13;
                  break;
               case 1:
                  var10003 = 70;
                  break;
               case 2:
                  var10003 = 74;
                  break;
               case 3:
                  var10003 = 20;
                  break;
               default:
                  var10003 = 110;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }
}
