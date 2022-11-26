package org.apache.velocity.runtime.parser;

import java.io.ByteArrayInputStream;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.directive.Macro;
import org.apache.velocity.runtime.directive.MacroParseException;
import org.apache.velocity.runtime.parser.node.ASTAddNode;
import org.apache.velocity.runtime.parser.node.ASTAndNode;
import org.apache.velocity.runtime.parser.node.ASTAssignment;
import org.apache.velocity.runtime.parser.node.ASTBlock;
import org.apache.velocity.runtime.parser.node.ASTComment;
import org.apache.velocity.runtime.parser.node.ASTDirective;
import org.apache.velocity.runtime.parser.node.ASTDivNode;
import org.apache.velocity.runtime.parser.node.ASTEQNode;
import org.apache.velocity.runtime.parser.node.ASTElseIfStatement;
import org.apache.velocity.runtime.parser.node.ASTElseStatement;
import org.apache.velocity.runtime.parser.node.ASTEscape;
import org.apache.velocity.runtime.parser.node.ASTEscapedDirective;
import org.apache.velocity.runtime.parser.node.ASTExpression;
import org.apache.velocity.runtime.parser.node.ASTFalse;
import org.apache.velocity.runtime.parser.node.ASTGENode;
import org.apache.velocity.runtime.parser.node.ASTGTNode;
import org.apache.velocity.runtime.parser.node.ASTIdentifier;
import org.apache.velocity.runtime.parser.node.ASTIfStatement;
import org.apache.velocity.runtime.parser.node.ASTIntegerRange;
import org.apache.velocity.runtime.parser.node.ASTLENode;
import org.apache.velocity.runtime.parser.node.ASTLTNode;
import org.apache.velocity.runtime.parser.node.ASTMethod;
import org.apache.velocity.runtime.parser.node.ASTModNode;
import org.apache.velocity.runtime.parser.node.ASTMulNode;
import org.apache.velocity.runtime.parser.node.ASTNENode;
import org.apache.velocity.runtime.parser.node.ASTNotNode;
import org.apache.velocity.runtime.parser.node.ASTNumberLiteral;
import org.apache.velocity.runtime.parser.node.ASTObjectArray;
import org.apache.velocity.runtime.parser.node.ASTOrNode;
import org.apache.velocity.runtime.parser.node.ASTReference;
import org.apache.velocity.runtime.parser.node.ASTSetDirective;
import org.apache.velocity.runtime.parser.node.ASTStringLiteral;
import org.apache.velocity.runtime.parser.node.ASTSubtractNode;
import org.apache.velocity.runtime.parser.node.ASTText;
import org.apache.velocity.runtime.parser.node.ASTTrue;
import org.apache.velocity.runtime.parser.node.ASTWord;
import org.apache.velocity.runtime.parser.node.ASTprocess;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.apache.velocity.util.StringUtils;

public class Parser implements ParserTreeConstants, ParserConstants {
   protected JJTParserState jjtree;
   private Hashtable directives;
   String currentTemplateName;
   VelocityCharStream velcharstream;
   private RuntimeServices rsvc;
   public ParserTokenManager token_source;
   public Token token;
   public Token jj_nt;
   private int jj_ntk;
   private Token jj_scanpos;
   private Token jj_lastpos;
   private int jj_la;
   public boolean lookingAhead;
   private boolean jj_semLA;
   private int jj_gen;
   private final int[] jj_la1;
   private final int[] jj_la1_0;
   private final int[] jj_la1_1;
   private final JJCalls[] jj_2_rtns;
   private boolean jj_rescan;
   private int jj_gc;
   private Vector jj_expentries;
   private int[] jj_expentry;
   private int jj_kind;
   private int[] jj_lasttokens;
   private int jj_endpos;

   public Parser(RuntimeServices rs) {
      this((CharStream)(new VelocityCharStream(new ByteArrayInputStream("\n".getBytes()), 1, 1)));
      this.velcharstream = new VelocityCharStream(new ByteArrayInputStream("\n".getBytes()), 1, 1);
      this.rsvc = rs;
   }

   public SimpleNode parse(Reader reader, String templateName) throws ParseException {
      SimpleNode sn = null;
      this.currentTemplateName = templateName;

      try {
         this.token_source.clearStateVars();
         this.velcharstream.ReInit((Reader)reader, 1, 1);
         this.ReInit((CharStream)this.velcharstream);
         sn = this.process();
      } catch (MacroParseException var8) {
         this.rsvc.error("Parser Error:  #macro() : " + templateName + " : " + StringUtils.stackTrace(var8));
         throw new ParseException(var8.getMessage());
      } catch (ParseException var9) {
         this.rsvc.error("Parser Exception: " + templateName + " : " + StringUtils.stackTrace(var9));
         throw new ParseException(var9.currentToken, var9.expectedTokenSequences, var9.tokenImage);
      } catch (TokenMgrError var10) {
         throw new ParseException("Lexical error: " + var10.toString());
      } catch (Exception var11) {
         this.rsvc.error("Parser Error: " + templateName + " : " + StringUtils.stackTrace(var11));
      }

      this.currentTemplateName = "";
      return sn;
   }

   public void setDirectives(Hashtable directives) {
      this.directives = directives;
   }

   public Directive getDirective(String directive) {
      return (Directive)this.directives.get(directive);
   }

   public boolean isDirective(String directive) {
      return this.directives.containsKey(directive);
   }

   private String escapedDirective(String strImage) {
      int iLast = strImage.lastIndexOf("\\");
      String strDirective = strImage.substring(iLast + 1);
      boolean bRecognizedDirective = false;
      if (this.isDirective(strDirective.substring(1))) {
         bRecognizedDirective = true;
      } else if (this.rsvc.isVelocimacro(strDirective.substring(1), this.currentTemplateName)) {
         bRecognizedDirective = true;
      } else if (strDirective.substring(1).equals("if") || strDirective.substring(1).equals("end") || strDirective.substring(1).equals("set") || strDirective.substring(1).equals("else") || strDirective.substring(1).equals("elseif") || strDirective.substring(1).equals("stop")) {
         bRecognizedDirective = true;
      }

      return bRecognizedDirective ? strImage.substring(0, iLast / 2) + strDirective : strImage;
   }

