package weblogic.management.scripting;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.management.InstanceAlreadyExistsException;
import org.python.core.PyLong;
import org.python.core.PyObject;
import org.python.core.PyString;
import weblogic.diagnostics.accessor.HarvestedDataCSVWriter;
import weblogic.diagnostics.accessor.runtime.AccessRuntimeMBean;
import weblogic.diagnostics.accessor.runtime.DataAccessRuntimeMBean;
import weblogic.diagnostics.utils.DateUtils;
import weblogic.management.WebLogicMBean;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.runtime.ClusterRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.WLDFControlRuntimeMBean;
import weblogic.management.runtime.WLDFDebugPatchTaskRuntimeMBean;
import weblogic.management.runtime.WLDFDebugPatchesRuntimeMBean;
import weblogic.management.runtime.WLDFHarvesterManagerRuntimeMBean;
import weblogic.management.runtime.WLDFImageCreationTaskRuntimeMBean;
import weblogic.management.runtime.WLDFPartitionImageRuntimeMBean;
import weblogic.management.runtime.WLDFSystemResourceControlRuntimeMBean;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;

public class DiagnosticsHandler {
   private static final String DEFAULT_DATE_PATTERN = "EEE MM/dd/YY k:mm:ss:SSS z";
   private WLScriptContext scriptContext;
   private WLSTMsgTextFormatter txtFmt;
   private String dateHeader;
   private String timestampHeader;
   private static final String DELIM = ",";

   DiagnosticsHandler(WLScriptContext ctx) {
      this.scriptContext = ctx;
      this.txtFmt = ctx.getWLSTMsgFormatter();
      this.dateHeader = this.txtFmt.getDumpDiagonsticDataDateHeader();
      this.timestampHeader = this.txtFmt.getDumpDiagonsticDataTimestampHeader();
   }

   public void listSystemResourceControls(String server, String target) {
      Collection targetServers = this.getMatchingRunningServerNames(server, target);
      if (targetServers.size() > 0) {
         Iterator var4 = targetServers.iterator();

         while(var4.hasNext()) {
            String s = (String)var4.next();
            this.listSystemResourceControls(s);
         }
      } else {
         this.listSystemResourceControls((String)null);
      }

   }

