package monfox.toolkit.snmp;

import java.util.Hashtable;

public class Snmp {
   public static final int snmpV1 = 0;
   public static final int snmpV2 = 1;
   public static final int snmpV3 = 3;
   public static final int securityV1 = 1;
   public static final int securityV2C = 2;
   public static final int securityUSM = 3;
   public static final int securityAny = 0;
   public static final byte noAuthNoPriv = 0;
   public static final byte authNoPriv = 1;
   public static final byte authPriv = 3;
   public static final int MD5 = 0;
   public static final SnmpOid MD5_AUTH_PROTOCOL_OID = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 10L, 1L, 1L, 2L});
   public static final int SHA = 1;
   public static final SnmpOid SHA_AUTH_PROTOCOL_OID = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 10L, 1L, 1L, 3L});
   public static final int DES = 2;
   public static final SnmpOid DES_PRIV_PROTOCOL_OID = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 10L, 1L, 2L, 2L});
   public static final int AES128 = 4;
   public static final SnmpOid AES128_PRIV_PROTOCOL_OID = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 10L, 1L, 2L, 4L});
   public static final int AES192 = 5;
   public static final SnmpOid AES192_PRIV_PROTOCOL_OID = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 10L, 1L, 2L, 4L, 3L});
   public static final int AES256 = 6;
   public static final SnmpOid AES256_PRIV_PROTOCOL_OID = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 10L, 1L, 2L, 4L, 4L});
   public static final int TDES = 14832;
   public static final int TripleDES = 14832;
   public static final SnmpOid TDES_PRIV_PROTOCOL_OID = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 4L, 1L, 14832L, 1L, 1L});
   public static final int snmpGet = 160;
   public static final int snmpGetNext = 161;
   public static final int snmpResponse = 162;
   public static final int snmpSet = 163;
   public static final int snmpV1Trap = 164;
   public static final int snmpGetBulk = 165;
   public static final int snmpInform = 166;
   public static final int snmpV2Trap = 167;
   public static final int snmpReport = 168;
   public static final int accessNotAccessible = 0;
   public static final int accessReadOnly = 1;
   public static final int accessWriteOnly = 2;
   public static final int accessReadWrite = 3;
   public static final int accessReadCreate = 5;
   public static final int accessAccessibleForNotify = 8;
   public static final int accessNotImplemented = 16;
   public static final int smiInteger = 1;
   public static final int smiInteger32 = 2;
   public static final int smiUnsigned32 = 3;
   public static final int smiCounter = 4;
   public static final int smiCounter32 = 5;
   public static final int smiCounter64 = 6;
   public static final int smiGauge = 7;
   public static final int smiGauge32 = 8;
   public static final int smiIpAddress = 9;
   public static final int smiTimeTicks = 10;
   public static final int smiOpaque = 11;
   public static final int smiOid = 12;
   public static final int smiString = 13;
   public static final int smiBits = 14;
   public static final int snmpInteger = 2;
   public static final int snmpString = 4;
   public static final int snmpNull = 5;
   public static final int snmpOid = 6;
   public static final int snmpIpAddress = 64;
   public static final int snmpCounter = 65;
   public static final int snmpGauge = 66;
   public static final int snmpTimeTicks = 67;
   public static final int snmpOpaque = 68;
   public static final int snmpCounter64 = 70;
   public static final int snmpEntry = 255;
   public static final int snmpNoSuchObject = 128;
   public static final int snmpNoSuchInstance = 129;
   public static final int snmpEndOfMibView = 130;
   public static final int UDP = 1;
   public static final int TCP = 2;
   public static final SnmpOid transportDomainUdpIpv4 = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 2L, 1L, 100L, 1L, 1L});
   public static final SnmpOid transportDomainUdpIpv6 = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 2L, 1L, 100L, 1L, 2L});
   public static final SnmpOid transportDomainTcpIpv4 = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 2L, 1L, 100L, 1L, 5L});
   public static final SnmpOid transportDomainTcpIpv6 = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 2L, 1L, 100L, 1L, 6L});
   public static final SnmpOid snmpUDPDomain = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 1L, 1L});
   public static final SnmpOid snmpTCPDomain = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 1L, 6L});
   public static final SnmpOid monfoxUndefinedDomain = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 4L, 1L, 3817L, 100L, 1L, 1L});
   public static final int noError = 0;
   public static final int tooBig = 1;
   public static final int noSuchName = 2;
   public static final int badValue = 3;
   public static final int readOnly = 4;
   public static final int genErr = 5;
   public static final int noAccess = 6;
   public static final int wrongType = 7;
   public static final int wrongLength = 8;
   public static final int wrongEncoding = 9;
   public static final int wrongValue = 10;
   public static final int noCreation = 11;
   public static final int inconsistentValue = 12;
   public static final int resourceUnavailable = 13;
   public static final int commitFailed = 14;
   public static final int undoFailed = 15;
   public static final int authorizationError = 16;
   public static final int notWritable = 17;
   public static final int inconsistentName = 18;
   public static final int timeoutConstant = 1;
   public static final int timeoutLinear = 2;
   public static final int timeoutExponential = 3;
   public static final int timeoutAdaptive = 4;
   public static final int timeoutDefault = -1;
   public static final int coldStart = 0;
   public static final int warmStart = 1;
   public static final int linkDown = 2;
   public static final int linkUp = 3;
   public static final int authenticationFailure = 4;
   public static final int egpNeighborLoss = 5;
   public static final int enterpriseSpecific = 6;
   private static String[] a = new String[]{a("S\u0000\t~RD\u000e\u0017n"), a("G\u000e\u0017wRD\u000e\u0017n"), a("\\\u0006\u000bqE_\u0018\u000b"), a("\\\u0006\u000bqT@"), a("Q\u001a\u0011rd^\u001b\fy`D\u0006\ntGQ\u0006\tosU"), a("U\b\u0015TdY\b\rxnB#\nir"), a("U\u0001\u0011\u007fs@\u001d\fidc\u001f\u0000yhV\u0006\u0006")};
   public static int DEFAULT_MAX_SIZE = 65535;
   public static int DEFAULT_RECEIVE_BUFFER_SIZE = 65535;
   public static int DEFAULT_RECEIVE_BUFFER_PRIORITY = 7;
   private static String[] b = new String[]{a("^\u0000 hs_\u001d"), a("D\u0000\nXhW"), a("^\u00006obX!\u0004wd"), a("R\u000e\u0001L`\\\u001a\u0000"), a("B\n\u0004~N^\u0003\u001c"), a("W\n\u000b_sB"), a("^\u0000$ybU\u001c\u0016"), a("G\u001d\ntfd\u0016\u0015\u007f"), a("G\u001d\ntf|\n\u000b}uX"), a("G\u001d\ntfu\u0001\u0006ueY\u0001\u0002"), a("G\u001d\ntff\u000e\tod"), a("^\u0000&hdQ\u001b\fuo"), a("Y\u0001\u0006uoC\u0006\u0016nd^\u001b3{mE\n"), a("B\n\u0016utB\f\u0000OoQ\u0019\u0004smQ\r\t\u007f"), a("S\u0000\bwhD)\u0004smU\u000b"), a("E\u0001\u0001uGQ\u0006\t\u007fe"), a("Q\u001a\u0011rnB\u0006\u001f{uY\u0000\u000b_sB\u0000\u0017"), a("^\u0000\u0011MsY\u001b\u0004xmU"), a("Y\u0001\u0006uoC\u0006\u0016nd^\u001b+{lU")};
   private static Hashtable c = new Hashtable();
   private static final String d = "$Id: Snmp.java,v 1.30 2014/07/28 13:44:09 sking Exp $";

   public static String versionToString(int var0) {
      switch (var0) {
         case 0:
            return a("F^");
         case 1:
            return a("F]");
         case 2:
         default:
            return a("E\u0001\u000etnG\u0001");
         case 3:
            return a("F\\");
      }
   }

   public static int getAuthProto(String var0) {
      if (var0 == null) {
         return 0;
      } else if (var0.trim().equalsIgnoreCase(a("}+P"))) {
         return 0;
      } else {
         return var0.trim().equalsIgnoreCase(a("c'$")) ? 1 : -1;
      }
   }

   public static String authProtoToString(int var0) {
      switch (var0) {
         case 0:
            return a("}+P");
         case 1:
            return a("c'$");
         default:
            return a("e!.TNg!M") + var0 + ")";
      }
   }

   public static String privProtoToString(int var0) {
      switch (var0) {
         case 2:
            return a("t*6");
         case 4:
            return a("q*6+3\b");
         case 5:
            return a("q*6+8\u0002");
         case 6:
            return a("q*6(4\u0006");
         case 14832:
            return a("\u0003+ I");
         default:
            return a("e!.TNg!M") + var0 + ")";
      }
   }

   public static int getPrivProto(String var0) {
      if (var0 == null) {
         return 2;
      } else if (var0.trim().equalsIgnoreCase(a("t*6"))) {
         return 2;
      } else if (var0.trim().equalsIgnoreCase(a("\u0003+ I"))) {
         return 14832;
      } else if (var0.trim().equalsIgnoreCase(a("d+ I"))) {
         return 14832;
      } else if (var0.trim().equalsIgnoreCase(a("q*6"))) {
         return 4;
      } else if (var0.trim().equalsIgnoreCase(a("q*6+3\b"))) {
         return 4;
      } else if (var0.trim().equalsIgnoreCase(a("q*6+8\u0002"))) {
         return 5;
      } else {
         return var0.trim().equalsIgnoreCase(a("q*W/7")) ? 6 : 2;
      }
   }

   public static String securityModelToString(int var0) {
      switch (var0) {
         case 0:
            return a("q\u0001\u001c");
         case 1:
            return a("c!(Jw\u0001");
         case 2:
            return a("c!(Jw\u0002\f");
         case 3:
            return a("e<(");
         default:
            return a("E\u0001\u000etnG\u0001");
      }
   }

   public static String securityLevelToString(int var0) {
      switch (var0 & 3) {
         case 0:
            return a("^\u0000$ouX!\nJsY\u0019");
         case 1:
            return a("Q\u001a\u0011rO_?\u0017sw");
         case 2:
         default:
            return a("E\u0001\u000etnG\u0001");
         case 3:
            return a("Q\u001a\u0011rQB\u0006\u0013");
      }
   }

   public static String errorStatusToString(int var0) {
      return var0 >= 0 && var0 <= 18 ? b[var0] : String.valueOf(var0);
   }

   public static String genericTrapToString(int var0) {
      return var0 >= 0 && var0 <= 6 ? a[var0] : a("^\u00006obX(\u0000tdB\u0006\u0006NsQ\u001f");
   }

   public static int stringToSmiType(String var0) {
      Integer var1 = (Integer)c.get(var0);
      return var1 != null ? var1 : -1;
   }

   public static String smiTypeToShortString(int var0) {
      boolean var2 = SnmpValue.b;
      String var1 = a("~:)V");
      switch (var0) {
         case 13:
            var1 = "s";
            if (!var2) {
               break;
            }
         case 1:
            var1 = "i";
            if (!var2) {
               break;
            }
         case 2:
            var1 = a("Y\\W");
            if (!var2) {
               break;
            }
         case 3:
            var1 = a("E\\W");
            if (!var2) {
               break;
            }
         case 4:
            var1 = "c";
            if (!var2) {
               break;
            }
         case 5:
            var1 = a("S\\W");
            if (!var2) {
               break;
            }
         case 6:
            var1 = a("SYQ");
            if (!var2) {
               break;
            }
         case 7:
            var1 = "g";
            if (!var2) {
               break;
            }
         case 8:
            var1 = a("W\\W");
            if (!var2) {
               break;
            }
         case 9:
            var1 = a("Y\u001f");
            if (!var2) {
               break;
            }
         case 10:
            var1 = "t";
            if (!var2) {
               break;
            }
         case 11:
            var1 = a("_\u001f");
            if (!var2) {
               break;
            }
         case 12:
            var1 = "o";
            if (!var2) {
               break;
            }
         case 14:
            var1 = "b";
            if (!var2) {
               break;
            }
         default:
            var1 = a("U\u0001\u0011hx");
      }

      return var1;
   }

   public static String smiTypeToString(int var0) {
      boolean var2 = SnmpValue.b;
      String var1 = a("~:)V");
      switch (var0) {
         case 13:
            var1 = a("\u007f,1_U\u0010<1HH~(");
            if (!var2) {
               break;
            }
         case 1:
            var1 = a("y!1_Fu=");
            if (!var2) {
               break;
            }
         case 2:
            var1 = a("y\u0001\u0011\u007ffU\u001dV(");
            if (!var2) {
               break;
            }
         case 3:
            var1 = a("e\u0001\u0016sf^\n\u0001)3");
            if (!var2) {
               break;
            }
         case 4:
            var1 = a("s\u0000\u0010tuU\u001d");
            if (!var2) {
               break;
            }
         case 5:
            var1 = a("s\u0000\u0010tuU\u001dV(");
            if (!var2) {
               break;
            }
         case 6:
            var1 = a("s\u0000\u0010tuU\u001dS.");
            if (!var2) {
               break;
            }
         case 7:
            var1 = a("w\u000e\u0010}d");
            if (!var2) {
               break;
            }
         case 8:
            var1 = a("w\u000e\u0010}d\u0003]");
            if (!var2) {
               break;
            }
         case 9:
            var1 = a("y\u001f$~eB\n\u0016i");
            if (!var2) {
               break;
            }
         case 10:
            var1 = a("d\u0006\b\u007fUY\f\u000ei");
            if (!var2) {
               break;
            }
         case 11:
            var1 = a("\u007f\u001f\u0004ktU");
            if (!var2) {
               break;
            }
         case 14:
            var1 = a("r&1I");
            if (!var2) {
               break;
            }
         case 12:
            var1 = a("\u007f-/_BdO,^D~;,\\Hu=");
            if (!var2) {
               break;
            }
         default:
            var1 = a("~:)V");
      }

      return var1;
   }

   static {
      c.put(a("\u007f,1_U\u0010<1HH~("), new Integer(13));
      c.put(a("y!1_Fu="), new Integer(1));
      c.put(a("y\u0001\u0011\u007ffU\u001dV("), new Integer(2));
      c.put(a("e\u0001\u0016sf^\n\u0001)3"), new Integer(3));
      c.put(a("s\u0000\u0010tuU\u001d"), new Integer(4));
      c.put(a("s\u0000\u0010tuU\u001dV("), new Integer(5));
      c.put(a("s\u0000\u0010tuU\u001dS."), new Integer(6));
      c.put(a("w\u000e\u0010}d"), new Integer(7));
      c.put(a("w\u000e\u0010}d\u0003]"), new Integer(8));
      c.put(a("y\u001f$~eB\n\u0016i"), new Integer(9));
      c.put(a("d\u0006\b\u007fUY\f\u000ei"), new Integer(10));
      c.put(a("\u007f\u001f\u0004ktU"), new Integer(11));
      c.put(a("\u007f-/_BdO,^D~;,\\Hu="), new Integer(12));
      c.put(a("r&1I"), new Integer(14));
      c.put("s", new Integer(13));
      c.put("i", new Integer(1));
      c.put(a("Y\\W"), new Integer(2));
      c.put(a("E\\W"), new Integer(3));
      c.put("c", new Integer(4));
      c.put(a("S\\W"), new Integer(5));
      c.put(a("SYQ"), new Integer(6));
      c.put("g", new Integer(7));
      c.put(a("W\\W"), new Integer(8));
      c.put(a("Y\u001f"), new Integer(9));
      c.put("t", new Integer(10));
      c.put(a("_\u001f"), new Integer(11));
      c.put("o", new Integer(12));
      c.put("b", new Integer(14));
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 48;
               break;
            case 1:
               var10003 = 111;
               break;
            case 2:
               var10003 = 101;
               break;
            case 3:
               var10003 = 26;
               break;
            default:
               var10003 = 1;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
