package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.EjbRefMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public class EJBReference extends BaseServletDescriptor implements EjbRefMBean {
   private static final long serialVersionUID = 6384556811917373836L;
   String description;
   String ejbLink;
   String ejbRefName;
   String ejbRefType;
   String home;
   String remote;
   String runAs;

   public EJBReference() {
   }

   public EJBReference(String ejbRefDescription, String ejbRefName, String ejbRefType, String homeInterfaceName, String remoteInterfaceName, String linkedEjbName) {
      this.setDescription(ejbRefDescription);
      this.setEJBRefName(ejbRefName);
      this.setEJBRefType(ejbRefType);
      this.setHomeInterfaceName(homeInterfaceName);
      this.setRemoteInterfaceName(remoteInterfaceName);
      this.setEJBLinkName(linkedEjbName);
   }

   public EJBReference(EjbRefMBean mbean) {
      this.setDescription(mbean.getDescription());
      this.setEJBLinkName(mbean.getEJBLinkName());
      this.setEJBRefName(mbean.getEJBRefName());
      this.setEJBRefType(mbean.getEJBRefType());
      this.setHomeInterfaceName(mbean.getHomeInterfaceName());
      this.setRemoteInterfaceName(mbean.getRemoteInterfaceName());
      this.setRunAs(mbean.getRunAs());
   }

   public EJBReference(Element parentElement) throws DOMProcessingException {
      this.setDescription(DOMUtils.getOptionalValueByTagName(parentElement, "description"));
      this.setEJBRefName(DOMUtils.getValueByTagName(parentElement, "ejb-ref-name"));
      this.setEJBRefType(DOMUtils.getValueByTagName(parentElement, "ejb-ref-type"));
      this.setHomeInterfaceName(DOMUtils.getValueByTagName(parentElement, "home"));
      this.setRemoteInterfaceName(DOMUtils.getValueByTagName(parentElement, "remote"));
      this.setEJBLinkName(DOMUtils.getOptionalValueByTagName(parentElement, "ejb-link"));
      this.setRunAs(DOMUtils.getOptionalValueByTagName(parentElement, "run-as"));
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

   public String getEJBRefName() {
      return this.ejbRefName;
   }

   public void setEJBRefName(String rn) {
      String old = this.ejbRefName;
      this.ejbRefName = rn;
      if (!comp(old, rn)) {
         this.firePropertyChange("ejbRefName", old, rn);
      }

   }

   public String getEJBRefType() {
      return this.ejbRefType;
   }

   public void setEJBRefType(String t) {
      String old = this.ejbRefType;
      this.ejbRefType = t;
      if (!comp(old, t)) {
         this.firePropertyChange("ejbRefType", old, t);
      }

   }

   public String getHomeInterfaceName() {
      return this.home;
   }

   public void setHomeInterfaceName(String h) {
      String old = this.home;
      this.home = h;
      if (!comp(old, h)) {
         this.firePropertyChange("homeInterfaceName", old, h);
      }

   }

   public String getEJBLinkName() {
      return this.ejbLink;
   }

   public void setEJBLinkName(String l) {
      String old = this.ejbLink;
      this.ejbLink = l;
      if (!comp(old, l)) {
         this.firePropertyChange("ejbLinkName", old, l);
      }

   }

   public String getRemoteInterfaceName() {
      return this.remote;
   }

   public void setRemoteInterfaceName(String r) {
      String old = this.remote;
      this.remote = r;
      if (!comp(old, r)) {
         this.firePropertyChange("remoteInterfaceName", old, r);
      }

   }

   public String getRunAs() {
      return this.runAs;
   }

   public void setRunAs(String r) {
      String old = this.runAs;
      this.runAs = r;
      if (!comp(old, r)) {
         this.firePropertyChange("runAs", old, r);
      }

   }

   public String toString() {
      return this.getEJBRefName();
   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toXML(int indent) {
      String result = "";
      result = result + this.indentStr(indent) + "<ejb-ref>\n";
      indent += 2;
      String d = this.getDescription();
      if (d != null) {
         result = result + this.indentStr(indent) + "<description>" + d + "</description>\n";
      }

      result = result + this.indentStr(indent) + "<ejb-ref-name>" + this.getEJBRefName() + "</ejb-ref-name>\n";
      result = result + this.indentStr(indent) + "<ejb-ref-type>" + this.getEJBRefType() + "</ejb-ref-type>\n";
      result = result + this.indentStr(indent) + "<home>" + this.getHomeInterfaceName() + "</home>\n";
      result = result + this.indentStr(indent) + "<remote>" + this.getRemoteInterfaceName() + "</remote>\n";
      String link = this.getEJBLinkName();
      if (link != null) {
         result = result + this.indentStr(indent) + "<ejb-link>" + link + "</ejb-link>\n";
      }

      indent -= 2;
      result = result + this.indentStr(indent) + "</ejb-ref>\n";
      return result;
   }

   public boolean isLocalLink() {
      return false;
   }
}
