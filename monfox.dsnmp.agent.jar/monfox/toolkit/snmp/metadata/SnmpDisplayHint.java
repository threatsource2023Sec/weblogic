package monfox.toolkit.snmp.metadata;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValueException;

public class SnmpDisplayHint implements Serializable {
   static final long serialVersionUID = 8248678647769104866L;
   private OctetFormat[] _format = null;

   public SnmpDisplayHint(String var1) {
      this.set(var1);
   }

   public void set(String var1) {
      this._format = this.a(var1);
   }

   public void format(StringBuffer var1, long var2) {
      var1.append(this.format(var2));
   }

   public String format(long var1) {
      if (this._format != null && this._format.length != 0 && !this._format[0].isString) {
         OctetFormat var3 = this._format[0];
         switch (var3.format) {
            case 'b':
               return Long.toBinaryString(var1);
            case 'd':
               return this.a(var1, var3.length);
            case 'o':
               return Long.toOctalString(var1);
            case 'x':
               return Long.toHexString(var1);
            default:
               return String.valueOf(var1);
         }
      } else {
         return String.valueOf(var1);
      }
   }

   public String format(String var1) {
      return var1 == null ? null : this.format(SnmpFramework.stringToByteArray(var1, (SnmpOid)null));
   }

   public void format(StringBuffer var1, String var2) {
      if (var2 != null) {
         this.format(var1, SnmpFramework.stringToByteArray(var2, (SnmpOid)null));
      }
   }

   public String format(byte[] var1) {
      if (var1 == null) {
         return null;
      } else {
         StringBuffer var2 = new StringBuffer();
         this.format(var2, var1);
         return var2.toString();
      }
   }

   public void format(StringBuffer var1, byte[] var2) {
      boolean var7 = SnmpOidInfo.a;
      if (var2 != null) {
         if (this._format == null || this._format.length == 0 || !this._format[0].isString) {
            var1.append(SnmpFramework.byteArrayToString(var2, (SnmpOid)null));
            if (!var7) {
               return;
            }
         }

         int var3 = 0;
         OctetFormat var4 = null;
         int var5 = 0;

         int var6;
         do {
            int var10000;
            int var10001;
            label37: {
               if (var3 >= var2.length) {
                  var10000 = var5;
                  var10001 = this._format.length;
                  if (var7) {
                     break label37;
                  }

                  if (var5 >= var10001) {
                     break;
                  }
               }

               var10000 = var5;
               var10001 = this._format.length;
            }

            var4 = var10000 < var10001 ? this._format[var5++] : var4;
            var6 = var3;
            var3 = this.a(var4, var3, var2, var1);
         } while((var6 != var3 || var5 < this._format.length || var7) && !var7);

      }
   }

   public byte[] parseString(String var1) throws SnmpValueException {
      boolean var8 = SnmpOidInfo.a;
      if (var1 == null) {
         return null;
      } else if (this._format != null && this._format.length != 0 && this._format[0].isString) {
         try {
            ByteArrayOutputStream var2 = new ByteArrayOutputStream();
            new StringTokenizer(var1, "", true);
            OctetFormat var4 = null;
            int var5 = 0;
            int var6 = 0;
            char[] var7 = var1.toCharArray();

            do {
               int var10000;
               int var10001;
               label38: {
                  if (var6 >= var7.length) {
                     var10000 = var5;
                     var10001 = this._format.length;
                     if (var8) {
                        break label38;
                     }

                     if (var5 >= var10001) {
                        break;
                     }
                  }

                  var10000 = var5;
                  var10001 = this._format.length;
               }

               var4 = var10000 < var10001 ? this._format[var5++] : var4;
               var6 = this.a(var4, var6, var7, var2);
            } while(!var8);

            if (var6 < var7.length) {
               throw new SnmpValueException(b("/KF\u0007@$V]\u0000RjG]\u001eD$@\u0012\u001cOjU]\u0007L+GF\u0010Ej@F\u0007H$T\u0012R") + var1.substring(var6));
            } else {
               return var2.toByteArray();
            }
         } catch (IOException var9) {
            throw new SnmpValueException(b(")R\\\u001bN>\u0013B\u0014S9V\u0012\u0003@&FWU\u0006") + var1 + b("m\u0013T\u001aSjw{&q\u0006rkXi\u0003}fU\u0006") + this + "'");
         }
      } else {
         return SnmpFramework.stringToByteArray(var1, (SnmpOid)null);
      }
   }

