package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.MimeMappingMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class MimeMappingDescriptor extends BaseServletDescriptor implements ToXML, MimeMappingMBean {
   private static final long serialVersionUID = -7123219540911766475L;
   private static final String EXTENSION = "extension";
   private static final String MIME_TYPE = "mime-type";
   private String extension;
   private String mimeType;

   public MimeMappingDescriptor() {
      this("", "");
   }

   public MimeMappingDescriptor(MimeMappingMBean mbean) {
      this(mbean.getExtension(), mbean.getMimeType());
   }

   public MimeMappingDescriptor(String ext, String type) {
      this.extension = ext;
      this.mimeType = type;
   }

   public MimeMappingDescriptor(Element parentElement) throws DOMProcessingException {
      this.extension = DOMUtils.getValueByTagName(parentElement, "extension");
      this.mimeType = DOMUtils.getValueByTagName(parentElement, "mime-type");
   }

   public String getExtension() {
      return this.extension;
   }

   public void setExtension(String ext) {
      String old = this.extension;
      this.extension = ext;
      if (!comp(old, ext)) {
         this.firePropertyChange("extension", old, ext);
      }

   }

   public String getMimeType() {
      return this.mimeType;
   }

   public void setMimeType(String type) {
      String old = this.mimeType;
      this.mimeType = type;
      if (!comp(old, type)) {
         this.firePropertyChange("mimeType", old, type);
      }

   }

   public void validate() throws DescriptorValidationException {
      boolean ok = true;
      this.removeDescriptorErrors();
      if (this.extension == null || (this.extension = this.extension.trim()).length() == 0) {
         this.addDescriptorError("NO_MIME_EXTENSION", this.mimeType);
         ok = false;
      }

      if (this.mimeType == null || (this.mimeType = this.mimeType.trim()).length() == 0) {
         this.addDescriptorError("NO_MIME_TYPE", this.extension);
         ok = false;
      }

      if (!ok) {
         throw new DescriptorValidationException();
      }
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toXML(int indent) {
      String result = "";
      result = result + this.indentStr(indent) + "<mime-mapping>\n";
      indent += 2;
      result = result + this.indentStr(indent) + "<extension>" + this.extension + "</extension>\n";
      result = result + this.indentStr(indent) + "<mime-type>" + this.mimeType + "</mime-type>\n";
      indent -= 2;
      result = result + this.indentStr(indent) + "</mime-mapping>\n";
      return result;
   }
}
