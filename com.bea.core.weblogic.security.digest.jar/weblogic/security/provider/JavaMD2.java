package weblogic.security.provider;

import weblogic.security.MessageDigest;

public final class JavaMD2 extends MessageDigest implements Cloneable {
   public MD2State state;
   private byte[] digestBits;
   private boolean digestValid;

   public JavaMD2() {
      super("MD2");
      this.init();
   }

   public void update(byte[] input, int off, int len) {
      for(int i = off; i < off + len; ++i) {
         this.update(input[i]);
      }

   }

   public void update(byte b) {
      this.state.update(b);
   }

   public byte[] digest() {
      if (!this.digestValid) {
         this.computeCurrent();
      }

      return this.digestBits;
   }

   public void reset() {
      this.init();
   }

   public Object clone() {
      JavaMD2 md2 = new JavaMD2();
      md2.state = (MD2State)this.state.clone();
      if (this.digestBits != null) {
         md2.digestBits = new byte[16];
         System.arraycopy(this.digestBits, 0, md2.digestBits, 0, 16);
      }

      return md2;
   }

   private void init() {
      this.state = new MD2State();
      this.digestBits = new byte[16];
   }

   private void computeCurrent() {
      MD2State old = (MD2State)this.state.clone();
      this.state.computeCurrent(this.digestBits);
      this.state = old;
   }

   public boolean isEqual(byte[] otherDigest) {
      return isEqual(this.digestBits, otherDigest);
   }

   public static boolean isEqual(byte[] digesta, byte[] digestb) {
      if (digesta.length != digestb.length) {
         return false;
      } else {
         for(int i = 0; i < digesta.length; ++i) {
            if (digesta[i] != digestb[i]) {
               return false;
            }
         }

         return true;
      }
   }
}
