package weblogic.servlet.internal.dd;

import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webappext.VirtualDirectoryMappingMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public class VirtualDirectoryMappingDescriptor extends BaseServletDescriptor implements VirtualDirectoryMappingMBean, ToXML {
   private static final String VIRTUAL_DIRECTORY_MAPPING = "virtual-directory-mapping";
   private static final String LOCAL_PATH = "local-path";
   private static final String URL_PATTERN = "url-pattern";
   private String localPath;
   private String[] urlPatterns;
   private static String refErr = "Invalid virtual-directory-mapping in weblogic.xml";

   public VirtualDirectoryMappingDescriptor() {
      this.localPath = null;
      this.urlPatterns = null;
      this.localPath = null;
      this.urlPatterns = null;
   }

   public VirtualDirectoryMappingDescriptor(String path, String[] patterns) throws DOMProcessingException {
      this.localPath = null;
      this.urlPatterns = null;
      this.localPath = path;
      this.urlPatterns = patterns;
   }

   public VirtualDirectoryMappingDescriptor(VirtualDirectoryMappingMBean mbean) throws DOMProcessingException {
      this(mbean.getLocalPath(), mbean.getURLPatterns());
   }

   public VirtualDirectoryMappingDescriptor(Element parentElement) throws DOMProcessingException {
      this.localPath = null;
      this.urlPatterns = null;
      this.setLocalPath(DOMUtils.getOptionalValueByTagName(parentElement, "local-path"));
      List pList = DOMUtils.getValuesByTagName(parentElement, "url-pattern");
      if (pList == null) {
         this.setURLPatterns(new String[0]);
      } else {
         Iterator i = pList.iterator();
         String[] result = new String[pList.size()];
         pList.toArray(result);
         this.setURLPatterns(result);
      }

   }

   public String getLocalPath() {
      return this.localPath;
   }

   public void setLocalPath(String path) {
      String old = this.localPath;
      this.localPath = path;
      if (!comp(old, path)) {
         this.firePropertyChange("localPath", old, path);
      }

   }

   public String[] getURLPatterns() {
      return this.urlPatterns;
   }

   public void setURLPatterns(String[] patterns) {
      String[] old = this.urlPatterns;
      this.urlPatterns = patterns;
      if (!comp(old, patterns)) {
         this.firePropertyChange("urlPatterns", old, patterns);
      }

   }

   public void validate() throws DescriptorValidationException {
      boolean ok = true;
      this.removeDescriptorErrors();
      if (this.localPath == null) {
         this.addDescriptorError("local-path is not set in virtual-directory-mapping");
         ok = false;
      }

      if (this.urlPatterns != null) {
         for(int i = 0; i < this.urlPatterns.length; ++i) {
            if (this.urlPatterns[i] == null) {
               this.addDescriptorError("url-pattern is null in virtual-directory-mapping");
               ok = false;
            }
         }
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
      result = result + this.indentStr(indent) + "<" + "virtual-directory-mapping" + ">\n";
      indent += 2;
      if (this.localPath != null) {
         result = result + this.indentStr(indent) + "<" + "local-path" + ">" + this.localPath + "</" + "local-path" + ">\n";
      }

      if (this.urlPatterns != null) {
         for(int i = 0; i < this.urlPatterns.length; ++i) {
            result = result + this.indentStr(indent) + "<" + "url-pattern" + ">" + this.urlPatterns[i] + "</" + "url-pattern" + ">\n";
         }
      }

      indent -= 2;
      result = result + this.indentStr(indent) + "</" + "virtual-directory-mapping" + ">\n";
      return result;
   }
}
