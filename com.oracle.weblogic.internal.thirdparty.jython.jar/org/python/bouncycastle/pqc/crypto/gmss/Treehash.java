package org.python.bouncycastle.pqc.crypto.gmss;

import java.util.Vector;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.python.bouncycastle.util.Integers;
import org.python.bouncycastle.util.encoders.Hex;

public class Treehash {
   private int maxHeight;
   private Vector tailStack;
   private Vector heightOfNodes;
   private byte[] firstNode;
   private byte[] seedActive;
   private byte[] seedNext;
   private int tailLength;
   private int firstNodeHeight;
   private boolean isInitialized;
   private boolean isFinished;
   private boolean seedInitialized;
   private Digest messDigestTree;

   public Treehash(Digest var1, byte[][] var2, int[] var3) {
      this.messDigestTree = var1;
      this.maxHeight = var3[0];
      this.tailLength = var3[1];
      this.firstNodeHeight = var3[2];
      if (var3[3] == 1) {
         this.isFinished = true;
      } else {
         this.isFinished = false;
      }

      if (var3[4] == 1) {
         this.isInitialized = true;
      } else {
         this.isInitialized = false;
      }

      if (var3[5] == 1) {
         this.seedInitialized = true;
      } else {
         this.seedInitialized = false;
      }

      this.heightOfNodes = new Vector();

      int var4;
      for(var4 = 0; var4 < this.tailLength; ++var4) {
         this.heightOfNodes.addElement(Integers.valueOf(var3[6 + var4]));
      }

      this.firstNode = var2[0];
      this.seedActive = var2[1];
      this.seedNext = var2[2];
      this.tailStack = new Vector();

      for(var4 = 0; var4 < this.tailLength; ++var4) {
         this.tailStack.addElement(var2[3 + var4]);
      }

   }

   public Treehash(Vector var1, int var2, Digest var3) {
      this.tailStack = var1;
      this.maxHeight = var2;
      this.firstNode = null;
      this.isInitialized = false;
      this.isFinished = false;
      this.seedInitialized = false;
      this.messDigestTree = var3;
      this.seedNext = new byte[this.messDigestTree.getDigestSize()];
      this.seedActive = new byte[this.messDigestTree.getDigestSize()];
   }

   public void initializeSeed(byte[] var1) {
      System.arraycopy(var1, 0, this.seedNext, 0, this.messDigestTree.getDigestSize());
      this.seedInitialized = true;
   }

   public void initialize() {
      if (!this.seedInitialized) {
         System.err.println("Seed " + this.maxHeight + " not initialized");
      } else {
         this.heightOfNodes = new Vector();
         this.tailLength = 0;
         this.firstNode = null;
         this.firstNodeHeight = -1;
         this.isInitialized = true;
         System.arraycopy(this.seedNext, 0, this.seedActive, 0, this.messDigestTree.getDigestSize());
      }
   }

   public void update(GMSSRandom var1, byte[] var2) {
      if (this.isFinished) {
         System.err.println("No more update possible for treehash instance!");
      } else if (!this.isInitialized) {
         System.err.println("Treehash instance not initialized before update");
      } else {
         byte[] var3 = new byte[this.messDigestTree.getDigestSize()];
         boolean var4 = true;
         var1.nextSeed(this.seedActive);
         if (this.firstNode == null) {
            this.firstNode = var2;
            this.firstNodeHeight = 0;
         } else {
            var3 = var2;

            byte[] var5;
            int var6;
            for(var6 = 0; this.tailLength > 0 && var6 == (Integer)this.heightOfNodes.lastElement(); --this.tailLength) {
               var5 = new byte[this.messDigestTree.getDigestSize() << 1];
               System.arraycopy(this.tailStack.lastElement(), 0, var5, 0, this.messDigestTree.getDigestSize());
               this.tailStack.removeElementAt(this.tailStack.size() - 1);
               this.heightOfNodes.removeElementAt(this.heightOfNodes.size() - 1);
               System.arraycopy(var3, 0, var5, this.messDigestTree.getDigestSize(), this.messDigestTree.getDigestSize());
               this.messDigestTree.update(var5, 0, var5.length);
               var3 = new byte[this.messDigestTree.getDigestSize()];
               this.messDigestTree.doFinal(var3, 0);
               ++var6;
            }

            this.tailStack.addElement(var3);
            this.heightOfNodes.addElement(Integers.valueOf(var6));
            ++this.tailLength;
            if ((Integer)this.heightOfNodes.lastElement() == this.firstNodeHeight) {
               var5 = new byte[this.messDigestTree.getDigestSize() << 1];
               System.arraycopy(this.firstNode, 0, var5, 0, this.messDigestTree.getDigestSize());
               System.arraycopy(this.tailStack.lastElement(), 0, var5, this.messDigestTree.getDigestSize(), this.messDigestTree.getDigestSize());
               this.tailStack.removeElementAt(this.tailStack.size() - 1);
               this.heightOfNodes.removeElementAt(this.heightOfNodes.size() - 1);
               this.messDigestTree.update(var5, 0, var5.length);
               this.firstNode = new byte[this.messDigestTree.getDigestSize()];
               this.messDigestTree.doFinal(this.firstNode, 0);
               ++this.firstNodeHeight;
               this.tailLength = 0;
            }
         }

         if (this.firstNodeHeight == this.maxHeight) {
            this.isFinished = true;
         }

      }
   }

