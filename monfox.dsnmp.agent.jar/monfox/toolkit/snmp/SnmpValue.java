package monfox.toolkit.snmp;

import java.io.Serializable;
import java.math.BigInteger;
import monfox.toolkit.snmp.metadata.RangeItem;
import monfox.toolkit.snmp.metadata.SnmpDisplayHint;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import monfox.toolkit.snmp.metadata.SnmpOidInfo;
import monfox.toolkit.snmp.metadata.SnmpTypeInfo;
import monfox.toolkit.snmp.util.StringUtil;

public abstract class SnmpValue implements Cloneable, Serializable {
   static final long serialVersionUID = -4622445462948731120L;
   private static final String a = "$Id: SnmpValue.java,v 1.34 2011/08/30 21:54:23 sking Exp $";
   public static boolean b;

   protected SnmpValue() {
   }

   public SnmpValue cloneSnmpValue() {
      try {
         return (SnmpValue)this.clone();
      } catch (CloneNotSupportedException var2) {
         return null;
      }
   }

   public abstract String getTypeName();

   public abstract int getType();

   public SnmpOid toIndexOid() {
      return this.toIndexOid(false);
   }

   public abstract SnmpOid toIndexOid(boolean var1);

   public int fromIndexOid(SnmpOid var1, int var2) throws SnmpValueException {
      return this.fromIndexOid(var1, var2, false, -1);
   }

   public int fromIndexOid(SnmpOid var1, int var2, boolean var3) throws SnmpValueException {
      return this.fromIndexOid(var1, var2, var3, -1);
   }

   public abstract int fromIndexOid(SnmpOid var1, int var2, boolean var3, int var4) throws SnmpValueException;

   public void appendIndexOid(SnmpOid var1) {
      this.appendIndexOid(var1, false);
   }

   public abstract void appendIndexOid(SnmpOid var1, boolean var2);

   public SnmpOid toIndexOid(SnmpOid var1) {
      SnmpOid var2 = new SnmpOid(var1);
      this.appendIndexOid(var2);
      return var2;
   }

   public SnmpOid toIndexOid(SnmpOid var1, boolean var2) {
      SnmpOid var3 = new SnmpOid(var1);
      this.appendIndexOid(var3, var2);
      return var3;
   }

   public abstract long longValue();

   public boolean booleanValue() {
      return this.longValue() != 0L;
   }

   public int intValue() {
      return (int)this.longValue();
   }

   public byte byteValue() {
      return (byte)((int)this.longValue());
   }

   public String getString() {
      return this.toString();
   }

   public long[] toLongArray() {
      return new long[]{this.longValue()};
   }

   public abstract void toString(StringBuffer var1);

   public int getTypeId() {
      return this.getTag();
   }

   public String getTypeShortString() {
      return typeToShortString(this.getTag());
   }

   public String getTypeString() {
      return typeToString(this.getTag());
   }

   public static String typeToShortString(int var0) {
      switch (var0) {
         case 2:
            return "i";
         case 4:
            return "s";
         case 5:
            return "n";
         case 6:
            return "o";
         case 64:
            return a("ba");
         case 65:
            return "c";
         case 66:
            return "g";
         case 67:
            return "t";
         case 68:
            return a("da");
         case 70:
            return a("h'O");
         case 255:
            return a("n\u007f\u000f\u0006@");
         default:
            return "?";
      }
   }

   public static String typeToString(int var0) {
      switch (var0) {
         case 2:
            return a("B_/1~NC");
         case 4:
            return a("DR/1m+B/&pEV");
         case 5:
            return a("Ed\u0017\u0018");
         case 6:
            return a("DS11z_120|EE22pNC");
         case 64:
            return a("Ba:\u0010]yt\b\u0007");
         case 65:
            return a("H~\u000e\u001aMnc");
         case 66:
            return a("Lp\u000e\u0013\\");
         case 67:
            return a("_x\u0016\u0011mbr\u0010\u0007");
         case 68:
            return a("Da\u001a\u0005Ln");
         case 70:
            return a("H~\u000e\u001aMncM@");
         case 255:
            return a("N\u007f\u000f\u0006@");
         default:
            return "?";
      }
   }

   public static int stringToType(String var0) {
      String var1 = var0.toLowerCase();
      if (var1.startsWith(a("ba"))) {
         return 64;
      } else if (var1.startsWith("i")) {
         return 2;
      } else if (var1.startsWith("g")) {
         return 66;
      } else if (var1.startsWith("c") && var1.endsWith(a("=%"))) {
         return 70;
      } else if (var1.startsWith("c")) {
         return 65;
      } else if (var1.startsWith(a("da"))) {
         return 68;
      } else if (var1.startsWith("o")) {
         return 6;
      } else if (var1.startsWith("t")) {
         return 67;
      } else if (var1.startsWith("s")) {
         return 4;
      } else if (var1.startsWith("n")) {
         return 5;
      } else {
         return var1.startsWith("e") ? 255 : -1;
      }
   }

