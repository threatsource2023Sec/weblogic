package monfox.toolkit.snmp.v3.usm;

import java.util.Hashtable;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.ber.BERBuffer;
import monfox.toolkit.snmp.ber.BERCoder;
import monfox.toolkit.snmp.ber.BERException;
import monfox.toolkit.snmp.engine.SnmpBuffer;
import monfox.toolkit.snmp.engine.SnmpCoderException;
import monfox.toolkit.snmp.engine.SnmpContext;
import monfox.toolkit.snmp.engine.SnmpEngine;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.engine.SnmpMessage;
import monfox.toolkit.snmp.engine.SnmpMessageProfile;
import monfox.toolkit.snmp.engine.SnmpSecurityParameters;
import monfox.toolkit.snmp.util.ByteFormatter;
import monfox.toolkit.snmp.v3.SnmpSecurityCoder;
import monfox.toolkit.snmp.v3.SnmpSecurityCoderException;
import monfox.toolkit.snmp.v3.V3SnmpMessageParameters;
import monfox.toolkit.snmp.v3.usm.ext.UsmSecurityAuditTrailLogger;
import monfox.toolkit.snmp.v3.usm.ext.UsmUserSecurityExtension;

public class USMSecurityCoder implements SnmpSecurityCoder {
   private static boolean a = true;
   private static final int b = 3;
   private UsmSecurityAuditTrailLogger c;
   private UsmUserSecurityExtension d;
   private SnmpEngineID e;
   private USMEngineMap f;
   private USMUserTable g;
   private USMSecurityDB h;
   private Hashtable i;
   private Hashtable j;
   private static final byte[] k;
   private static final byte[] l;
   private static final byte[] m;
   private static final byte[] n;
   private int o;
   private static final String p = "$Id: USMSecurityCoder.java,v 1.41 2011/02/09 01:58:15 sking Exp $";
   private static final long q = 2147483647L;
   private static Logger r;
   private USMNotInTimeWindowListener s;
   private boolean t;

   public static boolean isNotInTimeWindowOnZerosEnabled() {
      return a;
   }

   public static void isNotInTimeWindowOnZerosEnabled(boolean var0) {
      a = var0;
   }

   public USMSecurityCoder() {
      this((SnmpEngine)null);
   }

   public USMSecurityCoder(SnmpEngine var1) {
      boolean var5 = USMLocalizedUserData.k;
      super();
      this.c = null;
      this.d = null;
      this.e = null;
      this.f = new USMEngineMap();
      this.g = null;
      this.h = null;
      this.i = new Hashtable();
      this.j = new Hashtable();
      this.o = 0;
      this.t = false;
      if (r == null) {
         r = Logger.getInstance(a("\u001eT'$P(r\u0018\u001eA2D\u0005\u0013P9"));
      }

      this.o = (int)System.currentTimeMillis();
      this.e = var1.getEngineID();
      if (this.e != null && this.e.isAuthoritative()) {
         if (r.isDebugEnabled()) {
            r.debug(a("\nc\u000e\u001e[,'\u000b\u0002A#h\u0018\u001eA*s\u0003\u0001Pkb\u0004\u0010\\%bP") + this.e);
         }

         this.f.add(new USMAuthoritativeEngineInfo(var1));
      }

      USMPrivProtocolSpec[] var2 = USMPrivProtocolSpec.getSpecs();
      int var3 = 0;

      while(var3 < var2.length) {
         this.addPrivModule(var2[var3].getPrivProtocol(), var2[var3].newModule());
         ++var3;
         if (var5) {
            break;
         }
      }

      USMAuthProtocolSpec[] var6 = USMAuthProtocolSpec.getSpecs();
      int var4 = 0;

      while(true) {
         if (var4 < var6.length) {
            this.addAuthModule(var6[var4].getAuthProtocol(), var6[var4].newModule());
            ++var4;
            if (var5) {
               break;
            }

            if (!var5) {
               continue;
            }
         }

         this.t = System.getProperty(a("*r\u001e\u001fw2w\u000b\u0004F"), (String)null) != null;
         break;
      }

   }

