package serp.bytecode;

import serp.bytecode.visitor.BCVisitor;

public class LocalVariableTable extends LocalTable {
   LocalVariableTable(int nameIndex, Attributes owner) {
      super(nameIndex, owner);
   }

   public LocalVariable[] getLocalVariables() {
      return (LocalVariable[])((LocalVariable[])this.getLocals());
   }

   public LocalVariable getLocalVariable(int local) {
      return (LocalVariable)this.getLocal(local);
   }

   public LocalVariable getLocalVariable(String name) {
      return (LocalVariable)this.getLocal(name);
   }

   public LocalVariable[] getLocalVariables(String name) {
      return (LocalVariable[])((LocalVariable[])this.getLocals(name));
   }

   public LocalVariable addLocalVariable(LocalVariable local) {
      return (LocalVariable)this.addLocal(local);
   }

   public LocalVariable addLocalVariable() {
      return (LocalVariable)this.addLocal();
   }

   public LocalVariable addLocalVariable(String name, String type) {
      return (LocalVariable)this.addLocal(name, type);
   }

   public LocalVariable addLocalVariable(String name, Class type) {
      String typeName = type == null ? null : type.getName();
      return this.addLocalVariable(name, typeName);
   }

   public LocalVariable addLocalVariable(String name, BCClass type) {
      String typeName = type == null ? null : type.getName();
      return this.addLocalVariable(name, typeName);
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterLocalVariableTable(this);
      LocalVariable[] locals = (LocalVariable[])((LocalVariable[])this.getLocals());

      for(int i = 0; i < locals.length; ++i) {
         locals[i].acceptVisit(visit);
      }

      visit.exitLocalVariableTable(this);
   }

   protected Local newLocal() {
      return new LocalVariable(this);
   }

   protected Local[] newLocalArray(int size) {
      return new LocalVariable[size];
   }
}
