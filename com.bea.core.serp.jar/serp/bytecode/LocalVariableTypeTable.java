package serp.bytecode;

import serp.bytecode.visitor.BCVisitor;

public class LocalVariableTypeTable extends LocalTable {
   LocalVariableTypeTable(int nameIndex, Attributes owner) {
      super(nameIndex, owner);
   }

   public LocalVariableType[] getLocalVariableTypes() {
      return (LocalVariableType[])((LocalVariableType[])this.getLocals());
   }

   public LocalVariableType getLocalVariableType(int local) {
      return (LocalVariableType)this.getLocal(local);
   }

   public LocalVariableType getLocalVariableType(String name) {
      return (LocalVariableType)this.getLocal(name);
   }

   public LocalVariableType[] getLocalVariableTypes(String name) {
      return (LocalVariableType[])((LocalVariableType[])this.getLocals(name));
   }

   public LocalVariableType addLocalVariableType(LocalVariableType local) {
      return (LocalVariableType)this.addLocal(local);
   }

   public LocalVariableType addLocalVariableType() {
      return (LocalVariableType)this.addLocal();
   }

   public LocalVariableType addLocalVariableType(String name, String type) {
      return (LocalVariableType)this.addLocal(name, type);
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterLocalVariableTypeTable(this);
      LocalVariableType[] locals = (LocalVariableType[])((LocalVariableType[])this.getLocals());

      for(int i = 0; i < locals.length; ++i) {
         locals[i].acceptVisit(visit);
      }

      visit.exitLocalVariableTypeTable(this);
   }

   protected Local newLocal() {
      return new LocalVariableType(this);
   }

   protected Local[] newLocalArray(int size) {
      return new LocalVariableType[size];
   }
}
