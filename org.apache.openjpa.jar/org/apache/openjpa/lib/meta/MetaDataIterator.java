package org.apache.openjpa.lib.meta;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.openjpa.lib.util.Closeable;

public interface MetaDataIterator extends Closeable {
   boolean hasNext() throws IOException;

   Object next() throws IOException;

   InputStream getInputStream() throws IOException;

   File getFile() throws IOException;

   void close();
}
