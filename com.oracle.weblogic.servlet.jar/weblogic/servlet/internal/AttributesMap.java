package weblogic.servlet.internal;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.servlet.HTTPLogger;
import weblogic.utils.classloaders.ClassLoaderUtils;

class AttributesMap {
   static final String REQUEST = "request";
   static final String CONTEXT = "servlet-context";
   private final Map attributes;
   private final String scope;

   AttributesMap(String s) {
      this.scope = s;
      if (this.scope == "servlet-context") {
         this.attributes = new ConcurrentHashMap();
      } else {
         this.attributes = new HashMap();
      }

   }

   Object put(String name, Object value, WebAppServletContext context) {
      this.checkNullName(name);
      if (value instanceof Serializable && AttributeWrapper.needToWrap(value)) {
         if (this.scope == "servlet-context") {
            value = new ContextAttributeWrapper(value, context);
         } else {
            value = new AttributeWrapper(value, context);
         }
      }

      return this.attributes.put(name, value);
   }

   public void put(String name, Object value) {
      this.checkNullName(name);
      this.attributes.put(name, value);
   }

   Object get(String name, WebAppServletContext context) {
      this.checkNullName(name);
      Object obj = this.attributes.get(name);
      if (!(obj instanceof AttributeWrapper)) {
         return obj;
      } else {
         AttributeWrapper w = (AttributeWrapper)obj;

         try {
            return w.getObject(context);
         } catch (ClassNotFoundException var6) {
            HTTPLogger.logFailedToDeserializeAttribute(context.getLogContext(), this.scope, name, var6);
         } catch (IOException var7) {
            HTTPLogger.logFailedToDeserializeAttribute(context.getLogContext(), this.scope, name, var7);
         } catch (RuntimeException var8) {
            HTTPLogger.logFailedToDeserializeAttribute(context.getLogContext(), this.scope, name, var8);
         }

         return null;
      }
   }

   Object remove(String name) {
      this.checkNullName(name);
      return this.attributes.remove(name);
   }

   Iterator keys() {
      return this.attributes.keySet().iterator();
   }

   void clear() {
      this.attributes.clear();
   }

   boolean isEmpty() {
      return this.attributes.isEmpty();
   }

   synchronized void removeTransientAttributes(ClassLoader oldWebAppCL, WebAppServletContext context) {
      if (!this.isEmpty()) {
         int webappCLHash = oldWebAppCL.hashCode();
         List removedAttrList = new ArrayList();
         Iterator var5 = this.attributes.keySet().iterator();

         while(true) {
            String attrName;
            ClassLoader valueCL;
            do {
               label52:
               do {
                  while(var5.hasNext()) {
                     attrName = (String)var5.next();
                     Object attrValue = this.attributes.get(attrName);
                     if (!(attrValue instanceof AttributeWrapper)) {
                        valueCL = attrValue.getClass().getClassLoader();
                        continue label52;
                     }

                     try {
                        ((AttributeWrapper)attrValue).convertToBytes();
                     } catch (IOException var11) {
                        try {
                           HTTPLogger.logRemoveNonSerializableAttributeForReload("servlet-context", attrName, ((AttributeWrapper)attrValue).getObject(false, context).getClass().getName());
                        } catch (Throwable var10) {
                        }

                        removedAttrList.add(attrName);
                     }
                  }

                  if (!removedAttrList.isEmpty()) {
                     var5 = removedAttrList.iterator();

                     while(var5.hasNext()) {
                        attrName = (String)var5.next();
                        context.removeAttribute(attrName);
                     }
                  }

                  return;
               } while(valueCL == null);
            } while(valueCL != oldWebAppCL && !ClassLoaderUtils.isChild(webappCLHash, valueCL));

            removedAttrList.add(attrName);
         }
      }
   }

   private void checkNullName(String name) {
      if (name == null) {
         throw new IllegalArgumentException("Attribute name can NOT be null.");
      }
   }
}
