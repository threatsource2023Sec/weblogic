package weblogic.diagnostics.image;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import javax.xml.stream.XMLStreamReader;
import weblogic.version;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.healthcheck.HealthCheck;
import weblogic.diagnostics.utils.SecurityHelper;
import weblogic.health.HealthState;
import weblogic.kernel.Kernel;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.XXEUtils;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.StreamUtils;

public class HealthSource implements ImageSource, HealthCheck {
   private static final double NANOS_PER_MILLI = 1000000.0;
   private static final DecimalFormat df = new DecimalFormat("0.000");
   private static final String HEALTH_CHECK_TYPE = "MachineHealthCheck";
   private static final String os = System.getProperty("os.name").toLowerCase();
   private static boolean calledFromMain = false;
   private static HealthSource SINGLETON = null;
   private static String[] PROTECTED = new String[]{"password", "passphrase", "credential", "pass", "pwd"};
   private static final int NO_PORT = -1;
   private static final int DEFAULT_PING_TIMEOUT = 5000;
   private static final int DEFAULT_CONNECT_TIMEOUT = 5000;
   private static final int DEFAULT_PING_ITERATIONS = 4;
   private Method getTotalSpace = null;
   private Method getUsableSpace = null;
   private Method getFreeSpace = null;
   private ArrayList settingErrors = new ArrayList();
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticImage");
   private boolean timeoutRequested;
   private ImageManager imageManager = null;
   private boolean isAdminServer;
   private static int generatedPropCount = 0;
   private int pingTimeout;
   private int connectTimeout;
   private int maxPingIterations = 4;
   private DomainAccess domainAccess;
   private DomainRuntimeMBean domainRuntime;
   private RuntimeAccess runtimeAccess;

   public static synchronized HealthSource getInstance() {
      if (Kernel.isServer()) {
         checkAccess();
      }

      if (SINGLETON == null) {
         SINGLETON = new HealthSource();
      }

      return SINGLETON;
   }

   private static void checkAccess() {
      SecurityHelper.checkForAdminRole();
   }

   HealthSource() {
      if (!calledFromMain) {
         this.imageManager = (ImageManager)LocatorUtilities.getService(ImageManager.class);
      }

      try {
         this.getTotalSpace = File.class.getMethod("getTotalSpace", (Class[])null);
         this.getUsableSpace = File.class.getMethod("getUsableSpace", (Class[])null);
         this.getFreeSpace = File.class.getMethod("getFreeSpace", (Class[])null);
      } catch (Exception var2) {
      }

   }

   public String getType() {
      return "MachineHealthCheck";
   }

   public void initialize() {
      this.runtimeAccess = (RuntimeAccess)LocatorUtilities.getService(RuntimeAccess.class);
      this.isAdminServer = this.runtimeAccess.isAdminServer();
      this.domainAccess = (DomainAccess)LocatorUtilities.getService(DomainAccess.class);
      if (this.domainAccess != null) {
         this.domainRuntime = this.domainAccess.getDomainRuntime();
      }

   }

   public boolean execute(Properties properties, OutputStream out) {
      try {
         this.createDiagnosticImageInternal(out, properties);
         return true;
      } catch (IOException var9) {
         IOException e = var9;
         PrintWriter pw = new PrintWriter(out);

         try {
            pw.println("Error completing " + this.getType());
            e.printStackTrace(pw);
         } finally {
            pw.close();
         }

         throw new RuntimeException(var9);
      }
   }

   public String getExtension() {
      return ".jar";
   }

   public int gatherDataToFile(String fileName, Properties settings) {
      FileOutputStream fileOut = null;

      byte var5;
      try {
         fileOut = new FileOutputStream(new File(calledFromMain ? "." : this.imageManager.getDestinationDirectory(), fileName));
         this.createDiagnosticImageInternal(fileOut, settings);
         byte var4 = 0;
         return var4;
      } catch (Exception var15) {
         var15.printStackTrace();
         var5 = 1;
      } finally {
         if (fileOut != null) {
            try {
               fileOut.flush();
               fileOut.close();
            } catch (IOException var14) {
               return 1;
            }
         }

      }

      return var5;
   }

   public void createDiagnosticImage(OutputStream out) throws ImageSourceCreationException {
      try {
         this.createDiagnosticImageInternal(out, (Properties)null);
      } catch (IOException var3) {
         throw new ImageSourceCreationException(var3);
      }
   }

   private void createDiagnosticImageInternal(OutputStream out, Properties settings) throws IOException {
      this.timeoutRequested = false;
      JarOutputStream jarOut = new JarOutputStream(out);
      PrintWriter img = new PrintWriter(jarOut);

      try {
         this.settingErrors.clear();
         this.copyFileAsEntry("/proc/meminfo", jarOut);
         this.copyFileAsEntry("/proc/cpuinfo", jarOut);
         this.copyFileAsEntry("/proc/diskstats", jarOut);
         this.copyFileAsEntry("/proc/iomem", jarOut);
         this.copyFileAsEntry("/proc/ioports", jarOut);
         this.copyFileAsEntry("/proc/partitions", jarOut);
         this.copyFileAsEntry("/proc/net/netstat", jarOut);
         this.copyFileAsEntry("/proc/net/sockstat", jarOut);
         this.copyFileAsEntry("/proc/net/sockstat6", jarOut);
         this.copyFileAsEntry("/proc/fs/nfsfs/servers", jarOut);
         this.copyFileAsEntry("/proc/fs/nfsfs/volumes", jarOut);
         this.gatherDomainHealth("domainhealth.xml", settings, jarOut);
         this.gatherJVMDetails("jvm.xml", jarOut);
         this.gatherSystemProperties("systemproperties.xml", jarOut);
         this.gatherVersionDetails("version.xml", jarOut);
         if (this.getTotalSpace != null) {
            this.gatherDiskDetails("diskhealth.xml", jarOut);
         }

         this.gatherFileDetails("filehealth.xml", settings, jarOut);
         this.gatherNetworkDetails("networkhealth.xml", settings, jarOut);
         if (this.settingErrors.size() > 0) {
            this.reportSettingErrors("settingerrors.xml", jarOut);
         }
      } finally {
         jarOut.flush();
         jarOut.close();
         img.flush();
      }

   }

