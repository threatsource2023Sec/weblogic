package weblogic.kernel;

import java.util.EmptyStackException;
import weblogic.utils.collections.Stack;

public final class ThreadLocalStack {
   private final AuditableThreadLocal tlstack;

   public ThreadLocalStack() {
      this.tlstack = AuditableThreadLocalFactory.createThreadLocal(new StackInitialValue());
   }

   public ThreadLocalStack(boolean inherit) {
      this.tlstack = AuditableThreadLocalFactory.createThreadLocal(new StackInitialValue(inherit));
   }

   public int getSize() {
      return ((Stack)this.tlstack.get()).size();
   }

   public Object get() {
      return this.peek();
   }

   public Object get(AuditableThread thr) {
      return this.peek(thr);
   }

   public void set(Object value) {
      Stack stack = (Stack)this.tlstack.get();
      stack.clear();
      stack.push(value);
   }

   public Object peek() {
      Stack stack = (Stack)this.tlstack.get();
      return stack.isEmpty() ? null : stack.peek();
   }

   public Object peek(AuditableThread thr) {
      Stack stack = (Stack)this.tlstack.get(thr);
      return stack != null && !stack.isEmpty() ? stack.peek() : null;
   }

   public void push(Object value) {
      ((Stack)this.tlstack.get()).push(value);
   }

   public Object pop() {
      try {
         return ((Stack)this.tlstack.get()).pop();
      } catch (EmptyStackException var2) {
         throw new AssertionError(var2);
      }
   }

   public Object popAndPeek() {
      try {
         Stack stack = (Stack)this.tlstack.get();
         stack.pop();
         return stack.isEmpty() ? null : stack.peek();
      } catch (EmptyStackException var2) {
         return null;
      }
   }

   private static final class StackInitialValue extends ThreadLocalInitialValue {
      private StackInitialValue() {
         this(false);
      }

      private StackInitialValue(boolean inherit) {
         super(inherit);
      }

      protected Object initialValue() {
         return new Stack();
      }

      protected Object resetValue(Object o) {
         ((Stack)o).clear();
         return o;
      }

      protected Object childValue(Object parentValue) {
         Stack parentStack = (Stack)parentValue;
         if (this.inherit && !parentStack.isEmpty()) {
            Stack newStack = (Stack)this.initialValue();
            newStack.push(parentStack.peek());
            return newStack;
         } else {
            return this.initialValue();
         }
      }

      // $FF: synthetic method
      StackInitialValue(Object x0) {
         this();
      }

      // $FF: synthetic method
      StackInitialValue(boolean x0, Object x1) {
         this(x0);
      }
   }
}
