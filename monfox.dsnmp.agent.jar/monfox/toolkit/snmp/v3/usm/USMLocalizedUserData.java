package monfox.toolkit.snmp.v3.usm;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.util.ByteFormatter;

public class USMLocalizedUserData implements Serializable {
   private String a;
   private int b;
   private int c;
   private int d;
   private byte[] e;
   private byte[] f;
   private byte[] g;
   private byte[] h;
   private static Logger i = null;
   private static final String j = "$Id: USMLocalizedUserData.java,v 1.17 2004/12/20 22:25:37 sking Exp $";
   public static boolean k;

   public USMLocalizedUserData(USMUser var1, SnmpEngineID var2) {
      this.d = 2;
      this.e = null;
      this.f = null;
      this.g = null;
      this.h = null;
      if (i == null) {
         i = Logger.getInstance(a("+Ix+F\u001d{Y\u000eS\u001b~`\u0014L\f^T\u0013H"));
      }

      this.a = var1.getName();
      this.b = var1.getSecurityLevel();
      if (this.hasAuth()) {
         this.c = var1.getAuthProtocol();
         this.d = var1.getPrivProtocol();
         this.g = var1.generateLocalizedAuthKey(var2.getValue());
         if (this.hasPriv()) {
            this.h = var1.generateLocalizedPrivKey(var2.getValue());
         }
      }

      if (i.isDebugEnabled()) {
         i.debug(a("=Hp&}7TrGe1Yt+\t+Ip5\t:[a&"));
         i.debug(a("S7\u0015\u0012Z\u001bh{\u0006D\u001b \u0015G\t^:\u0015G") + this.a);
         i.debug(a("S7\u0015\u0014L\u001doG\u000e]\u0007VP\u0011L\u0012 \u0015G") + this.b);
         i.debug(a("S7\u0015\u0006\\\nre\u0015F\nuV\bED:\u0015G") + USMUser.AuthProtocolToString(this.c));
         i.debug(a("S7\u0015\u000bF\u001d{Y&\\\nr~\u0002PD:\u0015G") + ByteFormatter.toHexString(this.g));
         i.debug(a("S7\u0015\u000bF\u001d{Y7[\u0017l~\u0002PD:\u0015G") + ByteFormatter.toHexString(this.h));
      }

   }

   public USMLocalizedUserData(String var1, int var2, int var3, byte[] var4, byte[] var5) {
      this(var1, var2, var3, 2, var4, var5);
   }

   public USMLocalizedUserData(String var1, int var2, int var3, int var4, byte[] var5, byte[] var6) {
      this.d = 2;
      this.e = null;
      this.f = null;
      this.g = null;
      this.h = null;
      if (i == null) {
         i = Logger.getInstance(a("+Ix+F\u001d{Y\u000eS\u001b~`\u0014L\f^T\u0013H"));
      }

      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      this.g = var5;
      this.h = var6;
      if (i.isDebugEnabled()) {
         i.debug(a("=Hp&}7TrGe1Yt+\t+Ip5\t:[a&\tVjG\u0002\u0004\u001a\u007fA\u0002[\u0013s[\u0002MW"));
         i.debug(a("S7\u0015\u0012Z\u001bh{\u0006D\u001b \u0015G\t^:\u0015G") + this.a);
         i.debug(a("S7\u0015\u0014L\u001doG\u000e]\u0007VP\u0011L\u0012 \u0015G") + this.b);
         i.debug(a("S7\u0015\u0006\\\nre\u0015F\nuV\bED:\u0015G") + USMUser.AuthProtocolToString(this.c));
         i.debug(a("S7\u0015\u000bF\u001d{Y&\\\nr~\u0002PD:\u0015G") + ByteFormatter.toHexString(this.g));
         i.debug(a("S7\u0015\u000bF\u001d{Y7[\u0017l~\u0002PD:\u0015G") + ByteFormatter.toHexString(this.h));
      }

   }

   public USMLocalizedUserData() {
      this.d = 2;
      this.e = null;
      this.f = null;
      this.g = null;
      this.h = null;
      if (i == null) {
         i = Logger.getInstance(a("+Ix+F\u001d{Y\u000eS\u001b~`\u0014L\f^T\u0013H"));
      }

   }

   public String getName() {
      return this.a;
   }

