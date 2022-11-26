package weblogic.management.commo;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import javax.management.Descriptor;
import javax.management.IntrospectionException;
import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBeanAttributeInfo;

public class CommoModelMBeanAttributeInfo extends ModelMBeanAttributeInfo {
   static final long serialVersionUID = 6101036170757294530L;
   private static DescriptorSupportBase baseAttributeDescriptorSupport = null;
   private boolean isCloned = false;

   public CommoModelMBeanAttributeInfo(String name, String description, Method getter, Method setter) throws IntrospectionException {
      super(name, description, getter, setter);
   }

   public CommoModelMBeanAttributeInfo(String name, String description, Method getter, Method setter, Descriptor descriptor) throws IntrospectionException {
      super(name, description, getter, setter, descriptor);
   }

   public CommoModelMBeanAttributeInfo(String name, String type, String description, boolean isReadable, boolean isWritable, boolean isIs) {
      super(name, type, description, isReadable, isWritable, isIs);
   }

   public CommoModelMBeanAttributeInfo(String name, String type, String description, boolean isReadable, boolean isWritable, boolean isIs, Descriptor descriptor) {
      super(name, type, description, isReadable, isWritable, isIs, descriptor);
   }

   public CommoModelMBeanAttributeInfo(ModelMBeanAttributeInfo inInfo) {
      super(inInfo);
   }

   public Object clone() {
      return new CommoModelMBeanAttributeInfo(this);
   }

   public Descriptor getDescriptorNoClone() {
      return null;
   }

   public void removeDefaultData() {
   }

   public void setMBeanDescriptorNoClone(Descriptor desc) {
   }

   protected Descriptor createDefaultDescriptor() {
      Descriptor dftDesc = null;

      try {
         dftDesc = makeAttributeDescriptorSupport(this.getName());
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

   static DescriptorSupport getBaseAttributeDescriptorSupport() throws MBeanException {
      if (baseAttributeDescriptorSupport == null) {
         baseAttributeDescriptorSupport = new DescriptorSupportBase(23);
         baseAttributeDescriptorSupport.setField("descriptorType", "Attribute");
         baseAttributeDescriptorSupport.setField("CachingDisabled", "false");
         baseAttributeDescriptorSupport.setField("CurrencyTimeLimit", "0");
         baseAttributeDescriptorSupport.setField("Deprecated", "false");
         baseAttributeDescriptorSupport.setField("Encrypted", "false");
         baseAttributeDescriptorSupport.setField("GenerateExtendedAccessors", "false");
         baseAttributeDescriptorSupport.setField("IsIs", "false");
         baseAttributeDescriptorSupport.setField("Iterable", "false");
         baseAttributeDescriptorSupport.setField("LegalNull", "true");
         baseAttributeDescriptorSupport.setField("Listen", "false");
         baseAttributeDescriptorSupport.setField("NoDoc", "false");
         baseAttributeDescriptorSupport.setField("NoDump", "false");
         baseAttributeDescriptorSupport.setField("Readable", "true");
         baseAttributeDescriptorSupport.setField("Type", "java.lang.String");
         baseAttributeDescriptorSupport.setField("Visibility", "1");
         baseAttributeDescriptorSupport.setField("Writeable", "true");
      }

      return baseAttributeDescriptorSupport;
   }

   private static DescriptorSupport makeAttributeDescriptorSupport(String name) throws MBeanException {
      DescriptorSupport descr = new DescriptorSupport(getBaseAttributeDescriptorSupport());
      descr.setField("name", name);
      return descr;
   }

   static DescriptorSupport makeAttributeDescriptorSupport() throws MBeanException {
      DescriptorSupport descr = new DescriptorSupport(getBaseAttributeDescriptorSupport());
      return descr;
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

   public boolean isCloned() {
      return this.isCloned;
   }

   public void setCloned(boolean isCloned) {
      this.isCloned = isCloned;
   }
}
