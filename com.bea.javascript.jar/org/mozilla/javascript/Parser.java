package org.mozilla.javascript;

import java.io.IOException;

class Parser {
   private int lastExprEndLine;
   private IRFactory nf;
   private ErrorReporter er;
   private boolean ok;

   public Parser(IRFactory var1) {
      this.nf = var1;
   }

   private Object addExpr(TokenStream var1, Source var2) throws IOException, JavaScriptException {
      int var3;
      Object var4;
      for(var4 = this.mulExpr(var1, var2); (var3 = var1.getToken()) == 23 || var3 == 24; var4 = this.nf.createBinary(var3, var4, this.mulExpr(var1, var2))) {
         var2.append((char)var3);
      }

      var1.ungetToken(var3);
      return var4;
   }

   private Object andExpr(TokenStream var1, Source var2, boolean var3) throws IOException, JavaScriptException {
      Object var4 = this.bitOrExpr(var1, var2, var3);
      if (var1.matchToken(101)) {
         var2.append('e');
         var4 = this.nf.createBinary(101, var4, this.andExpr(var1, var2, var3));
      }

      return var4;
   }

   private Object argumentList(TokenStream var1, Source var2, Object var3) throws IOException, JavaScriptException {
      var1.flags |= 16;
      boolean var4 = var1.matchToken(95);
      var1.flags &= -17;
      if (!var4) {
         boolean var5 = true;

         do {
            if (!var5) {
               var2.append('`');
            }

            var5 = false;
            this.nf.addChildToBack(var3, this.assignExpr(var1, var2, false));
         } while(var1.matchToken(96));

         this.mustMatchToken(var1, 95, "msg.no.paren.arg");
      }

      var2.append('_');
      return var3;
   }

   private Object assignExpr(TokenStream var1, Source var2, boolean var3) throws IOException, JavaScriptException {
      Object var4 = this.condExpr(var1, var2, var3);
      if (var1.matchToken(97)) {
         var2.append('a');
         var2.append((char)var1.getOp());
         var4 = this.nf.createBinary(97, var1.getOp(), var4, this.assignExpr(var1, var2, var3));
      }

      return var4;
   }

   private Object bitAndExpr(TokenStream var1, Source var2, boolean var3) throws IOException, JavaScriptException {
      Object var4;
      for(var4 = this.eqExpr(var1, var2, var3); var1.matchToken(13); var4 = this.nf.createBinary(13, var4, this.eqExpr(var1, var2, var3))) {
         var2.append('\r');
      }

      return var4;
   }

   private Object bitOrExpr(TokenStream var1, Source var2, boolean var3) throws IOException, JavaScriptException {
      Object var4;
      for(var4 = this.bitXorExpr(var1, var2, var3); var1.matchToken(11); var4 = this.nf.createBinary(11, var4, this.bitXorExpr(var1, var2, var3))) {
         var2.append('\u000b');
      }

      return var4;
   }

   private Object bitXorExpr(TokenStream var1, Source var2, boolean var3) throws IOException, JavaScriptException {
      Object var4;
      for(var4 = this.bitAndExpr(var1, var2, var3); var1.matchToken(12); var4 = this.nf.createBinary(12, var4, this.bitAndExpr(var1, var2, var3))) {
         var2.append('\f');
      }

      return var4;
   }

   private Object condExpr(TokenStream var1, Source var2, boolean var3) throws IOException, JavaScriptException {
      Object var6 = this.orExpr(var1, var2, var3);
      if (var1.matchToken(98)) {
         var2.append('b');
         Object var4 = this.assignExpr(var1, var2, false);
         this.mustMatchToken(var1, 99, "msg.no.colon.cond");
         var2.append('c');
         Object var5 = this.assignExpr(var1, var2, var3);
         return this.nf.createTernary(var6, var4, var5);
      } else {
         return var6;
      }
   }

   private Object condition(TokenStream var1, Source var2) throws IOException, JavaScriptException {
      this.mustMatchToken(var1, 94, "msg.no.paren.cond");
      var2.append('^');
      Object var3 = this.expr(var1, var2, false);
      this.mustMatchToken(var1, 95, "msg.no.paren.after.cond");
      var2.append('_');
      return var3;
   }

