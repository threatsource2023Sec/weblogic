package weblogic.management.provider.internal.situationalconfig;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import weblogic.management.DomainDir;

public class SituationalConfigDefaultTest {
   private static final String sitconfig = "situational-config.xml";
   private static final String sitprop = "situational-config.properties";
   private static final String targetDir = DomainDir.getOptConfigDir();
   private final String wildcard_sitconfig_contents = "<?xml version='1.0' encoding='UTF-8'?>\n<dom:domain\nxmlns:dom='http://xmlns.oracle.com/weblogic/domain'\nxmlns:f='http://xmlns.oracle.com/weblogic/domain-fragment'\nxmlns:s='http://xmlns.oracle.com/weblogic/situational-config' >\n<s:expiration> FOO </s:expiration>\n<dom:name>sitconfigDomain</dom:name>\n<dom:server>\n<dom:name>'*'</dom:name>\n<dom:server-debug>\n<dom:debug-jmx-core f:combine-mode='replace'>true</dom:debug-jmx-core>\n</dom:server-debug>\n</dom:server>\n</dom:domain>";
   private final String sitconfig_contents = "<?xml version='1.0' encoding='UTF-8'?>\n<dom:domain\nxmlns:dom='http://xmlns.oracle.com/weblogic/domain'\nxmlns:f='http://xmlns.oracle.com/weblogic/domain-fragment'\nxmlns:s='http://xmlns.oracle.com/weblogic/situational-config' >\n<s:expiration> FOO </s:expiration>\n<dom:name>sitconfigDomain</dom:name>\n<dom:server>\n<dom:name>admin</dom:name>\n<dom:server-debug>\n<dom:debug-jmx-core f:combine-mode='replace'>true</dom:debug-jmx-core>\n</dom:server-debug>\n</dom:server>\n</dom:domain>";
   private final String sitprop_contents = "#TestFile\nweblogic.debug.DebugSituationalConfig=true\n";
   private static final String sitconfigJMS = "my-jms-situational-config.xml";
   private static final String sitconfigJDBC = "my-jdbc-situational-config.xml";
   private static final String sitconfigDiagnostics = "my-wldf-situational-config.xml";
   private static final String targetJMSDir = DomainDir.getOptConfigJMSDir();
   private static final String targetJDBCDir = DomainDir.getOptConfigJDBCDir();
   private static final String targetDiagnosticsDir = DomainDir.getOptConfigDiagnosticsDir();
   private final String sitconfig_jms_contents = "<?xml version='1.0' encoding='UTF-8'?>\n<jms:weblogic-jms\nxmlns:jms='http://xmlns.oracle.com/weblogic/weblogic-jms'\nxmlns:f='http://xmlns.oracle.com/weblogic/weblogic-jms-fragment'\nxmlns:s='http://xmlns.oracle.com/weblogic/situational-config' >\n<s:expiration> FOO </s:expiration>\n<jms:quota jms:name=\"MyTemplate.Quota\">\n<jms:shared f:combine-mode=\"replace\">true</jms:shared>\n</jms:quota>\n<jms:template jms:name=\"MyTemplate\">\n<jms:delivery-failure-params>\n<jms:expiration-policy f:combine-mode=\"replace\">Discard</jms:expiration-policy>\n</jms:delivery-failure-params>\n</jms:template>\n</jms:weblogic-jms>";
   private final String sitconfig_jdbc_contents = "<?xml version='1.0' encoding='UTF-8'?>\n<jdbc:jdbc-data-source\nxmlns:jdbc='http://xmlns.oracle.com/weblogic/jdbc-data-source'\nxmlns:f='http://xmlns.oracle.com/weblogic/jdbc-data-source-fragment'\nxmlns:s='http://xmlns.oracle.com/weblogic/situational-config' >\n<s:expiration> FOO </s:expiration>\n<jdbc:name>jdbcDataSource</jdbc:name>\n<jdbc:jdbc-driver-params>\n<jdbc:properties>\n<jdbc:property f:combine-mode=\"replace\">\n<jdbc:name>user</jdbc:name>\n<jdbc:value>Jones</jdbc:value>\n</jdbc:property>\n<jdbc:property f:combine-mode=\"replace\">\n<jdbc:name>password</name>\n<jdbc:value>Welcome1</value>\n</jdbc:property>\n</jdbc:properties>\n</jdbc:jdbc-data-source>";
   private final String sitconfig_wldf_contents = "<?xml version='1.0' encoding='UTF-8'?>\n<wldf:wldf-resource\nxmlns:wldf='http://xmlns.oracle.com/weblogic/weblogic-diagnostics'\nxmlns:f='http://xmlns.oracle.com/weblogic/weblogic-diagnostics-fragment'\nxmlns:s='http://xmlns.oracle.com/weblogic/situational-config' >\n<s:expiration> FOO </s:expiration>\n<wldf:instrumentation>\n<wldf:enabled f:combine-mode=\"replace\">true</wldf:enabled>\n</wldf:instrumentation>\n</wldf:wldf-resource>";
   private static final boolean useWildcard = true;
   private static long ONE_DAY_MIN = 1440L;

