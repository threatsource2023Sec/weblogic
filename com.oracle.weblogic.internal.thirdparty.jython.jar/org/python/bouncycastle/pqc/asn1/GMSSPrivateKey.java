package org.python.bouncycastle.pqc.asn1;

import java.math.BigInteger;
import java.util.Vector;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.pqc.crypto.gmss.GMSSLeaf;
import org.python.bouncycastle.pqc.crypto.gmss.GMSSParameters;
import org.python.bouncycastle.pqc.crypto.gmss.GMSSRootCalc;
import org.python.bouncycastle.pqc.crypto.gmss.GMSSRootSig;
import org.python.bouncycastle.pqc.crypto.gmss.Treehash;

public class GMSSPrivateKey extends ASN1Object {
   private ASN1Primitive primitive;

   private GMSSPrivateKey(ASN1Sequence var1) {
      ASN1Sequence var2 = (ASN1Sequence)var1.getObjectAt(0);
      int[] var3 = new int[var2.size()];

      for(int var4 = 0; var4 < var2.size(); ++var4) {
         var3[var4] = checkBigIntegerInIntRange(var2.getObjectAt(var4));
      }

      ASN1Sequence var17 = (ASN1Sequence)var1.getObjectAt(1);
      byte[][] var5 = new byte[var17.size()][];

      for(int var6 = 0; var6 < var5.length; ++var6) {
         var5[var6] = ((DEROctetString)var17.getObjectAt(var6)).getOctets();
      }

      ASN1Sequence var18 = (ASN1Sequence)var1.getObjectAt(2);
      byte[][] var7 = new byte[var18.size()][];

      for(int var8 = 0; var8 < var7.length; ++var8) {
         var7[var8] = ((DEROctetString)var18.getObjectAt(var8)).getOctets();
      }

      ASN1Sequence var19 = (ASN1Sequence)var1.getObjectAt(3);
      byte[][][] var9 = new byte[var19.size()][][];

      for(int var10 = 0; var10 < var9.length; ++var10) {
         ASN1Sequence var11 = (ASN1Sequence)var19.getObjectAt(var10);
         var9[var10] = new byte[var11.size()][];

         for(int var12 = 0; var12 < var9[var10].length; ++var12) {
            var9[var10][var12] = ((DEROctetString)var11.getObjectAt(var12)).getOctets();
         }
      }

      ASN1Sequence var20 = (ASN1Sequence)var1.getObjectAt(4);
      byte[][][] var13 = new byte[var20.size()][][];

      for(int var14 = 0; var14 < var13.length; ++var14) {
         ASN1Sequence var21 = (ASN1Sequence)var20.getObjectAt(var14);
         var13[var14] = new byte[var21.size()][];

         for(int var15 = 0; var15 < var13[var14].length; ++var15) {
            var13[var14][var15] = ((DEROctetString)var21.getObjectAt(var15)).getOctets();
         }
      }

      ASN1Sequence var22 = (ASN1Sequence)var1.getObjectAt(5);
      Treehash[][] var16 = new Treehash[var22.size()][];
   }

   public GMSSPrivateKey(int[] var1, byte[][] var2, byte[][] var3, byte[][][] var4, byte[][][] var5, Treehash[][] var6, Treehash[][] var7, Vector[] var8, Vector[] var9, Vector[][] var10, Vector[][] var11, byte[][][] var12, GMSSLeaf[] var13, GMSSLeaf[] var14, GMSSLeaf[] var15, int[] var16, byte[][] var17, GMSSRootCalc[] var18, byte[][] var19, GMSSRootSig[] var20, GMSSParameters var21, AlgorithmIdentifier var22) {
      AlgorithmIdentifier[] var23 = new AlgorithmIdentifier[]{var22};
      this.primitive = this.encode(var1, var2, var3, var4, var5, var12, var6, var7, var8, var9, var10, var11, var13, var14, var15, var16, var17, var18, var19, var20, var21, var23);
   }

