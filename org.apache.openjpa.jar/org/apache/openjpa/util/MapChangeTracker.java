package org.apache.openjpa.util;

public interface MapChangeTracker extends ChangeTracker {
   boolean getTrackKeys();

   void setTrackKeys(boolean var1);

   void added(Object var1, Object var2);

   void removed(Object var1, Object var2);

   void changed(Object var1, Object var2, Object var3);
}
