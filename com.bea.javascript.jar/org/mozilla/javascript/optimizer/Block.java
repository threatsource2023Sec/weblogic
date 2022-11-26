package org.mozilla.javascript.optimizer;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Hashtable;
import java.util.Vector;
import org.mozilla.javascript.IRFactory;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.TokenStream;
import org.mozilla.javascript.VariableTable;

public class Block {
   private IRFactory itsIRFactory;
   private Block[] itsSuccessors;
   private Block[] itsPredecessors;
   private int itsStartNodeIndex;
   private int itsEndNodeIndex;
   private Node[] itsStatementNodes;
   private int itsBlockID;
   private DataFlowBitSet itsLiveOnEntrySet;
   private DataFlowBitSet itsLiveOnExitSet;
   private DataFlowBitSet itsUseBeforeDefSet;
   private DataFlowBitSet itsNotDefSet;

   public Block(int var1, int var2, Node[] var3) {
      this.itsStartNodeIndex = var1;
      this.itsEndNodeIndex = var2;
      this.itsStatementNodes = var3;
   }

   public static Block[] buildBlocks(Node[] var0) {
      Hashtable var1 = new Hashtable();
      Vector var2 = new Vector();
      int var3 = 0;

      FatBlock var5;
      for(int var4 = 0; var4 < var0.length; ++var4) {
         switch (var0[var4].getType()) {
            case 6:
            case 7:
            case 8:
               var5 = new FatBlock(var3, var4, var0);
               if (var0[var3].getType() == 137) {
                  var1.put(var0[var3], var5);
               }

               var2.addElement(var5);
               var3 = var4 + 1;
               break;
            case 137:
               if (var4 != var3) {
                  var5 = new FatBlock(var3, var4 - 1, var0);
                  if (var0[var3].getType() == 137) {
                     var1.put(var0[var3], var5);
                  }

                  var2.addElement(var5);
                  var3 = var4;
               }
         }
      }

      if (var3 != var0.length) {
         var5 = new FatBlock(var3, var0.length - 1, var0);
         if (var0[var3].getType() == 137) {
            var1.put(var0[var3], var5);
         }

         var2.addElement(var5);
      }

      for(int var11 = 0; var11 < var2.size(); ++var11) {
         FatBlock var6 = (FatBlock)var2.elementAt(var11);
         Node var7 = var6.getEndNode();
         int var8 = var7.getType();
         if (var8 != 6 && var11 < var2.size() - 1) {
            FatBlock var9 = (FatBlock)var2.elementAt(var11 + 1);
            var6.addSuccessor(var9);
            var9.addPredecessor(var6);
         }

         if (var8 == 8 || var8 == 7 || var8 == 6) {
            Node var15 = (Node)var7.getProp(1);
            FatBlock var10 = (FatBlock)var1.get(var15);
            var15.putProp(23, var10.getSlimmerSelf());
            var6.addSuccessor(var10);
            var10.addPredecessor(var6);
         }
      }

      Block[] var12 = new Block[var2.size()];

      for(int var13 = 0; var13 < var2.size(); ++var13) {
         FatBlock var14 = (FatBlock)var2.elementAt(var13);
         var12[var13] = var14.diet();
         var12[var13].setBlockID(var13);
      }

      return var12;
   }

   boolean doReachedUseDataFlow() {
      this.itsLiveOnExitSet.clear();
      if (this.itsSuccessors != null) {
         for(int var1 = 0; var1 < this.itsSuccessors.length; ++var1) {
            this.itsLiveOnExitSet.or(this.itsSuccessors[var1].itsLiveOnEntrySet);
         }
      }

      return this.itsLiveOnEntrySet.df2(this.itsLiveOnExitSet, this.itsUseBeforeDefSet, this.itsNotDefSet);
   }

   public boolean doTypeFlow() {
      boolean var1 = false;

      for(int var2 = this.itsStartNodeIndex; var2 <= this.itsEndNodeIndex; ++var2) {
         Node var3 = this.itsStatementNodes[var2];
         if (var3 != null) {
            var1 |= this.findDefPoints(var3);
         }
      }

      return var1;
   }

