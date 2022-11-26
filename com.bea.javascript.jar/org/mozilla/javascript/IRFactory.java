package org.mozilla.javascript;

public class IRFactory {
   private TokenStream ts;
   private Scriptable scope;

   public IRFactory(TokenStream var1, Scriptable var2) {
      this.ts = var1;
      this.scope = var2;
   }

   public void addChildToBack(Object var1, Object var2) {
      ((Node)var1).addChildToBack((Node)var2);
   }

   public Object createArrayLiteral(Object var1) {
      Node var3;
      Node var2 = var3 = new Node(30, new Node(44, "Array"));
      Node var4 = this.createNewTemp(var3);
      var3 = var4;
      ShallowNodeIterator var5 = ((Node)var1).getChildIterator();
      Node var6 = null;
      int var7 = 0;

      while(true) {
         Node var8;
         while(var5.hasMoreElements()) {
            var6 = (Node)var5.nextElement();
            if (var6.getType() == 109 && var6.getInt() == 74) {
               ++var7;
            } else {
               var8 = new Node(42, this.createUseTemp(var4), new Node(45, new Integer(var7)), var6);
               ++var7;
               var3 = new Node(96, var3, var8);
            }
         }

         if (Context.getContext().getLanguageVersion() == 120) {
            if (var6 != null && var6.getType() == 109 && var6.getInt() == 74) {
               var8 = new Node(40, this.createUseTemp(var4), new Node(46, "length"), new Node(45, new Integer(var7)));
               var3 = new Node(96, var3, var8);
            }
         } else {
            var2.addChildToBack(new Node(45, new Integer(var7)));
         }

         return new Node(96, var3, this.createUseTemp(var4));
      }
   }

   public Object createAssignment(int var1, Node var2, Node var3, Class var4, boolean var5) {
      int var6 = var2.getType();
      Node var8 = null;
      switch (var6) {
         case 39:
            String var7 = (String)var2.getProp(19);
            if (var7 != null) {
               var8 = new Node(46, var7);
            }
         case 41:
            if (var8 == null) {
               var8 = var2.getLastChild();
            }

            return this.createSetProp(var6, var1, var2.getFirstChild(), var8, var3, var4, var5);
         case 40:
         case 42:
         case 43:
         default:
            this.reportError("msg.bad.lhs.assign");
            return var2;
         case 44:
            return this.createSetName(var1, var2, var3, var4, var5);
      }
   }

   public Object createBinary(int var1, int var2, Object var3, Object var4) {
      return var1 == 97 ? this.createAssignment(var2, (Node)var3, (Node)var4, (Class)null, false) : new Node(var1, (Node)var3, (Node)var4, new Integer(var2));
   }

   public Object createBinary(int var1, Object var2, Object var3) {
      switch (var1) {
         case 90:
            var1 = 41;
            break;
         case 108:
            var1 = 39;
            Node var5 = (Node)var3;
            var5.setType(46);
            String var6 = var5.getString();
            if (var6.equals("__proto__") || var6.equals("__parent__")) {
               Node var7 = new Node(var1, (Node)var2);
               var7.putProp(19, var6);
               return var7;
            }
      }

      return new Node(var1, (Node)var2, (Node)var3);
   }

   public Object createBlock(int var1) {
      return new Node(133, new Integer(var1));
   }

   public Object createBreak(String var1, int var2) {
      Node var3 = new Node(121, new Integer(var2));
      if (var1 == null) {
         return var3;
      } else {
         Node var4 = new Node(44, var1);
         var3.addChildToBack(var4);
         return var3;
      }
   }

   public Object createCatch(String var1, Object var2, Object var3, int var4) {
      if (var2 == null) {
         var2 = new Node(109, new Integer(52));
      }

      Node var5 = new Node(125, (Node)this.createName(var1), (Node)var2, (Node)var3);
      var5.setDatum(new Integer(var4));
      return var5;
   }

   public Object createContinue(String var1, int var2) {
      Node var3 = new Node(122, new Integer(var2));
      if (var1 == null) {
         return var3;
      } else {
         Node var4 = new Node(44, var1);
         var3.addChildToBack(var4);
         return var3;
      }
   }

   private Node createConvert(Class var1, Node var2) {
      if (var1 == null) {
         return var2;
      } else {
         Node var3 = new Node(142, var2);
         var3.putProp(18, ScriptRuntime.NumberClass);
         return var3;
      }
   }

