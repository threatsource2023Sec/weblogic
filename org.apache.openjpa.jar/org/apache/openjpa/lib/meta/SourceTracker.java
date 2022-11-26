package org.apache.openjpa.lib.meta;

import java.io.File;

public interface SourceTracker {
   int SRC_OTHER = 0;
   int SRC_ANNOTATIONS = 1;
   int SRC_XML = 2;

   File getSourceFile();

   Object getSourceScope();

   int getSourceType();

   String getResourceName();
}
