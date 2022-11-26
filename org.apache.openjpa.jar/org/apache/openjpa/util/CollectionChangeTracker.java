package org.apache.openjpa.util;

public interface CollectionChangeTracker extends ChangeTracker {
   void added(Object var1);

   void removed(Object var1);
}
