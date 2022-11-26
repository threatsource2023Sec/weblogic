package com.sun.faces.config.processor;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.mgbean.BeanManager;
import com.sun.faces.mgbean.ManagedBeanInfo;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.TypedCollections;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ManagedBeanConfigProcessor extends AbstractConfigProcessor {
   private static final Logger LOGGER;
   private static final String MANAGED_BEAN = "managed-bean";
   private static final String DESCRIPTION = "description";
   private static final String MGBEAN_NAME = "managed-bean-name";
   private static final String MGBEAN_CLASS = "managed-bean-class";
   private static final String MGBEAN_SCOPE = "managed-bean-scope";
   private static final String MG_PROPERTY = "managed-property";
   private static final String MG_PROPERTY_NAME = "property-name";
   private static final String MG_PROPERTY_TYPE = "property-class";
   private static final String NULL_VALUE = "null-value";
   private static final String VALUE = "value";
   private static final String KEY = "key";
   private static final String MAP_KEY_CLASS = "key-class";
   private static final String VALUE_CLASS = "value-class";
   private static final String MAP_ENTRY = "map-entry";
   private static final String MAP_ENTRIES = "map-entries";
   private static final String LIST_ENTRIES = "list-entries";
   private static final String DEFAULT_SCOPE = "request";

   public void process(ServletContext sc, Document[] documents) throws Exception {
      BeanManager beanManager = null;

      for(int i = 0; i < documents.length; ++i) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Processing managed-bean elements for document: ''{0}''", documents[i].getDocumentURI()));
         }

         String namespace = documents[i].getDocumentElement().getNamespaceURI();
         NodeList managedBeans = documents[i].getDocumentElement().getElementsByTagNameNS(namespace, "managed-bean");
         if (managedBeans != null && managedBeans.getLength() > 0) {
            ApplicationAssociate associate = ApplicationAssociate.getInstance(sc);
            if (associate != null) {
               beanManager = associate.getBeanManager();
               int m = 0;

               for(int size = managedBeans.getLength(); m < size; ++m) {
                  this.addManagedBean(beanManager, managedBeans.item(m));
               }
            }
         }
      }

      if (beanManager != null) {
         beanManager.preProcessesBeans();
      }

      this.invokeNext(sc, documents);
   }

   private void addManagedBean(BeanManager beanManager, Node managedBean) {
      NodeList children = managedBean.getChildNodes();
      String beanName = null;
      String beanClass = null;
      String beanScope = null;
      ManagedBeanInfo.ListEntry listEntry = null;
      ManagedBeanInfo.MapEntry mapEntry = null;
      List managedProperties = null;
      List descriptions = null;
      int i = 0;

      Node n;
      for(int size = children.getLength(); i < size; ++i) {
         n = children.item(i);
         if (n.getNodeType() == 1) {
            if ("managed-bean-name".equals(n.getLocalName())) {
               beanName = this.getNodeText(n);
            } else if ("managed-bean-class".equals(n.getLocalName())) {
               beanClass = this.getNodeText(n);
            } else if ("managed-bean-scope".equals(n.getLocalName())) {
               beanScope = this.getNodeText(n);
               if (beanScope == null) {
                  beanScope = "request";
               }
            } else if ("list-entries".equals(n.getLocalName())) {
               listEntry = this.buildListEntry(n);
            } else if ("map-entries".equals(n.getLocalName())) {
               mapEntry = this.buildMapEntry(n);
            } else if ("managed-property".equals(n.getLocalName())) {
               if (managedProperties == null) {
                  managedProperties = new ArrayList(size);
               }

               managedProperties.add(n);
            } else if ("description".equals(n.getLocalName())) {
               if (descriptions == null) {
                  descriptions = new ArrayList(4);
               }

               descriptions.add(n);
            }
         }
      }

      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "Begin processing managed bean ''{0}''", beanName);
      }

      List properties = null;
      if (managedProperties != null && !managedProperties.isEmpty()) {
         properties = new ArrayList(managedProperties.size());
         Iterator i$ = managedProperties.iterator();

         while(i$.hasNext()) {
            n = (Node)i$.next();
            properties.add(this.buildManagedProperty(n));
         }
      }

      beanManager.register(new ManagedBeanInfo(beanName, beanClass, beanScope, mapEntry, listEntry, properties, this.getTextMap(descriptions)));
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "Completed processing bean ''{0}''", beanName);
      }

   }

   private ManagedBeanInfo.ListEntry buildListEntry(Node listEntry) {
      if (listEntry == null) {
         return null;
      } else {
         String valueClass = "java.lang.String";
         List values = null;
         NodeList children = listEntry.getChildNodes();
         int i = 0;

         for(int size = children.getLength(); i < size; ++i) {
            Node child = children.item(i);
            if (child.getNodeType() == 1) {
               if ("value-class".equals(child.getLocalName())) {
                  valueClass = this.getNodeText(child);
               } else if ("value".equals(child.getLocalName())) {
                  if (values == null) {
                     values = new ArrayList(size);
                  }

                  values.add(this.getNodeText(child));
               } else if ("null-value".equals(child.getLocalName())) {
                  if (values == null) {
                     values = new ArrayList(size);
                  }

                  values.add("null_value");
               }
            }
         }

         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Created ListEntry valueClass={1}, values={3}", valueClass, values != null && !values.isEmpty() ? values.toString() : "none"));
         }

         return new ManagedBeanInfo.ListEntry(valueClass, (List)(values == null ? TypedCollections.dynamicallyCastList(Collections.emptyList(), String.class) : values));
      }
   }

   private ManagedBeanInfo.MapEntry buildMapEntry(Node mapEntry) {
      if (mapEntry == null) {
         return null;
      } else {
         String valueClass = "java.lang.String";
         String keyClass = "java.lang.String";
         Map entries = null;
         NodeList children = mapEntry.getChildNodes();
         int i = 0;

         for(int size = children.getLength(); i < size; ++i) {
            Node child = children.item(i);
            if (child.getNodeType() == 1) {
               if ("value-class".equals(child.getLocalName())) {
                  valueClass = this.getNodeText(child);
               } else if ("key-class".equals(child.getLocalName())) {
                  keyClass = this.getNodeText(child);
               } else if ("map-entry".equals(child.getLocalName())) {
                  if (entries == null) {
                     entries = new LinkedHashMap(8, 1.0F);
                  }

                  NodeList c = child.getChildNodes();
                  String key = null;
                  String value = null;
                  int j = 0;

                  for(int jsize = c.getLength(); j < jsize; ++j) {
                     Node node = c.item(j);
                     if (node.getNodeType() == 1) {
                        if ("key".equals(node.getLocalName())) {
                           key = this.getNodeText(node);
                        } else if ("value".equals(node.getLocalName())) {
                           value = this.getNodeText(node);
                        } else if ("null-value".equals(node.getLocalName())) {
                           value = "null_value";
                        }
                     }
                  }

                  entries.put(key, value);
               }
            }
         }

         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Created MapEntry keyClass={0}, valueClass={1}, entries={3}", keyClass, valueClass, entries != null ? entries.toString() : "none"));
         }

         return new ManagedBeanInfo.MapEntry(keyClass, valueClass, entries);
      }
   }

   private ManagedBeanInfo.ManagedProperty buildManagedProperty(Node managedProperty) {
      if (managedProperty != null) {
         String propertyName = null;
         String propertyClass = null;
         String value = null;
         ManagedBeanInfo.MapEntry mapEntry = null;
         ManagedBeanInfo.ListEntry listEntry = null;
         NodeList children = managedProperty.getChildNodes();
         int i = 0;

         for(int size = children.getLength(); i < size; ++i) {
            Node child = children.item(i);
            if (child.getNodeType() == 1) {
               if ("property-name".equals(child.getLocalName())) {
                  propertyName = this.getNodeText(child);
               } else if ("property-class".equals(child.getLocalName())) {
                  propertyClass = this.getNodeText(child);
               } else if ("value".equals(child.getLocalName())) {
                  value = this.getNodeText(child);
               } else if ("null-value".equals(child.getLocalName())) {
                  value = "null_value";
               } else if ("list-entries".equals(child.getLocalName())) {
                  listEntry = this.buildListEntry(child);
               } else if ("map-entries".equals(child.getLocalName())) {
                  mapEntry = this.buildMapEntry(child);
               }
            }
         }

         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Adding ManagedProperty propertyName={0}, propertyClass={1}, propertyValue={2}, hasMapEntry={3}, hasListEntry={4}", propertyName, propertyClass != null ? propertyClass : "inferred", value, mapEntry != null, listEntry != null));
         }

         return new ManagedBeanInfo.ManagedProperty(propertyName, propertyClass, value, mapEntry, listEntry);
      } else {
         return null;
      }
   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
