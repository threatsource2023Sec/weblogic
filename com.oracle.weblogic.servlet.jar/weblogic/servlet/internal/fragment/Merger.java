package weblogic.servlet.internal.fragment;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;

public interface Merger {
   void merge(DescriptorBean var1, DescriptorBean var2, DescriptorBean var3, BeanUpdateEvent.PropertyUpdate var4) throws MergeException;

   boolean accept(DescriptorBean var1, BeanUpdateEvent.PropertyUpdate var2);
}