   public abstract void fromString(String var1) throws SnmpValueException;

   public static SnmpValue getInstance(SnmpOid var0, long var1) throws SnmpValueException {
      SnmpOidInfo var3 = var0.getOidInfo();
      if (var3 == null) {
         throw new SnmpValueException(a("e~[\u001bPo12\u001a_d1\u001d\u001bK11") + var0);
      } else if (!(var3 instanceof SnmpObjectInfo)) {
         throw new SnmpValueException(a("e~\u000fTXe1\u0014\u0016Snr\u000fTZgp\b\u0007\u0019dx\u001fN\u0019") + var0);
      } else {
         SnmpObjectInfo var4 = (SnmpObjectInfo)var3;
         return a(var4.getType(), var4.getTypeInfo(), (String)null, false, var0.getMetadata(), var1, var4);
      }
   }

   public static SnmpValue getInstance(int var0, long var1) throws SnmpValueException {
      return a(var0, (SnmpTypeInfo)null, (String)null, false, (SnmpMetadata)null, var1, (SnmpObjectInfo)null);
   }

   public static SnmpValue getInstance(SnmpOid var0, String var1) throws SnmpValueException {
      return getInstance(var0, var1, false);
   }

   public static SnmpValue getInstance(SnmpOid var0, String var1, boolean var2) throws SnmpValueException {
      SnmpOidInfo var3 = var0.getOidInfo();
      if (var3 == null) {
         throw new SnmpValueException(a("e~[\u001bPo12\u001a_d1\u001d\u001bK11") + var0);
      } else if (!(var3 instanceof SnmpObjectInfo)) {
         throw new SnmpValueException(a("e~\u000fTXe1\u0014\u0016Snr\u000fTZgp\b\u0007\u0019dx\u001fN\u0019") + var0);
      } else {
         SnmpObjectInfo var4 = (SnmpObjectInfo)var3;
         return a(var4.getType(), var4.getTypeInfo(), var1, var2, var0.getMetadata(), 0L, var4);
      }
   }

   public static SnmpValue getInstance(SnmpObjectInfo var0, String var1) throws SnmpValueException {
      return a(var0.getType(), var0.getTypeInfo(), var1, false, (SnmpMetadata)null, 0L, var0);
   }

   public static SnmpValue getInstance(SnmpObjectInfo var0, String var1, boolean var2) throws SnmpValueException {
      return a(var0.getType(), var0.getTypeInfo(), var1, var2, var0.getMetadata(), 0L, (SnmpObjectInfo)null);
   }

   public static SnmpValue getInstance(SnmpTypeInfo var0, String var1) throws SnmpValueException {
      return a(var0.getType(), var0, var1);
   }

   public static SnmpValue getInstance(SnmpTypeInfo var0, String var1, SnmpObjectInfo var2) throws SnmpValueException {
      return a(var0.getType(), var0, var1, false, (SnmpMetadata)null, 0L, var2);
   }

   public static SnmpValue getInstance(SnmpTypeInfo var0, String var1, SnmpObjectInfo var2, boolean var3) throws SnmpValueException {
      return a(var0.getType(), var0, var1, var3, (SnmpMetadata)null, 0L, var2);
   }

   public static SnmpValue getInstance(String var0, String var1) throws SnmpValueException {
      int var2 = stringToType(var0);
      if (var2 == -1) {
         throw new SnmpValueException(a("~\u007f\u0010\u001aV|\u007f[\u0000@{tAT") + var0);
      } else {
         return getInstance(var2, var1);
      }
   }

   public static SnmpValue getInstance(int var0, String var1) throws SnmpValueException {
      return a(var0, (SnmpTypeInfo)null, var1);
   }

   private static SnmpValue a(int var0, SnmpTypeInfo var1, String var2) throws SnmpValueException {
      return a(var0, var1, var2, false, (SnmpMetadata)null);
   }

   private static SnmpValue a(int var0, SnmpTypeInfo var1, String var2, boolean var3, SnmpMetadata var4) throws SnmpValueException {
      return a(var0, var1, var2, var3, var4, 0L, (SnmpObjectInfo)null);
   }

