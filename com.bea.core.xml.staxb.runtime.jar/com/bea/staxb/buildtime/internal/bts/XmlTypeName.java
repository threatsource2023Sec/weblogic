package com.bea.staxb.buildtime.internal.bts;

import com.bea.xbean.common.XMLChar;
import com.bea.xml.SchemaType;
import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.XmlBeans;
import com.bea.xml.soap.SOAPArrayType;
import java.io.Serializable;
import javax.xml.namespace.QName;

public class XmlTypeName implements Serializable {
   public static final char NOTATION = 'n';
   public static final char ELEMENT = 'e';
   public static final char ID_CONSTRAINT = 'k';
   public static final char MODEL_GROUP = 'g';
   public static final char ALL = 'l';
   public static final char SEQUENCE = 's';
   public static final char CHOICE = 'c';
   public static final char PARTICLE = 'p';
   public static final char WILDCARD = 'w';
   public static final char ATTRIBUTE_USE = 'v';
   public static final char ATTRIBUTE = 'a';
   public static final char ATTRIBUTE_GROUP = 'r';
   public static final char TYPE = 't';
   public static final char DOCUMENT_TYPE = 'd';
   public static final char ATTRIBUTE_TYPE = 'b';
   public static final char MEMBER = 'm';
   public static final char SOAP_ARRAY = 'y';
   public static final char EXCEPTION = 'x';
   public static final char NO_TYPE = 'z';
   public static final char ANY_ELEMENT_WILDCARD_TYPE = 'u';
   public static final char ANY_ELEMENT_WILDCARD_ELEMENT = 'o';
   public static final char ANY_ELEMENT_WILDCARD_ARRAY_TYPE = 'q';
   public static final char ANY_ELEMENT_WILDCARD_ARRAY_ELEMENT = 'j';
   private static final String WILDCARD_NS = "http://com.bea/wls92/com/bea/staxb/buildtime/internal/bts";
   public static final QName ANY_ELEMENT_WILDCARD_ELEMENT_NAME = new QName("http://com.bea/wls92/com/bea/staxb/buildtime/internal/bts", "_any");
   public static final QName ANY_ELEMENT_WILDCARD_TYPE_NAME = new QName("http://com.bea/wls92/com/bea/staxb/buildtime/internal/bts", "ANY_ELEMENT_WILDCARD_TYPE_NAME");
   public static final QName ANY_ELEMENT_WILDCARD_ARRAY_ELEMENT_NAME = new QName("http://com.bea/wls92/com/bea/staxb/buildtime/internal/bts", "_arrayOfAny");
   public static final QName ANY_ELEMENT_WILDCARD_ARRAY_TYPE_NAME = new QName("http://com.bea/wls92/com/bea/staxb/buildtime/internal/bts", "ANY_ELEMENT_WILDCARD_ARRAY_TYPE_NAME");
   private static final long serialVersionUID = 1L;
   private String namespace;
   private String path;

   public static XmlTypeName forString(String signature) {
      if (signature == null) {
         throw new IllegalArgumentException("null signature");
      } else {
         int atSign = signature.indexOf(64);
         String path;
         String namespace;
         if (atSign < 0) {
            namespace = "";
            path = signature;
         } else {
            namespace = signature.substring(atSign + 1).intern();
            path = signature.substring(0, atSign).intern();
         }

         return forPathAndNamespace(path, namespace);
      }
   }

   public static XmlTypeName forTypeNamed(QName name) {
      if (name != null && name.getLocalPart() != null) {
         return name.getLocalPart().startsWith("t|") ? forPathAndNamespace(name.getLocalPart(), name.getNamespaceURI()) : forPathAndNamespace("t=" + name.getLocalPart(), name.getNamespaceURI());
      } else {
         throw new IllegalArgumentException("null qname");
      }
   }

   public static XmlTypeName forGlobalName(char kind, QName name) {
      if (name == null) {
         throw new IllegalArgumentException("null qname");
      } else {
         return forPathAndNamespace(kind + "=" + name.getLocalPart(), name.getNamespaceURI());
      }
   }

   public static XmlTypeName forElementWildCardType() {
      return forPathAndNamespace("u=" + ANY_ELEMENT_WILDCARD_TYPE_NAME.getLocalPart(), ANY_ELEMENT_WILDCARD_TYPE_NAME.getNamespaceURI());
   }