   private Object eqExpr(TokenStream var1, Source var2, boolean var3) throws IOException, JavaScriptException {
      Object var4;
      for(var4 = this.relExpr(var1, var2, var3); var1.matchToken(102); var4 = this.nf.createBinary(102, var1.getOp(), var4, this.relExpr(var1, var2, var3))) {
         var2.append('f');
         var2.append((char)var1.getOp());
      }

      return var4;
   }

   private Object expr(TokenStream var1, Source var2, boolean var3) throws IOException, JavaScriptException {
      Object var4;
      for(var4 = this.assignExpr(var1, var2, var3); var1.matchToken(96); var4 = this.nf.createBinary(96, var4, this.assignExpr(var1, var2, var3))) {
         var2.append('`');
      }

      return var4;
   }

   private Object function(TokenStream var1, Source var2, boolean var3) throws IOException, JavaScriptException {
      String var4 = null;
      Object var5 = this.nf.createLeaf(94);
      int var7 = var1.getLineno();
      var2.append('n');
      var2.append(var2.functionNumber);
      ++var2.functionNumber;
      var2 = new Source();
      var2.append('n');
      if (var1.matchToken(44)) {
         var4 = var1.getString();
         var2.addString(44, var4);
      }

      this.mustMatchToken(var1, 94, "msg.no.paren.parms");
      var2.append('^');
      if (!var1.matchToken(95)) {
         boolean var8 = true;

         do {
            if (!var8) {
               var2.append('`');
            }

            var8 = false;
            this.mustMatchToken(var1, 44, "msg.no.parm");
            String var9 = var1.getString();
            this.nf.addChildToBack(var5, this.nf.createName(var9));
            var2.addString(44, var9);
         } while(var1.matchToken(96));

         this.mustMatchToken(var1, 95, "msg.no.paren.after.parms");
      }

      var2.append('_');
      this.mustMatchToken(var1, 92, "msg.no.brace.body");
      var2.append('\\');
      var2.append('\u0001');
      Object var6 = this.parseFunctionBody(var1, var2);
      this.mustMatchToken(var1, 93, "msg.no.brace.after.body");
      var2.append(']');
      return this.nf.createFunction(var4, var5, var6, var1.getSourceName(), var7, var1.getLineno(), var2.buf.toString(), var3);
   }

   private String matchLabel(TokenStream var1) throws IOException, JavaScriptException {
      int var2 = var1.getLineno();
      String var3 = null;
      int var4 = var1.peekTokenSameLine();
      if (var4 == 44) {
         var1.getToken();
         var3 = var1.getString();
      }

      if (var2 == var1.getLineno()) {
         this.wellTerminated(var1, -1);
      }

      return var3;
   }

   private Object memberExpr(TokenStream var1, Source var2, boolean var3) throws IOException, JavaScriptException {
      var1.flags |= 16;
      int var4 = var1.peekToken();
      var1.flags &= -17;
      Object var5;
      if (var4 == 30) {
         var1.getToken();
         var2.append('\u001e');
         var5 = this.nf.createLeaf(30);
         this.nf.addChildToBack(var5, this.memberExpr(var1, var2, false));
         if (var1.matchToken(94)) {
            var2.append('^');
            var5 = this.argumentList(var1, var2, var5);
         }

         var4 = var1.peekToken();
         if (var4 == 92) {
            this.nf.addChildToBack(var5, this.primaryExpr(var1, var2));
         }
      } else {
         var5 = this.primaryExpr(var1, var2);
      }

      this.lastExprEndLine = var1.getLineno();

      while((var4 = var1.getToken()) > 0) {
         if (var4 == 108) {
            var2.append('l');
            this.mustMatchToken(var1, 44, "msg.no.name.after.dot");
            String var6 = var1.getString();
            var2.addString(44, var6);
            var5 = this.nf.createBinary(108, var5, this.nf.createName(var1.getString()));
            this.lastExprEndLine = var1.getLineno();
         } else if (var4 == 90) {
            var2.append('Z');
            var5 = this.nf.createBinary(90, var5, this.expr(var1, var2, false));
            this.mustMatchToken(var1, 91, "msg.no.bracket.index");
            var2.append('[');
            this.lastExprEndLine = var1.getLineno();
         } else {
            if (!var3 || var4 != 94) {
               var1.ungetToken(var4);
               break;
            }

            var5 = this.nf.createUnary(43, var5);
            var2.append('^');
            var5 = this.argumentList(var1, var2, var5);
            this.lastExprEndLine = var1.getLineno();
         }
      }

      return var5;
   }

