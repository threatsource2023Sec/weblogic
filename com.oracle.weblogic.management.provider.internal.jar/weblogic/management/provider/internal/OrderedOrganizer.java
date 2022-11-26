package weblogic.management.provider.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

public class OrderedOrganizer {
   private final Map elements = new HashMap();

   public void add(Object group, Object element) {
      LinkedHashSet groupElements = (LinkedHashSet)this.elements.get(group);
      if (groupElements == null) {
         groupElements = new LinkedHashSet();
         this.elements.put(group, groupElements);
      }

      groupElements.add(element);
   }

   public Iterable elements(final Object group) {
      return new Iterable() {
         public Iterator iterator() {
            LinkedHashSet groupedElements = (LinkedHashSet)OrderedOrganizer.this.elements.get(group);
            return groupedElements == null ? Collections.EMPTY_SET.iterator() : groupedElements.iterator();
         }
      };
   }

   public Iterable groupIdentifiers() {
      return this.elements.keySet();
   }
}
