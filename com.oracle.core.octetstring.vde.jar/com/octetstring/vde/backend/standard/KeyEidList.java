package com.octetstring.vde.backend.standard;

public class KeyEidList implements Comparable {
   private KeyPtr keyptr = null;
   private int[] eids = new int[0];
   private int eidct = 0;

   public KeyEidList() {
   }

   public KeyEidList(KeyPtr keyptr) {
      this.keyptr = keyptr;
   }

   public int[] getEids() {
      synchronized(this) {
         int[] myeids = new int[this.eidct];
         System.arraycopy(this.eids, 0, myeids, 0, this.eidct);
         return myeids;
      }
   }

   public KeyPtr getKeyptr() {
      return this.keyptr;
   }

   public void addEid(int eid) {
      synchronized(this) {
         if (this.eids != null && this.eidct != 0) {
            if (this.eidct < this.eids.length) {
               this.eids[this.eidct] = eid;
               ++this.eidct;
            } else {
               int[] newEids = new int[this.eids.length * 2];
               System.arraycopy(this.eids, 0, newEids, 0, this.eids.length);
               newEids[this.eids.length] = eid;
               this.eids = newEids;
               this.eids[this.eidct] = eid;
               ++this.eidct;
            }
         } else {
            this.eids = new int[1];
            this.eids[0] = eid;
            this.eidct = 1;
         }
      }
   }

   public int compareTo(Object obj) {
      return this.keyptr.compareTo(((KeyEidList)obj).getKeyptr());
   }

   public boolean equals(Object obj) {
      return this.keyptr.equals(((KeyEidList)obj).getKeyptr());
   }

   public int hashCode() {
      return this.keyptr.hashCode();
   }

   public void removeEid(int eid) {
      synchronized(this) {
         if (this.eids != null) {
            int replaceAt = -1;

            for(int i = 0; i < this.eidct; ++i) {
               if (this.eids[i] == eid) {
                  replaceAt = i;
                  break;
               }
            }

            if (replaceAt != -1) {
               if (replaceAt == this.eidct - 1) {
                  --this.eidct;
               } else {
                  this.eids[replaceAt] = this.eids[this.eidct - 1];
                  --this.eidct;
               }
            }
         }
      }
   }

   public int size() {
      return this.eidct;
   }
}