   private void listSystemResourceControls(String server) {
      ServerRuntimeMBean serverRuntime = this.getServerRuntime(server);
      WLDFControlRuntimeMBean wldfControlRuntime = serverRuntime.getWLDFRuntime().getWLDFControlRuntime();
      if (wldfControlRuntime != null) {
         WLDFSystemResourceControlRuntimeMBean[] controls = wldfControlRuntime.getSystemResourceControls();
         this.scriptContext.println("\n" + serverRuntime.getName() + ":");
         if (controls != null) {
            this.scriptContext.println(this.txtFmt.getFormattedSystemResourceControlInfoHeader());
            WLDFSystemResourceControlRuntimeMBean[] var5 = controls;
            int var6 = controls.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               WLDFSystemResourceControlRuntimeMBean control = var5[var7];
               this.scriptContext.println(this.txtFmt.getFormattedSystemResourceControlInfo(control.getName(), control.isExternalResource(), control.isEnabled()));
            }
         }

      }
   }

   public void enableSystemResource(String resourceName, String server, String target) {
      this.enableSystemResource(resourceName, server, target, true);
   }

   public void disableSystemResource(String resourceName, String server, String target) {
      this.enableSystemResource(resourceName, server, target, false);
   }

   private void enableSystemResource(String resourceName, String server, String target, boolean enable) {
      Collection targetServers = this.getMatchingRunningServerNames(server, target);
      if (targetServers.size() > 0) {
         Iterator var6 = targetServers.iterator();

         while(var6.hasNext()) {
            String s = (String)var6.next();
            WLDFSystemResourceControlRuntimeMBean control = this.getSystemResourceControl(s, resourceName);
            if (control != null) {
               control.setEnabled(enable);
            } else {
               this.scriptContext.println(this.txtFmt.getWarnSystemResourceNotExist(resourceName, s));
            }
         }
      } else {
         WLDFSystemResourceControlRuntimeMBean control = this.getSystemResourceControl((String)null, resourceName);
         if (control != null) {
            control.setEnabled(enable);
         } else {
            this.scriptContext.println(this.txtFmt.getWarnSystemResourceNotExist(resourceName, ""));
         }
      }

   }

   public void createSystemResourceControl(String resourceName, String descriptorFile, String server, String target, String enabled) throws Throwable {
      BufferedReader in = null;

      try {
         boolean isEnabled = Boolean.parseBoolean(enabled);
         in = new BufferedReader(new FileReader(descriptorFile));
         StringBuffer buf = new StringBuffer();

         String line;
         while((line = in.readLine()) != null) {
            buf.append(line);
         }

         String descriptor = buf.toString();
         Collection targetServers = this.getMatchingRunningServerNames(server, target);
         if (targetServers.size() > 0) {
            this.checkResourceExists(resourceName, targetServers);
            Iterator var12 = targetServers.iterator();

            while(var12.hasNext()) {
               String s = (String)var12.next();
               this.createSystemResourceControl(s, resourceName, descriptor, isEnabled);
            }

         } else {
            this.createSystemResourceControl((String)null, resourceName, descriptor, isEnabled);
         }
      } finally {
         if (in != null) {
            in.close();
         }

      }
   }

   private void createSystemResourceControl(String server, String resourceName, String descriptor, boolean isEnabled) throws Throwable {
      WLDFSystemResourceControlRuntimeMBean resourceControl = this.getWLDFControlRuntimeMBean(server).createSystemResourceControl(resourceName, descriptor);
      resourceControl.setEnabled(isEnabled);
   }

   public void destroySystemResourceControl(String resourceName, String server, String target) throws Throwable {
      Collection targetServers = this.getMatchingRunningServerNames(server, target);
      if (targetServers.size() > 0) {
         Iterator var5 = targetServers.iterator();

         while(var5.hasNext()) {
            String s = (String)var5.next();
            this.destroySystemResourceControl(s, resourceName);
         }
      } else {
         this.destroySystemResourceControl((String)null, resourceName);
      }

   }

   private void destroySystemResourceControl(String server, String resourceName) throws Throwable {
      WLDFControlRuntimeMBean controlRuntime = this.getWLDFControlRuntimeMBean(server);
      WLDFSystemResourceControlRuntimeMBean control = controlRuntime.lookupSystemResourceControl(resourceName);
      if (control != null) {
         controlRuntime.destroySystemResourceControl(control);
      } else {
         this.scriptContext.println(this.txtFmt.getWarnSystemResourceNotExist(resourceName, server != null ? server : ""));
      }

   }

   public String[] getAvailableCapturedImages(String server, String partitionName) throws Throwable {
      return this.getWLDFPartitionImageRuntimeMBean(server, partitionName).getAvailableCapturedImages();
   }

   public void purgeCapturedImages(String server, String partition, String age) throws Throwable {
      this.scriptContext.println(this.txtFmt.beginPurgeCapturedImages());
      this.getWLDFPartitionImageRuntimeMBean(server, partition).purgeCapturedImages(age);
      this.scriptContext.println(this.txtFmt.endPurgeCapturedImages());
   }

   public void saveDiagnosticImageCaptureFile(String imageName, String server, String destFile, String partitionName) throws Throwable {
      String message = partitionName == null ? this.txtFmt.getCaptureImageFromServer(imageName, server != null ? server : "", destFile) : this.txtFmt.getCaptureImageFromServerPartition(imageName, server != null ? server : "", destFile, partitionName);
      this.scriptContext.println(message);
      this.retrieveAndSaveImageArtifact(server, imageName, (String)null, destFile, partitionName);
   }

   public void saveDiagnosticImageCaptureEntryFile(String imageName, String imageEntryName, String server, String destFile, String partitionName) throws Throwable {
      String message = partitionName == null ? this.txtFmt.getCaptureImageEntryFromServer(imageName, imageEntryName, server != null ? server : "", destFile) : this.txtFmt.getCaptureImageEntryFromServerPartition(imageName, imageEntryName, server != null ? server : "", destFile, partitionName);
      this.scriptContext.println(message);
      this.retrieveAndSaveImageArtifact(server, imageName, imageEntryName, destFile, partitionName);
   }

   private void retrieveAndSaveImageArtifact(String server, String imageName, String imageEntry, String destFileName, String partitionName) throws Exception {
      WLDFPartitionImageRuntimeMBean imageRuntime = this.getWLDFPartitionImageRuntimeMBean(server, partitionName);
      String handle = imageRuntime.openImageDataStream(imageName, imageEntry);
      OutputStream output = null;

      try {
         File destFile = new File(destFileName);
         File parent = destFile.getParentFile();
         if (parent != null) {
            parent.mkdirs();
         }

         output = new BufferedOutputStream(new FileOutputStream(destFile));
         byte[] bytes = null;

         while(true) {
            byte[] bytes = imageRuntime.getNextImageDataChunk(handle);
            if (bytes == null || bytes.length <= 0) {
               return;
            }

            output.write(bytes);
         }
      } finally {
         imageRuntime.closeImageDataStream(handle);
         if (output != null) {
            output.flush();
            output.close();
         }

      }
   }

   public void captureAndSaveDiagnosticImage(String server, String target, String outputFile, String outputDir, String partitionName) throws Throwable {
      this.scriptContext.println(this.txtFmt.getCaptureAndSaveImage());
      Collection targetServers = this.getMatchingRunningServerNames(server, target);
      if (targetServers.size() > 1) {
         Iterator var7 = targetServers.iterator();

         while(var7.hasNext()) {
            String s = (String)var7.next();
            this.captureAndSaveDiagnosticImage(s, (String)null, outputDir, partitionName);
         }
      } else {
         String[] names = (String[])targetServers.toArray(new String[0]);
         server = names.length > 0 ? names[0] : null;
         this.captureAndSaveDiagnosticImage(server, outputFile, outputDir, partitionName);
      }

   }

   private void captureAndSaveDiagnosticImage(String server, String outputFile, String outputDir, String partitionName) throws Throwable {
      String image = this.captureDiagnosticImage(server, partitionName);
      if (outputFile == null) {
         if (outputDir != null) {
            File dir = new File(outputDir);
            if (!dir.exists() && !dir.mkdirs()) {
               throw new IOException(this.txtFmt.getUnableToCreateDirectory(dir.getAbsolutePath()));
            }

            File file = new File(dir, image);
            outputFile = file.getAbsolutePath();
         } else {
            outputFile = image;
         }
      }

      this.saveDiagnosticImageCaptureFile(image, server, outputFile, partitionName);
   }

   public void dumpDiagnosticData(String resourceName, String server, String file, long freq, long duration, String dateFormat) throws Throwable {
      WLDFSystemResourceControlRuntimeMBean control = this.getSystemResourceControl(server, resourceName);
      if (control == null) {
         throw new IllegalArgumentException(this.txtFmt.getSystemResourceNotExist(resourceName, server != null ? server : ""));
      } else {
         File outputFile = new File(file);
         if (outputFile.isDirectory()) {
            throw new IllegalArgumentException(file + " is a directory");
         } else {
            String currentServerName = server == null ? this.scriptContext.getServerName() : server;
            System.out.println(this.txtFmt.getDumpDiagonsticDataCaptureStart(resourceName, currentServerName, Long.toString(freq), Long.toString(duration), file, outputFile.getAbsoluteFile().getParent()));
            String pattern = dateFormat != null ? dateFormat : "EEE MM/dd/YY k:mm:ss:SSS z";
            HarvestedDataCSVWriter csvWriter = new HarvestedDataCSVWriter(outputFile, pattern);

            try {
               WLDFHarvesterManagerRuntimeMBean harvesterManager = control.getHarvesterManagerRuntime();
               SortedMap sortedSnapShot = new TreeMap(harvesterManager.retrieveSnapshot());
               csvWriter.open();
               List headerColumns = csvWriter.buildHeaderColumns(sortedSnapShot);
               csvWriter.writeValues(headerColumns);
               long currentTimestamp = System.currentTimeMillis();
               csvWriter.writeAttributeList(currentTimestamp, sortedSnapShot);
               long endTime = currentTimestamp + duration;

               while(System.currentTimeMillis() < endTime) {
                  try {
                     Thread.sleep(freq);
                  } catch (InterruptedException var26) {
                  }

                  sortedSnapShot = new TreeMap(harvesterManager.retrieveSnapshot());
                  List currentSnapshotMetrics = csvWriter.buildHeaderColumns(sortedSnapShot);
                  if (!headerColumns.equals(currentSnapshotMetrics)) {
                     System.out.println();
                     System.out.println(this.txtFmt.getDumpDiagosticDataInstanceSetChanged(outputFile.getName()));
                     csvWriter.openNext();
                     headerColumns = currentSnapshotMetrics;
                     csvWriter.writeValues(currentSnapshotMetrics);
                  }

                  csvWriter.writeAttributeList(System.currentTimeMillis(), sortedSnapShot);
                  System.out.print(".");
               }
            } finally {
               if (csvWriter != null) {
                  csvWriter.close();
               }

            }

            System.out.println();
            System.out.println(this.txtFmt.getDumpDiagnosticDataCaptureComplete());
         }
      }
   }

   public void mergeDumpedDiagnosticData(String inputDir, String toFile) throws Exception {
      if (inputDir == null) {
         throw new IllegalArgumentException(this.txtFmt.getMergeDiagnosticDataNullInputDirName());
      } else {
         File inDir = new File(inputDir);
         if (inDir.exists() && inDir.isDirectory()) {
            String outFileName = toFile == null ? "merged.csv" : toFile;
            File outFile = new File(outFileName);
            if (outFile.exists() && !outFile.isFile()) {
               System.out.println(this.txtFmt.getMergeDiagnosticDataInvalidOutputFileName(outFileName));
            } else {
               System.out.println(this.txtFmt.getMergeDiagnosticDataStart(inDir.getAbsolutePath()));
               SortedSet fileSet = this.orderDumpFileSet(inDir);
               System.out.println(this.txtFmt.getMergeDiagnosticDataBuildKeySet());
               SortedSet outputKeys = new TreeSet();
               Iterator var8 = fileSet.iterator();

               while(var8.hasNext()) {
                  DumpFileProperties p = (DumpFileProperties)var8.next();
                  outputKeys.addAll(p.getKeys());
               }

               List outputKeysAsList = new ArrayList(outputKeys.size() + 2);
               outputKeysAsList.add(this.dateHeader);
               outputKeysAsList.add(this.timestampHeader);
               outputKeysAsList.addAll(outputKeys);
               System.out.println(this.txtFmt.getMergeDiagnosticTotalMergedKeys(outputKeysAsList.size()));
               HarvestedDataCSVWriter csvWriter = new HarvestedDataCSVWriter(outFile, "EEE MM/dd/YY k:mm:ss:SSS z");

               try {
                  csvWriter.open();
                  csvWriter.writeValues(outputKeysAsList);
                  Iterator var10 = fileSet.iterator();

                  label315:
                  while(var10.hasNext()) {
                     DumpFileProperties fileProps = (DumpFileProperties)var10.next();
                     File input = fileProps.getFile();
                     System.out.println(this.txtFmt.getMergeDiagnosticDataOpenFile(input.getName()));
                     BufferedReader br = null;

                     try {
                        br = new BufferedReader(new FileReader(input));
                        List fileKeys = this.splitCSV(br.readLine());
                        if (fileKeys.size() < 2) {
                           throw new IllegalArgumentException(this.txtFmt.getMergeDiagnosticDataInvalidDataSet(input.getName()));
                        }

                        int lineno = 0;

                        while(true) {
                           while(true) {
                              String line;
                              if ((line = br.readLine()) == null) {
                                 continue label315;
                              }

                              ++lineno;
                              List outValuesList = new ArrayList(outputKeys.size());
                              List values = this.splitCSV(line);
                              if (values.size() != fileKeys.size()) {
                                 System.out.println(this.txtFmt.getMergeDiagnosticDataUnexpectedFormat(input.getName(), Integer.toString(fileKeys.size()), Integer.toString(values.size()), Integer.toString(lineno), values.toString()));
                              } else {
                                 Iterator var19 = outputKeysAsList.iterator();

                                 while(var19.hasNext()) {
                                    String masterKey = (String)var19.next();
                                    int inputValuesIndex = fileKeys.indexOf(masterKey);
                                    String value = inputValuesIndex >= 0 ? (String)values.get(inputValuesIndex) : "";
                                    outValuesList.add(value);
                                 }

                                 csvWriter.writeValues(outValuesList);
                              }
                           }
                        }
                     } catch (Exception var31) {
                        System.out.println(this.txtFmt.getMergeDiagnosticDataFileError(input.getName(), var31));
                     } finally {
                        if (br != null) {
                           br.close();
                        }

                     }
                  }
               } finally {
                  if (csvWriter != null) {
                     csvWriter.close();
                  }

               }

               System.out.println(this.txtFmt.getMergeDiagnosticDataComplete());
            }
         } else {
            throw new IllegalArgumentException(this.txtFmt.getMergeDiagnosticDataInputDirDoesNotExist(inputDir));
         }
      }
   }

   public String captureDiagnosticImage(String server, String partitionName) throws Throwable {
      WLDFPartitionImageRuntimeMBean imageRuntime = this.getWLDFPartitionImageRuntimeMBean(server, partitionName);
      WLDFImageCreationTaskRuntimeMBean task = imageRuntime.captureImage();

      while(!task.getStatus().equals("Completed")) {
         if (task.getStatus().equals("Failed")) {
            throw new RuntimeException(this.scriptContext.getWLSTMsgFormatter().getImageCaptureFailed("" + task.getError()));
         }

         try {
            Thread.sleep(2000L);
         } catch (InterruptedException var6) {
         }
      }

      String imageCreated = task.getDescription();
      this.scriptContext.println(this.scriptContext.getWLSTMsgFormatter().getImageCreated((new File(imageCreated)).getName()));
      return (new File(imageCreated)).getName();
   }

   public void exportDiagnosticDataFromServer(PyObject[] args, String[] kw) throws Exception {
      PyString logicalName = (PyString)args[0];
      PyString query = (PyString)args[1];
      PyLong begin = (PyLong)args[2];
      PyLong end = (PyLong)args[3];
      PyString exportFileName = (PyString)args[4];
      PyString partitionName = (PyString)args[5];
      PyString targetServerName = (PyString)args[6];
      PyString format = (PyString)args[7];
      PyString last = (PyString)args[8];
      String server = targetServerName != null ? targetServerName.toString() : "";
      String partition = partitionName != null ? partitionName.toString() : "";
      AccessRuntimeMBean accessRuntime = this.getAccessRuntimeMBean(server, partition);
      DataAccessRuntimeMBean dataAccessRuntime = accessRuntime.lookupDataAccessRuntime(logicalName.toString());
      if (dataAccessRuntime == null) {
         throw new IllegalArgumentException(this.txtFmt.getInvalidLogicalNameMsgText(logicalName.toString()));
      } else {
         long beginTimestamp = begin.getValue().longValue();
         long endTimestamp = end.getValue().longValue();
         String handle;
         if (last != null) {
            handle = last.toString();
            if (handle != null && !handle.isEmpty()) {
               long[] range = DateUtils.getTimestampRange(handle);
               beginTimestamp = range[0];
               endTimestamp = range[1];
            }
         }

         handle = dataAccessRuntime.openQueryResultDataStream(beginTimestamp, endTimestamp, query.toString(), format.toString());
         OutputStream output = null;

         try {
            output = new BufferedOutputStream(new FileOutputStream(new File(exportFileName.toString())));
            byte[] bytes = null;

            while(true) {
               byte[] bytes = dataAccessRuntime.getNextQueryResultDataChunk(handle);
               if (bytes == null || bytes.length <= 0) {
                  return;
               }

               output.write(bytes);
            }
         } finally {
            dataAccessRuntime.closeQueryResultDataStream(handle);
            if (output != null) {
               output.flush();
               output.close();
            }

         }
      }
   }

   public void exportHarvestedTimeSeriesData(String moduleName, String serverName, String partitionName, long beginTimestamp, long endTimestamp, String exportFilePath, String dateTimePattern, String last) throws Exception {
      if (moduleName != null && !moduleName.isEmpty()) {
         File exportFile = new File(exportFilePath);
         if (!exportFile.isDirectory() && !exportFile.isHidden()) {
            if (beginTimestamp < 0L) {
               beginTimestamp = 0L;
            }

            if (endTimestamp < 0L) {
               endTimestamp = Long.MAX_VALUE;
            }

            if (last != null && !last.isEmpty()) {
               long[] range = DateUtils.getTimestampRange(last);
               beginTimestamp = range[0];
               endTimestamp = range[1];
            }

            AccessRuntimeMBean accessRuntime = this.getAccessRuntimeMBean(serverName, partitionName);
            DataAccessRuntimeMBean dataAccessRuntime = accessRuntime.lookupDataAccessRuntime("HarvestedDataArchive");
            Iterator dataRecords = dataAccessRuntime.retrieveDataRecords(beginTimestamp, endTimestamp, "WLDFMODULE='" + moduleName + "'");
            HarvestedDataCSVWriter csvWriter = new HarvestedDataCSVWriter(exportFile, dateTimePattern);

            try {
               csvWriter.open();
               csvWriter.writeTimeSeriesData(dataRecords);
            } finally {
               csvWriter.close();
            }

            System.out.println();
            System.out.println(this.txtFmt.getDumpDiagnosticDataCaptureComplete());
         } else {
            throw new IllegalArgumentException(this.txtFmt.getMergeDiagnosticDataInvalidOutputFileName(exportFilePath));
         }
      } else {
         throw new IllegalArgumentException(this.txtFmt.getWLDFModuleNameEmptyMsgText());
      }
   }

   public String[] getAvailableDiagnosticDataAccessorNames(String server, String partitionName) throws Throwable {
      return this.getAccessRuntimeMBean(server, partitionName).getAvailableDiagnosticDataAccessorNames();
   }

   public void listDebugPatches(String target) throws Throwable {
      Collection targetServers = this.getMatchingRunningServerNames((String)null, target);
      if (targetServers.size() > 0) {
         Iterator var3 = targetServers.iterator();

         while(var3.hasNext()) {
            String s = (String)var3.next();
            this.listDebugPatches_internal(s);
         }
      } else {
         this.listDebugPatches_internal((String)null);
      }

   }

   private void listDebugPatches_internal(String server) throws Throwable {
      ServerRuntimeMBean serverRuntime = this.getServerRuntime(server);
      WLDFDebugPatchesRuntimeMBean debugPatchesRuntime = serverRuntime.getWLDFRuntime().getWLDFDebugPatchesRuntime();
      String[] patches = debugPatchesRuntime.getActiveDebugPatches();
      this.scriptContext.println(serverRuntime.getName() + ":");
      this.scriptContext.println("Active Patches:");
      String[] available;
      int var7;
      if (patches != null) {
         available = patches;
         int var6 = patches.length;

         for(var7 = 0; var7 < var6; ++var7) {
            String p = available[var7];
            this.scriptContext.println("    " + p);
         }
      }

      this.scriptContext.println("Available Patches:");
      available = debugPatchesRuntime.getAvailableDebugPatches();
      if (available != null) {
         String[] var10 = available;
         var7 = available.length;

         for(int var11 = 0; var11 < var7; ++var11) {
            String p = var10[var11];
            this.scriptContext.println("    " + p);
         }
      }

      this.scriptContext.println("\n");
   }

   public void showDebugPatchInfo(String patch, String target) throws Throwable {
      Collection targetServers = this.getMatchingRunningServerNames((String)null, target);
      if (targetServers.size() > 0) {
         Iterator var4 = targetServers.iterator();

         while(var4.hasNext()) {
            String server = (String)var4.next();
            this.showDebugPatchInfo_internal(patch, server);
         }
      } else {
         this.showDebugPatchInfo_internal(patch, (String)null);
      }

   }

   private void showDebugPatchInfo_internal(String patch, String server) throws Throwable {
      ServerRuntimeMBean serverRuntime = this.getServerRuntime(server);
      WLDFDebugPatchesRuntimeMBean debugPatchesRuntime = serverRuntime.getWLDFRuntime().getWLDFDebugPatchesRuntime();
      String info = debugPatchesRuntime.showDebugPatchInfo(patch);
      this.scriptContext.println(serverRuntime.getName() + ":");
      this.scriptContext.println(info);
      this.scriptContext.println("\n");
   }

   public WebLogicMBean[] activateDebugPatch(String patch, String app, String module, String partition, String target) throws Throwable {
      Collection targetServers = this.getMatchingRunningServerNames((String)null, target);
      if (targetServers.size() <= 0) {
         WebLogicMBean task = this.activateDebugPatch_internal(patch, app, module, partition, (String)null);
         return new WebLogicMBean[]{task};
      } else {
         WebLogicMBean[] tasks = new WebLogicMBean[targetServers.size()];
         int index = 0;

         String server;
         for(Iterator var9 = targetServers.iterator(); var9.hasNext(); tasks[index++] = this.activateDebugPatch_internal(patch, app, module, partition, server)) {
            server = (String)var9.next();
         }

         return tasks;
      }
   }

   private WebLogicMBean activateDebugPatch_internal(String patch, String app, String module, String partition, String server) throws Throwable {
      ServerRuntimeMBean serverRuntime = this.getServerRuntime(server);
      WLDFDebugPatchesRuntimeMBean debugPatchesRuntime = serverRuntime.getWLDFRuntime().getWLDFDebugPatchesRuntime();
      return debugPatchesRuntime.activateDebugPatch(patch, app, module, partition);
   }

   public WebLogicMBean[] deactivateDebugPatches(String patch, String app, String module, String partition, String target) throws Throwable {
      Collection targetServers = this.getMatchingRunningServerNames((String)null, target);
      if (targetServers.size() <= 0) {
         WebLogicMBean task = this.deactivateDebugPatches_internal(patch, app, module, partition, (String)null);
         return new WebLogicMBean[]{task};
      } else {
         WebLogicMBean[] tasks = new WebLogicMBean[targetServers.size()];
         int index = 0;

         String server;
         for(Iterator var9 = targetServers.iterator(); var9.hasNext(); tasks[index++] = this.deactivateDebugPatches_internal(patch, app, module, partition, server)) {
            server = (String)var9.next();
         }

         return tasks;
      }
   }

   private WebLogicMBean deactivateDebugPatches_internal(String patch, String app, String module, String partition, String server) throws Throwable {
      ServerRuntimeMBean serverRuntime = this.getServerRuntime(server);
      WLDFDebugPatchesRuntimeMBean debugPatchesRuntime = serverRuntime.getWLDFRuntime().getWLDFDebugPatchesRuntime();
      return debugPatchesRuntime.deactivateDebugPatches(patch, app, module, partition);
   }

   public WebLogicMBean[] listDebugPatchTasks(String target) throws Throwable {
      Collection targetServers = this.getMatchingRunningServerNames((String)null, target);
      if (targetServers.size() <= 0) {
         return this.listDebugPatchTasks_internal((String)null);
      } else {
         List tasksList = new ArrayList();
         Iterator var4 = targetServers.iterator();

         while(true) {
            WebLogicMBean[] tasks;
            do {
               if (!var4.hasNext()) {
                  return (WebLogicMBean[])((WebLogicMBean[])tasksList.toArray(new WebLogicMBean[tasksList.size()]));
               }

               String server = (String)var4.next();
               tasks = this.listDebugPatchTasks_internal(server);
            } while(tasks == null);

            WebLogicMBean[] var7 = tasks;
            int var8 = tasks.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               WebLogicMBean t = var7[var9];
               tasksList.add(t);
            }
         }
      }
   }

   public WebLogicMBean[] listDebugPatchTasks_internal(String server) throws Throwable {
      ServerRuntimeMBean serverRuntime = this.getServerRuntime(server);
      WLDFDebugPatchesRuntimeMBean debugPatchesRuntime = serverRuntime.getWLDFRuntime().getWLDFDebugPatchesRuntime();
      WLDFDebugPatchTaskRuntimeMBean[] tasks = debugPatchesRuntime.getDebugPatchTasks();
      this.scriptContext.println(serverRuntime.getName() + ":");
      if (tasks != null) {
         int index = 0;
         WLDFDebugPatchTaskRuntimeMBean[] var6 = tasks;
         int var7 = tasks.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            WLDFDebugPatchTaskRuntimeMBean task = var6[var8];
            StringBuffer buf = new StringBuffer();
            StringBuffer var10000 = buf.append("  [");
            ++index;
            var10000.append(index).append("] ").append(task.getName()).append(" ").append(task.isActivationTask() ? "ACTIVATE " : "DEACTIVATE ").append(task.getPatches()).append("  ").append(task.getStatus());
            this.scriptContext.println(buf.toString());
         }

         this.scriptContext.println("\n");
      }

      return tasks;
   }

   public void purgeDebugPatchTasks(String target) throws Throwable {
      Collection targetServers = this.getMatchingRunningServerNames((String)null, target);
      if (targetServers.size() > 0) {
         Iterator var3 = targetServers.iterator();

         while(var3.hasNext()) {
            String server = (String)var3.next();
            this.purgeDebugPatchTasks_internal(server);
         }
      } else {
         this.purgeDebugPatchTasks_internal((String)null);
      }

   }

   public void purgeDebugPatchTasks_internal(String server) throws Throwable {
      ServerRuntimeMBean serverRuntime = this.getServerRuntime(server);
      WLDFDebugPatchesRuntimeMBean debugPatchesRuntime = serverRuntime.getWLDFRuntime().getWLDFDebugPatchesRuntime();
      debugPatchesRuntime.clearDebugPatchTasks();
   }

   public WebLogicMBean[] deactivateAllDebugPatches(String target) throws Throwable {
      Collection targetServers = this.getMatchingRunningServerNames((String)null, target);
      if (targetServers.size() <= 0) {
         WebLogicMBean task = this.deactivateAllDebugPatches_internal((String)null);
         return new WebLogicMBean[]{task};
      } else {
         WebLogicMBean[] tasks = new WebLogicMBean[targetServers.size()];
         int index = 0;

         String server;
         for(Iterator var5 = targetServers.iterator(); var5.hasNext(); tasks[index++] = this.deactivateAllDebugPatches_internal(server)) {
            server = (String)var5.next();
         }

         return tasks;
      }
   }

   public WebLogicMBean deactivateAllDebugPatches_internal(String server) throws Throwable {
      ServerRuntimeMBean serverRuntime = this.getServerRuntime(server);
      WLDFDebugPatchesRuntimeMBean debugPatchesRuntime = serverRuntime.getWLDFRuntime().getWLDFDebugPatchesRuntime();
      return debugPatchesRuntime.deactivateAllDebugPatches();
   }

   private AccessRuntimeMBean getAccessRuntimeMBean(String targetServerName, String partitionName) {
      String connectedPartition = this.scriptContext.getPartitionName();
      boolean connectedToDomain = "DOMAIN".equals(connectedPartition);
      if (connectedToDomain) {
         ServerRuntimeMBean serverRuntime = this.getServerRuntime(targetServerName);
         if (partitionName == null || partitionName.isEmpty() || partitionName.equals("DOMAIN")) {
            this.scriptContext.println(this.txtFmt.getServerDataAccessMsgText(serverRuntime.getName()));
            return serverRuntime.getWLDFRuntime().getWLDFAccessRuntime();
         }
      } else if (partitionName != null && !partitionName.isEmpty() && !connectedPartition.equals(partitionName)) {
         throw new IllegalArgumentException(this.txtFmt.getInvalidPartitionNameMsgText(partitionName));
      }

      PartitionRuntimeMBean partitionRuntime = this.getPartitionRuntimeMBean(connectedToDomain, targetServerName, partitionName);
      if (partitionRuntime == null) {
         throw new IllegalArgumentException(this.txtFmt.getInvalidPartitionNameMsgText(connectedToDomain ? partitionName : connectedPartition));
      } else {
         this.scriptContext.println(this.txtFmt.getPartitionDataAccessMsgText(targetServerName != null && !targetServerName.isEmpty() ? targetServerName : this.scriptContext.getServerName(), partitionRuntime.getName()));
         return partitionRuntime.getWLDFPartitionRuntime().getWLDFPartitionAccessRuntime();
      }
   }

   private PartitionRuntimeMBean getPartitionRuntimeMBean(boolean connectedToDomain, String server, String partitionName) {
      DomainRuntimeServiceMBean domainRuntime = this.scriptContext.getDomainRuntimeServiceMBean();
      PartitionRuntimeMBean partitionRuntime = null;
      if (domainRuntime == null) {
         if (server != null && !server.isEmpty() && !server.equals(this.scriptContext.getServerName())) {
            throw new IllegalArgumentException(this.txtFmt.getInvalidDomainRuntimeServiceAccess());
         }

         partitionRuntime = this.getServerRuntime((String)null).lookupPartitionRuntime(connectedToDomain ? partitionName : this.scriptContext.getPartitionName());
      } else {
         partitionRuntime = domainRuntime.findPartitionRuntime(connectedToDomain ? partitionName : this.scriptContext.getPartitionName(), server != null && !server.isEmpty() ? server : this.scriptContext.getServerName());
      }

      return partitionRuntime;
   }

   private void checkResourceExists(String resourceName, Collection targetServers) throws Throwable {
      String existsOn = null;
      Iterator var4 = targetServers.iterator();

      while(var4.hasNext()) {
         String server = (String)var4.next();
         WLDFControlRuntimeMBean ctlMBean = this.getWLDFControlRuntimeMBean(server);
         if (ctlMBean.lookupSystemResourceControl(resourceName) != null) {
            if (existsOn == null) {
               existsOn = server;
            } else {
               existsOn = existsOn + "," + server;
            }
         }
      }

      if (existsOn != null) {
         throw new InstanceAlreadyExistsException(this.txtFmt.getSystemResourceExistsOnServers(resourceName, existsOn));
      }
   }

   private Collection getMatchingRunningServerNames(String server, String targets) {
      Collection collection = new HashSet();
      if (server != null) {
         collection.add(server);
      }

      if (targets != null) {
         DomainRuntimeServiceMBean domainRuntime = this.scriptContext.getDomainRuntimeServiceMBean();
         if (domainRuntime == null) {
            throw new IllegalArgumentException(this.txtFmt.getInvalidDomainRuntimeServiceAccess());
         }

         ServerRuntimeMBean[] serverRuntimeMBeans = domainRuntime.getServerRuntimes();
         Collection serverNames = new HashSet();
         Map clusterMembersMap = new HashMap();
         ServerRuntimeMBean[] var8 = serverRuntimeMBeans;
         int var9 = serverRuntimeMBeans.length;

         int var10;
         for(var10 = 0; var10 < var9; ++var10) {
            ServerRuntimeMBean srmb = var8[var10];
            serverNames.add(srmb.getName());
            ClusterRuntimeMBean crmb = srmb.getClusterRuntime();
            if (crmb != null) {
               String clusterName = crmb.getName();
               Collection members = (Collection)clusterMembersMap.get(clusterName);
               if (members == null) {
                  members = new HashSet();
                  clusterMembersMap.put(clusterName, members);
               }

               ((Collection)members).add(srmb.getName());
            }
         }

         String[] names = targets.split(",");
         String[] var16 = names;
         var10 = names.length;

         for(int var17 = 0; var17 < var10; ++var17) {
            String name = var16[var17];
            name = name.trim();
            Collection members = (Collection)clusterMembersMap.get(name);
            if (members != null) {
               collection.addAll(members);
            } else {
               if (!serverNames.contains(name)) {
                  throw new IllegalArgumentException(this.txtFmt.getRunningServerOrClusterNotFound(name));
               }

               collection.add(name);
            }
         }
      }

      return collection;
   }

   private ServerRuntimeMBean getServerRuntime(String server) {
      ServerRuntimeMBean serverRuntime = null;
      if (server != null && !server.isEmpty()) {
         DomainRuntimeServiceMBean domainRuntime = this.scriptContext.getDomainRuntimeServiceMBean();
         if (domainRuntime == null) {
            throw new IllegalArgumentException(this.txtFmt.getDomainRuntimeServiceNotFound(server));
         }

         serverRuntime = domainRuntime.lookupServerRuntime(server);
      } else {
         serverRuntime = this.scriptContext.getServerRuntimeServerRuntimeMBean();
      }

      if (serverRuntime == null) {
         throw new IllegalArgumentException(this.txtFmt.getInvalidServerName(server));
      } else {
         return serverRuntime;
      }
   }

   private WLDFControlRuntimeMBean getWLDFControlRuntimeMBean(String server) {
      ServerRuntimeMBean serverRuntime = this.getServerRuntime(server);
      return serverRuntime.getWLDFRuntime().getWLDFControlRuntime();
   }

   private WLDFSystemResourceControlRuntimeMBean getSystemResourceControl(String server, String resourceName) {
      ServerRuntimeMBean serverRuntime = this.getServerRuntime(server);
      WLDFSystemResourceControlRuntimeMBean control = serverRuntime.getWLDFRuntime().getWLDFControlRuntime().lookupSystemResourceControl(resourceName);
      return control;
   }

   private SortedSet orderDumpFileSet(File inDir) throws IOException {
      File[] listFiles = inDir.listFiles();
      SortedSet fileSet = new TreeSet();
      File[] var4 = listFiles;
      int var5 = listFiles.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         File input = var4[var6];
         BufferedReader reader = null;

         try {
            reader = new BufferedReader(new FileReader(input));
            String headerLine = reader.readLine();
            Set keySet = this.buildKeySetFromLine(headerLine);
            String firstLine = reader.readLine();
            List record = this.splitCSV(firstLine);
            if (record.size() < 2 && record.size() - 2 != keySet.size()) {
               throw new IllegalArgumentException();
            }

            String timestampString = (String)record.get(1);
            Long timestamp = Long.parseLong(timestampString);
            fileSet.add(new DumpFileProperties(input, timestamp, keySet));
         } catch (NumberFormatException var21) {
            System.out.println(this.txtFmt.getMergeDiagnosticDataTimestampParseError(input.getName(), var21.getMessage()));
         } catch (IllegalArgumentException var22) {
            System.out.println(this.txtFmt.getMergeDiagnosticDataUnrecognizedFileFormat(input.getName()));
         } catch (FileNotFoundException var23) {
            System.out.println(this.txtFmt.getMergeDiagnosticDataInputFileNotFound(input.getName()));
         } catch (IOException var24) {
         } finally {
            if (reader != null) {
               reader.close();
            }

         }
      }

      return fileSet;
   }

   private Set buildKeySetFromLine(String headerLine) {
      List columns = this.splitCSV(headerLine);
      Set columnKeys = new HashSet(columns.size());

      for(int i = 0; i < columns.size(); ++i) {
         String colName = (String)columns.get(i);
         if (i < 2) {
            if (!colName.equals(this.timestampHeader) && !colName.equals(this.dateHeader)) {
               throw new IllegalArgumentException();
            }
         } else {
            columnKeys.add(colName);
         }
      }

      return columnKeys;
   }

   private List splitCSV(String line) {
      String[] cols = line.split(",");
      return Arrays.asList(cols);
   }

   private void printMap(Map mapInfo) {
      if (mapInfo != null) {
         Iterator it = mapInfo.keySet().iterator();

         while(it.hasNext()) {
            String key = (String)it.next();
            Object value = mapInfo.get(key);
            if (value instanceof Object[]) {
               System.out.println(key + ": " + Arrays.toString((Object[])((Object[])value)));
            } else {
               System.out.println(key + ": " + value);
            }
         }

      }
   }

   private WLDFPartitionImageRuntimeMBean getWLDFPartitionImageRuntimeMBean(String server, String partitionName) {
      String connectedPartition = this.scriptContext.getPartitionName();
      boolean connectedToDomain = "DOMAIN".equals(connectedPartition);
      if (connectedToDomain) {
         ServerRuntimeMBean serverRuntime = this.getServerRuntime(server);
         if (partitionName == null || partitionName.isEmpty() || partitionName.equals("DOMAIN")) {
            return serverRuntime.getWLDFRuntime().getWLDFImageRuntime();
         }
      } else if (partitionName != null && !connectedPartition.equals(partitionName)) {
         throw new IllegalArgumentException(this.txtFmt.getInvalidPartitionNameMsgText(partitionName));
      }

      PartitionRuntimeMBean partitionRuntime = this.getPartitionRuntimeMBean(connectedToDomain, server, partitionName);
      if (partitionRuntime == null) {
         throw new IllegalArgumentException(this.txtFmt.getInvalidPartitionNameMsgText(connectedToDomain ? partitionName : connectedPartition));
      } else {
         return partitionRuntime.getWLDFPartitionRuntime().getWLDFPartitionImageRuntime();
      }
   }

   private static class DumpFileProperties implements Comparable {
      private File file;
      private Long timestamp;
      private Set keys;

      public DumpFileProperties(File f, Long ts, Set keySet) {
         this.file = f;
         this.timestamp = ts;
         this.keys = keySet;
      }

      public Collection getKeys() {
         return this.keys;
      }

      public File getFile() {
         return this.file;
      }

      public int compareTo(DumpFileProperties o) {
         return (int)(this.timestamp - o.timestamp);
      }
   }
}
