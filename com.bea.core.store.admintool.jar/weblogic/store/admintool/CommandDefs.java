package weblogic.store.admintool;

import java.util.ArrayList;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

public class CommandDefs {
   static final String PARAM_PREFIX = "-";
   static final String XML_SUFFIX = ".xml";
   static final String TAB = "  ";
   static final String[] HELP_PREAMBLE = new String[]{"", "A store can be backed by a file system ('file store')", "a JDBC fronted database ('JDBC store'), or replicated", "memory ('replicated store' - an Exalogic-only feature).", "", "JDBC and file stores share the same set of commands. Most", "of them work in terms of store names and some also", "work in terms of connection names. Connections are", "logical groups of records within stores. For example,", "the JMS and JTA subsystems persist their respective", "records in different connections in the same store.", "To administer a JDBC or file store, it must be opened first", "before most other operations can be performed on it.", "", "Replicated stores have a different set of commands than", "JDBC and file stores.  Replicated store commands may start", "with 'rs' and/or contain the abbreviation 'RS' in their", "help text. To administer a replicated store, you must first", "use the rsattach command.  See also 'rshelp'.", ""};

   static CommandType getCommandType(String arg) {
      return arg == null ? null : (CommandType)CommandDefs.CommandType.commandMap.get(arg.toUpperCase(Locale.ENGLISH));
   }

   static CommandType[] getAllCommandTypes() {
      return (CommandType[])((CommandType[])CommandDefs.CommandType.commandMap.values().toArray(new CommandType[CommandDefs.CommandType.commandMap.size()]));
   }

   static CommandType[] getAllRsCommandTypes() {
      return (CommandType[])((CommandType[])CommandDefs.CommandType.rsCommandMap.values().toArray(new CommandType[CommandDefs.CommandType.rsCommandMap.size()]));
   }

   static Command getCommand(StoreAdmin sa, String[] args) {
      Interpreter ip = sa.getInterpreter();
      if (args != null && args.length != 0) {
         CommandType ct = null;
         if ((ct = getCommandType(args[0])) == null || ct.isAdvanced() && !sa.getAdvancedMode()) {
            ip.error("Unrecognized command : " + args[0]);
            return null;
         } else {
            ArrayList names = new ArrayList();
            if (!ct.validate(ip, args, names)) {
               if (sa.getJavaMode()) {
                  ct.usage(ip, sa.getAdvancedMode());
               }

               return null;
            } else {
               String[] optValues = (String[])((String[])names.toArray(new String[names.size()]));
               Command c = null;

               try {
                  c = (Command)ct.execClass.newInstance();
               } catch (Exception var8) {
                  ip.fatal("Instantiating processor for command " + args[0], var8);
                  return null;
               }

               c.sa = sa;
               c.ip = sa.getInterpreter();
               c.cmdType = ct;
               c.args = new String[args.length];
               System.arraycopy(args, 0, c.args, 0, args.length);
               if (optValues != null && optValues.length > 0) {
                  c.optValues = new String[optValues.length];
                  System.arraycopy(optValues, 0, c.optValues, 0, optValues.length);
               }

               return c;
            }
         }
      } else {
         ip.fatal("Internal error: Invalid null parameter list");
         return null;
      }
   }

   abstract static class Command implements Runnable {
      static final String TAB = "  ";
      CommandType cmdType;
      String[] args;
      String[] optValues;
      StoreAdmin sa;
      Interpreter ip;

      String getParamVal(CommandParam p) {
         return Interpreter.getParamArg(p.opt(), this.args, p.optCount() > 0);
      }

      String[] getOptionalValues() {
         return this.optValues;
      }
   }

