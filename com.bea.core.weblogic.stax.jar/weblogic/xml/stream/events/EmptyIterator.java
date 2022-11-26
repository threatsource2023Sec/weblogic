package weblogic.xml.stream.events;

import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.AttributeIterator;

/** @deprecated */
@Deprecated
public class EmptyIterator implements AttributeIterator {
   public boolean hasNext() {
      return false;
   }

   public Attribute next() {
      return null;
   }

   public Attribute peek() {
      return null;
   }

   public void skip() {
   }
}
