package weblogic.management.provider.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.DescriptorImpl;
import weblogic.diagnostics.debug.DebugLogger;

public final class DescriptorInfoUtils {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");

   public static void addDescriptorInfo(DescriptorInfo descInfo, DescriptorImpl rootDesc) {
      String key = ((AbstractDescriptorBean)descInfo.getConfigurationExtension())._isTransient() ? "TransientDescriptorInfo" : "DescriptorInfo";
      Map context = rootDesc.getContext();
      List descList = (List)context.get(key);
      if (descList == null) {
         descList = new ArrayList();
         context.put(key, descList);
      }

      ((List)descList).add(descInfo);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Added descriptor info to " + key + " desc: " + descInfo.getDescriptorBean());
      }

   }

   public static DescriptorInfo removeDescriptorInfo(DescriptorBean externalDescBean, Descriptor rootDesc) {
      if (externalDescBean == null) {
         return null;
      } else {
         boolean isTransient = ((AbstractDescriptorBean)externalDescBean)._isTransient();
         String key = isTransient ? "TransientDescriptorInfo" : "DescriptorInfo";
         Map context = ((DescriptorImpl)rootDesc).getContext();
         List descList = (List)context.get(key);
         if (descList == null) {
            throw new AssertionError("No DescriptorInfo list");
         } else {
            Iterator it = descList.iterator();

            DescriptorInfo descInfo;
            Descriptor externalDesc;
            do {
               if (!it.hasNext()) {
                  return null;
               }

               descInfo = (DescriptorInfo)it.next();
               externalDesc = descInfo.getDescriptor();
            } while(externalDesc == null || !externalDesc.equals(externalDescBean.getDescriptor()));

            it.remove();
            if (!isTransient) {
               addDeletedDescriptorInfo(descInfo, (DescriptorImpl)rootDesc);
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Removed descriptor info " + descInfo.getDescriptorBean());
               }
            }

            return descInfo;
         }
      }
   }

   public static Iterator getDescriptorInfos(Descriptor rootDesc) {
      Map context = ((DescriptorImpl)rootDesc).getContext();
      List descList = (List)context.get("DescriptorInfo");
      return descList == null ? null : descList.iterator();
   }

   public static void addDeletedDescriptorInfo(DescriptorInfo descInfo, DescriptorImpl rootDesc) {
      Map context = rootDesc.getContext();
      List descList = (List)context.get("DeletedDescriptorInfo");
      if (descList == null) {
         descList = new ArrayList();
         context.put("DeletedDescriptorInfo", descList);
      }

      ((List)descList).add(descInfo);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Added deleted descriptor info " + descInfo.getDescriptorBean());
      }

   }

   public static void removeAllDeletedDescriptorInfos(Descriptor rootDesc) {
      Map context = ((DescriptorImpl)rootDesc).getContext();
      context.put("DeletedDescriptorInfo", (Object)null);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Clear deleted deleted descriptors");
      }

   }

   public static Iterator getDeletedDescriptorInfos(Descriptor rootDesc) {
      Map context = ((DescriptorImpl)rootDesc).getContext();
      List descList = (List)context.get("DeletedDescriptorInfo");
      return descList == null ? null : descList.iterator();
   }

   public static void addNotFoundDescriptorInfo(DescriptorInfo descInfo, DescriptorImpl rootDesc) {
      Map context = rootDesc.getContext();
      List descList = (List)context.get("NotFoundDescriptorInfo");
      if (descList == null) {
         descList = new ArrayList();
         context.put("NotFoundDescriptorInfo", descList);
      }

      ((List)descList).add(descInfo);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Added not found descriptor info " + descInfo.getDescriptorBean());
      }

   }

   public static Iterator getNotFoundDescriptorInfos(Descriptor rootDesc) {
      Map context = ((DescriptorImpl)rootDesc).getContext();
      List descList = (List)context.get("NotFoundDescriptorInfo");
      return descList == null ? null : descList.iterator();
   }

   public static Iterator getTransientDescriptorInfos(Descriptor rootDesc) {
      Map context = ((DescriptorImpl)rootDesc).getContext();
      List descList = (List)context.get("TransientDescriptorInfo");
      return descList == null ? null : descList.iterator();
   }

   public static boolean getDescriptorLoadExtensions(Descriptor rootDesc) {
      Map context = ((DescriptorImpl)rootDesc).getContext();
      Boolean load = (Boolean)context.get("DescriptorExtensionLoad");
      return load == null ? true : load;
   }

   public static void setDescriptorLoadExtensions(Descriptor rootDesc, boolean load) {
      Map context = ((DescriptorImpl)rootDesc).getContext();
      Boolean extensionLoad = new Boolean(load);
      context.put("DescriptorExtensionLoad", extensionLoad);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Set descriptor load extension " + load);
      }

   }

   public static void setExtensionTemporaryFiles(Descriptor rootDesc, Map temporaryFiles) {
      Map context = ((DescriptorImpl)rootDesc).getContext();
      context.put("ExtensionTemporaryFiles", temporaryFiles);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Set descriptor extension temporary files " + temporaryFiles);
      }

   }

   public static Map getExtensionTemporaryFiles(Descriptor rootDesc) {
      Map context = ((DescriptorImpl)rootDesc).getContext();
      return (Map)context.get("ExtensionTemporaryFiles");
   }

   public static Object getDescriptorConfigExtension(Descriptor rootDesc) {
      Map context = ((DescriptorImpl)rootDesc).getContext();
      Object extension = context.get("DescriptorConfigExtension");
      return extension;
   }

   public static void setDescriptorConfigExtension(Descriptor rootDesc, Object extension, String extensionAttributeName) {
      Map context = ((DescriptorImpl)rootDesc).getContext();
      context.put("DescriptorConfigExtension", extension);
      context.put("DescriptorConfigExtensionAttribute", extensionAttributeName);
   }
}
