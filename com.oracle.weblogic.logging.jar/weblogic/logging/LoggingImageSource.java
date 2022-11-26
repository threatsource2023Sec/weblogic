package weblogic.logging;

import com.bea.logging.LogFileRotator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;
import weblogic.diagnostics.image.ImageSourceCreationException;
import weblogic.diagnostics.image.PartitionAwareImageSource;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.LogRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class LoggingImageSource implements PartitionAwareImageSource {
   private static final int NUMBER_OF_ROTATED_FILES = 1;
   private static final boolean DEBUG = false;
   private static DiagnosticsTextTextFormatter textFormatter = DiagnosticsTextTextFormatter.getInstance();
   private static LoggingImageSource singleton = new LoggingImageSource();
   private boolean timedOut = false;

   public static LoggingImageSource getInstance() {
      return singleton;
   }

   private LoggingImageSource() {
   }

   public void createDiagnosticImage(String partitionName, OutputStream out) throws ImageSourceCreationException {
      this.createDiagnosticImageInternal(partitionName, out);
   }

   public void createDiagnosticImage(OutputStream out) throws ImageSourceCreationException {
      this.createDiagnosticImageInternal((String)null, out);
   }

   private void createDiagnosticImageInternal(String partitionName, OutputStream out) throws ImageSourceCreationException {
      this.timedOut = false;
      BufferedWriter writer = null;

      try {
         writer = new BufferedWriter(new OutputStreamWriter(out));
         RuntimeAccess rt = getRuntimeAccess();
         LogRuntimeMBean logRuntime = rt.getServerRuntime().getLogRuntime();
         SortedSet rotatedFiles = logRuntime.getRotatedLogFiles();
         ArrayList logFiles = new ArrayList();
         LogFileRotator.FileInfo fileInfo;
         if (rotatedFiles != null) {
            for(int i = 0; i < 1; ++i) {
               if (rotatedFiles.size() > 0) {
                  fileInfo = (LogFileRotator.FileInfo)rotatedFiles.last();
                  rotatedFiles.remove(fileInfo);
                  logFiles.add(fileInfo);
               }
            }
         }

         logFiles.add(new LogFileRotator.FileInfo(new File(logRuntime.getCurrentLogFile())));
         Iterator var29 = logFiles.iterator();

         while(var29.hasNext()) {
            fileInfo = (LogFileRotator.FileInfo)var29.next();
            if (this.timedOut) {
               writer.write(textFormatter.getLoggingImageSourceTimedOutMsgText(fileInfo.getFile().getAbsolutePath()));
               writer.newLine();
               break;
            }

            FileInputStream fis = null;
            BufferedReader reader = null;

            try {
               fis = new FileInputStream(fileInfo.getFile());
               reader = new BufferedReader(new InputStreamReader(fis));
               String line = null;

               while((line = reader.readLine()) != null) {
                  if (!excludeDueToPartitionedCapture(partitionName, line)) {
                     writer.write(line);
                     writer.newLine();
                     if (this.timedOut) {
                        writer.write(textFormatter.getLoggingImageSourceTimedOutMsgText(fileInfo.getFile().getAbsolutePath()));
                        writer.newLine();
                        break;
                     }
                  }
               }
            } finally {
               if (reader != null) {
                  reader.close();
               }

               if (fis != null) {
                  fis.close();
               }

            }
         }
      } catch (Exception var27) {
         throw new ImageSourceCreationException(var27);
      } finally {
         this.timedOut = false;
         if (writer != null) {
            try {
               writer.flush();
               writer.close();
            } catch (IOException var25) {
            }
         }

      }

   }

   public void timeoutImageCreation() {
      this.timedOut = true;
   }

   private static RuntimeAccess getRuntimeAccess() {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      return ManagementService.getRuntimeAccess(kernelId);
   }

   private static boolean excludeDueToPartitionedCapture(String partitionName, String logLine) {
      if (partitionName == null) {
         return false;
      } else {
         return !logLine.contains(partitionName);
      }
   }
}
