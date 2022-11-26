package org.python.google.common.collect;

import java.util.Deque;
import java.util.Iterator;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtIncompatible
public abstract class ForwardingDeque extends ForwardingQueue implements Deque {
   protected ForwardingDeque() {
   }

   protected abstract Deque delegate();

   public void addFirst(Object e) {
      this.delegate().addFirst(e);
   }

   public void addLast(Object e) {
      this.delegate().addLast(e);
   }

   public Iterator descendingIterator() {
      return this.delegate().descendingIterator();
   }

   public Object getFirst() {
      return this.delegate().getFirst();
   }

   public Object getLast() {
      return this.delegate().getLast();
   }

   @CanIgnoreReturnValue
   public boolean offerFirst(Object e) {
      return this.delegate().offerFirst(e);
   }

   @CanIgnoreReturnValue
   public boolean offerLast(Object e) {
      return this.delegate().offerLast(e);
   }

   public Object peekFirst() {
      return this.delegate().peekFirst();
   }

   public Object peekLast() {
      return this.delegate().peekLast();
   }

   @CanIgnoreReturnValue
   public Object pollFirst() {
      return this.delegate().pollFirst();
   }

   @CanIgnoreReturnValue
   public Object pollLast() {
      return this.delegate().pollLast();
   }

   @CanIgnoreReturnValue
   public Object pop() {
      return this.delegate().pop();
   }

   public void push(Object e) {
      this.delegate().push(e);
   }

   @CanIgnoreReturnValue
   public Object removeFirst() {
      return this.delegate().removeFirst();
   }

   @CanIgnoreReturnValue
   public Object removeLast() {
      return this.delegate().removeLast();
   }

   @CanIgnoreReturnValue
   public boolean removeFirstOccurrence(Object o) {
      return this.delegate().removeFirstOccurrence(o);
   }

   @CanIgnoreReturnValue
   public boolean removeLastOccurrence(Object o) {
      return this.delegate().removeLastOccurrence(o);
   }
}
