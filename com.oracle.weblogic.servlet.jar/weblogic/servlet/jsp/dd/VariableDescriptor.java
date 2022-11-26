package weblogic.servlet.jsp.dd;

import org.w3c.dom.Element;
import weblogic.management.descriptors.webapp.VariableMBean;
import weblogic.servlet.internal.dd.BaseServletDescriptor;
import weblogic.servlet.internal.dd.ToXML;
import weblogic.utils.io.XMLWriter;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class VariableDescriptor extends BaseServletDescriptor implements VariableMBean, ToXML {
   private String name;
   private String type;
   private String description;
   boolean nameFromAttribute;
   int scope;
   boolean declare = true;

   public VariableDescriptor() {
   }

   public VariableDescriptor(Element parent) throws DOMProcessingException {
      Element e = null;
      e = DOMUtils.getOptionalElementByTagName(parent, "name-given");
      if (e != null) {
         this.name = DOMUtils.getTextData(e);
         this.nameFromAttribute = false;
      } else {
         e = DOMUtils.getOptionalElementByTagName(parent, "name-from-attribute");
         this.name = DOMUtils.getTextData(e);
         this.nameFromAttribute = true;
      }

      this.type = "java.lang.String";
      e = DOMUtils.getOptionalElementByTagName(parent, "variable-class");
      if (e != null) {
         this.type = DOMUtils.getTextData(e);
      }

      this.declare = true;
      e = DOMUtils.getOptionalElementByTagName(parent, "declare");
      String s;
      if (e != null) {
         s = DOMUtils.getTextData(e);
         this.declare = "true".equalsIgnoreCase(s) || "yes".equalsIgnoreCase(s);
      }

      this.scope = 0;
      e = DOMUtils.getOptionalElementByTagName(parent, "scope");
      if (e != null) {
         s = DOMUtils.getTextData(e);
         if ("AT_BEGIN".equalsIgnoreCase(s)) {
            this.scope = 1;
         } else if ("AT_END".equalsIgnoreCase(s)) {
            this.scope = 2;
         }
      }

   }

   public String getName() {
      return this.name;
   }

   public void setName(String s) {
      String old = this.name;
      this.name = s;
      if (!comp(old, this.name)) {
         this.firePropertyChange("name", old, this.name);
      }

   }

   public String getType() {
      return this.type;
   }

   public void setType(String s) {
      String old = this.type;
      this.type = s;
      if (!comp(old, s)) {
         this.firePropertyChange("type", old, s);
      }

   }

   public boolean getNameFromAttribute() {
      return this.nameFromAttribute;
   }

   public void setNameFromAttribute(boolean b) {
      if (this.nameFromAttribute != b) {
         this.nameFromAttribute = b;
         this.firePropertyChange("nameFromAttribute", new Boolean(!b), new Boolean(b));
      }

   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String s) {
      String old = this.description;
      this.description = s;
      if (!comp(old, s)) {
         this.firePropertyChange("description", old, s);
      }

   }

   public boolean getDeclare() {
      return this.declare;
   }

   public void setDeclare(boolean b) {
      if (this.declare != b) {
         this.declare = b;
         this.firePropertyChange("declare", new Boolean(!b), new Boolean(b));
      }

   }

   public int getScope() {
      return this.scope;
   }

   public void setScope(int s) {
      int old = this.scope;
      this.scope = s;
      if (this.scope != s) {
         this.firePropertyChange("scope", new Integer(old), new Integer(s));
      }

   }

   public void setScopeStr(String s) {
      String old = this.getScopeStr();
      if (s != null) {
         if (s.equalsIgnoreCase("AT_BEGIN")) {
            this.scope = 1;
         } else if (s.equalsIgnoreCase("AT_END")) {
            this.scope = 2;
         } else if (s.equalsIgnoreCase("NESTED")) {
            this.scope = 0;
         }

         if (!comp(old, this.getScopeStr())) {
            this.firePropertyChange("scopeStr", old, this.getScopeStr());
         }

      }
   }

   public String getScopeStr() {
      if (this.scope == 1) {
         return "AT_BEGIN";
      } else {
         return this.scope == 2 ? "AT_END" : "NESTED";
      }
   }

   public void validate() {
      throw new Error("NYI");
   }

   public void toXML(XMLWriter x) {
      x.println("<variable>");
      x.incrIndent();
      if (this.getNameFromAttribute()) {
         x.println("<name-from-attribute>" + this.getName() + "</name-from-attribute>");
      } else {
         x.println("<name-given>" + this.getName() + "</name-given>");
      }

      x.println("<variable-class>" + this.getType() + "</variable-class>");
      x.println("<declare>" + this.getDeclare() + "</declare>");
      x.println("<scope>" + this.getScopeStr() + "</scope>");
      if (this.description != null && (this.description = this.description.trim()).length() > 0) {
         x.println("<description>" + cdata(this.description) + "</description>");
      }

      x.decrIndent();
      x.println("</variable>");
   }

   public String toXML(int indent) {
      return TLDDescriptor.toXML(this, indent);
   }
}
