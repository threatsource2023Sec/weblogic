package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.UIMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class UIDescriptor extends BaseServletDescriptor implements UIMBean {
   private static final long serialVersionUID = 6939835176059638336L;
   public static final String DESCRIPTION = "description";
   public static final String DISPLAY_NAME = "display-name";
   public static final String LARGE_ICON = "large-icon";
   public static final String SMALL_ICON = "small-icon";
   public static final String ICON = "icon";
   private String description;
   private String displayName;
   private String largeIconFileName;
   private String smallIconFileName;

   public UIDescriptor() {
      this((String)null, (String)null, (String)null, (String)null);
   }

   public UIDescriptor(String descr, String disp, String li, String si) {
      this.description = descr;
      this.displayName = disp;
      this.largeIconFileName = li;
      this.smallIconFileName = si;
   }

   public UIDescriptor(UIMBean mbean) {
      this(mbean.getDescription(), mbean.getDisplayName(), mbean.getLargeIconFileName(), mbean.getSmallIconFileName());
   }

   public UIDescriptor(Element parentElement) throws DOMProcessingException {
      this.description = DOMUtils.getOptionalValueByTagName(parentElement, "description");
      this.displayName = DOMUtils.getOptionalValueByTagName(parentElement, "display-name");
      this.largeIconFileName = DOMUtils.getOptionalValueByTagName(parentElement, "large-icon");
      this.smallIconFileName = DOMUtils.getOptionalValueByTagName(parentElement, "small-icon");
      if (this.largeIconFileName == null && this.smallIconFileName == null) {
         Element icon = DOMUtils.getOptionalElementByTagName(parentElement, "icon");
         if (icon != null) {
            this.largeIconFileName = DOMUtils.getOptionalValueByTagName(icon, "large-icon");
            this.smallIconFileName = DOMUtils.getOptionalValueByTagName(icon, "small-icon");
         }
      }

   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String d) {
      String old = this.description;
      this.description = d;
      if (!comp(old, d)) {
         this.firePropertyChange("description", old, d);
      }

   }

   public String getDisplayName() {
      return this.displayName;
   }

   public void setDisplayName(String d) {
      String old = this.displayName;
      this.displayName = d;
      if (!comp(old, d)) {
         this.firePropertyChange("displayName", old, d);
      }

   }

   public String getLargeIconFileName() {
      return this.largeIconFileName;
   }

   public void setLargeIconFileName(String fileName) {
      String old = this.largeIconFileName;
      this.largeIconFileName = fileName;
      if (!comp(old, fileName)) {
         this.firePropertyChange("largeIconFileName", old, fileName);
      }

   }

   public String getSmallIconFileName() {
      return this.smallIconFileName;
   }

   public void setSmallIconFileName(String fileName) {
      String old = this.smallIconFileName;
      this.smallIconFileName = fileName;
      if (!comp(old, fileName)) {
         this.firePropertyChange("smallIconFileName", old, fileName);
      }

   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toXML(int indent) {
      String result = "";
      String smallIcon = this.getSmallIconFileName();
      String largeIcon = this.getLargeIconFileName();
      if (smallIcon != null || largeIcon != null) {
         result = result + this.indentStr(indent) + "<icon>\n";
         if (smallIcon != null) {
            result = result + this.indentStr(indent + 2) + "<small-icon>" + smallIcon + "</small-icon>\n";
         }

         if (largeIcon != null) {
            result = result + this.indentStr(indent + 2) + "<large-icon>" + largeIcon + "</large-icon>\n";
         }

         result = result + this.indentStr(indent) + "</icon>\n";
      }

      if (this.getDisplayName() != null) {
         result = result + this.indentStr(indent) + "<display-name>" + this.getDisplayName() + "</display-name>\n";
      }

      if (this.getDescription() != null) {
         result = result + this.indentStr(indent) + "<description>" + this.getDescription() + "</description>\n";
      }

      return result;
   }
}
