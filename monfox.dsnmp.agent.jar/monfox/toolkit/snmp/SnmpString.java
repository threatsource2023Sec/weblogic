package monfox.toolkit.snmp;

import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;
import monfox.toolkit.snmp.util.StringUtil;

public class SnmpString extends SnmpValue {
   static final long serialVersionUID = 7011014451269367155L;
   byte[] a;
   private static char[] b = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
   private static final String c = "$Id: SnmpString.java,v 1.28 2011/08/30 21:54:23 sking Exp $";

   public SnmpString(SnmpString var1) {
      this.a = var1.a;
   }

   public SnmpString() {
      this.a = new byte[0];
   }

   public SnmpString(String var1) throws SnmpValueException {
      this.fromString(var1);
   }

   public void fromString(String var1) throws SnmpValueException {
      boolean var2 = SnmpValue.b;
      if (var1.startsWith("'") && (var1.endsWith(a("u\u0002")) || var1.endsWith(a("u\"")))) {
         this.fromHexString(var1);
         if (!var2) {
            return;
         }
      }

      if (var1.startsWith(a("b2"))) {
         this.fromHexString(var1);
         if (!var2) {
            return;
         }
      }

      if (var1.startsWith("'") && (var1.endsWith(a("u\b")) || var1.endsWith(a("u(")))) {
         this.fromBinaryString(var1);
         if (!var2) {
            return;
         }
      }

      var1 = StringUtil.StripQuotes(var1);
      this.a = SnmpFramework.stringToByteArray(var1, (SnmpOid)null);
   }

   public void fromString(String var1, String var2) throws SnmpValueException, UnsupportedEncodingException {
      boolean var3 = SnmpValue.b;
      if (var1.startsWith("'") && (var1.endsWith(a("u\u0002")) || var1.endsWith(a("u\"")))) {
         this.fromHexString(var1);
         if (!var3) {
            return;
         }
      }

      if (var1.startsWith(a("b2"))) {
         this.fromHexString(var1);
         if (!var3) {
            return;
         }
      }

      if (var1.startsWith("'") && (var1.endsWith(a("u\b")) || var1.endsWith(a("u(")))) {
         this.fromBinaryString(var1);
         if (!var3) {
            return;
         }
      }

      var1 = StringUtil.StripQuotes(var1);
      this.a = var1.getBytes(var2);
   }

   public SnmpString(byte[] var1) {
      this.a = var1;
   }

   public byte[] toByteArray() {
      return this.a;
   }

   public synchronized Object clone() {
      return new SnmpString(this);
   }

   public final synchronized SnmpValue duplicate() {
      return new SnmpString(this.a);
   }

   public String getTypeName() {
      return a("\u001d\t\u000fsIr\u0019\u000fdT\u001c\r");
   }

   public SnmpOid toIndexOid(boolean var1) {
      boolean var4 = SnmpValue.b;
      long[] var2;
      int var3;
      if (!var1) {
         var2 = new long[this.a.length + 1];
         var2[0] = (long)this.a.length;
         var3 = 0;

         while(var3 < this.a.length) {
            var2[var3 + 1] = (long)(this.a[var3] & 255);
            ++var3;
            if (var4) {
               break;
            }
         }

         return new SnmpOid(var2);
      } else {
         var2 = new long[this.a.length];
         var3 = 0;

         while(var3 < this.a.length) {
            var2[var3] = (long)(this.a[var3] & 255);
            ++var3;
            if (var4) {
               break;
            }
         }

         return new SnmpOid(var2);
      }
   }

   public void appendIndexOid(SnmpOid var1, boolean var2) {
      var1.append(this.toIndexOid(var2));
   }

