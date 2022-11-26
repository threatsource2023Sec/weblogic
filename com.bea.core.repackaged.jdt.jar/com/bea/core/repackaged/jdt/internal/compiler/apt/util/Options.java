package com.bea.core.repackaged.jdt.internal.compiler.apt.util;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public final class Options {
   private static final Set ZERO_ARGUMENT_OPTIONS = new HashSet();
   private static final Set ONE_ARGUMENT_OPTIONS;
   private static final Set FILE_MANAGER_OPTIONS;

   static {
      ZERO_ARGUMENT_OPTIONS.add("-progress");
      ZERO_ARGUMENT_OPTIONS.add("-proceedOnError");
      ZERO_ARGUMENT_OPTIONS.add("-proceedOnError:Fatal");
      ZERO_ARGUMENT_OPTIONS.add("-time");
      ZERO_ARGUMENT_OPTIONS.add("-v");
      ZERO_ARGUMENT_OPTIONS.add("-version");
      ZERO_ARGUMENT_OPTIONS.add("-showversion");
      ZERO_ARGUMENT_OPTIONS.add("-deprecation");
      ZERO_ARGUMENT_OPTIONS.add("-help");
      ZERO_ARGUMENT_OPTIONS.add("-?");
      ZERO_ARGUMENT_OPTIONS.add("-help:warn");
      ZERO_ARGUMENT_OPTIONS.add("-?:warn");
      ZERO_ARGUMENT_OPTIONS.add("-noExit");
      ZERO_ARGUMENT_OPTIONS.add("-verbose");
      ZERO_ARGUMENT_OPTIONS.add("-referenceInfo");
      ZERO_ARGUMENT_OPTIONS.add("-inlineJSR");
      ZERO_ARGUMENT_OPTIONS.add("-g");
      ZERO_ARGUMENT_OPTIONS.add("-g:none");
      ZERO_ARGUMENT_OPTIONS.add("-warn:none");
      ZERO_ARGUMENT_OPTIONS.add("-preserveAllLocals");
      ZERO_ARGUMENT_OPTIONS.add("-enableJavadoc");
      ZERO_ARGUMENT_OPTIONS.add("-Xemacs");
      ZERO_ARGUMENT_OPTIONS.add("-X");
      ZERO_ARGUMENT_OPTIONS.add("-O");
      ZERO_ARGUMENT_OPTIONS.add("-1.3");
      ZERO_ARGUMENT_OPTIONS.add("-1.4");
      ZERO_ARGUMENT_OPTIONS.add("-1.5");
      ZERO_ARGUMENT_OPTIONS.add("-5");
      ZERO_ARGUMENT_OPTIONS.add("-5.0");
      ZERO_ARGUMENT_OPTIONS.add("-1.6");
      ZERO_ARGUMENT_OPTIONS.add("-6");
      ZERO_ARGUMENT_OPTIONS.add("-6.0");
      ZERO_ARGUMENT_OPTIONS.add("-1.7");
      ZERO_ARGUMENT_OPTIONS.add("-7");
      ZERO_ARGUMENT_OPTIONS.add("-7.0");
      ZERO_ARGUMENT_OPTIONS.add("-1.8");
      ZERO_ARGUMENT_OPTIONS.add("-8");
      ZERO_ARGUMENT_OPTIONS.add("-8.0");
      ZERO_ARGUMENT_OPTIONS.add("-proc:only");
      ZERO_ARGUMENT_OPTIONS.add("-proc:none");
      ZERO_ARGUMENT_OPTIONS.add("-XprintProcessorInfo");
      ZERO_ARGUMENT_OPTIONS.add("-XprintRounds");
      ZERO_ARGUMENT_OPTIONS.add("-parameters");
      ZERO_ARGUMENT_OPTIONS.add("-genericsignature");
      FILE_MANAGER_OPTIONS = new HashSet();
      FILE_MANAGER_OPTIONS.add("-bootclasspath");
      FILE_MANAGER_OPTIONS.add("-encoding");
      FILE_MANAGER_OPTIONS.add("-d");
      FILE_MANAGER_OPTIONS.add("-classpath");
      FILE_MANAGER_OPTIONS.add("-cp");
      FILE_MANAGER_OPTIONS.add("-sourcepath");
      FILE_MANAGER_OPTIONS.add("-extdirs");
      FILE_MANAGER_OPTIONS.add("-endorseddirs");
      FILE_MANAGER_OPTIONS.add("-s");
      FILE_MANAGER_OPTIONS.add("-processorpath");
      ONE_ARGUMENT_OPTIONS = new HashSet();
      ONE_ARGUMENT_OPTIONS.addAll(FILE_MANAGER_OPTIONS);
      ONE_ARGUMENT_OPTIONS.add("-log");
      ONE_ARGUMENT_OPTIONS.add("-repeat");
      ONE_ARGUMENT_OPTIONS.add("-maxProblems");
      ONE_ARGUMENT_OPTIONS.add("-source");
      ONE_ARGUMENT_OPTIONS.add("-target");
      ONE_ARGUMENT_OPTIONS.add("-processor");
      ONE_ARGUMENT_OPTIONS.add("-classNames");
      ONE_ARGUMENT_OPTIONS.add("-properties");
   }

   public static int processOptionsFileManager(String option) {
      if (option == null) {
         return -1;
      } else {
         return FILE_MANAGER_OPTIONS.contains(option) ? 1 : -1;
      }
   }

   public static int processOptions(String option) {
      if (option == null) {
         return -1;
      } else if (ZERO_ARGUMENT_OPTIONS.contains(option)) {
         return 0;
      } else if (ONE_ARGUMENT_OPTIONS.contains(option)) {
         return 1;
      } else {
         int foldersStart;
         String folders;
         if (option.startsWith("-g")) {
            foldersStart = option.length();
            if (foldersStart > 3) {
               StringTokenizer tokenizer = new StringTokenizer(option.substring(3, option.length()), ",");

               do {
                  if (!tokenizer.hasMoreTokens()) {
                     return 0;
                  }

                  folders = tokenizer.nextToken();
               } while("vars".equals(folders) || "lines".equals(folders) || "source".equals(folders));

               return -1;
            } else {
               return -1;
            }
         } else if (option.startsWith("-warn")) {
            foldersStart = option.length();
            if (foldersStart <= 6) {
               return -1;
            } else {
               byte warnTokenStart;
               switch (option.charAt(6)) {
                  case '+':
                     warnTokenStart = 7;
                     break;
                  case ',':
                  default:
                     warnTokenStart = 6;
                     break;
                  case '-':
                     warnTokenStart = 7;
               }

               StringTokenizer tokenizer = new StringTokenizer(option.substring(warnTokenStart, option.length()), ",");
               int tokenCounter = 0;

               while(tokenizer.hasMoreTokens()) {
                  String token = tokenizer.nextToken();
                  ++tokenCounter;
                  if (!token.equals("allDeadCode") && !token.equals("allDeprecation") && !token.equals("allJavadoc") && !token.equals("allOver-ann") && !token.equals("assertIdentifier") && !token.equals("boxing") && !token.equals("charConcat") && !token.equals("compareIdentical") && !token.equals("conditionAssign") && !token.equals("constructorName") && !token.equals("deadCode") && !token.equals("dep-ann") && !token.equals("deprecation") && !token.equals("discouraged") && !token.equals("emptyBlock") && !token.equals("enumIdentifier") && !token.equals("enumSwitch") && !token.equals("fallthrough") && !token.equals("fieldHiding") && !token.equals("finalBound") && !token.equals("finally") && !token.equals("forbidden") && !token.equals("hashCode") && !token.equals("hiding") && !token.equals("includeAssertNull") && !token.equals("incomplete-switch") && !token.equals("indirectStatic") && !token.equals("interfaceNonInherited") && !token.equals("intfAnnotation") && !token.equals("intfNonInherited") && !token.equals("intfRedundant") && !token.equals("javadoc") && !token.equals("localHiding") && !token.equals("maskedCatchBlock") && !token.equals("maskedCatchBlocks") && !token.equals("nls") && !token.equals("noEffectAssign") && !token.equals("noImplicitStringConversion") && !token.equals("null") && !token.equals("nullDereference") && !token.equals("over-ann") && !token.equals("packageDefaultMethod") && !token.equals("paramAssign") && !token.equals("pkgDefaultMethod") && !token.equals("raw") && !token.equals("semicolon") && !token.equals("serial") && !token.equals("specialParamHiding") && !token.equals("static-access") && !token.equals("staticReceiver") && !token.equals("super") && !token.equals("suppress") && !token.equals("syncOverride") && !token.equals("synthetic-access") && !token.equals("syntheticAccess") && !token.equals("typeHiding") && !token.equals("unchecked") && !token.equals("unnecessaryElse") && !token.equals("unnecessaryOperator") && !token.equals("unqualified-field-access") && !token.equals("unqualifiedField") && !token.equals("unsafe") && !token.equals("unused") && !token.equals("unusedArgument") && !token.equals("unusedArguments") && !token.equals("unusedImport") && !token.equals("unusedImports") && !token.equals("unusedLabel") && !token.equals("unusedLocal") && !token.equals("unusedLocals") && !token.equals("unusedPrivate") && !token.equals("unusedThrown") && !token.equals("unusedTypeArgs") && !token.equals("uselessTypeCheck") && !token.equals("varargsCast") && !token.equals("warningToken")) {
                     if (!token.equals("tasks")) {
                        return -1;
                     }

                     String taskTags = "";
                     int start = token.indexOf(40);
                     int end = token.indexOf(41);
                     if (start >= 0 && end >= 0 && start < end) {
                        taskTags = token.substring(start + 1, end).trim();
                        taskTags = taskTags.replace('|', ',');
                     }

                     if (taskTags.length() == 0) {
                        return -1;
                     }
                  }
               }

               if (tokenCounter == 0) {
                  return -1;
               } else {
                  return 0;
               }
            }
         } else if (option.startsWith("-nowarn")) {
            switch (option.length()) {
               case 7:
                  return 0;
               case 8:
                  return -1;
               default:
                  foldersStart = option.indexOf(91) + 1;
                  int foldersEnd = option.lastIndexOf(93);
                  if (foldersStart > 8 && foldersEnd != -1 && foldersStart <= foldersEnd && foldersEnd >= option.length() - 1) {
                     folders = option.substring(foldersStart, foldersEnd);
                     return folders.length() > 0 ? 0 : -1;
                  } else {
                     return -1;
                  }
            }
         } else {
            return !option.startsWith("-J") && !option.startsWith("-X") && !option.startsWith("-A") ? -1 : 0;
         }
      }
   }
}
