package com.oracle.cmm.agent.internal;

import com.oracle.cmm.agent.CMMAgent;
import com.oracle.cmm.agent.CMMException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import javax.management.Descriptor;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.RuntimeOperationsException;
import javax.management.modelmbean.InvalidTargetObjectTypeException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanConstructorInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.management.modelmbean.ModelMBeanNotificationInfo;
import javax.management.modelmbean.ModelMBeanOperationInfo;
import javax.management.modelmbean.RequiredModelMBean;

public class CMMAgentImpl implements CMMAgent {
   private static final String GET_METHOD = "getMethod";
   private static final String SET_METHOD = "setMethod";
   private static final String ROLE = "role";
   private static final String GETTER_ROLE = "getter";
   private static final String SETTER_ROLE = "setter";
   private static final String OBJECT_REFERENCE = "ObjectReference";

   private static Method getMemoryPressureGetter() {
      try {
         return MemoryPressure.class.getMethod("getPressureLevel");
      } catch (NoSuchMethodException var1) {
         throw new CMMException(var1);
      }
   }

   private static Method getMemoryPressureSetter() {
      try {
         return MemoryPressure.class.getMethod("setPressureLevel", Integer.TYPE);
      } catch (NoSuchMethodException var1) {
         throw new CMMException(var1);
      }
   }

   private static ObjectName getMemoryPressureObjectName() {
      Hashtable kvPair = new Hashtable();
      kvPair.put("type", "ResourcePressureMBean");

      try {
         return new ObjectName("com.oracle.management", kvPair);
      } catch (MalformedObjectNameException var2) {
         throw new CMMException(var2);
      }
   }

   private static boolean hasBean(MBeanServer mbeanserver) {
      try {
         mbeanserver.getMBeanInfo(getMemoryPressureObjectName());
         return true;
      } catch (InstanceNotFoundException var2) {
         return false;
      } catch (IntrospectionException var3) {
         throw new RuntimeException(var3);
      } catch (ReflectionException var4) {
         throw new RuntimeException(var4);
      }
   }

   private ModelMBean getMemoryPressureModel(MemoryPressure memoryPressure) {
      try {
         ModelMBeanAttributeInfo[] attributes = new ModelMBeanAttributeInfo[]{new ModelMBeanAttributeInfo("MemoryPressure", "The current memory pressure value", getMemoryPressureGetter(), getMemoryPressureSetter())};
         Descriptor attCopyDesc = attributes[0].getDescriptor();
         attCopyDesc.setField("getMethod", getMemoryPressureGetter().getName());
         attCopyDesc.setField("setMethod", getMemoryPressureSetter().getName());
         attributes[0].setDescriptor(attCopyDesc);
         ModelMBeanOperationInfo[] operations = new ModelMBeanOperationInfo[]{new ModelMBeanOperationInfo("The setter for the MemoryPressure attribute", getMemoryPressureSetter()), null};
         Descriptor memPresSetDesc = operations[0].getDescriptor();
         memPresSetDesc.setField("role", "setter");
         operations[0].setDescriptor(memPresSetDesc);
         operations[1] = new ModelMBeanOperationInfo("The getter for the MemoryPressure attribute", getMemoryPressureGetter());
         Descriptor memPresGetDesc = operations[1].getDescriptor();
         memPresGetDesc.setField("role", "getter");
         operations[1].setDescriptor(memPresGetDesc);
         ModelMBeanInfo info = new ModelMBeanInfoSupport(MemoryPressure.class.getName(), "MemoryPressureMBean", attributes, new ModelMBeanConstructorInfo[0], operations, new ModelMBeanNotificationInfo[0]);
         RequiredModelMBean managementMBean = new RequiredModelMBean(info);
         managementMBean.setManagedResource(memoryPressure, "ObjectReference");
         return managementMBean;
      } catch (IntrospectionException var9) {
         throw new CMMException(var9);
      } catch (RuntimeOperationsException var10) {
         throw new CMMException(var10);
      } catch (InstanceNotFoundException var11) {
         throw new CMMException(var11);
      } catch (InvalidTargetObjectTypeException var12) {
         throw new CMMException(var12);
      } catch (MBeanException var13) {
         throw new CMMException(var13);
      }
   }

   public void addCMMMBeanToServer(MBeanServer server) {
      if (!hasBean(server)) {
         MemoryPressure memoryPressure = new MemoryPressure();

         try {
            ModelMBean memoryPressureMBean = this.getMemoryPressureModel(memoryPressure);
            server.registerMBean(memoryPressureMBean, getMemoryPressureObjectName());
         } catch (InstanceAlreadyExistsException var4) {
         } catch (RuntimeOperationsException var5) {
            throw new CMMException(var5);
         } catch (MBeanException var6) {
            throw new CMMException(var6);
         } catch (NotCompliantMBeanException var7) {
            throw new CMMException(var7);
         }
      }
   }

   public void removeCMMMBeanFromServer(MBeanServer server) {
      if (hasBean(server)) {
         try {
            server.unregisterMBean(getMemoryPressureObjectName());
         } catch (MBeanRegistrationException var3) {
         } catch (InstanceNotFoundException var4) {
         }

      }
   }
}
