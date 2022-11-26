package weblogic.entitlement.expression;

public abstract class Identifier extends EExprRep {
   private Object mId = null;

   protected Identifier(Object id) {
      if (id == null) {
         throw new NullPointerException("null id");
      } else {
         this.mId = id;
      }
   }

   protected int getDependsOnInternal() {
      return 1;
   }

   public final Object getId() {
      return this.mId;
   }

   void outForPersist(StringBuffer buf) {
      this.writeTypeId(buf);
      writeStr(this.mId.toString(), buf);
   }

   protected void writeExternalIdentifier(StringBuffer buf) {
      buf.append(this.mId.toString());
   }

   protected void writeExternalForm(StringBuffer buf) {
      if (this.Enclosed) {
         buf.append('{');
      }

      buf.append(this.getIdTag());
      buf.append('(');
      this.writeExternalIdentifier(buf);
      buf.append(')');
      if (this.Enclosed) {
         buf.append('}');
      }

   }

   protected abstract String getIdTag();

   public String toString() {
      return this.mId.toString();
   }
}
