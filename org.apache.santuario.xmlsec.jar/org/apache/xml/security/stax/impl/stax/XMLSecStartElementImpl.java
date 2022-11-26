package org.apache.xml.security.stax.impl.stax;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Namespace;
import org.apache.xml.security.stax.ext.stax.XMLSecAttribute;
import org.apache.xml.security.stax.ext.stax.XMLSecNamespace;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;

public class XMLSecStartElementImpl extends XMLSecEventBaseImpl implements XMLSecStartElement {
   private final QName elementName;
   private XMLSecNamespace elementNamespace;
   private List attributes = Collections.emptyList();
   private List namespaces = Collections.emptyList();

   public XMLSecStartElementImpl(QName elementName, List attributes, List namespaces, XMLSecStartElement parentXmlSecStartElement) {
      this.elementName = elementName;
      this.setParentXMLSecStartElement(parentXmlSecStartElement);
      if (attributes != null) {
         this.attributes = attributes;
      }

      if (namespaces != null) {
         this.namespaces = namespaces;
      }

   }

   public XMLSecStartElementImpl(QName elementName, Collection attributes, Collection namespaces) {
      this.elementName = elementName;
      if (attributes != null && !attributes.isEmpty()) {
         this.attributes = new ArrayList(attributes);
      }

      if (namespaces != null && !namespaces.isEmpty()) {
         this.namespaces = new ArrayList(namespaces);
      }

   }

   public QName getName() {
      return this.elementName;
   }

   public XMLSecNamespace getElementNamespace() {
      if (this.elementNamespace == null) {
         this.elementNamespace = XMLSecNamespaceImpl.getInstance(this.elementName.getPrefix(), this.elementName.getNamespaceURI());
      }

      return this.elementNamespace;
   }

   public Iterator getAttributes() {
      return (Iterator)(this.attributes.isEmpty() ? getEmptyIterator() : this.attributes.iterator());
   }

   public void getAttributesFromCurrentScope(List comparableAttributeList) {
      comparableAttributeList.addAll(this.attributes);
      if (this.parentXMLSecStartELement != null) {
         this.parentXMLSecStartELement.getAttributesFromCurrentScope(comparableAttributeList);
      }

   }

   public List getOnElementDeclaredAttributes() {
      return this.attributes;
   }

   public void addAttribute(XMLSecAttribute xmlSecAttribute) {
      if (this.attributes == Collections.emptyList()) {
         this.attributes = new ArrayList(1);
      }

      this.attributes.add(xmlSecAttribute);
   }

   public int getDocumentLevel() {
      return super.getDocumentLevel() + 1;
   }

   public void getElementPath(List list) {
      super.getElementPath(list);
      list.add(this.getName());
   }

   public XMLSecStartElement getStartElementAtLevel(int level) {
      int thisLevel = this.getDocumentLevel();
      if (thisLevel < level) {
         return null;
      } else {
         return (XMLSecStartElement)(thisLevel == level ? this : this.parentXMLSecStartELement.getStartElementAtLevel(level));
      }
   }

   public Iterator getNamespaces() {
      return (Iterator)(this.namespaces.isEmpty() ? getEmptyIterator() : this.namespaces.iterator());
   }

   public void getNamespacesFromCurrentScope(List comparableNamespaceList) {
      if (this.parentXMLSecStartELement != null) {
         this.parentXMLSecStartELement.getNamespacesFromCurrentScope(comparableNamespaceList);
      }

      comparableNamespaceList.addAll(this.namespaces);
   }

   public List getOnElementDeclaredNamespaces() {
      return this.namespaces;
   }

   public void addNamespace(XMLSecNamespace xmlSecNamespace) {
      if (this.namespaces == Collections.emptyList()) {
         this.namespaces = new ArrayList(1);
      }

      this.namespaces.add(xmlSecNamespace);
   }

   public Attribute getAttributeByName(QName name) {
      for(int i = 0; i < this.attributes.size(); ++i) {
         Attribute comparableAttribute = (Attribute)this.attributes.get(i);
         if (name.equals(comparableAttribute.getName())) {
            return comparableAttribute;
         }
      }

      return null;
   }