   public int fromIndexOid(SnmpOid var1, int var2, boolean var3, int var4) throws SnmpValueException {
      int var6;
      boolean var9;
      int var10;
      label39: {
         var9 = SnmpValue.b;
         boolean var5 = true;
         if (var4 > 0) {
            var10 = var4;
            var6 = var2;
            if (!var9) {
               break label39;
            }
         }

         if (!var3) {
            var10 = (int)var1.get(var2);
            var6 = var2 + 1;
            if (!var9) {
               break label39;
            }
         }

         var10 = var1.getLength() - var2;
         var6 = var2;
      }

      if (var10 + var6 > var1.getLength()) {
         throw new SnmpValueException(a("\u001b$-Wq;.{_s6/#\u0016R\u001b\u000e{Zx<-/^'r") + var10 + a("|j\u0015Yir/5Yh5\"{Sq7'>Xi!d"));
      } else {
         byte[] var7 = new byte[var10];
         int var8 = 0;

         while(true) {
            if (var8 < var10) {
               var7[var8] = (byte)((int)var1.get(var8 + var6));
               ++var8;
               if (!var9 || !var9) {
                  continue;
               }
               break;
            }

            this.a = var7;
            break;
         }

         return var6 + var10;
      }
   }

   void a(StringBuffer var1, SnmpOid var2) {
      var1.append(this.a(var2));
   }

   public void toString(StringBuffer var1) {
      var1.append(this.toString());
   }

   public String toString(String var1) throws UnsupportedEncodingException {
      return new String(this.a, var1);
   }

   public String toString() {
      return this.a((SnmpOid)null);
   }

   public void fromJavaString(String var1, SnmpOid var2) throws SnmpValueException {
      boolean var3 = SnmpValue.b;
      if (var1.startsWith("'") && (var1.endsWith(a("u\u0002")) || var1.endsWith(a("u\"")))) {
         this.fromHexString(var1);
         if (!var3) {
            return;
         }
      }

      if (var1.startsWith(a("b2"))) {
         this.fromHexString(var1);
         if (!var3) {
            return;
         }
      }

      if (var1.startsWith("'") && (var1.endsWith(a("u\b")) || var1.endsWith(a("u(")))) {
         this.fromBinaryString(var1);
         if (!var3) {
            return;
         }
      }

      var1 = StringUtil.StripQuotes(var1);
      this.a = SnmpFramework.stringToByteArray(var1, var2);
   }

