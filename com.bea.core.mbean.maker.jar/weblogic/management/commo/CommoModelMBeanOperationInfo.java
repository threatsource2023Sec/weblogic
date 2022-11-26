package weblogic.management.commo;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import javax.management.Descriptor;
import javax.management.MBeanException;
import javax.management.MBeanParameterInfo;
import javax.management.modelmbean.ModelMBeanOperationInfo;

public class CommoModelMBeanOperationInfo extends ModelMBeanOperationInfo {
   static final long serialVersionUID = -665469141712332428L;
   private static DescriptorSupportBase baseOperationDescriptorSupport = null;
   private boolean isCloned = false;

   public CommoModelMBeanOperationInfo(String description, Method operationMethod) {
      super(description, operationMethod);
   }

   public CommoModelMBeanOperationInfo(String description, Method operationMethod, Descriptor descriptor) {
      super(description, operationMethod, descriptor);
   }

   public CommoModelMBeanOperationInfo(String name, String description, MBeanParameterInfo[] signature, String type, int impact) {
      super(name, description, signature, type, impact);
   }

   public CommoModelMBeanOperationInfo(String name, String description, MBeanParameterInfo[] signature, String type, int impact, Descriptor descriptor) {
      super(name, description, signature, type, impact, descriptor);
   }

   public CommoModelMBeanOperationInfo(ModelMBeanOperationInfo inInfo) {
      super(inInfo);
   }

   static DescriptorSupport getBaseOperationDescriptorSupport() throws MBeanException {
      if (baseOperationDescriptorSupport == null) {
         baseOperationDescriptorSupport = new DescriptorSupportBase(12);
         baseOperationDescriptorSupport.setField("descriptorType", "Operation");
         baseOperationDescriptorSupport.setField("CurrencyTimeLimit", "-1");
         baseOperationDescriptorSupport.setField("Deprecated", "false");
         baseOperationDescriptorSupport.setField("Listen", "false");
         baseOperationDescriptorSupport.setField("NoDoc", "false");
         baseOperationDescriptorSupport.setField("ReturnType", "java.lang.String");
         baseOperationDescriptorSupport.setField("Visibility", "1");
      }

      return baseOperationDescriptorSupport;
   }

   static DescriptorSupport makeOperationDescriptorSupport() throws MBeanException {
      DescriptorSupport descr = new DescriptorSupport(getBaseOperationDescriptorSupport());
      return descr;
   }

   private static DescriptorSupport makeOperationDescriptorSupport(String name) throws MBeanException {
      DescriptorSupport descr = new DescriptorSupport(getBaseOperationDescriptorSupport());
      descr.setField("name", name);
      return descr;
   }

   public boolean isCloned() {
      return this.isCloned;
   }

   public void setCloned(boolean isCloned) {
      this.isCloned = isCloned;
   }

   public Object clone() {
      return new CommoModelMBeanOperationInfo(this);
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
         dftDesc = makeOperationDescriptorSupport(this.getName());
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
