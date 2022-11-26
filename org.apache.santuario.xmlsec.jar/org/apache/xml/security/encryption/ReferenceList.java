package org.apache.xml.security.encryption;

import java.util.Iterator;

public interface ReferenceList {
   int DATA_REFERENCE = 1;
   int KEY_REFERENCE = 2;

   void add(Reference var1);

   void remove(Reference var1);

   int size();

   boolean isEmpty();

   Iterator getReferences();

   Reference newDataReference(String var1);

   Reference newKeyReference(String var1);
}
