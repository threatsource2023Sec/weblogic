package monfox.toolkit.snmp.util;

import monfox.log.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

public class XmlErrorHandler implements ErrorHandler {
   private StringBuffer a = new StringBuffer();
   private String b = a("\u001cE\u0017Q<\u001eE");
   private String c = "";
   private int d = 0;
   private int e = 0;
   private Logger f = Logger.getInstance(a("-x2r\u0003"), a("<\u007f5s"), a("1F\u0010z!\u001bD\u000ew2\u0007O\u0010Z!"));

   public XmlErrorHandler(String var1, String var2) {
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
      ++this.d;
      String var2 = a("c\u0006Q\u001f\b\u0011F\u0010\u001f6\u001bY\u0013M\u000eI\u0006Q5sI\u000b\u001aV?\f\u000b\\\u001fiI") + this.b + a("c\u000b\\\u001f#\u001cI\u0010V0I\u0011\\") + var1.getPublicId() + a("c\u000b\\\u001f \u0010X\bZ>I\u0011\\") + var1.getSystemId() + a("c\u000b\\\u001f?\u0000E\u0019\u001fsI\u0011\\") + var1.getLineNumber() + a("c\u000b\\\u001f6\u001bY\u0013MsI\u0011\\") + var1.getMessage() + "\n";
      this.a.append(var2);
      this.f.error("[" + this.c + "]" + var2, var1.getException());
   }

   public void fatalError(SAXParseException var1) {
      ++this.d;
      String var2 = a("c\u0006Q\u001f\b\u000fJ\b^?IS\u0011Ss\fY\u000eP!4\u000bQ\u0012YI\u000b\\Y:\u0005N\\\u001fsS\u000b") + this.b + a("c\u000b\\\u001f#\u001cI\u0010V0I\u0011\\") + var1.getPublicId() + a("c\u000b\\\u001f \u0010X\bZ>I\u0011\\") + var1.getSystemId() + a("c\u000b\\\u001f?\u0000E\u0019\u001fsI\u0011\\") + var1.getLineNumber() + a("c\u000b\\\u001f6\u001bY\u0013MsI\u0011\\") + var1.getMessage() + "\n";
      this.a.append(var2);
      this.f.error("[" + this.c + "]" + var2, var1.getException());
   }

   public void warning(SAXParseException var1) {
      String var2 = a("c\u0006Q\u001f\b\u0011F\u0010\u001f$\bY\u0012V=\u000ev\\\u0012~c\u000b\\\u001f5\u0000G\u0019\u001fsI\u0011\\") + this.b + a("c\u000b\\\u001f#\u001cI\u0010V0I\u0011\\") + var1.getPublicId() + a("c\u000b\\\u001f \u0010X\bZ>I\u0011\\") + var1.getSystemId() + a("c\u000b\\\u001f?\u0000E\u0019\u001fsI\u0011\\") + var1.getLineNumber() + a("c\u000b\\\u001f6\u001bY\u0013MsI\u0011\\") + var1.getMessage() + "\n";
      this.a.append(var2);
      this.f.warn("[" + this.c + "]" + var2, var1.getException());
      ++this.e;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 105;
               break;
            case 1:
               var10003 = 43;
               break;
            case 2:
               var10003 = 124;
               break;
            case 3:
               var10003 = 63;
               break;
            default:
               var10003 = 83;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