   public void timeoutImageCreation() {
      this.timeoutRequested = true;
   }

   private static boolean isFileAvailable(String fileName) {
      File inputFile = null;

      try {
         inputFile = new File(fileName);
         return inputFile.exists() && inputFile.canRead() && inputFile.isFile();
      } catch (Exception var3) {
         var3.printStackTrace();
         return false;
      }
   }

   private void copyFileAsEntry(String fileName, JarOutputStream outJar) {
      if (!this.timeoutRequested && isFileAvailable(fileName)) {
         try {
            FileInputStream fileStream = null;

            try {
               fileStream = new FileInputStream(fileName);
               String entryName = fileName.startsWith("/") ? fileName.substring(1, fileName.length()) : fileName;
               outJar.putNextEntry(new JarEntry(entryName));
               StreamUtils.writeTo(fileStream, outJar);
               outJar.flush();
               return;
            } catch (IOException var9) {
               outJar.flush();
            } finally {
               if (fileStream != null) {
                  fileStream.close();
               }

            }

         } catch (IOException var11) {
         }
      }
   }

   private void gatherVersionDetails(String entryFileName, JarOutputStream jarOut) {
      try {
         jarOut.putNextEntry(new JarEntry(entryFileName));
         PrintWriter pw = new PrintWriter(jarOut);
         pw.println("<!-- Versions -->");
         pw.println("<versions>");
         pw.println("  <weblogic>");
         String versions = version.getVersions(true);
         StringTokenizer st = new StringTokenizer(versions, "\n");
         int tokens = st.countTokens();

         for(int i = 0; i < tokens; ++i) {
            pw.println("    <version>" + st.nextToken() + "</version>");
         }

         if (!Kernel.isServer()) {
            String serviceVersions = version.getServiceVersions();
            st = new StringTokenizer(serviceVersions, "\n");
            tokens = st.countTokens();

            for(int i = 0; i < tokens; ++i) {
               String token = st.nextToken();
               if (!token.startsWith("SERVICE NAME") && !token.startsWith("===")) {
                  pw.println("    <version>" + token + "</version>");
               }
            }
         }

         pw.println("  </weblogic>");
         pw.println("</versions>");
         pw.println("");
         pw.flush();
         jarOut.flush();
      } catch (Exception var18) {
         var18.printStackTrace();
      } finally {
         try {
            jarOut.flush();
         } catch (IOException var17) {
         }

      }

   }

   private void reportSettingErrors(String entryFileName, JarOutputStream jarOut) {
      try {
         jarOut.putNextEntry(new JarEntry(entryFileName));
         PrintWriter pw = new PrintWriter(jarOut);
         pw.println("<!-- Settings Errors -->");
         pw.println("<settings-errors>");
         Iterator var4 = this.settingErrors.iterator();

         while(var4.hasNext()) {
            SettingError theError = (SettingError)var4.next();
            pw.println("  <setting-error>");
            pw.println("    <property-name>" + theError.propName + "</property-name>");
            pw.println("    <property-value>" + theError.propValue + "</property-value>");
            pw.println("    <error-message>" + theError.error + "</error-message>");
            pw.println("  </setting-error>");
         }

         pw.println("</settings-errors>");
         pw.println("");
         pw.flush();
         jarOut.flush();
      } catch (Exception var14) {
      } finally {
         try {
            jarOut.flush();
         } catch (IOException var13) {
         }

      }

   }

   private void gatherJVMDetails(String entryFileName, JarOutputStream jarOut) {
      try {
         jarOut.putNextEntry(new JarEntry(entryFileName));
         JVMSource jvm = new JVMSource();
         jvm.createDiagnosticImage(jarOut);
         jarOut.flush();
      } catch (Exception var12) {
         var12.printStackTrace();
      } finally {
         try {
            jarOut.flush();
         } catch (IOException var11) {
         }

      }

   }

   private void gatherSystemProperties(String entryFileName, JarOutputStream jarOut) {
      try {
         jarOut.putNextEntry(new JarEntry(entryFileName));
         PrintWriter pw = new PrintWriter(jarOut);
         Properties props = System.getProperties();
         Enumeration e = props.propertyNames();
         pw.println("<!-- System Properties -->");
         pw.println("<system-properties>");

         while(e.hasMoreElements()) {
            String key = (String)e.nextElement();
            String val = props.getProperty(key);

            for(int i = 0; i < PROTECTED.length; ++i) {
               if (key.toLowerCase(Locale.US).indexOf(PROTECTED[i]) >= 0) {
                  val = "********";
               }
            }

            pw.println("  <property>");
            pw.println("    <key>" + key + "</key>");
            pw.println("    <val>" + val + "</val>");
            pw.println("  </property>");
         }

         pw.println("</system-properties>");
         pw.println("");
         pw.flush();
         jarOut.flush();
      } catch (Exception var17) {
      } finally {
         try {
            jarOut.flush();
         } catch (IOException var16) {
         }

      }

   }

