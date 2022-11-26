package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.ListenerMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class ListenerDescriptor extends BaseServletDescriptor implements ToXML, ListenerMBean {
   private static final long serialVersionUID = 9012728722776252126L;
   private static final String LISTENER_CLASS = "listener-class";
   private String listenerClassName;

   public ListenerDescriptor() {
   }

   public ListenerDescriptor(ListenerMBean mbean) {
      this(mbean.getListenerClassName());
   }

   public ListenerDescriptor(String className) {
      this.listenerClassName = className;
   }

   public ListenerDescriptor(Element parentElement) throws DOMProcessingException {
      this.listenerClassName = DOMUtils.getValueByTagName(parentElement, "listener-class");
   }

   public String getListenerClassName() {
      return this.listenerClassName;
   }

   public void setListenerClassName(String name) {
      String old = this.listenerClassName;
      this.listenerClassName = name;
      if (!comp(old, name)) {
         this.firePropertyChange("listenerClassName", old, name);
      }

   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
      if (this.listenerClassName == null) {
         this.addDescriptorError("NO_LISTENER_CLASS");
         throw new DescriptorValidationException();
      }
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toXML(int indent) {
      String result = "";
      if (this.listenerClassName != null) {
         result = result + this.indentStr(indent) + "<listener>\n";
         indent += 2;
         result = result + this.indentStr(indent) + "<listener-class>" + this.listenerClassName + "</listener-class>\n";
         indent -= 2;
         result = result + this.indentStr(indent) + "</listener>\n";
      }

      return result;
   }
}
