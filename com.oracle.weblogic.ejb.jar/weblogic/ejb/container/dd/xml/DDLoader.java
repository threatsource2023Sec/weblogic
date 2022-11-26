package weblogic.ejb.container.dd.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;
import org.xml.sax.InputSource;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.j2ee.descriptor.EnterpriseBeansBean;
import weblogic.j2ee.descriptor.MessageDrivenBeanBean;
import weblogic.j2ee.descriptor.SessionBeanBean;
import weblogic.j2ee.descriptor.wl.WeblogicEjbJarBean;
import weblogic.logging.Loggable;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.process.XMLProcessingException;

public abstract class DDLoader {
   protected EjbDescriptorBean ejbDescriptor = null;
   protected Set ejbNamesWithValidatedCTs = new HashSet();
   protected boolean validate = true;
   protected String encoding = null;
   protected Set relationNames = new HashSet();
   protected HashSet ejbNames = new HashSet();

   public void setEJBDescriptor(EjbDescriptorBean ejbDescriptor) {
      this.ejbDescriptor = ejbDescriptor;
   }

   public EjbDescriptorBean getEJBDescriptor() {
      return this.ejbDescriptor;
   }

   public void setValidate(boolean val) {
      this.validate = val;
   }

   public void setEncoding(String encoding) {
      this.encoding = encoding;
   }

   public abstract void process(String var1) throws IOException, XMLParsingException, XMLProcessingException;

   public abstract void process(InputStream var1) throws IOException, XMLParsingException, XMLProcessingException;

   public abstract void process(Reader var1) throws IOException, XMLParsingException, XMLProcessingException;

   public abstract void process(File var1) throws IOException, XMLParsingException, XMLProcessingException;

   public abstract void process(InputSource var1) throws IOException, XMLParsingException, XMLProcessingException;

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

   protected void validatePositiveIntegerOrNoLimitString(String val) throws Exception {
      int intVal;
      try {
         intVal = Integer.parseInt(val);
      } catch (NumberFormatException var4) {
         if (val.equalsIgnoreCase("NO Limit")) {
            return;
         }

         throw new Exception("Parameter must be an integer");
      }

      if (intVal < 0) {
         throw new Exception("Parameter must be a positive integer");
      }
   }

   protected void validateConcurrencyStrategy(String val) throws Exception {
      if (!val.equalsIgnoreCase("readonly") && !val.equalsIgnoreCase("readonlyexclusive") && !val.equalsIgnoreCase("exclusive") && !val.equalsIgnoreCase("database") && !val.equalsIgnoreCase("optimistic")) {
         Loggable l = EJBLogger.logillegalConcurrencyStrategyLoggable(val);
         throw new Exception(l.getMessage());
      }
   }

   protected void validateIntegerGreaterThanZero(String val) throws Exception {
      int intVal;
      try {
         intVal = Integer.parseInt(val);
      } catch (NumberFormatException var5) {
         Loggable l = EJBLogger.logparamIntegerLoggable();
         throw new Exception(l.getMessageText());
      }

      if (intVal <= 0) {
         Loggable l = EJBLogger.logparamPositiveIntegerLoggable();
         throw new Exception(l.getMessageText());
      }
   }

   protected void validateContainerTransaction(EjbJarBean ejbJar, String ejbName) {
      if (!this.ejbNamesWithValidatedCTs.contains(ejbName)) {
         EnterpriseBeansBean ebmb = ejbJar.getEnterpriseBeans();
         SessionBeanBean[] var4 = ebmb.getSessions();
         int var5 = var4.length;

         int var6;
         for(var6 = 0; var6 < var5; ++var6) {
            SessionBeanBean session = var4[var6];
            if (session.getEjbName().equals(ejbName) && session.getTransactionType().equals("Bean")) {
               EJBLogger.logContainerTransactionSetForBeanManagedEJB(ejbName);
            }
         }

         MessageDrivenBeanBean[] var8 = ebmb.getMessageDrivens();
         var5 = var8.length;

         for(var6 = 0; var6 < var5; ++var6) {
            MessageDrivenBeanBean mdb = var8[var6];
            if (mdb.getEjbName().equals(ejbName) && mdb.getTransactionType().equals("Bean")) {
               EJBLogger.logContainerTransactionSetForBeanManagedEJB(ejbName);
            }
         }

         this.ejbNamesWithValidatedCTs.add(ejbName);
      }
   }

   protected boolean addEJBName(String ejbName) {
      if (this.ejbNames == null) {
         this.ejbNames = new HashSet();
      }

      return this.ejbNames.add(ejbName);
   }

   protected void setEntityAlwaysUsesTransactionDefault() {
      WeblogicEjbJarBean wlEjbJar = this.ejbDescriptor.getWeblogicEjbJarBean();
      wlEjbJar.getWeblogicCompatibility().setEntityAlwaysUsesTransaction(true);
   }

   class PersistenceType {
      public String id;
      public String version;
   }
}
