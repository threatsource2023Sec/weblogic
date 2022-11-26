package weblogic.management.provider.internal;

import weblogic.descriptor.AbstractMacroSubstitutor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorMacroResolver;
import weblogic.management.configuration.PartitionMBean;

public class PartitionMacroSubstitutor extends AbstractMacroSubstitutor {
   protected static final DescriptorMacroResolver resolver = new PartitionMacroResolver();

   public String performMacroSubstitution(String input, DescriptorBean bean) {
      if (!(bean instanceof PartitionMBean)) {
         throw new IllegalArgumentException("supplied bean must be of type PartitionMBean");
      } else {
         return this.substituteMacro(input, resolver, bean);
      }
   }

   protected static class PartitionMacroResolver implements DescriptorMacroResolver {
      public String resolveMacroValue(String macro, DescriptorBean bean) {
         if (!(bean instanceof PartitionMBean)) {
            throw new IllegalArgumentException("supplied bean must be of type PartitionMBean");
         } else if (macro != null && !macro.isEmpty()) {
            PartitionMBean part = (PartitionMBean)bean;
            if (macro.equals("partition.name")) {
               return part.getName();
            } else {
               return macro.equals("partition.id") ? String.valueOf(part.getPartitionID()) : "";
            }
         } else {
            return "";
         }
      }
   }
}