   private int a(OctetFormat var1, int var2, char[] var3, ByteArrayOutputStream var4) throws SnmpValueException, IOException {
      if (var1.isString) {
         return this.b(var1, var2, var3, var4);
      } else {
         throw new SnmpValueException(b("\u000eza%m\u000bj\u001f=h\u0004g\u0012\u0005@8@[\u001bFj]]\u0001\u00019FB\u0005N8GW\u0011\u0001,\\@Uh\u0004gw2d\u0018\u0013D\u0014M?VA"));
      }
   }

   private int b(OctetFormat var1, int var2, char[] var3, ByteArrayOutputStream var4) throws SnmpValueException, IOException {
      boolean var8 = SnmpOidInfo.a;
      ByteArrayOutputStream var5 = new ByteArrayOutputStream();
      boolean var6 = false;
      int var7 = 0;

      label64:
      while(var2 < var3.length) {
         char var10000 = var1.format;

         do {
            switch (var10000) {
               case 'x':
                  var2 = this.d(var1, var2, var3, var5);
                  if (!var8) {
                     break;
                  }
               case 'o':
                  var2 = this.e(var1, var2, var3, var5);
                  if (!var8) {
                     break;
                  }
               case 'd':
                  var2 = this.f(var1, var2, var3, var5);
                  if (!var8) {
                     break;
                  }
               case 'a':
                  var2 = this.c(var1, var2, var3, var5);
            }

            label70: {
               ++var7;
               if (var2 >= var3.length) {
                  var6 = true;
                  if (!var8) {
                     break label70;
                  }
               }

               if (var1.separator != 0) {
                  label71: {
                     if (var3[var2] == var1.separator) {
                        ++var2;
                        if (!var8) {
                           break label71;
                        }
                     }

                     if (var1.terminator == 0 || var3[var2] != var1.terminator) {
                        throw new SnmpValueException(b("'ZA\u0006H$T\u0012\u0006D:R@\u0014U%A\u001d\u0001D8^[\u001b@>\\@UN8\u0013[\u001bW+_[\u0011\u0001)[S\u0007@)GW\u0007\u0001j\u0014") + var3[var2] + b("m\u0013S\u0001\u0001:\\AU") + var2 + b("jU]\u0007\u0001,\\@\u0018@>\u0013\u0015") + var1 + b("m\u001d"));
                     }

                     ++var2;
                     var6 = true;
                     if (var8) {
                        throw new SnmpValueException(b("'ZA\u0006H$T\u0012\u0006D:R@\u0014U%A\u001d\u0001D8^[\u001b@>\\@UN8\u0013[\u001bW+_[\u0011\u0001)[S\u0007@)GW\u0007\u0001j\u0014") + var3[var2] + b("m\u0013S\u0001\u0001:\\AU") + var2 + b("jU]\u0007\u0001,\\@\u0018@>\u0013\u0015") + var1 + b("m\u001d"));
                     }
                  }
               }
            }

            if (var1.repeating && !var6 && var2 < var3.length) {
               continue label64;
            }

            var10000 = var1.repeating;
         } while(var8);

         if (var10000 != 0) {
            var4.write(var7);
         }

         var4.write(var5.toByteArray());
         return var2;
      }

      throw new SnmpValueException(b("#]D\u0014M#W\u0012\u0006U8Z\\\u0012\u0001,\\@\u0018@>\u001f\u0012\u0010Y:VQ\u0001D.\u0013Q\u001d@8RQ\u0001D8@\u0012\u001aGjU]\u0007L+G\u0012R") + var1 + b("m\u0013[\u001b\u0001m") + new String(var3) + "'");
   }

