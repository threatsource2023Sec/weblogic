package com.octetstring.vde.backend.standard;

import com.octetstring.vde.Entry;
import com.octetstring.vde.EntrySet;
import com.octetstring.vde.backend.Backend;
import com.octetstring.vde.util.DirectoryException;
import java.util.Hashtable;

public class StandardEntrySet implements EntrySet {
   private int[] entries = null;
   private Backend myBackend = null;
   private int entryCount = 0;
   private boolean hasMore = false;
   private Hashtable alreadySeen = null;
   private static final DirectoryException DLAPEXCEPTION32 = new DirectoryException(32);

   public StandardEntrySet() {
   }

   public StandardEntrySet(Backend myBackend, int[] entries) {
      this.myBackend = myBackend;
      this.entries = entries;
      if (entries.length > 0) {
         this.hasMore = true;
         this.entryCount = 0;
      }

      this.alreadySeen = new Hashtable();
   }

   public Entry getNext() throws DirectoryException {
      if (!this.hasMore) {
         return null;
      } else {
         boolean entAvail = false;
         Integer nextEID = null;

         while(!entAvail) {
            nextEID = new Integer(this.entries[this.entryCount]);
            if (this.alreadySeen.containsKey(nextEID)) {
               ++this.entryCount;
               if (this.entryCount >= this.entries.length) {
                  this.hasMore = false;
                  return null;
               }
            } else {
               this.alreadySeen.put(nextEID, nextEID);
               entAvail = true;
            }
         }

         if (this.entries[this.entryCount] == -1) {
            throw DLAPEXCEPTION32;
         } else {
            Entry current = this.myBackend.getByID(new Integer(this.entries[this.entryCount]));
            if (this.entryCount < this.entries.length - 1) {
               ++this.entryCount;
            } else {
               this.hasMore = false;
            }

            return current;
         }
      }
   }

   public boolean hasMore() {
      return this.hasMore;
   }
}
