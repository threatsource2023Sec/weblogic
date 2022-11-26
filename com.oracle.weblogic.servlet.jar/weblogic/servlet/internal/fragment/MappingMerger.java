package weblogic.servlet.internal.fragment;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.j2ee.descriptor.FilterMappingBean;
import weblogic.j2ee.descriptor.ServletMappingBean;
import weblogic.j2ee.descriptor.WebAppBaseBean;
import weblogic.j2ee.descriptor.WebAppBean;

public class MappingMerger extends AbstractMerger {
   public boolean accept(DescriptorBean bean, BeanUpdateEvent.PropertyUpdate update) {
      return bean instanceof ServletMappingBean || bean instanceof FilterMappingBean || update.getAddedObject() instanceof ServletMappingBean || update.getAddedObject() instanceof FilterMappingBean;
   }

   protected void handleAddEvent(DescriptorBean targetBean, DescriptorBean sourceBean, DescriptorBean proposedBean, BeanUpdateEvent.PropertyUpdate update) throws MergeException {
      String property = update.getPropertyName();
      Object updatedValue = update.getAddedObject();
      property = singularizeProperty(property);
      if (updatedValue instanceof ServletMappingBean || updatedValue instanceof FilterMappingBean) {
         addChildBean(targetBean, property, updatedValue);
      }

   }

   protected void handleChangeEvent(DescriptorBean targetBean, DescriptorBean sourceBean, DescriptorBean proposedBean, BeanUpdateEvent.PropertyUpdate update) throws MergeException {
      String property = update.getPropertyName();
      if ("ServletName".equals(property)) {
         DescriptorBean targetBean = (AbstractDescriptorBean)((AbstractDescriptorBean)targetBean).getParentBean();
         DescriptorBean sourceBean = (AbstractDescriptorBean)((AbstractDescriptorBean)sourceBean).getParentBean();
         String servletName = (String)getProperty(proposedBean, update.getPropertyName());
         if (!isServletMappingExist(sourceBean, servletName)) {
            addChildBean(targetBean, "ServletMapping", proposedBean);
         }
      }

   }

   private static boolean isServletMappingExist(DescriptorBean bean, String servletName) {
      assert bean instanceof WebAppBaseBean;

      WebAppBean wab = (WebAppBean)bean;
      ServletMappingBean[] mappings = wab.getServletMappings();
      ServletMappingBean[] var4 = mappings;
      int var5 = mappings.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ServletMappingBean mapping = var4[var6];
         if (servletName.equals(mapping.getServletName())) {
            return true;
         }
      }

      return false;
   }
}