   public Object createDoWhile(Object var1, Object var2, int var3) {
      Node var4 = new Node(138, new Integer(var3));
      Node var5 = new Node(137);
      Node var6 = new Node(137);
      Node var7 = new Node(7, (Node)var2);
      var7.putProp(1, var5);
      Node var8 = new Node(137);
      var4.addChildToBack(var5);
      var4.addChildrenToBack((Node)var1);
      var4.addChildToBack(var6);
      var4.addChildToBack(var7);
      var4.addChildToBack(var8);
      var4.putProp(2, var8);
      var4.putProp(3, var6);
      return var4;
   }

   public Object createExprStatement(Object var1, int var2) {
      return new Node(140, (Node)var1, new Integer(var2));
   }

   public Object createFor(Object var1, Object var2, Object var3, Object var4, int var5) {
      if (((Node)var2).getType() == 132) {
         var2 = new Node(109, new Integer(52));
      }

      Node var6 = (Node)this.createWhile(var2, var4, var5);
      Node var7 = (Node)var1;
      if (var7.getType() != 132) {
         if (var7.getType() != 123) {
            var7 = new Node(57, var7);
         }

         var6.addChildToFront(var7);
      }

      Node var8 = (Node)var6.getProp(3);
      Node var9 = new Node(137);
      var6.addChildBefore(var9, var8);
      if (((Node)var3).getType() != 132) {
         var3 = this.createUnary(57, var3);
         var6.addChildAfter((Node)var3, var9);
      }

      var6.putProp(3, var9);
      return var6;
   }

   public Object createForIn(Object var1, Object var2, Object var3, int var4) {
      Node var6 = (Node)var1;
      Node var7 = (Node)var2;
      int var8 = var6.getType();
      Node var9 = var6;
      Node var10;
      switch (var8) {
         case 123:
            var10 = var6.getLastChild();
            if (var6.getFirstChild() != var10) {
               this.reportError("msg.mult.index");
            }

            var9 = new Node(44, var10.getString());
         case 39:
         case 41:
         case 44:
            var10 = new Node(79, var7);
            Node var11 = new Node(80);
            var11.putProp(4, var10);
            Node var12 = this.createNewTemp(var11);
            Node var13 = new Node(102, new Integer(15));
            var13.addChildToBack(var12);
            var13.addChildToBack(new Node(109, new Integer(49)));
            Node var14 = new Node(133);
            Node var15 = (Node)this.createAssignment(128, var9, this.createUseTemp(var12), (Class)null, false);
            var14.addChildToBack(new Node(57, var15));
            var14.addChildToBack((Node)var3);
            Node var16 = (Node)this.createWhile(var13, var14, var4);
            var16.addChildToFront(var10);
            if (var8 == 123) {
               var16.addChildToFront(var6);
            }

            Node var17 = new Node(139);
            var17.putProp(4, var10);
            var16.addChildToBack(var17);
            return var16;
         default:
            this.reportError("msg.bad.for.in.lhs");
            return var7;
      }
   }

   public Object createFunction(String var1, Object var2, Object var3, String var4, int var5, int var6, Object var7, boolean var8) {
      FunctionNode var9 = (FunctionNode)this.createFunctionNode(var1, var2, var3);
      var9.setFunctionType((byte)(var8 ? 2 : 1));
      var9.putProp(16, var4);
      var9.putProp(28, new Integer(var5));
      var9.putProp(29, new Integer(var6));
      if (var7 != null) {
         var9.putProp(17, var7);
      }

      Node var10 = new Node(110, var1);
      var10.putProp(5, var9);
      return var10;
   }

   public Object createFunctionNode(String var1, Object var2, Object var3) {
      if (var1 == null) {
         var1 = "";
      }

      return new FunctionNode(var1, (Node)var2, (Node)var3);
   }

   public Object createIf(Object var1, Object var2, Object var3, int var4) {
      Node var5 = new Node(133, new Integer(var4));
      Node var6 = new Node(137);
      Node var7 = new Node(8, (Node)var1);
      var7.putProp(1, var6);
      var5.addChildToBack(var7);
      var5.addChildrenToBack((Node)var2);
      if (var3 != null) {
         Node var8 = new Node(6);
         Node var9 = new Node(137);
         var8.putProp(1, var9);
         var5.addChildToBack(var8);
         var5.addChildToBack(var6);
         var5.addChildrenToBack((Node)var3);
         var5.addChildToBack(var9);
      } else {
         var5.addChildToBack(var6);
      }

      return var5;
   }

