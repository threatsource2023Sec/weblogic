package weblogic.servlet.jsp.dd;

import org.w3c.dom.Element;
import weblogic.management.descriptors.webapp.AttributeMBean;
import weblogic.servlet.internal.dd.BaseServletDescriptor;
import weblogic.servlet.internal.dd.ToXML;
import weblogic.utils.io.XMLWriter;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class AttributeDescriptor extends BaseServletDescriptor implements AttributeMBean, ToXML {
   private String name;
   private String type;
   private String description;
   private boolean required;
   private boolean rtexpr;
   private boolean _12;

   public AttributeDescriptor() {
      this._12 = true;
   }

   public AttributeDescriptor(Element parent, boolean _12) throws DOMProcessingException {
      this._12 = _12;
      String s = null;
      Element e = DOMUtils.getElementByTagName(parent, "name");
      this.name = DOMUtils.getTextData(e);
      e = DOMUtils.getOptionalElementByTagName(parent, "required");
      if (e != null) {
         s = DOMUtils.getTextData(e);
         this.required = "true".equalsIgnoreCase(s) || "yes".equalsIgnoreCase(s);
      }

      e = DOMUtils.getOptionalElementByTagName(parent, "rtexprvalue");
      if (e != null) {
         s = DOMUtils.getTextData(e);
         this.rtexpr = "true".equalsIgnoreCase(s) || "yes".equalsIgnoreCase(s);
      } else {
         this.rtexpr = false;
      }

      if (_12) {
         e = DOMUtils.getOptionalElementByTagName(parent, "type");
         if (e != null) {
            this.type = DOMUtils.getTextData(e);
         }
      }

   }

   public boolean is12() {
      return this._12;
   }

   public void set12(boolean b) {
      this._12 = b;
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
      if (!comp(old, this.type)) {
         this.firePropertyChange("type", old, this.type);
      }

   }

   public boolean isRequired() {
      return this.required;
   }

   public void setRequired(boolean b) {
      if (b != this.required) {
         this.required = b;
         this.firePropertyChange("required", new Boolean(!b), new Boolean(b));
      }

   }

   public boolean isRtexpr() {
      return this.rtexpr;
   }

   public void setRtexpr(boolean b) {
      if (b != this.rtexpr) {
         this.rtexpr = b;
         this.firePropertyChange("rtexpr", new Boolean(!b), new Boolean(b));
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

   public void validate() {
   }

   public void toXML(XMLWriter x) {
      x.println("<attribute>");
      x.incrIndent();
      x.println("<name>" + this.getName() + "</name>");
      x.println("<required>" + this.isRequired() + "</required>");
      x.println("<rtexprvalue>" + this.isRtexpr() + "</rtexprvalue>");
      if (this._12 && this.type != null && (this.type = this.type.trim()).length() > 0) {
         x.println("<type>" + this.getType() + "</type>");
      }

      if (this._12 && this.description != null && (this.description = this.description.trim()).length() > 0) {
         x.println("<description>" + cdata(this.description) + "</description>");
      }

      x.decrIndent();
      x.println("</attribute>");
   }

   public String toXML(int indent) {
      return TLDDescriptor.toXML(this, indent);
   }
}
