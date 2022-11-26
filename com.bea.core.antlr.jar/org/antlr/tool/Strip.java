package org.antlr.tool;

import java.util.List;
import org.antlr.grammar.v3.ANTLRv3Lexer;
import org.antlr.grammar.v3.ANTLRv3Parser;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenRewriteStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.TreeAdaptor;
import org.antlr.runtime.tree.TreeWizard;

public class Strip {
   protected String filename;
   protected TokenRewriteStream tokens;
   protected boolean tree_option = false;
   protected String[] args;

   public static void main(String[] args) throws Exception {
      Strip s = new Strip(args);
      s.parseAndRewrite();
      System.out.println(s.tokens);
   }

   public Strip(String[] args) {
      this.args = args;
   }

   public TokenRewriteStream getTokenStream() {
      return this.tokens;
   }

   public void parseAndRewrite() throws Exception {
      this.processArgs(this.args);
      Object input;
      if (this.filename != null) {
         input = new ANTLRFileStream(this.filename);
      } else {
         input = new ANTLRInputStream(System.in);
      }

      ANTLRv3Lexer lex = new ANTLRv3Lexer((CharStream)input);
      this.tokens = new TokenRewriteStream(lex);
      ANTLRv3Parser g = new ANTLRv3Parser(this.tokens);
      ANTLRv3Parser.grammarDef_return r = g.grammarDef();
      CommonTree t = r.getTree();
      if (this.tree_option) {
         System.out.println(t.toStringTree());
      }

      this.rewrite(g.getTreeAdaptor(), t, g.getTokenNames());
   }

   public void rewrite(TreeAdaptor adaptor, CommonTree t, String[] tokenNames) throws Exception {
      TreeWizard wiz = new TreeWizard(adaptor, tokenNames);
      wiz.visit(t, 4, new TreeWizard.Visitor() {
         public void visit(Object t) {
            Strip.ACTION(Strip.this.tokens, (CommonTree)t);
         }
      });
      wiz.visit(t, 12, new TreeWizard.Visitor() {
         public void visit(Object t) {
            CommonTree a = (CommonTree)t;
            CommonTree action = null;
            if (a.getChildCount() == 2) {
               action = (CommonTree)a.getChild(1);
            } else if (a.getChildCount() == 3) {
               action = (CommonTree)a.getChild(2);
            }

            if (action.getType() == 4) {
               Strip.this.tokens.delete(a.getTokenStartIndex(), a.getTokenStopIndex());
               Strip.killTrailingNewline(Strip.this.tokens, action.getTokenStopIndex());
            }

         }
      });
      wiz.visit(t, 9, new TreeWizard.Visitor() {
         public void visit(Object t) {
            CommonTree a = (CommonTree)t;
            a = (CommonTree)a.getChild(0);
            Strip.this.tokens.delete(a.token.getTokenIndex());
            Strip.killTrailingNewline(Strip.this.tokens, a.token.getTokenIndex());
         }
      });
      wiz.visit(t, 47, new TreeWizard.Visitor() {
         public void visit(Object t) {
            CommonTree a = (CommonTree)t;
            CommonTree ret = (CommonTree)a.getChild(0);
            Strip.this.tokens.delete(a.token.getTokenIndex(), ret.token.getTokenIndex());
         }
      });
      wiz.visit(t, 53, new TreeWizard.Visitor() {
         public void visit(Object t) {
            CommonTree a = (CommonTree)t;
            Strip.this.tokens.replace(a.token.getTokenIndex(), "/*" + a.getText() + "*/");
         }
      });
      wiz.visit(t, 29, new TreeWizard.Visitor() {
         public void visit(Object t) {
            CommonTree a = (CommonTree)t;
            String text = Strip.this.tokens.toString(a.getTokenStartIndex(), a.getTokenStopIndex());
            Strip.this.tokens.replace(a.getTokenStartIndex(), a.getTokenStopIndex(), "/*" + text + "*/");
         }
      });
      wiz.visit(t, 52, new TreeWizard.Visitor() {
         public void visit(Object t) {
            CommonTree a = (CommonTree)t;
            Strip.this.tokens.delete(a.getTokenStartIndex(), a.getTokenStopIndex());
            Strip.killTrailingNewline(Strip.this.tokens, a.getTokenStopIndex());
         }
      });
      wiz.visit(t, 11, new TreeWizard.Visitor() {
         public void visit(Object t) {
            CommonTree a = (CommonTree)t;
            if (a.getParent().getType() == 51) {
               Strip.this.tokens.delete(a.getTokenStartIndex(), a.getTokenStopIndex());
            }

         }
      });
      wiz.visit(t, 33, new TreeWizard.Visitor() {
         public void visit(Object t) {
            CommonTree a = (CommonTree)t;
            if (!a.hasAncestor(42)) {
               CommonTree child = (CommonTree)a.getChild(0);
               Strip.this.tokens.delete(a.token.getTokenIndex());
               Strip.this.tokens.delete(child.token.getTokenIndex());
            }

         }
      });
      wiz.visit(t, 36, new TreeWizard.Visitor() {
         public void visit(Object t) {
            CommonTree a = (CommonTree)t;
            CommonTree child = (CommonTree)a.getChild(0);
            Strip.this.tokens.delete(a.token.getTokenIndex());
            Strip.this.tokens.delete(child.token.getTokenIndex());
         }
      });
      wiz.visit(t, 48, new TreeWizard.Visitor() {
         public void visit(Object t) {
            CommonTree a = (CommonTree)t;
            CommonTree child = (CommonTree)a.getChild(0);
            int stop = child.getTokenStopIndex();
            if (child.getType() == 53) {
               CommonTree rew = (CommonTree)a.getChild(1);
               stop = rew.getTokenStopIndex();
            }

            Strip.this.tokens.delete(a.token.getTokenIndex(), stop);
            Strip.killTrailingNewline(Strip.this.tokens, stop);
         }
      });
      wiz.visit(t, 49, new TreeWizard.Visitor() {
         public void visit(Object t) {
            Strip.this.tokens.delete(((CommonTree)t).token.getTokenIndex());
         }
      });
      wiz.visit(t, 14, new TreeWizard.Visitor() {
         public void visit(Object t) {
            Strip.this.tokens.delete(((CommonTree)t).token.getTokenIndex());
         }
      });
   }

