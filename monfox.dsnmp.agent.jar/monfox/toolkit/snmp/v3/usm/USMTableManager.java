package monfox.toolkit.snmp.v3.usm;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import monfox.toolkit.snmp.SnmpFramework;

public final class USMTableManager {
   private static final String a = "}\u0001.\u000e6Y+&\u001djl-\u000f\u001d\u001a~";
   private static final int b = 20;

   private static Cipher a(byte[] var0, int var1) throws GeneralSecurityException {
      MessageDigest var2 = null;
      String var3 = SnmpFramework.getSecurityProviderName();
      if (var3 != null) {
         try {
            var2 = MessageDigest.getInstance(a("`\u0007^"), var3);
         } catch (Exception var13) {
         }
      }

      if (var2 == null) {
         var2 = MessageDigest.getInstance(a("`\u0007^"));
      }

      byte[] var4 = new byte[8];
      PBEKeySpec var5 = new PBEKeySpec((new String(var0)).toCharArray());
      SecretKeyFactory var6 = SecretKeyFactory.getInstance(a("}\u0001.\u000e6Y+&\u001djl-\u000f\u001d\u001a~"));
      SecretKey var7 = var6.generateSecret(var5);
      var2.update(var0);
      byte[] var8 = var2.digest();
      System.arraycopy(var8, 0, var4, 0, 8);
      PBEParameterSpec var9 = new PBEParameterSpec(var4, 20);
      Cipher var10 = null;
      if (var3 != null) {
         try {
            var10 = Cipher.getInstance(a("}\u0001.\u000e6Y+&\u001djl-\u000f\u001d\u001a~"), var3);
         } catch (Exception var12) {
         }
      }

      if (var10 == null) {
         var10 = Cipher.getInstance(a("}\u0001.\u000e6Y+&\u001djl-\u000f\u001d\u001a~"));
      }

      var10.init(var1, var7, var9);
      return var10;
   }

   private static void a(String var0, byte[] var1, USMUserTable var2) throws IOException, GeneralSecurityException {
      Cipher var3 = a(var1, 1);
      FileOutputStream var4 = new FileOutputStream(var0);
      CipherOutputStream var5 = new CipherOutputStream(var4, var3);
      ObjectOutputStream var6 = new ObjectOutputStream(var5);
      var6.writeObject(var2);
      var6.close();
      var5.close();
      var4.close();
   }

   private static USMUserTable a(String var0, byte[] var1) throws IOException, GeneralSecurityException, ClassNotFoundException {
      Cipher var2 = a(var1, 2);
      FileInputStream var3 = new FileInputStream(var0);
      CipherInputStream var4 = new CipherInputStream(var3, var2);
      ObjectInputStream var5 = new ObjectInputStream(var4);
      USMUserTable var6 = (USMUserTable)var5.readObject();
      var5.close();
      var4.close();
      var3.close();
      return var6;
   }

   public static USMUserTable getUserTable(String var0, String var1) throws IOException, GeneralSecurityException, ClassNotFoundException {
      return getUserTable(var0, var1.getBytes());
   }

   public static USMUserTable getUserTable(String var0, byte[] var1) throws IOException, GeneralSecurityException, ClassNotFoundException {
      return a(var0, var1);
   }

   public static void storeUSMUserTable(USMUserTable var0, String var1, String var2) throws IOException, GeneralSecurityException {
      storeUSMUserTable(var0, var1, var2.getBytes());
   }

   public static void storeUSMUserTable(USMUserTable var0, String var1, byte[] var2) throws IOException, GeneralSecurityException {
      a(var1, var2, var0);
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 45;
               break;
            case 1:
               var10003 = 67;
               break;
            case 2:
               var10003 = 107;
               break;
            case 3:
               var10003 = 89;
               break;
            default:
               var10003 = 95;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
