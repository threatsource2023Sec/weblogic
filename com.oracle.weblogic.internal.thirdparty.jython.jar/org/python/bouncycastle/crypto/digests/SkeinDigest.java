package org.python.bouncycastle.crypto.digests;

import org.python.bouncycastle.crypto.ExtendedDigest;
import org.python.bouncycastle.crypto.params.SkeinParameters;
import org.python.bouncycastle.util.Memoable;

public class SkeinDigest implements ExtendedDigest, Memoable {
   public static final int SKEIN_256 = 256;
   public static final int SKEIN_512 = 512;
   public static final int SKEIN_1024 = 1024;
   private SkeinEngine engine;

   public SkeinDigest(int var1, int var2) {
      this.engine = new SkeinEngine(var1, var2);
      this.init((SkeinParameters)null);
   }

   public SkeinDigest(SkeinDigest var1) {
      this.engine = new SkeinEngine(var1.engine);
   }

   public void reset(Memoable var1) {
      SkeinDigest var2 = (SkeinDigest)var1;
      this.engine.reset(var2.engine);
   }

   public Memoable copy() {
      return new SkeinDigest(this);
   }

   public String getAlgorithmName() {
      return "Skein-" + this.engine.getBlockSize() * 8 + "-" + this.engine.getOutputSize() * 8;
   }

   public int getDigestSize() {
      return this.engine.getOutputSize();
   }

   public int getByteLength() {
      return this.engine.getBlockSize();
   }

   public void init(SkeinParameters var1) {
      this.engine.init(var1);
   }

   public void reset() {
      this.engine.reset();
   }

   public void update(byte var1) {
      this.engine.update(var1);
   }

   public void update(byte[] var1, int var2, int var3) {
      this.engine.update(var1, var2, var3);
   }

   public int doFinal(byte[] var1, int var2) {
      return this.engine.doFinal(var1, var2);
   }
}