   public static void ACTION(TokenRewriteStream tokens, CommonTree t) {
      CommonTree parent = (CommonTree)t.getParent();
      int ptype = parent.getType();
      if (ptype != 52 && ptype != 12) {
         CommonTree root = (CommonTree)t.getAncestor(50);
         if (root != null) {
            CommonTree rule = (CommonTree)root.getChild(0);
            if (!Character.isUpperCase(rule.getText().charAt(0))) {
               tokens.delete(t.getTokenStartIndex(), t.getTokenStopIndex());
               killTrailingNewline(tokens, t.token.getTokenIndex());
            }
         }

      }
   }

   private static void killTrailingNewline(TokenRewriteStream tokens, int index) {
      List all = tokens.getTokens();
      Token tok = (Token)all.get(index);
      Token after = (Token)all.get(index + 1);
      String ws = after.getText();
      if (ws.startsWith("\n")) {
         if (ws.length() > 1) {
            int space = ws.indexOf(32);
            int tab = ws.indexOf(9);
            if (ws.startsWith("\n") && space >= 0 || tab >= 0) {
               return;
            }

            ws = ws.replaceAll("\n", "");
            tokens.replace(after.getTokenIndex(), ws);
         } else {
            tokens.delete(after.getTokenIndex());
         }
      }

   }

   public void processArgs(String[] args) {
      if (args != null && args.length != 0) {
         for(int i = 0; i < args.length; ++i) {
            if (args[i].equals("-tree")) {
               this.tree_option = true;
            } else if (args[i].charAt(0) != '-') {
               this.filename = args[i];
            }
         }

      } else {
         help();
      }
   }

   private static void help() {
      System.err.println("usage: java org.antlr.tool.Strip [args] file.g");
      System.err.println("  -tree      print out ANTLR grammar AST");
   }
}
