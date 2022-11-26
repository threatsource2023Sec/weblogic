package weblogic.management.commo;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.management.Descriptor;
import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBeanNotificationInfo;

public class CommoModelMBeanNotificationInfo extends ModelMBeanNotificationInfo {
   private static DescriptorSupportBase baseNotificationDescriptorSupport = null;
   private boolean isCloned = false;

   public CommoModelMBeanNotificationInfo(ModelMBeanNotificationInfo inInfo) {
      super(inInfo);
   }

   static DescriptorSupport getBaseNotificationDescriptorSupport() throws MBeanException {
      if (baseNotificationDescriptorSupport == null) {
         baseNotificationDescriptorSupport = new DescriptorSupportBase(13);
         baseNotificationDescriptorSupport.setField("descriptorType", "Notification");
         baseNotificationDescriptorSupport.setField("ClassName", "javax.management.Notification");
         baseNotificationDescriptorSupport.setField("Deprecated", "false");
         baseNotificationDescriptorSupport.setField("Listen", "false");
         baseNotificationDescriptorSupport.setField("Log", "false");
         baseNotificationDescriptorSupport.setField("NoDoc", "false");
         baseNotificationDescriptorSupport.setField("Severity", "5");
         baseNotificationDescriptorSupport.setField("Visibility", "1");
      }

      return baseNotificationDescriptorSupport;
   }

   static DescriptorSupport makeNotificationDescriptorSupport() throws MBeanException {
      DescriptorSupport descr = new DescriptorSupport(getBaseNotificationDescriptorSupport());
      return descr;
   }

   public boolean isCloned() {
      return this.isCloned;
   }

   public void setCloned(boolean isCloned) {
      this.isCloned = isCloned;
   }

   public Object clone() {
      return new CommoModelMBeanNotificationInfo(this);
   }

   protected boolean isValid(Descriptor inDesc) {
      boolean results = true;
      if (inDesc == null) {
         return false;
      } else {
         if (inDesc.getFieldValue("name") == null) {
            results = false;
         } else if (!((String)inDesc.getFieldValue("name")).equalsIgnoreCase(this.getName())) {
            results = false;
         }

         return results;
      }
   }

   protected Descriptor createDefaultDescriptor() {
      Descriptor dftDesc = null;

      try {
         dftDesc = this.makeNotificationDescriptorSupport(this.getName());
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

   private DescriptorSupport makeNotificationDescriptorSupport(String name) throws MBeanException {
      DescriptorSupport descr = new DescriptorSupport(getBaseNotificationDescriptorSupport());
      descr.setField("name", name);
      return descr;
   }
}
