package weblogic.management.commo;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import javax.management.Descriptor;
import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBeanConstructorInfo;

public class CommoModelMBeanConstructorInfo extends ModelMBeanConstructorInfo {
   private static DescriptorSupportBase baseConstructorDescriptorSupport = null;
   private boolean isCloned = false;

   public CommoModelMBeanConstructorInfo(String description, Constructor constructorMethod) {
      super(description, constructorMethod);
   }

   static DescriptorSupport getBaseConstructorDescriptorSupport() throws MBeanException {
      if (baseConstructorDescriptorSupport == null) {
         baseConstructorDescriptorSupport = new DescriptorSupportBase(12);
         baseConstructorDescriptorSupport.setField("descriptorType", "Operation");
         baseConstructorDescriptorSupport.setField("role", "constructor");
         baseConstructorDescriptorSupport.setField("CurrencyTimeLimit", "0");
         baseConstructorDescriptorSupport.setField("Deprecated", "false");
         baseConstructorDescriptorSupport.setField("Listen", "false");
         baseConstructorDescriptorSupport.setField("NoDoc", "false");
         baseConstructorDescriptorSupport.setField("Visibility", "1");
      }

      return baseConstructorDescriptorSupport;
   }

   static DescriptorSupport makeConstructorDescriptorSupport() throws MBeanException {
      DescriptorSupport descr = new DescriptorSupport(getBaseConstructorDescriptorSupport());
      return descr;
   }

   private static DescriptorSupport makeConstructorDescriptorSupport(String name) throws MBeanException {
      DescriptorSupport descr = new DescriptorSupport(getBaseConstructorDescriptorSupport());
      descr.setField("name", name);
      return descr;
   }

   public boolean isCloned() {
      return this.isCloned;
   }

   public void setCloned(boolean isCloned) {
      this.isCloned = isCloned;
   }

   protected boolean isValid(Descriptor inDesc) {
      boolean results = true;
      if (inDesc == null) {
         results = false;
      } else if (inDesc.getFieldValue("name") == null) {
         results = false;
      } else if (!((String)inDesc.getFieldValue("name")).equalsIgnoreCase(this.getName())) {
         results = false;
      }

      return results;
   }

   protected Descriptor createDefaultDescriptor() {
      Descriptor dftDesc = null;

      try {
         dftDesc = makeConstructorDescriptorSupport(this.getName());
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
