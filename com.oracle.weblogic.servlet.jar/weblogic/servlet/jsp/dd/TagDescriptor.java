package weblogic.servlet.jsp.dd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;
import weblogic.management.descriptors.webapp.AttributeMBean;
import weblogic.management.descriptors.webapp.TagMBean;
import weblogic.management.descriptors.webapp.VariableMBean;
import weblogic.servlet.internal.dd.BaseServletDescriptor;
import weblogic.servlet.internal.dd.ToXML;
import weblogic.utils.io.XMLWriter;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class TagDescriptor extends BaseServletDescriptor implements TagMBean, ToXML {
   private String name;
   private String classname;
   private String extraInfoClassname;
   private String bodyContent;
   private String description;
   AttributeMBean[] atts;
   VariableMBean[] vars;
   private boolean _12;

   private String getTEIDTDName() {
      return this.is12() ? "tei-class" : "teiclass";
   }

   private String getTagClassDTDName() {
      return this.is12() ? "tag-class" : "tagclass";
   }

   private String getBodyContentDTDName() {
      return this.is12() ? "body-content" : "bodycontent";
   }

   private String getDescriptionDTDName() {
      return this.is12() ? "description" : "info";
   }

   public TagDescriptor() {
      this._12 = true;
   }

   public TagDescriptor(Element parent, boolean _12) throws DOMProcessingException {
      this._12 = _12;
      Element e = DOMUtils.getElementByTagName(parent, "name");
      this.name = DOMUtils.getTextData(e);
      e = DOMUtils.getElementByTagName(parent, this.getTagClassDTDName());
      this.classname = DOMUtils.getTextData(e);
      e = DOMUtils.getOptionalElementByTagName(parent, this.getTEIDTDName());
      if (e != null) {
         this.extraInfoClassname = DOMUtils.getTextData(e);
      }

      e = DOMUtils.getOptionalElementByTagName(parent, this.getBodyContentDTDName());
      if (e != null) {
         this.bodyContent = DOMUtils.getTextData(e);
      }

      e = DOMUtils.getOptionalElementByTagName(parent, this.getDescriptionDTDName());
      if (e != null) {
         this.description = DOMUtils.getTextData(e);
      }

      List elts = DOMUtils.getOptionalElementsByTagName(parent, "variable");
      List p = new ArrayList();
      Iterator I = elts.iterator();

      while(I.hasNext()) {
         p.add(new VariableDescriptor((Element)I.next()));
      }

      this.vars = new VariableMBean[p.size()];
      p.toArray(this.vars);
      elts = DOMUtils.getOptionalElementsByTagName(parent, "attribute");
      p.clear();
      I = elts.iterator();

      while(I.hasNext()) {
         p.add(new AttributeDescriptor((Element)I.next(), this.is12()));
      }

      this.atts = new AttributeMBean[p.size()];
      p.toArray(this.atts);
   }

   public boolean is12() {
      return this._12;
   }

   public void set12(boolean b) {
      this._12 = b;
      AttributeMBean[] atts = this.getAtts();

      for(int i = 0; atts != null && i < atts.length; ++i) {
         if (atts[i] instanceof AttributeDescriptor) {
            AttributeDescriptor ad = (AttributeDescriptor)atts[i];
            ad.set12(this._12);
         }
      }

   }

   public String getName() {
      return this.name;
   }

   public void setName(String s) {
      String old = this.name;
      this.name = s;
      if (!comp(old, s)) {
         this.firePropertyChange("name", old, s);
      }

   }

   public String getClassname() {
      return this.classname;
   }

   public void setClassname(String s) {
      String o = this.classname;
      this.classname = s;
      if (!comp(o, s)) {
         this.firePropertyChange("classname", o, s);
      }

   }

   public String getExtraInfoClassname() {
      return this.extraInfoClassname;
   }

   public void setExtraInfoClassname(String s) {
      String o = this.extraInfoClassname;
      this.extraInfoClassname = s;
      if (!comp(o, s)) {
         this.firePropertyChange("extraInfoClassname", o, s);
      }

   }

   public String getBodyContent() {
      return this.bodyContent;
   }

   public void setBodyContent(String s) {
      String o = this.bodyContent;
      this.bodyContent = s;
      if (!comp(o, s)) {
         this.firePropertyChange("bodyContent", o, s);
      }

   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String s) {
      String o = this.description;
      this.description = s;
      if (!comp(o, s)) {
         this.firePropertyChange("description", o, s);
      }

   }

   public VariableMBean[] getVars() {
      if (this.vars == null) {
         this.vars = new VariableMBean[0];
      }

      return (VariableMBean[])((VariableMBean[])this.vars.clone());
   }

   public void setVars(VariableMBean[] v) {
      VariableMBean[] old = this.vars;
      if (v != null) {
         this.vars = (VariableMBean[])((VariableMBean[])v.clone());
         if (!comp(old, v)) {
            this.firePropertyChange("vars", old, v);
         }

      }
   }

   public AttributeMBean[] getAtts() {
      if (this.atts == null) {
         this.atts = new AttributeMBean[0];
      }

      return (AttributeMBean[])((AttributeMBean[])this.atts.clone());
   }

   public void setAtts(AttributeMBean[] a) {
      AttributeMBean[] old = this.atts;
      if (a != null) {
         this.atts = (AttributeMBean[])((AttributeMBean[])a.clone());
         if (!comp(old, a)) {
            this.firePropertyChange("atts", old, a);
         }

      }
   }

   public String toString() {
      return "[TagDesc: name=" + this.getName() + " classname=" + this.getClassname() + " TEI=" + this.getExtraInfoClassname() + " bc=" + this.getBodyContent() + " desc=" + this.getDescription() + " #vars=" + this.getVars().length + " #atts=" + this.getAtts().length + "]";
   }

   public void validate() {
      throw new Error("NYI");
   }

   public void toXML(XMLWriter x) {
      this.set12(this.is12());
      x.println("<tag>");
      x.incrIndent();
      x.println("<name>" + this.getName() + "</name>");
      x.println("<" + this.getTagClassDTDName() + ">" + this.getClassname() + "</" + this.getTagClassDTDName() + ">");
      if (this.getExtraInfoClassname() != null) {
         x.println("<" + this.getTEIDTDName() + ">" + this.getExtraInfoClassname() + "</" + this.getTEIDTDName() + ">");
      }

      if (this.getBodyContent() != null) {
         x.println("<" + this.getBodyContentDTDName() + ">" + this.getBodyContent() + "</" + this.getBodyContentDTDName() + ">");
      }

      if (this.getDescription() != null) {
         x.println("<" + this.getDescriptionDTDName() + ">" + cdata(this.getDescription()) + "</" + this.getDescriptionDTDName() + ">");
      }

      int i;
      if (this.is12()) {
         VariableMBean[] vs = this.getVars();

         for(i = 0; vs != null && i < vs.length; ++i) {
            VariableDescriptor vd = (VariableDescriptor)vs[i];
            vd.toXML(x);
         }
      }

      AttributeMBean[] as = this.getAtts();

      for(i = 0; as != null && i < as.length; ++i) {
         AttributeDescriptor ad = (AttributeDescriptor)as[i];
         ad.toXML(x);
      }

      x.decrIndent();
      x.println("</tag>");
   }

   public String toXML(int indent) {
      return TLDDescriptor.toXML(this, indent);
   }
}
