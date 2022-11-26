package org.apache.xml.security.stax.impl.transformer.canonicalizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.xml.security.stax.ext.stax.XMLSecAttribute;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;

public class Canonicalizer11 extends CanonicalizerBase {
   public Canonicalizer11(boolean includeComments) {
      super(includeComments);
   }

   protected List getInitialUtilizedAttributes(XMLSecStartElement xmlSecStartElement, CanonicalizerBase.C14NStack outputStack) {
      List utilizedAttributes = Collections.emptyList();
      List visibleAttributes = new ArrayList();
      xmlSecStartElement.getAttributesFromCurrentScope(visibleAttributes);

      for(int i = 0; i < visibleAttributes.size(); ++i) {
         XMLSecAttribute comparableAttribute = (XMLSecAttribute)visibleAttributes.get(i);
         QName comparableAttributeName = comparableAttribute.getName();
         if ("xml".equals(comparableAttributeName.getPrefix()) && !"id".equals(comparableAttributeName.getLocalPart()) && !"base".equals(comparableAttributeName.getLocalPart()) && outputStack.containsOnStack(comparableAttribute) == null) {
            if (utilizedAttributes == Collections.emptyList()) {
               utilizedAttributes = new ArrayList(2);
            }

            ((List)utilizedAttributes).add(comparableAttribute);
            outputStack.peek().add(comparableAttribute);
         }
      }

      List elementAttributes = xmlSecStartElement.getOnElementDeclaredAttributes();

      for(int i = 0; i < elementAttributes.size(); ++i) {
         XMLSecAttribute comparableAttribute = (XMLSecAttribute)elementAttributes.get(i);
         QName attributeName = comparableAttribute.getName();
         if (!"xml".equals(attributeName.getPrefix())) {
            if (utilizedAttributes == Collections.emptyList()) {
               utilizedAttributes = new ArrayList(2);
            }

            ((List)utilizedAttributes).add(comparableAttribute);
         }
      }

      return (List)utilizedAttributes;
   }
}
