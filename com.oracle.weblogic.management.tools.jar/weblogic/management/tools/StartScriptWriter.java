package weblogic.management.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class StartScriptWriter {
   public static void writeShFile(String directory, String domainName, String serverName, String wlHome) {
      String dir = directory;
      String domain = domainName;
      String server = serverName;
      String shwl = wlHome.replace('\\', '/');

      try {
         File d = new File(dir);
         d.mkdir();
         File fil = new File(dir + File.separator + "start" + domain + ".sh");
         String fileName = fil.getName();
         FileOutputStream fos = new FileOutputStream(new File(dir + File.separator + "start" + domain + ".sh"));
         PrintStream ps = new PrintStream(fos);
         println(ps, "#!/bin/sh");
         println(ps, "# ****************************************************************************");
         println(ps, "# This script is used to start WebLogic Server for the domain in the current ");
         println(ps, "# working directory.  This script simply sets the SERVER_NAME variable ");
         println(ps, "# and calls the startWLS.sh script under ${WL_HOME}/server/bin.");
         println(ps, "#");
         println(ps, "# To create your own start script for your domain, all you need to set is ");
         println(ps, "# SERVER_NAME, then start the server.");
         println(ps, "#");
         println(ps, "# Other variables that startWLS takes are:");
         println(ps, "#");
         println(ps, "# WLS_USER       - cleartext user for server startup");
         println(ps, "# WLS_PW         - cleartext password for server startup");
         println(ps, "# STARTMODE      - Set to true for production mode servers, false for ");
         println(ps, "#                  development mode");
         println(ps, "# JAVA_OPTIONS   - Java command-line options for running the server. (These");
         println(ps, "#                  will be tagged on to the end of the JAVA_VM and MEM_ARGS)");
         println(ps, "# JAVA_VM        - The java arg specifying the VM to run.  (i.e. -server, ");
         println(ps, "#                  -hotspot, etc.)");
         println(ps, "# MEM_ARGS       - The variable to override the standard memory arguments");
         println(ps, "#                  passed to java");
         println(ps, "#");
         println(ps, "# For additional information, refer to the WebLogic Server Administration Guide");
         println(ps, "# in the Oracle online documentation at (http://www.oracle.com/technetwork/middleware/weblogic/overview/index.html).");
         println(ps, "# ****************************************************************************");
         println(ps, "");
         println(ps, "# set up WL_HOME, the root directory of your WebLogic installation");
         println(ps, "WL_HOME=\"" + shwl + "\"");
         println(ps, "");
         println(ps, "# set up common environment");
         println(ps, ". \"${WL_HOME}//..//oracle_common//common//bin//commEnv.sh\"");
         println(ps, "");
         println(ps, "# Set SERVER_NAME to the name of the server you wish to start up.");
         println(ps, "SERVER_NAME=\"" + server + "\"");
         println(ps, "");
         println(ps, "# Set WLS_USER equal to your system username and WLS_PW equal  ");
         println(ps, "# to your system password for no username and password prompt ");
         println(ps, "# during server startup.  Both are required to bypass the startup");
         println(ps, "# prompt.");
         println(ps, "WLS_USER=");
         println(ps, "WLS_PW=");
         println(ps, "");
         println(ps, "# Set Production Mode.  When this is set to true, the server starts up in ");
         println(ps, "# production mode.  When set to false, the server starts up in development ");
         println(ps, "# mode.  If it is not set, it will default to false.");
         println(ps, "STARTMODE=\"\"");
         println(ps, "");
         println(ps, "# Set JAVA_OPTIONS to the java flags you want to pass to the vm.  If there ");
         println(ps, "# are more than one, include quotes around them.  For instance: ");
         println(ps, "# JAVA_OPTIONS=\"-Dweblogic.attribute=value -Djava.attribute=value\"");
         println(ps, "");
         println(ps, "# Over-write JVM arguments initialized in commEnv.sh");
         println(ps, "case $JAVA_VENDOR in");
         println(ps, "BEA)");
         println(ps, "MEM_ARGS=");
         println(ps, "JAVA_VM=");
         println(ps, ";;");
         println(ps, "IBM)");
         println(ps, "MEM_ARGS=");
         println(ps, "JAVA_VM=");
         println(ps, ";;");
         println(ps, "HP)");
         println(ps, "MEM_ARGS=");
         println(ps, "JAVA_VM=");
         println(ps, ";;");
         println(ps, "SUN)");
         println(ps, "MEM_ARGS=");
         println(ps, "JAVA_VM=");
         println(ps, ";;");
         println(ps, "*)");
         println(ps, ";;");
         println(ps, "esac");
         println(ps, "");
         println(ps, "# Reset number of open file descriptors in the current process");
         println(ps, "# This function is defined in commEnv.sh");
         println(ps, "resetFd");
         println(ps, "");
         println(ps, "# Start WebLogic server");
         println(ps, "CLASSPATH=\"${WEBLOGIC_CLASSPATH}${CLASSPATHSEP}${POINTBASE_CLASSPATH}${CLASSPATHSEP}${JAVA_HOME}/jre/lib/rt.jar${CLASSPATHSEP}${WL_HOME}/server/lib/webservices.jar${CLASSPATHSEP}${CLASSPATH}\"");
         println(ps, "export CLASSPATH");
         println(ps, "");
         println(ps, "java ${JAVA_VM} ${MEM_ARGS} ${JAVA_OPTIONS} -Dweblogic.Name=${SERVER_NAME} -Dweblogic.management.username=${WLS_USER} -Dweblogic.management.password=${WLS_PW} -Dweblogic.ProductionModeEnabled=${STARTMODE} -Djava.security.policy=\"${WL_HOME}/server/lib/weblogic.policy\" weblogic.Server");
         Runtime.getRuntime().exec("chmod +x " + fileName);
         fos.close();
      } catch (IOException var14) {
         var14.printStackTrace();
      }

   }

   public static void writeCmdFile(String directory, String domainName, String serverName, String wlHome) {
      String dir = directory;
      String domain = domainName;
      String server = serverName;

      try {
         File d = new File(dir);
         d.mkdir();
         FileOutputStream fos = new FileOutputStream(new File(dir + File.separator + "start" + domain + ".cmd"));
         PrintStream ps = new PrintStream(fos);
         println(ps, "@rem *************************************************************************");
         println(ps, "@rem This script is used to start WebLogic Server for the domain in the ");
         println(ps, "@rem current working directory.  This script simply sets the SERVER_NAME ");
         println(ps, "@rem variable and calls the startWLS.cmd script under");
         println(ps, "@rem %WL_HOME%\\server\\bin.");
         println(ps, "@rem");
         println(ps, "@rem To create your own start script for your domain, all you need to set is ");
         println(ps, "@rem SERVER_NAME, then start the server.");
         println(ps, "@rem");
         println(ps, "@rem Other variables that startWLS takes are:");
         println(ps, "@rem");
         println(ps, "@rem WLS_USER     - cleartext user for server startup");
         println(ps, "@rem WLS_PW       - cleartext password for server startup");
         println(ps, "@rem STARTMODE    - true for production mode servers, false for ");
         println(ps, "@rem                development mode");
         println(ps, "@rem JAVA_OPTIONS - Java command-line options for running the server. (These");
         println(ps, "@rem                will be tagged on to the end of the JAVA_VM and MEM_ARGS)");
         println(ps, "@rem JAVA_VM      - The java arg specifying the VM to run.  (i.e. -server, ");
         println(ps, "@rem                -hotspot, etc.)");
         println(ps, "@rem MEM_ARGS     - The variable to override the standard memory arguments");
         println(ps, "@rem                passed to java");
         println(ps, "@rem");
         println(ps, "@rem For additional information, refer to the WebLogic Server Administration Guide");
         println(ps, "@rem in the Oracle online documentation at (http://www.oracle.com/technetwork/middleware/weblogic/overview/index.html).");
         println(ps, "@rem *************************************************************************");
         println(ps, "");
         println(ps, "echo off");
         println(ps, "SETLOCAL");
         println(ps, "");
         println(ps, "set WL_HOME=\"" + wlHome + "\"");
         println(ps, "call \"%WL_HOME%\\..\\oracle_common\\common\\bin\\commEnv.cmd\"");
         println(ps, "@rem Set SERVER_NAME to the name of the server you wish to start up.");
         println(ps, "set SERVER_NAME=\"" + server + "\"");
         println(ps, "@rem Set WLS_USER equal to your system username and WLS_PW equal  ");
         println(ps, "@rem to your system password for no username and password prompt ");
         println(ps, "@rem during server startup.  Both are required to bypass the startup");
         println(ps, "@rem prompt.  This is not recomended for a production environment.");
         println(ps, "set WLS_USER=");
         println(ps, "set WLS_PW=");
         println(ps, "");
         println(ps, "@rem Set Production Mode.  When this is set to true, the server starts up in ");
         println(ps, "@rem production mode.  When set to false, the server starts up in development ");
         println(ps, "@rem mode.  If it is not set, it will default to false.");
         println(ps, "set STARTMODE=");
         println(ps, "");
         println(ps, "@rem Set JAVA_OPTIONS to the java flags you want to pass to the vm. i.e.: ");
         println(ps, "@rem set JAVA_OPTIONS=-Dweblogic.attribute=value -Djava.attribute=value");
         println(ps, "set JAVA_OPTIONS=");
         println(ps, "");
         println(ps, "@rem Set JAVA_VM to the java virtual machine you want to run.  For instance:");
         println(ps, "@rem For instance:");
         println(ps, "set JAVA_VM=");
         println(ps, "");
         println(ps, "@rem Set MEM_ARGS to the memory args you want to pass to java.  For instance:");
         println(ps, "@rem if \"%JAVA_VENDOR%\"==\"BEA\" set MEM_ARGS=-Xms32m -Xmx200m");
         println(ps, "set MEM_ARGS=");
         println(ps, "");
         println(ps, "@rem Call WebLogic Server");
         println(ps, "");
         println(ps, "set CLASSPATH=%WEBLOGIC_CLASSPATH%;%POINTBASE_CLASSPATH%;%JAVA_HOME%\\jre\\lib\\rt.jar;%WL_HOME%\\server\\lib\\webservices.jar;%CLASSPATH%");
         println(ps, "\"%JAVA_HOME%\\bin\\java\" %JAVA_VM% %MEM_ARGS% %JAVA_OPTIONS% -Dweblogic.Name=%SERVER_NAME% -Dweblogic.management.username=%WLS_USER% -Dweblogic.management.password=%WLS_PW% -Dweblogic.ProductionModeEnabled=%STARTMODE% -Djava.security.policy=\"%WL_HOME%\\server\\lib\\weblogic.policy\" weblogic.Server");
         println(ps, "ENDLOCAL");
         fos.close();
      } catch (IOException var11) {
         var11.printStackTrace();
      }

   }

   public static void main(String[] args) throws IOException {
      writeCmdFile(args[0], args[1], args[2], args[3]);
      writeShFile(args[0], args[1], args[2], args[3]);
   }

   private static void println(PrintStream ps, String str) {
      ps.print(str + "\n");
   }

   private static void print(PrintStream ps, String str) {
      ps.print(str);
   }
}
