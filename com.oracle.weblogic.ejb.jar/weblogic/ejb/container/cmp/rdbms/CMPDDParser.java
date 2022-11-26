package weblogic.ejb.container.cmp.rdbms;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBean;
import weblogic.logging.Loggable;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.process.XMLProcessingException;

public abstract class CMPDDParser {
   private Map bean2defaultHelper = new HashMap();
   private String fileName;
   private WeblogicRdbmsJarBean cmpDescriptor;
   protected EjbDescriptorBean ejbDescriptor;
   protected String encoding = null;

   public void setEJBDescriptor(EjbDescriptorBean ejbDescriptor) {
      this.ejbDescriptor = ejbDescriptor;
   }

   public void setFileName(String fileName) {
      this.fileName = fileName;
   }

   public String getFileName() {
      return this.fileName;
   }

   public void setEncoding(String encoding) {
      this.encoding = encoding;
   }

   public void setDescriptorMBean(WeblogicRdbmsJarBean cmpDesc) {
      this.cmpDescriptor = cmpDesc;
   }

   public WeblogicRdbmsJarBean getDescriptorMBean() {
      return this.cmpDescriptor;
   }

   public void addDefaultHelper(String ejbName, DefaultHelper helper) {
      this.bean2defaultHelper.put(ejbName, helper);
   }

   public DefaultHelper getDefaultHelper(String ejbName) {
      return (DefaultHelper)this.bean2defaultHelper.get(ejbName);
   }

   public abstract void process(Reader var1) throws IOException, XMLParsingException, XMLProcessingException;

   public abstract void process(InputStream var1) throws IOException, XMLParsingException, XMLProcessingException;

   protected void validatePositiveInteger(String val) throws Exception {
      int intVal;
      try {
         intVal = Integer.parseInt(val);
      } catch (NumberFormatException var5) {
         Loggable l = EJBLogger.logparamIntegerLoggable();
         throw new Exception(l.getMessageText());
      }

      if (intVal < 0) {
         Loggable l = EJBLogger.logparamPositiveIntegerLoggable();
         throw new Exception(l.getMessageText());
      }
   }
}