   private Object mulExpr(TokenStream var1, Source var2) throws IOException, JavaScriptException {
      int var3;
      Object var4;
      for(var4 = this.unaryExpr(var1, var2); (var3 = var1.peekToken()) == 25 || var3 == 26 || var3 == 27; var4 = this.nf.createBinary(var3, var4, this.unaryExpr(var1, var2))) {
         var3 = var1.getToken();
         var2.append((char)var3);
      }

      return var4;
   }

   private void mustMatchToken(TokenStream var1, int var2, String var3) throws IOException, JavaScriptException {
      int var4;
      if ((var4 = var1.getToken()) != var2) {
         this.reportError(var1, var3);
         var1.ungetToken(var4);
      }

   }

   private Object orExpr(TokenStream var1, Source var2, boolean var3) throws IOException, JavaScriptException {
      Object var4 = this.andExpr(var1, var2, var3);
      if (var1.matchToken(100)) {
         var2.append('d');
         var4 = this.nf.createBinary(100, var4, this.orExpr(var1, var2, var3));
      }

      return var4;
   }

   public Object parse(TokenStream var1) throws IOException {
      this.ok = true;
      Source var2 = new Source();
      int var4 = var1.getLineno();
      Object var5 = this.nf.createLeaf(133);

      while(true) {
         var1.flags |= 16;
         int var3 = var1.getToken();
         var1.flags &= -17;
         if (var3 <= 0) {
            break;
         }

         if (var3 == 110) {
            try {
               this.nf.addChildToBack(var5, this.function(var1, var2, false));
               var2.append('\u0001');
               this.wellTerminated(var1, 110);
            } catch (JavaScriptException var7) {
               this.ok = false;
               break;
            }
         } else {
            var1.ungetToken(var3);
            this.nf.addChildToBack(var5, this.statement(var1, var2));
         }
      }

      if (!this.ok) {
         return null;
      } else {
         Object var6 = this.nf.createScript(var5, var1.getSourceName(), var4, var1.getLineno(), var2.buf.toString());
         return var6;
      }
   }

   private Object parseFunctionBody(TokenStream var1, Source var2) throws IOException {
      int var3 = var1.flags;
      var1.flags &= -13;
      var1.flags |= 2;
      Object var4 = this.nf.createBlock(var1.getLineno());

      try {
         int var7;
         try {
            while((var7 = var1.peekToken()) > 0 && var7 != 93) {
               if (var7 == 110) {
                  var1.getToken();
                  this.nf.addChildToBack(var4, this.function(var1, var2, false));
                  var2.append('\u0001');
                  this.wellTerminated(var1, 110);
               } else {
                  this.nf.addChildToBack(var4, this.statement(var1, var2));
               }
            }
         } catch (JavaScriptException var10) {
            this.ok = false;
         }
      } finally {
         var1.flags = var3;
      }

      return var4;
   }

