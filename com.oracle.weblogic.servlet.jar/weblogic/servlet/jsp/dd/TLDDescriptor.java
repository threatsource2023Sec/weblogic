package weblogic.servlet.jsp.dd;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import weblogic.management.descriptors.webapp.ListenerMBean;
import weblogic.management.descriptors.webapp.TLDMBean;
import weblogic.management.descriptors.webapp.TagMBean;
import weblogic.management.descriptors.webapp.UIMBean;
import weblogic.management.descriptors.webapp.ValidatorMBean;
import weblogic.servlet.internal.dd.BaseServletDescriptor;
import weblogic.servlet.internal.dd.ListenerDescriptor;
import weblogic.servlet.internal.dd.ToXML;
import weblogic.utils.io.XMLWriter;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class TLDDescriptor extends BaseServletDescriptor implements ToXML, TLDMBean, UIMBean {
   static final long serialVersionUID = 8049213100848306898L;
   private String taglibVersion;
   private String jspVersion;
   private String shortName;
   private String uri;
   private String displayName;
   private String smallIcon;
   private String largeIcon;
   private String description;
   private ValidatorDescriptor validator;
   private ListenerMBean[] listeners;
   private TagMBean[] tags;
   private boolean _12;
   private static final String ID_11 = "<!DOCTYPE taglib PUBLIC \"-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.1//EN\"\n\"http://java.sun.com/j2ee/dtds/web-jsptaglibrary_1_1.dtd\">\n";
   private static final String ID_12 = "<!DOCTYPE taglib PUBLIC \"-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN\"\n\"http://java.sun.com/dtd/web-jsptaglibrary_1_2.dtd\">\n";
   static final String CDATA_BEGIN = "<![CDATA[";
   static final String CDATA_END = "]]>";

   static void p(String s) {
      System.err.println("[TLDDescriptor]: " + s);
   }

   private String getTlibVersionName() {
      return this.is12() ? "tlib-version" : "tlibversion";
   }

   private String getJspVersionName() {
      return this.is12() ? "jsp-version" : "jspversion";
   }

   private String getShortNameName() {
      return this.is12() ? "short-name" : "shortname";
   }

   public TLDDescriptor() {
      this._12 = true;
   }

   public TLDDescriptor(Document parent) throws DOMProcessingException {
      String publicId;
      if (parent.getDoctype() != null) {
         publicId = parent.getDoctype().getPublicId();
         if ("-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN".equals(publicId)) {
            this._12 = true;
         } else {
            if (!"-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.1//EN".equals(publicId)) {
               throw new DOMProcessingException("Invalid DTD for taglib: cannot resolve '" + publicId + "'");
            }

            this._12 = false;
         }
      } else {
         this._12 = true;
      }

      publicId = null;
      Element parentElement = parent.getDocumentElement();
      Element e = DOMUtils.getOptionalElementByTagName(parentElement, this.getTlibVersionName());
      if (e != null) {
         this.taglibVersion = DOMUtils.getTextData(e);
      } else {
         this.taglibVersion = "1.0";
      }

      e = DOMUtils.getOptionalElementByTagName(parentElement, this.getJspVersionName());
      if (e != null) {
         this.jspVersion = DOMUtils.getTextData(e);
      }

      e = DOMUtils.getOptionalElementByTagName(parentElement, this.getShortNameName());
      if (e != null) {
         this.shortName = DOMUtils.getTextData(e);
      } else {
         this.shortName = "";
      }

      e = DOMUtils.getOptionalElementByTagName(parentElement, "uri");
      if (e != null) {
         this.uri = DOMUtils.getTextData(e);
      }

      e = DOMUtils.getOptionalElementByTagName(parentElement, "display-name");
      if (e != null) {
         this.displayName = DOMUtils.getTextData(e);
      }

      e = DOMUtils.getOptionalElementByTagName(parentElement, "small-icon");
      if (e != null) {
         this.smallIcon = DOMUtils.getTextData(e);
      }

      e = DOMUtils.getOptionalElementByTagName(parentElement, "large-icon");
      if (e != null) {
         this.largeIcon = DOMUtils.getTextData(e);
      }

      e = DOMUtils.getOptionalElementByTagName(parentElement, "description");
      if (e != null) {
         this.description = DOMUtils.getTextData(e);
      }

      List elts = null;
      Iterator I = null;
      List t = new ArrayList();
      if (this.is12()) {
         e = DOMUtils.getOptionalElementByTagName(parentElement, "validator");
         if (e != null) {
            this.validator = new ValidatorDescriptor(e);
         }

         elts = DOMUtils.getOptionalElementsByTagName(parentElement, "listener");
         I = elts.iterator();
         t.clear();

         while(I.hasNext()) {
            ListenerDescriptor ld = new ListenerDescriptor((Element)I.next());
            t.add(ld);
         }

         this.listeners = new ListenerMBean[t.size()];
         t.toArray(this.listeners);
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "tag");
      I = elts.iterator();
      t.clear();

      while(I.hasNext()) {
         t.add(new TagDescriptor((Element)I.next(), this.is12()));
      }

      this.tags = new TagMBean[t.size()];
      t.toArray(this.tags);
      this.set12(true);
   }

   public boolean is12() {
      return this._12;
   }

   public void set12(boolean b) {
      if (this._12 != b) {
         this._12 = b;
         TagMBean[] tags = this.getTags();

         for(int i = 0; tags != null && i < tags.length; ++i) {
            if (tags[i] instanceof TagDescriptor) {
               TagDescriptor td = (TagDescriptor)tags[i];
               td.set12(this._12);
            }
         }

      }
   }

   public String getTaglibVersion() {
      return this.taglibVersion;
   }

   public void setTaglibVersion(String s) {
      String old = this.taglibVersion;
      this.taglibVersion = s;
      if (!comp(old, s)) {
         this.firePropertyChange("taglibVersion", old, s);
      }

   }

   public String getJspVersion() {
      return this.jspVersion;
   }

   public void setJspVersion(String s) {
      String old = this.jspVersion;
      this.jspVersion = s;
      if (!comp(old, s)) {
         this.firePropertyChange("jspVersion", old, s);
      }

   }

   public String getShortName() {
      return this.shortName;
   }

   public void setShortName(String s) {
      String old = this.shortName;
      this.shortName = s;
      if (!comp(old, s)) {
         this.firePropertyChange("shortName", old, s);
      }

   }

   public String getURI() {
      return this.uri;
   }

   public void setURI(String s) {
      String old = this.uri;
      this.uri = s;
      if (!comp(old, s)) {
         this.firePropertyChange("uri", old, s);
      }

   }

   public void setLargeIconFileName(String s) {
      this.setLargeIcon(s);
   }

   public String getLargeIconFileName() {
      return this.getLargeIcon();
   }

   public void setSmallIconFileName(String s) {
      this.setSmallIcon(s);
   }

   public String getSmallIconFileName() {
      return this.getSmallIcon();
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public void setDisplayName(String s) {
      String old = this.displayName;
      this.displayName = s;
      if (!comp(old, s)) {
         this.firePropertyChange("displayName", old, s);
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

   public String getSmallIcon() {
      return this.smallIcon;
   }

   public void setSmallIcon(String s) {
      String old = this.smallIcon;
      this.smallIcon = s;
      if (!comp(old, s)) {
         this.firePropertyChange("smallIcon", old, s);
      }

   }

   public String getLargeIcon() {
      return this.largeIcon;
   }

   public void setLargeIcon(String s) {
      String old = this.largeIcon;
      this.largeIcon = s;
      if (!comp(old, s)) {
         this.firePropertyChange("largeIcon", old, s);
      }

   }

   public ValidatorMBean getValidator() {
      return this.validator;
   }

   public void setValidator(ValidatorMBean v) {
      this.validator = (ValidatorDescriptor)v;
   }

   public TagMBean[] getTags() {
      if (this.tags == null) {
         this.tags = new TagMBean[0];
      }

      return (TagMBean[])((TagMBean[])this.tags.clone());
   }

   public void setTags(TagMBean[] t) {
      TagMBean[] old = this.tags;
      if (t != null) {
         this.tags = (TagMBean[])((TagMBean[])t.clone());
         if (!comp(old, t)) {
            this.firePropertyChange("tags", old, t);
         }

      }
   }

   public ListenerMBean[] getListeners() {
      if (this.listeners == null) {
         this.listeners = new ListenerMBean[0];
      }

      return (ListenerMBean[])((ListenerMBean[])this.listeners.clone());
   }

   public void setListeners(ListenerMBean[] s) {
      ListenerMBean[] old = this.listeners;
      if (s != null) {
         this.listeners = (ListenerMBean[])((ListenerMBean[])s.clone());
      } else {
         this.listeners = new ListenerMBean[0];
      }

      if (!comp(old, s)) {
         this.firePropertyChange("listeners", old, s);
      }

   }

   public void validate() {
      throw new Error("NYI");
   }

   private String getPreamble() {
      return this.is12() ? "<!DOCTYPE taglib PUBLIC \"-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN\"\n\"http://java.sun.com/dtd/web-jsptaglibrary_1_2.dtd\">\n" : "<!DOCTYPE taglib PUBLIC \"-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.1//EN\"\n\"http://java.sun.com/j2ee/dtds/web-jsptaglibrary_1_1.dtd\">\n";
   }

   public void toXML(XMLWriter x) {
      x.println(this.getPreamble());
      x.println("<taglib>");
      x.incrIndent();
      x.println("<" + this.getTlibVersionName() + ">" + this.getTaglibVersion() + "</" + this.getTlibVersionName() + ">");
      if (this.jspVersion != null) {
         x.println("<" + this.getJspVersionName() + ">" + this.getJspVersion() + "</" + this.getJspVersionName() + ">");
      }

      x.println("<" + this.getShortNameName() + ">" + this.getShortName() + "</" + this.getShortNameName() + ">");
      if (this.getURI() != null) {
         x.println("<uri>" + this.getURI() + "</uri>");
      }

      int i;
      if (this.is12()) {
         if (this.getDisplayName() != null) {
            x.println("<display-name>" + this.getDisplayName() + "</display-name>");
         }

         if (this.getSmallIcon() != null) {
            x.println("<small-icon>" + this.getSmallIcon() + "</small-icon>");
         }

         if (this.getLargeIcon() != null) {
            x.println("<large-icon>" + this.getLargeIcon() + "</large-icon>");
         }

         if (this.getDescription() != null) {
            x.println("<description>" + cdata(this.getDescription()) + "</description>");
         }

         if (this.getValidator() != null) {
            ValidatorDescriptor vd = (ValidatorDescriptor)this.getValidator();
            vd.toXML(x);
         }

         ListenerMBean[] lds = this.getListeners();

         for(i = 0; lds != null && i < lds.length; ++i) {
            ListenerDescriptor ld = (ListenerDescriptor)lds[i];
            ld.toXML(x);
         }
      } else if (this.getDescription() != null) {
         x.println("<info>" + cdata(this.getDescription()) + "</info>");
      }

      TagMBean[] tgs = this.getTags();

      for(i = 0; tgs != null && i < tgs.length; ++i) {
         TagDescriptor td = (TagDescriptor)tgs[i];
         td.toXML(x);
      }

      x.decrIndent();
      x.println("</taglib>");
   }

   public static String toXML(ToXML tx, int indent) {
      StringWriter stringwriter = new StringWriter();
      XMLWriter x = new XMLWriter(stringwriter);
      tx.toXML(x);
      x.flush();
      return stringwriter.toString();
   }

   public String toXML(int indent) {
      return toXML(this, indent);
   }
}
