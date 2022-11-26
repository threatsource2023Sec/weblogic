package org.glassfish.hk2.xml.internal.alt;

import java.util.List;

public interface AltMethod {
   String getName();

   AltClass getReturnType();

   List getParameterTypes();

   AltClass getFirstTypeArgument();

   AltClass getFirstTypeArgumentOfParameter(int var1);

   AltAnnotation getAnnotation(String var1);

   List getAnnotations();

   boolean isVarArgs();

   void setMethodInformation(MethodInformationI var1);

   MethodInformationI getMethodInformation();
}
