package weblogic.xml.util;

import java.util.Hashtable;
import java.util.Stack;

public class NameSpaceContext {
   Hashtable current = new Hashtable();
   Stack contexts = new Stack();

   public final void addNameSpace(Atom url, Atom n) {
      this.current.put(url, n);
   }

   public final Atom findNameSpace(Atom n) {
      return (Atom)this.current.get(n);
   }

   public final void push() {
      this.contexts.push(this.current);
      this.current = (Hashtable)this.current.clone();
   }

   public final void pop() {
      this.current = (Hashtable)this.contexts.pop();
   }
}
