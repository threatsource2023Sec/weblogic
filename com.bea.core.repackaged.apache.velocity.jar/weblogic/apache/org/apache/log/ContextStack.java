package weblogic.apache.org.apache.log;

import java.util.Stack;

/** @deprecated */
public class ContextStack {
   private static final ThreadLocal c_context = new ThreadLocal();
   private Stack m_stack = new Stack();

   public static final ContextStack getCurrentContext() {
      return getCurrentContext(true);
   }

   static final ContextStack getCurrentContext(boolean autocreate) {
      ContextStack context = (ContextStack)c_context.get();
      if (null == context && autocreate) {
         context = new ContextStack();
         context.push(Thread.currentThread().getName());
         c_context.set(context);
      }

      return context;
   }

   public void clear() {
      this.m_stack.setSize(0);
   }

   public Object get(int index) {
      return this.m_stack.elementAt(index);
   }

   public Object pop() {
      return this.m_stack.pop();
   }

   public void push(Object context) {
      this.m_stack.push(context);
   }

   public void set(ContextStack stack) {
      this.clear();
      int size = stack.m_stack.size();

      for(int i = 0; i < size; ++i) {
         this.m_stack.push(stack.m_stack.elementAt(i));
      }

   }

   public int getSize() {
      return this.m_stack.size();
   }

   public String toString() {
      return this.toString(this.getSize());
   }

   public String toString(int count) {
      StringBuffer sb = new StringBuffer();
      int end = this.getSize() - 1;
      int start = Math.max(end - count + 1, 0);

      for(int i = start; i < end; ++i) {
         sb.append(this.fix(this.get(i).toString()));
         sb.append('.');
      }

      sb.append(this.fix(this.get(end).toString()));
      return sb.toString();
   }

   private String fix(String context) {
      return context.replace('.', '_');
   }
}
