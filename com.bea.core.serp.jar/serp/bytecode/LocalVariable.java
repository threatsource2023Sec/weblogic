package serp.bytecode;

import serp.bytecode.visitor.BCVisitor;
import serp.util.Strings;

public class LocalVariable extends Local {
   LocalVariable(LocalVariableTable owner) {
      super(owner);
   }

   public LocalVariableTable getLocalVariableTable() {
      return (LocalVariableTable)this.getTable();
   }

   public Class getType() {
      String type = this.getTypeName();
      return type == null ? null : Strings.toClass(type, this.getClassLoader());
   }

   public BCClass getTypeBC() {
      String type = this.getTypeName();
      return type == null ? null : this.getProject().loadClass(type, this.getClassLoader());
   }

   public void setType(Class type) {
      if (type == null) {
         this.setType((String)((String)null));
      } else {
         this.setType((String)type.getName());
      }

   }

   public void setType(BCClass type) {
      if (type == null) {
         this.setType((String)((String)null));
      } else {
         this.setType((String)type.getName());
      }

   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterLocalVariable(this);
      visit.exitLocalVariable(this);
   }
}