   public Object createLabel(String var1, int var2) {
      Node var3 = new Node(136, new Integer(var2));
      Node var4 = new Node(44, var1);
      var3.addChildToBack(var4);
      return var3;
   }

   public Object createLeaf(int var1) {
      return new Node(var1);
   }

   public Object createLeaf(int var1, int var2) {
      return new Node(var1, new Integer(var2));
   }

   public Object createLeaf(int var1, String var2) {
      return new Node(var1, var2);
   }

   public Object createName(String var1) {
      return new Node(44, var1);
   }

   public Node createNewLocal(Node var1) {
      Node var2 = new Node(144, var1);
      return var2;
   }

   public Node createNewTemp(Node var1) {
      int var2 = var1.getType();
      if (var2 != 46 && var2 != 45) {
         Node var3 = new Node(69, var1);
         return var3;
      } else {
         return var1;
      }
   }

   public Object createNumber(Number var1) {
      return new Node(45, var1);
   }

   public Object createObjectLiteral(Object var1) {
      Node var2 = new Node(30, new Node(44, "Object"));
      Node var3 = this.createNewTemp(var2);
      var2 = var3;

      Node var7;
      for(ShallowNodeIterator var4 = ((Node)var1).getChildIterator(); var4.hasMoreElements(); var2 = new Node(96, var2, var7)) {
         Node var5 = (Node)var4.nextElement();
         int var6 = var5.getType() == 44 ? 40 : 42;
         var7 = new Node(var6, this.createUseTemp(var3), var5, (Node)var4.nextElement());
      }

      return new Node(96, var2, this.createUseTemp(var3));
   }

   public Object createRegExp(String var1, String var2) {
      return var2.length() == 0 ? new Node(56, new Node(46, var1)) : new Node(56, new Node(46, var1), new Node(46, var2));
   }

   public Object createReturn(Object var1, int var2) {
      return var1 == null ? new Node(5, new Integer(var2)) : new Node(5, (Node)var1, new Integer(var2));
   }

   public Object createScript(Object var1, String var2, int var3, int var4, Object var5) {
      Node var6 = new Node(146, var2);
      Node var7 = ((Node)var1).getFirstChild();
      if (var7 != null) {
         var6.addChildrenToBack(var7);
      }

      var6.putProp(16, var2);
      var6.putProp(28, new Integer(var3));
      var6.putProp(29, new Integer(var4));
      if (var5 != null) {
         var6.putProp(17, var5);
      }

      return var6;
   }

   private Object createSetName(int var1, Node var2, Node var3, Class var4, boolean var5) {
      if (var1 == 128) {
         var2.setType(61);
         return new Node(10, var2, var3);
      } else {
         String var6 = var2.getString();
         Node var7;
         if (!var6.equals("__proto__") && !var6.equals("__parent__")) {
            var7 = new Node(44, var6);
            if (var4 != null) {
               var7 = this.createConvert(var4, var7);
            }

            if (var5) {
               var7 = this.createNewTemp(var7);
            }

            Node var8 = new Node(var1, var7, var3);
            Node var9 = new Node(61, var6);
            Node var10 = new Node(10, var9, var8);
            if (var5) {
               var10 = new Node(96, var10, this.createUseTemp(var7));
            }

            return var10;
         } else {
            var7 = new Node(40, var2, var3);
            var7.putProp(19, var6);
            return var7;
         }
      }
   }

