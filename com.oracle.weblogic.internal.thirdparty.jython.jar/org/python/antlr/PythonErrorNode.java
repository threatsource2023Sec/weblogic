package org.python.antlr;

import org.python.antlr.runtime.RecognitionException;
import org.python.antlr.runtime.Token;
import org.python.antlr.runtime.TokenStream;
import org.python.antlr.runtime.tree.CommonErrorNode;

public class PythonErrorNode extends PythonTree {
   private CommonErrorNode errorNode;

   public PythonErrorNode(TokenStream input, Token start, Token stop, RecognitionException e) {
      this.errorNode = new CommonErrorNode(input, start, stop, e);
   }

   public PythonErrorNode(CommonErrorNode errorNode) {
      this.errorNode = errorNode;
   }

   public boolean isNil() {
      return this.errorNode.isNil();
   }

   public int getAntlrType() {
      return this.errorNode.getType();
   }

   public String getText() {
      return this.errorNode.getText();
   }

   public String toString() {
      return this.errorNode.toString();
   }
}
