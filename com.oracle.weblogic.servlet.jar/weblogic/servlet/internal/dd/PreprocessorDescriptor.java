package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webappext.PreprocessorMBean;
import weblogic.servlet.internal.WebAppModule;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public class PreprocessorDescriptor extends BaseServletDescriptor implements ToXML, PreprocessorMBean {
   private static final String PREPROCESSOR_NAME = "preprocessor-name";
   private static final String PREPROCESSOR_CLASS = "preprocessor-class";
   private String preprocessorName;
   private String preprocessorClass;

   public PreprocessorDescriptor() {
   }

   public PreprocessorDescriptor(PreprocessorMBean mbean) {
      this.setPreprocessorName(mbean.getPreprocessorName());
      this.setPreprocessorClass(mbean.getPreprocessorClass());
   }

   public PreprocessorDescriptor(Element parentElement) throws DOMProcessingException {
      this.preprocessorName = DOMUtils.getValueByTagName(parentElement, "preprocessor-name");
      this.preprocessorClass = DOMUtils.getValueByTagName(parentElement, "preprocessor-class");
      if (this.preprocessorClass == null) {
         throw new DOMProcessingException("Preprocessor node does not contain preprocessor-class node");
      }
   }

   public String getPreprocessorName() {
      return this.preprocessorName;
   }

   public void setPreprocessorName(String name) {
      String old = this.preprocessorName;
      this.preprocessorName = name;
      if (!comp(old, name)) {
         this.firePropertyChange("preprocessorName", old, name);
      }

   }

   public String getPreprocessorClass() {
      return this.preprocessorClass;
   }

   public void setPreprocessorClass(String className) {
      String old = this.preprocessorClass;
      this.preprocessorClass = className;
      if (!comp(old, className)) {
         this.firePropertyChange("preprocessorClass", old, className);
      }

   }

   public void validate() throws DescriptorValidationException {
      boolean ok = true;
      this.removeDescriptorErrors();
      String s = this.getPreprocessorName();
      if (s != null && (s = s.trim()).length() != 0) {
         this.setPreprocessorName(s);
      } else {
         this.addDescriptorError("NO_PREPROCESSOR_NAME");
         ok = false;
      }

      s = this.getPreprocessorClass();
      if (s == null) {
         this.addDescriptorError("NO_PREPROCESSOR_CLASS", this.getPreprocessorName() == null ? "" : this.getPreprocessorName());
         ok = false;
      }

      if (s != null) {
         s = s.trim();
         this.setPreprocessorClass(s);
      }

      if (!ok) {
         throw new DescriptorValidationException();
      }
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toXML(int indent) {
      StringBuffer result = new StringBuffer();
      result.append(this.indentStr(indent));
      result.append("<preprocessor>\n");
      result.append(this.indentStr(indent + 2));
      result.append("<preprocessor-name>");
      result.append(this.getPreprocessorName());
      result.append("</preprocessor-name>\n");
      result.append(this.indentStr(indent + 2));
      result.append("<preprocessor-class>");
      result.append(this.getPreprocessorClass());
      result.append("</preprocessor-class>\n");
      result.append(this.indentStr(indent));
      result.append("</preprocessor>");
      if (WebAppModule.DEBUG.isDebugEnabled()) {
         WebAppModule.DEBUG.debug(result.toString());
      }

      return result.toString();
   }
}
