package weblogic.diagnostics.snmp.agent.monfox;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Map;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.ext.table.SnmpMibTableAdaptor;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.snmp.agent.MD5;
import weblogic.diagnostics.snmp.i18n.SNMPLogger;
import weblogic.logging.Loggable;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.ArrayUtils;

public class MBeanInstanceTableRow extends SnmpMibTableAdaptor.Row {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugSNMPAgent");
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String LOCATION_KEY = "Location";
   private static final int SIZE_LIMIT = 512;
   private static String domainName;
   private MBeanServerConnection mbeanServerConnection;
   private ObjectName objectName;
   private Map columnsMetadata;
   private String index;
   private String location;

   public MBeanInstanceTableRow(MBeanServerConnection mbeanServerConnection, ObjectName objectName, Map columnsMetadata) {
      this.mbeanServerConnection = mbeanServerConnection;
      this.objectName = objectName;
      this.columnsMetadata = columnsMetadata;
      this.index = computeIndex(this.objectName.toString());
      this.location = objectName.getKeyProperty("Location");
   }

   public boolean isAvailableForContextName(String context_name) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Context name = " + context_name);
      }

      if (context_name != null && context_name.length() > 0) {
         if (context_name.equals(getDomainName())) {
            return true;
         }

         if (this.location != null && this.location.length() > 0) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Location = " + this.location + " for " + this.objectName);
            }

            return this.location.equals(context_name);
         }
      }

      return true;
   }

   public SnmpValue getValue(SnmpOid arg0) throws SnmpValueException {
      String snmpColumnName = arg0.getOidInfo().getName();
      final String attributeName = (String)this.columnsMetadata.get(snmpColumnName);
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Getting attribute " + attributeName + " for SNMP Column " + snmpColumnName);
      }

      if (attributeName.equals("Index")) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Index = " + this.index + " for " + arg0);
         }

         return SnmpValue.getInstance(arg0, this.index);
      } else if (attributeName.equals("ObjectName")) {
         return SnmpValue.getInstance(arg0, this.objectName.toString());
      } else {
         Object value = null;

         try {
            value = SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  return MBeanInstanceTableRow.this.mbeanServerConnection.getAttribute(MBeanInstanceTableRow.this.objectName, attributeName);
               }
            });
         } catch (PrivilegedActionException var6) {
            throw new SnmpValueException("Error getting MBean attribute value");
         }

         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Got attribute " + attributeName + " value " + value);
         }

         if (value == null) {
            value = "";
         } else if (value instanceof Object[]) {
            value = toString((Object[])((Object[])value));
         }

         String str = value.toString();
         if (str.length() > 512) {
            str = str.substring(0, 512);
         }

         return SnmpValue.getInstance(arg0, str);
      }
   }

   private static String toString(Object[] a) {
      ArrayList al = new ArrayList();
      ArrayUtils.addAll(al, a);
      return al.toString();
   }

   public void setValue(SnmpOid arg0, SnmpValue arg1) throws SnmpValueException {
      Loggable l = SNMPLogger.logCannotModifyMBeanAttributesLoggable();
      String msg = l.getMessage();
      throw new SnmpValueException(msg);
   }

   static String computeIndex(String objName) {
      MD5 md = new MD5();
      byte[] out = new byte[16];
      md.update(objName.toString().getBytes());
      md.md5final(out);
      return "0x" + MD5.dumpBytes(out);
   }

   String getIndex() {
      return this.index;
   }

   static String getDomainName() {
      if (domainName == null) {
         domainName = ManagementService.getRuntimeAccess(KERNEL_ID).getDomainName();
      }

      return domainName;
   }

   ObjectName getObjectName() {
      return this.objectName;
   }

   String getAttributeName(String columnName) {
      String attributeName = (String)this.columnsMetadata.get(columnName);
      return attributeName;
   }
}
