package weblogic.management.utils;

public interface NameListerMBean extends ListerMBean {
   String getCurrentName(String var1) throws InvalidCursorException;
}
