package monfox.toolkit.snmp;

import java.net.InetAddress;
import java.util.StringTokenizer;
import monfox.toolkit.snmp.util.StringUtil;

public class SnmpIpAddress extends SnmpValue {
   static final long serialVersionUID = 2083163112464753508L;
   private byte[] a;
   private static final String b = "$Id: SnmpIpAddress.java,v 1.16 2007/03/15 20:36:48 sking Exp $";

   public SnmpIpAddress() {
      this.a = null;
      this.a = new byte[]{-1, -1, -1, -1};
   }

   public SnmpIpAddress(SnmpIpAddress var1) {
      this.a = null;
      this.a = var1.a;
   }

   public SnmpIpAddress(long var1) {
      this.a = null;
      this.a = a(var1);
   }

   public SnmpIpAddress(byte var1, byte var2, byte var3, byte var4) {
      this.a = null;
      this.a = new byte[]{var1, var2, var3, var4};
   }

   public SnmpIpAddress(InetAddress var1) {
      this(var1.getAddress());
   }

   public SnmpIpAddress(long var1, long var3, long var5, long var7) {
      this.a = null;
      this.a = new byte[]{(byte)((int)(var1 & 255L)), (byte)((int)(var3 & 255L)), (byte)((int)(var5 & 255L)), (byte)((int)(var7 & 255L))};
   }

   public SnmpIpAddress(String var1) throws SnmpValueException {
      this.a = null;
      this.fromString(var1);
   }

   public void fromString(String var1) throws SnmpValueException {
      boolean var9 = SnmpValue.b;
      if (var1 == null || var1.equals("0")) {
         this.a = new byte[]{0, 0, 0, 0};
         if (!var9) {
            return;
         }
      }

      int var3;
      int var5;
      if (var1.startsWith("'") && (var1.endsWith(a("q\u000b")) || var1.endsWith(a("q+")))) {
         String var2 = var1.substring(1, var1.length() - 2);
         var3 = var2.length();
         if (var3 % 2 == 1) {
            var2 = "0" + var2;
         }

         int var4 = var2.length() / 2;
         if (var4 != 4) {
            throw new SnmpValueException(a("\u001f-woX?'!GD\u0017'e|Q%0!)") + var2 + "'");
         }

         this.a = new byte[var4];
         var5 = 0;

         while(var5 < this.a.length) {
            char var6 = Character.toUpperCase(var2.charAt(var5 * 2));
            char var7 = Character.toUpperCase(var2.charAt(var5 * 2 + 1));

            try {
               this.a[var5] = Integer.decode(a("f;") + var6 + var7).byteValue();
            } catch (NumberFormatException var10) {
               throw new SnmpValueException(a("\u001f-woX?'!GD\u0017'e|Q%0!)") + var2 + "'");
            }

            if (var9) {
               return;
            }

            ++var5;
            if (var9) {
               break;
            }
         }

         if (!var9) {
            return;
         }
      }

      var1 = StringUtil.StripQuotes(var1);
      StringTokenizer var12 = new StringTokenizer(var1, ".", false);
      if (var12.countTokens() != 4) {
         throw new SnmpValueException(a("\u001f-woX?'!GD\u0017'e|Q%0;.") + var1);
      } else {
         this.a = new byte[4];
         var3 = 0;

         while(var12.hasMoreTokens()) {
            String var13 = var12.nextToken();

            try {
               var5 = Integer.parseInt(var13);
               if (var5 < 0 || var5 > 255) {
                  throw new SnmpValueException(a("\u001f-woX?'!oP21d}Gv ncD9-d`@vy!U") + var13 + a("\u000bch`\u0014~") + var1 + ")");
               }

               this.a[var3++] = (byte)(var5 & 255);
            } catch (NumberFormatException var11) {
               throw new SnmpValueException(a("\u001f-woX?'!oP21d}Gv ncD9-d`@vy!U") + var13 + a("\u000bch`\u0014~") + var1 + ")");
            }

            if (var9) {
               break;
            }
         }

      }
   }

   public SnmpIpAddress(byte[] var1) {
      boolean var3 = SnmpValue.b;
      super();
      this.a = null;
      if (var1 == null) {
         this.a = new byte[]{0, 0, 0, 0};
         if (!var3) {
            return;
         }
      }

      if (var1.length == 4) {
         this.a = var1;
         if (!var3) {
            return;
         }
      }

      if (var1.length < 4) {
         this.a = new byte[]{0, 0, 0, 0};
         int var2 = 0;

         while(var2 < var1.length) {
            this.a[var2] = var1[var2];
            ++var2;
            if (var3 && var3) {
               return;
            }
         }

         if (!var3) {
            return;
         }
      }

      this.a = new byte[]{var1[0], var1[1], var1[2], var1[3]};
   }

