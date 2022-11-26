package weblogic.xml.babel.baseparser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.utils.collections.Stack;

public final class SymbolTable {
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

   public void openScope() {
      ++this.depth;
   }

   public List closeScope() {
      return this.closeScope(true);
   }

   public List closeScope(boolean collect_outofscope_entries) {
      List returnValues = null;
      if (!this.table.isEmpty()) {
         Symbol symbol = (Symbol)this.table.peek();

         for(int symbolDepth = symbol.depth; symbolDepth == this.depth && !this.table.isEmpty(); symbolDepth = symbol.depth) {
            symbol = (Symbol)this.table.pop();
            Stack valueStack = (Stack)this.values.get(symbol.name);
            String oldValue = (String)valueStack.pop();
            if (collect_outofscope_entries) {
               String value = null;
               if (!valueStack.isEmpty()) {
                  value = (String)valueStack.peek();
               }

               if (returnValues == null) {
                  returnValues = new ArrayList();
               }

               returnValues.add(new PrefixMapping(symbol.name, value, oldValue));
            }

            if (this.table.isEmpty()) {
               break;
            }

            symbol = (Symbol)this.table.peek();
         }
      }

      --this.depth;
      return (List)(returnValues != null && !returnValues.isEmpty() ? returnValues : Collections.EMPTY_LIST);
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

   public String toString() {
      Iterator i = this.table.iterator();

      String retVal;
      Symbol symbol;
      for(retVal = ""; i.hasNext(); retVal = retVal + symbol + "\n") {
         symbol = (Symbol)i.next();
      }

      return retVal;
   }

   public static void main(String[] args) {
      try {
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
      } catch (Exception var2) {
         System.out.println(var2);
      }

   }
}
