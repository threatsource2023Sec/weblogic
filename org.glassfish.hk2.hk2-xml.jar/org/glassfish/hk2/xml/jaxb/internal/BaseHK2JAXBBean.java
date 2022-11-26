package org.glassfish.hk2.xml.jaxb.internal;

import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.namespace.QName;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Customizer;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.configuration.hub.api.WriteableBeanDatabase;
import org.glassfish.hk2.configuration.hub.api.WriteableType;
import org.glassfish.hk2.utilities.general.GeneralUtilities;
import org.glassfish.hk2.utilities.reflection.ClassReflectionHelper;
import org.glassfish.hk2.utilities.reflection.Logger;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;
import org.glassfish.hk2.xml.api.XmlHk2ConfigurationBean;
import org.glassfish.hk2.xml.api.XmlRootHandle;
import org.glassfish.hk2.xml.internal.ChildType;
import org.glassfish.hk2.xml.internal.DynamicChangeInfo;
import org.glassfish.hk2.xml.internal.ModelImpl;
import org.glassfish.hk2.xml.internal.ModelPropertyType;
import org.glassfish.hk2.xml.internal.NamespaceBeanLikeMapImpl;
import org.glassfish.hk2.xml.internal.ParentedModel;
import org.glassfish.hk2.xml.internal.QNameUtilities;
import org.glassfish.hk2.xml.internal.Utilities;
import org.glassfish.hk2.xml.internal.XmlDynamicChange;
import org.glassfish.hk2.xml.internal.XmlRootHandleImpl;

@XmlTransient
public abstract class BaseHK2JAXBBean implements XmlHk2ConfigurationBean, Serializable {
   private static final long serialVersionUID = 8149986319033910297L;
   private static final boolean DEBUG_GETS_AND_SETS = (Boolean)AccessController.doPrivileged(new PrivilegedAction() {
      public Boolean run() {
         return Boolean.parseBoolean(System.getProperty("org.jvnet.hk2.properties.xmlservice.jaxb.getsandsets", "false"));
      }
   });
   private static final String EMPTY = "";
   public static final char XML_PATH_SEPARATOR = '/';
   private final NamespaceBeanLikeMap nBeanLikeMap = new NamespaceBeanLikeMapImpl();
   private final Map keyedChildrenCache = new ConcurrentHashMap();
   private XmlHk2ConfigurationBean parent;
   private String selfNamespace;
   private String selfXmlTag;
   private String instanceName;
   private String keyValue;
   private ClassReflectionHelper classReflectionHelper;
   private String xmlPath = "";
   private transient volatile DynamicChangeInfo changeControl;
   private transient volatile XmlRootHandleImpl root;
   private boolean active = false;
   private transient ActiveDescriptor selfDescriptor;
   private transient int addCost = -1;
   private final Map prefixToNamespaceMap = new HashMap();
   private final Map namespaceToPrefixMap = new HashMap();

   public void _setProperty(String propName, Object propValue) {
      this._setProperty("##default", propName, propValue);
   }

   public void _setProperty(String propNamespace, String propName, Object propValue) {
      this._setProperty(propNamespace, propName, propValue, true);
   }

   public void _setProperty(String propNamespace, String propName, Object propValue, boolean changeInHub) {
      this._setProperty(propNamespace, propName, propValue, changeInHub, false);
   }

   public void _setProperty(QName qName, Object propValue) {
      String namespace = QNameUtilities.getNamespace(qName);
      String propName = qName.getLocalPart();
      this._setProperty(namespace, propName, propValue);
   }

   public void _setProperty(QName qName, Object propValue, boolean changeInHub) {
      String namespace = QNameUtilities.getNamespace(qName);
      String propName = qName.getLocalPart();
      this._setProperty(namespace, propName, propValue, changeInHub);
   }

