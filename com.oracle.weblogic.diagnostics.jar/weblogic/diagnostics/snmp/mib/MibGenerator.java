package weblogic.diagnostics.snmp.mib;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.diagnostics.snmp.agent.SNMPAgent;
import weblogic.diagnostics.snmp.agent.SNMPAgentToolkit;
import weblogic.diagnostics.snmp.agent.SNMPAgentToolkitException;
import weblogic.diagnostics.snmp.agent.SNMPUtil;
import weblogic.health.HealthState;
import weblogic.i18n.logging.NonCatalogLogger;
import weblogic.management.provider.ManagementServiceClient;
import weblogic.management.provider.beaninfo.BeanInfoAccess;
import weblogic.utils.Getopt2;

public class MibGenerator implements MibConstants {
   private static final boolean DEBUG = true;
   private static final String HELP_OPTION = "help";
   private static final String VERBOSE_OPTION = "verbose";
   private static final String MIB_METADATA = "mibMetadata";
   private static final String COMPILED_MIB_DIR = "compiledMibDir";
   private static final String MIB_PORT = "port";
   private static Set supportedPackages = new HashSet();
   private static Set excludedAttributes = new HashSet();
   private static Set ignoredAttributes = new HashSet();
   private static Set supportedClasses = new HashSet();
   private static final NonCatalogLogger LOGGER;
   private static final String ROOT_OID = ".1.3.6.1.4.1.140.625";
   private static final int INCREMENT = 5;
   private static final int DEFAULT_MIB_GEN_PORT = 1160;

   public static void main(String[] args) throws Exception {
      Getopt2 options = new Getopt2();
      options.setUsageFooter("MibGenerator is a command line tool to generate the ASN1 file for WebLogic from WLS config and runtime MBean interfaces.");
      options.setFailOnUnrecognizedOpts(true);
      options.addFlag("help", "Prints help about supported options and flags");
      options.addFlag("verbose", "Prints additional output during execution");
      options.addOption("mibMetadata", "MIB metadata dat file", "WLS MIB metadata serialized file containing mapping of MBean type and attribute names to Snmp table and columns names.");
      options.addOption("compiledMibDir", "Directory path for compiled MIB", "Directory path containing BEA-WEBLOGIC-MIB.XML generated from the ASN1 file using Monfox MIB compiler.");
      options.addOption("port", "Listen port for SNMP Agent", "UDP Listen port for the SNMP Agent, defaults to 1160.");
      options.setUsageArgs("[ASN1 output file path]");

      try {
         options.grok(args);
      } catch (IllegalArgumentException var31) {
         logException("Invalid command line", var31);
         printUsageAndExit(options);
      }

      if (options.containsOption("help")) {
         printUsageAndExit(options);
      }

      boolean verbose = options.containsOption("verbose");
      if (verbose) {
         System.setProperty("weblogic.log.StdoutSeverity", "Debug");
      }

      int udpListenPort = 1160;
      if (options.containsOption("port")) {
         udpListenPort = Integer.parseInt(options.getOption("port"));
      }

      args = options.args();
      String fileName = args != null && args.length > 0 ? args[0] : "BEA-WEBLOGIC-MIB.asn1";
      LOGGER.notice("MIB output file specified " + fileName);
      WLSMibMetadata metadata = null;
      if (options.containsOption("mibMetadata")) {
         String metadataPath = options.getOption("mibMetadata");
         FileInputStream fis = new FileInputStream(metadataPath);
         BufferedInputStream bufferedInput = new BufferedInputStream(fis);

         try {
            metadata = WLSMibMetadata.loadResource(bufferedInput);
         } finally {
            bufferedInput.close();
         }
      } else {
         metadata = WLSMibMetadata.loadResource();
      }

      SNMPUtil.initializeTypeNamePrefixes(SUBSYSTEM_PREFIXES);
      SNMPAgent agent = new SNMPAgent();
      agent.setUdpListenPort(udpListenPort);
      if (options.containsOption("compiledMibDir")) {
         String compiledMibDir = options.getOption("compiledMibDir");
         agent.setMibBasePath(compiledMibDir);
      }

      agent.initialize();
      SNMPAgentToolkit toolkit = agent.getSNMPAgentToolkit();
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date());
      String stringDate = new String("" + cal.get(1));
      if (cal.get(2) + 1 < 10) {
         stringDate = stringDate + "0";
      }

      stringDate = stringDate + (cal.get(2) + 1);
      if (cal.get(5) < 10) {
         stringDate = stringDate + "0";
      }

      stringDate = stringDate + cal.get(5);
      if (cal.get(11) < 10) {
         stringDate = stringDate + "0";
      }

      stringDate = stringDate + cal.get(11);
      if (cal.get(12) < 10) {
         stringDate = stringDate + "0";
      }