   public int encodeSecurityParameters(BERBuffer var1, SnmpMessage var2) throws SnmpCoderException, BERException {
      boolean var16 = USMLocalizedUserData.k;
      if (r.isDebugEnabled()) {
         r.debug(a("\u000eI)8q\u0002I-Wf\u000eD?%|\u001f^J't\u0019F'2a\u000eU9"));
      }

      SnmpMessageProfile var3;
      SnmpEngineID var4;
      label136: {
         var3 = var2.getMessageProfile();
         var4 = null;
         if (var2.getSnmpEngineID() == null && var2.isDiscovery()) {
            var4 = null;
            if (!var16) {
               break label136;
            }

            SnmpException.b = !SnmpException.b;
         }

         if (this.isAuthoritative(var2.getSnmpEngineID())) {
            var4 = this.e;
         } else {
            var4 = var2.getSnmpEngineID();
         }
      }

      USMEngineInfo var5 = null;
      USMSecurityDB var6 = this.h;
      if (var6 != null) {
         var5 = var6.getEngineInfo(var4);
      }

      if (var5 == null) {
         var5 = this.f.get(var4);
      }

      if (var5 == null && var4 != null && !this.isAuthoritative(var4)) {
         label123: {
            if (var6 != null) {
               if (r.isDebugEnabled()) {
                  r.debug(a("\u001eT'$P(r\u0018\u001eA2C(Y[.p/\u0019R\"i\u000f>[-hPW") + var4);
               }

               var5 = var6.newEngineInfo(var4);
               if (!var16) {
                  break label123;
               }
            }

            var5 = new USMEngineInfo(var4);
            this.f.add(var5);
            if (r.isDebugEnabled()) {
               r.debug(a("`'+\u0013Q\"i\rW[.pJ\u0012[,n\u0004\u0012|\u000f=J") + var4);
            }
         }
      }

      long var7 = 0L;
      long var9 = 0L;
      if (var5 != null) {
         var7 = (long)var5.getEngineBoots();
         var9 = (long)var5.getEngineTime();
      }

      String var11;
      int var12;
      int var13;
      label116: {
         var11 = var3.getSecurityName();
         var12 = var3.getSecurityLevel();
         var13 = 0;
         if ((var12 & 2) != 0) {
            byte[] var14 = this.a(var1, var2, var5, var7, var9);
            var13 += BERCoder.encodeString(var1, var14, 4);
            if (!var16) {
               break label116;
            }
         }

         var13 += BERCoder.encodeString(var1, m, 4);
      }

      int var17;
      label141: {
         var17 = -1;
         if ((var12 & 1) != 0) {
            if (r.isDebugEnabled()) {
               r.debug(a("f*J\u001aF,F\u001f\u0003]\u001bf\u0018\u0016X.s\u000f\u0005Fq'J") + ByteFormatter.toHexString(k));
            }

            var13 += BERCoder.encodeString(var1, k, 4);
            int var15 = var1.getEncLength();
            var15 -= 2;
            var17 = var15;
            if (!var16) {
               break label141;
            }
         }

         if (r.isDebugEnabled()) {
            r.debug(a("f*J\u001aF,F\u001f\u0003]\u001bf\u0018\u0016X.s\u000f\u0005Fq'J") + ByteFormatter.toHexString(l));
         }

         var13 += BERCoder.encodeString(var1, l, 4);
      }

      if (r.isDebugEnabled()) {
         r.debug(a("f*J\u001aF,R\u0019\u0012G\u0005f\u0007\u0012\u000fk'JW\u0015k'J") + (var11 == null ? "" : new String(var11)));
      }

      var13 += BERCoder.encodeString(var1, var11 == null ? new byte[0] : var11.getBytes(), 4);
      SnmpContext var18 = var2.getContext();
      if (r.isDebugEnabled()) {
         r.debug(a(".i\r\u001e[.N\u000eJ") + var4 + a("gb\u0004\u0010\\%b#\u0019S$:") + var5);
      }

      label142: {
         if (var5 == null) {
            if (r.isDebugEnabled()) {
               r.debug(a("f*J\u0019Zkb\u0004\u0010\\%bJ\u001e[-hPW\u0015k'J") + var4);
               r.debug(a("f*J\u001aF,F\u001f\u0003]\u000ei\r\u001e[.E\u0005\u0018A8=JG"));
               r.debug(a("f*J\u001aF,F\u001f\u0003]\u000ei\r\u001e[.S\u0003\u001aPq'JG"));
            }

            var13 += BERCoder.encodeInteger(var1, 0L, 2);
            var13 += BERCoder.encodeInteger(var1, 0L, 2);
            var13 += BERCoder.encodeString(var1, n, 4);
            if (!var16) {
               break label142;
            }
         }

         label143: {
            if (var2.isTimeSync()) {
               if (r.isDebugEnabled()) {
                  r.debug(a("\u000eI)8q\u0002I-Wa\u0002J/Wf\u0012I)M\u0015cu\u000f\u0006@.t\u001eM\u0015") + var2.getMsgID() + ")");
               }

               var5.a(var2.getMsgID());
               if (r.isDebugEnabled()) {
                  r.debug(a("f*J\u001eFks\u0003\u001aPkt\u0013\u0019Vq'JW\u0015k'J") + var4);
                  r.debug(a("f*J\u001aF,F\u001f\u0003]\u000ei\r\u001e[.E\u0005\u0018A8=JG"));
                  r.debug(a("f*J\u001aF,F\u001f\u0003]\u000ei\r\u001e[.S\u0003\u001aPq'JG"));
               }

               var13 += BERCoder.encodeInteger(var1, 0L, 2);
               var13 += BERCoder.encodeInteger(var1, 0L, 2);
               if (!var16) {
                  break label143;
               }
            }

            if (r.isDebugEnabled()) {
               r.debug(a("f*J\u0019Z9j\u000b\u001b\u0015&t\rW\u000fk'JW\u0015k'J") + var4);
               r.debug(a("f*J\u001aF,F\u001f\u0003]\u000ei\r\u001e[.E\u0005\u0018A8=J") + var7);
               r.debug(a("f*J\u001aF,F\u001f\u0003]\u000ei\r\u001e[.S\u0003\u001aPq'J") + var9);
            }

            var13 += BERCoder.encodeInteger(var1, var9, 2);
            var13 += BERCoder.encodeInteger(var1, var7, 2);
         }

         if (r.isDebugEnabled()) {
            r.debug(a("f*J\u001aF,F\u001f\u0003]\u000ei\r\u001e[.N.M\u0015k'J") + var5.getEngineID());
         }

         var13 += BERCoder.encodeString(var1, var5.getEngineID().toByteArray(), 4);
      }

      var13 += BERCoder.encodeLength(var1, var13);
      var13 += BERCoder.encodeTag(var1, 48);
      var13 += BERCoder.encodeLength(var1, var13);
      int var10000 = var13 + BERCoder.encodeTag(var1, 4);
      return var17;
   }

