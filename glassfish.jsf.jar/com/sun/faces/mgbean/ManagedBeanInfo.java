package com.sun.faces.mgbean;

import com.sun.faces.el.ELUtils;
import java.util.List;
import java.util.Map;

public class ManagedBeanInfo {
   public static final String NULL_VALUE = "null_value";
   private String name;
   private String className;
   private String beanScope;
   boolean eager;
   private MapEntry mapEntry;
   private ListEntry listEntry;
   private List managedProperties;
   private Map descriptions;

   public ManagedBeanInfo(String name, String className, String beanScope, MapEntry mapEntry, ListEntry listEntry, List managedProperties, Map descriptions) {
      this(name, className, beanScope, false, mapEntry, listEntry, managedProperties, descriptions);
   }

   public ManagedBeanInfo(String name, String className, String beanScope, boolean eager, MapEntry mapEntry, ListEntry listEntry, List managedProperties, Map descriptions) {
      this.name = name;
      this.className = className;
      this.beanScope = beanScope;
      this.eager = eager;
      this.mapEntry = mapEntry;
      this.listEntry = listEntry;
      this.managedProperties = managedProperties;
      this.descriptions = descriptions;
      if (eager && !ELUtils.Scope.APPLICATION.toString().equals(beanScope)) {
         this.eager = false;
      }

   }

   public String getName() {
      return this.name;
   }

   public String getClassName() {
      return this.className;
   }

   public String getScope() {
      return this.beanScope;
   }

   public boolean isEager() {
      return this.eager;
   }

   public boolean hasMapEntry() {
      return this.mapEntry != null;
   }

   public MapEntry getMapEntry() {
      return this.mapEntry;
   }

   public boolean hasListEntry() {
      return this.listEntry != null;
   }

   public ListEntry getListEntry() {
      return this.listEntry;
   }

   public boolean hasManagedProperties() {
      return this.managedProperties != null;
   }

   public List getManagedProperties() {
      return this.managedProperties;
   }

   public Map getDescriptions() {
      return this.descriptions;
   }

   public ManagedBeanInfo clone(String name, String scope, boolean eager, ManagedBeanInfo source) {
      return new ManagedBeanInfo(name, source.className, scope, eager, source.mapEntry, source.listEntry, source.managedProperties, source.descriptions);
   }

   public static class ManagedProperty {
      private String propertyAlias;
      private String propertyName;
      private String propertyClass;
      private String propertyValue;
      private MapEntry mapEntry;
      private ListEntry listEntry;

      public ManagedProperty(String propertyName, String propertyClass, String propertyValue, MapEntry mapEntry, ListEntry listEntry) {
         this.propertyName = propertyName;
         this.propertyClass = propertyClass;
         this.propertyValue = propertyValue;
         this.mapEntry = mapEntry;
         this.listEntry = listEntry;
      }

      public ManagedProperty(String propertyAlias, String propertyName, String propertyClass, String propertyValue, MapEntry mapEntry, ListEntry listEntry) {
         this(propertyName, propertyClass, propertyValue, mapEntry, listEntry);
         this.propertyAlias = propertyAlias;
      }

      public String getPropertyAlias() {
         return this.propertyAlias;
      }

      public String getPropertyName() {
         return this.propertyName;
      }

      public String getPropertyClass() {
         return this.propertyClass;
      }

      public boolean hasPropertyValue() {
         return this.propertyValue != null;
      }

      public String getPropertyValue() {
         return this.propertyValue;
      }

      public boolean hasMapEntry() {
         return this.mapEntry != null;
      }

      public MapEntry getMapEntry() {
         return this.mapEntry;
      }

      public boolean hasListEntry() {
         return this.listEntry != null;
      }

      public ListEntry getListEntry() {
         return this.listEntry;
      }
   }

   public static class ListEntry {
      private String valueClass;
      private List values;

      public ListEntry(String valueClass, List values) {
         this.valueClass = valueClass;
         this.values = values;
      }

      public String getValueClass() {
         return this.valueClass;
      }

      public List getValues() {
         return this.values;
      }
   }

   public static class MapEntry {
      private String keyClass;
      private String valueClass;
      private Map entries;

      public MapEntry(String keyClass, String valueClass, Map entries) {
         this.keyClass = keyClass;
         this.valueClass = valueClass;
         this.entries = entries;
      }

      public String getKeyClass() {
         return this.keyClass;
      }

      public String getValueClass() {
         return this.valueClass;
      }

      public Map getEntries() {
         return this.entries;
      }
   }
}
