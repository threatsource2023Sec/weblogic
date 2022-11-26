package org.python.bouncycastle.crypto.examples;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import org.python.bouncycastle.crypto.CryptoException;
import org.python.bouncycastle.crypto.KeyGenerationParameters;
import org.python.bouncycastle.crypto.engines.DESedeEngine;
import org.python.bouncycastle.crypto.generators.DESedeKeyGenerator;
import org.python.bouncycastle.crypto.modes.CBCBlockCipher;
import org.python.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.util.encoders.Hex;

public class DESExample {
   private boolean encrypt = true;
   private PaddedBufferedBlockCipher cipher = null;
   private BufferedInputStream in = null;
   private BufferedOutputStream out = null;
   private byte[] key = null;

   public static void main(String[] var0) {
      boolean var1 = true;
      String var2 = null;
      String var3 = null;
      String var4 = null;
      DESExample var5;
      if (var0.length < 2) {
         var5 = new DESExample();
         System.err.println("Usage: java " + var5.getClass().getName() + " infile outfile [keyfile]");
         System.exit(1);
      }

      var4 = "deskey.dat";
      var2 = var0[0];
      var3 = var0[1];
      if (var0.length > 2) {
         var1 = false;
         var4 = var0[2];
      }

      var5 = new DESExample(var2, var3, var4, var1);
      var5.process();
   }

   public DESExample() {
   }

   public DESExample(String var1, String var2, String var3, boolean var4) {
      this.encrypt = var4;

      try {
         this.in = new BufferedInputStream(new FileInputStream(var1));
      } catch (FileNotFoundException var14) {
         System.err.println("Input file not found [" + var1 + "]");
         System.exit(1);
      }

      try {
         this.out = new BufferedOutputStream(new FileOutputStream(var2));
      } catch (IOException var13) {
         System.err.println("Output file not created [" + var2 + "]");
         System.exit(1);
      }

      if (var4) {
         try {
            SecureRandom var5 = null;

            try {
               var5 = new SecureRandom();
               var5.setSeed("www.bouncycastle.org".getBytes());
            } catch (Exception var11) {
               System.err.println("Hmmm, no SHA1PRNG, you need the Sun implementation");
               System.exit(1);
            }

            KeyGenerationParameters var6 = new KeyGenerationParameters(var5, 192);
            DESedeKeyGenerator var7 = new DESedeKeyGenerator();
            var7.init(var6);
            this.key = var7.generateKey();
            BufferedOutputStream var8 = new BufferedOutputStream(new FileOutputStream(var3));
            byte[] var9 = Hex.encode(this.key);
            var8.write(var9, 0, var9.length);
            var8.flush();
            var8.close();
         } catch (IOException var12) {
            System.err.println("Could not decryption create key file [" + var3 + "]");
            System.exit(1);
         }
      } else {
         try {
            BufferedInputStream var15 = new BufferedInputStream(new FileInputStream(var3));
            int var16 = var15.available();
            byte[] var17 = new byte[var16];
            var15.read(var17, 0, var16);
            this.key = Hex.decode(var17);
         } catch (IOException var10) {
            System.err.println("Decryption key file not found, or not valid [" + var3 + "]");
            System.exit(1);
         }
      }

   }

   private void process() {
      this.cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new DESedeEngine()));
      if (this.encrypt) {
         this.performEncrypt(this.key);
      } else {
         this.performDecrypt(this.key);
      }

      try {
         this.in.close();
         this.out.flush();
         this.out.close();
      } catch (IOException var2) {
         System.err.println("exception closing resources: " + var2.getMessage());
      }

   }

   private void performEncrypt(byte[] var1) {
      this.cipher.init(true, new KeyParameter(var1));
      byte var2 = 47;
      int var3 = this.cipher.getOutputSize(var2);
      byte[] var4 = new byte[var2];
      byte[] var5 = new byte[var3];

      try {
         Object var6 = null;

         int var7;
         int var8;
         byte[] var12;
         while((var7 = this.in.read(var4, 0, var2)) > 0) {
            var8 = this.cipher.processBytes(var4, 0, var7, var5, 0);
            if (var8 > 0) {
               var12 = Hex.encode(var5, 0, var8);
               this.out.write(var12, 0, var12.length);
               this.out.write(10);
            }
         }

         try {
            var8 = this.cipher.doFinal(var5, 0);
            if (var8 > 0) {
               var12 = Hex.encode(var5, 0, var8);
               this.out.write(var12, 0, var12.length);
               this.out.write(10);
            }
         } catch (CryptoException var10) {
         }
      } catch (IOException var11) {
         var11.printStackTrace();
      }

   }

   private void performDecrypt(byte[] var1) {
      this.cipher.init(false, new KeyParameter(var1));
      BufferedReader var2 = new BufferedReader(new InputStreamReader(this.in));

      try {
         Object var3 = null;
         byte[] var4 = null;
         String var5 = null;

         int var6;
         while((var5 = var2.readLine()) != null) {
            byte[] var10 = Hex.decode(var5);
            var4 = new byte[this.cipher.getOutputSize(var10.length)];
            var6 = this.cipher.processBytes(var10, 0, var10.length, var4, 0);
            if (var6 > 0) {
               this.out.write(var4, 0, var6);
            }
         }

         try {
            var6 = this.cipher.doFinal(var4, 0);
            if (var6 > 0) {
               this.out.write(var4, 0, var6);
            }
         } catch (CryptoException var8) {
         }
      } catch (IOException var9) {
         var9.printStackTrace();
      }

   }
}
