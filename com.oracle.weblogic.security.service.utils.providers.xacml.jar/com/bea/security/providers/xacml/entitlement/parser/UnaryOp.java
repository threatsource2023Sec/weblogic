package com.bea.security.providers.xacml.entitlement.parser;

import com.bea.security.xacml.IOException;
import java.io.Writer;

public abstract class UnaryOp implements Expression {
   private Expression op;

   UnaryOp() {
      this((Expression)null);
   }

   public UnaryOp(Expression op) {
      this.op = op;
   }

   public Expression getOp() {
      return this.op;
   }

   void setOp(Expression op) {
      this.op = op;
   }

   boolean hasOp() {
      return this.op != null;
   }

   protected abstract char getPersistantTypeId();

   private void writePersistantTypeId(Writer w) throws IOException {
      try {
         w.write((char)(this.getPersistantTypeId() | (this.hasOp() ? 128 : 0)));
      } catch (java.io.IOException var3) {
         throw new IOException(var3);
      }
   }

   public void writePersistantForm(Writer w) throws IOException {
      this.writePersistantTypeId(w);

      try {
         w.write((char)(this.hasOp() ? 1 : 0));
      } catch (java.io.IOException var3) {
         throw new IOException(var3);
      }

      if (this.hasOp()) {
         this.getOp().writePersistantForm(w);
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.getPersistantTypeId());
      if (this.hasOp()) {
         Expression op = this.getOp();
         if (!(op instanceof BinaryOp) && !(op instanceof UnaryOp)) {
            sb.append(op.toString());
         } else {
            sb.append('{');
            sb.append(op.toString());
            sb.append('}');
         }
      } else {
         sb.append("not-set");
      }

      return sb.toString();
   }
}
