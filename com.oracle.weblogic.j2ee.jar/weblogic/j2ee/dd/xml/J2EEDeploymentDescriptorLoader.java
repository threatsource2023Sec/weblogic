package weblogic.j2ee.dd.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import weblogic.j2ee.dd.J2EEDeploymentDescriptor;
import weblogic.j2ee.dd.ModuleDescriptor;
import weblogic.j2ee.dd.RoleDescriptor;
import weblogic.management.descriptors.application.J2EEApplicationDescriptorMBean;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.process.XMLProcessingException;

public abstract class J2EEDeploymentDescriptorLoader {
   protected J2EEApplicationDescriptorMBean dd = new J2EEDeploymentDescriptor();
   private ModuleDescriptor currentModuleContext;

   public void setDD(J2EEApplicationDescriptorMBean d) {
      this.dd = d;
   }

   public J2EEApplicationDescriptorMBean getDD() {
      return this.dd;
   }

   public abstract void process(String var1) throws IOException, XMLParsingException, XMLProcessingException;

   public abstract void process(InputStream var1) throws IOException, XMLParsingException, XMLProcessingException;

   public abstract void process(Reader var1) throws IOException, XMLParsingException, XMLProcessingException;

   public abstract void process(File var1) throws IOException, XMLParsingException, XMLProcessingException;

   protected void validatePositiveInteger(String val) throws Exception {
      int intVal;
      try {
         intVal = Integer.parseInt(val);
      } catch (NumberFormatException var4) {
         throw new Exception("Parameter must be an integer");
      }

      if (intVal < 0) {
         throw new Exception("Parameter must be a positive integer");
      }
   }

   protected void setCurrentModuleContext(ModuleDescriptor m) {
      this.currentModuleContext = m;
   }

   protected ModuleDescriptor getCurrentModuleContext() {
      return this.currentModuleContext;
   }

   protected static class RoleHolder {
      public RoleDescriptor role = null;
   }

   protected static class ModuleDescriptorHolder {
      public ModuleDescriptor modules = null;
   }
}
