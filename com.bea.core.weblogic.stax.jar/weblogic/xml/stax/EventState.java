package weblogic.xml.stax;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import weblogic.xml.stax.util.TypeNames;

public class EventState {
   private int type;
   private QName qname;
   private List attributes;
   private List namespaces;
   private String data;
   private String extraData;

   public EventState() {
   }

   public EventState(int type) {
      this.type = type;
      this.attributes = new ArrayList();
      this.namespaces = new ArrayList();
   }

   public void clear() {
      this.qname = null;
      this.attributes = new ArrayList();
      this.namespaces = new ArrayList();
      this.data = null;
      this.extraData = null;
   }

   public void setType(int type) {
      this.type = type;
   }

   public int getType() {
      return this.type;
   }

   public QName getName() {
      return this.qname;
   }

   public String getLocalName() {
      return this.qname.getLocalPart();
   }

   public String getPrefix() {
      return this.qname.getPrefix();
   }

   public String getNamespaceURI() {
      return this.qname.getNamespaceURI();
   }

   public void setName(QName n) {
      this.qname = n;
   }

   public void setAttributes(List atts) {
      this.attributes = atts;
   }

   public void addAttribute(Object obj) {
      this.attributes.add(obj);
   }

   public void addNamespace(Object obj) {
      this.namespaces.add(obj);
   }

   public List getAttributes() {
      return this.attributes;
   }

   public void setNamespaces(List ns) {
      this.namespaces = ns;
   }

   public List getNamespaces() {
      return this.namespaces;
   }

   public String getData() {
      return this.data;
   }

   public void setData(String data) {
      this.data = data;
   }

   public String getExtraData() {
      return this.extraData;
   }

   public void setExtraData(String d) {
      this.extraData = d;
   }

   public String toString() {
      StringBuffer b = new StringBuffer();
      b.append("[" + TypeNames.getName(this.type) + "]");
      if (this.qname != null) {
         b.append("[name='" + this.qname + "']");
      }

      Iterator i = this.namespaces.iterator();

      while(i.hasNext()) {
         b.append(i.next() + " ");
      }

      i = this.attributes.iterator();

      while(i.hasNext()) {
         b.append(i.next() + " ");
      }

      if (this.data != null) {
         b.append(",data=[" + this.data + "]");
      }

      if (this.extraData != null) {
         b.append(",extradata=[" + this.extraData + "]");
      }

      return b.toString();
   }
}
