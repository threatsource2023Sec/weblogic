package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.SessionConfigMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class SessionDescriptor extends BaseServletDescriptor implements ToXML, SessionConfigMBean {
   private static final long serialVersionUID = 4376958427997195978L;
   private static final String SESSION_TIMEOUT = "session-timeout";
   private int sessionTimeout = -2;

   public SessionDescriptor() {
   }

   public SessionDescriptor(SessionConfigMBean mbean) {
      this.setSessionTimeout(mbean.getSessionTimeout());
   }

   public SessionDescriptor(int t) {
      this.sessionTimeout = t;
   }

   public SessionDescriptor(Element parentElement) throws DOMProcessingException {
      String tmp = DOMUtils.getOptionalValueByTagName(parentElement, "session-timeout");
      if (tmp != null) {
         try {
            this.sessionTimeout = Integer.parseInt(tmp);
         } catch (NumberFormatException var4) {
         }
      }

   }

   public int getSessionTimeout() {
      return this.sessionTimeout;
   }

   public void setSessionTimeout(int t) {
      int old = this.sessionTimeout;
      this.sessionTimeout = t;
      if (old != t) {
         this.firePropertyChange("sessionTimeout", new Integer(old), new Integer(t));
      }

   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
      if (this.sessionTimeout != -2 && this.sessionTimeout < 0) {
         this.addDescriptorError("INVALID_SESSION_TIMEOUT", "" + this.sessionTimeout);
         throw new DescriptorValidationException("INVALID_SESSION_TIMEOUT");
      }
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toXML(int indent) {
      String result = "";
      if (this.sessionTimeout != -2) {
         result = result + this.indentStr(indent) + "<session-config>\n";
         indent += 2;
         result = result + this.indentStr(indent) + "<session-timeout>" + this.sessionTimeout + "</session-timeout>\n";
         indent -= 2;
         result = result + this.indentStr(indent) + "</session-config>\n";
      }

      return result;
   }
}