   public void _setProperty(String propNamespace, String propName, Object propValue, boolean changeInHub, boolean rawSet) {
      if (propNamespace != null && propName != null) {
         if (DEBUG_GETS_AND_SETS) {
            Logger.getLogger().debug("XmlService setting property " + propName + " to " + propValue + " in " + this + " rawSet=" + rawSet);
         }

         if (propValue != null && propValue instanceof List) {
            if (propValue instanceof ArrayList) {
               propValue = Collections.unmodifiableList((ArrayList)propValue);
            } else {
               propValue = Collections.unmodifiableList(new ArrayList((List)propValue));
            }
         }

         if (this.changeControl == null) {
            if (this.active) {
               synchronized(this) {
                  this.nBeanLikeMap.setValue(propNamespace, propName, propValue);
               }
            } else {
               this.nBeanLikeMap.setValue(propNamespace, propName, propValue);
            }
         } else {
            boolean doAdd = false;
            boolean doRemove = false;
            boolean doModify = false;
            boolean doDirectReplace = false;
            Object currentValue = null;
            String directCurrentKey = null;
            String directNewKey = null;
            if (!rawSet) {
               label1122: {
                  this.changeControl.getReadLock().lock();

                  try {
                     currentValue = this.nBeanLikeMap.getValue(propNamespace, propName);
                     if (currentValue != propValue) {
                        ModelImpl model = this._getModel();
                        ParentedModel childModel = model.getChild(propNamespace, propName);
                        if (childModel != null) {
                           if (ChildType.DIRECT.equals(childModel.getChildType())) {
                              QName keyQName;
                              String childkeyNamespace;
                              String childKeyProperty;
                              if (currentValue == null && propValue != null) {
                                 doAdd = true;
                                 keyQName = childModel.getChildModel().getKeyProperty();
                                 childkeyNamespace = keyQName == null ? null : keyQName.getNamespaceURI();
                                 childKeyProperty = keyQName == null ? null : keyQName.getLocalPart();
                                 if (childKeyProperty != null) {
                                    directNewKey = (String)((BaseHK2JAXBBean)propValue)._getProperty(childkeyNamespace, childKeyProperty);
                                 }
                              } else if (currentValue != null && propValue == null) {
                                 doRemove = true;
                              } else {
                                 keyQName = childModel.getChildModel().getKeyProperty();
                                 childkeyNamespace = keyQName == null ? null : keyQName.getNamespaceURI();
                                 childKeyProperty = keyQName == null ? null : keyQName.getLocalPart();
                                 if (childKeyProperty != null) {
                                    directCurrentKey = (String)((BaseHK2JAXBBean)currentValue)._getProperty(childkeyNamespace, childKeyProperty);
                                    directNewKey = (String)((BaseHK2JAXBBean)propValue)._getProperty(childkeyNamespace, childKeyProperty);
                                    if (GeneralUtilities.safeEquals(directCurrentKey, directNewKey)) {
                                       doModify = true;
                                    } else {
                                       doDirectReplace = true;
                                    }
                                 } else {
                                    doModify = true;
                                 }
                              }
                           } else {
                              doModify = true;
                           }
                        }
                        break label1122;
                     }
                  } finally {
                     this.changeControl.getReadLock().unlock();
                  }

                  return;
               }
            }

            if (doAdd) {
               this._doAdd(propNamespace, propName, propValue, directNewKey, -1);
               return;
            }

            if (doRemove) {
               this._doRemove(propNamespace, propName, (String)null, -1, currentValue);
               return;
            }

            if (doModify) {
               this._doModify(propNamespace, propName, currentValue, propValue);
               return;
            }

            if (doDirectReplace) {
               this.changeControl.getWriteLock().lock();

               try {
                  boolean success = false;
                  this.changeControl.startOrContinueChange(this);

                  try {
                     this._doRemove(propNamespace, propName, directCurrentKey, -1, currentValue, false);
                     this._doAdd(propNamespace, propName, propValue, directNewKey, -1, true);
                     success = true;
                  } finally {
                     this.changeControl.endOrDeferChange(success);
                  }
               } finally {
                  this.changeControl.getWriteLock().unlock();
               }

               return;
            }

            QName keyQName = this._getModel().getKeyProperty();
            String keyPropertyNamespace = keyQName == null ? null : QNameUtilities.getNamespace(keyQName);
            String keyProperty = keyQName == null ? null : keyQName.getLocalPart();
            if (keyProperty != null && propName.equals(keyProperty) && this.keyValue != null && keyPropertyNamespace != null && propNamespace.equals(keyPropertyNamespace)) {
               throw new IllegalArgumentException("The key property of a bean (" + keyProperty + ") may not be changed from " + this.keyValue + " to " + propValue);
            }

            this.changeControl.getWriteLock().lock();

            try {
               boolean success = false;
               this.changeControl.startOrContinueChange(this);

               try {
                  if (!rawSet) {
                     Object oValue = this.nBeanLikeMap.getValue(propNamespace, propName);
                     Utilities.invokeVetoableChangeListeners(this.changeControl, this, oValue, propValue, propName, this.classReflectionHelper);
                  }

                  if (changeInHub) {
                     this.changeInHubDirect(propNamespace, propName, propValue);
                  }

                  this.nBeanLikeMap.backup();
                  this.nBeanLikeMap.setValue(propNamespace, propName, propValue);
                  success = true;
               } finally {
                  this.changeControl.endOrDeferChange(success);
               }
            } finally {
               this.changeControl.getWriteLock().unlock();
            }
         }

      } else {
         throw new IllegalArgumentException("properyName or propertyNamespace may not be null");
      }
   }

