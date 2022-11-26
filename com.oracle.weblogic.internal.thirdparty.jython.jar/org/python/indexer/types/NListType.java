package org.python.indexer.types;

import org.python.indexer.Indexer;

public class NListType extends NType {
   private NType eltType;

   public NListType() {
      this(new NUnknownType());
   }

   public NListType(NType elt0) {
      this.eltType = elt0;
      this.getTable().addSuper(Indexer.idx.builtins.BaseList.getTable());
      this.getTable().setPath(Indexer.idx.builtins.BaseList.getTable().getPath());
   }

   public void setElementType(NType eltType) {
      this.eltType = eltType;
   }

   public NType getElementType() {
      return this.eltType;
   }

   public void add(NType another) {
      this.eltType = NUnionType.union(this.eltType, another);
   }

   public NTupleType toTupleType(int n) {
      NTupleType ret = new NTupleType();

      for(int i = 0; i < n; ++i) {
         ret.add(this.eltType);
      }

      return ret;
   }

   public void printKids(NType.CyclicTypeRecorder ctr, StringBuilder sb) {
      this.eltType.print(ctr, sb);
   }
}
