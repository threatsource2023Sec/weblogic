package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface WTCtBridgeRedirectMBean extends ConfigurationMBean {
   void setDirection(String var1) throws InvalidAttributeValueException;

   String getDirection();

   void setTranslateFML(String var1) throws InvalidAttributeValueException;

   String getTranslateFML();

   void setMetaDataFile(String var1) throws InvalidAttributeValueException;

   String getMetaDataFile();

   void setReplyQ(String var1) throws InvalidAttributeValueException;

   String getReplyQ();

   void setSourceAccessPoint(String var1) throws InvalidAttributeValueException;

   String getSourceAccessPoint();

   void setSourceQspace(String var1) throws InvalidAttributeValueException;

   String getSourceQspace();

   void setSourceName(String var1) throws InvalidAttributeValueException;

   String getSourceName();

   void setTargetAccessPoint(String var1) throws InvalidAttributeValueException;

   String getTargetAccessPoint();

   void setTargetQspace(String var1) throws InvalidAttributeValueException;

   String getTargetQspace();

   void setTargetName(String var1) throws InvalidAttributeValueException;

   String getTargetName();
}
