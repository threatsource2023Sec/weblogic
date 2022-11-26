package weblogic.jms.extensions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.application.descriptor.VersionMunger;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.utils.DescriptorUtils;
import weblogic.j2ee.descriptor.wl.ClientSAFBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;

public class ClientSAFParser {
   public static ClientSAFBean createClientSAFDescriptor(String uri) throws IOException, XMLStreamException {
      if (uri == null) {
         throw new IOException("Null URI specified");
      } else {
         AbstractDescriptorLoader2 jmsDescriptor = new AbstractDescriptorLoader2(new File(uri), (File)null, (DeploymentPlanBean)null, (String)null, uri) {
            protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
               String schemaHelper = "weblogic.j2ee.descriptor.wl.ClientSAFBeanImpl$SchemaHelper2";
               return new VersionMunger(is, this, schemaHelper, "http://xmlns.oracle.com/weblogic/weblogic-jms");
            }
         };
         return (ClientSAFBean)jmsDescriptor.loadDescriptorBean();
      }
   }

   public static void main(String[] args) throws Exception {
      if (args.length != 1) {
         usage();
      } else {
         String filePath = args[0];
         ClientSAFBean rootBean = null;

         try {
            System.out.println("\n\n... getting JMSBean from JMSMD:\n\n");
            rootBean = createClientSAFDescriptor(filePath);
            if (rootBean != null) {
               DescriptorUtils.writeAsXML((DescriptorBean)rootBean);
            }
         } catch (Throwable var6) {
            int lcv = 0;

            for(Throwable cause = var6; cause != null; cause = cause.getCause()) {
               System.out.println("\nERROR: run threw an exception: level " + lcv);
               ++lcv;
               cause.printStackTrace();
            }
         }

      }
   }

   private static void usage() {
      System.err.println("usage: java weblogic.jms.extensions.ClientSAFParser file-path");
   }
}
