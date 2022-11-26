package weblogic.application.utils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface MetaDataIterator extends Closeable {
   boolean hasNext() throws IOException;

   MetaData next() throws IOException;

   InputStream getInputStream() throws IOException;

   File getFile() throws IOException;

   void close();

   public interface MetaData {
      String getName();

      List getAnnotations();
   }
}
