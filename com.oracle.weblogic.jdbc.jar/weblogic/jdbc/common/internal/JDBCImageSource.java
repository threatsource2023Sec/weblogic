package weblogic.jdbc.common.internal;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import weblogic.diagnostics.image.ImageSourceCreationException;
import weblogic.diagnostics.image.PartitionAwareImageSource;

public final class JDBCImageSource implements PartitionAwareImageSource {
   private boolean imageCreationTimeout = false;

   public void createDiagnosticImage(OutputStream out) throws ImageSourceCreationException {
      this.createDiagnosticImage((String)null, out);
   }

   public void timeoutImageCreation() {
      this.imageCreationTimeout = true;
   }

   public void createDiagnosticImage(String partitionName, OutputStream out) throws ImageSourceCreationException {
      this.imageCreationTimeout = false;
      PrintWriter img = new PrintWriter(out);
      synchronized(ConnectionPoolManager.getLockObject()) {
         Iterator iter = ConnectionPoolManager.getConnectionPools();

         while(iter.hasNext()) {
            JDBCConnectionPool cp = (JDBCConnectionPool)iter.next();
            if (partitionName != null) {
               if (partitionName.equals(cp.getPartitionName())) {
                  cp.dumpPool(img);
               }
            } else {
               cp.dumpPool(img);
            }

            if (this.imageCreationTimeout) {
               img.println("ImageSource timed out.");
               break;
            }
         }
      }

      img.flush();
   }
}
