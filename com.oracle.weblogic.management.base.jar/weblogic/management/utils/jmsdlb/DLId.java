package weblogic.management.utils.jmsdlb;

import java.io.Serializable;
import java.util.Comparator;

public class DLId implements Serializable {
   private final int index;
   private final int id;
   private final int[] mask;
   private final String name;

   private DLId(String name, int index) {
      this.name = name;
      this.index = index;
      this.id = index + 1;
      this.mask = DLUtil.BitUtility.getBitFlags(this.id);
   }

   public String toString() {
      return this.getName();
   }

   public String toPrettyString() {
      return this.getClass().getName() + ":" + this.getName() + "={id=" + this.getId() + ", index=" + this.getIndex() + ", mask=" + DLUtil.BitUtility.toMaskString(this.getMask()) + "}";
   }

   public int hashCode() {
      return this.getId();
   }

   public boolean equals(Object o) {
      if (o.getClass().equals(this.getClass())) {
         return this.getId() == ((DLId)o).getId();
      } else {
         return false;
      }
   }

   public int compareTo(DLId o) {
      return this.getId() - o.getId();
   }

   public int getIndex() {
      return this.index;
   }

   public int getId() {
      return this.id;
   }

   public int[] getMask() {
      return (int[])this.mask.clone();
   }

   public String getName() {
      return this.name;
   }

   public Comparator getComparator() {
      return new BitComparator();
   }

   // $FF: synthetic method
   DLId(String x0, int x1, Object x2) {
      this(x0, x1);
   }

   public static class DLIdFactory implements Serializable {
      private final String prefix;
      private int nextIndex = 0;
      private DLId lastID = null;

      public DLIdFactory(String prefix) {
         this.prefix = prefix;
      }

      public DLId getNewID(String name) {
         if (this.prefix != null) {
            name = this.prefix + ":" + name;
         }

         this.lastID = new DLId(name, this.nextIndex++);
         return this.lastID;
      }

      public DLId getCurrentMaxId() {
         return this.lastID;
      }
   }

   private static class BitComparator implements Comparator, Serializable {
      private BitComparator() {
      }

      public int compare(DLId o1, DLId o2) {
         return o1.compareTo(o2);
      }

      // $FF: synthetic method
      BitComparator(Object x0) {
         this();
      }
   }
}
