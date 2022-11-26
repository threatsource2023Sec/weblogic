package org.apache.openjpa.lib.meta;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class MetaDataIteratorChain implements MetaDataIterator {
   private List _itrs = null;
   private int _cur = -1;
   private MetaDataIterator _itr = null;

   public MetaDataIteratorChain() {
   }

   public MetaDataIteratorChain(MetaDataIterator itr1, MetaDataIterator itr2) {
      this._itrs = new ArrayList(2);
      this._itrs.add(itr1);
      this._itrs.add(itr2);
   }

   public void addIterator(MetaDataIterator itr) {
      if (this._cur != -1) {
         throw new IllegalStateException();
      } else {
         if (this._itrs == null) {
            this._itrs = new ArrayList(4);
         }

         this._itrs.add(itr);
      }
   }

   public boolean hasNext() throws IOException {
      if (this._itrs == null) {
         return false;
      } else {
         if (this._cur == -1) {
            this._cur = 0;
         }

         while(this._cur < this._itrs.size()) {
            MetaDataIterator itr = (MetaDataIterator)this._itrs.get(this._cur);
            if (itr.hasNext()) {
               this._itr = itr;
               return true;
            }

            ++this._cur;
         }

         this._itr = null;
         return false;
      }
   }

   public Object next() throws IOException {
      if (!this.hasNext()) {
         throw new NoSuchElementException();
      } else {
         return this._itr.next();
      }
   }

   public InputStream getInputStream() throws IOException {
      if (this._itr == null) {
         throw new IllegalStateException();
      } else {
         return this._itr.getInputStream();
      }
   }

   public File getFile() throws IOException {
      if (this._itr == null) {
         throw new IllegalStateException();
      } else {
         return this._itr.getFile();
      }
   }

   public void close() {
      if (this._itrs != null) {
         Iterator itr = this._itrs.iterator();

         while(itr.hasNext()) {
            ((MetaDataIterator)itr.next()).close();
         }
      }

   }
}
