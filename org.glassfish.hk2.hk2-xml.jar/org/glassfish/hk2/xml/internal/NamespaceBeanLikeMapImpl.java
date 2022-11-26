package org.glassfish.hk2.xml.internal;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import org.glassfish.hk2.xml.jaxb.internal.NamespaceBeanLikeMap;

public class NamespaceBeanLikeMapImpl implements NamespaceBeanLikeMap, Serializable {
   private static final long serialVersionUID = 7351909351649012181L;
   private Map namespaceMap = new LinkedHashMap();
   private Map backupMap;

   public NamespaceBeanLikeMapImpl() {
      this.namespaceMap.put("##default", new LinkedHashMap());
   }

   private static Map deepCopyNamespaceBeanLikeMaps(Map copyMe) {
      Map retVal = new LinkedHashMap();
      if (copyMe == null) {
         return retVal;
      } else {
         Iterator var2 = copyMe.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            String namespace = (String)entry.getKey();
            Map blm = (Map)entry.getValue();
            retVal.put(namespace, new LinkedHashMap(blm));
         }

         return retVal;
      }
   }

   public Object getValue(String namespace, String key) {
      namespace = QNameUtilities.fixNamespace(namespace);
      Map nMap = (Map)this.namespaceMap.get(namespace);
      return nMap == null ? null : nMap.get(key);
   }

   public void setValue(String namespace, String key, Object value) {
      namespace = QNameUtilities.fixNamespace(namespace);
      Map narrowedMap = (Map)this.namespaceMap.get(namespace);
      if (narrowedMap == null) {
         narrowedMap = new LinkedHashMap();
         this.namespaceMap.put(namespace, narrowedMap);
      }

      ((Map)narrowedMap).put(key, value);
   }

   public boolean isSet(String namespace, String key) {
      namespace = QNameUtilities.fixNamespace(namespace);
      Map narrowedMap = (Map)this.namespaceMap.get(namespace);
      return narrowedMap == null ? false : narrowedMap.containsKey(key);
   }

   public void backup() {
      if (this.backupMap == null) {
         this.backupMap = deepCopyNamespaceBeanLikeMaps(this.namespaceMap);
      }
   }

   public void restoreBackup(boolean drop) {
      try {
         if (this.backupMap == null) {
            return;
         }

         if (!drop) {
            this.namespaceMap = this.backupMap;
            return;
         }
      } finally {
         this.backupMap = null;
      }

   }

   public Map getBeanLikeMap(Map namespaceToPrefixMap) {
      LinkedHashMap retVal = new LinkedHashMap();
      Iterator var3 = this.namespaceMap.entrySet().iterator();

      while(true) {
         Map blm;
         boolean addNamespace;
         String prefix;
         do {
            if (!var3.hasNext()) {
               return retVal;
            }

            Map.Entry outerEntry = (Map.Entry)var3.next();
            String namespace = (String)outerEntry.getKey();
            blm = (Map)outerEntry.getValue();
            addNamespace = !"##default".equals(namespace);
            prefix = addNamespace ? (String)namespaceToPrefixMap.get(namespace) : null;
         } while(addNamespace && prefix == null);

         String key;
         Object value;
         for(Iterator var9 = blm.entrySet().iterator(); var9.hasNext(); retVal.put(key, value)) {
            Map.Entry innerEntry = (Map.Entry)var9.next();
            value = innerEntry.getValue();
            if (addNamespace) {
               key = prefix + ":" + (String)innerEntry.getKey();
            } else {
               key = (String)innerEntry.getKey();
            }
         }
      }
   }

   public void shallowCopy(NamespaceBeanLikeMap copyFrom, ModelImpl copyModel, boolean copyReferences) {
      Iterator var4 = copyFrom.getNamespaceBeanLikeMap().entrySet().iterator();

      label41:
      while(var4.hasNext()) {
         Map.Entry outerEntry = (Map.Entry)var4.next();
         String copyNamespace = (String)outerEntry.getKey();
         Map copyBeanLikeMap = (Map)outerEntry.getValue();
         Iterator var8 = copyBeanLikeMap.entrySet().iterator();

         while(true) {
            Map.Entry entrySet;
            String xmlTag;
            ChildDataModel cdm;
            do {
               QName childQName;
               do {
                  do {
                     if (!var8.hasNext()) {
                        continue label41;
                     }

                     entrySet = (Map.Entry)var8.next();
                     xmlTag = (String)entrySet.getKey();
                     childQName = QNameUtilities.createQName(copyNamespace, xmlTag);
                  } while(copyModel.getKeyedChildren().contains(childQName));
               } while(copyModel.getUnKeyedChildren().contains(childQName));

               cdm = (ChildDataModel)copyModel.getNonChildProperties().get(childQName);
            } while(!copyReferences && cdm != null && cdm.isReference());

            this.setValue(copyNamespace, xmlTag, entrySet.getValue());
         }
      }

   }

   public Map getNamespaceBeanLikeMap() {
      return this.namespaceMap;
   }

   public Map getQNameMap() {
      Map retVal = new LinkedHashMap();
      Iterator var2 = this.namespaceMap.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry outerEntry = (Map.Entry)var2.next();
         String namespace = (String)outerEntry.getKey();
         Map innerMap = (Map)outerEntry.getValue();
         Iterator var6 = innerMap.entrySet().iterator();

         while(var6.hasNext()) {
            Map.Entry innerEntry = (Map.Entry)var6.next();
            QName key = QNameUtilities.createQName(namespace, (String)innerEntry.getKey());
            Object value = innerEntry.getValue();
            retVal.put(key, value);
         }
      }

      return retVal;
   }

   public String toString() {
      return "NamespaceBeanLikeMapImpl(" + System.identityHashCode(this) + ")";
   }
}