   private Node createSetProp(int var1, int var2, Node var3, Node var4, Node var5, Class var6, boolean var7) {
      int var8 = var1 == 39 ? 40 : 42;
      Object var9 = var4.getDatum();
      Node var11;
      if (var8 == 40 && var9 != null && var9 instanceof String) {
         String var10 = (String)var9;
         if (var10.equals("__proto__") || var10.equals("__parent__")) {
            var11 = new Node(var8, var3, var5);
            var11.putProp(19, var10);
            return var11;
         }
      }

      if (var2 == 128) {
         return new Node(var8, var3, var4, var5);
      } else {
         Node var12;
         Node var13;
         Node var14;
         Node var15;
         if (!hasSideEffects(var5) && !hasSideEffects(var4) && var3.getType() == 44) {
            var15 = var3.cloneNode();
            var11 = var4.cloneNode();
            var12 = new Node(var1, var3, var4);
         } else {
            var15 = this.createNewTemp(var3);
            var13 = this.createUseTemp(var15);
            var11 = this.createNewTemp(var4);
            var14 = this.createUseTemp(var11);
            var12 = new Node(var1, var13, var14);
         }

         if (var6 != null) {
            var12 = this.createConvert(var6, var12);
         }

         if (var7) {
            var12 = this.createNewTemp(var12);
         }

         var13 = new Node(var2, var12, var5);
         var14 = new Node(var8, var15, var11, var13);
         if (var7) {
            var14 = new Node(96, var14, this.createUseTemp(var12));
         }

         return var14;
      }
   }

   public Object createString(String var1) {
      return new Node(46, var1);
   }

   public Object createSwitch(int var1) {
      return new Node(115, new Integer(var1));
   }

   public Object createTernary(Object var1, Object var2, Object var3) {
      return this.createIf(var1, var2, var3, -1);
   }

   public Object createThrow(Object var1, int var2) {
      return new Node(62, (Node)var1, new Integer(var2));
   }

   public Object createTryCatchFinally(Object var1, Object var2, Object var3, int var4) {
      Node var5 = (Node)var1;
      if (var5.getType() == 133 && !var5.hasChildren()) {
         return var5;
      } else {
         Node var6 = new Node(75, var5, new Integer(var4));
         Node var7 = (Node)var2;
         boolean var8 = var7.hasChildren();
         boolean var9 = false;
         Node var10 = null;
         Node var11 = null;
         Node var12;
         if (var3 != null) {
            var10 = (Node)var3;
            var9 = var10.getType() != 133 || var10.hasChildren();
            if (var9) {
               var11 = new Node(137);
               var6.putProp(21, var11);
               var12 = new Node(143);
               var12.putProp(1, var11);
               var6.addChildToBack(var12);
            }
         }

         if (!var9 && !var8) {
            return var5;
         } else {
            var12 = new Node(137);
            Node var13 = new Node(6);
            var13.putProp(1, var12);
            var6.addChildToBack(var13);
            Node var14;
            Node var15;
            Node var16;
            if (var8) {
               var14 = new Node(137);
               var6.putProp(1, var14);
               var6.addChildToBack(var14);
               var15 = this.createNewLocal(new Node(132));
               var6.addChildToBack(new Node(57, var15));
               var16 = new Node(137);

               Node var18;
               Node var20;
               for(Node var17 = var7.getFirstChild(); var17 != null; var17 = var17.getNextSibling()) {
                  var18 = new Node(133);
                  int var19 = (Integer)var17.getDatum();
                  var20 = var17.getFirstChild();
                  Node var21 = var20.getNextSibling();
                  Node var22 = var21.getNextSibling();
                  var17.removeChild(var20);
                  var17.removeChild(var21);
                  var17.removeChild(var22);
                  Node var23 = this.createNewLocal(new Node(77));
                  Node var24 = new Node(40, var23, new Node(46, var20.getString()), this.createUseLocal(var15));
                  var18.addChildToBack(new Node(57, var24));
                  var22.addChildToBack(new Node(4));
                  Node var25 = new Node(6);
                  var25.putProp(1, var16);
                  var22.addChildToBack(var25);
                  Node var26 = (Node)this.createIf(var21, var22, (Object)null, var19);
                  Node var27 = (Node)this.createWith(this.createUseLocal(var23), var26, var19);
                  var18.addChildToBack(var27);
                  var6.addChildToBack(var18);
               }

               var18 = new Node(62, this.createUseLocal(var15));
               var6.addChildToBack(var18);
               var6.addChildToBack(var16);
               if (var9) {
                  Node var28 = new Node(143);
                  var28.putProp(1, var11);
                  var6.addChildToBack(var28);
                  var20 = new Node(6);
                  var20.putProp(1, var12);
                  var6.addChildToBack(var20);
               }
            }

            if (var9) {
               var6.addChildToBack(var11);
               var14 = this.createNewLocal(new Node(132));
               var15 = new Node(57, var14);
               var6.addChildToBack(var15);
               var6.addChildToBack(var10);
               var16 = this.createUseLocal(var14);
               var16.putProp(1, Boolean.TRUE);
               var6.addChildToBack(var16);
            }

            var6.addChildToBack(var12);
            return var6;
         }
      }
   }

