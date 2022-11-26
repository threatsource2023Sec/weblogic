package monfox.toolkit.snmp.engine;

import java.io.Serializable;

public class SnmpContext implements Serializable {
   static final long serialVersionUID = 7682493455141792267L;
   public static final SnmpEngineID ANY_ENGINE_ID = new SnmpEngineID();
   private static final SnmpEngineID a = new SnmpEngineID();
   public static final String DEFAULT_CONTEXT_NAME = "";
   private SnmpEngineID b;
   private String c;
   private static final String d = "$Id: SnmpContext.java,v 1.6 2003/09/23 21:06:55 sking Exp $";

   public SnmpContext() {
      this(new SnmpEngineID(), "");
   }

   public SnmpContext(SnmpEngineID var1, String var2) {
      if (var1 == null) {
         var1 = new SnmpEngineID();
      }

      if (var2 == null) {
         var2 = "";
      }

      this.b = var1;
      this.c = var2;
   }

   public SnmpContext(byte[] var1, byte[] var2) {
      this.b = new SnmpEngineID(var1);
      this.c = new String(var2);
   }

   public SnmpContext(SnmpEngineID var1) {
      this(var1, "");
   }

   public String getContextName() {
      return this.c;
   }

   public SnmpEngineID getContextEngineID() {
      return this.b;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("\f\u001dmE\t\u0012\u0006vn\u0013\u0010\u0017lN43C"));
      var1.append(this.b);
      var1.append(a("[\u001dmE\t\u0012\u0006ve\u001c\u001a\u001b?")).append(this.c);
      var1.append('}');
      return var1.toString();
   }

   public int hashCode() {
      return this.c != null ? this.c.hashCode() : 1;
   }

   public boolean equals(Object var1) {
      try {
         SnmpContext var2 = (SnmpContext)var1;
         if ((this.b == var2.b || this.b.equals((Object)var2.b)) && (this.c == var2.c || this.c.equals(var2.c))) {
            return true;
         }
      } catch (Exception var3) {
      }

      return false;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 119;
               break;
            case 1:
               var10003 = 126;
               break;
            case 2:
               var10003 = 2;
               break;
            case 3:
               var10003 = 43;
               break;
            default:
               var10003 = 125;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
