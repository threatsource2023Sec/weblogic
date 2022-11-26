package org.glassfish.hk2.xml.internal.alt;

import java.util.Map;

public interface AltAnnotation {
   String annotationType();

   String getStringValue(String var1);

   boolean getBooleanValue(String var1);

   String[] getStringArrayValue(String var1);

   AltAnnotation[] getAnnotationArrayValue(String var1);

   AltClass getClassValue(String var1);

   Map getAnnotationValues();
}