   public NamespaceContext getNamespaceContext() {
      return new NamespaceContext() {
         public String getNamespaceURI(String prefix) {
            for(int i = 0; i < XMLSecStartElementImpl.this.namespaces.size(); ++i) {
               Namespace comparableNamespace = (Namespace)XMLSecStartElementImpl.this.namespaces.get(i);
               if (prefix.equals(comparableNamespace.getPrefix())) {
                  return comparableNamespace.getNamespaceURI();
               }
            }

            if (XMLSecStartElementImpl.this.parentXMLSecStartELement != null) {
               return XMLSecStartElementImpl.this.parentXMLSecStartELement.getNamespaceURI(prefix);
            } else {
               return null;
            }
         }

         public String getPrefix(String namespaceURI) {
            for(int i = 0; i < XMLSecStartElementImpl.this.namespaces.size(); ++i) {
               Namespace comparableNamespace = (Namespace)XMLSecStartElementImpl.this.namespaces.get(i);
               if (namespaceURI.equals(comparableNamespace.getNamespaceURI())) {
                  return comparableNamespace.getPrefix();
               }
            }

            if (XMLSecStartElementImpl.this.parentXMLSecStartELement != null) {
               return XMLSecStartElementImpl.this.parentXMLSecStartELement.getNamespaceContext().getPrefix(namespaceURI);
            } else {
               return null;
            }
         }

         public Iterator getPrefixes(String namespaceURI) {
            Set prefixes = new HashSet();
            List xmlSecNamespaces = new ArrayList();
            XMLSecStartElementImpl.this.getNamespacesFromCurrentScope(xmlSecNamespaces);

            for(int i = 0; i < xmlSecNamespaces.size(); ++i) {
               Namespace xmlSecNamespace = (Namespace)xmlSecNamespaces.get(i);
               if (namespaceURI.equals(xmlSecNamespace.getNamespaceURI())) {
                  prefixes.add(xmlSecNamespace.getPrefix());
               }
            }

            return prefixes.iterator();
         }
      };
   }

   public String getNamespaceURI(String prefix) {
      for(int i = 0; i < this.namespaces.size(); ++i) {
         Namespace comparableNamespace = (Namespace)this.namespaces.get(i);
         if (prefix.equals(comparableNamespace.getPrefix())) {
            return comparableNamespace.getNamespaceURI();
         }
      }

      if (this.parentXMLSecStartELement != null) {
         return this.parentXMLSecStartELement.getNamespaceURI(prefix);
      } else {
         return null;
      }
   }

   public int getEventType() {
      return 1;
   }

   public boolean isStartElement() {
      return true;
   }

   public XMLSecStartElement asStartElement() {
      return this;
   }

   public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
      try {
         writer.write(60);
         String prefix = this.getName().getPrefix();
         if (prefix != null && !prefix.isEmpty()) {
            writer.write(prefix);
            writer.write(58);
         }

         writer.write(this.getName().getLocalPart());
         Iterator var3 = this.namespaces.iterator();

         String attrPrefix;
         while(var3.hasNext()) {
            Namespace xmlSecNamespace = (Namespace)var3.next();
            writer.write(" xmlns");
            attrPrefix = xmlSecNamespace.getPrefix();
            if (attrPrefix != null && !attrPrefix.isEmpty()) {
               writer.write(58);
               writer.write(attrPrefix);
            }

            writer.write("=\"");
            writer.write(xmlSecNamespace.getValue());
            writer.write(34);
         }

         var3 = this.attributes.iterator();

         while(var3.hasNext()) {
            Attribute xmlSecAttribute = (Attribute)var3.next();
            writer.write(32);
            attrPrefix = xmlSecAttribute.getName().getPrefix();
            if (attrPrefix != null && !attrPrefix.isEmpty()) {
               writer.write(attrPrefix);
               writer.write(58);
            }

            writer.write(xmlSecAttribute.getName().getLocalPart());
            writer.write("=\"");
            writer.write(xmlSecAttribute.getValue());
            writer.write(34);
         }

         writer.write(62);
      } catch (IOException var6) {
         throw new XMLStreamException(var6);
      }
   }
}
