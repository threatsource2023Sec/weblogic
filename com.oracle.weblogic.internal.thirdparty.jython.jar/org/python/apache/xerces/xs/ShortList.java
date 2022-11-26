package org.python.apache.xerces.xs;

import java.util.List;

public interface ShortList extends List {
   int getLength();

   boolean contains(short var1);

   short item(int var1) throws XSException;
}
