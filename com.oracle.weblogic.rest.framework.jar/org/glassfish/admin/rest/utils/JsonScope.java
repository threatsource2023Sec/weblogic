package org.glassfish.admin.rest.utils;

import java.util.Iterator;
import java.util.Stack;

public class JsonScope {
   private String scope;
   private Stack scopeStack = new Stack();

   public void beginObjectAttr(String name) {
      if (!this.scopeStack.isEmpty() && this.scopeStack.peek() instanceof ArrayAttr && !((ArrayAttr)((ArrayAttr)this.scopeStack.peek())).inElement()) {
         throw new IllegalStateException("Not currently in an array element");
      } else {
         this.scopeStack.push(new ObjectAttr(name));
         this.computeScope();
      }
   }

   public void endObjectAttr() {
      if (!(this.scopeStack.peek() instanceof ObjectAttr)) {
         throw new IllegalStateException("Not currently in an object attribute");
      } else {
         this.scopeStack.pop();
         this.computeScope();
      }
   }

   public void beginArrayAttr(String name) {
      if (!this.scopeStack.isEmpty() && this.scopeStack.peek() instanceof ArrayAttr && !((ArrayAttr)((ArrayAttr)this.scopeStack.peek())).inElement()) {
         throw new IllegalStateException("Not currently in an array element");
      } else {
         this.scopeStack.push(new ArrayAttr(name));
         this.computeScope();
      }
   }

   public void endArrayAttr() {
      if (!(this.scopeStack.peek() instanceof ArrayAttr)) {
         throw new IllegalStateException("Not currently in an array attribute");
      } else {
         this.scopeStack.pop();
         this.computeScope();
      }
   }

   public void beginArrayElement() {
      if (!(this.scopeStack.peek() instanceof ArrayAttr)) {
         throw new IllegalStateException("Not currently in an array attribute");
      } else {
         ((ArrayAttr)((ArrayAttr)this.scopeStack.peek())).beginElement();
         this.computeScope();
      }
   }

   public void endArrayElement() {
      if (!(this.scopeStack.peek() instanceof ArrayAttr)) {
         throw new IllegalStateException("Not currently in an array attribute");
      } else {
         ((ArrayAttr)((ArrayAttr)this.scopeStack.peek())).endElement();
         this.computeScope();
      }
   }

   private void computeScope() {
      if (this.scopeStack.isEmpty()) {
         this.scope = null;
      } else {
         StringBuilder sb = new StringBuilder();
         boolean first = true;

         ScopeElement e;
         for(Iterator var3 = this.scopeStack.iterator(); var3.hasNext(); sb.append(e.toString())) {
            e = (ScopeElement)var3.next();
            if (!first) {
               sb.append(".");
            } else {
               first = false;
            }
         }

         this.scope = sb.toString();
      }
   }

   public String toString() {
      return this.scope;
   }

   private static class ArrayAttr implements ScopeElement {
      private boolean inElement;
      private String name;
      int index;

      private ArrayAttr(String name) {
         this.name = name;
         this.inElement = false;
         this.index = -1;
      }

      private boolean inElement() {
         return this.inElement;
      }

      private void beginElement() {
         if (this.inElement) {
            throw new IllegalStateException("Already in an array element");
         } else {
            this.inElement = true;
            ++this.index;
         }
      }

      private void endElement() {
         if (!this.inElement) {
            throw new IllegalStateException("Not in an array element");
         } else {
            this.inElement = false;
         }
      }

      public String toString() {
         return this.inElement ? this.name + "[" + this.index + "]" : this.name;
      }

      // $FF: synthetic method
      ArrayAttr(String x0, Object x1) {
         this(x0);
      }
   }

   private static class ObjectAttr implements ScopeElement {
      private String name;

      private ObjectAttr(String name) {
         this.name = name;
      }

      public String toString() {
         return this.name;
      }

      // $FF: synthetic method
      ObjectAttr(String x0, Object x1) {
         this(x0);
      }
   }

   private interface ScopeElement {
   }
}