   private int c(OctetFormat var1, int var2, char[] var3, ByteArrayOutputStream var4) throws SnmpValueException, IOException {
      boolean var8 = SnmpOidInfo.a;
      StringBuffer var5 = new StringBuffer();
      int var6 = 0;

      int var10000;
      while(true) {
         if (var2 < var3.length) {
            var10000 = var6;
            if (var8) {
               break;
            }

            if (var6 < var1.length) {
               char var7 = var3[var2];
               if (var7 != var1.separator && var7 != var1.terminator) {
                  var5.append(var7);
                  ++var6;
                  ++var2;
                  if (!var8) {
                     continue;
                  }
               }
            }
         }

         var4.write(SnmpFramework.stringToByteArray(var5.toString(), (SnmpOid)null));
         var10000 = var2;
         break;
      }

      return var10000;
   }

   private int d(OctetFormat var1, int var2, char[] var3, ByteArrayOutputStream var4) throws SnmpValueException, IOException {
      boolean var10 = SnmpOidInfo.a;
      long var5 = 0L;
      int var7 = var1.length * 2;
      int var8 = 0;

      int var10000;
      int var10001;
      while(true) {
         if (var2 < var3.length) {
            var10000 = var8;
            var10001 = var7;
            if (var10) {
               break;
            }

            if (var8 < var7) {
               label46: {
                  label61: {
                     char var9 = Character.toUpperCase(var3[var2]);
                     if (var9 >= '0' && var9 <= '9' || var9 >= 'A' && var9 <= 'F') {
                        var5 = var5 * 16L + (Long.decode(b("zK") + var9) & 15L);
                        ++var2;
                        if (!var10) {
                           break label61;
                        }
                     }

                     if (var9 != var1.separator) {
                        break label46;
                     }

                     var8 = 1;
                     if (!var10) {
                        break label46;
                     }
                  }

                  ++var8;
                  if (!var10) {
                     continue;
                  }
               }
            }
         }

         if (var8 > 0) {
            this.a(var4, var5, var1.length);
            if (!var10) {
               return var2;
            }
         }

         var10000 = var2;
         var10001 = var3.length;
         break;
      }

      if (var10000 < var10001) {
         throw new SnmpValueException(b("?]W\rQ/PF\u0010EjPZ\u0014S+PF\u0010Sp\u0013\u0015") + var3[var2] + "'");
      } else {
         return var2;
      }
   }

   private int e(OctetFormat var1, int var2, char[] var3, ByteArrayOutputStream var4) throws SnmpValueException, IOException {
      boolean var10 = SnmpOidInfo.a;
      long var5 = 0L;
      int var7 = (var1.length * 3 + 2) / 3;
      int var8 = 0;

      int var10000;
      int var10001;
      while(true) {
         if (var2 < var3.length) {
            var10000 = var8;
            var10001 = var7;
            if (var10) {
               break;
            }

            if (var8 < var7) {
               label37: {
                  label48: {
                     char var9 = Character.toUpperCase(var3[var2]);
                     if (var9 >= '0' && var9 <= '7') {
                        var5 = var5 * 8L + (long)(var9 - 48);
                        ++var2;
                        if (!var10) {
                           break label48;
                        }
                     }

                     if (var9 != var1.separator) {
                        break label37;
                     }

                     var8 = 1;
                     if (!var10) {
                        break label37;
                     }
                  }

                  ++var8;
                  if (!var10) {
                     continue;
                  }
               }
            }
         }

         if (var8 > 0) {
            this.a(var4, var5, var1.length);
            if (!var10) {
               return var2;
            }
         }

         var10000 = var2;
         var10001 = var3.length;
         break;
      }

      if (var10000 < var10001) {
         throw new SnmpValueException(b("?]W\rQ/PF\u0010EjPZ\u0014S+PF\u0010Sp\u0013\u0015") + var3[var2] + "'");
      } else {
         return var2;
      }
   }

