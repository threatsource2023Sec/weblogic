package weblogic.servlet.internal.dd;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.TopLevelDescriptorMBean;
import weblogic.utils.io.XMLWriter;

public final class TopDescriptor extends BaseServletDescriptor implements TopLevelDescriptorMBean {
   private WebAppDescriptor wad;
   private WLWebAppDescriptor wl;
   private String loadPath;

   public TopDescriptor() {
      this.wad = new WebAppDescriptor();
      this.wl = new WLWebAppDescriptor();
   }

   public TopDescriptor(WebAppDescriptor wd, WLWebAppDescriptor wl) {
      this.wad = wd;
      this.wl = wl;
   }

   public void _setLoadPath(String s) {
      this.loadPath = s;
   }

   public String _getLoadPath() {
      return this.loadPath;
   }

   public void setStandard(WebAppDescriptor w) {
      this.wad = w;
   }

   public WebAppDescriptor getStandard() {
      return this.wad;
   }

   public void setWeblogic(WLWebAppDescriptor w) {
      this.wl = w;
   }

   public WLWebAppDescriptor getWeblogic() {
      return this.wl;
   }

   public void toXML(XMLWriter x) {
      this.wad.toXML(x);
      this.wl.toXML(x);
   }

   public String toXML(int indent) {
      StringWriter stringwriter = new StringWriter();
      XMLWriter x = new XMLWriter(stringwriter);
      this.toXML(x);
      x.flush();
      return stringwriter.toString();
   }

   public void register() throws ManagementException {
   }

   public void usePersistenceDestination(String dir) {
      this._setLoadPath(dir);
   }

   public void validate() throws DescriptorValidationException {
      this.wad.validate();
      this.wl.validate();
   }

   public void persist(Properties changelist) throws IOException {
   }

   public void persist() throws IOException {
   }
}
