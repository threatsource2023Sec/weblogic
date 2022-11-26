package org.python.indexer.types;

import org.python.indexer.Indexer;

public class NFuncType extends NType {
   private NType returnType;

   public NFuncType() {
      this(new NUnknownType());
   }

   public NFuncType(NType from, NType to) {
      this(to);
   }

   public NFuncType(NType from1, NType from2, NType to) {
      this(to);
   }

   public NFuncType(NType to) {
      this.setReturnType(to);
      this.getTable().addSuper(Indexer.idx.builtins.BaseFunction.getTable());
      this.getTable().setPath(Indexer.idx.builtins.BaseFunction.getTable().getPath());
   }

   public void setReturnType(NType to) {
      if (to == null) {
         to = new NUnknownType();
      }

      this.returnType = (NType)to;
   }

   public NType getReturnType() {
      return this.returnType;
   }

   public void printKids(NType.CyclicTypeRecorder ctr, StringBuilder sb) {
      sb.append("_:");
      this.returnType.print(ctr, sb);
   }
}
