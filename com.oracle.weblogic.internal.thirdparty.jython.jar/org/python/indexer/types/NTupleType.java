package org.python.indexer.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.python.indexer.Indexer;

public class NTupleType extends NType {
   private List eltTypes;

   public NTupleType() {
      this.eltTypes = new ArrayList();
      this.getTable().addSuper(Indexer.idx.builtins.BaseTuple.getTable());
      this.getTable().setPath(Indexer.idx.builtins.BaseTuple.getTable().getPath());
   }

   public NTupleType(List eltTypes) {
      this();
      this.eltTypes = eltTypes;
   }

   public NTupleType(NType elt0) {
      this();
      this.eltTypes.add(elt0);
   }

   public NTupleType(NType elt0, NType elt1) {
      this();
      this.eltTypes.add(elt0);
      this.eltTypes.add(elt1);
   }

   public NTupleType(NType... types) {
      this();
      NType[] var2 = types;
      int var3 = types.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         NType type = var2[var4];
         this.eltTypes.add(type);
      }

   }

   public void setElementTypes(List eltTypes) {
      this.eltTypes = eltTypes;
   }

   public List getElementTypes() {
      return this.eltTypes;
   }

   public void add(NType elt) {
      this.eltTypes.add(elt);
   }

   public NType get(int i) {
      return (NType)this.eltTypes.get(i);
   }

   public NListType toListType() {
      NListType t = new NListType();
      Iterator var2 = this.eltTypes.iterator();

      while(var2.hasNext()) {
         NType e = (NType)var2.next();
         t.add(e);
      }

      return t;
   }

   public void printKids(NType.CyclicTypeRecorder ctr, StringBuilder sb) {
      sb.append("[");
      Iterator var3 = this.eltTypes.iterator();

      while(var3.hasNext()) {
         NType elt = (NType)var3.next();
         elt.print(ctr, sb);
         sb.append(",");
      }

      sb.setLength(sb.length() - 1);
      sb.append("]");
   }
}