   public SnmpSecurityParameters decodeSecurityParameters(BERBuffer var1, SnmpMessage var2) throws SnmpCoderException, BERException {
      boolean var19 = USMLocalizedUserData.k;
      if (r.isDebugEnabled()) {
         r.debug(a("\u000fB)8q\u0002I-Wf\u000eD?%|\u001f^J't\u0019F'2a\u000eU9"));
      }

      BERCoder.expectTag(var1, 4);
      BERCoder.getLength(var1);
      BERCoder.expectTag(var1, 48);
      BERCoder.getLength(var1);
      byte[] var3 = BERCoder.decodeString(var1, 4);
      int var4 = (int)BERCoder.decodeInteger(var1, 2);
      int var5 = (int)BERCoder.decodeInteger(var1, 2);
      byte[] var6 = BERCoder.decodeString(var1, 4);
      int var7 = var1.getIndex();
      byte[] var8 = BERCoder.decodeString(var1, 4);
      byte[] var9 = BERCoder.decodeString(var1, 4);
      if (r.isDebugEnabled()) {
         r.debug(a("f*J\u001aF,F\u001f\u0003]\u000ei\r\u001e[.N.M\u0015k'J") + ByteFormatter.toHexString(var3));
         r.debug(a("f*J\u001aF,F\u001f\u0003]\u000ei\r\u001e[.E\u0005\u0018A8=J") + var4);
         r.debug(a("f*J\u001aF,F\u001f\u0003]\u000ei\r\u001e[.S\u0003\u001aPq'J") + var5);
         r.debug(a("f*J\u001aF,R\u0019\u0012G\u0005f\u0007\u0012\u000fk'JW\u0015k'J") + new String(var6));
         r.debug(a("f*J\u001aF,F\u001f\u0003]\u001bf\u0018\u0016X.s\u000f\u0005Fq'J") + ByteFormatter.toHexString(var8));
         r.debug(a("f*J\u001aF,W\u0018\u001eC\u001bf\u0018\u0016X.s\u000f\u0005Fq'J") + ByteFormatter.toHexString(var9));
      }

      SnmpEngineID var10 = null;
      boolean var11 = false;
      V3SnmpMessageParameters var12 = (V3SnmpMessageParameters)var2.getMessageParameters();
      if ((var6 == null || var6.length == 0) && !var12.isPriv() & !var12.isAuth()) {
         if (this.a()) {
            label292: {
               if (var3 == null || var3.length == 0) {
                  var11 = true;
                  if (!var19) {
                     break label292;
                  }
               }

               if (!this.e.equals(var3)) {
                  var11 = true;
               }

               var10 = new SnmpEngineID(var3);
            }
         } else {
            if (var3 == null || var3.length == 0) {
               throw new SnmpSecurityCoderException(3, a("%r\u0006\u001b"), var12.isReportable());
            }

            var11 = true;
            var10 = new SnmpEngineID(var3);
         }
      } else {
         var10 = new SnmpEngineID(var3);
      }

      var2.setSnmpEngineID(var10);
      if (r.isDebugEnabled()) {
         r.debug(a("/n\u0019\u0014Z=b\u0018\u000e\b") + var11 + a("gb\u0004\u0010\\%b#3\b") + var10);
      }

      USMSecurityDB var13 = this.h;
      USMEngineInfo var14 = null;
      if (var13 != null) {
         if (var10 == null) {
            var14 = var13.getEngineInfo(this.e);
         } else {
            var14 = var13.getEngineInfo(var10);
         }
      }

      if (var14 == null) {
         label239: {
            if (var10 == null) {
               var14 = this.f.get(this.e);
               if (!var19) {
                  break label239;
               }
            }

            var14 = this.f.get(var10);
         }
      }

      label274: {
         if (var14 == null) {
            if (this.isAuthoritative(var10)) {
               break label274;
            }

            if (var12.isReportable() && this.a()) {
               throw new SnmpSecurityCoderException(3, var10.toString(), var12.isReportable());
            }

            if (var13 != null) {
               if (r.isDebugEnabled()) {
                  r.debug(a("\u001eT'$P(r\u0018\u001eA2C(Y[.p/\u0019R\"i\u000f>[-hPW") + var10);
               }

               var14 = var13.newEngineInfo(var10);
               if (!var19) {
                  break label274;
               }
            }

            var14 = new USMEngineInfo(var10);
            this.f.add(var14);
            if (!r.isDebugEnabled()) {
               break label274;
            }

            r.debug(a("f'+\u0013Q\"i\rW[.pJ\u0012[,n\u0004\u0012|\u000f=J") + var10);
            if (!var19) {
               break label274;
            }
         }

         if (r.isDebugEnabled()) {
            r.debug(a("\bO/4~\u0002I-Wa\u0002J/Wf\u0012I)M\u0015cu\u000f\u0006@.t\u001eM\u0015") + var2.getMsgID() + "=" + var14.b() + ")");
         }

         if (var2.getMsgID() > 0 && (long)var2.getMsgID() == var14.b()) {
            if (r.isDebugEnabled()) {
               r.debug(a("\u000fB)8q\u0002I-Wa\u0002J/Wf\u0012I)M\u0015cu\u000f\u0006@.t\u001eM\u0015") + var2.getMsgID() + ")");
            }

            var14.setEngineBoots(0);
            var14.setLastEngineTime(0);
            if (!var19) {
               break label274;
            }
         }

         if (var11) {
            if (r.isDebugEnabled()) {
               r.debug(a("\u0019BG3|\u0018D%!p\u0019^J%p\u0018W%9f\u000e=J%P8~\u0004\u0014\\%`J2[,n\u0004\u0012\u0015\th\u0005\u0003FdS\u0003\u001aP"));
            }

            var14.setEngineBoots(0);
            var14.setLastEngineTime(0);
         }
      }

      if (var14 == null) {
         throw new SnmpSecurityCoderException(3, var10.toString(), var12.isReportable());
      } else {
         USMLocalizedUserData var15 = null;
         if (!var11) {
            String var16 = new String(var6);
            if (var13 != null) {
               var15 = var13.getUserData(var14, var16);
            }

            if (var15 == null) {
               var15 = this.a(var16, var10);
            }

            if (var15 == null) {
               var15 = var14.getUserData(var16);
            }

            if (var15 == null) {
               USMUser var17 = null;
               if (var13 != null) {
                  var17 = var13.getUser(var10, var16);
               }

               if (var17 == null && this.g != null) {
                  var17 = this.g.getUser(var16);
               }

               if (var17 != null) {
                  label287: {
                     var15 = new USMLocalizedUserData(var17, var10);
                     if (var13 != null) {
                        var13.addUserData(var14, var15);
                        if (!var19) {
                           break label287;
                        }
                     }

                     var14.addUserData(var15);
                  }
               }
            }

            if (var15 == null && this.c != null) {
               this.c.logAuthOperation(new String(var6), 3, 1, -1, 0);
            }

            if (var12.isAuth() && var15 == null) {
               throw new SnmpSecurityCoderException(4, var16, var12.isReportable());
            }
         }

         if (var15 != null) {
            byte var22 = var12.getFlags();
            if ((var22 & 2) > 0 && !var15.hasPriv()) {
               throw new SnmpSecurityCoderException(2, a(";u\u0003\u0001"), var12.isReportable());
            }

            if ((var22 & 1) > 0 && !var15.hasAuth()) {
               throw new SnmpSecurityCoderException(2, a("*r\u001e\u001f"), var12.isReportable());
            }
         }

         if (var12.isAuth() && var15 != null) {
            if (var8.length != 12) {
               throw new SnmpSecurityCoderException(6, a("<u\u0005\u0019Rkc\u0003\u0010P8sJ\u001bP%`\u001e\u001f\u000fk") + var8.length, var12.isReportable());
            }

            if (!this.t) {
               this.a(var1, var7, var15, var8, var12, var2);
            }
         }

         label191: {
            if (var12.isAuth()) {
               if (this.isAuthoritative(var3)) {
                  r.debug(a("*r\u001e\u0018G\"s\u000b\u0003\\=bJ\u0012[,n\u0004\u0012\u0015(o\u000f\u0014^"));
                  this.a(true, var14.getEngineID(), var14.getEngineBoots(), var14.getEngineTime(), var4, var5, var12, var6);
                  if (!var19) {
                     break label191;
                  }
               }

               r.debug(a("%h\u0004ZT>s\u0005\u0005\\?f\u001e\u001eC.'\u000f\u0019R\"i\u000fWV#b\t\u001c"));

               try {
                  this.a(false, var14.getEngineID(), var14.getEngineBoots(), var14.getEngineTime(), var4, var5, var12, var6);
                  this.a(var4, var5, var14, var2.getMsgID());
               } catch (SnmpSecurityCoderException var21) {
                  boolean var24 = false;
                  if (this.s != null) {
                     r.debug(a("\"i\u001c\u0018^\"i\rW{$s#\u0019a\"j\u000f \\%c\u0005\u0000F\u0007n\u0019\u0003P%b\u0018WA$'\u0019\u0012Pkn\fWA#n\u0019WF#h\u001f\u001bQke\u000fWZ=b\u0018\u0005\\/c\u000f\u0019"));

                     try {
                        if (this.s.handleNotInTimeWindow(false, var14.getEngineID(), var14.getEngineBoots(), var14.getEngineTime(), var4, var5, var12, var6)) {
                           r.debug(a("*w\u001a\u001b\\(f\u001e\u001eZ%'\u0005\u0001P9u\u0003\u0013Q\"i\rWQ.a\u000b\u0002Y?'\u001e\u001eX.k\u0003\u0019P8tJ\u0014].d\u0001"));
                           var24 = true;
                           var14.a();
                           this.a(var4, var5, var14, var2.getMsgID());
                        }
                     } catch (Exception var20) {
                        r.debug(a(".u\u0018\u0018Gkn\u0004WZ=b\u0018\u0005\\/bJ\u0014].d\u0001"), var20);
                     }
                  }

                  if (!var24) {
                     int var18 = var14.c() + 1;
                     var14.b(var18);
                     if (r.isDebugEnabled()) {
                        r.debug(a("\"i\t\u0005P&b\u0004\u0003\\%`J\u0012[,n\u0004\u0012\u0015%h\u001e>[\u001fn\u0007\u0012b\"i\u000e\u0018B8D\u0005\u0002[?b\u0018M\u0015") + var18);
                     }

                     if (var14.getAutoTimeResyncThreshold() > 0 && var18 > var14.getAutoTimeResyncThreshold()) {
                        if (r.isWarnEnabled()) {
                           r.warn(a(".i\r\u001e[.'\u0002\u0016Fk`\u000f\u0019P9f\u001e\u0012Qk") + var18 + a("kt\u000f\u0006@.i\t\u001eT''\u0004\u0018A\u0002i>\u001eX.P\u0003\u0019Q$p\u0019WP9u\u0005\u0005Fg'\u001e\u001eX.'\u001e\u0018\u0015-h\u0018\u0014PkfJ\u0003\\&bG\u0004L%d\u0002\u0005Z%n\u0010\u0016A\"h\u0004Wn?o\u0018\u0012F#h\u0006\u0013\b") + var14.getAutoTimeResyncThreshold() + "]");
                        }

                        var14.a();
                     }

                     throw var21;
                  }
               }

               var14.b(0);
               if (!var19) {
                  break label191;
               }
            }

            if (var11 && !this.isAuthoritative(var3)) {
               this.a(var4, var5, var14, var2.getMsgID());
            }
         }

         USMSecurityParameters var23 = new USMSecurityParameters();
         var23.setAuthoritativeEngineID(var3);
         var23.setAuthoritativeEngineBoots(var4);
         var23.setAuthoritativeEngineTime(var5);
         var23.setUserName(var6);
         var23.setAuthenticationParameters(var8);
         var23.setPrivacyParameters(var9);
         var23.setSecurityLevel(var12.getFlags() & 3);
         var2.isDiscovery(var11);
         return var23;
      }
   }