   String a(SnmpOid var1) {
      boolean var7 = SnmpValue.b;
      if (SnmpFramework.isDisplayAllStringsAsCharacters()) {
         return SnmpFramework.byteArrayToString(this.a, var1);
      } else {
         byte var2 = 1;
         int var3 = 0;

         int var10000;
         while(true) {
            if (var2 != 0) {
               var10000 = var3;
               if (var7) {
                  break;
               }

               if (var3 < this.a.length) {
                  label147: {
                     byte var4 = this.a[var3];
                     if (var4 == 0 && var2 != 0) {
                        if (var3 == this.a.length - 1) {
                           return SnmpFramework.byteArrayToString(this.a, 0, var3, (SnmpOid)null);
                        }

                        var2 = 0;
                        if (!var7) {
                           break label147;
                        }
                     }

                     if ((var4 < 7 || var4 > 13) && (var4 < 32 || var4 > 126)) {
                        label146: {
                           if (SnmpFramework.isUTF8ToStringSupported()) {
                              byte var5;
                              label138: {
                                 var5 = 0;
                                 if ((var4 & 128) == 0) {
                                    var2 = 0;
                                    if (!var7) {
                                       break label138;
                                    }
                                 }

                                 if ((var4 & 224) == 192) {
                                    var5 = 1;
                                    if (!var7) {
                                       break label138;
                                    }
                                 }

                                 if ((var4 & 240) == 224) {
                                    var5 = 2;
                                    if (!var7) {
                                       break label138;
                                    }
                                 }

                                 if ((var4 & 248) == 240) {
                                    var5 = 3;
                                    if (!var7) {
                                       break label138;
                                    }
                                 }

                                 if ((var4 & 252) == 248) {
                                    var5 = 4;
                                    if (!var7) {
                                       break label138;
                                    }
                                 }

                                 if ((var4 & 254) == 252) {
                                    var5 = 5;
                                    if (!var7) {
                                       break label138;
                                    }
                                 }

                                 var5 = 0;
                                 var2 = 0;
                                 if (!var7) {
                                    break label147;
                                 }
                              }

                              if (var5 > 0) {
                                 label143: {
                                    if (var3 + var5 < this.a.length) {
                                       int var6 = 1;

                                       short var10001;
                                       label83: {
                                          while(var6 <= var5) {
                                             var10000 = this.a[var3 + var6] & 192;
                                             var10001 = 128;
                                             if (var7) {
                                                break label83;
                                             }

                                             if (var10000 != 128) {
                                                var2 = 0;
                                                if (!var7) {
                                                   break;
                                                }
                                             }

                                             ++var6;
                                             if (var7) {
                                                break;
                                             }
                                          }

                                          var10000 = var3;
                                          var10001 = var5;
                                       }

                                       var3 = var10000 + var10001;
                                       if (!var7) {
                                          break label143;
                                       }
                                    }

                                    var2 = 0;
                                 }
                              }

                              if (var2 == 0 && !var7) {
                                 break label147;
                              }

                              if (!var7) {
                                 break label146;
                              }
                           }

                           var2 = 0;
                           if (!var7) {
                              break label147;
                           }
                        }
                     }

                     ++var3;
                     if (!var7) {
                        continue;
                     }
                  }
               }
            }

            var10000 = var2;
            break;
         }

         return var10000 != 0 ? SnmpFramework.byteArrayToString(this.a, var1) : this.toHexString();
      }
   }

   public String getString() {
      return this.toString();
   }

   public String getString(String var1) throws UnsupportedEncodingException {
      return this.toString(var1);
   }

   public long longValue() {
      try {
         return Long.parseLong(SnmpFramework.byteArrayToString(this.a, (SnmpOid)null));
      } catch (NumberFormatException var2) {
         return 0L;
      }
   }

   public synchronized void fromHexString(String var1) throws SnmpValueException {
      boolean var8;
      label87: {
         var8 = SnmpValue.b;
         if (var1.startsWith(a("b2"))) {
            var1 = var1.substring(2);
            if (!var8) {
               break label87;
            }
         }

         if (var1.startsWith("x")) {
            var1 = var1.substring(1);
            if (!var8) {
               break label87;
            }
         }

         if (var1.startsWith("'") && (var1.endsWith(a("u\u0002")) || var1.endsWith(a("u\"")))) {
            var1 = var1.substring(1, var1.length() - 2);
         }
      }

      int var4;
      if (var1.indexOf(58) >= 0) {
         StringTokenizer var2 = new StringTokenizer(var1, ":", false);
         int var3 = var2.countTokens();
         var4 = 0;
         byte[] var5 = new byte[var3];

         label66: {
            while(var2.hasMoreTokens()) {
               String var6 = var2.nextToken();

               try {
                  var5[var4++] = Integer.decode(a("b2") + var6).byteValue();
               } catch (NumberFormatException var10) {
                  throw new SnmpValueException(a("\u0010+?\u0016u72{Br9/5\u0016:") + var6 + "'" + a("r#5\u0016:") + var1 + "'");
               }

               if (var8) {
                  break label66;
               }

               if (var8) {
                  break;
               }
            }

            this.a = var5;
         }

         if (!var8) {
            return;
         }
      }

      int var11 = var1.length();
      if (var11 % 2 == 1) {
         var1 = "0" + var1;
      }

      byte[] var12 = new byte[var1.length() / 2];
      var4 = 0;

      while(var4 < var12.length) {
         char var13 = Character.toUpperCase(var1.charAt(var4 * 2));
         char var14 = Character.toUpperCase(var1.charAt(var4 * 2 + 1));

         try {
            var12[var4] = Integer.decode(a("b2") + var13 + var14).byteValue();
         } catch (NumberFormatException var9) {
            throw new SnmpValueException(a("\u001b$-Wq;.{~x*j\bBo;$<\u0016:") + var1 + "'");
         }

         if (var8) {
            return;
         }

         ++var4;
         if (var8) {
            break;
         }
      }

      this.a = var12;
   }

