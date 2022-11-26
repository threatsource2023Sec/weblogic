package org.python.bouncycastle.pqc.crypto.gmss;

import java.util.Enumeration;
import java.util.Vector;
import org.python.bouncycastle.util.Arrays;

class GMSSUtils {
   static GMSSLeaf[] clone(GMSSLeaf[] var0) {
      if (var0 == null) {
         return null;
      } else {
         GMSSLeaf[] var1 = new GMSSLeaf[var0.length];
         System.arraycopy(var0, 0, var1, 0, var0.length);
         return var1;
      }
   }

   static GMSSRootCalc[] clone(GMSSRootCalc[] var0) {
      if (var0 == null) {
         return null;
      } else {
         GMSSRootCalc[] var1 = new GMSSRootCalc[var0.length];
         System.arraycopy(var0, 0, var1, 0, var0.length);
         return var1;
      }
   }

   static GMSSRootSig[] clone(GMSSRootSig[] var0) {
      if (var0 == null) {
         return null;
      } else {
         GMSSRootSig[] var1 = new GMSSRootSig[var0.length];
         System.arraycopy(var0, 0, var1, 0, var0.length);
         return var1;
      }
   }

   static byte[][] clone(byte[][] var0) {
      if (var0 == null) {
         return (byte[][])null;
      } else {
         byte[][] var1 = new byte[var0.length][];

         for(int var2 = 0; var2 != var0.length; ++var2) {
            var1[var2] = Arrays.clone(var0[var2]);
         }

         return var1;
      }
   }

   static byte[][][] clone(byte[][][] var0) {
      if (var0 == null) {
         return (byte[][][])null;
      } else {
         byte[][][] var1 = new byte[var0.length][][];

         for(int var2 = 0; var2 != var0.length; ++var2) {
            var1[var2] = clone(var0[var2]);
         }

         return var1;
      }
   }

   static Treehash[] clone(Treehash[] var0) {
      if (var0 == null) {
         return null;
      } else {
         Treehash[] var1 = new Treehash[var0.length];
         System.arraycopy(var0, 0, var1, 0, var0.length);
         return var1;
      }
   }

   static Treehash[][] clone(Treehash[][] var0) {
      if (var0 == null) {
         return (Treehash[][])null;
      } else {
         Treehash[][] var1 = new Treehash[var0.length][];

         for(int var2 = 0; var2 != var0.length; ++var2) {
            var1[var2] = clone(var0[var2]);
         }

         return var1;
      }
   }

   static Vector[] clone(Vector[] var0) {
      if (var0 == null) {
         return null;
      } else {
         Vector[] var1 = new Vector[var0.length];

         for(int var2 = 0; var2 != var0.length; ++var2) {
            var1[var2] = new Vector();
            Enumeration var3 = var0[var2].elements();

            while(var3.hasMoreElements()) {
               var1[var2].addElement(var3.nextElement());
            }
         }

         return var1;
      }
   }

   static Vector[][] clone(Vector[][] var0) {
      if (var0 == null) {
         return (Vector[][])null;
      } else {
         Vector[][] var1 = new Vector[var0.length][];

         for(int var2 = 0; var2 != var0.length; ++var2) {
            var1[var2] = clone(var0[var2]);
         }

         return var1;
      }
   }
}
