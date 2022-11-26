package weblogic.management.mbeanservers.internal;

import java.util.Iterator;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.Descriptor;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.ObjectName;
import javax.management.OperationsException;
import javax.management.ReflectionException;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import weblogic.management.jmx.modelmbean.ModelMBeanInfoWrapper;
import weblogic.management.jmx.modelmbean.ModelMBeanInfoWrapperManager;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;
import weblogic.management.mbeanservers.MBeanTypeService;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.beaninfo.BeanInfoAccess;

public class MBeanTypeServiceImpl implements MBeanTypeService {
   private WLSModelMBeanContext context;
   private BeanInfoAccess access;

   public MBeanTypeServiceImpl(WLSModelMBeanContext context) {
      this.context = context;
      this.access = ManagementService.getBeanInfoAccess();
   }

   public ModelMBeanInfo getMBeanInfo(String beanInterface) throws OperationsException {
      ModelMBeanInfoWrapper wrapper = ModelMBeanInfoWrapperManager.getModelMBeanInfoForInterface(beanInterface, this.context.isReadOnly(), this.context.getVersion(), this.context.getNameManager());
      return wrapper.getModelMBeanInfo();
   }

   public String[] getSubtypes(String baseInterface) {
      return this.access.getSubtypes(baseInterface);
   }

   public void validateAttribute(String beanInterface, Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
      ModelMBeanInfoWrapper wrapper;
      try {
         wrapper = ModelMBeanInfoWrapperManager.getModelMBeanInfoForInterface(beanInterface, this.context.isReadOnly(), this.context.getVersion(), this.context.getNameManager());
      } catch (OperationsException var5) {
         throw new MBeanException(var5);
      }

      validateOneAttribute(attribute, wrapper);
   }

   public AttributeList validateAttributes(String beanInterface, AttributeList attributes) throws MBeanException, ReflectionException {
      ModelMBeanInfoWrapper wrapper;
      try {
         wrapper = ModelMBeanInfoWrapperManager.getModelMBeanInfoForInterface(beanInterface, this.context.isReadOnly(), this.context.getVersion(), this.context.getNameManager());
      } catch (OperationsException var5) {
         throw new MBeanException(var5);
      }

      AttributeList result = validateMultipleAttributes(attributes, wrapper);
      return result;
   }

   public void validateAttribute(ObjectName beanInstance, Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
      ModelMBeanInfoWrapper wrapper = this.getWrapperForObjectName(beanInstance);
      validateOneAttribute(attribute, wrapper);
   }

   public AttributeList validateAttributes(ObjectName beanInstance, AttributeList attributes) throws MBeanException, ReflectionException {
      ModelMBeanInfoWrapper wrapper = this.getWrapperForObjectName(beanInstance);
      return validateMultipleAttributes(attributes, wrapper);
   }

   private static void validateOneAttribute(Attribute attribute, ModelMBeanInfoWrapper wrapper) throws AttributeNotFoundException, MBeanException, InvalidAttributeValueException, ReflectionException {
      String attributeName = attribute.getName();
      Object value = attribute.getValue();
      ModelMBeanAttributeInfo attributeInfo = wrapper.getModelMBeanInfo().getAttribute(attributeName);
      if (attributeInfo == null) {
         throw new AttributeNotFoundException(attributeName + " for " + wrapper.getModelMBeanInfo().getClassName());
      } else {
         try {
            if (value != null) {
               Class attributeClass = Class.forName(attributeInfo.getType());
               if (!value.getClass().isAssignableFrom(attributeClass)) {
                  throw new InvalidAttributeValueException(value.getClass() + "is not assignable from " + attributeClass);
               }
            }
         } catch (ClassNotFoundException var13) {
            throw new ReflectionException(var13);
         }

         Descriptor attributeDescriptor = attributeInfo.getDescriptor();
         Boolean legalNull = (Boolean)attributeDescriptor.getFieldValue("isNullable");
         if (legalNull != null) {
            if (legalNull) {
               if (value == null) {
                  return;
               }
            } else if (value == null) {
               throw new InvalidAttributeValueException("Null value is not allowed for " + attributeName);
            }
         }

         Object minValue = attributeDescriptor.getFieldValue("minValue");
         if (minValue == null || value != null && compareTo(value, minValue) >= 0) {
            Object maxValue = attributeDescriptor.getFieldValue("maxValue");
            if (maxValue != null && (value == null || compareTo(value, maxValue) > 0)) {
               throw new InvalidAttributeValueException("Value " + value + " is greater than " + maxValue + " for " + attributeName);
            } else {
               Object[] legalValues = (Object[])((Object[])attributeDescriptor.getFieldValue("legalValues"));
               if (legalValues != null) {
                  boolean notFound = true;

                  Object o;
                  for(int i = 0; notFound && i < legalValues.length; notFound = o.equals(value)) {
                     o = legalValues[i++];
                  }

                  if (notFound) {
                     throw new InvalidAttributeValueException("Value " + value + " is not in " + legalValues + " for " + attributeName);
                  }
               }

            }
         } else {
            throw new InvalidAttributeValueException("Value " + value + " is less than " + minValue + " for " + attributeName);
         }
      }
   }

   private static AttributeList validateMultipleAttributes(AttributeList attributes, ModelMBeanInfoWrapper wrapper) throws MBeanException, ReflectionException {
      AttributeList result = new AttributeList();
      Iterator iterator = attributes.iterator();

      while(iterator.hasNext()) {
         Attribute attribute = (Attribute)iterator.next();

         try {
            validateOneAttribute(attribute, wrapper);
         } catch (InvalidAttributeValueException var6) {
            result.add(new Attribute(attribute.getName(), var6));
         } catch (AttributeNotFoundException var7) {
            result.add(new Attribute(attribute.getName(), var7));
         }
      }

      return result;
   }

   private ModelMBeanInfoWrapper getWrapperForObjectName(ObjectName beanInstance) throws MBeanException {
      try {
         Object instance = this.context.getNameManager().lookupObject(beanInstance);
         ModelMBeanInfoWrapper wrapper = ModelMBeanInfoWrapperManager.getModelMBeanInfoForInstance(instance, this.context.isReadOnly(), this.context.getVersion(), this.context.getNameManager());
         return wrapper;
      } catch (OperationsException var4) {
         throw new MBeanException(var4);
      }
   }

   private static int compareTo(Object value, Object toCompare) {
      if (toCompare instanceof Comparable) {
         return ((Comparable)value).compareTo((Comparable)toCompare);
      } else {
         throw new AssertionError("Attribute with legalMin or legalMax is not comparable");
      }
   }
}
