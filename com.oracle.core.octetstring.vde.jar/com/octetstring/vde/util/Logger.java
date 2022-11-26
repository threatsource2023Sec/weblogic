package com.octetstring.vde.util;

import com.octetstring.nls.Messages;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class Logger {
   private static Logger instance;
   private int logLevel = 0;
   private boolean logConsole = true;
   private String lastDate = null;
   private long lasttime = 0L;
   private volatile boolean writelock = false;
   private SimpleDateFormat formatter = null;
   private PrintWriter logWriter = null;
   private PrintWriter consoleWriter = null;
   private BufferedWriter accessWriter = null;
   private ExternalLogger externalLogger = null;
   private String fullVLName = null;
   private String fullALName = null;
   public static final int LOG_ERROR = 0;
   public static final int LOG_WARN = 3;
   public static final int LOG_INFO = 5;
   public static final int LOG_DEBUG = 7;
   public static final int LOG_DETAIL = 9;
   public static final int LOG_DUMP = 11;
   private static final String LOG_MSG_ERROR = "ERROR  ";
   private static final String LOG_MSG_WARN = "WARN   ";
   private static final String LOG_MSG_INFO = "INFO   ";
   private static final String LOG_MSG_DEBUG = "DEBUG  ";
   private static final String LOG_MSG_DETAIL = "DETAIL ";
   private static final String LOG_MSG_DUMP = "DUMP   ";
   private static String[] LOG_MSG = new String[12];
   private static Hashtable shortname = new Hashtable();
   private static volatile boolean doflush = false;

   private Logger() {
      this.logLevel = new Integer((String)ServerConfig.getInstance().get("vde.debug"));
      if (((String)ServerConfig.getInstance().get("vde.logconsole")).equals("1")) {
         this.logConsole = true;
      } else {
         this.logConsole = false;
      }

      String lfn = (String)ServerConfig.getInstance().get("vde.logfile");
      String afn = (String)ServerConfig.getInstance().get("vde.accesslogfile");
      String ihome = System.getProperty("vde.home");
      String fullname = null;
      if (ihome == null) {
         fullname = lfn;
      } else {
         fullname = ihome + "/" + lfn;
      }

      String acfullname = null;
      if (ihome == null) {
         acfullname = afn;
      } else {
         acfullname = ihome + "/" + afn;
      }

      this.fullALName = acfullname;
      this.fullVLName = fullname;
      this.openLogs();
      LOG_MSG[0] = "ERROR  ";
      LOG_MSG[3] = "WARN   ";
      LOG_MSG[5] = "INFO   ";
      LOG_MSG[7] = "DEBUG  ";
      LOG_MSG[9] = "DETAIL ";
      LOG_MSG[11] = "DUMP   ";
      shortname.put("com.octetstring.vde.LDAPServer", "LDAPServer");
      shortname.put("com.octetstring.vde.ConnectionHandler", "ConnectionHandler");
      shortname.put("com.octetstring.vde.acl.ACLChecker", "ACLChecker");
      shortname.put("com.octetstring.vde.backend.BackendHandler", "BackendHandler");
      shortname.put("com.octetstring.vde.MessageHandler", "MessageHandler");
      shortname.put("com.octetstring.vde.Entry", "Entry");
      shortname.put("com.octetstring.vde.operation.BindOperation", "BindOperation");
      shortname.put("com.octetstring.vde.backend.jndi.BackendJNDI", "BackendJNDI");
      shortname.put("com.octetstring.vde.backend.standard.BackendStandard", "BackendStandard");
      shortname.put("com.octetstring.vde.backend.text.BackendText", "BackendText");
      shortname.put("com.octetstring.vde.backend.db.DBMapper", "DBMapper");
      shortname.put("com.octetstring.vde.License", "License");
      shortname.put("com.octetstring.vde.backend.standard.TransactionLog", "TransactionLog");
      shortname.put("com.octetstring.vde.backend.standard.TransactionProcessor", "TransactionProcessor");
      shortname.put("com.octetstring.vde.replication.Replication", "Replication");
      shortname.put("com.octetstring.vde.replication.Replicator", "Replicator");
      shortname.put("com.octetstring.vde.util.LDIF", "LDIF");
      shortname.put("com.octetstring.vde.backend.BackendRoot", "BackendRoot");
      shortname.put("com.octetstring.vde.frontend.LDAP", "LDAP");
      shortname.put("com.octetstring.vde.frontend.DSML", "DSML");
      shortname.put("com.octetstring.vde.frontend.XSLT", "XSLT");
      shortname.put("com.octetstring.vde.VDEServer", "VDEServer");
      shortname.put("com.octetstring.vde.frontend.ListenerHandler", "ListenerHandler");
      shortname.put("com.octetstring.vde.frontend.UDDI", "UDDI");
      shortname.put("com.octetstring.vde.backend.Mapper", "Mapper");
      shortname.put("com.octetstring.vde.frontend.AcceptThread", "LDAPnio-AcceptThread");
      shortname.put("com.octetstring.vde.frontend.ReadWriteThread", "LDAPnio-ReadWriteThread");
      shortname.put("com.octetstring.vde.backend.jndi.JNDIEntrySet", "JNDIEntrySet");
   }

   private void openLogs() {
      try {
         if (this.consoleWriter == null) {
            this.consoleWriter = new PrintWriter(new OutputStreamWriter(System.out), false);
         }

         this.logWriter = new PrintWriter(new BufferedWriter(new FileWriter(this.fullVLName, true)), false);
         this.accessWriter = new BufferedWriter(new FileWriter(this.fullALName, true));
      } catch (IOException var2) {
         System.out.println(Messages.getString("Error_creating_logFile___59") + var2.getMessage());
      }

   }

   public PrintWriter getConsole() {
      return this.consoleWriter;
   }

   public PrintWriter getLogWriter() {
      return this.logWriter;
   }

   public static Logger getInstance() {
      if (instance == null) {
         instance = new Logger();
      }

      return instance;
   }

   private synchronized void writeLock() {
      while(this.writelock) {
         try {
            this.wait();
         } catch (InterruptedException var2) {
         }
      }

      this.writelock = true;
   }

   private synchronized void writeUnlock() {
      this.writelock = false;
      this.notify();
   }

   public void rotate(int maxlogs) {
      try {
         this.writeLock();
         this.closeLogs();

         File alfile;
         File nvlfile;
         File nalfile;
         for(int i = maxlogs - 2; i >= 0; --i) {
            alfile = new File(this.fullVLName + "." + i);
            nvlfile = new File(this.fullALName + "." + i);
            nalfile = new File(this.fullVLName + "." + (i + 1));
            File nalfile = new File(this.fullALName + "." + (i + 1));
            nalfile.delete();
            nalfile.delete();
            alfile.renameTo(nalfile);
            nvlfile.renameTo(nalfile);
         }

         File vlfile = new File(this.fullVLName);
         alfile = new File(this.fullALName);
         nvlfile = new File(this.fullVLName + ".0");
         nalfile = new File(this.fullALName + ".0");
         nvlfile.delete();
         nalfile.delete();
         vlfile.renameTo(nvlfile);
         alfile.renameTo(nalfile);
         this.openLogs();
      } finally {
         this.writeUnlock();
      }
   }

   public void setLogLevel(int level) {
      this.logLevel = level;
   }

   public void setLogWriter(PrintWriter logWriter) {
      this.logWriter = logWriter;
   }

   public void setExternalLogger(ExternalLogger externalLogger) {
      this.externalLogger = externalLogger;
   }

   public boolean isLogable(int level) {
      return this.logLevel >= level;
   }

   public void log(int level, Object obj, String message) {
      if (this.logLevel >= level) {
         String loggedclass = obj.getClass().getName();
         if (shortname.get(loggedclass) != null) {
            loggedclass = (String)shortname.get(loggedclass);
         }

         String logEntry = "[" + this.getDate() + "] " + LOG_MSG[level] + " - " + loggedclass + ":  " + message;
         if (this.logConsole) {
            this.consoleWriter.println(logEntry);
         }

         if (this.logWriter != null) {
            this.writeLock();
            this.logWriter.println(logEntry);
            this.writeUnlock();
         }

         if (this.externalLogger != null) {
            this.externalLogger.log(level, loggedclass, message);
         }

         doflush = true;
      }

   }

   public void alog(int conn, String message) {
      if (this.accessWriter != null) {
         try {
            this.writeLock();
            this.accessWriter.write("[");
            this.accessWriter.write(this.getDate());
            this.accessWriter.write("] conn=");
            this.accessWriter.write(String.valueOf(conn));
            this.accessWriter.write(" ");
            this.accessWriter.write(message);
            this.accessWriter.newLine();
            doflush = true;
         } catch (IOException var7) {
         } finally {
            this.writeUnlock();
         }
      }

   }

   public void alog(int conn, StringBuffer message) {
      if (this.accessWriter != null) {
         try {
            message.insert(0, " ");
            message.insert(0, String.valueOf(conn));
            message.insert(0, "] conn=");
            message.insert(0, this.getDate());
            message.insert(0, "[");
            this.writeLock();
            this.accessWriter.write(message.toString());
            this.accessWriter.newLine();
            doflush = true;
         } catch (IOException var7) {
         } finally {
            this.writeUnlock();
         }
      }

   }

   public void alog(String conn, String message) {
      if (this.accessWriter != null) {
         try {
            this.writeLock();
            this.accessWriter.write("[");
            this.accessWriter.write(this.getDate());
            this.accessWriter.write("] conn=");
            this.accessWriter.write(conn);
            this.accessWriter.write(" ");
            this.accessWriter.write(message);
            this.accessWriter.newLine();
            doflush = true;
         } catch (IOException var7) {
         } finally {
            this.writeUnlock();
         }
      }

   }

   private String getDate() {
      if (this.formatter == null) {
         this.formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss z");
      }

      synchronized(this) {
         Date curtime = new Date();
         if (curtime.getTime() - this.lasttime > 1000L) {
            this.lasttime = curtime.getTime();
            this.lastDate = this.formatter.format(curtime);
         }
      }

      return this.lastDate;
   }

   public void flush() {
      if (doflush) {
         doflush = false;

         try {
            if (this.logWriter != null) {
               this.logWriter.flush();
            }

            if (this.consoleWriter != null) {
               this.consoleWriter.flush();
            }

            if (this.accessWriter != null) {
               this.accessWriter.flush();
            }
         } catch (IOException var2) {
         }

      }
   }

   public void printStackTrace(Throwable t) {
      if (this.externalLogger != null) {
         this.externalLogger.printStackTrace(t);
      } else {
         t.printStackTrace();
      }

   }

   public void printStackTraceLog(Throwable t) {
      if (this.externalLogger != null) {
         this.externalLogger.printStackTraceLog(t);
      } else {
         t.printStackTrace(this.getLogWriter());
      }

   }

   public void printStackTraceConsole(Throwable t) {
      if (this.externalLogger != null) {
         this.externalLogger.printStackTraceConsole(t);
      } else {
         t.printStackTrace(this.getConsole());
      }

   }

   private void closeLogs() {
      if (this.logWriter != null) {
         try {
            this.logWriter.flush();
            this.logWriter.close();
         } catch (Exception var3) {
         }
      }

      if (this.accessWriter != null) {
         try {
            this.accessWriter.flush();
            this.accessWriter.close();
         } catch (IOException var2) {
         }
      }

   }

   public void finalize() {
      this.closeLogs();
   }
}
