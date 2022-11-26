package com.bea.core.repackaged.jdt.internal.compiler.batch;

import com.bea.core.repackaged.jdt.core.compiler.CategorizedProblem;
import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.core.compiler.CompilationProgress;
import com.bea.core.repackaged.jdt.core.compiler.IProblem;
import com.bea.core.repackaged.jdt.internal.compiler.AbstractAnnotationProcessorManager;
import com.bea.core.repackaged.jdt.internal.compiler.ClassFile;
import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;
import com.bea.core.repackaged.jdt.internal.compiler.Compiler;
import com.bea.core.repackaged.jdt.internal.compiler.ICompilerRequestor;
import com.bea.core.repackaged.jdt.internal.compiler.IErrorHandlingPolicy;
import com.bea.core.repackaged.jdt.internal.compiler.IProblemFactory;
import com.bea.core.repackaged.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFileConstants;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFileReader;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFormatException;
import com.bea.core.repackaged.jdt.internal.compiler.env.AccessRule;
import com.bea.core.repackaged.jdt.internal.compiler.env.AccessRuleSet;
import com.bea.core.repackaged.jdt.internal.compiler.env.ICompilationUnit;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModule;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModulePathEntry;
import com.bea.core.repackaged.jdt.internal.compiler.env.IUpdatableModule;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerStats;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.PackageBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import com.bea.core.repackaged.jdt.internal.compiler.parser.Parser;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortCompilation;
import com.bea.core.repackaged.jdt.internal.compiler.problem.DefaultProblem;
import com.bea.core.repackaged.jdt.internal.compiler.problem.DefaultProblemFactory;
import com.bea.core.repackaged.jdt.internal.compiler.problem.ProblemReporter;
import com.bea.core.repackaged.jdt.internal.compiler.problem.ProblemSeverities;
import com.bea.core.repackaged.jdt.internal.compiler.util.GenericXMLWriter;
import com.bea.core.repackaged.jdt.internal.compiler.util.HashtableOfInt;
import com.bea.core.repackaged.jdt.internal.compiler.util.HashtableOfObject;
import com.bea.core.repackaged.jdt.internal.compiler.util.Messages;
import com.bea.core.repackaged.jdt.internal.compiler.util.SuffixConstants;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;

public class Main implements ProblemSeverities, SuffixConstants {
   private static final String ANNOTATION_SOURCE_CLASSPATH = "CLASSPATH";
   boolean enableJavadocOn;
   boolean warnJavadocOn;
   boolean warnAllJavadocOn;
   public Compiler batchCompiler;
   public ResourceBundle bundle;
   protected FileSystem.Classpath[] checkedClasspaths;
   protected IModule module;
   protected List annotationPaths;
   protected boolean annotationsFromClasspath;
   private List addonExports;
   private List addonReads;
   public Set rootModules;
   public Set limitedModules;
   public Locale compilerLocale;
   public CompilerOptions compilerOptions;
   public CompilationProgress progress;
   public String destinationPath;
   public String[] destinationPaths;
   protected boolean enablePreview;
   protected String releaseVersion;
   private boolean didSpecifySource;
   private boolean didSpecifyTarget;
   public String[] encodings;
   public int exportedClassFilesCounter;
   public String[] filenames;
   public String[] modNames;
   public String[] classNames;
   public int globalErrorsCount;
   public int globalProblemsCount;
   public int globalTasksCount;
   public int globalWarningsCount;
   public int globalInfoCount;
   private File javaHomeCache;
   private boolean javaHomeChecked;
   private boolean primaryNullAnnotationsSeen;
   public long lineCount0;
   public String log;
   public Logger logger;
   public int maxProblems;
   public Map options;
   protected long complianceLevel;
   public char[][] ignoreOptionalProblemsFromFolders;
   protected PrintWriter out;
   public boolean proceed;
   public boolean proceedOnError;
   public boolean produceRefInfo;
   public int currentRepetition;
   public int maxRepetition;
   public boolean showProgress;
   public long startTime;
   public ArrayList pendingErrors;
   public boolean systemExitWhenFinished;
   public static final int TIMING_DISABLED = 0;
   public static final int TIMING_ENABLED = 1;
   public static final int TIMING_DETAILED = 2;
   public int timing;
   public CompilerStats[] compilerStats;
   public boolean verbose;
   private String[] expandedCommandLine;
   private PrintWriter err;
   protected ArrayList extraProblems;
   public static final String bundleName = "com.bea.core.repackaged.jdt.internal.compiler.batch.messages";
   public static final int DEFAULT_SIZE_CLASSPATH = 4;
   public static final String NONE = "none";

   /** @deprecated */
   public static boolean compile(String commandLine) {
      return (new Main(new PrintWriter(System.out), new PrintWriter(System.err), false, (Map)null, (CompilationProgress)null)).compile(tokenize(commandLine));
   }

   /** @deprecated */
   public static boolean compile(String commandLine, PrintWriter outWriter, PrintWriter errWriter) {
      return (new Main(outWriter, errWriter, false, (Map)null, (CompilationProgress)null)).compile(tokenize(commandLine));
   }

   public static boolean compile(String[] commandLineArguments, PrintWriter outWriter, PrintWriter errWriter, CompilationProgress progress) {
      return (new Main(outWriter, errWriter, false, (Map)null, progress)).compile(commandLineArguments);
   }

   public static File[][] getLibrariesFiles(File[] files) {
      FilenameFilter filter = new FilenameFilter() {
         public boolean accept(File dir, String name) {
            return Util.archiveFormat(name) > -1;
         }
      };
      int filesLength = files.length;
      File[][] result = new File[filesLength][];

      for(int i = 0; i < filesLength; ++i) {
         File currentFile = files[i];
         if (currentFile.exists() && currentFile.isDirectory()) {
            result[i] = currentFile.listFiles(filter);
         }
      }

      return result;
   }

   public static void main(String[] argv) {
      (new Main(new PrintWriter(System.out), new PrintWriter(System.err), true, (Map)null, (CompilationProgress)null)).compile(argv);
   }

   public static String[] tokenize(String commandLine) {
      int count = 0;
      String[] arguments = new String[10];
      StringTokenizer tokenizer = new StringTokenizer(commandLine, " \"", true);
      String token = Util.EMPTY_STRING;
      boolean insideQuotes = false;
      boolean startNewToken = true;

      while(true) {
         while(tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
            if (token.equals(" ")) {
               if (insideQuotes) {
                  arguments[count - 1] = arguments[count - 1] + token;
                  startNewToken = false;
               } else {
                  startNewToken = true;
               }
            } else if (token.equals("\"")) {
               if (!insideQuotes && startNewToken) {
                  if (count == arguments.length) {
                     System.arraycopy(arguments, 0, arguments = new String[count * 2], 0, count);
                  }

                  arguments[count++] = Util.EMPTY_STRING;
               }

               insideQuotes = !insideQuotes;
               startNewToken = false;
            } else {
               if (insideQuotes) {
                  arguments[count - 1] = arguments[count - 1] + token;
               } else if (token.length() > 0 && !startNewToken) {
                  arguments[count - 1] = arguments[count - 1] + token;
               } else {
                  if (count == arguments.length) {
                     System.arraycopy(arguments, 0, arguments = new String[count * 2], 0, count);
                  }

                  String trimmedToken = token.trim();
                  if (trimmedToken.length() != 0) {
                     arguments[count++] = trimmedToken;
                  }
               }

               startNewToken = false;
            }
         }

         System.arraycopy(arguments, 0, arguments = new String[count], 0, count);
         return arguments;
      }
   }

   /** @deprecated */
   public Main(PrintWriter outWriter, PrintWriter errWriter, boolean systemExitWhenFinished) {
      this(outWriter, errWriter, systemExitWhenFinished, (Map)null, (CompilationProgress)null);
   }

   /** @deprecated */
   public Main(PrintWriter outWriter, PrintWriter errWriter, boolean systemExitWhenFinished, Map customDefaultOptions) {
      this(outWriter, errWriter, systemExitWhenFinished, customDefaultOptions, (CompilationProgress)null);
   }

   public Main(PrintWriter outWriter, PrintWriter errWriter, boolean systemExitWhenFinished, Map customDefaultOptions, CompilationProgress compilationProgress) {
      this.addonExports = Collections.EMPTY_LIST;
      this.addonReads = Collections.EMPTY_LIST;
      this.rootModules = Collections.EMPTY_SET;
      this.javaHomeChecked = false;
      this.primaryNullAnnotationsSeen = false;
      this.proceed = true;
      this.proceedOnError = false;
      this.produceRefInfo = false;
      this.showProgress = false;
      this.systemExitWhenFinished = true;
      this.timing = 0;
      this.verbose = false;
      this.initialize(outWriter, errWriter, systemExitWhenFinished, customDefaultOptions, compilationProgress);
      this.relocalize();
   }

   public void addExtraProblems(CategorizedProblem problem) {
      if (this.extraProblems == null) {
         this.extraProblems = new ArrayList();
      }

      this.extraProblems.add(problem);
   }

   protected void addNewEntry(ArrayList paths, String currentClasspathName, ArrayList currentRuleSpecs, String customEncoding, String destPath, boolean isSourceOnly, boolean rejectDestinationPathOnJars) {
      int rulesSpecsSize = currentRuleSpecs.size();
      AccessRuleSet accessRuleSet = null;
      if (rulesSpecsSize != 0) {
         AccessRule[] accessRules = new AccessRule[currentRuleSpecs.size()];
         boolean rulesOK = true;
         Iterator i = currentRuleSpecs.iterator();
         int j = 0;

         while(i.hasNext()) {
            String ruleSpec = (String)i.next();
            char key = ruleSpec.charAt(0);
            String pattern = ruleSpec.substring(1);
            if (pattern.length() > 0) {
               switch (key) {
                  case '+':
                     accessRules[j++] = new AccessRule(pattern.toCharArray(), 0);
                     break;
                  case '-':
                     accessRules[j++] = new AccessRule(pattern.toCharArray(), 16777523);
                     break;
                  case '?':
                     accessRules[j++] = new AccessRule(pattern.toCharArray(), 16777523, true);
                     break;
                  case '~':
                     accessRules[j++] = new AccessRule(pattern.toCharArray(), 16777496);
                     break;
                  default:
                     rulesOK = false;
               }
            } else {
               rulesOK = false;
            }
         }

         if (!rulesOK) {
            if (currentClasspathName.length() != 0) {
               this.addPendingErrors(this.bind("configure.incorrectClasspath", currentClasspathName));
            }

            return;
         }

         accessRuleSet = new AccessRuleSet(accessRules, (byte)0, currentClasspathName);
      }

      if ("none".equals(destPath)) {
         destPath = "none";
      }

      if (rejectDestinationPathOnJars && destPath != null && Util.archiveFormat(currentClasspathName) > -1) {
         throw new IllegalArgumentException(this.bind("configure.unexpectedDestinationPathEntryFile", currentClasspathName));
      } else {
         FileSystem.Classpath currentClasspath = FileSystem.getClasspath(currentClasspathName, customEncoding, isSourceOnly, accessRuleSet, destPath, this.options, this.releaseVersion);
         if (currentClasspath != null) {
            paths.add(currentClasspath);
         } else if (currentClasspathName.length() != 0) {
            this.addPendingErrors(this.bind("configure.incorrectClasspath", currentClasspathName));
         }

      }
   }

   void addPendingErrors(String message) {
      if (this.pendingErrors == null) {
         this.pendingErrors = new ArrayList();
      }

      this.pendingErrors.add(message);
   }

   public String bind(String id) {
      return this.bind(id, (String[])null);
   }

   public String bind(String id, String binding) {
      return this.bind(id, new String[]{binding});
   }

   public String bind(String id, String binding1, String binding2) {
      return this.bind(id, new String[]{binding1, binding2});
   }

   public String bind(String id, String[] arguments) {
      if (id == null) {
         return "No message available";
      } else {
         String message = null;

         try {
            message = this.bundle.getString(id);
         } catch (MissingResourceException var4) {
            return "Missing message: " + id + " in: " + "com.bea.core.repackaged.jdt.internal.compiler.batch.messages";
         }

         return MessageFormat.format(message, arguments);
      }
   }

   private boolean checkVMVersion(long minimalSupportedVersion) {
      String classFileVersion = System.getProperty("java.class.version");
      if (classFileVersion == null) {
         return false;
      } else {
         int index = classFileVersion.indexOf(46);
         if (index == -1) {
            return false;
         } else {
            int majorVersion;
            try {
               majorVersion = Integer.parseInt(classFileVersion.substring(0, index));
            } catch (NumberFormatException var6) {
               return false;
            }

            return ClassFileConstants.getComplianceLevelForJavaVersion(majorVersion) >= minimalSupportedVersion;
         }
      }
   }

   public boolean compile(String[] argv) {
      try {
         this.configure(argv);
         if (this.progress != null) {
            this.progress.begin(this.filenames == null ? 0 : this.filenames.length * this.maxRepetition);
         }

         if (this.proceed) {
            if (this.showProgress) {
               this.logger.compiling();
            }

            for(this.currentRepetition = 0; this.currentRepetition < this.maxRepetition; ++this.currentRepetition) {
               this.globalProblemsCount = 0;
               this.globalErrorsCount = 0;
               this.globalWarningsCount = 0;
               this.globalInfoCount = 0;
               this.globalTasksCount = 0;
               this.exportedClassFilesCounter = 0;
               if (this.maxRepetition > 1) {
                  this.logger.flush();
                  this.logger.logRepetition(this.currentRepetition, this.maxRepetition);
               }

               this.performCompilation();
            }

            if (this.compilerStats != null) {
               this.logger.logAverage();
            }

            if (this.showProgress) {
               this.logger.printNewLine();
            }
         }

         if (this.systemExitWhenFinished) {
            this.logger.flush();
            this.logger.close();
            System.exit(this.globalErrorsCount > 0 ? -1 : 0);
         }

         return this.globalErrorsCount == 0 && (this.progress == null || !this.progress.isCanceled());
      } catch (IllegalArgumentException var7) {
         this.logger.logException(var7);
         if (this.systemExitWhenFinished) {
            this.logger.flush();
            this.logger.close();
            System.exit(-1);
         }
      } catch (Exception var8) {
         this.logger.logException(var8);
         if (this.systemExitWhenFinished) {
            this.logger.flush();
            this.logger.close();
            System.exit(-1);
         }

         return false;
      } finally {
         this.logger.flush();
         this.logger.close();
         if (this.progress != null) {
            this.progress.done();
         }

      }

      return false;
   }