      stringDate = stringDate + cal.get(12);
      stringDate = stringDate + "Z";
      toolkit.addModuleIdentityInfo(stringDate, "BEA Systems Inc.", "www.bea.com", "BEA", "BEA-WEBLOGIC-MIB", ".1.3.6.1.4.1.140.625", "wls");
      toolkit.createSNMPMibTables("BEA-WEBLOGIC-MIB");
      long tableIndex = toolkit.getMaxLastOid("BEA-WEBLOGIC-MIB", "Table");
      tableIndex += 5L;
      LOGGER.debug("Starting index = " + tableIndex);
      BeanInfoAccess beanInfoAccess = ManagementServiceClient.getBeanInfoAccess();
      String[] types = beanInfoAccess.getSubtypes((String)null);

      for(int i = 0; i < types.length; ++i) {
         String type = types[i];
         if (!type.endsWith("MBean")) {
            LOGGER.debug("Not supported " + type);
         } else {
            try {
               Class clz = Class.forName(type);
               String packageName = clz.getPackage().getName();
               if (!supportedPackages.contains(packageName)) {
                  continue;
               }
            } catch (Exception var32) {
               LOGGER.debug("Type not found, ignoring.", var32);
               continue;
            }

            String[] subTypes = beanInfoAccess.getSubtypes(type);
            boolean hasSubTypes = false;
            if (subTypes.length > 1) {
               List l = new ArrayList();
               Collections.addAll(l, subTypes);
               LOGGER.debug("Found subtypes " + l + " for type " + type);
               hasSubTypes = true;
            }

            LOGGER.debug("Found type " + type);
            BeanInfo bi = beanInfoAccess.getBeanInfoForInterface(types[i], false, (String)null);
            Object deprecated = bi.getBeanDescriptor().getValue("deprecated");
            if (deprecated != null) {
               LOGGER.debug("Deprecated type " + type + " " + deprecated);
            } else {
               Boolean abstractType = (Boolean)bi.getBeanDescriptor().getValue("abstract");
               if (abstractType != null && abstractType) {
                  LOGGER.debug("Abstract type " + type);
               } else {
                  Boolean excludedType = (Boolean)bi.getBeanDescriptor().getValue("exclude");
                  if (excludedType != null && excludedType) {
                     LOGGER.debug("Excluded type " + type);
                  } else {
                     String obsoleteValue = (String)bi.getBeanDescriptor().getValue("obsolete");
                     if (obsoleteValue != null && obsoleteValue.length() > 0) {
                        LOGGER.debug("Obsolete type " + type + " " + obsoleteValue);
                     } else {
                        List ps = getPropertyDescriptorsForSNMP(type, bi);
                        String tableName = metadata.getSNMPTableName(type);
                        if (!isTableNameRemoved(tableName)) {
                           String tableDesc = bi.getBeanDescriptor().getShortDescription();
                           tableDesc = SNMPUtil.stripHtmlTags(tableDesc);
                           if (tableName == null && !hasSubTypes) {
                              LOGGER.debug("Missing type from mib " + type);
                              tableName = getTableNameForType(type);
                              if (!isTableNameRemoved(tableName)) {
                                 LOGGER.debug("Defining table " + tableName);
                                 String tableOid = ".1.3.6.1.4.1.140.625." + tableIndex;
                                 tableIndex += 5L;
                                 LOGGER.debug("Table OID for " + tableName + " = " + tableOid);
                                 toolkit.createSNMPTable("BEA-WEBLOGIC-MIB", tableName, tableDesc, tableOid, ps);
                              }
                           } else {
                              toolkit.updateSNMPTable("BEA-WEBLOGIC-MIB", tableName, tableDesc);
                              updateAttributesForType(toolkit, metadata, tableName, ps);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      String mib = toolkit.outputMIBModule("BEA-WEBLOGIC-MIB");
      LOGGER.notice("Writing MIB output file " + fileName);
      FileOutputStream fos = new FileOutputStream(fileName);
      OutputStreamWriter osw = new OutputStreamWriter(fos);
      BufferedWriter buf = new BufferedWriter(osw);
      buf.write(mib);
      buf.flush();
      buf.close();
   }

   private static boolean isTableNameRemoved(String tableName) {
      return WLSMibMetadataBuilder.isTableNameRemoved(tableName);
   }

   private static void updateAttributesForType(SNMPAgentToolkit toolkit, WLSMibMetadata metadata, String tableName, List snmpAttrs) throws SNMPAgentToolkitException {
      WLSMibTableColumnsMetadata colsMetadata = metadata.getColumnsMetadataForSNMPTable(tableName);
      if (colsMetadata == null) {
         LOGGER.debug("No metadata found for table " + tableName);
      } else {
         Map attrMap = colsMetadata.getAttributeColumnMap();
         Iterator i = snmpAttrs.iterator();

         String propName;
         String snmpColName;
         while(i.hasNext()) {
            PropertyDescriptor pd = (PropertyDescriptor)i.next();
            propName = pd.getName();
            snmpColName = SNMPUtil.getColumnNameForAttribute(tableName, propName);
            LOGGER.debug("Missing attribute " + propName + " snmp col name = " + snmpColName);
            if (snmpColName.length() > 64) {
               LOGGER.debug("SNMP Column name " + snmpColName + " length is larger than 64 characters, looking for an OVERRIDEN name");
               if (WLSMibMetadataBuilder.isColumnNameOverridden(snmpColName)) {
                  snmpColName = WLSMibMetadataBuilder.getColumnNameOverride(snmpColName);
               } else {
                  LOGGER.error("Length of SNMP attribute " + snmpColName + " is greater than 64 characters in violation of SNMP standard. Please add shorter name override to the WLSMibMetadata.OVERRIDES map");
                  System.exit(1);
               }
            }

            String description = SNMPUtil.stripHtmlTags((String)pd.getValue("description"));
            if (attrMap.containsKey(propName)) {
               toolkit.updateSNMPColumn(tableName, snmpColName, description);
            } else {
               toolkit.createSNMPColumn(tableName, snmpColName, pd.getPropertyType(), description);
            }
         }

         Set currentAttrs = new HashSet();
         Iterator i = snmpAttrs.iterator();

         while(i.hasNext()) {
            PropertyDescriptor pd = (PropertyDescriptor)i.next();
            snmpColName = pd.getName();
            currentAttrs.add(snmpColName);
         }

         i = attrMap.keySet().iterator();

         while(i.hasNext()) {
            propName = (String)((String)i.next());
            LOGGER.debug("Evaluating removal of " + propName + " from " + tableName);
            if (!currentAttrs.contains(propName) && !ignoredAttributes.contains(propName)) {
               LOGGER.debug("Removing column " + attrMap.get(propName) + " from " + tableName);
               snmpColName = (String)attrMap.get(propName);
               if (WLSMibMetadataBuilder.isColumnNameOverridden(snmpColName)) {
                  snmpColName = WLSMibMetadataBuilder.getColumnNameOverride(snmpColName);
               }

               toolkit.removeSNMPColumn(tableName, snmpColName);
            }
         }

         toolkit.completeTableEdit(tableName);
      }
   }

   private static String getTableNameForType(String typeName) {
      return SNMPUtil.convertTypeNameToSNMPTableName(typeName);
   }

   private static List getPropertyDescriptorsForSNMP(String type, BeanInfo beanInfo) {
      List result = new ArrayList();
      PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
      LOGGER.debug("Discovering properties for " + type);

      for(int i = 0; i < props.length; ++i) {
         PropertyDescriptor p = props[i];
         Class clz = p.getPropertyType();
         if (isClassSupported(clz)) {
            LOGGER.debug("Found property " + p.getName());
            Boolean value = (Boolean)p.getValue("encrypted");
            if (value != null && value.equals(Boolean.TRUE)) {
               LOGGER.debug("Found encrypted property " + p.getName());
            } else {
               value = (Boolean)p.getValue("exclude");
               if (value != null && value.equals(Boolean.TRUE)) {
                  LOGGER.debug("Found excluded property " + p.getName());
               } else {
                  Object obsolete = p.getValue("obsolete");
                  if (obsolete != null) {
                     LOGGER.debug("Found obsolete property " + p.getName() + " for " + type);
                  } else {
                     value = (Boolean)p.getValue("internal");
                     if (value != null && value.equals(Boolean.TRUE)) {
                        LOGGER.debug("Found internal property " + p.getName());
                     } else {
                        String propName = p.getName();
                        if (!excludedAttributes.contains(propName)) {
                           result.add(p);
                        }
                     }
                  }
               }
            }
         } else {
            LOGGER.debug("Ignoring property " + p.getName() + " of type " + clz);
         }
      }

      return result;
   }

   private static boolean isClassSupported(Class clz) {
      if (clz.isPrimitive()) {
         return true;
      } else if (clz.isArray()) {
         return clz.getComponentType() == String.class;
      } else {
         return supportedClasses.contains(clz);
      }
   }

   private static void printUsageAndExit(Getopt2 options) {
      options.usageAndExit(MibGenerator.class.getName());
   }

   private static void log(String message) {
      LOGGER.notice(message);
   }

   private static void logException(String message, Exception e) {
      LOGGER.error(message, e);
   }

   static {
      supportedPackages.add("weblogic.management.configuration");
      supportedPackages.add("weblogic.management.runtime");
      excludedAttributes.add("Notes");
      excludedAttributes.add("DynamicallyCreated");
      excludedAttributes.add("Id");
      ignoredAttributes.add("Index");
      ignoredAttributes.add("Parent");
      ignoredAttributes.add("ObjectName");
      supportedClasses.add(Character.class);
      supportedClasses.add(String.class);
      supportedClasses.add(Boolean.class);
      supportedClasses.add(Byte.class);
      supportedClasses.add(Short.class);
      supportedClasses.add(Integer.class);
      supportedClasses.add(Long.class);
      supportedClasses.add(Float.class);
      supportedClasses.add(Double.class);
      supportedClasses.add(Date.class);
      supportedClasses.add(HealthState.class);
      LOGGER = new NonCatalogLogger(MibGenerator.class.getSimpleName());
   }
}
