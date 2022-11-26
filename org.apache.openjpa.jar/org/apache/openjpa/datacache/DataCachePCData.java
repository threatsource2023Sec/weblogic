package org.apache.openjpa.datacache;

import org.apache.openjpa.kernel.PCData;

public interface DataCachePCData extends PCData {
   boolean isTimedOut();
}
