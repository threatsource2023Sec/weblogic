package com.bea.core.repackaged.jdt.core;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.antadapter.AntAdapterMessages;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.util.SuffixConstants;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.Javac;
import org.apache.tools.ant.taskdefs.compilers.DefaultCompilerAdapter;
import org.apache.tools.ant.types.Commandline;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.util.JavaEnvUtils;

public class JDTCompilerAdapter extends DefaultCompilerAdapter {
   private static final char[] SEPARATOR_CHARS = new char[]{'/', '\\'};
   private static final char[] ADAPTER_PREFIX = "#ADAPTER#".toCharArray();
   private static final char[] ADAPTER_ENCODING = "ENCODING#".toCharArray();
   private static final char[] ADAPTER_ACCESS = "ACCESS#".toCharArray();
   private static String compilerClass = "com.bea.core.repackaged.jdt.internal.compiler.batch.Main";
   String logFileName;
   Map customDefaultOptions;
   private Map fileEncodings = null;
   private Map dirEncodings = null;
   private List accessRules = null;

   public boolean execute() throws BuildException {
      this.attributes.log(AntAdapterMessages.getString("ant.jdtadapter.info.usingJDTCompiler"), 3);
      Commandline cmd = this.setupJavacCommand();

      try {
         Class c = Class.forName(compilerClass);
         Constructor batchCompilerConstructor = c.getConstructor(PrintWriter.class, PrintWriter.class, Boolean.TYPE, Map.class);
         Object batchCompilerInstance = batchCompilerConstructor.newInstance(new PrintWriter(System.out), new PrintWriter(System.err), Boolean.TRUE, this.customDefaultOptions);
         Method compile = c.getMethod("compile", String[].class);
         Object result = compile.invoke(batchCompilerInstance, cmd.getArguments());
         boolean resultValue = (Boolean)result;
         if (!resultValue && this.logFileName != null) {
            this.attributes.log(AntAdapterMessages.getString("ant.jdtadapter.error.compilationFailed", this.logFileName));
         }

         return resultValue;
      } catch (ClassNotFoundException var8) {
         throw new BuildException(AntAdapterMessages.getString("ant.jdtadapter.error.cannotFindJDTCompiler"), var8);
      } catch (Exception var9) {
         throw new BuildException(var9);
      }
   }

