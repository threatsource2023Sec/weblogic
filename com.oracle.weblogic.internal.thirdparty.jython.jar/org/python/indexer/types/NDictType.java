package org.python.indexer.types;

import org.python.indexer.Indexer;

public class NDictType extends NType {
   private NType keyType;
   private NType valueType;

   public NDictType() {
      this(new NUnknownType(), new NUnknownType());
   }

   public NDictType(NType key0, NType val0) {
      this.keyType = key0;
      this.valueType = val0;
      this.getTable().addSuper(Indexer.idx.builtins.BaseDict.getTable());
      this.getTable().setPath(Indexer.idx.builtins.BaseDict.getTable().getPath());
   }

   public void setKeyType(NType keyType) {
      this.keyType = keyType;
   }

   public NType getKeyType() {
      return this.keyType;
   }

   public void setValueType(NType valType) {
      this.valueType = valType;
   }

   public NType getValueType() {
      return this.valueType;
   }

   public void add(NType key, NType val) {
      this.keyType = NUnionType.union(this.keyType, key);
      this.valueType = NUnionType.union(this.valueType, val);
   }

   public NTupleType toTupleType(int n) {
      NTupleType ret = new NTupleType();

      for(int i = 0; i < n; ++i) {
         ret.add(this.keyType);
      }

      return ret;
   }

   public void printKids(NType.CyclicTypeRecorder ctr, StringBuilder sb) {
      this.keyType.print(ctr, sb);
      sb.append(":");
      this.valueType.print(ctr, sb);
   }
}
