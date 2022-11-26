package org.python.util;

import java.util.List;
import java.util.Properties;
import org.python.core.Options;

class CommandLineOptions {
   public String filename = null;
   public boolean jar;
   public boolean interactive;
   public boolean notice;
   public boolean runCommand;
   public boolean runModule;
   public boolean fixInteractive;
   public boolean help;
   public boolean version;
   public String[] argv;
   public Properties properties;
   public String command;
   public List warnoptions = Generic.list();
   public String encoding;
   public String division;
   public String moduleName;

   public CommandLineOptions() {
      this.jar = this.fixInteractive = false;
      this.interactive = this.notice = true;
      this.runModule = false;
      this.properties = new Properties();
      this.help = this.version = false;
   }

   public void setProperty(String key, String value) {
      this.properties.put(key, value);

      try {
         System.setProperty(key, value);
      } catch (SecurityException var4) {
      }

   }

   private boolean argumentExpected(String arg) {
      System.err.println("Argument expected for the " + arg + " option");
      return false;
   }

   public boolean parse(String[] args) {
      int index = 0;

      int i;
      int n;
      while(index < args.length && args[index].startsWith("-")) {
         String arg = args[index];
         if (!arg.equals("-h") && !arg.equals("-?") && !arg.equals("--help")) {
            if (!arg.equals("-V") && !arg.equals("--version")) {
               if (arg.equals("-")) {
                  if (!this.fixInteractive) {
                     this.interactive = false;
                  }

                  this.filename = "-";
               } else if (arg.equals("-i")) {
                  this.fixInteractive = true;
                  this.interactive = true;
               } else if (arg.equals("-jar")) {
                  this.jar = true;
                  if (!this.fixInteractive) {
                     this.interactive = false;
                  }
               } else if (arg.equals("-u")) {
                  Options.unbuffered = true;
               } else if (arg.equals("-v")) {
                  ++Options.verbose;
               } else if (arg.equals("-vv")) {
                  Options.verbose += 2;
               } else if (arg.equals("-vvv")) {
                  Options.verbose += 3;
               } else if (arg.equals("-s")) {
                  Options.no_user_site = true;
               } else if (arg.equals("-S")) {
                  Options.importSite = false;
               } else if (arg.equals("-B")) {
                  Options.dont_write_bytecode = true;
               } else {
                  if (arg.startsWith("-c")) {
                     this.runCommand = true;
                     if (arg.length() > 2) {
                        this.command = arg.substring(2);
                     } else {
                        if (index + 1 >= args.length) {
                           return this.argumentExpected(arg);
                        }

                        ++index;
                        this.command = args[index];
                     }

                     if (!this.fixInteractive) {
                        this.interactive = false;
                     }

                     ++index;
                     break;
                  }

                  if (arg.startsWith("-W")) {
                     if (arg.length() > 2) {
                        this.warnoptions.add(arg.substring(2));
                     } else {
                        if (index + 1 >= args.length) {
                           return this.argumentExpected(arg);
                        }

                        ++index;
                        this.warnoptions.add(args[index]);
                     }
                  } else if (arg.equals("-E")) {
                     Options.ignore_environment = true;
                  } else {
                     String opt;
                     if (arg.startsWith("-D")) {
                        opt = null;
                        String value = null;
                        int equals = arg.indexOf("=");
                        if (equals == -1) {
                           ++index;
                           String arg2 = args[index];
                           opt = arg.substring(2, arg.length());
                           value = arg2;
                        } else {
                           opt = arg.substring(2, equals);
                           value = arg.substring(equals + 1, arg.length());
                        }

                        this.setProperty(opt, value);
                     } else if (arg.startsWith("-Q")) {
                        if (arg.length() > 2) {
                           this.division = arg.substring(2);
                        } else {
                           ++index;
                           this.division = args[index];
                        }
                     } else {
                        if (arg.startsWith("-m")) {
                           this.runModule = true;
                           if (arg.length() > 2) {
                              this.moduleName = arg.substring(2);
                           } else {
                              if (index + 1 >= args.length) {
                                 return this.argumentExpected(arg);
                              }

                              ++index;
                              this.moduleName = args[index];
                           }

                           if (!this.fixInteractive) {
                              this.interactive = false;
                           }

                           ++index;
                           n = args.length - index + 1;
                           this.argv = new String[n];
                           this.argv[0] = this.moduleName;

                           for(i = 1; index < args.length; ++index) {
                              this.argv[i] = args[index];
                              ++i;
                           }

                           return true;
                        }

                        if (!arg.startsWith("-3")) {
                           opt = args[index];
                           if (opt.startsWith("--")) {
                              opt = opt.substring(2);
                           } else if (opt.startsWith("-")) {
                              opt = opt.substring(1);
                           }

                           System.err.println("Unknown option: " + opt);
                           return false;
                        }

                        Options.py3k_warning = true;
                     }
                  }
               }

               ++index;
               continue;
            }

            this.version = true;
            return false;
         }

         this.help = true;
         return false;
      }

      this.notice = this.interactive;
      if (this.filename == null && index < args.length && this.command == null) {
         this.filename = args[index++];
         if (!this.fixInteractive) {
            this.interactive = false;
         }

         this.notice = false;
      }

      if (this.command != null) {
         this.notice = false;
      }

      int n = args.length - index + 1;
      n = -1;

      for(i = index; i < args.length; ++i) {
         if (args[i].startsWith("-J-Dcpython_cmd=")) {
            n = i;
            System.setProperty("cpython_cmd", args[i].substring(16));
            --n;
            break;
         }
      }

      this.argv = new String[n];
      if (this.filename != null) {
         this.argv[0] = this.filename;
      } else if (this.command != null) {
         this.argv[0] = "-c";
      } else {
         this.argv[0] = "";
      }

      if (n == -1) {
         for(i = 1; i < n; ++index) {
            this.argv[i] = args[index];
            ++i;
         }
      } else {
         for(i = 1; i < n; ++index) {
            if (index == n) {
               ++index;
            }

            this.argv[i] = args[index];
            ++i;
         }
      }

      return true;
   }
}
