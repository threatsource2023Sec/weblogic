package org.mozilla.javascript.optimizer;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import org.mozilla.javascript.FunctionNode;
import org.mozilla.javascript.IRFactory;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.PreorderNodeIterator;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.VariableTable;

public class Optimizer {
   static final boolean DEBUG_OPTIMIZER = false;
   static final boolean DO_CONSTANT_FOLDING = true;
   static int blockCount = 0;
   boolean inDirectCallFunction;
   boolean parameterUsedInNumberContext;
   int itsOptLevel;
   // $FF: synthetic field
   static Class class$java$lang$Double;
   // $FF: synthetic field
   static Class class$java$lang$Object;

   private Node[] buildStatementList(FunctionNode var1) {
      Vector var2 = new Vector();
      StmtNodeIterator var3 = new StmtNodeIterator(var1);

      for(Node var4 = var3.nextNode(); var4 != null; var4 = var3.nextNode()) {
         var2.addElement(var4);
      }

      Node[] var5 = new Node[var2.size()];

      for(int var6 = 0; var6 < var2.size(); ++var6) {
         var5[var6] = (Node)var2.elementAt(var6);
      }

      return var5;
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   boolean convertParameter(Node var1) {
      if (this.inDirectCallFunction && var1.getType() == 72) {
         OptLocalVariable var2 = (OptLocalVariable)var1.getProp(24);
         if (var2 != null && var2.isParameter()) {
            var1.putProp(26, (Object)null);
            return true;
         }
      }

      return false;
   }

   void doBlockLocalCSE(Block[] var1, Block var2, Hashtable var3, boolean[] var4, OptFunctionNode var5) {
      if (!var4[var2.getBlockID()]) {
         var4[var2.getBlockID()] = true;
         var3 = var2.localCSE(var3, var5);
         Block[] var6 = var1[var2.getBlockID()].getSuccessorList();
         if (var6 != null) {
            for(int var7 = 0; var7 < var6.length; ++var7) {
               int var8 = var6[var7].getBlockID();
               Block[] var9 = var1[var8].getPredecessorList();
               if (var9.length == 1) {
                  this.doBlockLocalCSE(var1, var6[var7], (Hashtable)var3.clone(), var4, var5);
               }
            }
         }
      }

   }

   void findSinglyTypedVars(VariableTable var1, Block[] var2) {
      for(int var3 = 0; var3 < var1.size(); ++var3) {
         OptLocalVariable var4 = (OptLocalVariable)var1.getVariable(var3);
         if (!var4.isParameter()) {
            int var5 = var4.getTypeUnion();
            if (var5 == 1) {
               var4.setIsNumber();
            }
         }
      }

   }

   void foldConstants(Node var1, Node var2) {
      Node var4 = null;
      Node var3 = var1.getFirstChild();
      if (var3 != null) {
         var4 = var3.getNextSibling();
         if (var4 == null) {
            this.foldConstants(var3, var1);
         } else {
            this.foldConstants(var3, var1);
            this.foldConstants(var4, var1);

            for(Node var5 = var4.getNextSibling(); var5 != null; var5 = var5.getNextSibling()) {
               this.foldConstants(var5, var1);
            }

            var3 = var1.getFirstChild();
            if (var3 != null) {
               var4 = var3.getNextSibling();
               if (var4 != null) {
                  int var6 = var3.getType();
                  int var7 = var4.getType();
                  double var12;
                  long var15;
                  switch (var1.getType()) {
                     case 23:
                        if (var6 == 45 && var7 == 45) {
                           if (!(var3.getDatum() instanceof Double) && !(var4.getDatum() instanceof Double)) {
                              var15 = var3.getLong() + var4.getLong();
                              var2.replaceChild(var1, new Node(45, toSmallestType(var15)));
                           } else {
                              var2.replaceChild(var1, new Node(45, new Double(var3.getDouble() + var4.getDouble())));
                           }
                        } else if (var6 == 46 && var7 == 46) {
                           var2.replaceChild(var1, new Node(46, var3.getString() + var4.getString()));
                        } else if (var6 == 46 && var7 == 45) {
                           var2.replaceChild(var1, new Node(46, var3.getString() + ScriptRuntime.numberToString(var4.getDouble(), 10)));
                        } else if (var6 == 45 && var7 == 46) {
                           var2.replaceChild(var1, new Node(46, ScriptRuntime.numberToString(var3.getDouble(), 10) + var4.getString()));
                        }
                        break;
                     case 24:
                        if (var6 == 45 && var7 == 45) {
                           if (!(var3.getDatum() instanceof Double) && !(var4.getDatum() instanceof Double)) {
                              var15 = var3.getLong() - var4.getLong();
                              var2.replaceChild(var1, new Node(45, toSmallestType(var15)));
                           } else {
                              var2.replaceChild(var1, new Node(45, new Double(var3.getDouble() - var4.getDouble())));
                           }
                        } else if (var6 == 45 && var3.getDouble() == 0.0) {
                           var2.replaceChild(var1, new Node(105, var4, new Integer(24)));
                        } else if (var7 == 45 && var4.getDouble() == 0.0) {
                           var2.replaceChild(var1, var3);
                        }
                        break;
                     case 25:
                        if (var6 == 45 && var7 == 45) {
                           if (!(var3.getDatum() instanceof Double) && !(var4.getDatum() instanceof Double)) {
                              var15 = var3.getLong() * var4.getLong();
                              var2.replaceChild(var1, new Node(45, toSmallestType(var15)));
                           } else {
                              var2.replaceChild(var1, new Node(45, new Double(var3.getDouble() * var4.getDouble())));
                           }
                        } else if (var6 == 45) {
                           var12 = ((Number)var3.getDatum()).doubleValue();
                           if (var12 == 1.0) {
                              var2.replaceChild(var1, var4);
                           }
                        } else if (var7 == 45) {
                           var12 = ((Number)var4.getDatum()).doubleValue();
                           if (var12 == 1.0) {
                              var2.replaceChild(var1, var3);
                           }
                        }
                        break;
                     case 26:
                        if (var6 == 45 && var7 == 45) {
                           if (!(var3.getDatum() instanceof Double) && !(var4.getDatum() instanceof Double)) {
                              int var13 = var4.getInt();
                              if (var13 == 0) {
                                 return;
                              }

                              long var14 = var3.getLong() / var4.getLong();
                              var2.replaceChild(var1, new Node(45, toSmallestType(var14)));
                           } else {
                              var12 = var4.getDouble();
                              if (var12 == 0.0) {
                                 return;
                              }

                              var2.replaceChild(var1, new Node(45, new Double(var3.getDouble() / var12)));
                           }
                        } else if (var7 == 45 && var4.getDouble() == 1.0) {
                           var2.replaceChild(var1, var3);
                        }
                        break;
                     case 100:
                        if (var6 == 109 && var3.getInt() == 49 || var6 == 109 && var3.getInt() == 74 || var6 == 109 && var3.getInt() == 51 || var6 == 45 && ((Number)var3.getDatum()).doubleValue() == 0.0) {
                           var2.replaceChild(var1, var4);
                        } else if (var7 == 109 && var4.getInt() == 49 || var7 == 109 && var4.getInt() == 74 || var7 == 109 && var4.getInt() == 51 || var7 == 45 && ((Number)var4.getDatum()).doubleValue() == 0.0) {
                           var2.replaceChild(var1, var3);
                        } else if ((var6 == 109 && (Integer)var3.getDatum() == 52 || var6 == 45 && ((Number)var3.getDatum()).doubleValue() != 0.0) && !IRFactory.hasSideEffects(var4)) {
                           var2.replaceChild(var1, new Node(109, new Integer(52)));
                        } else if ((var7 == 109 && (Integer)var4.getDatum() == 52 || var7 == 45 && ((Number)var4.getDatum()).doubleValue() != 0.0) && !IRFactory.hasSideEffects(var3)) {
                           var2.replaceChild(var1, new Node(109, new Integer(52)));
                        }
                        break;
                     case 101:
                        if ((var6 == 109 && var3.getInt() == 49 || var6 == 109 && var3.getInt() == 74) && !IRFactory.hasSideEffects(var4)) {
                           var2.replaceChild(var1, new Node(109, new Integer(51)));
                        } else if ((var7 == 109 && var4.getInt() == 49 || var7 == 109 && var4.getInt() == 74) && !IRFactory.hasSideEffects(var3)) {
                           var2.replaceChild(var1, new Node(109, new Integer(51)));
                        } else if ((var6 != 109 || (Integer)var3.getDatum() != 52) && (var6 != 45 || ((Number)var3.getDatum()).doubleValue() == 0.0)) {
                           if (var7 == 109 && (Integer)var4.getDatum() == 52 || var7 == 45 && ((Number)var4.getDatum()).doubleValue() != 0.0) {
                              var2.replaceChild(var1, var3);
                           }
                        } else {
                           var2.replaceChild(var1, var4);
                        }
                        break;
                     case 133:
                        if (var3.getType() == 8) {
                           Node var8 = var3.getFirstChild();
                           if (var8.getType() == 109) {
                              int var9 = var8.getInt();
                              if (var9 != 51 && var9 != 49 && var9 != 74) {
                                 if ((var8.getType() == 109 && var8.getInt() == 52 || var8.getType() == 45 && ((Number)var8.getDatum()).doubleValue() != 0.0) && var4.getType() == 133) {
                                    var2.replaceChild(var1, var4.getFirstChild());
                                 }
                              } else {
                                 Node var10 = null;

                                 try {
                                    var10 = var4.getNextSibling().getNextSibling().getNextSibling().getFirstChild();
                                 } catch (Exception var11) {
                                    return;
                                 }

                                 if (var10 != null) {
                                    var2.replaceChild(var1, var10);
                                 }
                              }
                           }
                        }
                  }

               }
            }
         }
      }
   }

   void localCSE(Block[] var1, OptFunctionNode var2) {
      boolean[] var3 = new boolean[var1.length];
      this.doBlockLocalCSE(var1, var1[0], (Hashtable)null, var3, var2);

      for(int var4 = 0; var4 < var1.length; ++var4) {
         if (!var3[var4]) {
            var1[var4].localCSE((Hashtable)null, var2);
         }
      }

   }

   void markDCPNumberContext(Node var1) {
      if (this.inDirectCallFunction && var1.getType() == 72) {
         OptLocalVariable var2 = (OptLocalVariable)var1.getProp(24);
         if (var2 != null && var2.isParameter()) {
            this.parameterUsedInNumberContext = true;
         }
      }

   }

   public void optimize(Node var1, int var2) {
      this.itsOptLevel = var2;
      PreorderNodeIterator var3 = var1.getPreorderIterator();

      Node var4;
      while((var4 = var3.nextNode()) != null) {
         if (var4.getType() == 110) {
            OptFunctionNode var5 = (OptFunctionNode)var4.getProp(5);
            if (var5 != null) {
               this.optimizeFunction(var5);
            }
         }
      }

   }

   void optimizeFunction(OptFunctionNode var1) {
      if (!var1.requiresActivation()) {
         this.inDirectCallFunction = var1.isTargetOfDirectCall();
         Node[] var2 = this.buildStatementList(var1);
         Block[] var3 = Block.buildBlocks(var2);
         Object var4 = null;

         try {
            OptVariableTable var5 = (OptVariableTable)var1.getVariableTable();
            if (var5 != null) {
               var5.establishIndices();

               for(int var6 = 0; var6 < var2.length; ++var6) {
                  this.replaceVariableAccess(var2[var6], var5);
               }

               this.foldConstants(var1, (Node)null);
               this.reachingDefDataFlow(var5, var3);
               this.typeFlow(var5, var3);
               this.findSinglyTypedVars(var5, var3);
               this.localCSE(var3, var1);
               if (!var1.requiresActivation()) {
                  this.parameterUsedInNumberContext = false;

                  for(int var7 = 0; var7 < var2.length; ++var7) {
                     this.rewriteForNumberVariables(var2[var7]);
                  }

                  var1.setParameterNumberContext(this.parameterUsedInNumberContext);
               }
            }
         } catch (IOException var8) {
         }

      }
   }

   void reachingDefDataFlow(VariableTable var1, Block[] var2) {
      for(int var3 = 0; var3 < var2.length; ++var3) {
         var2[var3].initLiveOnEntrySets(var1);
      }

      boolean[] var4 = new boolean[var2.length];
      boolean[] var5 = new boolean[var2.length];
      int var6 = var2.length - 1;
      boolean var7 = false;
      var4[var6] = true;

      while(true) {
         if (var4[var6] || !var5[var6]) {
            var5[var6] = true;
            var4[var6] = false;
            if (var2[var6].doReachedUseDataFlow()) {
               Block[] var8 = var2[var6].getPredecessorList();
               if (var8 != null) {
                  for(int var9 = 0; var9 < var8.length; ++var9) {
                     int var10 = var8[var9].getBlockID();
                     var4[var10] = true;
                     var7 |= var10 > var6;
                  }
               }
            }
         }

         if (var6 == 0) {
            if (!var7) {
               for(int var11 = 0; var11 < var2.length; ++var11) {
                  var2[var11].markVolatileVariables(var1);
               }

               var2[0].markAnyTypeVariables(var1);
               return;
            }

            var6 = var2.length - 1;
            var7 = false;
         } else {
            --var6;
         }
      }
   }

   void replaceVariableAccess(Node var1, VariableTable var2) {
      for(Node var3 = var1.getFirstChild(); var3 != null; var3 = var3.getNextSibling()) {
         this.replaceVariableAccess(var3, var2);
      }

      String var4;
      OptLocalVariable var5;
      switch (var1.getType()) {
         case 72:
            var4 = var1.getString();
            var5 = (OptLocalVariable)var2.getVariable(var4);
            if (var5 != null) {
               var1.putProp(24, var5);
            }
            break;
         case 73:
            var4 = var1.getFirstChild().getString();
            var5 = (OptLocalVariable)var2.getVariable(var4);
            if (var5 != null) {
               var1.putProp(24, var5);
            }
      }

   }

   int rewriteForNumberVariables(Node var1) {
      Node var2;
      int var4;
      int var5;
      Node var6;
      Node var10;
      Node var15;
      switch (var1.getType()) {
         case 11:
         case 12:
         case 13:
         case 20:
         case 21:
         case 24:
         case 25:
         case 26:
         case 27:
            var2 = var1.getFirstChild();
            var10 = var2.getNextSibling();
            var4 = this.rewriteForNumberVariables(var2);
            var5 = this.rewriteForNumberVariables(var10);
            this.markDCPNumberContext(var2);
            this.markDCPNumberContext(var10);
            if (var4 == 1) {
               if (var5 == 1) {
                  var1.putProp(26, new Integer(0));
                  return 1;
               }

               if (!this.convertParameter(var10)) {
                  var1.removeChild(var10);
                  var6 = new Node(142, var10);
                  var6.putProp(18, class$java$lang$Double != null ? class$java$lang$Double : (class$java$lang$Double = class$("java.lang.Double")));
                  var1.addChildToBack(var6);
                  var1.putProp(26, new Integer(0));
               }

               return 1;
            } else {
               if (var5 == 1) {
                  if (!this.convertParameter(var2)) {
                     var1.removeChild(var2);
                     var6 = new Node(142, var2);
                     var6.putProp(18, class$java$lang$Double != null ? class$java$lang$Double : (class$java$lang$Double = class$("java.lang.Double")));
                     var1.addChildToFront(var6);
                     var1.putProp(26, new Integer(0));
                  }

                  return 1;
               }

               if (!this.convertParameter(var2)) {
                  var1.removeChild(var2);
                  var6 = new Node(142, var2);
                  var6.putProp(18, class$java$lang$Double != null ? class$java$lang$Double : (class$java$lang$Double = class$("java.lang.Double")));
                  var1.addChildToFront(var6);
               }

               if (!this.convertParameter(var10)) {
                  var1.removeChild(var10);
                  var6 = new Node(142, var10);
                  var6.putProp(18, class$java$lang$Double != null ? class$java$lang$Double : (class$java$lang$Double = class$("java.lang.Double")));
                  var1.addChildToBack(var6);
               }

               var1.putProp(26, new Integer(0));
               return 1;
            }
         case 23:
            var2 = var1.getFirstChild();
            var10 = var2.getNextSibling();
            var4 = this.rewriteForNumberVariables(var2);
            var5 = this.rewriteForNumberVariables(var10);
            if (this.convertParameter(var2)) {
               if (this.convertParameter(var10)) {
                  return 0;
               }

               if (var5 == 1) {
                  var1.putProp(26, new Integer(2));
               }
            } else if (this.convertParameter(var10)) {
               if (var4 == 1) {
                  var1.putProp(26, new Integer(1));
               }
            } else if (var4 == 1) {
               if (var5 == 1) {
                  var1.putProp(26, new Integer(0));
                  return 1;
               }

               var1.putProp(26, new Integer(1));
            } else if (var5 == 1) {
               var1.putProp(26, new Integer(2));
            }

            return 0;
         case 41:
            var2 = var1.getFirstChild();
            var10 = var2.getNextSibling();
            var4 = this.rewriteForNumberVariables(var2);
            if (var4 == 1 && !this.convertParameter(var2)) {
               var1.removeChild(var2);
               var15 = new Node(142, var2);
               var15.putProp(18, class$java$lang$Object != null ? class$java$lang$Object : (class$java$lang$Object = class$("java.lang.Object")));
               var1.addChildToFront(var15);
            }

            var5 = this.rewriteForNumberVariables(var10);
            if (var5 == 1) {
               var1.putProp(26, new Integer(2));
               this.markDCPNumberContext(var10);
            }

            return 0;
         case 42:
            var2 = var1.getFirstChild();
            var10 = var2.getNextSibling();
            Node var13 = var10.getNextSibling();
            var5 = this.rewriteForNumberVariables(var2);
            if (var5 == 1 && !this.convertParameter(var2)) {
               var1.removeChild(var2);
               var6 = new Node(142, var2);
               var6.putProp(18, class$java$lang$Object != null ? class$java$lang$Object : (class$java$lang$Object = class$("java.lang.Object")));
               var1.addChildToFront(var6);
            }

            int var16 = this.rewriteForNumberVariables(var10);
            if (var16 == 1) {
               var1.putProp(26, new Integer(1));
               this.markDCPNumberContext(var10);
            }

            int var7 = this.rewriteForNumberVariables(var13);
            if (var7 == 1 && !this.convertParameter(var13)) {
               var1.removeChild(var13);
               Node var8 = new Node(142, var13);
               var8.putProp(18, class$java$lang$Object != null ? class$java$lang$Object : (class$java$lang$Object = class$("java.lang.Object")));
               var1.addChildToBack(var8);
            }

            return 0;
         case 43:
            FunctionNode var11 = (FunctionNode)var1.getProp(27);
            if (var11 != null) {
               var10 = var1.getFirstChild();
               this.rewriteForNumberVariables(var10);
               var10 = var10.getNextSibling();
               this.rewriteForNumberVariables(var10);

               for(var10 = var10.getNextSibling(); var10 != null; var10 = var10.getNextSibling()) {
                  var4 = this.rewriteForNumberVariables(var10);
                  if (var4 == 1) {
                     this.markDCPNumberContext(var10);
                  }
               }

               return 0;
            }
         default:
            for(var2 = var1.getFirstChild(); var2 != null; var2 = var10) {
               var10 = var2.getNextSibling();
               var4 = this.rewriteForNumberVariables(var2);
               if (var4 == 1 && !this.convertParameter(var2)) {
                  var1.removeChild(var2);
                  var15 = new Node(142, var2);
                  var15.putProp(18, class$java$lang$Object != null ? class$java$lang$Object : (class$java$lang$Object = class$("java.lang.Object")));
                  if (var10 == null) {
                     var1.addChildToBack(var15);
                  } else {
                     var1.addChildBefore(var15, var10);
                  }
               }
            }

            return 0;
         case 45:
            var1.putProp(26, new Integer(0));
            return 1;
         case 57:
            var2 = var1.getFirstChild();
            int var12 = this.rewriteForNumberVariables(var2);
            if (var12 == 1) {
               var1.putProp(26, new Integer(0));
            }

            return 0;
         case 72:
            OptLocalVariable var9 = (OptLocalVariable)var1.getProp(24);
            if (var9 != null) {
               if (this.inDirectCallFunction && var9.isParameter()) {
                  var1.putProp(26, new Integer(0));
                  return 1;
               }

               if (var9.isNumber()) {
                  var1.putProp(26, new Integer(0));
                  return 1;
               }
            }

            return 0;
         case 73:
            var2 = var1.getFirstChild();
            var10 = var2.getNextSibling();
            var4 = this.rewriteForNumberVariables(var10);
            OptLocalVariable var14 = (OptLocalVariable)var1.getProp(24);
            if (this.inDirectCallFunction && var14.isParameter()) {
               if (var4 == 1) {
                  if (!this.convertParameter(var10)) {
                     var1.putProp(26, new Integer(0));
                     return 1;
                  }

                  this.markDCPNumberContext(var10);
                  return 0;
               }

               return var4;
            } else {
               if (var14 != null && var14.isNumber()) {
                  if (var4 != 1) {
                     var1.removeChild(var10);
                     var6 = new Node(142, var10);
                     var6.putProp(18, class$java$lang$Double != null ? class$java$lang$Double : (class$java$lang$Double = class$("java.lang.Double")));
                     var1.addChildToBack(var6);
                  }

                  var1.putProp(26, new Integer(0));
                  this.markDCPNumberContext(var10);
                  return 1;
               }

               if (var4 == 1 && !this.convertParameter(var10)) {
                  var1.removeChild(var10);
                  var6 = new Node(142, var10);
                  var6.putProp(18, class$java$lang$Object != null ? class$java$lang$Object : (class$java$lang$Object = class$("java.lang.Object")));
                  var1.addChildToBack(var6);
               }

               return 0;
            }
         case 103:
            var2 = var1.getFirstChild();
            var10 = var2.getNextSibling();
            var4 = this.rewriteForNumberVariables(var2);
            var5 = this.rewriteForNumberVariables(var10);
            this.markDCPNumberContext(var2);
            this.markDCPNumberContext(var10);
            if (var1.getInt() != 64 && var1.getInt() != 63) {
               if (this.convertParameter(var2)) {
                  if (this.convertParameter(var10)) {
                     return 0;
                  }

                  if (var5 == 1) {
                     var1.putProp(26, new Integer(2));
                  }
               } else if (this.convertParameter(var10)) {
                  if (var4 == 1) {
                     var1.putProp(26, new Integer(1));
                  }
               } else if (var4 == 1) {
                  if (var5 == 1) {
                     var1.putProp(26, new Integer(0));
                  } else {
                     var1.putProp(26, new Integer(1));
                  }
               } else if (var5 == 1) {
                  var1.putProp(26, new Integer(2));
               }
            } else {
               if (var4 == 1 && !this.convertParameter(var2)) {
                  var1.removeChild(var2);
                  var6 = new Node(142, var2);
                  var6.putProp(18, class$java$lang$Object != null ? class$java$lang$Object : (class$java$lang$Object = class$("java.lang.Object")));
                  var1.addChildToFront(var6);
               }

               if (var5 == 1 && !this.convertParameter(var10)) {
                  var1.removeChild(var10);
                  var6 = new Node(142, var10);
                  var6.putProp(18, class$java$lang$Object != null ? class$java$lang$Object : (class$java$lang$Object = class$("java.lang.Object")));
                  var1.addChildToBack(var6);
               }
            }

            return 0;
         case 106:
         case 107:
            var2 = var1.getFirstChild();
            if (var2.getType() == 72) {
               OptLocalVariable var3 = (OptLocalVariable)var2.getProp(24);
               if (var3 != null && var3.isNumber()) {
                  var1.putProp(26, new Integer(0));
                  this.markDCPNumberContext(var2);
                  return 1;
               } else {
                  return 0;
               }
            } else {
               return 0;
            }
      }
   }

   private static Number toSmallestType(long var0) {
      if (var0 >= -128L && var0 <= 127L) {
         return new Byte((byte)((int)var0));
      } else if (var0 >= -32768L && var0 <= 32767L) {
         return new Short((short)((int)var0));
      } else {
         return (Number)(var0 >= -2147483648L && var0 <= 2147483647L ? new Integer((int)var0) : new Double((double)var0));
      }
   }

   void typeFlow(VariableTable var1, Block[] var2) {
      boolean[] var3 = new boolean[var2.length];
      boolean[] var4 = new boolean[var2.length];
      int var5 = 0;
      boolean var6 = false;
      var3[var5] = true;

      while(true) {
         if (var3[var5] || !var4[var5]) {
            var4[var5] = true;
            var3[var5] = false;
            if (var2[var5].doTypeFlow()) {
               Block[] var7 = var2[var5].getSuccessorList();
               if (var7 != null) {
                  for(int var8 = 0; var8 < var7.length; ++var8) {
                     int var9 = var7[var8].getBlockID();
                     var3[var9] = true;
                     var6 |= var9 < var5;
                  }
               }
            }
         }

         if (var5 == var2.length - 1) {
            if (!var6) {
               return;
            }

            var5 = 0;
            var6 = false;
         } else {
            ++var5;
         }
      }
   }
}
