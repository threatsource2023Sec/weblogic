package monfox.toolkit.snmp.metadata.output;

import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpModuleIdentityInfo;
import monfox.toolkit.snmp.metadata.SnmpNotificationGroupInfo;
import monfox.toolkit.snmp.metadata.SnmpNotificationInfo;
import monfox.toolkit.snmp.metadata.SnmpObjectGroupInfo;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import monfox.toolkit.snmp.metadata.SnmpOidInfo;
import monfox.toolkit.snmp.metadata.SnmpTableEntryInfo;
import monfox.toolkit.snmp.metadata.SnmpTableInfo;
import monfox.toolkit.snmp.util.TextBuffer;

public class MibComponentOutputter {
   private SnmpMetadata a;
   private MibOutputter b;

   public MibComponentOutputter(SnmpMetadata var1) {
      this.a = var1;
      this.b = new MibOutputter();
   }

   public String getString(SnmpOidInfo var1) throws SnmpValueException {
      if (var1 instanceof SnmpObjectInfo) {
         return this.getString((SnmpObjectInfo)var1);
      } else if (var1 instanceof SnmpObjectGroupInfo) {
         return this.getString((SnmpObjectGroupInfo)var1);
      } else if (var1 instanceof SnmpNotificationInfo) {
         return this.getString((SnmpNotificationInfo)var1);
      } else if (var1 instanceof SnmpNotificationGroupInfo) {
         return this.getString((SnmpNotificationGroupInfo)var1);
      } else if (var1 instanceof SnmpTableInfo) {
         return this.getString((SnmpTableInfo)var1);
      } else if (var1 instanceof SnmpTableEntryInfo) {
         return this.getString((SnmpTableEntryInfo)var1);
      } else if (var1 instanceof SnmpModuleIdentityInfo) {
         return this.getString((SnmpModuleIdentityInfo)var1);
      } else {
         TextBuffer var2 = new TextBuffer();
         MibOutputter.Context var3 = new MibOutputter.Context(var1.getModule());
         this.b.processSnmpOidInfo(this.a, var2, var1, var3);
         return var2.toString();
      }
   }

   public String getString(SnmpObjectInfo var1) {
      TextBuffer var2 = new TextBuffer();
      MibOutputter.Context var3 = new MibOutputter.Context(var1.getModule());
      this.b.processSnmpObjectInfo(this.a, var2, var1, var3);
      return var2.toString();
   }

   public String getString(SnmpObjectGroupInfo var1) {
      TextBuffer var2 = new TextBuffer();
      MibOutputter.Context var3 = new MibOutputter.Context(var1.getModule());
      this.b.processSnmpObjectGroupInfo(this.a, var2, var1, var3);
      return var2.toString();
   }

   public String getString(SnmpNotificationInfo var1) {
      TextBuffer var2 = new TextBuffer();
      MibOutputter.Context var3 = new MibOutputter.Context(var1.getModule());
      this.b.processSnmpNotificationInfo(this.a, var2, var1, var3);
      return var2.toString();
   }

   public String getString(SnmpNotificationGroupInfo var1) {
      TextBuffer var2 = new TextBuffer();
      MibOutputter.Context var3 = new MibOutputter.Context(var1.getModule());
      this.b.processSnmpNotificationGroupInfo(this.a, var2, var1, var3);
      return var2.toString();
   }

   public String getString(SnmpTableInfo var1) throws SnmpValueException {
      TextBuffer var2 = new TextBuffer();
      MibOutputter.Context var3 = new MibOutputter.Context(var1.getModule());
      this.b.processSnmpTableInfo(this.a, var2, var1, var3);
      return var2.toString();
   }

   public String getString(SnmpTableEntryInfo var1) {
      TextBuffer var2 = new TextBuffer();
      MibOutputter.Context var3 = new MibOutputter.Context(var1.getModule());
      SnmpTableInfo var4 = (SnmpTableInfo)var1.getParent();
      this.b.processSnmpTableEntry(this.a, var4, var1, var2, var3);
      return var2.toString();
   }

   public String getString(SnmpModuleIdentityInfo var1) {
      TextBuffer var2 = new TextBuffer();
      return var2.toString();
   }
}
