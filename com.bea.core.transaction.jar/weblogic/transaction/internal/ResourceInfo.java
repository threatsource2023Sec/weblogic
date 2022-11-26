package weblogic.transaction.internal;

import java.util.ArrayList;

class ResourceInfo {
   private String name;
   private ArrayList scInfoList;

   protected ResourceInfo() {
   }

   public String toString() {
      return this.name;
   }

   ResourceInfo(String aName) {
      this.name = aName;
   }

   final String getName() {
      return this.name;
   }

   final ArrayList getSCInfoList() {
      return this.scInfoList;
   }

   void addSC(SCInfo sci) {
      if (this.scInfoList == null) {
         this.scInfoList = new ArrayList();
      }

      if (!this.scInfoList.contains(sci)) {
         this.scInfoList.add(sci);
      }
   }

   protected final ArrayList getOrCreateSCInfoList() {
      if (this.scInfoList == null) {
         this.scInfoList = new ArrayList(2);
      }

      return this.scInfoList;
   }

   protected final void setName(String n) {
      this.name = n;
   }

   void decrementTxRefCount() {
   }

   void incrementTxRefCount() {
   }
}