   public void configure(String[] argv) {
      if (argv != null && argv.length != 0) {
         ArrayList bootclasspaths = new ArrayList(4);
         String sourcepathClasspathArg = null;
         String modulepathArg = null;
         String moduleSourcepathArg = null;
         ArrayList sourcepathClasspaths = new ArrayList(4);
         ArrayList classpaths = new ArrayList(4);
         ArrayList extdirsClasspaths = null;
         ArrayList endorsedDirClasspaths = null;
         this.annotationPaths = null;
         this.annotationsFromClasspath = false;
         int index = -1;
         int filesCount = 0;
         int classCount = 0;
         int argCount = argv.length;
         int mode = 0;
         this.maxRepetition = 0;
         boolean printUsageRequired = false;
         String usageSection = null;
         boolean printVersionRequired = false;
         boolean didSpecifyDeprecation = false;
         boolean didSpecifyCompliance = false;
         boolean didSpecifyDisabledAnnotationProcessing = false;
         String customEncoding = null;
         String customDestinationPath = null;
         String currentSourceDirectory = null;
         String currentArg = Util.EMPTY_STRING;
         String moduleName = null;
         Set specifiedEncodings = null;
         boolean needExpansion = false;

         for(int i = 0; i < argCount; ++i) {
            if (argv[i].startsWith("@")) {
               needExpansion = true;
               break;
            }
         }

         String[] newCommandLineArgs = null;
         int length;
         String[] result;
         String folders;
         int i;
         int length;
         if (needExpansion) {
            newCommandLineArgs = new String[argCount];
            index = 0;

            for(length = 0; length < argCount; ++length) {
               result = null;
               folders = argv[length].trim();
               if (folders.startsWith("@")) {
                  try {
                     LineNumberReader reader = new LineNumberReader(new StringReader(new String(Util.getFileCharContent(new File(folders.substring(1)), (String)null))));
                     StringBuffer buffer = new StringBuffer();

                     String line;
                     while((line = reader.readLine()) != null) {
                        line = line.trim();
                        if (!line.startsWith("#")) {
                           buffer.append(line).append(" ");
                        }
                     }

                     result = tokenize(buffer.toString());
                  } catch (IOException var41) {
                     throw new IllegalArgumentException(this.bind("configure.invalidexpansionargumentname", folders));
                  }
               }

               if (result != null) {
                  i = newCommandLineArgs.length;
                  length = result.length;
                  System.arraycopy(newCommandLineArgs, 0, newCommandLineArgs = new String[i + length - 1], 0, index);
                  System.arraycopy(result, 0, newCommandLineArgs, index, length);
                  index += length;
               } else {
                  newCommandLineArgs[index++] = folders;
               }
            }

            index = -1;
         } else {
            newCommandLineArgs = argv;

            for(length = 0; length < argCount; ++length) {
               newCommandLineArgs[length] = newCommandLineArgs[length].trim();
            }
         }

         argCount = newCommandLineArgs.length;
         this.expandedCommandLine = newCommandLineArgs;

         label1156:
         while(true) {
            ++index;
            if (index >= argCount) {
               if (this.enablePreview) {
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.enablePreviewFeatures", "enabled");
               }

               if (this.enableJavadocOn) {
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.doc.comment.support", "enabled");
               } else if (this.warnJavadocOn || this.warnAllJavadocOn) {
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.doc.comment.support", "enabled");
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameterIncludeDocCommentReference", "disabled");
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedDeclaredThrownExceptionIncludeDocCommentReference", "disabled");
               }

               if (this.warnJavadocOn) {
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTags", "enabled");
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTagsDeprecatedRef", "enabled");
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTagsNotVisibleRef", "enabled");
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagsVisibility", "private");
               }

               if (!printUsageRequired && (filesCount != 0 || classCount != 0)) {
                  if (this.log != null) {
                     this.logger.setLog(this.log);
                  } else {
                     this.showProgress = false;
                  }

                  this.logger.logVersion(printVersionRequired);
                  this.validateOptions(didSpecifyCompliance);
                  if (!didSpecifyDisabledAnnotationProcessing && CompilerOptions.versionToJdkLevel((String)this.options.get("com.bea.core.repackaged.jdt.core.compiler.compliance")) >= 3276800L) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.processAnnotations", "enabled");
                  }

                  this.logger.logCommandLineArguments(newCommandLineArgs);
                  this.logger.logOptions(this.options);
                  if (this.maxRepetition == 0) {
                     this.maxRepetition = 1;
                  }

                  if (this.maxRepetition >= 3 && (this.timing & 1) != 0) {
                     this.compilerStats = new CompilerStats[this.maxRepetition];
                  }

                  if (filesCount != 0) {
                     System.arraycopy(this.filenames, 0, this.filenames = new String[filesCount], 0, filesCount);
                  }

                  if (classCount != 0) {
                     System.arraycopy(this.classNames, 0, this.classNames = new String[classCount], 0, classCount);
                  }

                  this.setPaths(bootclasspaths, sourcepathClasspathArg, sourcepathClasspaths, classpaths, modulepathArg, moduleSourcepathArg, extdirsClasspaths, endorsedDirClasspaths, customEncoding);
                  if (specifiedEncodings != null && specifiedEncodings.size() > 1) {
                     this.logger.logWarning(this.bind("configure.multipleencodings", (String)this.options.get("com.bea.core.repackaged.jdt.core.encoding"), getAllEncodings(specifiedEncodings)));
                  }

                  if (this.pendingErrors != null) {
                     Iterator iterator = this.pendingErrors.iterator();

                     while(iterator.hasNext()) {
                        String message = (String)iterator.next();
                        this.logger.logPendingError(message);
                     }

                     this.pendingErrors = null;
                  }

                  return;
               }

               if (usageSection == null) {
                  this.printUsage();
               } else {
                  this.printUsage(usageSection);
               }

               this.proceed = false;
               return;
            }

            if (customEncoding != null) {
               throw new IllegalArgumentException(this.bind("configure.unexpectedCustomEncoding", currentArg, customEncoding));
            }

            currentArg = newCommandLineArgs[index];
            int errorTokenStart;
            StringTokenizer tokenizer;
            switch (mode) {
               case 0:
                  int length;
                  if (currentArg.startsWith("-nowarn")) {
                     switch (currentArg.length()) {
                        case 7:
                           this.disableAll(0);
                           break;
                        case 8:
                           throw new IllegalArgumentException(this.bind("configure.invalidNowarnOption", currentArg));
                        default:
                           length = currentArg.indexOf(91) + 1;
                           length = currentArg.lastIndexOf(93);
                           if (length <= 8 || length == -1 || length > length || length < currentArg.length() - 1) {
                              throw new IllegalArgumentException(this.bind("configure.invalidNowarnOption", currentArg));
                           }

                           folders = currentArg.substring(length, length);
                           if (folders.length() <= 0) {
                              throw new IllegalArgumentException(this.bind("configure.invalidNowarnOption", currentArg));
                           }

                           char[][] currentFolders = decodeIgnoreOptionalProblemsFromFolders(folders);
                           if (this.ignoreOptionalProblemsFromFolders != null) {
                              length = this.ignoreOptionalProblemsFromFolders.length + currentFolders.length;
                              char[][] tempFolders = new char[length][];
                              System.arraycopy(this.ignoreOptionalProblemsFromFolders, 0, tempFolders, 0, this.ignoreOptionalProblemsFromFolders.length);
                              System.arraycopy(currentFolders, 0, tempFolders, this.ignoreOptionalProblemsFromFolders.length, currentFolders.length);
                              this.ignoreOptionalProblemsFromFolders = tempFolders;
                           } else {
                              this.ignoreOptionalProblemsFromFolders = currentFolders;
                           }
                     }

                     mode = 0;
                     continue;
                  }

                  if (currentArg.startsWith("[")) {
                     throw new IllegalArgumentException(this.bind("configure.unexpectedBracket", currentArg));
                  }

                  if (currentArg.endsWith("]")) {
                     length = currentArg.indexOf(91) + 1;
                     if (length <= 1) {
                        throw new IllegalArgumentException(this.bind("configure.unexpectedBracket", currentArg));
                     }

                     length = currentArg.length() - 1;
                     if (length >= 1) {
                        if (length < length) {
                           customEncoding = currentArg.substring(length, length);

                           try {
                              new InputStreamReader(new ByteArrayInputStream(new byte[0]), customEncoding);
                           } catch (UnsupportedEncodingException var37) {
                              throw new IllegalArgumentException(this.bind("configure.unsupportedEncoding", customEncoding), var37);
                           }
                        }

                        currentArg = currentArg.substring(0, length - 1);
                     }
                  }

                  if (currentArg.endsWith(".java")) {
                     if (moduleName == null) {
                        IModule mod = this.extractModuleDesc(currentArg);
                        if (mod != null) {
                           moduleName = new String(mod.name());
                           this.module = mod;
                        }
                     }

                     if (this.filenames == null) {
                        this.filenames = new String[argCount - index];
                        this.encodings = new String[argCount - index];
                        this.modNames = new String[argCount - index];
                        this.destinationPaths = new String[argCount - index];
                     } else if (filesCount == this.filenames.length) {
                        length = this.filenames.length;
                        System.arraycopy(this.filenames, 0, this.filenames = new String[length + argCount - index], 0, length);
                        System.arraycopy(this.encodings, 0, this.encodings = new String[length + argCount - index], 0, length);
                        System.arraycopy(this.destinationPaths, 0, this.destinationPaths = new String[length + argCount - index], 0, length);
                        System.arraycopy(this.modNames, 0, this.modNames = new String[length + argCount - index], 0, length);
                     }

                     this.filenames[filesCount] = currentArg;
                     this.modNames[filesCount] = moduleName;
                     this.encodings[filesCount++] = customEncoding;
                     customEncoding = null;
                     mode = 0;
                     continue;
                  }

                  if (currentArg.equals("-log")) {
                     if (this.log != null) {
                        throw new IllegalArgumentException(this.bind("configure.duplicateLog", currentArg));
                     }

                     mode = 5;
                     continue;
                  }

                  if (currentArg.equals("-repeat")) {
                     if (this.maxRepetition > 0) {
                        throw new IllegalArgumentException(this.bind("configure.duplicateRepeat", currentArg));
                     }

                     mode = 6;
                     continue;
                  }

                  if (currentArg.equals("-maxProblems")) {
                     if (this.maxProblems > 0) {
                        throw new IllegalArgumentException(this.bind("configure.duplicateMaxProblems", currentArg));
                     }

                     mode = 11;
                     continue;
                  }

                  if (currentArg.equals("--release")) {
                     mode = 30;
                     continue;
                  }

                  if (currentArg.equals("-source")) {
                     mode = 7;
                     continue;
                  }

                  if (currentArg.equals("-encoding")) {
                     mode = 8;
                     continue;
                  }

                  if (currentArg.equals("-1.3")) {
                     if (didSpecifyCompliance) {
                        throw new IllegalArgumentException(this.bind("configure.duplicateCompliance", currentArg));
                     }

                     didSpecifyCompliance = true;
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.compliance", "1.3");
                     mode = 0;
                     continue;
                  }

                  if (currentArg.equals("-1.4")) {
                     if (didSpecifyCompliance) {
                        throw new IllegalArgumentException(this.bind("configure.duplicateCompliance", currentArg));
                     }

                     didSpecifyCompliance = true;
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.compliance", "1.4");
                     mode = 0;
                     continue;
                  }

                  if (!currentArg.equals("-1.5") && !currentArg.equals("-5") && !currentArg.equals("-5.0")) {
                     if (!currentArg.equals("-1.6") && !currentArg.equals("-6") && !currentArg.equals("-6.0")) {
                        if (!currentArg.equals("-1.7") && !currentArg.equals("-7") && !currentArg.equals("-7.0")) {
                           if (!currentArg.equals("-1.8") && !currentArg.equals("-8") && !currentArg.equals("-8.0")) {
                              if (!currentArg.equals("-1.9") && !currentArg.equals("-9") && !currentArg.equals("-9.0")) {
                                 if (!currentArg.equals("-10") && !currentArg.equals("-10.0")) {
                                    if (!currentArg.equals("-11") && !currentArg.equals("-11.0")) {
                                       if (!currentArg.equals("-12") && !currentArg.equals("-12.0")) {
                                          StringBuffer errorMessage;
                                          if (currentArg.equals("-d")) {
                                             if (this.destinationPath != null) {
                                                errorMessage = new StringBuffer();
                                                errorMessage.append(currentArg);
                                                if (index + 1 < argCount) {
                                                   errorMessage.append(' ');
                                                   errorMessage.append(newCommandLineArgs[index + 1]);
                                                }

                                                throw new IllegalArgumentException(this.bind("configure.duplicateOutputPath", errorMessage.toString()));
                                             }

                                             mode = 3;
                                             continue;
                                          }

                                          if (!currentArg.equals("-classpath") && !currentArg.equals("-cp")) {
                                             if (currentArg.equals("-bootclasspath")) {
                                                if (bootclasspaths.size() > 0) {
                                                   errorMessage = new StringBuffer();
                                                   errorMessage.append(currentArg);
                                                   if (index + 1 < argCount) {
                                                      errorMessage.append(' ');
                                                      errorMessage.append(newCommandLineArgs[index + 1]);
                                                   }

                                                   throw new IllegalArgumentException(this.bind("configure.duplicateBootClasspath", errorMessage.toString()));
                                                }

                                                mode = 9;
                                                continue;
                                             }

                                             if (currentArg.equals("--enable-preview")) {
                                                this.enablePreview = true;
                                                mode = 0;
                                                continue;
                                             }

                                             if (currentArg.equals("--system")) {
                                                mode = 27;
                                                continue;
                                             }

                                             if (!currentArg.equals("--module-path") && !currentArg.equals("-p") && !currentArg.equals("--processor-module-path")) {
                                                if (currentArg.equals("--module-source-path")) {
                                                   if (sourcepathClasspathArg != null) {
                                                      throw new IllegalArgumentException(this.bind("configure.OneOfModuleOrSourcePath"));
                                                   }

                                                   mode = 24;
                                                   continue;
                                                }

                                                if (currentArg.equals("--add-exports")) {
                                                   mode = 25;
                                                   continue;
                                                }

                                                if (currentArg.equals("--add-reads")) {
                                                   mode = 26;
                                                   continue;
                                                }

                                                if (currentArg.equals("--add-modules")) {
                                                   mode = 29;
                                                   continue;
                                                }

                                                if (currentArg.equals("--limit-modules")) {
                                                   mode = 31;
                                                   continue;
                                                }

                                                if (currentArg.equals("-sourcepath")) {
                                                   if (sourcepathClasspathArg != null) {
                                                      errorMessage = new StringBuffer();
                                                      errorMessage.append(currentArg);
                                                      if (index + 1 < argCount) {
                                                         errorMessage.append(' ');
                                                         errorMessage.append(newCommandLineArgs[index + 1]);
                                                      }

                                                      throw new IllegalArgumentException(this.bind("configure.duplicateSourcepath", errorMessage.toString()));
                                                   }

                                                   if (moduleSourcepathArg != null) {
                                                      throw new IllegalArgumentException(this.bind("configure.OneOfModuleOrSourcePath"));
                                                   }

                                                   mode = 13;
                                                   continue;
                                                }

                                                if (currentArg.equals("-extdirs")) {
                                                   if (extdirsClasspaths != null) {
                                                      errorMessage = new StringBuffer();
                                                      errorMessage.append(currentArg);
                                                      if (index + 1 < argCount) {
                                                         errorMessage.append(' ');
                                                         errorMessage.append(newCommandLineArgs[index + 1]);
                                                      }

                                                      throw new IllegalArgumentException(this.bind("configure.duplicateExtDirs", errorMessage.toString()));
                                                   }

                                                   mode = 12;
                                                   continue;
                                                }

                                                if (currentArg.equals("-endorseddirs")) {
                                                   if (endorsedDirClasspaths != null) {
                                                      errorMessage = new StringBuffer();
                                                      errorMessage.append(currentArg);
                                                      if (index + 1 < argCount) {
                                                         errorMessage.append(' ');
                                                         errorMessage.append(newCommandLineArgs[index + 1]);
                                                      }

                                                      throw new IllegalArgumentException(this.bind("configure.duplicateEndorsedDirs", errorMessage.toString()));
                                                   }

                                                   mode = 15;
                                                   continue;
                                                }

                                                if (currentArg.equals("-progress")) {
                                                   mode = 0;
                                                   this.showProgress = true;
                                                   continue;
                                                }

                                                if (currentArg.startsWith("-proceedOnError")) {
                                                   mode = 0;
                                                   length = currentArg.length();
                                                   if (length > 15) {
                                                      if (!currentArg.equals("-proceedOnError:Fatal")) {
                                                         throw new IllegalArgumentException(this.bind("configure.invalidWarningConfiguration", currentArg));
                                                      }

                                                      this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.fatalOptionalError", "enabled");
                                                   } else {
                                                      this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.fatalOptionalError", "disabled");
                                                   }

                                                   this.proceedOnError = true;
                                                   continue;
                                                }

                                                if (currentArg.equals("-time")) {
                                                   mode = 0;
                                                   this.timing = 1;
                                                   continue;
                                                }

                                                if (currentArg.equals("-time:detail")) {
                                                   mode = 0;
                                                   this.timing = 3;
                                                   continue;
                                                }

                                                if (!currentArg.equals("-version") && !currentArg.equals("-v")) {
                                                   if (currentArg.equals("-showversion")) {
                                                      printVersionRequired = true;
                                                      mode = 0;
                                                      continue;
                                                   }

                                                   if ("-deprecation".equals(currentArg)) {
                                                      didSpecifyDeprecation = true;
                                                      this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.deprecation", "warning");
                                                      mode = 0;
                                                      continue;
                                                   }

                                                   if (!currentArg.equals("-help") && !currentArg.equals("-?")) {
                                                      if (!currentArg.equals("-help:warn") && !currentArg.equals("-?:warn")) {
                                                         if (currentArg.equals("-noExit")) {
                                                            this.systemExitWhenFinished = false;
                                                            mode = 0;
                                                            continue;
                                                         }

                                                         if (currentArg.equals("-verbose")) {
                                                            this.verbose = true;
                                                            mode = 0;
                                                            continue;
                                                         }

                                                         if (currentArg.equals("-referenceInfo")) {
                                                            this.produceRefInfo = true;
                                                            mode = 0;
                                                            continue;
                                                         }

                                                         if (currentArg.equals("-inlineJSR")) {
                                                            mode = 0;
                                                            this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.inlineJsrBytecode", "enabled");
                                                            continue;
                                                         }

                                                         if (currentArg.equals("-parameters")) {
                                                            mode = 0;
                                                            this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.methodParameters", "generate");
                                                            continue;
                                                         }

                                                         if (currentArg.equals("-genericsignature")) {
                                                            mode = 0;
                                                            this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.lambda.genericSignature", "generate");
                                                            continue;
                                                         }

                                                         if (currentArg.startsWith("-g")) {
                                                            mode = 0;
                                                            length = currentArg.length();
                                                            if (length == 2) {
                                                               this.options.put("com.bea.core.repackaged.jdt.core.compiler.debug.localVariable", "generate");
                                                               this.options.put("com.bea.core.repackaged.jdt.core.compiler.debug.lineNumber", "generate");
                                                               this.options.put("com.bea.core.repackaged.jdt.core.compiler.debug.sourceFile", "generate");
                                                               continue;
                                                            }

                                                            if (length <= 3) {
                                                               throw new IllegalArgumentException(this.bind("configure.invalidDebugOption", currentArg));
                                                            }

                                                            this.options.put("com.bea.core.repackaged.jdt.core.compiler.debug.localVariable", "do not generate");
                                                            this.options.put("com.bea.core.repackaged.jdt.core.compiler.debug.lineNumber", "do not generate");
                                                            this.options.put("com.bea.core.repackaged.jdt.core.compiler.debug.sourceFile", "do not generate");
                                                            if (length == 7 && currentArg.equals("-g:none")) {
                                                               continue;
                                                            }

                                                            StringTokenizer tokenizer = new StringTokenizer(currentArg.substring(3, currentArg.length()), ",");

                                                            while(true) {
                                                               if (!tokenizer.hasMoreTokens()) {
                                                                  continue label1156;
                                                               }

                                                               String token = tokenizer.nextToken();
                                                               if (token.equals("vars")) {
                                                                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.debug.localVariable", "generate");
                                                               } else if (token.equals("lines")) {
                                                                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.debug.lineNumber", "generate");
                                                               } else {
                                                                  if (!token.equals("source")) {
                                                                     throw new IllegalArgumentException(this.bind("configure.invalidDebugOption", currentArg));
                                                                  }

                                                                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.debug.sourceFile", "generate");
                                                               }
                                                            }
                                                         }

                                                         boolean isEnabling;
                                                         StringTokenizer tokenizer;
                                                         String token;
                                                         int tokenCounter;
                                                         if (currentArg.startsWith("-info")) {
                                                            mode = 0;
                                                            length = currentArg.length();
                                                            if (length == 10 && currentArg.equals("-info:none")) {
                                                               this.disableAll(1024);
                                                               continue;
                                                            }

                                                            if (length <= 6) {
                                                               throw new IllegalArgumentException(this.bind("configure.invalidInfoConfiguration", currentArg));
                                                            }

                                                            switch (currentArg.charAt(6)) {
                                                               case '+':
                                                                  errorTokenStart = 7;
                                                                  isEnabling = true;
                                                                  break;
                                                               case ',':
                                                               default:
                                                                  this.disableAll(1024);
                                                                  errorTokenStart = 6;
                                                                  isEnabling = true;
                                                                  break;
                                                               case '-':
                                                                  errorTokenStart = 7;
                                                                  isEnabling = false;
                                                            }

                                                            tokenizer = new StringTokenizer(currentArg.substring(errorTokenStart, currentArg.length()), ",");

                                                            for(tokenCounter = 0; tokenizer.hasMoreTokens(); this.handleInfoToken(token, isEnabling)) {
                                                               token = tokenizer.nextToken();
                                                               ++tokenCounter;
                                                               switch (token.charAt(0)) {
                                                                  case '+':
                                                                     isEnabling = true;
                                                                     token = token.substring(1);
                                                                  case ',':
                                                                  default:
                                                                     break;
                                                                  case '-':
                                                                     isEnabling = false;
                                                                     token = token.substring(1);
                                                               }
                                                            }

                                                            if (tokenCounter == 0) {
                                                               throw new IllegalArgumentException(this.bind("configure.invalidInfoOption", currentArg));
                                                            }
                                                            continue;
                                                         }

                                                         if (currentArg.startsWith("-warn")) {
                                                            mode = 0;
                                                            length = currentArg.length();
                                                            if (length == 10 && currentArg.equals("-warn:none")) {
                                                               this.disableAll(0);
                                                               continue;
                                                            }

                                                            if (length <= 6) {
                                                               throw new IllegalArgumentException(this.bind("configure.invalidWarningConfiguration", currentArg));
                                                            }

                                                            switch (currentArg.charAt(6)) {
                                                               case '+':
                                                                  errorTokenStart = 7;
                                                                  isEnabling = true;
                                                                  break;
                                                               case ',':
                                                               default:
                                                                  this.disableAll(0);
                                                                  errorTokenStart = 6;
                                                                  isEnabling = true;
                                                                  break;
                                                               case '-':
                                                                  errorTokenStart = 7;
                                                                  isEnabling = false;
                                                            }

                                                            tokenizer = new StringTokenizer(currentArg.substring(errorTokenStart, currentArg.length()), ",");
                                                            tokenCounter = 0;
                                                            if (didSpecifyDeprecation) {
                                                               this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.deprecation", "warning");
                                                            }

                                                            for(; tokenizer.hasMoreTokens(); this.handleWarningToken(token, isEnabling)) {
                                                               token = tokenizer.nextToken();
                                                               ++tokenCounter;
                                                               switch (token.charAt(0)) {
                                                                  case '+':
                                                                     isEnabling = true;
                                                                     token = token.substring(1);
                                                                  case ',':
                                                                  default:
                                                                     break;
                                                                  case '-':
                                                                     isEnabling = false;
                                                                     token = token.substring(1);
                                                               }
                                                            }

                                                            if (tokenCounter == 0) {
                                                               throw new IllegalArgumentException(this.bind("configure.invalidWarningOption", currentArg));
                                                            }
                                                            continue;
                                                         }

                                                         if (currentArg.startsWith("-err")) {
                                                            mode = 0;
                                                            length = currentArg.length();
                                                            if (length <= 5) {
                                                               throw new IllegalArgumentException(this.bind("configure.invalidErrorConfiguration", currentArg));
                                                            }

                                                            switch (currentArg.charAt(5)) {
                                                               case '+':
                                                                  errorTokenStart = 6;
                                                                  isEnabling = true;
                                                                  break;
                                                               case ',':
                                                               default:
                                                                  this.disableAll(1);
                                                                  errorTokenStart = 5;
                                                                  isEnabling = true;
                                                                  break;
                                                               case '-':
                                                                  errorTokenStart = 6;
                                                                  isEnabling = false;
                                                            }

                                                            tokenizer = new StringTokenizer(currentArg.substring(errorTokenStart, currentArg.length()), ",");

                                                            for(tokenCounter = 0; tokenizer.hasMoreTokens(); this.handleErrorToken(token, isEnabling)) {
                                                               token = tokenizer.nextToken();
                                                               ++tokenCounter;
                                                               switch (token.charAt(0)) {
                                                                  case '+':
                                                                     isEnabling = true;
                                                                     token = token.substring(1);
                                                                  case ',':
                                                                  default:
                                                                     break;
                                                                  case '-':
                                                                     isEnabling = false;
                                                                     token = token.substring(1);
                                                               }
                                                            }

                                                            if (tokenCounter == 0) {
                                                               throw new IllegalArgumentException(this.bind("configure.invalidErrorOption", currentArg));
                                                            }
                                                            continue;
                                                         }

                                                         if (currentArg.equals("-target")) {
                                                            mode = 4;
                                                            continue;
                                                         }

                                                         if (currentArg.equals("-preserveAllLocals")) {
                                                            this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.unusedLocal", "preserve");
                                                            mode = 0;
                                                            continue;
                                                         }

                                                         if (currentArg.equals("-enableJavadoc")) {
                                                            mode = 0;
                                                            this.enableJavadocOn = true;
                                                            continue;
                                                         }

                                                         if (currentArg.equals("-Xemacs")) {
                                                            mode = 0;
                                                            this.logger.setEmacs();
                                                            continue;
                                                         }

                                                         if (currentArg.startsWith("-A")) {
                                                            mode = 0;
                                                            continue;
                                                         }

                                                         if (currentArg.equals("-processorpath")) {
                                                            mode = 17;
                                                            continue;
                                                         }

                                                         if (currentArg.equals("-processor")) {
                                                            mode = 18;
                                                            continue;
                                                         }

                                                         if (currentArg.equals("--processor-module-path")) {
                                                            mode = 28;
                                                            continue;
                                                         }

                                                         if (currentArg.equals("-proc:only")) {
                                                            this.options.put("com.bea.core.repackaged.jdt.core.compiler.generateClassFiles", "disabled");
                                                            mode = 0;
                                                            continue;
                                                         }

                                                         if (currentArg.equals("-proc:none")) {
                                                            didSpecifyDisabledAnnotationProcessing = true;
                                                            this.options.put("com.bea.core.repackaged.jdt.core.compiler.processAnnotations", "disabled");
                                                            mode = 0;
                                                            continue;
                                                         }

                                                         if (currentArg.equals("-s")) {
                                                            mode = 19;
                                                            continue;
                                                         }

                                                         if (!currentArg.equals("-XprintProcessorInfo") && !currentArg.equals("-XprintRounds")) {
                                                            if (currentArg.startsWith("-X")) {
                                                               mode = 0;
                                                               continue;
                                                            }

                                                            if (currentArg.startsWith("-J")) {
                                                               mode = 0;
                                                               continue;
                                                            }

                                                            if (currentArg.equals("-O")) {
                                                               mode = 0;
                                                               continue;
                                                            }

                                                            if (currentArg.equals("-classNames")) {
                                                               mode = 20;
                                                               continue;
                                                            }

                                                            if (currentArg.equals("-properties")) {
                                                               mode = 21;
                                                               continue;
                                                            }

                                                            if (currentArg.equals("-missingNullDefault")) {
                                                               this.options.put("com.bea.core.repackaged.jdt.core.compiler.annotation.missingNonNullByDefaultAnnotation", "warning");
                                                               continue;
                                                            }

                                                            if (currentArg.equals("-annotationpath")) {
                                                               mode = 22;
                                                               continue;
                                                            }
                                                            break;
                                                         }

                                                         mode = 0;
                                                         continue;
                                                      }

                                                      printUsageRequired = true;
                                                      usageSection = "misc.usage.warn";
                                                      continue;
                                                   }

                                                   printUsageRequired = true;
                                                   mode = 0;
                                                   continue;
                                                }

                                                this.logger.logVersion(true);
                                                this.proceed = false;
                                                return;
                                             }

                                             mode = 23;
                                             continue;
                                          }

                                          mode = 1;
                                          continue;
                                       }

                                       if (didSpecifyCompliance) {
                                          throw new IllegalArgumentException(this.bind("configure.duplicateCompliance", currentArg));
                                       }

                                       didSpecifyCompliance = true;
                                       this.options.put("com.bea.core.repackaged.jdt.core.compiler.compliance", "12");
                                       mode = 0;
                                       continue;
                                    }

                                    if (didSpecifyCompliance) {
                                       throw new IllegalArgumentException(this.bind("configure.duplicateCompliance", currentArg));
                                    }

                                    didSpecifyCompliance = true;
                                    this.options.put("com.bea.core.repackaged.jdt.core.compiler.compliance", "11");
                                    mode = 0;
                                    continue;
                                 }

                                 if (didSpecifyCompliance) {
                                    throw new IllegalArgumentException(this.bind("configure.duplicateCompliance", currentArg));
                                 }

                                 didSpecifyCompliance = true;
                                 this.options.put("com.bea.core.repackaged.jdt.core.compiler.compliance", "10");
                                 mode = 0;
                                 continue;
                              }

                              if (didSpecifyCompliance) {
                                 throw new IllegalArgumentException(this.bind("configure.duplicateCompliance", currentArg));
                              }

                              didSpecifyCompliance = true;
                              this.options.put("com.bea.core.repackaged.jdt.core.compiler.compliance", "9");
                              mode = 0;
                              continue;
                           }

                           if (didSpecifyCompliance) {
                              throw new IllegalArgumentException(this.bind("configure.duplicateCompliance", currentArg));
                           }

                           didSpecifyCompliance = true;
                           this.options.put("com.bea.core.repackaged.jdt.core.compiler.compliance", "1.8");
                           mode = 0;
                           continue;
                        }

                        if (didSpecifyCompliance) {
                           throw new IllegalArgumentException(this.bind("configure.duplicateCompliance", currentArg));
                        }

                        didSpecifyCompliance = true;
                        this.options.put("com.bea.core.repackaged.jdt.core.compiler.compliance", "1.7");
                        mode = 0;
                        continue;
                     }

                     if (didSpecifyCompliance) {
                        throw new IllegalArgumentException(this.bind("configure.duplicateCompliance", currentArg));
                     }

                     didSpecifyCompliance = true;
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.compliance", "1.6");
                     mode = 0;
                     continue;
                  }

                  if (didSpecifyCompliance) {
                     throw new IllegalArgumentException(this.bind("configure.duplicateCompliance", currentArg));
                  }

                  didSpecifyCompliance = true;
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.compliance", "1.5");
                  mode = 0;
                  continue;
               case 1:
                  mode = 0;
                  index += this.processPaths(newCommandLineArgs, index, currentArg, classpaths);
                  continue;
               case 2:
               case 10:
               case 14:
               default:
                  break;
               case 3:
                  this.setDestinationPath(currentArg.equals("none") ? "none" : currentArg);
                  mode = 0;
                  continue;
               case 4:
                  if (this.didSpecifyTarget) {
                     throw new IllegalArgumentException(this.bind("configure.duplicateTarget", currentArg));
                  }

                  if (this.releaseVersion != null) {
                     throw new IllegalArgumentException(this.bind("configure.unsupportedWithRelease", "-target"));
                  }

                  this.didSpecifyTarget = true;
                  if (currentArg.equals("1.1")) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.1");
                  } else if (currentArg.equals("1.2")) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.2");
                  } else if (currentArg.equals("1.3")) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.3");
                  } else if (currentArg.equals("1.4")) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.4");
                  } else if (!currentArg.equals("1.5") && !currentArg.equals("5") && !currentArg.equals("5.0")) {
                     if (!currentArg.equals("1.6") && !currentArg.equals("6") && !currentArg.equals("6.0")) {
                        if (!currentArg.equals("1.7") && !currentArg.equals("7") && !currentArg.equals("7.0")) {
                           if (!currentArg.equals("1.8") && !currentArg.equals("8") && !currentArg.equals("8.0")) {
                              if (!currentArg.equals("1.9") && !currentArg.equals("9") && !currentArg.equals("9.0")) {
                                 if (!currentArg.equals("10") && !currentArg.equals("10.0")) {
                                    if (!currentArg.equals("11") && !currentArg.equals("11.0")) {
                                       if (!currentArg.equals("12") && !currentArg.equals("12.0")) {
                                          if (currentArg.equals("jsr14")) {
                                             this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "jsr14");
                                          } else {
                                             if (!currentArg.equals("cldc1.1")) {
                                                throw new IllegalArgumentException(this.bind("configure.targetJDK", currentArg));
                                             }

                                             this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "cldc1.1");
                                             this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.inlineJsrBytecode", "enabled");
                                          }
                                       } else {
                                          this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "12");
                                       }
                                    } else {
                                       this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "11");
                                    }
                                 } else {
                                    this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "10");
                                 }
                              } else {
                                 this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "9");
                              }
                           } else {
                              this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.8");
                           }
                        } else {
                           this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.7");
                        }
                     } else {
                        this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.6");
                     }
                  } else {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.5");
                  }

                  mode = 0;
                  continue;
               case 5:
                  this.log = currentArg;
                  mode = 0;
                  continue;
               case 6:
                  try {
                     this.maxRepetition = Integer.parseInt(currentArg);
                     if (this.maxRepetition <= 0) {
                        throw new IllegalArgumentException(this.bind("configure.repetition", currentArg));
                     }
                  } catch (NumberFormatException var40) {
                     throw new IllegalArgumentException(this.bind("configure.repetition", currentArg), var40);
                  }

                  mode = 0;
                  continue;
               case 7:
                  if (this.didSpecifySource) {
                     throw new IllegalArgumentException(this.bind("configure.duplicateSource", currentArg));
                  }

                  if (this.releaseVersion != null) {
                     throw new IllegalArgumentException(this.bind("configure.unsupportedWithRelease", "-source"));
                  }

                  this.didSpecifySource = true;
                  if (currentArg.equals("1.3")) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.source", "1.3");
                  } else if (currentArg.equals("1.4")) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.source", "1.4");
                  } else if (!currentArg.equals("1.5") && !currentArg.equals("5") && !currentArg.equals("5.0")) {
                     if (!currentArg.equals("1.6") && !currentArg.equals("6") && !currentArg.equals("6.0")) {
                        if (!currentArg.equals("1.7") && !currentArg.equals("7") && !currentArg.equals("7.0")) {
                           if (!currentArg.equals("1.8") && !currentArg.equals("8") && !currentArg.equals("8.0")) {
                              if (!currentArg.equals("1.9") && !currentArg.equals("9") && !currentArg.equals("9.0")) {
                                 if (!currentArg.equals("10") && !currentArg.equals("10.0")) {
                                    if (!currentArg.equals("11") && !currentArg.equals("11.0")) {
                                       if (!currentArg.equals("12") && !currentArg.equals("12.0")) {
                                          throw new IllegalArgumentException(this.bind("configure.source", currentArg));
                                       }

                                       this.options.put("com.bea.core.repackaged.jdt.core.compiler.source", "12");
                                    } else {
                                       this.options.put("com.bea.core.repackaged.jdt.core.compiler.source", "11");
                                    }
                                 } else {
                                    this.options.put("com.bea.core.repackaged.jdt.core.compiler.source", "10");
                                 }
                              } else {
                                 this.options.put("com.bea.core.repackaged.jdt.core.compiler.source", "9");
                              }
                           } else {
                              this.options.put("com.bea.core.repackaged.jdt.core.compiler.source", "1.8");
                           }
                        } else {
                           this.options.put("com.bea.core.repackaged.jdt.core.compiler.source", "1.7");
                        }
                     } else {
                        this.options.put("com.bea.core.repackaged.jdt.core.compiler.source", "1.6");
                     }
                  } else {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.source", "1.5");
                  }

                  mode = 0;
                  continue;
               case 8:
                  if (specifiedEncodings != null) {
                     if (!specifiedEncodings.contains(currentArg)) {
                        if (specifiedEncodings.size() > 1) {
                           this.logger.logWarning(this.bind("configure.differentencodings", currentArg, getAllEncodings(specifiedEncodings)));
                        } else {
                           this.logger.logWarning(this.bind("configure.differentencoding", currentArg, getAllEncodings(specifiedEncodings)));
                        }
                     }
                  } else {
                     specifiedEncodings = new HashSet();
                  }

                  try {
                     new InputStreamReader(new ByteArrayInputStream(new byte[0]), currentArg);
                  } catch (UnsupportedEncodingException var38) {
                     throw new IllegalArgumentException(this.bind("configure.unsupportedEncoding", currentArg), var38);
                  }

                  specifiedEncodings.add(currentArg);
                  this.options.put("com.bea.core.repackaged.jdt.core.encoding", currentArg);
                  mode = 0;
                  continue;
               case 9:
                  mode = 0;
                  index += this.processPaths(newCommandLineArgs, index, currentArg, bootclasspaths);
                  continue;
               case 11:
                  try {
                     this.maxProblems = Integer.parseInt(currentArg);
                     if (this.maxProblems <= 0) {
                        throw new IllegalArgumentException(this.bind("configure.maxProblems", currentArg));
                     }

                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.maxProblemPerUnit", currentArg);
                  } catch (NumberFormatException var39) {
                     throw new IllegalArgumentException(this.bind("configure.maxProblems", currentArg), var39);
                  }

                  mode = 0;
                  continue;
               case 12:
                  if (currentArg.indexOf("[-d") != -1) {
                     throw new IllegalArgumentException(this.bind("configure.unexpectedDestinationPathEntry", "-extdir"));
                  }

                  tokenizer = new StringTokenizer(currentArg, File.pathSeparator, false);
                  extdirsClasspaths = new ArrayList(4);

                  while(tokenizer.hasMoreTokens()) {
                     extdirsClasspaths.add(tokenizer.nextToken());
                  }

                  mode = 0;
                  continue;
               case 13:
                  mode = 0;
                  String[] sourcePaths = new String[1];
                  index += this.processPaths(newCommandLineArgs, index, currentArg, sourcePaths);
                  sourcepathClasspathArg = sourcePaths[0];
                  continue;
               case 15:
                  if (currentArg.indexOf("[-d") != -1) {
                     throw new IllegalArgumentException(this.bind("configure.unexpectedDestinationPathEntry", "-endorseddirs"));
                  }

                  tokenizer = new StringTokenizer(currentArg, File.pathSeparator, false);
                  endorsedDirClasspaths = new ArrayList(4);

                  while(tokenizer.hasMoreTokens()) {
                     endorsedDirClasspaths.add(tokenizer.nextToken());
                  }

                  mode = 0;
                  continue;
               case 16:
                  if (!currentArg.endsWith("]")) {
                     throw new IllegalArgumentException(this.bind("configure.incorrectDestinationPathEntry", "[-d " + currentArg));
                  }

                  customDestinationPath = currentArg.substring(0, currentArg.length() - 1);
                  break;
               case 17:
                  mode = 0;
                  continue;
               case 18:
                  mode = 0;
                  continue;
               case 19:
                  mode = 0;
                  continue;
               case 20:
                  tokenizer = new StringTokenizer(currentArg, ",");
                  if (this.classNames == null) {
                     this.classNames = new String[4];
                  }

                  for(; tokenizer.hasMoreTokens(); this.classNames[classCount++] = tokenizer.nextToken()) {
                     if (this.classNames.length == classCount) {
                        System.arraycopy(this.classNames, 0, this.classNames = new String[classCount * 2], 0, classCount);
                     }
                  }

                  mode = 0;
                  continue;
               case 21:
                  this.initializeWarnings(currentArg);
                  mode = 0;
                  continue;
               case 22:
                  mode = 0;
                  if (!currentArg.isEmpty() && currentArg.charAt(0) != '-') {
                     if ("CLASSPATH".equals(currentArg)) {
                        this.annotationsFromClasspath = true;
                        continue;
                     }

                     if (this.annotationPaths == null) {
                        this.annotationPaths = new ArrayList();
                     }

                     StringTokenizer tokens = new StringTokenizer(currentArg, File.pathSeparator);

                     while(true) {
                        if (!tokens.hasMoreTokens()) {
                           continue label1156;
                        }

                        this.annotationPaths.add(tokens.nextToken());
                     }
                  }

                  throw new IllegalArgumentException(this.bind("configure.missingAnnotationPath", currentArg));
               case 23:
                  mode = 0;
                  String[] modulepaths = new String[1];
                  index += this.processPaths(newCommandLineArgs, index, currentArg, modulepaths);
                  modulepathArg = modulepaths[0];
                  continue;
               case 24:
                  mode = 0;
                  String[] moduleSourcepaths = new String[1];
                  index += this.processPaths(newCommandLineArgs, index, currentArg, moduleSourcepaths);
                  moduleSourcepathArg = moduleSourcepaths[0];
                  continue;
               case 25:
                  mode = 0;
                  if (this.addonExports == Collections.EMPTY_LIST) {
                     this.addonExports = new ArrayList();
                  }

                  this.addonExports.add(currentArg);
                  continue;
               case 26:
                  mode = 0;
                  if (this.addonReads == Collections.EMPTY_LIST) {
                     this.addonReads = new ArrayList();
                  }

                  this.addonReads.add(currentArg);
                  continue;
               case 27:
                  mode = 0;
                  this.setJavaHome(currentArg);
                  continue;
               case 28:
                  mode = 0;
                  continue;
               case 29:
                  mode = 0;
                  if (this.rootModules == Collections.EMPTY_SET) {
                     this.rootModules = new HashSet();
                  }

                  tokenizer = new StringTokenizer(currentArg, ",");

                  while(true) {
                     if (!tokenizer.hasMoreTokens()) {
                        continue label1156;
                     }

                     this.rootModules.add(tokenizer.nextToken().trim());
                  }
               case 30:
                  this.releaseVersion = currentArg;
                  long releaseToJDKLevel = CompilerOptions.releaseToJDKLevel(currentArg);
                  if (releaseToJDKLevel == 0L) {
                     throw new IllegalArgumentException(this.bind("configure.unsupportedReleaseVersion", currentArg));
                  }

                  this.complianceLevel = releaseToJDKLevel;
                  folders = CompilerOptions.versionFromJdkLevel(releaseToJDKLevel);
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.compliance", folders);
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.source", folders);
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", folders);
                  mode = 0;
                  continue;
               case 31:
                  mode = 0;
                  tokenizer = new StringTokenizer(currentArg, ",");

                  while(true) {
                     if (!tokenizer.hasMoreTokens()) {
                        continue label1156;
                     }

                     if (this.limitedModules == null) {
                        this.limitedModules = new HashSet();
                     }

                     this.limitedModules.add(tokenizer.nextToken().trim());
                  }
            }

            if (customDestinationPath == null) {
               if (File.separatorChar != '/') {
                  currentArg = currentArg.replace('/', File.separatorChar);
               }

               if (currentArg.endsWith("[-d")) {
                  currentSourceDirectory = currentArg.substring(0, currentArg.length() - 3);
                  mode = 16;
                  continue;
               }

               currentSourceDirectory = currentArg;
            }

            File dir = new File(currentSourceDirectory);
            if (!dir.isDirectory()) {
               throw new IllegalArgumentException(this.bind("configure.unrecognizedOption", currentSourceDirectory));
            }

            result = FileFinder.find(dir, ".java");
            if ("none".equals(customDestinationPath)) {
               customDestinationPath = "none";
            }

            if (this.filenames != null) {
               errorTokenStart = result.length;
               System.arraycopy(this.filenames, 0, this.filenames = new String[errorTokenStart + filesCount], 0, filesCount);
               System.arraycopy(this.encodings, 0, this.encodings = new String[errorTokenStart + filesCount], 0, filesCount);
               System.arraycopy(this.destinationPaths, 0, this.destinationPaths = new String[errorTokenStart + filesCount], 0, filesCount);
               System.arraycopy(this.modNames, 0, this.modNames = new String[errorTokenStart + filesCount], 0, filesCount);
               System.arraycopy(result, 0, this.filenames, filesCount, errorTokenStart);

               for(i = 0; i < errorTokenStart; ++i) {
                  this.encodings[filesCount + i] = customEncoding;
                  this.destinationPaths[filesCount + i] = customDestinationPath;
                  this.modNames[filesCount + i] = moduleName;
               }

               filesCount += errorTokenStart;
               customEncoding = null;
               customDestinationPath = null;
               currentSourceDirectory = null;
            } else {
               this.filenames = result;
               filesCount = this.filenames.length;
               this.encodings = new String[filesCount];
               this.destinationPaths = new String[filesCount];
               this.modNames = new String[filesCount];

               for(errorTokenStart = 0; errorTokenStart < filesCount; ++errorTokenStart) {
                  this.encodings[errorTokenStart] = customEncoding;
                  this.destinationPaths[errorTokenStart] = customDestinationPath;
               }

               customEncoding = null;
               customDestinationPath = null;
               currentSourceDirectory = null;
            }

            mode = 0;
         }
      } else {
         this.printUsage();
      }
   }

   private Parser getNewParser() {
      return new Parser(new ProblemReporter(this.getHandlingPolicy(), new CompilerOptions(this.options), this.getProblemFactory()), false);
   }

   private IModule extractModuleDesc(String fileName) {
      IModule mod = null;
      Map opts = new HashMap(this.options);
      opts.put("com.bea.core.repackaged.jdt.core.compiler.source", (String)this.options.get("com.bea.core.repackaged.jdt.core.compiler.compliance"));
      Parser parser = new Parser(new ProblemReporter(this.getHandlingPolicy(), new CompilerOptions(opts), this.getProblemFactory()), false);
      if (fileName.toLowerCase().endsWith("module-info.java")) {
         ICompilationUnit cu = new CompilationUnit((char[])null, fileName, (String)null);
         CompilationResult compilationResult = new CompilationResult(cu, 0, 1, 10);
         CompilationUnitDeclaration unit = parser.parse((ICompilationUnit)cu, (CompilationResult)compilationResult);
         if (unit.isModuleInfo() && unit.moduleDeclaration != null) {
            mod = new BasicModule(unit.moduleDeclaration, (IModulePathEntry)null);
         }
      } else if (fileName.toLowerCase().endsWith("module-info.class")) {
         try {
            ClassFileReader reader = ClassFileReader.read(fileName);
            mod = reader.getModuleDeclaration();
         } catch (IOException | ClassFormatException var8) {
            var8.printStackTrace();
            throw new IllegalArgumentException(this.bind("configure.invalidModuleDescriptor", fileName));
         }
      }

      return (IModule)mod;
   }

   private static char[][] decodeIgnoreOptionalProblemsFromFolders(String folders) {
      StringTokenizer tokenizer = new StringTokenizer(folders, File.pathSeparator);
      char[][] result = new char[tokenizer.countTokens()][];
      int count = 0;

      while(tokenizer.hasMoreTokens()) {
         String fileName = tokenizer.nextToken();
         File file = new File(fileName);
         if (file.exists()) {
            try {
               result[count++] = file.getCanonicalPath().toCharArray();
            } catch (IOException var6) {
               result[count++] = fileName.toCharArray();
            }
         } else {
            result[count++] = fileName.toCharArray();
         }
      }

      return result;
   }

   private static String getAllEncodings(Set encodings) {
      int size = encodings.size();
      String[] allEncodings = new String[size];
      encodings.toArray(allEncodings);
      Arrays.sort(allEncodings);
      StringBuffer buffer = new StringBuffer();

      for(int i = 0; i < size; ++i) {
         if (i > 0) {
            buffer.append(", ");
         }

         buffer.append(allEncodings[i]);
      }

      return String.valueOf(buffer);
   }

   private void initializeWarnings(String propertiesFile) {
      File file = new File(propertiesFile);
      if (!file.exists()) {
         throw new IllegalArgumentException(this.bind("configure.missingwarningspropertiesfile", propertiesFile));
      } else {
         BufferedInputStream stream = null;
         Properties properties = null;

         try {
            stream = new BufferedInputStream(new FileInputStream(propertiesFile));
            properties = new Properties();
            properties.load(stream);
         } catch (IOException var13) {
            var13.printStackTrace();
            throw new IllegalArgumentException(this.bind("configure.ioexceptionwarningspropertiesfile", propertiesFile));
         } finally {
            if (stream != null) {
               try {
                  stream.close();
               } catch (IOException var12) {
               }
            }

         }

         Iterator iterator = properties.entrySet().iterator();

         while(iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            String key = entry.getKey().toString();
            if (key.startsWith("com.bea.core.repackaged.jdt.core.compiler.")) {
               this.options.put(key, entry.getValue().toString());
            }
         }

         if (!properties.containsKey("com.bea.core.repackaged.jdt.core.compiler.debug.localVariable")) {
            this.options.put("com.bea.core.repackaged.jdt.core.compiler.debug.localVariable", "generate");
         }

         if (!properties.containsKey("com.bea.core.repackaged.jdt.core.compiler.codegen.unusedLocal")) {
            this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.unusedLocal", "preserve");
         }

         if (!properties.containsKey("com.bea.core.repackaged.jdt.core.compiler.doc.comment.support")) {
            this.options.put("com.bea.core.repackaged.jdt.core.compiler.doc.comment.support", "enabled");
         }

         if (!properties.containsKey("com.bea.core.repackaged.jdt.core.compiler.problem.forbiddenReference")) {
            this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.forbiddenReference", "error");
         }

      }
   }

   protected void enableAll(int severity) {
      String newValue = null;
      switch (severity) {
         case 0:
            newValue = "warning";
            break;
         case 1:
            newValue = "error";
      }

      Map.Entry[] entries = (Map.Entry[])this.options.entrySet().toArray(new Map.Entry[this.options.size()]);
      int i = 0;

      for(int max = entries.length; i < max; ++i) {
         Map.Entry entry = entries[i];
         if (((String)entry.getValue()).equals("ignore")) {
            this.options.put((String)entry.getKey(), newValue);
         }
      }

      this.options.put("com.bea.core.repackaged.jdt.core.compiler.taskTags", Util.EMPTY_STRING);
   }

   protected void disableAll(int severity) {
      String checkedValue = null;
      switch (severity) {
         case 0:
            checkedValue = "warning";
            break;
         case 1:
            checkedValue = "error";
            break;
         case 1024:
            checkedValue = "info";
      }

      Set entrySet = this.options.entrySet();
      Iterator var5 = entrySet.iterator();

      while(var5.hasNext()) {
         Map.Entry entry = (Map.Entry)var5.next();
         if (((String)entry.getValue()).equals(checkedValue)) {
            this.options.put((String)entry.getKey(), "ignore");
         }
      }

      if (severity == 0) {
         this.disableAll(1024);
      }

   }

   public String extractDestinationPathFromSourceFile(CompilationResult result) {
      ICompilationUnit compilationUnit = result.compilationUnit;
      if (compilationUnit != null) {
         char[] fileName = compilationUnit.getFileName();
         int lastIndex = CharOperation.lastIndexOf(File.separatorChar, fileName);
         if (lastIndex != -1) {
            String outputPathName = new String(fileName, 0, lastIndex);
            File output = new File(outputPathName);
            if (output.exists() && output.isDirectory()) {
               return outputPathName;
            }
         }
      }

      return System.getProperty("user.dir");
   }

   public ICompilerRequestor getBatchRequestor() {
      return new BatchCompilerRequestor(this);
   }

   public CompilationUnit[] getCompilationUnits() {
      int fileCount = this.filenames.length;
      CompilationUnit[] units = new CompilationUnit[fileCount];
      HashtableOfObject knownFileNames = new HashtableOfObject(fileCount);
      String defaultEncoding = (String)this.options.get("com.bea.core.repackaged.jdt.core.encoding");
      if (Util.EMPTY_STRING.equals(defaultEncoding)) {
         defaultEncoding = null;
      }

      for(int round = 0; round < 2; ++round) {
         for(int i = 0; i < fileCount; ++i) {
            char[] charName = this.filenames[i].toCharArray();
            boolean isModuleInfo = CharOperation.endsWith(charName, TypeConstants.MODULE_INFO_FILE_NAME);
            if (isModuleInfo == (round == 0)) {
               if (knownFileNames.get(charName) != null) {
                  throw new IllegalArgumentException(this.bind("unit.more", this.filenames[i]));
               }

               knownFileNames.put(charName, charName);
               File file = new File(this.filenames[i]);
               if (!file.exists()) {
                  throw new IllegalArgumentException(this.bind("unit.missing", this.filenames[i]));
               }

               String encoding = this.encodings[i];
               if (encoding == null) {
                  encoding = defaultEncoding;
               }

               String fileName;
               try {
                  fileName = file.getCanonicalPath();
               } catch (IOException var12) {
                  fileName = this.filenames[i];
               }

               units[i] = new CompilationUnit((char[])null, fileName, encoding, this.destinationPaths[i], shouldIgnoreOptionalProblems(this.ignoreOptionalProblemsFromFolders, fileName.toCharArray()), this.modNames[i]);
            }
         }
      }

      return units;
   }

   public IErrorHandlingPolicy getHandlingPolicy() {
      return new IErrorHandlingPolicy() {
         public boolean proceedOnErrors() {
            return Main.this.proceedOnError;
         }

         public boolean stopOnFirstError() {
            return false;
         }

         public boolean ignoreAllErrors() {
            return false;
         }
      };
   }

   private void setJavaHome(String javaHome) {
      File release = new File(javaHome, "release");
      Properties prop = new Properties();

      try {
         prop.load(new FileReader(release));
         String ver = prop.getProperty("JAVA_VERSION");
         if (ver != null) {
            ver = ver.replace("\"", "");
         }

         this.javaHomeCache = new File(javaHome);
         this.javaHomeChecked = true;
      } catch (IOException var5) {
         throw new IllegalArgumentException(this.bind("configure.invalidSystem", javaHome));
      }
   }

   public File getJavaHome() {
      if (!this.javaHomeChecked) {
         this.javaHomeChecked = true;
         this.javaHomeCache = Util.getJavaHome();
      }

      return this.javaHomeCache;
   }

   public FileSystem getLibraryAccess() {
      FileSystem nameEnvironment = new FileSystem(this.checkedClasspaths, this.filenames, this.annotationsFromClasspath && "enabled".equals(this.options.get("com.bea.core.repackaged.jdt.core.compiler.annotation.nullanalysis")), this.limitedModules);
      nameEnvironment.module = this.module;
      this.processAddonModuleOptions(nameEnvironment);
      return nameEnvironment;
   }

   public IProblemFactory getProblemFactory() {
      return new DefaultProblemFactory(this.compilerLocale);
   }

   protected ArrayList handleBootclasspath(ArrayList bootclasspaths, String customEncoding) {
      ArrayList result = new ArrayList(4);
      int bootclasspathsSize;
      if (bootclasspaths != null && (bootclasspathsSize = bootclasspaths.size()) != 0) {
         result = new ArrayList(bootclasspathsSize);
         Iterator var6 = bootclasspaths.iterator();

         while(var6.hasNext()) {
            String path = (String)var6.next();
            this.processPathEntries(4, result, path, customEncoding, false, true);
         }
      } else {
         try {
            Util.collectVMBootclasspath(result, this.javaHomeCache);
         } catch (IllegalStateException var7) {
            throw new IllegalArgumentException(this.bind("configure.invalidSystem", this.javaHomeCache.toString()));
         }
      }

      return result;
   }

   private void processAddonModuleOptions(FileSystem env) {
      Map exports = new HashMap();

      String option;
      Iterator var4;
      String modName;
      IModule.IPackageExport export;
      for(var4 = this.addonExports.iterator(); var4.hasNext(); env.addModuleUpdate(modName, (m) -> {
         m.addExports(export.name(), export.targets());
      }, IUpdatableModule.UpdateKind.PACKAGE)) {
         option = (String)var4.next();
         ModuleFinder.AddExport addExport = ModuleFinder.extractAddonExport(option);
         if (addExport == null) {
            throw new IllegalArgumentException(this.bind("configure.invalidModuleOption", "--add-exports " + option));
         }

         modName = addExport.sourceModuleName;
         export = addExport.export;
         IModule.IPackageExport[] existing = (IModule.IPackageExport[])exports.get(modName);
         if (existing == null) {
            existing = new IModule.IPackageExport[]{export};
            exports.put(modName, existing);
         } else {
            IModule.IPackageExport[] var12 = existing;
            int var11 = existing.length;

            for(int var10 = 0; var10 < var11; ++var10) {
               IModule.IPackageExport iPackageExport = var12[var10];
               if (CharOperation.equals(iPackageExport.name(), export.name())) {
                  throw new IllegalArgumentException(this.bind("configure.duplicateExport"));
               }
            }

            IModule.IPackageExport[] updated = new IModule.IPackageExport[existing.length + 1];
            System.arraycopy(existing, 0, updated, 0, existing.length);
            updated[existing.length] = export;
            exports.put(modName, updated);
         }
      }

      var4 = this.addonReads.iterator();

      while(var4.hasNext()) {
         option = (String)var4.next();
         String[] result = ModuleFinder.extractAddonRead(option);
         if (result == null || result.length != 2) {
            throw new IllegalArgumentException(this.bind("configure.invalidModuleOption", "--add-reads " + option));
         }

         env.addModuleUpdate(result[0], (m) -> {
            m.addReads(result[1].toCharArray());
         }, IUpdatableModule.UpdateKind.MODULE);
      }

   }

   protected ArrayList handleModulepath(String arg) {
      ArrayList modulePaths = this.processModulePathEntries(arg);
      ArrayList result = new ArrayList();
      if (modulePaths != null && modulePaths.size() > 0) {
         Iterator var5 = modulePaths.iterator();

         while(var5.hasNext()) {
            String path = (String)var5.next();
            File file = new File(path);
            if (file.isDirectory()) {
               result.addAll(ModuleFinder.findModules(file, (String)null, this.getNewParser(), this.options, true, this.releaseVersion));
            } else {
               FileSystem.Classpath modulePath = ModuleFinder.findModule(file, (String)null, this.getNewParser(), this.options, true, this.releaseVersion);
               if (modulePath != null) {
                  result.add(modulePath);
               }
            }
         }
      }

      return result;
   }

   protected ArrayList handleModuleSourcepath(String arg) {
      ArrayList modulePaths = this.processModulePathEntries(arg);
      ArrayList result = new ArrayList();
      if (modulePaths != null && modulePaths.size() != 0) {
         if (this.destinationPath == null) {
            this.addPendingErrors(this.bind("configure.missingDestinationPath"));
         }

         String[] paths = new String[modulePaths.size()];
         modulePaths.toArray(paths);

         int j;
         for(j = 0; j < paths.length; ++j) {
            File dir = new File(paths[j]);
            if (dir.isDirectory()) {
               List modules = ModuleFinder.findModules(dir, this.destinationPath, this.getNewParser(), this.options, false, this.releaseVersion);
               Iterator var9 = modules.iterator();

               while(var9.hasNext()) {
                  FileSystem.Classpath classpath = (FileSystem.Classpath)var9.next();
                  result.add(classpath);
                  Path modLocation = Paths.get(classpath.getPath()).toAbsolutePath();
                  String destPath = classpath.getDestinationPath();
                  IModule mod = classpath.getModule();
                  String moduleName = mod == null ? null : new String(mod.name());

                  for(int j = 0; j < this.filenames.length; ++j) {
                     try {
                        Path filePath = (new File(this.filenames[j])).getCanonicalFile().toPath();
                        if (filePath.startsWith(modLocation)) {
                           this.modNames[j] = moduleName;
                           this.destinationPaths[j] = destPath;
                        }
                     } catch (IOException var16) {
                        this.modNames[j] = "";
                     }
                  }
               }
            }
         }

         for(j = 0; j < this.filenames.length; ++j) {
            if (this.modNames[j] == null) {
               throw new IllegalArgumentException(this.bind("configure.notOnModuleSourcePath", new String[]{this.filenames[j]}));
            }
         }
      }

      return result;
   }

   protected ArrayList handleClasspath(ArrayList classpaths, String customEncoding) {
      ArrayList initial = new ArrayList(4);
      String classProp;
      FileSystem.Classpath currentClasspath;
      if (classpaths != null && classpaths.size() > 0) {
         Iterator var12 = classpaths.iterator();

         while(var12.hasNext()) {
            classProp = (String)var12.next();
            this.processPathEntries(4, initial, classProp, customEncoding, false, true);
         }
      } else {
         classProp = System.getProperty("java.class.path");
         if (classProp != null && classProp.length() != 0) {
            StringTokenizer tokenizer = new StringTokenizer(classProp, File.pathSeparator);

            while(tokenizer.hasMoreTokens()) {
               String token = tokenizer.nextToken();
               currentClasspath = FileSystem.getClasspath(token, customEncoding, (AccessRuleSet)null, this.options, this.releaseVersion);
               if (currentClasspath != null) {
                  initial.add(currentClasspath);
               } else if (token.length() != 0) {
                  this.addPendingErrors(this.bind("configure.incorrectClasspath", token));
               }
            }
         } else {
            this.addPendingErrors(this.bind("configure.noClasspath"));
            FileSystem.Classpath classpath = FileSystem.getClasspath(System.getProperty("user.dir"), customEncoding, (AccessRuleSet)null, this.options, this.releaseVersion);
            if (classpath != null) {
               initial.add(classpath);
            }
         }
      }

      ArrayList result = new ArrayList();
      HashMap knownNames = new HashMap();
      FileSystem.ClasspathSectionProblemReporter problemReporter = new FileSystem.ClasspathSectionProblemReporter() {
         public void invalidClasspathSection(String jarFilePath) {
            Main.this.addPendingErrors(Main.this.bind("configure.invalidClasspathSection", jarFilePath));
         }

         public void multipleClasspathSections(String jarFilePath) {
            Main.this.addPendingErrors(Main.this.bind("configure.multipleClasspathSections", jarFilePath));
         }
      };

      while(!initial.isEmpty()) {
         currentClasspath = (FileSystem.Classpath)initial.remove(0);
         String currentPath = currentClasspath.getPath();
         if (knownNames.get(currentPath) == null) {
            knownNames.put(currentPath, currentClasspath);
            result.add(currentClasspath);
            List linkedJars = currentClasspath.fetchLinkedJars(problemReporter);
            if (linkedJars != null) {
               initial.addAll(0, linkedJars);
            }
         }
      }

      return result;
   }

   protected ArrayList handleEndorseddirs(ArrayList endorsedDirClasspaths) {
      File javaHome = this.getJavaHome();
      if (endorsedDirClasspaths == null) {
         endorsedDirClasspaths = new ArrayList(4);
         String endorsedDirsStr = System.getProperty("java.endorsed.dirs");
         if (endorsedDirsStr == null) {
            if (javaHome != null) {
               endorsedDirClasspaths.add(javaHome.getAbsolutePath() + "/lib/endorsed");
            }
         } else {
            StringTokenizer tokenizer = new StringTokenizer(endorsedDirsStr, File.pathSeparator);

            while(tokenizer.hasMoreTokens()) {
               endorsedDirClasspaths.add(tokenizer.nextToken());
            }
         }
      }

      if (endorsedDirClasspaths.size() == 0) {
         return FileSystem.EMPTY_CLASSPATH;
      } else {
         ArrayList result = new ArrayList();
         File[] directoriesToCheck = new File[endorsedDirClasspaths.size()];

         for(int i = 0; i < directoriesToCheck.length; ++i) {
            directoriesToCheck[i] = new File((String)endorsedDirClasspaths.get(i));
         }

         File[][] endorsedDirsJars = getLibrariesFiles(directoriesToCheck);
         if (endorsedDirsJars != null) {
            int i = 0;

            for(int max = endorsedDirsJars.length; i < max; ++i) {
               File[] current = endorsedDirsJars[i];
               if (current != null) {
                  int j = 0;

                  for(int max2 = current.length; j < max2; ++j) {
                     FileSystem.Classpath classpath = FileSystem.getClasspath(current[j].getAbsolutePath(), (String)null, (AccessRuleSet)null, this.options, this.releaseVersion);
                     if (classpath != null) {
                        result.add(classpath);
                     }
                  }
               } else if (directoriesToCheck[i].isFile()) {
                  this.addPendingErrors(this.bind("configure.incorrectEndorsedDirsEntry", directoriesToCheck[i].getAbsolutePath()));
               }
            }
         }

         return result;
      }
   }

   protected ArrayList handleExtdirs(ArrayList extdirsClasspaths) {
      File javaHome = this.getJavaHome();
      if (extdirsClasspaths == null) {
         extdirsClasspaths = new ArrayList(4);
         String extdirsStr = System.getProperty("java.ext.dirs");
         if (extdirsStr == null) {
            extdirsClasspaths.add(javaHome.getAbsolutePath() + "/lib/ext");
         } else {
            StringTokenizer tokenizer = new StringTokenizer(extdirsStr, File.pathSeparator);

            while(tokenizer.hasMoreTokens()) {
               extdirsClasspaths.add(tokenizer.nextToken());
            }
         }
      }

      if (extdirsClasspaths.size() == 0) {
         return FileSystem.EMPTY_CLASSPATH;
      } else {
         ArrayList result = new ArrayList();
         File[] directoriesToCheck = new File[extdirsClasspaths.size()];

         for(int i = 0; i < directoriesToCheck.length; ++i) {
            directoriesToCheck[i] = new File((String)extdirsClasspaths.get(i));
         }

         File[][] extdirsJars = getLibrariesFiles(directoriesToCheck);
         if (extdirsJars != null) {
            int i = 0;

            for(int max = extdirsJars.length; i < max; ++i) {
               File[] current = extdirsJars[i];
               if (current != null) {
                  int j = 0;

                  for(int max2 = current.length; j < max2; ++j) {
                     FileSystem.Classpath classpath = FileSystem.getClasspath(current[j].getAbsolutePath(), (String)null, (AccessRuleSet)null, this.options, this.releaseVersion);
                     if (classpath != null) {
                        result.add(classpath);
                     }
                  }
               } else if (directoriesToCheck[i].isFile()) {
                  this.addPendingErrors(this.bind("configure.incorrectExtDirsEntry", directoriesToCheck[i].getAbsolutePath()));
               }
            }
         }

         return result;
      }
   }

   protected void handleInfoToken(String token, boolean isEnabling) {
      this.handleErrorOrWarningToken(token, isEnabling, 1024);
   }

   protected void handleWarningToken(String token, boolean isEnabling) {
      this.handleErrorOrWarningToken(token, isEnabling, 0);
   }

   protected void handleErrorToken(String token, boolean isEnabling) {
      this.handleErrorOrWarningToken(token, isEnabling, 1);
   }

   private void setSeverity(String compilerOptions, int severity, boolean isEnabling) {
      if (isEnabling) {
         switch (severity) {
            case 0:
               this.options.put(compilerOptions, "warning");
               break;
            case 1:
               this.options.put(compilerOptions, "error");
               break;
            case 1024:
               this.options.put(compilerOptions, "info");
               break;
            default:
               this.options.put(compilerOptions, "ignore");
         }
      } else {
         String currentValue;
         switch (severity) {
            case 0:
               currentValue = (String)this.options.get(compilerOptions);
               if ("warning".equals(currentValue)) {
                  this.options.put(compilerOptions, "ignore");
               }
               break;
            case 1:
               currentValue = (String)this.options.get(compilerOptions);
               if ("error".equals(currentValue)) {
                  this.options.put(compilerOptions, "ignore");
               }
               break;
            case 1024:
               currentValue = (String)this.options.get(compilerOptions);
               if ("info".equals(currentValue)) {
                  this.options.put(compilerOptions, "ignore");
               }
               break;
            default:
               this.options.put(compilerOptions, "ignore");
         }
      }

   }

   private void handleErrorOrWarningToken(String token, boolean isEnabling, int severity) {
      if (token.length() != 0) {
         String annotationNames;
         int end;
         int end;
         int start;
         String visibility;
         switch (token.charAt(0)) {
            case 'a':
               if (token.equals("allDeprecation")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.deprecation", severity, isEnabling);
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.terminalDeprecation", severity, isEnabling);
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.deprecationInDeprecatedCode", isEnabling ? "enabled" : "disabled");
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.deprecationWhenOverridingDeprecatedMethod", isEnabling ? "enabled" : "disabled");
                  return;
               }

               if (token.equals("allJavadoc")) {
                  this.warnAllJavadocOn = this.warnJavadocOn = isEnabling;
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadoc", severity, isEnabling);
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTags", severity, isEnabling);
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocComments", severity, isEnabling);
                  return;
               }

               if (token.equals("assertIdentifier")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.assertIdentifier", severity, isEnabling);
                  return;
               }

               if (token.equals("allDeadCode")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.deadCode", severity, isEnabling);
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.deadCodeInTrivialIfStatement", isEnabling ? "enabled" : "disabled");
                  return;
               }

               if (token.equals("allOver-ann")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.missingOverrideAnnotation", severity, isEnabling);
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingOverrideAnnotationForInterfaceMethodImplementation", isEnabling ? "enabled" : "disabled");
                  return;
               }

               if (token.equals("all-static-method")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.reportMethodCanBeStatic", severity, isEnabling);
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.reportMethodCanBePotentiallyStatic", severity, isEnabling);
                  return;
               }

               if (token.equals("all")) {
                  if (isEnabling) {
                     this.enableAll(severity);
                  } else {
                     this.disableAll(severity);
                  }

                  return;
               }
               break;
            case 'b':
               if (token.equals("boxing")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.autoboxing", severity, isEnabling);
                  return;
               }
               break;
            case 'c':
               if (token.equals("constructorName")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.methodWithConstructorName", severity, isEnabling);
                  return;
               }

               if (token.equals("conditionAssign")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.possibleAccidentalBooleanAssignment", severity, isEnabling);
                  return;
               }

               if (token.equals("compareIdentical")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.comparingIdentical", severity, isEnabling);
                  return;
               }

               if (token.equals("charConcat")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.noImplicitStringConversion", severity, isEnabling);
                  return;
               }
               break;
            case 'd':
               if (token.equals("deprecation")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.deprecation", severity, isEnabling);
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.deprecationInDeprecatedCode", "disabled");
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.deprecationWhenOverridingDeprecatedMethod", "disabled");
                  return;
               }

               if (token.equals("dep-ann")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.missingDeprecatedAnnotation", severity, isEnabling);
                  return;
               }

               if (token.equals("discouraged")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.discouragedReference", severity, isEnabling);
                  return;
               }

               if (token.equals("deadCode")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.deadCode", severity, isEnabling);
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.deadCodeInTrivialIfStatement", "disabled");
                  return;
               }
               break;
            case 'e':
               if (token.equals("enumSwitch")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.incompleteEnumSwitch", severity, isEnabling);
                  return;
               }

               if (token.equals("enumSwitchPedantic")) {
                  if (isEnabling) {
                     switch (severity) {
                        case 0:
                           if ("ignore".equals(this.options.get("com.bea.core.repackaged.jdt.core.compiler.problem.incompleteEnumSwitch"))) {
                              this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.incompleteEnumSwitch", severity, isEnabling);
                           }
                           break;
                        case 1:
                           this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.incompleteEnumSwitch", severity, isEnabling);
                     }
                  }

                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingEnumCaseDespiteDefault", isEnabling ? "enabled" : "disabled");
                  return;
               }

               if (token.equals("emptyBlock")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.undocumentedEmptyBlock", severity, isEnabling);
                  return;
               }

               if (token.equals("enumIdentifier")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.enumIdentifier", severity, isEnabling);
                  return;
               }

               if (token.equals("exports")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.APILeak", severity, isEnabling);
                  return;
               }
               break;
            case 'f':
               if (token.equals("fieldHiding")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.fieldHiding", severity, isEnabling);
                  return;
               }

               if (token.equals("finalBound")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.finalParameterBound", severity, isEnabling);
                  return;
               }

               if (token.equals("finally")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.finallyBlockNotCompletingNormally", severity, isEnabling);
                  return;
               }

               if (token.equals("forbidden")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.forbiddenReference", severity, isEnabling);
                  return;
               }

               if (token.equals("fallthrough")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.fallthroughCase", severity, isEnabling);
                  return;
               }
            case 'g':
            case 'k':
            case 'q':
            default:
               break;
            case 'h':
               if (token.equals("hiding")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.hiddenCatchBlock", severity, isEnabling);
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.localVariableHiding", severity, isEnabling);
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.fieldHiding", severity, isEnabling);
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.typeParameterHiding", severity, isEnabling);
                  return;
               }

               if (token.equals("hashCode")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.missingHashCodeMethod", severity, isEnabling);
                  return;
               }
               break;
            case 'i':
               if (token.equals("indirectStatic")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.indirectStaticAccess", severity, isEnabling);
                  return;
               }

               if (token.equals("inheritNullAnnot")) {
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.annotation.inheritNullAnnotations", isEnabling ? "enabled" : "disabled");
                  return;
               }

               if (!token.equals("intfNonInherited") && !token.equals("interfaceNonInherited")) {
                  if (token.equals("intfAnnotation")) {
                     this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.annotationSuperInterface", severity, isEnabling);
                     return;
                  }

                  if (token.equals("intfRedundant")) {
                     this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.redundantSuperinterface", severity, isEnabling);
                     return;
                  }

                  if (token.equals("includeAssertNull")) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.includeNullInfoFromAsserts", isEnabling ? "enabled" : "disabled");
                     return;
                  }

                  if (token.equals("invalidJavadoc")) {
                     this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadoc", severity, isEnabling);
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTags", isEnabling ? "enabled" : "disabled");
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTagsDeprecatedRef", isEnabling ? "enabled" : "disabled");
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTagsNotVisibleRef", isEnabling ? "enabled" : "disabled");
                     if (isEnabling) {
                        this.options.put("com.bea.core.repackaged.jdt.core.compiler.doc.comment.support", "enabled");
                        this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTagsVisibility", "private");
                     }

                     return;
                  }

                  if (token.equals("invalidJavadocTag")) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTags", isEnabling ? "enabled" : "disabled");
                     return;
                  }

                  if (token.equals("invalidJavadocTagDep")) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTagsDeprecatedRef", isEnabling ? "enabled" : "disabled");
                     return;
                  }

                  if (token.equals("invalidJavadocTagNotVisible")) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTagsNotVisibleRef", isEnabling ? "enabled" : "disabled");
                     return;
                  }

                  if (token.startsWith("invalidJavadocTagVisibility")) {
                     start = token.indexOf(40);
                     end = token.indexOf(41);
                     visibility = null;
                     if (isEnabling && start >= 0 && end >= 0 && start < end) {
                        visibility = token.substring(start + 1, end).trim();
                     }

                     if ((visibility == null || !visibility.equals("public")) && !visibility.equals("private") && !visibility.equals("protected") && !visibility.equals("default")) {
                        throw new IllegalArgumentException(this.bind("configure.invalidJavadocTagVisibility", token));
                     }

                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTagsVisibility", visibility);
                     return;
                  }
                  break;
               }

               this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.incompatibleNonInheritedInterfaceMethod", severity, isEnabling);
               return;
            case 'j':
               if (token.equals("javadoc")) {
                  this.warnJavadocOn = isEnabling;
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadoc", severity, isEnabling);
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTags", severity, isEnabling);
                  return;
               }
               break;
            case 'l':
               if (token.equals("localHiding")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.localVariableHiding", severity, isEnabling);
                  return;
               }
               break;
            case 'm':
               if (!token.equals("maskedCatchBlock") && !token.equals("maskedCatchBlocks")) {
                  if (token.equals("missingJavadocTags")) {
                     this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTags", severity, isEnabling);
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagsOverriding", isEnabling ? "enabled" : "disabled");
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagsMethodTypeParameters", isEnabling ? "enabled" : "disabled");
                     if (isEnabling) {
                        this.options.put("com.bea.core.repackaged.jdt.core.compiler.doc.comment.support", "enabled");
                        this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagsVisibility", "private");
                     }

                     return;
                  }

                  if (token.equals("missingJavadocTagsOverriding")) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagsOverriding", isEnabling ? "enabled" : "disabled");
                     return;
                  }

                  if (token.equals("missingJavadocTagsMethod")) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagsMethodTypeParameters", isEnabling ? "enabled" : "disabled");
                     return;
                  }

                  if (token.startsWith("missingJavadocTagsVisibility")) {
                     start = token.indexOf(40);
                     end = token.indexOf(41);
                     visibility = null;
                     if (isEnabling && start >= 0 && end >= 0 && start < end) {
                        visibility = token.substring(start + 1, end).trim();
                     }

                     if ((visibility == null || !visibility.equals("public")) && !visibility.equals("private") && !visibility.equals("protected") && !visibility.equals("default")) {
                        throw new IllegalArgumentException(this.bind("configure.missingJavadocTagsVisibility", token));
                     }

                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagsVisibility", visibility);
                     return;
                  }

                  if (token.equals("missingJavadocComments")) {
                     this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocComments", severity, isEnabling);
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocCommentsOverriding", isEnabling ? "enabled" : "disabled");
                     if (isEnabling) {
                        this.options.put("com.bea.core.repackaged.jdt.core.compiler.doc.comment.support", "enabled");
                        this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocCommentsVisibility", "private");
                     }

                     return;
                  }

                  if (token.equals("missingJavadocCommentsOverriding")) {
                     this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocComments", severity, isEnabling);
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocCommentsOverriding", isEnabling ? "enabled" : "disabled");
                     return;
                  }

                  if (token.startsWith("missingJavadocCommentsVisibility")) {
                     start = token.indexOf(40);
                     end = token.indexOf(41);
                     visibility = null;
                     if (isEnabling && start >= 0 && end >= 0 && start < end) {
                        visibility = token.substring(start + 1, end).trim();
                     }

                     if ((visibility == null || !visibility.equals("public")) && !visibility.equals("private") && !visibility.equals("protected") && !visibility.equals("default")) {
                        throw new IllegalArgumentException(this.bind("configure.missingJavadocCommentsVisibility", token));
                     }

                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocCommentsVisibility", visibility);
                     return;
                  }

                  if (token.equals("module")) {
                     this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unstableAutoModuleName", severity, isEnabling);
                     return;
                  }
                  break;
               }

               this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.hiddenCatchBlock", severity, isEnabling);
               return;
            case 'n':
               if (token.equals("nls")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.nonExternalizedStringLiteral", severity, isEnabling);
                  return;
               }

               if (token.equals("noEffectAssign")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.noEffectAssignment", severity, isEnabling);
                  return;
               }

               if (token.equals("noImplicitStringConversion")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.noImplicitStringConversion", severity, isEnabling);
                  return;
               }

               if (token.equals("null")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.nullReference", severity, isEnabling);
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.potentialNullReference", severity, isEnabling);
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.redundantNullCheck", severity, isEnabling);
                  return;
               }

               if (token.equals("nullDereference")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.nullReference", severity, isEnabling);
                  if (!isEnabling) {
                     this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.potentialNullReference", 256, isEnabling);
                     this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.redundantNullCheck", 256, isEnabling);
                  }

                  return;
               }

               if (token.equals("nullAnnotConflict")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.nullAnnotationInferenceConflict", severity, isEnabling);
                  return;
               }

               if (token.equals("nullAnnotRedundant")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.redundantNullAnnotation", severity, isEnabling);
                  return;
               }

               if (token.startsWith("nullAnnot")) {
                  annotationNames = Util.EMPTY_STRING;
                  end = token.indexOf(40);
                  end = token.indexOf(41);
                  String nonNullAnnotName = null;
                  String nullableAnnotName = null;
                  String nonNullByDefaultAnnotName = null;
                  if (isEnabling && end >= 0 && end >= 0 && end < end) {
                     boolean isPrimarySet = !this.primaryNullAnnotationsSeen;
                     annotationNames = token.substring(end + 1, end).trim();
                     int separator1 = annotationNames.indexOf(124);
                     if (separator1 == -1) {
                        throw new IllegalArgumentException(this.bind("configure.invalidNullAnnot", token));
                     }

                     nullableAnnotName = annotationNames.substring(0, separator1).trim();
                     if (isPrimarySet && nullableAnnotName.length() == 0) {
                        throw new IllegalArgumentException(this.bind("configure.invalidNullAnnot", token));
                     }

                     int separator2 = annotationNames.indexOf(124, separator1 + 1);
                     if (separator2 == -1) {
                        throw new IllegalArgumentException(this.bind("configure.invalidNullAnnot", token));
                     }

                     nonNullAnnotName = annotationNames.substring(separator1 + 1, separator2).trim();
                     if (isPrimarySet && nonNullAnnotName.length() == 0) {
                        throw new IllegalArgumentException(this.bind("configure.invalidNullAnnot", token));
                     }

                     nonNullByDefaultAnnotName = annotationNames.substring(separator2 + 1).trim();
                     if (isPrimarySet && nonNullByDefaultAnnotName.length() == 0) {
                        throw new IllegalArgumentException(this.bind("configure.invalidNullAnnot", token));
                     }

                     if (isPrimarySet) {
                        this.primaryNullAnnotationsSeen = true;
                        this.options.put("com.bea.core.repackaged.jdt.core.compiler.annotation.nullable", nullableAnnotName);
                        this.options.put("com.bea.core.repackaged.jdt.core.compiler.annotation.nonnull", nonNullAnnotName);
                        this.options.put("com.bea.core.repackaged.jdt.core.compiler.annotation.nonnullbydefault", nonNullByDefaultAnnotName);
                     } else {
                        String nnbdList;
                        if (nullableAnnotName.length() > 0) {
                           nnbdList = (String)this.options.get("com.bea.core.repackaged.jdt.core.compiler.annotation.nullable.secondary");
                           nnbdList = nnbdList.isEmpty() ? nullableAnnotName : nnbdList + ',' + nullableAnnotName;
                           this.options.put("com.bea.core.repackaged.jdt.core.compiler.annotation.nullable.secondary", nnbdList);
                        }

                        if (nonNullAnnotName.length() > 0) {
                           nnbdList = (String)this.options.get("com.bea.core.repackaged.jdt.core.compiler.annotation.nonnull.secondary");
                           nnbdList = nnbdList.isEmpty() ? nonNullAnnotName : nnbdList + ',' + nonNullAnnotName;
                           this.options.put("com.bea.core.repackaged.jdt.core.compiler.annotation.nonnull.secondary", nnbdList);
                        }

                        if (nonNullByDefaultAnnotName.length() > 0) {
                           nnbdList = (String)this.options.get("com.bea.core.repackaged.jdt.core.compiler.annotation.nonnullbydefault.secondary");
                           nnbdList = nnbdList.isEmpty() ? nonNullByDefaultAnnotName : nnbdList + ',' + nonNullByDefaultAnnotName;
                           this.options.put("com.bea.core.repackaged.jdt.core.compiler.annotation.nonnullbydefault.secondary", nnbdList);
                        }
                     }
                  }

                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.annotation.nullanalysis", isEnabling ? "enabled" : "disabled");
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.nullSpecViolation", severity, isEnabling);
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.nullAnnotationInferenceConflict", severity, isEnabling);
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.nullUncheckedConversion", severity, isEnabling);
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.redundantNullAnnotation", severity, isEnabling);
                  return;
               }

               if (token.equals("nullUncheckedConversion")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.nullUncheckedConversion", severity, isEnabling);
                  return;
               }

               if (token.equals("nonnullNotRepeated")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.nonnullParameterAnnotationDropped", severity, isEnabling);
                  return;
               }
               break;
            case 'o':
               if (token.equals("over-sync")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.missingSynchronizedOnInheritedMethod", severity, isEnabling);
                  return;
               }

               if (token.equals("over-ann")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.missingOverrideAnnotation", severity, isEnabling);
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingOverrideAnnotationForInterfaceMethodImplementation", "disabled");
                  return;
               }
               break;
            case 'p':
               if (!token.equals("pkgDefaultMethod") && !token.equals("packageDefaultMethod")) {
                  if (token.equals("paramAssign")) {
                     this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.parameterAssignment", severity, isEnabling);
                     return;
                  }
                  break;
               }

               this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.overridingPackageDefaultMethod", severity, isEnabling);
               return;
            case 'r':
               if (token.equals("raw")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.rawTypeReference", severity, isEnabling);
                  return;
               }

               if (token.equals("redundantSuperinterface")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.redundantSuperinterface", severity, isEnabling);
                  return;
               }

               if (token.equals("resource")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unclosedCloseable", severity, isEnabling);
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.potentiallyUnclosedCloseable", severity, isEnabling);
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.explicitlyClosedAutoCloseable", severity, isEnabling);
                  return;
               }

               if (token.equals("removal")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.terminalDeprecation", severity, isEnabling);
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.deprecationInDeprecatedCode", "disabled");
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.deprecationWhenOverridingDeprecatedMethod", "disabled");
                  return;
               }
               break;
            case 's':
               if (token.equals("specialParamHiding")) {
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.specialParameterHidingField", isEnabling ? "enabled" : "disabled");
                  return;
               }

               if (!token.equals("syntheticAccess") && !token.equals("synthetic-access")) {
                  if (token.equals("staticReceiver")) {
                     this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.staticAccessReceiver", severity, isEnabling);
                     return;
                  }

                  if (token.equals("syncOverride")) {
                     this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.missingSynchronizedOnInheritedMethod", severity, isEnabling);
                     return;
                  }

                  if (token.equals("semicolon")) {
                     this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.emptyStatement", severity, isEnabling);
                     return;
                  }

                  if (token.equals("serial")) {
                     this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.missingSerialVersion", severity, isEnabling);
                     return;
                  }

                  if (token.equals("suppress")) {
                     switch (severity) {
                        case 0:
                           this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.suppressWarnings", isEnabling ? "enabled" : "disabled");
                           this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.suppressOptionalErrors", "disabled");
                           break;
                        case 1:
                           this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.suppressWarnings", isEnabling ? "enabled" : "disabled");
                           this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.suppressOptionalErrors", isEnabling ? "enabled" : "disabled");
                     }

                     return;
                  }

                  if (token.equals("static-access")) {
                     this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.staticAccessReceiver", severity, isEnabling);
                     this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.indirectStaticAccess", severity, isEnabling);
                     return;
                  }

                  if (token.equals("super")) {
                     this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.overridingMethodWithoutSuperInvocation", severity, isEnabling);
                     return;
                  }

                  if (token.equals("static-method")) {
                     this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.reportMethodCanBeStatic", severity, isEnabling);
                     return;
                  }

                  if (token.equals("switchDefault")) {
                     this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.missingDefaultCase", severity, isEnabling);
                     return;
                  }

                  if (token.equals("syntacticAnalysis")) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.syntacticNullAnalysisForFields", isEnabling ? "enabled" : "disabled");
                     return;
                  }
                  break;
               }

               this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.syntheticAccessEmulation", severity, isEnabling);
               return;
            case 't':
               if (token.startsWith("tasks")) {
                  annotationNames = Util.EMPTY_STRING;
                  end = token.indexOf(40);
                  end = token.indexOf(41);
                  if (end >= 0 && end >= 0 && end < end) {
                     annotationNames = token.substring(end + 1, end).trim();
                     annotationNames = annotationNames.replace('|', ',');
                  }

                  if (annotationNames.length() == 0) {
                     throw new IllegalArgumentException(this.bind("configure.invalidTaskTag", token));
                  }

                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.taskTags", isEnabling ? annotationNames : Util.EMPTY_STRING);
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.tasks", severity, isEnabling);
                  return;
               }

               if (token.equals("typeHiding")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.typeParameterHiding", severity, isEnabling);
                  return;
               }
               break;
            case 'u':
               if (!token.equals("unusedLocal") && !token.equals("unusedLocals")) {
                  if (!token.equals("unusedArgument") && !token.equals("unusedArguments")) {
                     if (token.equals("unusedExceptionParam")) {
                        this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unusedExceptionParameter", severity, isEnabling);
                        return;
                     }

                     if (token.equals("unusedImport") || token.equals("unusedImports")) {
                        this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unusedImport", severity, isEnabling);
                        return;
                     }

                     if (token.equals("unusedAllocation")) {
                        this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unusedObjectAllocation", severity, isEnabling);
                        return;
                     }

                     if (token.equals("unusedPrivate")) {
                        this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unusedPrivateMember", severity, isEnabling);
                        return;
                     }

                     if (token.equals("unusedLabel")) {
                        this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unusedLabel", severity, isEnabling);
                        return;
                     }

                     if (token.equals("uselessTypeCheck")) {
                        this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unnecessaryTypeCheck", severity, isEnabling);
                        return;
                     }

                     if (!token.equals("unchecked") && !token.equals("unsafe")) {
                        if (token.equals("unlikelyCollectionMethodArgumentType")) {
                           this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unlikelyCollectionMethodArgumentType", severity, isEnabling);
                           return;
                        }

                        if (token.equals("unlikelyEqualsArgumentType")) {
                           this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unlikelyEqualsArgumentType", severity, isEnabling);
                           return;
                        }

                        if (token.equals("unnecessaryElse")) {
                           this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unnecessaryElse", severity, isEnabling);
                           return;
                        }

                        if (token.equals("unusedThrown")) {
                           this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unusedDeclaredThrownException", severity, isEnabling);
                           return;
                        }

                        if (token.equals("unusedThrownWhenOverriding")) {
                           this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedDeclaredThrownExceptionWhenOverriding", isEnabling ? "enabled" : "disabled");
                           return;
                        }

                        if (token.equals("unusedThrownIncludeDocComment")) {
                           this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedDeclaredThrownExceptionIncludeDocCommentReference", isEnabling ? "enabled" : "disabled");
                           return;
                        }

                        if (token.equals("unusedThrownExemptExceptionThrowable")) {
                           this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedDeclaredThrownExceptionExemptExceptionAndThrowable", isEnabling ? "enabled" : "disabled");
                           return;
                        }

                        if (!token.equals("unqualifiedField") && !token.equals("unqualified-field-access")) {
                           if (token.equals("unused")) {
                              this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unusedLocal", severity, isEnabling);
                              this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameter", severity, isEnabling);
                              this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unusedImport", severity, isEnabling);
                              this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unusedPrivateMember", severity, isEnabling);
                              this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unusedDeclaredThrownException", severity, isEnabling);
                              this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unusedLabel", severity, isEnabling);
                              this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unusedTypeArgumentsForMethodInvocation", severity, isEnabling);
                              this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.redundantSpecificationOfTypeArguments", severity, isEnabling);
                              this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unusedTypeParameter", severity, isEnabling);
                              return;
                           }

                           if (token.equals("unusedParam")) {
                              this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameter", severity, isEnabling);
                              return;
                           }

                           if (token.equals("unusedTypeParameter")) {
                              this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unusedTypeParameter", severity, isEnabling);
                              return;
                           }

                           if (token.equals("unusedParamIncludeDoc")) {
                              this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameterIncludeDocCommentReference", isEnabling ? "enabled" : "disabled");
                              return;
                           }

                           if (token.equals("unusedParamOverriding")) {
                              this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameterWhenOverridingConcrete", isEnabling ? "enabled" : "disabled");
                              return;
                           }

                           if (token.equals("unusedParamImplementing")) {
                              this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameterWhenImplementingAbstract", isEnabling ? "enabled" : "disabled");
                              return;
                           }

                           if (token.equals("unusedTypeArgs")) {
                              this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unusedTypeArgumentsForMethodInvocation", severity, isEnabling);
                              this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.redundantSpecificationOfTypeArguments", severity, isEnabling);
                              return;
                           }

                           if (token.equals("unavoidableGenericProblems")) {
                              this.options.put("com.bea.core.repackaged.jdt.core.compiler.problem.unavoidableGenericTypeProblems", isEnabling ? "enabled" : "disabled");
                              return;
                           }
                           break;
                        }

                        this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unqualifiedFieldAccess", severity, isEnabling);
                        return;
                     }

                     this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.uncheckedTypeOperation", severity, isEnabling);
                     return;
                  }

                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameter", severity, isEnabling);
                  return;
               }

               this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unusedLocal", severity, isEnabling);
               return;
            case 'v':
               if (token.equals("varargsCast")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.varargsArgumentNeedCast", severity, isEnabling);
                  return;
               }
               break;
            case 'w':
               if (token.equals("warningToken")) {
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unhandledWarningToken", severity, isEnabling);
                  this.setSeverity("com.bea.core.repackaged.jdt.core.compiler.problem.unusedWarningToken", severity, isEnabling);
                  return;
               }
         }

         annotationNames = null;
         switch (severity) {
            case 0:
               annotationNames = this.bind("configure.invalidWarning", token);
               break;
            case 1:
               annotationNames = this.bind("configure.invalidError", token);
               break;
            case 1024:
               annotationNames = this.bind("configure.invalidInfo", token);
         }

         this.addPendingErrors(annotationNames);
      }
   }

   /** @deprecated */
   protected void initialize(PrintWriter outWriter, PrintWriter errWriter, boolean systemExit) {
      this.initialize(outWriter, errWriter, systemExit, (Map)null, (CompilationProgress)null);
   }

   /** @deprecated */
   protected void initialize(PrintWriter outWriter, PrintWriter errWriter, boolean systemExit, Map customDefaultOptions) {
      this.initialize(outWriter, errWriter, systemExit, customDefaultOptions, (CompilationProgress)null);
   }

   protected void initialize(PrintWriter outWriter, PrintWriter errWriter, boolean systemExit, Map customDefaultOptions, CompilationProgress compilationProgress) {
      this.logger = new Logger(this, outWriter, errWriter);
      this.proceed = true;
      this.out = outWriter;
      this.err = errWriter;
      this.systemExitWhenFinished = systemExit;
      this.options = (new CompilerOptions()).getMap();
      this.ignoreOptionalProblemsFromFolders = null;
      this.progress = compilationProgress;
      if (customDefaultOptions != null) {
         this.didSpecifySource = customDefaultOptions.get("com.bea.core.repackaged.jdt.core.compiler.source") != null;
         this.didSpecifyTarget = customDefaultOptions.get("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform") != null;
         Iterator iter = customDefaultOptions.entrySet().iterator();

         while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            this.options.put((String)entry.getKey(), (String)entry.getValue());
         }
      } else {
         this.didSpecifySource = false;
         this.didSpecifyTarget = false;
      }

      this.classNames = null;
   }

   protected void initializeAnnotationProcessorManager() {
      String className = "com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch.BatchAnnotationProcessorManager";

      try {
         Class c = Class.forName(className);
         AbstractAnnotationProcessorManager annotationManager = (AbstractAnnotationProcessorManager)c.newInstance();
         annotationManager.configure(this, this.expandedCommandLine);
         annotationManager.setErr(this.err);
         annotationManager.setOut(this.out);
         this.batchCompiler.annotationProcessorManager = annotationManager;
      } catch (InstantiationException | ClassNotFoundException var4) {
         this.logger.logUnavaibleAPT(className);
         throw new AbortCompilation();
      } catch (IllegalAccessException var5) {
         throw new AbortCompilation();
      } catch (UnsupportedClassVersionError var6) {
         this.logger.logIncorrectVMVersionForAnnotationProcessing();
      }

   }

   private static boolean isParentOf(char[] folderName, char[] fileName) {
      if (folderName.length >= fileName.length) {
         return false;
      } else if (fileName[folderName.length] != '\\' && fileName[folderName.length] != '/') {
         return false;
      } else {
         for(int i = folderName.length - 1; i >= 0; --i) {
            if (folderName[i] != fileName[i]) {
               return false;
            }
         }

         return true;
      }
   }

   public void outputClassFiles(CompilationResult unitResult) {
      if (unitResult != null && (!unitResult.hasErrors() || this.proceedOnError)) {
         ClassFile[] classFiles = unitResult.getClassFiles();
         String currentDestinationPath = null;
         boolean generateClasspathStructure = false;
         CompilationUnit compilationUnit = (CompilationUnit)unitResult.compilationUnit;
         if (compilationUnit.destinationPath == null) {
            if (this.destinationPath == null) {
               currentDestinationPath = this.extractDestinationPathFromSourceFile(unitResult);
            } else if (this.destinationPath != "none") {
               currentDestinationPath = this.destinationPath;
               generateClasspathStructure = true;
            }
         } else if (compilationUnit.destinationPath != "none") {
            currentDestinationPath = compilationUnit.destinationPath;
            generateClasspathStructure = true;
         }

         if (currentDestinationPath != null) {
            int i = 0;

            for(int fileCount = classFiles.length; i < fileCount; ++i) {
               ClassFile classFile = classFiles[i];
               char[] filename = classFile.fileName();
               int length = filename.length;
               char[] relativeName = new char[length + 6];
               System.arraycopy(filename, 0, relativeName, 0, length);
               System.arraycopy(SuffixConstants.SUFFIX_class, 0, relativeName, length, 6);
               CharOperation.replace(relativeName, '/', File.separatorChar);
               String relativeStringName = new String(relativeName);

               try {
                  if (this.compilerOptions.verbose) {
                     this.out.println(Messages.bind(Messages.compilation_write, (Object[])(new String[]{String.valueOf(this.exportedClassFilesCounter + 1), relativeStringName})));
                  }

                  Util.writeToDisk(generateClasspathStructure, currentDestinationPath, relativeStringName, classFile);
                  this.logger.logClassFile(generateClasspathStructure, currentDestinationPath, relativeStringName);
                  ++this.exportedClassFilesCounter;
               } catch (IOException var14) {
                  this.logger.logNoClassFileCreated(currentDestinationPath, relativeStringName, var14);
               }
            }

            this.batchCompiler.lookupEnvironment.releaseClassFiles(classFiles);
         }
      }

   }

   public void performCompilation() {
      this.startTime = System.currentTimeMillis();
      FileSystem environment = this.getLibraryAccess();

      try {
         this.compilerOptions = new CompilerOptions(this.options);
         this.compilerOptions.performMethodsFullRecovery = false;
         this.compilerOptions.performStatementsRecovery = false;
         this.batchCompiler = new Compiler(environment, this.getHandlingPolicy(), this.compilerOptions, this.getBatchRequestor(), this.getProblemFactory(), this.out, this.progress);
         this.batchCompiler.remainingIterations = this.maxRepetition - this.currentRepetition;
         String setting = System.getProperty("jdt.compiler.useSingleThread");
         this.batchCompiler.useSingleThread = setting != null && setting.equals("true");
         if (this.compilerOptions.complianceLevel >= 3276800L && this.compilerOptions.processAnnotations) {
            if (this.checkVMVersion(3276800L)) {
               this.initializeAnnotationProcessorManager();
               if (this.classNames != null) {
                  this.batchCompiler.setBinaryTypes(this.processClassNames(this.batchCompiler.lookupEnvironment));
               }
            } else {
               this.logger.logIncorrectVMVersionForAnnotationProcessing();
            }

            if (this.checkVMVersion(3473408L)) {
               this.initRootModules(this.batchCompiler.lookupEnvironment, environment);
            }
         }

         this.compilerOptions.verbose = this.verbose;
         this.compilerOptions.produceReferenceInfo = this.produceRefInfo;

         try {
            this.logger.startLoggingSources();
            this.batchCompiler.compile(this.getCompilationUnits());
         } finally {
            this.logger.endLoggingSources();
         }

         if (this.extraProblems != null) {
            this.loggingExtraProblems();
            this.extraProblems = null;
         }

         if (this.compilerStats != null) {
            this.compilerStats[this.currentRepetition] = this.batchCompiler.stats;
         }

         this.logger.printStats();
      } finally {
         environment.cleanup();
      }

   }

   protected void loggingExtraProblems() {
      this.logger.loggingExtraProblems(this);
   }

   public void printUsage() {
      this.printUsage("misc.usage");
   }

   private void printUsage(String sectionID) {
      this.logger.logUsage(this.bind(sectionID, new String[]{System.getProperty("path.separator"), this.bind("compiler.name"), this.bind("compiler.version"), this.bind("compiler.copyright")}));
      this.logger.flush();
   }

   private void initRootModules(LookupEnvironment environment, FileSystem fileSystem) {
      Map map = new HashMap();
      Iterator var5 = this.rootModules.iterator();

      String m;
      ModuleBinding mod;
      while(var5.hasNext()) {
         m = (String)var5.next();
         mod = environment.getModule(m.toCharArray());
         if (mod == null) {
            throw new IllegalArgumentException(this.bind("configure.invalidModuleName", m));
         }

         PackageBinding[] exports = mod.getExports();
         PackageBinding[] var11 = exports;
         int var10 = exports.length;

         for(int var9 = 0; var9 < var10; ++var9) {
            PackageBinding packageBinding = var11[var9];
            String qName = CharOperation.toString(packageBinding.compoundName);
            String existing = (String)map.get(qName);
            if (existing != null) {
               throw new IllegalArgumentException(this.bind("configure.packageConflict", new String[]{qName, existing, m}));
            }

            map.put(qName, m);
         }
      }

      if (this.limitedModules != null) {
         var5 = this.limitedModules.iterator();

         while(var5.hasNext()) {
            m = (String)var5.next();
            mod = environment.getModule(m.toCharArray());
            if (mod == null) {
               throw new IllegalArgumentException(this.bind("configure.invalidModuleName", m));
            }
         }
      }

   }

   private ReferenceBinding[] processClassNames(LookupEnvironment environment) {
      int length = this.classNames.length;
      ReferenceBinding[] referenceBindings = new ReferenceBinding[length];
      ModuleBinding[] modules = new ModuleBinding[length];
      Set modSet = new HashSet();
      String[] typeNames = new String[length];
      int i;
      String currentName;
      ModuleBinding mod;
      if (this.complianceLevel <= 3407872L) {
         typeNames = this.classNames;
      } else {
         for(i = 0; i < length; ++i) {
            currentName = this.classNames[i];
            int idx = currentName.indexOf(47);
            mod = null;
            if (idx > 0) {
               String m = currentName.substring(0, idx);
               mod = environment.getModule(m.toCharArray());
               if (mod == null) {
                  throw new IllegalArgumentException(this.bind("configure.invalidModuleName", m));
               }

               modules[i] = mod;
               modSet.add(mod);
               currentName = currentName.substring(idx + 1);
            }

            typeNames[i] = currentName;
         }
      }

      for(i = 0; i < length; ++i) {
         currentName = null;
         String cls = typeNames[i];
         char[][] compoundName;
         if (cls.indexOf(46) != -1) {
            char[] typeName = cls.toCharArray();
            compoundName = CharOperation.splitOn('.', typeName);
         } else {
            compoundName = new char[][]{cls.toCharArray()};
         }

         mod = modules[i];
         ReferenceBinding type = mod != null ? environment.getType(compoundName, mod) : environment.getType(compoundName);
         if (type == null || !type.isValidBinding()) {
            throw new IllegalArgumentException(this.bind("configure.invalidClassName", this.classNames[i]));
         }

         if (type.isBinaryBinding()) {
            referenceBindings[i] = type;
            type.superclass();
         }
      }

      return referenceBindings;
   }

   private ArrayList processModulePathEntries(String arg) {
      ArrayList paths = new ArrayList();
      if (arg == null) {
         return paths;
      } else {
         StringTokenizer tokenizer = new StringTokenizer(arg, File.pathSeparator, false);

         while(tokenizer.hasMoreTokens()) {
            paths.add(tokenizer.nextToken());
         }

         return paths;
      }
   }

   public void processPathEntries(int defaultSize, ArrayList paths, String currentPath, String customEncoding, boolean isSourceOnly, boolean rejectDestinationPathOnJars) {
      String currentClasspathName = null;
      String currentDestinationPath = null;
      ArrayList currentRuleSpecs = new ArrayList(defaultSize);
      StringTokenizer tokenizer = new StringTokenizer(currentPath, File.pathSeparator + "[]", true);
      ArrayList tokens = new ArrayList();

      while(tokenizer.hasMoreTokens()) {
         tokens.add(tokenizer.nextToken());
      }

      int state = 0;
      String token = null;
      int cursor = 0;
      int tokensNb = tokens.size();
      int bracket = -1;

      while(cursor < tokensNb && state != 99) {
         token = (String)tokens.get(cursor++);
         if (token.equals(File.pathSeparator)) {
            switch (state) {
               case 0:
               case 3:
               case 10:
                  break;
               case 1:
               case 2:
               case 8:
                  state = 3;
                  this.addNewEntry(paths, currentClasspathName, currentRuleSpecs, customEncoding, currentDestinationPath, isSourceOnly, rejectDestinationPathOnJars);
                  currentRuleSpecs.clear();
                  break;
               case 4:
               case 5:
               case 9:
               default:
                  state = 99;
                  break;
               case 6:
                  state = 4;
                  break;
               case 7:
                  throw new IllegalArgumentException(this.bind("configure.incorrectDestinationPathEntry", currentPath));
               case 11:
                  cursor = bracket + 1;
                  state = 5;
            }
         } else if (token.equals("[")) {
            switch (state) {
               case 0:
                  currentClasspathName = "";
               case 1:
                  bracket = cursor - 1;
               case 11:
                  state = 10;
                  break;
               case 2:
                  state = 9;
                  break;
               case 3:
               case 4:
               case 5:
               case 6:
               case 7:
               case 9:
               case 10:
               default:
                  state = 99;
                  break;
               case 8:
                  state = 5;
            }
         } else if (token.equals("]")) {
            switch (state) {
               case 6:
                  state = 2;
                  break;
               case 7:
                  state = 8;
                  break;
               case 8:
               case 9:
               case 11:
               default:
                  state = 99;
                  break;
               case 10:
                  state = 11;
            }
         } else {
            switch (state) {
               case 0:
               case 3:
                  state = 1;
                  currentClasspathName = token;
                  break;
               case 1:
               case 2:
               case 6:
               case 7:
               case 8:
               default:
                  state = 99;
                  break;
               case 5:
                  if (token.startsWith("-d ")) {
                     if (currentDestinationPath != null) {
                        throw new IllegalArgumentException(this.bind("configure.duplicateDestinationPathEntry", currentPath));
                     }

                     currentDestinationPath = token.substring(3).trim();
                     state = 7;
                     break;
                  }
               case 4:
                  if (currentDestinationPath != null) {
                     throw new IllegalArgumentException(this.bind("configure.accessRuleAfterDestinationPath", currentPath));
                  }

                  state = 6;
                  currentRuleSpecs.add(token);
                  break;
               case 9:
                  if (!token.startsWith("-d ")) {
                     state = 99;
                  } else {
                     currentDestinationPath = token.substring(3).trim();
                     state = 7;
                  }
               case 10:
                  break;
               case 11:
                  for(int i = bracket; i < cursor; ++i) {
                     currentClasspathName = currentClasspathName + (String)tokens.get(i);
                  }

                  state = 1;
            }
         }

         if (state == 11 && cursor == tokensNb) {
            cursor = bracket + 1;
            state = 5;
         }
      }

      switch (state) {
         case 1:
         case 2:
         case 8:
            this.addNewEntry(paths, currentClasspathName, currentRuleSpecs, customEncoding, currentDestinationPath, isSourceOnly, rejectDestinationPathOnJars);
         case 3:
            break;
         case 4:
         case 5:
         case 6:
         case 7:
         case 9:
         case 10:
         case 11:
         default:
            if (currentPath.length() != 0) {
               this.addPendingErrors(this.bind("configure.incorrectClasspath", currentPath));
            }
      }

   }

   private int processPaths(String[] args, int index, String currentArg, ArrayList paths) {
      int localIndex = index;
      int count = 0;
      int i = 0;

      for(int max = currentArg.length(); i < max; ++i) {
         switch (currentArg.charAt(i)) {
            case '[':
               ++count;
            case '\\':
            default:
               break;
            case ']':
               --count;
         }
      }

      if (count == 0) {
         paths.add(currentArg);
         return index - index;
      } else if (count > 1) {
         throw new IllegalArgumentException(this.bind("configure.unexpectedBracket", currentArg));
      } else {
         StringBuffer currentPath = new StringBuffer(currentArg);

         while(localIndex < args.length) {
            ++localIndex;
            String nextArg = args[localIndex];
            int i = 0;

            for(int max = nextArg.length(); i < max; ++i) {
               switch (nextArg.charAt(i)) {
                  case '[':
                     if (count > 1) {
                        throw new IllegalArgumentException(this.bind("configure.unexpectedBracket", nextArg));
                     }

                     ++count;
                  case '\\':
                  default:
                     break;
                  case ']':
                     --count;
               }
            }

            if (count == 0) {
               currentPath.append(' ');
               currentPath.append(nextArg);
               paths.add(currentPath.toString());
               return localIndex - index;
            }

            if (count < 0) {
               throw new IllegalArgumentException(this.bind("configure.unexpectedBracket", nextArg));
            }

            currentPath.append(' ');
            currentPath.append(nextArg);
         }

         throw new IllegalArgumentException(this.bind("configure.unexpectedBracket", currentArg));
      }
   }

   private int processPaths(String[] args, int index, String currentArg, String[] paths) {
      int localIndex = index;
      int count = 0;
      int i = 0;

      for(int max = currentArg.length(); i < max; ++i) {
         switch (currentArg.charAt(i)) {
            case '[':
               ++count;
            case '\\':
            default:
               break;
            case ']':
               --count;
         }
      }

      if (count == 0) {
         paths[0] = currentArg;
         return index - index;
      } else {
         StringBuffer currentPath = new StringBuffer(currentArg);

         while(true) {
            ++localIndex;
            if (localIndex >= args.length) {
               throw new IllegalArgumentException(this.bind("configure.unexpectedBracket", currentArg));
            }

            String nextArg = args[localIndex];
            int i = 0;

            for(int max = nextArg.length(); i < max; ++i) {
               switch (nextArg.charAt(i)) {
                  case '[':
                     if (count > 1) {
                        throw new IllegalArgumentException(this.bind("configure.unexpectedBracket", currentArg));
                     }

                     ++count;
                  case '\\':
                  default:
                     break;
                  case ']':
                     --count;
               }
            }

            if (count == 0) {
               currentPath.append(' ');
               currentPath.append(nextArg);
               paths[0] = currentPath.toString();
               return localIndex - index;
            }

            if (count < 0) {
               throw new IllegalArgumentException(this.bind("configure.unexpectedBracket", currentArg));
            }

            currentPath.append(' ');
            currentPath.append(nextArg);
         }
      }
   }

   public void relocalize() {
      this.relocalize(Locale.getDefault());
   }

   private void relocalize(Locale locale) {
      this.compilerLocale = locale;

      try {
         this.bundle = Main.ResourceBundleFactory.getBundle(locale);
      } catch (MissingResourceException var3) {
         System.out.println("Missing resource : " + "com.bea.core.repackaged.jdt.internal.compiler.batch.messages".replace('.', '/') + ".properties for locale " + locale);
         throw var3;
      }
   }

   public void setDestinationPath(String dest) {
      this.destinationPath = dest;
   }

   public void setLocale(Locale locale) {
      this.relocalize(locale);
   }

   protected void setPaths(ArrayList bootclasspaths, String sourcepathClasspathArg, ArrayList sourcepathClasspaths, ArrayList classpaths, String modulePath, String moduleSourcepath, ArrayList extdirsClasspaths, ArrayList endorsedDirClasspaths, String customEncoding) {
      String allPaths;
      if (this.complianceLevel == 0L) {
         allPaths = (String)this.options.get("com.bea.core.repackaged.jdt.core.compiler.compliance");
         this.complianceLevel = CompilerOptions.versionToJdkLevel(allPaths);
      }

      allPaths = null;
      long jdkLevel = this.validateClasspathOptions(bootclasspaths, endorsedDirClasspaths, extdirsClasspaths);
      ArrayList allPaths;
      if (this.releaseVersion != null && this.complianceLevel < jdkLevel) {
         allPaths = new ArrayList();
         allPaths.add(FileSystem.getOlderSystemRelease(this.javaHomeCache.getAbsolutePath(), this.releaseVersion, (AccessRuleSet)null));
      } else {
         allPaths = this.handleBootclasspath(bootclasspaths, customEncoding);
      }

      List cp = this.handleClasspath(classpaths, customEncoding);
      List mp = this.handleModulepath(modulePath);
      List msp = this.handleModuleSourcepath(moduleSourcepath);
      ArrayList sourcepaths = new ArrayList();
      if (sourcepathClasspathArg != null) {
         this.processPathEntries(4, sourcepaths, sourcepathClasspathArg, customEncoding, true, false);
      }

      List extdirs = this.handleExtdirs(extdirsClasspaths);
      List endorsed = this.handleEndorseddirs(endorsedDirClasspaths);
      allPaths.addAll(0, endorsed);
      allPaths.addAll(extdirs);
      allPaths.addAll(sourcepaths);
      allPaths.addAll(cp);
      allPaths.addAll(mp);
      allPaths.addAll(msp);
      allPaths = FileSystem.ClasspathNormalizer.normalize(allPaths);
      this.checkedClasspaths = new FileSystem.Classpath[allPaths.size()];
      allPaths.toArray(this.checkedClasspaths);
      this.logger.logClasspath(this.checkedClasspaths);
      if (this.annotationPaths != null && "enabled".equals(this.options.get("com.bea.core.repackaged.jdt.core.compiler.annotation.nullanalysis"))) {
         FileSystem.Classpath[] var22;
         int var21 = (var22 = this.checkedClasspaths).length;

         for(int var20 = 0; var20 < var21; ++var20) {
            FileSystem.Classpath c = var22[var20];
            if (c instanceof ClasspathJar) {
               ((ClasspathJar)c).annotationPaths = this.annotationPaths;
            } else if (c instanceof ClasspathJrt) {
               ((ClasspathJrt)c).annotationPaths = this.annotationPaths;
            }
         }
      }

   }

   protected static final boolean shouldIgnoreOptionalProblems(char[][] folderNames, char[] fileName) {
      if (folderNames != null && fileName != null) {
         int i = 0;

         for(int max = folderNames.length; i < max; ++i) {
            char[] folderName = folderNames[i];
            if (isParentOf(folderName, fileName)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   protected long validateClasspathOptions(ArrayList bootclasspaths, ArrayList endorsedDirClasspaths, ArrayList extdirsClasspaths) {
      if (this.complianceLevel > 3407872L) {
         if (bootclasspaths != null && bootclasspaths.size() > 0) {
            throw new IllegalArgumentException(this.bind("configure.unsupportedOption", "-bootclasspath"));
         }

         if (extdirsClasspaths != null && extdirsClasspaths.size() > 0) {
            throw new IllegalArgumentException(this.bind("configure.unsupportedOption", "-extdirs"));
         }

         if (endorsedDirClasspaths != null && endorsedDirClasspaths.size() > 0) {
            throw new IllegalArgumentException(this.bind("configure.unsupportedOption", "-endorseddirs"));
         }
      }

      long jdkLevel = Util.getJDKLevel(this.getJavaHome());
      if (jdkLevel < 3473408L && this.releaseVersion != null) {
         throw new IllegalArgumentException(this.bind("configure.unsupportedReleaseOption"));
      } else {
         return jdkLevel;
      }
   }

   protected void validateOptions(boolean didSpecifyCompliance) {
      String sourceVersion;
      String targetVersion;
      if (didSpecifyCompliance) {
         sourceVersion = (String)this.options.get("com.bea.core.repackaged.jdt.core.compiler.compliance");
         if (this.releaseVersion != null) {
            throw new IllegalArgumentException(this.bind("configure.unsupportedWithRelease", sourceVersion));
         }

         if ("1.3".equals(sourceVersion)) {
            if (!this.didSpecifySource) {
               this.options.put("com.bea.core.repackaged.jdt.core.compiler.source", "1.3");
            }

            if (!this.didSpecifyTarget) {
               this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.1");
            }
         } else {
            Object source;
            if ("1.4".equals(sourceVersion)) {
               if (this.didSpecifySource) {
                  source = this.options.get("com.bea.core.repackaged.jdt.core.compiler.source");
                  if ("1.3".equals(source)) {
                     if (!this.didSpecifyTarget) {
                        this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.2");
                     }
                  } else if ("1.4".equals(source) && !this.didSpecifyTarget) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.4");
                  }
               } else {
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.source", "1.3");
                  if (!this.didSpecifyTarget) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.2");
                  }
               }
            } else if ("1.5".equals(sourceVersion)) {
               if (this.didSpecifySource) {
                  source = this.options.get("com.bea.core.repackaged.jdt.core.compiler.source");
                  if (!"1.3".equals(source) && !"1.4".equals(source)) {
                     if ("1.5".equals(source) && !this.didSpecifyTarget) {
                        this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.5");
                     }
                  } else if (!this.didSpecifyTarget) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.4");
                  }
               } else {
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.source", "1.5");
                  if (!this.didSpecifyTarget) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.5");
                  }
               }
            } else if ("1.6".equals(sourceVersion)) {
               if (this.didSpecifySource) {
                  source = this.options.get("com.bea.core.repackaged.jdt.core.compiler.source");
                  if (!"1.3".equals(source) && !"1.4".equals(source)) {
                     if (("1.5".equals(source) || "1.6".equals(source)) && !this.didSpecifyTarget) {
                        this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.6");
                     }
                  } else if (!this.didSpecifyTarget) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.4");
                  }
               } else {
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.source", "1.6");
                  if (!this.didSpecifyTarget) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.6");
                  }
               }
            } else if ("1.7".equals(sourceVersion)) {
               if (this.didSpecifySource) {
                  source = this.options.get("com.bea.core.repackaged.jdt.core.compiler.source");
                  if (!"1.3".equals(source) && !"1.4".equals(source)) {
                     if (!"1.5".equals(source) && !"1.6".equals(source)) {
                        if ("1.7".equals(source) && !this.didSpecifyTarget) {
                           this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.7");
                        }
                     } else if (!this.didSpecifyTarget) {
                        this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.6");
                     }
                  } else if (!this.didSpecifyTarget) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.4");
                  }
               } else {
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.source", "1.7");
                  if (!this.didSpecifyTarget) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.7");
                  }
               }
            } else if ("1.8".equals(sourceVersion)) {
               if (this.didSpecifySource) {
                  source = this.options.get("com.bea.core.repackaged.jdt.core.compiler.source");
                  if (!"1.3".equals(source) && !"1.4".equals(source)) {
                     if (!"1.5".equals(source) && !"1.6".equals(source)) {
                        if ("1.7".equals(source)) {
                           if (!this.didSpecifyTarget) {
                              this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.7");
                           }
                        } else if ("1.8".equals(source) && !this.didSpecifyTarget) {
                           this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.8");
                        }
                     } else if (!this.didSpecifyTarget) {
                        this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.6");
                     }
                  } else if (!this.didSpecifyTarget) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.4");
                  }
               } else {
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.source", "1.8");
                  if (!this.didSpecifyTarget) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.8");
                  }
               }
            } else if ("9".equals(sourceVersion)) {
               if (this.didSpecifySource) {
                  source = this.options.get("com.bea.core.repackaged.jdt.core.compiler.source");
                  if (!"1.3".equals(source) && !"1.4".equals(source)) {
                     if (!"1.5".equals(source) && !"1.6".equals(source)) {
                        if ("1.7".equals(source)) {
                           if (!this.didSpecifyTarget) {
                              this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.7");
                           }
                        } else if ("1.8".equals(source)) {
                           if (!this.didSpecifyTarget) {
                              this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.8");
                           }
                        } else if ("9".equals(source) && !this.didSpecifyTarget) {
                           this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "9");
                        }
                     } else if (!this.didSpecifyTarget) {
                        this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.6");
                     }
                  } else if (!this.didSpecifyTarget) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.4");
                  }
               } else {
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.source", "9");
                  if (!this.didSpecifyTarget) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "9");
                  }
               }
            } else if ("10".equals(sourceVersion)) {
               if (this.didSpecifySource) {
                  source = this.options.get("com.bea.core.repackaged.jdt.core.compiler.source");
                  if (!"1.3".equals(source) && !"1.4".equals(source)) {
                     if (!"1.5".equals(source) && !"1.6".equals(source)) {
                        if ("1.7".equals(source)) {
                           if (!this.didSpecifyTarget) {
                              this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.7");
                           }
                        } else if ("1.8".equals(source)) {
                           if (!this.didSpecifyTarget) {
                              this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.8");
                           }
                        } else if ("9".equals(source)) {
                           if (!this.didSpecifyTarget) {
                              this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "9");
                           }
                        } else if ("10".equals(source) && !this.didSpecifyTarget) {
                           this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "10");
                        }
                     } else if (!this.didSpecifyTarget) {
                        this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.6");
                     }
                  } else if (!this.didSpecifyTarget) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.4");
                  }
               } else {
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.source", "10");
                  if (!this.didSpecifyTarget) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "10");
                  }
               }
            } else if (!this.didSpecifyTarget) {
               if (this.didSpecifySource) {
                  targetVersion = (String)this.options.get("com.bea.core.repackaged.jdt.core.compiler.source");
                  if (!"1.3".equals(targetVersion) && !"1.4".equals(targetVersion)) {
                     if (!"1.5".equals(targetVersion) && !"1.6".equals(targetVersion)) {
                        if (CompilerOptions.versionToJdkLevel(targetVersion) >= 3342336L) {
                           this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", targetVersion);
                        }
                     } else {
                        this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.6");
                     }
                  } else if (!this.didSpecifyTarget) {
                     this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.4");
                  }
               } else if (CompilerOptions.versionToJdkLevel(sourceVersion) > 3538944L) {
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.source", sourceVersion);
                  this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", sourceVersion);
               }
            }
         }
      } else if (this.didSpecifySource) {
         sourceVersion = (String)this.options.get("com.bea.core.repackaged.jdt.core.compiler.source");
         if ("1.4".equals(sourceVersion)) {
            if (!didSpecifyCompliance) {
               this.options.put("com.bea.core.repackaged.jdt.core.compiler.compliance", "1.4");
            }

            if (!this.didSpecifyTarget) {
               this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.4");
            }
         } else if ("1.5".equals(sourceVersion)) {
            if (!didSpecifyCompliance) {
               this.options.put("com.bea.core.repackaged.jdt.core.compiler.compliance", "1.5");
            }

            if (!this.didSpecifyTarget) {
               this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.5");
            }
         } else if ("1.6".equals(sourceVersion)) {
            if (!didSpecifyCompliance) {
               this.options.put("com.bea.core.repackaged.jdt.core.compiler.compliance", "1.6");
            }

            if (!this.didSpecifyTarget) {
               this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.6");
            }
         } else if ("1.7".equals(sourceVersion)) {
            if (!didSpecifyCompliance) {
               this.options.put("com.bea.core.repackaged.jdt.core.compiler.compliance", "1.7");
            }

            if (!this.didSpecifyTarget) {
               this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.7");
            }
         } else if ("1.8".equals(sourceVersion)) {
            if (!didSpecifyCompliance) {
               this.options.put("com.bea.core.repackaged.jdt.core.compiler.compliance", "1.8");
            }

            if (!this.didSpecifyTarget) {
               this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.8");
            }
         } else if ("9".equals(sourceVersion)) {
            if (!didSpecifyCompliance) {
               this.options.put("com.bea.core.repackaged.jdt.core.compiler.compliance", "9");
            }

            if (!this.didSpecifyTarget) {
               this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "9");
            }
         } else if ("10".equals(sourceVersion)) {
            if (!didSpecifyCompliance) {
               this.options.put("com.bea.core.repackaged.jdt.core.compiler.compliance", "10");
            }

            if (!this.didSpecifyTarget) {
               this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "10");
            }
         } else if (CompilerOptions.versionToJdkLevel(sourceVersion) > 3538944L) {
            if (!didSpecifyCompliance) {
               this.options.put("com.bea.core.repackaged.jdt.core.compiler.compliance", sourceVersion);
            }

            if (!this.didSpecifyTarget) {
               this.options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", sourceVersion);
            }
         }
      }

      sourceVersion = (String)this.options.get("com.bea.core.repackaged.jdt.core.compiler.source");
      if (this.complianceLevel == 0L) {
         targetVersion = (String)this.options.get("com.bea.core.repackaged.jdt.core.compiler.compliance");
         this.complianceLevel = CompilerOptions.versionToJdkLevel(targetVersion);
      }

      if (sourceVersion.equals("10") && this.complianceLevel < 3538944L) {
         throw new IllegalArgumentException(this.bind("configure.incompatibleComplianceForSource", (String)this.options.get("com.bea.core.repackaged.jdt.core.compiler.compliance"), "10"));
      } else if (sourceVersion.equals("9") && this.complianceLevel < 3473408L) {
         throw new IllegalArgumentException(this.bind("configure.incompatibleComplianceForSource", (String)this.options.get("com.bea.core.repackaged.jdt.core.compiler.compliance"), "9"));
      } else if (sourceVersion.equals("1.8") && this.complianceLevel < 3407872L) {
         throw new IllegalArgumentException(this.bind("configure.incompatibleComplianceForSource", (String)this.options.get("com.bea.core.repackaged.jdt.core.compiler.compliance"), "1.8"));
      } else if (sourceVersion.equals("1.7") && this.complianceLevel < 3342336L) {
         throw new IllegalArgumentException(this.bind("configure.incompatibleComplianceForSource", (String)this.options.get("com.bea.core.repackaged.jdt.core.compiler.compliance"), "1.7"));
      } else if (sourceVersion.equals("1.6") && this.complianceLevel < 3276800L) {
         throw new IllegalArgumentException(this.bind("configure.incompatibleComplianceForSource", (String)this.options.get("com.bea.core.repackaged.jdt.core.compiler.compliance"), "1.6"));
      } else if (sourceVersion.equals("1.5") && this.complianceLevel < 3211264L) {
         throw new IllegalArgumentException(this.bind("configure.incompatibleComplianceForSource", (String)this.options.get("com.bea.core.repackaged.jdt.core.compiler.compliance"), "1.5"));
      } else if (sourceVersion.equals("1.4") && this.complianceLevel < 3145728L) {
         throw new IllegalArgumentException(this.bind("configure.incompatibleComplianceForSource", (String)this.options.get("com.bea.core.repackaged.jdt.core.compiler.compliance"), "1.4"));
      } else {
         long ver = CompilerOptions.versionToJdkLevel(sourceVersion);
         if (this.complianceLevel < ver) {
            throw new IllegalArgumentException(this.bind("configure.incompatibleComplianceForSource", (String)this.options.get("com.bea.core.repackaged.jdt.core.compiler.compliance"), sourceVersion));
         } else if (this.enablePreview && this.complianceLevel != ClassFileConstants.getLatestJDKLevel()) {
            throw new IllegalArgumentException(this.bind("configure.unsupportedPreview"));
         } else {
            if (this.didSpecifyTarget) {
               targetVersion = (String)this.options.get("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform");
               if ("jsr14".equals(targetVersion)) {
                  if (CompilerOptions.versionToJdkLevel(sourceVersion) < 3211264L) {
                     throw new IllegalArgumentException(this.bind("configure.incompatibleTargetForGenericSource", targetVersion, sourceVersion));
                  }
               } else if ("cldc1.1".equals(targetVersion)) {
                  if (this.didSpecifySource && CompilerOptions.versionToJdkLevel(sourceVersion) >= 3145728L) {
                     throw new IllegalArgumentException(this.bind("configure.incompatibleSourceForCldcTarget", targetVersion, sourceVersion));
                  }

                  if (this.complianceLevel >= 3211264L) {
                     throw new IllegalArgumentException(this.bind("configure.incompatibleComplianceForCldcTarget", targetVersion, sourceVersion));
                  }
               } else {
                  if (CompilerOptions.versionToJdkLevel(sourceVersion) >= 3407872L && CompilerOptions.versionToJdkLevel(targetVersion) < 3407872L) {
                     throw new IllegalArgumentException(this.bind("configure.incompatibleTargetForSource", targetVersion, "1.8"));
                  }

                  if (CompilerOptions.versionToJdkLevel(sourceVersion) >= 3342336L && CompilerOptions.versionToJdkLevel(targetVersion) < 3342336L) {
                     throw new IllegalArgumentException(this.bind("configure.incompatibleTargetForSource", targetVersion, "1.7"));
                  }

                  if (CompilerOptions.versionToJdkLevel(sourceVersion) >= 3276800L && CompilerOptions.versionToJdkLevel(targetVersion) < 3276800L) {
                     throw new IllegalArgumentException(this.bind("configure.incompatibleTargetForSource", targetVersion, "1.6"));
                  }

                  if (CompilerOptions.versionToJdkLevel(sourceVersion) >= 3211264L && CompilerOptions.versionToJdkLevel(targetVersion) < 3211264L) {
                     throw new IllegalArgumentException(this.bind("configure.incompatibleTargetForSource", targetVersion, "1.5"));
                  }

                  if (CompilerOptions.versionToJdkLevel(sourceVersion) >= 3145728L && CompilerOptions.versionToJdkLevel(targetVersion) < 3145728L) {
                     throw new IllegalArgumentException(this.bind("configure.incompatibleTargetForSource", targetVersion, "1.4"));
                  }

                  if (this.complianceLevel < CompilerOptions.versionToJdkLevel(targetVersion)) {
                     throw new IllegalArgumentException(this.bind("configure.incompatibleComplianceForTarget", (String)this.options.get("com.bea.core.repackaged.jdt.core.compiler.compliance"), targetVersion));
                  }
               }
            }

         }
      }
   }

   public static class Logger {
      private PrintWriter err;
      private PrintWriter log;
      private Main main;
      private PrintWriter out;
      private HashMap parameters;
      int tagBits;
      private static final String CLASS = "class";
      private static final String CLASS_FILE = "classfile";
      private static final String CLASSPATH = "classpath";
      private static final String CLASSPATH_FILE = "FILE";
      private static final String CLASSPATH_FOLDER = "FOLDER";
      private static final String CLASSPATH_ID = "id";
      private static final String CLASSPATH_JAR = "JAR";
      private static final String CLASSPATHS = "classpaths";
      private static final String COMMAND_LINE_ARGUMENT = "argument";
      private static final String COMMAND_LINE_ARGUMENTS = "command_line";
      private static final String COMPILER = "compiler";
      private static final String COMPILER_COPYRIGHT = "copyright";
      private static final String COMPILER_NAME = "name";
      private static final String COMPILER_VERSION = "version";
      public static final int EMACS = 2;
      private static final String ERROR = "ERROR";
      private static final String ERROR_TAG = "error";
      private static final String WARNING_TAG = "warning";
      private static final String EXCEPTION = "exception";
      private static final String EXTRA_PROBLEM_TAG = "extra_problem";
      private static final String EXTRA_PROBLEMS = "extra_problems";
      private static final HashtableOfInt FIELD_TABLE = new HashtableOfInt();
      private static final String KEY = "key";
      private static final String MESSAGE = "message";
      private static final String NUMBER_OF_CLASSFILES = "number_of_classfiles";
      private static final String NUMBER_OF_ERRORS = "errors";
      private static final String NUMBER_OF_LINES = "number_of_lines";
      private static final String NUMBER_OF_PROBLEMS = "problems";
      private static final String NUMBER_OF_TASKS = "tasks";
      private static final String NUMBER_OF_WARNINGS = "warnings";
      private static final String NUMBER_OF_INFOS = "infos";
      private static final String OPTION = "option";
      private static final String OPTIONS = "options";
      private static final String OUTPUT = "output";
      private static final String PACKAGE = "package";
      private static final String PATH = "path";
      private static final String PROBLEM_ARGUMENT = "argument";
      private static final String PROBLEM_ARGUMENT_VALUE = "value";
      private static final String PROBLEM_ARGUMENTS = "arguments";
      private static final String PROBLEM_CATEGORY_ID = "categoryID";
      private static final String ID = "id";
      private static final String PROBLEM_ID = "problemID";
      private static final String PROBLEM_LINE = "line";
      private static final String PROBLEM_OPTION_KEY = "optionKey";
      private static final String PROBLEM_MESSAGE = "message";
      private static final String PROBLEM_SEVERITY = "severity";
      private static final String PROBLEM_SOURCE_END = "charEnd";
      private static final String PROBLEM_SOURCE_START = "charStart";
      private static final String PROBLEM_SUMMARY = "problem_summary";
      private static final String PROBLEM_TAG = "problem";
      private static final String PROBLEMS = "problems";
      private static final String SOURCE = "source";
      private static final String SOURCE_CONTEXT = "source_context";
      private static final String SOURCE_END = "sourceEnd";
      private static final String SOURCE_START = "sourceStart";
      private static final String SOURCES = "sources";
      private static final String STATS = "stats";
      private static final String TASK = "task";
      private static final String TASKS = "tasks";
      private static final String TIME = "time";
      private static final String VALUE = "value";
      private static final String WARNING = "WARNING";
      private static final String INFO = "INFO";
      public static final int XML = 1;
      private static final String XML_DTD_DECLARATION = "<!DOCTYPE compiler PUBLIC \"-//Eclipse.org//DTD Eclipse JDT 3.2.006 Compiler//EN\" \"http://www.eclipse.org/jdt/core/compiler_32_006.dtd\">";

      static {
         try {
            Class c = IProblem.class;
            Field[] fields = c.getFields();
            int i = 0;

            for(int max = fields.length; i < max; ++i) {
               Field field = fields[i];
               if (field.getType().equals(Integer.TYPE)) {
                  Integer value = (Integer)field.get((Object)null);
                  int key2 = value & 4194303;
                  if (key2 == 0) {
                     key2 = Integer.MAX_VALUE;
                  }

                  FIELD_TABLE.put(key2, field.getName());
               }
            }
         } catch (SecurityException var7) {
            var7.printStackTrace();
         } catch (IllegalArgumentException var8) {
            var8.printStackTrace();
         } catch (IllegalAccessException var9) {
            var9.printStackTrace();
         }

      }

      public Logger(Main main, PrintWriter out, PrintWriter err) {
         this.out = out;
         this.err = err;
         this.parameters = new HashMap();
         this.main = main;
      }

      public String buildFileName(String outputPath, String relativeFileName) {
         char fileSeparatorChar = File.separatorChar;
         String fileSeparator = File.separator;
         outputPath = outputPath.replace('/', fileSeparatorChar);
         StringBuffer outDir = new StringBuffer(outputPath);
         if (!outputPath.endsWith(fileSeparator)) {
            outDir.append(fileSeparator);
         }

         StringTokenizer tokenizer = new StringTokenizer(relativeFileName, fileSeparator);

         String token;
         for(token = tokenizer.nextToken(); tokenizer.hasMoreTokens(); token = tokenizer.nextToken()) {
            outDir.append(token).append(fileSeparator);
         }

         return outDir.append(token).toString();
      }

      public void close() {
         if (this.log != null) {
            if ((this.tagBits & 1) != 0) {
               this.endTag("compiler");
               this.flush();
            }

            this.log.close();
         }

      }

      public void compiling() {
         this.printlnOut(this.main.bind("progress.compiling"));
      }

      private void endLoggingExtraProblems() {
         this.endTag("extra_problems");
      }

      private void endLoggingProblems() {
         this.endTag("problems");
      }

      public void endLoggingSource() {
         if ((this.tagBits & 1) != 0) {
            this.endTag("source");
         }

      }

      public void endLoggingSources() {
         if ((this.tagBits & 1) != 0) {
            this.endTag("sources");
         }

      }

      public void endLoggingTasks() {
         if ((this.tagBits & 1) != 0) {
            this.endTag("tasks");
         }

      }

      private void endTag(String name) {
         if (this.log != null) {
            ((GenericXMLWriter)this.log).endTag(name, true, true);
         }

      }

      private String errorReportSource(CategorizedProblem problem, char[] unitSource, int bits) {
         int startPosition = problem.getSourceStart();
         int endPosition = problem.getSourceEnd();
         if (unitSource == null && problem.getOriginatingFileName() != null) {
            try {
               unitSource = Util.getFileCharContent(new File(new String(problem.getOriginatingFileName())), (String)null);
            } catch (IOException var12) {
            }
         }

         int length;
         if (startPosition <= endPosition && (startPosition >= 0 || endPosition >= 0) && unitSource != null && (length = unitSource.length) != 0) {
            StringBuffer errorBuffer = new StringBuffer();
            if ((bits & 2) == 0) {
               errorBuffer.append(' ').append(Messages.bind(Messages.problem_atLine, (Object)String.valueOf(problem.getSourceLineNumber())));
               errorBuffer.append(Util.LINE_SEPARATOR);
            }

            errorBuffer.append('\t');

            char c;
            int begin;
            for(begin = startPosition >= length ? length - 1 : startPosition; begin > 0 && (c = unitSource[begin - 1]) != '\n' && c != '\r'; --begin) {
            }

            int end;
            for(end = endPosition >= length ? length - 1 : endPosition; end + 1 < length && (c = unitSource[end + 1]) != '\r' && c != '\n'; ++end) {
            }

            while((c = unitSource[begin]) == ' ' || c == '\t') {
               ++begin;
            }

            errorBuffer.append(unitSource, begin, end - begin + 1);
            errorBuffer.append(Util.LINE_SEPARATOR).append("\t");

            int i;
            for(i = begin; i < startPosition; ++i) {
               errorBuffer.append((char)(unitSource[i] == '\t' ? '\t' : ' '));
            }

            for(i = startPosition; i <= (endPosition >= length ? length - 1 : endPosition); ++i) {
               errorBuffer.append('^');
            }

            return errorBuffer.toString();
         } else {
            return Messages.problem_noSourceInformation;
         }
      }

      private void extractContext(CategorizedProblem problem, char[] unitSource) {
         int startPosition = problem.getSourceStart();
         int endPosition = problem.getSourceEnd();
         if (unitSource == null && problem.getOriginatingFileName() != null) {
            try {
               unitSource = Util.getFileCharContent(new File(new String(problem.getOriginatingFileName())), (String)null);
            } catch (IOException var10) {
            }
         }

         int length;
         if (startPosition <= endPosition && (startPosition >= 0 || endPosition >= 0) && unitSource != null && (length = unitSource.length) > 0 && endPosition <= length) {
            char c;
            int begin;
            for(begin = startPosition >= length ? length - 1 : startPosition; begin > 0 && (c = unitSource[begin - 1]) != '\n' && c != '\r'; --begin) {
            }

            int end;
            for(end = endPosition >= length ? length - 1 : endPosition; end + 1 < length && (c = unitSource[end + 1]) != '\r' && c != '\n'; ++end) {
            }

            while((c = unitSource[begin]) == ' ' || c == '\t') {
               ++begin;
            }

            while((c = unitSource[end]) == ' ' || c == '\t') {
               --end;
            }

            StringBuffer buffer = new StringBuffer();
            buffer.append(unitSource, begin, end - begin + 1);
            this.parameters.put("value", String.valueOf(buffer));
            this.parameters.put("sourceStart", Integer.toString(startPosition - begin));
            this.parameters.put("sourceEnd", Integer.toString(endPosition - begin));
            this.printTag("source_context", this.parameters, true, true);
         } else {
            this.parameters.put("value", Messages.problem_noSourceInformation);
            this.parameters.put("sourceStart", "-1");
            this.parameters.put("sourceEnd", "-1");
            this.printTag("source_context", this.parameters, true, true);
         }
      }

      public void flush() {
         this.out.flush();
         this.err.flush();
         if (this.log != null) {
            this.log.flush();
         }

      }

      private String getFieldName(int id) {
         int key2 = id & 4194303;
         if (key2 == 0) {
            key2 = Integer.MAX_VALUE;
         }

         return (String)FIELD_TABLE.get(key2);
      }

      private String getProblemOptionKey(int problemID) {
         int irritant = ProblemReporter.getIrritant(problemID);
         return CompilerOptions.optionKeyFromIrritant(irritant);
      }

      public void logAverage() {
         Arrays.sort(this.main.compilerStats);
         long lineCount = this.main.compilerStats[0].lineCount;
         int length = this.main.maxRepetition;
         long sum = 0L;
         long parseSum = 0L;
         long resolveSum = 0L;
         long analyzeSum = 0L;
         long generateSum = 0L;
         int i = 1;

         for(int max = length - 1; i < max; ++i) {
            CompilerStats stats = this.main.compilerStats[i];
            sum += stats.elapsedTime();
            parseSum += stats.parseTime;
            resolveSum += stats.resolveTime;
            analyzeSum += stats.analyzeTime;
            generateSum += stats.generateTime;
         }

         long time = sum / (long)(length - 2);
         long parseTime = parseSum / (long)(length - 2);
         long resolveTime = resolveSum / (long)(length - 2);
         long analyzeTime = analyzeSum / (long)(length - 2);
         long generateTime = generateSum / (long)(length - 2);
         this.printlnOut(this.main.bind("compile.averageTime", new String[]{String.valueOf(lineCount), String.valueOf(time), String.valueOf((double)((int)((double)lineCount * 10000.0 / (double)time)) / 10.0)}));
         if ((this.main.timing & 2) != 0) {
            this.printlnOut(this.main.bind("compile.detailedTime", new String[]{String.valueOf(parseTime), String.valueOf((double)((int)((double)parseTime * 1000.0 / (double)time)) / 10.0), String.valueOf(resolveTime), String.valueOf((double)((int)((double)resolveTime * 1000.0 / (double)time)) / 10.0), String.valueOf(analyzeTime), String.valueOf((double)((int)((double)analyzeTime * 1000.0 / (double)time)) / 10.0), String.valueOf(generateTime), String.valueOf((double)((int)((double)generateTime * 1000.0 / (double)time)) / 10.0)}));
         }

      }

      public void logClassFile(boolean generatePackagesStructure, String outputPath, String relativeFileName) {
         if ((this.tagBits & 1) != 0) {
            String fileName = null;
            if (generatePackagesStructure) {
               fileName = this.buildFileName(outputPath, relativeFileName);
            } else {
               char fileSeparatorChar = File.separatorChar;
               String fileSeparator = File.separator;
               outputPath = outputPath.replace('/', fileSeparatorChar);
               int indexOfPackageSeparator = relativeFileName.lastIndexOf(fileSeparatorChar);
               if (indexOfPackageSeparator == -1) {
                  if (outputPath.endsWith(fileSeparator)) {
                     fileName = outputPath + relativeFileName;
                  } else {
                     fileName = outputPath + fileSeparator + relativeFileName;
                  }
               } else {
                  int length = relativeFileName.length();
                  if (outputPath.endsWith(fileSeparator)) {
                     fileName = outputPath + relativeFileName.substring(indexOfPackageSeparator + 1, length);
                  } else {
                     fileName = outputPath + fileSeparator + relativeFileName.substring(indexOfPackageSeparator + 1, length);
                  }
               }
            }

            File f = new File(fileName);

            try {
               this.parameters.put("path", f.getCanonicalPath());
               this.printTag("classfile", this.parameters, true, true);
            } catch (IOException var9) {
               this.logNoClassFileCreated(outputPath, relativeFileName, var9);
            }
         }

      }

      public void logClasspath(FileSystem.Classpath[] classpaths) {
         if (classpaths != null) {
            if ((this.tagBits & 1) != 0) {
               int length = classpaths.length;
               if (length != 0) {
                  this.printTag("classpaths", (HashMap)null, true, false);

                  for(int i = 0; i < length; ++i) {
                     String classpath = classpaths[i].getPath();
                     this.parameters.put("path", classpath);
                     File f = new File(classpath);
                     String id = null;
                     if (f.isFile()) {
                        int kind = Util.archiveFormat(classpath);
                        switch (kind) {
                           case 0:
                              id = "JAR";
                              break;
                           default:
                              id = "FILE";
                        }
                     } else if (f.isDirectory()) {
                        id = "FOLDER";
                     }

                     if (id != null) {
                        this.parameters.put("id", id);
                        this.printTag("classpath", this.parameters, true, true);
                     }
                  }

                  this.endTag("classpaths");
               }
            }

         }
      }

      public void logCommandLineArguments(String[] commandLineArguments) {
         if (commandLineArguments != null) {
            if ((this.tagBits & 1) != 0) {
               int length = commandLineArguments.length;
               if (length != 0) {
                  this.printTag("command_line", (HashMap)null, true, false);

                  for(int i = 0; i < length; ++i) {
                     this.parameters.put("value", commandLineArguments[i]);
                     this.printTag("argument", this.parameters, true, true);
                  }

                  this.endTag("command_line");
               }
            }

         }
      }

      public void logException(Exception e) {
         StringWriter writer = new StringWriter();
         PrintWriter printWriter = new PrintWriter(writer);
         e.printStackTrace(printWriter);
         printWriter.flush();
         printWriter.close();
         String stackTrace = writer.toString();
         if ((this.tagBits & 1) != 0) {
            LineNumberReader reader = new LineNumberReader(new StringReader(stackTrace));
            int i = 0;
            StringBuffer buffer = new StringBuffer();
            String message = e.getMessage();
            if (message != null) {
               buffer.append(message).append(Util.LINE_SEPARATOR);
            }

            try {
               String line;
               while((line = reader.readLine()) != null && i < 4) {
                  buffer.append(line).append(Util.LINE_SEPARATOR);
                  ++i;
               }

               reader.close();
            } catch (IOException var10) {
            }

            message = buffer.toString();
            this.parameters.put("message", message);
            this.parameters.put("class", e.getClass());
            this.printTag("exception", this.parameters, true, true);
         }

         String message = e.getMessage();
         if (message == null) {
            this.printlnErr(stackTrace);
         } else {
            this.printlnErr(message);
         }

      }

      private void logExtraProblem(CategorizedProblem problem, int localErrorCount, int globalErrorCount) {
         char[] originatingFileName = problem.getOriginatingFileName();
         String fileName;
         if (originatingFileName == null) {
            fileName = problem.isError() ? "requestor.extraerror" : (problem.isInfo() ? "requestor.extrainfo" : "requestor.extrawarning");
            this.printErr(this.main.bind(fileName, Integer.toString(globalErrorCount)));
            this.printErr(" ");
            this.printlnErr(problem.getMessage());
         } else {
            fileName = new String(originatingFileName);
            String severity;
            String errorReportSource;
            if ((this.tagBits & 2) != 0) {
               severity = problem.isError() ? "output.emacs.error" : (problem.isInfo() ? "output.emacs.info" : "output.emacs.warning");
               errorReportSource = fileName + ":" + problem.getSourceLineNumber() + ": " + this.main.bind(severity) + ": " + problem.getMessage();
               this.printlnErr(errorReportSource);
               String errorReportSource = this.errorReportSource(problem, (char[])null, this.tagBits);
               this.printlnErr(errorReportSource);
            } else {
               if (localErrorCount == 0) {
                  this.printlnErr("----------");
               }

               severity = problem.isError() ? "requestor.error" : (problem.isInfo() ? "requestor.info" : "requestor.warning");
               this.printErr(this.main.bind(severity, Integer.toString(globalErrorCount), fileName));
               errorReportSource = this.errorReportSource(problem, (char[])null, 0);
               this.printlnErr(errorReportSource);
               this.printlnErr(problem.getMessage());
               this.printlnErr("----------");
            }
         }

      }

      public void loggingExtraProblems(Main currentMain) {
         ArrayList problems = currentMain.extraProblems;
         int count = problems.size();
         int localProblemCount = 0;
         if (count != 0) {
            int errors = 0;
            int warnings = 0;
            int infos = 0;

            int i;
            CategorizedProblem problem;
            for(i = 0; i < count; ++i) {
               problem = (CategorizedProblem)problems.get(i);
               if (problem != null) {
                  ++currentMain.globalProblemsCount;
                  this.logExtraProblem(problem, localProblemCount, currentMain.globalProblemsCount);
                  ++localProblemCount;
                  if (problem.isError()) {
                     ++errors;
                     ++currentMain.globalErrorsCount;
                  } else if (problem.isInfo()) {
                     ++currentMain.globalInfoCount;
                     ++infos;
                  } else {
                     ++currentMain.globalWarningsCount;
                     ++warnings;
                  }
               }
            }

            if ((this.tagBits & 1) != 0 && errors + warnings + infos != 0) {
               this.startLoggingExtraProblems(count);

               for(i = 0; i < count; ++i) {
                  problem = (CategorizedProblem)problems.get(i);
                  if (problem != null && problem.getID() != 536871362) {
                     this.logXmlExtraProblem(problem, localProblemCount, currentMain.globalProblemsCount);
                  }
               }

               this.endLoggingExtraProblems();
            }
         }

      }

      public void logUnavaibleAPT(String className) {
         if ((this.tagBits & 1) != 0) {
            this.parameters.put("message", this.main.bind("configure.unavailableAPT", className));
            this.printTag("error", this.parameters, true, true);
         }

         this.printlnErr(this.main.bind("configure.unavailableAPT", className));
      }

      public void logIncorrectVMVersionForAnnotationProcessing() {
         if ((this.tagBits & 1) != 0) {
            this.parameters.put("message", this.main.bind("configure.incorrectVMVersionforAPT"));
            this.printTag("error", this.parameters, true, true);
         }

         this.printlnErr(this.main.bind("configure.incorrectVMVersionforAPT"));
      }

      public void logNoClassFileCreated(String outputDir, String relativeFileName, IOException e) {
         if ((this.tagBits & 1) != 0) {
            this.parameters.put("message", this.main.bind("output.noClassFileCreated", new String[]{outputDir, relativeFileName, e.getMessage()}));
            this.printTag("error", this.parameters, true, true);
         }

         this.printlnErr(this.main.bind("output.noClassFileCreated", new String[]{outputDir, relativeFileName, e.getMessage()}));
      }

      public void logNumberOfClassFilesGenerated(int exportedClassFilesCounter) {
         if ((this.tagBits & 1) != 0) {
            this.parameters.put("value", exportedClassFilesCounter);
            this.printTag("number_of_classfiles", this.parameters, true, true);
         }

         if (exportedClassFilesCounter == 1) {
            this.printlnOut(this.main.bind("compile.oneClassFileGenerated"));
         } else {
            this.printlnOut(this.main.bind("compile.severalClassFilesGenerated", String.valueOf(exportedClassFilesCounter)));
         }

      }

      public void logOptions(Map options) {
         if ((this.tagBits & 1) != 0) {
            this.printTag("options", (HashMap)null, true, false);
            Set entriesSet = options.entrySet();
            Map.Entry[] entries = (Map.Entry[])entriesSet.toArray(new Map.Entry[entriesSet.size()]);
            Arrays.sort(entries, new Comparator() {
               public int compare(Map.Entry o1, Map.Entry o2) {
                  return ((String)o1.getKey()).compareTo((String)o2.getKey());
               }
            });
            int i = 0;

            for(int max = entries.length; i < max; ++i) {
               Map.Entry entry = entries[i];
               String key = (String)entry.getKey();
               this.parameters.put("key", key);
               this.parameters.put("value", entry.getValue());
               this.printTag("option", this.parameters, true, true);
            }

            this.endTag("options");
         }

      }

      public void logPendingError(String error) {
         if ((this.tagBits & 1) != 0) {
            this.parameters.put("message", error);
            this.printTag("error", this.parameters, true, true);
         }

         this.printlnErr(error);
      }

      public void logWarning(String message) {
         if ((this.tagBits & 1) != 0) {
            this.parameters.put("message", message);
            this.printTag("warning", this.parameters, true, true);
         }

         this.printlnOut(message);
      }

      private void logProblem(CategorizedProblem problem, int localErrorCount, int globalErrorCount, char[] unitSource) {
         if (problem instanceof DefaultProblem) {
            ((DefaultProblem)problem).reportError();
         }

         String severity;
         String errorReportSource;
         if ((this.tagBits & 2) != 0) {
            severity = problem.isError() ? "output.emacs.error" : (problem.isInfo() ? "output.emacs.info" : "output.emacs.warning");
            errorReportSource = new String(problem.getOriginatingFileName()) + ":" + problem.getSourceLineNumber() + ": " + this.main.bind(severity) + ": " + problem.getMessage();
            this.printlnErr(errorReportSource);
            String errorReportSource = this.errorReportSource(problem, unitSource, this.tagBits);
            if (errorReportSource.length() != 0) {
               this.printlnErr(errorReportSource);
            }
         } else {
            if (localErrorCount == 0) {
               this.printlnErr("----------");
            }

            severity = problem.isError() ? "requestor.error" : (problem.isInfo() ? "requestor.info" : "requestor.warning");
            this.printErr(this.main.bind(severity, Integer.toString(globalErrorCount), new String(problem.getOriginatingFileName())));

            try {
               errorReportSource = this.errorReportSource(problem, unitSource, 0);
               this.printlnErr(errorReportSource);
               this.printlnErr(problem.getMessage());
            } catch (Exception var8) {
               this.printlnErr(this.main.bind("requestor.notRetrieveErrorMessage", problem.toString()));
            }

            this.printlnErr("----------");
         }

      }

      public int logProblems(CategorizedProblem[] problems, char[] unitSource, Main currentMain) {
         int count = problems.length;
         int localErrorCount = 0;
         int localProblemCount = 0;
         if (count != 0) {
            int errors = 0;
            int warnings = 0;
            int infos = 0;
            int tasks = 0;

            int i;
            CategorizedProblem problem;
            for(i = 0; i < count; ++i) {
               problem = problems[i];
               if (problem != null) {
                  ++currentMain.globalProblemsCount;
                  this.logProblem(problem, localProblemCount, currentMain.globalProblemsCount, unitSource);
                  ++localProblemCount;
                  if (problem.isError()) {
                     ++localErrorCount;
                     ++errors;
                     ++currentMain.globalErrorsCount;
                  } else if (problem.getID() == 536871362) {
                     ++currentMain.globalTasksCount;
                     ++tasks;
                  } else if (problem.isInfo()) {
                     ++currentMain.globalInfoCount;
                     ++infos;
                  } else {
                     ++currentMain.globalWarningsCount;
                     ++warnings;
                  }
               }
            }

            if ((this.tagBits & 1) != 0) {
               if (errors + warnings + infos != 0) {
                  this.startLoggingProblems(errors, warnings, infos);

                  for(i = 0; i < count; ++i) {
                     problem = problems[i];
                     if (problem != null && problem.getID() != 536871362) {
                        this.logXmlProblem(problem, unitSource);
                     }
                  }

                  this.endLoggingProblems();
               }

               if (tasks != 0) {
                  this.startLoggingTasks(tasks);

                  for(i = 0; i < count; ++i) {
                     problem = problems[i];
                     if (problem != null && problem.getID() == 536871362) {
                        this.logXmlTask(problem, unitSource);
                     }
                  }

                  this.endLoggingTasks();
               }
            }
         }

         return localErrorCount;
      }

      public void logProblemsSummary(int globalProblemsCount, int globalErrorsCount, int globalWarningsCount, int globalInfoCount, int globalTasksCount) {
         if ((this.tagBits & 1) != 0) {
            this.parameters.put("problems", globalProblemsCount);
            this.parameters.put("errors", globalErrorsCount);
            this.parameters.put("warnings", globalWarningsCount);
            this.parameters.put("infos", globalInfoCount);
            this.parameters.put("tasks", globalTasksCount);
            this.printTag("problem_summary", this.parameters, true, true);
         }

         String errorMessage;
         if (globalProblemsCount == 1) {
            errorMessage = null;
            if (globalErrorsCount == 1) {
               errorMessage = this.main.bind("compile.oneError");
            } else if (globalInfoCount == 1) {
               errorMessage = this.main.bind("compile.oneInfo");
            } else {
               errorMessage = this.main.bind("compile.oneWarning");
            }

            this.printErr(this.main.bind("compile.oneProblem", errorMessage));
         } else {
            errorMessage = null;
            String warningMessage = null;
            String infoMessage = null;
            if (globalErrorsCount > 0) {
               if (globalErrorsCount == 1) {
                  errorMessage = this.main.bind("compile.oneError");
               } else {
                  errorMessage = this.main.bind("compile.severalErrors", String.valueOf(globalErrorsCount));
               }
            }

            int warningsNumber = globalWarningsCount + globalTasksCount;
            if (warningsNumber > 0) {
               if (warningsNumber == 1) {
                  warningMessage = this.main.bind("compile.oneWarning");
               } else {
                  warningMessage = this.main.bind("compile.severalWarnings", String.valueOf(warningsNumber));
               }
            }

            if (globalInfoCount == 1) {
               infoMessage = this.main.bind("compile.oneInfo");
            } else if (globalInfoCount > 1) {
               infoMessage = this.main.bind("compile.severalInfos", String.valueOf(warningsNumber));
            }

            if (globalProblemsCount != globalInfoCount && globalProblemsCount != globalErrorsCount && globalProblemsCount != globalWarningsCount) {
               if (globalInfoCount == 0) {
                  this.printErr(this.main.bind("compile.severalProblemsErrorsAndWarnings", new String[]{String.valueOf(globalProblemsCount), errorMessage, warningMessage}));
               } else {
                  if (errorMessage == null) {
                     errorMessage = this.main.bind("compile.severalErrors", String.valueOf(globalErrorsCount));
                  }

                  if (warningMessage == null) {
                     warningMessage = this.main.bind("compile.severalWarnings", String.valueOf(warningsNumber));
                  }

                  this.printErr(this.main.bind("compile.severalProblems", new String[]{String.valueOf(globalProblemsCount), errorMessage, warningMessage, infoMessage}));
               }
            } else {
               String msg = errorMessage != null ? errorMessage : (warningMessage != null ? warningMessage : infoMessage);
               this.printErr(this.main.bind("compile.severalProblemsErrorsOrWarnings", String.valueOf(globalProblemsCount), msg));
            }
         }

         if ((this.tagBits & 1) == 0) {
            this.printlnErr();
         }

      }

      public void logProgress() {
         this.printOut('.');
      }

      public void logRepetition(int i, int repetitions) {
         this.printlnOut(this.main.bind("compile.repetition", String.valueOf(i + 1), String.valueOf(repetitions)));
      }

      public void logTiming(CompilerStats compilerStats) {
         long time = compilerStats.elapsedTime();
         long lineCount = compilerStats.lineCount;
         if ((this.tagBits & 1) != 0) {
            this.parameters.put("value", time);
            this.printTag("time", this.parameters, true, true);
            this.parameters.put("value", lineCount);
            this.printTag("number_of_lines", this.parameters, true, true);
         }

         if (lineCount != 0L) {
            this.printlnOut(this.main.bind("compile.instantTime", new String[]{String.valueOf(lineCount), String.valueOf(time), String.valueOf((double)((int)((double)lineCount * 10000.0 / (double)time)) / 10.0)}));
         } else {
            this.printlnOut(this.main.bind("compile.totalTime", new String[]{String.valueOf(time)}));
         }

         if ((this.main.timing & 2) != 0) {
            this.printlnOut(this.main.bind("compile.detailedTime", new String[]{String.valueOf(compilerStats.parseTime), String.valueOf((double)((int)((double)compilerStats.parseTime * 1000.0 / (double)time)) / 10.0), String.valueOf(compilerStats.resolveTime), String.valueOf((double)((int)((double)compilerStats.resolveTime * 1000.0 / (double)time)) / 10.0), String.valueOf(compilerStats.analyzeTime), String.valueOf((double)((int)((double)compilerStats.analyzeTime * 1000.0 / (double)time)) / 10.0), String.valueOf(compilerStats.generateTime), String.valueOf((double)((int)((double)compilerStats.generateTime * 1000.0 / (double)time)) / 10.0)}));
         }

      }

      public void logUsage(String usage) {
         this.printlnOut(usage);
      }

      public void logVersion(boolean printToOut) {
         String version;
         if (this.log != null && (this.tagBits & 1) == 0) {
            version = this.main.bind("misc.version", new String[]{this.main.bind("compiler.name"), this.main.bind("compiler.version"), this.main.bind("compiler.copyright")});
            this.log.println("# " + version);
            if (printToOut) {
               this.out.println(version);
               this.out.flush();
            }
         } else if (printToOut) {
            version = this.main.bind("misc.version", new String[]{this.main.bind("compiler.name"), this.main.bind("compiler.version"), this.main.bind("compiler.copyright")});
            this.out.println(version);
            this.out.flush();
         }

      }

      public void logWrongJDK() {
         if ((this.tagBits & 1) != 0) {
            this.parameters.put("message", this.main.bind("configure.requiresJDK1.2orAbove"));
            this.printTag("ERROR", this.parameters, true, true);
         }

         this.printlnErr(this.main.bind("configure.requiresJDK1.2orAbove"));
      }

      private void logXmlExtraProblem(CategorizedProblem problem, int globalErrorCount, int localErrorCount) {
         int sourceStart = problem.getSourceStart();
         int sourceEnd = problem.getSourceEnd();
         boolean isError = problem.isError();
         this.parameters.put("severity", isError ? "ERROR" : (problem.isInfo() ? "INFO" : "WARNING"));
         this.parameters.put("line", problem.getSourceLineNumber());
         this.parameters.put("charStart", sourceStart);
         this.parameters.put("charEnd", sourceEnd);
         this.printTag("extra_problem", this.parameters, true, false);
         this.parameters.put("value", problem.getMessage());
         this.printTag("message", this.parameters, true, true);
         this.extractContext(problem, (char[])null);
         this.endTag("extra_problem");
      }

      private void logXmlProblem(CategorizedProblem problem, char[] unitSource) {
         int sourceStart = problem.getSourceStart();
         int sourceEnd = problem.getSourceEnd();
         int id = problem.getID();
         this.parameters.put("id", this.getFieldName(id));
         this.parameters.put("problemID", id);
         boolean isError = problem.isError();
         int severity = isError ? 1 : 0;
         this.parameters.put("severity", isError ? "ERROR" : (problem.isInfo() ? "INFO" : "WARNING"));
         this.parameters.put("line", problem.getSourceLineNumber());
         this.parameters.put("charStart", sourceStart);
         this.parameters.put("charEnd", sourceEnd);
         String problemOptionKey = this.getProblemOptionKey(id);
         if (problemOptionKey != null) {
            this.parameters.put("optionKey", problemOptionKey);
         }

         int categoryID = ProblemReporter.getProblemCategory(severity, id);
         this.parameters.put("categoryID", categoryID);
         this.printTag("problem", this.parameters, true, false);
         this.parameters.put("value", problem.getMessage());
         this.printTag("message", this.parameters, true, true);
         this.extractContext(problem, unitSource);
         String[] arguments = problem.getArguments();
         int length = arguments.length;
         if (length != 0) {
            this.printTag("arguments", (HashMap)null, true, false);

            for(int i = 0; i < length; ++i) {
               this.parameters.put("value", arguments[i]);
               this.printTag("argument", this.parameters, true, true);
            }

            this.endTag("arguments");
         }

         this.endTag("problem");
      }

      private void logXmlTask(CategorizedProblem problem, char[] unitSource) {
         this.parameters.put("line", problem.getSourceLineNumber());
         this.parameters.put("charStart", problem.getSourceStart());
         this.parameters.put("charEnd", problem.getSourceEnd());
         String problemOptionKey = this.getProblemOptionKey(problem.getID());
         if (problemOptionKey != null) {
            this.parameters.put("optionKey", problemOptionKey);
         }

         this.printTag("task", this.parameters, true, false);
         this.parameters.put("value", problem.getMessage());
         this.printTag("message", this.parameters, true, true);
         this.extractContext(problem, unitSource);
         this.endTag("task");
      }

      private void printErr(String s) {
         this.err.print(s);
         if ((this.tagBits & 1) == 0 && this.log != null) {
            this.log.print(s);
         }

      }

      private void printlnErr() {
         this.err.println();
         if ((this.tagBits & 1) == 0 && this.log != null) {
            this.log.println();
         }

      }

      private void printlnErr(String s) {
         this.err.println(s);
         if ((this.tagBits & 1) == 0 && this.log != null) {
            this.log.println(s);
         }

      }

      private void printlnOut(String s) {
         this.out.println(s);
         if ((this.tagBits & 1) == 0 && this.log != null) {
            this.log.println(s);
         }

      }

      public void printNewLine() {
         this.out.println();
      }

      private void printOut(char c) {
         this.out.print(c);
      }

      public void printStats() {
         boolean isTimed = (this.main.timing & 1) != 0;
         if ((this.tagBits & 1) != 0) {
            this.printTag("stats", (HashMap)null, true, false);
         }

         if (isTimed) {
            CompilerStats compilerStats = this.main.batchCompiler.stats;
            compilerStats.startTime = this.main.startTime;
            compilerStats.endTime = System.currentTimeMillis();
            this.logTiming(compilerStats);
         }

         if (this.main.globalProblemsCount > 0) {
            this.logProblemsSummary(this.main.globalProblemsCount, this.main.globalErrorsCount, this.main.globalWarningsCount, this.main.globalInfoCount, this.main.globalTasksCount);
         }

         if (this.main.exportedClassFilesCounter != 0 && (this.main.showProgress || isTimed || this.main.verbose)) {
            this.logNumberOfClassFilesGenerated(this.main.exportedClassFilesCounter);
         }

         if ((this.tagBits & 1) != 0) {
            this.endTag("stats");
         }

      }

      private void printTag(String name, HashMap params, boolean insertNewLine, boolean closeTag) {
         if (this.log != null) {
            ((GenericXMLWriter)this.log).printTag(name, this.parameters, true, insertNewLine, closeTag);
         }

         this.parameters.clear();
      }

      public void setEmacs() {
         this.tagBits |= 2;
      }

      public void setLog(String logFileName) {
         Date date = new Date();
         DateFormat dateFormat = DateFormat.getDateTimeInstance(3, 1, Locale.getDefault());

         try {
            int index = logFileName.lastIndexOf(46);
            if (index != -1) {
               if (logFileName.substring(index).toLowerCase().equals(".xml")) {
                  this.log = new GenericXMLWriter(new OutputStreamWriter(new FileOutputStream(logFileName, false), "UTF-8"), Util.LINE_SEPARATOR, true);
                  this.tagBits |= 1;
                  this.log.println("<!-- " + dateFormat.format(date) + " -->");
                  this.log.println("<!DOCTYPE compiler PUBLIC \"-//Eclipse.org//DTD Eclipse JDT 3.2.006 Compiler//EN\" \"http://www.eclipse.org/jdt/core/compiler_32_006.dtd\">");
                  this.parameters.put("name", this.main.bind("compiler.name"));
                  this.parameters.put("version", this.main.bind("compiler.version"));
                  this.parameters.put("copyright", this.main.bind("compiler.copyright"));
                  this.printTag("compiler", this.parameters, true, false);
               } else {
                  this.log = new PrintWriter(new FileOutputStream(logFileName, false));
                  this.log.println("# " + dateFormat.format(date));
               }
            } else {
               this.log = new PrintWriter(new FileOutputStream(logFileName, false));
               this.log.println("# " + dateFormat.format(date));
            }

         } catch (FileNotFoundException var5) {
            throw new IllegalArgumentException(this.main.bind("configure.cannotOpenLog", logFileName), var5);
         } catch (UnsupportedEncodingException var6) {
            throw new IllegalArgumentException(this.main.bind("configure.cannotOpenLogInvalidEncoding", logFileName), var6);
         }
      }

      private void startLoggingExtraProblems(int count) {
         this.parameters.put("problems", count);
         this.printTag("extra_problems", this.parameters, true, false);
      }

      private void startLoggingProblems(int errors, int warnings, int infos) {
         this.parameters.put("problems", errors + warnings);
         this.parameters.put("errors", errors);
         this.parameters.put("warnings", warnings);
         this.parameters.put("infos", infos);
         this.printTag("problems", this.parameters, true, false);
      }

      public void startLoggingSource(CompilationResult compilationResult) {
         if ((this.tagBits & 1) != 0) {
            ICompilationUnit compilationUnit = compilationResult.compilationUnit;
            if (compilationUnit != null) {
               char[] fileName = compilationUnit.getFileName();
               File f = new File(new String(fileName));
               if (fileName != null) {
                  this.parameters.put("path", f.getAbsolutePath());
               }

               char[][] packageName = compilationResult.packageName;
               if (packageName != null) {
                  this.parameters.put("package", new String(CharOperation.concatWith(packageName, File.separatorChar)));
               }

               CompilationUnit unit = (CompilationUnit)compilationUnit;
               String destinationPath = unit.destinationPath;
               if (destinationPath == null) {
                  destinationPath = this.main.destinationPath;
               }

               if (destinationPath != null && destinationPath != "none") {
                  if (File.separatorChar == '/') {
                     this.parameters.put("output", destinationPath);
                  } else {
                     this.parameters.put("output", destinationPath.replace('/', File.separatorChar));
                  }
               }
            }

            this.printTag("source", this.parameters, true, false);
         }

      }

      public void startLoggingSources() {
         if ((this.tagBits & 1) != 0) {
            this.printTag("sources", (HashMap)null, true, false);
         }

      }

      public void startLoggingTasks(int tasks) {
         if ((this.tagBits & 1) != 0) {
            this.parameters.put("tasks", tasks);
            this.printTag("tasks", this.parameters, true, false);
         }

      }
   }

   public static class ResourceBundleFactory {
      private static HashMap Cache = new HashMap();

      public static synchronized ResourceBundle getBundle(Locale locale) {
         ResourceBundle bundle = (ResourceBundle)Cache.get(locale);
         if (bundle == null) {
            bundle = ResourceBundle.getBundle("com.bea.core.repackaged.jdt.internal.compiler.batch.messages", locale);
            Cache.put(locale, bundle);
         }

         return bundle;
      }
   }
}
