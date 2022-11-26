package org.mozilla.javascript;

import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

public class NodeTransformer {
   protected Stack loops;
   protected Stack loopEnds;
   protected boolean inFunction;
   protected IRFactory irFactory;

   protected void addParameters(FunctionNode var1) {
      VariableTable var2 = var1.getVariableTable();
      Node var3 = var1.getFirstChild();
      if (var3.getType() == 94 && var2.getParameterCount() == 0) {
         ShallowNodeIterator var4 = var3.getChildIterator();

         while(var4.hasMoreElements()) {
            Node var5 = var4.nextNode();
            String var6 = var5.getString();
            var2.addParameter(var6);
         }
      }

   }

   protected void addVariables(Node var1, VariableTable var2) {
      boolean var3 = var1.getType() == 110;
      PreorderNodeIterator var4 = var1.getPreorderIterator();
      Hashtable var5 = null;

      label70:
      while(true) {
         Node var6;
         int var7;
         Node var9;
         label57:
         do {
            String var8;
            do {
               if ((var6 = var4.nextNode()) == null) {
                  String var10 = (String)var1.getDatum();
                  if (var3 && ((FunctionNode)var1).getFunctionType() == 2 && var10 != null && var10.length() > 0 && var2.getVariable(var10) == null) {
                     var2.addLocal(var10);
                     Node var12 = var1.getLastChild();
                     var9 = new Node(57, new Node(73, new Node(46, var10), new Node(109, new Integer(87))));
                     var12.addChildrenToFront(var9);
                  }

                  return;
               }

               var7 = var6.getType();
               if (!var3 || var7 != 110 || var6 == var1 || ((FunctionNode)var6.getProp(5)).getFunctionType() != 3) {
                  continue label57;
               }

               var8 = var6.getString();
            } while(var8 == null);

            var2.removeLocal(var8);
            if (var5 == null) {
               var5 = new Hashtable();
            }

            var5.put(var8, Boolean.TRUE);
         } while(var7 != 123);

         ShallowNodeIterator var11 = var6.getChildIterator();

         while(true) {
            do {
               if (!var11.hasMoreElements()) {
                  continue label70;
               }

               var9 = var11.nextNode();
            } while(var5 != null && var5.get(var9.getString()) != null);

            var2.addLocal(var9.getString());
         }
      }
   }

   public IRFactory createIRFactory(TokenStream var1, Scriptable var2) {
      return new IRFactory(var1, var2);
   }

   protected VariableTable createVariableTable() {
      return new VariableTable();
   }

   protected VariableTable getVariableTable(Node var1) {
      if (this.inFunction) {
         return ((FunctionNode)var1).getVariableTable();
      } else {
         VariableTable var2 = (VariableTable)var1.getProp(10);
         if (var2 == null) {
            var2 = this.createVariableTable();
            var1.putProp(10, var2);
         }

         return var2;
      }
   }

   protected boolean inWithStatement() {
      for(int var1 = this.loops.size() - 1; var1 >= 0; --var1) {
         Node var2 = (Node)this.loops.elementAt(var1);
         if (var2.getType() == 124) {
            return true;
         }
      }

      return false;
   }

   private boolean isSpecialCallName(Node var1, Node var2) {
      Node var3 = var2.getFirstChild();
      boolean var4 = false;
      String var5;
      if (var3.getType() == 44) {
         var5 = var3.getString();
         var4 = var5.equals("eval") || var5.equals("With");
      } else if (var3.getType() == 39) {
         var5 = var3.getLastChild().getString();
         var4 = var5.equals("exec");
      }

      if (var4) {
         if (this.inFunction) {
            ((FunctionNode)var1).setRequiresActivation(true);
         }

         return true;
      } else {
         return false;
      }
   }

   public NodeTransformer newInstance() {
      return new NodeTransformer();
   }

   protected void reportMessage(Context var1, String var2, Node var3, Node var4, boolean var5, Scriptable var6) {
      Object var7 = var3.getDatum();
      int var8 = 0;
      if (var7 != null && var7 instanceof Integer) {
         var8 = (Integer)var7;
      }

      Object var9 = var4 == null ? null : var4.getProp(16);
      if (var5) {
         if (var6 != null) {
            throw NativeGlobal.constructError(var1, "SyntaxError", var2, var6, (String)var9, var8, 0, (String)null);
         }

         Context.reportError(var2, (String)var9, var8, (String)null, 0);
      } else {
         Context.reportWarning(var2, (String)var9, var8, (String)null, 0);
      }

   }

