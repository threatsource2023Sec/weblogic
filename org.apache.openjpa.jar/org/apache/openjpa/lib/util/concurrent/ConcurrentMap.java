package org.apache.openjpa.lib.util.concurrent;

import java.util.Iterator;
import java.util.Map;

public interface ConcurrentMap extends Map {
   Map.Entry removeRandom();

   Iterator randomEntryIterator();
}
