package weblogic.management.utils;

import java.util.Properties;

public interface PropertiesListerMBean extends ListerMBean {
   Properties getCurrentProperties(String var1) throws InvalidCursorException;
}
