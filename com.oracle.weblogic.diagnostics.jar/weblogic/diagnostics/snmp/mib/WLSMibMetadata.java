package weblogic.diagnostics.snmp.mib;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.PlatformConstants;

public class WLSMibMetadata implements Serializable, MibConstants {
   private static final long serialVersionUID = -2396017281810551450L;
   Map snmpTableNameToWLSTypeName = new HashMap();
   Map wlsTypeNameToSNMPTableName = new HashMap();
   Map snmpTableNameToColumns = new HashMap();
   Map wlsTypeNameToColumns = new HashMap();

   public static WLSMibMetadata loadResource() throws WLSMibMetadataException {
      InputStream is = null;

      WLSMibMetadata var1;
      try {
         is = WLSMibMetadata.class.getResourceAsStream("WLSMibMetadata.dat");
         if (is == null) {
            throw new WLSMibMetadataException("Can't locate resource WLSMibMetadata.dat");
         }

         is = new BufferedInputStream((InputStream)is);
         var1 = loadResource((InputStream)is);
      } finally {
         if (is != null) {
            try {
               ((InputStream)is).close();
            } catch (IOException var8) {
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("Exception closing input.", var8);
               }
            }
         }

      }

      return var1;
   }

   public static WLSMibMetadata loadResource(InputStream is) throws WLSMibMetadataException {
      ObjectInputStream ois = null;

      WLSMibMetadata var3;
      try {
         ois = new ObjectInputStream(is);
         Object obj = ois.readObject();
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Loaded " + obj);
         }

         var3 = (WLSMibMetadata)obj;
      } catch (Exception var12) {
         throw new WLSMibMetadataException(var12);
      } finally {
         if (ois != null) {
            try {
               ois.close();
            } catch (IOException var11) {
               throw new WLSMibMetadataException(var11);
            }
         }

      }

      return var3;
   }

   public String getSNMPTableName(String wlsTypeName) {
      return (String)this.wlsTypeNameToSNMPTableName.get(wlsTypeName);
   }

   public String getWLSTypeName(String snmpTableName) {
      return (String)this.snmpTableNameToWLSTypeName.get(snmpTableName);
   }

   public WLSMibTableColumnsMetadata getColumnsMetadataForSNMPTable(String snmpTableName) {
      return (WLSMibTableColumnsMetadata)this.snmpTableNameToColumns.get(snmpTableName);
   }

   public WLSMibTableColumnsMetadata getColumnsMetadataForWLSType(String wlsTypeName) {
      return (WLSMibTableColumnsMetadata)this.wlsTypeNameToColumns.get(wlsTypeName);
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("snmpTableNameToWLSTypeName = " + this.snmpTableNameToWLSTypeName);
      sb.append(PlatformConstants.EOL);
      sb.append("wlsTypeNameToSNMPTableName = " + this.wlsTypeNameToSNMPTableName);
      sb.append(PlatformConstants.EOL);
      sb.append("snmpTableNameToColumns = " + this.snmpTableNameToColumns);
      sb.append(PlatformConstants.EOL);
      sb.append("wlsTypeNameToColumns = " + this.wlsTypeNameToColumns);
      sb.append(PlatformConstants.EOL);
      return sb.toString();
   }

   public static void main(String[] args) throws WLSMibMetadataException {
      WLSMibMetadata mibMetadata = loadResource();
      DebugLogger.println("Loaded MIB metadata ...");
      DebugLogger.println("" + mibMetadata);
   }
}