   public static class CommandParam {
      public static final CommandParam STORE = new CommandParam("store", 1);
      public static final CommandParam FROM = new CommandParam("from", 1);
      public static final CommandParam TO = new CommandParam("to", 1);
      public static final CommandParam CONNECTION = new CommandParam("conn", 1);
      public static final CommandParam OUTFILE = new CommandParam("out", 1);
      public static final CommandParam DIR = new CommandParam("dir", 1);
      public static final CommandParam TEMPDIR = new CommandParam("tempdir", 1);
      public static final CommandParam ON = new CommandParam("on", 0);
      public static final CommandParam OFF = new CommandParam("off", 0);
      public static final CommandParam CREATE = new CommandParam("create", 0);
      public static final CommandParam DEEP = new CommandParam("deep", 0);
      public static final CommandParam OVERWRITE = new CommandParam("overwrite", 0);
      public static final CommandParam ALL = new CommandParam("all", 0);
      public static final CommandParam URL = new CommandParam("url", 1);
      public static final CommandParam PREFIX = new CommandParam("prefix", 1);
      public static final CommandParam DRIVER = new CommandParam("driver", 1);
      public static final CommandParam USER = new CommandParam("user", 1);
      public static final CommandParam PASSWORD = new CommandParam("password", 1);
      public static final CommandParam PROPFILE = new CommandParam("propfile", 1);
      public static final CommandParam DDL = new CommandParam("ddl", 1);
      public static final CommandParam LOCALINDEX = new CommandParam("localindex", 1);
      public static final CommandParam TRACE = new CommandParam("trace", 1);
      public static final CommandParam DAEMON = new CommandParam("daemon", 1);
      public static final CommandParam NAME = new CommandParam("name", 1);
      public static final CommandParam SORT = new CommandParam("sort", 1);
      public static final CommandParam FORCE = new CommandParam("force", 0);
      public static final CommandParam SAFE = new CommandParam("safe", 0);
      public static final CommandParam FORMAT = new CommandParam("format", 1);
      private final String prefix;
      private final int optCount;

      private CommandParam(String prefix, int count) {
         this.prefix = prefix;
         this.optCount = count;
      }

      String opt() {
         return "-" + this.prefix;
      }

      int optCount() {
         return this.optCount;
      }

      public String toString() {
         return this.prefix;
      }

      public String asString(String value) {
         return " " + this.opt() + (this.optCount() > 0 ? " " + value : "");
      }
   }

   public static class CommandType {
      static final SortedMap commandMap = new TreeMap();
      static final SortedMap rsCommandMap = new TreeMap();
      static final int FLAG_HIDDEN = 1;
      static final int FLAG_ADVANCED = 2;
      public static final CommandType OPENFILE;
      public static CommandType OPENJDBC;
      public static final CommandType CLOSE;
      public static CommandType LIST;
      public static CommandType DUMP;
      public static final CommandType COPY;
      public static CommandType DELETE;
      public static CommandType VERBOSE;
      public static CommandType OPTS;
      public static CommandType HELP;
      public static CommandType QUIT;
      public static CommandType COMPACT;
      public static CommandType ADVANCED;
      public static CommandType RSATTACH;
      public static CommandType RSDETATCH;
      public static CommandType RSDAEMONSLS;
      public static CommandType RSDAEMONSHUTDOWN;
      public static CommandType RSREGIONSLS;
      public static CommandType RSREGIONSRM;
      public static CommandType RSHELP;
      private final String command;
      private final CommandParam[] mandParams;
      private final CommandParam[] optParams;
      private final boolean allowNonParams;
      private final String summary;
      private final String usage;
      private final String[] description;
      private final Class execClass;
      private final long flags;

      private CommandType(String command, CommandParam[] m, CommandParam[] o, boolean allowNonParams, String summary, String usage, String[] description, Class execClass, long flags) {
         this.command = command;
         this.mandParams = m != null ? m : new CommandParam[0];
         this.optParams = o != null ? o : new CommandParam[0];
         this.allowNonParams = allowNonParams;
         this.summary = summary != null ? summary : "";
         this.usage = usage != null ? usage : "";
         this.description = description != null ? description : new String[0];
         this.execClass = execClass;
         this.flags = flags;
         commandMap.put(command.toUpperCase(Locale.ENGLISH), this);
         if (command.toUpperCase(Locale.ENGLISH).substring(0, 2).equalsIgnoreCase("RS") || command.toUpperCase(Locale.ENGLISH).equalsIgnoreCase("LSD") || command.toUpperCase(Locale.ENGLISH).equalsIgnoreCase("SHUTDOWN") || command.toUpperCase(Locale.ENGLISH).equalsIgnoreCase("LSR") || command.toUpperCase(Locale.ENGLISH).equalsIgnoreCase("RMR")) {
            rsCommandMap.put(command.toUpperCase(Locale.ENGLISH), this);
         }

      }

