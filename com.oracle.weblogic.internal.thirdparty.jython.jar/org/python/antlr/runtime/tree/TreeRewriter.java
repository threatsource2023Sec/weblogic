package org.python.antlr.runtime.tree;

import org.python.antlr.runtime.RecognitionException;
import org.python.antlr.runtime.RecognizerSharedState;
import org.python.antlr.runtime.TokenStream;

public class TreeRewriter extends TreeParser {
   protected TokenStream originalTokenStream;
   protected TreeAdaptor originalAdaptor;
   fptr topdown_fptr = new fptr() {
      public Object rule() throws RecognitionException {
         return TreeRewriter.this.topdown();
      }
   };
   fptr bottomup_ftpr = new fptr() {
      public Object rule() throws RecognitionException {
         return TreeRewriter.this.bottomup();
      }
   };

   public TreeRewriter(TreeNodeStream input) {
      super(input);
   }

   public TreeRewriter(TreeNodeStream input, RecognizerSharedState state) {
      super(input, state);
      this.originalAdaptor = input.getTreeAdaptor();
      this.originalTokenStream = input.getTokenStream();
   }

   public Object applyOnce(Object t, fptr whichRule) {
      if (t == null) {
         return null;
      } else {
         try {
            this.state = new RecognizerSharedState();
            this.input = new CommonTreeNodeStream(this.originalAdaptor, t);
            ((CommonTreeNodeStream)this.input).setTokenStream(this.originalTokenStream);
            this.setBacktrackingLevel(1);
            TreeRuleReturnScope r = (TreeRuleReturnScope)whichRule.rule();
            this.setBacktrackingLevel(0);
            if (this.failed()) {
               return t;
            } else {
               if (r != null && !t.equals(r.getTree()) && r.getTree() != null) {
                  System.out.println(((CommonTree)t).toStringTree() + " -> " + ((CommonTree)r.getTree()).toStringTree());
               }

               return r != null && r.getTree() != null ? r.getTree() : t;
            }
         } catch (RecognitionException var4) {
            return t;
         }
      }
   }

   public Object applyRepeatedly(Object t, fptr whichRule) {
      Object u;
      for(boolean treeChanged = true; treeChanged; t = u) {
         u = this.applyOnce(t, whichRule);
         treeChanged = !t.equals(u);
      }

      return t;
   }

   public Object downup(Object t) {
      TreeVisitor v = new TreeVisitor(new CommonTreeAdaptor());
      TreeVisitorAction actions = new TreeVisitorAction() {
         public Object pre(Object t) {
            return TreeRewriter.this.applyOnce(t, TreeRewriter.this.topdown_fptr);
         }

         public Object post(Object t) {
            return TreeRewriter.this.applyRepeatedly(t, TreeRewriter.this.bottomup_ftpr);
         }
      };
      t = v.visit(t, actions);
      return t;
   }

   public Object topdown() throws RecognitionException {
      return null;
   }

   public Object bottomup() throws RecognitionException {
      return null;
   }

   public interface fptr {
      Object rule() throws RecognitionException;
   }
}
