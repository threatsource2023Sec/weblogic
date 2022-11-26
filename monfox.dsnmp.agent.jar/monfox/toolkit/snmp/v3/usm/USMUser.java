package monfox.toolkit.snmp.v3.usm;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValueException;

public class USMUser implements Serializable {
   static final long serialVersionUID = -2075395021113600600L;
   static Logger a = null;
   private String b;
   private byte[] c;
   private byte[] d;
   private byte[] e;
   private int f;
   private int g;
   private int h;
   private static final String i = "$Id: USMUser.java,v 1.16 2007/03/23 04:12:00 sking Exp $";

   public USMUser(String var1) throws NoSuchAlgorithmException {
      this(var1, (byte[])(new byte[0]), (byte[])(new byte[0]), 0);
   }

   public USMUser(String var1, byte[] var2) throws NoSuchAlgorithmException {
      this(var1, (byte[])var2, (byte[])(new byte[0]), 0);
   }

   public USMUser(String var1, String var2) throws NoSuchAlgorithmException {
      this(var1, (byte[])(var2 == null ? new byte[0] : var2.getBytes()), (byte[])(new byte[0]), 0);
   }

   public USMUser(String var1, byte[] var2, int var3) throws NoSuchAlgorithmException {
      this(var1, var2, new byte[0], var3);
   }

   public USMUser(String var1, String var2, int var3) throws NoSuchAlgorithmException {
      this(var1, var2 == null ? new byte[0] : var2.getBytes(), new byte[0], var3);
   }

   /** @deprecated */
   public USMUser(String var1, byte[] var2, byte[] var3) throws NoSuchAlgorithmException {
      this(var1, (byte[])var2, (byte[])var3, 0);
   }

   public USMUser(String var1, String var2, String var3) throws NoSuchAlgorithmException {
      this(var1, (byte[])(var2 == null ? new byte[0] : var2.getBytes()), (byte[])(var3 == null ? new byte[0] : var3.getBytes()), 0);
   }

   public USMUser(String var1, String var2, String var3, int var4, int var5) throws NoSuchAlgorithmException {
      this(var1, var2 == null ? new byte[0] : var2.getBytes(), var3 == null ? new byte[0] : var3.getBytes(), var4, var5);
   }

   /** @deprecated */
   public USMUser(String var1, byte[] var2, byte[] var3, int var4) throws NoSuchAlgorithmException {
      this(var1, (byte[])var2, (byte[])var3, var4, 2);
   }

   public USMUser(String var1, byte[] var2, byte[] var3, int var4, int var5) throws NoSuchAlgorithmException {
      boolean var8 = USMLocalizedUserData.k;
      super();
      this.b = null;
      this.c = new byte[0];
      this.d = null;
      this.e = null;
      this.f = 0;
      this.g = 0;
      this.h = 2;
      synchronized(this) {
         if (a == null) {
            a = Logger.getInstance(a("\u0010\u0005ZGu $"));
         }
      }

      if (var1 == null) {
         throw new NullPointerException(a("0%r`H$;r"));
      } else if (var1.length() == 0) {
         throw new IllegalArgumentException(a("0%r`H$;r"));
      } else {
         this.b = var1;
         this.c = var1.getBytes();
         if (var2 == null || var2.length == 0) {
            this.d = null;
            this.e = null;
            this.f = 0;
            if (!var8) {
               return;
            }
         }

         if (var4 != 0 && var4 != 1) {
            throw new NoSuchAlgorithmException(a("08dgv59efc!vvgr-3yfo&7c{i+vg`i19t}j\u007fv") + var4);
         } else {
            this.g = var4;
            if (var8) {
               throw new NoSuchAlgorithmException(a("08dgv59efc!vvgr-3yfo&7c{i+vg`i19t}j\u007fv") + var4);
            } else {
               this.d = a(var2, var4);
               switch (var5) {
                  case 2:
                  case 4:
                  case 5:
                  case 6:
                  case 14832:
                     if (!var8) {
                        this.h = var5;
                        if (var3 == null || var3.length == 0) {
                           this.e = null;
                           this.f = 1;
                           if (!var8) {
                              break;
                           }
                        }

                        this.e = a(var3, var4);
                        this.f = 3;
                        break;
                     }

                     throw new NoSuchAlgorithmException(a("08dgv59efc!vg`o37tk&5$xfi&9{(&") + var5);
                  default:
                     throw new NoSuchAlgorithmException(a("08dgv59efc!vg`o37tk&5$xfi&9{(&") + var5);
               }

            }
         }
      }
   }