   public static XmlTypeName forElementWildCardElement() {
      return forPathAndNamespace("o=" + ANY_ELEMENT_WILDCARD_ELEMENT_NAME.getLocalPart(), ANY_ELEMENT_WILDCARD_ELEMENT_NAME.getNamespaceURI());
   }

   public static XmlTypeName forElementWildCardArrayType() {
      return forPathAndNamespace("q=" + ANY_ELEMENT_WILDCARD_ARRAY_TYPE_NAME.getLocalPart(), ANY_ELEMENT_WILDCARD_ARRAY_TYPE_NAME.getNamespaceURI());
   }

   public static XmlTypeName forElementWildCardArrayElement() {
      return forPathAndNamespace("j=" + ANY_ELEMENT_WILDCARD_ARRAY_ELEMENT_NAME.getLocalPart(), ANY_ELEMENT_WILDCARD_ARRAY_ELEMENT_NAME.getNamespaceURI());
   }

   public static XmlTypeName forNestedName(char kind, String localName, boolean qualified, XmlTypeName outer) {
      if (localName == null) {
         throw new IllegalArgumentException("null localName");
      } else if (outer == null) {
         throw new IllegalArgumentException("null outer");
      } else {
         return forPathAndNamespace(kind + (qualified ? "=" : "-") + localName + "|" + outer.path, outer.namespace);
      }
   }

   public static XmlTypeName forNestedNumber(char kind, int n, XmlTypeName outer) {
      if (outer == null) {
         throw new IllegalArgumentException("null outer");
      } else {
         return forPathAndNamespace(kind + "." + n + "|" + outer.path, outer.namespace);
      }
   }

   public static XmlTypeName forNestedAnonymous(char kind, XmlTypeName outer) {
      if (outer == null) {
         throw new IllegalArgumentException("null outer");
      } else {
         return forPathAndNamespace(kind + "|" + outer.path, outer.namespace);
      }
   }

   public static XmlTypeName forFaultType(QName messageName, String partName, XmlTypeName member) {
      if (member == null) {
         throw new IllegalArgumentException("null member");
      } else {
         return forPathAndNamespace("x=" + messageName + "|" + 'x' + "-" + partName + "|" + member.path, member.namespace);
      }
   }

   public static XmlTypeName forSchemaType(SchemaType sType) {
      if (sType == null) {
         throw new IllegalArgumentException("null sType");
      } else if (sType.getName() != null) {
         return forTypeNamed(sType.getName());
      } else if (sType.isDocumentType()) {
         return forGlobalName('d', sType.getDocumentElementName());
      } else if (sType.isAttributeType()) {
         return forGlobalName('b', sType.getAttributeTypeAttributeName());
      } else if (!sType.isNoType() && sType.getOuterType() != null) {
         SchemaType outerType = sType.getOuterType();
         XmlTypeName outerName = forSchemaType(outerType);
         if (sType.getContainerField() != null) {
            boolean qualified = sType.getContainerField().getName().getNamespaceURI().length() > 0;
            String localName = sType.getContainerField().getName().getLocalPart();
            char kind = sType.getContainerField().isAttribute() ? 97 : 101;
            return forNestedAnonymous('t', forNestedName((char)kind, localName, qualified, outerName));
         } else {
            return outerType.getSimpleVariety() == 2 ? forNestedAnonymous('t', forNestedNumber('m', sType.getAnonymousUnionMemberOrdinal(), outerName)) : forNestedAnonymous('t', outerName);
         }
      } else {
         return forPathAndNamespace("z", "");
      }
   }

   public static XmlTypeName forSoapArrayType(SOAPArrayType sType) {
      if (sType == null) {
         throw new IllegalArgumentException("null sType");
      } else {
         StringBuffer sb = new StringBuffer();
         sb.append("y." + sType.getDimensions().length);
         int[] ranks = sType.getRanks();

         for(int i = ranks.length - 1; i >= 0; --i) {
            sb.append("|y." + ranks[i]);
         }

         QName name = sType.getQName();
         sb.append("|t=" + name.getLocalPart());
         return forPathAndNamespace(sb.toString(), name.getNamespaceURI());
      }
   }