   public SituationalConfigDefaultTest() throws RuntimeException {
      try {
         writeFile("situational-config.properties", targetDir, "#TestFile\nweblogic.debug.DebugSituationalConfig=true\n");
         writeFile("situational-config.xml", targetDir, "<?xml version='1.0' encoding='UTF-8'?>\n<dom:domain\nxmlns:dom='http://xmlns.oracle.com/weblogic/domain'\nxmlns:f='http://xmlns.oracle.com/weblogic/domain-fragment'\nxmlns:s='http://xmlns.oracle.com/weblogic/situational-config' >\n<s:expiration> FOO </s:expiration>\n<dom:name>sitconfigDomain</dom:name>\n<dom:server>\n<dom:name>'*'</dom:name>\n<dom:server-debug>\n<dom:debug-jmx-core f:combine-mode='replace'>true</dom:debug-jmx-core>\n</dom:server-debug>\n</dom:server>\n</dom:domain>");
         writeFile("my-jms-situational-config.xml", targetJMSDir, "<?xml version='1.0' encoding='UTF-8'?>\n<jms:weblogic-jms\nxmlns:jms='http://xmlns.oracle.com/weblogic/weblogic-jms'\nxmlns:f='http://xmlns.oracle.com/weblogic/weblogic-jms-fragment'\nxmlns:s='http://xmlns.oracle.com/weblogic/situational-config' >\n<s:expiration> FOO </s:expiration>\n<jms:quota jms:name=\"MyTemplate.Quota\">\n<jms:shared f:combine-mode=\"replace\">true</jms:shared>\n</jms:quota>\n<jms:template jms:name=\"MyTemplate\">\n<jms:delivery-failure-params>\n<jms:expiration-policy f:combine-mode=\"replace\">Discard</jms:expiration-policy>\n</jms:delivery-failure-params>\n</jms:template>\n</jms:weblogic-jms>");
         writeFile("my-jdbc-situational-config.xml", targetJDBCDir, "<?xml version='1.0' encoding='UTF-8'?>\n<jdbc:jdbc-data-source\nxmlns:jdbc='http://xmlns.oracle.com/weblogic/jdbc-data-source'\nxmlns:f='http://xmlns.oracle.com/weblogic/jdbc-data-source-fragment'\nxmlns:s='http://xmlns.oracle.com/weblogic/situational-config' >\n<s:expiration> FOO </s:expiration>\n<jdbc:name>jdbcDataSource</jdbc:name>\n<jdbc:jdbc-driver-params>\n<jdbc:properties>\n<jdbc:property f:combine-mode=\"replace\">\n<jdbc:name>user</jdbc:name>\n<jdbc:value>Jones</jdbc:value>\n</jdbc:property>\n<jdbc:property f:combine-mode=\"replace\">\n<jdbc:name>password</name>\n<jdbc:value>Welcome1</value>\n</jdbc:property>\n</jdbc:properties>\n</jdbc:jdbc-data-source>");
         writeFile("my-wldf-situational-config.xml", targetDiagnosticsDir, "<?xml version='1.0' encoding='UTF-8'?>\n<wldf:wldf-resource\nxmlns:wldf='http://xmlns.oracle.com/weblogic/weblogic-diagnostics'\nxmlns:f='http://xmlns.oracle.com/weblogic/weblogic-diagnostics-fragment'\nxmlns:s='http://xmlns.oracle.com/weblogic/situational-config' >\n<s:expiration> FOO </s:expiration>\n<wldf:instrumentation>\n<wldf:enabled f:combine-mode=\"replace\">true</wldf:enabled>\n</wldf:instrumentation>\n</wldf:wldf-resource>");
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   private static String W3Cdate(long delta) {
      DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
      format.setTimeZone(TimeZone.getTimeZone("GMT"));
      Date date = new Date(System.currentTimeMillis() + delta * 60L * 1000L);
      return format.format(date) + "+00:00";
   }

   private static void writeFile(String filename, String dir, String contents) throws IOException {
      if (!exists(dir, filename)) {
         writeSituationalConfigToOptconfig(contents, dir, filename);
      }

   }

   private static boolean exists(String targetDir, String filename) throws IOException {
      String targetLocation = targetDir + File.separator + filename;
      return (new File(targetLocation)).exists();
   }

   private static void writeSituationalConfigToOptconfig(String contents, String targetDir, String filename) throws IOException {
      writeSituationalConfigToOptconfig(contents, targetDir, filename, W3Cdate(ONE_DAY_MIN));
   }

   private static void writeSituationalConfigToOptconfig(String contents, String targetDir, String filename, String time) throws IOException {
      File targetDirFile = new File(targetDir);
      if (!targetDirFile.exists()) {
         targetDirFile.mkdir();
      }

      String targetLocation = targetDir + File.separator + filename;
      BufferedReader reader = new BufferedReader(new StringReader(contents));
      BufferedWriter writer = new BufferedWriter(new FileWriter(targetLocation));
      System.err.println("------------------------------------------");
      System.err.println("------------------------------------------");

      String line;
      while((line = reader.readLine()) != null) {
         if (time != null) {
            line = line.replace("FOO", time);
         }

         System.err.println(line);
         writer.write(line);
         writer.newLine();
      }

      System.err.println("------------------------------------------");
      System.err.println("------------------------------------------");
      reader.close();
      writer.flush();
      writer.close();
   }
}
