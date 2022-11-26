package org.glassfish.hk2.xml.internal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.glassfish.hk2.utilities.general.GeneralUtilities;

public class ChildDataModel implements Serializable {
   private static final long serialVersionUID = 208423310453044595L;
   private static final Map TYPE_MAP = new HashMap();
   private final Object lock = new Object();
   private String childType;
   private String defaultAsString;
   private boolean isReference;
   private Format format;
   private String childListType;
   private AliasType aliasType;
   private String aliasOf;
   private boolean required;
   private String originalMethodName;
   private ClassLoader myLoader;
   private Class childTypeAsClass;
   private Class childListTypeAsClass;

   public ChildDataModel() {
   }

   public ChildDataModel(String childType, String childListType, String defaultAsString, boolean isReference, Format format, AliasType aliasType, String aliasOf, boolean required, String originalMethodName) {
      this.childType = childType;
      this.defaultAsString = defaultAsString;
      this.isReference = isReference;
      this.format = format;
      this.childListType = childListType;
      this.aliasType = aliasType;
      this.aliasOf = aliasOf;
      this.required = required;
      this.originalMethodName = originalMethodName;
   }

   public String getChildType() {
      return this.childType;
   }

   public String getChildListType() {
      return this.childListType;
   }

   public String getDefaultAsString() {
      return this.defaultAsString;
   }

   public boolean isReference() {
      return this.isReference;
   }

   public Format getFormat() {
      return this.format;
   }

   public AliasType getAliasType() {
      return this.aliasType;
   }

   public String getXmlAlias() {
      return this.aliasOf;
   }

   public void setLoader(ClassLoader myLoader) {
      synchronized(this.lock) {
         this.myLoader = myLoader;
      }
   }

   public Class getChildTypeAsClass() {
      synchronized(this.lock) {
         if (this.childTypeAsClass != null) {
            return this.childTypeAsClass;
         } else {
            this.childTypeAsClass = (Class)TYPE_MAP.get(this.childType);
            if (this.childTypeAsClass != null) {
               return this.childTypeAsClass;
            } else {
               this.childTypeAsClass = GeneralUtilities.loadClass(this.myLoader, this.childType);
               return this.childTypeAsClass;
            }
         }
      }
   }

   public Class getChildListTypeAsClass() {
      synchronized(this.lock) {
         if (this.childListType == null) {
            return null;
         } else if (this.childListTypeAsClass != null) {
            return this.childListTypeAsClass;
         } else {
            this.childListTypeAsClass = (Class)TYPE_MAP.get(this.childListType);
            if (this.childListTypeAsClass != null) {
               return this.childListTypeAsClass;
            } else {
               this.childListTypeAsClass = GeneralUtilities.loadClass(this.myLoader, this.childListType);
               return this.childListTypeAsClass;
            }
         }
      }
   }

   public boolean isRequired() {
      return this.required;
   }

   public String getOriginalMethodName() {
      return this.originalMethodName;
   }

   public String toString() {
      return "ChildDataModel(" + this.childType + "," + this.defaultAsString + "," + this.isReference + "," + this.childListType + "," + this.aliasType + "," + this.format + "," + this.required + "," + this.originalMethodName + "," + System.identityHashCode(this) + ")";
   }

   static {
      TYPE_MAP.put("char", Character.TYPE);
      TYPE_MAP.put("byte", Byte.TYPE);
      TYPE_MAP.put("short", Short.TYPE);
      TYPE_MAP.put("int", Integer.TYPE);
      TYPE_MAP.put("float", Float.TYPE);
      TYPE_MAP.put("long", Long.TYPE);
      TYPE_MAP.put("double", Double.TYPE);
      TYPE_MAP.put("boolean", Boolean.TYPE);
   }
}
