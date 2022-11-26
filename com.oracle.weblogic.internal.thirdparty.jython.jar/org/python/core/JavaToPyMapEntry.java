package org.python.core;

import java.util.Map;

class JavaToPyMapEntry extends SimpleEntry {
   public JavaToPyMapEntry(Map.Entry entry) {
      super(Py.java2py(entry.getKey()), Py.java2py(entry.getValue()));
   }
}
