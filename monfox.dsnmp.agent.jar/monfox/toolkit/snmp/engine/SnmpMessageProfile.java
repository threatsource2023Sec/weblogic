package monfox.toolkit.snmp.engine;

import java.io.Serializable;

public class SnmpMessageProfile implements Cloneable, Serializable {
   static final long serialVersionUID = 5882400816644065984L;
   private int a = 0;
   private int b = 1;
   private int c = 0;
   private String d = a(",\u0001\u007fU\u001f?");
   private byte[] e = null;
   private static final String f = "$Id: SnmpMessageProfile.java,v 1.4 2002/07/09 16:07:09 samin Exp $";

   public SnmpMessageProfile(String var1) {
      this.a = 0;
      this.b = 1;
      this.c = 0;
      this.d = var1;
   }

   public SnmpMessageProfile() {
      this.a = 0;
      this.b = 1;
      this.c = 0;
      this.d = a(",\u0001\u007fU\u001f?");
   }

   public SnmpMessageProfile(int var1, int var2, int var3, String var4) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
   }

   public int getSnmpVersion() {
      return this.a;
   }

   public int getSecurityLevel() {
      return this.c;
   }

   public int getSecurityModel() {
      return this.b;
   }

   public String getSecurityName() {
      return this.d;
   }

   public byte[] getSecurityNameBytes() {
      if (this.e == null) {
         if (this.d == null) {
            this.e = new byte[0];
            if (!SnmpPDU.i) {
               return this.e;
            }
         }

         this.e = this.d.getBytes();
      }

      return this.e;
   }

   public void setSnmpVersion(int var1) {
      this.a = var1;
   }

   public void setSecurityModel(int var1) {
      this.b = var1;
   }

   public void setSecurityLevel(int var1) {
      this.c = var1;
   }

   public void setSecurityName(String var1) {
      this.d = var1;
      this.e = null;
   }

   public boolean hasAuth() {
      return (this.c & 1) != 0;
   }

   public boolean hasPriv() {
      return (this.c & 2) != 0;
   }

   public int hashCode() {
      return this.d.hashCode();
   }

   public Object clone() {
      SnmpMessageProfile var1 = new SnmpMessageProfile();
      var1.setSnmpVersion(this.a);
      var1.setSecurityModel(this.b);
      var1.setSecurityLevel(this.c);
      var1.setSecurityName(this.d);
      return var1;
   }

   public boolean equals(Object var1) {
      if (var1 == null) {
         return false;
      } else if (var1 == this) {
         return true;
      } else if (!(var1 instanceof SnmpMessageProfile)) {
         return false;
      } else {
         SnmpMessageProfile var2 = (SnmpMessageProfile)var1;
         if (!this.d.equals(var2.d)) {
            return false;
         } else if (this.b != var2.b) {
            return false;
         } else {
            return this.c == var2.c;
         }
      }
   }

   public String toString() {
      boolean var2 = SnmpPDU.i;
      StringBuffer var1 = new StringBuffer();
      var1.append(a("'\u0002xK\u00055\u001bs\u0004"));
      switch (this.a) {
         case 0:
            var1.append(a("*E"));
            if (!var2) {
               break;
            }
         case 1:
            var1.append(a("*F"));
            if (!var2) {
               break;
            }
         case 3:
            var1.append(a("*G"));
            if (!var2) {
               break;
            }
         case 2:
         default:
            var1.append(a("cK\"\u0011") + this.a + ")");
      }

      var1.append(a("p\u0007xZ;3\u0010xUK"));
      switch (this.b & 3) {
         case 1:
            var1.append(a("*E"));
            if (!var2) {
               break;
            }
         case 2:
            var1.append(a("*F~"));
            if (!var2) {
               break;
            }
         case 3:
            var1.append(a(")\u0007p"));
            if (!var2) {
               break;
            }
         default:
            var1.append(a("cK\"\u0011") + this.b + ")");
      }

      var1.append(a("p\u0007xZ:9\u0002xUK"));
      switch (this.c & 3) {
         case 0:
            var1.append(a("2\u001b\\L\u00024:ri\u00045\u0002"));
            if (!var2) {
               break;
            }
         case 1:
            var1.append(a("=\u0001iQ83$oP\u0000"));
            if (!var2) {
               break;
            }
         case 3:
            var1.append(a("=\u0001iQ&.\u001dk"));
            if (!var2) {
               break;
            }
         case 2:
         default:
            var1.append(a("cK\"\u0011") + this.c + ")");
      }

      var1.append(a("p\u0007xZ8=\u0019x\u0004")).append(this.d).append('}');
      return var1.toString();
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 92;
               break;
            case 1:
               var10003 = 116;
               break;
            case 2:
               var10003 = 29;
               break;
            case 3:
               var10003 = 57;
               break;
            default:
               var10003 = 118;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
