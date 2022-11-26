package org.glassfish.grizzly.http.server.accesslog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.server.HttpServer;

public class FileAppender extends StreamAppender {
   private static final Logger LOGGER = Grizzly.logger(HttpServer.class);

   public FileAppender(File file) throws IOException {
      this(file, true);
   }

   public FileAppender(File file, boolean append) throws IOException {
      super(new FileOutputStream(file, append));
      LOGGER.info("Access log file \"" + file.getAbsolutePath() + "\" opened");
   }
}
