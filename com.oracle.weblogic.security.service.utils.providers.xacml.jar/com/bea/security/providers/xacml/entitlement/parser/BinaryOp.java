package com.bea.security.providers.xacml.entitlement.parser;

import com.bea.security.xacml.IOException;
import java.io.Writer;

public abstract class BinaryOp implements Expression {
   private Expression left;
   private Expression right;

   BinaryOp(Expression left) {
      this(left, (Expression)null);
   }

   public BinaryOp(Expression left, Expression right) {
      this.left = left;
      this.right = right;
   }

   public Expression getLeft() {
      return this.left;
   }

   public Expression getRight() {
      return this.right;
   }

   void setRight(Expression right) {
      this.right = right;
   }

   boolean hasRight() {
      return this.right != null;
   }

   protected abstract char getPersistantTypeId();

   private void writePersistantTypeId(Writer w) throws IOException {
      try {
         w.write((char)(this.getPersistantTypeId() | 128));
      } catch (java.io.IOException var3) {
         throw new IOException(var3);
      }
   }

   public void writePersistantForm(Writer w) throws IOException {
      this.writePersistantTypeId(w);

      try {
         w.write((char)(this.hasRight() ? 2 : 1));
      } catch (java.io.IOException var3) {
         throw new IOException(var3);
      }

      this.getLeft().writePersistantForm(w);
      if (this.hasRight()) {
         this.getRight().writePersistantForm(w);
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      Expression left = this.getLeft();
      if (this.getClass().equals(left.getClass()) || !(left instanceof BinaryOp) && !(left instanceof UnaryOp)) {
         sb.append(left.toString());
      } else {
         sb.append('{');
         sb.append(left.toString());
         sb.append('}');
      }

      sb.append(this.getPersistantTypeId());
      if (this.hasRight()) {
         Expression right = this.getRight();
         if (this.getClass().equals(right.getClass()) || !(right instanceof BinaryOp) && !(right instanceof UnaryOp)) {
            sb.append(right.toString());
         } else {
            sb.append('{');
            sb.append(right.toString());
            sb.append('}');
         }
      } else {
         sb.append("not-set");
      }

      return sb.toString();
   }
}
