package org.glassfish.hk2.xml.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.glassfish.hk2.utilities.general.GeneralUtilities;
import org.glassfish.hk2.utilities.general.IndentingXMLStreamWriter;
import org.glassfish.hk2.utilities.reflection.ClassReflectionHelper;
import org.glassfish.hk2.utilities.reflection.Logger;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;
import org.glassfish.hk2.xml.api.XmlHk2ConfigurationBean;
import org.glassfish.hk2.xml.api.XmlRootHandle;
import org.glassfish.hk2.xml.jaxb.internal.BaseHK2JAXBBean;
import org.glassfish.hk2.xml.spi.Model;

public class XmlStreamImpl {
   private static final boolean DEBUG_PARSING;
   private static final QName ANY_ATTRIBUTE_QNAME;

   public static Object parseRoot(XmlServiceImpl xmlService, Model rootModel, XMLStreamReader reader, Unmarshaller.Listener listener) throws Exception {
      Class rootProxyClass = rootModel.getProxyAsClass();
      ClassReflectionHelper classReflectionHelper = xmlService.getClassReflectionHelper();
      BaseHK2JAXBBean hk2Root = Utilities.createBean(rootProxyClass);
      hk2Root._setClassReflectionHelper(classReflectionHelper);
      if (DEBUG_PARSING) {
         Logger.getLogger().debug("XmlServiceDebug Created root bean with model " + hk2Root._getModel());
      }

      Map referenceMap = new HashMap();
      List unresolved = new LinkedList();

      while(reader.hasNext()) {
         int event = reader.next();
         if (DEBUG_PARSING) {
            Logger.getLogger().debug("XmlServiceDebug got xml event (A) " + eventToString(event));
         }

         switch (event) {
            case 1:
               HashMap rootNamespace = new HashMap();
               String defaultNamespace = null;
               int namespaceCount = reader.getNamespaceCount();
               int nLcv = 0;

               String elementTag;
               for(; nLcv < namespaceCount; ++nLcv) {
                  elementTag = reader.getNamespacePrefix(nLcv);
                  String namespaceURI = reader.getNamespaceURI(nLcv);
                  if (elementTag == null) {
                     defaultNamespace = namespaceURI;
                  } else {
                     rootNamespace.put(elementTag, namespaceURI);
                  }
               }

               String elementTagNamespace = QNameUtilities.getNamespace(reader.getName(), defaultNamespace);
               elementTag = reader.getName().getLocalPart();
               if (DEBUG_PARSING) {
                  Logger.getLogger().debug("XmlServiceDebug starting document tag " + elementTag);
               }

               handleElement(hk2Root, (BaseHK2JAXBBean)null, reader, classReflectionHelper, listener, referenceMap, unresolved, elementTagNamespace, elementTag, rootNamespace, defaultNamespace);
               break;
            case 8:
               Utilities.fillInUnfinishedReferences(referenceMap, unresolved);
               if (DEBUG_PARSING) {
                  Logger.getLogger().debug("XmlServiceDebug finished reading document");
               }

               return hk2Root;
         }
      }

      throw new IllegalStateException("Unexpected end of XMLReaderStream");
   }

