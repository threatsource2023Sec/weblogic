package weblogic.management.descriptors;

import java.io.Serializable;
import weblogic.management.ManagementException;

public interface BaseDescriptor extends Serializable {
   String getName();

   void setName(String var1);

   String toXML(int var1);

   void register() throws ManagementException;

   void unregister() throws ManagementException;
}
