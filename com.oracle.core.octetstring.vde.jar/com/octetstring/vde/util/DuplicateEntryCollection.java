package com.octetstring.vde.util;

import java.util.Collection;

public interface DuplicateEntryCollection {
   void add(String var1);

   Collection getDuplicateEntries();

   boolean isEmpty();
}