   public byte[] toByteArray() {
      return this.a;
   }

   public final String getTypeName() {
      return a("\u001f3@jP$&r}");
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      this.toString(var1);
      return var1.toString();
   }

   public void toString(StringBuffer var1) {
      this.toNumericString(var1);
   }

   public void toNumericString(StringBuffer var1) {
      int var2 = 0;

      while(var2 < this.a.length) {
         var1.append(this.a[var2] & 255);
         if (var2 + 1 < this.a.length) {
            var1.append('.');
         }

         ++var2;
         if (SnmpValue.b) {
            break;
         }
      }

   }

   public SnmpOid toIndexOid(boolean var1) {
      long[] var2 = new long[]{(long)this.a[0] & 255L, (long)this.a[1] & 255L, (long)this.a[2] & 255L, (long)this.a[3] & 255L};
      return new SnmpOid(var2);
   }

   public void appendIndexOid(SnmpOid var1, boolean var2) {
      var1.append(this.toIndexOid());
   }

   public int fromIndexOid(SnmpOid var1, int var2, boolean var3, int var4) throws SnmpValueException {
      boolean var7 = SnmpValue.b;
      if (var2 + 4 > var1.getLength()) {
         throw new SnmpValueException(a("\u001f-woX?'!gZ2&y.{\u001f\u0007!bQ8$uf\u000ev") + var1.getLength() + a("xcOa@v&oaA1+!kX3.d`@%m"));
      } else {
         byte[] var5 = new byte[4];
         int var6 = 0;

         while(true) {
            if (var6 < 4) {
               var5[var6] = (byte)((int)var1.get(var2 + var6));
               ++var6;
               if (var7) {
                  break;
               }

               if (!var7) {
                  continue;
               }
            }

            this.a = var5;
            break;
         }

         return var2 + 4;
      }
   }

   public Object clone() {
      return new SnmpIpAddress(this);
   }

   public boolean equals(Object var1) {
      boolean var5 = SnmpValue.b;
      byte var10000;
      int var3;
      if (var1 instanceof SnmpIpAddress) {
         SnmpIpAddress var7 = (SnmpIpAddress)var1;
         if (this.a.length != var7.a.length) {
            return false;
         } else {
            var3 = 0;

            while(true) {
               if (var3 < this.a.length) {
                  var10000 = this.a[var3];
                  if (var5) {
                     break;
                  }

                  if (var10000 != var7.a[var3]) {
                     return false;
                  }

                  ++var3;
                  if (!var5) {
                     continue;
                  }
               }

               var10000 = 1;
               break;
            }

            return (boolean)var10000;
         }
      } else if (!(var1 instanceof String)) {
         return false;
      } else {
         StringTokenizer var2 = new StringTokenizer((String)var1, ".", false);
         if (var2.countTokens() != 4) {
            return false;
         } else {
            var3 = 0;

            while(true) {
               if (var3 < 4) {
                  try {
                     var10000 = this.a[var3];
                     if (var5) {
                        break;
                     }

                     if (var10000 != Byte.parseByte(var2.nextToken())) {
                        return false;
                     }
                  } catch (NumberFormatException var6) {
                     return false;
                  }

                  ++var3;
                  if (!var5) {
                     continue;
                  }
               }

               var10000 = 1;
               break;
            }

            return (boolean)var10000;
         }
      }
   }

   public long longValue() {
      return a(this.a);
   }

   public int getType() {
      return 64;
   }

   public int getTag() {
      return 64;
   }

   public int getCoder() {
      return 3;
   }

   private static long a(byte[] var0) {
      long var1 = 0L;
      var1 |= (long)(var0[0] << 24 & -16777216);
      var1 |= (long)(var0[1] << 16 & 16711680);
      var1 |= (long)(var0[2] << 8 & '\uff00');
      var1 |= (long)(var0[3] & 255);
      return var1;
   }

   private static byte[] a(long var0) {
      byte var2 = (byte)((int)(var0 & 255L));
      byte var3 = (byte)((int)(var0 >> 8 & 255L));
      byte var4 = (byte)((int)(var0 >> 16 & 255L));
      byte var5 = (byte)((int)(var0 >> 24 & 255L));
      byte[] var6 = new byte[]{var5, var4, var3, var2};
      return var6;
   }

   public byte[] getByteArray() {
      return this.a;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 86;
               break;
            case 1:
               var10003 = 67;
               break;
            case 2:
               var10003 = 1;
               break;
            case 3:
               var10003 = 14;
               break;
            default:
               var10003 = 52;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
