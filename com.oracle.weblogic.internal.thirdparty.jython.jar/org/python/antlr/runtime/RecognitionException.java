package org.python.antlr.runtime;

import org.python.antlr.runtime.tree.CommonTree;
import org.python.antlr.runtime.tree.Tree;
import org.python.antlr.runtime.tree.TreeAdaptor;
import org.python.antlr.runtime.tree.TreeNodeStream;

public class RecognitionException extends Exception {
   public transient IntStream input;
   public int index;
   public Token token;
   public Object node;
   public int c;
   public int line;
   public int charPositionInLine;
   public boolean approximateLineInfo;

   public RecognitionException() {
   }

   public RecognitionException(IntStream input) {
      this.input = input;
      this.index = input.index();
      if (input instanceof TokenStream) {
         this.token = ((TokenStream)input).LT(1);
         this.line = this.token.getLine();
         this.charPositionInLine = this.token.getCharPositionInLine();
      }

      if (input instanceof TreeNodeStream) {
         this.extractInformationFromTreeNodeStream(input);
      } else if (input instanceof CharStream) {
         this.c = input.LA(1);
         this.line = ((CharStream)input).getLine();
         this.charPositionInLine = ((CharStream)input).getCharPositionInLine();
      } else {
         this.c = input.LA(1);
      }

   }

   protected void extractInformationFromTreeNodeStream(IntStream input) {
      TreeNodeStream nodes = (TreeNodeStream)input;
      this.node = nodes.LT(1);
      TreeAdaptor adaptor = nodes.getTreeAdaptor();
      Token payload = adaptor.getToken(this.node);
      int i;
      if (payload != null) {
         this.token = payload;
         if (payload.getLine() <= 0) {
            i = -1;

            for(Object priorNode = nodes.LT(i); priorNode != null; priorNode = nodes.LT(i)) {
               Token priorPayload = adaptor.getToken(priorNode);
               if (priorPayload != null && priorPayload.getLine() > 0) {
                  this.line = priorPayload.getLine();
                  this.charPositionInLine = priorPayload.getCharPositionInLine();
                  this.approximateLineInfo = true;
                  break;
               }

               --i;
            }
         } else {
            this.line = payload.getLine();
            this.charPositionInLine = payload.getCharPositionInLine();
         }
      } else if (this.node instanceof Tree) {
         this.line = ((Tree)this.node).getLine();
         this.charPositionInLine = ((Tree)this.node).getCharPositionInLine();
         if (this.node instanceof CommonTree) {
            this.token = ((CommonTree)this.node).token;
         }
      } else {
         i = adaptor.getType(this.node);
         String text = adaptor.getText(this.node);
         this.token = new CommonToken(i, text);
      }

   }

   public int getUnexpectedType() {
      if (this.input instanceof TokenStream) {
         return this.token.getType();
      } else if (this.input instanceof TreeNodeStream) {
         TreeNodeStream nodes = (TreeNodeStream)this.input;
         TreeAdaptor adaptor = nodes.getTreeAdaptor();
         return adaptor.getType(this.node);
      } else {
         return this.c;
      }
   }
}
