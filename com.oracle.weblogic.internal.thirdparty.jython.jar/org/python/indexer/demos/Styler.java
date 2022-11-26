package org.python.indexer.demos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.python.antlr.PythonLexer;
import org.python.antlr.PythonTree;
import org.python.antlr.RecordingErrorHandler;
import org.python.antlr.runtime.ANTLRStringStream;
import org.python.antlr.runtime.BaseRecognizer;
import org.python.antlr.runtime.CommonToken;
import org.python.antlr.runtime.RecognitionException;
import org.python.antlr.runtime.RecognizerSharedState;
import org.python.antlr.runtime.Token;
import org.python.indexer.Indexer;
import org.python.indexer.StyleRun;
import org.python.indexer.ast.DefaultNodeVisitor;
import org.python.indexer.ast.NFunctionDef;
import org.python.indexer.ast.NModule;
import org.python.indexer.ast.NName;
import org.python.indexer.ast.NNode;
import org.python.indexer.ast.NNum;
import org.python.indexer.ast.NStr;

class Styler extends DefaultNodeVisitor {
   static final Pattern BUILTIN = Pattern.compile("None|True|False|NotImplemented|Ellipsis|__debug__");
   private static final Pattern TRISTRING_PREFIX = Pattern.compile("^[ruRU]{0,2}['\"]{3}");
   private Indexer indexer;
   private String source;
   private String path;
   private List styles = new ArrayList();
   private Linker linker;
   private Set docOffsets = new HashSet();

   public Styler(Indexer idx, Linker linker) {
      this.indexer = idx;
      this.linker = linker;
   }

   public List addStyles(String path, String src) throws Exception {
      this.path = path;
      this.source = src;
      NModule m = this.indexer.getAstForFile(path);
      if (m != null) {
         m.visit(this);
         this.highlightLexicalTokens();
      }

      return this.styles;
   }

   public boolean visit(NName n) {
      NNode parent = n.getParent();
      if (!(parent instanceof NFunctionDef)) {
         if (BUILTIN.matcher(n.id).matches()) {
            this.addStyle(n, StyleRun.Type.BUILTIN);
            return true;
         } else {
            return true;
         }
      } else {
         NFunctionDef fn = (NFunctionDef)parent;
         if (n == fn.name) {
            this.addStyle(n, StyleRun.Type.FUNCTION);
         } else if (n == fn.kwargs || n == fn.varargs) {
            this.addStyle(n, StyleRun.Type.PARAMETER);
         }

         return true;
      }
   }

   public boolean visit(NNum n) {
      this.addStyle(n, StyleRun.Type.NUMBER);
      return true;
   }

   public boolean visit(NStr n) {
      String s = this.sourceString(n.start(), n.end());
      if (TRISTRING_PREFIX.matcher(s).lookingAt()) {
         this.addStyle(n.start(), n.end() - n.start(), StyleRun.Type.DOC_STRING);
         this.docOffsets.add(n.start());
         this.highlightDocString(n);
      }

      return true;
   }

   private void highlightDocString(NStr node) {
      String s = this.sourceString(node.start(), node.end());
      DocStringParser dsp = new DocStringParser(s, node, this.linker);
      dsp.setResolveReferences(true);
      this.styles.addAll(dsp.highlight());
   }

   private void highlightLexicalTokens() {
      RecognizerSharedState state = new RecognizerSharedState();
      state.errorRecovery = true;
      PythonLexer lex = new PythonLexer(new ANTLRStringStream(this.source) {
         public String getSourceName() {
            return Styler.this.path;
         }
      }, state);
      lex.setErrorHandler(new RecordingErrorHandler() {
         public void error(String message, PythonTree t) {
         }

         public void reportError(BaseRecognizer br, RecognitionException re) {
         }
      });

      Token tok;
      while((tok = lex.nextToken()) != Token.EOF_TOKEN) {
         int beg;
         int end;
         switch (tok.getType()) {
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 23:
            case 24:
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
               beg = ((CommonToken)tok).getStartIndex();
               end = ((CommonToken)tok).getStopIndex();
               this.addStyle(beg, end - beg + 1, StyleRun.Type.KEYWORD);
            case 22:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            default:
               break;
            case 90:
               beg = ((CommonToken)tok).getStartIndex();
               end = ((CommonToken)tok).getStopIndex();
               if (!this.docOffsets.contains(beg)) {
                  this.addStyle(beg, end - beg + 1, StyleRun.Type.STRING);
               }
               break;
            case 96:
               beg = ((CommonToken)tok).getStartIndex();
               end = ((CommonToken)tok).getStopIndex();
               String comment = tok.getText();
               this.addStyle(beg, end - beg + 1, StyleRun.Type.COMMENT);
         }
      }

   }

   private void addStyle(NNode e, int start, int len, StyleRun.Type type) {
      if (e != null && e.getFile() != null) {
         this.addStyle(start, len, type);
      }
   }

   private void addStyle(NNode e, StyleRun.Type type) {
      if (e != null) {
         this.addStyle(e, e.start(), e.end() - e.start(), type);
      }

   }

   private void addStyle(int beg, int len, StyleRun.Type type) {
      this.styles.add(new StyleRun(type, beg, len));
   }

   private String sourceString(NNode e) {
      return this.sourceString(e.start(), e.end());
   }

   private String sourceString(int beg, int end) {
      int a = Math.max(beg, 0);
      int b = Math.min(end, this.source.length());
      b = Math.max(b, 0);

      try {
         return this.source.substring(a, b);
      } catch (StringIndexOutOfBoundsException var6) {
         System.out.println("whoops: beg=" + a + ", end=" + b + ", len=" + this.source.length());
         return "";
      }
   }
}
