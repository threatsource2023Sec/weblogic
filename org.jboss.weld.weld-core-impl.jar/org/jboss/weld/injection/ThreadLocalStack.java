package org.jboss.weld.injection;

import java.util.ArrayDeque;
import java.util.Deque;
import org.jboss.weld.contexts.cache.RequestScopedCache;
import org.jboss.weld.contexts.cache.RequestScopedItem;

public class ThreadLocalStack {
   private final ThreadLocal threadLocalStack = new ThreadLocal() {
      protected Stack initialValue() {
         return new Stack(this);
      }
   };
   private static final ThreadLocalStackReference NULL_REFERENCE = new ThreadLocalStackReference() {
      public Object pop() {
         return null;
      }
   };

   public ThreadLocalStackReference push(Object item) {
      Stack stack = (Stack)this.threadLocalStack.get();
      stack.push(item);
      return stack;
   }

   public Object peek() {
      Stack stack = (Stack)this.threadLocalStack.get();
      Object top = stack.peek();
      stack.removeIfEmpty();
      return top;
   }

   public ThreadLocalStackReference pushConditionally(Object item, boolean condition) {
      return condition ? this.push(item) : NULL_REFERENCE;
   }

   public ThreadLocalStackReference pushIfNotNull(Object item) {
      return this.pushConditionally(item, item != null);
   }

   private static class Stack implements RequestScopedItem, ThreadLocalStackReference {
      private final Deque elements;
      private final ThreadLocal interceptionContexts;
      private boolean removeWhenEmpty;
      private boolean valid;

      private Stack(ThreadLocal interceptionContexts) {
         this.interceptionContexts = interceptionContexts;
         this.elements = new ArrayDeque();
         this.removeWhenEmpty = !RequestScopedCache.addItemIfActive((RequestScopedItem)this);
         this.valid = true;
      }

      private void checkState() {
         if (!this.valid) {
            throw new IllegalStateException("This ThreadLocalStack is no longer valid.");
         }
      }

      public void push(Object item) {
         this.checkState();
         this.elements.addFirst(item);
      }

      public Object peek() {
         this.checkState();
         return this.elements.peekFirst();
      }

      public Object pop() {
         this.checkState();
         Object top = this.elements.removeFirst();
         this.removeIfEmpty();
         return top;
      }

      private void removeIfEmpty() {
         if (this.removeWhenEmpty && this.elements.isEmpty()) {
            this.interceptionContexts.remove();
            this.valid = false;
         }

      }

      public void invalidate() {
         this.removeWhenEmpty = true;
         this.removeIfEmpty();
      }

      // $FF: synthetic method
      Stack(ThreadLocal x0, Object x1) {
         this(x0);
      }
   }

   public interface ThreadLocalStackReference {
      Object pop();
   }
}
