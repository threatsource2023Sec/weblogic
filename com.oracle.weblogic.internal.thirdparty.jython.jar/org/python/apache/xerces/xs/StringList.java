package org.python.apache.xerces.xs;

import java.util.List;

public interface StringList extends List {
   int getLength();

   boolean contains(String var1);

   String item(int var1);
}
