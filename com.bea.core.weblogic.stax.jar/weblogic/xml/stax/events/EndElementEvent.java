package weblogic.xml.stax.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.Namespace;
import weblogic.utils.collections.Iterators;

public class EndElementEvent extends NamedEvent implements EndElement {
   private List outOfScopeNamespaces;

   public EndElementEvent() {
      this.init();
   }

   protected void init() {
      this.setEventType(2);
   }

   public EndElementEvent(QName name) {
      super(name);
      this.init();
   }

   public Iterator getNamespaces() {
      return this.outOfScopeNamespaces == null ? Iterators.EMPTY_ITERATOR : this.outOfScopeNamespaces.iterator();
   }

   public void addNamespace(Namespace n) {
      if (this.outOfScopeNamespaces == null) {
         this.outOfScopeNamespaces = new ArrayList();
      }

      this.outOfScopeNamespaces.add(n);
   }

   public void reset() {
      if (this.outOfScopeNamespaces != null) {
         this.outOfScopeNamespaces.clear();
      }

   }

   public String toString() {
      String value = "</" + this.nameAsString();

      for(Iterator ni = this.getNamespaces(); ni.hasNext(); value = value + " " + ni.next().toString()) {
      }

      value = value + ">";
      return value;
   }
}
