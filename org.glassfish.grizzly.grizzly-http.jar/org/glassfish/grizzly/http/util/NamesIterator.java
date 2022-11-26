package org.glassfish.grizzly.http.util;

class NamesIterator extends BaseIterator {
   String next;

   NamesIterator(MimeHeaders headers, boolean trailersOnly) {
      super(headers);
      this.pos = trailersOnly ? headers.mark : 0;
      this.size = headers.size();
      this.findNext();
   }

   protected void findNext() {
      for(this.next = null; this.pos < this.size; ++this.pos) {
         this.next = this.headers.getName(this.pos).toString();

         for(int j = 0; j < this.pos; ++j) {
            if (this.headers.getName(j).equalsIgnoreCase(this.next)) {
               this.next = null;
               break;
            }
         }

         if (this.next != null) {
            break;
         }
      }

      ++this.pos;
   }

   public boolean hasNext() {
      return this.next != null;
   }

   public String next() {
      this.currentPos = this.pos - 1;
      String current = this.next;
      this.findNext();
      return current;
   }
}
