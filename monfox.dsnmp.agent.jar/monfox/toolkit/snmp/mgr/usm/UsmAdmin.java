package monfox.toolkit.snmp.mgr.usm;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import monfox.log.Logger;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.ber.BERBuffer;
import monfox.toolkit.snmp.ber.BERCoder;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.engine.SnmpMessageProfile;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.mgr.SnmpErrorException;
import monfox.toolkit.snmp.mgr.SnmpParameters;
import monfox.toolkit.snmp.mgr.SnmpPeer;
import monfox.toolkit.snmp.mgr.SnmpResponseListener;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.mgr.SnmpTimeoutException;
import monfox.toolkit.snmp.util.CryptoUtil;
import monfox.toolkit.snmp.util.PBKDF2;
import monfox.toolkit.snmp.v3.usm.USMAuthProtocolSpec;
import monfox.toolkit.snmp.v3.usm.USMLocalizedUserData;
import monfox.toolkit.snmp.v3.usm.USMPrivProtocolSpec;
import monfox.toolkit.snmp.v3.usm.USMUser;
import monfox.toolkit.snmp.v3.usm.USMUtil;

public class UsmAdmin {
   static final int a = 1;
   static final int b = 2;
   public static final int AUTH_PROTOCOL_SHA = 1;
   public static final int AUTH_PROTOCOL_MD5 = 0;
   public static final int AUTH_PROTOCOL_UNKNOWN = -1;
   public static final int PRIV_PROTOCOL_UNKNOWN = -1;
   static byte[] c = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -55, 15, -38, -94, 33, 104, -62, 52, -60, -58, 98, -117, -128, -36, 28, -47, 41, 2, 78, 8, -118, 103, -52, 116, 2, 11, -66, -90, 59, 19, -101, 34, 81, 74, 8, 121, -114, 52, 4, -35, -17, -107, 25, -77, -51, 58, 67, 27, 48, 43, 10, 109, -14, 95, 20, 55, 79, -31, 53, 109, 109, 81, -62, 69, -28, -123, -75, 118, 98, 94, 126, -58, -12, 76, 66, -23, -90, 55, -19, 107, 11, -1, 92, -74, -12, 6, -73, -19, -18, 56, 107, -5, 90, -119, -97, -91, -82, -97, 36, 17, 124, 75, 31, -26, 73, 40, 102, 81, -20, -26, 83, -127, -1, -1, -1, -1, -1, -1, -1, -1};
   static BigInteger d;
   static BigInteger e;
   static int f;
   static byte[] g;
   static byte[] h;
   private static final SnmpOid i;
   private static final SnmpOid j;
   private static final SnmpOid k;
   private SnmpSession l = null;
   private SnmpMetadata m = null;
   private byte[] n;
   private byte[] o;
   private Logger p;

   UsmAdmin(SnmpSession var1) throws SnmpException {
      this.n = h;
      this.o = g;
      this.p = null;
      this.l = var1;
      this.p = Logger.getInstance(a("V2H%\u000fn(K"));
      this.m = this.a(this.l.getMetadata());
      if (this.m == null) {
         this.m = this.a(SnmpFramework.getMetadata());
      }

      if (this.m == null) {
         try {
            this.m = SnmpFramework.loadMibs(a("P\u000fh4FV\u0012`6FA\u0000v!/.\u0012hI&J\u0003\u001f7%N\u0011\b18Nla,FL\u0003o!(W\u0012\b)\"A"));
         } catch (Exception var3) {
            throw new SnmpException(a("` K\n\u0004waI\u000b\ngap7&#\fl&"));
         }
      }

   }

   private SnmpMetadata a(SnmpMetadata var1) {
      if (var1 == null) {
         return null;
      } else {
         try {
            if (var1.getModule(a("P\u000fh4FV\u0012`6FA\u0000v!/.\u0012hI&J\u0003")) != null && var1.getModule(a("P\u000fh4FV\u0012hI/Klj&!F\u0002q7FN\bg")) != null) {
               return var1;
            }
         } catch (SnmpException var3) {
         }

         return null;
      }
   }

   public void createUser(SnmpPeer var1, String var2, String var3) throws SnmpException {
      this.a(var1, var2, var3, false);
   }

   private void a(SnmpPeer var1, String var2, String var3, boolean var4) throws SnmpException {
      boolean var15 = Usm.M;
      if (this.p.isDebugEnabled()) {
         this.p.debug(a("`3@\u0005\u001ff\u0014V\u0001\u0019+4V\u0001\u0019>") + var2 + a("/\"I\u000b\u0005f\u0007W\u000b\u0006>") + var3);
      }

      byte var5 = 10;
      int var6 = 1;

      while(true) {
         long var7 = -1L;

         try {
            SnmpVarBindList var9;
            UsmAdmin var10000;
            label82: {
               var9 = new SnmpVarBindList(this.m);
               if (var1.getSnmpEngineID() == null) {
                  var10000 = this;
                  if (var15) {
                     break label82;
                  }

                  this.l.performDiscovery(var1, (SnmpResponseListener)null);
               }

               if (var1.getSnmpEngineID() == null) {
                  throw new SnmpException(a("m.\u0005\u0001\u0005d(K\u0001Kj%"));
               }

               var10000 = this;
            }

            var7 = var10000.a(var1);
            if (var7 > -1L) {
               var9.add(a("v2H1\u0018f3v\u0014\u0002m\rJ\u0007\u0000-q"), var7);
            }

            SnmpString var11;
            SnmpString var12;
            label58: {
               SnmpEngineID var10 = var1.getSnmpEngineID();
               var11 = new SnmpString(var10.getValue());
               var12 = new SnmpString(var2);
               if (var4) {
                  var9.add(a("v2H1\u0018f3v\u0010\nw4V"), a("`3@\u0005\u001ff\u0000K\u0000<b(Q"));
                  if (!var15) {
                     break label58;
                  }
               }

               var9.add(a("v2H1\u0018f3v\u0010\nw4V"), a("`3@\u0005\u001ff\u0000K\u0000,l"));
            }

            if (var3 != null) {
               SnmpString var13 = new SnmpString(var3);
               SnmpOid var14 = new SnmpOid(this.m, a("v2H1\u0018f3v\u0001\bv3L\u0010\u0012M H\u0001"));
               var11.appendIndexOid(var14, false);
               var13.appendIndexOid(var14, false);
               var9.add((String)a("v2H1\u0018f3f\b\u0004m$c\u0016\u0004n"), (SnmpValue)var14);
            }

            var9.addInstance(a("v2H1\u0018f3q\u0005\to$"), new SnmpValue[]{var11, var12});
            SnmpVarBindList var18 = this.l.performSet(var1, var9);
            if (this.p.isDebugEnabled()) {
               this.p.debug(a("#l\bD\u0019f2P\b\u001f9a") + var18);
            }

            return;
         } catch (SnmpErrorException var16) {
            if (this.p.isDebugEnabled()) {
               this.p.debug(a(".l\u0005\u0001\u0013`$U\u0010\u0002l/\u001fD") + var16);
            }

            if (var7 < 0L || var16.getErrorStatus() != 12 || var16.getErrorIndex() != 1) {
               throw var16;
            }

            this.p.debug(a("v2H1\u0018f3v\u0014\u0002m\rJ\u0007\u0000#'D\r\u0007f%\u0005L\nw5@\t\u001bw{") + var6 + ")");
            if (var6 >= var5) {
               throw var16;
            }

            ++var6;
         } catch (SnmpException var17) {
            if (this.p.isDebugEnabled()) {
               this.p.debug(a(".l\u0005\u0001\u0013`$U\u0010\u0002l/\u001fD") + var17);
            }

            throw var17;
         }
      }
   }

   public void changeOwnAuthKey(SnmpPeer var1, String var2) throws SnmpException, NoSuchAlgorithmException {
      this.a(var1, var2, 1);
   }

   public void changeOwnPrivKey(SnmpPeer var1, String var2) throws SnmpException, NoSuchAlgorithmException {
      this.a(var1, var2, 2);
   }

   public byte[] getDHKickstartAuthSalt() {
      return this.n;
   }

   public void setDHKickstartAuthSalt(byte[] var1) {
      this.n = var1;
   }

   public byte[] getDHKickstartPrivSalt() {
      return this.o;
   }

   public void setDHKickstartPrivSalt(byte[] var1) {
      this.o = var1;
   }

   public USMLocalizedUserData dhKickstartUser(SnmpPeer var1, String var2, BigInteger var3, int var4, int var5) throws SnmpValueException, SnmpException, NoSuchAlgorithmException {
      boolean var28 = Usm.M;
      if (var1.getSnmpEngineID() == null) {
         this.l.performDiscovery(var1, (SnmpResponseListener)null);
      }

      SnmpEngineID var6 = var1.getSnmpEngineID();
      SnmpVarBindList var7 = new SnmpVarBindList(this.m);
      var7.add(a("v2H #H(F\u000f\u0018w W\u0010&z\u0011P\u0006\u0007j\""));
      var7.add(a("v2H #H(F\u000f\u0018w W\u0010&d3u\u0011\to(F"));
      var7.add(a("v2H #H(F\u000f\u0018w W\u00108f\"P\u0016\u0002w8k\u0005\u0006f"));
      SnmpVarBindList[] var8 = this.l.performGetAllInstances(var1, var7);
      BigInteger var9 = e.modPow(var3, d);
      boolean var10 = false;
      int var11 = 0;

      BigInteger var15;
      label63: {
         while(true) {
            if (var11 < var8.length) {
               SnmpVarBindList var12 = var8[var11];
               SnmpVarBind var13 = var12.get(2);
               if (var28) {
                  break;
               }

               if (var13 != null && var13.getValue() != null && var13.getValue().getString() != null && var13.getValue().getString().equals(var2)) {
                  var13 = var12.get(0);
                  Object var14 = null;

                  byte[] var31;
                  try {
                     var31 = var13.getValue().getByteArray();
                  } catch (Exception var30) {
                     throw new SnmpValueException(a("j/S\u0005\u0007j%\u0005C\u001ep,a, j\"N\u0017\u001fb3Q)\u0012S4G\b\u0002`f\u0005\u0012\no4@D\rl3\u0005\u0011\u0018f3\u0005C") + var2 + a("$aW\u0001\u001fq(@\u0012\u000eg{\u0005") + var13);
                  }

                  var15 = new BigInteger(1, var31);
                  var13 = var12.get(1);
                  Object var16 = null;

                  byte[] var32;
                  try {
                     var32 = var13.getValue().getByteArray();
                  } catch (Exception var29) {
                     throw new SnmpValueException(a("j/S\u0005\u0007j%\u0005C\u001ep,a, j\"N\u0017\u001fb3Q)\fq\u0011P\u0006\u0007j\"\u0002D\u001db-P\u0001Ke.WD\u001ep$WDL") + var2 + a("$aW\u0001\u001fq(@\u0012\u000eg{\u0005") + var13);
                  }

                  BigInteger var17 = new BigInteger(1, var32);
                  if (var17.equals(var9)) {
                     break label63;
                  }

                  var10 = true;
                  if (var28) {
                     SnmpException.b = !SnmpException.b;
                     break label63;
                  }
               }

               ++var11;
               if (!var28) {
                  continue;
               }
            }

            if (var10) {
               throw new SnmpValueException(a("m.\u0005\u0011\u0018n\u0005m/\u0002`*v\u0010\nq5q\u0005\to$\u0005\u0001\u0005w3\\D\rl3\u0005\u0011\u0018f3\u0005C") + var2 + a("$aR\f\u0002`)\u0005\t\nw\"M\u0001\u0018#5M\u0001Kp1@\u0007\u0002e(@\u0000Kn K\u0005\ff3\u0005\u0016\nm%J\tKm4H\u0006\u000eqo\u00054\u0007f V\u0001Kq$F\f\u000e`*\u0005\u0010\u0003faV\u0011\u001bs-L\u0001\u000f#,B\u00169b/A\u000b\u0006#7D\b\u001efo"));
            }
            break;
         }

         throw new SnmpValueException(a("m.\u0005\u000f\u0002`*v\u0010\nq5\u0005\u0010\na-@D\u000em5W\u001dKe.WD\u001ep$WDL") + var2 + "'");
      }

      BigInteger var18 = var15.modPow(var3, d);
      byte[] var19 = CryptoUtil.encodeUnsigned(var18);
      USMAuthProtocolSpec var20 = USMAuthProtocolSpec.getSpec(var4);
      int var21 = var20.getDigestLength();
      USMPrivProtocolSpec var22 = USMPrivProtocolSpec.getSpec(var5);
      int var23 = var22.getKeyLength();
      PBKDF2 var24 = new PBKDF2(a("P\td"));
      byte[] var25 = var24.passwd2key(var19, this.getDHKickstartAuthSalt(), 500, var21);
      byte[] var26 = var24.passwd2key(var19, this.getDHKickstartPrivSalt(), 500, var23);
      USMLocalizedUserData var27 = new USMLocalizedUserData(var2, 3, var4, var5, (byte[])null, (byte[])null);
      var27.setLocalizedAuthKey(var25);
      var27.setLocalizedPrivKey(var26);
      this.l.getUsm().a(var6, var27);
      return var27;
   }

   public byte[] changeDHAuthKey(SnmpPeer var1, String var2) throws SnmpException, NoSuchAlgorithmException, SnmpKeyChangeFallbackException {
      return this.a(var1, var2, 1, -1, -1, false);
   }

   public byte[] changeDHAuthKey(SnmpPeer var1, String var2, int var3) throws SnmpException, NoSuchAlgorithmException, SnmpKeyChangeFallbackException {
      return this.a(var1, var2, 1, var3, -1, false);
   }

   public byte[] changeDHPrivKey(SnmpPeer var1, String var2) throws SnmpException, NoSuchAlgorithmException, SnmpKeyChangeFallbackException {
      return this.a(var1, var2, 2, -1, -1, false);
   }

   public byte[] changeDHPrivKey(SnmpPeer var1, String var2, int var3) throws SnmpException, NoSuchAlgorithmException, SnmpKeyChangeFallbackException {
      return this.a(var1, var2, 2, -1, var3, false);
   }

   public byte[] changeDHOwnPrivKey(SnmpPeer var1) throws SnmpException, NoSuchAlgorithmException, SnmpKeyChangeFallbackException {
      return this.a(var1, 2, -1, -1);
   }

   public byte[] changeDHOwnAuthKey(SnmpPeer var1) throws SnmpException, NoSuchAlgorithmException, SnmpKeyChangeFallbackException {
      return this.a(var1, 1, -1, -1);
   }

   public void changeAuthKey(SnmpPeer var1, String var2, String var3, String var4) throws SnmpException, NoSuchAlgorithmException {
      this.a(var1, var2, var3, var4, 1, -1);
   }

   public void changePrivKey(SnmpPeer var1, String var2, String var3, String var4) throws SnmpException, NoSuchAlgorithmException {
      this.a(var1, var2, var3, var4, 2, -1);
   }

   private void a(SnmpPeer var1, String var2, String var3, String var4, int var5, int var6) throws SnmpException, NoSuchAlgorithmException {
      if (this.p.isDebugEnabled()) {
         this.p.debug(a("`)D\n\ff\n@\u001dCv2@\u0016V") + var2 + a("/a\u000bJE/a\u000bJE"));
      }

      byte var7 = 10;
      int var8 = 1;
      if (var6 == -1) {
         var6 = this.a(var1, var2);
      }

      if (var6 == -1) {
         throw new SnmpException(a("v/N\n\u0004t/\u0005\u0005\u001ew)\u0005\u0014\u0019l5J\u0007\u0004o"));
      } else {
         while(true) {
            long var9 = -1L;

            try {
               if (var1.getSnmpEngineID() == null) {
                  this.l.performDiscovery(var1, (SnmpResponseListener)null);
               }

               SnmpVarBindList var11 = new SnmpVarBindList(this.m);
               SnmpEngineID var12 = var1.getSnmpEngineID();
               SnmpString var13 = new SnmpString(var12.getValue());
               SnmpString var14 = new SnmpString(var2);
               byte[] var15 = USMUtil.generateKeyChangeFromPasswd(var12.getValue(), var3, var4, var6, var5 == 2);
               var9 = this.a(var1);
               if (var9 > -1L) {
                  var11.add(a("v2H1\u0018f3v\u0014\u0002m\rJ\u0007\u0000-q"), var9);
               }

               label76: {
                  if (var5 == 2) {
                     var11.add((String)a("v2H1\u0018f3u\u0016\u0002u\n@\u001d(k K\u0003\u000e"), (SnmpValue)(new SnmpString(var15)));
                     if (!Usm.M) {
                        break label76;
                     }
                  }

                  var11.add((String)a("v2H1\u0018f3d\u0011\u001fk\n@\u001d(k K\u0003\u000e"), (SnmpValue)(new SnmpString(var15)));
               }

               byte[] var16 = USMUtil.generateRandom(10);
               var11.add((String)a("v2H1\u0018f3u\u0011\to(F"), (SnmpValue)(new SnmpString(var16)));
               var11.addInstance(a("v2H1\u0018f3q\u0005\to$"), new SnmpValue[]{var13, var14});
               SnmpVarBindList var17 = this.l.performSet(var1, var11);
               if (this.p.isDebugEnabled()) {
                  this.p.debug(a(".l\u0005\u0016\u000ep4I\u0010Q#") + var17);
               }

               SnmpVarBindList var18 = new SnmpVarBindList(this.m);
               var18.add(a("v2H1\u0018f3u\u0011\to(F"));
               var18.addInstance(a("v2H1\u0018f3q\u0005\to$"), new SnmpValue[]{var13, var14});
               SnmpVarBindList var19 = this.l.performGet(var1, var18);
               byte[] var20 = var19.get(0).getValue().getByteArray();
               if (a(var16, var20)) {
                  return;
               }

               this.p.debug(a("v2H1\u0018f3u\u0011\to(FD\bk K\u0003\u000e#'D\r\u0007f%\u0005L\nw5@\t\u001bw{") + var8 + ")");
               if (var8 >= var7) {
                  throw new SnmpException(a("v2H1\u0018f3u\u0011\to(FD\bk K\u0003\u000e#'D\r\u0007f%"));
               }
            } catch (SnmpErrorException var21) {
               if (this.p.isDebugEnabled()) {
                  this.p.debug(a(".l\u0005\u0001\u0013`$U\u0010\u0002l/\u001fD") + var21);
               }

               if (var9 < 0L || var21.getErrorStatus() != 12 || var21.getErrorIndex() != 1) {
                  throw var21;
               }

               this.p.debug(a("v2H1\u0018f3v\u0014\u0002m\rJ\u0007\u0000#'D\r\u0007f%\u0005L\nw5@\t\u001bw{") + var8 + ")");
               if (var8 >= var7) {
                  throw var21;
               }
            } catch (SnmpException var22) {
               if (this.p.isDebugEnabled()) {
                  this.p.debug(a(".l\u0005\u0001\u0013`$U\u0010\u0002l/\u001fD") + var22);
               }

               throw var22;
            }

            ++var8;
         }
      }
   }

   private byte[] a(SnmpPeer var1, int var2, int var3, int var4) throws SnmpException, NoSuchAlgorithmException, SnmpKeyChangeFallbackException {
      if (this.p.isDebugEnabled()) {
         this.p.debug(a("`)D\n\ff\u0005m+\u001cm\n@\u001dCs$@\u0016V") + var1 + a("/a\u000bJE/a\u000bJE"));
      }

      SnmpParameters var5 = var1.getParameters();
      SnmpMessageProfile var6 = var5.getWriteProfile();
      if (var6 == null) {
         throw new SnmpException(a("m.\u0005\u0013\u0019j5@D\u001bq.C\r\u0007faV\u0014\u000e`(C\r\u000eg"));
      } else if (var6.getSecurityModel() != 3) {
         throw new SnmpException(a("v2HD\u0018f\"P\u0016\u0002w8\u0005\t\u0004g$ID\u0005l5\u0005\u0017\u001bf\"L\u0002\u0002f%"));
      } else {
         if (var1.getSnmpEngineID() == null) {
            this.l.performDiscovery(var1, (SnmpResponseListener)null);
         }

         SnmpEngineID var7 = var1.getSnmpEngineID();
         if (var7 == null) {
            throw new SnmpException(a("m.\u0005\u0001\u0005d(K\u0001Kj%\u0005\u0005\u001db(I\u0005\to$\u0005\u0002\u0004qaU\u0001\u000eq"));
         } else {
            String var8 = var6.getSecurityName();
            USMLocalizedUserData var9 = this.l.getUsm().getUser(var7, var8);
            if (var9 == null) {
               throw new SnmpException(a("m.\u0005\b\u0004` ID\u001ep$WDL") + var8 + a("$aC\u000b\u001em%\u000b"));
            } else {
               if (var3 == -1 && var9.hasAuth()) {
                  var3 = var9.getAuthProtocol();
               }

               if (var4 == -1 && var9.hasPriv()) {
                  var4 = var9.getPrivProtocol();
               }

               return this.a(var1, var8, var2, var3, var4, true);
            }
         }
      }
   }

   private byte[] a(SnmpPeer var1, String var2, int var3, int var4, int var5, boolean var6) throws SnmpException, NoSuchAlgorithmException, SnmpKeyChangeFallbackException {
      boolean var31 = Usm.M;
      if (this.p.isDebugEnabled()) {
         this.p.debug(a("`)D\n\ff\u0005m/\u000eziP\u0017\u000eq|") + var2 + a("/a\u000bJE/a\u000bJE"));
      }

      if (var3 == 1) {
         if (var4 == -1) {
            var4 = this.a(var1, var2);
         }

         if (var4 == -1) {
            throw new SnmpException(a("v/N\n\u0004t/\u0005\u0005\u001ew)\u0005\u0014\u0019l5J\u0007\u0004o"));
         }
      } else {
         if (var5 == -1) {
            var5 = this.b(var1, var2);
         }

         if (var5 == -1) {
            throw new SnmpException(a("v/N\n\u0004t/\u0005\u0014\u0019j7\u0005\u0014\u0019l5J\u0007\u0004o"));
         }
      }

      if (var1.getSnmpEngineID() == null) {
         this.l.performDiscovery(var1, (SnmpResponseListener)null);
      }

      SnmpEngineID var7;
      SnmpString var8;
      SnmpString var9;
      SnmpVarBindList var10;
      label144: {
         var7 = var1.getSnmpEngineID();
         var8 = new SnmpString(var7.getValue());
         var9 = new SnmpString(var2);
         var10 = new SnmpVarBindList(this.m);
         if (var3 == 2) {
            if (var6) {
               var10.add(a("v2H #V2@\u0016$t/u\u0016\u0002u\n@\u001d(k K\u0003\u000e"));
               if (!var31) {
                  break label144;
               }
            }

            var10.add(a("v2H #V2@\u0016;q(S/\u000ez\u0002M\u0005\u0005d$"));
            if (!var31) {
               break label144;
            }
         }

         if (var6) {
            var10.add(a("v2H #V2@\u0016$t/d\u0011\u001fk\n@\u001d(k K\u0003\u000e"));
            if (!var31) {
               break label144;
            }
         }

         var10.add(a("v2H #V2@\u0016*v5M/\u000ez\u0002M\u0005\u0005d$"));
      }

      var10.addInstance(a("v2H #V2@\u0016 f8q\u0005\to$"), new SnmpValue[]{var8, var9});
      var10.add(a("v2H #S W\u0005\u0006f5@\u0016\u0018-q"));
      SnmpVarBindList var11 = this.l.performGet(var1, var10);
      if (var11.get(0).isError()) {
         throw new SnmpException(a("f3W\u000b\u0019#.KD\n`\"@\u0017\u0018j/B^K") + var11.get(0));
      } else if (var11.get(1).isError()) {
         throw new SnmpException(a("f3W\u000b\u0019#.KD\n`\"@\u0017\u0018j/B^K") + var11.get(1));
      } else {
         byte[] var12 = var11.get(0).getValue().getByteArray();
         byte[] var13 = var11.get(1).getValue().getByteArray();
         BigInteger var14 = d;
         BigInteger var15 = e;
         int var16 = f;

         try {
            BERBuffer var17 = new BERBuffer(var13);
            BERCoder.expectTag(var17, 48);
            BERCoder.getLength(var17);
            var14 = BERCoder.decodeBigInteger(var17, 2);
            var15 = BERCoder.decodeBigInteger(var17, 2);
            if (var17.hasMoreData()) {
               var16 = (int)BERCoder.decodeInteger(var17, 2);
            }
         } catch (Exception var35) {
            this.p.error(a("j/S\u0005\u0007j%\u0005 ##1D\u0016\nn$Q\u0001\u0019paW\u0001\u001fq(@\u0012\u000egaC\u0016\u0004naD\u0003\u000em5"), var35);
            throw new SnmpException(a("j/S\u0005\u0007j%\u0005\u0011\u0018n\u0005m4\nq H\u0001\u001ff3VD\u001db-P\u0001K+") + var35 + ")");
         }

         BigInteger var36 = new BigInteger(1, var12);
         BigInteger var18 = null;
         SecureRandom var19 = new SecureRandom();

         do {
            var18 = new BigInteger(var16, var19);
         } while(var14.signum() > 0 && var18.compareTo(var14) >= 0);

         SnmpVarBindList var23;
         label145: {
            BigInteger var20 = var15.modPow(var18, var14);
            byte[] var21 = CryptoUtil.encodeUnsigned(var20);
            byte[] var22 = new byte[var12.length + var21.length];
            System.arraycopy(var12, 0, var22, 0, var12.length);
            System.arraycopy(var21, 0, var22, var12.length, var21.length);
            var23 = new SnmpVarBindList(this.m);
            if (var3 == 2) {
               if (var6) {
                  var23.add((String)a("v2H #V2@\u0016$t/u\u0016\u0002u\n@\u001d(k K\u0003\u000e"), (SnmpValue)(new SnmpString(var22)));
                  if (!var31) {
                     break label145;
                  }
               }

               var23.add((String)a("v2H #V2@\u0016;q(S/\u000ez\u0002M\u0005\u0005d$"), (SnmpValue)(new SnmpString(var22)));
               if (!var31) {
                  break label145;
               }
            }

            if (var6) {
               var23.add((String)a("v2H #V2@\u0016$t/d\u0011\u001fk\n@\u001d(k K\u0003\u000e"), (SnmpValue)(new SnmpString(var22)));
               if (!var31) {
                  break label145;
               }
            }

            var23.add((String)a("v2H #V2@\u0016*v5M/\u000ez\u0002M\u0005\u0005d$"), (SnmpValue)(new SnmpString(var22)));
         }

         var23.addInstance(a("v2H #V2@\u0016 f8q\u0005\to$"), new SnmpValue[]{var8, var9});
         byte[] var24 = null;
         Object var25 = null;
         BigInteger var26 = var36.modPow(var18, var14);
         byte[] var27 = CryptoUtil.encodeUnsigned(var26);
         USMLocalizedUserData var28 = this.l.getUsm().getUser(var7, var2);
         USMUser var29;
         if (var28 == null) {
            var29 = this.l.getUsm().getDefaultUser(var2);
            if (var29 != null) {
               var28 = this.l.getUsm().addUser(var7, var29);
            }
         }

         int var30;
         byte[] var37;
         if (var3 == 2) {
            USMPrivProtocolSpec var38 = USMPrivProtocolSpec.getSpec(var5);
            var30 = var38.getKeyLength();
            var37 = new byte[var30];
            System.arraycopy(var27, var27.length - var30, var37, 0, var30);
            if (var28 != null) {
               var24 = var28.getLocalizedPrivKey();
            }
         } else {
            USMAuthProtocolSpec var39 = USMAuthProtocolSpec.getSpec(var4);
            var30 = var39.getDigestLength();
            var37 = new byte[var30];
            System.arraycopy(var27, var27.length - var30, var37, 0, var30);
            if (var28 != null) {
               var24 = var28.getLocalizedAuthKey();
            }
         }

         var29 = null;

         SnmpVarBindList var40;
         try {
            var40 = this.l.performSet(var1, var23);
         } catch (SnmpTimeoutException var32) {
            throw new SnmpKeyChangeFallbackException(a("baQ\r\u0006f.P\u0010Kl\"F\u0011\u0019q$AD\u000fv3L\n\f#5M\u0001KG(C\u0002\u0002flm\u0001\u0007o,D\nKh$\\D\u000e{\"M\u0005\u0005d$\u000bD8v\"F\u0001\u0018paJ\u0002Kf9F\f\nm&@D\u0002paP\n\u0000m.R\nE#\rJ\u0007\noaN\u0001\u0012p5J\u0016\u000e#/J\u0010Kv1A\u0005\u001ff%\u000b"), var3, var24, var37);
         } catch (SnmpErrorException var33) {
            throw new SnmpKeyChangeFallbackException(a("G(C\u0002\u0002flm\u0001\u0007o,D\nKh$\\D\bk K\u0003\u000e#'D\r\u0007f%\u0005\u0000\u001efaQ\u000bKb/\u00057%N\u0011\u0005\u0001\u0019q.WD\u0004maQ\f\u000e#2@\u0010Q#") + var33.getMessage(), var3, var24, var37);
         } catch (SnmpException var34) {
            throw new SnmpKeyChangeFallbackException(a("G(C\u0002\u0002flm\u0001\u0007o,D\nKh$\\D\bk K\u0003\u000e#'D\r\u0007f%\u0005\u0000\u001efaQ\u000bKb/\u00057%N\u0011\u0005\u0001\u0013`$U\u0010\u0002l/\u0005\u000b\u0005#5M\u0001Kp$Q^K") + var34.getMessage(), var3, var24, var37);
         }

         if (this.p.isDebugEnabled()) {
            this.p.debug(a(".l\u0005\u0016\u000ep4I\u0010Q#") + var40);
         }

         if (var3 == 2) {
            if (var28 != null) {
               var28.setLocalizedPrivKey(var37);
            }

            return var37;
         } else {
            if (var28 != null) {
               var28.setLocalizedAuthKey(var37);
            }

            return var37;
         }
      }
   }

   private void a(SnmpPeer var1, String var2, int var3) throws SnmpException, NoSuchAlgorithmException {
      boolean var23 = Usm.M;
      if (this.p.isDebugEnabled()) {
         this.p.debug(a("`)D\n\ff\u000eR\n f8\r\u0014\u000ef3\u0018") + var1 + a("/a\u000bJE/a\u000bJE"));
      }

      SnmpParameters var4 = var1.getParameters();
      SnmpMessageProfile var5 = var4.getWriteProfile();
      if (var5 == null) {
         throw new SnmpException(a("m.\u0005\u0013\u0019j5@D\u001bq.C\r\u0007faV\u0014\u000e`(C\r\u000eg"));
      } else if (var5.getSecurityModel() != 3) {
         throw new SnmpException(a("v2HD\u0018f\"P\u0016\u0002w8\u0005\t\u0004g$ID\u0005l5\u0005\u0017\u001bf\"L\u0002\u0002f%"));
      } else {
         if (var1.getSnmpEngineID() == null) {
            this.l.performDiscovery(var1, (SnmpResponseListener)null);
         }

         SnmpEngineID var6 = var1.getSnmpEngineID();
         if (var6 == null) {
            throw new SnmpException(a("m.\u0005\u0001\u0005d(K\u0001Kj%\u0005\u0005\u001db(I\u0005\to$\u0005\u0002\u0004qaU\u0001\u000eq"));
         } else {
            String var7 = var5.getSecurityName();
            USMLocalizedUserData var8 = this.l.getUsm().getUser(var6, var7);
            USMUser var9;
            if (var8 == null) {
               var9 = this.l.getUsm().getDefaultUser(var7);
               if (var9 == null) {
                  throw new SnmpException(a("m.\u000518NaP\u0017\u000eqa\u0002") + var7 + a("$aC\u000b\u001em%\u000b"));
               }

               var8 = this.l.getUsm().addUser(var6, var9);
            }

            if (var8 == null) {
               throw new SnmpException(a("m.\u0005\b\u0004` ID\u001ep$WDL") + var7 + a("$aC\u000b\u001em%\u000b"));
            } else {
               var9 = null;
               byte[] var26;
               if (var3 == 1) {
                  var26 = var8.getLocalizedAuthKey();
               } else {
                  var26 = var8.getLocalizedPrivKey();
               }

               if (var26 == null) {
                  throw new SnmpException(a("o.F\u0005\u0007j;@\u0000Kv2@\u0016Kh$\\D\rl3\u0005C") + var7 + a("$aK\u000b\u001f#'J\u0011\u0005go"));
               } else {
                  int var10 = var8.getAuthProtocol();
                  byte[] var11 = USMUtil.generateKey(var2.getBytes(), var10);
                  byte[] var12 = USMUtil.localizeKey(var11, var6.getValue(), var10);
                  SnmpString var13 = new SnmpString(var6.getValue());
                  SnmpString var14 = new SnmpString(var7);
                  byte[] var15 = USMUtil.generateKeyChangeFromLocalKey(var26, var12, var10, var3 == 2);
                  byte var16 = 10;
                  int var17 = 1;

                  while(true) {
                     long var18 = -1L;

                     try {
                        SnmpVarBindList var20;
                        label103: {
                           SnmpVarBindList var10000;
                           String var10001;
                           label120: {
                              var20 = new SnmpVarBindList(this.m);
                              var18 = this.a(var1);
                              if (var18 > -1L) {
                                 var10000 = var20;
                                 var10001 = a("v2H1\u0018f3v\u0014\u0002m\rJ\u0007\u0000-q");
                                 if (var23) {
                                    break label120;
                                 }

                                 var20.add(var10001, var18);
                              }

                              if (var3 == 2) {
                                 var20.add((String)a("v2H1\u0018f3j\u0013\u0005S3L\u0012 f8f\f\nm&@"), (SnmpValue)(new SnmpString(var15)));
                                 if (!var23) {
                                    break label103;
                                 }
                              }

                              var10000 = var20;
                              var10001 = a("v2H1\u0018f3j\u0013\u0005B4Q\f f8f\f\nm&@");
                           }

                           var10000.add((String)var10001, (SnmpValue)(new SnmpString(var15)));
                        }

                        byte[] var21 = USMUtil.generateRandom(10);
                        var20.add((String)a("v2H1\u0018f3u\u0011\to(F"), (SnmpValue)(new SnmpString(var21)));
                        var20.addInstance(a("v2H1\u0018f3q\u0005\to$"), new SnmpValue[]{var13, var14});
                        SnmpVarBindList var22 = this.l.performSet(var1, var20);
                        if (this.p.isDebugEnabled()) {
                           this.p.debug(a(".l\u0005\u0016\u000ep4I\u0010Q#") + var22);
                        }

                        if (var3 == 1) {
                           var8.setLocalizedAuthKey(var12);
                           if (!var23) {
                              return;
                           }
                        }

                        var8.setLocalizedPrivKey(var12);
                        return;
                     } catch (SnmpErrorException var24) {
                        if (this.p.isDebugEnabled()) {
                           this.p.debug(a(".l\u0005\u0001\u0013`$U\u0010\u0002l/\u001fD") + var24);
                        }

                        if (var18 < 0L || var24.getErrorStatus() != 12 || var24.getErrorIndex() != 1) {
                           throw var24;
                        }

                        this.p.debug(a("v2H1\u0018f3v\u0014\u0002m\rJ\u0007\u0000#'D\r\u0007f%\u0005L\nw5@\t\u001bw{") + var17 + ")");
                        if (var17 >= var16) {
                           throw var24;
                        }

                        ++var17;
                     } catch (SnmpException var25) {
                        if (this.p.isDebugEnabled()) {
                           this.p.debug(a(".l\u0005\u0001\u0013`$U\u0010\u0002l/\u001fD") + var25);
                        }

                        throw var25;
                     }
                  }
               }
            }
         }
      }
   }

   public void deleteUser(SnmpPeer var1, String var2) throws SnmpException {
      byte var3 = 10;
      int var4 = 1;

      while(true) {
         long var5 = -1L;

         try {
            SnmpVarBindList var7;
            label36: {
               var7 = new SnmpVarBindList(this.m);
               var5 = this.a(var1);
               if (var5 > -1L) {
                  if (Usm.M) {
                     break label36;
                  }

                  var7.add(a("v2H1\u0018f3v\u0014\u0002m\rJ\u0007\u0000-q"), var5);
               }

               if (var1.getSnmpEngineID() == null) {
                  this.l.performDiscovery(var1, (SnmpResponseListener)null);
               }
            }

            if (var1.getSnmpEngineID() == null) {
               throw new SnmpException(a("m.\u0005\u0001\u0005d(K\u0001Kj%"));
            }

            SnmpEngineID var8 = var1.getSnmpEngineID();
            SnmpString var9 = new SnmpString(var8.getValue());
            SnmpString var10 = new SnmpString(var2);
            var7.add(a("v2H1\u0018f3v\u0010\nw4V"), a("g$V\u0010\u0019l8"));
            var7.addInstance(a("v2H1\u0018f3q\u0005\to$"), new SnmpValue[]{var9, var10});
            this.l.performSet(var1, var7);
            return;
         } catch (SnmpErrorException var12) {
            if (var5 < 0L || var12.getErrorStatus() != 12 || var12.getErrorIndex() != 1) {
               throw var12;
            }

            this.p.debug(a("v2H1\u0018f3v\u0014\u0002m\rJ\u0007\u0000#'D\r\u0007f%\u0005L\nw5@\t\u001bw{") + var4 + ")");
            if (var4 >= var3) {
               throw var12;
            }

            ++var4;
         } catch (SnmpException var13) {
            throw var13;
         }
      }
   }

   private long a(SnmpPeer var1) {
      long var2 = -1L;

      try {
         SnmpVarBindList var4 = new SnmpVarBindList(this.m);
         var4.add(a("v2H1\u0018f3v\u0014\u0002m\rJ\u0007\u0000-q"));
         SnmpVarBindList var5 = this.l.performGet(var1, var4);
         return var5.get(0).getValue().longValue();
      } catch (SnmpException var6) {
         return -1L;
      }
   }

   private int a(SnmpPeer var1, String var2) {
      try {
         if (var1.getSnmpEngineID() == null) {
            this.l.performDiscovery(var1, (SnmpResponseListener)null);
         }

         if (var1.getSnmpEngineID() == null) {
            throw new SnmpException(a("m.\u0005\u0001\u0005d(K\u0001Kj%"));
         } else {
            SnmpVarBindList var3 = new SnmpVarBindList(this.m);
            SnmpEngineID var4 = var1.getSnmpEngineID();
            SnmpString var5 = new SnmpString(var4.getValue());
            SnmpString var6 = new SnmpString(var2);
            var3.add(a("v2H1\u0018f3d\u0011\u001fk\u0011W\u000b\u001fl\"J\b"));
            var3.addInstance(a("v2H1\u0018f3q\u0005\to$"), new SnmpValue[]{var5, var6});
            SnmpVarBindList var7 = this.l.performGet(var1, var3);
            SnmpValue var8 = var7.get(0).getValue();
            if (var8 instanceof SnmpOid) {
               SnmpOid var9 = (SnmpOid)var7.get(0).getValue();
               if (j.equals(var9)) {
                  this.p.debug(a("v2L\n\f#\faQKb4Q\fKs3J\u0010\u0004`.I^K") + var2);
                  return 0;
               } else if (k.equals(var9)) {
                  this.p.debug(a("v2L\n\f#\u0012m%Kb4Q\fKs3J\u0010\u0004`.I^K") + var2);
                  return 1;
               } else {
                  this.p.debug(a("v/N\n\u0004t/\u0005\u0005\u001ew)\u0005\u0014\u0019l5J\u0007\u0004oaC\u000b\u00199a") + var2);
                  return -1;
               }
            } else {
               this.p.debug(a("f3W\u000b\u00199aF\u0005\u0005m.QD\n`\"@\u0017\u0018#fP\u0017\u0006V2@\u0016*v5M4\u0019l5J\u0007\u0004of"));
               return -1;
            }
         }
      } catch (SnmpException var10) {
         this.p.error(a("f3W\u000b\u0019#(KD\u0019f5W\u0001\u0002u(K\u0003KV2H1\u0018f3d\u0011\u001fkaU\u0016\u0004w.F\u000b\u0007#'U\u0016Kv2@\u0016Q#") + var2, var10);
         return -1;
      }
   }

   private int b(SnmpPeer var1, String var2) {
      try {
         if (var1.getSnmpEngineID() == null) {
            this.l.performDiscovery(var1, (SnmpResponseListener)null);
         }

         if (var1.getSnmpEngineID() == null) {
            throw new SnmpException(a("m.\u0005\u0001\u0005d(K\u0001Kj%"));
         } else {
            SnmpVarBindList var3 = new SnmpVarBindList(this.m);
            SnmpEngineID var4 = var1.getSnmpEngineID();
            SnmpString var5 = new SnmpString(var4.getValue());
            SnmpString var6 = new SnmpString(var2);
            var3.add(a("v2H1\u0018f3u\u0016\u0002u\u0011W\u000b\u001fl\"J\b"));
            var3.addInstance(a("v2H1\u0018f3q\u0005\to$"), new SnmpValue[]{var5, var6});
            SnmpVarBindList var7 = this.l.performGet(var1, var3);
            SnmpValue var8 = var7.get(0).getValue();
            if (var8 instanceof SnmpOid) {
               SnmpOid var9 = (SnmpOid)var7.get(0).getValue();
               if (Snmp.DES_PRIV_PROTOCOL_OID.equals(var9)) {
                  return 2;
               } else if (Snmp.TDES_PRIV_PROTOCOL_OID.equals(var9)) {
                  return 14832;
               } else if (Snmp.AES128_PRIV_PROTOCOL_OID.equals(var9)) {
                  return 4;
               } else if (Snmp.AES192_PRIV_PROTOCOL_OID.equals(var9)) {
                  return 5;
               } else {
                  return Snmp.AES256_PRIV_PROTOCOL_OID.equals(var9) ? 6 : -1;
               }
            } else {
               this.p.debug(a("f3W\u000b\u00199aF\u0005\u0005m.QD\n`\"@\u0017\u0018#fP\u0017\u0006V2@\u0016;q(S4\u0019l5J\u0007\u0004of"));
               return -1;
            }
         }
      } catch (SnmpException var10) {
         return -1;
      }
   }

   private static boolean a(byte[] var0, byte[] var1) {
      boolean var3 = Usm.M;
      if (var0.length != var1.length) {
         return false;
      } else {
         int var2 = 0;

         byte var10000;
         while(true) {
            if (var2 < var0.length) {
               var10000 = var0[var2];
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
   }

   static {
      d = new BigInteger(1, c);
      e = BigInteger.valueOf(2L);
      f = 1024;
      g = new byte[]{-47, 49, 11, -90};
      h = new byte[]{-104, -33, -75, -84};
      i = new SnmpOid(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 10L, 1L, 1L, 1L});
      j = new SnmpOid(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 10L, 1L, 1L, 2L});
      k = new SnmpOid(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 10L, 1L, 1L, 3L});
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 3;
               break;
            case 1:
               var10003 = 65;
               break;
            case 2:
               var10003 = 37;
               break;
            case 3:
               var10003 = 100;
               break;
            default:
               var10003 = 107;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
