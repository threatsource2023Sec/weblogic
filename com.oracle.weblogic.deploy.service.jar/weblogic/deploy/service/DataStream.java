package weblogic.deploy.service;

import java.io.IOException;
import java.io.InputStream;

public interface DataStream {
   String getName();

   boolean isZip();

   InputStream getInputStream() throws IOException;

   void close();
}