   private void a(int var1, int var2, USMEngineInfo var3, int var4) {
      int var5 = var3.getEngineBoots();
      int var6 = var3.getEngineTime();
      int var7 = var3.getLastEngineTime();
      if (r.isDebugEnabled()) {
         r.debug(a("8~\u0004\u0014]9h\u0004\u001eO.S\u0003\u001aPq|\u000b\u0002A#E\u0005\u0018A8:") + var1 + a("gf\u001f\u0003]\u001fn\u0007\u0012\b") + var2 + a("gb\u0004\u0010\\%b(\u0018Z?tW") + var5 + a("gb\u0004\u0010\\%b>\u001eX.:") + var6 + a("gk\u000b\u0003P8s8\u0012V.n\u001c\u0012Qv") + var7 + "}");
      }

      if (var1 > var5 || var1 == var5 && var2 > var7 || (long)var4 == var3.b()) {
         var3.a(-1);
         var3.setEngineBoots(var1);
         var3.setLastEngineTime(var2);
         if (!r.isDebugEnabled()) {
            return;
         }

         r.debug(a("8~\u0004\u0014]9h\u0004\u001eO.S\u0003\u001aPqs\u0018\u0002P"));
         r.debug(a(">w\u000e\u0016A.cJ\u0012[,n\u0004\u0012\u0015\"i\f\u0018\u0015q") + var3);
         if (!USMLocalizedUserData.k) {
            return;
         }
      }

      if (r.isDebugEnabled()) {
         r.debug(a("8~\u0004\u0014]9h\u0004\u001eO.S\u0003\u001aPqa\u000b\u001bF."));
      }

   }