   private Object primaryExpr(TokenStream var1, Source var2) throws IOException, JavaScriptException {
      var1.flags |= 16;
      int var3 = var1.getToken();
      var1.flags &= -17;
      Object var4;
      boolean var5;
      String var7;
      switch (var3) {
         case -1:
            break;
         case 44:
            String var10 = var1.getString();
            var2.addString(44, var10);
            return this.nf.createName(var10);
         case 45:
            Number var12 = var1.getNumber();
            var2.addNumber(var12);
            return this.nf.createNumber(var12);
         case 46:
            var7 = var1.getString();
            var2.addString(46, var7);
            return this.nf.createString(var7);
         case 56:
            String var13 = var1.regExpFlags;
            var1.regExpFlags = null;
            String var9 = var1.getString();
            var2.addString(56, '/' + var9 + '/' + var13);
            return this.nf.createRegExp(var9, var13);
         case 90:
            var2.append('Z');
            var4 = this.nf.createLeaf(134);
            var1.flags |= 16;
            var5 = var1.matchToken(91);
            var1.flags &= -17;
            if (!var5) {
               boolean var11 = true;

               do {
                  var1.flags |= 16;
                  var3 = var1.peekToken();
                  var1.flags &= -17;
                  if (!var11) {
                     var2.append('`');
                  } else {
                     var11 = false;
                  }

                  if (var3 == 91) {
                     break;
                  }

                  if (var3 == 96) {
                     this.nf.addChildToBack(var4, this.nf.createLeaf(109, 74));
                  } else {
                     this.nf.addChildToBack(var4, this.assignExpr(var1, var2, false));
                  }
               } while(var1.matchToken(96));

               this.mustMatchToken(var1, 91, "msg.no.bracket.arg");
            }

            var2.append('[');
            return this.nf.createArrayLiteral(var4);
         case 92:
            var4 = this.nf.createLeaf(135);
            var2.append('\\');
            if (!var1.matchToken(93)) {
               var5 = true;

               label48:
               do {
                  if (!var5) {
                     var2.append('`');
                  } else {
                     var5 = false;
                  }

                  var3 = var1.getToken();
                  Object var6;
                  switch (var3) {
                     case 44:
                     case 46:
                        var7 = var1.getString();
                        var2.addString(44, var7);
                        var6 = this.nf.createString(var1.getString());
                        break;
                     case 45:
                        Number var8 = var1.getNumber();
                        var2.addNumber(var8);
                        var6 = this.nf.createNumber(var8);
                        break;
                     case 93:
                        var1.ungetToken(var3);
                        break label48;
                     default:
                        this.reportError(var1, "msg.bad.prop");
                        break label48;
                  }

                  this.mustMatchToken(var1, 99, "msg.no.colon.prop");
                  var2.append('\u0087');
                  this.nf.addChildToBack(var4, var6);
                  this.nf.addChildToBack(var4, this.assignExpr(var1, var2, false));
               } while(var1.matchToken(96));

               this.mustMatchToken(var1, 93, "msg.no.brace.prop");
            }

            var2.append(']');
            return this.nf.createObjectLiteral(var4);
         case 94:
            var2.append('^');
            var4 = this.expr(var1, var2, false);
            var2.append('_');
            this.mustMatchToken(var1, 95, "msg.no.paren");
            return var4;
         case 109:
            var2.append('m');
            var2.append((char)var1.getOp());
            return this.nf.createLeaf(109, var1.getOp());
         case 110:
            return this.function(var1, var2, true);
         case 127:
            this.reportError(var1, "msg.reserved.id");
            break;
         default:
            this.reportError(var1, "msg.syntax");
      }

      return null;
   }

   private Object relExpr(TokenStream var1, Source var2, boolean var3) throws IOException, JavaScriptException {
      Object var4;
      int var5;
      for(var4 = this.shiftExpr(var1, var2); var1.matchToken(103); var4 = this.nf.createBinary(103, var5, var4, this.shiftExpr(var1, var2))) {
         var5 = var1.getOp();
         if (var3 && var5 == 63) {
            var1.ungetToken(103);
            break;
         }

         var2.append('g');
         var2.append((char)var5);
      }

      return var4;
   }

   private void reportError(TokenStream var1, String var2) throws JavaScriptException {
      this.ok = false;
      var1.reportSyntaxError(var2, (Object[])null);
      throw new JavaScriptException(var2);
   }

   private Object shiftExpr(TokenStream var1, Source var2) throws IOException, JavaScriptException {
      Object var3;
      for(var3 = this.addExpr(var1, var2); var1.matchToken(104); var3 = this.nf.createBinary(var1.getOp(), var3, this.addExpr(var1, var2))) {
         var2.append('h');
         var2.append((char)var1.getOp());
      }

      return var3;
   }

   private Object statement(TokenStream var1, Source var2) throws IOException {
      try {
         return this.statementHelper(var1, var2);
      } catch (JavaScriptException var5) {
         int var3 = var1.getLineno();

         int var4;
         do {
            var4 = var1.getToken();
         } while(var4 != 89 && var4 != 1 && var4 != 0 && var4 != -1);

         return this.nf.createExprStatement(this.nf.createName("error"), var3);
      }
   }

