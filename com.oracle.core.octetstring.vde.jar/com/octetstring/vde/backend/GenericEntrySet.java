package com.octetstring.vde.backend;

import com.octetstring.vde.Entry;
import com.octetstring.vde.EntrySet;
import com.octetstring.vde.util.DirectoryException;
import java.util.Vector;

public class GenericEntrySet implements EntrySet {
   private Vector entries = null;
   private Backend myBackend = null;
   private int entryCount = 0;
   private boolean hasMore = false;

   public GenericEntrySet() {
   }

   public GenericEntrySet(Backend myBackend, Vector entries) {
      this.myBackend = myBackend;
      this.entries = entries;
      if (!entries.isEmpty()) {
         this.hasMore = true;
         this.entryCount = 0;
      }

   }

   public Entry getNext() throws DirectoryException {
      if (!this.hasMore) {
         return null;
      } else {
         Entry current = this.myBackend.getByID((Integer)this.entries.elementAt(this.entryCount));
         if (this.entryCount < this.entries.size() - 1) {
            ++this.entryCount;
         } else {
            this.hasMore = false;
         }

         return current;
      }
   }

   public boolean hasMore() {
      return this.hasMore;
   }
}