   private void a(boolean var1, SnmpEngineID var2, int var3, int var4, int var5, int var6, V3SnmpMessageParameters var7, byte[] var8) throws SnmpCoderException {
      if (r.isDebugEnabled()) {
         r.debug(a("(o\u000f\u0014^\u001fn\u0007\u0012Y\"i\u000f\u0004Fq|\u000f\u0019R\"i\u000f>qv") + var2 + a("*r\u001e\u001fw$h\u001e\u0004\b") + var5 + a("gf\u001f\u0003]\u001fn\u0007\u0012\b") + var6 + a("gb\u0004\u0010\\%b(\u0018Z?tW") + var3 + a("gb\u0004\u0010\\%b>\u001eX.:") + var4 + "}");
      }

      if (var1) {
         if ((long)var3 == 2147483647L || var5 != var3 || var4 - 150 > var6 || var4 + 150 < var6) {
            throw new SnmpSecurityCoderException(5, var7.isReportable(), var7.getFlags() & 1, var8);
         }

         if (var5 == 0 && var6 == 0 && isNotInTimeWindowOnZerosEnabled()) {
            r.comms(a("?n\u0007\u0012\u00158~\u0004\u0014]9h\u0004\u001eO*s\u0003\u0018[kh\u001a\u0012G*s\u0003\u0018[g'\u0018\u0012E$u\u001e\u001e[,"));
            throw new SnmpSecurityCoderException(5, var7.isReportable(), var7.getFlags() & 1, var8);
         }
      } else if ((long)var3 == 2147483647L || var5 <= var3 && (var5 != var3 || var4 - 150 > var6)) {
         throw new SnmpSecurityCoderException(5, var7.isReportable(), var7.getFlags() & 1, var8);
      }

   }

   private void a(BERBuffer var1, int var2, USMLocalizedUserData var3, byte[] var4, V3SnmpMessageParameters var5, SnmpMessage var6) throws SnmpCoderException {
      byte[] var7 = var1.getBytes();
      System.arraycopy(k, 0, var7, var2 + 2, 12);
      byte[] var8 = var3.getK1();
      byte[] var9 = var3.getK2();
      USMAuthModule.Context var10 = new USMAuthModule.Context();
      var10.setK1(var8);
      var10.setK2(var9);
      var10.setAuthParams(var4);
      USMAuthModule var11 = this.getAuthModule(var3.getAuthProtocol());
      if (var11 == null) {
         r.error(a("\u001eI9\"e\u001bH8#p\u000fX92v\u001eU##l\u0014J%3p\u0007=J\u001e[=f\u0006\u001eQkf\u001f\u0003]kw\u0018\u0018A$d\u0005\u001b\u000fk") + var3.getAuthProtocol());
         throw new SnmpSecurityCoderException(1, a("\"i\u001c\u0016Y\"cJ\u0016@?oJ\u0007G$s\u0005\u0014Z'"), false);
      } else if (var3.getLocalizedAuthKey() == null) {
         r.error(a("&n\u0019\u0004\\%`J\u001bZ(f\u0006\u001eO.cJ\u0016@?oJ\u001cP2"));
         throw new SnmpSecurityCoderException(2, a("*r\u001e\u001f"));
      } else {
         SnmpBuffer var12 = new SnmpBuffer();
         var12.data = var1.getBytes();
         var12.offset = 0;
         var12.length = var1.getLength();
         boolean var13 = false;

         try {
            var13 = var11.authenticateIncoming(var3.getLocalizedAuthKey(), var10, var2 + 2, var12);
         } catch (SnmpSecurityCoderException var22) {
            throw var22;
         } finally {
            if (this.c != null) {
               try {
                  this.c.logAuthOperation(var3.getName(), 3, 1, var3.getAuthProtocol(), var13 ? 1 : 0);
               } catch (Throwable var21) {
                  r.error(a(".\u007f\t\u0012E?n\u0005\u0019\u0015\"iJ\u0016@/n\u001eWA9f\u0003\u001b\u0015'h\r\u0010\\%`"), var21);
               }
            }

         }

         if (!var13) {
            throw new SnmpSecurityCoderException(6, a("\u0006F)WX\"t\u0007\u0016A(o"), var5.isReportable());
         }
      }
   }

