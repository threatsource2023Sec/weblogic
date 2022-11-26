package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface WTCImportMBean extends ConfigurationMBean {
   void setResourceName(String var1) throws InvalidAttributeValueException;

   String getResourceName();

   void setLocalAccessPoint(String var1) throws InvalidAttributeValueException;

   String getLocalAccessPoint();

   void setRemoteAccessPointList(String var1) throws InvalidAttributeValueException;

   String getRemoteAccessPointList();

   void setRemoteName(String var1) throws InvalidAttributeValueException;

   String getRemoteName();
}
