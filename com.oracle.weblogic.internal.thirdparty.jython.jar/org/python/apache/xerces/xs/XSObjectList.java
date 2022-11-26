package org.python.apache.xerces.xs;

import java.util.List;

public interface XSObjectList extends List {
   int getLength();

   XSObject item(int var1);
}