   public int getSoapArrayRankAt(int pos) {
      int rank = -1;
      if (this.path == null) {
         return rank;
      } else if (!this.path.startsWith(String.valueOf('y'))) {
         return rank;
      } else {
         String soapArrayPrefix = 'y' + ".";
         int currPos = 0;
         int index = 0;

         int nextIndex;
         for(int nextIndex = false; (nextIndex = this.path.indexOf(soapArrayPrefix, index)) != -1; index = nextIndex + 2) {
            String dim = this.path.substring(nextIndex + 2, this.path.indexOf("|", nextIndex + 2));
            rank = Integer.parseInt(dim);
            ++currPos;
            if (currPos >= pos) {
               break;
            }
         }

         if (currPos < pos) {
            rank = -1;
         }

         return rank;
      }
   }

   public boolean valid() {
      XmlTypeName outerComponent = null;
      int outerType = 0;
      String localName = this.internalGetStringName();
      boolean hasNumber = this.internalGetNumber() >= 0;
      boolean hasName = localName != null;
      boolean isAnonymous = this.internalIsAnonymous();
      boolean isQualified = this.internalIsQualified();
      boolean isGlobal = this.isGlobal();
      if (!isGlobal) {
         outerComponent = this.getOuterComponent();
         outerType = outerComponent.getComponentType();
      }

      if (localName != null && !XMLChar.isValidNCName(localName)) {
         return false;
      } else {
         boolean result;
         switch (this.getComponentType()) {
            case 97:
               result = hasName && (isGlobal && isQualified || outerType == 116 || outerType == 118);
               break;
            case 98:
               result = hasName && isQualified && isGlobal;
               break;
            case 99:
            case 108:
            case 115:
               result = isAnonymous && (outerType == 112 || outerType == 103);
               break;
            case 100:
               result = hasName && isQualified && isGlobal;
               break;
            case 101:
               result = hasName && (isGlobal && isQualified || outerType == 116 || outerType == 112);
               break;
            case 102:
            case 104:
            case 105:
            case 106:
            case 111:
            case 113:
            case 117:
            default:
               result = false;
               break;
            case 103:
               result = hasName && isGlobal;
               break;
            case 107:
               result = hasName && outerType == 101;
               break;
            case 109:
               result = isAnonymous && outerType == 116;
               break;
            case 110:
               result = isGlobal && hasName && isQualified;
               break;
            case 112:
               result = hasNumber && (outerType == 115 || outerType == 99 || outerType == 108 || outerType == 116);
               break;
            case 114:
               result = hasName && isQualified && isGlobal;
               break;
            case 116:
               result = hasName && isQualified && isGlobal || isAnonymous && outerType == 116 || outerType == 101 || outerType == 97 || outerType == 109;
               break;
            case 118:
               result = hasName && (outerType == 116 || outerType == 114);
               break;
            case 119:
               result = isAnonymous && (outerType == 112 || outerType == 116 || outerType == 114);
               break;
            case 120:
               result = hasName && (isQualified && outerType == 120 || !isQualified && (outerType == 116 || outerType == 101) && outerComponent.isGlobal());
               break;
            case 121:
               result = hasNumber && (outerType == 121 || outerType == 116 && outerComponent.isGlobal());
               break;
            case 122:
               result = isAnonymous && isGlobal && this.namespace.length() == 0;
         }

         if (!result) {
            return false;
         } else {
            return isGlobal ? true : outerComponent.valid();
         }
      }
   }

   public boolean isSchemaType() {
      switch (this.getComponentType()) {
         case 98:
         case 100:
         case 116:
         case 122:
            return true;
         default:
            return false;
      }
   }

   public boolean isExceptionType() {
      return 120 == this.getComponentType();
   }

   public boolean isType() {
      return 116 == this.getComponentType();
   }

   public boolean isElement() {
      return 101 == this.getComponentType();
   }

   public boolean isElementWildcardType() {
      return 117 == this.getComponentType();
   }

   public boolean isElementWildcardElement() {
      return 111 == this.getComponentType();
   }

   public boolean isElementWildcardArrayType() {
      return 113 == this.getComponentType();
   }

   public boolean isElementWildcardArrayElement() {
      return 106 == this.getComponentType();
   }

