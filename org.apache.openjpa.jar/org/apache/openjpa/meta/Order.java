package org.apache.openjpa.meta;

import java.io.Serializable;
import java.util.Comparator;

public interface Order extends Serializable {
   String ELEMENT = "#element";

   String getName();

   boolean isAscending();

   Comparator getComparator();
}
