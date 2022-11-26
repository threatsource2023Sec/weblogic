package org.python.apache.xerces.impl.xs.models;

import java.util.HashMap;
import java.util.Vector;
import org.python.apache.xerces.impl.dtd.models.CMNode;
import org.python.apache.xerces.impl.dtd.models.CMStateSet;
import org.python.apache.xerces.impl.xs.SubstitutionGroupHandler;
import org.python.apache.xerces.impl.xs.XMLSchemaException;
import org.python.apache.xerces.impl.xs.XSConstraints;
import org.python.apache.xerces.impl.xs.XSElementDecl;
import org.python.apache.xerces.impl.xs.XSWildcardDecl;
import org.python.apache.xerces.xni.QName;

public class XSDFACM implements XSCMValidator {
   private static final boolean DEBUG = false;
   private static final boolean DEBUG_VALIDATE_CONTENT = false;
   private Object[] fElemMap = null;
   private int[] fElemMapType = null;
   private int[] fElemMapId = null;
   private int fElemMapSize = 0;
   private boolean[] fFinalStateFlags = null;
   private CMStateSet[] fFollowList = null;
   private CMNode fHeadNode = null;
   private int fLeafCount = 0;
   private XSCMLeaf[] fLeafList = null;
   private int[] fLeafListType = null;
   private int[][] fTransTable = null;
   private Occurence[] fCountingStates = null;
   private int fTransTableSize = 0;
   private boolean fIsCompactedForUPA;
   private static long time = 0L;

   public XSDFACM(CMNode var1, int var2) {
      this.fLeafCount = var2;
      this.fIsCompactedForUPA = var1.isCompactedForUPA();
      this.buildDFA(var1);
   }

   public boolean isFinalState(int var1) {
      return var1 < 0 ? false : this.fFinalStateFlags[var1];
   }

   public Object oneTransition(QName var1, int[] var2, SubstitutionGroupHandler var3) {
      int var4 = var2[0];
      if (var4 != -1 && var4 != -2) {
         int var5 = 0;
         int var6 = 0;

         Object var7;
         for(var7 = null; var6 < this.fElemMapSize; ++var6) {
            var5 = this.fTransTable[var4][var6];
            if (var5 != -1) {
               int var8 = this.fElemMapType[var6];
               if (var8 == 1) {
                  var7 = var3.getMatchingElemDecl(var1, (XSElementDecl)this.fElemMap[var6]);
                  if (var7 != null) {
                     break;
                  }
               } else if (var8 == 2 && ((XSWildcardDecl)this.fElemMap[var6]).allowNamespace(var1.uri)) {
                  var7 = this.fElemMap[var6];
                  break;
               }
            }
         }

         if (var6 == this.fElemMapSize) {
            var2[1] = var2[0];
            var2[0] = -1;
            return this.findMatchingDecl(var1, var3);
         } else {
            if (this.fCountingStates != null) {
               Occurence var9 = this.fCountingStates[var4];
               if (var9 != null) {
                  if (var4 == var5) {
                     if (++var2[2] > var9.maxOccurs && var9.maxOccurs != -1) {
                        return this.findMatchingDecl(var1, var2, var3, var6);
                     }
                  } else {
                     if (var2[2] < var9.minOccurs) {
                        var2[1] = var2[0];
                        var2[0] = -1;
                        return this.findMatchingDecl(var1, var3);
                     }

                     var9 = this.fCountingStates[var5];
                     if (var9 != null) {
                        var2[2] = var6 == var9.elemIndex ? 1 : 0;
                     }
                  }
               } else {
                  var9 = this.fCountingStates[var5];
                  if (var9 != null) {
                     var2[2] = var6 == var9.elemIndex ? 1 : 0;
                  }
               }
            }

            var2[0] = var5;
            return var7;
         }
      } else {
         if (var4 == -1) {
            var2[0] = -2;
         }

         return this.findMatchingDecl(var1, var3);
      }
   }

