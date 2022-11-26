package weblogic.apache.xerces.stax.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.events.Namespace;

abstract class ElementImpl extends XMLEventImpl {
   private final QName fName;
   private final List fNamespaces;

   ElementImpl(QName var1, boolean var2, Iterator var3, Location var4) {
      super(var2 ? 1 : 2, var4);
      this.fName = var1;
      if (var3 != null && var3.hasNext()) {
         this.fNamespaces = new ArrayList();

         do {
            Namespace var5 = (Namespace)var3.next();
            this.fNamespaces.add(var5);
         } while(var3.hasNext());
      } else {
         this.fNamespaces = Collections.EMPTY_LIST;
      }

   }

   public final QName getName() {
      return this.fName;
   }

   public final Iterator getNamespaces() {
      return createImmutableIterator(this.fNamespaces.iterator());
   }

   static Iterator createImmutableIterator(Iterator var0) {
      return new NoRemoveIterator(var0);
   }

   private static final class NoRemoveIterator implements Iterator {
      private final Iterator fWrapped;

      public NoRemoveIterator(Iterator var1) {
         this.fWrapped = var1;
      }

      public boolean hasNext() {
         return this.fWrapped.hasNext();
      }

      public Object next() {
         return this.fWrapped.next();
      }

      public void remove() {
         throw new UnsupportedOperationException("Attributes iterator is read-only.");
      }
   }
}
