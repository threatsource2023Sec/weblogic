package org.python.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.python.Version;
import org.python.core.CodeFlag;
import org.python.core.CompileMode;
import org.python.core.Options;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyException;
import org.python.core.PyFile;
import org.python.core.PyList;
import org.python.core.PyNullImporter;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyStringMap;
import org.python.core.PySystemState;
import org.python.core.imp;
import org.python.core.util.RelativeFile;
import org.python.modules._systemrestart;
import org.python.modules.posix.PosixModule;
import org.python.modules.thread.thread;

public class jython {
   private static final String PYTHON_CONSOLE_CLASS = "org.python.util.JLineConsole";
   private static final String COPYRIGHT = "Type \"help\", \"copyright\", \"credits\" or \"license\" for more information.";
   static final String usageHeader = "usage: jython [option] ... [-c cmd | -m mod | file | -] [arg] ...\n";
   private static final String usage;
   public static boolean shouldRestart;
   private static List validWarnActions;

   public static void runJar(String filename) {
      try {
         ZipFile zip = new ZipFile(filename);
         ZipEntry runit = zip.getEntry("__run__.py");
         if (runit == null) {
            throw Py.ValueError("jar file missing '__run__.py'");
         } else {
            PyStringMap locals = Py.newStringMap();
            int beginIndex;
            if ((beginIndex = filename.lastIndexOf(File.separator)) != -1) {
               filename = filename.substring(beginIndex + 1);
            }

            locals.__setitem__((String)"__name__", new PyString(filename));
            locals.__setitem__("zipfile", Py.java2py(zip));
            InputStream file = zip.getInputStream(runit);

            PyCode code;
            try {
               code = Py.compile(file, "__run__", CompileMode.exec);
            } finally {
               file.close();
            }

            Py.runCode(code, locals, locals);
         }
      } catch (IOException var11) {
         throw Py.IOError(var11);
      }
   }

   public static void main(String[] args) {
      do {
         shouldRestart = false;
         run(args);
      } while(shouldRestart);

   }

   private static List warnOptionsFromEnv() {
      ArrayList opts = new ArrayList();

      try {
         String envVar = System.getenv("PYTHONWARNINGS");
         if (envVar == null) {
            return opts;
         }

         String[] var2 = envVar.split(",");
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String opt = var2[var4];
            opt = opt.trim();
            if (opt.length() != 0) {
               opts.add(opt);
            }
         }
      } catch (SecurityException var6) {
      }