   private Object statementHelper(TokenStream var1, Source var2) throws IOException, JavaScriptException {
      Object var3 = null;
      boolean var4 = false;
      boolean var6 = false;
      int var5 = var1.getToken();
      int var7;
      Object var8;
      Object var9;
      Object var10;
      String var15;
      Object var16;
      int var18;
      switch (var5) {
         case -1:
         case 1:
         case 89:
            var3 = this.nf.createLeaf(132);
            var4 = true;
            break;
         case 5:
            var16 = null;
            var18 = 0;
            var2.append('\u0005');
            if ((var1.flags & 2) == 0) {
               this.reportError(var1, "msg.bad.return");
            }

            var1.flags |= 16;
            var5 = var1.peekTokenSameLine();
            var1.flags &= -17;
            if (var5 != 0 && var5 != 1 && var5 != 89 && var5 != 93) {
               var18 = var1.getLineno();
               var16 = this.expr(var1, var2, false);
               if (var1.getLineno() == var18) {
                  this.wellTerminated(var1, -1);
               }

               var1.flags |= 4;
            } else {
               var1.flags |= 8;
            }

            var3 = this.nf.createReturn(var16, var18);
            break;
         case 62:
            var7 = var1.getLineno();
            var2.append('>');
            var3 = this.nf.createThrow(this.expr(var1, var2, false), var7);
            if (var7 == var1.getLineno()) {
               this.wellTerminated(var1, -1);
            }
            break;
         case 75:
            var7 = var1.getLineno();
            var9 = null;
            var10 = null;
            var4 = true;
            var2.append('K');
            var2.append('\\');
            var2.append('\u0001');
            var8 = this.statement(var1, var2);
            var2.append(']');
            var2.append('\u0001');
            var9 = this.nf.createLeaf(133);
            boolean var17 = false;
            int var12 = var1.peekToken();
            if (var12 == 125) {
               while(var1.matchToken(125)) {
                  if (var17) {
                     this.reportError(var1, "msg.catch.unreachable");
                  }

                  var2.append('}');
                  this.mustMatchToken(var1, 94, "msg.no.paren.catch");
                  var2.append('^');
                  this.mustMatchToken(var1, 44, "msg.bad.catchcond");
                  String var13 = var1.getString();
                  var2.addString(44, var13);
                  Object var14 = null;
                  if (var1.matchToken(113)) {
                     var2.append('q');
                     var14 = this.expr(var1, var2, false);
                  } else {
                     var17 = true;
                  }

                  this.mustMatchToken(var1, 95, "msg.bad.catchcond");
                  var2.append('_');
                  this.mustMatchToken(var1, 92, "msg.no.brace.catchblock");
                  var2.append('\\');
                  var2.append('\u0001');
                  this.nf.addChildToBack(var9, this.nf.createCatch(var13, var14, this.statements(var1, var2), var1.getLineno()));
                  this.mustMatchToken(var1, 93, "msg.no.brace.after.body");
                  var2.append(']');
                  var2.append('\u0001');
               }
            } else if (var12 != 126) {
               this.mustMatchToken(var1, 126, "msg.try.no.catchfinally");
            }

            if (var1.matchToken(126)) {
               var2.append('~');
               var2.append('\\');
               var2.append('\u0001');
               var10 = this.statement(var1, var2);
               var2.append(']');
               var2.append('\u0001');
            }

            var3 = this.nf.createTryCatchFinally(var8, var9, var10, var7);
            break;
         case 92:
            var4 = true;
            var3 = this.statements(var1, var2);
            this.mustMatchToken(var1, 93, "msg.no.brace.block");
            break;
         case 113:
            var4 = true;
            var2.append('q');
            var7 = var1.getLineno();
            var8 = this.condition(var1, var2);
            var2.append('\\');
            var2.append('\u0001');
            var9 = this.statement(var1, var2);
            var10 = null;
            if (var1.matchToken(114)) {
               var2.append(']');
               var2.append('r');
               var2.append('\\');
               var2.append('\u0001');
               var10 = this.statement(var1, var2);
            }

            var2.append(']');
            var2.append('\u0001');
            var3 = this.nf.createIf(var8, var9, var10, var7);
            break;
         case 115:
            var4 = true;
            var2.append('s');
            var3 = this.nf.createSwitch(var1.getLineno());
            var16 = null;
            this.mustMatchToken(var1, 94, "msg.no.paren.switch");
            var2.append('^');
            this.nf.addChildToBack(var3, this.expr(var1, var2, false));
            this.mustMatchToken(var1, 95, "msg.no.paren.after.switch");
            var2.append('_');
            this.mustMatchToken(var1, 92, "msg.no.brace.switch");
            var2.append('\\');
            var2.append('\u0001');

            while((var5 = var1.getToken()) != 93 && var5 != 0) {
               switch (var5) {
                  case 116:
                     var2.append('t');
                     var16 = this.nf.createUnary(116, this.expr(var1, var2, false));
                     var2.append('c');
                     var2.append('\u0001');
                     break;
                  case 117:
                     var16 = this.nf.createLeaf(117);
                     var2.append('u');
                     var2.append('c');
                     var2.append('\u0001');
                     break;
                  default:
                     this.reportError(var1, "msg.bad.switch");
               }

               this.mustMatchToken(var1, 99, "msg.no.colon.case");
               var8 = this.nf.createLeaf(133);

               while((var5 = var1.peekToken()) != 93 && var5 != 116 && var5 != 117 && var5 != 0) {
                  this.nf.addChildToBack(var8, this.statement(var1, var2));
               }

               this.nf.addChildToBack(var16, var8);
               this.nf.addChildToBack(var3, var16);
            }

            var2.append(']');
            var2.append('\u0001');
            break;
         case 118:
            var4 = true;
            var2.append('v');
            var7 = var1.getLineno();
            var8 = this.condition(var1, var2);
            var2.append('\\');
            var2.append('\u0001');
            var9 = this.statement(var1, var2);
            var2.append(']');
            var2.append('\u0001');
            var3 = this.nf.createWhile(var8, var9, var7);
            break;
         case 119:
            var2.append('w');
            var2.append('\\');
            var2.append('\u0001');
            var7 = var1.getLineno();
            var8 = this.statement(var1, var2);
            var2.append(']');
            this.mustMatchToken(var1, 118, "msg.no.while.do");
            var2.append('v');
            var9 = this.condition(var1, var2);
            var3 = this.nf.createDoWhile(var8, var9, var7);
            break;
         case 120:
            var4 = true;
            var2.append('x');
            var7 = var1.getLineno();
            var10 = null;
            this.mustMatchToken(var1, 94, "msg.no.paren.for");
            var2.append('^');
            var5 = var1.peekToken();
            if (var5 == 89) {
               var8 = this.nf.createLeaf(132);
            } else if (var5 == 123) {
               var1.getToken();
               var8 = this.variables(var1, var2, true);
            } else {
               var8 = this.expr(var1, var2, true);
            }

            var5 = var1.peekToken();
            if (var5 == 103 && var1.getOp() == 63) {
               var1.matchToken(103);
               var2.append('?');
               var9 = this.expr(var1, var2, false);
            } else {
               this.mustMatchToken(var1, 89, "msg.no.semi.for");
               var2.append('Y');
               if (var1.peekToken() == 89) {
                  var9 = this.nf.createLeaf(132);
               } else {
                  var9 = this.expr(var1, var2, false);
               }

               this.mustMatchToken(var1, 89, "msg.no.semi.for.cond");
               var2.append('Y');
               if (var1.peekToken() == 95) {
                  var10 = this.nf.createLeaf(132);
               } else {
                  var10 = this.expr(var1, var2, false);
               }
            }

            this.mustMatchToken(var1, 95, "msg.no.paren.for.ctrl");
            var2.append('_');
            var2.append('\\');
            var2.append('\u0001');
            Object var11 = this.statement(var1, var2);
            var2.append(']');
            var2.append('\u0001');
            if (var10 == null) {
               var3 = this.nf.createForIn(var8, var9, var11, var7);
            } else {
               var3 = this.nf.createFor(var8, var9, var10, var11, var7);
            }
            break;
         case 121:
            var7 = var1.getLineno();
            var2.append('y');
            var15 = this.matchLabel(var1);
            if (var15 != null) {
               var2.addString(44, var15);
            }

            var3 = this.nf.createBreak(var15, var7);
            break;
         case 122:
            var7 = var1.getLineno();
            var2.append('z');
            var15 = this.matchLabel(var1);
            if (var15 != null) {
               var2.addString(44, var15);
            }

            var3 = this.nf.createContinue(var15, var7);
            break;
         case 123:
            var7 = var1.getLineno();
            var3 = this.variables(var1, var2, false);
            if (var1.getLineno() == var7) {
               this.wellTerminated(var1, -1);
            }
            break;
         case 124:
            var4 = true;
            var2.append('|');
            var7 = var1.getLineno();
            this.mustMatchToken(var1, 94, "msg.no.paren.with");
            var2.append('^');
            var8 = this.expr(var1, var2, false);
            this.mustMatchToken(var1, 95, "msg.no.paren.after.with");
            var2.append('_');
            var2.append('\\');
            var2.append('\u0001');
            var9 = this.statement(var1, var2);
            var2.append(']');
            var2.append('\u0001');
            var3 = this.nf.createWith(var8, var9, var7);
            break;
         default:
            var7 = var1.getTokenno();
            var1.ungetToken(var5);
            var18 = var1.getLineno();
            var3 = this.expr(var1, var2, false);
            if (var1.peekToken() == 99) {
               if (var5 != 44 || var1.getTokenno() != var7) {
                  this.reportError(var1, "msg.bad.label");
               }

               var1.getToken();
               String var19 = var1.getString();
               var3 = this.nf.createLabel(var19, var18);
               var2.append('c');
               var2.append('\u0001');
               return var3;
            }

            if (var5 == 110) {
               this.nf.setFunctionExpressionStatement(var3);
            }

            var3 = this.nf.createExprStatement(var3, var18);
            if (var1.getLineno() == var18 || var5 == 110 && var1.getLineno() == this.lastExprEndLine) {
               this.wellTerminated(var1, var5);
            }
      }

      var1.matchToken(89);
      if (!var4) {
         var2.append('Y');
         var2.append('\u0001');
      }

      return var3;
   }

