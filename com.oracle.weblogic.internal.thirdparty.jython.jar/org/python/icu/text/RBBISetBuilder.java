package org.python.icu.text;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.python.icu.impl.Assert;
import org.python.icu.impl.IntTrieBuilder;
import org.python.icu.impl.TrieBuilder;

class RBBISetBuilder {
   RBBIRuleBuilder fRB;
   RangeDescriptor fRangeList;
   IntTrieBuilder fTrie;
   int fGroupCount;
   boolean fSawBOF;
   RBBIDataManipulate dm = new RBBIDataManipulate();

   RBBISetBuilder(RBBIRuleBuilder rb) {
      this.fRB = rb;
   }

   void build() {
      if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("usets") >= 0) {
         this.printSets();
      }

      this.fRangeList = new RangeDescriptor();
      this.fRangeList.fStartChar = 0;
      this.fRangeList.fEndChar = 1114111;
      Iterator var1 = this.fRB.fUSetNodes.iterator();

      RangeDescriptor rlRange;
      while(var1.hasNext()) {
         RBBINode usetNode = (RBBINode)var1.next();
         UnicodeSet inputSet = usetNode.fInputSet;
         int inputSetRangeCount = inputSet.getRangeCount();
         int inputSetRangeIndex = 0;
         rlRange = this.fRangeList;

         while(inputSetRangeIndex < inputSetRangeCount) {
            int inputSetRangeBegin = inputSet.getRangeStart(inputSetRangeIndex);

            int inputSetRangeEnd;
            for(inputSetRangeEnd = inputSet.getRangeEnd(inputSetRangeIndex); rlRange.fEndChar < inputSetRangeBegin; rlRange = rlRange.fNext) {
            }

            if (rlRange.fStartChar < inputSetRangeBegin) {
               rlRange.split(inputSetRangeBegin);
            } else {
               if (rlRange.fEndChar > inputSetRangeEnd) {
                  rlRange.split(inputSetRangeEnd + 1);
               }

               if (rlRange.fIncludesSets.indexOf(usetNode) == -1) {
                  rlRange.fIncludesSets.add(usetNode);
               }

               if (inputSetRangeEnd == rlRange.fEndChar) {
                  ++inputSetRangeIndex;
               }

               rlRange = rlRange.fNext;
            }
         }
      }

