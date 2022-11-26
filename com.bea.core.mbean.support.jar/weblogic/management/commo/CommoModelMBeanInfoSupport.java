package weblogic.management.commo;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import javax.management.Descriptor;
import javax.management.JMRuntimeException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanException;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanConstructorInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.management.modelmbean.ModelMBeanNotificationInfo;
import javax.management.modelmbean.ModelMBeanOperationInfo;

public class CommoModelMBeanInfoSupport extends ModelMBeanInfoSupport implements Cloneable, Serializable {
   static final long serialVersionUID = 3877437648870883929L;
   private static DescriptorSupportBase baseMBeanDescriptorSupport = null;

   public CommoModelMBeanInfoSupport(String className, String description, ModelMBeanAttributeInfo[] attributes, ModelMBeanConstructorInfo[] constructors, ModelMBeanOperationInfo[] operations, ModelMBeanNotificationInfo[] notifications) {
      super(className, description, attributes, constructors, operations, notifications);
   }

   CommoModelMBeanInfoSupport() throws MBeanException {
      this("weblogic.management.commo.CommoModelMBean", "", new CommoModelMBeanAttributeInfo[0], new CommoModelMBeanConstructorInfo[0], new CommoModelMBeanOperationInfo[0], new CommoModelMBeanNotificationInfo[0], makeMBeanDescriptorSupport());
   }

   CommoModelMBeanInfoSupport(ModelMBeanInfo mbi) throws MBeanException {
      super(mbi.getClassName(), mbi.getDescription(), (ModelMBeanAttributeInfo[])((ModelMBeanAttributeInfo[])mbi.getAttributes()), (ModelMBeanConstructorInfo[])((ModelMBeanConstructorInfo[])mbi.getConstructors()), (ModelMBeanOperationInfo[])((ModelMBeanOperationInfo[])mbi.getOperations()), (ModelMBeanNotificationInfo[])((ModelMBeanNotificationInfo[])mbi.getNotifications()), mbi.getMBeanDescriptor());
   }

   public MBeanAttributeInfo[] getAttributes() {
      MBeanAttributeInfo[] result = super.getAttributes();
      return (MBeanAttributeInfo[])(result.length == 0 ? new CommoModelMBeanAttributeInfo[0] : result);
   }

   public MBeanOperationInfo[] getOperations() {
      MBeanOperationInfo[] result = super.getOperations();
      return (MBeanOperationInfo[])(result.length == 0 ? new CommoModelMBeanOperationInfo[0] : result);
   }

   public MBeanConstructorInfo[] getConstructors() {
      MBeanConstructorInfo[] result = super.getConstructors();
      return (MBeanConstructorInfo[])(result.length == 0 ? new CommoModelMBeanConstructorInfo[0] : result);
   }

   public MBeanNotificationInfo[] getNotifications() {
      MBeanNotificationInfo[] result = super.getNotifications();
      return (MBeanNotificationInfo[])(result.length == 0 ? new CommoModelMBeanNotificationInfo[0] : result);
   }

   public Descriptor getDescriptorNoClone() {
      return null;
   }

   public void removeDefaultData() {
   }

   public void setMBeanDescriptorNoClone(Descriptor desc) {
   }

   CommoModelMBeanInfoSupport(String className, String description, ModelMBeanAttributeInfo[] attributes, ModelMBeanConstructorInfo[] constructors, ModelMBeanOperationInfo[] operations, ModelMBeanNotificationInfo[] notifications, Descriptor mbeandescriptor) {
      super(className, description, attributes, constructors, operations, notifications, mbeandescriptor);
   }

   public static DescriptorSupport makeMBeanDescriptorSupport() throws MBeanException {
      DescriptorSupport descr = new DescriptorSupport(getBaseMBeanDescriptorSupport());
      return descr;
   }

   static DescriptorSupport getBaseMBeanDescriptorSupport() throws MBeanException {
      if (baseMBeanDescriptorSupport == null) {
         baseMBeanDescriptorSupport = new DescriptorSupportBase(19);
         baseMBeanDescriptorSupport.setField("descriptorType", "MBean");
         baseMBeanDescriptorSupport.setField("Abstract", "false");
         baseMBeanDescriptorSupport.setField("CachingDisabled", "false");
         baseMBeanDescriptorSupport.setField("CurrencyTimeLimit", "-1");
         baseMBeanDescriptorSupport.setField("Deprecated", "false");
         baseMBeanDescriptorSupport.setField("Export", "false");
         baseMBeanDescriptorSupport.setField("GenerateExtendedAccessors", "false");
         baseMBeanDescriptorSupport.setField("Listen", "false");
         baseMBeanDescriptorSupport.setField("Log", "false");
         baseMBeanDescriptorSupport.setField("Mbeanclassname", "weblogic.management.commo.CommoModelMBean");
         baseMBeanDescriptorSupport.setField("NoDoc", "false");
         baseMBeanDescriptorSupport.setField("PersistPolicy", "never");
         baseMBeanDescriptorSupport.setField("Readable", "true");
         baseMBeanDescriptorSupport.setField("VersionID", "1L");
         baseMBeanDescriptorSupport.setField("Visibility", "1");
         baseMBeanDescriptorSupport.setField("Writeable", "true");
      }

      return baseMBeanDescriptorSupport;
   }

   private static DescriptorSupport makeMBeanDescriptorSupport(String name) throws MBeanException {
      DescriptorSupport descr = new DescriptorSupport(getBaseMBeanDescriptorSupport());
      descr.setField("name", name);
      return descr;
   }

   public Object clone() {
      try {
         return new CommoModelMBeanInfoSupport(this);
      } catch (MBeanException var2) {
         throw new JMRuntimeException("Exception " + var2 + " cloning MBean.");
      }
   }

   protected boolean isValidDescriptor(Descriptor inDesc) {
      boolean results = true;
      if (inDesc == null) {
         results = false;
      } else if (inDesc.getFieldValue("name") == null) {
         results = false;
      } else if ((String)inDesc.getFieldValue("name") == null) {
         results = false;
      }

      return results;
   }

   protected Descriptor createDefaultDescriptor() {
      Descriptor dftDesc = null;

      try {
         dftDesc = makeMBeanDescriptorSupport(this.getClassName());
         return dftDesc;
      } catch (MBeanException var7) {
         MBeanException x = var7;
         String excp = "" + var7;

         try {
            StringWriter swtr = new StringWriter();
            PrintWriter pwtr = new PrintWriter(swtr);
            x.printStackTrace(pwtr);
            pwtr.flush();
            swtr.flush();
            excp = swtr.toString();
            pwtr.close();
            swtr.close();
         } catch (IOException var6) {
         }

         throw new RuntimeException(excp);
      }
   }
}
