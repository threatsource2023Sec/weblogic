package weblogic.diagnostics.archive.filestore;

import java.io.File;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import weblogic.diagnostics.debug.DebugLogger;

class LogRotationHandler extends Handler {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");
   private FileDataArchive archive;

   LogRotationHandler(FileDataArchive archive) {
      if (DEBUG.isDebugEnabled()) {
         DebugLogger.println("Registering LogRotationHandler for archive " + archive.getName());
      }

      this.archive = archive;
   }

   public void close() {
   }

   public void flush() {
   }

   public void publish(LogRecord record) {
      Object[] params = record.getParameters();
      int size = params != null ? params.length : 0;
      File from = size > 0 ? new File((String)params[0]) : null;
      File to = size > 1 ? new File((String)params[1]) : null;
      if (DEBUG.isDebugEnabled()) {
         DebugLogger.println("Rotation event on archive (argcnt=" + size + ") " + this.archive.getName() + "\n  current=" + this.archive.getArchiveFile() + "\n  from=" + from + "\n  to=" + to);
      }

      File fromFile;
      if (size >= 1 && size <= 2) {
         fromFile = new File((String)params[0]);
         if (!this.archive.getArchiveFile().equals(fromFile)) {
            return;
         }
      }

      if (size == 1) {
         this.archive.preRotate();
      } else if (size == 2) {
         fromFile = new File((String)params[0]);
         File toFile = new File((String)params[1]);
         this.archive.postRotate(fromFile, toFile);
      }

   }
}
