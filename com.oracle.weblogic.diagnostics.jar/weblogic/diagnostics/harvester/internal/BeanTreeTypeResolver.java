package weblogic.diagnostics.harvester.internal;

import com.bea.adaptive.harvester.jmx.AttributeSpec;
import com.bea.adaptive.harvester.jmx.AttributeTerm;
import com.bea.adaptive.harvester.jmx.MetricMetaDataResolver;
import com.bea.adaptive.harvester.jmx.MetricTypeInfo;
import com.bea.adaptive.harvester.jmx.RegistrationManager;
import java.beans.BeanInfo;
import java.beans.FeatureDescriptor;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import weblogic.diagnostics.debug.DebugLogger;

class BeanTreeTypeResolver implements MetricMetaDataResolver {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticsHarvesterTreeBeanPlugin");
   private String diagUnharvestableTag;
   private String[] otherUnharvestableDescriptorTags;

   public BeanTreeTypeResolver(String unharvestableTag, String[] otherUnharvestableTags) {
      this.diagUnharvestableTag = unharvestableTag;
      this.otherUnharvestableDescriptorTags = otherUnharvestableTags;
   }

   public String getTypeForInstance(String instanceName) {
      String typeName = null;

      try {
         typeName = TreeBeanHarvestableDataProviderHelper.getTypeNameForInstance(instanceName);
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug(instanceName + " resolves to type " + typeName);
         }
      } catch (Throwable var4) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Error resolving type name for " + instanceName, var4);
         }
      }

      return typeName;
   }

   public List getHarvestableAttributesForInstance(String instName, String type) {
      return this.getHarvestableAttributesSpecs(instName, type, (String)null);
   }

   protected List getHarvestableAttributesSpecs(String instance, String typeName, String attrNameRegex) {
      List specs = null;
      Pattern pat = attrNameRegex != null ? Pattern.compile(attrNameRegex) : null;
      BeanInfo bi = BeanTreeRegistrationManager.getBeanInfoForInterface(typeName);
      if (bi == null) {
         return null;
      } else {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Checking attributes for type " + typeName);
         }

         String typePartitionVisibilityTag = this.getPartitionVisibilityTag(bi);
         PropertyDescriptor[] propDesriptors = bi.getPropertyDescriptors();
         int length = propDesriptors != null ? propDesriptors.length : 0;
         specs = new ArrayList(length);

         for(int i = 0; i < length; ++i) {
            PropertyDescriptor pd = propDesriptors[i];
            String attrName = pd.getName();
            String attributePartitionVisibilityTag = this.getPartitionVisibilityTag((FeatureDescriptor)pd);
            boolean visibleToPartitions = RegistrationManager.isAttributeVisibleToPartitions(typePartitionVisibilityTag, attributePartitionVisibilityTag);
            boolean unharvestable = this.isUnharvestable(attrName, pd);
            if (DEBUG.isDebugEnabled()) {
               if (unharvestable) {
                  DEBUG.debug("\tAttribute " + attrName + " is unharvestable");
               } else {
                  DEBUG.debug("\tAttribute " + attrName + " is harvestable");
               }
            }

            if (!unharvestable && (pat == null || pat.matcher(attrName).matches())) {
               String attrTypeName = pd.getPropertyType().getName();
               String attrDescr = pd.getShortDescription();
               AttributeSpec spec = new AttributeSpec(attrName, typeName, attrTypeName, visibleToPartitions, new AttributeTerm.SimpleTerm(attrName, (AttributeTerm)null));
               spec.setAttributeDescription(attrDescr);
               spec.setReadMethod(pd.getReadMethod());
               specs.add(spec);
            }
         }

         return specs;
      }
   }

   protected String getPartitionVisibilityTag(BeanInfo bi) {
      String typeVisibilityTag = null;
      Object interfaceName = bi.getBeanDescriptor().getValue("interfaceclassname");
      if (interfaceName != null && interfaceName instanceof String && MetricTypeInfo.isTypeImplicitlyPartitionVisible((String)interfaceName)) {
         typeVisibilityTag = "ALWAYS";
      }

      if (typeVisibilityTag == null) {
         typeVisibilityTag = this.getPartitionVisibilityTag((FeatureDescriptor)bi.getBeanDescriptor());
      }

      return typeVisibilityTag;
   }

   protected String getPartitionVisibilityTag(FeatureDescriptor fd) {
      String tag = "NONE";
      Object value = fd.getValue("VisibleToPartitions");
      if (value != null && value instanceof String) {
         tag = (String)value;
      }

      return tag;
   }

   private boolean isUnharvestable(String attrName, PropertyDescriptor pd) {
      for(int i = 0; i < this.otherUnharvestableDescriptorTags.length; ++i) {
         String tagName = this.otherUnharvestableDescriptorTags[i];
         Boolean value = (Boolean)pd.getValue(tagName);
         if (value != null && (tagName.equals(this.diagUnharvestableTag) || value)) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("\tAttribute " + attrName + " tagged with @" + tagName);
            }

            return true;
         }
      }

      return false;
   }
}
