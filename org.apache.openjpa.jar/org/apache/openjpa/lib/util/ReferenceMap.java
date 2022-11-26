package org.apache.openjpa.lib.util;

import java.util.Map;

public interface ReferenceMap extends Map {
   int HARD = 0;
   int WEAK = 1;
   int SOFT = 2;

   void removeExpired();

   void keyExpired(Object var1);

   void valueExpired(Object var1);
}
