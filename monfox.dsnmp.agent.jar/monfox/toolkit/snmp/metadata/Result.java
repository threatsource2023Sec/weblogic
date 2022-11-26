package monfox.toolkit.snmp.metadata;

import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.util.FormatUtil;

public class Result {
   Vector _critical = new Vector();
   Vector _major = new Vector();
   Vector _minor = new Vector();
   Vector _warning = new Vector();
   Vector _errors = new Vector();

   public int getCriticalErrorCount() {
      return this._critical.size();
   }

   public int getMajorErrorCount() {
      return this._major.size();
   }

   public int getMinorErrorCount() {
      return this._minor.size();
   }

   public int getErrorCount() {
      return this._errors.size();
   }

   public int getWarningCount() {
      return this._warning.size();
   }

   public List getCriticalErrors() {
      return this._critical;
   }

   public List getMajorErrors() {
      return this._major;
   }

   public List getMinorErrors() {
      return this._minor;
   }

   public List getErrors() {
      return this._errors;
   }

   public List getWarnings() {
      return this._warning;
   }

   public void addError(MetadataError var1) {
      label18: {
         boolean var2 = SnmpOidInfo.a;
         switch (var1.getSeverity()) {
            case 1:
               this._critical.addElement(var1);
               if (!var2) {
                  break;
               }
            case 2:
               this._major.addElement(var1);
               if (!var2) {
                  break;
               }
            case 3:
               this._minor.addElement(var1);
               if (var2) {
                  break label18;
               }
               break;
            default:
               break label18;
         }

         this._errors.addElement(var1);
         return;
      }

      this._warning.addElement(var1);
   }

   public String toString() {
      boolean var4 = SnmpOidInfo.a;
      StringBuffer var1 = new StringBuffer();
      Object[][] var2 = new Object[][]{{a("RM\n=C\nW\u001b2\u0018"), a("\u001bL\u0006?O\u001b_\u0003"), a("\u0015_\u0005$T"), a("\u0015W\u0001$T"), a("\u000f_\u001d%O\u0016Y")}, {a("R]\u0000>H\fMOk\u0018"), new Integer(this.getCriticalErrorCount()), new Integer(this.getMajorErrorCount()), new Integer(this.getMinorErrorCount()), new Integer(this.getWarningCount())}};
      String var3 = FormatUtil.formatTable(var2);
      var1.append(var3);
      this.a(this._critical, var1);
      this.a(this._major, var1);
      this.a(this._minor, var1);
      this.a(this._warning, var1);
      String var10000 = var1.toString();
      if (SnmpException.b) {
         SnmpOidInfo.a = !var4;
      }

      return var10000;
   }

   private void a(Vector var1, StringBuffer var2) {
      Enumeration var3 = var1.elements();

      while(var3.hasMoreElements()) {
         var2.append("\n");
         var2.append(var3.nextElement());
         if (SnmpOidInfo.a) {
            break;
         }
      }

   }

   public void addResult(Result var1) {
      this._critical.addAll(var1._critical);
      this._major.addAll(var1._major);
      this._minor.addAll(var1._minor);
      this._warning.addAll(var1._warning);
      this._errors.addAll(var1._errors);
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 120;
               break;
            case 1:
               var10003 = 62;
               break;
            case 2:
               var10003 = 111;
               break;
            case 3:
               var10003 = 75;
               break;
            default:
               var10003 = 38;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
