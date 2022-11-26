package weblogic.diagnostics.instrumentation.gathering;

import com.oracle.jrockit.jfr.InvalidEventDefinitionException;
import com.oracle.jrockit.jfr.InvalidValueException;
import com.oracle.jrockit.jfr.Producer;
import java.net.URISyntaxException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.flightrecorder.FlightRecorderManager;
import weblogic.diagnostics.flightrecorder.event.BaseInstantEvent;
import weblogic.diagnostics.flightrecorder.event.BaseTimedEvent;
import weblogic.diagnostics.flightrecorder.event.ConnectorActivateEndpointEvent;
import weblogic.diagnostics.flightrecorder.event.ConnectorBaseEvent;
import weblogic.diagnostics.flightrecorder.event.ConnectorDeactivateEndpointEvent;
import weblogic.diagnostics.flightrecorder.event.ConnectorEndpointBaseEvent;
import weblogic.diagnostics.flightrecorder.event.ConnectorInboundTransactionCommitEvent;
import weblogic.diagnostics.flightrecorder.event.ConnectorInboundTransactionRollbackEvent;
import weblogic.diagnostics.flightrecorder.event.ConnectorInboundTransactionStartEvent;
import weblogic.diagnostics.flightrecorder.event.ConnectorOutboundConnectionClosedEvent;
import weblogic.diagnostics.flightrecorder.event.ConnectorOutboundConnectionErrorEvent;
import weblogic.diagnostics.flightrecorder.event.ConnectorOutboundDestroyConnectionEvent;
import weblogic.diagnostics.flightrecorder.event.ConnectorOutboundRegisterResourceEvent;
import weblogic.diagnostics.flightrecorder.event.ConnectorOutboundReleaseConnectionEvent;
import weblogic.diagnostics.flightrecorder.event.ConnectorOutboundReserveConnectionEvent;
import weblogic.diagnostics.flightrecorder.event.ConnectorOutboundTransactionCommitEvent;
import weblogic.diagnostics.flightrecorder.event.ConnectorOutboundTransactionRollbackEvent;
import weblogic.diagnostics.flightrecorder.event.ConnectorOutboundTransactionStartEvent;
import weblogic.diagnostics.flightrecorder.event.ConnectorOutboundUnregisterResourceEvent;
import weblogic.diagnostics.flightrecorder.event.ConnectorTransactionBaseEvent;
import weblogic.diagnostics.flightrecorder.event.DeploymentBaseInstantEvent;
import weblogic.diagnostics.flightrecorder.event.DeploymentCompleteEvent;
import weblogic.diagnostics.flightrecorder.event.DeploymentDoCancelEvent;
import weblogic.diagnostics.flightrecorder.event.DeploymentDoPrepareEvent;
import weblogic.diagnostics.flightrecorder.event.DeploymentOperationEvent;
import weblogic.diagnostics.flightrecorder.event.ECIDMappingEvent;
import weblogic.diagnostics.flightrecorder.event.EJBBaseInstantEvent;
import weblogic.diagnostics.flightrecorder.event.EJBBaseTimedEvent;
import weblogic.diagnostics.flightrecorder.event.EJBBusinessMethodInvokeEvent;
import weblogic.diagnostics.flightrecorder.event.EJBBusinessMethodPostInvokeCleanupEvent;
import weblogic.diagnostics.flightrecorder.event.EJBBusinessMethodPostInvokeEvent;
import weblogic.diagnostics.flightrecorder.event.EJBBusinessMethodPreInvokeEvent;
import weblogic.diagnostics.flightrecorder.event.EJBDatabaseAccessEvent;
import weblogic.diagnostics.flightrecorder.event.EJBHomeCreateEvent;
import weblogic.diagnostics.flightrecorder.event.EJBHomeRemoveEvent;
import weblogic.diagnostics.flightrecorder.event.EJBPoolManagerCreateEvent;
import weblogic.diagnostics.flightrecorder.event.EJBPoolManagerPostInvokeEvent;
import weblogic.diagnostics.flightrecorder.event.EJBPoolManagerPreInvokeEvent;
import weblogic.diagnostics.flightrecorder.event.EJBPoolManagerRemoveEvent;
import weblogic.diagnostics.flightrecorder.event.EJBReplicatedSessionManagerEvent;
import weblogic.diagnostics.flightrecorder.event.EJBTimerManagerEvent;
import weblogic.diagnostics.flightrecorder.event.GlobalInformationEvent;
import weblogic.diagnostics.flightrecorder.event.HarvesterDataSampleEvent;
import weblogic.diagnostics.flightrecorder.event.JDBCBaseInstantEvent;
import weblogic.diagnostics.flightrecorder.event.JDBCBaseTimedEvent;
import weblogic.diagnostics.flightrecorder.event.JDBCConnectionCloseEvent;
import weblogic.diagnostics.flightrecorder.event.JDBCConnectionCommitEvent;
import weblogic.diagnostics.flightrecorder.event.JDBCConnectionCreateStatementEvent;
import weblogic.diagnostics.flightrecorder.event.JDBCConnectionGetVendorConnectionEvent;
import weblogic.diagnostics.flightrecorder.event.JDBCConnectionPrepareEvent;
import weblogic.diagnostics.flightrecorder.event.JDBCConnectionReleaseEvent;
import weblogic.diagnostics.flightrecorder.event.JDBCConnectionReserveEvent;
import weblogic.diagnostics.flightrecorder.event.JDBCConnectionRollbackEvent;
import weblogic.diagnostics.flightrecorder.event.JDBCDataSourceGetConnectionEvent;
import weblogic.diagnostics.flightrecorder.event.JDBCDriverConnectEvent;
import weblogic.diagnostics.flightrecorder.event.JDBCStatementCreationEvent;
import weblogic.diagnostics.flightrecorder.event.JDBCStatementExecuteBeginEvent;
import weblogic.diagnostics.flightrecorder.event.JDBCStatementExecuteEvent;
import weblogic.diagnostics.flightrecorder.event.JDBCTransactionBaseEvent;
import weblogic.diagnostics.flightrecorder.event.JDBCTransactionCommitEvent;
import weblogic.diagnostics.flightrecorder.event.JDBCTransactionEndEvent;
import weblogic.diagnostics.flightrecorder.event.JDBCTransactionGetXAResourceEvent;
import weblogic.diagnostics.flightrecorder.event.JDBCTransactionIsSameRMEvent;
import weblogic.diagnostics.flightrecorder.event.JDBCTransactionPrepareEvent;
import weblogic.diagnostics.flightrecorder.event.JDBCTransactionRollbackEvent;
import weblogic.diagnostics.flightrecorder.event.JDBCTransactionStartEvent;
import weblogic.diagnostics.flightrecorder.event.JMSBEConsumerLogEvent;
import weblogic.diagnostics.flightrecorder.event.JTABaseInstantEvent;
import weblogic.diagnostics.flightrecorder.event.JTATransactionCommitEvent;
import weblogic.diagnostics.flightrecorder.event.JTATransactionEndEvent;
import weblogic.diagnostics.flightrecorder.event.JTATransactionPrepareEvent;
import weblogic.diagnostics.flightrecorder.event.JTATransactionPreparedEvent;
import weblogic.diagnostics.flightrecorder.event.JTATransactionStartEvent;
import weblogic.diagnostics.flightrecorder.event.LogRecordEvent;
import weblogic.diagnostics.flightrecorder.event.LoggingEvent;
import weblogic.diagnostics.flightrecorder.event.ServletAsyncActionEvent;
import weblogic.diagnostics.flightrecorder.event.ServletBaseInstantEvent;
import weblogic.diagnostics.flightrecorder.event.ServletBaseTimedEvent;
import weblogic.diagnostics.flightrecorder.event.ServletCheckAccessEvent;
import weblogic.diagnostics.flightrecorder.event.ServletContextExecuteEvent;
import weblogic.diagnostics.flightrecorder.event.ServletContextHandleThrowableEvent;
import weblogic.diagnostics.flightrecorder.event.ServletExecuteEvent;
import weblogic.diagnostics.flightrecorder.event.ServletFilterEvent;
import weblogic.diagnostics.flightrecorder.event.ServletInvocationEvent;
import weblogic.diagnostics.flightrecorder.event.ServletRequestActionEvent;
import weblogic.diagnostics.flightrecorder.event.ServletRequestCancelEvent;
import weblogic.diagnostics.flightrecorder.event.ServletRequestDispatchEvent;
import weblogic.diagnostics.flightrecorder.event.ServletRequestOverloadEvent;
import weblogic.diagnostics.flightrecorder.event.ServletRequestRunBeginEvent;
import weblogic.diagnostics.flightrecorder.event.ServletRequestRunEvent;
import weblogic.diagnostics.flightrecorder.event.ServletResponseSendEvent;
import weblogic.diagnostics.flightrecorder.event.ServletResponseWriteHeadersEvent;
import weblogic.diagnostics.flightrecorder.event.ServletStaleResourceEvent;
import weblogic.diagnostics.flightrecorder.event.ThrottleInformationEvent;
import weblogic.diagnostics.flightrecorder.event.WLLogRecordEvent;
import weblogic.diagnostics.flightrecorder.event.WebApplicationBaseTimedEvent;
import weblogic.diagnostics.flightrecorder.event.WebApplicationLoadEvent;
import weblogic.diagnostics.flightrecorder.event.WebApplicationUnloadEvent;
import weblogic.diagnostics.flightrecorder.event.WebservicesJAXRPCBaseTimedEvent;
import weblogic.diagnostics.flightrecorder.event.WebservicesJAXRPCClientRequestActionEvent;
import weblogic.diagnostics.flightrecorder.event.WebservicesJAXRPCClientResponseActionEvent;
import weblogic.diagnostics.flightrecorder.event.WebservicesJAXRPCDispatchActionEvent;
import weblogic.diagnostics.flightrecorder.event.WebservicesJAXRPCRequestActionEvent;
import weblogic.diagnostics.flightrecorder.event.WebservicesJAXRPCResponseActionEvent;
import weblogic.diagnostics.flightrecorder.event.WebservicesJAXWSBaseTimedEvent;
import weblogic.diagnostics.flightrecorder.event.WebservicesJAXWSEndPointEvent;
import weblogic.diagnostics.flightrecorder.event.WebservicesJAXWSRequestActionEvent;
import weblogic.diagnostics.flightrecorder.event.WebservicesJAXWSResourceEvent;