   public void setName(String var1) {
      this.a = var1;
   }

   public int getAuthProtocol() {
      return this.c;
   }

   public String getAuthProtocolString() {
      return USMUser.AuthProtocolToString(this.c);
   }

   public int getPrivProtocol() {
      return this.d;
   }

   public String getPrivProtocolString() {
      return USMUser.PrivProtocolToString(this.d);
   }

   public int getSecurityLevel() {
      return this.b;
   }

   public boolean hasAuth() {
      return (this.b & 1) != 0;
   }

   public boolean hasPriv() {
      return (this.b & 2) != 0;
   }

   public byte[] getLocalizedPrivKey() {
      return this.h;
   }

   public byte[] getLocalizedAuthKey() {
      return this.g;
   }

   public void setLocalizedAuthKey(String var1) throws SnmpValueException {
      SnmpString var2 = new SnmpString(var1);
      this.setLocalizedAuthKey(var2.toByteArray());
   }

   public void setLocalizedPrivKey(String var1) throws SnmpValueException {
      SnmpString var2 = new SnmpString(var1);
      this.setLocalizedPrivKey(var2.toByteArray());
   }

   public void setLocalizedPrivKey(byte[] var1) {
      i.debug(a("-\u007fA\u0013@\u0010}\u0015\u000bF\u001d{Y\u000eS\u001b~e\u0015@\bQP\u001e"));
      this.h = var1;
      i.debug(a("S7\u0015\u000bF\u001d{Y7[\u0017l~\u0002PD:\u0015G") + ByteFormatter.toHexString(this.h));
   }

   public void setLocalizedAuthKey(byte[] var1) {
      i.debug(a("-\u007fA\u0013@\u0010}\u0015\u000bF\u001d{Y\u000eS\u001b~t\u0012]\u0016QP\u001e"));
      this.g = var1;
      i.debug(a("S7\u0015\u000bF\u001d{Y&\\\nr~\u0002PD:\u0015G") + ByteFormatter.toHexString(this.g));
      this.e = null;
      this.f = null;
   }

   public void setSecurityLevel(int var1) {
      this.b = var1;
   }

   public byte[] getK1() {
      boolean var3 = k;
      byte[] var10000;
      if (this.e == null) {
         byte[] var1 = this.getLocalizedAuthKey();
         if (var1 != null) {
            this.e = new byte[64];
            System.arraycopy(var1, 0, this.e, 0, var1.length);
            int var2 = 0;

            while(var2 < 64) {
               var10000 = this.e;
               if (var3) {
                  return var10000;
               }

               var10000[var2] = (byte)(var10000[var2] ^ 54);
               ++var2;
               if (var3) {
                  break;
               }
            }
         }
      }

      var10000 = this.e;
      return var10000;
   }

   public byte[] getK2() {
      boolean var3 = k;
      byte[] var10000;
      if (this.f == null) {
         byte[] var1 = this.getLocalizedAuthKey();
         if (var1 != null) {
            this.f = new byte[64];
            System.arraycopy(var1, 0, this.f, 0, var1.length);
            int var2 = 0;

            while(var2 < 64) {
               var10000 = this.f;
               if (var3) {
                  return var10000;
               }

               var10000[var2] = (byte)(var10000[var2] ^ 92);
               ++var2;
               if (var3) {
                  break;
               }
            }
         }
      }

      var10000 = this.f;
      return var10000;
   }

   public USMLocalizedUserData clone(String var1) {
      if (var1 == null) {
         throw new NullPointerException();
      } else {
         USMLocalizedUserData var2 = new USMLocalizedUserData();
         var2.a = var1;
         var2.b = this.b;
         var2.c = this.c;
         var2.e = this.e;
         var2.f = this.f;
         var2.g = this.g;
         var2.h = this.h;
         return var2;
      }
   }

   public void clearAuth() {
      if (this.hasAuth()) {
         if (!this.hasPriv()) {
            this.b = 0;
            this.c = 0;
            this.e = null;
            this.f = null;
            this.g = null;
         }
      }
   }

   public void clearPriv() {
      if (this.hasPriv()) {
         this.b = 1;
         this.h = null;
      }
   }

