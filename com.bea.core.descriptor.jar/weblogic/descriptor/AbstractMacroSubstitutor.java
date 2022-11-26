package weblogic.descriptor;

public abstract class AbstractMacroSubstitutor implements DescriptorMacroSubstitutor {
   private static final String START_MACRO = "${";
   private static final String END_MACRO = "}";

   public String substituteMacro(String inputValue, DescriptorMacroResolver resolver, DescriptorBean bean) {
      if (inputValue != null && resolver != null) {
         int start = 0;
         int idx = inputValue.indexOf("${");
         if (idx == -1) {
            return inputValue;
         } else {
            StringBuilder retStr = new StringBuilder();

            while(idx != -1) {
               retStr.append(inputValue.substring(start, idx));
               int end = inputValue.indexOf("}", idx);
               if (end == -1) {
                  start = idx;
                  idx = -1;
               } else {
                  String macro = inputValue.substring(idx + 2, end);
                  String macroVal = resolver.resolveMacroValue(macro, bean);
                  if (macroVal != null) {
                     retStr.append(macroVal);
                  }

                  start = end + 1;
                  idx = inputValue.indexOf("${", start);
               }
            }

            retStr.append(inputValue.substring(start));
            return retStr.toString();
         }
      } else {
         return inputValue;
      }
   }

   public abstract String performMacroSubstitution(String var1, DescriptorBean var2);
}