   public void authenticateOutgoing(BERBuffer var1, SnmpMessage var2, int var3) throws SnmpCoderException, BERException {
      boolean var20 = USMLocalizedUserData.k;
      boolean var4 = var3 >= 0;
      SnmpMessageProfile var5 = var2.getMessageProfile();
      String var6 = var5.getSecurityName();
      if (var6 != null && !"".equals(var6) || var4) {
         SnmpEngineID var7 = var2.getSnmpEngineID();
         USMSecurityDB var8 = this.h;
         USMEngineInfo var9 = null;
         if (var8 != null) {
            var9 = var8.getEngineInfo(var7);
         }

         if (var9 == null) {
            var9 = this.f.get(var7);
         }

         if (var9 == null && var7 != null && !this.isAuthoritative(var7)) {
            label278: {
               if (var8 != null) {
                  if (r.isDebugEnabled()) {
                     r.debug(a("\u001eT'$P(r\u0018\u001eA2C(Y[.p/\u0019R\"i\u000f>[-hPW") + var7);
                  }

                  var9 = var8.newEngineInfo(var7);
                  if (!var20) {
                     break label278;
                  }
               }

               var9 = new USMEngineInfo(var7);
               this.f.add(var9);
               if (r.isDebugEnabled()) {
                  r.debug(a("a'+\u0013Q\"i\rW[.pJ\u0012[,n\u0004\u0012|\u000f=J") + var7);
               }
            }
         }

         if (var9 == null) {
            throw new SnmpSecurityCoderException(3, var7 + a("e'\u000e\u001eF(h\u001c\u0012G2'\u0018\u0012D>n\u0018\u0012Q"));
         } else {
            USMLocalizedUserData var10 = null;
            if (var8 != null) {
               var10 = var8.getUserData(var9, var6);
            }

            if (var10 == null) {
               var10 = this.a(var6, var7);
            }

            if (var10 == null) {
               var10 = var9.getUserData(var6);
            }

            if (var10 == null) {
               label293: {
                  USMUser var11 = null;
                  if (var8 != null) {
                     var11 = var8.getUser(var7, var6);
                  }

                  if (var11 == null) {
                     Object var12 = var2.getUserTable();
                     if (var12 == null) {
                        var12 = this.g;
                     }

                     if (var12 == null) {
                        throw new SnmpSecurityCoderException(4, a("%hJ\u0002F.uJ\u0003T)k\u000fWF.s"));
                     }

                     var11 = (USMUser)((Hashtable)var12).get(var6);
                  }

                  if (var11 == null) {
                     throw new SnmpSecurityCoderException(4, var6);
                  }

                  var10 = new USMLocalizedUserData(var11, var7);
                  if (var8 != null) {
                     var8.addUserData(var9, var10);
                     if (!var20) {
                        break label293;
                     }
                  }

                  var9.addUserData(var10);
               }
            }

            if (var4) {
               if (r.isDebugEnabled()) {
                  r.debug(a(">t\u000f\u0005\b") + var10);
               }

               if (!var10.hasAuth()) {
                  throw new SnmpSecurityCoderException(2, a("*r\u001e\u001f"));
               } else {
                  byte[] var28 = var10.getK1();
                  byte[] var29 = var10.getK2();
                  SnmpBuffer var13 = new SnmpBuffer();
                  var13.data = var1.getRawData();
                  var13.offset = var1.getOffset();
                  var13.length = var1.getLength();
                  USMAuthModule.Context var14 = new USMAuthModule.Context();
                  var14.setK1(var28);
                  var14.setK2(var29);
                  USMAuthModule var15 = this.getAuthModule(var10.getAuthProtocol());
                  if (var15 == null) {
                     r.error(a("\u001eI9\"e\u001bH8#p\u000fX92v\u001eU##l\u0014J%3p\u0007=J\u001e[=f\u0006\u001eQkf\u001f\u0003]kw\u0018\u0018A$d\u0005\u001b\u000fk") + var10.getAuthProtocol());
                     throw new SnmpSecurityCoderException(1, a("\"i\u001c\u0016Y\"cJ\u0016@?oJ\u0007G$s\u0005\u0014Z'"), false);
                  } else if (var10.getLocalizedAuthKey() == null) {
                     r.error(a("&n\u0019\u0004\\%`J\u001bZ(f\u0006\u001eO.cJ\u0007G\"qJ\u001cP2"));
                     throw new SnmpSecurityCoderException(2, a("*r\u001e\u001f"));
                  } else {
                     boolean var16 = false;

                     try {
                        var15.authenticateOutgoing(var10.getLocalizedAuthKey(), var14, var3, var13);
                        var16 = true;
                     } catch (SnmpSecurityCoderException var26) {
                        throw var26;
                     } finally {
                        if (this.c != null) {
                           try {
                              this.c.logAuthOperation(var6, 3, 2, var10.getAuthProtocol(), var16 ? 1 : 0);
                           } catch (Throwable var25) {
                              r.error(a(".\u007f\t\u0012E?n\u0005\u0019\u0015\"iJ\u0016@/n\u001eWA9f\u0003\u001b\u0015'h\r\u0010\\%`"), var25);
                           }
                        }

                     }

                  }
               }
            }
         }
      }
   }

   public byte[] encryptScopedPDU(BERBuffer var1, SnmpMessage var2) throws SnmpCoderException, BERException {
      return null;
   }

