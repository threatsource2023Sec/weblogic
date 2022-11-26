package weblogic.deploy.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.descriptor.DescriptorManager;

abstract class AbstractPlanDescriptorLoader extends AbstractDescriptorLoader2 {
   protected static boolean debug = false;

   public AbstractPlanDescriptorLoader(File planFile) {
      super((File)planFile, (String)null);
      if (debug) {
         System.out.println("\n\n\n\nParsing plan at " + planFile.toString());
      }

   }

   public AbstractPlanDescriptorLoader(InputStream is, DescriptorManager dm) {
      super(is, dm, (List)null, true);
   }

   public String getDocumentURI() {
      return null;
   }

   protected Object loadPlanBean() throws IOException, XMLStreamException {
      return this.loadDescriptorBean();
   }

   protected static void usage(Class clazz) {
      System.err.println("usage: java " + clazz.getName() + " <descriptor file name>");
      System.err.println("\n\n example:\n java " + clazz.getName() + " sample.xml");
      System.exit(0);
   }
}