   private static SnmpValue a(int var0, SnmpTypeInfo var1, String var2, boolean var3, SnmpMetadata var4, long var5, SnmpObjectInfo var7) throws SnmpValueException {
      switch (var0) {
         case 2:
            if (var2 == null) {
               return new SnmpInt(var5);
            } else {
               try {
                  if (var1 != null) {
                     try {
                        long var17 = var1.namedNumberToLong(var2);
                        return new SnmpInt(var17);
                     } catch (NumberFormatException var11) {
                        return new SnmpInt(var2);
                     }
                  }

                  return new SnmpInt(var2);
               } catch (NumberFormatException var12) {
                  throw new SnmpValueException(a("b\u007f\r\u0015Ubu[\u0002Xgd\u001eT_dc[\u001b[at\u0018\u0000\u0019\u007fh\u000b\u0011\u0019") + typeToString(var0) + a("11") + var2);
               }
            }
         case 4:
            if (var2 == null) {
               return new SnmpString();
            } else {
               if (var1 != null && var1.getSmiType() == 14 && var2.startsWith("{")) {
                  byte[] var8 = var1.namedBitsStringToBytes(var2);
                  if (var8 != null) {
                     try {
                        return new SnmpString(var8);
                     } catch (RuntimeException var14) {
                     }
                  }
               }

               SnmpString var16;
               if (!var3) {
                  if (var7 == null) {
                     return new SnmpString(var2);
                  }

                  var16 = new SnmpString();
                  var16.fromJavaString(var2, var7.getOid());
                  return var16;
               } else {
                  if (var1 != null) {
                     SnmpDisplayHint var15 = var1.getSnmpDisplayHint();
                     if (var15 != null) {
                        if (var2.startsWith("'") && (var2.endsWith(a(",y")) || var2.endsWith(a(",Y")) || var2.endsWith(a(",s")) || var2.endsWith(a(",S")))) {
                           return new SnmpString(var2);
                        }

                        var2 = StringUtil.StripQuotes(var2);
                        byte[] var9 = var15.parseString(var2);
                        if (var9 != null) {
                           try {
                              return new SnmpString(var9);
                           } catch (RuntimeException var13) {
                           }
                        }
                     }
                  }

                  if (var7 == null) {
                     return new SnmpString(var2);
                  }

                  var16 = new SnmpString();
                  var16.fromJavaString(var2, var7.getOid());
                  return var16;
               }
            }
         case 5:
            return new SnmpNull();
         case 6:
            if (var2 == null) {
               return new SnmpOid(var4, a(";?J"));
            }

            return new SnmpOid(var4, var2);
         case 64:
            if (var2 == null) {
               return new SnmpIpAddress();
            }

            return new SnmpIpAddress(var2);
         case 65:
            if (var2 == null) {
               return new SnmpCounter(var5);
            }

            return new SnmpCounter(var2);
         case 66:
            if (var2 == null) {
               return new SnmpGauge(var5);
            }

            return new SnmpGauge(var2);
         case 67:
            if (var2 == null) {
               return new SnmpTimeTicks(var5);
            }

            return new SnmpTimeTicks(var2);
         case 68:
            if (var2 == null) {
               return new SnmpOpaque();
            }

            return new SnmpOpaque(var2);
         case 70:
            if (var2 == null) {
               return new SnmpCounter64(var5);
            }

            return new SnmpCounter64(var2);
         default:
            throw new SnmpValueException(a("~\u007f\u0010\u001aV|\u007f[\u0000@{tAT") + var0);
      }
   }

   public static boolean validate(SnmpOid var0, SnmpValue var1) throws SnmpValueException {
      SnmpOidInfo var2 = var0.getOidInfo();
      if (var0 == null) {
         throw new SnmpValueException(a("e~[\u001bPo12\u001a_d1\u001d\u001bK11") + var0);
      } else if (!(var2 instanceof SnmpObjectInfo)) {
         throw new SnmpValueException(a("e~\u000fTXe1\u0014\u0016Snr\u000fTZgp\b\u0007\u0019dx\u001fN\u0019") + var0);
      } else {
         SnmpObjectInfo var3 = (SnmpObjectInfo)var2;
         return validate(var3.getType(), var1);
      }
   }

   public static boolean validate(SnmpObjectInfo var0, SnmpValue var1) {
      return validate(var0.getType(), var1);
   }

   public static boolean validate(int var0, SnmpValue var1) {
      return var0 == var1.getType();
   }

   public static int validate(SnmpTypeInfo var0, SnmpValue var1) {
      int var2 = var0.getType();
      if (var2 != var1.getType()) {
         return 7;
      } else {
         if (var0.hasRangeSpec()) {
            boolean var4;
            label43: {
               RangeItem[] var3 = var0.getRangeSpec();
               var4 = true;
               if (var2 == 2 || var2 == 66) {
                  var4 = a(var3, var1.longValue());
                  if (!b) {
                     break label43;
                  }
               }

               if (var2 == 4 || var2 == 68) {
                  var4 = a(var3, (long)var1.getByteArray().length);
               }
            }

            if (!var4) {
               return 10;
            }
         }

         return var2 == 2 && var0.getNumberToNameMap() != null && var0.intToNamedNumber(var1.longValue()) == null ? 10 : 0;
      }
   }

