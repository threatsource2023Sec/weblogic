package com.bea.xml.stream.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SymbolTable {
   private int depth = 0;
   private Stack table = new Stack();
   private Map values = new HashMap();

   public void clear() {
      this.depth = 0;
      this.table.clear();
      this.values.clear();
   }

   public int getDepth() {
      return this.depth;
   }

   public boolean withinElement() {
      return this.depth > 0;
   }

   public void put(String name, String value) {
      this.table.push(new Symbol(name, value, this.depth));
      Stack valueStack;
      if (!this.values.containsKey(name)) {
         valueStack = new Stack();
         valueStack.push(value);
         this.values.put(name, valueStack);
      } else {
         valueStack = (Stack)this.values.get(name);
         valueStack.push(value);
      }

   }

   public String get(String name) {
      Stack valueStack = (Stack)this.values.get(name);
      return valueStack != null && !valueStack.isEmpty() ? (String)valueStack.peek() : null;
   }

   public Set getAll(String name) {
      HashSet result = new HashSet();
      Iterator i = this.table.iterator();

      while(i.hasNext()) {
         Symbol s = (Symbol)i.next();
         if (name.equals(s.getName())) {
            result.add(s.getValue());
         }
      }

      return result;
   }

   public void openScope() {
      ++this.depth;
   }

   public void closeScope() {
      Symbol symbol = (Symbol)this.table.peek();

      for(int symbolDepth = symbol.depth; symbolDepth == this.depth && !this.table.isEmpty(); symbolDepth = symbol.depth) {
         symbol = (Symbol)this.table.pop();
         Stack valueStack = (Stack)this.values.get(symbol.name);
         valueStack.pop();
         if (this.table.isEmpty()) {
            break;
         }

         symbol = (Symbol)this.table.peek();
      }

      --this.depth;
   }

   public String toString() {
      Iterator i = this.table.iterator();

      String retVal;
      Symbol symbol;
      for(retVal = ""; i.hasNext(); retVal = retVal + symbol + "\n") {
         symbol = (Symbol)i.next();
      }

      return retVal;
   }

   public static void main(String[] args) throws Exception {
      SymbolTable st = new SymbolTable();
      st.openScope();
      st.put("x", "foo");
      st.put("y", "bar");
      System.out.println("1 x:" + st.get("x"));
      System.out.println("1 y:" + st.get("y"));
      st.openScope();
      st.put("x", "bar");
      st.put("y", "foo");
      st.openScope();
      st.put("x", "barbie");
      st.openScope();
      st.closeScope();
      System.out.println("3 x:" + st.get("x"));
      st.closeScope();
      System.out.println("2 x:" + st.get("x"));
      System.out.println("2 y:" + st.get("y"));
      System.out.print(st);
      st.closeScope();
      System.out.println("1 x:" + st.get("x"));
      System.out.println("1 y:" + st.get("y"));
      st.closeScope();
      System.out.print(st);
   }
}