   public Node transform(Node var1, Node var2, TokenStream var3, Scriptable var4) {
      this.loops = new Stack();
      this.loopEnds = new Stack();
      this.inFunction = var1.getType() == 110;
      if (!this.inFunction) {
         this.addVariables(var1, this.getVariableTable(var1));
      }

      this.irFactory = this.createIRFactory(var3, var4);
      boolean var5 = false;
      PreorderNodeIterator var6 = var1.getPreorderIterator();

      while(true) {
         Node var7;
         label303:
         while((var7 = var6.nextNode()) != null) {
            int var8 = var7.getType();
            String var10;
            Node var12;
            Node var18;
            Node var20;
            Node var23;
            int var30;
            Node var31;
            Node var35;
            Context var40;
            switch (var8) {
               case 4:
               case 137:
                  if (!this.loopEnds.empty() && this.loopEnds.peek() == var7) {
                     this.loopEnds.pop();
                     this.loops.pop();
                  }
                  break;
               case 5:
                  if (!var5) {
                     break;
                  }

                  var18 = var6.getCurrentParent();
                  int var33 = this.loops.size() - 1;

                  while(true) {
                     if (var33 < 0) {
                        continue label303;
                     }

                     var23 = (Node)this.loops.elementAt(var33);
                     var30 = var23.getType();
                     if (var30 == 75) {
                        var31 = new Node(143);
                        Object var42 = var23.getProp(21);
                        var31.putProp(1, var42);
                        var18.addChildBefore(var31, var7);
                     } else if (var30 == 124) {
                        var18.addChildBefore(new Node(4), var7);
                     }

                     --var33;
                  }
               case 10:
               case 31:
                  if (this.inFunction && !this.inWithStatement()) {
                     var18 = var7.getFirstChild();
                     if (var18 != null && var18.getType() == 61) {
                        var10 = var18.getString();
                        var40 = Context.getCurrentContext();
                        if (var40 != null && var40.isActivationNeeded(var10)) {
                           ((FunctionNode)var1).setRequiresActivation(true);
                        }

                        VariableTable var36 = this.getVariableTable(var1);
                        if (var36.getVariable(var10) != null) {
                           if (var8 == 10) {
                              var7.setType(73);
                              var18.setType(46);
                           } else {
                              var31 = new Node(109, new Integer(51));
                              var6.replaceCurrent(var31);
                           }
                        }
                     }
                  }
                  break;
               case 30:
                  if (this.isSpecialCallName(var1, var7)) {
                     var7.putProp(30, Boolean.TRUE);
                  }

                  this.visitNew(var7, var1);
                  break;
               case 39:
                  if (!this.inFunction) {
                     break;
                  }

                  var18 = var7.getFirstChild().getNextSibling();
                  var10 = var18 == null ? "" : var18.getString();
                  var40 = Context.getCurrentContext();
                  if (var40 != null && var40.isActivationNeeded(var10) || var10.equals("length") && Context.getContext().getLanguageVersion() == 120) {
                     ((FunctionNode)var1).setRequiresActivation(true);
                  }
                  break;
               case 43:
                  if (this.isSpecialCallName(var1, var7)) {
                     var7.putProp(30, Boolean.TRUE);
                  }

                  this.visitCall(var7, var1);
                  break;
               case 44:
                  if (this.inFunction && !this.inWithStatement()) {
                     String var37 = var7.getString();
                     Context var28 = Context.getCurrentContext();
                     if (var28 != null && var28.isActivationNeeded(var37)) {
                        ((FunctionNode)var1).setRequiresActivation(true);
                     }

                     VariableTable var38 = this.getVariableTable(var1);
                     if (var38.getVariable(var37) != null) {
                        var7.setType(72);
                     }
                  }
                  break;
               case 56:
                  Vector var32 = (Vector)var1.getProp(12);
                  if (var32 == null) {
                     var32 = new Vector(3);
                     var1.putProp(12, var32);
                  }

                  var32.addElement(var7);
                  var20 = new Node(56);
                  var6.replaceCurrent(var20);
                  var20.putProp(12, var7);
                  break;
               case 75:
                  var18 = (Node)var7.getProp(21);
                  if (var18 != null) {
                     var5 = true;
                     this.loops.push(var7);
                     this.loopEnds.push(var18);
                  }

                  Integer var26 = (Integer)var1.getProp(22);
                  if (var26 == null) {
                     var1.putProp(22, new Integer(1));
                  } else {
                     var1.putProp(22, new Integer(var26 + 1));
                  }
                  break;
               case 108:
                  var18 = var7.getLastChild();
                  var18.setType(46);
                  break;
               case 110:
                  if (var7 == var1) {
                     VariableTable var29 = this.getVariableTable(var1);
                     this.addVariables(var1, var29);
                     var20 = var7.getLastChild();
                     var23 = var20.getLastChild();
                     if (var23 == null || var23.getType() != 5) {
                        var20.addChildToBack(new Node(5));
                     }
                     break;
                  }

                  FunctionNode var27 = (FunctionNode)var7.getProp(5);
                  if (this.inFunction) {
                     ((FunctionNode)var1).setRequiresActivation(true);
                     var27.setCheckThis(true);
                  }

                  this.addParameters(var27);
                  NodeTransformer var24 = this.newInstance();
                  var27 = (FunctionNode)var24.transform(var27, var1, var3, var4);
                  var7.putProp(5, var27);
                  Vector var34 = (Vector)var1.getProp(5);
                  if (var34 == null) {
                     var34 = new Vector(7);
                     var1.putProp(5, var34);
                  }

                  var34.addElement(var27);
                  break;
               case 115:
                  var18 = new Node(137);
                  var20 = var6.getCurrentParent();
                  var20.addChildAfter(var18, var7);
                  var23 = var7;

                  for(var12 = var7.getFirstChild().next; var12 != null; var12 = var31) {
                     var31 = var12.next;
                     var7.removeChild(var12);
                     var20.addChildAfter(var12, var23);
                     var23 = var12;
                  }

                  var7.putProp(2, var18);
                  this.loops.push(var7);
                  this.loopEnds.push(var18);
                  var7.putProp(13, new Vector(13));
                  break;
               case 116:
               case 117:
                  var18 = (Node)this.loops.peek();
                  if (var8 == 116) {
                     Vector var22 = (Vector)var18.getProp(13);
                     var22.addElement(var7);
                  } else {
                     var18.putProp(14, var7);
                  }
                  break;
               case 121:
               case 122:
                  var18 = null;
                  boolean var21 = var7.hasChildren();
                  String var25 = null;
                  if (var21) {
                     var12 = var7.getFirstChild();
                     var25 = var12.getString();
                     var7.removeChild(var12);
                  }

                  var31 = var6.getCurrentParent();

                  for(var30 = this.loops.size() - 1; var30 >= 0; --var30) {
                     var35 = (Node)this.loops.elementAt(var30);
                     int var15 = var35.getType();
                     if (var15 == 124) {
                        var31.addChildBefore(new Node(4), var7);
                     } else if (var15 == 75) {
                        Node var16 = new Node(143);
                        Object var17 = var35.getProp(21);
                        var16.putProp(1, var17);
                        var31.addChildBefore(var16, var7);
                     } else {
                        if (!var21 && (var15 == 138 || var15 == 115 && var8 == 121)) {
                           var18 = var35;
                           break;
                        }

                        if (var21 && var15 == 136 && var25.equals((String)var35.getProp(20))) {
                           var18 = var35;
                           break;
                        }
                     }
                  }

                  int var41 = var8 == 121 ? 2 : 3;
                  Node var39 = var18 == null ? null : (Node)var18.getProp(var41);
                  if (var18 != null && var39 != null) {
                     var7.setType(6);
                     var7.putProp(1, var39);
                     break;
                  }

                  String var43;
                  if (!var21) {
                     if (var8 == 122) {
                        var43 = Context.getMessage("msg.continue.outside", (Object[])null);
                     } else {
                        var43 = Context.getMessage("msg.bad.break", (Object[])null);
                     }
                  } else if (var18 != null) {
                     var43 = Context.getMessage0("msg.continue.nonloop");
                  } else {
                     Object[] var44 = new Object[]{var25};
                     var43 = Context.getMessage("msg.undef.label", var44);
                  }

                  this.reportMessage(Context.getContext(), var43, var7, var1, true, var4);
                  var7.setType(128);
                  break;
               case 123:
                  ShallowNodeIterator var19 = var7.getChildIterator();
                  var20 = new Node(133);

                  while(var19.hasMoreElements()) {
                     var23 = var19.nextNode();
                     if (var23.hasChildren()) {
                        var12 = var23.getFirstChild();
                        var23.removeChild(var12);
                        var31 = (Node)this.irFactory.createAssignment(128, var23, var12, (Class)null, false);
                        var35 = new Node(57, var31, var7.getDatum());
                        var20.addChildToBack(var35);
                     }
                  }

                  var6.replaceCurrent(var20);
                  break;
               case 124:
                  if (this.inFunction) {
                     ((FunctionNode)var1).setRequiresActivation(true);
                  }

                  this.loops.push(var7);
                  var18 = var7.getNextSibling();
                  if (var18.getType() != 4) {
                     throw new RuntimeException("Unexpected tree");
                  }

                  this.loopEnds.push(var18);
                  break;
               case 136:
                  var18 = var7.getFirstChild();
                  var7.removeChild(var18);
                  var10 = var18.getString();

                  for(int var11 = this.loops.size() - 1; var11 >= 0; --var11) {
                     var12 = (Node)this.loops.elementAt(var11);
                     if (var12.getType() == 136) {
                        String var13 = (String)var12.getProp(20);
                        if (var10.equals(var13)) {
                           String var14 = Context.getMessage1("msg.dup.label", var10);
                           this.reportMessage(Context.getContext(), var14, var7, var1, true, var4);
                           continue label303;
                        }
                     }
                  }

                  var7.putProp(20, var10);
                  var12 = new Node(137);
                  var31 = var6.getCurrentParent();

                  for(var35 = var7.getNextSibling(); var35 != null && (var35.getType() == 136 || var35.getType() == 137); var35 = var35.getNextSibling()) {
                  }

                  if (var35 != null) {
                     var31.addChildAfter(var12, var35);
                     var7.putProp(2, var12);
                     if (var35.getType() == 138) {
                        var7.putProp(3, var35.getProp(3));
                     }

                     this.loops.push(var7);
                     this.loopEnds.push(var12);
                  }
                  break;
               case 138:
                  this.loops.push(var7);
                  this.loopEnds.push(var7.getProp(2));
                  break;
               case 140:
                  var7.setType(this.inFunction ? 57 : 2);
                  break;
               case 144:
                  Integer var9 = (Integer)var1.getProp(22);
                  if (var9 == null) {
                     var1.putProp(22, new Integer(1));
                  } else {
                     var1.putProp(22, new Integer(var9 + 1));
                  }
            }
         }

         return var1;
      }
   }