   byte[] a(BERBuffer var1, SnmpMessage var2, USMEngineInfo var3, long var4, long var6) throws SnmpCoderException, BERException {
      SnmpMessageProfile var8 = var2.getMessageProfile();
      SnmpEngineID var9 = var2.getSnmpEngineID();
      if (var3 == null) {
         throw new SnmpSecurityCoderException(3, var9 + a("e'\u000e\u001eF(h\u001c\u0012G2'\u0018\u0012D>n\u0018\u0012Qe"));
      } else {
         String var10 = var8.getSecurityName();
         USMLocalizedUserData var11 = null;
         USMSecurityDB var12 = this.h;
         if (var12 != null) {
            var11 = var12.getUserData(var3, var10);
         }

         if (var11 == null) {
            var11 = this.a(var10, var9);
         }

         if (var11 == null) {
            var11 = var3.getUserData(var10);
         }

         if (var11 == null) {
            label207: {
               USMUser var13 = null;
               if (var12 != null) {
                  var13 = var12.getUser(var9, var10);
               }

               if (var13 == null) {
                  Object var14 = var2.getUserTable();
                  if (var14 == null) {
                     var14 = this.g;
                  }

                  if (var14 == null) {
                     throw new SnmpSecurityCoderException(4, a("%hJ\u0002F.uJ\u0003T)k\u000fWF.s"));
                  }

                  var13 = (USMUser)((Hashtable)var14).get(var10);
               }

               if (var13 == null) {
                  throw new SnmpSecurityCoderException(4, var10);
               }

               var11 = new USMLocalizedUserData(var13, var9);
               if (var12 != null) {
                  var12.addUserData(var3, var11);
                  if (!USMLocalizedUserData.k) {
                     break label207;
                  }
               }

               var3.addUserData(var11);
            }
         }

         if (r.isDebugEnabled()) {
            r.debug(a("\u000eI)%l\u001bSJ8`\u001f@%>{\f"));
            r.debug(a("f*J\u0002F.u$\u0016X.=JW\u0015k'") + var11.getName());
         }

         if (!var11.hasPriv()) {
            throw new SnmpSecurityCoderException(2, a(";u\u0003\u0001"));
         } else {
            USMPrivModule var30 = this.getPrivModule(var11.getPrivProtocol());
            if (var30 == null) {
               r.error(a("\u001eI9\"e\u001bH8#p\u000fX92v\u001eU##l\u0014J%3p\u0007=J\u001e[=f\u0006\u001eQkw\u0018\u001eCkw\u0018\u0018A$d\u0005\u001b\u000fk") + var11.getPrivProtocol());
               throw new SnmpSecurityCoderException(1, a("\"i\u001c\u0016Y\"cJ\u0007G\"qJ\u0007G$s\u0005\u0014Z'"), false);
            } else {
               byte[] var31 = var11.getLocalizedPrivKey();
               if (var31 == null) {
                  r.error(a("&n\u0019\u0004\\%`J\u001bZ(f\u0006\u001eO.cJ\u0007G\"qJ\u001cP2"));
                  throw new SnmpSecurityCoderException(1, a("%hJ\u0007G\"qJ\u001cP2'\u001a\u0005Z=n\u000e\u0012Q"), false);
               } else {
                  USMPrivModule.Context var15 = new USMPrivModule.Context(var4, var6);
                  SnmpBuffer var16 = new SnmpBuffer();
                  var16.data = var1.getBytes();
                  var16.offset = 0;
                  var16.length = var16.data.length;
                  SnmpBuffer var17 = new SnmpBuffer();
                  SnmpBuffer var18 = new SnmpBuffer();
                  boolean var19 = false;

                  try {
                     var30.encryptData(var31, var15, var16, var17, var18);
                     var19 = true;
                  } catch (SnmpSecurityCoderException var28) {
                     throw var28;
                  } finally {
                     if (this.c != null) {
                        try {
                           this.c.logPrivOperation(var10, 3, 2, var11.getPrivProtocol(), var19 ? 1 : 0);
                        } catch (Throwable var27) {
                           r.error(a(".\u007f\t\u0012E?n\u0005\u0019\u0015\"iJ\u0016@/n\u001eWA9f\u0003\u001b\u0015'h\r\u0010\\%`"), var27);
                        }
                     }

                  }

                  if (r.isDebugEnabled()) {
                     r.debug(a("\u000eI)%l\u001bS/3\u0015\tR,1p\u0019=`") + var17);
                  }

                  BERBuffer var20 = new BERBuffer();
                  BERCoder.encodeString(var20, var17.data, 4);
                  var1.setFrom(var20);
                  return var18.data;
               }
            }
         }
      }
   }

   public BERBuffer decryptScopedPDU(BERBuffer var1, SnmpMessage var2) throws SnmpCoderException {
      V3SnmpMessageParameters var3;
      USMSecurityParameters var4;
      long var5;
      long var7;
      SnmpEngineID var10;
      boolean var27;
      label290: {
         var27 = USMLocalizedUserData.k;
         var3 = (V3SnmpMessageParameters)var2.getMessageParameters();
         var4 = (USMSecurityParameters)var2.getSecurityParameters();
         var5 = (long)var4.getAuthoritativeEngineBoots();
         var7 = (long)var4.getAuthoritativeEngineTime();
         SnmpMessageProfile var9 = var2.getMessageProfile();
         if (this.isAuthoritative(var2.getSnmpEngineID())) {
            var10 = this.e;
            if (!var27) {
               break label290;
            }
         }

         var10 = var2.getSnmpEngineID();
      }

      USMEngineInfo var11 = null;
      USMSecurityDB var12 = this.h;
      if (var12 != null) {
         var11 = var12.getEngineInfo(var10);
      }

      if (var11 == null) {
         var11 = this.f.get(var10);
      }

      if (var11 == null) {
         throw new SnmpSecurityCoderException(3, var10 + a("e'\u000e\u001eF(h\u001c\u0012G2'\u0018\u0012D>n\u0018\u0012Q"), var3.isReportable());
      } else {
         String var13 = new String(var4.getUserName());
         USMLocalizedUserData var14 = null;
         if (var12 != null) {
            var14 = var12.getUserData(var11, var13);
         }

         if (var14 == null) {
            var14 = this.a(var13, var10);
         }

         if (var14 == null) {
            var14 = var11.getUserData(var13);
         }

         if (var14 == null) {
            label293: {
               if (this.isAuthoritative(var2.getSnmpEngineID())) {
                  throw new SnmpSecurityCoderException(4, var13, var3.isReportable());
               }

               USMUser var15 = null;
               if (var12 != null) {
                  var15 = var12.getUser(var10, var13);
               }

               if (var15 == null) {
                  Object var16 = var2.getUserTable();
                  if (var16 == null) {
                     var16 = this.g;
                  }

                  if (var16 == null) {
                     throw new SnmpSecurityCoderException(4, a("%hJ\u0002F.uJ\u0003T)k\u000fWF.s"), var3.isReportable());
                  }

                  var15 = (USMUser)((Hashtable)var16).get(var13);
               }

               if (var15 == null) {
                  throw new SnmpSecurityCoderException(4, var13, var3.isReportable());
               }

               var14 = new USMLocalizedUserData(var15, var10);
               if (var12 != null) {
                  var12.addUserData(var11, var14);
                  if (!var27) {
                     break label293;
                  }
               }

               var11.addUserData(var14);
            }
         }

         if (!var14.hasPriv()) {
            throw new SnmpSecurityCoderException(2, a(";u\u0003\u0001"), var3.isReportable());
         } else {
            byte[] var35 = var14.getLocalizedPrivKey();
            if (r.isDebugEnabled()) {
               r.debug(a("\u000fB)%l\u001bSJ>{\bH'>{\f"));
               r.debug(a("f*J\u0002F.u$\u0016X.=JW\u0015k'") + var14.getName());
            }

            byte[] var36 = ((USMSecurityParameters)var2.getSecurityParameters()).getPrivacyParameters();
            USMPrivModule var17 = this.getPrivModule(var14.getPrivProtocol());
            if (var17 == null) {
               throw new SnmpSecurityCoderException(1, a("\"i\u001c\u0016Y\"cJ\u0007G\"qJ\u0007G$s\u0005\u0014Z'"), false);
            } else if (var35 == null) {
               r.error(a("&n\u0019\u0004\\%`J\u001bZ(f\u0006\u001eO.cJ\u0007G\"qJ\u001cP2"));
               throw new SnmpSecurityCoderException(2, a(";u\u0003\u0001"), var3.isReportable());
            } else {
               USMPrivModule.Context var18 = new USMPrivModule.Context(var5, var7);
               SnmpBuffer var19 = new SnmpBuffer(var1.getBytes());
               SnmpBuffer var20 = new SnmpBuffer(var36);
               SnmpBuffer var21 = new SnmpBuffer();
               boolean var22 = false;

               try {
                  var17.decryptData(var35, var18, var19, var20, var21);
                  var22 = true;
               } catch (SnmpSecurityCoderException var33) {
                  throw var33;
               } finally {
                  if (this.c != null) {
                     if (var22 && (var21.data == null || var21.data.length == 0 || var21.data[0] != 48)) {
                        var22 = false;
                     }

                     try {
                        this.c.logPrivOperation(var13, 3, 1, var14.getPrivProtocol(), var22 ? 1 : 0);
                     } catch (Throwable var32) {
                        r.error(a(".\u007f\t\u0012E?n\u0005\u0019\u0015\"iJ\u0016@/n\u001eWA9f\u0003\u001b\u0015'h\r\u0010\\%`"), var32);
                     }
                  }

               }

               byte[] var23 = var21.data;
               if (r.isDebugEnabled()) {
                  r.debug(a("\u000fB)%l\u001bS/3\u0015\tR,1p\u0019=") + SnmpBuffer.BytesToString(var23, 0, var23.length));
               }

               BERBuffer var24 = new BERBuffer(var23, true);
               return var24;
            }
         }
      }
   }

