package net.shibboleth.utilities.java.support.xml;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public final class AttributeSupport {
   private AttributeSupport() {
   }

   public static void addXMLBase(@Nonnull Element element, @Nonnull String base) {
      Constraint.isNotNull(element, "Element may not be null");
      Constraint.isNotNull(base, "base attribute value may not be null");
      Attr attr = constructAttribute(element.getOwnerDocument(), XMLConstants.XML_BASE_ATTRIB_NAME);
      attr.setValue(base);
      element.setAttributeNodeNS(attr);
   }

   public static void addXMLId(@Nonnull Element element, @Nonnull String id) {
      Constraint.isNotNull(element, "Element may not be null");
      Constraint.isNotNull(id, "id attribute value may not be null");
      Attr attr = constructAttribute(element.getOwnerDocument(), XMLConstants.XML_ID_ATTRIB_NAME);
      attr.setValue(id);
      element.setAttributeNodeNS(attr);
      element.setIdAttributeNode(attr, true);
   }

   public static void addXMLLang(@Nonnull Element element, @Nonnull String lang) {
      Constraint.isNotNull(element, "Element may not be null");
      Constraint.isNotNull(lang, "lang attribute value may not be null");
      Attr attr = constructAttribute(element.getOwnerDocument(), XMLConstants.XML_LANG_ATTRIB_NAME);
      attr.setValue(lang);
      element.setAttributeNodeNS(attr);
   }

   public static void addXMLSpace(@Nonnull Element element, @Nonnull XMLSpace space) {
      Constraint.isNotNull(element, "Element may not be null");
      Constraint.isNotNull(space, "space attribute value may not be null");
      Attr attr = constructAttribute(element.getOwnerDocument(), XMLConstants.XML_SPACE_ATTRIB_NAME);
      attr.setValue(space.toString());
      element.setAttributeNodeNS(attr);
   }

   public static void appendAttribute(@Nonnull Element element, @Nonnull QName attributeName, List attributeValues, boolean isIDAttribute) {
      appendAttribute(element, attributeName, StringSupport.listToStringValue(attributeValues, " "), isIDAttribute);
   }

   public static void appendAttribute(@Nonnull Element element, @Nonnull QName attributeName, @Nonnull String attributeValue) {
      appendAttribute(element, attributeName, attributeValue, false);
   }

   public static void appendAttribute(@Nonnull Element element, @Nonnull QName attributeName, @Nonnull String attributeValue, boolean isIDAttribute) {
      Constraint.isNotNull(element, "Element may not be null");
      Constraint.isNotNull(attributeName, "Attribute name may not be null");
      Constraint.isNotNull(attributeValue, "Attribute value may not be null");
      Document document = element.getOwnerDocument();
      Attr attribute = constructAttribute(document, attributeName);
      attribute.setValue(attributeValue);
      element.setAttributeNodeNS(attribute);
      if (isIDAttribute) {
         element.setIdAttributeNode(attribute, true);
      }

   }

   public static void appendDateTimeAttribute(@Nonnull Element element, @Nonnull QName attributeName, long duration) {
      appendAttribute(element, attributeName, DOMTypeSupport.longToDateTime(duration));
   }

   public static void appendDurationAttribute(@Nonnull Element element, @Nonnull QName attributeName, long duration) {
      appendAttribute(element, attributeName, DOMTypeSupport.longToDuration(duration));
   }

   @Nonnull
   public static Attr constructAttribute(@Nonnull Document owningDocument, @Nonnull QName attributeName) {
      Constraint.isNotNull(attributeName, "Attribute name can not be null");
      return constructAttribute(owningDocument, attributeName.getNamespaceURI(), attributeName.getLocalPart(), attributeName.getPrefix());
   }

   @Nonnull
   public static Attr constructAttribute(@Nonnull Document document, @Nullable String namespaceURI, @Nonnull String localName, @Nullable String prefix) {
      Constraint.isNotNull(document, "Document may not null");
      String trimmedLocalName = (String)Constraint.isNotNull(StringSupport.trimOrNull(localName), "Attribute local name may not be null or empty");
      String trimmedPrefix = StringSupport.trimOrNull(prefix);
      String qualifiedName;
      if (trimmedPrefix != null) {
         qualifiedName = trimmedPrefix + ":" + StringSupport.trimOrNull(trimmedLocalName);
      } else {
         qualifiedName = StringSupport.trimOrNull(trimmedLocalName);
      }

      return document.createAttributeNS(StringSupport.trimOrNull(namespaceURI), qualifiedName);
   }

   @Nonnull
   public static Attr getAttribute(@Nullable Element element, @Nullable QName attributeName) {
      return element != null && attributeName != null ? element.getAttributeNodeNS(StringSupport.trimOrNull(attributeName.getNamespaceURI()), attributeName.getLocalPart()) : null;
   }

   @Nullable
   public static String getAttributeValue(@Nullable Element element, @Nullable QName attributeName) {
      return element != null && attributeName != null ? getAttributeValue(element, StringSupport.trimOrNull(attributeName.getNamespaceURI()), attributeName.getLocalPart()) : null;
   }

   @Nullable
   public static String getAttributeValue(@Nullable Element element, @Nullable String namespace, @Nullable String attributeLocalName) {
      if (element != null && attributeLocalName != null) {
         Attr attr = element.getAttributeNodeNS(namespace, attributeLocalName);
         return attr == null ? null : attr.getValue();
      } else {
         return null;
      }
   }

   @Nullable
   public static Boolean getAttributeValueAsBoolean(@Nullable Attr attribute) {
      if (attribute == null) {
         return null;
      } else {
         String valueStr = StringSupport.trimOrNull(attribute.getValue());
         if (!"0".equals(valueStr) && !"false".equals(valueStr)) {
            return !"1".equals(valueStr) && !"true".equals(valueStr) ? null : Boolean.TRUE;
         } else {
            return Boolean.FALSE;
         }
      }
   }

   @Nonnull
   public static List getAttributeValueAsList(@Nullable Attr attribute) {
      return attribute == null ? Collections.emptyList() : StringSupport.stringToList(attribute.getValue(), " \n\r\t");
   }

   @Nullable
   public static QName getAttributeValueAsQName(@Nullable Attr attribute) {
      if (attribute == null) {
         return null;
      } else {
         String attributeValue = StringSupport.trimOrNull(attribute.getTextContent());
         if (attributeValue == null) {
            return null;
         } else {
            String[] valueComponents = attributeValue.split(":");
            return valueComponents.length == 1 ? QNameSupport.constructQName(attribute.lookupNamespaceURI((String)null), valueComponents[0], (String)null) : QNameSupport.constructQName(attribute.lookupNamespaceURI(valueComponents[0]), valueComponents[1], valueComponents[0]);
         }
      }
   }

   @Nullable
   public static Long getDateTimeAttributeAsLong(@Nullable Attr attribute) {
      return attribute != null && StringSupport.trimOrNull(attribute.getValue()) != null ? DOMTypeSupport.dateTimeToLong(attribute.getValue()) : null;
   }

   @Nullable
   public static Long getDurationAttributeValueAsLong(@Nullable Attr attribute) {
      return attribute != null && StringSupport.trimOrNull(attribute.getValue()) != null ? DOMTypeSupport.durationToLong(attribute.getValue()) : null;
   }

   @Nullable
   public static Attr getIdAttribute(@Nullable Element element) {
      if (element != null && element.hasAttributes()) {
         NamedNodeMap attributes = element.getAttributes();

         for(int i = 0; i < attributes.getLength(); ++i) {
            Attr attribute = (Attr)attributes.item(i);
            if (attribute.isId()) {
               return attribute;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   @Nullable
   public static String getXMLBase(@Nullable Element element) {
      return getAttributeValue(element, XMLConstants.XML_BASE_ATTRIB_NAME);
   }

   @Nullable
   public static String getXMLId(@Nullable Element element) {
      return getAttributeValue(element, XMLConstants.XML_ID_ATTRIB_NAME);
   }

   @Nullable
   public static String getXMLLang(@Nullable Element element) {
      return getAttributeValue(element, XMLConstants.XML_LANG_ATTRIB_NAME);
   }

   @Nullable
   public static Locale getXMLLangAsLocale(@Nullable Element element) {
      if (element == null) {
         return null;
      } else {
         String lang = StringSupport.trimOrNull(getXMLLang(element));
         if (lang != null) {
            if (lang.contains("-")) {
               lang = lang.substring(0, lang.indexOf("-"));
            }

            return new Locale(lang.toUpperCase());
         } else {
            return Locale.getDefault();
         }
      }
   }

   @Nullable
   public static XMLSpace getXMLSpace(@Nullable Element element) {
      if (null == element) {
         return null;
      } else {
         String value = getAttributeValue(element, XMLConstants.XML_SPACE_ATTRIB_NAME);
         if (null == value) {
            return null;
         } else {
            try {
               return XMLSpace.parseValue(value);
            } catch (IllegalArgumentException var3) {
               return null;
            }
         }
      }
   }

   public static boolean hasAttribute(@Nullable Element element, @Nullable QName name) {
      return element != name && name != null ? element.hasAttributeNS(StringSupport.trimOrNull(name.getNamespaceURI()), name.getLocalPart()) : false;
   }

   public static boolean removeAttribute(@Nullable Element element, @Nullable QName attributeName) {
      if (hasAttribute(element, attributeName)) {
         element.removeAttributeNS(StringSupport.trimOrNull(attributeName.getNamespaceURI()), attributeName.getLocalPart());
         return true;
      } else {
         return false;
      }
   }
}