   private int f(OctetFormat var1, int var2, char[] var3, ByteArrayOutputStream var4) throws SnmpValueException, IOException {
      boolean var9 = SnmpOidInfo.a;
      long var5 = 0L;
      int var7 = 0;

      int var10000;
      int var10001;
      while(true) {
         if (var2 < var3.length) {
            label46: {
               char var8 = Character.toUpperCase(var3[var2]);
               var10000 = var8;
               var10001 = 48;
               if (var9) {
                  break;
               }

               label47: {
                  if (var8 >= '0' && var8 <= '9') {
                     var5 = var5 * 10L + (long)(var8 - 48);
                     ++var2;
                     if (!var9) {
                        break label47;
                     }
                  }

                  if (var8 != var1.separator) {
                     break label46;
                  }

                  var7 = 1;
                  if (!var9) {
                     break label46;
                  }
               }

               ++var7;
               if (!var9) {
                  continue;
               }
            }
         }

         if (var7 > 0) {
            this.a(var4, var5, var1.length);
            if (!var9) {
               return var2;
            }
         }

         var10000 = var2;
         var10001 = var3.length;
         break;
      }

      if (var10000 < var10001) {
         throw new SnmpValueException(b("?]W\rQ/PF\u0010EjPZ\u0014S+PF\u0010Sp\u0013\u0015") + var3[var2] + "'");
      } else {
         return var2;
      }
   }

   private void a(ByteArrayOutputStream var1, long var2, int var4) throws IOException {
      int var5 = var4 - 1;

      while(var5 >= 0) {
         int var6 = (int)(var2 >> 8 * var5 & 255L);
         var1.write(var6);
         --var5;
         if (SnmpOidInfo.a) {
            break;
         }
      }

   }

   public String toString() {
      boolean var3 = SnmpOidInfo.a;
      if (this._format == null) {
         return "";
      } else {
         StringBuffer var1 = new StringBuffer();
         int var2 = 0;

         StringBuffer var10000;
         while(true) {
            if (var2 < this._format.length) {
               var10000 = var1.append(this._format[var2].toString());
               if (var3) {
                  break;
               }

               ++var2;
               if (!var3) {
                  continue;
               }
            }

            var10000 = var1;
            break;
         }

         return var10000.toString();
      }
   }

   private int a(OctetFormat var1, int var2, byte[] var3, StringBuffer var4) {
      boolean var12 = SnmpOidInfo.a;
      if (var2 >= var3.length) {
         return var2;
      } else {
         byte var5 = 1;
         if (var1.repeating) {
            var5 = var3[var2++];
         }

         boolean var6 = false;
         int var7 = 0;

         int var10000;
         while(true) {
            if (!var6) {
               var10000 = var7;
               if (var12) {
                  break;
               }

               if (var7 < var5) {
                  label130: {
                     long var8 = 0L;
                     if (var1.format == 'a' || var1.format == 't') {
                        Object var10 = null;
                        byte[] var14;
                        if (var1.length > var3.length - var2) {
                           var14 = new byte[var3.length - var2];
                           System.arraycopy(var3, var2, var14, 0, var14.length);
                        } else {
                           var14 = new byte[var1.length];
                           System.arraycopy(var3, var2, var14, 0, var14.length);
                        }

                        try {
                           label103: {
                              if (SnmpFramework.getUTF8Encoding() != null) {
                                 var4.append(new String(var14, SnmpFramework.getUTF8Encoding()));
                                 if (!var12) {
                                    break label103;
                                 }
                              }

                              var4.append(new String(var14));
                           }
                        } catch (Exception var13) {
                           var13.printStackTrace();
                        }

                        var2 += var14.length;
                        if (!var12) {
                           break label130;
                        }
                     }

                     int var15 = 0;

                     int var10001;
                     while(true) {
                        if (!var6) {
                           var10000 = var15;
                           var10001 = var1.length;
                           if (var12) {
                              break;
                           }

                           if (var15 < var10001) {
                              label91: {
                                 byte var11 = var3[var2++];
                                 if (var1.format == 'a') {
                                    if (var11 != 0) {
                                       var4.append((char)var11);
                                       if (!var12) {
                                          break label91;
                                       }
                                    }

                                    var2 = var3.length;
                                    if (!var12) {
                                       break label91;
                                    }
                                 }

                                 var8 = var8 * 256L + ((long)var11 & 255L);
                              }

                              ++var15;
                              if (!var12) {
                                 continue;
                              }
                           }
                        }

                        switch (var1.format) {
                           case 'x':
                              var4.append(Long.toHexString(var8));
                              if (!var12) {
                                 break;
                              }
                           case 'o':
                              var4.append(Long.toOctalString(var8));
                              if (!var12) {
                                 break;
                              }
                           case 'd':
                              var4.append(String.valueOf(var8));
                        }

                        var10000 = var2;
                        var10001 = var3.length;
                        break;
                     }

                     if (var10000 >= var10001) {
                        var6 = true;
                     }
                  }

                  if (!var6 && (var7 + 1 < var5 || var1.terminator == 0) && var1.separator != 0) {
                     var4.append(var1.separator);
                  }

                  ++var7;
                  if (!var12) {
                     continue;
                  }
               }
            }

            var10000 = var1.repeating;
            break;
         }

         if (var10000 != 0 && var1.terminator != 0) {
            var4.append(var1.terminator);
         }

         return var2;
      }
   }

