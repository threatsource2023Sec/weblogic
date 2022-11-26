package com.bea.x2004.x03.wlw.externalConfig.impl;

import com.bea.x2004.x03.wlw.externalConfig.AnnotationRefBean;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;

public class AnnotationRefBeanImpl extends XmlComplexContentImpl implements AnnotationRefBean {
   private static final long serialVersionUID = 1L;

   public AnnotationRefBeanImpl(SchemaType sType) {
      super(sType);
   }
}
