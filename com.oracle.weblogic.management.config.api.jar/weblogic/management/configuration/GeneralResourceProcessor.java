package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.ManagementException;

public interface GeneralResourceProcessor {
   void start();

   void end();

   void processResource(ConfigurationMBean var1) throws InvalidAttributeValueException, ManagementException;
}
