package weblogic.utils.enumerations;

import java.util.Enumeration;
import java.util.NoSuchElementException;

public class CombinedEnumeration implements Enumeration {
   private Enumeration[] enums;
   private int curEnumIndex = 0;

   public CombinedEnumeration(Enumeration[] enums) {
      this.enums = enums;
   }

   public boolean hasMoreElements() {
      this.incrementIndex();
      return this.curEnumIndex < this.enums.length;
   }

   public Object nextElement() {
      this.incrementIndex();
      if (this.curEnumIndex < this.enums.length) {
         return this.enums[this.curEnumIndex].nextElement();
      } else {
         throw new NoSuchElementException();
      }
   }

   private void incrementIndex() {
      while(this.curEnumIndex < this.enums.length) {
         if (this.enums[this.curEnumIndex] != null && this.enums[this.curEnumIndex].hasMoreElements()) {
            return;
         }

         ++this.curEnumIndex;
      }

   }
}
