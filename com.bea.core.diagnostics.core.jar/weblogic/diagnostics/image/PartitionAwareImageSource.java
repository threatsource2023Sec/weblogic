package weblogic.diagnostics.image;

import java.io.OutputStream;

public interface PartitionAwareImageSource extends ImageSource {
   void createDiagnosticImage(String var1, OutputStream var2) throws ImageSourceCreationException;
}
