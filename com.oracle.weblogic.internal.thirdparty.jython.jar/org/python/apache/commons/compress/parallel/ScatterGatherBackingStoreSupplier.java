package org.python.apache.commons.compress.parallel;

import java.io.IOException;

public interface ScatterGatherBackingStoreSupplier {
   ScatterGatherBackingStore get() throws IOException;
}
