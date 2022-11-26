package org.stringtemplate.v4.compiler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.misc.ErrorType;

public class Compiler {
   public static final String SUBTEMPLATE_PREFIX = "_sub";
   public static final int TEMPLATE_INITIAL_CODE_SIZE = 15;
   public static final Map supportedOptions = new HashMap() {
      {
         this.put("anchor", Interpreter.Option.ANCHOR);
         this.put("format", Interpreter.Option.FORMAT);
         this.put("null", Interpreter.Option.NULL);
         this.put("separator", Interpreter.Option.SEPARATOR);
         this.put("wrap", Interpreter.Option.WRAP);
      }
   };
   public static final int NUM_OPTIONS;
   public static final Map defaultOptionValues;
   public static Map funcs;
   public static int subtemplateCount;
   public STGroup group;

   public Compiler() {
      this(STGroup.defaultGroup);
   }

   public Compiler(STGroup group) {
      this.group = group;
   }

   public CompiledST compile(String template) {
      CompiledST code = this.compile((String)null, (String)null, (List)null, template, (Token)null);
      code.hasFormalArgs = false;
      return code;
   }

   public CompiledST compile(String name, String template) {
      CompiledST code = this.compile((String)null, name, (List)null, template, (Token)null);
      code.hasFormalArgs = false;
      return code;
   }

   public CompiledST compile(String srcName, String name, List args, String template, Token templateToken) {
      ANTLRStringStream is = new ANTLRStringStream(template);
      is.name = srcName != null ? srcName : name;
      STLexer lexer = null;
      if (templateToken != null && templateToken.getType() == 6) {
         lexer = new STLexer(this.group.errMgr, is, templateToken, this.group.delimiterStartChar, this.group.delimiterStopChar) {
            public Token nextToken() {
               Token t;
               for(t = super.nextToken(); t.getType() == 32 || t.getType() == 31; t = super.nextToken()) {
               }

               return t;
            }
         };
      } else {
         lexer = new STLexer(this.group.errMgr, is, templateToken, this.group.delimiterStartChar, this.group.delimiterStopChar);
      }

      CommonTokenStream tokens = new CommonTokenStream(lexer);
      STParser p = new STParser(tokens, this.group.errMgr, templateToken);
      STParser.templateAndEOF_return r = null;

      try {
         r = p.templateAndEOF();
      } catch (RecognitionException var16) {
         this.reportMessageAndThrowSTException(tokens, templateToken, p, var16);
         return null;
      }

      if (p.getNumberOfSyntaxErrors() <= 0 && r.getTree() != null) {
         CommonTreeNodeStream nodes = new CommonTreeNodeStream(r.getTree());
         nodes.setTokenStream(tokens);
         CodeGenerator gen = new CodeGenerator(nodes, this.group.errMgr, name, template, templateToken);
         CompiledST impl = null;

         try {
            impl = gen.template(name, args);
            impl.nativeGroup = this.group;
            impl.template = template;
            impl.ast = r.getTree();
            impl.ast.setUnknownTokenBoundaries();
            impl.tokens = tokens;
         } catch (RecognitionException var15) {
            this.group.errMgr.internalError((ST)null, "bad tree structure", var15);
         }

         return impl;
      } else {
         CompiledST impl = new CompiledST();
         impl.defineFormalArgs(args);
         return impl;
      }
   }

   public static CompiledST defineBlankRegion(CompiledST outermostImpl, Token nameToken) {
      String outermostTemplateName = outermostImpl.name;
      String mangled = STGroup.getMangledRegionName(outermostTemplateName, nameToken.getText());
      CompiledST blank = new CompiledST();
      blank.isRegion = true;
      blank.templateDefStartToken = nameToken;
      blank.regionDefType = ST.RegionType.IMPLICIT;
      blank.name = mangled;
      outermostImpl.addImplicitlyDefinedTemplate(blank);
      return blank;
   }

   public static String getNewSubtemplateName() {
      ++subtemplateCount;
      return "_sub" + subtemplateCount;
   }

   protected void reportMessageAndThrowSTException(TokenStream tokens, Token templateToken, Parser parser, RecognitionException re) {
      String msg;
      if (re.token.getType() == -1) {
         msg = "premature EOF";
         this.group.errMgr.compileTimeError(ErrorType.SYNTAX_ERROR, templateToken, re.token, msg);
      } else if (re instanceof NoViableAltException) {
         msg = "'" + re.token.getText() + "' came as a complete surprise to me";
         this.group.errMgr.compileTimeError(ErrorType.SYNTAX_ERROR, templateToken, re.token, msg);
      } else if (tokens.index() == 0) {
         msg = "this doesn't look like a template: \"" + tokens + "\"";
         this.group.errMgr.compileTimeError(ErrorType.SYNTAX_ERROR, templateToken, re.token, msg);
      } else if (tokens.LA(1) == 23) {
         msg = "doesn't look like an expression";
         this.group.errMgr.compileTimeError(ErrorType.SYNTAX_ERROR, templateToken, re.token, msg);
      } else {
         msg = parser.getErrorMessage(re, parser.getTokenNames());
         this.group.errMgr.compileTimeError(ErrorType.SYNTAX_ERROR, templateToken, re.token, msg);
      }

      throw new STException();
   }

   static {
      NUM_OPTIONS = supportedOptions.size();
      defaultOptionValues = new HashMap() {
         {
            this.put("anchor", "true");
            this.put("wrap", "\n");
         }
      };
      funcs = new HashMap() {
         {
            this.put("first", Short.valueOf((short)27));
            this.put("last", Short.valueOf((short)28));
            this.put("rest", Short.valueOf((short)29));
            this.put("trunc", Short.valueOf((short)30));
            this.put("strip", Short.valueOf((short)31));
            this.put("trim", Short.valueOf((short)32));
            this.put("length", Short.valueOf((short)33));
            this.put("strlen", Short.valueOf((short)34));
            this.put("reverse", Short.valueOf((short)35));
         }
      };
      subtemplateCount = 0;
   }
}
