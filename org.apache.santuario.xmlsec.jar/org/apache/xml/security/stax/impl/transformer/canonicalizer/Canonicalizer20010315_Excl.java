package org.apache.xml.security.stax.impl.transformer.canonicalizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.stax.XMLSecAttribute;
import org.apache.xml.security.stax.ext.stax.XMLSecEventFactory;
import org.apache.xml.security.stax.ext.stax.XMLSecNamespace;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;

public abstract class Canonicalizer20010315_Excl extends CanonicalizerBase {
   public static final String INCLUSIVE_NAMESPACES_PREFIX_LIST = "inclusiveNamespacePrefixList";
   public static final String PROPAGATE_DEFAULT_NAMESPACE = "propagateDefaultNamespace";
   protected List inclusiveNamespaces;
   protected boolean propagateDefaultNamespace = false;

   public Canonicalizer20010315_Excl(boolean includeComments) {
      super(includeComments);
   }

   public void setProperties(Map properties) throws XMLSecurityException {
      this.inclusiveNamespaces = getPrefixList((List)properties.get("inclusiveNamespacePrefixList"));
      Boolean propagateDfltNs = (Boolean)properties.get("propagateDefaultNamespace");
      if (propagateDfltNs != null) {
         this.propagateDefaultNamespace = propagateDfltNs;
      }

   }

   protected static List getPrefixList(List inclusiveNamespaces) {
      if (inclusiveNamespaces != null && !inclusiveNamespaces.isEmpty()) {
         List prefixes = new ArrayList(inclusiveNamespaces.size());

         for(int i = 0; i < inclusiveNamespaces.size(); ++i) {
            String s = ((String)inclusiveNamespaces.get(i)).intern();
            if ("#default".equals(s)) {
               prefixes.add("");
            } else {
               prefixes.add(s);
            }
         }

         return prefixes;
      } else {
         return null;
      }
   }

   protected List getCurrentUtilizedNamespaces(XMLSecStartElement xmlSecStartElement, CanonicalizerBase.C14NStack outputStack) {
      List utilizedNamespaces = Collections.emptyList();
      XMLSecNamespace elementNamespace = xmlSecStartElement.getElementNamespace();
      XMLSecNamespace found = (XMLSecNamespace)outputStack.containsOnStack(elementNamespace);
      if (found == null || found.getNamespaceURI() == null || !found.getNamespaceURI().equals(elementNamespace.getNamespaceURI())) {
         utilizedNamespaces = new ArrayList(2);
         ((List)utilizedNamespaces).add(elementNamespace);
         outputStack.peek().add(elementNamespace);
      }

      List comparableAttributes = xmlSecStartElement.getOnElementDeclaredAttributes();

      int i;
      XMLSecNamespace comparableNamespace;
      for(i = 0; i < comparableAttributes.size(); ++i) {
         XMLSecAttribute comparableAttribute = (XMLSecAttribute)comparableAttributes.get(i);
         XMLSecNamespace attributeNamespace = comparableAttribute.getAttributeNamespace();
         if (!"xml".equals(attributeNamespace.getPrefix()) && attributeNamespace.getNamespaceURI() != null && !attributeNamespace.getNamespaceURI().isEmpty()) {
            comparableNamespace = (XMLSecNamespace)outputStack.containsOnStack(attributeNamespace);
            if (comparableNamespace == null || comparableNamespace.getNamespaceURI() == null || !comparableNamespace.getNamespaceURI().equals(attributeNamespace.getNamespaceURI())) {
               if (utilizedNamespaces == Collections.emptyList()) {
                  utilizedNamespaces = new ArrayList(2);
               }

               ((List)utilizedNamespaces).add(attributeNamespace);
               outputStack.peek().add(attributeNamespace);
            }
         }
      }

      if (this.inclusiveNamespaces != null) {
         for(i = 0; i < this.inclusiveNamespaces.size(); ++i) {
            String prefix = (String)this.inclusiveNamespaces.get(i);
            String ns = xmlSecStartElement.getNamespaceURI(prefix);
            if (ns == null && prefix.isEmpty()) {
               ns = "";
            } else if (ns == null) {
               continue;
            }

            comparableNamespace = XMLSecEventFactory.createXMLSecNamespace(prefix, ns);
            XMLSecNamespace resultNamespace = (XMLSecNamespace)outputStack.containsOnStack(comparableNamespace);
            if (resultNamespace == null || resultNamespace.getNamespaceURI() == null || !resultNamespace.getNamespaceURI().equals(comparableNamespace.getNamespaceURI()) || this.firstCall && this.propagateDefaultNamespace && !((List)utilizedNamespaces).contains(comparableNamespace)) {
               if (utilizedNamespaces == Collections.emptyList()) {
                  utilizedNamespaces = new ArrayList(2);
               }

               ((List)utilizedNamespaces).add(comparableNamespace);
               outputStack.peek().add(comparableNamespace);
            }
         }
      }

      return (List)utilizedNamespaces;
   }

   protected List getInitialUtilizedNamespaces(XMLSecStartElement xmlSecStartElement, CanonicalizerBase.C14NStack outputStack) {
      return this.getCurrentUtilizedNamespaces(xmlSecStartElement, outputStack);
   }

   protected List getInitialUtilizedAttributes(XMLSecStartElement xmlSecStartElement, CanonicalizerBase.C14NStack outputStack) {
      List utilizedAttributes = Collections.emptyList();
      List comparableAttributes = xmlSecStartElement.getOnElementDeclaredAttributes();

      for(int i = 0; i < comparableAttributes.size(); ++i) {
         XMLSecAttribute comparableAttribute = (XMLSecAttribute)comparableAttributes.get(i);
         if (utilizedAttributes == Collections.emptyList()) {
            utilizedAttributes = new ArrayList(2);
         }

         ((List)utilizedAttributes).add(comparableAttribute);
      }

      return (List)utilizedAttributes;
   }
}
