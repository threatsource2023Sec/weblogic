package weblogic.descriptor;

import java.beans.PropertyChangeListener;

public interface DescriptorBean extends SettableBean {
   Descriptor getDescriptor();

   DescriptorBean getParentBean();

   void addPropertyChangeListener(PropertyChangeListener var1);

   void removePropertyChangeListener(PropertyChangeListener var1);

   void addBeanUpdateListener(BeanUpdateListener var1);

   void removeBeanUpdateListener(BeanUpdateListener var1);

   boolean isEditable();

   DescriptorBean createChildCopy(String var1, DescriptorBean var2) throws IllegalArgumentException, BeanAlreadyExistsException;

   DescriptorBean createChildCopyIncludingObsolete(String var1, DescriptorBean var2) throws IllegalArgumentException, BeanAlreadyExistsException;
}
