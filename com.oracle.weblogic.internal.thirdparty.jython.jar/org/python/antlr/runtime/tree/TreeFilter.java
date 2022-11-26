package org.python.antlr.runtime.tree;

import org.python.antlr.runtime.RecognitionException;
import org.python.antlr.runtime.RecognizerSharedState;
import org.python.antlr.runtime.TokenStream;

public class TreeFilter extends TreeParser {
   protected TokenStream originalTokenStream;
   protected TreeAdaptor originalAdaptor;
   fptr topdown_fptr = new fptr() {
      public void rule() throws RecognitionException {
         TreeFilter.this.topdown();
      }
   };
   fptr bottomup_fptr = new fptr() {
      public void rule() throws RecognitionException {
         TreeFilter.this.bottomup();
      }
   };

   public TreeFilter(TreeNodeStream input) {
      super(input);
   }

   public TreeFilter(TreeNodeStream input, RecognizerSharedState state) {
      super(input, state);
      this.originalAdaptor = input.getTreeAdaptor();
      this.originalTokenStream = input.getTokenStream();
   }

   public void applyOnce(Object t, fptr whichRule) {
      if (t != null) {
         try {
            this.state = new RecognizerSharedState();
            this.input = new CommonTreeNodeStream(this.originalAdaptor, t);
            ((CommonTreeNodeStream)this.input).setTokenStream(this.originalTokenStream);
            this.setBacktrackingLevel(1);
            whichRule.rule();
            this.setBacktrackingLevel(0);
         } catch (RecognitionException var4) {
         }

      }
   }

   public void downup(Object t) {
      TreeVisitor v = new TreeVisitor(new CommonTreeAdaptor());
      TreeVisitorAction actions = new TreeVisitorAction() {
         public Object pre(Object t) {
            TreeFilter.this.applyOnce(t, TreeFilter.this.topdown_fptr);
            return t;
         }

         public Object post(Object t) {
            TreeFilter.this.applyOnce(t, TreeFilter.this.bottomup_fptr);
            return t;
         }
      };
      v.visit(t, actions);
   }

   public void topdown() throws RecognitionException {
   }

   public void bottomup() throws RecognitionException {
   }

   public interface fptr {
      void rule() throws RecognitionException;
   }
}
