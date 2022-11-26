package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.TLDMBean;
import weblogic.management.descriptors.webapp.TagLibMBean;
import weblogic.servlet.jsp.dd.TLDDescriptor;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class TaglibDescriptor extends BaseServletDescriptor implements ToXML, TagLibMBean {
   private static final long serialVersionUID = -2310746458588034306L;
   private static final String TAGLIB = "taglib";
   private static final String TAGLIB_URI = "taglib-uri";
   private static final String TAGLIB_LOCATION = "taglib-location";
   private String uri;
   private String location;
   private TLDDescriptor tld;

   public TaglibDescriptor(TagLibMBean mbean) {
      this(mbean.getURI(), mbean.getLocation());
   }

   public TaglibDescriptor() {
      this("", "");
   }

   public TaglibDescriptor(String u, String l) {
      this.uri = u;
      this.location = l;
   }

   public TaglibDescriptor(Element parent) throws DOMProcessingException {
      this.uri = DOMUtils.getValueByTagName(parent, "taglib-uri");
      this.location = DOMUtils.getValueByTagName(parent, "taglib-location");
   }

   public String getURI() {
      return this.uri;
   }

   public void setURI(String u) {
      String old = this.uri;
      this.uri = u;
      if (!comp(old, u)) {
         this.firePropertyChange("uri", old, u);
      }

   }

   public String getLocation() {
      return this.location;
   }

   public void setLocation(String l) {
      String old = this.location;
      this.location = l;
      if (!comp(old, l)) {
         this.firePropertyChange("location", old, l);
      }

   }

   public TLDMBean getTLD() {
      return this.tld;
   }

   public void setTLD(TLDMBean tld) {
      this.tld = (TLDDescriptor)tld;
   }

   public void validate() throws DescriptorValidationException {
      boolean ok = true;
      this.removeDescriptorErrors();
      String u = this.getURI();
      if (u != null) {
         u = u.trim();
         this.setURI(u);
      }

      String l = this.getLocation();
      if (l != null) {
         l = l.trim();
         this.setLocation(l);
      }

      if (u == null || u.length() == 0) {
         this.addDescriptorError("NO_TAGLIB_URI");
         ok = false;
      }

      if (l == null || l.length() == 0) {
         this.addDescriptorError("NO_TAGLIB_LOCATION", u);
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
      result = result + this.indentStr(indent) + "<taglib>\n";
      indent += 2;
      result = result + this.indentStr(indent) + "<taglib-uri>" + this.uri + "</taglib-uri>\n";
      result = result + this.indentStr(indent) + "<taglib-location>" + this.location + "</taglib-location>\n";
      indent -= 2;
      result = result + this.indentStr(indent) + "</taglib>\n";
      return result;
   }
}
