package org.antlr.stringtemplate.language;

import antlr.MismatchedTokenException;
import antlr.NoViableAltException;
import antlr.RecognitionException;
import antlr.TreeParser;
import antlr.collections.AST;
import antlr.collections.impl.BitSet;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.StringTemplateWriter;

public class ActionEvaluator extends TreeParser implements ActionEvaluatorTokenTypes {
   protected StringTemplate self = null;
   protected StringTemplateWriter out = null;
   protected ASTExpr chunk = null;
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "APPLY", "MULTI_APPLY", "ARGS", "INCLUDE", "\"if\"", "VALUE", "TEMPLATE", "FUNCTION", "SINGLEVALUEARG", "LIST", "NOTHING", "SEMI", "LPAREN", "RPAREN", "\"elseif\"", "COMMA", "ID", "ASSIGN", "COLON", "NOT", "PLUS", "DOT", "\"first\"", "\"rest\"", "\"last\"", "\"length\"", "\"strip\"", "\"trunc\"", "\"super\"", "ANONYMOUS_TEMPLATE", "STRING", "INT", "LBRACK", "RBRACK", "DOTDOTDOT", "TEMPLATE_ARGS", "NESTED_ANONYMOUS_TEMPLATE", "ESC_CHAR", "WS", "WS_CHAR"};
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());

   public ActionEvaluator(StringTemplate self, ASTExpr chunk, StringTemplateWriter out) {
      this.self = self;
      this.chunk = chunk;
      this.out = out;
   }

   public void reportError(RecognitionException e) {
      this.self.error("eval tree parse error", e);
   }

   public ActionEvaluator() {
      this.tokenNames = _tokenNames;
   }

   public final int action(AST _t) throws RecognitionException {
      int numCharsWritten = 0;
      StringTemplateAST var10000;
      if (_t == ASTNULL) {
         var10000 = null;
      } else {
         var10000 = (StringTemplateAST)_t;
      }

      Object e = null;

      try {
         e = this.expr(_t);
         _t = this._retTree;
         numCharsWritten = this.chunk.writeAttribute(this.self, e, this.out);
      } catch (RecognitionException var6) {
         this.reportError(var6);
         if (_t != null) {
            _t = _t.getNextSibling();
         }
      }

      this._retTree = _t;
      return numCharsWritten;
   }

   public final Object expr(AST _t) throws RecognitionException {
      Object value = null;
      StringTemplateAST var10000;
      if (_t == ASTNULL) {
         var10000 = null;
      } else {
         var10000 = (StringTemplateAST)_t;
      }

      Object a = null;
      Object b = null;
      Object e = null;
      Map argumentContext = null;

      try {
         if (_t == null) {
            _t = ASTNULL;
         }

         Object __t3;
         StringTemplateAST tmp1_AST_in;
         AST _t;
         switch (((AST)_t).getType()) {
            case 4:
            case 5:
               value = this.templateApplication((AST)_t);
               _t = this._retTree;
               break;
            case 6:
            case 8:
            case 10:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 21:
            case 22:
            case 23:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            default:
               throw new NoViableAltException((AST)_t);
            case 7:
               value = this.templateInclude((AST)_t);
               _t = this._retTree;
               break;
            case 9:
               __t3 = _t;
               tmp1_AST_in = (StringTemplateAST)_t;
               this.match((AST)_t, 9);
               _t = ((AST)_t).getFirstChild();
               e = this.expr(_t);
               _t = this._retTree;
               _t = ((AST)__t3).getNextSibling();
               StringWriter buf = new StringWriter();
               StringTemplateWriter sw = this.self.getGroup().getStringTemplateWriter(buf);
               int n = this.chunk.writeAttribute(this.self, e, sw);
               if (n > 0) {
                  value = buf.toString();
               }
               break;
            case 11:
               value = this.function((AST)_t);
               _t = this._retTree;
               break;
            case 13:
               value = this.list((AST)_t);
               _t = this._retTree;
               break;
            case 20:
            case 25:
            case 33:
            case 34:
            case 35:
               value = this.attribute((AST)_t);
               _t = this._retTree;
               break;
            case 24:
               __t3 = _t;
               tmp1_AST_in = (StringTemplateAST)_t;
               this.match((AST)_t, 24);
               _t = ((AST)_t).getFirstChild();
               a = this.expr(_t);
               _t = this._retTree;
               b = this.expr(_t);
               _t = this._retTree;
               value = this.chunk.add(a, b);
               _t = ((AST)__t3).getNextSibling();
         }
      } catch (RecognitionException var13) {
         this.reportError(var13);
         if (_t != null) {
            _t = ((AST)_t).getNextSibling();
         }
      }

      this._retTree = (AST)_t;
      return value;
   }

   public final Object templateApplication(AST _t) throws RecognitionException {
      Object value = null;
      StringTemplateAST var10000;
      if (_t == ASTNULL) {
         var10000 = null;
      } else {
         var10000 = (StringTemplateAST)_t;
      }

      StringTemplateAST anon = null;
      Object a = null;
      Vector templatesToApply = new Vector();
      List attributes = new ArrayList();

      try {
         if (_t == null) {
            _t = ASTNULL;
         }

         Object __t17;
         StringTemplateAST tmp4_AST_in;
         int _cnt19;
         AST _t;
         label64:
         switch (((AST)_t).getType()) {
            case 4:
               __t17 = _t;
               tmp4_AST_in = (StringTemplateAST)_t;
               this.match((AST)_t, 4);
               _t = ((AST)_t).getFirstChild();
               a = this.expr(_t);
               _t = this._retTree;
               _cnt19 = 0;

               while(true) {
                  if (_t == null) {
                     _t = ASTNULL;
                  }

                  if (((AST)_t).getType() != 10) {
                     if (_cnt19 < 1) {
                        throw new NoViableAltException((AST)_t);
                     }

                     value = this.chunk.applyListOfAlternatingTemplates(this.self, a, templatesToApply);
                     _t = ((AST)__t17).getNextSibling();
                     break label64;
                  }

                  this.template((AST)_t, templatesToApply);
                  _t = this._retTree;
                  ++_cnt19;
               }
            case 5:
               __t17 = _t;
               tmp4_AST_in = (StringTemplateAST)_t;
               this.match((AST)_t, 5);
               _t = ((AST)_t).getFirstChild();
               _cnt19 = 0;

               while(true) {
                  if (_t == null) {
                     _t = ASTNULL;
                  }

                  if (!_tokenSet_0.member(((AST)_t).getType())) {
                     if (_cnt19 < 1) {
                        throw new NoViableAltException((AST)_t);
                     }

                     StringTemplateAST tmp5_AST_in = (StringTemplateAST)_t;
                     this.match((AST)_t, 22);
                     _t = ((AST)_t).getNextSibling();
                     anon = (StringTemplateAST)_t;
                     this.match(_t, 33);
                     _t = _t.getNextSibling();
                     StringTemplate anonymous = anon.getStringTemplate();
                     templatesToApply.addElement(anonymous);
                     value = this.chunk.applyTemplateToListOfAttributes(this.self, attributes, anon.getStringTemplate());
                     _t = ((AST)__t17).getNextSibling();
                     break label64;
                  }

                  a = this.expr((AST)_t);
                  _t = this._retTree;
                  attributes.add(a);
                  ++_cnt19;
               }
            default:
               throw new NoViableAltException((AST)_t);
         }
      } catch (RecognitionException var12) {
         this.reportError(var12);
         if (_t != null) {
            _t = ((AST)_t).getNextSibling();
         }
      }

      this._retTree = (AST)_t;
      return value;
   }

   public final Object attribute(AST _t) throws RecognitionException {
      Object value = null;
      StringTemplateAST var10000;
      if (_t == ASTNULL) {
         var10000 = null;
      } else {
         var10000 = (StringTemplateAST)_t;
      }

      StringTemplateAST prop = null;
      StringTemplateAST i3 = null;
      StringTemplateAST i = null;
      StringTemplateAST s = null;
      StringTemplateAST at = null;
      Object obj = null;
      Object propName = null;
      Object e = null;

      try {
         if (_t == null) {
            _t = ASTNULL;
         }

         switch (((AST)_t).getType()) {
            case 20:
               i3 = (StringTemplateAST)_t;
               this.match((AST)_t, 20);
               _t = ((AST)_t).getNextSibling();
               value = this.self.getAttribute(i3.getText());
               break;
            case 25:
               AST __t33 = _t;
               StringTemplateAST tmp6_AST_in = (StringTemplateAST)_t;
               this.match((AST)_t, 25);
               AST _t = ((AST)_t).getFirstChild();
               obj = this.expr(_t);
               _t = this._retTree;
               if (_t == null) {
                  _t = ASTNULL;
               }

               switch (((AST)_t).getType()) {
                  case 9:
                     AST __t35 = _t;
                     StringTemplateAST tmp7_AST_in = (StringTemplateAST)_t;
                     this.match((AST)_t, 9);
                     _t = ((AST)_t).getFirstChild();
                     e = this.expr(_t);
                     _t = this._retTree;
                     _t = ((AST)__t35).getNextSibling();
                     if (e != null) {
                        propName = e;
                     }
                     break;
                  case 20:
                     prop = (StringTemplateAST)_t;
                     this.match((AST)_t, 20);
                     _t = ((AST)_t).getNextSibling();
                     propName = prop.getText();
                     break;
                  default:
                     throw new NoViableAltException((AST)_t);
               }

               _t = ((AST)__t33).getNextSibling();
               value = this.chunk.getObjectProperty(this.self, obj, propName);
               break;
            case 33:
               at = (StringTemplateAST)_t;
               this.match((AST)_t, 33);
               _t = ((AST)_t).getNextSibling();
               value = at.getText();
               if (at.getText() != null) {
                  StringTemplate valueST = new StringTemplate(this.self.getGroup(), at.getText());
                  valueST.setEnclosingInstance(this.self);
                  valueST.setName("<anonymous template argument>");
                  value = valueST;
               }
               break;
            case 34:
               s = (StringTemplateAST)_t;
               this.match((AST)_t, 34);
               _t = ((AST)_t).getNextSibling();
               value = s.getText();
               break;
            case 35:
               i = (StringTemplateAST)_t;
               this.match((AST)_t, 35);
               _t = ((AST)_t).getNextSibling();
               value = new Integer(i.getText());
               break;
            default:
               throw new NoViableAltException((AST)_t);
         }
      } catch (RecognitionException var16) {
         this.reportError(var16);
         if (_t != null) {
            _t = ((AST)_t).getNextSibling();
         }
      }

      this._retTree = (AST)_t;
      return value;
   }

   public final Object templateInclude(AST _t) throws RecognitionException {
      Object value = null;
      StringTemplateAST var10000;
      if (_t == ASTNULL) {
         var10000 = null;
      } else {
         var10000 = (StringTemplateAST)_t;
      }

      StringTemplateAST id = null;
      StringTemplateAST a1 = null;
      StringTemplateAST a2 = null;
      StringTemplateAST args = null;
      String name = null;
      Object n = null;

      try {
         AST __t10 = _t;
         StringTemplateAST tmp8_AST_in = (StringTemplateAST)_t;
         this.match(_t, 7);
         AST _t = _t.getFirstChild();
         if (_t == null) {
            _t = ASTNULL;
         }

         switch (((AST)_t).getType()) {
            case 9:
               AST __t12 = _t;
               StringTemplateAST tmp9_AST_in = (StringTemplateAST)_t;
               this.match((AST)_t, 9);
               _t = ((AST)_t).getFirstChild();
               n = this.expr(_t);
               _t = this._retTree;
               a2 = (StringTemplateAST)_t;
               if (_t == null) {
                  throw new MismatchedTokenException();
               }

               _t = _t.getNextSibling();
               _t = ((AST)__t12).getNextSibling();
               if (n != null) {
                  name = n.toString();
               }

               args = a2;
               break;
            case 20:
               id = (StringTemplateAST)_t;
               this.match((AST)_t, 20);
               _t = ((AST)_t).getNextSibling();
               a1 = (StringTemplateAST)_t;
               if (_t == null) {
                  throw new MismatchedTokenException();
               }

               _t = _t.getNextSibling();
               name = id.getText();
               args = a1;
               break;
            default:
               throw new NoViableAltException((AST)_t);
         }

         _t = __t10.getNextSibling();
         if (name != null) {
            value = this.chunk.getTemplateInclude(this.self, name, args);
         }
      } catch (RecognitionException var14) {
         this.reportError(var14);
         if (_t != null) {
            _t = _t.getNextSibling();
         }
      }

      this._retTree = _t;
      return value;
   }

   public final Object function(AST _t) throws RecognitionException {
      Object value = null;
      StringTemplateAST var10000;
      if (_t == ASTNULL) {
         var10000 = null;
      } else {
         var10000 = (StringTemplateAST)_t;
      }

      try {
         AST __t21 = _t;
         StringTemplateAST tmp10_AST_in = (StringTemplateAST)_t;
         this.match(_t, 11);
         AST _t = _t.getFirstChild();
         if (_t == null) {
            _t = ASTNULL;
         }

         Object a;
         StringTemplateAST tmp16_AST_in;
         switch (((AST)_t).getType()) {
            case 26:
               tmp16_AST_in = (StringTemplateAST)_t;
               this.match((AST)_t, 26);
               _t = ((AST)_t).getNextSibling();
               a = this.singleFunctionArg(_t);
               _t = this._retTree;
               value = this.chunk.first(a);
               break;
            case 27:
               tmp16_AST_in = (StringTemplateAST)_t;
               this.match((AST)_t, 27);
               _t = ((AST)_t).getNextSibling();
               a = this.singleFunctionArg(_t);
               _t = this._retTree;
               value = this.chunk.rest(a);
               break;
            case 28:
               tmp16_AST_in = (StringTemplateAST)_t;
               this.match((AST)_t, 28);
               _t = ((AST)_t).getNextSibling();
               a = this.singleFunctionArg(_t);
               _t = this._retTree;
               value = this.chunk.last(a);
               break;
            case 29:
               tmp16_AST_in = (StringTemplateAST)_t;
               this.match((AST)_t, 29);
               _t = ((AST)_t).getNextSibling();
               a = this.singleFunctionArg(_t);
               _t = this._retTree;
               value = this.chunk.length(a);
               break;
            case 30:
               tmp16_AST_in = (StringTemplateAST)_t;
               this.match((AST)_t, 30);
               _t = ((AST)_t).getNextSibling();
               a = this.singleFunctionArg(_t);
               _t = this._retTree;
               value = this.chunk.strip(a);
               break;
            case 31:
               tmp16_AST_in = (StringTemplateAST)_t;
               this.match((AST)_t, 31);
               _t = ((AST)_t).getNextSibling();
               a = this.singleFunctionArg(_t);
               _t = this._retTree;
               value = this.chunk.trunc(a);
               break;
            default:
               throw new NoViableAltException((AST)_t);
         }

         _t = __t21.getNextSibling();
      } catch (RecognitionException var8) {
         this.reportError(var8);
         if (_t != null) {
            _t = _t.getNextSibling();
         }
      }

      this._retTree = _t;
      return value;
   }

   public final Object list(AST _t) throws RecognitionException {
      Object value = null;
      StringTemplateAST var10000;
      if (_t == ASTNULL) {
         var10000 = null;
      } else {
         var10000 = (StringTemplateAST)_t;
      }

      Object e = null;
      List elements = new ArrayList();

      try {
         StringTemplateAST tmp17_AST_in = (StringTemplateAST)_t;
         this.match(_t, 13);
         AST _t = _t.getFirstChild();
         int _cnt8 = 0;

         label41:
         while(true) {
            if (_t == null) {
               _t = ASTNULL;
            }

            switch (((AST)_t).getType()) {
               case 4:
               case 5:
               case 7:
               case 9:
               case 11:
               case 13:
               case 20:
               case 24:
               case 25:
               case 33:
               case 34:
               case 35:
                  e = this.expr((AST)_t);
                  _t = this._retTree;
                  if (e != null) {
                     elements.add(e);
                  }
                  break;
               case 6:
               case 8:
               case 10:
               case 12:
               case 15:
               case 16:
               case 17:
               case 18:
               case 19:
               case 21:
               case 22:
               case 23:
               case 26:
               case 27:
               case 28:
               case 29:
               case 30:
               case 31:
               case 32:
               default:
                  if (_cnt8 < 1) {
                     throw new NoViableAltException((AST)_t);
                  }

                  _t = _t.getNextSibling();
                  value = new Cat(elements);
                  break label41;
               case 14:
                  StringTemplateAST tmp18_AST_in = (StringTemplateAST)_t;
                  this.match((AST)_t, 14);
                  _t = ((AST)_t).getNextSibling();
                  List nullSingleton = new ArrayList() {
                     {
                        this.add((Object)null);
                     }
                  };
                  elements.add(nullSingleton.iterator());
            }

            ++_cnt8;
         }
      } catch (RecognitionException var11) {
         this.reportError(var11);
         if (_t != null) {
            _t = _t.getNextSibling();
         }
      }

      this._retTree = _t;
      return value;
   }

   public final void template(AST _t, Vector templatesToApply) throws RecognitionException {
      StringTemplateAST var10000;
      if (_t == ASTNULL) {
         var10000 = null;
      } else {
         var10000 = (StringTemplateAST)_t;
      }

      StringTemplateAST t = null;
      StringTemplateAST args = null;
      StringTemplateAST anon = null;
      StringTemplateAST args2 = null;
      Map argumentContext = null;
      Object n = null;

      try {
         AST __t26 = _t;
         StringTemplateAST tmp19_AST_in = (StringTemplateAST)_t;
         this.match(_t, 10);
         AST _t = _t.getFirstChild();
         if (_t == null) {
            _t = ASTNULL;
         }

         StringTemplate embedded;
         switch (((AST)_t).getType()) {
            case 9:
               AST __t28 = _t;
               StringTemplateAST tmp20_AST_in = (StringTemplateAST)_t;
               this.match((AST)_t, 9);
               _t = ((AST)_t).getFirstChild();
               n = this.expr(_t);
               _t = this._retTree;
               args2 = (StringTemplateAST)_t;
               if (_t == null) {
                  throw new MismatchedTokenException();
               }

               _t = _t.getNextSibling();
               embedded = null;
               if (n != null) {
                  String templateName = n.toString();
                  StringTemplateGroup group = this.self.getGroup();
                  embedded = group.getEmbeddedInstanceOf(this.self, templateName);
                  if (embedded != null) {
                     embedded.setArgumentsAST(args2);
                     templatesToApply.addElement(embedded);
                  }
               }

               _t = ((AST)__t28).getNextSibling();
               break;
            case 20:
               t = (StringTemplateAST)_t;
               this.match((AST)_t, 20);
               _t = ((AST)_t).getNextSibling();
               args = (StringTemplateAST)_t;
               if (_t == null) {
                  throw new MismatchedTokenException();
               }

               _t = _t.getNextSibling();
               String templateName = t.getText();
               StringTemplateGroup group = this.self.getGroup();
               embedded = group.getEmbeddedInstanceOf(this.self, templateName);
               if (embedded != null) {
                  embedded.setArgumentsAST(args);
                  templatesToApply.addElement(embedded);
               }
               break;
            case 33:
               anon = (StringTemplateAST)_t;
               this.match((AST)_t, 33);
               _t = ((AST)_t).getNextSibling();
               StringTemplate anonymous = anon.getStringTemplate();
               anonymous.setGroup(this.self.getGroup());
               templatesToApply.addElement(anonymous);
               break;
            default:
               throw new NoViableAltException((AST)_t);
         }

         _t = __t26.getNextSibling();
      } catch (RecognitionException var17) {
         this.reportError(var17);
         if (_t != null) {
            _t = _t.getNextSibling();
         }
      }

      this._retTree = _t;
   }

   public final Object singleFunctionArg(AST _t) throws RecognitionException {
      Object value = null;
      StringTemplateAST var10000;
      if (_t == ASTNULL) {
         var10000 = null;
      } else {
         var10000 = (StringTemplateAST)_t;
      }

      try {
         AST __t24 = _t;
         StringTemplateAST tmp21_AST_in = (StringTemplateAST)_t;
         this.match(_t, 12);
         _t = _t.getFirstChild();
         value = this.expr(_t);
         _t = this._retTree;
         _t = __t24.getNextSibling();
      } catch (RecognitionException var6) {
         this.reportError(var6);
         if (_t != null) {
            _t = _t.getNextSibling();
         }
      }

      this._retTree = _t;
      return value;
   }

   public final boolean ifCondition(AST _t) throws RecognitionException {
      boolean value = false;
      StringTemplateAST var10000;
      if (_t == ASTNULL) {
         var10000 = null;
      } else {
         var10000 = (StringTemplateAST)_t;
      }

      Object a = null;
      Object b = null;

      try {
         if (_t == null) {
            _t = ASTNULL;
         }

         switch (((AST)_t).getType()) {
            case 4:
            case 5:
            case 7:
            case 9:
            case 11:
            case 13:
            case 20:
            case 24:
            case 25:
            case 33:
            case 34:
            case 35:
               a = this.ifAtom((AST)_t);
               _t = this._retTree;
               value = this.chunk.testAttributeTrue(a);
               break;
            case 6:
            case 8:
            case 10:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 21:
            case 22:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            default:
               throw new NoViableAltException((AST)_t);
            case 23:
               AST __t30 = _t;
               StringTemplateAST tmp22_AST_in = (StringTemplateAST)_t;
               this.match((AST)_t, 23);
               AST _t = ((AST)_t).getFirstChild();
               a = this.ifAtom(_t);
               _t = this._retTree;
               _t = ((AST)__t30).getNextSibling();
               value = !this.chunk.testAttributeTrue(a);
         }
      } catch (RecognitionException var8) {
         this.reportError(var8);
         if (_t != null) {
            _t = ((AST)_t).getNextSibling();
         }
      }

      this._retTree = (AST)_t;
      return value;
   }

   public final Object ifAtom(AST _t) throws RecognitionException {
      Object value = null;
      StringTemplateAST var10000;
      if (_t == ASTNULL) {
         var10000 = null;
      } else {
         var10000 = (StringTemplateAST)_t;
      }

      try {
         value = this.expr(_t);
         _t = this._retTree;
      } catch (RecognitionException var5) {
         this.reportError(var5);
         if (_t != null) {
            _t = _t.getNextSibling();
         }
      }

      this._retTree = _t;
      return value;
   }

   public final Map argList(AST _t, StringTemplate embedded, Map initialContext) throws RecognitionException {
      Map argumentContext = null;
      StringTemplateAST var10000;
      if (_t == ASTNULL) {
         var10000 = null;
      } else {
         var10000 = (StringTemplateAST)_t;
      }

      argumentContext = initialContext;
      if (initialContext == null) {
         argumentContext = new HashMap();
      }

      try {
         if (_t == null) {
            _t = ASTNULL;
         }

         label41:
         switch (((AST)_t).getType()) {
            case 6:
               AST __t37 = _t;
               StringTemplateAST tmp23_AST_in = (StringTemplateAST)_t;
               this.match((AST)_t, 6);
               _t = ((AST)_t).getFirstChild();

               while(true) {
                  if (_t == null) {
                     _t = ASTNULL;
                  }

                  if (((AST)_t).getType() != 21 && ((AST)_t).getType() != 38) {
                     _t = ((AST)__t37).getNextSibling();
                     break label41;
                  }

                  this.argumentAssignment((AST)_t, embedded, (Map)argumentContext);
                  _t = this._retTree;
               }
            case 12:
               this.singleTemplateArg((AST)_t, embedded, (Map)argumentContext);
               _t = this._retTree;
               break;
            default:
               throw new NoViableAltException((AST)_t);
         }
      } catch (RecognitionException var8) {
         this.reportError(var8);
         if (_t != null) {
            _t = ((AST)_t).getNextSibling();
         }
      }

      this._retTree = (AST)_t;
      return (Map)argumentContext;
   }

   public final void argumentAssignment(AST _t, StringTemplate embedded, Map argumentContext) throws RecognitionException {
      StringTemplateAST var10000;
      if (_t == ASTNULL) {
         var10000 = null;
      } else {
         var10000 = (StringTemplateAST)_t;
      }

      StringTemplateAST arg = null;
      Object e = null;

      try {
         if (_t == null) {
            _t = ASTNULL;
         }

         switch (((AST)_t).getType()) {
            case 21:
               AST __t43 = _t;
               StringTemplateAST tmp24_AST_in = (StringTemplateAST)_t;
               this.match((AST)_t, 21);
               AST _t = ((AST)_t).getFirstChild();
               arg = (StringTemplateAST)_t;
               this.match(_t, 20);
               _t = _t.getNextSibling();
               e = this.expr(_t);
               _t = this._retTree;
               _t = ((AST)__t43).getNextSibling();
               if (e != null) {
                  this.self.rawSetArgumentAttribute(embedded, argumentContext, arg.getText(), e);
               }
               break;
            case 38:
               StringTemplateAST tmp25_AST_in = (StringTemplateAST)_t;
               this.match((AST)_t, 38);
               _t = ((AST)_t).getNextSibling();
               embedded.setPassThroughAttributes(true);
               break;
            default:
               throw new NoViableAltException((AST)_t);
         }
      } catch (RecognitionException var9) {
         this.reportError(var9);
         if (_t != null) {
            _t = ((AST)_t).getNextSibling();
         }
      }

      this._retTree = (AST)_t;
   }

   public final void singleTemplateArg(AST _t, StringTemplate embedded, Map argumentContext) throws RecognitionException {
      StringTemplateAST var10000;
      if (_t == ASTNULL) {
         var10000 = null;
      } else {
         var10000 = (StringTemplateAST)_t;
      }

      Object e = null;

      try {
         AST __t41 = _t;
         StringTemplateAST tmp26_AST_in = (StringTemplateAST)_t;
         this.match(_t, 12);
         _t = _t.getFirstChild();
         e = this.expr(_t);
         _t = this._retTree;
         _t = __t41.getNextSibling();
         if (e != null) {
            String soleArgName = null;
            boolean error = false;
            Map formalArgs = embedded.getFormalArguments();
            if (formalArgs != null) {
               Set argNames = formalArgs.keySet();
               if (argNames.size() == 1) {
                  soleArgName = (String)argNames.toArray()[0];
               } else {
                  error = true;
               }
            } else {
               error = true;
            }

            if (error) {
               this.self.error("template " + embedded.getName() + " must have exactly one formal arg in template context " + this.self.getEnclosingInstanceStackString());
            } else {
               this.self.rawSetArgumentAttribute(embedded, argumentContext, soleArgName, e);
            }
         }
      } catch (RecognitionException var12) {
         this.reportError(var12);
         if (_t != null) {
            _t = _t.getNextSibling();
         }
      }

      this._retTree = _t;
   }

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[]{60180933296L, 0L};
      return data;
   }

   public static class NameValuePair {
      public String name;
      public Object value;
   }
}
