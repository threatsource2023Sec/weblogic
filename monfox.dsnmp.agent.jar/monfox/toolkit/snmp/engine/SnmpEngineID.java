package monfox.toolkit.snmp.engine;

import java.io.Serializable;
import java.net.InetAddress;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.util.ByteFormatter;

public class SnmpEngineID implements Serializable {
   static final long serialVersionUID = 7095439471579977357L;
   public static final int IPV4_FORMATTING = 1;
   public static final int IPV6_FORMATTING = 2;
   public static final int MAC_FORMATTING = 3;
   public static final int TEXT_FORMATTING = 4;
   public static final int OCTET_FORMATTING = 5;
   private boolean a;
   private int b;
   private byte[] c;
   private static final String d = "$Id: SnmpEngineID.java,v 1.10 2006/08/15 20:49:24 sking Exp $";

   public static SnmpEngineID getRawEngineID(String var0, boolean var1) throws SnmpValueException {
      return new SnmpEngineID(var0, var1);
   }

   public static SnmpEngineID getIPvXEngineID(long var0, InetAddress var2, boolean var3) {
      byte[] var4 = var2.getAddress();
      return var4.length == 4 ? new SnmpEngineID(var0, 1, var4, var3) : new SnmpEngineID(var0, 2, var4, var3);
   }

   public static SnmpEngineID getMACEngineID(long var0, byte[] var2, boolean var3) {
      return new SnmpEngineID(var0, 3, var2, var3);
   }

   public static SnmpEngineID getTextEngineID(long var0, String var2, boolean var3) {
      return new SnmpEngineID(var0, 4, SnmpFramework.stringToByteArray(var2, (SnmpOid)null), var3);
   }

   public static SnmpEngineID getOctetEngineID(long var0, byte[] var2, boolean var3) {
      return new SnmpEngineID(var0, 5, var2, var3);
   }

   public SnmpEngineID(long var1, byte[] var3, boolean var4) {
      this.a = false;
      this.b = -1;
      this.c = null;
      byte var5 = 12;
      this.c = new byte[var5];
      this.c[3] = (byte)((int)(var1 & 255L));
      this.c[2] = (byte)((int)(var1 >> 8 & 255L));
      this.c[1] = (byte)((int)(var1 >> 16 & 255L));
      this.c[0] = (byte)((int)(var1 >> 24 & 255L));
      int var6 = 0;

      while(var6 < var3.length && var6 < var5 - 4) {
         this.c[var5 - 1 - var6] = var3[var3.length - 1 - var6];
         ++var6;
         if (SnmpPDU.i) {
            break;
         }
      }

      byte[] var10000 = this.c;
      var10000[0] = (byte)(var10000[0] & -129);
      this.a = var4;
   }

   public SnmpEngineID(long var1, int var3, byte[] var4, boolean var5) {
      this.a = false;
      this.b = -1;
      this.c = null;
      int var6 = 5 + (var4 == null ? 0 : var4.length);
      if (var6 > 32) {
         var6 = 32;
      }

      this.c = new byte[var6];
      this.c[3] = (byte)((int)(var1 & 255L));
      this.c[2] = (byte)((int)(var1 >> 8 & 255L));
      this.c[1] = (byte)((int)(var1 >> 16 & 255L));
      this.c[0] = (byte)((int)(var1 >> 24 & 255L));
      this.c[4] = (byte)var3;
      if (var4 != null) {
         int var7 = 0;

         while(var7 < var4.length && var7 < var6 - 5) {
            this.c[var6 - 1 - var7] = var4[var4.length - 1 - var7];
            ++var7;
            if (SnmpPDU.i) {
               break;
            }
         }
      }

      byte[] var10000 = this.c;
      var10000[0] = (byte)(var10000[0] | 128);
      this.a = var5;
   }

   public SnmpEngineID(long var1, InetAddress var3, boolean var4) {
      this(var1, 1, var3.getAddress(), var4);
   }

   public SnmpEngineID(long var1, String var3, boolean var4) {
      this(var1, 4, var3.getBytes(), var4);
   }

   public SnmpEngineID() {
      this(new byte[0], false);
   }

   public SnmpEngineID(String var1, boolean var2) throws SnmpValueException {
      this((new SnmpString(var1)).toByteArray(), var2);
   }

   public SnmpEngineID(String var1) throws SnmpValueException {
      this(var1, false);
   }

   public SnmpEngineID(byte[] var1) {
      this(var1, false);
   }

   public SnmpEngineID(byte[] var1, boolean var2) {
      this.a = false;
      this.b = -1;
      this.c = null;
      if (var1 == null) {
         var1 = new byte[0];
      }

      this.c = var1;
      this.a = var2;
   }

   public byte[] getValue() {
      return this.c;
   }

   public byte[] toByteArray() {
      return this.c;
   }

   public int hashCode() {
      boolean var2 = SnmpPDU.i;
      SnmpEngineID var10000;
      if (this.b == -1) {
         this.b = 0;
         int var1 = 0;

         while(var1 < this.c.length) {
            var10000 = this;
            if (var2) {
               return var10000.b;
            }

            this.b = this.b * 37 + this.c[var1];
            ++var1;
            if (var2) {
               break;
            }
         }
      }

      var10000 = this;
      return var10000.b;
   }

   public boolean equals(Object var1) {
      boolean var4 = SnmpPDU.i;
      if (var1 == null) {
         return false;
      } else if (var1 == this) {
         return true;
      } else if (!(var1 instanceof SnmpEngineID)) {
         return false;
      } else {
         SnmpEngineID var2 = (SnmpEngineID)var1;
         if (this.b != var2.b) {
            return false;
         } else if (this.c == var2.c) {
            return true;
         } else if (this.c != null && var2.c != null) {
            if (this.c.length != var2.c.length) {
               return false;
            } else {
               int var3 = 0;

               byte var10000;
               while(true) {
                  if (var3 < this.c.length) {
                     var10000 = this.c[var3];
                     if (var4) {
                        break;
                     }

                     if (var10000 != var2.c[var3]) {
                        return false;
                     }

                     ++var3;
                     if (!var4) {
                        continue;
                     }
                  }

                  var10000 = 1;
                  break;
               }

               return (boolean)var10000;
            }
         } else {
            return false;
         }
      }
   }

   public boolean equals(byte[] var1) {
      boolean var3 = SnmpPDU.i;
      if (this.c == var1) {
         return true;
      } else if (this.c != null && var1 != null) {
         if (this.c.length != var1.length) {
            return false;
         } else {
            int var2 = 0;

            byte var10000;
            while(true) {
               if (var2 < this.c.length) {
                  var10000 = this.c[var2];
                  if (var3) {
                     break;
                  }

                  if (var10000 != var1[var2]) {
                     return false;
                  }

                  ++var2;
                  if (!var3) {
                     continue;
                  }
               }

               var10000 = 1;
               break;
            }

            return (boolean)var10000;
         }
      } else {
         return false;
      }
   }

   public boolean isAuthoritative() {
      return this.a;
   }

   public String toString() {
      if (this.c != null) {
         return this.isAuthoritative() ? ByteFormatter.toHexString(this.c) + a("`Iv") : ByteFormatter.toHexString(this.c);
      } else {
         return a("&}3f");
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
               var10003 = 72;
               break;
            case 1:
               var10003 = 8;
               break;
            case 2:
               var10003 = 95;
               break;
            case 3:
               var10003 = 10;
               break;
            default:
               var10003 = 7;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
