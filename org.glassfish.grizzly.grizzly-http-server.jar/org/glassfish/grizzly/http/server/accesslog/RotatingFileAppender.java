package org.glassfish.grizzly.http.server.accesslog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.server.HttpServer;

public class RotatingFileAppender implements AccessLogAppender {
   private static final Logger LOGGER = Grizzly.logger(HttpServer.class);
   private final SimpleDateFormatThreadLocal fileFormat;
   private final SimpleDateFormatThreadLocal archiveFormat;
   private FileAppender appender;
   private final File directory;
   private File currentArchive;
   private File currentFile;
   private boolean closed;

   public RotatingFileAppender(File directory, String filePattern) throws IOException {
      this(filePattern, filePattern, directory);
      LOGGER.fine("Creating rotating log appender in \"" + directory + "\" with file pattern \"" + filePattern + "\"");
   }

   public RotatingFileAppender(File directory, String fileName, String archivePattern) throws IOException {
      this(escape(fileName), archivePattern, directory);
      LOGGER.fine("Creating rotating log appender in \"" + directory + "\" writing to \"" + fileName + "\" and archive pattern \"" + archivePattern + "\"");
   }

   private static String escape(String fileName) {
      if (fileName == null) {
         throw new NullPointerException("Null file name");
      } else {
         return "'" + fileName.replace("'", "''") + "'";
      }
   }

   private RotatingFileAppender(String filePattern, String archivePattern, File directory) throws IOException {
      this.directory = directory.getCanonicalFile();
      this.archiveFormat = new SimpleDateFormatThreadLocal(archivePattern);
      this.fileFormat = new SimpleDateFormatThreadLocal(filePattern);
      Date now = new Date();
      this.currentArchive = (new File(directory, ((SimpleDateFormat)this.archiveFormat.get()).format(now))).getCanonicalFile();
      this.currentFile = (new File(directory, ((SimpleDateFormat)this.fileFormat.get()).format(now))).getCanonicalFile();
      if (!this.directory.equals(this.currentArchive.getParentFile())) {
         throw new IllegalArgumentException("Archive file \"" + this.currentArchive + "\" is not a child of the configured directory \"" + this.directory + "\"");
      } else if (!this.directory.equals(this.currentFile.getParentFile())) {
         throw new IllegalArgumentException("Access log file \"" + this.currentFile + "\" is not a child of the configured directory \"" + this.directory + "\"");
      } else if (this.currentArchive.equals(this.currentFile)) {
         throw new IllegalArgumentException("Access log file and archive file point to the same file \"" + this.currentFile + "\"");
      } else {
         this.appender = new FileAppender(this.currentFile, true);
      }
   }

   public void append(String accessLogEntry) throws IOException {
      if (!this.closed) {
         Date date = new Date();
         synchronized(this) {
            SimpleDateFormat archiveFormat = (SimpleDateFormat)this.archiveFormat.get();
            File archive = new File(this.directory, archiveFormat.format(date));
            if (!archive.equals(this.currentArchive)) {
               try {
                  this.appender.close();
                  if (!this.currentFile.equals(this.currentArchive)) {
                     LOGGER.info("Archiving \"" + this.currentFile + "\" to \"" + this.currentArchive + "\"");
                     if (!this.currentFile.renameTo(this.currentArchive)) {
                        throw new IOException("Unable to rename \"" + this.currentFile + "\" to \"" + this.currentArchive + "\"");
                     }
                  }

                  this.currentArchive = archive;
                  this.currentFile = new File(this.directory, ((SimpleDateFormat)this.fileFormat.get()).format(date));
                  this.appender = new FileAppender(this.currentFile, true);
               } catch (IOException var8) {
                  LOGGER.log(Level.WARNING, "I/O error rotating access log file", var8);
               }
            }

            this.appender.append(accessLogEntry);
         }
      }
   }

   public void close() throws IOException {
      this.closed = true;
      this.appender.close();
   }
}