   boolean findDefPoints(Node var1) {
      boolean var2 = false;
      Node var3;
      OptLocalVariable var4;
      Node var5;
      switch (var1.getType()) {
         case 40:
            var3 = var1.getFirstChild();
            Node var7 = var3.getNextSibling();
            var5 = var7.getNextSibling();
            if (var3 != null) {
               if (var3.getType() == 72) {
                  OptLocalVariable var8 = (OptLocalVariable)var3.getProp(24);
                  if (var8 != null) {
                     var8.assignType(3);
                  }
               }

               var2 |= this.findDefPoints(var3);
            }

            if (var7 != null) {
               var2 |= this.findDefPoints(var7);
            }

            if (var5 != null) {
               var2 |= this.findDefPoints(var5);
            }
            break;
         case 73:
            var3 = var1.getFirstChild();
            var4 = (OptLocalVariable)var1.getProp(24);
            if (var4 != null) {
               var5 = var3.getNextSibling();
               int var6 = this.findExpressionType(var5);
               var2 |= var4.assignType(var6);
            }
            break;
         case 106:
         case 107:
            var3 = var1.getFirstChild();
            var4 = (OptLocalVariable)var3.getProp(24);
            if (var4 != null) {
               var2 |= var4.assignType(1);
            }
            break;
         default:
            for(var3 = var1.getFirstChild(); var3 != null; var3 = var3.getNextSibling()) {
               var2 |= this.findDefPoints(var3);
            }
      }

      return var2;
   }

   void findDefs() {
      for(int var1 = this.itsStartNodeIndex; var1 <= this.itsEndNodeIndex; ++var1) {
         Node var2 = this.itsStatementNodes[var1];
         if (var2 != null) {
            this.findDefPoints(var2);
         }
      }

   }

   int findExpressionType(Node var1) {
      int var3;
      Node var5;
      switch (var1.getType()) {
         case 23:
            var5 = var1.getFirstChild();
            var3 = this.findExpressionType(var5);
            int var4 = this.findExpressionType(var5.getNextSibling());
            return var3 | var4;
         case 30:
         case 43:
            return 0;
         case 41:
            return 3;
         case 45:
            return 1;
         case 72:
            OptLocalVariable var2 = (OptLocalVariable)var1.getProp(24);
            if (var2 != null) {
               return var2.getTypeUnion();
            }
         case 11:
         case 12:
         case 13:
         case 20:
         case 21:
         case 22:
         case 24:
         case 26:
         case 27:
         case 106:
         case 107:
            return 1;
         default:
            var5 = var1.getFirstChild();
            if (var5 == null) {
               return 3;
            } else {
               for(var3 = 0; var5 != null; var5 = var5.getNextSibling()) {
                  var3 |= this.findExpressionType(var5);
               }

               return var3;
            }
      }
   }

   public int getBlockID() {
      return this.itsBlockID;
   }

   public Node getEndNode() {
      return this.itsStatementNodes[this.itsEndNodeIndex];
   }

   public Block[] getPredecessorList() {
      return this.itsPredecessors;
   }

   public Node getStartNode() {
      return this.itsStatementNodes[this.itsStartNodeIndex];
   }

   public Block[] getSuccessorList() {
      return this.itsSuccessors;
   }

   public void initLiveOnEntrySets(VariableTable var1) {
      int var2 = var1.size();
      Node[] var3 = new Node[var2];
      this.itsUseBeforeDefSet = new DataFlowBitSet(var2);
      this.itsNotDefSet = new DataFlowBitSet(var2);
      this.itsLiveOnEntrySet = new DataFlowBitSet(var2);
      this.itsLiveOnExitSet = new DataFlowBitSet(var2);

      for(int var4 = this.itsStartNodeIndex; var4 <= this.itsEndNodeIndex; ++var4) {
         Node var5 = this.itsStatementNodes[var4];
         this.lookForVariableAccess(var5, var3);
      }

      for(int var6 = 0; var6 < var2; ++var6) {
         if (var3[var6] != null) {
            var3[var6].putProp(25, this);
         }
      }

      this.itsNotDefSet.not();
   }

   public boolean isLiveOnEntry(int var1) {
      return this.itsLiveOnEntrySet != null && this.itsLiveOnEntrySet.test(var1);
   }

   Hashtable localCSE(Hashtable var1, OptFunctionNode var2) {
      this.itsIRFactory = new IRFactory((TokenStream)null, (Scriptable)null);
      if (var1 == null) {
         var1 = new Hashtable(5);
      }

      for(int var3 = this.itsStartNodeIndex; var3 <= this.itsEndNodeIndex; ++var3) {
         Node var4 = this.itsStatementNodes[var3];
         if (var4 != null) {
            this.localCSE((Node)null, var4, var1, var2);
         }
      }

      return var1;
   }

