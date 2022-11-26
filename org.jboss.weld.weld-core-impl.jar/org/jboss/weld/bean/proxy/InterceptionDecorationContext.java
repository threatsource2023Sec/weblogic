package org.jboss.weld.bean.proxy;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EmptyStackException;
import org.jboss.weld.contexts.cache.RequestScopedCache;
import org.jboss.weld.contexts.cache.RequestScopedItem;

public class InterceptionDecorationContext {
   private static ThreadLocal interceptionContexts = new ThreadLocal();

   private InterceptionDecorationContext() {
   }

   public static CombinedInterceptorAndDecoratorStackMethodHandler peek() {
      return peek((Stack)interceptionContexts.get());
   }

   public static CombinedInterceptorAndDecoratorStackMethodHandler peekIfNotEmpty() {
      Stack stack = (Stack)interceptionContexts.get();
      return stack == null ? null : stack.peek();
   }

   public static boolean empty() {
      return empty((Stack)interceptionContexts.get());
   }

   public static void endInterceptorContext() {
      pop((Stack)interceptionContexts.get());
   }

   public static Stack startIfNotEmpty() {
      Stack stack = getStack();
      if (!stack.elements.isEmpty()) {
         stack.push(CombinedInterceptorAndDecoratorStackMethodHandler.NULL_INSTANCE);
         return stack;
      } else {
         stack.removeIfEmpty();
         return null;
      }
   }

   public static Stack startIfNotOnTop(CombinedInterceptorAndDecoratorStackMethodHandler context) {
      Stack stack = getStack();
      return stack.startIfNotOnTop(context) ? stack : null;
   }

   public static Stack getStack() {
      Stack stack = (Stack)interceptionContexts.get();
      if (stack == null) {
         stack = new Stack(interceptionContexts);
         interceptionContexts.set(stack);
      }

      return stack;
   }

   private static CombinedInterceptorAndDecoratorStackMethodHandler pop(Stack stack) {
      if (stack == null) {
         throw new EmptyStackException();
      } else {
         return stack.pop();
      }
   }

   private static CombinedInterceptorAndDecoratorStackMethodHandler peek(Stack stack) {
      if (stack == null) {
         throw new EmptyStackException();
      } else {
         return stack.peek();
      }
   }

   private static boolean empty(Stack stack) {
      return stack == null ? true : stack.elements.isEmpty();
   }

   public static class Stack implements RequestScopedItem {
      private boolean removeWhenEmpty;
      private final Deque elements;
      private final ThreadLocal interceptionContexts;
      private boolean valid;

      private Stack(ThreadLocal interceptionContexts) {
         this.interceptionContexts = interceptionContexts;
         this.elements = new ArrayDeque();
         this.removeWhenEmpty = !RequestScopedCache.addItemIfActive((RequestScopedItem)this);
         this.valid = true;
      }

      public boolean startIfNotOnTop(CombinedInterceptorAndDecoratorStackMethodHandler context) {
         this.checkState();
         if (!this.elements.isEmpty() && this.peek() == context) {
            return false;
         } else {
            this.push(context);
            return true;
         }
      }

      public void end() {
         this.pop();
      }

      private void push(CombinedInterceptorAndDecoratorStackMethodHandler item) {
         this.checkState();
         this.elements.addFirst(item);
      }

      public CombinedInterceptorAndDecoratorStackMethodHandler peek() {
         this.checkState();
         return (CombinedInterceptorAndDecoratorStackMethodHandler)this.elements.peekFirst();
      }

      private CombinedInterceptorAndDecoratorStackMethodHandler pop() {
         this.checkState();
         CombinedInterceptorAndDecoratorStackMethodHandler top = (CombinedInterceptorAndDecoratorStackMethodHandler)this.elements.removeFirst();
         this.removeIfEmpty();
         return top;
      }

      private void checkState() {
         if (!this.valid) {
            throw new IllegalStateException("This InterceptionDecorationContext is no longer valid.");
         }
      }

      public void invalidate() {
         this.removeWhenEmpty = true;
         this.removeIfEmpty();
      }

      private void removeIfEmpty() {
         if (this.removeWhenEmpty && this.elements.isEmpty()) {
            this.interceptionContexts.remove();
            this.valid = false;
         }

      }

      public int size() {
         return this.elements.size();
      }

      public String toString() {
         return "Stack [valid=" + this.valid + ", cached=" + !this.removeWhenEmpty + ", elements=" + this.elements + "]";
      }

      // $FF: synthetic method
      Stack(ThreadLocal x0, Object x1) {
         this(x0);
      }
   }
}
