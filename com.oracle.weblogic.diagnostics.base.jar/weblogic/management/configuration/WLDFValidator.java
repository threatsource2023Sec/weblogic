package weblogic.management.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;
import weblogic.management.DomainDir;
import weblogic.utils.ArrayUtils;
import weblogic.utils.PlatformConstants;

@Service
@ContractsProvided({DomainMBeanValidator.class})
public class WLDFValidator implements DomainMBeanValidator {
   private static final DiagnosticsTextTextFormatter DIAG_TXT_FMT = DiagnosticsTextTextFormatter.getInstance();

   public static void validateWLDFServerDiagnosticConfiguration(DomainMBean domain) {
      ServerMBean[] servers = domain.getServers();

      for(int i = 0; i < servers.length; ++i) {
         WLDFServerDiagnosticMBean wldfConfig = servers[i].getServerDiagnosticConfig();
         if (wldfConfig != null) {
            String archiveType = wldfConfig.getDiagnosticDataArchiveType();
            if (archiveType.equals("JDBCArchive")) {
               JDBCSystemResourceMBean jdbcResource = wldfConfig.getDiagnosticJDBCResource();
               if (jdbcResource == null) {
                  throw new IllegalArgumentException(DIAG_TXT_FMT.getNoJDBCSystemResourceConfiguredText(servers[i].getName()));
               }

               TargetMBean[] targets = jdbcResource.getTargets();
               if (targets == null) {
                  throw new IllegalArgumentException(DIAG_TXT_FMT.getJDBCSystemResourceNotTargettedToServer(servers[i].getName(), jdbcResource.getName()));
               }

               ServerMBean[] targetServers = getServersInTargets(targets);
               HashSet serverNames = new HashSet();

               for(int j = 0; j < targets.length; ++j) {
                  serverNames.add(targets[j].getName());
               }

               if (!serverNames.contains(servers[i].getName())) {
                  throw new IllegalArgumentException(DIAG_TXT_FMT.getJDBCSystemResourceNotTargettedToServer(servers[i].getName(), jdbcResource.getName()));
               }
            }

            validateDataRetirements(wldfConfig);
         }
      }

   }

   private static void validateDataRetirements(WLDFServerDiagnosticMBean wldfConfig) {
      HashMap map = new HashMap();
      WLDFDataRetirementMBean[] retirements = wldfConfig.getWLDFDataRetirements();
      int cnt = retirements != null ? retirements.length : 0;

      for(int i = 0; i < cnt; ++i) {
         String archiveName = retirements[i].getArchiveName();
         if (archiveName != null) {
            archiveName = archiveName.trim();
         }

         if (archiveName != null && archiveName.length() > 0) {
            Integer val = (Integer)map.get(archiveName);
            int intVal = val != null ? val : 0;
            map.put(archiveName, new Integer(intVal + 1));
         }
      }

      String names = null;
      Iterator it = map.keySet().iterator();

      while(it.hasNext()) {
         String archiveName = (String)it.next();
         Integer val = (Integer)map.get(archiveName);
         int intVal = val != null ? val : 0;
         if (intVal > 1) {
            if (names == null) {
               names = archiveName;
            } else {
               names = names + "," + archiveName;
            }
         }
      }

      if (names != null) {
         throw new IllegalArgumentException(DIAG_TXT_FMT.getDuplicateRetirementsErrorText(names));
      }
   }

   public static ServerMBean[] getServersInTargets(TargetMBean[] targets) {
      ArrayList servers = new ArrayList();

      for(int i = 0; i < targets.length; ++i) {
         TargetMBean target = targets[i];
         if (target instanceof ServerMBean) {
            servers.add(target);
         } else {
            if (!(target instanceof ClusterMBean)) {
               throw new AssertionError("The list of targets contained a non-server or a non-cluster member");
            }

            ClusterMBean cluster = (ClusterMBean)target;
            ArrayUtils.addAll(servers, cluster.getServers());
         }
      }

      ServerMBean[] result = new ServerMBean[servers.size()];
      servers.toArray(result);
      return result;
   }

   public static void validateDataRetirementArchiveName(WLDFDataRetirementMBean mbean, String name) throws IllegalArgumentException {
      ConfigurationValidator.validateName(name);
      WLDFServerDiagnosticMBean wldfConf = (WLDFServerDiagnosticMBean)mbean.getParent();
      WLDFDataRetirementMBean[] retirements = wldfConf != null ? wldfConf.getWLDFDataRetirements() : null;
      int cnt = retirements != null ? retirements.length : 0;

      for(int i = 0; i < cnt; ++i) {
         WLDFDataRetirementMBean mb = retirements[i];
         if (!mb.getName().equals(mbean.getName()) && name.equals(mb.getArchiveName())) {
            throw new IllegalArgumentException(DIAG_TXT_FMT.getDuplicateRetirementsErrorText(name));
         }
      }

      if (!name.equals("HarvestedDataArchive") && !name.equals("EventsDataArchive") && !name.startsWith("CUSTOM/")) {
         throw new IllegalArgumentException(DIAG_TXT_FMT.getInvalidArchiveNameForDataRetirementText(name, "HarvestedDataArchive | EventsDataArchive | CUSTOM/xxx"));
      }
   }

   public static void validateDataRetirementTime(WLDFDataRetirementMBean mbean, int retirementTime) throws IllegalArgumentException {
      if (retirementTime < 0 || retirementTime > 23) {
         throw new IllegalArgumentException(DIAG_TXT_FMT.getInvalidRetirementTimeText(mbean.getName(), retirementTime));
      }
   }

   public static void validateRelativePath(WLDFServerDiagnosticMBean diagMBean, String path) {
      File dir = new File(path);
      ServerMBean server = (ServerMBean)diagMBean.getParent();
      String serverDirPath = DomainDir.getDirForServer(server.getName());
      File serverDir = new File(serverDirPath);
      if (!dir.isAbsolute()) {
         dir = new File(serverDir, path);
      }

      try {
         serverDir = serverDir.getCanonicalFile();
         dir = dir.getCanonicalFile();
      } catch (IOException var8) {
         throw new IllegalArgumentException(var8.getMessage(), var8);
      }

      String absolutePath = dir.getAbsolutePath();
      String absoluteServerPath = serverDir.getAbsolutePath() + PlatformConstants.FILE_SEP;
      if (!absolutePath.startsWith(absoluteServerPath)) {
         throw new IllegalArgumentException(DIAG_TXT_FMT.getInvalidDumpDirectory(absolutePath, absoluteServerPath));
      }
   }

   public void validate(DomainMBean domain) {
      validateWLDFServerDiagnosticConfiguration(domain);
   }
}