   public final SimpleNode process() throws ParseException {
      ASTprocess jjtn000 = new ASTprocess(this, 0);
      boolean jjtc000 = true;
      this.jjtree.openNodeScope(jjtn000);

      try {
         while(true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
               case 5:
               case 6:
               case 8:
               case 9:
               case 16:
               case 17:
               case 18:
               case 19:
               case 20:
               case 21:
               case 24:
               case 44:
               case 47:
               case 49:
               case 52:
               case 56:
               case 57:
               case 58:
               case 59:
                  this.Statement();
                  break;
               case 7:
               case 10:
               case 11:
               case 12:
               case 13:
               case 14:
               case 15:
               case 22:
               case 23:
               case 25:
               case 26:
               case 27:
               case 28:
               case 29:
               case 30:
               case 31:
               case 32:
               case 33:
               case 34:
               case 35:
               case 36:
               case 37:
               case 38:
               case 39:
               case 40:
               case 41:
               case 42:
               case 43:
               case 45:
               case 46:
               case 48:
               case 50:
               case 51:
               case 53:
               case 54:
               case 55:
               default:
                  this.jj_la1[0] = this.jj_gen;
                  this.jj_consume_token(0);
                  this.jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                  ASTprocess var3 = jjtn000;
                  return var3;
            }
         }
      } catch (Throwable var8) {
         if (jjtc000) {
            this.jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
         } else {
            this.jjtree.popNode();
         }

         if (var8 instanceof RuntimeException) {
            throw (RuntimeException)var8;
         } else if (var8 instanceof ParseException) {
            throw (ParseException)var8;
         } else {
            throw (Error)var8;
         }
      } finally {
         if (jjtc000) {
            this.jjtree.closeNodeScope(jjtn000, true);
         }

      }
   }

   public final void Statement() throws ParseException {
      switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 44:
            this.IfStatement();
            break;
         case 47:
            this.StopStatement();
            break;
         default:
            this.jj_la1[1] = this.jj_gen;
            if (this.jj_2_1(2)) {
               this.Reference();
            } else {
               switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                  case 5:
                  case 6:
                  case 17:
                  case 18:
                  case 24:
                  case 49:
                  case 57:
                  case 58:
                  case 59:
                     this.Text();
                     break;
                  case 7:
                  case 10:
                  case 11:
                  case 12:
                  case 13:
                  case 14:
                  case 15:
                  case 22:
                  case 23:
                  case 25:
                  case 26:
                  case 27:
                  case 28:
                  case 29:
                  case 30:
                  case 31:
                  case 32:
                  case 33:
                  case 34:
                  case 35:
                  case 36:
                  case 37:
                  case 38:
                  case 39:
                  case 40:
                  case 41:
                  case 42:
                  case 43:
                  case 44:
                  case 45:
                  case 46:
                  case 47:
                  case 48:
                  case 50:
                  case 51:
                  case 53:
                  case 54:
                  case 55:
                  case 56:
                  default:
                     this.jj_la1[2] = this.jj_gen;
                     this.jj_consume_token(-1);
                     throw new ParseException();
                  case 8:
                     this.EscapedDirective();
                     break;
                  case 9:
                     this.SetDirective();
                     break;
                  case 16:
                     this.Escape();
                     break;
                  case 19:
                  case 20:
                  case 21:
                     this.Comment();
                     break;
                  case 52:
                     this.Directive();
               }
            }
      }

   }

   public final void EscapedDirective() throws ParseException {
      ASTEscapedDirective jjtn000 = new ASTEscapedDirective(this, 2);
      boolean jjtc000 = true;
      this.jjtree.openNodeScope(jjtn000);

      try {
         Token t = null;
         t = this.jj_consume_token(8);
         this.jjtree.closeNodeScope(jjtn000, true);
         jjtc000 = false;
         t.image = this.escapedDirective(t.image);
      } finally {
         if (jjtc000) {
            this.jjtree.closeNodeScope(jjtn000, true);
         }

      }

   }

   public final void Escape() throws ParseException {
      ASTEscape jjtn000 = new ASTEscape(this, 3);
      boolean jjtc000 = true;
      this.jjtree.openNodeScope(jjtn000);

      try {
         Token t = null;
         int count = 0;
         boolean control = false;

         do {
            t = this.jj_consume_token(16);
            ++count;
         } while(this.jj_2_2(2));

         this.jjtree.closeNodeScope(jjtn000, true);
         jjtc000 = false;
         switch (t.next.kind) {
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
               control = true;
            default:
               if (this.isDirective(t.next.image.substring(1))) {
                  control = true;
               } else if (this.rsvc.isVelocimacro(t.next.image.substring(1), this.currentTemplateName)) {
                  control = true;
               }

               jjtn000.val = "";

               for(int i = 0; i < count; ++i) {
                  jjtn000.val = jjtn000.val + (control ? "\\" : "\\\\");
               }

         }
      } finally {
         if (jjtc000) {
            this.jjtree.closeNodeScope(jjtn000, true);
         }

      }
   }

   public final void Comment() throws ParseException {
      ASTComment jjtn000 = new ASTComment(this, 4);
      boolean jjtc000 = true;
      this.jjtree.openNodeScope(jjtn000);

      try {
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 19:
               this.jj_consume_token(19);
               break;
            case 20:
               this.jj_consume_token(20);
               break;
            case 21:
               this.jj_consume_token(21);
               break;
            default:
               this.jj_la1[3] = this.jj_gen;
               this.jj_consume_token(-1);
               throw new ParseException();
         }
      } finally {
         if (jjtc000) {
            this.jjtree.closeNodeScope(jjtn000, true);
         }

      }

   }

   public final void NumberLiteral() throws ParseException {
      ASTNumberLiteral jjtn000 = new ASTNumberLiteral(this, 5);
      boolean jjtc000 = true;
      this.jjtree.openNodeScope(jjtn000);

      try {
         this.jj_consume_token(49);
      } finally {
         if (jjtc000) {
            this.jjtree.closeNodeScope(jjtn000, true);
         }

      }

   }

   public final void StringLiteral() throws ParseException {
      ASTStringLiteral jjtn000 = new ASTStringLiteral(this, 6);
      boolean jjtc000 = true;
      this.jjtree.openNodeScope(jjtn000);

      try {
         this.jj_consume_token(24);
      } finally {
         if (jjtc000) {
            this.jjtree.closeNodeScope(jjtn000, true);
         }

      }

   }

   public final void Identifier() throws ParseException {
      ASTIdentifier jjtn000 = new ASTIdentifier(this, 7);
      boolean jjtc000 = true;
      this.jjtree.openNodeScope(jjtn000);

      try {
         this.jj_consume_token(56);
      } finally {
         if (jjtc000) {
            this.jjtree.closeNodeScope(jjtn000, true);
         }

      }

   }

   public final void Word() throws ParseException {
      ASTWord jjtn000 = new ASTWord(this, 8);
      boolean jjtc000 = true;
      this.jjtree.openNodeScope(jjtn000);

      try {
         this.jj_consume_token(52);
      } finally {
         if (jjtc000) {
            this.jjtree.closeNodeScope(jjtn000, true);
         }

      }

   }

   public final int DirectiveArg() throws ParseException {
      switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 24:
            this.StringLiteral();
            return 6;
         case 49:
            this.NumberLiteral();
            return 5;
         case 52:
            this.Word();
            return 8;
         case 56:
         case 58:
            this.Reference();
            return 14;
         default:
            this.jj_la1[4] = this.jj_gen;
            if (this.jj_2_3(Integer.MAX_VALUE)) {
               this.IntegerRange();
               return 12;
            } else {
               switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                  case 1:
                     this.ObjectArray();
                     return 11;
                  case 25:
                     this.True();
                     return 15;
                  case 26:
                     this.False();
                     return 16;
                  default:
                     this.jj_la1[5] = this.jj_gen;
                     this.jj_consume_token(-1);
                     throw new ParseException();
               }
            }
      }
   }

   public final SimpleNode Directive() throws ParseException {
      ASTDirective jjtn000 = new ASTDirective(this, 9);
      boolean jjtc000 = true;
      this.jjtree.openNodeScope(jjtn000);
      Token t = null;
      int argPos = false;
      boolean isVM = false;
      boolean doItNow = false;

      try {
         t = this.jj_consume_token(52);
         String directiveName = t.image.substring(1);
         Directive d = (Directive)this.directives.get(directiveName);
         if (directiveName.equals("macro")) {
            doItNow = true;
         }

         jjtn000.setDirectiveName(directiveName);
         int directiveType;
         ASTDirective var11;
         if (d == null) {
            isVM = this.rsvc.isVelocimacro(directiveName, this.currentTemplateName);
            if (!isVM) {
               this.token_source.stateStackPop();
               this.token_source.inDirective = false;
               var11 = jjtn000;
               return var11;
            }

            directiveType = 2;
         } else {
            directiveType = d.getType();
         }

         this.token_source.SwitchTo(0);
         int argPos = 0;
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 23:
               this.jj_consume_token(23);
               break;
            default:
               this.jj_la1[6] = this.jj_gen;
         }

         this.jj_consume_token(5);

         for(; this.jj_2_4(2); ++argPos) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
               case 23:
                  this.jj_consume_token(23);
                  break;
               default:
                  this.jj_la1[7] = this.jj_gen;
            }

            int argType = this.DirectiveArg();
            if (argType == 8) {
               if ((!doItNow || argPos != 0) && (!t.image.equals("#foreach") || argPos != 1)) {
                  throw new MacroParseException("Invalid arg #" + argPos + " in " + (isVM ? "VM " : "directive ") + t.image + " at line " + t.beginLine + ", column " + t.beginColumn + " in template " + this.currentTemplateName);
               }
            } else if (doItNow && argPos == 0) {
               throw new MacroParseException("Invalid first arg  in #macro() directive - must be a word token (no ' or \" surrounding) at line " + t.beginLine + ", column " + t.beginColumn + " in template " + this.currentTemplateName);
            }
         }

         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 23:
               this.jj_consume_token(23);
               break;
            default:
               this.jj_la1[8] = this.jj_gen;
         }

         this.jj_consume_token(6);
         if (directiveType == 2) {
            var11 = jjtn000;
            return var11;
         } else {
            ASTBlock jjtn001 = new ASTBlock(this, 10);
            boolean jjtc001 = true;
            this.jjtree.openNodeScope(jjtn001);

            try {
               label537:
               while(true) {
                  this.Statement();
                  switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                     case 5:
                     case 6:
                     case 8:
                     case 9:
                     case 16:
                     case 17:
                     case 18:
                     case 19:
                     case 20:
                     case 21:
                     case 24:
                     case 44:
                     case 47:
                     case 49:
                     case 52:
                     case 56:
                     case 57:
                     case 58:
                     case 59:
                        break;
                     case 7:
                     case 10:
                     case 11:
                     case 12:
                     case 13:
                     case 14:
                     case 15:
                     case 22:
                     case 23:
                     case 25:
                     case 26:
                     case 27:
                     case 28:
                     case 29:
                     case 30:
                     case 31:
                     case 32:
                     case 33:
                     case 34:
                     case 35:
                     case 36:
                     case 37:
                     case 38:
                     case 39:
                     case 40:
                     case 41:
                     case 42:
                     case 43:
                     case 45:
                     case 46:
                     case 48:
                     case 50:
                     case 51:
                     case 53:
                     case 54:
                     case 55:
                     default:
                        this.jj_la1[9] = this.jj_gen;
                        break label537;
                  }
               }
            } catch (Throwable var26) {
               if (jjtc001) {
                  this.jjtree.clearNodeScope(jjtn001);
                  jjtc001 = false;
               } else {
                  this.jjtree.popNode();
               }

               if (var26 instanceof RuntimeException) {
                  throw (RuntimeException)var26;
               }

               if (var26 instanceof ParseException) {
                  throw (ParseException)var26;
               }

               throw (Error)var26;
            } finally {
               if (jjtc001) {
                  this.jjtree.closeNodeScope(jjtn001, true);
               }

            }

            this.jj_consume_token(43);
            this.jjtree.closeNodeScope(jjtn000, true);
            jjtc000 = false;
            if (doItNow) {
               Macro.processAndRegister(this.rsvc, jjtn000, this.currentTemplateName);
            }

            ASTDirective var32 = jjtn000;
            return var32;
         }
      } catch (Throwable var28) {
         if (jjtc000) {
            this.jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
         } else {
            this.jjtree.popNode();
         }

         if (var28 instanceof RuntimeException) {
            throw (RuntimeException)var28;
         } else if (var28 instanceof ParseException) {
            throw (ParseException)var28;
         } else {
            throw (Error)var28;
         }
      } finally {
         if (jjtc000) {
            this.jjtree.closeNodeScope(jjtn000, true);
         }

      }
   }

   public final void ObjectArray() throws ParseException {
      ASTObjectArray jjtn000 = new ASTObjectArray(this, 11);
      boolean jjtc000 = true;
      this.jjtree.openNodeScope(jjtn000);

      try {
         this.jj_consume_token(1);
         label129:
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 1:
            case 23:
            case 24:
            case 25:
            case 26:
            case 49:
            case 56:
            case 58:
               this.Parameter();

               while(true) {
                  switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                     case 3:
                        this.jj_consume_token(3);
                        this.Parameter();
                        break;
                     default:
                        this.jj_la1[10] = this.jj_gen;
                        break label129;
                  }
               }
            default:
               this.jj_la1[11] = this.jj_gen;
         }

         this.jj_consume_token(2);
      } catch (Throwable var8) {
         if (jjtc000) {
            this.jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
         } else {
            this.jjtree.popNode();
         }

         if (var8 instanceof RuntimeException) {
            throw (RuntimeException)var8;
         }

         if (var8 instanceof ParseException) {
            throw (ParseException)var8;
         }

         throw (Error)var8;
      } finally {
         if (jjtc000) {
            this.jjtree.closeNodeScope(jjtn000, true);
         }

      }

   }

   public final void IntegerRange() throws ParseException {
      ASTIntegerRange jjtn000 = new ASTIntegerRange(this, 12);
      boolean jjtc000 = true;
      this.jjtree.openNodeScope(jjtn000);

      try {
         this.jj_consume_token(1);
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 23:
               this.jj_consume_token(23);
               break;
            default:
               this.jj_la1[12] = this.jj_gen;
         }

         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 49:
               this.NumberLiteral();
               break;
            case 56:
            case 58:
               this.Reference();
               break;
            default:
               this.jj_la1[13] = this.jj_gen;
               this.jj_consume_token(-1);
               throw new ParseException();
         }

         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 23:
               this.jj_consume_token(23);
               break;
            default:
               this.jj_la1[14] = this.jj_gen;
         }

         this.jj_consume_token(4);
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 23:
               this.jj_consume_token(23);
               break;
            default:
               this.jj_la1[15] = this.jj_gen;
         }

         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 49:
               this.NumberLiteral();
               break;
            case 56:
            case 58:
               this.Reference();
               break;
            default:
               this.jj_la1[16] = this.jj_gen;
               this.jj_consume_token(-1);
               throw new ParseException();
         }

         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 23:
               this.jj_consume_token(23);
               break;
            default:
               this.jj_la1[17] = this.jj_gen;
         }

         this.jj_consume_token(2);
      } catch (Throwable var8) {
         if (jjtc000) {
            this.jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
         } else {
            this.jjtree.popNode();
         }

         if (var8 instanceof RuntimeException) {
            throw (RuntimeException)var8;
         }

         if (var8 instanceof ParseException) {
            throw (ParseException)var8;
         }

         throw (Error)var8;
      } finally {
         if (jjtc000) {
            this.jjtree.closeNodeScope(jjtn000, true);
         }

      }

   }

   public final void Parameter() throws ParseException {
      switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 23:
            this.jj_consume_token(23);
            break;
         default:
            this.jj_la1[18] = this.jj_gen;
      }

      switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 24:
            this.StringLiteral();
            break;
         default:
            this.jj_la1[19] = this.jj_gen;
            if (this.jj_2_5(Integer.MAX_VALUE)) {
               this.IntegerRange();
            } else {
               switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                  case 1:
                     this.ObjectArray();
                     break;
                  case 25:
                     this.True();
                     break;
                  case 26:
                     this.False();
                     break;
                  case 49:
                     this.NumberLiteral();
                     break;
                  case 56:
                  case 58:
                     this.Reference();
                     break;
                  default:
                     this.jj_la1[20] = this.jj_gen;
                     this.jj_consume_token(-1);
                     throw new ParseException();
               }
            }
      }

      switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 23:
            this.jj_consume_token(23);
            break;
         default:
            this.jj_la1[21] = this.jj_gen;
      }

   }

   public final void Method() throws ParseException {
      ASTMethod jjtn000 = new ASTMethod(this, 13);
      boolean jjtc000 = true;
      this.jjtree.openNodeScope(jjtn000);

      try {
         this.Identifier();
         this.jj_consume_token(5);
         label129:
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 1:
            case 23:
            case 24:
            case 25:
            case 26:
            case 49:
            case 56:
            case 58:
               this.Parameter();

               while(true) {
                  switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                     case 3:
                        this.jj_consume_token(3);
                        this.Parameter();
                        break;
                     default:
                        this.jj_la1[22] = this.jj_gen;
                        break label129;
                  }
               }
            default:
               this.jj_la1[23] = this.jj_gen;
         }

         this.jj_consume_token(7);
      } catch (Throwable var8) {
         if (jjtc000) {
            this.jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
         } else {
            this.jjtree.popNode();
         }

         if (var8 instanceof RuntimeException) {
            throw (RuntimeException)var8;
         }

         if (var8 instanceof ParseException) {
            throw (ParseException)var8;
         }

         throw (Error)var8;
      } finally {
         if (jjtc000) {
            this.jjtree.closeNodeScope(jjtn000, true);
         }

      }

   }

   public final void Reference() throws ParseException {
      ASTReference jjtn000 = new ASTReference(this, 14);
      boolean jjtc000 = true;
      this.jjtree.openNodeScope(jjtn000);

      try {
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 56:
               this.jj_consume_token(56);

               while(this.jj_2_6(2)) {
                  this.jj_consume_token(57);
                  if (this.jj_2_7(3)) {
                     this.Method();
                  } else {
                     switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 56:
                           this.Identifier();
                           break;
                        default:
                           this.jj_la1[24] = this.jj_gen;
                           this.jj_consume_token(-1);
                           throw new ParseException();
                     }
                  }
               }

               return;
            case 58:
               this.jj_consume_token(58);
               this.jj_consume_token(56);

               while(this.jj_2_8(2)) {
                  this.jj_consume_token(57);
                  if (this.jj_2_9(3)) {
                     this.Method();
                  } else {
                     switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 56:
                           this.Identifier();
                           break;
                        default:
                           this.jj_la1[25] = this.jj_gen;
                           this.jj_consume_token(-1);
                           throw new ParseException();
                     }
                  }
               }

               this.jj_consume_token(59);
               return;
            default:
               this.jj_la1[26] = this.jj_gen;
               this.jj_consume_token(-1);
               throw new ParseException();
         }
      } catch (Throwable var8) {
         if (jjtc000) {
            this.jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
         } else {
            this.jjtree.popNode();
         }

         if (var8 instanceof RuntimeException) {
            throw (RuntimeException)var8;
         } else if (var8 instanceof ParseException) {
            throw (ParseException)var8;
         } else {
            throw (Error)var8;
         }
      } finally {
         if (jjtc000) {
            this.jjtree.closeNodeScope(jjtn000, true);
         }

      }
   }

   public final void True() throws ParseException {
      ASTTrue jjtn000 = new ASTTrue(this, 15);
      boolean jjtc000 = true;
      this.jjtree.openNodeScope(jjtn000);

      try {
         this.jj_consume_token(25);
      } finally {
         if (jjtc000) {
            this.jjtree.closeNodeScope(jjtn000, true);
         }

      }

   }

   public final void False() throws ParseException {
      ASTFalse jjtn000 = new ASTFalse(this, 16);
      boolean jjtc000 = true;
      this.jjtree.openNodeScope(jjtn000);

      try {
         this.jj_consume_token(26);
      } finally {
         if (jjtc000) {
            this.jjtree.closeNodeScope(jjtn000, true);
         }

      }

   }

   public final void Text() throws ParseException {
      ASTText jjtn000 = new ASTText(this, 17);
      boolean jjtc000 = true;
      this.jjtree.openNodeScope(jjtn000);

      try {
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 5:
               this.jj_consume_token(5);
               break;
            case 6:
               this.jj_consume_token(6);
               break;
            case 17:
               this.jj_consume_token(17);
               break;
            case 18:
               this.jj_consume_token(18);
               break;
            case 24:
               this.jj_consume_token(24);
               break;
            case 49:
               this.jj_consume_token(49);
               break;
            case 57:
               this.jj_consume_token(57);
               break;
            case 58:
               this.jj_consume_token(58);
               break;
            case 59:
               this.jj_consume_token(59);
               break;
            default:
               this.jj_la1[27] = this.jj_gen;
               this.jj_consume_token(-1);
               throw new ParseException();
         }
      } finally {
         if (jjtc000) {
            this.jjtree.closeNodeScope(jjtn000, true);
         }

      }

   }

   public final void IfStatement() throws ParseException {
      ASTIfStatement jjtn000 = new ASTIfStatement(this, 18);
      boolean jjtc000 = true;
      this.jjtree.openNodeScope(jjtn000);

      try {
         this.jj_consume_token(44);
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 23:
               this.jj_consume_token(23);
               break;
            default:
               this.jj_la1[28] = this.jj_gen;
         }

         this.jj_consume_token(5);
         this.Expression();
         this.jj_consume_token(6);
         ASTBlock jjtn001 = new ASTBlock(this, 10);
         boolean jjtc001 = true;
         this.jjtree.openNodeScope(jjtn001);

         try {
            label368:
            while(true) {
               this.Statement();
               switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                  case 5:
                  case 6:
                  case 8:
                  case 9:
                  case 16:
                  case 17:
                  case 18:
                  case 19:
                  case 20:
                  case 21:
                  case 24:
                  case 44:
                  case 47:
                  case 49:
                  case 52:
                  case 56:
                  case 57:
                  case 58:
                  case 59:
                     break;
                  case 7:
                  case 10:
                  case 11:
                  case 12:
                  case 13:
                  case 14:
                  case 15:
                  case 22:
                  case 23:
                  case 25:
                  case 26:
                  case 27:
                  case 28:
                  case 29:
                  case 30:
                  case 31:
                  case 32:
                  case 33:
                  case 34:
                  case 35:
                  case 36:
                  case 37:
                  case 38:
                  case 39:
                  case 40:
                  case 41:
                  case 42:
                  case 43:
                  case 45:
                  case 46:
                  case 48:
                  case 50:
                  case 51:
                  case 53:
                  case 54:
                  case 55:
                  default:
                     this.jj_la1[29] = this.jj_gen;
                     break label368;
               }
            }
         } catch (Throwable var18) {
            if (jjtc001) {
               this.jjtree.clearNodeScope(jjtn001);
               jjtc001 = false;
            } else {
               this.jjtree.popNode();
            }

            if (var18 instanceof RuntimeException) {
               throw (RuntimeException)var18;
            }

            if (var18 instanceof ParseException) {
               throw (ParseException)var18;
            }

            throw (Error)var18;
         } finally {
            if (jjtc001) {
               this.jjtree.closeNodeScope(jjtn001, true);
            }

         }

         label379:
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 45:
               while(true) {
                  this.ElseIfStatement();
                  switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                     case 45:
                        break;
                     default:
                        this.jj_la1[30] = this.jj_gen;
                        break label379;
                  }
               }
            default:
               this.jj_la1[31] = this.jj_gen;
         }

         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 46:
               this.ElseStatement();
               break;
            default:
               this.jj_la1[32] = this.jj_gen;
         }

         this.jj_consume_token(43);
      } catch (Throwable var20) {
         if (jjtc000) {
            this.jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
         } else {
            this.jjtree.popNode();
         }

         if (var20 instanceof RuntimeException) {
            throw (RuntimeException)var20;
         }

         if (var20 instanceof ParseException) {
            throw (ParseException)var20;
         }

         throw (Error)var20;
      } finally {
         if (jjtc000) {
            this.jjtree.closeNodeScope(jjtn000, true);
         }

      }

   }

   public final void ElseStatement() throws ParseException {
      ASTElseStatement jjtn000 = new ASTElseStatement(this, 19);
      boolean jjtc000 = true;
      this.jjtree.openNodeScope(jjtn000);

      try {
         this.jj_consume_token(46);
         ASTBlock jjtn001 = new ASTBlock(this, 10);
         boolean jjtc001 = true;
         this.jjtree.openNodeScope(jjtn001);

         try {
            while(true) {
               this.Statement();
               switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                  case 5:
                  case 6:
                  case 8:
                  case 9:
                  case 16:
                  case 17:
                  case 18:
                  case 19:
                  case 20:
                  case 21:
                  case 24:
                  case 44:
                  case 47:
                  case 49:
                  case 52:
                  case 56:
                  case 57:
                  case 58:
                  case 59:
                     break;
                  case 7:
                  case 10:
                  case 11:
                  case 12:
                  case 13:
                  case 14:
                  case 15:
                  case 22:
                  case 23:
                  case 25:
                  case 26:
                  case 27:
                  case 28:
                  case 29:
                  case 30:
                  case 31:
                  case 32:
                  case 33:
                  case 34:
                  case 35:
                  case 36:
                  case 37:
                  case 38:
                  case 39:
                  case 40:
                  case 41:
                  case 42:
                  case 43:
                  case 45:
                  case 46:
                  case 48:
                  case 50:
                  case 51:
                  case 53:
                  case 54:
                  case 55:
                  default:
                     this.jj_la1[33] = this.jj_gen;
                     return;
               }
            }
         } catch (Throwable var18) {
            if (jjtc001) {
               this.jjtree.clearNodeScope(jjtn001);
               jjtc001 = false;
            } else {
               this.jjtree.popNode();
            }

            if (var18 instanceof RuntimeException) {
               throw (RuntimeException)var18;
            } else if (var18 instanceof ParseException) {
               throw (ParseException)var18;
            } else {
               throw (Error)var18;
            }
         } finally {
            if (jjtc001) {
               this.jjtree.closeNodeScope(jjtn001, true);
            }

         }
      } catch (Throwable var20) {
         if (jjtc000) {
            this.jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
         } else {
            this.jjtree.popNode();
         }

         if (var20 instanceof RuntimeException) {
            throw (RuntimeException)var20;
         } else if (var20 instanceof ParseException) {
            throw (ParseException)var20;
         } else {
            throw (Error)var20;
         }
      } finally {
         if (jjtc000) {
            this.jjtree.closeNodeScope(jjtn000, true);
         }

      }
   }

   public final void ElseIfStatement() throws ParseException {
      ASTElseIfStatement jjtn000 = new ASTElseIfStatement(this, 20);
      boolean jjtc000 = true;
      this.jjtree.openNodeScope(jjtn000);

      try {
         this.jj_consume_token(45);
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 23:
               this.jj_consume_token(23);
               break;
            default:
               this.jj_la1[34] = this.jj_gen;
         }

         this.jj_consume_token(5);
         this.Expression();
         this.jj_consume_token(6);
         ASTBlock jjtn001 = new ASTBlock(this, 10);
         boolean jjtc001 = true;
         this.jjtree.openNodeScope(jjtn001);

         try {
            while(true) {
               this.Statement();
               switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                  case 5:
                  case 6:
                  case 8:
                  case 9:
                  case 16:
                  case 17:
                  case 18:
                  case 19:
                  case 20:
                  case 21:
                  case 24:
                  case 44:
                  case 47:
                  case 49:
                  case 52:
                  case 56:
                  case 57:
                  case 58:
                  case 59:
                     break;
                  case 7:
                  case 10:
                  case 11:
                  case 12:
                  case 13:
                  case 14:
                  case 15:
                  case 22:
                  case 23:
                  case 25:
                  case 26:
                  case 27:
                  case 28:
                  case 29:
                  case 30:
                  case 31:
                  case 32:
                  case 33:
                  case 34:
                  case 35:
                  case 36:
                  case 37:
                  case 38:
                  case 39:
                  case 40:
                  case 41:
                  case 42:
                  case 43:
                  case 45:
                  case 46:
                  case 48:
                  case 50:
                  case 51:
                  case 53:
                  case 54:
                  case 55:
                  default:
                     this.jj_la1[35] = this.jj_gen;
                     return;
               }
            }
         } catch (Throwable var18) {
            if (jjtc001) {
               this.jjtree.clearNodeScope(jjtn001);
               jjtc001 = false;
            } else {
               this.jjtree.popNode();
            }

            if (var18 instanceof RuntimeException) {
               throw (RuntimeException)var18;
            } else if (var18 instanceof ParseException) {
               throw (ParseException)var18;
            } else {
               throw (Error)var18;
            }
         } finally {
            if (jjtc001) {
               this.jjtree.closeNodeScope(jjtn001, true);
            }

         }
      } catch (Throwable var20) {
         if (jjtc000) {
            this.jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
         } else {
            this.jjtree.popNode();
         }

         if (var20 instanceof RuntimeException) {
            throw (RuntimeException)var20;
         } else if (var20 instanceof ParseException) {
            throw (ParseException)var20;
         } else {
            throw (Error)var20;
         }
      } finally {
         if (jjtc000) {
            this.jjtree.closeNodeScope(jjtn000, true);
         }

      }
   }

   public final void SetDirective() throws ParseException {
      ASTSetDirective jjtn000 = new ASTSetDirective(this, 21);
      boolean jjtc000 = true;
      this.jjtree.openNodeScope(jjtn000);

      try {
         this.jj_consume_token(9);
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 23:
               this.jj_consume_token(23);
               break;
            default:
               this.jj_la1[36] = this.jj_gen;
         }

         this.Reference();
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 23:
               this.jj_consume_token(23);
               break;
            default:
               this.jj_la1[37] = this.jj_gen;
         }

         this.jj_consume_token(42);
         this.Expression();
         this.jj_consume_token(6);
         this.token_source.inSet = false;
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 27:
               this.jj_consume_token(27);
               break;
            default:
               this.jj_la1[38] = this.jj_gen;
         }
      } catch (Throwable var8) {
         if (jjtc000) {
            this.jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
         } else {
            this.jjtree.popNode();
         }

         if (var8 instanceof RuntimeException) {
            throw (RuntimeException)var8;
         }

         if (var8 instanceof ParseException) {
            throw (ParseException)var8;
         }

         throw (Error)var8;
      } finally {
         if (jjtc000) {
            this.jjtree.closeNodeScope(jjtn000, true);
         }

      }

   }

   public final void StopStatement() throws ParseException {
      this.jj_consume_token(47);
   }

   public final void Expression() throws ParseException {
      ASTExpression jjtn000 = new ASTExpression(this, 22);
      boolean jjtc000 = true;
      this.jjtree.openNodeScope(jjtn000);

      try {
         this.ConditionalOrExpression();
      } catch (Throwable var8) {
         if (jjtc000) {
            this.jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
         } else {
            this.jjtree.popNode();
         }

         if (var8 instanceof RuntimeException) {
            throw (RuntimeException)var8;
         }

         if (var8 instanceof ParseException) {
            throw (ParseException)var8;
         }

         throw (Error)var8;
      } finally {
         if (jjtc000) {
            this.jjtree.closeNodeScope(jjtn000, true);
         }

      }

   }

   public final void Assignment() throws ParseException {
      ASTAssignment jjtn000 = new ASTAssignment(this, 23);
      boolean jjtc000 = true;
      this.jjtree.openNodeScope(jjtn000);

      try {
         this.PrimaryExpression();
         this.jj_consume_token(42);
         this.Expression();
      } catch (Throwable var8) {
         if (jjtc000) {
            this.jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
         } else {
            this.jjtree.popNode();
         }

         if (var8 instanceof RuntimeException) {
            throw (RuntimeException)var8;
         }

         if (var8 instanceof ParseException) {
            throw (ParseException)var8;
         }

         throw (Error)var8;
      } finally {
         if (jjtc000) {
            this.jjtree.closeNodeScope(jjtn000, 2);
         }

      }

   }

   public final void ConditionalOrExpression() throws ParseException {
      this.ConditionalAndExpression();

      while(true) {
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 34:
               this.jj_consume_token(34);
               ASTOrNode jjtn001 = new ASTOrNode(this, 24);
               boolean jjtc001 = true;
               this.jjtree.openNodeScope(jjtn001);

               try {
                  this.ConditionalAndExpression();
                  break;
               } catch (Throwable var8) {
                  if (jjtc001) {
                     this.jjtree.clearNodeScope(jjtn001);
                     jjtc001 = false;
                  } else {
                     this.jjtree.popNode();
                  }

                  if (var8 instanceof RuntimeException) {
                     throw (RuntimeException)var8;
                  }

                  if (var8 instanceof ParseException) {
                     throw (ParseException)var8;
                  }

                  throw (Error)var8;
               } finally {
                  if (jjtc001) {
                     this.jjtree.closeNodeScope(jjtn001, 2);
                  }

               }
            default:
               this.jj_la1[39] = this.jj_gen;
               return;
         }
      }
   }

   public final void ConditionalAndExpression() throws ParseException {
      this.EqualityExpression();

      while(true) {
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 33:
               this.jj_consume_token(33);
               ASTAndNode jjtn001 = new ASTAndNode(this, 25);
               boolean jjtc001 = true;
               this.jjtree.openNodeScope(jjtn001);

               try {
                  this.EqualityExpression();
                  break;
               } catch (Throwable var8) {
                  if (jjtc001) {
                     this.jjtree.clearNodeScope(jjtn001);
                     jjtc001 = false;
                  } else {
                     this.jjtree.popNode();
                  }

                  if (var8 instanceof RuntimeException) {
                     throw (RuntimeException)var8;
                  }

                  if (var8 instanceof ParseException) {
                     throw (ParseException)var8;
                  }

                  throw (Error)var8;
               } finally {
                  if (jjtc001) {
                     this.jjtree.closeNodeScope(jjtn001, 2);
                  }

               }
            default:
               this.jj_la1[40] = this.jj_gen;
               return;
         }
      }
   }

   public final void EqualityExpression() throws ParseException {
      this.RelationalExpression();

      while(true) {
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 39:
            case 40:
               switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                  case 39:
                     this.jj_consume_token(39);
                     ASTEQNode jjtn001 = new ASTEQNode(this, 26);
                     boolean jjtc001 = true;
                     this.jjtree.openNodeScope(jjtn001);

                     try {
                        this.RelationalExpression();
                        continue;
                     } catch (Throwable var18) {
                        if (jjtc001) {
                           this.jjtree.clearNodeScope(jjtn001);
                           jjtc001 = false;
                        } else {
                           this.jjtree.popNode();
                        }

                        if (var18 instanceof RuntimeException) {
                           throw (RuntimeException)var18;
                        }

                        if (var18 instanceof ParseException) {
                           throw (ParseException)var18;
                        }

                        throw (Error)var18;
                     } finally {
                        if (jjtc001) {
                           this.jjtree.closeNodeScope(jjtn001, 2);
                        }

                     }
                  case 40:
                     this.jj_consume_token(40);
                     ASTNENode jjtn002 = new ASTNENode(this, 27);
                     boolean jjtc002 = true;
                     this.jjtree.openNodeScope(jjtn002);

                     try {
                        this.RelationalExpression();
                        continue;
                     } catch (Throwable var16) {
                        if (jjtc002) {
                           this.jjtree.clearNodeScope(jjtn002);
                           jjtc002 = false;
                        } else {
                           this.jjtree.popNode();
                        }

                        if (var16 instanceof RuntimeException) {
                           throw (RuntimeException)var16;
                        }

                        if (var16 instanceof ParseException) {
                           throw (ParseException)var16;
                        }

                        throw (Error)var16;
                     } finally {
                        if (jjtc002) {
                           this.jjtree.closeNodeScope(jjtn002, 2);
                        }

                     }
                  default:
                     this.jj_la1[42] = this.jj_gen;
                     this.jj_consume_token(-1);
                     throw new ParseException();
               }
            default:
               this.jj_la1[41] = this.jj_gen;
               return;
         }
      }
   }

   public final void RelationalExpression() throws ParseException {
      this.AdditiveExpression();

      while(true) {
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 35:
            case 36:
            case 37:
            case 38:
               switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                  case 35:
                     this.jj_consume_token(35);
                     ASTLTNode jjtn001 = new ASTLTNode(this, 28);
                     boolean jjtc001 = true;
                     this.jjtree.openNodeScope(jjtn001);

                     try {
                        this.AdditiveExpression();
                        continue;
                     } catch (Throwable var48) {
                        if (jjtc001) {
                           this.jjtree.clearNodeScope(jjtn001);
                           jjtc001 = false;
                        } else {
                           this.jjtree.popNode();
                        }

                        if (var48 instanceof RuntimeException) {
                           throw (RuntimeException)var48;
                        }

                        if (var48 instanceof ParseException) {
                           throw (ParseException)var48;
                        }

                        throw (Error)var48;
                     } finally {
                        if (jjtc001) {
                           this.jjtree.closeNodeScope(jjtn001, 2);
                        }

                     }
                  case 36:
                     this.jj_consume_token(36);
                     ASTLENode jjtn003 = new ASTLENode(this, 30);
                     boolean jjtc003 = true;
                     this.jjtree.openNodeScope(jjtn003);

                     try {
                        this.AdditiveExpression();
                        continue;
                     } catch (Throwable var44) {
                        if (jjtc003) {
                           this.jjtree.clearNodeScope(jjtn003);
                           jjtc003 = false;
                        } else {
                           this.jjtree.popNode();
                        }

                        if (var44 instanceof RuntimeException) {
                           throw (RuntimeException)var44;
                        }

                        if (var44 instanceof ParseException) {
                           throw (ParseException)var44;
                        }

                        throw (Error)var44;
                     } finally {
                        if (jjtc003) {
                           this.jjtree.closeNodeScope(jjtn003, 2);
                        }

                     }
                  case 37:
                     this.jj_consume_token(37);
                     ASTGTNode jjtn002 = new ASTGTNode(this, 29);
                     boolean jjtc002 = true;
                     this.jjtree.openNodeScope(jjtn002);

                     try {
                        this.AdditiveExpression();
                        continue;
                     } catch (Throwable var50) {
                        if (jjtc002) {
                           this.jjtree.clearNodeScope(jjtn002);
                           jjtc002 = false;
                        } else {
                           this.jjtree.popNode();
                        }

                        if (var50 instanceof RuntimeException) {
                           throw (RuntimeException)var50;
                        }

                        if (var50 instanceof ParseException) {
                           throw (ParseException)var50;
                        }

                        throw (Error)var50;
                     } finally {
                        if (jjtc002) {
                           this.jjtree.closeNodeScope(jjtn002, 2);
                        }

                     }
                  case 38:
                     this.jj_consume_token(38);
                     ASTGENode jjtn004 = new ASTGENode(this, 31);
                     boolean jjtc004 = true;
                     this.jjtree.openNodeScope(jjtn004);

                     try {
                        this.AdditiveExpression();
                        continue;
                     } catch (Throwable var46) {
                        if (jjtc004) {
                           this.jjtree.clearNodeScope(jjtn004);
                           jjtc004 = false;
                        } else {
                           this.jjtree.popNode();
                        }

                        if (var46 instanceof RuntimeException) {
                           throw (RuntimeException)var46;
                        }

                        if (var46 instanceof ParseException) {
                           throw (ParseException)var46;
                        }

                        throw (Error)var46;
                     } finally {
                        if (jjtc004) {
                           this.jjtree.closeNodeScope(jjtn004, 2);
                        }

                     }
                  default:
                     this.jj_la1[44] = this.jj_gen;
                     this.jj_consume_token(-1);
                     throw new ParseException();
               }
            default:
               this.jj_la1[43] = this.jj_gen;
               return;
         }
      }
   }

   public final void AdditiveExpression() throws ParseException {
      this.MultiplicativeExpression();

      while(true) {
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 28:
            case 29:
               switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                  case 28:
                     this.jj_consume_token(28);
                     ASTSubtractNode jjtn002 = new ASTSubtractNode(this, 33);
                     boolean jjtc002 = true;
                     this.jjtree.openNodeScope(jjtn002);

                     try {
                        this.MultiplicativeExpression();
                        continue;
                     } catch (Throwable var18) {
                        if (jjtc002) {
                           this.jjtree.clearNodeScope(jjtn002);
                           jjtc002 = false;
                        } else {
                           this.jjtree.popNode();
                        }

                        if (var18 instanceof RuntimeException) {
                           throw (RuntimeException)var18;
                        }

                        if (var18 instanceof ParseException) {
                           throw (ParseException)var18;
                        }

                        throw (Error)var18;
                     } finally {
                        if (jjtc002) {
                           this.jjtree.closeNodeScope(jjtn002, 2);
                        }

                     }
                  case 29:
                     this.jj_consume_token(29);
                     ASTAddNode jjtn001 = new ASTAddNode(this, 32);
                     boolean jjtc001 = true;
                     this.jjtree.openNodeScope(jjtn001);

                     try {
                        this.MultiplicativeExpression();
                        continue;
                     } catch (Throwable var16) {
                        if (jjtc001) {
                           this.jjtree.clearNodeScope(jjtn001);
                           jjtc001 = false;
                        } else {
                           this.jjtree.popNode();
                        }

                        if (var16 instanceof RuntimeException) {
                           throw (RuntimeException)var16;
                        }

                        if (var16 instanceof ParseException) {
                           throw (ParseException)var16;
                        }

                        throw (Error)var16;
                     } finally {
                        if (jjtc001) {
                           this.jjtree.closeNodeScope(jjtn001, 2);
                        }

                     }
                  default:
                     this.jj_la1[46] = this.jj_gen;
                     this.jj_consume_token(-1);
                     throw new ParseException();
               }
            default:
               this.jj_la1[45] = this.jj_gen;
               return;
         }
      }
   }

   public final void MultiplicativeExpression() throws ParseException {
      this.UnaryExpression();

      while(true) {
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 30:
            case 31:
            case 32:
               switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                  case 30:
                     this.jj_consume_token(30);
                     ASTMulNode jjtn001 = new ASTMulNode(this, 34);
                     boolean jjtc001 = true;
                     this.jjtree.openNodeScope(jjtn001);

                     try {
                        this.UnaryExpression();
                        continue;
                     } catch (Throwable var28) {
                        if (jjtc001) {
                           this.jjtree.clearNodeScope(jjtn001);
                           jjtc001 = false;
                        } else {
                           this.jjtree.popNode();
                        }

                        if (var28 instanceof RuntimeException) {
                           throw (RuntimeException)var28;
                        }

                        if (var28 instanceof ParseException) {
                           throw (ParseException)var28;
                        }

                        throw (Error)var28;
                     } finally {
                        if (jjtc001) {
                           this.jjtree.closeNodeScope(jjtn001, 2);
                        }

                     }
                  case 31:
                     this.jj_consume_token(31);
                     ASTDivNode jjtn002 = new ASTDivNode(this, 35);
                     boolean jjtc002 = true;
                     this.jjtree.openNodeScope(jjtn002);

                     try {
                        this.UnaryExpression();
                        continue;
                     } catch (Throwable var30) {
                        if (jjtc002) {
                           this.jjtree.clearNodeScope(jjtn002);
                           jjtc002 = false;
                        } else {
                           this.jjtree.popNode();
                        }

                        if (var30 instanceof RuntimeException) {
                           throw (RuntimeException)var30;
                        }

                        if (var30 instanceof ParseException) {
                           throw (ParseException)var30;
                        }

                        throw (Error)var30;
                     } finally {
                        if (jjtc002) {
                           this.jjtree.closeNodeScope(jjtn002, 2);
                        }

                     }
                  case 32:
                     this.jj_consume_token(32);
                     ASTModNode jjtn003 = new ASTModNode(this, 36);
                     boolean jjtc003 = true;
                     this.jjtree.openNodeScope(jjtn003);

                     try {
                        this.UnaryExpression();
                        continue;
                     } catch (Throwable var32) {
                        if (jjtc003) {
                           this.jjtree.clearNodeScope(jjtn003);
                           jjtc003 = false;
                        } else {
                           this.jjtree.popNode();
                        }

                        if (var32 instanceof RuntimeException) {
                           throw (RuntimeException)var32;
                        }

                        if (var32 instanceof ParseException) {
                           throw (ParseException)var32;
                        }

                        throw (Error)var32;
                     } finally {
                        if (jjtc003) {
                           this.jjtree.closeNodeScope(jjtn003, 2);
                        }

                     }
                  default:
                     this.jj_la1[48] = this.jj_gen;
                     this.jj_consume_token(-1);
                     throw new ParseException();
               }
            default:
               this.jj_la1[47] = this.jj_gen;
               return;
         }
      }
   }

   public final void UnaryExpression() throws ParseException {
      if (this.jj_2_10(2)) {
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 23:
               this.jj_consume_token(23);
               break;
            default:
               this.jj_la1[49] = this.jj_gen;
         }

         this.jj_consume_token(41);
         ASTNotNode jjtn001 = new ASTNotNode(this, 37);
         boolean jjtc001 = true;
         this.jjtree.openNodeScope(jjtn001);

         try {
            this.UnaryExpression();
         } catch (Throwable var8) {
            if (jjtc001) {
               this.jjtree.clearNodeScope(jjtn001);
               jjtc001 = false;
            } else {
               this.jjtree.popNode();
            }

            if (var8 instanceof RuntimeException) {
               throw (RuntimeException)var8;
            }

            if (var8 instanceof ParseException) {
               throw (ParseException)var8;
            }

            throw (Error)var8;
         } finally {
            if (jjtc001) {
               this.jjtree.closeNodeScope(jjtn001, 1);
            }

         }
      } else {
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 1:
            case 5:
            case 23:
            case 24:
            case 25:
            case 26:
            case 49:
            case 56:
            case 58:
               this.PrimaryExpression();
               break;
            default:
               this.jj_la1[50] = this.jj_gen;
               this.jj_consume_token(-1);
               throw new ParseException();
         }
      }

   }

   public final void PrimaryExpression() throws ParseException {
      switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 23:
            this.jj_consume_token(23);
            break;
         default:
            this.jj_la1[51] = this.jj_gen;
      }

      switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 24:
            this.StringLiteral();
            break;
         case 49:
            this.NumberLiteral();
            break;
         case 56:
         case 58:
            this.Reference();
            break;
         default:
            this.jj_la1[52] = this.jj_gen;
            if (this.jj_2_11(Integer.MAX_VALUE)) {
               this.IntegerRange();
            } else {
               switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                  case 1:
                     this.ObjectArray();
                     break;
                  case 5:
                     this.jj_consume_token(5);
                     this.Expression();
                     this.jj_consume_token(6);
                     break;
                  case 25:
                     this.True();
                     break;
                  case 26:
                     this.False();
                     break;
                  default:
                     this.jj_la1[53] = this.jj_gen;
                     this.jj_consume_token(-1);
                     throw new ParseException();
               }
            }
      }

      switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 23:
            this.jj_consume_token(23);
            break;
         default:
            this.jj_la1[54] = this.jj_gen;
      }

   }

   private final boolean jj_2_1(int xla) {
      this.jj_la = xla;
      this.jj_lastpos = this.jj_scanpos = this.token;
      boolean retval = !this.jj_3_1();
      this.jj_save(0, xla);
      return retval;
   }

   private final boolean jj_2_2(int xla) {
      this.jj_la = xla;
      this.jj_lastpos = this.jj_scanpos = this.token;
      boolean retval = !this.jj_3_2();
      this.jj_save(1, xla);
      return retval;
   }

   private final boolean jj_2_3(int xla) {
      this.jj_la = xla;
      this.jj_lastpos = this.jj_scanpos = this.token;
      boolean retval = !this.jj_3_3();
      this.jj_save(2, xla);
      return retval;
   }

   private final boolean jj_2_4(int xla) {
      this.jj_la = xla;
      this.jj_lastpos = this.jj_scanpos = this.token;
      boolean retval = !this.jj_3_4();
      this.jj_save(3, xla);
      return retval;
   }

   private final boolean jj_2_5(int xla) {
      this.jj_la = xla;
      this.jj_lastpos = this.jj_scanpos = this.token;
      boolean retval = !this.jj_3_5();
      this.jj_save(4, xla);
      return retval;
   }

   private final boolean jj_2_6(int xla) {
      this.jj_la = xla;
      this.jj_lastpos = this.jj_scanpos = this.token;
      boolean retval = !this.jj_3_6();
      this.jj_save(5, xla);
      return retval;
   }

   private final boolean jj_2_7(int xla) {
      this.jj_la = xla;
      this.jj_lastpos = this.jj_scanpos = this.token;
      boolean retval = !this.jj_3_7();
      this.jj_save(6, xla);
      return retval;
   }

   private final boolean jj_2_8(int xla) {
      this.jj_la = xla;
      this.jj_lastpos = this.jj_scanpos = this.token;
      boolean retval = !this.jj_3_8();
      this.jj_save(7, xla);
      return retval;
   }

   private final boolean jj_2_9(int xla) {
      this.jj_la = xla;
      this.jj_lastpos = this.jj_scanpos = this.token;
      boolean retval = !this.jj_3_9();
      this.jj_save(8, xla);
      return retval;
   }

   private final boolean jj_2_10(int xla) {
      this.jj_la = xla;
      this.jj_lastpos = this.jj_scanpos = this.token;
      boolean retval = !this.jj_3_10();
      this.jj_save(9, xla);
      return retval;
   }

   private final boolean jj_2_11(int xla) {
      this.jj_la = xla;
      this.jj_lastpos = this.jj_scanpos = this.token;
      boolean retval = !this.jj_3_11();
      this.jj_save(10, xla);
      return retval;
   }

   private final boolean jj_3R_54() {
      if (this.jj_scan_token(24)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_24() {
      if (this.jj_scan_token(23)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_41() {
      if (this.jj_scan_token(49)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3_4() {
      Token xsp = this.jj_scanpos;
      if (this.jj_3R_24()) {
         this.jj_scanpos = xsp;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      }

      if (this.jj_3R_25()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_38() {
      if (this.jj_scan_token(23)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_29() {
      if (this.jj_scan_token(23)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_32() {
      if (this.jj_3R_50()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_30() {
      if (this.jj_3R_50()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_37() {
      if (this.jj_3R_41()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3_2() {
      if (this.jj_scan_token(16)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_28() {
      if (this.jj_3R_41()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_36() {
      if (this.jj_3R_19()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3_9() {
      if (this.jj_3R_31()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_82() {
      if (this.jj_scan_token(3)) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else if (this.jj_3R_59()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_23() {
      if (this.jj_scan_token(23)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3_7() {
      if (this.jj_3R_31()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_27() {
      if (this.jj_3R_19()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_58() {
      if (this.jj_scan_token(26)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_35() {
      if (this.jj_scan_token(23)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_57() {
      if (this.jj_scan_token(25)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3_8() {
      if (this.jj_scan_token(57)) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else {
         Token xsp = this.jj_scanpos;
         if (this.jj_3_9()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_32()) {
               return true;
            }

            if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
               return false;
            }
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }

         return false;
      }
   }

   private final boolean jj_3R_51() {
      if (this.jj_3R_59()) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else {
         do {
            Token xsp = this.jj_scanpos;
            if (this.jj_3R_82()) {
               this.jj_scanpos = xsp;
               return false;
            }
         } while(this.jj_la != 0 || this.jj_scanpos != this.jj_lastpos);

         return false;
      }
   }

   private final boolean jj_3_6() {
      if (this.jj_scan_token(57)) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else {
         Token xsp = this.jj_scanpos;
         if (this.jj_3_7()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_30()) {
               return true;
            }

            if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
               return false;
            }
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }

         return false;
      }
   }

   private final boolean jj_3R_40() {
      if (this.jj_scan_token(58)) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else if (this.jj_scan_token(56)) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else {
         do {
            Token xsp = this.jj_scanpos;
            if (this.jj_3_8()) {
               this.jj_scanpos = xsp;
               if (this.jj_scan_token(59)) {
                  return true;
               }

               if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
                  return false;
               }

               return false;
            }
         } while(this.jj_la != 0 || this.jj_scanpos != this.jj_lastpos);

         return false;
      }
   }

   private final boolean jj_3R_22() {
      if (this.jj_3R_41()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3_11() {
      if (this.jj_scan_token(1)) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else {
         Token xsp = this.jj_scanpos;
         if (this.jj_3R_35()) {
            this.jj_scanpos = xsp;
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }

         xsp = this.jj_scanpos;
         if (this.jj_3R_36()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_37()) {
               return true;
            }

            if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
               return false;
            }
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }

         xsp = this.jj_scanpos;
         if (this.jj_3R_38()) {
            this.jj_scanpos = xsp;
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }

         if (this.jj_scan_token(4)) {
            return true;
         } else {
            return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
         }
      }
   }

   private final boolean jj_3R_26() {
      if (this.jj_scan_token(23)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_39() {
      if (this.jj_scan_token(56)) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else {
         do {
            Token xsp = this.jj_scanpos;
            if (this.jj_3_6()) {
               this.jj_scanpos = xsp;
               return false;
            }
         } while(this.jj_la != 0 || this.jj_scanpos != this.jj_lastpos);

         return false;
      }
   }

   private final boolean jj_3_1() {
      if (this.jj_3R_19()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_81() {
      if (this.jj_scan_token(5)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_19() {
      Token xsp = this.jj_scanpos;
      if (this.jj_3R_39()) {
         this.jj_scanpos = xsp;
         if (this.jj_3R_40()) {
            return true;
         }

         if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      }

      return false;
   }

   private final boolean jj_3R_80() {
      if (this.jj_3R_58()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_33() {
      if (this.jj_scan_token(23)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_79() {
      if (this.jj_3R_57()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_78() {
      if (this.jj_3R_56()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_77() {
      if (this.jj_3R_55()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_76() {
      if (this.jj_3R_19()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_21() {
      if (this.jj_3R_19()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3_5() {
      if (this.jj_scan_token(1)) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else {
         Token xsp = this.jj_scanpos;
         if (this.jj_3R_26()) {
            this.jj_scanpos = xsp;
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }

         xsp = this.jj_scanpos;
         if (this.jj_3R_27()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_28()) {
               return true;
            }

            if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
               return false;
            }
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }

         xsp = this.jj_scanpos;
         if (this.jj_3R_29()) {
            this.jj_scanpos = xsp;
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }

         if (this.jj_scan_token(4)) {
            return true;
         } else {
            return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
         }
      }
   }

   private final boolean jj_3R_75() {
      if (this.jj_3R_41()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_31() {
      if (this.jj_3R_50()) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else if (this.jj_scan_token(5)) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else {
         Token xsp = this.jj_scanpos;
         if (this.jj_3R_51()) {
            this.jj_scanpos = xsp;
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }

         if (this.jj_scan_token(7)) {
            return true;
         } else {
            return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
         }
      }
   }

   private final boolean jj_3R_74() {
      if (this.jj_3R_54()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_73() {
      if (this.jj_scan_token(23)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_60() {
      Token xsp = this.jj_scanpos;
      if (this.jj_3R_73()) {
         this.jj_scanpos = xsp;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      }

      xsp = this.jj_scanpos;
      if (this.jj_3R_74()) {
         this.jj_scanpos = xsp;
         if (this.jj_3R_75()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_76()) {
               this.jj_scanpos = xsp;
               if (this.jj_3R_77()) {
                  this.jj_scanpos = xsp;
                  if (this.jj_3R_78()) {
                     this.jj_scanpos = xsp;
                     if (this.jj_3R_79()) {
                        this.jj_scanpos = xsp;
                        if (this.jj_3R_80()) {
                           this.jj_scanpos = xsp;
                           if (this.jj_3R_81()) {
                              return true;
                           }

                           if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
                              return false;
                           }
                        } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
                           return false;
                        }
                     } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
                        return false;
                     }
                  } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
                     return false;
                  }
               } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
                  return false;
               }
            } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
               return false;
            }
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      }

      return false;
   }

   private final boolean jj_3R_85() {
      if (this.jj_scan_token(23)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_72() {
      if (this.jj_3R_41()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_71() {
      if (this.jj_3R_19()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_83() {
      if (this.jj_scan_token(23)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_70() {
      if (this.jj_3R_58()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_69() {
      if (this.jj_3R_57()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_34() {
      Token xsp = this.jj_scanpos;
      if (this.jj_3_10()) {
         this.jj_scanpos = xsp;
         if (this.jj_3R_52()) {
            return true;
         }

         if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      }

      return false;
   }

   private final boolean jj_3_10() {
      Token xsp = this.jj_scanpos;
      if (this.jj_3R_33()) {
         this.jj_scanpos = xsp;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      }

      if (this.jj_scan_token(41)) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else if (this.jj_3R_34()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_52() {
      if (this.jj_3R_60()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_68() {
      if (this.jj_3R_56()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_67() {
      if (this.jj_3R_55()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_66() {
      if (this.jj_3R_54()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_20() {
      if (this.jj_scan_token(23)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_87() {
      if (this.jj_3R_41()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_89() {
      if (this.jj_scan_token(3)) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else if (this.jj_3R_59()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_63() {
      if (this.jj_3R_41()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_65() {
      if (this.jj_scan_token(23)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_59() {
      Token xsp = this.jj_scanpos;
      if (this.jj_3R_65()) {
         this.jj_scanpos = xsp;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      }

      xsp = this.jj_scanpos;
      if (this.jj_3R_66()) {
         this.jj_scanpos = xsp;
         if (this.jj_3R_67()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_68()) {
               this.jj_scanpos = xsp;
               if (this.jj_3R_69()) {
                  this.jj_scanpos = xsp;
                  if (this.jj_3R_70()) {
                     this.jj_scanpos = xsp;
                     if (this.jj_3R_71()) {
                        this.jj_scanpos = xsp;
                        if (this.jj_3R_72()) {
                           return true;
                        }

                        if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
                           return false;
                        }
                     } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
                        return false;
                     }
                  } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
                     return false;
                  }
               } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
                  return false;
               }
            } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
               return false;
            }
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      }

      xsp = this.jj_scanpos;
      if (this.jj_3R_83()) {
         this.jj_scanpos = xsp;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      }

      return false;
   }

   private final boolean jj_3R_61() {
      if (this.jj_scan_token(23)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3_3() {
      if (this.jj_scan_token(1)) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else {
         Token xsp = this.jj_scanpos;
         if (this.jj_3R_20()) {
            this.jj_scanpos = xsp;
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }

         xsp = this.jj_scanpos;
         if (this.jj_3R_21()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_22()) {
               return true;
            }

            if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
               return false;
            }
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }

         xsp = this.jj_scanpos;
         if (this.jj_3R_23()) {
            this.jj_scanpos = xsp;
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }

         if (this.jj_scan_token(4)) {
            return true;
         } else {
            return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
         }
      }
   }

   private final boolean jj_3R_49() {
      if (this.jj_3R_58()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_88() {
      if (this.jj_scan_token(23)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_86() {
      if (this.jj_3R_19()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_48() {
      if (this.jj_3R_57()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_84() {
      if (this.jj_scan_token(23)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_62() {
      if (this.jj_3R_19()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_64() {
      if (this.jj_3R_59()) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else {
         do {
            Token xsp = this.jj_scanpos;
            if (this.jj_3R_89()) {
               this.jj_scanpos = xsp;
               return false;
            }
         } while(this.jj_la != 0 || this.jj_scanpos != this.jj_lastpos);

         return false;
      }
   }

   private final boolean jj_3R_47() {
      if (this.jj_3R_56()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_55() {
      if (this.jj_scan_token(1)) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else {
         Token xsp = this.jj_scanpos;
         if (this.jj_3R_61()) {
            this.jj_scanpos = xsp;
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }

         xsp = this.jj_scanpos;
         if (this.jj_3R_62()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_63()) {
               return true;
            }

            if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
               return false;
            }
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }

         xsp = this.jj_scanpos;
         if (this.jj_3R_84()) {
            this.jj_scanpos = xsp;
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }

         if (this.jj_scan_token(4)) {
            return true;
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         } else {
            xsp = this.jj_scanpos;
            if (this.jj_3R_85()) {
               this.jj_scanpos = xsp;
            } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
               return false;
            }

            xsp = this.jj_scanpos;
            if (this.jj_3R_86()) {
               this.jj_scanpos = xsp;
               if (this.jj_3R_87()) {
                  return true;
               }

               if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
                  return false;
               }
            } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
               return false;
            }

            xsp = this.jj_scanpos;
            if (this.jj_3R_88()) {
               this.jj_scanpos = xsp;
            } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
               return false;
            }

            if (this.jj_scan_token(2)) {
               return true;
            } else {
               return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
            }
         }
      }
   }

   private final boolean jj_3R_46() {
      if (this.jj_3R_55()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_45() {
      if (this.jj_3R_41()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_56() {
      if (this.jj_scan_token(1)) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else {
         Token xsp = this.jj_scanpos;
         if (this.jj_3R_64()) {
            this.jj_scanpos = xsp;
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }

         if (this.jj_scan_token(2)) {
            return true;
         } else {
            return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
         }
      }
   }

   private final boolean jj_3R_44() {
      if (this.jj_3R_54()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_43() {
      if (this.jj_3R_53()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_42() {
      if (this.jj_3R_19()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_25() {
      Token xsp = this.jj_scanpos;
      if (this.jj_3R_42()) {
         this.jj_scanpos = xsp;
         if (this.jj_3R_43()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_44()) {
               this.jj_scanpos = xsp;
               if (this.jj_3R_45()) {
                  this.jj_scanpos = xsp;
                  if (this.jj_3R_46()) {
                     this.jj_scanpos = xsp;
                     if (this.jj_3R_47()) {
                        this.jj_scanpos = xsp;
                        if (this.jj_3R_48()) {
                           this.jj_scanpos = xsp;
                           if (this.jj_3R_49()) {
                              return true;
                           }

                           if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
                              return false;
                           }
                        } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
                           return false;
                        }
                     } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
                        return false;
                     }
                  } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
                     return false;
                  }
               } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
                  return false;
               }
            } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
               return false;
            }
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      }

      return false;
   }

   private final boolean jj_3R_53() {
      if (this.jj_scan_token(52)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_50() {
      if (this.jj_scan_token(56)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   public Parser(CharStream stream) {
      this.jjtree = new JJTParserState();
      this.directives = new Hashtable(0);
      this.currentTemplateName = "";
      this.velcharstream = null;
      this.rsvc = null;
      this.lookingAhead = false;
      this.jj_la1 = new int[55];
      this.jj_la1_0 = new int[]{20906848, 0, 20906848, 3670016, 16777216, 100663298, 8388608, 8388608, 8388608, 20906848, 8, 125829122, 8388608, 0, 8388608, 8388608, 0, 8388608, 8388608, 16777216, 100663298, 8388608, 8, 125829122, 0, 0, 0, 17170528, 8388608, 20906848, 0, 0, 0, 20906848, 8388608, 20906848, 8388608, 8388608, 134217728, 0, 0, 0, 0, 0, 0, 805306368, 805306368, -1073741824, -1073741824, 8388608, 125829154, 8388608, 16777216, 100663330, 8388608};
      this.jj_la1_1 = new int[]{252874752, 36864, 236060672, 0, 85065728, 0, 0, 0, 0, 252874752, 0, 84017152, 0, 84017152, 0, 0, 84017152, 0, 0, 0, 84017152, 0, 0, 84017152, 16777216, 16777216, 83886080, 235012096, 0, 252874752, 8192, 8192, 16384, 252874752, 0, 252874752, 0, 0, 0, 4, 2, 384, 384, 120, 120, 0, 0, 1, 1, 0, 84017152, 0, 84017152, 0, 0};
      this.jj_2_rtns = new JJCalls[11];
      this.jj_rescan = false;
      this.jj_gc = 0;
      this.jj_expentries = new Vector();
      this.jj_kind = -1;
      this.jj_lasttokens = new int[100];
      this.token_source = new ParserTokenManager(stream);
      this.token = new Token();
      this.jj_ntk = -1;
      this.jj_gen = 0;

      for(int i = 0; i < 55; ++i) {
         this.jj_la1[i] = -1;
      }

      for(int i = 0; i < this.jj_2_rtns.length; ++i) {
         this.jj_2_rtns[i] = new JJCalls();
      }

   }

   public void ReInit(CharStream stream) {
      this.token_source.ReInit(stream);
      this.token = new Token();
      this.jj_ntk = -1;
      this.jjtree.reset();
      this.jj_gen = 0;

      for(int i = 0; i < 55; ++i) {
         this.jj_la1[i] = -1;
      }

      for(int i = 0; i < this.jj_2_rtns.length; ++i) {
         this.jj_2_rtns[i] = new JJCalls();
      }

   }

   public Parser(ParserTokenManager tm) {
      this.jjtree = new JJTParserState();
      this.directives = new Hashtable(0);
      this.currentTemplateName = "";
      this.velcharstream = null;
      this.rsvc = null;
      this.lookingAhead = false;
      this.jj_la1 = new int[55];
      this.jj_la1_0 = new int[]{20906848, 0, 20906848, 3670016, 16777216, 100663298, 8388608, 8388608, 8388608, 20906848, 8, 125829122, 8388608, 0, 8388608, 8388608, 0, 8388608, 8388608, 16777216, 100663298, 8388608, 8, 125829122, 0, 0, 0, 17170528, 8388608, 20906848, 0, 0, 0, 20906848, 8388608, 20906848, 8388608, 8388608, 134217728, 0, 0, 0, 0, 0, 0, 805306368, 805306368, -1073741824, -1073741824, 8388608, 125829154, 8388608, 16777216, 100663330, 8388608};
      this.jj_la1_1 = new int[]{252874752, 36864, 236060672, 0, 85065728, 0, 0, 0, 0, 252874752, 0, 84017152, 0, 84017152, 0, 0, 84017152, 0, 0, 0, 84017152, 0, 0, 84017152, 16777216, 16777216, 83886080, 235012096, 0, 252874752, 8192, 8192, 16384, 252874752, 0, 252874752, 0, 0, 0, 4, 2, 384, 384, 120, 120, 0, 0, 1, 1, 0, 84017152, 0, 84017152, 0, 0};
      this.jj_2_rtns = new JJCalls[11];
      this.jj_rescan = false;
      this.jj_gc = 0;
      this.jj_expentries = new Vector();
      this.jj_kind = -1;
      this.jj_lasttokens = new int[100];
      this.token_source = tm;
      this.token = new Token();
      this.jj_ntk = -1;
      this.jj_gen = 0;

      for(int i = 0; i < 55; ++i) {
         this.jj_la1[i] = -1;
      }

      for(int i = 0; i < this.jj_2_rtns.length; ++i) {
         this.jj_2_rtns[i] = new JJCalls();
      }

   }

   public void ReInit(ParserTokenManager tm) {
      this.token_source = tm;
      this.token = new Token();
      this.jj_ntk = -1;
      this.jjtree.reset();
      this.jj_gen = 0;

      for(int i = 0; i < 55; ++i) {
         this.jj_la1[i] = -1;
      }

      for(int i = 0; i < this.jj_2_rtns.length; ++i) {
         this.jj_2_rtns[i] = new JJCalls();
      }

   }

   private final Token jj_consume_token(int kind) throws ParseException {
      Token oldToken;
      if ((oldToken = this.token).next != null) {
         this.token = this.token.next;
      } else {
         this.token = this.token.next = this.token_source.getNextToken();
      }

      this.jj_ntk = -1;
      if (this.token.kind != kind) {
         this.token = oldToken;
         this.jj_kind = kind;
         throw this.generateParseException();
      } else {
         ++this.jj_gen;
         if (++this.jj_gc > 100) {
            this.jj_gc = 0;

            for(int i = 0; i < this.jj_2_rtns.length; ++i) {
               for(JJCalls c = this.jj_2_rtns[i]; c != null; c = c.next) {
                  if (c.gen < this.jj_gen) {
                     c.first = null;
                  }
               }
            }
         }

         return this.token;
      }
   }

   private final boolean jj_scan_token(int kind) {
      if (this.jj_scanpos == this.jj_lastpos) {
         --this.jj_la;
         if (this.jj_scanpos.next == null) {
            this.jj_lastpos = this.jj_scanpos = this.jj_scanpos.next = this.token_source.getNextToken();
         } else {
            this.jj_lastpos = this.jj_scanpos = this.jj_scanpos.next;
         }
      } else {
         this.jj_scanpos = this.jj_scanpos.next;
      }

      if (this.jj_rescan) {
         int i = 0;

         Token tok;
         for(tok = this.token; tok != null && tok != this.jj_scanpos; tok = tok.next) {
            ++i;
         }

         if (tok != null) {
            this.jj_add_error_token(kind, i);
         }
      }

      return this.jj_scanpos.kind != kind;
   }

   public final Token getNextToken() {
      if (this.token.next != null) {
         this.token = this.token.next;
      } else {
         this.token = this.token.next = this.token_source.getNextToken();
      }

      this.jj_ntk = -1;
      ++this.jj_gen;
      return this.token;
   }

   public final Token getToken(int index) {
      Token t = this.lookingAhead ? this.jj_scanpos : this.token;

      for(int i = 0; i < index; ++i) {
         if (t.next != null) {
            t = t.next;
         } else {
            t = t.next = this.token_source.getNextToken();
         }
      }

      return t;
   }

   private final int jj_ntk() {
      return (this.jj_nt = this.token.next) == null ? (this.jj_ntk = (this.token.next = this.token_source.getNextToken()).kind) : (this.jj_ntk = this.jj_nt.kind);
   }

   private void jj_add_error_token(int kind, int pos) {
      if (pos < 100) {
         if (pos == this.jj_endpos + 1) {
            this.jj_lasttokens[this.jj_endpos++] = kind;
         } else if (this.jj_endpos != 0) {
            this.jj_expentry = new int[this.jj_endpos];

            for(int i = 0; i < this.jj_endpos; ++i) {
               this.jj_expentry[i] = this.jj_lasttokens[i];
            }

            boolean exists = false;
            Enumeration enum = this.jj_expentries.elements();

            label47:
            do {
               int[] oldentry;
               do {
                  if (!enum.hasMoreElements()) {
                     break label47;
                  }

                  oldentry = (int[])enum.nextElement();
               } while(oldentry.length != this.jj_expentry.length);

               exists = true;

               for(int i = 0; i < this.jj_expentry.length; ++i) {
                  if (oldentry[i] != this.jj_expentry[i]) {
                     exists = false;
                     break;
                  }
               }
            } while(!exists);

            if (!exists) {
               this.jj_expentries.addElement(this.jj_expentry);
            }

            if (pos != 0) {
               this.jj_lasttokens[(this.jj_endpos = pos) - 1] = kind;
            }
         }

      }
   }

   public final ParseException generateParseException() {
      this.jj_expentries.removeAllElements();
      boolean[] la1tokens = new boolean[62];

      for(int i = 0; i < 62; ++i) {
         la1tokens[i] = false;
      }

      if (this.jj_kind >= 0) {
         la1tokens[this.jj_kind] = true;
         this.jj_kind = -1;
      }

      int j;
      for(int i = 0; i < 55; ++i) {
         if (this.jj_la1[i] == this.jj_gen) {
            for(j = 0; j < 32; ++j) {
               if ((this.jj_la1_0[i] & 1 << j) != 0) {
                  la1tokens[j] = true;
               }

               if ((this.jj_la1_1[i] & 1 << j) != 0) {
                  la1tokens[32 + j] = true;
               }
            }
         }
      }

      for(j = 0; j < 62; ++j) {
         if (la1tokens[j]) {
            this.jj_expentry = new int[1];
            this.jj_expentry[0] = j;
            this.jj_expentries.addElement(this.jj_expentry);
         }
      }

      this.jj_endpos = 0;
      this.jj_rescan_token();
      this.jj_add_error_token(0, 0);
      int[][] exptokseq = new int[this.jj_expentries.size()][];

      for(int i = 0; i < this.jj_expentries.size(); ++i) {
         exptokseq[i] = (int[])this.jj_expentries.elementAt(i);
      }

      return new ParseException(this.token, exptokseq, ParserConstants.tokenImage);
   }

   public final void enable_tracing() {
   }

   public final void disable_tracing() {
   }

   private final void jj_rescan_token() {
      this.jj_rescan = true;

      for(int i = 0; i < 11; ++i) {
         JJCalls p = this.jj_2_rtns[i];

         do {
            if (p.gen > this.jj_gen) {
               this.jj_la = p.arg;
               this.jj_lastpos = this.jj_scanpos = p.first;
               switch (i) {
                  case 0:
                     this.jj_3_1();
                     break;
                  case 1:
                     this.jj_3_2();
                     break;
                  case 2:
                     this.jj_3_3();
                     break;
                  case 3:
                     this.jj_3_4();
                     break;
                  case 4:
                     this.jj_3_5();
                     break;
                  case 5:
                     this.jj_3_6();
                     break;
                  case 6:
                     this.jj_3_7();
                     break;
                  case 7:
                     this.jj_3_8();
                     break;
                  case 8:
                     this.jj_3_9();
                     break;
                  case 9:
                     this.jj_3_10();
                     break;
                  case 10:
                     this.jj_3_11();
               }
            }

            p = p.next;
         } while(p != null);
      }

      this.jj_rescan = false;
   }

   private final void jj_save(int index, int xla) {
      JJCalls p;
      for(p = this.jj_2_rtns[index]; p.gen > this.jj_gen; p = p.next) {
         if (p.next == null) {
            p = p.next = new JJCalls();
            break;
         }
      }

      p.gen = this.jj_gen + xla - this.jj_la;
      p.first = this.token;
      p.arg = xla;
   }

   static final class JJCalls {
      int gen;
      Token first;
      int arg;
      JJCalls next;
   }
}