      public String toString() {
         return this.command;
      }

      boolean isHidden() {
         return (this.flags & 1L) != 0L;
      }

      boolean isAdvanced() {
         return (this.flags & 2L) != 0L;
      }

      void describe(Interpreter ip, boolean advancedMode) {
         if (!this.isHidden()) {
            if (!this.isAdvanced() || advancedMode) {
               ip.message("  " + String.format("%8s", this.command) + " : " + "  " + this.summary);
            }
         }
      }

      void usage(Interpreter ip, boolean advancedMode) {
         if (!this.isHidden()) {
            if (!this.isAdvanced() || advancedMode) {
               ip.message("  Usage:");
               ip.message("    " + this.usage);
            }
         }
      }

      void manPage(Interpreter ip, boolean advancedMode) {
         if (!this.isHidden()) {
            if (!this.isAdvanced() || advancedMode) {
               ip.message("  Command:");
               ip.message("    " + this.command);
               ip.message("  Summary:");
               ip.message("    " + this.summary);
               this.usage(ip, advancedMode);
               ip.message("  Description:");

               for(int i = 0; i < this.description.length; ++i) {
                  ip.message("    " + this.description[i]);
               }

            }
         }
      }

      boolean validate(Interpreter ip, String[] args, ArrayList optValues) {
         if (args != null && args.length != 0 && args[0] != null) {
            if (!args[0].equalsIgnoreCase(this.command)) {
               ip.error("Internal error: Mismatched command : " + this.command + ":" + args[0]);
               return false;
            } else {
               String[] largs = new String[args.length];
               System.arraycopy(args, 0, largs, 0, args.length);
               largs[0] = null;
               if (!this.validateInternal(ip, largs, this.mandParams, true)) {
                  return false;
               } else if (!this.validateInternal(ip, largs, this.optParams, false)) {
                  return false;
               } else {
                  boolean checkInvalidParams = false;
                  int i;
                  String s;
                  if (this.allowNonParams) {
                     for(i = 0; i < largs.length; ++i) {
                        s = largs[i];
                        if (s != null) {
                           if (!s.startsWith("-")) {
                              optValues.add(s);
                           } else {
                              checkInvalidParams = true;
                           }
                        }
                     }

                     if (!checkInvalidParams) {
                        return true;
                     } else {
                        return false;
                     }
                  } else {
                     for(i = 0; i < largs.length; ++i) {
                        s = largs[i];
                        if (s != null) {
                           ip.error("Invalid parameter or argument : " + s);
                           return false;
                        }
                     }

                     return true;
                  }
               }
            }
         } else {
            ip.error("Internal error: Invalid null args for " + this.command);
            return false;
         }
      }

      private boolean validateInternal(Interpreter ip, String[] largs, CommandParam[] params, boolean isMandatory) {
         for(int index = 0; index < params.length; ++index) {
            CommandParam p = params[index];
            boolean foundValid = false;

            for(int i = 0; i < largs.length; ++i) {
               if (p.opt().equals(largs[i])) {
                  if (i + p.optCount > largs.length - 1) {
                     ip.error("Invalid arg count for " + p);
                     return false;
                  }

                  for(int j = 0; j <= p.optCount; ++j) {
                     if (largs[i + j] == null) {
                        ip.error("Invalid null arg for parameter" + p);
                        return false;
                     }

                     largs[i + j] = null;
                  }

                  foundValid = true;
                  break;
               }
            }

            if (!foundValid && isMandatory) {
               ip.error("Missing mandatory parameter : " + p);
               return false;
            }
         }

         return true;
      }

