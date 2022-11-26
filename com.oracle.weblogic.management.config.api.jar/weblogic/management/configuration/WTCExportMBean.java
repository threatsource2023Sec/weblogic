package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface WTCExportMBean extends ConfigurationMBean {
   void setResourceName(String var1) throws InvalidAttributeValueException;

   String getResourceName();

   void setLocalAccessPoint(String var1) throws InvalidAttributeValueException;

   String getLocalAccessPoint();

   void setEJBName(String var1) throws InvalidAttributeValueException;

   String getEJBName();

   void setTargetClass(String var1) throws InvalidAttributeValueException;

   String getTargetClass();

   void setTargetJar(String var1) throws InvalidAttributeValueException;

   String getTargetJar();

   void setRemoteName(String var1) throws InvalidAttributeValueException;

   String getRemoteName();
}
