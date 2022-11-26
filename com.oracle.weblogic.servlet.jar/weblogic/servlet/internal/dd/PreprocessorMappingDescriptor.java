package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webappext.PreprocessorMBean;
import weblogic.management.descriptors.webappext.PreprocessorMappingMBean;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.WebAppModule;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class PreprocessorMappingDescriptor extends BaseServletDescriptor implements PreprocessorMappingMBean, ToXML {
   private static final String PREPROCESSOR_NAME = "preprocessor-name";
   private static final String URL_PATTERN = "url-pattern";
   private PreprocessorMBean preprocessor;
   private String urlPattern;

   public PreprocessorMappingDescriptor() {
      this.preprocessor = null;
      this.urlPattern = "";
   }

   public PreprocessorMappingDescriptor(WLWebAppDescriptor wad, PreprocessorMappingMBean mbean) {
      this(mbean.getPreprocessor(), mbean.getURLPattern());
   }

   public PreprocessorMappingDescriptor(PreprocessorMBean prep, String pattern) {
      this.preprocessor = prep;
      this.urlPattern = pattern;
   }

   public PreprocessorMappingDescriptor(WLWebAppDescriptor wad, Element parentElement) throws DOMProcessingException {
      this.urlPattern = DOMUtils.getValueByTagName(parentElement, "url-pattern");
      String preprocessorName = DOMUtils.getValueByTagName(parentElement, "preprocessor-name");
      if (wad != null) {
         this.setPreprocessor(wad, preprocessorName);
      }

   }

   public void setPreprocessor(WLWebAppDescriptor wad, String name) {
      if (wad == null) {
         HTTPLogger.logPreprocessorNotFound(name);
      } else if (name == null) {
         HTTPLogger.logPreprocessorNotFound("null");
      } else {
         PreprocessorMBean[] p = wad.getPreprocessors();
         if (p != null) {
            for(int i = 0; i < p.length; ++i) {
               if (p[i] != null && name.equals(p[i].getPreprocessorName())) {
                  this.preprocessor = p[i];
                  break;
               }
            }

            if (this.preprocessor == null) {
               HTTPLogger.logPreprocessorNotFound(name);
            }
         }
      }
   }

   public PreprocessorMBean getPreprocessor() {
      return this.preprocessor;
   }

   public void setPreprocessor(PreprocessorMBean prep) {
      PreprocessorMBean old = this.preprocessor;
      this.preprocessor = prep;
      if (!comp(old, prep)) {
         this.firePropertyChange("preprocessor", old, prep);
      }

   }

   public String getURLPattern() {
      return this.urlPattern;
   }

   public void setURLPattern(String pattern) {
      String old = this.urlPattern;
      this.urlPattern = pattern;
      if (!comp(old, pattern)) {
         this.firePropertyChange("urlPattern", old, pattern);
      }

   }

   public void validate() throws DescriptorValidationException {
      boolean ok = true;
      this.removeDescriptorErrors();
      PreprocessorMBean s = this.getPreprocessor();
      String u = this.getURLPattern();
      if (u != null) {
         u = u.trim();
         this.setURLPattern(u);
      }

      if (u == null || u.length() == 0) {
         this.addDescriptorError("NO_PREPROCESSOR_URL_PATTERN", s == null ? "" : s.getPreprocessorName());
         ok = false;
      }

      if (s == null) {
         this.addDescriptorError("NO_MAPPING_PREPROCESSOR_NAME", u);
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
      StringBuffer result = new StringBuffer("");
      PreprocessorMBean p = this.getPreprocessor();
      String pName = p == null ? "" : p.getPreprocessorName();
      result.append(this.indentStr(indent));
      result.append(this.indentStr(indent + 2));
      result.append("<preprocessor-mapping>\n");
      result.append(this.indentStr(indent + 2));
      result.append("<preprocessor-name>");
      result.append(pName);
      result.append("</preprocessor-name>\n");
      result.append(this.indentStr(indent + 2));
      result.append("<url-pattern>");
      result.append(this.urlPattern);
      result.append("</url-pattern>\n");
      result.append(this.indentStr(indent));
      result.append("</preprocessor-mapping>\n");
      if (WebAppModule.DEBUG.isDebugEnabled()) {
         WebAppModule.DEBUG.debug(result.toString());
      }

      return result.toString();
   }
}