      if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("range") >= 0) {
         this.printRanges();
      }

      for(rlRange = this.fRangeList; rlRange != null; rlRange = rlRange.fNext) {
         for(RangeDescriptor rlSearchRange = this.fRangeList; rlSearchRange != rlRange; rlSearchRange = rlSearchRange.fNext) {
            if (rlRange.fIncludesSets.equals(rlSearchRange.fIncludesSets)) {
               rlRange.fNum = rlSearchRange.fNum;
               break;
            }
         }

         if (rlRange.fNum == 0) {
            ++this.fGroupCount;
            rlRange.fNum = this.fGroupCount + 2;
            rlRange.setDictionaryFlag();
            this.addValToSets(rlRange.fIncludesSets, this.fGroupCount + 2);
         }
      }

      String eofString = "eof";
      String bofString = "bof";
      Iterator var12 = this.fRB.fUSetNodes.iterator();

      while(var12.hasNext()) {
         RBBINode usetNode = (RBBINode)var12.next();
         UnicodeSet inputSet = usetNode.fInputSet;
         if (inputSet.contains(eofString)) {
            this.addValToSet(usetNode, 1);
         }

         if (inputSet.contains(bofString)) {
            this.addValToSet(usetNode, 2);
            this.fSawBOF = true;
         }
      }

      if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("rgroup") >= 0) {
         this.printRangeGroups();
      }

      if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("esets") >= 0) {
         this.printSets();
      }

      this.fTrie = new IntTrieBuilder((int[])null, 100000, 0, 0, true);

      for(rlRange = this.fRangeList; rlRange != null; rlRange = rlRange.fNext) {
         this.fTrie.setRange(rlRange.fStartChar, rlRange.fEndChar + 1, rlRange.fNum, true);
      }

   }

   int getTrieSize() {
      int size = 0;

      try {
         size = this.fTrie.serialize((OutputStream)null, true, this.dm);
      } catch (IOException var3) {
         Assert.assrt(false);
      }

      return size;
   }

   void serializeTrie(OutputStream os) throws IOException {
      this.fTrie.serialize(os, true, this.dm);
   }

   void addValToSets(List sets, int val) {
      Iterator var3 = sets.iterator();

      while(var3.hasNext()) {
         RBBINode usetNode = (RBBINode)var3.next();
         this.addValToSet(usetNode, val);
      }

   }

   void addValToSet(RBBINode usetNode, int val) {
      RBBINode leafNode = new RBBINode(3);
      leafNode.fVal = val;
      if (usetNode.fLeftChild == null) {
         usetNode.fLeftChild = leafNode;
         leafNode.fParent = usetNode;
      } else {
         RBBINode orNode = new RBBINode(9);
         orNode.fLeftChild = usetNode.fLeftChild;
         orNode.fRightChild = leafNode;
         orNode.fLeftChild.fParent = orNode;
         orNode.fRightChild.fParent = orNode;
         usetNode.fLeftChild = orNode;
         orNode.fParent = usetNode;
      }

   }

   int getNumCharCategories() {
      return this.fGroupCount + 3;
   }

   boolean sawBOF() {
      return this.fSawBOF;
   }

   int getFirstChar(int category) {
      int retVal = -1;

      for(RangeDescriptor rlRange = this.fRangeList; rlRange != null; rlRange = rlRange.fNext) {
         if (rlRange.fNum == category) {
            retVal = rlRange.fStartChar;
            break;
         }
      }

      return retVal;
   }

   void printRanges() {
      System.out.print("\n\n Nonoverlapping Ranges ...\n");

      for(RangeDescriptor rlRange = this.fRangeList; rlRange != null; rlRange = rlRange.fNext) {
         System.out.print(" " + rlRange.fNum + "   " + rlRange.fStartChar + "-" + rlRange.fEndChar);

         for(int i = 0; i < rlRange.fIncludesSets.size(); ++i) {
            RBBINode usetNode = (RBBINode)rlRange.fIncludesSets.get(i);
            String setName = "anon";
            RBBINode setRef = usetNode.fParent;
            if (setRef != null) {
               RBBINode varRef = setRef.fParent;
               if (varRef != null && varRef.fType == 2) {
                  setName = varRef.fText;
               }
            }

            System.out.print(setName);
            System.out.print("  ");
         }

         System.out.println("");
      }

   }

   void printRangeGroups() {
      int lastPrintedGroupNum = 0;
      System.out.print("\nRanges grouped by Unicode Set Membership...\n");

      for(RangeDescriptor rlRange = this.fRangeList; rlRange != null; rlRange = rlRange.fNext) {
         int groupNum = rlRange.fNum & '뿿';
         if (groupNum > lastPrintedGroupNum) {
            lastPrintedGroupNum = groupNum;
            if (groupNum < 10) {
               System.out.print(" ");
            }

            System.out.print(groupNum + " ");
            if ((rlRange.fNum & 16384) != 0) {
               System.out.print(" <DICT> ");
            }

            int i;
            for(i = 0; i < rlRange.fIncludesSets.size(); ++i) {
               RBBINode usetNode = (RBBINode)rlRange.fIncludesSets.get(i);
               String setName = "anon";
               RBBINode setRef = usetNode.fParent;
               if (setRef != null) {
                  RBBINode varRef = setRef.fParent;
                  if (varRef != null && varRef.fType == 2) {
                     setName = varRef.fText;
                  }
               }

               System.out.print(setName);
               System.out.print(" ");
            }

            i = 0;

            for(RangeDescriptor tRange = rlRange; tRange != null; tRange = tRange.fNext) {
               if (tRange.fNum == rlRange.fNum) {
                  if (i++ % 5 == 0) {
                     System.out.print("\n    ");
                  }

                  RBBINode.printHex(tRange.fStartChar, -1);
                  System.out.print("-");
                  RBBINode.printHex(tRange.fEndChar, 0);
               }
            }

            System.out.print("\n");
         }
      }

      System.out.print("\n");
   }

   void printSets() {
      System.out.print("\n\nUnicode Sets List\n------------------\n");

      for(int i = 0; i < this.fRB.fUSetNodes.size(); ++i) {
         RBBINode usetNode = (RBBINode)this.fRB.fUSetNodes.get(i);
         RBBINode.printInt(2, i);
         String setName = "anonymous";
         RBBINode setRef = usetNode.fParent;
         if (setRef != null) {
            RBBINode varRef = setRef.fParent;
            if (varRef != null && varRef.fType == 2) {
               setName = varRef.fText;
            }
         }

         System.out.print("  " + setName);
         System.out.print("   ");
         System.out.print(usetNode.fText);
         System.out.print("\n");
         if (usetNode.fLeftChild != null) {
            usetNode.fLeftChild.printTree(true);
         }
      }

      System.out.print("\n");
   }

   class RBBIDataManipulate implements TrieBuilder.DataManipulate {
      public int getFoldedValue(int start, int offset) {
         boolean[] inBlockZero = new boolean[1];
         int limit = start + 1024;

         while(start < limit) {
            int value = RBBISetBuilder.this.fTrie.getValue(start, inBlockZero);
            if (inBlockZero[0]) {
               start += 32;
            } else {
               if (value != 0) {
                  return offset | '耀';
               }

               ++start;
            }
         }

         return 0;
      }
   }

   static class RangeDescriptor {
      int fStartChar;
      int fEndChar;
      int fNum;
      List fIncludesSets;
      RangeDescriptor fNext;

      RangeDescriptor() {
         this.fIncludesSets = new ArrayList();
      }

      RangeDescriptor(RangeDescriptor other) {
         this.fStartChar = other.fStartChar;
         this.fEndChar = other.fEndChar;
         this.fNum = other.fNum;
         this.fIncludesSets = new ArrayList(other.fIncludesSets);
      }

      void split(int where) {
         Assert.assrt(where > this.fStartChar && where <= this.fEndChar);
         RangeDescriptor nr = new RangeDescriptor(this);
         nr.fStartChar = where;
         this.fEndChar = where - 1;
         nr.fNext = this.fNext;
         this.fNext = nr;
      }

      void setDictionaryFlag() {
         for(int i = 0; i < this.fIncludesSets.size(); ++i) {
            RBBINode usetNode = (RBBINode)this.fIncludesSets.get(i);
            String setName = "";
            RBBINode setRef = usetNode.fParent;
            if (setRef != null) {
               RBBINode varRef = setRef.fParent;
               if (varRef != null && varRef.fType == 2) {
                  setName = varRef.fText;
               }
            }

            if (setName.equals("dictionary")) {
               this.fNum |= 16384;
               break;
            }
         }

      }
   }
}
