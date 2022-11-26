package weblogic.j2ee.descriptors;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Properties;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.ApplicationDescriptorMBean;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.Encoding;
import weblogic.management.descriptors.TopLevelDescriptorMBean;
import weblogic.management.descriptors.XMLDeclarationMBean;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.descriptors.application.J2EEApplicationDescriptorMBean;
import weblogic.management.descriptors.application.weblogic.WeblogicApplicationMBean;
import weblogic.utils.Debug;
import weblogic.utils.io.XMLWriter;
import weblogic.utils.jars.RandomAccessJarFile;

public final class ApplicationDescriptorMBeanImpl extends XMLElementMBeanDelegate implements ApplicationDescriptorMBean, TopLevelDescriptorMBean {
   private static final long serialVersionUID = 473061217792399747L;
   private String persistentDestination = null;
   private J2EEApplicationDescriptorMBean j2eeAppDescr;
   private WeblogicApplicationMBean weblogicAppDescr;

   public ApplicationDescriptorMBeanImpl(String nameArg) {
      super(nameArg);
   }

   public ApplicationDescriptorMBeanImpl() {
   }

   public void setJ2EEApplicationDescriptor(J2EEApplicationDescriptorMBean j2eeAppDescr) {
      this.j2eeAppDescr = j2eeAppDescr;
   }

   public J2EEApplicationDescriptorMBean getJ2EEApplicationDescriptor() {
      return this.j2eeAppDescr;
   }

   public void unregister() throws ManagementException {
      super.unregister();
      if (this.j2eeAppDescr != null) {
         this.j2eeAppDescr.unregister();
      }

      if (this.weblogicAppDescr != null) {
         this.weblogicAppDescr.unregister();
      }

   }

   public void toXML(XMLWriter x) {
      if (this.j2eeAppDescr != null) {
         this.j2eeAppDescr.toXML(x);
      }

   }

   public String toXML(int level) {
      return this.toXML();
   }

   public String toXML() {
      StringWriter w = new StringWriter();
      this.toXML(new XMLWriter(w));
      return w.toString();
   }

   public void validate() throws DescriptorValidationException {
   }

   public void usePersistenceDestination(String dir) {
      this.persistentDestination = dir;
   }

   public void persist() throws IOException {
      this.persist((Properties)null);
   }

   public void persist(Properties changelist) throws IOException {
      Debug.assertion(this.persistentDestination != null, "must call usePersistenceDestination on " + this + " before calling persist");
      File f = new File(this.persistentDestination);
      OutputStream ostream = null;
      RandomAccessJarFile raj = null;
      File changeFile;
      String tempDir;
      XMLWriter writer;
      if (this.j2eeAppDescr != null) {
         if (f.isDirectory()) {
            changeFile = new File(f, "META-INF/application.xml");
            ostream = new FileOutputStream(changeFile);
         } else {
            tempDir = ".";
            raj = new RandomAccessJarFile(new File(tempDir), f);
            ostream = raj.writeEntry("META-INF/application.xml", true);
         }

         writer = this.getXMLWriter(this.j2eeAppDescr, (OutputStream)ostream);
         this.toXML(writer);
         writer.close();
      }

      if (this.weblogicAppDescr != null) {
         if (f.isDirectory()) {
            changeFile = new File(f, "META-INF/weblogic-application.xml");
            ostream = new FileOutputStream(changeFile);
         } else {
            tempDir = ".";
            raj = new RandomAccessJarFile(new File(tempDir), f);
            ostream = raj.writeEntry("META-INF/weblogic-application.xml", true);
         }

         writer = this.getXMLWriter(this.weblogicAppDescr, (OutputStream)ostream);
         writer.println(this.weblogicAppDescr.toXML(2));
         writer.close();
      }

      if (changelist != null) {
         if (f.isDirectory()) {
            changeFile = new File(f.getPath() + File.separator + "META-INF", "_wl_dynamic_change_list.properties");
            ostream = new FileOutputStream(changeFile);
         } else {
            ostream = raj.writeEntry("META-INF/_wl_dynamic_change_list.properties", true);
         }

         changelist.store((OutputStream)ostream, "Dynamic DD change list");
         ((OutputStream)ostream).close();
      }

      if (raj != null) {
         raj.close();
      }

   }

   private XMLWriter getXMLWriter(XMLDeclarationMBean mbean, OutputStream ostream) throws IOException {
      String j2eeAppDescrEncoding = mbean.getEncoding();
      XMLWriter writer = null;
      if (j2eeAppDescrEncoding != null) {
         String j2eeAppDescrJavaEncoding = Encoding.getIANA2JavaMapping(j2eeAppDescrEncoding);
         writer = new XMLWriter(ostream, j2eeAppDescrJavaEncoding);
      } else {
         writer = new XMLWriter(ostream);
      }

      return writer;
   }

   public void setWeblogicApplicationDescriptor(WeblogicApplicationMBean weblogicApplicationMBean) {
      this.weblogicAppDescr = weblogicApplicationMBean;
   }

   public WeblogicApplicationMBean getWeblogicApplicationDescriptor() {
      return this.weblogicAppDescr;
   }
}
