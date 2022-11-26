package weblogic.management.provider.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.management.VersionConstants;
import weblogic.management.upgrade.ConfigFileHelper;

public final class DescriptorManagerHelper {
   private static boolean productionModeSet = false;
   private static boolean productionMode = false;

   public static boolean isProductionMode() {
      if (productionModeSet) {
         return productionMode;
      } else {
         return ConfigFileHelper.getProductionModeEnabled() || Boolean.getBoolean("weblogic.ProductionModeEnabled");
      }
   }

   public static void setProductionMode(boolean mode) {
      productionModeSet = true;
      productionMode = mode;
   }

   public static DescriptorManager getDescriptorManager(boolean editable) {
      return (DescriptorManager)(editable ? DescriptorManagerHelper.EDIT_SINGLETON.instance : DescriptorManagerHelper.READONLY_SINGLETON.instance);
   }

   public static Descriptor loadDescriptor(InputStream in, DescriptorManagerHelperContext ctx) throws IOException, XMLStreamException {
      DescriptorManager descriptorManager = getDescriptorManager(ctx.isEditable());
      if (ctx.isTransform()) {
         ConfigReaderContext readerCtx = new ConfigReaderContext();
         ConfigReader reader = new ConfigReader(in, readerCtx);
         Descriptor desc = descriptorManager.createDescriptor(reader, ctx.getErrors(), ctx.isValidate());
         if (reader.isModified()) {
            ctx.setTransformed(true);
         }

         return desc;
      } else {
         return descriptorManager.createDescriptor(in, ctx.getErrors(), ctx.isValidate());
      }
   }

   public static Descriptor loadDescriptor(String fileName, boolean editable, boolean validate, List errors) throws IOException, XMLStreamException {
      DescriptorManagerHelperContext ctx = new DescriptorManagerHelperContext();
      ctx.setEditable(editable);
      ctx.setValidate(validate);
      ctx.setTransform(true);
      ctx.setErrors(errors);
      if (editable) {
         ctx.setEProductionModeEnabled(isProductionMode());
      } else {
         ctx.setRProductionModeEnabled(isProductionMode());
      }

      File descriptorFile = new File(fileName);
      InputStream in = new FileInputStream(descriptorFile);
      return loadDescriptor(in, ctx);
   }

   public static void saveDescriptor(Descriptor descriptor, OutputStream outputStream) throws IOException {
      DescriptorManagerHelper.EDIT_SINGLETON.instance.writeDescriptorAsXML(descriptor, outputStream);
   }

   private static void setSchemaLocationIfNeeded(EditableDescriptorManager manager) {
      String replace = System.getProperty("weblogic.configuration.schema.location.replace");
      if (replace != null) {
         manager.setSchemaLocation(replace);
      }

   }

   private static class EDIT_SINGLETON {
      static EditableDescriptorManager instance = new EditableDescriptorManager();

      static {
         instance.setProductionMode(DescriptorManagerHelper.isProductionMode());
         DescriptorManagerHelper.setSchemaLocationIfNeeded(instance);
         instance.addInitialNamespace("sec", "http://xmlns.oracle.com/weblogic/security");
         instance.addInitialNamespace("wls", "http://xmlns.oracle.com/weblogic/security/wls");

         for(int i = 0; i < VersionConstants.NAMESPACE_MAPPING.length; ++i) {
            instance.addNamespaceMapping(VersionConstants.NAMESPACE_MAPPING[i][0], VersionConstants.NAMESPACE_MAPPING[i][1]);
         }

         instance.setDescriptorMacroSubstitutor(new DomainMacroSubstitutor());
      }
   }

   private static class READONLY_SINGLETON {
      static DescriptorManager instance = new DescriptorManager();

      static {
         instance.setProductionMode(DescriptorManagerHelper.isProductionMode());

         for(int i = 0; i < VersionConstants.NAMESPACE_MAPPING.length; ++i) {
            instance.addNamespaceMapping(VersionConstants.NAMESPACE_MAPPING[i][0], VersionConstants.NAMESPACE_MAPPING[i][1]);
            instance.setDescriptorMacroSubstitutor(new DomainMacroSubstitutor());
         }

      }
   }
}