   Object findMatchingDecl(QName var1, SubstitutionGroupHandler var2) {
      XSElementDecl var3 = null;

      for(int var4 = 0; var4 < this.fElemMapSize; ++var4) {
         int var5 = this.fElemMapType[var4];
         if (var5 == 1) {
            var3 = var2.getMatchingElemDecl(var1, (XSElementDecl)this.fElemMap[var4]);
            if (var3 != null) {
               return var3;
            }
         } else if (var5 == 2 && ((XSWildcardDecl)this.fElemMap[var4]).allowNamespace(var1.uri)) {
            return this.fElemMap[var4];
         }
      }

      return null;
   }

   Object findMatchingDecl(QName var1, int[] var2, SubstitutionGroupHandler var3, int var4) {
      int var5 = var2[0];
      int var6 = 0;
      Object var7 = null;

      while(true) {
         ++var4;
         if (var4 >= this.fElemMapSize) {
            break;
         }

         var6 = this.fTransTable[var5][var4];
         if (var6 != -1) {
            int var8 = this.fElemMapType[var4];
            if (var8 == 1) {
               var7 = var3.getMatchingElemDecl(var1, (XSElementDecl)this.fElemMap[var4]);
               if (var7 != null) {
                  break;
               }
            } else if (var8 == 2 && ((XSWildcardDecl)this.fElemMap[var4]).allowNamespace(var1.uri)) {
               var7 = this.fElemMap[var4];
               break;
            }
         }
      }

      if (var4 == this.fElemMapSize) {
         var2[1] = var2[0];
         var2[0] = -1;
         return this.findMatchingDecl(var1, var3);
      } else {
         var2[0] = var6;
         Occurence var9 = this.fCountingStates[var6];
         if (var9 != null) {
            var2[2] = var4 == var9.elemIndex ? 1 : 0;
         }

         return var7;
      }
   }

   public int[] startContentModel() {
      return new int[3];
   }