   private static void handleElement(BaseHK2JAXBBean target, BaseHK2JAXBBean parent, XMLStreamReader reader, ClassReflectionHelper classReflectionHelper, Unmarshaller.Listener listener, Map referenceMap, List unresolved, String outerElementNamespace, String outerElementTag, Map namespaceMap, String defaultNamespace) throws Exception {
      listener.beforeUnmarshal(target, parent);
      Map listChildren = new HashMap();
      Map arrayChildren = new HashMap();
      Map listNonChild = new HashMap();
      Map arrayNonChild = new HashMap();
      ModelImpl targetModel = target._getModel();
      Map nonChildProperties = targetModel.getNonChildProperties();
      Map childProperties = targetModel.getChildrenByName();
      Set allWrappers = targetModel.getAllXmlWrappers();
      Map xmlAnyAttributeData = new LinkedHashMap();
      int numAttributes = reader.getAttributeCount();

      String attributeName;
      String elementTagNamespace;
      for(int lcv = 0; lcv < numAttributes; ++lcv) {
         String attributeNamespace = QNameUtilities.fixNamespace(reader.getAttributeNamespace(lcv));
         attributeName = reader.getAttributeLocalName(lcv);
         elementTagNamespace = reader.getAttributeValue(lcv);
         QName attributeQName = QNameUtilities.createQName(attributeNamespace, attributeName, defaultNamespace);
         if (DEBUG_PARSING) {
            Logger.getLogger().debug("XmlServiceDebug handling attribute " + attributeQName + " with value " + elementTagNamespace);
         }

         ChildDataModel childDataModel = (ChildDataModel)nonChildProperties.get(attributeQName);
         if (childDataModel == null) {
            xmlAnyAttributeData.put(attributeQName, elementTagNamespace);
         } else if (Format.ATTRIBUTE.equals(childDataModel.getFormat())) {
            Class childType = targetModel.getNonChildType(attributeNamespace, attributeName);
            if (!childDataModel.isReference()) {
               Object convertedValue = Utilities.getDefaultValue(elementTagNamespace, childType, namespaceMap);
               target._setProperty(attributeNamespace, attributeName, convertedValue);
            } else {
               if (DEBUG_PARSING) {
                  Logger.getLogger().debug("XmlServiceDebug attribute " + attributeName + " is a reference");
               }

               ReferenceKey rk = new ReferenceKey(childDataModel.getChildType(), elementTagNamespace);
               BaseHK2JAXBBean reference = (BaseHK2JAXBBean)referenceMap.get(rk);
               if (reference != null) {
                  target._setProperty(attributeNamespace, attributeName, reference);
               } else {
                  unresolved.add(new UnresolvedReference(childDataModel.getChildType(), elementTagNamespace, attributeNamespace, attributeName, target));
               }
            }
         }
      }

      ChildDataModel childDataModel = (ChildDataModel)nonChildProperties.get(ANY_ATTRIBUTE_QNAME);
      if (childDataModel != null) {
         target._setProperty((QName)ANY_ATTRIBUTE_QNAME, xmlAnyAttributeData);
      }

      while(reader.hasNext()) {
         int event = reader.next();
         if (DEBUG_PARSING) {
            Logger.getLogger().debug("XmlServiceDebug got xml event (B) " + eventToString(event));
         }

         String myType;
         String childTagNamespace;
         Object actualArray;
         switch (event) {
            case 1:
               attributeName = null;
               Map effectiveNamespaceMap = new HashMap(namespaceMap);
               int namespaceCount = reader.getNamespaceCount();
               int nLcv = 0;

               for(; nLcv < namespaceCount; ++nLcv) {
                  String namespacePrefix = reader.getNamespacePrefix(nLcv);
                  String namespaceURI = reader.getNamespaceURI(nLcv);
                  if (namespacePrefix == null) {
                     defaultNamespace = namespaceURI;
                  } else {
                     effectiveNamespaceMap.put(reader.getNamespacePrefix(nLcv), reader.getNamespaceURI(nLcv));
                  }
               }

               elementTagNamespace = QNameUtilities.getNamespace(reader.getName(), defaultNamespace);
               String elementTag = reader.getName().getLocalPart();
               QName elementTagQName = QNameUtilities.createQName(elementTagNamespace, elementTag, defaultNamespace);
               if (DEBUG_PARSING) {
                  Logger.getLogger().debug("XmlServiceDebug starting parse of element " + elementTag);
               }

               ChildDataModel cdm = (ChildDataModel)nonChildProperties.get(elementTagQName);
               Object realThing;
               Object cList;
               if (cdm != null && Format.ELEMENT.equals(cdm.getFormat())) {
                  String elementValue = advanceNonChildElement(reader, elementTag);
                  Class childType = cdm.getChildTypeAsClass();
                  if (!cdm.isReference()) {
                     Class aType;
                     if (List.class.equals(childType)) {
                        aType = cdm.getChildListTypeAsClass();
                        realThing = Utilities.getDefaultValue(elementValue, aType, effectiveNamespaceMap);
                        cList = (List)listNonChild.get(elementTagQName);
                        if (cList == null) {
                           cList = new LinkedList();
                           listNonChild.put(elementTagQName, cList);
                        }

                        ((List)cList).add(realThing);
                     } else if (childType.isArray() && !Byte.TYPE.equals(childType.getComponentType())) {
                        aType = childType.getComponentType();
                        realThing = Utilities.getDefaultValue(elementValue, aType, effectiveNamespaceMap);
                        ArrayInformation ai = (ArrayInformation)arrayNonChild.get(elementTagQName);
                        if (ai == null) {
                           ai = new ArrayInformation(aType);
                           arrayNonChild.put(elementTagQName, ai);
                        }

                        ai.add(realThing);
                     } else {
                        Object convertedValue = Utilities.getDefaultValue(elementValue, childType, effectiveNamespaceMap);
                        target._setProperty(elementTagNamespace, elementTag, convertedValue);
                     }
                  } else {
                     ReferenceKey referenceKey = new ReferenceKey(cdm.getChildType(), elementValue);
                     BaseHK2JAXBBean reference = (BaseHK2JAXBBean)referenceMap.get(referenceKey);
                     if (reference != null) {
                        target._setProperty(elementTagNamespace, elementTag, reference);
                     } else {
                        unresolved.add(new UnresolvedReference(cdm.getChildType(), elementValue, elementTagNamespace, elementTag, target));
                     }
                  }
               } else {
                  ParentedModel informedChild = (ParentedModel)childProperties.get(elementTagQName);
                  if (informedChild != null) {
                     ModelImpl grandChild = informedChild.getChildModel();
                     BaseHK2JAXBBean hk2Root = Utilities.createBean(grandChild.getProxyAsClass());
                     hk2Root._setClassReflectionHelper(classReflectionHelper);
                     if (DEBUG_PARSING) {
                        Logger.getLogger().debug("XmlServiceBean created child bean of " + outerElementNamespace + "," + outerElementTag + " with model " + hk2Root._getModel());
                     }

                     handleElement(hk2Root, target, reader, classReflectionHelper, listener, referenceMap, unresolved, outerElementTag, elementTag, effectiveNamespaceMap, defaultNamespace);
                     realThing = hk2Root;
                     if (informedChild.getAdapter() != null) {
                        XmlAdapter adapter = (XmlAdapter)ReflectionHelper.cast(informedChild.getAdapterObject());
                        realThing = adapter.unmarshal(hk2Root);
                     }

                     if (informedChild.getChildType().equals(ChildType.DIRECT)) {
                        target._setProperty(elementTagNamespace, elementTag, realThing);
                     } else if (informedChild.getChildType().equals(ChildType.LIST)) {
                        cList = (List)listChildren.get(elementTagQName);
                        if (cList == null) {
                           cList = new ArrayList();
                           listChildren.put(elementTagQName, cList);
                        }

                        ((List)cList).add(hk2Root);
                     } else if (informedChild.getChildType().equals(ChildType.ARRAY)) {
                        cList = (List)arrayChildren.get(elementTagQName);
                        if (cList == null) {
                           cList = new LinkedList();
                           arrayChildren.put(elementTagQName, cList);
                        }

                        ((List)cList).add(hk2Root);
                     }
                  } else if (allWrappers.contains(elementTag)) {
                     skipWrapperElement(target, parent, reader, classReflectionHelper, listener, referenceMap, unresolved, elementTagNamespace, elementTag, effectiveNamespaceMap, defaultNamespace, elementTag, listChildren, arrayChildren);
                  } else {
                     if (DEBUG_PARSING) {
                        Logger.getLogger().debug("XmlServiceBean found unknown element in " + outerElementTag + " named " + elementTag + " skipping");
                     }

                     skip(reader, elementTag);
                  }
               }
               break;
            case 2:
               Iterator var58 = listChildren.entrySet().iterator();

               Map.Entry entry;
               while(var58.hasNext()) {
                  entry = (Map.Entry)var58.next();
                  myType = QNameUtilities.getNamespace((QName)entry.getKey(), defaultNamespace);
                  childTagNamespace = ((QName)entry.getKey()).getLocalPart();
                  target._setProperty(myType, childTagNamespace, entry.getValue());
               }

               var58 = arrayChildren.entrySet().iterator();

               QName childTag;
               while(var58.hasNext()) {
                  entry = (Map.Entry)var58.next();
                  childTag = (QName)entry.getKey();
                  childTagNamespace = QNameUtilities.getNamespace(childTag, defaultNamespace);
                  String childTagKey = childTag.getLocalPart();
                  ParentedModel pn = targetModel.getChild(childTagNamespace, childTagKey);
                  Class childType = pn.getChildModel().getOriginalInterfaceAsClass();
                  List individuals = (List)entry.getValue();
                  Object actualArray = Array.newInstance(childType, individuals.size());
                  int index = 0;
                  Iterator var40 = individuals.iterator();

                  while(var40.hasNext()) {
                     BaseHK2JAXBBean individual = (BaseHK2JAXBBean)var40.next();
                     Array.set(actualArray, index++, individual);
                  }

                  target._setProperty(childTag, actualArray);
               }

               var58 = listNonChild.entrySet().iterator();

               while(var58.hasNext()) {
                  entry = (Map.Entry)var58.next();
                  childTag = (QName)entry.getKey();
                  List value = (List)entry.getValue();
                  target._setProperty((QName)childTag, value);
               }

               var58 = arrayNonChild.entrySet().iterator();

               while(var58.hasNext()) {
                  entry = (Map.Entry)var58.next();
                  childTag = (QName)entry.getKey();
                  ArrayInformation ai = (ArrayInformation)entry.getValue();
                  actualArray = Array.newInstance(ai.getAType(), ai.getValues().size());
                  int lcv = 0;
                  Iterator var76 = ai.getValues().iterator();

                  while(var76.hasNext()) {
                     Object value = var76.next();
                     Array.set(actualArray, lcv++, value);
                  }

                  target._setProperty(childTag, actualArray);
               }

               listener.afterUnmarshal(target, parent);
               QName keyProp = target._getKeyPropertyName();
               if (keyProp != null) {
                  String keyVal = (String)target._getProperty(keyProp);
                  myType = target._getModel().getOriginalInterface();
                  if (keyVal != null && myType != null) {
                     referenceMap.put(new ReferenceKey(myType, keyVal), target);
                  }
               }

               if (DEBUG_PARSING) {
                  Logger.getLogger().debug("XmlServiceDebug ending parse of element " + outerElementTag);
               }

               return;
            case 3:
            case 5:
            default:
               break;
            case 4:
               ChildDataModel valueModel = targetModel.getValueData();
               if (valueModel != null) {
                  String text = reader.getText();
                  Class childType = valueModel.getChildTypeAsClass();
                  myType = targetModel.getValuePropertyNamespace();
                  childTagNamespace = targetModel.getValueProperty();
                  actualArray = Utilities.getDefaultValue(text, childType, namespaceMap);
                  target._setProperty(myType, childTagNamespace, actualArray);
               }
         }
      }

   }