   public boolean authKeyChange(byte[] var1) {
      if (!this.hasAuth()) {
         return false;
      } else {
         byte[] var2 = a(this.g, var1, this.c);
         if (var2 == null) {
            return false;
         } else {
            this.g = var2;
            this.e = null;
            this.f = null;
            return true;
         }
      }
   }

   public boolean validateAuthKeyChange(byte[] var1) {
      return !this.hasAuth() ? false : b(this.g, var1, this.c);
   }

   public boolean privKeyChange(byte[] var1) {
      if (!this.hasPriv()) {
         return false;
      } else {
         byte[] var2 = a(this.h, var1, this.c);
         if (var2 == null) {
            return false;
         } else {
            this.h = var2;
            return true;
         }
      }
   }

   public boolean validatePrivKeyChange(byte[] var1) {
      return !this.hasPriv() ? false : b(this.h, var1, this.c);
   }

   private static byte[] a(byte[] var0, byte[] var1, int var2) {
      boolean var10 = k;
      if (var1 != null && var0 != null) {
         int var3 = var0.length;
         if (var1.length != var3 * 2) {
            return null;
         } else {
            String var4 = USMUser.AuthProtocolToString(var2);
            MessageDigest var5 = null;
            String var6 = SnmpFramework.getSecurityProviderName();
            if (var6 != null) {
               try {
                  var5 = MessageDigest.getInstance(var4, var6);
               } catch (Exception var12) {
               }
            }

            if (var5 == null) {
               try {
                  var5 = MessageDigest.getInstance(var4);
               } catch (NoSuchAlgorithmException var11) {
                  return null;
               }
            }

            var5.reset();
            var5.update(var0);
            var5.update(var1, 0, var3);
            byte[] var7 = var5.digest();
            byte[] var8 = new byte[var3];
            int var9 = 0;

            byte[] var10000;
            while(true) {
               if (var9 < var3) {
                  var10000 = var8;
                  if (var10) {
                     break;
                  }

                  var8[var9] = (byte)(var7[var9] ^ var1[var3 + var9]);
                  ++var9;
                  if (!var10) {
                     continue;
                  }
               }

               var10000 = var8;
               break;
            }

            return var10000;
         }
      } else {
         return null;
      }
   }

   private static boolean b(byte[] var0, byte[] var1, int var2) {
      if (var1 != null && var0 != null) {
         int var3 = var0.length;
         if (var1.length != var3 * 2) {
            return false;
         } else {
            String var4 = USMUser.AuthProtocolToString(var2);
            MessageDigest var5 = null;
            String var6 = SnmpFramework.getSecurityProviderName();
            if (var6 != null) {
               try {
                  var5 = MessageDigest.getInstance(var4, var6);
               } catch (Exception var9) {
               }
            }

            if (var5 == null) {
               try {
                  var5 = MessageDigest.getInstance(var4);
               } catch (NoSuchAlgorithmException var8) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private byte[] a(int var1) {
      return new byte[var1];
   }

   public String toString() {
      boolean var2 = k;
      StringBuffer var1 = new StringBuffer();
      var1.append(a("+Ix+F\u001d{Y\u000eS\u001b~`\u0014L\f N\u0012Z\u001bh{\u0006D\u001b'")).append(this.a);
      var1.append(a("RiP\u0004e\u001blP\u000b\u0014"));
      switch (this.b) {
         case 0:
            var1.append(a("\u0010ut\u0012]\u0016TZ7[\u0017l"));
            if (!var2) {
               break;
            }
         case 1:
            var1.append(a("\u001foA\u000fg\u0011JG\u000e_"));
            if (!var2) {
               break;
            }
         case 3:
            var1.append(a("\u001foA\u000fy\fsC"));
            if (!var2) {
               break;
            }
         case 2:
         default:
            var1.append(a("A%\n"));
      }

      var1.append(a("R{@\u0013A.hZ\u0013F\u001duYZ"));
      var1.append(USMUser.AuthProtocolToString(this.c));
      var1.append(a("RjG\u000e_.hZ\u0013F\u001duYZ"));
      var1.append(USMUser.PrivProtocolToString(this.d));
      var1.append("}");
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
               var10003 = 126;
               break;
            case 1:
               var10003 = 26;
               break;
            case 2:
               var10003 = 53;
               break;
            case 3:
               var10003 = 103;
               break;
            default:
               var10003 = 41;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
