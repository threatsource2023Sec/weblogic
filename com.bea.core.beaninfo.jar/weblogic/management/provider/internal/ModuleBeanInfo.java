package weblogic.management.provider.internal;

import java.util.ArrayList;
import java.util.List;
import weblogic.descriptor.DescriptorBean;

public class ModuleBeanInfo {
   private String name;
   private String type;
   private DescriptorBean stdDesc;
   private DescriptorBean configDesc;
   private List modules = new ArrayList();

   public ModuleBeanInfo(String name, String type, DescriptorBean stdDesc, DescriptorBean configDesc) {
      this.name = name;
      this.type = type;
      this.stdDesc = stdDesc;
      this.configDesc = configDesc;
   }

   public String getName() {
      return this.name;
   }

   public String getType() {
      return this.type;
   }

   public DescriptorBean getStdDesc() {
      return this.stdDesc;
   }

   public DescriptorBean getConfigDesc() {
      return this.configDesc;
   }

   public List getModules() {
      return this.modules;
   }

   public void addModule(ModuleBeanInfo module) {
      this.modules.add(module);
   }
}