   private String a(long var1, int var3) {
      boolean var8 = SnmpOidInfo.a;
      if (var3 <= 0) {
         return String.valueOf(var1);
      } else {
         boolean var4 = false;
         if (var1 < 0L) {
            var4 = true;
            var1 = -1L * var1;
         }

         StringBuffer var5 = new StringBuffer();
         int var6 = 0;

         while(true) {
            if (var6 < var3) {
               int var7 = (int)var1 % 10;
               var5.insert(0, var7);
               var1 /= 10L;
               ++var6;
               if (var8) {
                  break;
               }

               if (!var8) {
                  continue;
               }
            }

            var5.insert(0, '.');
            var5.insert(0, var1);
            break;
         }

         if (var4) {
            var5.insert(0, '-');
         }

         return var5.toString();
      }
   }

   private OctetFormat[] a(String var1) {
      DHParser var2 = new DHParser();
      return var2.a(var1);
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 74;
               break;
            case 1:
               var10003 = 51;
               break;
            case 2:
               var10003 = 50;
               break;
            case 3:
               var10003 = 117;
               break;
            default:
               var10003 = 33;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private class DHParser {
      private static final int stINIT = 1;
      private static final int stLENGTH = 2;
      private static final int stSEPARATOR = 3;
      private static final int stTERMINATOR = 4;
      private static final int stDECIMAL = 5;
      private static final int stDONE = 6;
      private int _state = 1;
      private OctetFormat _curr = null;
      private StringBuffer _len = null;
      private List _list = new Vector();

      DHParser() {
      }

      OctetFormat[] a(String var1) {
         boolean var5 = SnmpOidInfo.a;
         int var2 = 1;
         int var3 = 0;

         int var10000;
         int var4;
         while(true) {
            if (var2 != 6) {
               var10000 = var3;
               if (var5) {
                  break;
               }

               if (var3 < var1.length()) {
                  var4 = var1.charAt(var3);
                  switch (var2) {
                     case 1:
                        var2 = this.a((char)var4);
                        if (!var5) {
                           break;
                        }
                     case 2:
                        var2 = this.c((char)var4);
                        if (!var5) {
                           break;
                        }
                     case 3:
                        var2 = this.d((char)var4);
                        if (!var5) {
                           break;
                        }
                     case 4:
                        var2 = this.e((char)var4);
                        if (!var5) {
                           break;
                        }
                     case 5:
                        var2 = this.b((char)var4);
                        if (!var5) {
                           break;
                        }
                     default:
                        var2 = 6;
                  }

                  ++var3;
                  if (!var5) {
                     continue;
                  }
               }
            }

            if (this._curr != null) {
               this._list.add(this._curr);
            }

            var10000 = this._list.size();
            break;
         }

         OctetFormat[] var6 = new OctetFormat[var10000];
         var4 = 0;

         OctetFormat[] var7;
         while(true) {
            if (var4 < var6.length) {
               var7 = var6;
               if (var5) {
                  break;
               }

               var6[var4] = (OctetFormat)this._list.get(var4);
               ++var4;
               if (!var5) {
                  continue;
               }
            }

            var7 = var6;
            break;
         }

         return var7;
      }

      private int a(char var1) {
         if (this._curr != null) {
            this._list.add(this._curr);
         }

         this._curr = new OctetFormat();
         this._len = new StringBuffer();
         if (var1 == '*') {
            this._curr.repeating = true;
            return 2;
         } else if (var1 >= '0' && var1 <= '9') {
            this._len.append(var1);
            return 2;
         } else {
            switch (var1) {
               case 'B':
               case 'D':
               case 'O':
               case 'X':
               case 'b':
               case 'd':
               case 'o':
               case 'x':
                  this._curr.format = Character.toLowerCase(var1);
                  this._curr.isString = false;
                  this._curr.length = 0;
                  return 5;
               default:
                  return 1;
            }
         }
      }

      private int b(char var1) {
         if (var1 == '-') {
            return 5;
         } else if (var1 >= '0' && var1 <= '9') {
            this._len.append(var1);
            this._curr.length = Integer.parseInt(this._len.toString());
            return 5;
         } else {
            return 6;
         }
      }

      private int c(char var1) {
         if (var1 >= '0' && var1 <= '9') {
            this._len.append(var1);
            return 2;
         } else {
            label19: {
               if (this._len.length() > 0) {
                  this._curr.length = Integer.parseInt(this._len.toString());
                  if (!SnmpOidInfo.a) {
                     break label19;
                  }
               }

               this._curr.length = 0;
            }

            switch (var1) {
               case 'A':
               case 'D':
               case 'O':
               case 'X':
               case 'a':
               case 'd':
               case 'o':
               case 'x':
                  this._curr.format = Character.toLowerCase(var1);
                  return 3;
               default:
                  return 3;
            }
         }
      }

      private int d(char var1) {
         if (var1 == '*') {
            return this.a(var1);
         } else if (var1 >= '0' && var1 <= '9') {
            return this.a(var1);
         } else {
            this._curr.separator = var1;
            return 4;
         }
      }

      private int e(char var1) {
         if (var1 == '*') {
            return this.a(var1);
         } else if (var1 >= '0' && var1 <= '9') {
            return this.a(var1);
         } else {
            this._curr.terminator = var1;
            return 1;
         }
      }
   }

   private static class OctetFormat implements Serializable {
      public boolean isString;
      public boolean repeating;
      public int length;
      public char format;
      public char separator;
      public char terminator;

      private OctetFormat() {
         this.isString = true;
         this.repeating = false;
         this.length = 1;
         this.format = 'a';
         this.separator = 0;
         this.terminator = 0;
      }

      public String toString() {
         StringBuffer var1 = new StringBuffer();
         if (this.isString) {
            if (this.repeating) {
               var1.append('*');
            }

            var1.append(this.length);
            var1.append(this.format);
            if (this.separator != 0) {
               var1.append(this.separator);
            }

            if (this.terminator == 0) {
               return var1.toString();
            }

            var1.append(this.terminator);
            if (!SnmpOidInfo.a) {
               return var1.toString();
            }
         }

         var1.append(this.format);
         if (this.length > 0) {
            var1.append('-').append(this.length);
         }

         return var1.toString();
      }

      // $FF: synthetic method
      OctetFormat(Object var1) {
         this();
      }
   }
}