   public void _setProperty(String propNamespace, String propName, byte propValue) {
      if (propName == null) {
         throw new IllegalArgumentException("properyName may not be null");
      } else {
         this._setProperty(propNamespace, propName, propValue);
      }
   }

   public void _setProperty(String propNamespace, String propName, boolean propValue) {
      if (propName == null) {
         throw new IllegalArgumentException("properyName may not be null");
      } else {
         this._setProperty(propNamespace, propName, propValue);
      }
   }

   public void _setProperty(String propNamespace, String propName, char propValue) {
      if (propName == null) {
         throw new IllegalArgumentException("properyName may not be null");
      } else {
         this._setProperty(propNamespace, propName, propValue);
      }
   }

   public void _setProperty(String propNamespace, String propName, short propValue) {
      if (propName == null) {
         throw new IllegalArgumentException("properyName may not be null");
      } else {
         this._setProperty(propNamespace, propName, propValue);
      }
   }

   public void _setProperty(String propNamespace, String propName, int propValue) {
      if (propName == null) {
         throw new IllegalArgumentException("properyName may not be null");
      } else {
         this._setProperty(propNamespace, propName, propValue);
      }
   }

   public void _setProperty(String propNamespace, String propName, float propValue) {
      if (propName == null) {
         throw new IllegalArgumentException("properyName may not be null");
      } else {
         this._setProperty(propNamespace, propName, propValue);
      }
   }

   public void _setProperty(String propNamespace, String propName, long propValue) {
      if (propName == null) {
         throw new IllegalArgumentException("properyName may not be null");
      } else {
         this._setProperty(propNamespace, propName, propValue);
      }
   }

   public void _setProperty(String propNamespace, String propName, double propValue) {
      if (propName == null) {
         throw new IllegalArgumentException("properyName may not be null");
      } else {
         this._setProperty(propNamespace, propName, propValue);
      }
   }

   private Object _getProperty(String propNamespace, String propName, Class expectedClass) {
      return this._getProperty(propNamespace, propName, expectedClass, (ParentedModel)null);
   }

   private Object _getProperty(String propNamespace, String propName, Class expectedClass, ParentedModel parentNode) {
      if (propNamespace == null) {
         throw new IllegalArgumentException("propNamespace must not be null");
      } else {
         boolean doDefaulting = this.active;
         boolean isSet;
         Object retVal;
         if (this.changeControl == null) {
            if (this.active) {
               synchronized(this) {
                  isSet = this.nBeanLikeMap.isSet(propNamespace, propName);
                  retVal = this.nBeanLikeMap.getValue(propNamespace, propName);
               }
            } else {
               isSet = this.nBeanLikeMap.isSet(propNamespace, propName);
               retVal = this.nBeanLikeMap.getValue(propNamespace, propName);
            }
         } else {
            this.changeControl.getReadLock().lock();

            try {
               doDefaulting = true;
               isSet = this.nBeanLikeMap.isSet(propNamespace, propName);
               retVal = this.nBeanLikeMap.getValue(propNamespace, propName);
            } finally {
               this.changeControl.getReadLock().unlock();
            }
         }

         if (doDefaulting && retVal == null && !isSet) {
            if (expectedClass != null) {
               retVal = Utilities.getDefaultValue(this._getModel().getDefaultChildValue(propNamespace, propName), expectedClass, this.prefixToNamespaceMap);
            } else if (parentNode != null) {
               switch (parentNode.getChildType()) {
                  case LIST:
                     retVal = Collections.EMPTY_LIST;
                     break;
                  case ARRAY:
                     Class cType = parentNode.getChildModel().getOriginalInterfaceAsClass();
                     retVal = Array.newInstance(cType, 0);
                  case DIRECT:
               }
            }
         }

         if (DEBUG_GETS_AND_SETS) {
            Logger.getLogger().debug("XmlService getting property " + propName + "=" + retVal + " in " + this);
         }

         return retVal;
      }
   }

   public Object _getProperty(QName qName) {
      String propNamespace = QNameUtilities.getNamespace(qName);
      String propName = qName.getLocalPart();
      return this._getProperty(propNamespace, propName);
   }

   public Object _getProperty(String propNamespace, String propName) {
      ModelImpl model = this._getModel();
      ModelPropertyType mpt = model.getModelPropertyType(propNamespace, propName);
      switch (mpt) {
         case FLAT_PROPERTY:
            return this._getProperty(propNamespace, propName, model.getNonChildType(propNamespace, propName));
         case TREE_ROOT:
            ParentedModel parent = model.getChild(propNamespace, propName);
            return this._getProperty(parent.getChildXmlNamespace(), parent.getChildXmlTag(), (Class)null, parent);
         case UNKNOWN:
         default:
            throw new AssertionError("Unknown type " + mpt + " for " + propName + " in " + this);
      }
   }

