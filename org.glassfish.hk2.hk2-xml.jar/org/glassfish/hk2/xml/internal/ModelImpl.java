package org.glassfish.hk2.xml.internal;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.namespace.QName;
import org.glassfish.hk2.utilities.general.GeneralUtilities;
import org.glassfish.hk2.utilities.reflection.ClassReflectionHelper;
import org.glassfish.hk2.utilities.reflection.MethodWrapper;
import org.glassfish.hk2.xml.internal.alt.AltMethod;
import org.glassfish.hk2.xml.internal.alt.clazz.MethodAltMethodImpl;
import org.glassfish.hk2.xml.spi.Model;

public class ModelImpl implements Model {
   private static final long serialVersionUID = 752816761552710497L;
   private static final Object lock = new Object();
   private String originalInterface;
   private volatile Class originalInterfaceAsClass;
   private String translatedClass;
   private volatile Class translatedClassAsClass;
   private QName rootName;
   private final Map childrenByName = new LinkedHashMap();
   private final Map nonChildProperty = new LinkedHashMap();
   private final Map allChildren = new LinkedHashMap();
   private QName keyProperty;
   private Set unKeyedChildren = null;
   private Set keyedChildren = null;
   private transient JAUtilities jaUtilities = null;
   private ClassLoader myLoader;
   private Map keyToJavaNameMap = null;
   private Set allXmlWrappers;
   private String valuePropertyNamespace;
   private String valueProperty;
   private ChildDataModel valueData;

   public ModelImpl() {
   }

   public ModelImpl(String originalInterface, String translatedClass) {
      this.originalInterface = originalInterface;
      this.translatedClass = translatedClass;
   }

   public void setRootName(QName root) {
      this.rootName = root;
   }

   public void setRootName(String rootNamespace, String rootName) {
      this.rootName = QNameUtilities.createQName(rootNamespace, rootName);
   }

   public void setKeyProperty(QName qName) {
      String namespace = QNameUtilities.getNamespace(qName);
      String name = qName.getLocalPart();
      this.setKeyProperty(namespace, name);
   }

   public void setKeyProperty(String keyNamespace, String keyProperty) {
      this.keyProperty = QNameUtilities.createQName(keyNamespace, keyProperty);
   }

   public void addChild(String childInterface, String namespace, String xmlTag, String xmlAlias, ChildType childType, String givenDefault, AliasType aliased, String childWrapperTag, String adapter, boolean required, String originalMethodName) {
      ParentedModel pm = new ParentedModel(childInterface, namespace, xmlTag, xmlAlias, childType, givenDefault, aliased, childWrapperTag, adapter, required, originalMethodName);
      this.childrenByName.put(QNameUtilities.createQName(namespace, xmlTag), pm);
      this.allChildren.put(QNameUtilities.createQName(namespace, xmlTag), new ChildDescriptor(pm));
   }

   public void addNonChild(QName qName, String defaultValue, String childType, String childListType, boolean isReference, Format format, AliasType aliasType, String aliasOf, boolean required, String originalMethodName) {
      String namespace = QNameUtilities.getNamespace(qName);
      String xmlTag = qName.getLocalPart();
      this.addNonChild(namespace, xmlTag, defaultValue, childType, childListType, isReference, format, aliasType, aliasOf, required, originalMethodName);
   }

   public void addNonChild(String namespace, String xmlTag, String defaultValue, String childType, String childListType, boolean isReference, Format format, AliasType aliasType, String aliasOf, boolean required, String originalMethodName) {
      ChildDataModel cdm = new ChildDataModel(childType, childListType, defaultValue, isReference, format, aliasType, aliasOf, required, originalMethodName);
      this.nonChildProperty.put(QNameUtilities.createQName(namespace, xmlTag), cdm);
      this.allChildren.put(QNameUtilities.createQName(namespace, xmlTag), new ChildDescriptor(cdm));
      if (Format.VALUE.equals(format)) {
         this.valuePropertyNamespace = namespace;
         this.valueProperty = xmlTag;
         this.valueData = cdm;
      }

   }

   public String getOriginalInterface() {
      return this.originalInterface;
   }

   public String getTranslatedClass() {
      return this.translatedClass;
   }

   public QName getRootName() {
      return this.rootName;
   }

   public QName getKeyProperty() {
      return this.keyProperty;
   }

   public Map getChildrenByName() {
      return this.childrenByName;
   }

   public Map getNonChildProperties() {
      return this.nonChildProperty;
   }

   public Map getAllChildrenDescriptors() {
      return this.allChildren;
   }

