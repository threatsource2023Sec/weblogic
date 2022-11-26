package org.glassfish.hk2.xml.internal;

import java.beans.Introspector;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Customize;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.configuration.hub.api.WriteableBeanDatabase;
import org.glassfish.hk2.configuration.hub.api.WriteableType;
import org.glassfish.hk2.utilities.AbstractActiveDescriptor;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.general.GeneralUtilities;
import org.glassfish.hk2.utilities.reflection.ClassReflectionHelper;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;
import org.glassfish.hk2.xml.api.XmlRootHandle;
import org.glassfish.hk2.xml.api.annotations.XmlIdentifier;
import org.glassfish.hk2.xml.internal.alt.AdapterInformation;
import org.glassfish.hk2.xml.internal.alt.AltAnnotation;
import org.glassfish.hk2.xml.internal.alt.AltClass;
import org.glassfish.hk2.xml.internal.alt.AltMethod;
import org.glassfish.hk2.xml.internal.alt.MethodInformationI;
import org.glassfish.hk2.xml.internal.alt.clazz.ClassAltClassImpl;
import org.glassfish.hk2.xml.internal.alt.papi.ArrayTypeAltClassImpl;
import org.glassfish.hk2.xml.internal.alt.papi.TypeElementAltClassImpl;
import org.glassfish.hk2.xml.jaxb.internal.BaseHK2JAXBBean;

public class Utilities {
   public static final char INSTANCE_PATH_SEPARATOR = '.';
   private static final char XML_PATH_SEPARATOR = '/';
   private static final String CLASS_ADD_ON_NAME = "_Hk2_Jaxb";
   private static final String NOT_UNIQUE_UNIQUE_ID = "not-unique";
   private static final String EMPTY_STRING = "";
   private static final Boolean DEFAULT_BOOLEAN;
   private static final Byte DEFAULT_BYTE;
   private static final Character DEFAULT_CHARACTER;
   private static final Short DEFAULT_SHORT;
   private static final Integer DEFAULT_INTEGER;
   private static final Long DEFAULT_LONG;
   private static final Float DEFAULT_FLOAT;
   private static final Double DEFAULT_DOUBLE;
   private static final String ENUM_FROM_VALUE_METHOD_NAME = "fromValue";
   private static final Class[] ENUM_FROM_VALUE_PARAM_TYPES;