   protected void visitCall(Node var1, Node var2) {
      Node var3 = var1.getFirstChild();
      int var4 = 0;

      for(Node var5 = var3.getNextSibling(); var5 != null; ++var4) {
         var5 = var5.getNextSibling();
      }

      boolean var6 = false;
      Node var9;
      if (var3.getType() == 44) {
         VariableTable var7 = this.getVariableTable(var2);
         String var8 = var3.getString();
         if (this.inFunction && var7.getVariable(var8) != null && !this.inWithStatement()) {
            var3.setType(72);
         } else {
            var1.removeChild(var3);
            var3.setType(71);
            var9 = var3.cloneNode();
            var9.setType(46);
            Node var10 = new Node(39, var3, var9);
            var1.addChildToFront(var10);
            var3 = var10;
            var6 = this.inWithStatement() || !this.inFunction;
         }
      }

      Node var11;
      Node var12;
      if (var3.getType() != 39 && var3.getType() != 41) {
         var1.removeChild(var3);
         var11 = this.irFactory.createNewTemp(var3);
         var12 = this.irFactory.createUseTemp(var11);
         var12.putProp(6, var11);
         var9 = new Node(141, var12);
         var1.addChildToFront(var9);
         var1.addChildToFront(var11);
      } else {
         var11 = var3.getFirstChild();
         var3.removeChild(var11);
         var12 = this.irFactory.createNewTemp(var11);
         var3.addChildToFront(var12);
         var9 = this.irFactory.createUseTemp(var12);
         var9.putProp(6, var12);
         if (var6) {
            var9 = new Node(68, var9);
         }

         var1.addChildAfter(var9, var3);
      }
   }

   protected void visitNew(Node var1, Node var2) {
   }
}