   private ASN1Primitive encode(int[] var1, byte[][] var2, byte[][] var3, byte[][][] var4, byte[][][] var5, byte[][][] var6, Treehash[][] var7, Treehash[][] var8, Vector[] var9, Vector[] var10, Vector[][] var11, Vector[][] var12, GMSSLeaf[] var13, GMSSLeaf[] var14, GMSSLeaf[] var15, int[] var16, byte[][] var17, GMSSRootCalc[] var18, byte[][] var19, GMSSRootSig[] var20, GMSSParameters var21, AlgorithmIdentifier[] var22) {
      ASN1EncodableVector var23 = new ASN1EncodableVector();
      ASN1EncodableVector var24 = new ASN1EncodableVector();

      for(int var25 = 0; var25 < var1.length; ++var25) {
         var24.add(new ASN1Integer((long)var1[var25]));
      }

      var23.add(new DERSequence(var24));
      ASN1EncodableVector var72 = new ASN1EncodableVector();

      for(int var26 = 0; var26 < var2.length; ++var26) {
         var72.add(new DEROctetString(var2[var26]));
      }

      var23.add(new DERSequence(var72));
      ASN1EncodableVector var73 = new ASN1EncodableVector();

      for(int var27 = 0; var27 < var3.length; ++var27) {
         var73.add(new DEROctetString(var3[var27]));
      }

      var23.add(new DERSequence(var73));
      ASN1EncodableVector var74 = new ASN1EncodableVector();
      ASN1EncodableVector var28 = new ASN1EncodableVector();

      for(int var29 = 0; var29 < var4.length; ++var29) {
         for(int var30 = 0; var30 < var4[var29].length; ++var30) {
            var74.add(new DEROctetString(var4[var29][var30]));
         }

         var28.add(new DERSequence(var74));
         var74 = new ASN1EncodableVector();
      }

      var23.add(new DERSequence(var28));
      ASN1EncodableVector var75 = new ASN1EncodableVector();
      ASN1EncodableVector var76 = new ASN1EncodableVector();

      for(int var31 = 0; var31 < var5.length; ++var31) {
         for(int var32 = 0; var32 < var5[var31].length; ++var32) {
            var75.add(new DEROctetString(var5[var31][var32]));
         }

         var76.add(new DERSequence(var75));
         var75 = new ASN1EncodableVector();
      }

      var23.add(new DERSequence(var76));
      ASN1EncodableVector var77 = new ASN1EncodableVector();
      ASN1EncodableVector var78 = new ASN1EncodableVector();
      ASN1EncodableVector var33 = new ASN1EncodableVector();
      ASN1EncodableVector var34 = new ASN1EncodableVector();
      ASN1EncodableVector var35 = new ASN1EncodableVector();

      int var36;
      int var37;
      int var38;
      int var39;
      for(var36 = 0; var36 < var7.length; ++var36) {
         for(var37 = 0; var37 < var7[var36].length; ++var37) {
            var33.add(new DERSequence(var22[0]));
            var38 = var7[var36][var37].getStatInt()[1];
            var34.add(new DEROctetString(var7[var36][var37].getStatByte()[0]));
            var34.add(new DEROctetString(var7[var36][var37].getStatByte()[1]));
            var34.add(new DEROctetString(var7[var36][var37].getStatByte()[2]));

            for(var39 = 0; var39 < var38; ++var39) {
               var34.add(new DEROctetString(var7[var36][var37].getStatByte()[3 + var39]));
            }

            var33.add(new DERSequence(var34));
            var34 = new ASN1EncodableVector();
            var35.add(new ASN1Integer((long)var7[var36][var37].getStatInt()[0]));
            var35.add(new ASN1Integer((long)var38));
            var35.add(new ASN1Integer((long)var7[var36][var37].getStatInt()[2]));
            var35.add(new ASN1Integer((long)var7[var36][var37].getStatInt()[3]));
            var35.add(new ASN1Integer((long)var7[var36][var37].getStatInt()[4]));
            var35.add(new ASN1Integer((long)var7[var36][var37].getStatInt()[5]));

            for(var39 = 0; var39 < var38; ++var39) {
               var35.add(new ASN1Integer((long)var7[var36][var37].getStatInt()[6 + var39]));
            }

            var33.add(new DERSequence(var35));
            var35 = new ASN1EncodableVector();
            var78.add(new DERSequence(var33));
            var33 = new ASN1EncodableVector();
         }

         var77.add(new DERSequence(var78));
         var78 = new ASN1EncodableVector();
      }

      var23.add(new DERSequence(var77));
      var77 = new ASN1EncodableVector();
      var78 = new ASN1EncodableVector();
      var33 = new ASN1EncodableVector();
      var34 = new ASN1EncodableVector();
      var35 = new ASN1EncodableVector();

      for(var36 = 0; var36 < var8.length; ++var36) {
         for(var37 = 0; var37 < var8[var36].length; ++var37) {
            var33.add(new DERSequence(var22[0]));
            var38 = var8[var36][var37].getStatInt()[1];
            var34.add(new DEROctetString(var8[var36][var37].getStatByte()[0]));
            var34.add(new DEROctetString(var8[var36][var37].getStatByte()[1]));
            var34.add(new DEROctetString(var8[var36][var37].getStatByte()[2]));

            for(var39 = 0; var39 < var38; ++var39) {
               var34.add(new DEROctetString(var8[var36][var37].getStatByte()[3 + var39]));
            }

            var33.add(new DERSequence(var34));
            var34 = new ASN1EncodableVector();
            var35.add(new ASN1Integer((long)var8[var36][var37].getStatInt()[0]));
            var35.add(new ASN1Integer((long)var38));
            var35.add(new ASN1Integer((long)var8[var36][var37].getStatInt()[2]));
            var35.add(new ASN1Integer((long)var8[var36][var37].getStatInt()[3]));
            var35.add(new ASN1Integer((long)var8[var36][var37].getStatInt()[4]));
            var35.add(new ASN1Integer((long)var8[var36][var37].getStatInt()[5]));

            for(var39 = 0; var39 < var38; ++var39) {
               var35.add(new ASN1Integer((long)var8[var36][var37].getStatInt()[6 + var39]));
            }

            var33.add(new DERSequence(var35));
            var35 = new ASN1EncodableVector();
            var78.add(new DERSequence(var33));
            var33 = new ASN1EncodableVector();
         }

         var77.add(new DERSequence(new DERSequence(var78)));
         var78 = new ASN1EncodableVector();
      }

      var23.add(new DERSequence(var77));
      ASN1EncodableVector var79 = new ASN1EncodableVector();
      ASN1EncodableVector var80 = new ASN1EncodableVector();

      for(var38 = 0; var38 < var6.length; ++var38) {
         for(var39 = 0; var39 < var6[var38].length; ++var39) {
            var79.add(new DEROctetString(var6[var38][var39]));
         }

         var80.add(new DERSequence(var79));
         var79 = new ASN1EncodableVector();
      }

      var23.add(new DERSequence(var80));
      ASN1EncodableVector var81 = new ASN1EncodableVector();
      ASN1EncodableVector var84 = new ASN1EncodableVector();

      for(int var40 = 0; var40 < var9.length; ++var40) {
         for(int var41 = 0; var41 < var9[var40].size(); ++var41) {
            var81.add(new DEROctetString((byte[])((byte[])var9[var40].elementAt(var41))));
         }

         var84.add(new DERSequence(var81));
         var81 = new ASN1EncodableVector();
      }

      var23.add(new DERSequence(var84));
      ASN1EncodableVector var82 = new ASN1EncodableVector();
      ASN1EncodableVector var83 = new ASN1EncodableVector();

      for(int var42 = 0; var42 < var10.length; ++var42) {
         for(int var43 = 0; var43 < var10[var42].size(); ++var43) {
            var82.add(new DEROctetString((byte[])((byte[])var10[var42].elementAt(var43))));
         }

         var83.add(new DERSequence(var82));
         var82 = new ASN1EncodableVector();
      }

      var23.add(new DERSequence(var83));
      ASN1EncodableVector var85 = new ASN1EncodableVector();
      ASN1EncodableVector var86 = new ASN1EncodableVector();
      ASN1EncodableVector var44 = new ASN1EncodableVector();

      for(int var45 = 0; var45 < var11.length; ++var45) {
         for(int var46 = 0; var46 < var11[var45].length; ++var46) {
            for(int var47 = 0; var47 < var11[var45][var46].size(); ++var47) {
               var85.add(new DEROctetString((byte[])((byte[])var11[var45][var46].elementAt(var47))));
            }

            var86.add(new DERSequence(var85));
            var85 = new ASN1EncodableVector();
         }

         var44.add(new DERSequence(var86));
         var86 = new ASN1EncodableVector();
      }

      var23.add(new DERSequence(var44));
      ASN1EncodableVector var87 = new ASN1EncodableVector();
      ASN1EncodableVector var88 = new ASN1EncodableVector();
      ASN1EncodableVector var89 = new ASN1EncodableVector();

      int var49;
      int var50;
      for(int var48 = 0; var48 < var12.length; ++var48) {
         for(var49 = 0; var49 < var12[var48].length; ++var49) {
            for(var50 = 0; var50 < var12[var48][var49].size(); ++var50) {
               var87.add(new DEROctetString((byte[])((byte[])var12[var48][var49].elementAt(var50))));
            }

            var88.add(new DERSequence(var87));
            var87 = new ASN1EncodableVector();
         }

         var89.add(new DERSequence(var88));
         var88 = new ASN1EncodableVector();
      }

      var23.add(new DERSequence(var89));
      ASN1EncodableVector var90 = new ASN1EncodableVector();
      var33 = new ASN1EncodableVector();
      var34 = new ASN1EncodableVector();
      var35 = new ASN1EncodableVector();

      for(var49 = 0; var49 < var13.length; ++var49) {
         var33.add(new DERSequence(var22[0]));
         byte[][] var92 = var13[var49].getStatByte();
         var34.add(new DEROctetString(var92[0]));
         var34.add(new DEROctetString(var92[1]));
         var34.add(new DEROctetString(var92[2]));
         var34.add(new DEROctetString(var92[3]));
         var33.add(new DERSequence(var34));
         var34 = new ASN1EncodableVector();
         int[] var51 = var13[var49].getStatInt();
         var35.add(new ASN1Integer((long)var51[0]));
         var35.add(new ASN1Integer((long)var51[1]));
         var35.add(new ASN1Integer((long)var51[2]));
         var35.add(new ASN1Integer((long)var51[3]));
         var33.add(new DERSequence(var35));
         var35 = new ASN1EncodableVector();
         var90.add(new DERSequence(var33));
         var33 = new ASN1EncodableVector();
      }

      var23.add(new DERSequence(var90));
      ASN1EncodableVector var91 = new ASN1EncodableVector();
      var33 = new ASN1EncodableVector();
      var34 = new ASN1EncodableVector();
      var35 = new ASN1EncodableVector();

      for(var50 = 0; var50 < var14.length; ++var50) {
         var33.add(new DERSequence(var22[0]));
         byte[][] var93 = var14[var50].getStatByte();
         var34.add(new DEROctetString(var93[0]));
         var34.add(new DEROctetString(var93[1]));
         var34.add(new DEROctetString(var93[2]));
         var34.add(new DEROctetString(var93[3]));
         var33.add(new DERSequence(var34));
         var34 = new ASN1EncodableVector();
         int[] var52 = var14[var50].getStatInt();
         var35.add(new ASN1Integer((long)var52[0]));
         var35.add(new ASN1Integer((long)var52[1]));
         var35.add(new ASN1Integer((long)var52[2]));
         var35.add(new ASN1Integer((long)var52[3]));
         var33.add(new DERSequence(var35));
         var35 = new ASN1EncodableVector();
         var91.add(new DERSequence(var33));
         var33 = new ASN1EncodableVector();
      }

      var23.add(new DERSequence(var91));
      ASN1EncodableVector var94 = new ASN1EncodableVector();
      var33 = new ASN1EncodableVector();
      var34 = new ASN1EncodableVector();
      var35 = new ASN1EncodableVector();

      for(int var95 = 0; var95 < var15.length; ++var95) {
         var33.add(new DERSequence(var22[0]));
         byte[][] var97 = var15[var95].getStatByte();
         var34.add(new DEROctetString(var97[0]));
         var34.add(new DEROctetString(var97[1]));
         var34.add(new DEROctetString(var97[2]));
         var34.add(new DEROctetString(var97[3]));
         var33.add(new DERSequence(var34));
         var34 = new ASN1EncodableVector();
         int[] var53 = var15[var95].getStatInt();
         var35.add(new ASN1Integer((long)var53[0]));
         var35.add(new ASN1Integer((long)var53[1]));
         var35.add(new ASN1Integer((long)var53[2]));
         var35.add(new ASN1Integer((long)var53[3]));
         var33.add(new DERSequence(var35));
         var35 = new ASN1EncodableVector();
         var94.add(new DERSequence(var33));
         var33 = new ASN1EncodableVector();
      }

      var23.add(new DERSequence(var94));
      ASN1EncodableVector var96 = new ASN1EncodableVector();

      for(int var98 = 0; var98 < var16.length; ++var98) {
         var96.add(new ASN1Integer((long)var16[var98]));
      }

      var23.add(new DERSequence(var96));
      ASN1EncodableVector var99 = new ASN1EncodableVector();

      for(int var100 = 0; var100 < var17.length; ++var100) {
         var99.add(new DEROctetString(var17[var100]));
      }

      var23.add(new DERSequence(var99));
      ASN1EncodableVector var101 = new ASN1EncodableVector();
      ASN1EncodableVector var54 = new ASN1EncodableVector();
      new ASN1EncodableVector();
      ASN1EncodableVector var56 = new ASN1EncodableVector();
      ASN1EncodableVector var57 = new ASN1EncodableVector();
      ASN1EncodableVector var58 = new ASN1EncodableVector();
      ASN1EncodableVector var59 = new ASN1EncodableVector();

      int var61;
      for(int var60 = 0; var60 < var18.length; ++var60) {
         var54.add(new DERSequence(var22[0]));
         new ASN1EncodableVector();
         var61 = var18[var60].getStatInt()[0];
         int var62 = var18[var60].getStatInt()[7];
         var56.add(new DEROctetString(var18[var60].getStatByte()[0]));

         int var63;
         for(var63 = 0; var63 < var61; ++var63) {
            var56.add(new DEROctetString(var18[var60].getStatByte()[1 + var63]));
         }

         for(var63 = 0; var63 < var62; ++var63) {
            var56.add(new DEROctetString(var18[var60].getStatByte()[1 + var61 + var63]));
         }

         var54.add(new DERSequence(var56));
         var56 = new ASN1EncodableVector();
         var57.add(new ASN1Integer((long)var61));
         var57.add(new ASN1Integer((long)var18[var60].getStatInt()[1]));
         var57.add(new ASN1Integer((long)var18[var60].getStatInt()[2]));
         var57.add(new ASN1Integer((long)var18[var60].getStatInt()[3]));
         var57.add(new ASN1Integer((long)var18[var60].getStatInt()[4]));
         var57.add(new ASN1Integer((long)var18[var60].getStatInt()[5]));
         var57.add(new ASN1Integer((long)var18[var60].getStatInt()[6]));
         var57.add(new ASN1Integer((long)var62));

         for(var63 = 0; var63 < var61; ++var63) {
            var57.add(new ASN1Integer((long)var18[var60].getStatInt()[8 + var63]));
         }

         for(var63 = 0; var63 < var62; ++var63) {
            var57.add(new ASN1Integer((long)var18[var60].getStatInt()[8 + var61 + var63]));
         }

         var54.add(new DERSequence(var57));
         var57 = new ASN1EncodableVector();
         var33 = new ASN1EncodableVector();
         var34 = new ASN1EncodableVector();
         var35 = new ASN1EncodableVector();
         int var64;
         if (var18[var60].getTreehash() != null) {
            for(var63 = 0; var63 < var18[var60].getTreehash().length; ++var63) {
               var33.add(new DERSequence(var22[0]));
               var62 = var18[var60].getTreehash()[var63].getStatInt()[1];
               var34.add(new DEROctetString(var18[var60].getTreehash()[var63].getStatByte()[0]));
               var34.add(new DEROctetString(var18[var60].getTreehash()[var63].getStatByte()[1]));
               var34.add(new DEROctetString(var18[var60].getTreehash()[var63].getStatByte()[2]));

               for(var64 = 0; var64 < var62; ++var64) {
                  var34.add(new DEROctetString(var18[var60].getTreehash()[var63].getStatByte()[3 + var64]));
               }

               var33.add(new DERSequence(var34));
               var34 = new ASN1EncodableVector();
               var35.add(new ASN1Integer((long)var18[var60].getTreehash()[var63].getStatInt()[0]));
               var35.add(new ASN1Integer((long)var62));
               var35.add(new ASN1Integer((long)var18[var60].getTreehash()[var63].getStatInt()[2]));
               var35.add(new ASN1Integer((long)var18[var60].getTreehash()[var63].getStatInt()[3]));
               var35.add(new ASN1Integer((long)var18[var60].getTreehash()[var63].getStatInt()[4]));
               var35.add(new ASN1Integer((long)var18[var60].getTreehash()[var63].getStatInt()[5]));

               for(var64 = 0; var64 < var62; ++var64) {
                  var35.add(new ASN1Integer((long)var18[var60].getTreehash()[var63].getStatInt()[6 + var64]));
               }

               var33.add(new DERSequence(var35));
               var35 = new ASN1EncodableVector();
               var58.add(new DERSequence(var33));
               var33 = new ASN1EncodableVector();
            }
         }

         var54.add(new DERSequence(var58));
         var58 = new ASN1EncodableVector();
         var85 = new ASN1EncodableVector();
         if (var18[var60].getRetain() != null) {
            for(var63 = 0; var63 < var18[var60].getRetain().length; ++var63) {
               for(var64 = 0; var64 < var18[var60].getRetain()[var63].size(); ++var64) {
                  var85.add(new DEROctetString((byte[])((byte[])var18[var60].getRetain()[var63].elementAt(var64))));
               }

               var59.add(new DERSequence(var85));
               var85 = new ASN1EncodableVector();
            }
         }

         var54.add(new DERSequence(var59));
         var59 = new ASN1EncodableVector();
         var101.add(new DERSequence(var54));
         var54 = new ASN1EncodableVector();
      }

      var23.add(new DERSequence(var101));
      ASN1EncodableVector var102 = new ASN1EncodableVector();

      for(var61 = 0; var61 < var19.length; ++var61) {
         var102.add(new DEROctetString(var19[var61]));
      }

      var23.add(new DERSequence(var102));
      ASN1EncodableVector var103 = new ASN1EncodableVector();
      ASN1EncodableVector var104 = new ASN1EncodableVector();
      new ASN1EncodableVector();
      ASN1EncodableVector var105 = new ASN1EncodableVector();
      ASN1EncodableVector var65 = new ASN1EncodableVector();

      for(int var66 = 0; var66 < var20.length; ++var66) {
         var104.add(new DERSequence(var22[0]));
         new ASN1EncodableVector();
         var105.add(new DEROctetString(var20[var66].getStatByte()[0]));
         var105.add(new DEROctetString(var20[var66].getStatByte()[1]));
         var105.add(new DEROctetString(var20[var66].getStatByte()[2]));
         var105.add(new DEROctetString(var20[var66].getStatByte()[3]));
         var105.add(new DEROctetString(var20[var66].getStatByte()[4]));
         var104.add(new DERSequence(var105));
         var105 = new ASN1EncodableVector();
         var65.add(new ASN1Integer((long)var20[var66].getStatInt()[0]));
         var65.add(new ASN1Integer((long)var20[var66].getStatInt()[1]));
         var65.add(new ASN1Integer((long)var20[var66].getStatInt()[2]));
         var65.add(new ASN1Integer((long)var20[var66].getStatInt()[3]));
         var65.add(new ASN1Integer((long)var20[var66].getStatInt()[4]));
         var65.add(new ASN1Integer((long)var20[var66].getStatInt()[5]));
         var65.add(new ASN1Integer((long)var20[var66].getStatInt()[6]));
         var65.add(new ASN1Integer((long)var20[var66].getStatInt()[7]));
         var65.add(new ASN1Integer((long)var20[var66].getStatInt()[8]));
         var104.add(new DERSequence(var65));
         var65 = new ASN1EncodableVector();
         var103.add(new DERSequence(var104));
         var104 = new ASN1EncodableVector();
      }

      var23.add(new DERSequence(var103));
      ASN1EncodableVector var106 = new ASN1EncodableVector();
      ASN1EncodableVector var67 = new ASN1EncodableVector();
      ASN1EncodableVector var68 = new ASN1EncodableVector();
      ASN1EncodableVector var69 = new ASN1EncodableVector();

      for(int var70 = 0; var70 < var21.getHeightOfTrees().length; ++var70) {
         var67.add(new ASN1Integer((long)var21.getHeightOfTrees()[var70]));
         var68.add(new ASN1Integer((long)var21.getWinternitzParameter()[var70]));
         var69.add(new ASN1Integer((long)var21.getK()[var70]));
      }

      var106.add(new ASN1Integer((long)var21.getNumOfLayers()));
      var106.add(new DERSequence(var67));
      var106.add(new DERSequence(var68));
      var106.add(new DERSequence(var69));
      var23.add(new DERSequence(var106));
      ASN1EncodableVector var107 = new ASN1EncodableVector();

      for(int var71 = 0; var71 < var22.length; ++var71) {
         var107.add(var22[var71]);
      }

      var23.add(new DERSequence(var107));
      return new DERSequence(var23);
   }

   private static int checkBigIntegerInIntRange(ASN1Encodable var0) {
      BigInteger var1 = ((ASN1Integer)var0).getValue();
      if (var1.compareTo(BigInteger.valueOf(2147483647L)) <= 0 && var1.compareTo(BigInteger.valueOf(-2147483648L)) >= 0) {
         return var1.intValue();
      } else {
         throw new IllegalArgumentException("BigInteger not in Range: " + var1.toString());
      }
   }

   public ASN1Primitive toASN1Primitive() {
      return this.primitive;
   }
}
