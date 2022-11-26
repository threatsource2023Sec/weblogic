package org.apache.openjpa.util;

import java.util.Collection;

public interface ChangeTracker {
   boolean isTracking();

   void startTracking();

   void stopTracking();

   Collection getAdded();

   Collection getRemoved();

   Collection getChanged();

   int getNextSequence();

   void setNextSequence(int var1);
}