   protected Commandline setupJavacCommand() throws BuildException {
      Commandline cmd = new Commandline();
      this.customDefaultOptions = (new CompilerOptions()).getMap();
      Class javacClass = Javac.class;
      String[] compilerArgs = this.processCompilerArguments(javacClass);
      cmd.createArgument().setValue("-noExit");
      if (this.bootclasspath != null) {
         cmd.createArgument().setValue("-bootclasspath");
         if (this.bootclasspath.size() != 0) {
            cmd.createArgument().setPath(this.bootclasspath);
         } else {
            cmd.createArgument().setValue(Util.EMPTY_STRING);
         }
      }

      if (this.extdirs != null) {
         cmd.createArgument().setValue("-extdirs");
         cmd.createArgument().setPath(this.extdirs);
      }

      Path classpath = new Path(this.project);
      classpath.append(this.getCompileClasspath());
      cmd.createArgument().setValue("-classpath");
      this.createClasspathArgument(cmd, classpath);
      Path sourcepath = null;
      Method getSourcepathMethod = null;

      try {
         getSourcepathMethod = javacClass.getMethod("getSourcepath", (Class[])null);
      } catch (NoSuchMethodException var20) {
      }

      Path compileSourcePath = null;
      if (getSourcepathMethod != null) {
         try {
            compileSourcePath = (Path)getSourcepathMethod.invoke(this.attributes, (Object[])null);
         } catch (IllegalAccessException var18) {
         } catch (InvocationTargetException var19) {
         }
      }

      if (compileSourcePath != null) {
         sourcepath = compileSourcePath;
      } else {
         sourcepath = this.src;
      }

      cmd.createArgument().setValue("-sourcepath");
      this.createClasspathArgument(cmd, sourcepath);
      String javaVersion = JavaEnvUtils.getJavaVersion();
      String memoryParameterPrefix = javaVersion.equals("1.1") ? "-J-" : "-J-X";
      if (this.memoryInitialSize != null) {
         if (!this.attributes.isForkedJavac()) {
            this.attributes.log(AntAdapterMessages.getString("ant.jdtadapter.info.ignoringMemoryInitialSize"), 1);
         } else {
            cmd.createArgument().setValue(memoryParameterPrefix + "ms" + this.memoryInitialSize);
         }
      }

      if (this.memoryMaximumSize != null) {
         if (!this.attributes.isForkedJavac()) {
            this.attributes.log(AntAdapterMessages.getString("ant.jdtadapter.info.ignoringMemoryMaximumSize"), 1);
         } else {
            cmd.createArgument().setValue(memoryParameterPrefix + "mx" + this.memoryMaximumSize);
         }
      }

      if (this.debug) {
         Method getDebugLevelMethod = null;

         try {
            getDebugLevelMethod = javacClass.getMethod("getDebugLevel", (Class[])null);
         } catch (NoSuchMethodException var17) {
         }

         String debugLevel = null;
         if (getDebugLevelMethod != null) {
            try {
               debugLevel = (String)getDebugLevelMethod.invoke(this.attributes, (Object[])null);
            } catch (IllegalAccessException var15) {
            } catch (InvocationTargetException var16) {
            }
         }

         if (debugLevel != null) {
            this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.debug.localVariable", "do not generate");
            this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.debug.lineNumber", "do not generate");
            this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.debug.sourceFile", "do not generate");
            if (debugLevel.length() != 0) {
               if (debugLevel.indexOf("vars") != -1) {
                  this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.debug.localVariable", "generate");
               }

               if (debugLevel.indexOf("lines") != -1) {
                  this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.debug.lineNumber", "generate");
               }

               if (debugLevel.indexOf("source") != -1) {
                  this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.debug.sourceFile", "generate");
               }
            }
         } else {
            this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.debug.localVariable", "generate");
            this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.debug.lineNumber", "generate");
            this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.debug.sourceFile", "generate");
         }
      } else {
         this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.debug.localVariable", "do not generate");
         this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.debug.lineNumber", "do not generate");
         this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.debug.sourceFile", "do not generate");
      }

      int i;
      int length;
      if (this.attributes.getNowarn()) {
         Object[] entries = this.customDefaultOptions.entrySet().toArray();
         length = 0;

         for(i = entries.length; length < i; ++length) {
            Map.Entry entry = (Map.Entry)entries[length];
            if (entry.getKey() instanceof String && entry.getValue() instanceof String && ((String)entry.getValue()).equals("warning")) {
               this.customDefaultOptions.put(entry.getKey(), "ignore");
            }
         }

         this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.taskTags", Util.EMPTY_STRING);
         if (this.deprecation) {
            this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.problem.deprecation", "warning");
            this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.problem.deprecationInDeprecatedCode", "enabled");
            this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.problem.deprecationWhenOverridingDeprecatedMethod", "enabled");
         }
      } else if (this.deprecation) {
         this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.problem.deprecation", "warning");
         this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.problem.deprecationInDeprecatedCode", "enabled");
         this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.problem.deprecationWhenOverridingDeprecatedMethod", "enabled");
      } else {
         this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.problem.deprecation", "ignore");
         this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.problem.deprecationInDeprecatedCode", "disabled");
         this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.problem.deprecationWhenOverridingDeprecatedMethod", "disabled");
      }

      if (this.destDir != null) {
         cmd.createArgument().setValue("-d");
         cmd.createArgument().setFile(this.destDir.getAbsoluteFile());
      }

      if (this.verbose) {
         cmd.createArgument().setValue("-verbose");
      }

      if (!this.attributes.getFailonerror()) {
         cmd.createArgument().setValue("-proceedOnError");
      }

      if (this.target != null) {
         this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", this.target);
      }

      String source = this.attributes.getSource();
      if (source != null) {
         this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.source", source);
         this.customDefaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.compliance", source);
      }

      if (compilerArgs != null) {
         length = compilerArgs.length;
         if (length != 0) {
            i = 0;

            for(int max = length; i < max; ++i) {
               String arg = compilerArgs[i];
               if (this.logFileName == null && "-log".equals(arg) && i + 1 < max) {
                  this.logFileName = compilerArgs[i + 1];
               }

               cmd.createArgument().setValue(arg);
            }
         }
      }

      if (this.encoding != null) {
         cmd.createArgument().setValue("-encoding");
         cmd.createArgument().setValue(this.encoding);
      }

      this.logAndAddFilesToCompile(cmd);
      return cmd;
   }