   public Object _getProperty(String propName) {
      return this._getProperty("##default", propName);
   }

   public boolean _getPropertyZ(String propNamespace, String propName) {
      return (Boolean)this._getProperty(propNamespace, propName, Boolean.TYPE);
   }

   public byte _getPropertyB(String propNamespace, String propName) {
      return (Byte)this._getProperty(propNamespace, propName, Byte.TYPE);
   }

   public char _getPropertyC(String propNamespace, String propName) {
      return (Character)this._getProperty(propNamespace, propName, Character.TYPE);
   }

   public short _getPropertyS(String propNamespace, String propName) {
      return (Short)this._getProperty(propNamespace, propName, Short.TYPE);
   }

   public int _getPropertyI(String propNamespace, String propName) {
      return (Integer)this._getProperty(propNamespace, propName, Integer.TYPE);
   }

   public float _getPropertyF(String propNamespace, String propName) {
      return (Float)this._getProperty(propNamespace, propName, Float.TYPE);
   }

   public long _getPropertyJ(String propNamespace, String propName) {
      return (Long)this._getProperty(propNamespace, propName, Long.TYPE);
   }

   public double _getPropertyD(String propNamespace, String propName) {
      return (Double)this._getProperty(propNamespace, propName, Double.TYPE);
   }

   private Object internalLookup(String propNamespace, String propName, String keyValue) {
      QName propertyQName = QNameUtilities.createQName(propNamespace, propName);
      Object retVal = null;
      Map byName = (Map)this.keyedChildrenCache.get(propertyQName);
      if (byName != null) {
         retVal = ((Map)byName).get(keyValue);
      }

      if (retVal != null) {
         return retVal;
      } else {
         Object prop = this._getProperty(propNamespace, propName);
         if (prop == null) {
            return null;
         } else {
            if (prop instanceof List) {
               Iterator var8 = ((List)prop).iterator();

               while(var8.hasNext()) {
                  BaseHK2JAXBBean child = (BaseHK2JAXBBean)var8.next();
                  if (GeneralUtilities.safeEquals(keyValue, child._getKeyValue())) {
                     if (byName == null) {
                        byName = new ConcurrentHashMap();
                        this.keyedChildrenCache.put(propertyQName, byName);
                     }

                     ((Map)byName).put(keyValue, child);
                     return child;
                  }
               }
            } else if (prop.getClass().isArray()) {
               Object[] var13 = (Object[])((Object[])prop);
               int var14 = var13.length;

               for(int var10 = 0; var10 < var14; ++var10) {
                  Object childRaw = var13[var10];
                  BaseHK2JAXBBean child = (BaseHK2JAXBBean)childRaw;
                  if (GeneralUtilities.safeEquals(keyValue, child._getKeyValue())) {
                     if (byName == null) {
                        byName = new ConcurrentHashMap();
                        this.keyedChildrenCache.put(propertyQName, byName);
                     }

                     ((Map)byName).put(keyValue, child);
                     return child;
                  }
               }
            }

            return null;
         }
      }
   }

   public Object _lookupChild(String propName, String keyValue) {
      return this._lookupChild("##default", propName, keyValue);
   }

   public Object _lookupChild(String propNamespace, String propName, String keyValue) {
      if (this.changeControl == null) {
         return this.internalLookup(propNamespace, propName, keyValue);
      } else {
         this.changeControl.getReadLock().lock();

         Object var4;
         try {
            var4 = this.internalLookup(propNamespace, propName, keyValue);
         } finally {
            this.changeControl.getReadLock().unlock();
         }

         return var4;
      }
   }

   public Object _doAdd(String propNamespace, String childProperty, Object rawChild, String childKey, int index) {
      return this._doAdd(propNamespace, childProperty, rawChild, childKey, index, true);
   }

