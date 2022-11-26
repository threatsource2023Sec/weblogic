package org.python.bouncycastle.crypto.macs;

import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.Mac;
import org.python.bouncycastle.crypto.engines.DESEngine;
import org.python.bouncycastle.crypto.modes.CBCBlockCipher;
import org.python.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithIV;

public class ISO9797Alg3Mac implements Mac {
   private byte[] mac;
   private byte[] buf;
   private int bufOff;
   private BlockCipher cipher;
   private BlockCipherPadding padding;
   private int macSize;
   private KeyParameter lastKey2;
   private KeyParameter lastKey3;

   public ISO9797Alg3Mac(BlockCipher var1) {
      this(var1, var1.getBlockSize() * 8, (BlockCipherPadding)null);
   }

   public ISO9797Alg3Mac(BlockCipher var1, BlockCipherPadding var2) {
      this(var1, var1.getBlockSize() * 8, var2);
   }

   public ISO9797Alg3Mac(BlockCipher var1, int var2) {
      this(var1, var2, (BlockCipherPadding)null);
   }

   public ISO9797Alg3Mac(BlockCipher var1, int var2, BlockCipherPadding var3) {
      if (var2 % 8 != 0) {
         throw new IllegalArgumentException("MAC size must be multiple of 8");
      } else if (!(var1 instanceof DESEngine)) {
         throw new IllegalArgumentException("cipher must be instance of DESEngine");
      } else {
         this.cipher = new CBCBlockCipher(var1);
         this.padding = var3;
         this.macSize = var2 / 8;
         this.mac = new byte[var1.getBlockSize()];
         this.buf = new byte[var1.getBlockSize()];
         this.bufOff = 0;
      }
   }

   public String getAlgorithmName() {
      return "ISO9797Alg3";
   }

   public void init(CipherParameters var1) {
      this.reset();
      if (!(var1 instanceof KeyParameter) && !(var1 instanceof ParametersWithIV)) {
         throw new IllegalArgumentException("params must be an instance of KeyParameter or ParametersWithIV");
      } else {
         KeyParameter var2;
         if (var1 instanceof KeyParameter) {
            var2 = (KeyParameter)var1;
         } else {
            var2 = (KeyParameter)((ParametersWithIV)var1).getParameters();
         }

         byte[] var3 = var2.getKey();
         KeyParameter var4;
         if (var3.length == 16) {
            var4 = new KeyParameter(var3, 0, 8);
            this.lastKey2 = new KeyParameter(var3, 8, 8);
            this.lastKey3 = var4;
         } else {
            if (var3.length != 24) {
               throw new IllegalArgumentException("Key must be either 112 or 168 bit long");
            }

            var4 = new KeyParameter(var3, 0, 8);
            this.lastKey2 = new KeyParameter(var3, 8, 8);
            this.lastKey3 = new KeyParameter(var3, 16, 8);
         }

         if (var1 instanceof ParametersWithIV) {
            this.cipher.init(true, new ParametersWithIV(var4, ((ParametersWithIV)var1).getIV()));
         } else {
            this.cipher.init(true, var4);
         }

      }
   }

   public int getMacSize() {
      return this.macSize;
   }

   public void update(byte var1) {
      if (this.bufOff == this.buf.length) {
         this.cipher.processBlock(this.buf, 0, this.mac, 0);
         this.bufOff = 0;
      }

      this.buf[this.bufOff++] = var1;
   }

   public void update(byte[] var1, int var2, int var3) {
      if (var3 < 0) {
         throw new IllegalArgumentException("Can't have a negative input length!");
      } else {
         int var4 = this.cipher.getBlockSize();
         int var5 = 0;
         int var6 = var4 - this.bufOff;
         if (var3 > var6) {
            System.arraycopy(var1, var2, this.buf, this.bufOff, var6);
            var5 += this.cipher.processBlock(this.buf, 0, this.mac, 0);
            this.bufOff = 0;
            var3 -= var6;

            for(var2 += var6; var3 > var4; var2 += var4) {
               var5 += this.cipher.processBlock(var1, var2, this.mac, 0);
               var3 -= var4;
            }
         }

         System.arraycopy(var1, var2, this.buf, this.bufOff, var3);
         this.bufOff += var3;
      }
   }

   public int doFinal(byte[] var1, int var2) {
      int var3 = this.cipher.getBlockSize();
      if (this.padding == null) {
         while(this.bufOff < var3) {
            this.buf[this.bufOff] = 0;
            ++this.bufOff;
         }
      } else {
         if (this.bufOff == var3) {
            this.cipher.processBlock(this.buf, 0, this.mac, 0);
            this.bufOff = 0;
         }

         this.padding.addPadding(this.buf, this.bufOff);
      }

      this.cipher.processBlock(this.buf, 0, this.mac, 0);
      DESEngine var4 = new DESEngine();
      var4.init(false, this.lastKey2);
      var4.processBlock(this.mac, 0, this.mac, 0);
      var4.init(true, this.lastKey3);
      var4.processBlock(this.mac, 0, this.mac, 0);
      System.arraycopy(this.mac, 0, var1, var2, this.macSize);
      this.reset();
      return this.macSize;
   }

   public void reset() {
      for(int var1 = 0; var1 < this.buf.length; ++var1) {
         this.buf[var1] = 0;
      }

      this.bufOff = 0;
      this.cipher.reset();
   }
}
