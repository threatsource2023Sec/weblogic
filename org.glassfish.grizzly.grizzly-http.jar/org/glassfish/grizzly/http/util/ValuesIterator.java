package org.glassfish.grizzly.http.util;

final class ValuesIterator extends BaseIterator {
   DataChunk next;
   final String name;

   ValuesIterator(MimeHeaders headers, String name, boolean trailersOnly) {
      super(headers);
      this.name = name;
      this.pos = trailersOnly ? headers.mark : 0;
      this.size = headers.size();
      this.findNext();
   }

   protected void findNext() {
      for(this.next = null; this.pos < this.size; ++this.pos) {
         DataChunk n1 = this.headers.getName(this.pos);
         if (n1.equalsIgnoreCase(this.name)) {
            this.next = this.headers.getValue(this.pos);
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
      String current = this.next.toString();
      this.findNext();
      return current;
   }
}