   public Object createUnary(int var1, int var2, Object var3) {
      Node var4 = (Node)var3;
      int var5 = var4.getType();
      if (var2 == 32 && var5 == 44) {
         var4.setType(32);
         return var4;
      } else {
         Node var6;
         if (var1 != 106 && var1 != 107) {
            var6 = new Node(var1, new Integer(var2));
            var6.addChildToBack((Node)var3);
            return var6;
         } else if (!hasSideEffects(var4) && var2 == 131 && (var5 == 44 || var5 == 39 || var5 == 41)) {
            return new Node(var1, var4);
         } else {
            var6 = (Node)this.createNumber(new Double(1.0));
            return this.createAssignment(var1 == 106 ? 23 : 24, var4, var6, ScriptRuntime.NumberClass, var2 == 131);
         }
      }
   }

   public Object createUnary(int var1, Object var2) {
      Node var3 = (Node)var2;
      if (var1 == 31) {
         int var4 = var3.getType();
         Node var5;
         Node var6;
         if (var4 == 44) {
            var3.setType(61);
            var5 = var3;
            var6 = var3.cloneNode();
            var6.setType(46);
         } else {
            if (var4 != 39 && var4 != 41) {
               return new Node(109, new Integer(52));
            }

            var5 = var3.getFirstChild();
            var6 = var3.getLastChild();
            var3.removeChild(var5);
            var3.removeChild(var6);
         }

         return new Node(var1, var5, var6);
      } else {
         return new Node(var1, var3);
      }
   }

   public Node createUseLocal(Node var1) {
      int var2 = var1.getType();
      if (var2 == 144) {
         Node var3 = new Node(145);
         var3.putProp(7, var1);
         return var3;
      } else {
         return var1.cloneNode();
      }
   }

   public Node createUseTemp(Node var1) {
      int var2 = var1.getType();
      if (var2 == 69) {
         Node var3 = new Node(70);
         var3.putProp(6, var1);
         Integer var4 = (Integer)var1.getProp(11);
         if (var4 == null) {
            var4 = new Integer(1);
         } else if (var4 < Integer.MAX_VALUE) {
            var4 = new Integer(var4 + 1);
         }

         var1.putProp(11, var4);
         return var3;
      } else {
         return var1.cloneNode();
      }
   }

   public Object createVariables(int var1) {
      return new Node(123, new Integer(var1));
   }

   public Object createWhile(Object var1, Object var2, int var3) {
      Node var4 = (Node)this.createDoWhile(var2, var1, var3);
      Node var5 = (Node)var4.getProp(3);
      Node var6 = new Node(6);
      var6.putProp(1, var5);
      var4.addChildToFront(var6);
      return var4;
   }

   public Object createWith(Object var1, Object var2, int var3) {
      Node var4 = new Node(133, new Integer(var3));
      var4.addChildToBack(new Node(3, (Node)var1));
      Node var5 = new Node(124, (Node)var2, new Integer(var3));
      var4.addChildrenToBack(var5);
      var4.addChildToBack(new Node(4));
      return var4;
   }

   public static boolean hasSideEffects(Node var0) {
      switch (var0.getType()) {
         case 10:
         case 30:
         case 40:
         case 42:
         case 43:
         case 106:
         case 107:
            return true;
         default:
            for(Node var1 = var0.getFirstChild(); var1 != null; var1 = var1.getNextSibling()) {
               if (hasSideEffects(var1)) {
                  return true;
               }
            }

            return false;
      }
   }

   private void reportError(String var1) {
      if (this.scope != null) {
         throw NativeGlobal.constructError(Context.getContext(), "SyntaxError", ScriptRuntime.getMessage0(var1), this.scope);
      } else {
         String var2 = Context.getMessage0(var1);
         Context.reportError(var2, this.ts.getSourceName(), this.ts.getLineno(), this.ts.getLine(), this.ts.getOffset());
      }
   }

   public void setFunctionExpressionStatement(Object var1) {
      Node var2 = (Node)var1;
      FunctionNode var3 = (FunctionNode)var2.getProp(5);
      var3.setFunctionType((byte)3);
   }
}