public class JFRHelperV1Impl implements JFRHelper {
   private static DebugLogger debugLog = DebugLogger.getDebugLogger("DebugDiagnosticDataGathering");
   private static FlightRecorderManager flightRecorderMgr = weblogic.diagnostics.flightrecorder.FlightRecorderManager.Factory.getInstance();
   private Producer baseProducer;
   private Producer lowProducer;
   private Producer mediumProducer;
   private Producer highProducer;

   public void initialize(int diagnosticVolume) {
      if (debugLog.isDebugEnabled()) {
         debugLog.debug("initializeProducers() WLDF Diagnostic volume = " + diagnosticVolume);
      }

      try {
         this.baseProducer = new Producer("WLDF Base Producer", "WLDF Base Producer", "http://www.oracle.com/wls/flightrecorder/base");
      } catch (URISyntaxException var6) {
         if (debugLog.isDebugEnabled()) {
            debugLog.debug("Failed to create base producer", var6);
         }

         return;
      }

      if (DataGatheringManager.constantPoolsEnabled) {
         this.baseProducer.createConstantPool(String.class, "GlobalPool", 20480, 10485760, true);
      }

      this.addEvent(this.baseProducer, "weblogic.diagnostics.flightrecorder.event.GlobalInformationEvent", GlobalInformationEvent.class);
      this.addNonInstrumentedEvent(this.baseProducer, "weblogic.diagnostics.flightrecorder.event.HarvesterDataSampleEvent", HarvesterDataSampleEvent.class);
      this.baseProducer.register();

      try {
         this.lowProducer = new Producer("WLDF Low Diagnostic Volume Producer", "WLDF Low Diagnostic Volume Producer", "http://www.oracle.com/wls/flightrecorder/low");
      } catch (URISyntaxException var3) {
         if (debugLog.isDebugEnabled()) {
            debugLog.debug("Failed to create low producer", var3);
         }

         return;
      }

      if (DataGatheringManager.constantPoolsEnabled) {
         this.lowProducer.createConstantPool(String.class, "GlobalPool", 20480, 10485760, true);
         this.lowProducer.createConstantPool(String.class, "LocalPool", 20480, 1048576, true);
      }

      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.ThrottleInformationEvent", ThrottleInformationEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.BaseInstantEvent", BaseInstantEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.ConnectorBaseEvent", ConnectorBaseEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.ConnectorEndpointBaseEvent", ConnectorEndpointBaseEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.ConnectorTransactionBaseEvent", ConnectorTransactionBaseEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.DeploymentBaseInstantEvent", DeploymentBaseInstantEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.EJBBaseInstantEvent", EJBBaseInstantEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.JDBCBaseInstantEvent", JDBCBaseInstantEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.ServletBaseInstantEvent", ServletBaseInstantEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.BaseTimedEvent", BaseTimedEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.EJBBaseTimedEvent", EJBBaseTimedEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.JDBCBaseTimedEvent", JDBCBaseTimedEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.ServletBaseTimedEvent", ServletBaseTimedEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.WebApplicationBaseTimedEvent", WebApplicationBaseTimedEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.LoggingEvent", LoggingEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.LogRecordEvent", LogRecordEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.WLLogRecordEvent", WLLogRecordEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.ConnectorActivateEndpointEvent", ConnectorActivateEndpointEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.ConnectorDeactivateEndpointEvent", ConnectorDeactivateEndpointEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.ConnectorInboundTransactionRollbackEvent", ConnectorInboundTransactionRollbackEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.ConnectorOutboundConnectionClosedEvent", ConnectorOutboundConnectionClosedEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.ConnectorOutboundConnectionErrorEvent", ConnectorOutboundConnectionErrorEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.ConnectorOutboundDestroyConnectionEvent", ConnectorOutboundDestroyConnectionEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.ConnectorOutboundRegisterResourceEvent", ConnectorOutboundRegisterResourceEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.ConnectorOutboundReleaseConnectionEvent", ConnectorOutboundReleaseConnectionEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.ConnectorOutboundReserveConnectionEvent", ConnectorOutboundReserveConnectionEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.ConnectorOutboundTransactionRollbackEvent", ConnectorOutboundTransactionRollbackEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.ConnectorOutboundUnregisterResourceEvent", ConnectorOutboundUnregisterResourceEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.DeploymentCompleteEvent", DeploymentCompleteEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.DeploymentDoCancelEvent", DeploymentDoCancelEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.DeploymentDoPrepareEvent", DeploymentDoPrepareEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.DeploymentOperationEvent", DeploymentOperationEvent.class);
      this.addNonInstrumentedEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.ECIDMappingEvent", ECIDMappingEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.EJBBusinessMethodInvokeEvent", EJBBusinessMethodInvokeEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.EJBBusinessMethodPostInvokeEvent", EJBBusinessMethodPostInvokeEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.EJBBusinessMethodPreInvokeEvent", EJBBusinessMethodPreInvokeEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.JDBCConnectionRollbackEvent", JDBCConnectionRollbackEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.JDBCStatementExecuteEvent", JDBCStatementExecuteEvent.class);
      this.addNonInstrumentedEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.JDBCStatementExecuteBeginEvent", JDBCStatementExecuteBeginEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.JDBCTransactionRollbackEvent", JDBCTransactionRollbackEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.ServletInvocationEvent", ServletInvocationEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.ServletRequestRunEvent", ServletRequestRunEvent.class);
      this.addNonInstrumentedEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.ServletRequestRunBeginEvent", ServletRequestRunBeginEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.WebApplicationLoadEvent", WebApplicationLoadEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.WebApplicationUnloadEvent", WebApplicationUnloadEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.WebservicesJAXRPCBaseTimedEvent", WebservicesJAXRPCBaseTimedEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.WebservicesJAXRPCClientRequestActionEvent", WebservicesJAXRPCClientRequestActionEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.WebservicesJAXRPCClientResponseActionEvent", WebservicesJAXRPCClientResponseActionEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.WebservicesJAXRPCDispatchActionEvent", WebservicesJAXRPCDispatchActionEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.WebservicesJAXRPCRequestActionEvent", WebservicesJAXRPCRequestActionEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.WebservicesJAXRPCResponseActionEvent", WebservicesJAXRPCResponseActionEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.WebservicesJAXWSBaseTimedEvent", WebservicesJAXWSBaseTimedEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.WebservicesJAXWSEndPointEvent", WebservicesJAXWSEndPointEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.WebservicesJAXWSRequestActionEvent", WebservicesJAXWSRequestActionEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.WebservicesJAXWSResourceEvent", WebservicesJAXWSResourceEvent.class);
      this.addEvent(this.lowProducer, "weblogic.diagnostics.flightrecorder.event.ServletContextHandleThrowableEvent", ServletContextHandleThrowableEvent.class);
      this.lowProducer.register();
      if (diagnosticVolume < 1) {
         this.lowProducer.disable();
      }

      try {
         this.mediumProducer = new Producer("WLDF Medium Diagnostic Volume Producer", "WLDF Medium Diagnostic Volume Producer", "http://www.oracle.com/wls/flightrecorder/medium");
      } catch (URISyntaxException var5) {
         if (debugLog.isDebugEnabled()) {
            debugLog.debug("Failed to create medium producer", var5);
         }

         return;
      }

      if (DataGatheringManager.constantPoolsEnabled) {
         this.mediumProducer.createConstantPool(String.class, "GlobalPool", 20480, 10485760, true);
         this.mediumProducer.createConstantPool(String.class, "LocalPool", 20480, 1048576, true);
      }

      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.ConnectorInboundTransactionCommitEvent", ConnectorInboundTransactionCommitEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.ConnectorInboundTransactionStartEvent", ConnectorInboundTransactionStartEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.ConnectorOutboundTransactionCommitEvent", ConnectorOutboundTransactionCommitEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.ConnectorOutboundTransactionStartEvent", ConnectorOutboundTransactionStartEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.EJBHomeCreateEvent", EJBHomeCreateEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.EJBHomeRemoveEvent", EJBHomeRemoveEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.EJBPoolManagerCreateEvent", EJBPoolManagerCreateEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.EJBPoolManagerPostInvokeEvent", EJBPoolManagerPostInvokeEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.EJBPoolManagerPreInvokeEvent", EJBPoolManagerPreInvokeEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.JDBCConnectionCloseEvent", JDBCConnectionCloseEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.JDBCConnectionCommitEvent", JDBCConnectionCommitEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.JDBCConnectionCreateStatementEvent", JDBCConnectionCreateStatementEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.JDBCConnectionGetVendorConnectionEvent", JDBCConnectionGetVendorConnectionEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.JDBCConnectionPrepareEvent", JDBCConnectionPrepareEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.JDBCConnectionReleaseEvent", JDBCConnectionReleaseEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.JDBCConnectionReserveEvent", JDBCConnectionReserveEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.JDBCDataSourceGetConnectionEvent", JDBCDataSourceGetConnectionEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.JDBCDriverConnectEvent", JDBCDriverConnectEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.JDBCStatementCreationEvent", JDBCStatementCreationEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.ServletExecuteEvent", ServletExecuteEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.ServletRequestDispatchEvent", ServletRequestDispatchEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.ServletRequestActionEvent", ServletRequestActionEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.ServletFilterEvent", ServletFilterEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.ServletAsyncActionEvent", ServletAsyncActionEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.ServletContextExecuteEvent", ServletContextExecuteEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.ServletResponseWriteHeadersEvent", ServletResponseWriteHeadersEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.ServletResponseSendEvent", ServletResponseSendEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.ServletStaleResourceEvent", ServletStaleResourceEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.ServletCheckAccessEvent", ServletCheckAccessEvent.class);
      this.addEvent(this.mediumProducer, "weblogic.diagnostics.flightrecorder.event.JMSBEConsumerLogEvent", JMSBEConsumerLogEvent.class);
      this.mediumProducer.register();
      if (diagnosticVolume < 2) {
         this.mediumProducer.disable();
      }

      try {
         this.highProducer = new Producer("WLDF High Diagnostic Volume Producer", "WLDF High Diagnostic Volume Producer", "http://www.oracle.com/wls/flightrecorder/high");
      } catch (URISyntaxException var4) {
         if (debugLog.isDebugEnabled()) {
            debugLog.debug("Failed to create high producer", var4);
         }

         return;
      }

      if (DataGatheringManager.constantPoolsEnabled) {
         this.highProducer.createConstantPool(String.class, "GlobalPool", 20480, 10485760, true);
         this.highProducer.createConstantPool(String.class, "LocalPool", 20480, 1048576, true);
      }

      this.addEvent(this.highProducer, "weblogic.diagnostics.flightrecorder.event.EJBDatabaseAccessEvent", EJBDatabaseAccessEvent.class);
      this.addEvent(this.highProducer, "weblogic.diagnostics.flightrecorder.event.EJBBusinessMethodPostInvokeCleanupEvent", EJBBusinessMethodPostInvokeCleanupEvent.class);
      this.addEvent(this.highProducer, "weblogic.diagnostics.flightrecorder.event.EJBPoolManagerRemoveEvent", EJBPoolManagerRemoveEvent.class);
      this.addEvent(this.highProducer, "weblogic.diagnostics.flightrecorder.event.EJBReplicatedSessionManagerEvent", EJBReplicatedSessionManagerEvent.class);
      this.addEvent(this.highProducer, "weblogic.diagnostics.flightrecorder.event.EJBTimerManagerEvent", EJBTimerManagerEvent.class);
      this.addEvent(this.highProducer, "weblogic.diagnostics.flightrecorder.event.JDBCTransactionBaseEvent", JDBCTransactionBaseEvent.class);
      this.addEvent(this.highProducer, "weblogic.diagnostics.flightrecorder.event.JDBCTransactionCommitEvent", JDBCTransactionCommitEvent.class);
      this.addEvent(this.highProducer, "weblogic.diagnostics.flightrecorder.event.JDBCTransactionEndEvent", JDBCTransactionEndEvent.class);
      this.addEvent(this.highProducer, "weblogic.diagnostics.flightrecorder.event.JDBCTransactionGetXAResourceEvent", JDBCTransactionGetXAResourceEvent.class);
      this.addEvent(this.highProducer, "weblogic.diagnostics.flightrecorder.event.JDBCTransactionIsSameRMEvent", JDBCTransactionIsSameRMEvent.class);
      this.addEvent(this.highProducer, "weblogic.diagnostics.flightrecorder.event.JDBCTransactionPrepareEvent", JDBCTransactionPrepareEvent.class);
      this.addEvent(this.highProducer, "weblogic.diagnostics.flightrecorder.event.JDBCTransactionStartEvent", JDBCTransactionStartEvent.class);
      this.addEvent(this.highProducer, "weblogic.diagnostics.flightrecorder.event.JTABaseInstantEvent", JTABaseInstantEvent.class);
      this.addEvent(this.highProducer, "weblogic.diagnostics.flightrecorder.event.JTATransactionCommitEvent", JTATransactionCommitEvent.class);
      this.addEvent(this.highProducer, "weblogic.diagnostics.flightrecorder.event.JTATransactionEndEvent", JTATransactionEndEvent.class);
      this.addEvent(this.highProducer, "weblogic.diagnostics.flightrecorder.event.JTATransactionPreparedEvent", JTATransactionPreparedEvent.class);
      this.addEvent(this.highProducer, "weblogic.diagnostics.flightrecorder.event.JTATransactionPrepareEvent", JTATransactionPrepareEvent.class);
      this.addEvent(this.highProducer, "weblogic.diagnostics.flightrecorder.event.JTATransactionStartEvent", JTATransactionStartEvent.class);
      this.addEvent(this.highProducer, "weblogic.diagnostics.flightrecorder.event.ServletRequestOverloadEvent", ServletRequestOverloadEvent.class);
      this.addEvent(this.highProducer, "weblogic.diagnostics.flightrecorder.event.ServletRequestCancelEvent", ServletRequestCancelEvent.class);
      this.highProducer.register();
      if (diagnosticVolume != 3) {
         this.highProducer.disable();
      }

   }

   public void handlePropertyChange(int newVolume) {
      if (newVolume == 0) {
         if (!flightRecorderMgr.isJFR2()) {
            if (this.lowProducer != null) {
               this.lowProducer.disable();
            }

            if (this.mediumProducer != null) {
               this.mediumProducer.disable();
            }

            if (this.highProducer != null) {
               this.highProducer.disable();
            }
         }

         flightRecorderMgr.disableJVMEventsInImageRecording();
      } else if (newVolume == 1) {
         if (!flightRecorderMgr.isJFR2()) {
            if (this.lowProducer != null) {
               this.lowProducer.enable();
            }

            if (this.mediumProducer != null) {
               this.mediumProducer.disable();
            }

            if (this.highProducer != null) {
               this.highProducer.disable();
            }
         }

         if (flightRecorderMgr.areJVMEventsExpensive()) {
            flightRecorderMgr.disableJVMEventsInImageRecording();
         } else {
            flightRecorderMgr.enableJVMEventsInImageRecording();
         }
      } else if (newVolume == 2) {
         if (!flightRecorderMgr.isJFR2()) {
            if (this.lowProducer != null) {
               this.lowProducer.enable();
            }

            if (this.mediumProducer != null) {
               this.mediumProducer.enable();
            }

            if (this.highProducer != null) {
               this.highProducer.disable();
            }
         }

         flightRecorderMgr.enableJVMEventsInImageRecording();
      } else if (newVolume == 3) {
         if (!flightRecorderMgr.isJFR2()) {
            if (this.lowProducer != null) {
               this.lowProducer.enable();
            }

            if (this.mediumProducer != null) {
               this.mediumProducer.enable();
            }

            if (this.highProducer != null) {
               this.highProducer.enable();
            }
         }

         flightRecorderMgr.enableJVMEventsInImageRecording();
      }

   }

   private void addEvent(Producer producer, String eventClassName, Class eventClass) {
      try {
         if (DataGatheringManager.eventClassNamesInUse != null && DataGatheringManager.eventClassNamesInUse.contains(eventClassName)) {
            if (debugLog.isDebugEnabled()) {
               debugLog.debug("Adding " + eventClassName + " to " + producer.getName());
            }

            Object token = producer.addEvent(eventClass);
            flightRecorderMgr.addEvent(token, eventClass);
         } else if (debugLog.isDebugEnabled()) {
            debugLog.debug(eventClassName + " not used by instrumentation config, not registering it");
         }

      } catch (InvalidEventDefinitionException var5) {
         if (debugLog.isDebugEnabled()) {
            debugLog.debug("Failed to add " + eventClassName + " to " + producer.getName(), var5);
         }

      } catch (InvalidValueException var6) {
         if (debugLog.isDebugEnabled()) {
            debugLog.debug("Failed to add " + eventClassName + " to " + producer.getName(), var6);
         }

      }
   }

   private void addNonInstrumentedEvent(Producer producer, String eventClassName, Class eventClass) {
      try {
         if (debugLog.isDebugEnabled()) {
            debugLog.debug("Adding " + eventClassName + " to " + producer.getName());
         }

         Object token = producer.addEvent(eventClass);
         flightRecorderMgr.addEvent(token, eventClass);
      } catch (InvalidEventDefinitionException var5) {
         if (debugLog.isDebugEnabled()) {
            debugLog.debug("Failed to add " + eventClassName + " to " + producer.getName(), var5);
         }

      } catch (InvalidValueException var6) {
         if (debugLog.isDebugEnabled()) {
            debugLog.debug("Failed to add " + eventClassName + " to " + producer.getName(), var6);
         }

      }
   }
}
