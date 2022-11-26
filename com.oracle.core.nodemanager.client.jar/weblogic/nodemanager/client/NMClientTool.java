package weblogic.nodemanager.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import weblogic.admin.plugin.ChangeList;
import weblogic.admin.plugin.NMComponentTypeChangeList;
import weblogic.admin.plugin.NMMachineChangeList;
import weblogic.admin.plugin.ChangeList.Change.Type;
import weblogic.nodemanager.common.Command;
import weblogic.nodemanager.common.DataFormat;
import weblogic.nodemanager.common.NMInputOutput;

public class NMClientTool {
   private String host = "localhost";
   private int port = -1;
   private String domainName = "mydomain";
   private String domainDir;
   private String serverName = "myserver";
   private String serverType = "WebLogic";
   private String nmUser;
   private String nmPass;
   private String nmDir;
   private String clientType = "ssl";
   private String scriptPath;
   private String timeout;
   private Command cmd;
   private boolean verbose;
   private Map scriptEnv;
   private String scriptDir;
   private boolean noScriptOutput;
   private boolean restartUpdate = false;
   private boolean restartAll = false;
   private static final String SERVER_NAME = "myserver";
   private static final String DOMAIN_NAME = "mydomain";
   private static final String CLIENT_TYPE = "ssl";
   private static final String SERVER_TYPE = "WebLogic";
   private static final String TRANSACTION = System.getProperty("transactionID");
   private static final String FILE_PATH = System.getProperty("filePath");
   private static final String ADMIN_URL = System.getProperty("adminURL", "http://localhost:7001/");
   private static final String[] USAGE = new String[]{"Usage: java weblogic.nodemanager.client.NMClientTool [OPTIONS] CMD", "", "Where OPTIONS include:", "  -type <type>      Node manager client type (ssl, plain, rsh, ssh)", "                    (default is 'ssl')", "  -host <host>      Node manager host name (default is 'localhost')", "  -port <port>      Node manager port (default based on client type)", "  -nmdir <nmdir>    Node manager home directory", "  -server <server>  Server name (default is 'myserver')", "  -domain <domain>  Domain name (default is 'mydomain')", "  -serverType <st>  Server type (default is 'WebLogic') - one of " + getServerTypeAsString(), "  -root <dir>       Domain root directory", "  -user <username>  Node manager username", "  -pass <password>  Node manager password", "  -verbose          Enable verbose output", "  -help             Print this help message", "", "And CMD is one of the following:", "  START       Start server", "  KILL        Kill server", "  STAT        Get server status", "  GETLOG      Retrieve WLS server log", "  GETNMLOG    Retrieve node manager server log", "  VERSION     Return node manager server version", "  QUIT        Asks the nodemanager to quit", "  REMOVE      Remove server", ""};

   NMClientTool(String[] args) {
      int i = this.parseOptions(args);
      if (i >= args.length) {
         this.printUsage();
         System.exit(1);
      }

      try {
         this.cmd = Command.parse(args[i]);
      } catch (IllegalArgumentException var4) {
         System.err.println("Unrecognized command: " + args[i]);
         System.exit(1);
      }

   }

   public int run() throws Throwable {
      NMClient nmc = NMClient.getInstance(this.clientType);

      int var2;
      try {
         var2 = this.doCommand(nmc);
      } finally {
         nmc.done();
      }

      return var2;
   }