   private static String advanceNonChildElement(XMLStreamReader reader, String outerTag) throws Exception {
      String retVal = null;

      while(reader.hasNext()) {
         int nextEvent = reader.next();
         if (DEBUG_PARSING) {
            Logger.getLogger().debug("XmlServiceDebug got xml event (C) " + eventToString(nextEvent));
         }

         switch (nextEvent) {
            case 2:
               if (DEBUG_PARSING) {
                  Logger.getLogger().debug("XmlServiceDebug ending parse of non-child element " + outerTag);
               }

               return retVal;
            case 4:
               String text = reader.getText();
               retVal = text.trim();
               if (DEBUG_PARSING) {
                  Logger.getLogger().debug("XmlServiceDebug characters of tag " + outerTag + " is " + retVal);
               }
         }
      }

      return retVal;
   }

   private static void skipWrapperElement(BaseHK2JAXBBean target, BaseHK2JAXBBean parent, XMLStreamReader reader, ClassReflectionHelper classReflectionHelper, Unmarshaller.Listener listener, Map referenceMap, List unresolved, String outerElementTagNamespace, String outerElementTag, Map namespaceMap, String defaultNamespace, String xmlWrapper, Map listChildren, Map arrayChildren) throws Exception {
      ModelImpl targetModel = target._getModel();
      Map childProperties = targetModel.getChildrenByName();

      while(reader.hasNext()) {
         int event = reader.next();
         if (DEBUG_PARSING) {
            String name = null;
            if (reader.hasName()) {
               name = reader.getName().getLocalPart();
            }

            Logger.getLogger().debug("XmlServiceDebug got xml event (E) " + eventToString(event) + " with name " + name);
         }

         switch (event) {
            case 1:
               Map effectiveNamespaceMap = new HashMap(namespaceMap);
               int namespaceCount = reader.getNamespaceCount();
               int nLcv = 0;

               String elementTag;
               for(; nLcv < namespaceCount; ++nLcv) {
                  elementTag = reader.getNamespacePrefix(nLcv);
                  String namespaceURI = reader.getNamespaceURI(nLcv);
                  if (elementTag == null) {
                     defaultNamespace = namespaceURI;
                  } else {
                     effectiveNamespaceMap.put(elementTag, namespaceURI);
                  }
               }

               String elementTagNamespace = QNameUtilities.getNamespace(reader.getName(), defaultNamespace);
               elementTag = reader.getName().getLocalPart();
               QName elementTagQName = QNameUtilities.createQName(elementTagNamespace, elementTag, defaultNamespace);
               ParentedModel informedChild = (ParentedModel)childProperties.get(elementTagQName);
               if (informedChild != null && GeneralUtilities.safeEquals(xmlWrapper, informedChild.getXmlWrapperTag())) {
                  ModelImpl grandChild = informedChild.getChildModel();
                  BaseHK2JAXBBean hk2Root = Utilities.createBean(grandChild.getProxyAsClass());
                  hk2Root._setClassReflectionHelper(classReflectionHelper);
                  if (DEBUG_PARSING) {
                     Logger.getLogger().debug("XmlServiceBean created child bean of " + outerElementTagNamespace + "," + outerElementTag + " with model " + hk2Root._getModel());
                  }

                  handleElement(hk2Root, target, reader, classReflectionHelper, listener, referenceMap, unresolved, elementTagNamespace, elementTag, effectiveNamespaceMap, defaultNamespace);
                  if (informedChild.getChildType().equals(ChildType.DIRECT)) {
                     target._setProperty(elementTagNamespace, elementTag, hk2Root);
                  } else {
                     Object cList;
                     if (informedChild.getChildType().equals(ChildType.LIST)) {
                        cList = (List)listChildren.get(elementTagQName);
                        if (cList == null) {
                           cList = new ArrayList();
                           listChildren.put(elementTagQName, cList);
                        }

                        ((List)cList).add(hk2Root);
                     } else if (informedChild.getChildType().equals(ChildType.ARRAY)) {
                        cList = (List)arrayChildren.get(elementTagQName);
                        if (cList == null) {
                           cList = new LinkedList();
                           arrayChildren.put(elementTagQName, cList);
                        }

                        ((List)cList).add(hk2Root);
                     }
                  }
               }
               break;
            case 2:
               reader.getName().getLocalPart();
               return;
         }
      }

   }