   public static USMUser createKeyUser(String var0, String var1, String var2, int var3) throws NoSuchAlgorithmException, SnmpValueException {
      return createKeyUser(var0, (String)var1, (String)var2, var3, 2);
   }

   public static USMUser createKeyUser(String var0, String var1, String var2, int var3, int var4) throws NoSuchAlgorithmException, SnmpValueException {
      boolean var7 = USMLocalizedUserData.k;
      USMUser var5 = new USMUser(var0, (byte[])null, (byte[])null, var3, var4);
      SnmpString var6;
      if (var1 != null) {
         var6 = new SnmpString();
         var6.fromHexString(var1);
         var5.d = var6.toByteArray();
      }

      if (var2 != null) {
         var6 = new SnmpString();
         var6.fromHexString(var2);
         var5.e = var6.toByteArray();
      }

      var5.g = var3;
      var5.h = var4;
      if (var5.d == null) {
         var5.e = null;
         var5.f = 0;
         if (!var7) {
            return var5;
         }
      }

      if (var5.e == null) {
         var5.f = 1;
         if (!var7) {
            return var5;
         }
      }

      var5.f = 3;
      return var5;
   }

   public static USMUser createKeyUser(String var0, byte[] var1, byte[] var2, int var3) throws NoSuchAlgorithmException {
      return createKeyUser(var0, (byte[])var1, (byte[])var2, var3, 2);
   }

   public static USMUser createKeyUser(String var0, byte[] var1, byte[] var2, int var3, int var4) throws NoSuchAlgorithmException {
      boolean var6 = USMLocalizedUserData.k;
      USMUser var5 = new USMUser(var0, (byte[])null, (byte[])null, var3, var4);
      var5.d = var1;
      var5.e = var2;
      var5.g = var3;
      var5.h = var4;
      if (var5.d == null) {
         var5.e = null;
         var5.f = 0;
         if (!var6) {
            return var5;
         }
      }

      if (var5.e == null) {
         var5.f = 1;
         if (!var6) {
            return var5;
         }
      }

      var5.f = 3;
      return var5;
   }

   public USMUser(String var1, String var2, String var3, int var4) throws NoSuchAlgorithmException {
      this(var1, var2 == null ? new byte[0] : var2.getBytes(), var3 == null ? new byte[0] : var3.getBytes(), var4);
   }

   public String getName() {
      return this.b;
   }

   public byte[] getNameBytes() {
      return this.c;
   }

   public byte[] getPrivKey() {
      return this.e;
   }

   public byte[] getAuthKey() {
      return this.d;
   }

   public int getAuthProtocol() {
      return this.g;
   }

   public String getAuthProtocolName() {
      return AuthProtocolToString(this.g);
   }

   public int getPrivProtocol() {
      return this.h;
   }

   public String getPrivProtocolName() {
      return PrivProtocolToString(this.h);
   }

   public int getSecurityLevel() {
      return this.f;
   }

   public boolean hasAuth() {
      return (this.f & 1) != 0;
   }

   public boolean hasPriv() {
      return (this.f & 2) != 0;
   }

   public byte[] generateLocalizedAuthKey(byte[] var1) {
      if (!this.hasAuth()) {
         return null;
      } else {
         try {
            return a(this.d, var1, this.g, -1);
         } catch (NoSuchAlgorithmException var3) {
            throw new IllegalArgumentException(a("+9Dge-\u0017{ui7?czk\u007f") + this.g);
         }
      }
   }

   public byte[] generateLocalizedPrivKey(byte[] var1) {
      if (!this.hasPriv()) {
         return null;
      } else {
         try {
            byte[] var2 = a(this.e, var1, this.g, this.h);
            return var2;
         } catch (NoSuchAlgorithmException var4) {
            throw new IllegalArgumentException(a("+9Dge-\u0017{ui7?czk\u007f") + this.g);
         }
      }
   }

