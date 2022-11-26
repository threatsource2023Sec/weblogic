package weblogic.deploy.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.application.descriptor.VersionMunger;
import weblogic.descriptor.DescriptorManager;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;

public class DeploymentPlanDescriptorLoader extends AbstractPlanDescriptorLoader {
   public DeploymentPlanDescriptorLoader(File planFile) {
      super(planFile);
   }

   public DeploymentPlanDescriptorLoader(InputStream is, DescriptorManager dm) {
      super(is, dm);
   }

   public DeploymentPlanBean getDeploymentPlanBean() throws IOException, XMLStreamException {
      return (DeploymentPlanBean)super.loadPlanBean();
   }

   protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
      return new VersionMunger(is, this, "weblogic.j2ee.descriptor.wl.DeploymentPlanBeanImpl$SchemaHelper2", "http://xmlns.oracle.com/weblogic/deployment-plan");
   }

   public static void main(String[] args) throws Exception {
      if (args.length < 1) {
         usage(DeploymentPlanDescriptorLoader.class);
      }

      debug = true;
      String planPath = args[0];
      File f = new File(planPath);
      System.out.println("\n\n... getting DeploymentPlanBean :");
      AbstractDescriptorLoader2 loader = new DeploymentPlanDescriptorLoader(f);
      loader.loadDescriptorBean().getDescriptor().toXML(System.out);
   }
}