   private int doCommand(NMClient nmc) throws IOException {
      if (this.host != null) {
         nmc.setHost(this.host);
      }

      if (this.port > 0) {
         nmc.setPort(this.port);
      }

      if (this.nmDir != null) {
         nmc.setNMDir(this.nmDir);
      }

      if (this.domainName != null) {
         nmc.setDomainName(this.domainName);
      }

      if (this.domainDir != null) {
         nmc.setDomainDir(this.domainDir);
      }

      if (this.serverName != null) {
         nmc.setServerName(this.serverName);
      }

      if (this.serverType != null) {
         nmc.setServerType(this.serverType);
      }

      if (this.nmUser != null) {
         nmc.setNMUser(this.nmUser);
      }

      if (this.nmPass != null) {
         nmc.setNMPass(this.nmPass);
      }

      if (this.verbose) {
         nmc.setVerbose(true);
      }

      if (this.cmd == Command.STAT) {
         System.out.println(nmc.getState(0));
      } else if (this.cmd == Command.VERSION) {
         System.out.println(nmc.getVersion());
      } else if (this.cmd == Command.START) {
         nmc.start();
      } else if (this.cmd == Command.STARTP) {
         Properties props = new Properties();
         final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
         NMInputOutput ioHandler = new NMInputOutput((InputStream)null, (OutputStream)null) {
            public void copy(Writer out) throws IOException {
               throw new UnsupportedOperationException();
            }

            public OutputStream getOutputStream() {
               throw new UnsupportedOperationException();
            }

            public String readLine() throws IOException {
               return reader.readLine();
            }

            public void writeLine(String line) throws IOException {
               throw new UnsupportedOperationException();
            }

            public void copy(OutputStream out) throws IOException {
               throw new UnsupportedOperationException();
            }

            public void copy(InputStream is) throws IOException {
               throw new UnsupportedOperationException();
            }

            public void writeObject(Object o) throws IOException {
               throw new UnsupportedOperationException();
            }

            public Object readObject() throws IOException, ClassNotFoundException {
               throw new UnsupportedOperationException();
            }
         };
         DataFormat.readProperties(ioHandler, props);
         nmc.start(props);
      } else if (this.cmd == Command.KILL) {
         nmc.kill();
      } else if (this.cmd == Command.GETSTATES) {
         System.out.println(nmc.getStates(0));
      } else {
         OutputStreamWriter writer;
         if (this.cmd == Command.GETLOG) {
            writer = new OutputStreamWriter(System.out);
            nmc.getLog(writer);
            writer.flush();
         } else if (this.cmd == Command.GETNMLOG) {
            writer = new OutputStreamWriter(System.out);
            nmc.getNMLog(writer);
            writer.flush();
         } else if (this.cmd == Command.EXECSCRIPT) {
            nmc.execScript(this.scriptPath, this.timeout != null ? Long.parseLong(this.timeout) : 0L);
         } else if (this.cmd == Command.SCRIPTCALL) {
            writer = null;
            if (!this.noScriptOutput) {
               writer = new OutputStreamWriter(System.out);
            }

            System.exit(nmc.executeScript(this.scriptPath, this.scriptDir, this.scriptEnv, writer, this.timeout != null ? Long.parseLong(this.timeout) : 0L));
         } else if (this.cmd == Command.REMOVE) {
            nmc.remove();
         } else if (this.cmd == Command.QUIT) {
            nmc.quit();
         } else if (TRANSACTION != null && this.cmd == Command.CHANGELIST) {
            NMMachineChangeList cl = this.createNMMachineChangeList(TRANSACTION);
            nmc.changeList(cl);
         } else if (TRANSACTION != null && this.cmd == Command.COMMIT_CHANGELIST) {
            nmc.commitChangeList(TRANSACTION);
         } else if (TRANSACTION != null && this.cmd == Command.VALIDATE_CHANGELIST) {
            nmc.validateChangeList(TRANSACTION);
         } else if (TRANSACTION != null && this.cmd == Command.ROLLBACK_CHANGELIST) {
            nmc.rollbackChangeList(TRANSACTION);
         } else if (this.cmd == Command.GETCHANGELIST) {
            NMComponentTypeChangeList changeList = nmc.getChangeListForAllFiles(this.serverType, "");
            System.out.println("ChangeList received: " + changeList);
            ChangeList cl = changeList.getComponentTypeChanges();
            Iterator var10 = cl.getChanges().entrySet().iterator();

            while(var10.hasNext()) {
               Map.Entry c = (Map.Entry)var10.next();
               System.out.println("\tchange: " + (String)c.getKey() + "\t" + ((ChangeList.Change)c.getValue()).getRelativePath() + "\t" + ((ChangeList.Change)c.getValue()).getVersion());
            }
         } else if (FILE_PATH != null && this.cmd == Command.GETFILE) {
            nmc.getFile(this.serverType, "", FILE_PATH);
         } else if (this.cmd == Command.RESTARTNM) {
            if (this.restartAll) {
               nmc.restartAll(Long.parseLong(this.timeout), this.restartUpdate);
            } else {
               nmc.restart(Long.parseLong(this.timeout), this.restartUpdate);
            }
         } else {
            if (this.cmd != Command.PRINTTHREADDUMP) {
               System.err.println("Unrecognized command: " + this.cmd);
               return 1;
            }

            nmc.printThreadDump();
         }
      }

      return 0;
   }

