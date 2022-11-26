package monfox.toolkit.snmp.mgr;

import java.net.InetAddress;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.engine.SnmpRequestPDU;
import monfox.toolkit.snmp.engine.SnmpTrapPDU;
import monfox.toolkit.snmp.engine.TransportEntity;
import monfox.toolkit.snmp.metadata.SnmpMetadata;

/** @deprecated */
public class SnmpNotificationDispatcher {
   public static final int DEFAULT_PORT = 162;
   private SnmpNotificationListener a;
   private SnmpNotificationDispatcherImpl b;
   private boolean c;
   private static final String d = "$Id: SnmpNotificationDispatcher.java,v 1.7 2002/02/05 23:17:28 sking Exp $";

   public SnmpNotificationDispatcher(SnmpMetadata var1, InetAddress var2, int var3, int var4, int var5) throws SnmpException {
      this.a = null;
      this.b = null;
      this.c = true;
      this.b = new SnmpNotificationDispatcherImpl(this, var1, var3, var2, var4, var5);
   }

   SnmpNotificationDispatcher(SnmpMetadata var1, InetAddress var2, int var3, int var4) throws SnmpException {
      this(var1, var2, var3, var4, Snmp.DEFAULT_RECEIVE_BUFFER_SIZE);
   }

   public SnmpNotificationDispatcher(SnmpMetadata var1) throws SnmpException {
      this(var1, (InetAddress)null, 162, 1);
   }

   public SnmpNotificationDispatcher(SnmpMetadata var1, int var2) throws SnmpException {
      this(var1, (InetAddress)null, var2, 1);
   }

   public SnmpNotificationDispatcher() throws SnmpException {
      this((SnmpMetadata)null, (InetAddress)null, 162, 1);
   }

   public SnmpNotificationDispatcher(int var1) throws SnmpException {
      this((SnmpMetadata)null, (InetAddress)null, var1, 1);
   }

   public SnmpNotificationDispatcher(int var1, int var2) throws SnmpException {
      this((SnmpMetadata)null, (InetAddress)null, var1, 1, var2);
   }

   public SnmpNotificationDispatcher(InetAddress var1, int var2, int var3) throws SnmpException {
      this((SnmpMetadata)null, var1, var2, 1, var3);
   }

   public void addNotificationListener(SnmpNotificationListener var1) {
      this.a = k.add(this.a, var1);
   }

   public void removeNotificationListener(SnmpNotificationListener var1) {
      this.a = k.remove(this.a, var1);
   }

   public boolean containsNotificationListener(SnmpNotificationListener var1) {
      return k.contains(this.a, var1);
   }

   void a(SnmpTrapPDU var1, TransportEntity var2) {
      SnmpNotificationListener var3 = this.a;
      if (var3 != null) {
         var3.handleTrapV1(this, var1, var2);
      }

   }

   void a(SnmpRequestPDU var1, TransportEntity var2) {
      SnmpNotificationListener var3 = this.a;
      if (var3 != null) {
         var3.handleTrapV2(this, var1, var2);
      }

   }

   void b(SnmpRequestPDU var1, TransportEntity var2) {
      SnmpNotificationListener var3 = this.a;
      if (var3 != null) {
         var3.handleInform(this, var1, var2);
      }

   }

   public boolean isActive() {
      return this.c;
   }

   public void shutdown() {
      this.c = false;
      this.b.a();
   }

   public void setReceiveBufferSize(int var1) {
      this.b.setReceiveBufferSize(var1);
   }

   public int getReceiveBufferSize() {
      return this.b.getReceiveBufferSize();
   }

   public void sendInformResponse(TransportEntity var1, SnmpRequestPDU var2, SnmpVarBindList var3) throws SnmpException {
      this.sendInformResponse(var1, var2, var3, 0, 0);
   }

   public void sendInformResponse(TransportEntity var1, SnmpRequestPDU var2, SnmpVarBindList var3, int var4, int var5) throws SnmpException {
      this.b.sendInformResponse(var1, var2, var3, var4, var5);
   }
}
