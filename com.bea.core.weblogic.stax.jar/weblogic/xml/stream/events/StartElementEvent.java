package weblogic.xml.stream.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import weblogic.utils.collections.CombinedIterator;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.AttributeIterator;
import weblogic.xml.stream.Location;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLName;
import weblogic.xml.util.LLNamespaceMap;

/** @deprecated */
@Deprecated
public class StartElementEvent extends ElementEvent implements StartElement {
   protected List attributes;
   protected List namespaces;
   protected Map namespaceURIs;
   private static final EmptyIterator emptyIterator = new EmptyIterator();

   public StartElementEvent() {
      this.init();
   }

   public StartElementEvent(XMLName name) {
      this.init();
      this.name = name;
   }

   public StartElementEvent(XMLName name, Location location) {
      this.init();
      this.name = name;
      this.location = location;
   }

   public StartElementEvent(StartElement element) {
      this.init();
      this.name = element.getName();
      this.location = element.getLocation();
      AttributeIterator ai = element.getAttributes();

      while(ai.hasNext()) {
         this.addAttribute(ai.next());
      }

      ai = element.getNamespaces();

      while(ai.hasNext()) {
         this.addNamespace(ai.next());
      }

      this.setTransientNamespaceMap(element.getNamespaceMap());
   }

   protected void init() {
      this.type = 2;
   }

   public AttributeIterator getAttributes() {
      return (AttributeIterator)(this.attributes == null ? emptyIterator : new AttributeIteratorImpl(this.attributes.iterator()));
   }

   public AttributeIterator getNamespaces() {
      return (AttributeIterator)(this.namespaces == null ? emptyIterator : new AttributeIteratorImpl(this.namespaces.iterator()));
   }

   public AttributeIterator getAttributesAndNamespaces() {
      if (this.attributes == null) {
         return (AttributeIterator)(this.namespaces == null ? emptyIterator : new AttributeIteratorImpl(this.namespaces.iterator()));
      } else if (this.namespaces == null) {
         return new AttributeIteratorImpl(this.attributes.iterator());
      } else {
         CombinedIterator ci = new CombinedIterator(this.namespaces.iterator(), this.attributes.iterator());
         return new AttributeIteratorImpl(ci);
      }
   }

   public Attribute getAttributeByName(XMLName name) {
      AttributeIterator i = this.getAttributes();

      while(i.hasNext()) {
         Attribute a = i.next();
         if (name.getNamespaceUri() == null) {
            if (name.getLocalName().equals(a.getName().getLocalName())) {
               return a;
            }
         } else if (name.getNamespaceUri().equals(a.getName().getNamespaceUri()) && name.getLocalName().equals(a.getName().getLocalName())) {
            return a;
         }
      }

      return null;
   }

   public void setAttributes(List attributes) {
      this.attributes = attributes;
   }

   public void addAttribute(Attribute attribute) {
      if (this.attributes == null) {
         this.attributes = new ArrayList();
      }

      this.attributes.add(attribute);
   }

   public void addNamespace(Attribute attribute) {
      if (this.namespaces == null) {
         this.namespaces = new ArrayList();
      }

      this.namespaces.add(attribute);
   }

   public String getNamespaceUri(String prefix) {
      return this.namespaceURIs == null ? null : (String)this.namespaceURIs.get(prefix);
   }

   public void setTransientNamespaceMap(Map uriMap) {
      if (this.namespaces != null && this.namespaces.iterator().hasNext()) {
         this.setNamespaceMap(uriMap);
      } else {
         this.namespaceURIs = uriMap;
      }

   }

   public void setNamespaceMap(Map uriMap) {
      if (uriMap != null) {
         if (!(uriMap instanceof LLNamespaceMap)) {
            this.namespaceURIs = new HashMap();
            this.namespaceURIs.putAll(uriMap);
         } else {
            this.namespaceURIs = uriMap;
         }

      }
   }

   public Map getNamespaceMap() {
      return this.namespaceURIs;
   }

   public String toString() {
      String value = "<" + this.name;

      for(AttributeIterator ai = this.getAttributesAndNamespaces(); ai.hasNext(); value = value + " " + ai.next().toString()) {
      }

      value = value + ">";
      return value;
   }

   public boolean equals(Object obj) {
      if (!super.equals(obj)) {
         return false;
      } else if (!(obj instanceof StartElement)) {
         return false;
      } else {
         StartElement startElement = (StartElement)obj;
         return this.getAttributesAndNamespaces().equals(startElement.getAttributesAndNamespaces());
      }
   }
}