   private String[] processCompilerArguments(Class javacClass) {
      Method getCurrentCompilerArgsMethod = null;

      try {
         getCurrentCompilerArgsMethod = javacClass.getMethod("getCurrentCompilerArgs", (Class[])null);
      } catch (NoSuchMethodException var6) {
      }

      String[] compilerArgs = null;
      if (getCurrentCompilerArgsMethod != null) {
         try {
            compilerArgs = (String[])getCurrentCompilerArgsMethod.invoke(this.attributes, (Object[])null);
         } catch (IllegalAccessException var4) {
         } catch (InvocationTargetException var5) {
         }
      }

      if (compilerArgs != null) {
         this.checkCompilerArgs(compilerArgs);
      }

      return compilerArgs;
   }

   private void checkCompilerArgs(String[] args) {
      for(int i = 0; i < args.length; ++i) {
         if (args[i].charAt(0) == '@') {
            try {
               char[] content = Util.getFileCharContent(new File(args[i].substring(1)), (String)null);
               int offset = 0;

               int end;
               for(int prefixLength = ADAPTER_PREFIX.length; (offset = CharOperation.indexOf(ADAPTER_PREFIX, content, true, offset)) > -1; offset = end) {
                  int start = offset + prefixLength;
                  end = CharOperation.indexOf('\n', content, start);
                  if (end == -1) {
                     end = content.length;
                  }

                  while(CharOperation.isWhitespace(content[end])) {
                     --end;
                  }

                  int accessStart;
                  String str;
                  if (CharOperation.equals(ADAPTER_ENCODING, content, start, start + ADAPTER_ENCODING.length)) {
                     CharOperation.replace(content, SEPARATOR_CHARS, File.separatorChar, start, end + 1);
                     start += ADAPTER_ENCODING.length;
                     accessStart = CharOperation.lastIndexOf('[', content, start, end);
                     if (start < accessStart && accessStart < end) {
                        boolean isFile = CharOperation.equals(SuffixConstants.SUFFIX_java, content, accessStart - 5, accessStart, false);
                        str = String.valueOf(content, start, accessStart - start);
                        String enc = String.valueOf(content, accessStart, end - accessStart + 1);
                        if (isFile) {
                           if (this.fileEncodings == null) {
                              this.fileEncodings = new HashMap();
                           }

                           this.fileEncodings.put(str, enc);
                        } else {
                           if (this.dirEncodings == null) {
                              this.dirEncodings = new HashMap();
                           }

                           this.dirEncodings.put(str, enc);
                        }
                     }
                  } else if (CharOperation.equals(ADAPTER_ACCESS, content, start, start + ADAPTER_ACCESS.length)) {
                     start += ADAPTER_ACCESS.length;
                     accessStart = CharOperation.indexOf('[', content, start, end);
                     CharOperation.replace(content, SEPARATOR_CHARS, File.separatorChar, start, accessStart);
                     if (start < accessStart && accessStart < end) {
                        String path = String.valueOf(content, start, accessStart - start);
                        str = String.valueOf(content, accessStart, end - accessStart + 1);
                        if (this.accessRules == null) {
                           this.accessRules = new ArrayList();
                        }

                        this.accessRules.add(path);
                        this.accessRules.add(str);
                     }
                  }
               }
            } catch (IOException var12) {
            }
         }
      }

   }

