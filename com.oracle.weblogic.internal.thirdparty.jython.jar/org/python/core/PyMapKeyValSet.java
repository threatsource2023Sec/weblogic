package org.python.core;

import java.util.Collection;

class PyMapKeyValSet extends PyMapSet {
   PyMapKeyValSet(Collection coll) {
      super(coll);
   }

   Object toJava(Object o) {
      return AbstractDict.tojava(o);
   }

   Object toPython(Object o) {
      return Py.java2py(o);
   }
}
