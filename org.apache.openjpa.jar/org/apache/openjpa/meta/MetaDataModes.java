package org.apache.openjpa.meta;

public interface MetaDataModes {
   int MODE_NONE = 0;
   int MODE_META = 1;
   int MODE_MAPPING = 2;
   int MODE_QUERY = 4;
   int MODE_MAPPING_INIT = 8;
   int MODE_ANN_MAPPING = 16;
   int MODE_ALL = 31;
}