      return opts;
   }

   private static void addWarnings(List from, PyList to) {
      Iterator var2 = from.iterator();

      while(true) {
         label24:
         while(var2.hasNext()) {
            String opt = (String)var2.next();
            String action = opt.split(":")[0];
            Iterator var5 = validWarnActions.iterator();

            while(var5.hasNext()) {
               String validWarnAction = (String)var5.next();
               if (validWarnAction.startsWith(action)) {
                  if (opt.contains(":")) {
                     to.append(Py.newString(validWarnAction + opt.substring(opt.indexOf(":"))));
                  } else {
                     to.append(Py.newString(validWarnAction));
                  }
                  continue label24;
               }
            }

            System.err.println(String.format("Invalid -W option ignored: invalid action: '%s'", action));
         }

         return;
      }
   }

   private static void runModule(InteractiveConsole interp, String moduleName) {
      runModule(interp, moduleName, false);
   }

   private static void runModule(InteractiveConsole interp, String moduleName, boolean set_argv0) {
      try {
         PyObject runpy = imp.importName("runpy", true);
         PyObject runmodule = runpy.__findattr__("_run_module_as_main");
         runmodule.__call__((PyObject)Py.fileSystemEncode(moduleName), (PyObject)Py.newBoolean(set_argv0));
      } catch (Throwable var5) {
         Py.printException(var5);
         interp.cleanup();
         System.exit(-1);
      }

   }

   private static boolean runMainFromImporter(InteractiveConsole interp, String filename) {
      PyString argv0 = Py.fileSystemEncode(filename);
      PyObject importer = imp.getImporter(argv0);
      if (!(importer instanceof PyNullImporter)) {
         Py.getSystemState().path.insert(0, argv0);
         runModule(interp, "__main__", true);
         return true;
      } else {
         return false;
      }
   }

   public static void run(String[] args) {
      CommandLineOptions opts = new CommandLineOptions();
      if (!opts.parse(args)) {
         if (opts.version) {
            System.err.println("Jython " + Version.PY_VERSION);
            System.exit(0);
         }

         if (opts.help) {
            System.err.println(usage);
         } else if (!opts.runCommand && !opts.runModule) {
            System.err.print("usage: jython [option] ... [-c cmd | -m mod | file | -] [arg] ...\n");
            System.err.println("Try `jython -h' for more information.");
         }

         int exitcode = opts.help ? 0 : -1;
         System.exit(exitcode);
      }

      Properties preProperties = PySystemState.getBaseProperties();
      String pythonIoEncoding = getenv("PYTHONIOENCODING");
      if (pythonIoEncoding != null) {
         String[] spec = splitString(pythonIoEncoding, ':', 2);
         addDefault(preProperties, "python.io.encoding", spec[0]);
         addDefault(preProperties, "python.io.errors", spec[1]);
      }

      if (!opts.fixInteractive || opts.interactive) {
         opts.interactive = Py.isInteractive();
         if (opts.interactive) {
            addDefault(preProperties, "python.console", "org.python.util.JLineConsole");
         }
      }

      PySystemState.initialize(preProperties, opts.properties, opts.argv);
      PySystemState systemState = Py.getSystemState();
      PyList warnoptions = new PyList();
      addWarnings(opts.warnoptions, warnoptions);
      if (!Options.ignore_environment) {
         addWarnings(warnOptionsFromEnv(), warnoptions);
      }

      systemState.setWarnoptions(warnoptions);
      if (warnoptions.size() > 0) {
         imp.load("warnings");
      }

      if (!opts.interactive) {
         systemState.ps1 = systemState.ps2 = Py.EmptyString;
      }

      InteractiveConsole interp = new InteractiveConsole();
      if (opts.interactive && opts.notice && !opts.runModule) {
         System.err.println(InteractiveConsole.getDefaultBanner());
      }

      if (Py.importSiteIfSelected() && opts.interactive && opts.notice && !opts.runModule) {
         System.err.println("Type \"help\", \"copyright\", \"credits\" or \"license\" for more information.");
      }

      if (opts.division != null) {
         if ("old".equals(opts.division)) {
            Options.division_warning = 0;
         } else if ("warn".equals(opts.division)) {
            Options.division_warning = 1;
         } else if ("warnall".equals(opts.division)) {
            Options.division_warning = 2;
         } else if ("new".equals(opts.division)) {
            Options.Qnew = true;
            interp.cflags.setFlag(CodeFlag.CO_FUTURE_DIVISION);
         }
      }

      String encoding;
      if (opts.filename == null) {
         Py.getSystemState().path.insert(0, Py.EmptyString);
         if (opts.command != null) {
            try {
               interp.exec(opts.command);
            } catch (Throwable var22) {
               Py.printException(var22);
               System.exit(1);
            }
         }

         if (opts.moduleName != null) {
            runModule(interp, opts.moduleName);
            interp.cleanup();
            return;
         }
      } else {
         if (runMainFromImporter(interp, opts.filename)) {
            interp.cleanup();
            return;
         }

         try {
            encoding = (new File(opts.filename)).getCanonicalFile().getParent();
         } catch (IOException var27) {
            encoding = (new File(opts.filename)).getAbsoluteFile().getParent();
         }

         if (encoding == null) {
            encoding = "";
         }

         Py.getSystemState().path.insert(0, Py.fileSystemEncode(encoding));
         if (opts.jar) {
            try {
               runJar(opts.filename);
            } catch (Throwable var26) {
               Py.printException(var26);
               System.exit(-1);
            }
         } else if (opts.filename.equals("-")) {
            try {
               interp.globals.__setitem__((PyObject)(new PyString("__file__")), new PyString("<stdin>"));
               interp.execfile(System.in, "<stdin>");
            } catch (Throwable var25) {
               Py.printException(var25);
            }
         } else {
            try {
               label392: {
                  interp.globals.__setitem__((PyObject)(new PyString("__file__")), Py.fileSystemEncode(opts.filename));

                  FileInputStream file;
                  try {
                     file = new FileInputStream(new RelativeFile(opts.filename));
                  } catch (FileNotFoundException var24) {
                     throw Py.IOError((IOException)var24);
                  }

                  try {
                     boolean isInteractive = false;

                     try {
                        isInteractive = PosixModule.getPOSIX().isatty(file.getFD());
                     } catch (SecurityException var23) {
                     }

                     if (!isInteractive) {
                        interp.execfile(file, opts.filename);
                        break label392;
                     }

                     opts.interactive = true;
                     interp.interact((String)null, new PyFile(file));
                  } finally {
                     file.close();
                  }

                  return;
               }
            } catch (Throwable var29) {
               if (var29 instanceof PyException && ((PyException)var29).match(_systemrestart.SystemRestart)) {
                  shouldRestart = true;
                  shutdownInterpreter();
                  interp.cleanup();
                  Py.setSystemState(new PySystemState());
                  return;
               }

               Py.printException(var29);
               interp.cleanup();
               System.exit(-1);
            }
         }
      }

      if (opts.fixInteractive || opts.filename == null && opts.command == null) {
         encoding = Py.getConsole().getEncoding();

         try {
            interp.cflags.encoding = encoding;
            if (!opts.interactive) {
               interp._interact((String)null, (PyObject)null);
            } else {
               interp.interact((String)null, (PyObject)null);
            }
         } catch (Throwable var21) {
            Py.printException(var21);
         }
      }

      interp.cleanup();
   }

   public static void shutdownInterpreter() {
      thread.interruptAllThreads();
      Py.getSystemState()._systemRestart = true;

      try {
         imp.load("socket").__findattr__("_closeActiveSockets").__call__();
      } catch (PyException var1) {
      }

   }

   private static String[] splitString(String spec, char sep, int n) {
      String[] list = new String[n];
      int p = 0;
      int i = 0;

      int c;
      for(int L = spec.length(); p < L; p = c + 1) {
         c = spec.indexOf(sep, p);
         if (c < 0 || i >= n - 1) {
            c = L;
         }

         String s = spec.substring(p, c).trim();
         list[i++] = s.length() > 0 ? s : null;
      }

      return list;
   }

   private static boolean addDefault(Properties registry, String key, String value) {
      if (value != null && !registry.containsKey(key)) {
         registry.setProperty(key, value);
         return true;
      } else {
         return false;
      }
   }

   private static String getenv(String varname) {
      if (!Options.ignore_environment) {
         try {
            return System.getenv(varname);
         } catch (SecurityException var2) {
            Options.ignore_environment = true;
         }
      }

      return null;
   }

   static {
      usage = "usage: jython [option] ... [-c cmd | -m mod | file | -] [arg] ...\nOptions and arguments:\n-B       : don't write bytecode files on import\n-c cmd   : program passed in as string (terminates option list)\n-Dprop=v : Set the property `prop' to value `v'\n-E       : ignore environment variables (such as JYTHONPATH)\n-h       : print this help message and exit (also --help)\n-i       : inspect interactively after running script\n           and force prompts, even if stdin does not appear to be a terminal\n-jar jar : program read from __run__.py in jar file\n-m mod   : run library module as a script (terminates option list)\n-Q arg   : division options: -Qold (default), -Qwarn, -Qwarnall, -Qnew\n-s       : don't add user site directory to sys.path;\n-S       : don't imply 'import site' on initialization\n-u       : unbuffered binary stdout and stderr\n-v       : verbose (trace import statements)\n           can be supplied multiple times to increase verbosity\n-V       : print the Python version number and exit (also --version)\n-W arg   : warning control (arg is action:message:category:module:lineno)\n-3       : warn about Python 3.x incompatibilities that 2to3 cannot trivially fix\nfile     : program read from script file\n-        : program read from stdin (default; interactive mode if a tty)\narg ...  : arguments passed to program in sys.argv[1:]\n\nOther environment variables:\nJYTHONPATH: '" + File.pathSeparator + "'-separated list of directories prefixed to the default module\n" + "            search path.  The result is sys.path.\n" + "PYTHONIOENCODING: Encoding[:errors] used for stdin/stdout/stderr.";
      validWarnActions = Arrays.asList("error", "ignore", "always", "default", "module", "once");
   }
}
