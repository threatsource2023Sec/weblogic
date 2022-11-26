package weblogic.security.providers.utils;

import java.util.List;

public class BusinessObjectLister implements ListerManager.Lister {
   private List businessObjectList = null;
   private int currentIndex;
   private int maxToReturn;

   public BusinessObjectLister(List list, int maxToReturn) {
      this.businessObjectList = list;
      this.currentIndex = 0;
      this.maxToReturn = maxToReturn;
   }

   public boolean haveCurrent() {
      return this.businessObjectList != null && this.currentIndex < this.businessObjectList.size() && (this.maxToReturn <= 0 || this.currentIndex < this.maxToReturn);
   }

   public Object getCurrentBusinessObject() {
      return this.haveCurrent() ? this.businessObjectList.get(this.currentIndex) : null;
   }

   public void advance() {
      if (this.haveCurrent()) {
         ++this.currentIndex;
      }

   }

   public void close() {
      this.businessObjectList = null;
   }
}