   private void gatherDiskDetails(String entryFileName, JarOutputStream jarOut) {
      try {
         jarOut.putNextEntry(new JarEntry(entryFileName));
         PrintWriter pw = new PrintWriter(jarOut);
         pw.println("<!-- Disk Details -->");
         pw.println("<disk-details>");
         File[] roots = File.listRoots();
         File[] var5 = roots;
         int var6 = roots.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            File rootToCheck = var5[var7];
            pw.println("  <disk>");
            pw.println("    <root-path>" + rootToCheck.getPath() + "</root-path>");
            pw.println("    <total-space>" + this.getTotalSpace.invoke(rootToCheck, (Object[])null) + "</total-space>");
            pw.println("    <usable-space>" + this.getUsableSpace.invoke(rootToCheck, (Object[])null) + "</usable-space>");
            pw.println("    <free-space>" + this.getFreeSpace.invoke(rootToCheck, (Object[])null) + "</free-space>");
            pw.println("  </disk>");
         }

         pw.println("</disk-details>");
         pw.println("");
         pw.flush();
      } catch (Exception var17) {
      } finally {
         try {
            jarOut.flush();
         } catch (IOException var16) {
         }

      }

   }

   private void gatherFileDetails(String entryFileName, Properties settings, JarOutputStream jarOut) {
      try {
         FileInfo[] filesToCheck = this.determineFilesToCheck(settings);
         if (filesToCheck != null) {
            jarOut.putNextEntry(new JarEntry(entryFileName));
            PrintWriter pw = new PrintWriter(jarOut);
            pw.println("<!-- File Details -->");
            pw.println("<file-details>");
            FileInfo[] var6 = filesToCheck;
            int var7 = filesToCheck.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               FileInfo fileToCheck = var6[var8];
               pw.println("  <file>");
               pw.println("    <path>" + fileToCheck.file.getPath() + "</path>");
               pw.println("    <exists>" + fileToCheck.file.exists() + "</exists>");
               pw.println("    <is-directory>" + fileToCheck.file.isDirectory() + "</is-directory>");
               pw.println("    <is-readable>" + fileToCheck.file.canRead() + "</is-readable>");
               pw.println("    <is-writable>" + fileToCheck.file.canWrite() + "</is-writable>");
               pw.println("    <is-executable>" + fileToCheck.file.canWrite() + "</is-executable>");
               if (fileToCheck.verifyReadEntireFile) {
                  pw.println("    <readability-confirmed>" + this.readEntireFile(fileToCheck.file) + "</readability-confirmed>");
               }

               if (fileToCheck.verifyWriteIntoDirectory) {
                  pw.println("    <writeability-confirmed>" + this.writeFileIntoDirectory(fileToCheck.file) + "</writeability-confirmed>");
               }

               pw.println("  </file>");
            }

            pw.println("</file-details>");
            pw.println("");
            pw.flush();
            return;
         }
      } catch (Exception var19) {
         return;
      } finally {
         try {
            jarOut.flush();
         } catch (IOException var18) {
         }

      }

   }

   private void gatherNetworkDetails(String entryFileName, Properties settings, JarOutputStream jarOut) {
      try {
         NetAddrInfo[] netAddrsToCheck = this.determineNetAddrsToCheck(settings);
         if (netAddrsToCheck != null) {
            jarOut.putNextEntry(new JarEntry(entryFileName));
            PrintWriter pw = new PrintWriter(jarOut);
            pw.println("<!-- Network Details -->");
            pw.println("<network-details>");
            NetAddrInfo[] var6 = netAddrsToCheck;
            int var7 = netAddrsToCheck.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               NetAddrInfo netAddrToCheck = var6[var8];
               pw.println("  <inetaddress>");
               pw.println("    <host-name>" + (netAddrToCheck.hostName == null ? "" : netAddrToCheck.hostName) + "</host-name>");
               if (netAddrToCheck.addr == null) {
                  pw.println("    <host-error-message>Check for errors in settingerrors.xml</host-error-message>");
               } else {
                  pw.println("    <host-address>" + netAddrToCheck.addr.getHostAddress() + "</host-address>");
                  pw.println("    <ping-timeout-setting>" + netAddrToCheck.pingTimeout + "</ping-timeout-setting>");
                  pw.println("    <ping-rtt-average>" + df.format(this.ping(netAddrToCheck)) + "</ping-rtt-average>");
                  pw.println("    <ping-min>" + df.format(netAddrToCheck.min) + "</ping-min>");
                  pw.println("    <ping-max>" + df.format(netAddrToCheck.max) + "</ping-max>");
                  if (netAddrToCheck.pingError != null) {
                     pw.println("    <ping-error>" + netAddrToCheck.pingError + "</ping-error>");
                  }

                  if (netAddrToCheck.portToConnect > 0) {
                     pw.println("    <port-to-connect-setting>" + netAddrToCheck.portToConnect + "</port-to-connect-setting>");
                     pw.println("    <connect-timeout-setting>" + netAddrToCheck.connectTimeout + "</connect-timeout-setting>");
                     pw.println("    <connect-time>" + this.connect(netAddrToCheck) + "</connect-time>");
                     if (netAddrToCheck.connectError != null) {
                        pw.println("    <connect-error>" + netAddrToCheck.connectError + "</connect-error>");
                     }
                  }
               }

               pw.println("  </inetaddress>");
            }

            pw.println("</network-details>");
            pw.println("");
            pw.flush();
            return;
         }
      } catch (Exception var19) {
         var19.printStackTrace();
         return;
      } finally {
         try {
            jarOut.flush();
         } catch (IOException var18) {
         }

      }

   }

   private void gatherDomainHealth(String entryFileName, Properties settings, JarOutputStream jarOut) {
      try {
         if (!Kernel.isServer()) {
            return;
         }

         if (!this.isAdminServer) {
            return;
         }

         String thisServer = this.runtimeAccess.getServerName();
         DomainRuntimeServiceMBean domainService = this.domainAccess.getDomainRuntimeService();
         ServerLifeCycleRuntimeMBean[] serverLCRuntimes = this.domainRuntime.getServerLifeCycleRuntimes();
         jarOut.putNextEntry(new JarEntry(entryFileName));
         PrintWriter pw = new PrintWriter(jarOut);
         pw.println("<!-- Domain Runtime Details -->");
         pw.println("<domain-runtime-details>");
         ServerLifeCycleRuntimeMBean[] var8 = serverLCRuntimes;
         int var9 = serverLCRuntimes.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            ServerLifeCycleRuntimeMBean serverLCRuntime = var8[var10];
            String serverName = serverLCRuntime.getName();
            pw.println("  <server>");
            pw.println("    <name>" + serverName + "</name>");
            pw.println("    <state>" + serverLCRuntime.getState() + "</state>");
            ServerRuntimeMBean serverRuntime = domainService.lookupServerRuntime(serverName);
            if (serverRuntime != null) {
               ConnectivityStatistics jmxConnectivityStats = this.getJMXConnectivityStats(serverRuntime);
               pw.println("    <jmx-connectivity>");
               pw.println("        <server-name>" + jmxConnectivityStats.serverName + "</server-name>");
               pw.println("        <server-state>" + jmxConnectivityStats.serverState + "</server-state>");
               pw.println("        <rtt-min>" + df.format(jmxConnectivityStats.min) + "</rtt-min>");
               pw.println("        <rtt-max>" + df.format(jmxConnectivityStats.max) + "</rtt-max>");
               pw.println("        <rtt-average>" + df.format(jmxConnectivityStats.rttAverage) + "</rtt-average>");
               pw.println("        <rtt-values>");
               double[] var15 = jmxConnectivityStats.rawValues;
               int var16 = var15.length;

               int var17;
               for(var17 = 0; var17 < var16; ++var17) {
                  double rawValue = var15[var17];
                  pw.println("            <value>" + df.format(rawValue) + "</value>");
               }

               pw.println("        </rtt-values>");
               pw.println("        <conn-error-occurred>" + jmxConnectivityStats.errorOccurred() + "</conn-error-occurred>");
               pw.println("        <conn-error>" + (jmxConnectivityStats.connectionError == null ? "" : jmxConnectivityStats.connectionError.getMessage()) + "</conn-error>");
               pw.println("    </jmx-connectivity>");
               HealthState healthState = serverRuntime.getHealthState();
               pw.println("    <health-state>");
               pw.println("       <state>" + HealthState.mapToString(healthState.getState()) + "</state>");
               pw.println("       <is-critical>" + healthState.isCritical() + "</is-critical>");
               pw.println("       <reason-codes>");
               String[] var33 = healthState.getReasonCode();
               var17 = var33.length;

               for(int var34 = 0; var34 < var17; ++var34) {
                  String reason = var33[var34];
                  pw.println("       <reason>" + reason + "</reason>");
               }

               pw.println("       </reason-codes>");
               pw.println("    </health-state>");
            }

            pw.println("  </server>");
         }

         pw.println("</domain-runtime-details>");
         pw.println("");
         pw.flush();
      } catch (Exception var30) {
         var30.printStackTrace();
      } finally {
         try {
            jarOut.flush();
         } catch (IOException var29) {
         }

      }

   }

   private ConnectivityStatistics getJMXConnectivityStats(ServerRuntimeMBean serverRuntime) {
      ConnectivityStatistics stats = new ConnectivityStatistics(serverRuntime.getName());
      stats.rawValues = new double[this.maxPingIterations];

      try {
         for(int i = 0; i < this.maxPingIterations; ++i) {
            double beforeCallTime = (double)System.nanoTime();
            stats.serverState = serverRuntime.getState();
            double elapsedTimeNanos = (double)System.nanoTime() - beforeCallTime;
            if (!"RUNNING".equals(stats.serverState)) {
               break;
            }

            double millis = elapsedTimeNanos / 1000000.0;
            stats.rawValues[i] = millis;
            this.computeInterimStats(stats, millis);
         }
      } catch (Throwable var10) {
         stats.connectionError = var10;
      }

      stats.rttAverage = stats.rttSum / (double)this.maxPingIterations;
      return stats;
   }

   private double ping(NetAddrInfo netInfo) {
      try {
         for(int i = 0; i < this.maxPingIterations; ++i) {
            double start = (double)System.nanoTime();
            netInfo.addr.isReachable(netInfo.pingTimeout);
            double duration = (double)System.nanoTime() - start;
            double durationMillis = duration / 1000000.0;
            this.computeInterimStats(netInfo, durationMillis);
         }

         netInfo.pingAverage = netInfo.rttSum / (double)this.maxPingIterations;
      } catch (Exception var9) {
         netInfo.pingError = var9.toString();
         netInfo.pingAverage = -1.0;
      }

      return netInfo.pingAverage;
   }

   private void traceRoute(NetAddrInfo netInfo) {
      ArrayList route = new ArrayList();

      try {
         Process traceProc;
         if (os.toLowerCase().contains("win")) {
            traceProc = Runtime.getRuntime().exec("tracert " + netInfo.addr.getHostAddress());
         } else {
            traceProc = Runtime.getRuntime().exec("traceroute " + netInfo.addr.getHostAddress());
         }

         BufferedReader reader = new BufferedReader(new InputStreamReader(traceProc.getInputStream()));
         String current = null;
         System.out.println("traceroute: " + netInfo.addr);

         while(true) {
            current = reader.readLine();
            if (current == null) {
               break;
            }

            route.add(current);
            System.out.println(current);
         }
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      if (route.size() != 0) {
         String[] routeArr = new String[route.size()];
         netInfo.traceRoute = (String[])route.toArray(routeArr);
      }
   }

   private long connect(NetAddrInfo netInfo) {
      Socket sock = null;

      long var4;
      try {
         long start = System.currentTimeMillis();
         InetSocketAddress inetSockAddr = new InetSocketAddress(netInfo.addr, netInfo.portToConnect);
         sock = new Socket();
         sock.connect(inetSockAddr, netInfo.connectTimeout);
         long duration = System.currentTimeMillis() - start;
         long var8 = duration;
         return var8;
      } catch (Exception var19) {
         netInfo.connectError = var19.toString();
         var4 = -1L;
      } finally {
         try {
            if (sock != null) {
               sock.close();
            }
         } catch (Exception var18) {
         }

      }

      return var4;
   }

   private boolean writeFileIntoDirectory(File theFile) {
      try {
         if (!theFile.isDirectory()) {
            return false;
         } else {
            File tmpFile = File.createTempFile("__tmp", ".healthWriteCheck", theFile);
            FileOutputStream outStr = new FileOutputStream(tmpFile);
            byte[] buf = new byte[]{5, 6, 7, 8};
            outStr.write(buf);
            outStr.flush();
            outStr.close();
            tmpFile.delete();
            return true;
         }
      } catch (Exception var5) {
         return false;
      }
   }

   private boolean readEntireFile(File theFile) {
      try {
         FileInputStream in = new FileInputStream(theFile);
         Chunk c = Chunk.getChunk();
         byte[] buf = c.buf;

         try {
            while(true) {
               if (in.read(buf, 0, Chunk.CHUNK_SIZE) > -1) {
                  continue;
               }
            }
         } finally {
            Chunk.releaseChunk(c);
            in.close();
         }

         return true;
      } catch (Exception var10) {
         return false;
      }
   }

   private NetAddrInfo[] determineNetAddrsToCheck(Properties settings) {
      ArrayList networkAddrInfos = new ArrayList();
      this.parseNetAddrSettings(networkAddrInfos, settings);
      this.contributeNetAddrsFromConfig(networkAddrInfos);
      return networkAddrInfos.isEmpty() ? null : (NetAddrInfo[])networkAddrInfos.toArray(new NetAddrInfo[networkAddrInfos.size()]);
   }

   private FileInfo[] determineFilesToCheck(Properties settings) {
      ArrayList fileInfos = new ArrayList();
      this.parseFileSettings(fileInfos, settings);
      contributeFilesFromConfig(fileInfos);
      return fileInfos.isEmpty() ? null : (FileInfo[])fileInfos.toArray(new FileInfo[fileInfos.size()]);
   }

   private void parseFileSettings(ArrayList fileInfos, Properties settings) {
      if (settings != null) {
         Enumeration e = settings.propertyNames();

         while(true) {
            while(true) {
               while(true) {
                  String propName;
                  do {
                     if (!e.hasMoreElements()) {
                        return;
                     }

                     propName = (String)e.nextElement();
                  } while(propName == null);

                  String fileName;
                  if (propName.startsWith("checkFileExists")) {
                     fileName = settings.getProperty(propName);
                     if (fileName != null && fileName.length() != 0) {
                        fileInfos.add(new FileInfo(fileName, false, false));
                     } else {
                        this.settingErrors.add(new SettingError(propName, fileName, "null or empty file name specified"));
                     }
                  } else if (propName.startsWith("checkFileReadable")) {
                     fileName = settings.getProperty(propName);
                     if (fileName != null && fileName.length() != 0) {
                        fileInfos.add(new FileInfo(fileName, true, false));
                     } else {
                        this.settingErrors.add(new SettingError(propName, fileName, "null or empty file name specified"));
                     }
                  } else if (propName.startsWith("checkDirectoryWriteable")) {
                     fileName = settings.getProperty(propName);
                     if (fileName != null && fileName.length() != 0) {
                        fileInfos.add(new FileInfo(fileName, false, true));
                     } else {
                        this.settingErrors.add(new SettingError(propName, fileName, "null or empty file name specified"));
                     }
                  }
               }
            }
         }
      }
   }

   private void parseNetAddrSettings(ArrayList netInfos, Properties settings) {
      if (settings != null) {
         Enumeration en = settings.propertyNames();

         while(true) {
            while(true) {
               while(true) {
                  String propName;
                  do {
                     do {
                        if (!en.hasMoreElements()) {
                           return;
                        }

                        propName = (String)en.nextElement();
                     } while(propName == null);
                  } while(!propName.startsWith("checkNetwork"));

                  String propValue = settings.getProperty(propName);
                  if (propValue != null && propValue.length() != 0) {
                     StringTokenizer st = new StringTokenizer(propValue, "|");
                     int tokens = st.countTokens();
                     if (tokens != 4) {
                        this.settingErrors.add(new SettingError(propName, propValue, "unexpected format: expect |HostOrIPAddress|[pingTimeout]|[port]|[connectTimeout]|[pingIterations] use whitespace for empty optional items"));
                     } else {
                        String host = st.nextToken();
                        InetAddress[] allAddrs = null;

                        try {
                           allAddrs = InetAddress.getAllByName(host);
                           if (allAddrs == null || allAddrs.length == 0) {
                              this.settingErrors.add(new SettingError(propName, propValue, "No addresses found for hostname: " + host));
                              continue;
                           }
                        } catch (Exception var20) {
                           this.settingErrors.add(new SettingError(propName, propValue, "problem with hostname value: " + host + ": " + var20.toString()));
                           continue;
                        }

                        String token = st.nextToken();
                        this.pingTimeout = 5000;
                        if (token != null && token.length() > 0 && token.trim().length() > 0) {
                           try {
                              this.pingTimeout = Integer.parseInt(token);
                           } catch (Exception var19) {
                              this.settingErrors.add(new SettingError(propName, propValue, "problem with pingTimeout value, needs to be int format, found: " + token));
                              continue;
                           }
                        }

                        token = st.nextToken();
                        int port = -1;
                        if (token != null && token.length() > 0 && token.trim().length() > 0) {
                           try {
                              port = Integer.parseInt(token);
                           } catch (Exception var18) {
                              this.settingErrors.add(new SettingError(propName, propValue, "problem with port value, needs to be int format, found: " + token));
                              continue;
                           }
                        }

                        token = st.nextToken();
                        this.connectTimeout = 5000;
                        if (token != null && token.length() > 0 && token.trim().length() > 0) {
                           try {
                              this.connectTimeout = Integer.parseInt(token);
                           } catch (Exception var17) {
                              this.settingErrors.add(new SettingError(propName, propValue, "problem with connectTimeout value, needs to be int format, found: " + token));
                              continue;
                           }
                        }

                        token = st.nextToken();
                        this.maxPingIterations = 4;
                        if (token != null && token.length() > 0 && token.trim().length() > 0) {
                           try {
                              this.maxPingIterations = Integer.parseInt(token);
                           } catch (NumberFormatException var16) {
                           }
                        }

                        InetAddress[] var12 = allAddrs;
                        int var13 = allAddrs.length;

                        for(int var14 = 0; var14 < var13; ++var14) {
                           InetAddress addr = var12[var14];
                           netInfos.add(new NetAddrInfo(host, addr, this.pingTimeout, port, this.connectTimeout));
                        }
                     }
                  } else {
                     this.settingErrors.add(new SettingError(propName, propValue, "null or empty network property specified"));
                  }
               }
            }
         }
      }
   }

   private void contributeNetAddrsFromConfig(ArrayList netInfos) {
      if (Kernel.isServer()) {
         DomainMBean domain = this.runtimeAccess.getDomain();
         if (this.isAdminServer) {
            DomainRuntimeMBean domainRuntime = this.domainAccess.getDomainRuntime();
            ServerLifeCycleRuntimeMBean[] var4 = domainRuntime.getServerLifeCycleRuntimes();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               ServerLifeCycleRuntimeMBean slc = var4[var6];
               ServerMBean serverMBean = domain.lookupServer(slc.getName());
               this.addServerNetAddrs(netInfos, serverMBean);
            }
         } else {
            ServerMBean adminServerMBean = domain.lookupServer(this.runtimeAccess.getAdminServerName());
            this.addServerNetAddrs(netInfos, adminServerMBean);
         }

      }
   }

   private void addServerNetAddrs(ArrayList netInfos, ServerMBean serverMBean) {
      try {
         String serverAddress = serverMBean.getListenAddress();
         InetAddress[] addrArray = InetAddress.getAllByName(serverAddress);
         InetAddress[] var5 = addrArray;
         int var6 = addrArray.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            InetAddress addr = var5[var7];
            netInfos.add(new NetAddrInfo(serverAddress, addr, this.pingTimeout, -1, this.connectTimeout));
         }
      } catch (UnknownHostException var9) {
         this.settingErrors.add(new SettingError(serverMBean.getName(), "hostname", var9.getMessage()));
      }

   }

   private static void contributeFilesFromConfig(ArrayList fileInfos) {
      if (Kernel.isServer()) {
         ;
      }
   }

   private static Properties determineOfflineProperties(String configXMLFileName) throws Exception {
      Properties props = new Properties();
      if (Kernel.isServer()) {
         return props;
      } else {
         XMLStreamReader xmlReader = XXEUtils.createXMLInputFactoryInstance().createXMLStreamReader(new FileInputStream(configXMLFileName));
         ParseInfo parseInfo = new ParseInfo();

         while(xmlReader.hasNext()) {
            int state = xmlReader.next();
            switch (state) {
               case 1:
                  parseInfo.handleStartElement(xmlReader);
               case 2:
               case 3:
               case 5:
               case 6:
               case 7:
               case 8:
               case 9:
               case 10:
               case 11:
               case 12:
               case 13:
                  break;
               case 4:
                  parseInfo.handleCharacters(xmlReader);
                  break;
               default:
                  System.out.println("UNKNOWN STATE???");
            }
         }

         parseInfo.addProperties(props);
         dumpProperties(props);
         return props;
      }
   }

   private static void dumpProperties(Properties props) {
      try {
         props.store(System.out, "Properties defined: " + props.size());
      } catch (Exception var2) {
      }

   }

   private void computeInterimStats(NetAddrInfo netInfo, double elapsedTimeMillis) {
      netInfo.rttSum += elapsedTimeMillis;
      netInfo.max = Math.max(elapsedTimeMillis, netInfo.max);
      netInfo.min = Math.min(elapsedTimeMillis, netInfo.min);
   }

   private void computeInterimStats(ConnectivityStatistics state, double elapsedTimeMillis) {
      state.rttSum += elapsedTimeMillis;
      state.min = Math.min(elapsedTimeMillis, state.min);
      state.max = Math.max(elapsedTimeMillis, state.max);
   }

   public static void main(String[] args) throws Exception {
      if (Kernel.isServer()) {
         throw new Exception("Not allowed within server");
      } else {
         calledFromMain = true;
         HealthSource source = getInstance();
         if (args.length != 2) {
            System.out.println("usage: java weblogic.diagnostics.image.HealtSource result-file configxmlfile-or-configdirectory-or-properties");
         } else {
            Properties props = null;
            File inputFile = new File(args[1]);
            long start;
            if (inputFile.isDirectory()) {
               start = System.currentTimeMillis();
               props = determineOfflineProperties(args[1] + File.separator + "config.xml");
               System.out.println("Time to determine properties: " + (System.currentTimeMillis() - start));
            } else if (args[1].endsWith(".xml")) {
               start = System.currentTimeMillis();
               props = determineOfflineProperties(args[1]);
               System.out.println("Time to determine properties: " + (System.currentTimeMillis() - start));
            } else {
               FileInputStream in = new FileInputStream(inputFile);
               props = new Properties();
               props.load(in);
               in.close();
            }

            start = System.currentTimeMillis();
            System.out.println("gatherDataToFile returns: " + source.gatherDataToFile(args[0], props));
            System.out.println("Time to gather data: " + (System.currentTimeMillis() - start));
         }
      }
   }

   private static class ServerInfo {
      String serverName;
      String listenAddress;
      String listenPort;

      private ServerInfo() {
         this.serverName = null;
         this.listenAddress = "localhost";
         this.listenPort = "7001";
      }

      private String getPropValueString() {
         return "|" + this.listenAddress + "| |" + this.listenPort + "| |";
      }

      // $FF: synthetic method
      ServerInfo(Object x0) {
         this();
      }
   }

   private static class ParseInfo {
      private static final int EXPECT_NOTHING = 0;
      private static final int EXPECT_ADMIN_PORT_ENABLED = 1;
      private static final int EXPECT_ADDRESS_VALUE = 2;
      private static final int EXPECT_PORT_VALUE = 3;
      private static final int EXPECT_FILE_NAME = 4;
      private int expecting;
      private boolean isAdminPortEnabled;
      private ArrayList servers;
      private ArrayList fileLocations;

      private ParseInfo() {
         this.expecting = 0;
         this.isAdminPortEnabled = false;
         this.servers = new ArrayList();
         this.fileLocations = new ArrayList();
      }

      private void handleStartElement(XMLStreamReader xmlReader) {
         String elementName = xmlReader.getName().toString();
         if ("{http://xmlns.oracle.com/weblogic/domain}administration-port-enabled".equals(elementName)) {
            this.expecting = 1;
         } else if (this.matchesFileReadCheck(elementName)) {
            this.expecting = 4;
         } else if (this.matchesNetworkAddress(elementName)) {
            this.expecting = 2;
         } else if (this.matchesNetworkPort(elementName)) {
            this.expecting = 3;
         } else {
            this.expecting = 0;
         }

      }

      private void handleCharacters(XMLStreamReader xmlReader) {
         if (this.expecting != 0) {
            if (!xmlReader.hasText()) {
               this.expecting = 0;
            } else {
               String text = xmlReader.getText();
               if (text != null && text.trim().length() != 0) {
                  if (this.expecting == 4) {
                     this.addFileEntry(text);
                  } else if (this.expecting == 2) {
                     this.setListenAddress(text);
                  } else if (this.expecting == 3) {
                     this.setListenPort(text);
                  } else if (this.expecting == 1) {
                     this.isAdminPortEnabled = Boolean.parseBoolean(text);
                  }

                  this.expecting = 0;
               } else {
                  this.expecting = 0;
               }
            }
         }
      }

      private boolean matchesFileReadCheck(String elementName) {
         return "{http://xmlns.oracle.com/weblogic/domain}custom-trust-key-store-file-name".equals(elementName) || "{http://xmlns.oracle.com/weblogic/domain}custom-identity-key-store-file-name".equals(elementName) || "{http://xmlns.oracle.com/weblogic/domain}source-path".equals(elementName) || "{http://xmlns.oracle.com/weblogic/domain}plan-path".equals(elementName) || "{http://xmlns.oracle.com/weblogic/domain}descriptor-file-name".equals(elementName);
      }

      private boolean matchesNetworkAddress(String elementName) {
         return "{http://xmlns.oracle.com/weblogic/domain}listen-address".equals(elementName) || "{http://xmlns.oracle.com/weblogic/security/wls}host".equals(elementName);
      }

      private boolean matchesNetworkPort(String elementName) {
         if ("{http://xmlns.oracle.com/weblogic/domain}administration-port".equals(elementName)) {
            return this.isAdminPortEnabled;
         } else {
            return "{http://xmlns.oracle.com/weblogic/domain}listen-port".equals(elementName) || "{http://xmlns.oracle.com/weblogic/security/wls}port".equals(elementName);
         }
      }

      private void addFileEntry(String fileName) {
         System.out.println("addFileEntry(" + fileName + ")");
         this.fileLocations.add(fileName);
      }

      private void setListenAddress(String listenAddress) {
         System.out.println("setListenAddress(" + listenAddress + ")");
         ServerInfo newServer = new ServerInfo();
         newServer.listenAddress = listenAddress;
         this.servers.add(newServer);
      }

      private void setListenPort(String listenPort) {
         System.out.println("setListenPort(" + listenPort + ")");
         if (this.servers.size() != 0) {
            int currentIndex = this.servers.size() - 1;
            ((ServerInfo)this.servers.get(currentIndex)).listenPort = listenPort;
         }
      }

      private void addProperties(Properties props) {
         System.out.println("addProperties, servers size: " + this.servers.size() + ", files size: " + this.fileLocations.size());
         Iterator var2;
         if (this.servers.size() > 0) {
            var2 = this.servers.iterator();

            while(var2.hasNext()) {
               ServerInfo info = (ServerInfo)var2.next();
               props.setProperty("checkNetworkOFFLINEGEN" + HealthSource.generatedPropCount++, info.getPropValueString());
            }
         }

         if (this.fileLocations.size() > 0) {
            var2 = this.fileLocations.iterator();

            while(var2.hasNext()) {
               String fileName = (String)var2.next();
               props.setProperty("checkFileReadableOFFLINEGEN" + HealthSource.generatedPropCount++, fileName);
            }
         }

      }

      // $FF: synthetic method
      ParseInfo(Object x0) {
         this();
      }
   }

   private static class SettingError {
      String propName;
      String propValue;
      String error;

      private SettingError(String propName, String propValue, String error) {
         this.propName = null;
         this.propValue = null;
         this.error = null;
         this.propName = propName;
         this.propValue = propValue;
         this.error = error;
      }

      // $FF: synthetic method
      SettingError(String x0, String x1, String x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private static class NetAddrInfo {
      String pingError;
      String connectError;
      String hostName;
      InetAddress addr;
      int portToConnect;
      int connectTimeout;
      int pingTimeout;
      String[] traceRoute;
      double rttSum;
      double min;
      double max;
      double pingAverage;

      private NetAddrInfo(String hostName, InetAddress addr, int pingTimeout, int portToConnect, int connectTimeout) {
         this.pingError = null;
         this.connectError = null;
         this.hostName = null;
         this.addr = null;
         this.portToConnect = -1;
         this.connectTimeout = 5000;
         this.pingTimeout = 5000;
         this.traceRoute = null;
         this.min = 9.223372036854776E18;
         this.hostName = hostName;
         this.addr = addr;
         this.pingTimeout = pingTimeout >= 0 ? pingTimeout : 0;
         this.portToConnect = portToConnect;
         this.connectTimeout = connectTimeout >= 0 ? connectTimeout : 0;
      }

      // $FF: synthetic method
      NetAddrInfo(String x0, InetAddress x1, int x2, int x3, int x4, Object x5) {
         this(x0, x1, x2, x3, x4);
      }
   }

   private static class FileInfo {
      boolean verifyReadEntireFile;
      boolean verifyWriteIntoDirectory;
      File file;

      private FileInfo(String fileName, boolean verifyReadEntireFile, boolean verifyWriteIntoDirectory) {
         this.verifyReadEntireFile = false;
         this.verifyWriteIntoDirectory = false;
         this.file = null;
         this.file = new File(fileName);
         this.verifyReadEntireFile = verifyReadEntireFile;
         this.verifyWriteIntoDirectory = verifyWriteIntoDirectory;
      }

      // $FF: synthetic method
      FileInfo(String x0, boolean x1, boolean x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private class ConnectivityStatistics {
      String serverName;
      String serverState;
      double rttAverage;
      double min = 9.223372036854776E18;
      double max = -1.0;
      double rttSum;
      double[] rawValues;
      Throwable connectionError;

      public ConnectivityStatistics(String name) {
         this.serverName = name;
      }

      public boolean errorOccurred() {
         return this.connectionError != null;
      }
   }
}