   private static void skip(XMLStreamReader reader, String skipOverTag) throws Exception {
      while(true) {
         if (reader.hasNext()) {
            int event = reader.next();
            String elementTag;
            if (DEBUG_PARSING) {
               elementTag = null;
               if (reader.hasName()) {
                  elementTag = reader.getName().getLocalPart();
               }

               Logger.getLogger().debug("XmlServiceDebug got xml event (D) " + eventToString(event) + " with name " + elementTag);
            }

            if (2 != event) {
               continue;
            }

            elementTag = reader.getName().getLocalPart();
            if (!skipOverTag.equals(elementTag)) {
               continue;
            }

            return;
         }

         return;
      }
   }

   private static String eventToString(int event) {
      switch (event) {
         case 1:
            return "START_ELEMENT";
         case 2:
            return "END_ELEMENT";
         case 3:
            return "PROCESSING_INSTRUCTION";
         case 4:
            return "CHARACTERS";
         case 5:
            return "COMMENT";
         case 6:
            return "SPACE";
         case 7:
            return "START_DOCUMENT";
         case 8:
            return "END_DOCUMENT";
         case 9:
            return "ENTITY_REFERENCE";
         case 10:
            return "ATTRIBUTE";
         case 11:
            return "DTD";
         case 12:
            return "CDATA";
         case 13:
            return "NAMESPACE";
         case 14:
            return "NOTATION_DECLARATION";
         case 15:
            return "ENTITY_DECLARATION";
         default:
            return "UNKNOWN EVENT: " + event;
      }
   }