   private Object statements(TokenStream var1, Source var2) throws IOException {
      Object var3 = this.nf.createBlock(var1.getLineno());

      int var4;
      while((var4 = var1.peekToken()) > 0 && var4 != 93) {
         this.nf.addChildToBack(var3, this.statement(var1, var2));
      }

      return var3;
   }

   private Object unaryExpr(TokenStream var1, Source var2) throws IOException, JavaScriptException {
      var1.flags |= 16;
      int var3 = var1.getToken();
      var1.flags &= -17;
      switch (var3) {
         case -1:
            return this.nf.createName("err");
         case 23:
         case 24:
            var2.append('i');
            var2.append((char)var3);
            return this.nf.createUnary(105, var3, this.unaryExpr(var1, var2));
         case 31:
            var2.append('\u001f');
            return this.nf.createUnary(31, this.unaryExpr(var1, var2));
         case 105:
            var2.append('i');
            var2.append((char)var1.getOp());
            return this.nf.createUnary(105, var1.getOp(), this.unaryExpr(var1, var2));
         case 106:
         case 107:
            var2.append((char)var3);
            return this.nf.createUnary(var3, 130, this.memberExpr(var1, var2, true));
         default:
            var1.ungetToken(var3);
            int var4 = var1.getLineno();
            Object var5 = this.memberExpr(var1, var2, true);
            int var6;
            if (((var6 = var1.peekToken()) == 106 || var6 == 107) && var1.getLineno() == var4) {
               int var7 = var1.getToken();
               var2.append((char)var7);
               return this.nf.createUnary(var7, 131, var5);
            } else {
               return var5;
            }
      }
   }