   public Object _doAdd(String propNamespace, String childProperty, Object rawChild, String childKey, int index, boolean changeList) {
      if (this.changeControl == null) {
         return Utilities.internalAdd(this, propNamespace, childProperty, rawChild, childKey, index, (DynamicChangeInfo)null, XmlDynamicChange.EMPTY, new LinkedList(), changeList);
      } else {
         this.changeControl.getWriteLock().lock();

         try {
            Object oldValue = this.nBeanLikeMap.getValue(propNamespace, childProperty);
            LinkedList addedServices = new LinkedList();
            boolean success = false;
            XmlDynamicChange change = this.changeControl.startOrContinueChange(this);

            BaseHK2JAXBBean retVal;
            try {
               retVal = Utilities.internalAdd(this, propNamespace, childProperty, rawChild, childKey, index, this.changeControl, change, addedServices, changeList);
               Object newValue = this.nBeanLikeMap.getValue(propNamespace, childProperty);
               Utilities.invokeVetoableChangeListeners(this.changeControl, this, oldValue, newValue, childProperty, this.classReflectionHelper);
               success = true;
            } finally {
               this.changeControl.endOrDeferChange(success);
            }

            ServiceLocator locator = this.changeControl.getServiceLocator();
            Iterator var13 = addedServices.iterator();

            while(var13.hasNext()) {
               ActiveDescriptor added = (ActiveDescriptor)var13.next();
               locator.getServiceHandle(added).getService();
            }

            BaseHK2JAXBBean var23 = retVal;
            return var23;
         } finally {
            this.changeControl.getWriteLock().unlock();
         }
      }
   }

   private void _doModify(String propNamespace, String propName, Object currentValue, Object newValue) {
      if (this.root == null) {
         throw new IllegalStateException("A direct set will only work on a rooted bean");
      } else {
         this.changeControl.getWriteLock().lock();

         try {
            boolean success = false;
            XmlDynamicChange change = this.changeControl.startOrContinueChange(this);

            try {
               Utilities.internalModifyChild(this, propNamespace, propName, currentValue, newValue, this.root, this.changeControl, change);
               success = true;
            } finally {
               this.changeControl.endOrDeferChange(success);
            }
         } finally {
            this.changeControl.getWriteLock().unlock();
         }

      }
   }

   public Object _invokeCustomizedMethod(String methodName, Class[] params, Object[] values) {
      if (DEBUG_GETS_AND_SETS) {
         Logger.getLogger().debug("XmlService invoking customized method " + methodName + " with params " + Arrays.toString(params) + " adn values " + Arrays.toString(values));
      }

      Class tClass = this.getClass();
      Customizer customizer = (Customizer)tClass.getAnnotation(Customizer.class);
      if (customizer == null) {
         throw new RuntimeException("Method " + methodName + " was called on class " + tClass.getName() + " with no customizer, failing");
      } else {
         Class[] cClassArray = customizer.value();
         String[] cNameArray = customizer.name();
         if (cNameArray.length > 0 && cClassArray.length != cNameArray.length) {
            throw new RuntimeException("The @Customizer annotation must have the value and name arrays be of equal size.  The class array is of size " + cClassArray.length + " while the name array is of size " + cNameArray.length + " for class " + tClass.getName());
         } else {
            LinkedList errors = new LinkedList();

            for(int lcv = 0; lcv < cClassArray.length; ++lcv) {
               Class cClass = cClassArray[lcv];
               String cName = cNameArray.length == 0 ? null : cNameArray[lcv];
               Object cService = null;
               if (cName != null && !"".equals(cName)) {
                  cService = this.changeControl.getServiceLocator().getService(cClass, cName, new Annotation[0]);
               } else {
                  cService = this.changeControl.getServiceLocator().getService(cClass, new Annotation[0]);
               }

               if (cService == null) {
                  if (customizer.failWhenMethodNotFound()) {
                     errors.add(new RuntimeException("Method " + methodName + " was called on class " + tClass.getName() + " but service " + cClass.getName() + " with name " + cName + " was not found"));
                  }
               } else {
                  ModelImpl model = this._getModel();
                  Class topInterface = model == null ? null : model.getOriginalInterfaceAsClass();
                  Method cMethod = Utilities.findSuitableCustomizerMethod(cClass, methodName, params, topInterface);
                  if (cMethod != null) {
                     boolean useAlt = false;
                     if (cMethod.getParameterTypes().length == params.length + 1) {
                        useAlt = true;
                     }

                     if (useAlt) {
                        Object[] altValues = new Object[values.length + 1];
                        altValues[0] = this;

                        for(int lcv2 = 0; lcv2 < values.length; ++lcv2) {
                           altValues[lcv2 + 1] = values[lcv2];
                        }

                        values = altValues;
                     }

                     try {
                        return ReflectionHelper.invoke(cService, cMethod, values, false);
                     } catch (RuntimeException var19) {
                        throw var19;
                     } catch (Throwable var20) {
                        throw new RuntimeException(var20);
                     }
                  }

                  if (customizer.failWhenMethodNotFound()) {
                     errors.add(new RuntimeException("No customizer method with name " + methodName + " was found on customizer " + cClass.getName() + " with parameters " + Arrays.toString(params) + " for bean " + tClass.getName()));
                  }
               }
            }

            if (errors.isEmpty()) {
               return null;
            } else {
               throw new MultiException(errors);
            }
         }
      }
   }

