package weblogic.application.archive.collage;

import java.io.IOException;
import java.io.InputStream;

public interface Artifact extends Node {
   InputStream getInputStream() throws IOException;

   long getSize();
}
