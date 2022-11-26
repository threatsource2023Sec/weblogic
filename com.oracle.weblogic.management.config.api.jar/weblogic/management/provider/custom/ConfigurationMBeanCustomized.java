package weblogic.management.provider.custom;

import java.io.Serializable;
import javax.management.AttributeNotFoundException;
import javax.management.MBeanException;
import javax.management.Notification;
import weblogic.management.configuration.ConfigurationException;
import weblogic.management.configuration.ConfigurationMBean;

public interface ConfigurationMBeanCustomized extends Cloneable, Serializable {
   ConfigurationMBean getMbean();

   void putValue(String var1, Object var2);

   void putValueNotify(String var1, Object var2);

   Object getValue(String var1);

   boolean isAdmin();

   boolean isConfig();

   boolean isEdit();

   boolean isRuntime();

   void sendNotification(Notification var1);

   void touch() throws ConfigurationException;

   void freezeCurrentValue(String var1) throws AttributeNotFoundException, MBeanException;

   void restoreDefaultValue(String var1) throws AttributeNotFoundException;
}