   public int _invokeCustomizedMethodI(String methodName, Class[] params, Object[] values) {
      return (Integer)this._invokeCustomizedMethod(methodName, params, values);
   }

   public long _invokeCustomizedMethodJ(String methodName, Class[] params, Object[] values) {
      return (Long)this._invokeCustomizedMethod(methodName, params, values);
   }

   public boolean _invokeCustomizedMethodZ(String methodName, Class[] params, Object[] values) {
      return (Boolean)this._invokeCustomizedMethod(methodName, params, values);
   }

   public byte _invokeCustomizedMethodB(String methodName, Class[] params, Object[] values) {
      return (Byte)this._invokeCustomizedMethod(methodName, params, values);
   }

   public char _invokeCustomizedMethodC(String methodName, Class[] params, Object[] values) {
      return (Character)this._invokeCustomizedMethod(methodName, params, values);
   }

   public short _invokeCustomizedMethodS(String methodName, Class[] params, Object[] values) {
      return (Short)this._invokeCustomizedMethod(methodName, params, values);
   }

   public float _invokeCustomizedMethodF(String methodName, Class[] params, Object[] values) {
      return (Float)this._invokeCustomizedMethod(methodName, params, values);
   }

   public double _invokeCustomizedMethodD(String methodName, Class[] params, Object[] values) {
      return (Double)this._invokeCustomizedMethod(methodName, params, values);
   }

   public Object _doRemove(String propNamespace, String childProperty, String childKey, int index, Object child) {
      return this._doRemove(propNamespace, childProperty, childKey, index, child, true);
   }

   public Object _doRemove(String propNamespace, String childProperty, String childKey, int index, Object child, boolean changeList) {
      QName childPropQName = QNameUtilities.createQName(propNamespace, childProperty);
      if (this.changeControl == null) {
         Object retVal = Utilities.internalRemove(this, propNamespace, childProperty, childKey, index, child, (DynamicChangeInfo)null, XmlDynamicChange.EMPTY, changeList);
         if (retVal != null) {
            this.keyedChildrenCache.remove(childPropQName);
         }

         return retVal;
      } else {
         this.changeControl.getWriteLock().lock();

         BaseHK2JAXBBean var21;
         try {
            XmlDynamicChange xmlDynamicChange = this.changeControl.startOrContinueChange(this);
            boolean success = false;

            BaseHK2JAXBBean retVal;
            try {
               Object oldVal = this.nBeanLikeMap.getValue(propNamespace, childProperty);
               retVal = Utilities.internalRemove(this, propNamespace, childProperty, childKey, index, child, this.changeControl, xmlDynamicChange, changeList);
               Object newVal = this.nBeanLikeMap.getValue(propNamespace, childProperty);
               Utilities.invokeVetoableChangeListeners(this.changeControl, this, oldVal, newVal, childProperty, this.classReflectionHelper);
               success = true;
            } finally {
               this.changeControl.endOrDeferChange(success);
            }

            if (retVal != null) {
               this.keyedChildrenCache.remove(childPropQName);
            }

            var21 = retVal;
         } finally {
            this.changeControl.getWriteLock().unlock();
         }

         return var21;
      }
   }

   public boolean _doRemoveZ(String propNamespace, String childProperty, String childKey, int index, Object child) {
      Object retVal = this._doRemove(propNamespace, childProperty, childKey, index, child);
      return retVal != null;
   }

   public boolean _hasProperty(String propNamespace, String propName) {
      if (this.changeControl == null) {
         if (this.active) {
            synchronized(this) {
               return this.nBeanLikeMap.isSet(propNamespace, propName);
            }
         } else {
            return this.nBeanLikeMap.isSet(propNamespace, propName);
         }
      } else {
         this.changeControl.getReadLock().lock();

         boolean var3;
         try {
            var3 = this.nBeanLikeMap.isSet(propNamespace, propName);
         } finally {
            this.changeControl.getReadLock().unlock();
         }

         return var3;
      }
   }

   public Map _getBeanLikeMap() {
      if (this.changeControl == null) {
         if (this.active) {
            synchronized(this) {
               return Collections.unmodifiableMap(this.nBeanLikeMap.getBeanLikeMap(this.namespaceToPrefixMap));
            }
         } else {
            return Collections.unmodifiableMap(this.nBeanLikeMap.getBeanLikeMap(this.namespaceToPrefixMap));
         }
      } else {
         this.changeControl.getReadLock().lock();

         Map var1;
         try {
            var1 = Collections.unmodifiableMap(this.nBeanLikeMap.getBeanLikeMap(this.namespaceToPrefixMap));
         } finally {
            this.changeControl.getReadLock().unlock();
         }

         return var1;
      }
   }

