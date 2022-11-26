package monfox.toolkit.snmp.agent.usm;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.math.BigInteger;
import java.util.StringTokenizer;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.SnmpMibException;

public class UsmDH extends Usm {
   public static final SnmpOid usmDHParameters = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 3L, 101L, 1L, 1L, 1L});
   public static final SnmpOid usmDHUserKeyTable = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 3L, 101L, 1L, 1L, 2L});
   public static final SnmpOid usmDHUserAuthKeyChange = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 3L, 101L, 1L, 1L, 2L, 1L, 1L});
   public static final SnmpOid usmDHUserOwnAuthKeyChange = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 3L, 101L, 1L, 1L, 2L, 1L, 2L});
   public static final SnmpOid usmDHUserPrivKeyChange = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 3L, 101L, 1L, 1L, 2L, 1L, 3L});
   public static final SnmpOid usmDHUserOwnPrivKeyChange = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 3L, 101L, 1L, 1L, 2L, 1L, 4L});
   public static final SnmpOid usmDHKickstartTable = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 3L, 101L, 1L, 2L, 1L});
   public static final SnmpOid usmDHKickstartIndex = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 3L, 101L, 1L, 2L, 1L, 1L, 1L});
   public static final SnmpOid usmDHKickstartMyPublic = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 3L, 101L, 1L, 2L, 1L, 1L, 2L});
   public static final SnmpOid usmDHKickstartMgrPublic = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 3L, 101L, 1L, 2L, 1L, 1L, 3L});
   public static final SnmpOid usmDHKickstartSecurityName = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 3L, 101L, 1L, 2L, 1L, 1L, 4L});
   private UsmDHParameters a;
   private UsmDHUserKeyTable b = new UsmDHUserKeyTable(this.getUserTable());
   private UsmDHKickstartTable c;

   public UsmDH(SnmpAgent var1) throws SnmpException {
      super(var1);
      var1.getMib().add(this.b, true);
      this.c = new UsmDHKickstartTable(this);
      var1.getMib().add(this.c, true);
      this.a = new UsmDHParameters(usmDHParameters, this.b);
      var1.getMib().add(this.a, true);
   }

   public void loadKickstartTable(String var1) throws IOException, SnmpMibException {
      boolean var16 = Usm.q;
      FileReader var2 = new FileReader(var1);
      LineNumberReader var3 = new LineNumberReader(var2);
      String var4 = null;

      while((var4 = var3.readLine()) != null) {
         var4 = var4.trim();
         StringTokenizer var5 = new StringTokenizer(var4, a("0Z\b"), false);
         int var6 = var5.countTokens();
         if (var6 != 0) {
            label148: {
               String var7;
               if (var6 != 4) {
                  var7 = var5.nextToken();
                  if (!var7.startsWith(a("?Y")) && !var7.startsWith("#")) {
                     throw new IOException(a("8\u001a[aM0") + var3.getLineNumber() + a("9L\u0012fFf\u0017^fL0\u0010]}Eq\u0002") + var4);
                  }

                  if (!var16) {
                     break label148;
                  }
               }

               String var9;
               String var10;
               byte var19;
               label144: {
                  var7 = var5.nextToken();
                  String var8 = var5.nextToken();
                  var9 = var5.nextToken();
                  var10 = var5.nextToken();
                  boolean var11 = false;
                  boolean var12 = true;
                  if (var8.equalsIgnoreCase(a("]2\u0007"))) {
                     var19 = 0;
                     if (!var16) {
                        break label144;
                     }
                  }

                  if (var8.equalsIgnoreCase(a("C>s"))) {
                     var19 = 1;
                     if (!var16) {
                        break label144;
                     }
                  }

                  if (!var8.equals("-")) {
                     throw new IOException(a("8\u001a[aM0") + var3.getLineNumber() + a("9L\u0012fFf\u0017^fL0\u0017G{@=\u0006@`\\\u007fL\u0012") + var8);
                  }

                  var19 = -1;
                  if (var16) {
                     throw new IOException(a("8\u001a[aM0") + var3.getLineNumber() + a("9L\u0012fFf\u0017^fL0\u0017G{@=\u0006@`\\\u007fL\u0012") + var8);
                  }
               }

               short var20;
               label145: {
                  if (var9.equalsIgnoreCase(a("T3a"))) {
                     var20 = 2;
                     if (!var16) {
                        break label145;
                     }
                  }

                  if (var9.equalsIgnoreCase(a("Q3a"))) {
                     var20 = 4;
                     if (!var16) {
                        break label145;
                     }
                  }

                  if (var9.equalsIgnoreCase(a("Q3a\"\u0019\"N"))) {
                     var20 = 4;
                     if (!var16) {
                        break label145;
                     }
                  }

                  if (var9.equalsIgnoreCase(a("Q3a>\u001a("))) {
                     var20 = 4;
                     if (!var16) {
                        break label145;
                     }
                  }

                  if (var9.equalsIgnoreCase(a("Q3a\"\u0019)D"))) {
                     var20 = 5;
                     if (!var16) {
                        break label145;
                     }
                  }

                  if (var9.equalsIgnoreCase(a("Q3a>\u0011\""))) {
                     var20 = 5;
                     if (!var16) {
                        break label145;
                     }
                  }

                  if (var9.equalsIgnoreCase(a("Q3a=\u001d&"))) {
                     var20 = 6;
                     if (!var16) {
                        break label145;
                     }
                  }

                  if (var9.equalsIgnoreCase(a("Q3a\"\u001a%@"))) {
                     var20 = 6;
                     if (!var16) {
                        break label145;
                     }
                  }

                  if (var9.equalsIgnoreCase(a("#2w\\"))) {
                     var20 = 14832;
                     if (!var16) {
                        break label145;
                     }
                  }

                  if (var9.equalsIgnoreCase(a("D2w\\"))) {
                     var20 = 14832;
                     if (!var16) {
                        break label145;
                     }
                  }

                  if (var9.equals("-")) {
                     var20 = -1;
                     if (!var16) {
                        break label145;
                     }
                  }

                  throw new IOException(a("8\u001a[aM0") + var3.getLineNumber() + a("9L\u0012fFf\u0017^fL0\u0006@f^=\u0006@`\\\u007fL\u0012") + var9);
               }

               BigInteger var13 = null;
               if (!var10.startsWith(a(" \u000e")) && !var10.startsWith("x")) {
                  var13 = new BigInteger(var10);
               } else {
                  try {
                     SnmpString var14 = new SnmpString();
                     var14.fromHexString(var10);
                     byte[] var15 = var14.toByteArray();
                     var13 = new BigInteger(1, var15);
                  } catch (Exception var18) {
                     throw new IOException(a("8\u001a[aM0") + var3.getLineNumber() + a("9L\u0012fFf\u0017^fL0\u001bU}\u0005`\u0003PcAsVDnDe\u0013\b/") + var10);
                  }
               }

               if (var19 >= 0 && var20 >= 0) {
                  try {
                     this.addKickstartEntry(var7, var13, var19, var20);
                  } catch (Exception var17) {
                     throw new SnmpMibException(a("8\u001a[aM0") + var3.getLineNumber() + a("9L\u0012") + var17.getMessage());
                  }
               } else {
                  this.addKickstartEntry(var7, var13);
               }
            }
         }

         if (var16) {
            break;
         }
      }

   }

   public void addKickstartEntry(String var1, BigInteger var2) throws SnmpMibException {
      this.c.a(var1, var2);
   }

   public void addKickstartEntry(String var1, BigInteger var2, int var3, int var4) throws SnmpMibException, SnmpValueException {
      this.c.a(var1, var2, var3, var4);
   }

   public UsmDHParameters getUsmDHParameters() {
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
               var10003 = 16;
               break;
            case 1:
               var10003 = 118;
               break;
            case 2:
               var10003 = 50;
               break;
            case 3:
               var10003 = 15;
               break;
            default:
               var10003 = 40;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
