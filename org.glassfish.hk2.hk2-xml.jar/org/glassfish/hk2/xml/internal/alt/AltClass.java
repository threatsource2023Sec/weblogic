package org.glassfish.hk2.xml.internal.alt;

import java.util.List;

public interface AltClass {
   String getName();

   String getSimpleName();

   List getAnnotations();

   List getMethods();

   AltClass getSuperParameterizedType(AltClass var1, int var2);

   boolean isInterface();

   boolean isArray();

   AltClass getComponentType();
}