   public Map _getQNameMap() {
      if (this.changeControl == null) {
         if (this.active) {
            synchronized(this) {
               return Collections.unmodifiableMap(this.nBeanLikeMap.getQNameMap());
            }
         } else {
            return Collections.unmodifiableMap(this.nBeanLikeMap.getQNameMap());
         }
      } else {
         this.changeControl.getReadLock().lock();

         Map var1;
         try {
            var1 = Collections.unmodifiableMap(this.nBeanLikeMap.getQNameMap());
         } finally {
            this.changeControl.getReadLock().unlock();
         }

         return var1;
      }
   }

   public XmlHk2ConfigurationBean _getParent() {
      return this.parent;
   }

   public void _setParent(XmlHk2ConfigurationBean parent) {
      this.parent = parent;
   }

   public void _setSelfXmlTag(String selfNamespace, String selfXmlTag) {
      this.selfNamespace = selfNamespace;
      this.selfXmlTag = selfXmlTag;
   }

   public String _getSelfNamespace() {
      return this.selfNamespace;
   }

   public String _getSelfXmlTag() {
      return this.selfXmlTag;
   }

   public String _getXmlPath() {
      return this.xmlPath;
   }

   public void _setInstanceName(String name) {
      this.instanceName = name;
   }

   public String _getInstanceName() {
      return this.instanceName;
   }

   public void _setKeyValue(String key) {
      this.keyValue = key;
   }

   public QName _getKeyPropertyName() {
      return this._getModel().getKeyProperty();
   }

   public void _setClassReflectionHelper(ClassReflectionHelper helper) {
      this.classReflectionHelper = helper;
   }

   public String _getKeyValue() {
      return this.keyValue;
   }

   private static String calculateXmlPath(BaseHK2JAXBBean leaf) {
      LinkedList stack;
      for(stack = new LinkedList(); leaf != null; leaf = (BaseHK2JAXBBean)leaf._getParent()) {
         stack.addFirst(leaf._getSelfXmlTag());
      }

      StringBuffer sb = new StringBuffer();
      Iterator var3 = stack.iterator();

      while(var3.hasNext()) {
         String component = (String)var3.next();
         sb.append('/' + component);
      }

      return sb.toString();
   }

   public void _setDynamicChangeInfo(XmlRootHandleImpl root, DynamicChangeInfo change) {
      this._setDynamicChangeInfo(root, change, true);
   }

   public void _setDynamicChangeInfo(XmlRootHandleImpl root, DynamicChangeInfo change, boolean doXmlPathCalculation) {
      if (doXmlPathCalculation) {
         this.xmlPath = calculateXmlPath(this);
      }

      this.changeControl = change;
      this.root = root;
      Utilities.calculateNamespaces(this, root, this.prefixToNamespaceMap);
      Iterator var4 = this.prefixToNamespaceMap.entrySet().iterator();

      while(var4.hasNext()) {
         Map.Entry entry = (Map.Entry)var4.next();
         this.namespaceToPrefixMap.put(entry.getValue(), entry.getKey());
      }

      if (this.changeControl != null) {
         this.active = true;
      }

   }

   public void _setActive() {
      this.active = true;
   }

   public Set _getChildrenXmlTags() {
      HashSet retVal = new HashSet(this._getModel().getKeyedChildren());
      retVal.addAll(this._getModel().getUnKeyedChildren());
      return retVal;
   }

   public void _shallowCopyFrom(BaseHK2JAXBBean copyMe, boolean copyReferences) {
      this.selfNamespace = copyMe.selfNamespace;
      this.selfXmlTag = copyMe.selfXmlTag;
      this.instanceName = copyMe.instanceName;
      this.keyValue = copyMe.keyValue;
      this.xmlPath = copyMe.xmlPath;
      this.nBeanLikeMap.shallowCopy(copyMe.nBeanLikeMap, copyMe._getModel(), copyReferences);
   }

   public boolean _changeInHub(String propNamespace, String propName, Object propValue, WriteableBeanDatabase wbd) {
      Object oldValue = this.nBeanLikeMap.getValue(propNamespace, propName);
      if (GeneralUtilities.safeEquals(oldValue, propValue)) {
         return false;
      } else {
         WriteableType wt = wbd.getWriteableType(this.xmlPath);
         HashMap modified = new HashMap(this.nBeanLikeMap.getBeanLikeMap(this.namespaceToPrefixMap));
         modified.put(propName, propValue);
         wt.modifyInstance(this.instanceName, modified, new PropertyChangeEvent[0]);
         return true;
      }
   }