   public void destroy() {
      this.isInitialized = false;
      this.isFinished = false;
      this.firstNode = null;
      this.tailLength = 0;
      this.firstNodeHeight = -1;
   }

   public int getLowestNodeHeight() {
      if (this.firstNode == null) {
         return this.maxHeight;
      } else {
         return this.tailLength == 0 ? this.firstNodeHeight : Math.min(this.firstNodeHeight, (Integer)this.heightOfNodes.lastElement());
      }
   }

   public int getFirstNodeHeight() {
      return this.firstNode == null ? this.maxHeight : this.firstNodeHeight;
   }

   public boolean wasInitialized() {
      return this.isInitialized;
   }

   public boolean wasFinished() {
      return this.isFinished;
   }

   public byte[] getFirstNode() {
      return this.firstNode;
   }

   public byte[] getSeedActive() {
      return this.seedActive;
   }

   public void setFirstNode(byte[] var1) {
      if (!this.isInitialized) {
         this.initialize();
      }

      this.firstNode = var1;
      this.firstNodeHeight = this.maxHeight;
      this.isFinished = true;
   }

   public void updateNextSeed(GMSSRandom var1) {
      var1.nextSeed(this.seedNext);
   }

   public Vector getTailStack() {
      return this.tailStack;
   }

   public byte[][] getStatByte() {
      byte[][] var1 = new byte[3 + this.tailLength][this.messDigestTree.getDigestSize()];
      var1[0] = this.firstNode;
      var1[1] = this.seedActive;
      var1[2] = this.seedNext;

      for(int var2 = 0; var2 < this.tailLength; ++var2) {
         var1[3 + var2] = (byte[])((byte[])this.tailStack.elementAt(var2));
      }

      return var1;
   }

   public int[] getStatInt() {
      int[] var1 = new int[6 + this.tailLength];
      var1[0] = this.maxHeight;
      var1[1] = this.tailLength;
      var1[2] = this.firstNodeHeight;
      if (this.isFinished) {
         var1[3] = 1;
      } else {
         var1[3] = 0;
      }

      if (this.isInitialized) {
         var1[4] = 1;
      } else {
         var1[4] = 0;
      }

      if (this.seedInitialized) {
         var1[5] = 1;
      } else {
         var1[5] = 0;
      }

      for(int var2 = 0; var2 < this.tailLength; ++var2) {
         var1[6 + var2] = (Integer)this.heightOfNodes.elementAt(var2);
      }

      return var1;
   }

   public String toString() {
      String var1 = "Treehash    : ";

      int var2;
      for(var2 = 0; var2 < 6 + this.tailLength; ++var2) {
         var1 = var1 + this.getStatInt()[var2] + " ";
      }

      for(var2 = 0; var2 < 3 + this.tailLength; ++var2) {
         if (this.getStatByte()[var2] != null) {
            var1 = var1 + new String(Hex.encode(this.getStatByte()[var2])) + " ";
         } else {
            var1 = var1 + "null ";
         }
      }

      var1 = var1 + "  " + this.messDigestTree.getDigestSize();
      return var1;
   }
}
