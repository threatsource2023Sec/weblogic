package com.bea.core.repackaged.springframework.jmx.export.assembler;

import javax.management.JMException;
import javax.management.modelmbean.ModelMBeanInfo;

public interface MBeanInfoAssembler {
   ModelMBeanInfo getMBeanInfo(Object var1, String var2) throws JMException;
}
