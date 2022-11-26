package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.ParameterMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class ParameterDescriptor extends BaseServletDescriptor implements ToXML, ParameterMBean {
   private static final long serialVersionUID = 4406607312692360385L;
   private static final String PARAM_NAME = "param-name";
   private static final String PARAM_VALUE = "param-value";
   private static final String DESCRIPTION = "description";
   private String description;
   private String paramName;
   private String paramValue;

   public ParameterDescriptor() {
      this((String)null, (String)null, (String)null);
   }

   public ParameterDescriptor(ParameterMBean mbean) {
      this(mbean.getParamName(), mbean.getParamValue(), mbean.getDescription());
   }

   public ParameterDescriptor(String name, String value, String descr) {
      this.description = descr;
      this.paramName = name;
      this.paramValue = value;
   }

   public ParameterDescriptor(Element parentElement) throws DOMProcessingException {
      this.description = DOMUtils.getOptionalValueByTagName(parentElement, "description");
      this.paramName = DOMUtils.getValueByTagName(parentElement, "param-name");
      this.paramValue = DOMUtils.getValueByTagName(parentElement, "param-value");
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

   public String getParamName() {
      return this.paramName;
   }

   public void setParamName(String name) {
      String old = this.paramName;
      this.paramName = name;
      if (!comp(old, name)) {
         this.firePropertyChange("paramName", old, name);
      }

   }

   public String getParamValue() {
      return this.paramValue;
   }

   public void setParamValue(String value) {
      String old = this.paramValue;
      this.paramValue = value;
      if (!comp(old, value)) {
         this.firePropertyChange("paramValue", old, value);
      }

   }

   public void validate() throws DescriptorValidationException {
      boolean ok = true;
      this.removeDescriptorErrors();
      if (this.paramName == null || this.paramName.length() == 0) {
         this.addDescriptorError("NO_PARAM_NAME", this.paramValue);
         ok = false;
      }

      if (this.paramValue == null) {
         this.addDescriptorError("NO_PARAM_VALUE", this.paramName);
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
      result = result + this.indentStr(indent) + "<context-param>\n";
      indent += 2;
      String outStr = this.paramValue;
      if (outStr == null) {
         outStr = "";
      }

      result = result + this.indentStr(indent) + "<param-name>" + this.paramName + "</param-name>\n";
      result = result + this.indentStr(indent) + "<param-value>" + outStr + "</param-value>\n";
      if (this.description != null && this.description.length() > 0) {
         result = result + this.indentStr(indent) + "<description>" + this.description + "</description>\n";
      }

      indent -= 2;
      result = result + this.indentStr(indent) + "</context-param>\n";
      return result;
   }
}