   public boolean _changeInHub(List events, WriteableBeanDatabase wbd) {
      WriteableType wt = wbd.getWriteableType(this.xmlPath);
      HashMap modified = new HashMap(this.nBeanLikeMap.getBeanLikeMap(this.namespaceToPrefixMap));
      List effectiveChanges = new ArrayList(events.size());
      Iterator var6 = events.iterator();

      while(var6.hasNext()) {
         PropertyChangeEvent event = (PropertyChangeEvent)var6.next();
         String propName = event.getPropertyName();
         Object oldValue = event.getOldValue();
         Object newValue = event.getNewValue();
         if (!GeneralUtilities.safeEquals(oldValue, newValue)) {
            effectiveChanges.add(event);
            modified.put(propName, newValue);
         }
      }

      boolean madeAChange = !effectiveChanges.isEmpty();
      if (madeAChange) {
         wt.modifyInstance(this.instanceName, modified, (PropertyChangeEvent[])effectiveChanges.toArray(new PropertyChangeEvent[effectiveChanges.size()]));
      }

      return madeAChange;
   }

   private void changeInHubDirect(String propNamespace, String propName, Object propValue) {
      if (this.changeControl != null) {
         XmlDynamicChange xmlDynamicChange = this.changeControl.startOrContinueChange(this);
         boolean success = false;

         try {
            WriteableBeanDatabase wbd = xmlDynamicChange.getBeanDatabase();
            if (wbd == null) {
               success = true;
               return;
            }

            this._changeInHub(propNamespace, propName, propValue, wbd);
            success = true;
         } finally {
            this.changeControl.endOrDeferChange(success);
         }

      }
   }

   public DynamicChangeInfo _getChangeControl() {
      return this.changeControl;
   }

   public ClassReflectionHelper _getClassReflectionHelper() {
      return this.classReflectionHelper;
   }

   public void _setSelfDescriptor(ActiveDescriptor selfDescriptor) {
      this.selfDescriptor = selfDescriptor;
   }

   public ActiveDescriptor _getSelfDescriptor() {
      return this.selfDescriptor;
   }

   public void __activateChange() {
      this.nBeanLikeMap.restoreBackup(true);
      this.addCost = -1;
   }

   public void __rollbackChange() {
      this.nBeanLikeMap.restoreBackup(false);
   }

   public XmlRootHandle _getRoot() {
      return this.root;
   }

   public boolean _isSet(String propName) {
      return this._isSet("##default", propName);
   }

   public boolean _isSet(String propNamespace, String propName) {
      if (this.changeControl == null) {
         if (this.active) {
            synchronized(this) {
               return this.nBeanLikeMap.isSet(propNamespace, propName);
            }
         } else {
            return this.nBeanLikeMap.isSet(propNamespace, propName);
         }
      } else {
         this.changeControl.getReadLock().lock();

         boolean var3;
         try {
            var3 = this.nBeanLikeMap.isSet(propNamespace, propName);
         } finally {
            this.changeControl.getReadLock().unlock();
         }

         return var3;
      }
   }

   public void __setAddCost(int addCost) {
      this.addCost = addCost;
   }

   public int __getAddCost() {
      return this.addCost;
   }

   public void __fixAlias(String propNamespace, String propName, String baseName) {
      Object propNameValueRaw = this.nBeanLikeMap.getValue(propNamespace, propName);
      if (propNameValueRaw != null) {
         if (!(propNameValueRaw instanceof List)) {
            throw new AssertionError("Aliasing with XmlElements only works with List type.  Found " + propNameValueRaw);
         } else {
            List propNameValue = (List)propNameValueRaw;
            if (!propNameValue.isEmpty()) {
               Object baseNamePropertyRaw = this.nBeanLikeMap.getValue(propNamespace, baseName);
               if (baseNamePropertyRaw == null) {
                  baseNamePropertyRaw = new ArrayList(propNameValue.size());
                  this.nBeanLikeMap.setValue(propNamespace, baseName, baseNamePropertyRaw);
               }

               if (!(baseNamePropertyRaw instanceof List)) {
                  throw new AssertionError("Aliasing with XmlElements only works with List type.  Found " + baseNamePropertyRaw);
               } else {
                  List baseNameProperty = (List)baseNamePropertyRaw;
                  baseNameProperty.addAll(propNameValue);
               }
            }
         }
      }
   }

   public String toString() {
      return "BaseHK2JAXBBean(XmlPath=" + this.xmlPath + ",instanceName=" + this.instanceName + ",keyValue=" + this.keyValue + ",model=" + this._getModel() + "," + System.identityHashCode(this) + ")";
   }
}