   public void setAuthKey(byte[] var1) {
      this.d = var1;
   }

   public void setPrivKey(byte[] var1) {
      this.e = var1;
   }

   private static byte[] a(byte[] var0, int var1) throws NoSuchAlgorithmException {
      boolean var10 = USMLocalizedUserData.k;
      String var2 = AuthProtocolToString(var1);
      int var3 = var0.length;
      int var4 = 1048576;
      int var5 = var4 / var3;
      int var6 = var4 % var3;
      MessageDigest var7 = null;
      String var8 = SnmpFramework.getSecurityProviderName();
      if (var8 != null) {
         try {
            var7 = MessageDigest.getInstance(var2, var8);
         } catch (Exception var11) {
            a.debug(a("\u00023ywt$\"rYc<l7bt* ~vc7vr`t*$-2") + var11);
         }
      }

      if (var7 == null) {
         var7 = MessageDigest.getInstance(var2);
      }

      int var9 = 0;

      while(true) {
         if (var9 < var5) {
            var7.update(var0);
            ++var9;
            if (var10) {
               break;
            }

            if (!var10) {
               continue;
            }
         }

         var7.update(var0, 0, var6);
         break;
      }

      byte[] var12 = var7.digest();
      return var12;
   }

   private static byte[] a(byte[] var0, byte[] var1, int var2, int var3) throws NoSuchAlgorithmException {
      String var4 = AuthProtocolToString(var2);
      MessageDigest var5 = null;
      String var6 = SnmpFramework.getSecurityProviderName();
      if (var6 != null) {
         try {
            var5 = MessageDigest.getInstance(var4, var6);
         } catch (Exception var10) {
            a.debug(a("\t9tsj,,rYc<l7bt* ~vc7vr`t*$-2") + var10);
         }
      }

      if (var5 == null) {
         var5 = MessageDigest.getInstance(var4);
      }

      var5.reset();
      var5.update(var0);
      var5.update(var1);
      var5.update(var0);
      byte[] var7 = var5.digest();
      byte[] var8 = var7;
      USMPrivProtocolSpec var9 = USMPrivProtocolSpec.getSpec(var3);
      if (var9 != null) {
         var8 = var9.extendLocalizedKey(var7, var1, var2);
      }

      return var8;
   }

   public void setSecurityLevel(int var1) {
      this.f = var1;
   }

   public String toString() {
      boolean var2 = USMLocalizedUserData.k;
      StringBuffer var1 = new StringBuffer();
      var1.append(a("\u0010\u0005ZGu $-i"));
      var1.append(a("0%r`H$;r/")).append(this.b);
      switch (this.f) {
         case 0:
            var1.append(a("i%rqJ  r~;")).append(a("+9Vgr-\u0018xBt, "));
            if (!var2) {
               break;
            }
         case 1:
            var1.append(a("i%rqJ  r~;")).append(a("$#czH*\u0006e{p"));
            if (!var2) {
               break;
            }
         case 3:
            var1.append(a("i%rqJ  r~;")).append(a("$#czV7?a"));
            if (!var2) {
               break;
            }
         case 2:
         default:
            var1.append(a("i%rqJ  r~;")).append(a("zi("));
      }

      var1.append(a("i7bfn\u0015$xfi&9{/")).append(AuthProtocolToString(this.g));
      var1.append("}");
      return var1.toString();
   }

   public static String AuthProtocolToString(int var0) {
      switch (var0) {
         case 0:
            return a("\b\u0012\"");
         case 1:
            return a("\u0016\u001eV");
         default:
            return "" + var0;
      }
   }

   public static String PrivProtocolToString(int var0) {
      switch (var0) {
         case 2:
            return a("\u0001\u0013D");
         case 4:
            return a("\u0004\u0013D?7wn");
         case 5:
            return a("\u0004\u0013D?7|d");
         case 6:
            return a("\u0004\u0013D?4p`");
         case 14832:
            return a("v\u0012RA");
         default:
            return a("\u0010\u0018\\\\I\u0012\u0018:") + var0;
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
               var10003 = 69;
               break;
            case 1:
               var10003 = 86;
               break;
            case 2:
               var10003 = 23;
               break;
            case 3:
               var10003 = 18;
               break;
            default:
               var10003 = 6;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
