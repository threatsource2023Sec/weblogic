package weblogic.store.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.diagnostics.image.ImageSource;
import weblogic.diagnostics.image.ImageSourceCreationException;
import weblogic.store.PersistentStoreManager;
import weblogic.xml.stax.util.XMLPrettyPrinter;

public class PersistentStoreImageSource implements ImageSource {
   public void createDiagnosticImage(OutputStream out) throws ImageSourceCreationException {
      try {
         XMLStreamWriter xsw = new XMLPrettyPrinter(new PrintWriter(new OutputStreamWriter(out, "UTF-8")), 2);
         xsw.writeStartDocument();
         PersistentStoreManager.getManager().dump(xsw);
         xsw.writeEndDocument();
         xsw.flush();
      } catch (XMLStreamException var3) {
         this.dumpError(out, var3);
         throw new ImageSourceCreationException("PersistentStore image  creation failed.", var3);
      } catch (IOException var4) {
         this.dumpError(out, var4);
         throw new ImageSourceCreationException("PersistentStore image  creation failed.", var4);
      }
   }

   private void dumpError(OutputStream out, Exception ex) {
      PrintStream err = new PrintStream(out);
      err.println("Diagnostic image creation failed");
      ex.printStackTrace(err);
   }

   public void timeoutImageCreation() {
   }
}
