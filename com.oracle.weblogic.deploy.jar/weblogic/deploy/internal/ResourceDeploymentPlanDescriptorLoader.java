package weblogic.deploy.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.application.descriptor.VersionMunger;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.j2ee.descriptor.wl.ResourceDeploymentPlanBean;

public class ResourceDeploymentPlanDescriptorLoader extends AbstractPlanDescriptorLoader {
   public ResourceDeploymentPlanDescriptorLoader(File planFile) {
      super(planFile);
   }

   public ResourceDeploymentPlanDescriptorLoader(InputStream is, DescriptorManager dm) {
      super(is, dm);
   }

   public ResourceDeploymentPlanBean getResourceDeploymentPlanBean() throws IOException, XMLStreamException {
      return (ResourceDeploymentPlanBean)super.loadPlanBean();
   }

   protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
      return new VersionMunger(is, this, "weblogic.j2ee.descriptor.wl.ResourceDeploymentPlanBeanImpl$SchemaHelper2", "http://xmlns.oracle.com/weblogic/resource-deployment-plan");
   }

   public static void main(String[] args) throws Exception {
      if (args.length < 1) {
         usage(ResourceDeploymentPlanDescriptorLoader.class);
      }

      debug = true;
      String planPath = args[0];
      File f = new File(planPath);
      System.out.println("\n\n... getting ResourceDeploymentPlanBean 4:");
      ResourceDeploymentPlanDescriptorLoader loader = new ResourceDeploymentPlanDescriptorLoader(f);
      ResourceDeploymentPlanBean bean = loader.getResourceDeploymentPlanBean();
      ((DescriptorBean)bean).getDescriptor().toXML(System.out);
   }
}
