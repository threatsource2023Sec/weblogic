package weblogic.management.provider.internal;

import weblogic.descriptor.DescriptorBean;

public class DescriptorBeanCount {
   private final DescriptorBean m;
   private int c;

   public DescriptorBeanCount(DescriptorBean s, int ct) {
      this.m = s;
      this.c = ct;
   }

   public DescriptorBeanCount(DescriptorBean s) {
      this(s, 1);
   }

   public int hashCode() {
      int hash = 5;
      hash = 83 * hash + (this.m != null ? this.m.hashCode() : 0);
      return hash;
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         DescriptorBeanCount other = (DescriptorBeanCount)obj;
         return this.m != null && this.m.equals(other.m);
      }
   }

   public void increment() {
      ++this.c;
   }

   public int getCount() {
      return this.c;
   }

   public DescriptorBean getCounted() {
      return this.m;
   }
}
