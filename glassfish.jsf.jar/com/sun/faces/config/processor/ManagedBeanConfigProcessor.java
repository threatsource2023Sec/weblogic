package com.sun.faces.config.processor;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.config.manager.documents.DocumentInfo;
import com.sun.faces.el.ELUtils;
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
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
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
   private static final String EAGER_ATTRIBUTE = "eager";
   private static final String DEFAULT_SCOPE = "request";

   public void process(ServletContext servletContext, FacesContext facesContext, DocumentInfo[] documentInfos) throws Exception {
      this.processAnnotations(facesContext, ManagedBean.class);
      BeanManager beanManager = ApplicationAssociate.getInstance(servletContext).getBeanManager();

      for(int i = 0; i < documentInfos.length; ++i) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Processing managed-bean elements for document: ''{0}''", documentInfos[i].getSourceURI()));
         }

         Document document = documentInfos[i].getDocument();
         String namespace = document.getDocumentElement().getNamespaceURI();
         NodeList managedBeans = document.getDocumentElement().getElementsByTagNameNS(namespace, "managed-bean");
         if (managedBeans != null && managedBeans.getLength() > 0) {
            int m = 0;

            for(int size = managedBeans.getLength(); m < size; ++m) {
               this.addManagedBean(beanManager, managedBeans.item(m));
            }
         }
      }

      beanManager.preProcessesBeans();
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

      Node managedProperty;
      for(int size = children.getLength(); i < size; ++i) {
         managedProperty = children.item(i);
         if (managedProperty.getNodeType() == 1) {
            switch (managedProperty.getLocalName()) {
               case "managed-bean-name":
                  beanName = this.getNodeText(managedProperty);
                  break;
               case "managed-bean-class":
                  beanClass = this.getNodeText(managedProperty);
                  break;
               case "managed-bean-scope":
                  beanScope = this.getNodeText(managedProperty);
                  if (beanScope == null) {
                     beanScope = "request";
                  }
                  break;
               case "list-entries":
                  listEntry = this.buildListEntry(managedProperty);
                  break;
               case "map-entries":
                  mapEntry = this.buildMapEntry(managedProperty);
                  break;
               case "managed-property":
                  if (managedProperties == null) {
                     managedProperties = new ArrayList(size);
                  }

                  managedProperties.add(managedProperty);
                  break;
               case "description":
                  if (descriptions == null) {
                     descriptions = new ArrayList(4);
                  }

                  descriptions.add(managedProperty);
            }
         }
      }

      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "Begin processing managed bean ''{0}''", beanName);
      }

      List properties = null;
      if (managedProperties != null && !managedProperties.isEmpty()) {
         properties = new ArrayList(managedProperties.size());
         Iterator var17 = managedProperties.iterator();

         while(var17.hasNext()) {
            managedProperty = (Node)var17.next();
            properties.add(this.buildManagedProperty(managedProperty));
         }
      }

      beanManager.register(new ManagedBeanInfo(beanName, beanClass, beanScope, this.isEager(managedBean, beanName, beanScope), mapEntry, listEntry, properties, this.getTextMap(descriptions)));
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
               switch (child.getLocalName()) {
                  case "value-class":
                     valueClass = this.getNodeText(child);
                     break;
                  case "value":
                     if (values == null) {
                        values = new ArrayList(size);
                     }

                     values.add(this.getNodeText(child));
                     break;
                  case "null-value":
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
               switch (child.getLocalName()) {
                  case "value-class":
                     valueClass = this.getNodeText(child);
                     break;
                  case "key-class":
                     keyClass = this.getNodeText(child);
                     break;
                  case "map-entry":
                     if (entries == null) {
                        entries = new LinkedHashMap(8, 1.0F);
                     }

                     NodeList c = child.getChildNodes();
                     String key = null;
                     String value = null;
                     int j = 0;
                     int jsize = c.getLength();

                     for(; j < jsize; ++j) {
                        Node node = c.item(j);
                        if (node.getNodeType() == 1) {
                           switch (node.getLocalName()) {
                              case "key":
                                 key = this.getNodeText(node);
                                 break;
                              case "value":
                                 value = this.getNodeText(node);
                                 break;
                              case "null-value":
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
               switch (child.getLocalName()) {
                  case "property-name":
                     propertyName = this.getNodeText(child);
                     break;
                  case "property-class":
                     propertyClass = this.getNodeText(child);
                     break;
                  case "value":
                     value = this.getNodeText(child);
                     break;
                  case "null-value":
                     value = "null_value";
                     break;
                  case "list-entries":
                     listEntry = this.buildListEntry(child);
                     break;
                  case "map-entries":
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

   private boolean isEager(Node managedBean, String beanName, String scope) {
      NamedNodeMap attributes = managedBean.getAttributes();
      Node eagerNode = attributes.getNamedItem("eager");
      boolean eager = false;
      if (eagerNode != null) {
         eager = Boolean.valueOf(this.getNodeText(eagerNode));
         if (eager && (scope == null || !ELUtils.Scope.APPLICATION.toString().equals(scope))) {
            if (LOGGER.isLoggable(Level.WARNING)) {
               LOGGER.log(Level.WARNING, "jsf.configuration.illegal.eager.bean", new Object[]{beanName, scope});
            }

            eager = false;
         }
      }

      return eager;
   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