   private Object variables(TokenStream var1, Source var2, boolean var3) throws IOException, JavaScriptException {
      Object var4 = this.nf.createVariables(var1.getLineno());
      boolean var5 = true;
      var2.append('{');

      do {
         this.mustMatchToken(var1, 44, "msg.bad.var");
         String var8 = var1.getString();
         if (!var5) {
            var2.append('`');
         }

         var5 = false;
         var2.addString(44, var8);
         Object var6 = this.nf.createName(var8);
         if (var1.matchToken(97)) {
            if (var1.getOp() != 128) {
               this.reportError(var1, "msg.bad.var.init");
            }

            var2.append('a');
            var2.append('\u0080');
            Object var7 = this.assignExpr(var1, var2, var3);
            this.nf.addChildToBack(var6, var7);
         }

         this.nf.addChildToBack(var4, var6);
      } while(var1.matchToken(96));

      return var4;
   }

   private boolean wellTerminated(TokenStream var1, int var2) throws IOException, JavaScriptException {
      int var3 = var1.peekTokenSameLine();
      if (var3 == -1) {
         return false;
      } else {
         if (var3 != 0 && var3 != 1 && var3 != 89 && var3 != 93) {
            int var4 = Context.getContext().getLanguageVersion();
            if ((var3 == 110 || var2 == 110) && var4 < 120) {
               return true;
            }

            this.reportError(var1, "msg.no.semi.stmt");
         }

         return true;
      }
   }
}
