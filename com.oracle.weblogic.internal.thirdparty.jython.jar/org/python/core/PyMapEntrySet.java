package org.python.core;

import java.util.Collection;
import java.util.Map;

class PyMapEntrySet extends PyMapSet {
   PyMapEntrySet(Collection coll) {
      super(coll);
   }

   Object toPython(Object o) {
      if (o != null && o instanceof Map.Entry) {
         return o instanceof PyToJavaMapEntry ? ((PyToJavaMapEntry)o).getEntry() : new JavaToPyMapEntry((Map.Entry)o);
      } else {
         return null;
      }
   }

   Object toJava(Object o) {
      return new PyToJavaMapEntry((Map.Entry)o);
   }
}