   private NMMachineChangeList createNMMachineChangeList(String transaction) {
      NMMachineChangeList cl = new NMMachineChangeList(transaction);
      ChangeList cl1 = new ChangeList();
      cl1.addChange(Type.ADD, "payloadDir/AddedFile", 123L);
      cl1.addChange(Type.EDIT, "payloadDir/EditedFile", 456L);
      cl1.addChange(Type.REMOVE, "payloadDir/RemovedFile", 789L);
      cl.addChangeListForType("type1", new NMComponentTypeChangeList(new String[]{"c1", "c2"}, cl1));
      return cl;
   }

   private static String getServerTypeAsString() {
      StringBuilder sb = new StringBuilder();
      sb.append("WebLogic").append(" ");
      sb.append("Coherence");
      return sb.toString();
   }

   private void printUsage() {
      String[] var1 = USAGE;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         String aUSAGE = var1[var3];
         System.out.println(aUSAGE);
      }

   }

   private int parseOptions(String[] args) {
      int i = 0;

      try {
         while(i < args.length && args[i].startsWith("-")) {
            String arg = args[i++];
            if (arg.equals("-host")) {
               this.host = args[i++];
            } else if (arg.equals("-port")) {
               String s = args[i++];

               try {
                  this.port = Integer.parseInt(s);
               } catch (NumberFormatException var6) {
                  System.err.println("Invalid port number: " + s);
                  System.exit(1);
               }
            } else if (!arg.equals("-nmdir") && !arg.equals("-n")) {
               if (!arg.equals("-server") && !arg.equals("-s")) {
                  if (arg.equals("-serverType")) {
                     this.serverType = args[i++];
                  } else if (!arg.equals("-domain") && !arg.equals("-d")) {
                     if (!arg.equals("-root") && !arg.equals("-r")) {
                        if (arg.equals("-user")) {
                           this.nmUser = args[i++];
                        } else if (arg.equals("-pass")) {
                           this.nmPass = args[i++];
                        } else if (!arg.equals("-type") && !arg.equals("-t")) {
                           if (!arg.equals("-verbose") && !arg.equals("-v")) {
                              if (!arg.equals("-script_path") && !arg.equals("-sp")) {
                                 if (!arg.equals("-script_timeout") && !arg.equals("-st")) {
                                    if (arg.equals("-script_env")) {
                                       this.scriptEnv = new HashMap();
                                       StringTokenizer st = new StringTokenizer(args[i++], ",=");

                                       while(st.hasMoreTokens()) {
                                          this.scriptEnv.put(st.nextToken(), st.nextToken());
                                       }
                                    } else if (arg.equals("-no_script_output")) {
                                       this.noScriptOutput = true;
                                    } else if (arg.equals("-script_dir")) {
                                       this.scriptDir = args[i++];
                                    } else if (arg.equals("-restart_timeout")) {
                                       this.timeout = args[i++];
                                    } else if (arg.equals("-restart_all")) {
                                       this.restartAll = true;
                                    } else if (arg.equals("-restart_update")) {
                                       this.restartUpdate = true;
                                    } else if (!arg.equals("-help") && !arg.equals("-?")) {
                                       System.err.println("Invalid option: " + arg);
                                       System.exit(1);
                                    } else {
                                       this.printUsage();
                                       System.exit(0);
                                    }
                                 } else {
                                    this.timeout = args[i++];
                                 }
                              } else {
                                 this.scriptPath = args[i++];
                              }
                           } else {
                              this.verbose = true;
                           }
                        } else {
                           this.clientType = args[i++];
                        }
                     } else {
                        this.domainDir = args[i++];
                     }
                  } else {
                     this.domainName = args[i++];
                  }
               } else {
                  this.serverName = args[i++];
               }
            } else {
               this.nmDir = args[i++];
            }
         }
      } catch (IndexOutOfBoundsException var7) {
         System.err.println("Invalid argument syntax");
         System.exit(1);
      }

      return i;
   }

   public static void main(String[] args) throws Throwable {
      NMClientTool tool = new NMClientTool(args);
      System.exit(tool.run());
   }
}
