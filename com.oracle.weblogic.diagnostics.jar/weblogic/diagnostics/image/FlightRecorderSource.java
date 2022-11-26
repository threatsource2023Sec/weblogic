package weblogic.diagnostics.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.AccessController;
import weblogic.diagnostics.context.CorrelationManager;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.flightrecorder.FlightRecorderManager;
import weblogic.diagnostics.flightrecorder.FlightRecorderManager.Factory;
import weblogic.diagnostics.flightrecorder.event.GlobalInformationEventInfo;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.management.configuration.WLDFServerDiagnosticMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.PropertyHelper;
import weblogic.utils.io.StreamUtils;

public class FlightRecorderSource implements ImageSource {
   private static final boolean DisableJVMEvents;
   private static final FlightRecorderManager flightRecorderMgr;
   private static DebugLogger debugLogger;
   private static final AuthenticatedSubject kernelId;
   private static WLDFServerDiagnosticMBean wldfConfig;
   private boolean timeoutRequested;
   private ImageManager imageManager;
   private File destinationTempFile = null;
   private GlobalInformation globalInfo = new GlobalInformation();
   private String recordingName = null;
   static final long serialVersionUID = -8638789012640400901L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.diagnostics.image.FlightRecorderSource");
   static final DelegatingMonitor _WLDF$INST_FLD_Globalinfo_Diagnostic_Volume_After_Off;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   public FlightRecorderSource(ImageManager imageManager) {
      this.imageManager = imageManager;
      this.initialize();
   }

   public void createDiagnosticImage(OutputStream imageStream) throws ImageSourceCreationException {
      boolean debug = debugLogger.isDebugEnabled();
      this.timeoutRequested = false;
      if (debug) {
         debugLogger.debug("FlightRecorderSource.createDiagnosticImage()");
      }

      if (!flightRecorderMgr.isImageRecordingActive()) {
         if (debug) {
            debugLogger.debug("FlightRecorderSource.createDiagnosticImage() no recording is active");
         }

      } else {
         File tempFile = null;

         try {
            try {
               this.triggerGlobalInformationEvent();
               CorrelationManager.getCorrelationManager().triggerThrottleInformationEvent();
               boolean copied = false;
               tempFile = File.createTempFile("__tmp", ".jfr", new File(this.imageManager.getDestinationDirectory()));

               try {
                  copied = flightRecorderMgr.copyImageRecordingToFile(tempFile);
               } catch (Throwable var38) {
                  RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
                  if (runtimeAccess != null) {
                     ServerRuntimeMBean serverRT = runtimeAccess.getServerRuntime();
                     FileInputStream fileStream = null;

                     try {
                        if (serverRT != null && serverRT.isShuttingDown() && this.destinationTempFile != null && this.destinationTempFile.exists() && this.destinationTempFile.canRead()) {
                           if (debug) {
                              debugLogger.debug("Server is shutting down, trying to capture from temporary recording file");
                           }

                           fileStream = new FileInputStream(this.destinationTempFile);
                           StreamUtils.writeTo(fileStream, imageStream);
                           imageStream.flush();
                           return;
                        }
                     } catch (IOException var36) {
                        DiagnosticsLogger.logErrorCapturingFlightRecorderImage(var36);
                     } finally {
                        if (fileStream != null) {
                           fileStream.close();
                        }

                     }
                  }

                  DiagnosticsLogger.logErrorCapturingFlightRecorderImage(var38);
               }

               if (copied) {
                  if (debug) {
                     debugLogger.debug("FlightRecorderSource exists= " + tempFile.exists());
                  }

                  FileInputStream fileStream = new FileInputStream(tempFile);

                  try {
                     try {
                        if (debug) {
                           debugLogger.debug("FlightRecorderSource writing from temp file " + tempFile + " to stream");
                        }

                        StreamUtils.writeTo(fileStream, imageStream);
                        imageStream.flush();
                     } catch (IOException var34) {
                        DiagnosticsLogger.logErrorCapturingFlightRecorderImage(var34);
                     }

                     return;
                  } finally {
                     if (fileStream != null) {
                        fileStream.close();
                     }

                  }
               }
            } catch (IOException var39) {
               DiagnosticsLogger.logErrorCapturingFlightRecorderImage(var39);
            }

         } finally {
            if (tempFile != null) {
               tempFile.delete();
            }

         }
      }
   }