   public boolean endContentModel(int[] var1) {
      int var2 = var1[0];
      if (this.fFinalStateFlags[var2]) {
         if (this.fCountingStates != null) {
            Occurence var3 = this.fCountingStates[var2];
            if (var3 != null && var1[2] < var3.minOccurs) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private void buildDFA(CMNode var1) {
      int var2 = this.fLeafCount;
      XSCMLeaf var3 = new XSCMLeaf(1, (Object)null, -1, this.fLeafCount++);
      this.fHeadNode = new XSCMBinOp(102, var1, var3);
      this.fLeafList = new XSCMLeaf[this.fLeafCount];
      this.fLeafListType = new int[this.fLeafCount];
      this.postTreeBuildInit(this.fHeadNode);
      this.fFollowList = new CMStateSet[this.fLeafCount];

      for(int var4 = 0; var4 < this.fLeafCount; ++var4) {
         this.fFollowList[var4] = new CMStateSet(this.fLeafCount);
      }

      this.calcFollowList(this.fHeadNode);
      this.fElemMap = new Object[this.fLeafCount];
      this.fElemMapType = new int[this.fLeafCount];
      this.fElemMapId = new int[this.fLeafCount];
      this.fElemMapSize = 0;
      Occurence[] var5 = null;

      int var8;
      for(int var6 = 0; var6 < this.fLeafCount; ++var6) {
         this.fElemMap[var6] = null;
         int var7 = 0;

         for(var8 = this.fLeafList[var6].getParticleId(); var7 < this.fElemMapSize && var8 != this.fElemMapId[var7]; ++var7) {
         }

         if (var7 == this.fElemMapSize) {
            XSCMLeaf var9 = this.fLeafList[var6];
            this.fElemMap[this.fElemMapSize] = var9.getLeaf();
            if (var9 instanceof XSCMRepeatingLeaf) {
               if (var5 == null) {
                  var5 = new Occurence[this.fLeafCount];
               }

               var5[this.fElemMapSize] = new Occurence((XSCMRepeatingLeaf)var9, this.fElemMapSize);
            }

            this.fElemMapType[this.fElemMapSize] = this.fLeafListType[var6];
            this.fElemMapId[this.fElemMapSize] = var8;
            ++this.fElemMapSize;
         }
      }

      --this.fElemMapSize;
      int[] var27 = new int[this.fLeafCount + this.fElemMapSize];
      var8 = 0;

      int var10;
      for(int var28 = 0; var28 < this.fElemMapSize; ++var28) {
         var10 = this.fElemMapId[var28];

         for(int var11 = 0; var11 < this.fLeafCount; ++var11) {
            if (var10 == this.fLeafList[var11].getParticleId()) {
               var27[var8++] = var11;
            }
         }

         var27[var8++] = -1;
      }

      var10 = this.fLeafCount * 4;
      CMStateSet[] var29 = new CMStateSet[var10];
      this.fFinalStateFlags = new boolean[var10];
      this.fTransTable = new int[var10][];
      CMStateSet var12 = this.fHeadNode.firstPos();
      int var13 = 0;
      int var14 = 0;
      this.fTransTable[var14] = this.makeDefStateList();
      var29[var14] = var12;
      ++var14;
      HashMap var15 = new HashMap();

      int var18;
      while(var13 < var14) {
         var12 = var29[var13];
         int[] var16 = this.fTransTable[var13];
         this.fFinalStateFlags[var13] = var12.getBit(var2);
         ++var13;
         CMStateSet var17 = null;
         var18 = 0;

         for(int var19 = 0; var19 < this.fElemMapSize; ++var19) {
            if (var17 == null) {
               var17 = new CMStateSet(this.fLeafCount);
            } else {
               var17.zeroBits();
            }

            for(int var20 = var27[var18++]; var20 != -1; var20 = var27[var18++]) {
               if (var12.getBit(var20)) {
                  var17.union(this.fFollowList[var20]);
               }
            }

            if (!var17.isEmpty()) {
               Integer var21 = (Integer)var15.get(var17);
               int var22 = var21 == null ? var14 : var21;
               if (var22 == var14) {
                  var29[var14] = var17;
                  this.fTransTable[var14] = this.makeDefStateList();
                  var15.put(var17, new Integer(var14));
                  ++var14;
                  var17 = null;
               }

               var16[var19] = var22;
               if (var14 == var10) {
                  int var23 = (int)((double)var10 * 1.5);
                  CMStateSet[] var24 = new CMStateSet[var23];
                  boolean[] var25 = new boolean[var23];
                  int[][] var26 = new int[var23][];
                  System.arraycopy(var29, 0, var24, 0, var10);
                  System.arraycopy(this.fFinalStateFlags, 0, var25, 0, var10);
                  System.arraycopy(this.fTransTable, 0, var26, 0, var10);
                  var10 = var23;
                  var29 = var24;
                  this.fFinalStateFlags = var25;
                  this.fTransTable = var26;
               }
            }
         }
      }

      if (var5 != null) {
         this.fCountingStates = new Occurence[var14];

         for(int var30 = 0; var30 < var14; ++var30) {
            int[] var31 = this.fTransTable[var30];

            for(var18 = 0; var18 < var31.length; ++var18) {
               if (var30 == var31[var18]) {
                  this.fCountingStates[var30] = var5[var18];
                  break;
               }
            }
         }
      }

      this.fHeadNode = null;
      this.fLeafList = null;
      this.fFollowList = null;
      this.fLeafListType = null;
      this.fElemMapId = null;
   }

   private void calcFollowList(CMNode var1) {
      if (var1.type() == 101) {
         this.calcFollowList(((XSCMBinOp)var1).getLeft());
         this.calcFollowList(((XSCMBinOp)var1).getRight());
      } else {
         CMStateSet var2;
         CMStateSet var3;
         int var4;
         if (var1.type() == 102) {
            this.calcFollowList(((XSCMBinOp)var1).getLeft());
            this.calcFollowList(((XSCMBinOp)var1).getRight());
            var2 = ((XSCMBinOp)var1).getLeft().lastPos();
            var3 = ((XSCMBinOp)var1).getRight().firstPos();

            for(var4 = 0; var4 < this.fLeafCount; ++var4) {
               if (var2.getBit(var4)) {
                  this.fFollowList[var4].union(var3);
               }
            }
         } else if (var1.type() != 4 && var1.type() != 6) {
            if (var1.type() == 5) {
               this.calcFollowList(((XSCMUniOp)var1).getChild());
            }
         } else {
            this.calcFollowList(((XSCMUniOp)var1).getChild());
            var2 = var1.firstPos();
            var3 = var1.lastPos();

            for(var4 = 0; var4 < this.fLeafCount; ++var4) {
               if (var3.getBit(var4)) {
                  this.fFollowList[var4].union(var2);
               }
            }
         }
      }

   }

   private void dumpTree(CMNode var1, int var2) {
      for(int var3 = 0; var3 < var2; ++var3) {
         System.out.print("   ");
      }

      int var4 = var1.type();
      switch (var4) {
         case 1:
            System.out.print("Leaf: (pos=" + ((XSCMLeaf)var1).getPosition() + "), " + "(elemIndex=" + ((XSCMLeaf)var1).getLeaf() + ") ");
            if (var1.isNullable()) {
               System.out.print(" Nullable ");
            }

            System.out.print("firstPos=");
            System.out.print(var1.firstPos().toString());
            System.out.print(" lastPos=");
            System.out.println(var1.lastPos().toString());
            break;
         case 2:
            System.out.print("Any Node: ");
            System.out.print("firstPos=");
            System.out.print(var1.firstPos().toString());
            System.out.print(" lastPos=");
            System.out.println(var1.lastPos().toString());
            break;
         case 4:
         case 5:
         case 6:
            System.out.print("Rep Node ");
            if (var1.isNullable()) {
               System.out.print("Nullable ");
            }

            System.out.print("firstPos=");
            System.out.print(var1.firstPos().toString());
            System.out.print(" lastPos=");
            System.out.println(var1.lastPos().toString());
            this.dumpTree(((XSCMUniOp)var1).getChild(), var2 + 1);
            break;
         case 101:
         case 102:
            if (var4 == 101) {
               System.out.print("Choice Node ");
            } else {
               System.out.print("Seq Node ");
            }

            if (var1.isNullable()) {
               System.out.print("Nullable ");
            }

            System.out.print("firstPos=");
            System.out.print(var1.firstPos().toString());
            System.out.print(" lastPos=");
            System.out.println(var1.lastPos().toString());
            this.dumpTree(((XSCMBinOp)var1).getLeft(), var2 + 1);
            this.dumpTree(((XSCMBinOp)var1).getRight(), var2 + 1);
            break;
         default:
            throw new RuntimeException("ImplementationMessages.VAL_NIICM");
      }

   }

   private int[] makeDefStateList() {
      int[] var1 = new int[this.fElemMapSize];

      for(int var2 = 0; var2 < this.fElemMapSize; ++var2) {
         var1[var2] = -1;
      }

      return var1;
   }

   private void postTreeBuildInit(CMNode var1) throws RuntimeException {
      var1.setMaxStates(this.fLeafCount);
      XSCMLeaf var2 = null;
      boolean var3 = false;
      int var4;
      if (var1.type() == 2) {
         var2 = (XSCMLeaf)var1;
         var4 = var2.getPosition();
         this.fLeafList[var4] = var2;
         this.fLeafListType[var4] = 2;
      } else if (var1.type() != 101 && var1.type() != 102) {
         if (var1.type() != 4 && var1.type() != 6 && var1.type() != 5) {
            if (var1.type() != 1) {
               throw new RuntimeException("ImplementationMessages.VAL_NIICM");
            }

            var2 = (XSCMLeaf)var1;
            var4 = var2.getPosition();
            this.fLeafList[var4] = var2;
            this.fLeafListType[var4] = 1;
         } else {
            this.postTreeBuildInit(((XSCMUniOp)var1).getChild());
         }
      } else {
         this.postTreeBuildInit(((XSCMBinOp)var1).getLeft());
         this.postTreeBuildInit(((XSCMBinOp)var1).getRight());
      }

   }

   public boolean checkUniqueParticleAttribution(SubstitutionGroupHandler var1) throws XMLSchemaException {
      byte[][] var2 = new byte[this.fElemMapSize][this.fElemMapSize];

      int var4;
      int var5;
      for(int var3 = 0; var3 < this.fTransTable.length && this.fTransTable[var3] != null; ++var3) {
         for(var4 = 0; var4 < this.fElemMapSize; ++var4) {
            for(var5 = var4 + 1; var5 < this.fElemMapSize; ++var5) {
               if (this.fTransTable[var3][var4] != -1 && this.fTransTable[var3][var5] != -1 && var2[var4][var5] == 0) {
                  if (XSConstraints.overlapUPA(this.fElemMap[var4], this.fElemMap[var5], var1)) {
                     if (this.fCountingStates != null) {
                        Occurence var6 = this.fCountingStates[var3];
                        if (var6 != null && this.fTransTable[var3][var4] == var3 ^ this.fTransTable[var3][var5] == var3 && var6.minOccurs == var6.maxOccurs) {
                           var2[var4][var5] = -1;
                           continue;
                        }
                     }

                     var2[var4][var5] = 1;
                  } else {
                     var2[var4][var5] = -1;
                  }
               }
            }
         }
      }

      for(var4 = 0; var4 < this.fElemMapSize; ++var4) {
         for(var5 = 0; var5 < this.fElemMapSize; ++var5) {
            if (var2[var4][var5] == 1) {
               throw new XMLSchemaException("cos-nonambig", new Object[]{this.fElemMap[var4].toString(), this.fElemMap[var5].toString()});
            }
         }
      }

      for(var5 = 0; var5 < this.fElemMapSize; ++var5) {
         if (this.fElemMapType[var5] == 2) {
            XSWildcardDecl var7 = (XSWildcardDecl)this.fElemMap[var5];
            if (var7.fType == 3 || var7.fType == 2) {
               return true;
            }
         }
      }

      return false;
   }

   public Vector whatCanGoHere(int[] var1) {
      int var2 = var1[0];
      if (var2 < 0) {
         var2 = var1[1];
      }

      Occurence var3 = this.fCountingStates != null ? this.fCountingStates[var2] : null;
      int var4 = var1[2];
      Vector var5 = new Vector();

      for(int var6 = 0; var6 < this.fElemMapSize; ++var6) {
         int var7 = this.fTransTable[var2][var6];
         if (var7 != -1) {
            if (var3 != null) {
               if (var2 == var7) {
                  if (var4 >= var3.maxOccurs && var3.maxOccurs != -1) {
                     continue;
                  }
               } else if (var4 < var3.minOccurs) {
                  continue;
               }
            }

            var5.addElement(this.fElemMap[var6]);
         }
      }

      return var5;
   }

   public int[] occurenceInfo(int[] var1) {
      if (this.fCountingStates != null) {
         int var2 = var1[0];
         if (var2 < 0) {
            var2 = var1[1];
         }

         Occurence var3 = this.fCountingStates[var2];
         if (var3 != null) {
            int[] var4 = new int[]{var3.minOccurs, var3.maxOccurs, var1[2], var3.elemIndex};
            return var4;
         }
      }

      return null;
   }

   public String getTermName(int var1) {
      Object var2 = this.fElemMap[var1];
      return var2 != null ? var2.toString() : null;
   }

   public boolean isCompactedForUPA() {
      return this.fIsCompactedForUPA;
   }

   static final class Occurence {
      final int minOccurs;
      final int maxOccurs;
      final int elemIndex;

      public Occurence(XSCMRepeatingLeaf var1, int var2) {
         this.minOccurs = var1.getMinOccurs();
         this.maxOccurs = var1.getMaxOccurs();
         this.elemIndex = var2;
      }

      public String toString() {
         return "minOccurs=" + this.minOccurs + ";maxOccurs=" + (this.maxOccurs != -1 ? Integer.toString(this.maxOccurs) : "unbounded");
      }
   }
}
