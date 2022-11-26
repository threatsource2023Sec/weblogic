package weblogic.kernel;

import java.util.EmptyStackException;
import weblogic.utils.collections.Stack;

public final class ResettableThreadLocalStack extends ResettableThreadLocal {
   public ResettableThreadLocalStack() {
   }

   public ResettableThreadLocalStack(boolean inherit) {
      super(inherit);
   }

   public Object get() {
      return this.peek();
   }

   public void set(Object value) {
      Stack stack = (Stack)super.get();
      stack.clear();
      stack.push(value);
   }

   public Object peek() {
      Stack stack = (Stack)super.get();
      return stack.isEmpty() ? null : stack.peek();
   }

   public void push(Object value) {
      ((Stack)super.get()).push(value);
   }

   public Object pop() {
      try {
         return ((Stack)super.get()).pop();
      } catch (EmptyStackException var2) {
         throw new AssertionError(var2);
      }
   }

   public int getSize() {
      return ((Stack)super.get()).size();
   }

   public Object popAndPeek() {
      try {
         Stack stack = (Stack)super.get();
         stack.pop();
         return stack.isEmpty() ? null : stack.peek();
      } catch (EmptyStackException var2) {
         return null;
      }
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
      if (!parentStack.isEmpty()) {
         Stack newStack = (Stack)this.initialValue();
         newStack.push(parentStack.peek());
         return newStack;
      } else {
         return this.initialValue();
      }
   }
}
