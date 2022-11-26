package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.ResourceRefMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class ResourceReference extends BaseServletDescriptor implements ResourceRefMBean {
   private static final long serialVersionUID = -4930785984109869909L;
   String refName;
   String refType;
   String auth;
   String sharingScope;
   String description;

   public ResourceReference() {
   }

   public ResourceReference(String description, String name, String type, String auth) {
      this.setRefName(name);
      this.setRefType(type);
      this.setAuth(auth);
      this.setDescription(description);
   }

   public ResourceReference(ResourceRefMBean mbean) {
      this.setDescription(mbean.getDescription());
      this.setRefName(mbean.getRefName());
      this.setRefType(mbean.getRefType());
      this.setAuth(mbean.getAuth());
      this.setSharingScope(mbean.getSharingScope());
   }

   public ResourceReference(Element parentElement) throws DOMProcessingException {
      this.setDescription(DOMUtils.getOptionalValueByTagName(parentElement, "description"));
      this.setRefName(DOMUtils.getValueByTagName(parentElement, "res-ref-name"));
      this.setRefType(DOMUtils.getValueByTagName(parentElement, "res-type"));
      this.setAuth(DOMUtils.getValueByTagName(parentElement, "res-auth"));
      this.setSharingScope(DOMUtils.getOptionalValueByTagName(parentElement, "res-sharing-scope"));
   }

   public String getRefName() {
      return this.refName;
   }

   public void setRefName(String n) {
      String old = this.refName;
      this.refName = n;
      if (!comp(old, n)) {
         this.firePropertyChange("refName", old, n);
      }

   }

   public String getRefType() {
      return this.refType;
   }

   public void setRefType(String t) {
      String old = this.refType;
      this.refType = t;
      if (!comp(old, t)) {
         this.firePropertyChange("refType", old, t);
      }

   }

   public String getAuth() {
      return this.auth;
   }

   public void setAuth(String authMode) {
      String old = this.auth;
      this.auth = authMode;
      if (!comp(old, authMode)) {
         this.firePropertyChange("auth", old, authMode);
      }

   }

   public String getSharingScope() {
      return this.sharingScope;
   }

   public void setSharingScope(String s) {
      String old = this.sharingScope;
      this.sharingScope = s;
      if (!comp(old, s)) {
         this.firePropertyChange("sharingScope", old, s);
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

   public String toString() {
      return this.getRefName();
   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toXML(int indent) {
      String result = "";
      result = result + this.indentStr(indent) + "<resource-ref>\n";
      indent += 2;
      String d = this.getDescription();
      if (d != null) {
         result = result + this.indentStr(indent) + "<description>" + d + "</description>\n";
      }

      result = result + this.indentStr(indent) + "<res-ref-name>" + this.getRefName() + "</res-ref-name>\n";

      try {
         result = result + this.indentStr(indent) + "<res-type>" + this.getRefType() + "</res-type>\n";
      } catch (Exception var5) {
      }

      result = result + this.indentStr(indent) + "<res-auth>" + this.getAuth() + "</res-auth>\n";
      if (this.sharingScope != null) {
         result = result + this.indentStr(indent) + "<res-sharing-scope>" + this.getSharingScope() + "</res-sharing-scope>\n";
      }

      indent -= 2;
      result = result + this.indentStr(indent) + "</resource-ref>\n";
      return result;
   }
}
