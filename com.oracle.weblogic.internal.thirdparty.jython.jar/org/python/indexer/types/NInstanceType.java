package org.python.indexer.types;

import org.python.indexer.Scope;

public class NInstanceType extends NType {
   private NType classType;

   public NInstanceType() {
      this.classType = new NUnknownType();
   }

   public NInstanceType(NType c) {
      this.setTable(c.getTable().copy(Scope.Type.INSTANCE));
      this.getTable().addSuper(c.getTable());
      this.getTable().setPath(c.getTable().getPath());
      this.classType = c;
   }

   public NType getClassType() {
      return this.classType;
   }

   public void printKids(NType.CyclicTypeRecorder ctr, StringBuilder sb) {
      this.classType.print(ctr, sb);
   }
}
