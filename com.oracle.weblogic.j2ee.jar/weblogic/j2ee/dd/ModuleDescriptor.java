package weblogic.j2ee.dd;

import java.io.StringWriter;
import weblogic.application.ApplicationFileManager;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.descriptors.application.ModuleMBean;
import weblogic.utils.io.XMLWriter;

public abstract class ModuleDescriptor extends XMLElementMBeanDelegate implements ModuleMBean {
   private String uri;
   private String altDDuri;

   public ModuleDescriptor() {
   }

   public ModuleDescriptor(String uri) {
      this();
      this.uri = uri;
      this.altDDuri = null;
   }

   public String getURI() {
      return this.uri;
   }

   public void setURI(String uri) {
      String old = this.uri;
      this.uri = uri;
      this.checkChange("uri", old, uri);
   }

   public String getModuleURI() {
      return this.uri;
   }

   public void setModuleURI(String uri) {
      String old = this.uri;
      this.uri = uri;
      this.checkChange("moduleURI", old, uri);
   }

   public String getModuleKey() {
      return this.uri;
   }

   public String getName() {
      return this.uri;
   }

   public void setName(String name) {
      this.setURI(name);
   }

   public String getAltDDURI() {
      return this.altDDuri;
   }

   public void setAltDDURI(String altDDuri) {
      String old = this.altDDuri;
      this.altDDuri = altDDuri;
      this.checkChange("altDDURI", old, altDDuri);
   }

   public void register() throws ManagementException {
      super.register();
   }

   public void unregister() throws ManagementException {
      super.unregister();
   }

   public String toXML() {
      StringWriter w = new StringWriter();
      this.toXML(new XMLWriter(w));
      return w.toString();
   }

   public abstract void toXML(XMLWriter var1);

   public abstract String getAdminMBeanType(ApplicationFileManager var1);
}
