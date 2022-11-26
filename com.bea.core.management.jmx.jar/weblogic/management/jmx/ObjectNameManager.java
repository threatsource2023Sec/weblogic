package weblogic.management.jmx;

import java.util.Collection;
import javax.management.ObjectName;
import weblogic.management.jmx.modelmbean.WLSModelMBeanInstanceContext;

public interface ObjectNameManager {
   boolean isClassMapped(Class var1);

   boolean isFiltered(Object var1);

   ObjectName lookupObjectName(Object var1);

   ObjectName lookupRegisteredObjectName(Object var1);

   Object lookupObject(ObjectName var1);

   void registerObject(ObjectName var1, Object var2);

   ObjectName unregisterObjectInstance(Object var1);

   Collection getAllObjectNames();

   ObjectName buildObjectName(Object var1);

   ObjectName buildObjectName(Object var1, WLSModelMBeanInstanceContext var2);
}