   void localCSE(Node var1, Node var2, Hashtable var3, OptFunctionNode var4) {
      Node var5;
      Node var6;
      Node var7;
      switch (var2.getType()) {
         case 39:
            var5 = var2.getFirstChild();
            if (var5 != null) {
               this.localCSE(var2, var5, var3, var4);
            }

            if (var5.getType() == 109 && var5.getInt() == 50) {
               var6 = var5.getNextSibling();
               if (var6.getType() == 46) {
                  String var12 = var6.getString();
                  Object var8 = var3.get(var12);
                  if (var8 == null) {
                     var3.put(var12, new CSEHolder(var1, var2));
                  } else if (var1 != null) {
                     Node var9;
                     Node var11;
                     if (var8 instanceof CSEHolder) {
                        CSEHolder var10 = (CSEHolder)var8;
                        var11 = var10.getPropChild.getNextSibling();
                        var10.getPropParent.removeChild(var10.getPropChild);
                        var9 = this.itsIRFactory.createNewLocal(var10.getPropChild);
                        var4.incrementLocalCount();
                        if (var11 == null) {
                           var10.getPropParent.addChildToBack(var9);
                        } else {
                           var10.getPropParent.addChildBefore(var9, var11);
                        }

                        var3.put(var12, var9);
                     } else {
                        var9 = (Node)var8;
                     }

                     Node var13 = var2.getNextSibling();
                     var1.removeChild(var2);
                     var11 = this.itsIRFactory.createUseLocal(var9);
                     if (var13 == null) {
                        var1.addChildToBack(var11);
                     } else {
                        var1.addChildBefore(var11, var13);
                     }
                  }
               }
            }
            break;
         case 40:
            var5 = var2.getFirstChild();
            var6 = var5.getNextSibling();
            var7 = var6.getNextSibling();
            if (var5 != null) {
               this.localCSE(var2, var5, var3, var4);
            }

            if (var6 != null) {
               this.localCSE(var2, var6, var3, var4);
            }

            if (var7 != null) {
               this.localCSE(var2, var7, var3, var4);
            }

            if (var6.getType() == 46) {
               var3.remove(var6.getString());
            } else {
               var3.clear();
            }
            break;
         case 42:
            var5 = var2.getFirstChild();
            var6 = var5.getNextSibling();
            var7 = var6.getNextSibling();
            if (var5 != null) {
               this.localCSE(var2, var5, var3, var4);
            }

            if (var6 != null) {
               this.localCSE(var2, var6, var3, var4);
            }

            if (var7 != null) {
               this.localCSE(var2, var7, var3, var4);
            }

            var3.clear();
            break;
         case 43:
            for(var5 = var2.getFirstChild(); var5 != null; var5 = var5.getNextSibling()) {
               this.localCSE(var2, var5, var3, var4);
            }

            var3.clear();
            break;
         case 106:
         case 107:
            var5 = var2.getFirstChild();
            if (var5.getType() == 39) {
               var6 = var5.getFirstChild().getNextSibling();
               if (var6.getType() == 46) {
                  var3.remove(var6.getString());
               } else {
                  var3.clear();
               }
            } else if (var5.getType() != 72) {
               var3.clear();
            }
            break;
         default:
            for(var5 = var2.getFirstChild(); var5 != null; var5 = var5.getNextSibling()) {
               this.localCSE(var2, var5, var3, var4);
            }
      }

   }

   void lookForVariableAccess(Node var1, Node[] var2) {
      Node var3;
      switch (var1.getType()) {
         case 72:
            Object var7 = var1.getProp(24);
            if (var7 != null) {
               int var9 = ((OptLocalVariable)var7).getIndex();
               if (!this.itsNotDefSet.test(var9)) {
                  this.itsUseBeforeDefSet.set(var9);
               }

               var2[var9] = var1;
            }
            break;
         case 73:
            var3 = var1.getFirstChild();
            Node var8 = var3.getNextSibling();
            this.lookForVariableAccess(var8, var2);
            Object var10 = var1.getProp(24);
            if (var10 != null) {
               int var6 = ((OptLocalVariable)var10).getIndex();
               this.itsNotDefSet.set(var6);
               if (var2[var6] != null) {
                  var2[var6].putProp(25, var10);
               }
            }
            break;
         case 106:
         case 107:
            var3 = var1.getFirstChild();
            if (var3.getType() == 72) {
               Object var4 = var3.getProp(24);
               if (var4 != null) {
                  int var5 = ((OptLocalVariable)var4).getIndex();
                  if (!this.itsNotDefSet.test(var5)) {
                     this.itsUseBeforeDefSet.set(var5);
                  }

                  this.itsNotDefSet.set(var5);
               }
            }
            break;
         default:
            for(var3 = var1.getFirstChild(); var3 != null; var3 = var3.getNextSibling()) {
               this.lookForVariableAccess(var3, var2);
            }
      }

   }