   public void fromBinaryString(String var1) throws SnmpValueException {
      boolean var5 = SnmpValue.b;
      if (var1.startsWith("'") && (var1.endsWith(a("u\b")) || var1.endsWith(a("u(")))) {
         var1 = var1.substring(1, var1.length() - 2);
      }

      String var10000;
      while(true) {
         if (var1.length() % 8 != 0) {
            var10000 = var1 + "0";
            if (var5) {
               break;
            }

            var1 = var10000;
            if (!var5) {
               continue;
            }
         }

         var10000 = var1;
         break;
      }

      int var2 = var10000.length();
      byte[] var3 = new byte[var2 / 8];
      int var4 = 0;

      while(true) {
         if (var4 < var2) {
            if (var5) {
               break;
            }

            label30: {
               if (var1.charAt(var4) == '1') {
                  var3[var4 / 8] |= (byte)(1 << 7 - var4 % 8);
                  if (!var5) {
                     break label30;
                  }
               }

               if (var1.charAt(var4) != '0') {
                  throw new SnmpValueException(a("\u001b$-Wq;.{tt<+)O=\u0001>)_s5j|") + var1 + "'");
               }
            }

            ++var4;
            if (!var5) {
               continue;
            }
         }

         this.a = var3;
         break;
      }

   }

   public String toHexString() {
      boolean var3 = SnmpValue.b;
      StringBuffer var1 = new StringBuffer();
      var1.append("'");
      int var2 = 0;

      while(true) {
         if (var2 < this.a.length) {
            var1.append(b[this.a[var2] >> 4 & 15]);
            var1.append(b[this.a[var2] & 15]);
            ++var2;
            if (var3) {
               break;
            }

            if (!var3) {
               continue;
            }
         }

         var1.append(a("u\u0002"));
         break;
      }

      return var1.toString();
   }

   public byte[] getByteArray() {
      return this.a;
   }

   public boolean equals(Object var1) {
      boolean var5 = SnmpValue.b;
      byte var10000;
      int var3;
      int var4;
      if (var1 instanceof SnmpString) {
         SnmpString var6 = (SnmpString)var1;
         if (this.a.length != var6.a.length) {
            return false;
         } else {
            var3 = this.a.length;
            var4 = 0;

            while(true) {
               if (var4 < var3) {
                  var10000 = this.a[var4];
                  if (var5) {
                     break;
                  }

                  if (var10000 != var6.a[var4]) {
                     return false;
                  }

                  ++var4;
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
         throw new ClassCastException(a("\u001c%/\u0016^='+Wo3(7S=%#/^=\u0001$6FN&82Xz"));
      } else {
         byte[] var2 = SnmpFramework.stringToByteArray((String)var1, (SnmpOid)null);
         if (this.a.length != var2.length) {
            return false;
         } else {
            var3 = this.a.length;
            var4 = 0;

            while(true) {
               if (var4 < var3) {
                  var10000 = this.a[var4];
                  if (var5) {
                     break;
                  }

                  if (var10000 != var2[var4]) {
                     return false;
                  }

                  ++var4;
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

   public int getType() {
      return 4;
   }

   public int getTag() {
      return 4;
   }

   public int getCoder() {
      return 3;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 82;
               break;
            case 1:
               var10003 = 74;
               break;
            case 2:
               var10003 = 91;
               break;
            case 3:
               var10003 = 54;
               break;
            default:
               var10003 = 29;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
