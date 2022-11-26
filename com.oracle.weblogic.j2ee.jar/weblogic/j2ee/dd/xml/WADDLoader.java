package weblogic.j2ee.dd.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import weblogic.j2ee.dd.ModuleDescriptor;
import weblogic.management.descriptors.ApplicationDescriptorMBean;
import weblogic.management.descriptors.application.weblogic.EntityCacheMBean;
import weblogic.xml.process.SAXValidationException;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.process.XMLProcessingException;

public abstract class WADDLoader {
   private static final String EXCLUSIVE = "Exclusive";
   private static final String MULTI_VERSION = "MultiVersion";
   protected ApplicationDescriptorMBean applicationDescriptor = null;
   private ModuleDescriptor currentModuleContext;
   protected boolean validate = true;

   public void setApplicationDescriptor(ApplicationDescriptorMBean applicationDescriptor) {
      this.applicationDescriptor = applicationDescriptor;
   }

   public ApplicationDescriptorMBean getApplicationDescriptor() {
      return this.applicationDescriptor;
   }

   public void setValidate(boolean val) {
      this.validate = val;
   }

   public abstract void process(String var1) throws IOException, XMLParsingException, XMLProcessingException;

   public abstract void process(File var1) throws IOException, XMLParsingException, XMLProcessingException;

   public abstract void process(InputStream var1) throws IOException, XMLParsingException, XMLProcessingException;

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

   protected void validateIntegerGreaterThanNegativeOne(String val) throws Exception {
      int intVal;
      try {
         intVal = Integer.parseInt(val);
      } catch (NumberFormatException var4) {
         throw new Exception("Parameter must be an integer");
      }

      if (intVal < -1) {
         throw new Exception("Parameter must be an integer greater than -1");
      }
   }

   protected void validateIntegerGreaterThanZero(String val) throws Exception {
      int intVal;
      try {
         intVal = Integer.parseInt(val);
      } catch (NumberFormatException var4) {
         throw new Exception("Parameter must be an integer");
      }

      if (intVal <= 0) {
         throw new Exception("Parameter must be an integer greater 0");
      }
   }

   protected void validateStmtCacheSize(String val) throws Exception {
      int intVal;
      try {
         intVal = Integer.parseInt(val);
      } catch (NumberFormatException var4) {
         throw new Exception("Parameter must be an integer");
      }

      if (intVal < 0 || intVal > 1024) {
         throw new Exception("Parameter must be an integer greater than (0) and lesser than (1024)");
      }
   }

   protected void validateStmtCacheType(String val) throws Exception {
      if (!val.equals("LRU") && !val.equals("FIXED")) {
         throw new Exception("Statement cache type must either be (LRU) or (FIXED)");
      }
   }

   protected void validateCachingStrategy(EntityCacheMBean ecmb, String val) throws SAXValidationException {
      if (!"Exclusive".equalsIgnoreCase(val) && !"MultiVersion".equalsIgnoreCase(val)) {
         throw new SAXValidationException("Invalid caching-strategy of '" + val + "' specified for entity-cache '" + ecmb.getEntityCacheName() + "'.  Value must be 'Exclusive' or 'MultiVersion'.");
      }
   }

   protected void setCurrentModuleContext(ModuleDescriptor m) {
      this.currentModuleContext = m;
   }

   protected ModuleDescriptor getCurrentModuleContext() {
      return this.currentModuleContext;
   }
}
