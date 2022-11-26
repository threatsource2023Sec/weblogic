package org.python.indexer.types;

public class NUnknownType extends NType {
   private NType pointer = null;

   public static void point(NType u, NType v) {
      if (u.isUnknownType()) {
         ((NUnknownType)u).pointer = v;
      }

   }

   public static NType follow(NType t) {
      if (t instanceof NUnknownType) {
         NUnknownType tv = (NUnknownType)t;
         return (NType)(tv.pointer != null ? follow(tv.pointer) : tv);
      } else {
         return t;
      }
   }

   public void printKids(NType.CyclicTypeRecorder ctr, StringBuilder sb) {
      if (this.pointer == null) {
         sb.append("null");
      } else {
         this.pointer.print(ctr, sb);
      }

   }
}
