package weblogic.deploy.service;

import java.io.File;
import java.io.IOException;

public interface FileDataStream extends DataStream {
   File getFile();

   int getLength() throws IOException;
}
