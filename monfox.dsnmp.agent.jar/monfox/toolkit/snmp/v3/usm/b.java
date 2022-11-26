package monfox.toolkit.snmp.v3.usm;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpFramework;

class b implements a {
   private static final byte[] a = new byte[]{1, 2, 3, 4, 5, 6, 6, 7};
   private String b = b("\n^ik#\fX\u0015\n\u000f\u001ez^ \t |");
   private String c = b("\n^i");
   private Cipher d = null;
   private Logger e = Logger.getInstance(b("\u0004X\u007fuR\riC4\u0014!"));

   public b() {
      this.e = Logger.getInstance(b("\u0004X\u007fuR\riC4\u0014!"));

      try {
         this.initialize();
      } catch (Exception var2) {
      }

   }

   public void initialize() throws GeneralSecurityException {
      try {
         if (this.d == null) {
            this.d = this.a(this.b);
         }
      } catch (NoClassDefFoundError var2) {
         this.e.error(b("\u000bUy\u00169\u001eOs\u000b.nUu\u0010@\u001dNj\u0014/\u001cO\u007f\u0000ZnX[*\u000e!o\u001a(\u000f/\u007f\u001a\u0007\t>s_6Z") + this.b, var2);
      } catch (Exception var3) {
         this.e.error(b("\u000bUy\u00169\u001eOs\u000b.nUu\u0010@\u001dNj\u0014/\u001cO\u007f\u0000ZnX[*\u000e!o\u001a(\u000f/\u007f\u001a\u0007\t>s_6Z") + this.b, var3);
      }

   }

   public void setTransform(String var1) {
      this.b = var1;
      this.d = null;
   }

   public byte[] crypt(int var1, byte[] var2, byte[] var3, byte[] var4) throws GeneralSecurityException {
      byte var5 = 1;
      if (var1 == 1) {
         var5 = 2;
      }

      try {
         if (this.d == null) {
            this.d = this.a(this.b);
         }
      } catch (NoClassDefFoundError var10) {
         this.e.error(b("\u000bUy\u00169\u001eOs\u000b.nUu\u0010@\u001dNj\u0014/\u001cO\u007f\u0000ZnX[*\u000e!o\u001a(\u000f/\u007f\u001a\u0007\t>s_6Z") + this.b, var10);
         throw new GeneralSecurityException(b("+uY6\u0019>oS+\u000enuU0@=nJ4\u000f<o_ "));
      } catch (Exception var11) {
         this.e.error(b("\u000bUy\u00169\u001eOs\u000b.nUu\u0010@\u001dNj\u0014/\u001cO\u007f\u0000ZnX[*\u000e!o\u001a(\u000f/\u007f\u001a\u0007\t>s_6Z") + this.b, var11);
         throw new GeneralSecurityException(b("+uY6\u0019>oS+\u000enuU0@=nJ4\u000f<o_ "));
      }

      byte[] var9;
      label23: {
         SecretKeySpec var6 = new SecretKeySpec(var2, 0, var2.length, this.c);
         IvParameterSpec var7 = new IvParameterSpec(var3);
         this.d.init(var5, var6, var7);
         int var8 = 8 - var4.length % 8;
         if (var8 == 8) {
            var8 = 0;
            if (!USMLocalizedUserData.k) {
               break label23;
            }
         }

         var9 = new byte[var4.length + var8];
         System.arraycopy(var4, 0, var9, 0, var4.length);
         System.arraycopy(a, 0, var9, var4.length, var8);
         var4 = var9;
      }

      var9 = this.d.doFinal(var4);
      return var9;
   }

   private Cipher a(String var1) throws GeneralSecurityException {
      if (this.e.isDebugEnabled()) {
         this.e.debug(b("p%\u001a\u000e#\u000b*\b\u0007\u00127kN+N-i_%\u0014+XS4\b+i\u0012") + var1 + ")");
      }

      String var2 = SnmpFramework.getSecurityProviderName();
      Cipher var3 = null;
      if (var2 == null) {
         var3 = Cipher.getInstance(var1);
      } else {
         if (this.e.isDebugEnabled()) {
            this.e.debug(b("c6\u001a1\u0013'u]d\u0010<tL-\u0004+i\u0000d") + var2);
         }

         try {
            var3 = Cipher.getInstance(var1, var2);
         } catch (Exception var5) {
            if (this.e.isDebugEnabled()) {
               this.e.debug(b("\rzT*\u000f:;V+\u0001*;y-\u0010&~Hd\u0006<tWd\u0010<tLj@t;") + var2);
               this.e.debug(b(";hS*\u0007n\u007f_\"\u0001;wNd*\r^\u001a4\u0012!mS \u0005<"));
            }

            var3 = Cipher.getInstance(var1);
         }
      }

      if (this.e.isDebugEnabled()) {
         this.e.debug(b("r'\u001a\u000e#\u000b*\b\u0007\u00127kN+N-i_%\u0014+XS4\b+i\u0012") + var1 + ")");
      }

      return var3;
   }

   public MessageDigest getMessageDigest(String var1) throws NoSuchAlgorithmException {
      return MessageDigest.getInstance(var1);
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 78;
               break;
            case 1:
               var10003 = 27;
               break;
            case 2:
               var10003 = 58;
               break;
            case 3:
               var10003 = 68;
               break;
            default:
               var10003 = 96;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
