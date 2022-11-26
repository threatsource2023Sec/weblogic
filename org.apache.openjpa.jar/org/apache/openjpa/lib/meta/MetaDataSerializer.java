package org.apache.openjpa.lib.meta;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public interface MetaDataSerializer {
   int COMPACT = 0;
   int PRETTY = 1;
   int APPEND = 2;
   int VERBOSE = 4;

   void serialize(int var1) throws IOException;

   void serialize(Map var1, int var2) throws IOException;

   void serialize(File var1, int var2) throws IOException;

   void serialize(Writer var1, int var2) throws IOException;
}
