package weblogic.management.commo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.management.InvalidAttributeValueException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.DescriptorBeanClassName;

public abstract class AbstractCommoConfigurationBean extends AbstractDescriptorBean implements StandardInterface {
   private ObjectName wls_objectName;

   public AbstractCommoConfigurationBean() {
   }

   public AbstractCommoConfigurationBean(DescriptorBean parent, int propIdx) {
      super(parent, propIdx);
   }

   public void _validate() throws IllegalArgumentException {
   }

   public String wls_getDisplayName() {
      return this.getName();
   }

   public String getCompatibilityObjectName() {
      throw new AssertionError("This method should only be called for security mbeans ");
   }

   public ObjectName wls_getObjectName() {
      if (this.wls_objectName == null) {
         try {
            this.wls_objectName = new ObjectName(this.getCompatibilityObjectName());
         } catch (MalformedObjectNameException var2) {
            throw new AssertionError(var2);
         }
      }

      return this.wls_objectName;
   }

   public String getName() {
      throw new AssertionError("AbstractCommoConfigurationBean.getName should never be called.");
   }

   public void setName(String value) throws InvalidAttributeValueException {
      throw new AssertionError("AbstractCommoConfigurationBean.setName should never be called.");
   }

   protected DescriptorBean createChildBean(Class ifc) {
      String implName;
      if (this.isEditable()) {
         implName = DescriptorBeanClassName.toEditableImpl(ifc.getName());
      } else {
         implName = DescriptorBeanClassName.toImpl(ifc.getName());
      }

      try {
         Class implClass = Class.forName(implName, true, Thread.currentThread().getContextClassLoader());
         Class[] signature = new Class[]{DescriptorBean.class};
         Constructor constructor = implClass.getConstructor(signature);
         return (DescriptorBean)constructor.newInstance(this);
      } catch (ClassNotFoundException var6) {
         throw (AssertionError)(new AssertionError(var6)).initCause(var6);
      } catch (NoSuchMethodException var7) {
         throw (AssertionError)(new AssertionError(var7)).initCause(var7);
      } catch (IllegalAccessException var8) {
         throw (AssertionError)(new AssertionError(var8)).initCause(var8);
      } catch (InvocationTargetException var9) {
         throw (AssertionError)(new AssertionError(var9)).initCause(var9);
      } catch (InstantiationException var10) {
         throw (AssertionError)(new AssertionError(var10)).initCause(var10);
      }
   }

   public Object _getKey() {
      Object key = this.getName();
      if (key == null) {
         key = super._getKey();
      }

      return key;
   }

   protected abstract static class Helper extends AbstractDescriptorBeanHelper {
      protected Helper(AbstractCommoConfigurationBean bean) {
         super(bean);
      }
   }
}
