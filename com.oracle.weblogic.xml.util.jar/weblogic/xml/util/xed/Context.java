package weblogic.xml.util.xed;

import java.util.ArrayList;
import java.util.List;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.AttributeIterator;
import weblogic.xml.stream.CharacterData;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.events.AttributeImpl;
import weblogic.xml.stream.events.CharacterDataEvent;
import weblogic.xml.stream.events.ElementEvent;
import weblogic.xml.stream.events.Name;
import weblogic.xml.stream.events.StartElementEvent;

public class Context {
   private Name name;
   private Name attName;
   private ElementEvent currentEvent;
   private AttributeImpl att;

   public void set(XMLEvent e) {
      this.currentEvent = (ElementEvent)e;
      this.name = (Name)e.getName();
   }

   public void set(XMLEvent e, Attribute a) {
      this.currentEvent = (ElementEvent)e;
      this.name = (Name)e.getName();
      this.att = (AttributeImpl)a;
      this.attName = (Name)a.getName();
   }

   public String getPrefix() {
      return this.name.getPrefix();
   }

   public String getLocalName() {
      return this.name.getLocalName();
   }

   public String getUri() {
      return this.name.getNamespaceUri();
   }

   public String getText() {
      return ((CharacterData)this.currentEvent).getContent();
   }

   public String getAttLocalName() {
      return this.attName.getLocalName();
   }

   public String getAttPrefix() {
      return this.attName.getPrefix();
   }

   public String getAttValue() {
      return this.att.getValue();
   }

   public void setPrefix(String p) {
      this.name.setPrefix(p);
   }

   public void setUri(String u) {
      this.name.setNamespaceUri(u);
   }

   public void setLocalName(String l) {
      this.name.setLocalName(l);
   }

   public List getAttributes() {
      ArrayList list = new ArrayList();
      AttributeIterator i = ((StartElement)this.currentEvent).getAttributesAndNamespaces();

      while(i.hasNext()) {
         list.add(i.next());
      }

      return list;
   }

   public void setAttributes(List atts) {
      ((StartElementEvent)this.currentEvent).setAttributes(atts);
   }

   public void setText(String t) {
      ((CharacterDataEvent)this.currentEvent).setContent(t);
   }

   public void setAttLocalName(String n) {
      this.attName.setLocalName(n);
   }

   public void setAttPrefix(String p) {
      this.attName.setPrefix(p);
   }

   public void setAttValue(String v) {
      this.att.setValue(v);
   }

   public int getEventType() {
      return this.currentEvent.getType();
   }

   public Object lookup(Variable v) throws StreamEditorException {
      if ("prefix".equals(v.getName())) {
         return this.getPrefix();
      } else if ("localName".equals(v.getName())) {
         return this.getLocalName();
      } else if ("uri".equals(v.getName())) {
         return this.getUri();
      } else if ("text".equals(v.getName())) {
         return this.getText();
      } else if ("@prefix".equals(v.getName())) {
         return this.getAttPrefix();
      } else if ("@localName".equals(v.getName())) {
         return this.getAttLocalName();
      } else if ("@value".equals(v.getName())) {
         return this.getAttValue();
      } else if ("@attributes".equals(v.getName())) {
         return this.getAttributes();
      } else {
         throw new StreamEditorException("Unknown variable:" + v.getName());
      }
   }

   public void assign(Variable v, Object value) throws StreamEditorException {
      if ("prefix".equals(v.getName())) {
         this.setPrefix((String)value);
      } else if ("localName".equals(v.getName())) {
         this.setLocalName((String)value);
      } else if ("uri".equals(v.getName())) {
         this.setUri((String)value);
      } else if ("text".equals(v.getName())) {
         this.setText((String)value);
      } else if ("@prefix".equals(v.getName())) {
         this.setAttPrefix((String)value);
      } else if ("@localName".equals(v.getName())) {
         this.setAttLocalName((String)value);
      } else if ("@value".equals(v.getName())) {
         this.setAttValue((String)value);
      } else {
         if (!"@attributes".equals(v.getName())) {
            throw new StreamEditorException("Unknown variable:" + v.getName());
         }

         this.setAttributes((List)value);
      }

   }
}