   public String getValuePropertyNamespace() {
      return this.valuePropertyNamespace;
   }

   public String getValueProperty() {
      return this.valueProperty;
   }

   public ChildDataModel getValueData() {
      return this.valueData;
   }

   public Set getAllXmlWrappers() {
      synchronized(lock) {
         if (this.allXmlWrappers != null) {
            return this.allXmlWrappers;
         } else {
            this.allXmlWrappers = new LinkedHashSet();
            Iterator var2 = this.childrenByName.values().iterator();

            while(var2.hasNext()) {
               ParentedModel pm = (ParentedModel)var2.next();
               if (pm.getXmlWrapperTag() != null) {
                  this.allXmlWrappers.add(pm.getXmlWrapperTag());
               }
            }

            return this.allXmlWrappers;
         }
      }
   }

   public ChildDescriptor getChildDescriptor(QName xmlTag) {
      return (ChildDescriptor)this.allChildren.get(xmlTag);
   }

   public Set getUnKeyedChildren() {
      synchronized(lock) {
         if (this.unKeyedChildren != null) {
            return this.unKeyedChildren;
         } else {
            this.unKeyedChildren = new HashSet();
            Iterator var2 = this.childrenByName.entrySet().iterator();

            while(var2.hasNext()) {
               Map.Entry entry = (Map.Entry)var2.next();
               if (((ParentedModel)entry.getValue()).getChildModel().getKeyProperty() == null) {
                  this.unKeyedChildren.add(entry.getKey());
               }
            }

            return this.unKeyedChildren;
         }
      }
   }

   public Set getKeyedChildren() {
      synchronized(lock) {
         if (this.keyedChildren != null) {
            return this.keyedChildren;
         } else {
            this.keyedChildren = new HashSet();
            Iterator var2 = this.childrenByName.entrySet().iterator();

            while(var2.hasNext()) {
               Map.Entry entry = (Map.Entry)var2.next();
               if (((ParentedModel)entry.getValue()).getChildModel().getKeyProperty() != null) {
                  this.keyedChildren.add(entry.getKey());
               }
            }

            return this.keyedChildren;
         }
      }
   }

   public void setJAUtilities(JAUtilities jaUtilities, ClassLoader myLoader) {
      synchronized(lock) {
         if (this.jaUtilities == null) {
            this.jaUtilities = jaUtilities;
            this.myLoader = myLoader;
            Iterator var4 = this.childrenByName.values().iterator();

            while(var4.hasNext()) {
               ParentedModel pm = (ParentedModel)var4.next();
               pm.setRuntimeInformation(jaUtilities, myLoader);
            }

            var4 = this.nonChildProperty.values().iterator();

            while(var4.hasNext()) {
               ChildDataModel cdm = (ChildDataModel)var4.next();
               cdm.setLoader(myLoader);
            }

         }
      }
   }

   public String getDefaultChildValue(String propNamespace, String propName) {
      QName propQName = QNameUtilities.createQName(propNamespace, propName);
      synchronized(lock) {
         ChildDataModel cd = (ChildDataModel)this.nonChildProperty.get(propQName);
         return cd == null ? null : cd.getDefaultAsString();
      }
   }

   public ModelPropertyType getModelPropertyType(String propNamespace, String propName) {
      QName propQName = QNameUtilities.createQName(propNamespace, propName);
      synchronized(lock) {
         if (this.nonChildProperty.containsKey(propQName)) {
            return ModelPropertyType.FLAT_PROPERTY;
         } else {
            return this.childrenByName.containsKey(propQName) ? ModelPropertyType.TREE_ROOT : ModelPropertyType.UNKNOWN;
         }
      }
   }

   public Class getNonChildType(String propNamespace, String propName) {
      QName propQName = QNameUtilities.createQName(propNamespace, propName);
      synchronized(lock) {
         ChildDataModel cd = (ChildDataModel)this.nonChildProperty.get(propQName);
         return cd == null ? null : cd.getChildTypeAsClass();
      }
   }

   public ParentedModel getChild(String propNamespace, String propName) {
      QName propQName = QNameUtilities.createQName(propNamespace, propName);
      synchronized(lock) {
         return (ParentedModel)this.childrenByName.get(propQName);
      }
   }

   public Class getOriginalInterfaceAsClass() {
      if (this.originalInterfaceAsClass != null) {
         return this.originalInterfaceAsClass;
      } else {
         synchronized(lock) {
            if (this.originalInterfaceAsClass != null) {
               return this.originalInterfaceAsClass;
            } else {
               this.originalInterfaceAsClass = GeneralUtilities.loadClass(this.myLoader, this.originalInterface);
               return this.originalInterfaceAsClass;
            }
         }
      }
   }

