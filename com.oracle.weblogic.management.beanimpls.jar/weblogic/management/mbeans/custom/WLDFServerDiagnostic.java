package weblogic.management.mbeans.custom;

import java.util.ArrayList;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.runtimecontrol.BuiltinSRDescriptorBeanHolder;
import weblogic.management.configuration.WLDFDataRetirementMBean;
import weblogic.management.configuration.WLDFServerDiagnosticMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.management.provider.internal.DescriptorInfoUtils;
import weblogic.utils.ArrayUtils;

public final class WLDFServerDiagnostic extends ConfigurationMBeanCustomizer {
   private static final long serialVersionUID = 6775637305805210195L;

   public WLDFServerDiagnostic(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public WLDFDataRetirementMBean[] getWLDFDataRetirements() {
      WLDFServerDiagnosticMBean mbean = (WLDFServerDiagnosticMBean)this.getMbean();
      ArrayList list = new ArrayList();
      addAll(list, mbean.getWLDFDataRetirementByAges());
      WLDFDataRetirementMBean[] arr = new WLDFDataRetirementMBean[list.size()];
      arr = (WLDFDataRetirementMBean[])((WLDFDataRetirementMBean[])list.toArray(arr));
      return arr;
   }

   public WLDFDataRetirementMBean lookupWLDFDataRetirement(String name) {
      WLDFServerDiagnosticMBean mbean = (WLDFServerDiagnosticMBean)this.getMbean();
      WLDFDataRetirementMBean retirement = null;
      retirement = mbean.lookupWLDFDataRetirementByAge(name);
      return retirement != null ? retirement : null;
   }

   private static void addAll(ArrayList list, WLDFDataRetirementMBean[] elements) {
      if (elements != null && elements.length > 0) {
         ArrayUtils.addAll(list, elements);
      }

   }

   public WLDFResourceBean getWLDFBuiltinSystemResourceDescriptorBean() {
      String resourceType = ((WLDFServerDiagnosticMBean)this.getMbean()).getWLDFBuiltinSystemResourceType();
      if (resourceType.equals("None")) {
         return null;
      } else {
         WLDFResourceBean bean = BuiltinSRDescriptorBeanHolder.getInstance().getBuiltinSRDescriptorBean();
         if (bean == null) {
            return null;
         } else {
            AbstractDescriptorBean abstractBean = (AbstractDescriptorBean)bean;
            Descriptor rootDescriptor = abstractBean.getDescriptor();
            WLDFServerDiagnosticMBean diag = (WLDFServerDiagnosticMBean)this.getMbean();
            DescriptorInfoUtils.setDescriptorConfigExtension(rootDescriptor, diag, "WLDFResource");
            return bean;
         }
      }
   }
}
