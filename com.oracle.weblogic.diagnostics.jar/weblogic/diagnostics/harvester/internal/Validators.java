package weblogic.diagnostics.harvester.internal;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.management.DynamicMBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.descriptor.WLDFHarvestedTypeBean;
import weblogic.diagnostics.harvester.AttributeNameNormalizer;
import weblogic.diagnostics.harvester.HarvesterException;
import weblogic.diagnostics.harvester.I18NConstants;
import weblogic.diagnostics.harvester.I18NSupport;
import weblogic.diagnostics.harvester.InstanceNameNormalizer;
import weblogic.diagnostics.harvester.InvalidHarvesterInstanceNameException;
import weblogic.diagnostics.harvester.WLDFHarvesterUtils;
import weblogic.management.jmx.modelmbean.WLSModelMBean;

public class Validators implements I18NConstants {
   private static final DebugLogger DBG = DebugSupport.getDebugLogger();
   private static final DebugLogger DBG2 = DebugSupport.getLowLevelDebugLogger();
   private static final HarvesterDefaultAttributeNormalizer DEFAULT_NORMALIZER = new HarvesterDefaultAttributeNormalizer();

   public static void validateNamespace(String namespace) throws IllegalArgumentException {
      if (!namespace.equals("ServerRuntime") && !namespace.equals("DomainRuntime")) {
         throw new IllegalArgumentException(namespace);
      }
   }

   public static String getDefaultMetricNamespace() {
      return "ServerRuntime";
   }

   public static void validateHarvestedTypeBean(WLDFHarvestedTypeBean bean) throws IllegalArgumentException {
      String typeName = bean.getName();
      if (DBG.isDebugEnabled()) {
         DBG.debug("Doing bean-level velidation for configured type: " + typeName);
      }

      if (WLDFHarvesterUtils.isHarvesterValidationAvailable()) {
         int vote = isTypeHandled(typeName);
         if (bean.isKnownType() && vote != 2) {
            throw new IllegalArgumentException(new HarvesterException.TypeNotHarvestable(typeName));
         }
      }
   }

   public static void validateConfiguredType(String typeName) throws IllegalArgumentException {
      if (DBG.isDebugEnabled()) {
         DBG.debug("Validating configured type name: " + typeName);
      }

      if (WLDFHarvesterUtils.isHarvesterValidationAvailable()) {
         if (isTypeHandled(typeName) == -1) {
            throw new IllegalArgumentException(new HarvesterException.TypeNotHarvestable(typeName));
         }
      }
   }

