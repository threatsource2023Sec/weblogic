package org.glassfish.hk2.xml.internal;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import org.glassfish.hk2.utilities.reflection.ClassReflectionHelper;
import org.glassfish.hk2.xml.jaxb.internal.BaseHK2JAXBBean;

public class Hk2JAXBUnmarshallerListener extends Unmarshaller.Listener {
   private final JAUtilities jaUtilities;
   private final ClassReflectionHelper classReflectionHelper;
   private final LinkedList allBeans = new LinkedList();

   Hk2JAXBUnmarshallerListener(JAUtilities jaUtilities, ClassReflectionHelper classReflectionHelper) {
      this.jaUtilities = jaUtilities;
      this.classReflectionHelper = classReflectionHelper;
   }

   private void setUserKey(BaseHK2JAXBBean bean, boolean listOrArray) {
      ModelImpl model = bean._getModel();
      QName keyProperty = model.getKeyProperty();
      if (keyProperty == null && listOrArray) {
         bean._setKeyValue(this.jaUtilities.getUniqueId());
      } else if (keyProperty != null) {
         String key = (String)bean._getProperty(QNameUtilities.getNamespace(keyProperty), keyProperty.getLocalPart());
         if (key != null) {
            bean._setKeyValue(key);
         }
      }
   }

   private void setSelfXmlTagInAllChildren(BaseHK2JAXBBean targetBean, BaseHK2JAXBBean parent) {
      ModelImpl model = targetBean._getModel();
      Iterator var4 = model.getAllChildrenDescriptors().entrySet().iterator();

      while(true) {
         while(true) {
            ParentedModel parentedNode;
            String xmlWrapperTag;
            Object children;
            do {
               String childXmlTagNamespace;
               String childXmlTag;
               label45:
               do {
                  while(var4.hasNext()) {
                     Map.Entry childDescriptorEntry = (Map.Entry)var4.next();
                     parentedNode = ((ChildDescriptor)childDescriptorEntry.getValue()).getParentedModel();
                     if (parentedNode != null) {
                        childXmlTagNamespace = parentedNode.getChildXmlNamespace();
                        childXmlTag = parentedNode.getChildXmlTag();
                        xmlWrapperTag = parentedNode.getXmlWrapperTag();
                        continue label45;
                     }

                     QName nonChildProp = (QName)childDescriptorEntry.getKey();
                     ChildDataModel cdm = ((ChildDescriptor)childDescriptorEntry.getValue()).getChildDataModel();
                     if (AliasType.IS_ALIAS.equals(cdm.getAliasType())) {
                        targetBean.__fixAlias(QNameUtilities.getNamespace(nonChildProp), nonChildProp.getLocalPart(), cdm.getXmlAlias());
                     }
                  }

                  return;
               } while(parentedNode.getAdapter() != null);

               switch (parentedNode.getAliasType()) {
                  case NORMAL:
                     children = targetBean._getProperty(childXmlTagNamespace, childXmlTag);
                     break;
                  case IS_ALIAS:
                     children = targetBean._getProperty(childXmlTagNamespace, childXmlTag);
                     targetBean.__fixAlias(childXmlTagNamespace, childXmlTag, parentedNode.getChildXmlAlias());
                     break;
                  case HAS_ALIASES:
                  default:
                     children = null;
               }
            } while(children == null);

            String proxyName = Utilities.getProxyNameFromInterfaceName(parentedNode.getChildInterface());
            if (children instanceof List) {
               Iterator var20 = ((List)children).iterator();

               while(var20.hasNext()) {
                  Object child = var20.next();
                  if (child.getClass().getName().equals(proxyName)) {
                     BaseHK2JAXBBean childBean = (BaseHK2JAXBBean)child;
                     childBean._setSelfXmlTag(parentedNode.getChildXmlNamespace(), Utilities.constructXmlTag(xmlWrapperTag, parentedNode.getChildXmlTag()));
                     this.setUserKey(childBean, true);
                  }
               }
            } else if (children.getClass().isArray()) {
               Object[] var19 = (Object[])((Object[])children);
               int var13 = var19.length;

               for(int var14 = 0; var14 < var13; ++var14) {
                  Object child = var19[var14];
                  BaseHK2JAXBBean childBean = (BaseHK2JAXBBean)child;
                  childBean._setSelfXmlTag(parentedNode.getChildXmlNamespace(), Utilities.constructXmlTag(xmlWrapperTag, parentedNode.getChildXmlTag()));
                  this.setUserKey(childBean, true);
               }
            } else {
               BaseHK2JAXBBean childBean = (BaseHK2JAXBBean)children;
               childBean._setSelfXmlTag(parentedNode.getChildXmlNamespace(), Utilities.constructXmlTag(xmlWrapperTag, parentedNode.getChildXmlTag()));
               this.setUserKey(childBean, false);
            }
         }
      }
   }

   public void afterUnmarshal(Object target, Object parent) {
      if (target instanceof BaseHK2JAXBBean) {
         BaseHK2JAXBBean targetBean = (BaseHK2JAXBBean)target;
         BaseHK2JAXBBean parentBean = (BaseHK2JAXBBean)parent;
         ModelImpl targetNode = targetBean._getModel();
         this.allBeans.add(targetBean);
         if (parentBean == null) {
            QName rootName = targetNode.getRootName();
            targetBean._setSelfXmlTag(QNameUtilities.getNamespace(rootName), rootName.getLocalPart());
         }

         this.setSelfXmlTagInAllChildren(targetBean, parentBean);
      }
   }

   public void beforeUnmarshal(Object target, Object parent) {
      if (target instanceof BaseHK2JAXBBean) {
         if (parent == null || parent instanceof BaseHK2JAXBBean) {
            BaseHK2JAXBBean targetBean = (BaseHK2JAXBBean)target;
            BaseHK2JAXBBean parentBean = (BaseHK2JAXBBean)parent;
            targetBean._setClassReflectionHelper(this.classReflectionHelper);
            targetBean._setParent(parentBean);
         }
      }
   }

   LinkedList getAllBeans() {
      return this.allBeans;
   }
}