   public static void marshall(OutputStream outputStream, XmlRootHandle root) throws IOException {
      try {
         marshallXmlStream(outputStream, root);
      } catch (XMLStreamException var3) {
         throw new IOException(var3);
      }
   }

   private static void marshallXmlStream(OutputStream outputStream, XmlRootHandle root) throws XMLStreamException {
      XMLStreamWriter rawWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(outputStream);
      IndentingXMLStreamWriter indenter = new IndentingXMLStreamWriter(rawWriter);

      try {
         indenter.writeStartDocument();
         XmlHk2ConfigurationBean bean = (XmlHk2ConfigurationBean)root.getRoot();
         if (bean != null) {
            marshallElement(indenter, bean, (ParentedModel)null);
         }

         indenter.writeEndDocument();
      } finally {
         rawWriter.close();
      }

   }

   private static void marshallElement(XMLStreamWriter indenter, XmlHk2ConfigurationBean bean, ParentedModel parented) throws XMLStreamException {
      ModelImpl model = bean._getModel();
      Map beanLikeMap = bean._getBeanLikeMap();
      String xmlTag;
      if (parented == null) {
         QName rootName = model.getRootName();
         xmlTag = rootName.getLocalPart();
      } else {
         xmlTag = parented.getChildXmlTag();
      }

      indenter.writeStartElement(xmlTag);
      Map attributeModels = model.getAllAttributeChildren();
      Iterator var7 = attributeModels.entrySet().iterator();

      while(var7.hasNext()) {
         Map.Entry entry = (Map.Entry)var7.next();
         QName attributeQName = (QName)entry.getKey();
         ChildDataModel childDataModel = (ChildDataModel)entry.getValue();
         String attributeTagKey = attributeQName.getLocalPart();
         Object value = beanLikeMap.get(attributeTagKey);
         if (value != null) {
            String valueAsString;
            if (!childDataModel.isReference()) {
               valueAsString = value.toString();
            } else {
               XmlHk2ConfigurationBean reference = (XmlHk2ConfigurationBean)value;
               valueAsString = reference._getKeyValue();
            }

            if (!GeneralUtilities.safeEquals(valueAsString, childDataModel.getDefaultAsString())) {
               indenter.writeAttribute(attributeTagKey, valueAsString);
            }
         }
      }

      Map elementDescriptors = model.getAllElementChildren();
      Iterator var22 = elementDescriptors.entrySet().iterator();

      while(true) {
         ParentedModel parentedChild;
         List asList;
         XmlHk2ConfigurationBean child;
         do {
            while(true) {
               Object value;
               int length;
               do {
                  while(true) {
                     ChildDescriptor descriptor;
                     String elementTagKey;
                     do {
                        if (!var22.hasNext()) {
                           indenter.writeEndElement();
                           return;
                        }

                        Map.Entry entry = (Map.Entry)var22.next();
                        QName elementQName = (QName)entry.getKey();
                        descriptor = (ChildDescriptor)entry.getValue();
                        elementTagKey = elementQName.getLocalPart();
                        value = beanLikeMap.get(elementTagKey);
                     } while(value == null);

                     parentedChild = descriptor.getParentedModel();
                     if (parentedChild != null) {
                        break;
                     }

                     ChildDataModel childDataModel = descriptor.getChildDataModel();
                     if (!childDataModel.isReference()) {
                        if (!value.getClass().isArray()) {
                           String valueAsString = value.toString();
                           indenter.writeStartElement(elementTagKey);
                           indenter.writeCharacters(valueAsString);
                           indenter.writeEndElement();
                        } else {
                           length = Array.getLength(value);

                           for(int lcv = 0; lcv < length; ++lcv) {
                              Object indexedValue = Array.get(value, lcv);
                              String valueAsString = indexedValue.toString();
                              indenter.writeStartElement(elementTagKey);
                              indenter.writeCharacters(valueAsString);
                              indenter.writeEndElement();
                           }
                        }
                     } else {
                        XmlHk2ConfigurationBean reference = (XmlHk2ConfigurationBean)value;
                        String keyValue = reference._getKeyValue();
                        indenter.writeStartElement(elementTagKey);
                        indenter.writeCharacters(keyValue);
                        indenter.writeEndElement();
                     }
                  }
               } while(AliasType.HAS_ALIASES.equals(parentedChild.getAliasType()));

               if (ChildType.LIST.equals(parentedChild.getChildType())) {
                  asList = (List)value;
                  break;
               }

               if (ChildType.ARRAY.equals(parentedChild.getChildType())) {
                  int length = Array.getLength(value);
                  if (length > 0) {
                     for(length = 0; length < length; ++length) {
                        child = (XmlHk2ConfigurationBean)Array.get(value, length);
                        marshallElement(indenter, child, parentedChild);
                     }
                  }
               } else {
                  marshallElement(indenter, (XmlHk2ConfigurationBean)value, parentedChild);
               }
            }
         } while(asList.isEmpty());

         Iterator var34 = asList.iterator();

         while(var34.hasNext()) {
            child = (XmlHk2ConfigurationBean)var34.next();
            marshallElement(indenter, child, parentedChild);
         }
      }
   }

   static {
      DEBUG_PARSING = XmlServiceImpl.DEBUG_PARSING;
      ANY_ATTRIBUTE_QNAME = QNameUtilities.createQName("##default", "##XmlAnyAttribute");
   }

   private static class ArrayInformation {
      private final Class aType;
      private final List values;

      private ArrayInformation(Class aType) {
         this.values = new LinkedList();
         this.aType = aType;
      }

      private void add(Object addMe) {
         this.values.add(addMe);
      }

      private Class getAType() {
         return this.aType;
      }

      private List getValues() {
         return this.values;
      }

      // $FF: synthetic method
      ArrayInformation(Class x0, Object x1) {
         this(x0);
      }
   }
}
