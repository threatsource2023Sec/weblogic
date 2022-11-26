package monfox.log.xml;

import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

class b implements ErrorHandler {
   private StringBuffer a = new StringBuffer();
   private String b = a("~T}\u0004#|T");
   private String c = "";
   private int d = 0;
   private int e = 0;
   private static Logger f = Logger.getInstance(a("On[$"), a("SwZ"), a("SWz/\"\u007fSb\u0013\u001enIy\u0006:nH"));

   public b(String var1, String var2) {
      this.b = var2;
      this.c = var1;
   }

   public int getErrorCount() {
      return this.d;
   }

   public int getWarningCount() {
      return this.e;
   }

   public String getMessages() {
      return this.a.toString();
   }

   public void error(SAXParseException var1) {
      boolean var3 = XmlLoggerConfigurator.a;
      ++this.d;
      String var2 = a("\u0001b[&lNhD%\u001e\u0001\u001a6J*bVsJl+\u00006") + this.b + a("\u0001\u001a6J<~Xz\u0003/+\u00006") + var1.getPublicId() + a("\u0001\u001a6J?rIb\u000f!+\u00006") + var1.getSystemId() + a("\u0001\u001a6J bTsJl+\u00006") + var1.getLineNumber() + a("\u0001\u001a6J)yHy\u0018l+\u00006") + var1.getMessage() + "\n";
      this.a.append(var2);
      f.error("[" + this.c + "]" + var2, var1.getException());
      if (var3) {
         SnmpException.b = !SnmpException.b;
      }

   }

   public void fatalError(SAXParseException var1) {
      ++this.d;
      String var2 = a("\u0001|W>\rG\u001aN'\u0000+\u007fD8\u0003Y06JlmSz\u000fl+\u001a,J") + this.b + a("\u0001\u001a6J<~Xz\u0003/+\u00006") + var1.getPublicId() + a("\u0001\u001a6J?rIb\u000f!+\u00006") + var1.getSystemId() + a("\u0001\u001a6J bTsJl+\u00006") + var1.getLineNumber() + a("\u0001\u001a6J)yHy\u0018l+\u00006") + var1.getMessage() + "\n";
      this.a.append(var2);
      f.error("[" + this.c + "]" + var2, var1.getException());
   }

   public void warning(SAXParseException var1) {
      boolean var3 = XmlLoggerConfigurator.a;
      String var2 = a("SwZJ\u001bJhX#\u0002L\u0000\u001cJl+\\\u007f\u0006)+\u001a6Pl") + this.b + a("\u0001\u001a6J<~Xz\u0003/+\u00006") + var1.getPublicId() + a("\u0001\u001a6J?rIb\u000f!+\u00006") + var1.getSystemId() + a("\u0001\u001a6J bTsJl+\u00006") + var1.getLineNumber() + a("\u0001\u001a6J)yHy\u0018l+\u00006") + var1.getMessage() + "\n";
      this.a.append(var2);
      f.warn("[" + this.c + "]" + var2, var1.getException());
      ++this.e;
      if (SnmpException.b) {
         XmlLoggerConfigurator.a = !var3;
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
               var10003 = 58;
               break;
            case 2:
               var10003 = 22;
               break;
            case 3:
               var10003 = 106;
               break;
            default:
               var10003 = 76;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
