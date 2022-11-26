package org.glassfish.hk2.xml.internal.alt;

import javax.xml.namespace.QName;
import org.glassfish.hk2.xml.internal.Format;
import org.glassfish.hk2.xml.internal.MethodType;

public interface MethodInformationI {
   AltMethod getOriginalMethod();

   MethodType getMethodType();

   AltClass getGetterSetterType();

   QName getRepresentedProperty();

   String getDefaultValue();

   String getWrapperTag();

   AltClass getBaseChildType();

   boolean isKey();

   boolean isList();

   boolean isArray();

   boolean isReference();

   boolean isRequired();

   String getDecapitalizedMethodProperty();

   Format getFormat();

   AltClass getListParameterizedType();

   AdapterInformation getAdapterInformation();

   String getOriginalMethodName();
}