      static {
         OPENFILE = new CommandType("openfile", new CommandParam[]{CommandDefs.CommandParam.STORE}, new CommandParam[]{CommandDefs.CommandParam.CREATE, CommandDefs.CommandParam.DIR}, false, "opens or creates a filestore for further operations", "openfile -store storename [-dir storedir] [-create]", new String[]{"#Note: A list of valid pre-existing storenames in a ", "  particular dir can be obtained by using the", "  'list' command with -dir option.", "", "openfile -store store1 #opens a pre-existing filestore", "  of name store1 in the current directory", "openfile -store store1 -dir c:/tmp/store #opens a", "  a pre-existing filestore of name store1 under /tmp/store", "openfile -store store1 -dir c:/tmp/store -create #creates", "  a new filestore with name store1 under /tmp/store"}, CommandImpls.CommandOpenFileStore.class, 0L);
         OPENJDBC = new CommandType("openjdbc", new CommandParam[]{CommandDefs.CommandParam.STORE}, new CommandParam[]{CommandDefs.CommandParam.PREFIX, CommandDefs.CommandParam.URL, CommandDefs.CommandParam.DRIVER, CommandDefs.CommandParam.PASSWORD, CommandDefs.CommandParam.PROPFILE, CommandDefs.CommandParam.USER, CommandDefs.CommandParam.DDL}, false, "opens a jdbc store for further operations", "openjdbc -store storename [-driver jdbcDriverClass] [-url dbUrl] [-prefix dbTableNamePrefix] [-user username] [-password pwd] [-propfile jdbcPropertyFile] [-ddl ddlfile]", new String[]{"#Note: Any of the following attributes:", "  [ driver, url, prefix, user, password, ddl ]", "  can also be specified in the jdbcPropertyFile as", "  properties of the same name. For example, instead of", "  '-user u' in the command-line, the property file", "  specified via -propfile could contain 'user=u'. If", "  an attribute is specified both in command-line and", "  in the properties file, the command-line value takes", "  precedence.", "  ", "  The following attributes must be specified in either", "  the command-line or the properties file:", "  [ driver, url ]", "  ", "  The storename is the configured name of the JDBC store", "  in the server.", "  ", "openjdbc -store s1 -driver com.bea.xxDbDriver -url dbUrl", "openjdbc -store s1 -driver com.bea.xxDbDriver -url dbUrl -user u -password pwd", "openjdbc -store s1 -propfile props.txt #props.txt must", "  contain values for url and driver", "openjdbc -store s1 -propfile props.txt -password pwd", "  #props.txt must contain values for url and driver", "openjdbc -store s1 -propfile props.txt -driver com.bea.xxDbDriver", "  #props.txt must contain value for url"}, CommandImpls.CommandOpenJDBCStore.class, 0L);
         CLOSE = new CommandType("close", new CommandParam[]{CommandDefs.CommandParam.STORE}, (CommandParam[])null, false, "closes a previously opened store", "close -store storename", new String[]{"close -store store1"}, CommandImpls.CommandClose.class, 0L);
         LIST = new CommandType("list", (CommandParam[])null, new CommandParam[]{CommandDefs.CommandParam.STORE, CommandDefs.CommandParam.DIR}, false, "lists store names, open stores, or connections in a store", "list [-store storename|-dir dir]", new String[]{"list #lists all opened stores by storename", "list -store store1 #lists all connections in store1", "list -dir dir1 #lists all storenames found in dir1"}, CommandImpls.CommandList.class, 0L);
         DUMP = new CommandType("dump", new CommandParam[]{CommandDefs.CommandParam.STORE, CommandDefs.CommandParam.OUTFILE}, new CommandParam[]{CommandDefs.CommandParam.CONNECTION, CommandDefs.CommandParam.DEEP}, false, "dumps store or connection contents in human-readable format", "dump -store storename -out outfile [-conn connName] [-deep]", new String[]{"dump -store store1 -out d:/tmp/x #dumps contents of", "  store1 to d:/tmp/x.xml excluding actual record contents", "dump -store store1 -out d:/tmp/x -deep #dumps contents of", "  store1 to d:/tmp/x.xml including record contents", "  as hexdump", "dump -store store1 -out d:/tmp/x.xml -conn ABCD #dumps", "  contents in connection 'ABCD' of store1 to", "  d:/tmp/x.xml. 'ABCD' should be in 'list -store store1", "WritePolicy in generated dump is the policy that dump tool ", "itself uses when it opens the store"}, CommandImpls.CommandDump.class, 0L);
         COPY = new CommandType("copy", new CommandParam[]{CommandDefs.CommandParam.FROM, CommandDefs.CommandParam.TO}, new CommandParam[]{CommandDefs.CommandParam.CONNECTION, CommandDefs.CommandParam.OVERWRITE}, false, "copies connections across stores", "copy -from storename1 -to storename2 [-conn connName] [-overwrite]", new String[]{"copy -from store1 -to store2 #copies all connections from", "  store1 to store2. Fails if store2 has same connections.", "copy -from store1 -to store2 -overwrite #copies connections", "  from store1 to store2, overwriting connections in store2", "  if necessary.", "copy -from store1 -to store2 -conn ABCD", "  #copies connection 'ABCD' from store1", "  to store2. 'ABCD' should be in 'list -store store1'."}, CommandImpls.CommandCopy.class, 2L);
         DELETE = new CommandType("delete", new CommandParam[]{CommandDefs.CommandParam.STORE}, new CommandParam[]{CommandDefs.CommandParam.CONNECTION, CommandDefs.CommandParam.ALL}, false, "deletes connections in a previously opened store", "delete -store storename {-conn connName|-all}", new String[]{"delete -store store1 -conn ABCD #deletes the connection", "  'ABCD' and all its records from store1. 'ABCD'", "  should be in 'list -store store1'.", "delete -store store1 -all #delete all connections", "  in store1."}, CommandImpls.CommandDelete.class, 2L);
         VERBOSE = new CommandType("verbose", (CommandParam[])null, new CommandParam[]{CommandDefs.CommandParam.ON, CommandDefs.CommandParam.OFF}, false, "controls display of additional information such as stack traces", "verbose [-on|-off]", new String[]{"verbose #displays current verbose setting", "verbose -on #turn on verbose mode", "verbose -off #turn off verbose mode"}, CommandImpls.CommandVerbose.class, 0L);
         OPTS = new CommandType("opts", (CommandParam[])null, (CommandParam[])null, false, "lists invocation options for this tool", "opts", (String[])null, CommandImpls.CommandOpts.class, 0L);
         HELP = new CommandType("help", (CommandParam[])null, (CommandParam[])null, true, "displays available commands and usage", "help [command]", new String[]{"help #list all available commands", "help verbose #list info on the 'verbose' command"}, CommandImpls.CommandHelp.class, 0L);
         QUIT = new CommandType("quit", (CommandParam[])null, (CommandParam[])null, false, "ends the admin session", "quit", new String[]{"quit #ends the admin session"}, CommandImpls.CommandQuit.class, 0L);
         COMPACT = new CommandType("compact", new CommandParam[]{CommandDefs.CommandParam.DIR}, new CommandParam[]{CommandDefs.CommandParam.TEMPDIR}, false, "compacts the space occupied by a filestore", "compact -dir storedir [-tempdir tempdir]", new String[]{"compact -dir c:/tmp/store -tempdir c:/tmp #tempdir should", "  have at least enough extra space as dir and should not", "  be under dir. None of the stores that have files in dir", "  should be open. The newly compacted store files will be", "  in dir on success. A new, uniquely named directory will", "  be left under tempdir containing the original", "  uncompacted store files.", "compact -dir c:/tmp/store #Same as above with tempdir", "  defaulting to the system tmpdir.", "", "Note: This operation will not delete the original store", "  files even on success"}, CommandImpls.CommandCompact.class, 0L);
         ADVANCED = new CommandType("advanced", (CommandParam[])null, new CommandParam[]{CommandDefs.CommandParam.ON, CommandDefs.CommandParam.OFF}, false, "", "", new String[0], CommandImpls.CommandAdvanced.class, 1L);
         RSATTACH = new CommandType("rsattach", (CommandParam[])null, new CommandParam[]{CommandDefs.CommandParam.DIR, CommandDefs.CommandParam.LOCALINDEX}, false, "attach to a RS Daemon Cluster", "rsattach [-dir dir] [-localindex num]", new String[]{"To administer a replicated store, you must first", "attach to a running RS Daemon Cluster using rsattach.", "", "The optional \"-dir\" parameter (default './') is the", "relative or absolute path of the RS Daemon Cluster's", "NFS mounted 'Global Directory'.  This directory must", "contain an rs_daemons.cfg file at its root.", "", "The optional \"-localindex\" parameter (default '0')", "specifies which particular local Daemon to attach to", "when the rs_daemons.cfg file has multiple Daemon", "entries configured to run on the current node.", "The local Daemon is chosen via the formula:", "((localindex) modulo (number-of-local-daemons)).", "When 0, it always resolves to the first-most rs_daemons.cfg", "Daemon entry that has an address on the current node.", "", "When an rsattach succeeds, the command prompt will", "change to include '[RS]'.", "", "Example:", "  rsattach -dir /mynfsv4mount/myrsglobaldir", ""}, RSCommandImpls.CommandRsAttach.class, 0L);
         RSDETATCH = new CommandType("rsdetach", (CommandParam[])null, (CommandParam[])null, false, "detach from a RS Daemon Cluster", "rsdetach", new String[]{"Use \"rsdetach\" to detach from a RS Daemon Cluster.", "", "When an rsdetach succeeds, the command prompt will", "change so that it no longer includes '[RS]'.", ""}, RSCommandImpls.CommandRsDetach.class, 0L);
         RSDAEMONSLS = new CommandType("lsd", (CommandParam[])null, new CommandParam[]{CommandDefs.CommandParam.DAEMON, CommandDefs.CommandParam.SORT, CommandDefs.CommandParam.FORMAT}, false, "list Daemon(s) in the attached RS Daemon Cluster", "lsd [-daemon  all|local|n[,n]*|n-n]] (default all)\n        [-sort name|time|size] (default name)\n        [-format table|record] (default table)", new String[]{"The \"lsd\" command prints information about the", "daemons in the current attached Daemon Cluster (see rsattach).", "", " [-daemon all|local|n[,n]*|n-n] (default all)", "     'all'       - list all daemons", "     'local'     - list attached daemon", "     n[,n]*|n-n  - list daemons with the given Daemon numbers", "", " [-sort name|time|size] (default name)", "     'name'      - sorts by daemon number", "     'time'      - sorts from newest to oldest", "     'size'      - sorts from smallest to largest memory usage.", "", " [-format table|record] (default table)", "     'table'     - output in table format", "     'record'    - output in record format", "  The record format includes more information than table.", "", "Example:", "  lsd -daemon local", ""}, RSCommandImpls.CommandRsDaemonsLs.class, 0L);
         RSDAEMONSHUTDOWN = new CommandType("shutdown", (CommandParam[])null, new CommandParam[]{CommandDefs.CommandParam.DAEMON, CommandDefs.CommandParam.FORCE, CommandDefs.CommandParam.SAFE}, false, "shutdown Daemon(s) in the attached RS Daemon Cluster", "shutdown -daemon all|local|n[,n]*|n-n  [-force|-safe]", new String[]{"The \"shutdown\" command shuts down the specified daemon(s).", "", " -daemon all|local|n[,n]*|n-n (required parameter)", "     'all'      - shutdown all daemons", "     'local'    - shutdown attached daemon", "     n[,n]*|n-n - shutdown daemons with the given Daemon numbers", "    (Use the 'lsd' command to discover Daemon numbers.)", "", " [-force|-safe] (default is -safe)", "", "   You can specify either '-force' or '-safe' to control", "   whether the shutdown command guards against data loss.", "", "   '-safe', the default, causes the affected Daemon(s) to", "   try migrate their inactive region copies to other Daemon(s)", "   that have enough memory to host the migrated regions.", "   This ensures that the affected regions continue to have", "   at least two copies in the Daemon cluster.", "   If there are no other Daemons that have enough memory", "   for the migrated regions, if any regions are active, or", "   the Daemon is the last Daemon in the cluster, then a", "   safe shutdown command will fail, and the Daemon will", "   keep running.", "", "   The '-force' option does not migrate", "   any regions, and causes the affected Daemon to shutdown", "   regardless of whether a region is active.", "", "   To make sure that a region is inactive, the corresponding", "   WebLogic Server Replicated Store must be shutdown or closed.", "", "If an admin session is attached to a Daemon that shuts down,", "it will implicitly close and detach.", "", "Examples:", "  shutdown -daemon local", ""}, RSCommandImpls.CommandRsDaemonsShutdown.class, 0L);
         RSREGIONSLS = new CommandType("lsr", (CommandParam[])null, new CommandParam[]{CommandDefs.CommandParam.NAME, CommandDefs.CommandParam.SORT, CommandDefs.CommandParam.DAEMON, CommandDefs.CommandParam.FORMAT}, true, "lists the regions in the attached RS Daemon Cluster", "lsr [ -daemon all|local|n[,n]*|n-n ] (default 'all')\n        [-sort name|time|size] (default 'name')\n        [-format table|record] (default 'table')\n        [name-expression]      (default '*')", new String[]{"The \"lsr\" command prints information about the", "regions in the current attached Daemon Cluster (see rsattach).", "", " [-daemon all|local|n[,n]*|n-n] (default all)", "     all         - list regions for all daemons", "     local       - list regions on the attached daemon", "     n[,n]*|n-n  - list regions on the given daemon numbers", "", " [-sort name|time|size] (default name)", "     name        - sorts by region name", "     time        - sorts from most to least recently touched", "     size        - sorts from smallest to largest memory usage.", "", " [-format table|record] (default table)", "     table       - output in table format", "     record      - output in record format", "  The record format includes more information than table.", "", " [name-expression] (default '*')", "     [:alnum:|'_'|'@'|'*']*", "     This is a region matching expression, where '*' is a", "     wildcard character.  For example, '*REGION*000000*'", "     matches 'MYREGION_000000_' and 'REGION000000'.", "", "Examples:", "  lsr -format record -sort time *MYREGION*", ""}, RSCommandImpls.CommandRsRegionsLs.class, 0L);
         RSREGIONSRM = new CommandType("rmr", (CommandParam[])null, new CommandParam[]{CommandDefs.CommandParam.NAME, CommandDefs.CommandParam.FORCE}, true, "delete region(s) in the attached RS Daemon Cluster", "rmr [-force] name-expression", new String[]{"The \"rmr\" command deletes regions in the current", "attached Daemon Cluster (see rsattach).", "", " [-force]", "     By default, the 'rmr' command will only delete regions", "     that are not currently opened by a Replicated Store. ", "     Specify '-force' to force the deletion of open regions.", "", " name-expression (required)", "     [:alnum:|'_'|'@'|'*']*", "     This is a region matching expression, where '*' is a", "     wildcard character.  For example, '*REGION*000000*'", "     matches 'MYREGION_000000_' and 'REGION000000'.", "", "If there are opened regions and '-force' wasn't specified,", "the 'rmr' command reports the number of open regions that", "would have been deleted if '-force' was specified.", "", "Example:", "  rmr -force *MyRegion*", ""}, RSCommandImpls.CommandRsRegionsRm.class, 0L);
         RSHELP = new CommandType("rshelp", (CommandParam[])null, (CommandParam[])null, true, "display Replicated Store (RS) command usage", "rshelp [command]", new String[]{"Use 'rshelp' without a parameter to list all available", "RS commands.  Specify an RS command name as a", "parameter to display detailed usage information about", "the command.", "", "A Replicated Store is a high performance", "replicated memory storage option for Exalogic hosted", "WebLogic Messaging Services and is an alternative for", "WebLogic's file and database storage options.", ""}, RSCommandImpls.CommandRsHelp.class, 0L);
      }
   }
}
