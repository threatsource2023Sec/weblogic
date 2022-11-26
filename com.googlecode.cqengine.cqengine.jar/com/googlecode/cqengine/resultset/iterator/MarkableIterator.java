package com.googlecode.cqengine.resultset.iterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MarkableIterator implements Iterator {
   final Iterator emptyIterator = Collections.emptyList().iterator();
   final Iterator backingIterator;
   State state;
   List replayBuffer;
   Iterator replayIterator;
   int readLimit;

   public MarkableIterator(Iterator backingIterator) {
      this.state = MarkableIterator.State.READ;
      this.replayBuffer = Collections.emptyList();
      this.replayIterator = this.emptyIterator;
      this.readLimit = 0;
      this.backingIterator = backingIterator;
   }

   public boolean hasNext() {
      switch (this.state) {
         case READ:
         case BUFFER:
            return this.backingIterator.hasNext();
         case REPLAY:
            return this.replayIterator.hasNext() || this.backingIterator.hasNext();
         default:
            throw new IllegalStateException(String.valueOf(this.state));
      }
   }

   public Object next() {
      switch (this.state) {
         case READ:
            return this.backingIterator.next();
         case BUFFER:
            if (this.replayBuffer.size() >= this.readLimit) {
               this.replayBuffer.clear();
               this.replayIterator = this.emptyIterator;
               this.state = MarkableIterator.State.READ;
               return this.next();
            }

            Object next = this.backingIterator.next();
            this.replayBuffer.add(next);
            return next;
         case REPLAY:
            if (this.replayIterator.hasNext()) {
               return this.replayIterator.next();
            }

            this.replayIterator = this.emptyIterator;
            this.state = MarkableIterator.State.BUFFER;
            return this.next();
         default:
            throw new IllegalStateException(String.valueOf(this.state));
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }

   public void mark(int readLimit) {
      this.readLimit = readLimit;
      switch (this.state) {
         case READ:
            this.replayBuffer = new ArrayList();
            this.replayIterator = this.emptyIterator;
            this.state = MarkableIterator.State.BUFFER;
            return;
         case BUFFER:
            this.replayBuffer.clear();
            this.replayIterator = this.emptyIterator;
            return;
         case REPLAY:
            this.replayBuffer = this.populateFromIterator(new ArrayList(), this.replayIterator);
            this.replayIterator = this.replayBuffer.iterator();
            return;
         default:
            throw new IllegalStateException(String.valueOf(this.state));
      }
   }

   public void reset() {
      if (this.state == MarkableIterator.State.READ) {
         throw new IllegalStateException("Iterator has not been marked or the mark has been invalidated");
      } else {
         this.replayIterator = this.replayBuffer.iterator();
         this.state = MarkableIterator.State.REPLAY;
      }
   }

   List populateFromIterator(List collection, Iterator iterator) {
      while(iterator.hasNext()) {
         collection.add(iterator.next());
      }

      return collection;
   }

   static enum State {
      READ,
      BUFFER,
      REPLAY;
   }
}