   private boolean a() {
      return this.e == null ? false : this.e.isAuthoritative();
   }

   public boolean isAuthoritative(SnmpEngineID var1) {
      return var1 == null ? this.a() : this.isAuthoritative(var1.getValue());
   }

   public boolean isAuthoritative(byte[] var1) {
      if (this.e == null) {
         return false;
      } else if (var1 == null) {
         return true;
      } else {
         return this.e.isAuthoritative() && this.e.equals(var1);
      }
   }

   public void setDefaultUserTable(USMUserTable var1) {
      this.g = var1;
   }

   public USMUserTable getDefaultUserTable() {
      return this.g;
   }

   public USMEngineMap getEngineMap() {
      return this.f;
   }

   public USMSecurityDB getSecurityDB() {
      return this.h;
   }

   public void setSecurityDB(USMSecurityDB var1) {
      this.h = var1;
   }

   public void setUsmUserSecurityExtension(UsmUserSecurityExtension var1) {
      this.d = var1;
   }

   public UsmUserSecurityExtension getUsmUserSecurityExtension() {
      return this.d;
   }

   private USMLocalizedUserData a(String var1, SnmpEngineID var2) {
      if (this.d != null) {
         r.debug(a("(o\u000f\u0014^\"i\rWS$uJ\u0002F.uJ\u0013T?fJ\u0011G$jJ\"F&R\u0019\u0012G\u0018b\t\u0002G\"s\u00132M?b\u0004\u0004\\$i"));
         UsmUserSecurityExtension.UserInfo var3 = this.d.getUserInfo(var1, var2);
         if (var3 == null) {
            return null;
         } else {
            r.debug(a("-h\u001f\u0019QkR\u0019\u0012G\u0002i\f\u0018\u0015-h\u0018W@8b\u0018M") + var1);
            USMLocalizedUserData var4 = new USMLocalizedUserData(var3.getUserName(), var3.getSecLevel(), var3.getAuthProtocol(), var3.getPrivProtocol(), var3.getLocalizedAuthKey(), var3.getLocalizedPrivKey());
            return var4;
         }
      } else {
         return null;
      }
   }

   public void addPrivModule(int var1, USMPrivModule var2) {
      this.i.put(new Integer(var1), var2);
   }

   public USMPrivModule getPrivModule(int var1) {
      USMPrivModule var2 = (USMPrivModule)this.i.get(new Integer(var1));
      return var2;
   }

   public void addAuthModule(int var1, USMAuthModule var2) {
      this.j.put(new Integer(var1), var2);
   }

   public USMAuthModule getAuthModule(int var1) {
      USMAuthModule var2 = (USMAuthModule)this.j.get(new Integer(var1));
      return var2;
   }

   public UsmSecurityAuditTrailLogger getAuditTrailLogger() {
      return this.c;
   }

   public void setAuditTrailLogger(UsmSecurityAuditTrailLogger var1) {
      this.c = var1;
   }

   public void setNotInTimeWindowListener(USMNotInTimeWindowListener var1) {
      this.s = var1;
   }

   static {
      USMAuthProtocolSpec.addSpec(new e());
      USMAuthProtocolSpec.addSpec(new f());
      USMPrivProtocolSpec.addSpec(new p());
      USMPrivProtocolSpec.addSpec(new m());
      USMPrivProtocolSpec.addSpec(new n());
      USMPrivProtocolSpec.addSpec(new o());
      USMPrivProtocolSpec.addSpec(new q());
      k = new byte[12];
      l = new byte[0];
      m = new byte[0];
      n = new byte[0];
      r = null;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 75;
               break;
            case 1:
               var10003 = 7;
               break;
            case 2:
               var10003 = 106;
               break;
            case 3:
               var10003 = 119;
               break;
            default:
               var10003 = 53;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
