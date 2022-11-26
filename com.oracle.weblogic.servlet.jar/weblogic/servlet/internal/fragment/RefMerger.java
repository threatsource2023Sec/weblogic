package weblogic.servlet.internal.fragment;

import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.EjbLocalRefBean;
import weblogic.j2ee.descriptor.EjbRefBean;
import weblogic.j2ee.descriptor.EnvEntryBean;
import weblogic.j2ee.descriptor.InjectionTargetBean;
import weblogic.j2ee.descriptor.MessageDestinationRefBean;
import weblogic.j2ee.descriptor.PersistenceContextRefBean;
import weblogic.j2ee.descriptor.PersistenceUnitRefBean;
import weblogic.j2ee.descriptor.ResourceEnvRefBean;
import weblogic.j2ee.descriptor.ResourceRefBean;
import weblogic.j2ee.descriptor.ServiceRefBean;

public class RefMerger extends AbstractMerger {
   public boolean accept(DescriptorBean bean, BeanUpdateEvent.PropertyUpdate update) {
      return this.isRefBean(bean);
   }

   private boolean isRefBean(Object bean) {
      return bean instanceof EnvEntryBean || bean instanceof EjbRefBean || bean instanceof EjbLocalRefBean || bean instanceof ServiceRefBean || bean instanceof ResourceRefBean || bean instanceof ResourceEnvRefBean || bean instanceof MessageDestinationRefBean || bean instanceof PersistenceContextRefBean || bean instanceof PersistenceUnitRefBean;
   }

   protected void handleAddEvent(DescriptorBean targetBean, DescriptorBean sourceBean, DescriptorBean proposedBean, BeanUpdateEvent.PropertyUpdate update) throws MergeException {
      String property = singularizeProperty(update.getPropertyName());
      if (update.getAddedObject() instanceof InjectionTargetBean && !isPropertySet(sourceBean, update.getPropertyName())) {
         try {
            addChildBean(targetBean, property, update.getAddedObject());
         } catch (BeanAlreadyExistsException var7) {
         }
      }

   }
}
