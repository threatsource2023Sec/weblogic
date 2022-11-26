package org.glassfish.grizzly.http.util;

import java.util.Iterator;

abstract class BaseIterator implements Iterator {
   int pos;
   int size;
   int currentPos;
   protected final MimeHeaders headers;

   public BaseIterator(MimeHeaders headers) {
      this.headers = headers;
   }

   protected abstract void findNext();

   public void remove() {
      if (this.currentPos < 0) {
         throw new IllegalStateException("No current element");
      } else {
         this.headers.removeHeader(this.currentPos);
         this.pos = this.currentPos;
         this.currentPos = -1;
         --this.size;
         this.findNext();
      }
   }
}
