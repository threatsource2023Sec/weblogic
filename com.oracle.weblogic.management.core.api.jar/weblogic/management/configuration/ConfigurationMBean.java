package weblogic.management.configuration;

import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBean;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicMBean;

public interface ConfigurationMBean extends WebLogicMBean, DescriptorBean, SettableBean {
   byte[] DEFAULT_EMPTY_BYTE_ARRAY = (new String("")).getBytes();

   String getName();

   void setName(String var1) throws InvalidAttributeValueException, ManagementException;

   String getNotes();

   void setNotes(String var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean isPersistenceEnabled();

   /** @deprecated */
   @Deprecated
   void setPersistenceEnabled(boolean var1);

   boolean isDefaultedMBean();

   /** @deprecated */
   @Deprecated
   void setDefaultedMBean(boolean var1);

   String getComments();

   void setComments(String var1);

   void touch() throws ConfigurationException;

   /** @deprecated */
   @Deprecated
   void freezeCurrentValue(String var1) throws AttributeNotFoundException, MBeanException;

   /** @deprecated */
   @Deprecated
   void restoreDefaultValue(String var1) throws AttributeNotFoundException;

   boolean isSet(String var1);

   void unSet(String var1);

   boolean isInherited(String var1);

   String[] getInheritedProperties(String[] var1);

   boolean isDynamicallyCreated();

   long getId();

   String[] getTags();

   void setTags(String[] var1) throws IllegalArgumentException;

   boolean addTag(String var1) throws IllegalArgumentException;

   boolean removeTag(String var1) throws IllegalArgumentException;
}
