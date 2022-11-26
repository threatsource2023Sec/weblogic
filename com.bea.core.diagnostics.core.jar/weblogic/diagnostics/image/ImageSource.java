package weblogic.diagnostics.image;

import java.io.OutputStream;

public interface ImageSource {
   void createDiagnosticImage(OutputStream var1) throws ImageSourceCreationException;

   void timeoutImageCreation();
}