   void lookForVariablesAndCalls(Node var1, boolean[] var2, VariableTable var3) {
      Node var4;
      int var9;
      switch (var1.getType()) {
         case 43:
            for(var4 = var1.getFirstChild(); var4 != null; var4 = var4.getNextSibling()) {
               this.lookForVariablesAndCalls(var4, var2, var3);
            }

            for(var9 = 0; var9 < var2.length; ++var9) {
               if (var2[var9]) {
                  ((OptLocalVariable)var3.getVariable(var9)).markLiveAcrossCall();
               }
            }

            return;
         case 72:
            Object var8 = var1.getProp(24);
            if (var8 != null) {
               var9 = ((OptLocalVariable)var8).getIndex();
               if (var1.getProp(25) != null && !this.itsLiveOnExitSet.test(var9)) {
                  var2[var9] = false;
               }
            }
            break;
         case 73:
            var4 = var1.getFirstChild();
            Node var5 = var4.getNextSibling();
            this.lookForVariablesAndCalls(var5, var2, var3);
            Object var6 = var1.getProp(24);
            if (var6 != null) {
               int var7 = ((OptLocalVariable)var6).getIndex();
               var2[var7] = true;
            }
            break;
         default:
            for(var4 = var1.getFirstChild(); var4 != null; var4 = var4.getNextSibling()) {
               this.lookForVariablesAndCalls(var4, var2, var3);
            }
      }

   }

   void markAnyTypeVariables(VariableTable var1) {
      for(int var2 = 0; var2 < var1.size(); ++var2) {
         if (this.itsLiveOnEntrySet.test(var2)) {
            ((OptLocalVariable)var1.getVariable(var2)).assignType(3);
         }
      }

   }

   void markVolatileVariables(VariableTable var1) {
      boolean[] var2 = new boolean[var1.size()];

      for(int var3 = 0; var3 < var2.length; ++var3) {
         var2[var3] = this.itsLiveOnEntrySet.test(var3);
      }

      for(int var4 = this.itsStartNodeIndex; var4 <= this.itsEndNodeIndex; ++var4) {
         Node var5 = this.itsStatementNodes[var4];
         this.lookForVariablesAndCalls(var5, var2, var1);
      }

   }

   public void printLiveOnEntrySet(PrintWriter var1, VariableTable var2) {
      for(int var3 = 0; var3 < var2.size(); ++var3) {
         if (this.itsUseBeforeDefSet.test(var3)) {
            var1.println(var2.getVariable(var3).getName() + " is used before def'd");
         }

         if (this.itsNotDefSet.test(var3)) {
            var1.println(var2.getVariable(var3).getName() + " is not def'd");
         }

         if (this.itsLiveOnEntrySet.test(var3)) {
            var1.println(var2.getVariable(var3).getName() + " is live on entry");
         }

         if (this.itsLiveOnExitSet.test(var3)) {
            var1.println(var2.getVariable(var3).getName() + " is live on exit");
         }
      }

   }

   public void setBlockID(int var1) {
      this.itsBlockID = var1;
   }

   public void setPredecessorList(Block[] var1) {
      this.itsPredecessors = var1;
   }

   public void setSuccessorList(Block[] var1) {
      this.itsSuccessors = var1;
   }

   public static String toString(Block[] var0, Node[] var1) {
      StringWriter var2 = new StringWriter();
      PrintWriter var3 = new PrintWriter(var2);
      var3.println(var0.length + " Blocks");

      for(int var4 = 0; var4 < var0.length; ++var4) {
         Block var5 = var0[var4];
         var3.println("#" + var5.itsBlockID);
         var3.println("from " + var5.itsStartNodeIndex + " " + var1[var5.itsStartNodeIndex].toString());
         var3.println("thru " + var5.itsEndNodeIndex + " " + var1[var5.itsEndNodeIndex].toString());
         var3.print("Predecessors ");
         int var6;
         if (var5.itsPredecessors == null) {
            var3.println("none");
         } else {
            for(var6 = 0; var6 < var5.itsPredecessors.length; ++var6) {
               var3.print(var5.itsPredecessors[var6].getBlockID() + " ");
            }

            var3.println();
         }

         var3.print("Successors ");
         if (var5.itsSuccessors == null) {
            var3.println("none");
         } else {
            for(var6 = 0; var6 < var5.itsSuccessors.length; ++var6) {
               var3.print(var5.itsSuccessors[var6].getBlockID() + " ");
            }

            var3.println();
         }
      }

      return var2.toString();
   }
}