   private static boolean a(RangeItem[] var0, long var1) {
      boolean var4 = b;
      int var3 = 0;

      boolean var10000;
      while(true) {
         if (var3 < var0.length) {
            var10000 = var0[var3].isSingle();
            if (var4) {
               break;
            }

            if (var10000) {
               if (var0[var3].getSingleValue() == var1) {
                  return true;
               }
            } else if (var0[var3].getLowerValue() <= var1 && var0[var3].getUpperValue() >= var1) {
               return true;
            }

            ++var3;
            if (!var4) {
               continue;
            }
         }

         var10000 = false;
         break;
      }

      return var10000;
   }

   public String toString(SnmpOid var1) {
      StringBuffer var2 = new StringBuffer();
      this.toString(var2, var1, false);
      return var2.toString();
   }

   public void toString(StringBuffer var1, SnmpOid var2, boolean var3) {
      if (var2 != null && var2.getOidInfo() != null && var2.getOidInfo() instanceof SnmpObjectInfo) {
         SnmpObjectInfo var5 = (SnmpObjectInfo)var2.getOidInfo();
         this.toString(var1, var5, var3);
         if (!b) {
            return;
         }
      }

      this.toString(var1, (SnmpObjectInfo)null, var3);
   }

   public void toString(StringBuffer var1, SnmpObjectInfo var2, boolean var3) {
      boolean var10 = b;
      if (var2 != null) {
         label57: {
            SnmpTypeInfo var5 = var2.getTypeInfo();
            if (var5 != null) {
               label54: {
                  SnmpDisplayHint var6 = var5.getSnmpDisplayHint();
                  if (this instanceof SnmpInt) {
                     long var7 = this.longValue();
                     String var9 = var5.intToNamedNumber(var7);
                     if (var9 != null) {
                        var1.append(var9);
                        if (!var3) {
                           var1.append("(").append(var7).append(")");
                        }

                        return;
                     }

                     if (var6 != null) {
                        var6.format(var1, var7);
                        return;
                     }

                     if (!var10) {
                        break label54;
                     }
                  }

                  if (this.getByteArray() != null) {
                     if (var5.getSmiType() == 14 && (var5.getNameToNumberMap() != null || var6 == null)) {
                        var5.bytesToNamedBitsString(var1, this.getByteArray());
                        return;
                     }

                     if (var6 != null) {
                        var6.format(var1, this.getByteArray());
                        return;
                     }
                  }
               }

               if (!var10) {
                  break label57;
               }
            }

            if (var2.getSmiType() == 14 && this instanceof SnmpString) {
               var1.append(((SnmpString)this).toHexString());
               return;
            }
         }

         if (this instanceof SnmpString) {
            ((SnmpString)this).a(var1, var2.getOid());
            return;
         }
      }

      this.toString(var1);
   }

   public void toString(StringBuffer var1, SnmpTypeInfo var2, boolean var3) {
      SnmpDisplayHint var4 = var2.getSnmpDisplayHint();
      if (this instanceof SnmpInt) {
         long var5 = this.longValue();
         String var7 = var2.intToNamedNumber(var5);
         if (var7 != null) {
            var1.append(var7);
            if (!var3) {
               var1.append("(").append(var5).append(")");
            }

            return;
         }

         if (var4 != null) {
            var4.format(var1, var5);
            return;
         }

         if (!b) {
            return;
         }
      }

      if (this.getByteArray() != null) {
         if (var2.getSmiType() == 14 && (var2.getNameToNumberMap() != null || var4 == null)) {
            var2.bytesToNamedBitsString(var1, this.getByteArray());
            return;
         }

         if (var4 != null) {
            var4.format(var1, this.getByteArray());
            return;
         }
      }

   }

   public int getTag() {
      return -1;
   }

   public int getCoder() {
      return -1;
   }

   public BigInteger getBigInteger() {
      return BigInteger.valueOf(this.getLongValue());
   }

   public long getLongValue() {
      return 0L;
   }

   public byte[] getByteArray() {
      return null;
   }

   public long[] getLongArray() {
      return null;
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
               var10003 = 17;
               break;
            case 2:
               var10003 = 123;
               break;
            case 3:
               var10003 = 116;
               break;
            default:
               var10003 = 57;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