   public void timeoutImageCreation() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("FlightRecorderSource.timeoutImageCreation()");
      }

      this.timeoutRequested = true;
   }

   private void initialize() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("FlightRecorderSource.initialize()");
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("FlightRecorderSource.initialize() start recording");
      }

      this.determineRecordingName();
      this.startRecording();
      this.triggerGlobalInformationEvent();
   }

   private void startRecording() {
      try {
         this.destinationTempFile = File.createTempFile("__tmp", ".jfr", new File(this.imageManager.getDestinationDirectory()));
         this.destinationTempFile.deleteOnExit();
         boolean jvmEventDisable = DisableJVMEvents || "Off".equals(this.getDiagnosticVolume()) || "Low".equals(this.getDiagnosticVolume()) && flightRecorderMgr.areJVMEventsExpensive();
         flightRecorderMgr.startImageFlightRecording(this.recordingName, this.destinationTempFile, jvmEventDisable);
         flightRecorderMgr.debugRecorderDetails();
      } catch (Exception var2) {
         DiagnosticsLogger.logErrorInitializingFlightRecording(var2);
      }

   }

   private String getDiagnosticVolume() {
      return wldfConfig.getWLDFDiagnosticVolume();
   }

   private void determineRecordingName() {
      StringBuffer recordingNameBuf = new StringBuffer();
      recordingNameBuf.append("WLDFDiagnosticImageRecording");
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      if (runtimeAccess != null) {
         this.globalInfo.setDomainName(runtimeAccess.getDomainName());
         this.globalInfo.setServerName(runtimeAccess.getServerName());
         ServerRuntimeMBean serverRT = runtimeAccess.getServerRuntime();
         if (serverRT != null) {
            this.globalInfo.setMachineName(serverRT.getCurrentMachine());
         }

         wldfConfig = runtimeAccess.getServer().getServerDiagnosticConfig();
      }

      recordingNameBuf.append("_");
      recordingNameBuf.append(this.globalInfo.getDomainName());
      recordingNameBuf.append("_");
      recordingNameBuf.append(this.globalInfo.getServerName());
      recordingNameBuf.append("_");
      recordingNameBuf.append(this.globalInfo.getMachineName());
      this.recordingName = recordingNameBuf.toString();
   }

   private GlobalInformation triggerGlobalInformationEvent() {
      LocalHolder var1;
      if ((var1 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
      }

      GlobalInformation var10000;
      try {
         this.globalInfo.setDiagnosticVolume(this.getDiagnosticVolume());
         var10000 = this.globalInfo;
      } catch (Throwable var3) {
         if (var1 != null) {
            var1.th = var3;
            var1.ret = null;
            InstrumentationSupport.createDynamicJoinPoint(var1);
            InstrumentationSupport.process(var1);
         }

         throw var3;
      }

      if (var1 != null) {
         var1.ret = var10000;
         InstrumentationSupport.createDynamicJoinPoint(var1);
         InstrumentationSupport.process(var1);
      }

      return var10000;
   }

   static {
      _WLDF$INST_FLD_Globalinfo_Diagnostic_Volume_After_Off = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Globalinfo_Diagnostic_Volume_After_Off");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "FlightRecorderSource.java", "weblogic.diagnostics.image.FlightRecorderSource", "triggerGlobalInformationEvent", "()Lweblogic/diagnostics/image/FlightRecorderSource$GlobalInformation;", 219, "", "", "", InstrumentationSupport.makeMap(new String[]{"Globalinfo_Diagnostic_Volume_After_Off"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, InstrumentationSupport.createValueHandlingInfo("return", (String)null, false, true), (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Globalinfo_Diagnostic_Volume_After_Off};
      DisableJVMEvents = PropertyHelper.getBoolean("weblogic.diagnostics.image.DisableJVMEvents");
      flightRecorderMgr = Factory.getInstance();
      debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticImage");
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   private static class GlobalInformation implements GlobalInformationEventInfo {
      private String domainName;
      private String serverName;
      private String machineName;
      private String diagnosticVolume;

      private GlobalInformation() {
      }

      public String getDomainName() {
         return this.domainName;
      }

      public void setDomainName(String domainName) {
         this.domainName = domainName;
      }

      public String getServerName() {
         return this.serverName;
      }

      public void setServerName(String serverName) {
         this.serverName = serverName;
      }

      public String getMachineName() {
         return this.machineName;
      }

      public void setMachineName(String machineName) {
         this.machineName = machineName;
      }

      public String getDiagnosticVolume() {
         return this.diagnosticVolume;
      }

      public void setDiagnosticVolume(String diagnosticVolume) {
         this.diagnosticVolume = diagnosticVolume;
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("domainName=");
         sb.append(this.domainName);
         sb.append(", serverName=");
         sb.append(this.serverName);
         sb.append(", machineName=");
         sb.append(this.machineName);
         sb.append(", diagnosticVolume=");
         sb.append(this.diagnosticVolume);
         return sb.toString();
      }

      // $FF: synthetic method
      GlobalInformation(Object x0) {
         this();
      }
   }
}
