package weblogic.connector.common;

import java.io.OutputStream;
import weblogic.diagnostics.image.ImageSourceCreationException;
import weblogic.diagnostics.image.PartitionAwareImageSource;

public class ConnectorDiagnosticImageSource implements PartitionAwareImageSource {
   private boolean timedout = false;

   public void createDiagnosticImage(OutputStream out) throws ImageSourceCreationException {
      try {
         RACollectionManager.getXMLBean(this).save(out);
      } catch (Throwable var7) {
         String exMsg = Debug.getExceptionImageSourceCreation(var7.toString());
         throw new ImageSourceCreationException(exMsg, var7);
      } finally {
         this.timedout = false;
      }

   }

   public void createDiagnosticImage(String partitionName, OutputStream out) throws ImageSourceCreationException {
      try {
         RACollectionManager.getXMLBean(partitionName, this).save(out);
      } catch (Throwable var8) {
         String exMsg = Debug.getExceptionImageSourceCreation(var8.toString());
         throw new ImageSourceCreationException(exMsg, var8);
      } finally {
         this.timedout = false;
      }

   }

   public void timeoutImageCreation() {
      this.timedout = true;
      Debug.logDiagnosticImageTimedOut();
   }

   public boolean timedout() {
      return this.timedout;
   }
}