   private void createClasspathArgument(Commandline cmd, Path classpath) {
      Commandline.Argument arg = cmd.createArgument();
      String[] pathElements = classpath.list();
      if (pathElements.length == 0) {
         arg.setValue(Util.EMPTY_STRING);
      } else if (this.accessRules == null) {
         arg.setPath(classpath);
      } else {
         int rulesLength = this.accessRules.size();
         String[] rules = (String[])this.accessRules.toArray(new String[rulesLength]);
         int nextRule = 0;
         StringBuffer result = new StringBuffer();
         int i = 0;

         for(int max = pathElements.length; i < max; ++i) {
            if (i > 0) {
               result.append(File.pathSeparatorChar);
            }

            String pathElement = pathElements[i];
            result.append(pathElement);

            for(int j = nextRule; j < rulesLength; j += 2) {
               String rule = rules[j];
               if (pathElement.endsWith(rule)) {
                  result.append(rules[j + 1]);
                  nextRule = j + 2;
                  break;
               }

               int ruleLength;
               if (rule.endsWith(File.separator)) {
                  ruleLength = rule.length();
                  if (pathElement.regionMatches(false, pathElement.length() - ruleLength + 1, rule, 0, ruleLength - 1)) {
                     result.append(rules[j + 1]);
                     nextRule = j + 2;
                     break;
                  }
               } else if (pathElement.endsWith(File.separator)) {
                  ruleLength = rule.length();
                  if (pathElement.regionMatches(false, pathElement.length() - ruleLength - 1, rule, 0, ruleLength)) {
                     result.append(rules[j + 1]);
                     nextRule = j + 2;
                     break;
                  }
               }
            }
         }

         arg.setValue(result.toString());
      }
   }

   protected void logAndAddFilesToCompile(Commandline cmd) {
      this.attributes.log("Compilation " + cmd.describeArguments(), 3);
      StringBuffer niceSourceList = new StringBuffer("File");
      if (this.compileList.length != 1) {
         niceSourceList.append("s");
      }

      niceSourceList.append(" to be compiled:");
      niceSourceList.append(System.lineSeparator());
      String[] encodedFiles = null;
      String[] encodedDirs = null;
      int encodedFilesLength = 0;
      int encodedDirsLength = 0;
      if (this.fileEncodings != null) {
         encodedFilesLength = this.fileEncodings.size();
         encodedFiles = new String[encodedFilesLength];
         this.fileEncodings.keySet().toArray(encodedFiles);
      }

      if (this.dirEncodings != null) {
         encodedDirsLength = this.dirEncodings.size();
         encodedDirs = new String[encodedDirsLength];
         this.dirEncodings.keySet().toArray(encodedDirs);
         Comparator comparator = new Comparator() {
            public int compare(Object o1, Object o2) {
               return ((String)o2).length() - ((String)o1).length();
            }
         };
         Arrays.sort(encodedDirs, comparator);
      }

      for(int i = 0; i < this.compileList.length; ++i) {
         String arg = this.compileList[i].getAbsolutePath();
         boolean encoded = false;
         int j;
         if (encodedFiles != null) {
            for(j = 0; j < encodedFilesLength; ++j) {
               if (arg.endsWith(encodedFiles[j])) {
                  arg = arg + (String)this.fileEncodings.get(encodedFiles[j]);
                  if (j < encodedFilesLength - 1) {
                     System.arraycopy(encodedFiles, j + 1, encodedFiles, j, encodedFilesLength - j - 1);
                  }

                  --encodedFilesLength;
                  encodedFiles[encodedFilesLength] = null;
                  encoded = true;
                  break;
               }
            }
         }

         if (!encoded && encodedDirs != null) {
            for(j = 0; j < encodedDirsLength; ++j) {
               if (arg.lastIndexOf(encodedDirs[j]) != -1) {
                  arg = arg + (String)this.dirEncodings.get(encodedDirs[j]);
                  break;
               }
            }
         }

         cmd.createArgument().setValue(arg);
         niceSourceList.append("    " + arg + System.lineSeparator());
      }

      this.attributes.log(niceSourceList.toString(), 3);
   }
}