   public static void validateConfiguredAttributes(String typeName, String[] attributes) throws IllegalArgumentException {
      if (DBG.isDebugEnabled()) {
         DBG.debug("Validating configured attributes for type " + typeName + ":  " + stringArraytoString(attributes));
      }

      if (WLDFHarvesterUtils.isHarvesterValidationAvailable()) {
         if (attributes != null && attributes.length != 0) {
            List invalidAttributes = new ArrayList();
            BeanInfo beanInfo = TreeBeanHarvestableDataProviderHelper.getBeanInfo(typeName);
            String attr;
            if (beanInfo == null) {
               if (DBG.isDebugEnabled()) {
                  DBG.debug("Unknown type, applying default normalizer");
               }

               String[] var4 = attributes;
               int var5 = attributes.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  String attribute = var4[var6];
                  if (attribute != null && attribute.length() > 0) {
                     try {
                        attr = DEFAULT_NORMALIZER.getNormalizedAttributeName(attribute);
                        if (DBG.isDebugEnabled()) {
                           DBG.debug("Normalizing " + attribute + ", result:  " + attr);
                        }
                     } catch (Exception var14) {
                        invalidAttributes.add(attribute);
                     }
                  }
               }
            } else {
               if (DBG.isDebugEnabled()) {
                  DBG.debug("Known type, looking up and applying any normalizers");
               }

               PropertyDescriptor[] attrDescrs = beanInfo.getPropertyDescriptors();
               Set simpleAttributes = new HashSet();
               Map complexAttributes = new HashMap();

               String s;
               int i;
               for(i = 0; i < attrDescrs.length; ++i) {
                  PropertyDescriptor attrDescr = attrDescrs[i];
                  Boolean isUnharvestableB = (Boolean)attrDescr.getValue("unharvestable");
                  boolean isHarvestable = isUnharvestableB != null ? !isUnharvestableB : true;
                  if (isHarvestable) {
                     String name = attrDescr.getName();
                     if (!typeIsComplex(attrDescr)) {
                        simpleAttributes.add(name);
                     } else {
                        s = (String)attrDescr.getValue("harvesterAttributeNormalizerClass");
                        if (s == null) {
                           s = HarvesterDefaultAttributeNormalizer.class.getName();
                        }

                        complexAttributes.put(name, s);
                     }
                  }
               }

               for(i = 0; i < attributes.length; ++i) {
                  attr = attributes[i];
                  if (attr != null && attr.length() != 0 && !simpleAttributes.contains(attr)) {
                     String attrSpec = DEFAULT_NORMALIZER.getAttributeName(attr);
                     String attrNormalizerClassName = (String)complexAttributes.get(attrSpec);
                     if (attrNormalizerClassName == null) {
                        invalidAttributes.add(attr);
                     } else {
                        try {
                           AttributeNameNormalizer normalizer = (AttributeNameNormalizer)Class.forName(attrNormalizerClassName).newInstance();
                           s = normalizer.getNormalizedAttributeName(attr);
                           if (DBG.isDebugEnabled()) {
                              DBG.debug("The normalized name is: " + attr + ":  " + s);
                           }
                        } catch (Exception var13) {
                           invalidAttributes.add(attr);
                        }
                     }
                  }
               }
            }

            if (invalidAttributes.size() > 0) {
               if (DBG.isDebugEnabled()) {
                  DBG.debug("The following attributes are invalid for type: " + typeName + ":  " + invalidAttributes);
               }

               throw new ValidationError(I18NSupport.formatter().getNotHarvestableMessage(ATTRIBUTES_I18N, typeName, invalidAttributes.toString()));
            } else {
               if (DBG2.isDebugEnabled()) {
                  DBG2.debug("Attributes for type: " + typeName + " have been successfully validated.");
               }

            }
         }
      }
   }

   private static boolean typeIsComplex(PropertyDescriptor attrDescr) {
      Class type = attrDescr.getPropertyType();
      if (type.isPrimitive()) {
         return false;
      } else if (type != String.class && type != Boolean.class && type != Character.class) {
         return !Number.class.isAssignableFrom(type);
      } else {
         return false;
      }
   }

   public static void validateConfiguredInstances(String[] instances) throws IllegalArgumentException {
      if (DBG.isDebugEnabled()) {
         DBG.debug("Validating configured instances:  " + stringArraytoString(instances));
      }

      if (WLDFHarvesterUtils.isHarvesterValidationAvailable()) {
         String[] var1 = instances;
         int var2 = instances.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            String instance = var1[var3];
            InstanceNameNormalizer normalizer = new InstanceNameNormalizer(instance);

            try {
               normalizer.translateHarvesterSpec();
            } catch (InvalidHarvesterInstanceNameException var7) {
               throw new IllegalArgumentException(var7);
            }
         }

         if (DBG2.isDebugEnabled()) {
            DBG2.debug("Instances have been successfully validated.");
         }

      }
   }

   private static String stringArraytoString(String[] sa) {
      if (sa == null) {
         return "[]";
      } else {
         String wk = "[";

         for(int i = 0; i < sa.length; ++i) {
            String thisEntry = sa[i];
            if (i != 0) {
               wk = wk + ",";
            }

            wk = wk + thisEntry;
         }

         wk = wk + "]";
         return wk;
      }
   }

   private static int isTypeHandled(String typeName) {
      int retVal = false;
      if (typeName != null && typeName.length() != 0) {
         BeanTreeHarvesterImpl bth = BeanTreeHarvesterImpl.getInstance();
         int retVal = bth.isTypeHandled(typeName);
         if (retVal != 2) {
            Class c = null;

            try {
               c = Class.forName(typeName);
            } catch (ClassNotFoundException var6) {
               retVal = 1;
            }

            if (c != null) {
               boolean isWeblogic = WLSModelMBean.class.isAssignableFrom(c);
               if (isWeblogic) {
                  retVal = 2;
               } else {
                  boolean isDynamicMBean = DynamicMBean.class.isAssignableFrom(c);
                  if (isDynamicMBean) {
                     retVal = 1;
                  } else if (!isStandardMBean(c) && !isMXBean(c)) {
                     retVal = -1;
                  } else {
                     retVal = 1;
                  }
               }
            }
         }

         if (DBG.isDebugEnabled()) {
            DBG.debug("Validator has has voted " + (retVal == 2 ? "yes" : (retVal == -1 ? "no" : "maybe")) + " for type " + typeName);
         }

         return retVal;
      } else {
         throw new ValidationError(I18NSupport.formatter().getNullParamMessage(TYPE_I18N));
      }
   }

   private static boolean isStandardMBean(Class c) {
      String standardMBeanIntfClassName = c.getName() + "MBean";

      try {
         c = Class.forName(standardMBeanIntfClassName);
         if (c.isInterface()) {
            return true;
         }
      } catch (ClassNotFoundException var3) {
      }

      return false;
   }

   private static boolean isMXBean(Class c) {
      Class[] interfaces = c.getInterfaces();
      Class[] var2 = interfaces;
      int var3 = interfaces.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class intf = var2[var4];
         if (intf.getCanonicalName().endsWith("MXBean")) {
            return true;
         }
      }

      return false;
   }

   public static class ValidationError extends IllegalArgumentException {
      private static final long serialVersionUID = 1L;

      public ValidationError(String msg) {
         super(msg);
      }
   }
}