   public SchemaType findTypeIn(SchemaTypeLoader loader) {
      switch (this.getComponentType()) {
         case 98:
            return loader.findAttributeType(this.getQName());
         case 100:
            return loader.findDocumentType(this.getQName());
         case 116:
            if (this.isGlobal()) {
               return loader.findType(this.getQName());
            } else {
               XmlTypeName outerName = this.getOuterComponent();

               XmlTypeName outerTypeName;
               for(outerTypeName = outerName; !outerTypeName.isSchemaType(); outerTypeName = outerTypeName.getOuterComponent()) {
                  if (outerTypeName.isGlobal()) {
                     switch (outerName.getComponentType()) {
                        case 97:
                           return loader.findAttributeType(outerTypeName.getQName()).getAttributeType(outerTypeName.getQName(), loader);
                        case 101:
                           return loader.findDocumentType(outerTypeName.getQName()).getElementType(outerTypeName.getQName(), (QName)null, loader);
                        default:
                           throw new IllegalStateException("Illegal type name " + this);
                     }
                  }
               }

               SchemaType outerType = outerTypeName.findTypeIn(loader);
               switch (outerName.getComponentType()) {
                  case 97:
                     return outerType.getAttributeType(outerName.getQName(), loader);
                  case 101:
                     return outerType.getElementType(outerName.getQName(), (QName)null, loader);
                  case 109:
                     return outerType.getAnonymousTypes()[outerName.getNumber()];
                  case 116:
                     return outerType.getAnonymousTypes()[0];
                  default:
                     throw new IllegalStateException("Illegal type name " + this);
               }
            }
         case 122:
            return XmlBeans.NO_TYPE;
         default:
            return null;
      }
   }

   public boolean isGlobal() {
      int index = this.path.indexOf(124);
      return index < 0;
   }

   public XmlTypeName getOuterComponent() {
      int index = this.path.indexOf(124);
      return index < 0 ? null : forPathAndNamespace(this.path.substring(index + 1), this.namespace);
   }

   public int getComponentType() {
      return this.path.length() > 0 ? this.path.charAt(0) : 0;
   }

   public int getNumber() {
      int result = this.internalGetNumber();
      if (result < 0) {
         throw new IllegalStateException("Path has no number");
      } else {
         return result;
      }
   }

   public QName getQName() {
      String localName = this.internalGetStringName();
      if (localName == null) {
         return null;
      } else {
         return this.internalIsQualified() ? new QName(this.namespace, localName) : new QName(localName);
      }
   }

   public String getNamespace() {
      return this.namespace;
   }

   private static XmlTypeName forPathAndNamespace(String path, String namespace) {
      return new XmlTypeName(path, namespace);
   }

   private XmlTypeName(String path, String namespace) {
      if (path != null && namespace != null) {
         this.path = path.intern();
         this.namespace = namespace.intern();
      } else {
         throw new IllegalArgumentException();
      }
   }

   private int internalGetNumber() {
      if (this.path.length() > 1 && this.path.charAt(1) == '.') {
         int index = this.path.indexOf(124);
         if (index < 0) {
            index = this.path.length();
         }

         try {
            return Integer.parseInt(this.path.substring(2, index));
         } catch (Exception var3) {
            return -1;
         }
      } else {
         return -1;
      }
   }

   private String internalGetStringName() {
      if (this.path.length() <= 1 || !this.path.startsWith("t|") && this.path.charAt(1) != '=' && this.path.charAt(1) != '-') {
         return null;
      } else if (this.path.startsWith("t|")) {
         return this.path;
      } else {
         int index = this.path.indexOf(124);
         if (index < 0) {
            index = this.path.length();
         }

         return this.path.substring(2, index);
      }
   }

   private boolean internalIsQualified() {
      return this.path.length() > 1 && (this.path.charAt(1) == '=' || this.path.startsWith("t|"));
   }

   private boolean internalIsAnonymous() {
      return this.path.length() <= 1 || this.path.charAt(1) == '|';
   }

   public String toString() {
      return this.namespace.length() == 0 ? this.path : this.path + '@' + this.namespace;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof XmlTypeName)) {
         return false;
      } else {
         XmlTypeName xmlName = (XmlTypeName)o;
         if (!this.namespace.equals(xmlName.namespace)) {
            return false;
         } else {
            return this.path.equals(xmlName.path);
         }
      }
   }

   public int hashCode() {
      int result = this.namespace.hashCode();
      result = 29 * result + this.path.hashCode();
      return result;
   }

   public boolean isAnonymousType() {
      String inPath = this.internalGetStringName();
      return inPath == null || inPath.startsWith("t|");
   }
}
