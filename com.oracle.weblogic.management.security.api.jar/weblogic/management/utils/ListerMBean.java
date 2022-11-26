package weblogic.management.utils;

import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;

public interface ListerMBean extends StandardInterface, DescriptorBean {
   boolean haveCurrent(String var1) throws InvalidCursorException;

   void advance(String var1) throws InvalidCursorException;

   void close(String var1) throws InvalidCursorException;
}
