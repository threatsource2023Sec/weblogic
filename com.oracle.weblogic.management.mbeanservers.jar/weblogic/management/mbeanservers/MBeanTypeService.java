package weblogic.management.mbeanservers;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.ObjectName;
import javax.management.OperationsException;
import javax.management.ReflectionException;
import javax.management.modelmbean.ModelMBeanInfo;

public interface MBeanTypeService {
   String OBJECT_NAME = "com.bea:Name=MBeanTypeService,Type=" + MBeanTypeService.class.getName();

   ModelMBeanInfo getMBeanInfo(String var1) throws OperationsException;

   String[] getSubtypes(String var1);

   void validateAttribute(String var1, Attribute var2) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException;

   AttributeList validateAttributes(String var1, AttributeList var2) throws MBeanException, ReflectionException;

   void validateAttribute(ObjectName var1, Attribute var2) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException;

   AttributeList validateAttributes(ObjectName var1, AttributeList var2) throws MBeanException, ReflectionException;
}
