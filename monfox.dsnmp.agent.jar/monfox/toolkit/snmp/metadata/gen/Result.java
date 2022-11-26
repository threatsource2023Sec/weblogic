package monfox.toolkit.snmp.metadata.gen;

import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import monfox.toolkit.snmp.util.FormatUtil;

public class Result {
   Vector a = new Vector();
   Vector b = new Vector();
   Vector c = new Vector();
   Vector d = new Vector();
   Vector e = new Vector();

   public int getCriticalErrorCount() {
      return this.a.size();
   }

   public int getMajorErrorCount() {
      return this.b.size();
   }

   public int getMinorErrorCount() {
      return this.c.size();
   }

   public int getErrorCount() {
      return this.e.size();
   }

   public int getWarningCount() {
      return this.d.size();
   }

   public List getCriticalErrors() {
      return this.a;
   }

   public List getMajorErrors() {
      return this.b;
   }

   public List getMinorErrors() {
      return this.c;
   }

   public List getErrors() {
      return this.e;
   }

   public List getWarnings() {
      return this.d;
   }

   void a(ErrorMessage var1) {
      label18: {
         int var2 = Message.d;
         switch (var1.getSeverity()) {
            case 1:
               this.a.addElement(var1);
               if (var2 == 0) {
                  break;
               }
            case 2:
               this.b.addElement(var1);
               if (var2 == 0) {
                  break;
               }
            case 3:
               this.c.addElement(var1);
               if (var2 != 0) {
                  break label18;
               }
               break;
            default:
               break label18;
         }

         this.e.addElement(var1);
         return;
      }

      this.d.addElement(var1);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      Object[][] var2 = new Object[][]{{a("WL\u0007\u0015p\u000fV\u0016\u001a+"), a("\u001eM\u000b\u0017|\u001e^\u000e"), a("\u0010^\b\fg"), a("\u0010V\f\fg"), a("\n^\u0010\r|\u0013X")}, {a("W\\\r\u0016{\tLBC+"), new Integer(this.getCriticalErrorCount()), new Integer(this.getMajorErrorCount()), new Integer(this.getMinorErrorCount()), new Integer(this.getWarningCount())}};
      String var3 = FormatUtil.formatTable(var2);
      var1.append(var3);
      this.a(this.a, var1);
      this.a(this.b, var1);
      this.a(this.c, var1);
      this.a(this.d, var1);
      return var1.toString();
   }

   private void a(Vector var1, StringBuffer var2) {
      Enumeration var3 = var1.elements();

      while(var3.hasMoreElements()) {
         var2.append("\n");
         var2.append(var3.nextElement());
         if (Message.d != 0) {
            break;
         }
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
               var10003 = 125;
               break;
            case 1:
               var10003 = 63;
               break;
            case 2:
               var10003 = 98;
               break;
            case 3:
               var10003 = 99;
               break;
            default:
               var10003 = 21;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
