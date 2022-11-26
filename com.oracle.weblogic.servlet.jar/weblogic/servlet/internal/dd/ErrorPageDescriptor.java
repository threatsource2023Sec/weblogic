package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.ErrorPageMBean;
import weblogic.utils.io.XMLWriter;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class ErrorPageDescriptor extends BaseServletDescriptor implements ToXML, ErrorPageMBean {
   private static final long serialVersionUID = -1138390047099452104L;
   private static final String ERROR_CODE = "error-code";
   private static final String EXCEPTION_TYPE = "exception-type";
   private static final String LOCATION = "location";
   private String eCode;
   private String eType;
   private String location;

   public ErrorPageDescriptor() {
   }

   public ErrorPageDescriptor(ErrorPageMBean mbean) {
      this(mbean.getErrorCode(), mbean.getExceptionType(), mbean.getLocation());
   }

   public ErrorPageDescriptor(String code, String type, String loc) {
      this.eCode = code;
      this.eType = type;
      this.location = loc;
   }

   public ErrorPageDescriptor(Element parentElement) throws DOMProcessingException {
      this.eCode = DOMUtils.getOptionalValueByTagName(parentElement, "error-code");
      if (this.eCode == null) {
         this.eType = DOMUtils.getOptionalValueByTagName(parentElement, "exception-type");
      }

      this.location = DOMUtils.getValueByTagName(parentElement, "location");
   }

   public void validate() throws DescriptorValidationException {
      boolean ok = true;
      this.removeDescriptorErrors();
      String code = this.getErrorCode();
      String type = this.getExceptionType();
      if (code != null && code.length() > 0 && type != null && type.length() > 0) {
         this.addDescriptorError("MULTIPLE_DEFINES_ERROR_PAGE", code, type);
         ok = false;
      }

      if (code != null && code.length() > 0) {
         this.eCode = code.trim();

         try {
            Integer.parseInt(this.eCode);
         } catch (Exception var5) {
            this.addDescriptorError("INVALID_ERROR_CODE", this.eCode);
            ok = false;
         }
      }

      String loc = this.getLocation();
      if (loc == null || loc.trim().length() == 0) {
         this.addDescriptorError("NO_ERROR_PAGE_LOCATION");
         ok = false;
      }

      if (!ok) {
         throw new DescriptorValidationException();
      }
   }

   public void register() throws ManagementException {
      super.register();
   }

   public void setError(String s) {
      try {
         s = s.trim();
         Integer.parseInt(s);
         this.setErrorCode(s);
         this.setExceptionType((String)null);
      } catch (Exception var3) {
         this.setErrorCode((String)null);
         this.setExceptionType(s);
      }

   }

   public String getError() {
      return this.eCode != null && this.eCode.length() > 0 ? this.eCode : this.eType;
   }

   public String getErrorCode() {
      return this.eCode;
   }

   public void setErrorCode(String code) {
      String old = this.eCode;
      this.eCode = code;
      if (!comp(old, code)) {
         this.firePropertyChange("errorCode", old, code);
      }

   }

   public String getExceptionType() {
      return this.eType;
   }

   public void setExceptionType(String type) {
      String old = this.eType;
      this.eType = type;
      if (!comp(old, type)) {
         this.firePropertyChange("exceptionType", old, type);
      }

   }

   public String getLocation() {
      return this.location;
   }

   public void setLocation(String loc) {
      String old = this.location;
      this.location = loc;
      if (!comp(old, loc)) {
         this.firePropertyChange("location", old, loc);
      }

   }

   public String toXML(int indent) {
      String result = "";
      result = result + this.indentStr(indent) + "<error-page>\n";
      indent += 2;
      if (this.eCode != null) {
         result = result + this.indentStr(indent) + "<error-code>" + this.eCode + "</error-code>\n";
      } else {
         result = result + this.indentStr(indent) + "<exception-type>" + this.eType + "</exception-type>\n";
      }

      result = result + this.indentStr(indent) + "<location>" + this.location + "</location>\n";
      indent -= 2;
      result = result + this.indentStr(indent) + "</error-page>\n";
      return result;
   }

   public static void main(String[] a) throws Exception {
      ToXML x = new ErrorPageDescriptor("401", (String)null, "/404.jsp");
      XMLWriter pw = new XMLWriter(System.out);
      x.toXML(pw);
      pw.flush();
   }
}