   static String convertXmlRootElementName(XmlRootElement root, Class clazz) {
      if (!"##default".equals(root.name())) {
         return root.name();
      } else {
         String simpleName = clazz.getSimpleName();
         char[] asChars = simpleName.toCharArray();
         StringBuffer sb = new StringBuffer();
         boolean firstChar = true;
         boolean lastCharWasCapital = false;
         char[] var7 = asChars;
         int var8 = asChars.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            char asChar = var7[var9];
            if (firstChar) {
               firstChar = false;
               if (Character.isUpperCase(asChar)) {
                  lastCharWasCapital = true;
                  sb.append(Character.toLowerCase(asChar));
               } else {
                  lastCharWasCapital = false;
                  sb.append(asChar);
               }
            } else if (Character.isUpperCase(asChar)) {
               if (!lastCharWasCapital) {
                  sb.append('-');
               }

               sb.append(Character.toLowerCase(asChar));
               lastCharWasCapital = true;
            } else {
               sb.append(asChar);
               lastCharWasCapital = false;
            }
         }

         return sb.toString();
      }
   }

   public static BaseHK2JAXBBean createBean(Class implClass) {
      try {
         Constructor noArgsConstructor = implClass.getConstructor();
         return (BaseHK2JAXBBean)ReflectionHelper.makeMe(noArgsConstructor, new Object[0], false);
      } catch (RuntimeException var2) {
         throw var2;
      } catch (Throwable var3) {
         throw new RuntimeException(var3);
      }
   }

   private static String getKeySegment(BaseHK2JAXBBean bean) {
      String selfXmlTag = bean._getSelfXmlTag();
      String baseKeySegment = bean._getKeyValue();
      if (baseKeySegment == null) {
         baseKeySegment = selfXmlTag;
         if (selfXmlTag != null) {
            baseKeySegment = selfXmlTag.replace('/', '.');
         }
      } else {
         String xmlWrapperTag = null;
         if (selfXmlTag != null) {
            int pathSep = selfXmlTag.indexOf(47);
            if (pathSep > 0) {
               xmlWrapperTag = selfXmlTag.substring(0, pathSep);
            }
         }

         if (xmlWrapperTag != null) {
            baseKeySegment = xmlWrapperTag + '.' + baseKeySegment;
         }
      }

      return baseKeySegment;
   }

   public static String createInstanceName(BaseHK2JAXBBean bean) {
      return bean._getParent() == null ? getKeySegment(bean) : createInstanceName((BaseHK2JAXBBean)bean._getParent()) + '.' + getKeySegment(bean);
   }

   public static ActiveDescriptor advertise(WriteableBeanDatabase wbd, DynamicConfiguration config, BaseHK2JAXBBean bean) {
      ActiveDescriptor selfDescriptor = null;
      if (config != null) {
         AbstractActiveDescriptor cDesc = BuilderHelper.createConstantDescriptor(bean);
         cDesc.setScopeAsAnnotation(ServiceLocatorUtilities.getSingletonAnnotation());
         if (bean._getKeyValue() != null) {
            cDesc.setName(bean._getKeyValue());
         }

         selfDescriptor = config.addActiveDescriptor(cDesc);
         bean._setSelfDescriptor(selfDescriptor);
      }

      if (wbd != null) {
         WriteableType wt = wbd.findOrAddWriteableType(bean._getXmlPath());
         wt.addInstance(bean._getInstanceName(), bean._getBeanLikeMap(), bean);
      }

      return selfDescriptor;
   }

   public static String convertToSetter(String getterName) {
      if (getterName.startsWith("is")) {
         return "set" + getterName.substring("is".length());
      } else if (!getterName.startsWith("get")) {
         throw new IllegalArgumentException("Unknown getter format: " + getterName);
      } else {
         return "set" + getterName.substring("get".length());
      }
   }

   public static String convertNameToString(Name name) {
      return name == null ? null : name.toString();
   }

   public static AltClass convertTypeMirror(TypeMirror typeMirror, ProcessingEnvironment processingEnv) {
      if (TypeKind.VOID.equals(typeMirror.getKind())) {
         return ClassAltClassImpl.VOID;
      } else if (TypeKind.BOOLEAN.equals(typeMirror.getKind())) {
         return ClassAltClassImpl.BOOLEAN;
      } else if (TypeKind.INT.equals(typeMirror.getKind())) {
         return ClassAltClassImpl.INT;
      } else if (TypeKind.LONG.equals(typeMirror.getKind())) {
         return ClassAltClassImpl.LONG;
      } else if (TypeKind.BYTE.equals(typeMirror.getKind())) {
         return ClassAltClassImpl.BYTE;
      } else if (TypeKind.CHAR.equals(typeMirror.getKind())) {
         return ClassAltClassImpl.CHAR;
      } else if (TypeKind.SHORT.equals(typeMirror.getKind())) {
         return ClassAltClassImpl.SHORT;
      } else if (TypeKind.FLOAT.equals(typeMirror.getKind())) {
         return ClassAltClassImpl.FLOAT;
      } else if (TypeKind.DOUBLE.equals(typeMirror.getKind())) {
         return ClassAltClassImpl.DOUBLE;
      } else if (TypeKind.DECLARED.equals(typeMirror.getKind())) {
         DeclaredType dt = (DeclaredType)typeMirror;
         TypeElement typeElement = (TypeElement)dt.asElement();
         TypeElementAltClassImpl addMe = new TypeElementAltClassImpl(typeElement, processingEnv);
         return addMe;
      } else if (TypeKind.ARRAY.equals(typeMirror.getKind())) {
         ArrayType at = (ArrayType)typeMirror;
         return new ArrayTypeAltClassImpl(at, processingEnv);
      } else if (TypeKind.TYPEVAR.equals(typeMirror.getKind())) {
         TypeVariable tv = (TypeVariable)typeMirror;
         TypeMirror upperBound = tv.getUpperBound();
         return upperBound != null && TypeKind.DECLARED.equals(upperBound.getKind()) ? convertTypeMirror(upperBound, processingEnv) : ClassAltClassImpl.OBJECT;
      } else {
         throw new AssertionError("Unknown parameter kind: " + typeMirror.getKind());
      }
   }

   public static void internalModifyChild(BaseHK2JAXBBean myParent, String childPropertyNamespace, String childProperty, Object currentValue, Object newValue, XmlRootHandleImpl root, DynamicChangeInfo changeInformation, XmlDynamicChange xmlDynamicChange) {
      ParentedModel childNode = myParent._getModel().getChild(childPropertyNamespace, childProperty);
      if (childNode == null) {
         throw new IllegalArgumentException("There is no child with xmlTag " + childProperty + " of " + myParent);
      } else {
         Differences differences = new Differences();
         String xmlTag = childNode.getChildXmlTag();
         BaseHK2JAXBBean aBean;
         XmlRootHandle aRoot;
         if (ChildType.ARRAY.equals(childNode.getChildType())) {
            int newLength = Array.getLength(newValue);
            Object newArrayWithCopies = Array.newInstance(childNode.getChildModel().getOriginalInterfaceAsClass(), newLength);

            for(int lcv = 0; lcv < newLength; ++lcv) {
               aBean = (BaseHK2JAXBBean)Array.get(newValue, lcv);
               if (aBean == null) {
                  throw new IllegalArgumentException("The new array may not have null elements, the element at index " + lcv + " is null");
               }

               aRoot = aBean._getRoot();
               if (aRoot != null) {
                  if (!aRoot.equals(root)) {
                     throw new IllegalArgumentException("Can not have a bean from a different tree added with set method.  The element at index " + lcv + " is from tree " + aRoot);
                  }

                  aBean = createUnrootedBeanTreeCopy(aBean);
               }

               Array.set(newArrayWithCopies, lcv, aBean);
            }

            getArrayDifferences(childNode, currentValue, newArrayWithCopies, differences, xmlTag, myParent);
         } else if (ChildType.LIST.equals(childNode.getChildType())) {
            List newValueAsList = (List)newValue;
            List newListWithCopies = new ArrayList(newValueAsList.size());

            for(Iterator var20 = newValueAsList.iterator(); var20.hasNext(); newListWithCopies.add(aBean)) {
               aBean = (BaseHK2JAXBBean)var20.next();
               if (aBean == null) {
                  throw new IllegalArgumentException("The new list may not have null elements");
               }

               aRoot = aBean._getRoot();
               if (aRoot != null) {
                  if (!aRoot.equals(root)) {
                     throw new IllegalArgumentException("Can not have a bean from a different tree added with set method");
                  }

                  aBean = createUnrootedBeanTreeCopy(aBean);
               }
            }

            getListDifferences(childNode, currentValue, newListWithCopies, differences, xmlTag, myParent);
         } else {
            if (!ChildType.DIRECT.equals(childNode.getChildType())) {
               throw new AssertionError("Unknown child type: " + childNode.getChildType());
            }

            BaseHK2JAXBBean aBean = (BaseHK2JAXBBean)newValue;
            XmlRootHandle aRoot = aBean._getRoot();
            if (aRoot != null) {
               if (!aRoot.equals(root)) {
                  throw new IllegalArgumentException("Can not have a bean from a different tree added with set method (direct child)");
               }

               aBean = createUnrootedBeanTreeCopy(aBean);
            }

            getAllDifferences((BaseHK2JAXBBean)currentValue, aBean, differences);
         }

         if (!differences.getDifferences().isEmpty()) {
            applyDiff(differences, changeInformation);
         }

      }
   }

   public static BaseHK2JAXBBean internalAdd(BaseHK2JAXBBean myParent, String childPropertyNamespace, String childProperty, Object rawChild, String childKey, int index, DynamicChangeInfo changeInformation, XmlDynamicChange xmlDynamicChange, List addedServices, boolean changeList) {
      if (index < -1) {
         throw new IllegalArgumentException("Unknown index " + index);
      } else if (childKey != null && myParent._lookupChild(childPropertyNamespace, childProperty, childKey) != null) {
         throw new IllegalStateException("There is already a child with name " + childKey + " for child " + childProperty);
      } else if (rawChild != null && !(rawChild instanceof BaseHK2JAXBBean)) {
         throw new IllegalArgumentException("The child added must be from XmlService.createBean");
      } else {
         ParentedModel childNode = myParent._getModel().getChild(childPropertyNamespace, childProperty);
         if (childNode == null) {
            throw new IllegalArgumentException("There is no child with xmlTag " + childProperty + " of " + myParent);
         } else {
            Object allMyChildren = myParent._getProperty(childPropertyNamespace, childProperty);
            List multiChildren = null;
            if (!ChildType.DIRECT.equals(childNode.getChildType())) {
               if (allMyChildren == null) {
                  multiChildren = new ArrayList(10);
               } else if (ChildType.LIST.equals(childNode.getChildType())) {
                  multiChildren = new ArrayList((List)allMyChildren);
               } else {
                  multiChildren = new ArrayList(Arrays.asList((Object[])((Object[])allMyChildren)));
               }

               if (changeList && index > multiChildren.size()) {
                  throw new IllegalArgumentException("The index given to add child " + childProperty + " to " + myParent + " is not in range (" + index + "," + multiChildren.size() + ")");
               }

               if (index == -1) {
                  index = multiChildren.size();
               }
            }

            BaseHK2JAXBBean child = createBean(childNode.getChildModel().getProxyAsClass());
            child._setClassReflectionHelper(myParent._getClassReflectionHelper());
            if (rawChild != null) {
               BaseHK2JAXBBean childToCopy = (BaseHK2JAXBBean)rawChild;
               Iterator var15 = childToCopy._getModel().getNonChildProperties().keySet().iterator();

               while(var15.hasNext()) {
                  QName nonChildProperty = (QName)var15.next();
                  String nonChildPropNamespace = QNameUtilities.getNamespace(nonChildProperty);
                  String nonChildPropKey = nonChildProperty.getLocalPart();
                  Object value = childToCopy._getProperty(nonChildPropNamespace, nonChildPropKey);
                  if (value != null) {
                     child._setProperty(nonChildPropNamespace, nonChildPropKey, value, false, true);
                  }
               }
            }

            if (childKey == null) {
               if (childNode.getChildModel().getKeyProperty() != null) {
                  if (rawChild != null) {
                     String keyPropNamespace = QNameUtilities.getNamespace(childNode.getChildModel().getKeyProperty());
                     String keyPropKey = childNode.getChildModel().getKeyProperty().getLocalPart();
                     childKey = (String)child._getProperty(keyPropNamespace, keyPropKey);
                  }

                  if (childKey == null) {
                     throw new IllegalArgumentException("Attempted to create child with xmlTag " + childProperty + " with no key field in " + myParent);
                  }

                  child._setKeyValue(childKey);
               } else if (!ChildType.DIRECT.equals(childNode.getChildType())) {
                  if (myParent._getChangeControl() == null) {
                     childKey = "not-unique";
                     child._setKeyValue("not-unique");
                  } else {
                     childKey = myParent._getChangeControl().getGeneratedId();
                     child._setKeyValue(childKey);
                  }
               }
            } else {
               if (childNode.getChildModel().getKeyProperty() == null) {
                  throw new IllegalArgumentException("Attempted to add an unkeyed child with key " + childKey + " in " + myParent);
               }

               QName keyProp = childNode.getChildModel().getKeyProperty();
               child._setProperty(QNameUtilities.getNamespace(keyProp), keyProp.getLocalPart(), childKey, false, true);
               child._setKeyValue(childKey);
            }

            child._setParent(myParent);
            child._setSelfXmlTag(childNode.getChildXmlNamespace(), constructXmlTag(childNode.getXmlWrapperTag(), childNode.getChildXmlTag()));
            child._setKeyValue(childKey);
            if (childKey != null) {
               child._setInstanceName(composeInstanceName(myParent._getInstanceName(), child._getKeyValue(), childNode.getXmlWrapperTag()));
            } else {
               child._setInstanceName(composeInstanceName(myParent._getInstanceName(), childNode.getChildXmlTag(), childNode.getXmlWrapperTag()));
            }

            child._setDynamicChangeInfo((XmlRootHandleImpl)myParent._getRoot(), changeInformation);
            externalAdd(child, xmlDynamicChange.getDynamicConfiguration(), xmlDynamicChange.getBeanDatabase(), addedServices);
            invokeVetoableChangeListeners(changeInformation, child, (Object)null, child, "", myParent._getClassReflectionHelper());
            if (rawChild != null) {
               handleChildren(child, (BaseHK2JAXBBean)rawChild, changeInformation, addedServices, xmlDynamicChange);
            }

            if (!changeList) {
               return child;
            } else {
               if (multiChildren != null) {
                  multiChildren.add(index, child);
                  Object finalChildList;
                  if (ChildType.LIST.equals(childNode.getChildType())) {
                     finalChildList = Collections.unmodifiableList(multiChildren);
                  } else {
                     finalChildList = Array.newInstance(childNode.getChildModel().getOriginalInterfaceAsClass(), multiChildren.size());

                     for(int lcv = 0; lcv < multiChildren.size(); ++lcv) {
                        Array.set(finalChildList, lcv, multiChildren.get(lcv));
                     }
                  }

                  if (xmlDynamicChange.getBeanDatabase() != null) {
                     myParent._changeInHub(childPropertyNamespace, childProperty, finalChildList, xmlDynamicChange.getBeanDatabase());
                  }

                  myParent._setProperty(childPropertyNamespace, childProperty, finalChildList, false, true);
               } else {
                  if (xmlDynamicChange.getBeanDatabase() != null) {
                     myParent._changeInHub(childPropertyNamespace, childProperty, child, xmlDynamicChange.getBeanDatabase());
                  }

                  myParent._setProperty(childPropertyNamespace, childProperty, child, false, true);
               }

               return child;
            }
         }
      }
   }

   private static String composeInstanceName(String parentName, String mySegment, String xmlWrapperTag) {
      return xmlWrapperTag == null ? parentName + '.' + mySegment : parentName + '.' + xmlWrapperTag + '.' + mySegment;
   }

   public static String getProxyNameFromInterfaceName(String iFaceName) {
      return iFaceName + "_Hk2_Jaxb";
   }

   private static void handleChildren(BaseHK2JAXBBean child, BaseHK2JAXBBean childToCopy, DynamicChangeInfo changeInformation, List addedServices, XmlDynamicChange xmlDynamicChange) {
      Map childrenMap = childToCopy._getModel().getChildrenProperties();
      Iterator var6 = childrenMap.entrySet().iterator();

      while(true) {
         ParentedModel childsChildParentNode;
         String childsChildPropertyNamespace;
         String childsChildPropertyKey;
         Object childsChildren;
         do {
            label50:
            do {
               while(var6.hasNext()) {
                  Map.Entry childsChildrenEntry = (Map.Entry)var6.next();
                  QName childsChildProperty = (QName)childsChildrenEntry.getKey();
                  childsChildParentNode = (ParentedModel)childsChildrenEntry.getValue();
                  childsChildPropertyNamespace = QNameUtilities.getNamespace(childsChildProperty);
                  childsChildPropertyKey = childsChildProperty.getLocalPart();
                  if (!ChildType.DIRECT.equals(childsChildParentNode.getChildType())) {
                     childsChildren = null;
                     if (ChildType.LIST.equals(childsChildParentNode.getChildType())) {
                        childsChildren = (List)childToCopy._getProperty(childsChildPropertyNamespace, childsChildPropertyKey);
                     } else {
                        Object arrayChildsChildren = childToCopy._getProperty(childsChildPropertyNamespace, childsChildPropertyKey);
                        if (arrayChildsChildren != null) {
                           childsChildren = new ArrayList(Array.getLength(arrayChildsChildren));

                           for(int lcv = 0; lcv < Array.getLength(arrayChildsChildren); ++lcv) {
                              ((List)childsChildren).add(lcv, (BaseHK2JAXBBean)Array.get(arrayChildsChildren, lcv));
                           }
                        }
                     }
                     continue label50;
                  }

                  BaseHK2JAXBBean childsChild = (BaseHK2JAXBBean)childToCopy._getProperty(childsChildPropertyNamespace, childsChildPropertyKey);
                  if (childsChild != null) {
                     BaseHK2JAXBBean grandchild = internalAdd(child, childsChildPropertyNamespace, childsChildPropertyKey, childsChild, (String)null, -1, changeInformation, xmlDynamicChange, addedServices, false);
                     child._setProperty(childsChildPropertyNamespace, childsChildPropertyKey, grandchild, false, true);
                  }
               }

               return;
            } while(childsChildren == null);
         } while(((List)childsChildren).size() <= 0);

         ArrayList copiedChildArray = new ArrayList(((List)childsChildren).size());
         Object asArray = Array.newInstance(childsChildParentNode.getChildModel().getOriginalInterfaceAsClass(), ((List)childsChildren).size());
         int lcv = 0;
         Iterator var16 = ((List)childsChildren).iterator();

         while(var16.hasNext()) {
            BaseHK2JAXBBean childsChild = (BaseHK2JAXBBean)var16.next();
            BaseHK2JAXBBean grandchild = internalAdd(child, childsChildPropertyNamespace, childsChildPropertyKey, childsChild, (String)null, -1, changeInformation, xmlDynamicChange, addedServices, false);
            copiedChildArray.add(grandchild);
            Array.set(asArray, lcv++, grandchild);
         }

         if (ChildType.LIST.equals(childsChildParentNode.getChildType())) {
            child._setProperty(childsChildPropertyNamespace, childsChildPropertyKey, copiedChildArray, false, true);
         } else {
            child._setProperty(childsChildPropertyNamespace, childsChildPropertyKey, asArray, false, true);
         }
      }
   }

   private static void externalAdd(BaseHK2JAXBBean root, DynamicConfiguration config, WriteableBeanDatabase writeableDatabase, List addedDescriptors) {
      if (config != null || writeableDatabase != null) {
         ActiveDescriptor added = advertise(writeableDatabase, config, root);
         if (added != null) {
            addedDescriptors.add(added);
         }

         Iterator var5 = root._getModel().getKeyedChildren().iterator();

         while(true) {
            while(true) {
               Object unkeyedRawChild;
               int aLength;
               int lcv;
               BaseHK2JAXBBean child;
               Iterable unkeyedMultiChildren;
               Iterator var14;
               do {
                  QName unkeyedChildProperty;
                  String unkeyedChildPropertyNamespace;
                  String unkeyedChildPropertyKey;
                  if (!var5.hasNext()) {
                     var5 = root._getModel().getUnKeyedChildren().iterator();

                     while(true) {
                        while(true) {
                           do {
                              if (!var5.hasNext()) {
                                 return;
                              }

                              unkeyedChildProperty = (QName)var5.next();
                              unkeyedChildPropertyNamespace = QNameUtilities.getNamespace(unkeyedChildProperty);
                              unkeyedChildPropertyKey = unkeyedChildProperty.getLocalPart();
                              unkeyedRawChild = root._getProperty(unkeyedChildPropertyNamespace, unkeyedChildPropertyKey);
                           } while(unkeyedRawChild == null);

                           if (unkeyedRawChild instanceof Iterable) {
                              unkeyedMultiChildren = (Iterable)unkeyedRawChild;
                              var14 = unkeyedMultiChildren.iterator();

                              while(var14.hasNext()) {
                                 child = (BaseHK2JAXBBean)var14.next();
                                 externalAdd(child, config, writeableDatabase, addedDescriptors);
                              }
                           } else if (unkeyedRawChild.getClass().isArray()) {
                              aLength = Array.getLength(unkeyedRawChild);

                              for(lcv = 0; lcv < aLength; ++lcv) {
                                 child = (BaseHK2JAXBBean)Array.get(unkeyedRawChild, lcv);
                                 externalAdd(child, config, writeableDatabase, addedDescriptors);
                              }
                           } else {
                              externalAdd((BaseHK2JAXBBean)unkeyedRawChild, config, writeableDatabase, addedDescriptors);
                           }
                        }
                     }
                  }

                  unkeyedChildProperty = (QName)var5.next();
                  unkeyedChildPropertyNamespace = QNameUtilities.getNamespace(unkeyedChildProperty);
                  unkeyedChildPropertyKey = unkeyedChildProperty.getLocalPart();
                  unkeyedRawChild = root._getProperty(unkeyedChildPropertyNamespace, unkeyedChildPropertyKey);
               } while(unkeyedRawChild == null);

               if (unkeyedRawChild instanceof Iterable) {
                  unkeyedMultiChildren = (Iterable)unkeyedRawChild;
                  var14 = unkeyedMultiChildren.iterator();

                  while(var14.hasNext()) {
                     child = (BaseHK2JAXBBean)var14.next();
                     externalAdd(child, config, writeableDatabase, addedDescriptors);
                  }
               } else if (unkeyedRawChild.getClass().isArray()) {
                  aLength = Array.getLength(unkeyedRawChild);

                  for(lcv = 0; lcv < aLength; ++lcv) {
                     child = (BaseHK2JAXBBean)Array.get(unkeyedRawChild, lcv);
                     externalAdd(child, config, writeableDatabase, addedDescriptors);
                  }
               } else {
                  externalAdd((BaseHK2JAXBBean)unkeyedRawChild, config, writeableDatabase, addedDescriptors);
               }
            }
         }
      }
   }

   public static BaseHK2JAXBBean _addRoot(ModelImpl rootNode, Object rawRoot, DynamicChangeInfo changeInfo, ClassReflectionHelper helper, WriteableBeanDatabase writeableDatabase, DynamicConfiguration dynamicService, List addedServices, XmlRootHandleImpl xmlRootHandle) {
      if (!(rawRoot instanceof BaseHK2JAXBBean)) {
         throw new IllegalArgumentException("The root added must be from XmlService.createBean");
      } else {
         BaseHK2JAXBBean child = createBean(rootNode.getProxyAsClass());
         child._setClassReflectionHelper(helper);
         BaseHK2JAXBBean childToCopy = (BaseHK2JAXBBean)rawRoot;
         Iterator var10 = childToCopy._getModel().getNonChildProperties().keySet().iterator();

         String rNameKey;
         while(var10.hasNext()) {
            QName nonChildProperty = (QName)var10.next();
            rNameKey = QNameUtilities.getNamespace(nonChildProperty);
            String nonChildPropertyKey = nonChildProperty.getLocalPart();
            if (childToCopy._isSet(rNameKey, nonChildPropertyKey)) {
               Object value = childToCopy._getProperty(rNameKey, nonChildPropertyKey);
               child._setProperty(rNameKey, nonChildPropertyKey, value, false);
            }
         }

         QName rName;
         if (rootNode.getKeyProperty() != null) {
            rName = rootNode.getKeyProperty();
            child._setKeyValue((String)child._getProperty(QNameUtilities.getNamespace(rName), rName.getLocalPart()));
         }

         rName = rootNode.getRootName();
         String rNameNamespace = QNameUtilities.getNamespace(rName);
         rNameKey = rName.getLocalPart();
         child._setSelfXmlTag(rNameNamespace, rNameKey);
         child._setInstanceName(rootNode.getRootName().getLocalPart());
         handleChildren(child, childToCopy, changeInfo, addedServices, XmlDynamicChange.EMPTY);
         child._setDynamicChangeInfo(xmlRootHandle, changeInfo);
         externalAdd(child, dynamicService, writeableDatabase, addedServices);
         return child;
      }
   }

   public static BaseHK2JAXBBean internalRemove(BaseHK2JAXBBean myParent, String childPropertyNamespace, String childProperty, String childKey, int index, Object childToRemove, DynamicChangeInfo changeInformation, XmlDynamicChange xmlDynamicChange, boolean changeList) {
      if (childProperty == null) {
         return null;
      } else {
         String instanceToRemove = null;
         if (childKey == null && index < 0 && childToRemove != null) {
            if (!(childToRemove instanceof BaseHK2JAXBBean)) {
               throw new IllegalArgumentException("Removed child must be a child of the parent " + myParent + " but is not of the correct type " + childToRemove.getClass().getName());
            }

            BaseHK2JAXBBean childToRemoveBean = (BaseHK2JAXBBean)childToRemove;
            BaseHK2JAXBBean childToRemoveParent = (BaseHK2JAXBBean)childToRemoveBean._getParent();
            if (childToRemoveParent == null) {
               throw new IllegalArgumentException("Removed child must be a child of the parent " + myParent + " but has no parent of its own");
            }

            if (!childToRemoveParent.equals(myParent)) {
               throw new IllegalArgumentException("Removed child must be a child of the parent " + myParent + " but has a different parent " + childToRemoveParent);
            }

            instanceToRemove = childToRemoveBean._getInstanceName();
         }

         ParentedModel removeMeParentedNode = myParent._getModel().getChild(childPropertyNamespace, childProperty);
         ModelImpl removeMeNode = removeMeParentedNode.getChildModel();
         BaseHK2JAXBBean rootForDeletion = null;
         String comparisonKey;
         if (!ChildType.DIRECT.equals(removeMeParentedNode.getChildType())) {
            if (childKey == null && index < 0 && instanceToRemove == null) {
               return null;
            }

            int removeFromArrayLength;
            if (ChildType.LIST.equals(removeMeParentedNode.getChildType())) {
               List removeFromList = (List)myParent._getProperty(childPropertyNamespace, childProperty);
               if (removeFromList == null) {
                  return null;
               }

               removeFromArrayLength = removeFromList.size() - 1;
               if (removeFromArrayLength < 0) {
                  return null;
               }

               List listWithObjectRemoved = new ArrayList(removeFromArrayLength + 1);
               if (childKey == null && instanceToRemove == null) {
                  if (index >= removeFromList.size()) {
                     return null;
                  }

                  for(int lcv = 0; lcv < removeFromList.size(); ++lcv) {
                     if (lcv == index) {
                        rootForDeletion = (BaseHK2JAXBBean)removeFromList.get(lcv);
                     } else {
                        listWithObjectRemoved.add(removeFromList.get(lcv));
                     }
                  }
               } else {
                  comparisonKey = childKey != null ? childKey : instanceToRemove;
                  Iterator var17 = removeFromList.iterator();

                  while(var17.hasNext()) {
                     BaseHK2JAXBBean candidate = (BaseHK2JAXBBean)var17.next();
                     String candidateKeyValue;
                     if (childKey != null) {
                        candidateKeyValue = candidate._getKeyValue();
                     } else {
                        candidateKeyValue = candidate._getInstanceName();
                     }

                     if (GeneralUtilities.safeEquals(candidateKeyValue, comparisonKey)) {
                        rootForDeletion = candidate;
                     } else {
                        listWithObjectRemoved.add(candidate);
                     }
                  }
               }

               if (rootForDeletion == null) {
                  return null;
               }

               if (changeList) {
                  if (xmlDynamicChange.getBeanDatabase() != null) {
                     myParent._changeInHub(childPropertyNamespace, childProperty, listWithObjectRemoved, xmlDynamicChange.getBeanDatabase());
                  }

                  myParent._setProperty(childPropertyNamespace, childProperty, listWithObjectRemoved, false, true);
               }
            } else {
               Object removeFromArray = myParent._getProperty(childPropertyNamespace, childProperty);
               if (removeFromArray == null) {
                  return null;
               }

               removeFromArrayLength = Array.getLength(removeFromArray);
               if (removeFromArrayLength == 0) {
                  return null;
               }

               Class arrayType = removeMeNode.getOriginalInterfaceAsClass();
               Object arrayWithObjectRemoved = Array.newInstance(arrayType, removeFromArrayLength - 1);
               int removeIndex;
               int lcv;
               if (childKey == null && instanceToRemove == null) {
                  int removeIndex;
                  if (index >= 0) {
                     if (index >= removeFromArrayLength) {
                        return null;
                     }

                     rootForDeletion = (BaseHK2JAXBBean)Array.get(removeFromArray, index);
                     removeIndex = 0;

                     for(removeIndex = 0; removeIndex < removeFromArrayLength; ++removeIndex) {
                        if (removeIndex != index) {
                           Array.set(arrayWithObjectRemoved, removeIndex++, Array.get(removeFromArray, removeIndex));
                        }
                     }
                  } else {
                     removeIndex = -1;

                     for(removeIndex = 0; removeIndex < removeFromArrayLength; ++removeIndex) {
                        BaseHK2JAXBBean candidate = (BaseHK2JAXBBean)Array.get(removeFromArray, removeIndex);
                        String candidateKeyValue = candidate._getInstanceName();
                        if (GeneralUtilities.safeEquals(candidateKeyValue, instanceToRemove)) {
                           rootForDeletion = candidate;
                           removeIndex = removeIndex;
                           break;
                        }
                     }

                     if (rootForDeletion == null) {
                        return null;
                     }

                     removeIndex = 0;

                     for(lcv = 0; lcv < removeFromArrayLength; ++lcv) {
                        if (lcv != removeIndex) {
                           Array.set(arrayWithObjectRemoved, removeIndex++, Array.get(removeFromArray, lcv));
                        }
                     }
                  }
               } else {
                  String comparisonKey = childKey != null ? childKey : instanceToRemove;
                  removeIndex = -1;

                  for(lcv = 0; lcv < removeFromArrayLength; ++lcv) {
                     BaseHK2JAXBBean candidate = (BaseHK2JAXBBean)Array.get(removeFromArray, lcv);
                     String candidateKeyValue;
                     if (childKey != null) {
                        candidateKeyValue = candidate._getKeyValue();
                     } else {
                        candidateKeyValue = candidate._getInstanceName();
                     }

                     if (GeneralUtilities.safeEquals(candidateKeyValue, comparisonKey)) {
                        rootForDeletion = candidate;
                        removeIndex = lcv;
                        break;
                     }
                  }

                  if (rootForDeletion == null) {
                     return null;
                  }

                  lcv = 0;

                  for(int lcv = 0; lcv < removeFromArrayLength; ++lcv) {
                     if (lcv != removeIndex) {
                        Array.set(arrayWithObjectRemoved, lcv++, Array.get(removeFromArray, lcv));
                     }
                  }
               }

               if (rootForDeletion == null) {
                  return null;
               }

               if (changeList) {
                  if (xmlDynamicChange.getBeanDatabase() != null) {
                     myParent._changeInHub(childPropertyNamespace, childProperty, arrayWithObjectRemoved, xmlDynamicChange.getBeanDatabase());
                  }

                  myParent._setProperty(childPropertyNamespace, childProperty, arrayWithObjectRemoved, false, true);
               }
            }
         } else {
            rootForDeletion = (BaseHK2JAXBBean)myParent._getProperty(childPropertyNamespace, childProperty);
            if (rootForDeletion == null) {
               return null;
            }

            if (changeList) {
               if (xmlDynamicChange.getBeanDatabase() != null) {
                  myParent._changeInHub(childPropertyNamespace, childProperty, (Object)null, xmlDynamicChange.getBeanDatabase());
               }

               myParent._setProperty(childPropertyNamespace, childProperty, (Object)null, false, true);
            }
         }

         invokeAllDeletedChangeListeners(changeInformation, rootForDeletion, myParent._getClassReflectionHelper());
         if (xmlDynamicChange.getDynamicConfiguration() != null) {
            HashSet descriptorsToRemove = new HashSet();
            getDescriptorsToRemove(rootForDeletion, descriptorsToRemove);
            Iterator var30 = descriptorsToRemove.iterator();

            while(var30.hasNext()) {
               ActiveDescriptor descriptorToRemove = (ActiveDescriptor)var30.next();
               xmlDynamicChange.getDynamicConfiguration().addUnbindFilter(BuilderHelper.createSpecificDescriptorFilter(descriptorToRemove));
            }
         }

         if (xmlDynamicChange.getBeanDatabase() != null) {
            String rootXmlPath = rootForDeletion._getXmlPath();
            String rootInstanceName = rootForDeletion._getInstanceName();
            WriteableType rootType = xmlDynamicChange.getBeanDatabase().getWriteableType(rootXmlPath);
            if (rootType != null) {
               rootType.removeInstance(rootInstanceName);
               comparisonKey = rootXmlPath + '/';
               Set allTypes = xmlDynamicChange.getBeanDatabase().getAllWriteableTypes();
               Iterator var44 = allTypes.iterator();

               while(true) {
                  WriteableType allType;
                  do {
                     if (!var44.hasNext()) {
                        return rootForDeletion;
                     }

                     allType = (WriteableType)var44.next();
                  } while(!allType.getName().startsWith(comparisonKey));

                  Map allInstances = allType.getInstances();
                  Set removeMe = new LinkedHashSet();
                  String rootInstancePrefix = rootInstanceName + ".";
                  Iterator var23 = allInstances.keySet().iterator();

                  String iKey;
                  while(var23.hasNext()) {
                     iKey = (String)var23.next();
                     if (iKey.startsWith(rootInstancePrefix)) {
                        removeMe.add(iKey);
                     }
                  }

                  var23 = removeMe.iterator();

                  while(var23.hasNext()) {
                     iKey = (String)var23.next();
                     allType.removeInstance(iKey);
                  }
               }
            }
         }

         return rootForDeletion;
      }
   }

   private static void getDescriptorsToRemove(BaseHK2JAXBBean fromMe, HashSet descriptorsToRemove) {
      ActiveDescriptor fromMeDescriptor = fromMe._getSelfDescriptor();
      if (fromMeDescriptor != null) {
         descriptorsToRemove.add(fromMeDescriptor);
         ModelImpl model = fromMe._getModel();
         if (model != null) {
            Iterator var4 = model.getAllChildren().iterator();

            label49:
            while(var4.hasNext()) {
               ParentedModel parentedChild = (ParentedModel)var4.next();
               String childPropertyNamespace = parentedChild.getChildXmlNamespace();
               String childPropertyName = parentedChild.getChildXmlTag();
               BaseHK2JAXBBean listChild;
               switch (parentedChild.getChildType()) {
                  case LIST:
                     List listChildren = (List)fromMe._getProperty(childPropertyNamespace, childPropertyName);
                     if (listChildren == null) {
                        break;
                     }

                     Iterator var14 = listChildren.iterator();

                     while(true) {
                        if (!var14.hasNext()) {
                           continue label49;
                        }

                        listChild = (BaseHK2JAXBBean)var14.next();
                        getDescriptorsToRemove(listChild, descriptorsToRemove);
                     }
                  case ARRAY:
                     Object arrayChildren = fromMe._getProperty(childPropertyNamespace, childPropertyName);
                     if (arrayChildren == null) {
                        break;
                     }

                     int arrayLength = Array.getLength(arrayChildren);
                     int lcv = 0;

                     while(true) {
                        if (lcv >= arrayLength) {
                           continue label49;
                        }

                        BaseHK2JAXBBean bean = (BaseHK2JAXBBean)Array.get(arrayChildren, lcv);
                        getDescriptorsToRemove(bean, descriptorsToRemove);
                        ++lcv;
                     }
                  case DIRECT:
                     listChild = (BaseHK2JAXBBean)fromMe._getProperty(childPropertyNamespace, childPropertyName);
                     if (listChild != null) {
                        getDescriptorsToRemove(listChild, descriptorsToRemove);
                     }
                     break;
                  default:
                     throw new AssertionError("Unknown child type " + parentedChild.getChildType());
               }
            }

         }
      }
   }

   public static Object getDefaultValue(String givenStringDefault, Class expectedClass, Map namespaceMap) {
      if (givenStringDefault != null && !"\u0000".equals(givenStringDefault)) {
         if (String.class.equals(expectedClass)) {
            return givenStringDefault;
         } else if (!Integer.TYPE.equals(expectedClass) && !Integer.class.equals(expectedClass)) {
            if (!Long.TYPE.equals(expectedClass) && !Long.class.equals(expectedClass)) {
               if (!Boolean.TYPE.equals(expectedClass) && !Boolean.class.equals(expectedClass)) {
                  if (!Short.TYPE.equals(expectedClass) && !Short.class.equals(expectedClass)) {
                     if (!Byte.TYPE.equals(expectedClass) && !Byte.class.equals(expectedClass)) {
                        if (!Character.TYPE.equals(expectedClass) && !Character.class.equals(expectedClass)) {
                           if (!Float.TYPE.equals(expectedClass) && !Float.class.equals(expectedClass)) {
                              if (!Double.TYPE.equals(expectedClass) && !Double.class.equals(expectedClass)) {
                                 if (expectedClass.isArray() && Byte.TYPE.equals(expectedClass.getComponentType())) {
                                    return givenStringDefault.getBytes();
                                 } else if (expectedClass.isEnum()) {
                                    try {
                                       Method m = expectedClass.getMethod("fromValue", ENUM_FROM_VALUE_PARAM_TYPES);
                                       if (!ReflectionHelper.isStatic(m)) {
                                          throw new IllegalArgumentException("Method " + m + " is not static");
                                       } else {
                                          Object[] params = new Object[]{givenStringDefault};
                                          return ReflectionHelper.invoke((Object)null, m, params, true);
                                       }
                                    } catch (Throwable var7) {
                                       throw new AssertionError("An enum with a default must have a fromValue(String) method to return the value for " + expectedClass.getName() + " and default value " + givenStringDefault, var7);
                                    }
                                 } else if (QName.class.equals(expectedClass)) {
                                    int indexOfColon = givenStringDefault.indexOf(58);
                                    if (indexOfColon < 0) {
                                       return new QName(givenStringDefault);
                                    } else {
                                       String prefix = givenStringDefault.substring(0, indexOfColon);
                                       String localPart = givenStringDefault.substring(indexOfColon + 1);
                                       String namespaceURI = (String)namespaceMap.get(prefix);
                                       return namespaceURI == null ? null : new QName(namespaceURI, localPart, prefix);
                                    }
                                 } else {
                                    throw new AssertionError("Default for type " + expectedClass.getName() + " not implemented with default " + givenStringDefault);
                                 }
                              } else {
                                 return Double.parseDouble(givenStringDefault);
                              }
                           } else {
                              return Float.parseFloat(givenStringDefault);
                           }
                        } else {
                           return givenStringDefault.charAt(0);
                        }
                     } else {
                        return Byte.parseByte(givenStringDefault);
                     }
                  } else {
                     return Short.parseShort(givenStringDefault);
                  }
               } else {
                  return Boolean.parseBoolean(givenStringDefault);
               }
            } else {
               return Long.parseLong(givenStringDefault);
            }
         } else {
            return Integer.parseInt(givenStringDefault);
         }
      } else if (!Integer.TYPE.equals(expectedClass) && !Integer.class.equals(expectedClass)) {
         if (!Long.TYPE.equals(expectedClass) && !Long.class.equals(expectedClass)) {
            if (!Boolean.TYPE.equals(expectedClass) && !Boolean.class.equals(expectedClass)) {
               if (!Short.TYPE.equals(expectedClass) && !Short.class.equals(expectedClass)) {
                  if (!Byte.TYPE.equals(expectedClass) && !Byte.class.equals(expectedClass)) {
                     if (!Character.TYPE.equals(expectedClass) && !Character.class.equals(expectedClass)) {
                        if (!Float.TYPE.equals(expectedClass) && !Float.class.equals(expectedClass)) {
                           return !Double.TYPE.equals(expectedClass) && !Double.class.equals(expectedClass) ? null : DEFAULT_DOUBLE;
                        } else {
                           return DEFAULT_FLOAT;
                        }
                     } else {
                        return DEFAULT_CHARACTER;
                     }
                  } else {
                     return DEFAULT_BYTE;
                  }
               } else {
                  return DEFAULT_SHORT;
               }
            } else {
               return DEFAULT_BOOLEAN;
            }
         } else {
            return DEFAULT_LONG;
         }
      } else {
         return DEFAULT_INTEGER;
      }
   }

   public static void fillInUnfinishedReferences(Map referenceMap, List unresolved) {
      List errors = new LinkedList();
      Iterator var3 = unresolved.iterator();

      while(var3.hasNext()) {
         UnresolvedReference unresolvedRef = (UnresolvedReference)var3.next();
         ReferenceKey key = new ReferenceKey(unresolvedRef.getType(), unresolvedRef.getXmlID());
         BaseHK2JAXBBean reference = (BaseHK2JAXBBean)referenceMap.get(key);
         if (reference == null) {
            errors.add(new IllegalStateException("No Reference was found for " + unresolvedRef));
         }

         BaseHK2JAXBBean unfinished = unresolvedRef.getUnfinished();
         unfinished._setProperty(unresolvedRef.getPropertyNamespace(), unresolvedRef.getPropertyName(), reference);
      }

      if (!errors.isEmpty()) {
         throw new MultiException(errors);
      }
   }

   public static Method findSuitableCustomizerMethod(Class cClass, String methodName, Class[] params, Class topInterface) {
      try {
         return cClass.getMethod(methodName, params);
      } catch (NoSuchMethodException var13) {
         if (topInterface == null) {
            return null;
         } else {
            int altParamsLength = params.length + 1;
            Class[] exactParams = new Class[altParamsLength];
            exactParams[0] = topInterface;

            int lcv;
            for(lcv = 0; lcv < params.length; ++lcv) {
               exactParams[lcv + 1] = params[lcv];
            }

            try {
               return cClass.getMethod(methodName, exactParams);
            } catch (NoSuchMethodException var12) {
               Method[] var14 = cClass.getMethods();
               int var15 = var14.length;

               for(lcv = 0; lcv < var15; ++lcv) {
                  Method candidate = var14[lcv];
                  if (methodName.equals(candidate.getName())) {
                     int altParamsLength = params.length + 1;
                     Class[] candidateParams = candidate.getParameterTypes();
                     if (candidateParams.length == altParamsLength && candidateParams[0].isAssignableFrom(topInterface)) {
                        boolean found = true;

                        for(int lcv = 1; lcv < altParamsLength; ++lcv) {
                           if (!candidateParams[lcv].equals(params[lcv - 1])) {
                              found = false;
                              break;
                           }
                        }

                        if (found) {
                           return candidate;
                        }
                     }
                  }
               }

               return null;
            }
         }
      }
   }

   private static void invokeAllDeletedChangeListeners(DynamicChangeInfo control, BaseHK2JAXBBean rootBean, ClassReflectionHelper helper) {
      ModelImpl model = rootBean._getModel();
      Map childrenByName = model.getChildrenByName();
      Iterator var5 = childrenByName.entrySet().iterator();

      while(true) {
         while(true) {
            ParentedModel parentModel;
            Object child;
            do {
               if (!var5.hasNext()) {
                  invokeVetoableChangeListeners(control, rootBean, rootBean, (Object)null, "", helper);
                  return;
               }

               Map.Entry entry = (Map.Entry)var5.next();
               QName propertyName = (QName)entry.getKey();
               parentModel = (ParentedModel)entry.getValue();
               String propertyNameNamespace = QNameUtilities.getNamespace(propertyName);
               String propertyNameKey = propertyName.getLocalPart();
               child = rootBean._getProperty(propertyNameNamespace, propertyNameKey);
            } while(child == null);

            BaseHK2JAXBBean grandchild;
            if (ChildType.LIST.equals(parentModel.getChildType())) {
               List listChildren = (List)child;
               Iterator var17 = listChildren.iterator();

               while(var17.hasNext()) {
                  grandchild = (BaseHK2JAXBBean)var17.next();
                  invokeAllDeletedChangeListeners(control, grandchild, helper);
               }
            } else if (ChildType.ARRAY.equals(parentModel.getChildType())) {
               int length = Array.getLength(child);

               for(int lcv = 0; lcv < length; ++lcv) {
                  grandchild = (BaseHK2JAXBBean)Array.get(child, lcv);
                  invokeAllDeletedChangeListeners(control, grandchild, helper);
               }
            } else if (ChildType.DIRECT.equals(parentModel.getChildType())) {
               BaseHK2JAXBBean grandchild = (BaseHK2JAXBBean)child;
               invokeAllDeletedChangeListeners(control, grandchild, helper);
            }
         }
      }
   }

   public static void invokeVetoableChangeListeners(DynamicChangeInfo control, BaseHK2JAXBBean source, Object oldValue, Object newValue, String propertyName, ClassReflectionHelper helper) {
      if (control != null) {
         Validator validator = control.findValidator();
         if (validator != null) {
            ModelImpl model = source._getModel();
            String javaName = model.getJavaNameFromKey(propertyName, helper);
            if (javaName != null) {
               Set violations = validator.validateValue(source.getClass(), javaName, newValue, new Class[0]);
               if (violations != null && !violations.isEmpty()) {
                  throw new MultiException(new ConstraintViolationException(violations));
               }
            }
         }

         List vetoers = control.getChangeListeners();
         PropertyChangeEvent event = new PropertyChangeEvent(source, propertyName, oldValue, newValue);
         List errors = new LinkedList();
         Iterator var10 = vetoers.iterator();

         while(var10.hasNext()) {
            VetoableChangeListener listener = (VetoableChangeListener)var10.next();

            try {
               listener.vetoableChange(event);
            } catch (PropertyVetoException var13) {
               errors.add(var13);
               throw new MultiException(errors);
            } catch (Throwable var14) {
               errors.add(var14);
            }
         }

         if (!errors.isEmpty()) {
            throw new MultiException(errors);
         }
      }
   }

   public static Differences getDiff(BaseHK2JAXBBean source, BaseHK2JAXBBean other) {
      ModelImpl sourceModel = source._getModel();
      ModelImpl otherModel = other._getModel();
      if (!sourceModel.equals(otherModel)) {
         throw new AssertionError("Can only diff two beans of the same type.  Source is " + sourceModel + " other is " + otherModel);
      } else {
         Differences retVal = new Differences();
         getAllDifferences(source, other, retVal);
         return retVal;
      }
   }

   private static Map getIndexMap(List list, String keyPropertyNamespace, String keyProperty) {
      Map retVal = new HashMap();

      for(int lcv = 0; lcv < list.size(); ++lcv) {
         BaseHK2JAXBBean bean = (BaseHK2JAXBBean)list.get(lcv);
         String key = bean._getKeyValue();
         if (key == null) {
            key = (String)bean._getProperty(keyPropertyNamespace, keyProperty);
            if (key == null) {
               throw new AssertionError("Found a keyed bean with no key " + bean + " at index " + lcv + " in " + list);
            }
         }

         retVal.put(key, lcv);
      }

      return retVal;
   }

   private static Map getIndexMapArray(Object array, String keyPropertyNamespace, String keyProperty) {
      Map retVal = new HashMap();
      int length = Array.getLength(array);

      for(int lcv = 0; lcv < length; ++lcv) {
         BaseHK2JAXBBean bean = (BaseHK2JAXBBean)Array.get(array, lcv);
         String key = bean._getKeyValue();
         if (key == null) {
            key = (String)bean._getProperty(keyPropertyNamespace, keyProperty);
            if (key == null) {
               throw new AssertionError("Found a keyed bean with no key " + bean + " at index " + lcv);
            }
         }

         retVal.put(key, lcv);
      }

      return retVal;
   }

   private static void getAllDifferences(BaseHK2JAXBBean source, BaseHK2JAXBBean other, Differences differences) {
      Differences.Difference localDifference = new Differences.Difference(source);
      ModelImpl sourceModel = source._getModel();
      Map sourceMap = source._getQNameMap();
      Map otherMap = other._getQNameMap();
      Map nonChildProperties = sourceModel.getNonChildProperties();
      Iterator var8 = nonChildProperties.entrySet().iterator();

      while(true) {
         Object sourceValue;
         String keyPropertyNamespace;
         String keyPropertyKey;
         while(var8.hasNext()) {
            Map.Entry nonChildPropertyEntry = (Map.Entry)var8.next();
            QName nonChildProperty = (QName)nonChildPropertyEntry.getKey();
            ChildDataModel dataModel = (ChildDataModel)nonChildPropertyEntry.getValue();
            String nonChildPropertyKey = nonChildProperty.getLocalPart();
            Object sourceValue = sourceMap.get(nonChildProperty);
            sourceValue = otherMap.get(nonChildProperty);
            if (!dataModel.isReference()) {
               if (!GeneralUtilities.safeEquals(sourceValue, sourceValue)) {
                  localDifference.addNonChildChange(new PropertyChangeEvent(source, nonChildPropertyKey, sourceValue, sourceValue));
               }
            } else if (sourceValue != null && sourceValue == null) {
               localDifference.addNonChildChange(new PropertyChangeEvent(source, nonChildPropertyKey, sourceValue, sourceValue));
            } else if (sourceValue == null && sourceValue != null) {
               localDifference.addNonChildChange(new PropertyChangeEvent(source, nonChildPropertyKey, sourceValue, sourceValue));
            } else if (sourceValue != null) {
               BaseHK2JAXBBean sourceReference = (BaseHK2JAXBBean)sourceValue;
               BaseHK2JAXBBean otherReference = (BaseHK2JAXBBean)sourceValue;
               keyPropertyNamespace = sourceReference._getKeyValue();
               keyPropertyKey = otherReference._getKeyValue();
               if (!GeneralUtilities.safeEquals(keyPropertyNamespace, keyPropertyKey)) {
                  localDifference.addNonChildChange(new PropertyChangeEvent(source, nonChildPropertyKey, sourceValue, sourceValue));
               }
            }
         }

         Map childProperties = sourceModel.getChildrenByName();
         Iterator var22 = childProperties.entrySet().iterator();

         while(true) {
            while(true) {
               while(var22.hasNext()) {
                  Map.Entry childEntry = (Map.Entry)var22.next();
                  QName xmlTag = (QName)childEntry.getKey();
                  ParentedModel pModel = (ParentedModel)childEntry.getValue();
                  String xmlTagKey = xmlTag.getLocalPart();
                  sourceValue = sourceMap.get(xmlTag);
                  Object otherValue = otherMap.get(xmlTag);
                  if (ChildType.DIRECT.equals(pModel.getChildType())) {
                     if (sourceValue == null && otherValue != null) {
                        localDifference.addAdd(xmlTagKey, (BaseHK2JAXBBean)otherValue, -1);
                     } else if (sourceValue != null && otherValue == null) {
                        localDifference.addRemove(xmlTagKey, new Differences.RemoveData(xmlTagKey, (BaseHK2JAXBBean)sourceValue));
                     } else if (sourceValue != null) {
                        QName keyProperty = pModel.getChildModel().getKeyProperty();
                        if (keyProperty == null) {
                           getAllDifferences((BaseHK2JAXBBean)sourceValue, (BaseHK2JAXBBean)otherValue, differences);
                        } else {
                           keyPropertyNamespace = QNameUtilities.getNamespace(keyProperty);
                           keyPropertyKey = keyProperty.getLocalPart();
                           String sourceKey = (String)((BaseHK2JAXBBean)sourceValue)._getProperty(keyPropertyNamespace, keyPropertyKey);
                           String otherKey = (String)((BaseHK2JAXBBean)otherValue)._getProperty(keyPropertyNamespace, keyPropertyKey);
                           if (GeneralUtilities.safeEquals(sourceKey, otherKey)) {
                              getAllDifferences((BaseHK2JAXBBean)sourceValue, (BaseHK2JAXBBean)otherValue, differences);
                           } else {
                              localDifference.addDirectReplace(xmlTagKey, (BaseHK2JAXBBean)otherValue, new Differences.RemoveData(xmlTagKey, (BaseHK2JAXBBean)sourceValue));
                           }
                        }
                     }
                  } else if (ChildType.LIST.equals(pModel.getChildType())) {
                     getListDifferences(pModel, sourceValue, otherValue, differences, xmlTagKey, source);
                  } else if (ChildType.ARRAY.equals(pModel.getChildType())) {
                     getArrayDifferences(pModel, sourceValue, otherValue, differences, xmlTagKey, source);
                  }
               }

               if (localDifference.isDirty()) {
                  differences.addDifference(localDifference);
               }

               return;
            }
         }
      }
   }

   private static void getListDifferences(ParentedModel pModel, Object sourceValue, Object otherValue, Differences differences, String xmlTag, BaseHK2JAXBBean source) {
      Differences.Difference localDifference = new Differences.Difference(source);
      QName keyProperty = pModel.getChildModel().getKeyProperty();
      List sourceValueList = (List)sourceValue;
      List otherValueList = (List)otherValue;
      if (sourceValueList == null) {
         sourceValueList = Collections.emptyList();
      }

      if (otherValueList == null) {
         otherValueList = Collections.emptyList();
      }

      if (keyProperty != null) {
         String keyPropertyNamespace = QNameUtilities.getNamespace(keyProperty);
         String keyPropertyKey = keyProperty.getLocalPart();
         Map sourceIndexMap = getIndexMap(sourceValueList, keyPropertyNamespace, keyPropertyKey);
         Map otherIndexMap = getIndexMap(otherValueList, keyPropertyNamespace, keyPropertyKey);
         Iterator var14 = sourceValueList.iterator();

         BaseHK2JAXBBean otherBean;
         String otherKeyValue;
         int addedIndex;
         while(var14.hasNext()) {
            otherBean = (BaseHK2JAXBBean)var14.next();
            otherKeyValue = otherBean._getKeyValue();
            if (!otherIndexMap.containsKey(otherKeyValue)) {
               localDifference.addRemove(xmlTag, new Differences.RemoveData(xmlTag, otherKeyValue, otherBean));
            } else {
               addedIndex = (Integer)sourceIndexMap.get(otherKeyValue);
               int otherIndex = (Integer)otherIndexMap.get(otherKeyValue);
               Object otherBean = otherValueList.get(otherIndex);
               if (otherIndex != addedIndex) {
                  localDifference.addMove(xmlTag, new Differences.MoveData(addedIndex, otherIndex));
               }

               getAllDifferences(otherBean, (BaseHK2JAXBBean)otherBean, differences);
            }
         }

         var14 = otherValueList.iterator();

         while(var14.hasNext()) {
            otherBean = (BaseHK2JAXBBean)var14.next();
            otherKeyValue = otherBean._getKeyValue();
            if (otherKeyValue == null) {
               otherKeyValue = (String)otherBean._getProperty(keyPropertyNamespace, keyPropertyKey);
            }

            if (!sourceIndexMap.containsKey(otherKeyValue)) {
               addedIndex = (Integer)otherIndexMap.get(otherKeyValue);
               localDifference.addAdd(xmlTag, otherBean, addedIndex);
            }
         }
      } else {
         UnkeyedDiff unkeyedDiff = new UnkeyedDiff(sourceValueList, otherValueList, source, pModel);
         Differences unkeyedDiffs = unkeyedDiff.compute();
         differences.merge(unkeyedDiffs);
      }

      if (localDifference.isDirty()) {
         differences.addDifference(localDifference);
      }

   }

   private static void getArrayDifferences(ParentedModel pModel, Object sourceValue, Object otherValue, Differences differences, String xmlTag, BaseHK2JAXBBean source) {
      Differences.Difference localDifference = new Differences.Difference(source);
      QName keyProperty = pModel.getChildModel().getKeyProperty();
      Object sourceArray = sourceValue == null ? new BaseHK2JAXBBean[0] : sourceValue;
      Object otherArray = otherValue == null ? new BaseHK2JAXBBean[0] : otherValue;
      if (keyProperty != null) {
         String keyPropertyNamespace = QNameUtilities.getNamespace(keyProperty);
         String keyPropertyKey = keyProperty.getLocalPart();
         Map sourceIndexMap = getIndexMapArray(sourceArray, keyPropertyNamespace, keyPropertyKey);
         Map otherIndexMap = getIndexMapArray(otherArray, keyPropertyNamespace, keyPropertyKey);
         int sourceLength = Array.getLength(sourceArray);

         int otherLength;
         for(otherLength = 0; otherLength < sourceLength; ++otherLength) {
            BaseHK2JAXBBean sourceBean = (BaseHK2JAXBBean)Array.get(sourceArray, otherLength);
            String sourceKeyValue = sourceBean._getKeyValue();
            if (!otherIndexMap.containsKey(sourceKeyValue)) {
               localDifference.addRemove(xmlTag, new Differences.RemoveData(xmlTag, sourceKeyValue, sourceBean));
            } else {
               int sourceIndex = (Integer)sourceIndexMap.get(sourceKeyValue);
               int otherIndex = (Integer)otherIndexMap.get(sourceKeyValue);
               BaseHK2JAXBBean otherBean = (BaseHK2JAXBBean)Array.get(otherArray, otherIndex);
               if (sourceIndex != otherIndex) {
                  localDifference.addMove(xmlTag, new Differences.MoveData(sourceIndex, otherIndex));
               }

               getAllDifferences(sourceBean, otherBean, differences);
            }
         }

         otherLength = Array.getLength(otherArray);

         for(int lcv = 0; lcv < otherLength; ++lcv) {
            BaseHK2JAXBBean otherBean = (BaseHK2JAXBBean)Array.get(otherArray, lcv);
            String otherKeyValue = otherBean._getKeyValue();
            if (otherKeyValue == null) {
               otherKeyValue = (String)otherBean._getProperty(keyPropertyNamespace, keyPropertyKey);
            }

            if (!sourceIndexMap.containsKey(otherKeyValue)) {
               localDifference.addAdd(xmlTag, otherBean, lcv);
            }
         }
      } else {
         UnkeyedDiff unkeyedDiff = new UnkeyedDiff((Object[])((Object[])sourceArray), (Object[])((Object[])otherArray), source, pModel);
         Differences unkeyedDiffs = unkeyedDiff.compute();
         differences.merge(unkeyedDiffs);
      }

      if (localDifference.isDirty()) {
         differences.addDifference(localDifference);
      }

   }

   public static void applyDiff(Differences differences, DynamicChangeInfo changeControl) {
      Iterator var2 = differences.getDifferences().iterator();

      while(true) {
         label91:
         while(var2.hasNext()) {
            Differences.Difference difference = (Differences.Difference)var2.next();
            BaseHK2JAXBBean source = difference.getSource();
            List allSourceChanges = new LinkedList();
            allSourceChanges.addAll(difference.getNonChildChanges());
            if (!difference.hasChildChanges()) {
               applyAllSourceChanges(source, allSourceChanges, changeControl);
            } else {
               ModelImpl model = source._getModel();
               Iterator var7 = difference.getChildChanges().entrySet().iterator();

               while(true) {
                  String xmlKey;
                  Differences.AddRemoveMoveDifference childDiffs;
                  ParentedModel parentedModel;
                  ChildType childType;
                  boolean changeList;
                  Object oldListOrArray;
                  LinkedHashMap arrayChanges;
                  do {
                     if (!var7.hasNext()) {
                        applyAllSourceChanges(source, allSourceChanges, changeControl);
                        continue label91;
                     }

                     Map.Entry childEntry = (Map.Entry)var7.next();
                     xmlKey = (String)childEntry.getKey();
                     childDiffs = (Differences.AddRemoveMoveDifference)childEntry.getValue();
                     parentedModel = model.getChild("", xmlKey);
                     childType = parentedModel.getChildType();
                     changeList = ChildType.DIRECT.equals(childType);
                     oldListOrArray = source._getProperty("", xmlKey);
                     arrayChanges = null;
                     if (!changeList) {
                        arrayChanges = new LinkedHashMap();
                     }

                     Iterator var16 = childDiffs.getDirectReplaces().iterator();

                     BaseHK2JAXBBean addedBean;
                     while(var16.hasNext()) {
                        Differences.AddRemoveData ard = (Differences.AddRemoveData)var16.next();
                        Differences.RemoveData removed = ard.getRemove();
                        Differences.AddData added = ard.getAdd();
                        addedBean = added.getToAdd();
                        String addedKey = addedBean._getKeyValue();
                        BaseHK2JAXBBean removedBean = (BaseHK2JAXBBean)source._doRemove("", xmlKey, removed.getChildKey(), removed.getIndex(), removed.getChild(), false);
                        BaseHK2JAXBBean addedBean = (BaseHK2JAXBBean)source._doAdd("", xmlKey, addedBean, addedKey, -1, false);
                        allSourceChanges.add(new PropertyChangeEvent(source, xmlKey, removedBean, addedBean));
                     }

                     var16 = childDiffs.getAdds().iterator();

                     BaseHK2JAXBBean movedBean;
                     while(var16.hasNext()) {
                        Differences.AddData added = (Differences.AddData)var16.next();
                        movedBean = added.getToAdd();
                        int index = added.getIndex();
                        addedBean = (BaseHK2JAXBBean)source._doAdd("", xmlKey, movedBean, (String)null, index, false);
                        if (!changeList) {
                           arrayChanges.put(index, addedBean);
                        } else {
                           allSourceChanges.add(new PropertyChangeEvent(source, xmlKey, (Object)null, addedBean));
                        }
                     }

                     var16 = childDiffs.getRemoves().iterator();

                     while(var16.hasNext()) {
                        Differences.RemoveData removed = (Differences.RemoveData)var16.next();
                        source._doRemove("", xmlKey, removed.getChildKey(), removed.getIndex(), removed.getChild(), false);
                        if (changeList) {
                           allSourceChanges.add(new PropertyChangeEvent(source, xmlKey, oldListOrArray, (Object)null));
                        }
                     }

                     var16 = childDiffs.getMoves().iterator();

                     while(var16.hasNext()) {
                        Differences.MoveData md = (Differences.MoveData)var16.next();
                        movedBean = getLOABean(oldListOrArray, childType, md.getOldIndex());
                        if (!changeList) {
                           arrayChanges.put(md.getNewIndex(), movedBean);
                        }
                     }
                  } while(changeList);

                  int newSize = childDiffs.getNewSize(getLOASize(oldListOrArray, childType));
                  Object newListOrArray = createLOA(childType, newSize, parentedModel.getChildModel());

                  for(int lcv = 0; lcv < newSize; ++lcv) {
                     BaseHK2JAXBBean toPut = (BaseHK2JAXBBean)arrayChanges.get(lcv);
                     if (toPut == null) {
                        toPut = getLOABean(oldListOrArray, childType, lcv);
                     }

                     putLOABean(newListOrArray, childType, lcv, toPut);
                  }

                  allSourceChanges.add(new PropertyChangeEvent(source, xmlKey, oldListOrArray, newListOrArray));
               }
            }
         }

         return;
      }
   }

   private static void applyAllSourceChanges(BaseHK2JAXBBean source, List events, DynamicChangeInfo changeControl) {
      boolean success = false;
      XmlDynamicChange xmlDynamicChange = changeControl.startOrContinueChange(source);

      try {
         WriteableBeanDatabase wbd = xmlDynamicChange.getBeanDatabase();
         boolean madeAChange = false;
         Iterator var7 = events.iterator();

         PropertyChangeEvent pce;
         while(var7.hasNext()) {
            pce = (PropertyChangeEvent)var7.next();
            if (!GeneralUtilities.safeEquals(pce.getOldValue(), pce.getNewValue())) {
               madeAChange = true;
               invokeVetoableChangeListeners(changeControl, source, pce.getOldValue(), pce.getNewValue(), pce.getPropertyName(), source._getClassReflectionHelper());
            }
         }

         if (madeAChange) {
            if (wbd != null) {
               source._changeInHub(events, wbd);
            }

            var7 = events.iterator();

            while(var7.hasNext()) {
               pce = (PropertyChangeEvent)var7.next();
               source._setProperty("", pce.getPropertyName(), pce.getNewValue(), false, true);
            }

            success = true;
            return;
         }

         success = true;
      } finally {
         changeControl.endOrDeferChange(success);
      }

   }

   private static Object createLOA(ChildType type, int size, ModelImpl childModel) {
      return ChildType.ARRAY.equals(type) ? Array.newInstance(childModel.getOriginalInterfaceAsClass(), size) : new ArrayList(size);
   }

   private static void putLOABean(Object listOrArray, ChildType type, int index, BaseHK2JAXBBean putMe) {
      if (ChildType.ARRAY.equals(type)) {
         Array.set(listOrArray, index, putMe);
      } else {
         List list = (List)listOrArray;
         list.add(index, putMe);
      }
   }

   private static BaseHK2JAXBBean getLOABean(Object listOrArray, ChildType type, int index) {
      if (ChildType.ARRAY.equals(type)) {
         return (BaseHK2JAXBBean)Array.get(listOrArray, index);
      } else if (ChildType.LIST.equals(type)) {
         List list = (List)listOrArray;
         return (BaseHK2JAXBBean)list.get(index);
      } else {
         return (BaseHK2JAXBBean)listOrArray;
      }
   }

   private static int getLOASize(Object listOrArray, ChildType type) {
      if (ChildType.ARRAY.equals(type)) {
         return Array.getLength(listOrArray);
      } else if (ChildType.LIST.equals(type)) {
         List list = (List)listOrArray;
         return list.size();
      } else {
         return 1;
      }
   }

   public static int calculateAddCost(BaseHK2JAXBBean bean) {
      if (bean == null) {
         return -1;
      } else {
         int knownValue = bean.__getAddCost();
         if (knownValue >= 0) {
            return knownValue;
         } else {
            int retVal = 1;
            ModelImpl model = bean._getModel();
            Iterator var4 = model.getAllChildren().iterator();

            label47:
            while(true) {
               ParentedModel parentedModel;
               Object rawChild;
               do {
                  if (!var4.hasNext()) {
                     bean.__setAddCost(retVal);
                     return retVal;
                  }

                  parentedModel = (ParentedModel)var4.next();
                  String propNamespace = parentedModel.getChildXmlNamespace();
                  String propName = parentedModel.getChildXmlTag();
                  rawChild = bean._getProperty(propNamespace, propName);
               } while(rawChild == null);

               int length;
               switch (parentedModel.getChildType()) {
                  case LIST:
                     List childList = (List)rawChild;
                     Iterator var15 = childList.iterator();

                     while(true) {
                        if (!var15.hasNext()) {
                           continue label47;
                        }

                        BaseHK2JAXBBean child = (BaseHK2JAXBBean)var15.next();
                        int childCost = calculateAddCost(child);
                        retVal += childCost;
                     }
                  case ARRAY:
                     length = Array.getLength(rawChild);
                     int lcv = 0;

                     while(true) {
                        if (lcv >= length) {
                           continue label47;
                        }

                        BaseHK2JAXBBean child = (BaseHK2JAXBBean)Array.get(rawChild, lcv);
                        int childCost = calculateAddCost(child);
                        retVal += childCost;
                        ++lcv;
                     }
                  case DIRECT:
                     BaseHK2JAXBBean child = (BaseHK2JAXBBean)rawChild;
                     length = calculateAddCost(child);
                     retVal += length;
                     break;
                  default:
                     throw new AssertionError("Unknown child type " + parentedModel.getChildType());
               }
            }
         }
      }
   }

   public static List prioritizeMethods(List methods, String[] specifiedOrdering, NameInformation xmlMap) {
      if (specifiedOrdering != null && specifiedOrdering.length > 0) {
         Map orderingAsMap = new HashMap();

         for(int lcv = 0; lcv < specifiedOrdering.length; ++lcv) {
            orderingAsMap.put(specifiedOrdering[lcv], lcv);
         }

         Map secondarySort = new HashMap();
         int lcv = 0;

         for(Iterator var6 = methods.iterator(); var6.hasNext(); ++lcv) {
            AltMethod method = (AltMethod)var6.next();
            secondarySort.put(method, lcv);
         }

         TreeSet orderedSet = new TreeSet(new SpecifiedOrderComparator(orderingAsMap, secondarySort, xmlMap));
         orderedSet.addAll(methods);
         ArrayList retVal = new ArrayList(orderedSet);
         return retVal;
      } else {
         return methods;
      }
   }

   private static boolean isSpecifiedCustom(AltMethod method) {
      AltAnnotation customAnnotation = method.getAnnotation(Customize.class.getName());
      return customAnnotation != null;
   }

   private static AltAnnotation isSpecifiedAdapted(AltMethod method) {
      AltAnnotation adapterAnnotation = method.getAnnotation(XmlJavaTypeAdapter.class.getName());
      return adapterAnnotation;
   }

   private static AdapterInformation getAdapterInformation(AltMethod method) {
      AltAnnotation adapterAnnotation = isSpecifiedAdapted(method);
      if (adapterAnnotation == null) {
         return null;
      } else {
         AltClass adapter = adapterAnnotation.getClassValue("value");
         AltClass valueType = adapter.getSuperParameterizedType(ClassAltClassImpl.XML_ADAPTER, 0);
         AltClass boundType = adapter.getSuperParameterizedType(ClassAltClassImpl.XML_ADAPTER, 1);
         return new AdapterInformationImpl(adapter, valueType, boundType);
      }
   }

   public static String isSetter(AltMethod method) {
      String name = method.getName();
      if (name.startsWith("set")) {
         if (name.length() <= "set".length()) {
            return null;
         } else if (method.getParameterTypes().size() != 1) {
            return null;
         } else if (Void.TYPE.getName().equals(method.getReturnType().getName())) {
            String variableName = name.substring("set".length());
            return Introspector.decapitalize(variableName);
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   public static String isGetter(AltMethod method) {
      String name = method.getName();
      String variableName;
      if (name.startsWith("get")) {
         if (name.length() <= "get".length()) {
            return null;
         } else if (method.getParameterTypes().size() != 0) {
            return null;
         } else if (Void.TYPE.getName().equals(method.getReturnType().getName())) {
            return null;
         } else {
            variableName = name.substring("get".length());
            return Introspector.decapitalize(variableName);
         }
      } else if (name.startsWith("is")) {
         if (name.length() <= "is".length()) {
            return null;
         } else if (method.getParameterTypes().size() != 0) {
            return null;
         } else if (!Boolean.TYPE.getName().equals(method.getReturnType().getName()) && !Boolean.class.getName().equals(method.getReturnType().getName())) {
            return null;
         } else {
            variableName = name.substring("is".length());
            return Introspector.decapitalize(variableName);
         }
      } else {
         return null;
      }
   }

   private static String isLookup(AltMethod method, NameInformation nameInformation) {
      String name = method.getName();
      String retVal = nameInformation.getLookupVariableName(name);
      if (retVal == null) {
         return null;
      } else {
         List parameterTypes = method.getParameterTypes();
         if (parameterTypes.size() != 1) {
            return null;
         } else if (!String.class.getName().equals(((AltClass)parameterTypes.get(0)).getName())) {
            return null;
         } else {
            return method.getReturnType() != null && !Void.TYPE.getName().equals(method.getReturnType().getName()) ? retVal : null;
         }
      }
   }

   private static String isAdd(AltMethod method, NameInformation nameInformation) {
      String name = method.getName();
      String retVal = nameInformation.getAddVariableName(name);
      if (retVal == null) {
         return null;
      } else if (!Void.TYPE.getName().equals(method.getReturnType().getName()) && !method.getReturnType().isInterface()) {
         return null;
      } else {
         List parameterTypes = method.getParameterTypes();
         if (parameterTypes.size() > 2) {
            return null;
         } else if (parameterTypes.size() == 0) {
            return retVal;
         } else {
            AltClass param0 = (AltClass)parameterTypes.get(0);
            AltClass param1 = null;
            if (parameterTypes.size() == 2) {
               param1 = (AltClass)parameterTypes.get(1);
            }

            if (String.class.getName().equals(param0.getName()) || Integer.TYPE.getName().equals(param0.getName()) || param0.isInterface()) {
               if (parameterTypes.size() == 1) {
                  return retVal;
               }

               if (Integer.TYPE.getName().equals(param0.getName())) {
                  return null;
               }

               if (String.class.getName().equals(param0.getName())) {
                  if (Integer.TYPE.getName().equals(param1.getName())) {
                     return retVal;
                  }
               } else if (Integer.TYPE.getName().equals(param1.getName())) {
                  return retVal;
               }
            }

            return null;
         }
      }
   }

   private static String isRemove(AltMethod method, NameInformation nameInformation) {
      String name = method.getName();
      String retVal = nameInformation.getRemoveVariableName(name);
      if (retVal == null) {
         return null;
      } else {
         AltClass returnType = method.getReturnType();
         if (returnType == null) {
            returnType = ClassAltClassImpl.VOID;
         }

         if (!Boolean.TYPE.getName().equals(returnType.getName()) && !returnType.isInterface() && !Void.TYPE.getName().equals(returnType.getName())) {
            return null;
         } else {
            List parameterTypes = method.getParameterTypes();
            if (parameterTypes.size() > 1) {
               return null;
            } else if (parameterTypes.size() == 0) {
               return retVal;
            } else {
               AltClass param0 = (AltClass)parameterTypes.get(0);
               return !String.class.getName().equals(param0.getName()) && !Integer.TYPE.getName().equals(param0.getName()) && !param0.isInterface() ? null : retVal;
            }
         }
      }
   }

   public static MethodInformationI getMethodInformation(AltMethod m, NameInformation xmlNameMap) {
      if (m.getMethodInformation() != null) {
         return m.getMethodInformation();
      } else {
         boolean isCustom = isSpecifiedCustom(m);
         AdapterInformation adapter = getAdapterInformation(m);
         if (isCustom && adapter != null) {
            throw new RuntimeException("The method " + m + " must not be marked both with @Custom and @XmlJavaTypeAdapter");
         } else {
            String setterVariable = null;
            String getterVariable = null;
            String lookupVariable = null;
            String addVariable = null;
            String removeVariable = null;
            if (!isCustom) {
               setterVariable = isSetter(m);
               if (setterVariable == null) {
                  getterVariable = isGetter(m);
                  if (getterVariable == null) {
                     lookupVariable = isLookup(m, xmlNameMap);
                     if (lookupVariable == null) {
                        addVariable = isAdd(m, xmlNameMap);
                        if (addVariable == null) {
                           removeVariable = isRemove(m, xmlNameMap);
                        }
                     }
                  }
               }
            }

            AltClass baseChildType = null;
            AltClass gsType = null;
            String variable = null;
            boolean isList = false;
            boolean isArray = false;
            AltClass listParameterizedType = null;
            MethodType methodType;
            AltClass setterType;
            AltClass adapterReturnType;
            AltClass adapterReturnType;
            if (getterVariable != null) {
               methodType = MethodType.GETTER;
               variable = getterVariable;
               setterType = m.getReturnType();
               gsType = setterType;
               if (List.class.getName().equals(setterType.getName())) {
                  isList = true;
                  listParameterizedType = m.getFirstTypeArgument();
                  if (listParameterizedType == null) {
                     throw new RuntimeException("Cannot find child type of method " + m);
                  }

                  if (adapter != null) {
                     adapterReturnType = adapter.getBoundType();
                     if (!GeneralUtilities.safeEquals(listParameterizedType, adapterReturnType)) {
                        throw new RuntimeException("The return type of an adapted method (" + listParameterizedType + ") must match the annotation " + adapterReturnType + " in " + m);
                     }

                     if (adapter.isChild()) {
                        baseChildType = adapter.getValueType();
                     }
                  } else if (listParameterizedType.isInterface()) {
                     baseChildType = listParameterizedType;
                  }
               } else if (setterType.isArray()) {
                  adapterReturnType = setterType.getComponentType();
                  if (adapter != null) {
                     adapterReturnType = adapter.getBoundType();
                     if (!GeneralUtilities.safeEquals(adapterReturnType, adapterReturnType)) {
                        throw new RuntimeException("The return type of an adapted method (" + adapterReturnType + ") must match the annotation " + adapterReturnType + " in " + m);
                     }

                     if (adapter.isChild()) {
                        baseChildType = adapter.getValueType();
                     }
                  } else if (adapterReturnType.isInterface()) {
                     isArray = true;
                     baseChildType = adapterReturnType;
                  }
               } else if (adapter != null) {
                  adapterReturnType = adapter.getBoundType();
                  if (!GeneralUtilities.safeEquals(setterType, adapterReturnType)) {
                     throw new RuntimeException("The return type of an adapted method (" + setterType + ") must match the annotation " + adapterReturnType + " in " + m);
                  }

                  if (adapter.isChild()) {
                     baseChildType = adapter.getValueType();
                  }
               } else if (setterType.isInterface() && !setterType.getName().startsWith("java.")) {
                  baseChildType = setterType;
               }
            } else if (setterVariable != null) {
               methodType = MethodType.SETTER;
               variable = setterVariable;
               setterType = (AltClass)m.getParameterTypes().get(0);
               gsType = setterType;
               if (List.class.getName().equals(setterType.getName())) {
                  isList = true;
                  listParameterizedType = m.getFirstTypeArgumentOfParameter(0);
                  if (listParameterizedType == null) {
                     throw new RuntimeException("Cannot find child type of method " + m);
                  }

                  if (adapter != null) {
                     adapterReturnType = adapter.getBoundType();
                     if (!GeneralUtilities.safeEquals(listParameterizedType, adapterReturnType)) {
                        throw new RuntimeException("The return type of an adapted method (" + listParameterizedType + ") must match the annotation " + adapterReturnType + " in " + m);
                     }

                     if (adapter.isChild()) {
                        baseChildType = adapter.getValueType();
                     }
                  } else if (listParameterizedType.isInterface()) {
                     baseChildType = listParameterizedType;
                  }
               } else if (setterType.isArray()) {
                  adapterReturnType = setterType.getComponentType();
                  if (adapter != null) {
                     adapterReturnType = adapter.getBoundType();
                     if (!GeneralUtilities.safeEquals(listParameterizedType, adapterReturnType)) {
                        throw new RuntimeException("The return type of an adapted method (" + adapterReturnType + ") must match the annotation " + adapterReturnType + " in " + m);
                     }

                     if (adapter.isChild()) {
                        baseChildType = adapter.getValueType();
                     }
                  } else if (adapterReturnType.isInterface()) {
                     isArray = true;
                     baseChildType = adapterReturnType;
                  }
               } else if (adapter != null) {
                  adapterReturnType = adapter.getBoundType();
                  if (!GeneralUtilities.safeEquals(setterType, adapterReturnType)) {
                     throw new RuntimeException("The return type of an adapted method (" + setterType + ") must match the annotation " + adapterReturnType + " in " + m);
                  }

                  if (adapter.isChild()) {
                     baseChildType = adapter.getValueType();
                  }
               } else if (setterType.isInterface() && !setterType.getName().startsWith("java.")) {
                  baseChildType = setterType;
               }
            } else if (lookupVariable != null) {
               methodType = MethodType.LOOKUP;
               variable = lookupVariable;
               setterType = m.getReturnType();
               gsType = setterType;
            } else if (addVariable != null) {
               methodType = MethodType.ADD;
               variable = addVariable;
            } else if (removeVariable != null) {
               methodType = MethodType.REMOVE;
               variable = removeVariable;
            } else {
               methodType = MethodType.CUSTOM;
            }

            String repPropNamespace = xmlNameMap.getNamespaceMap(variable);
            String repPropName = xmlNameMap.getNameMap(variable);
            boolean required = xmlNameMap.isRequired(variable);
            String originalMethodName = xmlNameMap.getOriginalMethodName(variable);
            QName representedProperty;
            if (repPropName == null) {
               representedProperty = QNameUtilities.createQName("", variable);
            } else {
               representedProperty = QNameUtilities.createQName(repPropNamespace, repPropName);
            }

            String defaultValue = xmlNameMap.getDefaultNameMap(variable);
            String xmlWrapperTag = xmlNameMap.getXmlWrapperTag(variable);
            boolean key = false;
            if (m.getAnnotation(XmlID.class.getName()) != null || m.getAnnotation(XmlIdentifier.class.getName()) != null) {
               key = true;
            }

            boolean isReference = xmlNameMap.isReference(variable);
            Format format = xmlNameMap.getFormat(variable);
            return new MethodInformation(m, methodType, variable, representedProperty, defaultValue, baseChildType, gsType, key, isList, isArray, isReference, format, listParameterizedType, xmlWrapperTag, adapter, required, originalMethodName);
         }
      }
   }

   private static MethodInformationI getAndSetMethodInformation(AltMethod am, NameInformation xmlMap) {
      MethodInformationI retVal = am.getMethodInformation();
      if (retVal != null) {
         return retVal;
      } else {
         retVal = getMethodInformation(am, xmlMap);
         am.setMethodInformation(retVal);
         return retVal;
      }
   }

   private static BaseHK2JAXBBean createUnrootedBeanTreeCopy(BaseHK2JAXBBean copyMe) {
      BaseHK2JAXBBean copy = null;

      try {
         copy = doCopy(copyMe, (DynamicChangeInfo)null, (BaseHK2JAXBBean)null, (XmlRootHandleImpl)null, (Map)null, (List)null);
         return copy;
      } catch (RuntimeException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new RuntimeException(var4);
      }
   }

   public static BaseHK2JAXBBean doCopy(BaseHK2JAXBBean copyMe, DynamicChangeInfo copyController, BaseHK2JAXBBean theCopiedParent, XmlRootHandleImpl rootHandle, Map referenceMap, List unresolved) throws Throwable {
      if (copyMe == null) {
         return null;
      } else {
         BaseHK2JAXBBean retVal = createBean(copyMe.getClass());
         retVal._shallowCopyFrom(copyMe, referenceMap == null);
         ModelImpl myModel = retVal._getModel();
         Set childrenProps = copyMe._getChildrenXmlTags();
         Iterator var9 = childrenProps.iterator();

         while(true) {
            while(true) {
               String childPropNamespace;
               String childPropKey;
               Object child;
               Object fromReferenceRaw;
               do {
                  if (!var9.hasNext()) {
                     if (theCopiedParent != null) {
                        retVal._setParent(theCopiedParent);
                     }

                     QName keyPropertyName = retVal._getKeyPropertyName();
                     if (referenceMap != null && keyPropertyName != null) {
                        String keyProperty = retVal._getKeyValue();
                        if (keyProperty != null) {
                           referenceMap.put(new ReferenceKey(myModel.getOriginalInterface(), keyProperty), retVal);
                        }

                        Map nonChildrenProps = myModel.getNonChildProperties();
                        Iterator var26 = nonChildrenProps.entrySet().iterator();

                        while(var26.hasNext()) {
                           Map.Entry nonChild = (Map.Entry)var26.next();
                           QName xmlTag = (QName)nonChild.getKey();
                           ChildDataModel cdm = (ChildDataModel)nonChild.getValue();
                           String xmlTagNamespace = QNameUtilities.getNamespace(xmlTag);
                           String xmlTagKey = xmlTag.getLocalPart();
                           if (cdm.isReference()) {
                              fromReferenceRaw = copyMe._getProperty(xmlTagNamespace, xmlTagKey);
                              if (fromReferenceRaw != null && fromReferenceRaw instanceof BaseHK2JAXBBean) {
                                 BaseHK2JAXBBean fromReference = (BaseHK2JAXBBean)fromReferenceRaw;
                                 String fromKeyValue = fromReference._getKeyValue();
                                 ReferenceKey rk = new ReferenceKey(cdm.getChildType(), fromKeyValue);
                                 BaseHK2JAXBBean toReference = (BaseHK2JAXBBean)referenceMap.get(rk);
                                 if (toReference != null) {
                                    retVal._setProperty(xmlTagNamespace, xmlTagKey, toReference);
                                 } else {
                                    unresolved.add(new UnresolvedReference(cdm.getChildType(), fromKeyValue, xmlTagNamespace, xmlTagKey, retVal));
                                 }
                              }
                           }
                        }
                     }

                     if (rootHandle != null) {
                        retVal._setDynamicChangeInfo(rootHandle, copyController, false);
                     }

                     return retVal;
                  }

                  QName childProp = (QName)var9.next();
                  childPropNamespace = QNameUtilities.getNamespace(childProp);
                  childPropKey = childProp.getLocalPart();
                  child = copyMe._getProperty(childPropNamespace, childPropKey);
               } while(child == null);

               if (child instanceof List) {
                  List childList = (List)child;
                  ArrayList toSetChildList = new ArrayList(childList.size());
                  Iterator var33 = childList.iterator();

                  while(var33.hasNext()) {
                     Object subChild = var33.next();
                     BaseHK2JAXBBean copiedChild = doCopy((BaseHK2JAXBBean)subChild, copyController, retVal, rootHandle, referenceMap, unresolved);
                     toSetChildList.add(copiedChild);
                  }

                  retVal._setProperty(childPropNamespace, childPropKey, toSetChildList);
               } else if (!child.getClass().isArray()) {
                  BaseHK2JAXBBean copiedChild = doCopy((BaseHK2JAXBBean)child, copyController, retVal, rootHandle, referenceMap, unresolved);
                  retVal._setProperty(childPropNamespace, childPropKey, copiedChild);
               } else {
                  int length = Array.getLength(child);
                  ParentedModel pm = myModel.getChild(childPropNamespace, childPropKey);
                  ModelImpl childModel = pm.getChildModel();
                  Class childInterface = childModel.getOriginalInterfaceAsClass();
                  fromReferenceRaw = Array.newInstance(childInterface, length);

                  for(int lcv = 0; lcv < length; ++lcv) {
                     Object subChild = Array.get(child, lcv);
                     BaseHK2JAXBBean copiedChild = doCopy((BaseHK2JAXBBean)subChild, copyController, retVal, rootHandle, referenceMap, unresolved);
                     Array.set(fromReferenceRaw, lcv, copiedChild);
                  }

                  retVal._setProperty(childPropNamespace, childPropKey, fromReferenceRaw);
               }
            }
         }
      }
   }

   public static String safeString(String originalValue) {
      if (originalValue == null) {
         return null;
      } else {
         return "\u0000".equals(originalValue) ? "\\u0000" : originalValue;
      }
   }

   public static void calculateNamespaces(BaseHK2JAXBBean bean, XmlRootHandleImpl root, Map currentValues) {
      BaseHK2JAXBBean parent = (BaseHK2JAXBBean)bean._getParent();
      if (parent != null) {
         calculateNamespaces(parent, root, currentValues);
      }

      Map packageOnly;
      if (root != null) {
         packageOnly = root.getPackageNamespace(bean.getClass());
      } else {
         packageOnly = PackageToNamespaceComputable.calculateNamespaces(bean.getClass());
      }

      currentValues.putAll(packageOnly);
   }

   public static String constructXmlTag(String wrapper, String tag) {
      return wrapper == null ? tag : wrapper + "/" + tag;
   }

   static {
      DEFAULT_BOOLEAN = Boolean.FALSE;
      DEFAULT_BYTE = new Byte((byte)0);
      DEFAULT_CHARACTER = new Character('\u0000');
      DEFAULT_SHORT = new Short((short)0);
      DEFAULT_INTEGER = new Integer(0);
      DEFAULT_LONG = new Long(0L);
      DEFAULT_FLOAT = new Float(0.0F);
      DEFAULT_DOUBLE = new Double(0.0);
      ENUM_FROM_VALUE_PARAM_TYPES = new Class[]{String.class};
   }

   private static final class SpecifiedOrderComparator implements Comparator {
      private final Map specifiedOrder;
      private final Map secondarySort;
      private final NameInformation xmlMap;

      private SpecifiedOrderComparator(Map specifiedOrder, Map secondarySort, NameInformation xmlMap) {
         this.specifiedOrder = specifiedOrder;
         this.secondarySort = secondarySort;
         this.xmlMap = xmlMap;
      }

      private int secondarySort(AltMethod o1, AltMethod o2) {
         Integer p1 = (Integer)this.secondarySort.get(o1);
         Integer p2 = (Integer)this.secondarySort.get(o2);
         int pr1 = p1;
         int pr2 = p2;
         return pr2 - pr1;
      }

      public int compare(AltMethod o1, AltMethod o2) {
         if (o1.equals(o2)) {
            return 0;
         } else {
            MethodInformationI methodInfo1 = Utilities.getAndSetMethodInformation(o1, this.xmlMap);
            MethodInformationI methodInfo2 = Utilities.getAndSetMethodInformation(o2, this.xmlMap);
            String prop1 = methodInfo1.getDecapitalizedMethodProperty();
            String prop2 = methodInfo2.getDecapitalizedMethodProperty();
            if (prop1 == null && prop2 == null) {
               return this.secondarySort(o1, o2);
            } else if (prop1 != null && prop2 == null) {
               return -1;
            } else if (prop1 == null && prop2 != null) {
               return 1;
            } else {
               Integer priority1 = (Integer)this.specifiedOrder.get(prop1);
               Integer priority2 = (Integer)this.specifiedOrder.get(prop2);
               if (priority1 != null && priority2 == null) {
                  return -1;
               } else if (priority1 == null && priority2 != null) {
                  return 1;
               } else {
                  if (priority1 != null && priority2 != null) {
                     int p1 = priority1;
                     int p2 = priority2;
                     if (p1 < p2) {
                        return -1;
                     }

                     if (p1 > p2) {
                        return 1;
                     }
                  }

                  return this.secondarySort(o1, o2);
               }
            }
         }
      }

      // $FF: synthetic method
      SpecifiedOrderComparator(Map x0, Map x1, NameInformation x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
