package org.apache.openjpa.lib.rop;

import java.io.Serializable;
import java.util.List;
import org.apache.openjpa.lib.util.Closeable;

public interface ResultList extends List, Serializable, Closeable {
   boolean isProviderOpen();

   void close();

   boolean isClosed();
}