   public Class getProxyAsClass() {
      if (this.translatedClassAsClass != null) {
         return this.translatedClassAsClass;
      } else {
         synchronized(lock) {
            if (this.translatedClassAsClass != null) {
               return this.translatedClassAsClass;
            } else {
               this.translatedClassAsClass = GeneralUtilities.loadClass(this.myLoader, this.translatedClass);
               return this.translatedClassAsClass;
            }
         }
      }
   }

   public Collection getAllChildren() {
      synchronized(lock) {
         return Collections.unmodifiableCollection(this.childrenByName.values());
      }
   }

   public Map getChildrenProperties() {
      synchronized(lock) {
         return Collections.unmodifiableMap(this.childrenByName);
      }
   }

   public Map getAllAttributeChildren() {
      Map retVal = new LinkedHashMap();
      Iterator var2 = this.nonChildProperty.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry candidate = (Map.Entry)var2.next();
         QName xmlKey = (QName)candidate.getKey();
         ChildDataModel childDataModel = (ChildDataModel)candidate.getValue();
         if (Format.ATTRIBUTE.equals(childDataModel.getFormat())) {
            retVal.put(xmlKey, childDataModel);
         }
      }

      return retVal;
   }

   public Map getAllElementChildren() {
      Map retVal = new LinkedHashMap();
      Iterator var2 = this.allChildren.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry candidate = (Map.Entry)var2.next();
         QName xmlKey = (QName)candidate.getKey();
         ChildDescriptor childDescriptor = (ChildDescriptor)candidate.getValue();
         if (childDescriptor.getParentedModel() != null) {
            retVal.put(xmlKey, childDescriptor);
         } else {
            ChildDataModel childDataModel = childDescriptor.getChildDataModel();
            if (Format.ELEMENT.equals(childDataModel.getFormat())) {
               retVal.put(xmlKey, childDescriptor);
            }
         }
      }

      return retVal;
   }

   public synchronized String getJavaNameFromKey(String key, ClassReflectionHelper reflectionHelper) {
      if (this.keyToJavaNameMap == null) {
         this.keyToJavaNameMap = new LinkedHashMap();
      }

      String result = (String)this.keyToJavaNameMap.get(key);
      if (result != null) {
         return result;
      } else if (reflectionHelper == null) {
         return null;
      } else {
         Class originalInterface = this.getOriginalInterfaceAsClass();
         Iterator var5 = reflectionHelper.getAllMethods(originalInterface).iterator();

         String javaName;
         String keyName;
         do {
            Method m;
            String xmlName;
            while(true) {
               if (!var5.hasNext()) {
                  return null;
               }

               MethodWrapper wrapper = (MethodWrapper)var5.next();
               m = wrapper.getMethod();
               XmlElement element = (XmlElement)m.getAnnotation(XmlElement.class);
               if (element == null) {
                  XmlAttribute attribute = (XmlAttribute)m.getAnnotation(XmlAttribute.class);
                  if (attribute == null) {
                     continue;
                  }

                  xmlName = attribute.name();
                  break;
               }

               xmlName = element.name();
               break;
            }

            javaName = getJavaNameFromGetterOrSetter(m, reflectionHelper);
            if ("##default".equals(xmlName)) {
               keyName = javaName;
            } else {
               keyName = xmlName;
            }
         } while(!key.equals(keyName));

         this.keyToJavaNameMap.put(key, javaName);
         return javaName;
      }
   }

   private static String getJavaNameFromGetterOrSetter(Method m, ClassReflectionHelper reflectionHelper) {
      AltMethod alt = new MethodAltMethodImpl(m, reflectionHelper);
      String retVal = Utilities.isGetter(alt);
      return retVal != null ? retVal : Utilities.isSetter(alt);
   }

   public int hashCode() {
      return this.translatedClass.hashCode();
   }

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else {
         return !(o instanceof ModelImpl) ? false : this.translatedClass.equals(((ModelImpl)o).getTranslatedClass());
      }
   }

   public String toString() {
      return "ModelImpl(interface=" + this.originalInterface + ",class=" + this.translatedClass + ",root=" + this.rootName + ",keyProperty=" + this.keyProperty + ",valuePropertyNamespace=" + this.valuePropertyNamespace + ",valueProperty=" + this.valueProperty + "," + System.identityHashCode(this) + ")";
   }
}
