package com.bea.core.repackaged.springframework.core.env;

class SimpleCommandLineArgsParser {
   public CommandLineArgs parse(String... args) {
      CommandLineArgs commandLineArgs = new CommandLineArgs();
      String[] var3 = args;
      int var4 = args.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String arg = var3[var5];
         if (arg.startsWith("--")) {
            String optionText = arg.substring(2, arg.length());
            String optionValue = null;
            String optionName;
            if (optionText.contains("=")) {
               optionName = optionText.substring(0, optionText.indexOf(61));
               optionValue = optionText.substring(optionText.indexOf(61) + 1, optionText.length());
            } else {
               optionName = optionText;
            }

            if (optionName.isEmpty() || optionValue != null && optionValue.isEmpty()) {
               throw new IllegalArgumentException("Invalid argument syntax: " + arg);
            }

            commandLineArgs.addOptionArg(optionName, optionValue);
         } else {
            commandLineArgs.addNonOptionArg(arg);
         }
      }

      return commandLineArgs;
   }
}
