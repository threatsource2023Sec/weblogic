package weblogic.application.archive.collage.impl;

import java.io.IOException;
import java.io.InputStream;

interface Streamer {
   InputStream getInputStream() throws IOException;

   long getSize();

   long getTime();
}
