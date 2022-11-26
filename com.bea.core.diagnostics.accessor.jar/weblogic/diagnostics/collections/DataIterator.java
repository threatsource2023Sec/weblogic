package weblogic.diagnostics.collections;

import java.io.Serializable;
import java.util.Iterator;

public interface DataIterator extends Iterator, Serializable {
   int DEFAULT_CACHE_SIZE = 100;
}
