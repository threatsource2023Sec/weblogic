package weblogic.utils.concurrent;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public final class JDK15ConcurrentBlockingQueue extends AbstractCollection implements ConcurrentBlockingQueue {
   private final LinkedBlockingQueue queue = new LinkedBlockingQueue();

   JDK15ConcurrentBlockingQueue() {
   }

   public void clear() {
      this.queue.clear();
   }

   public int size() {
      return this.queue.size();
   }

   public boolean isEmpty() {
      return this.queue.isEmpty();
   }

   public Iterator iterator() {
      return this.queue.iterator();
   }

   public boolean contains(Object element) {
      return this.queue.contains(element);
   }

   public boolean add(Object obj) {
      return this.queue.add(obj);
   }

   public boolean offer(Object obj) {
      return this.queue.offer(obj);
   }

   public Object poll() {
      return this.queue.poll();
   }

   public Object poll(long timeout) throws InterruptedException {
      return this.queue.poll(timeout, TimeUnit.MILLISECONDS);
   }

   public Object remove() {
      return this.queue.remove();
   }

   public Object peek() {
      return this.queue.peek();
   }

   public Object element() {
      return this.queue.element();
   }

   public Object take() throws InterruptedException {
      return this.queue.take();
   }

   public Object[] toArray() {
      return this.queue.toArray();
   }

   public Object[] toArray(Object[] a) {
      return this.queue.toArray(a);
   }

   public String toString() {
      return this.queue.toString();
   }
}
