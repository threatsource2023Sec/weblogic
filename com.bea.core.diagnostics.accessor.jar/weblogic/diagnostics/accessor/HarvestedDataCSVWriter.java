package weblogic.diagnostics.accessor;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public class HarvestedDataCSVWriter {
   private static final String CSV_FILENAME_EXTENSION = ".csv";
   public static final String CSV_DELIM = ",";
   private static final boolean DEBUG = false;
   private static AccessorMsgTextFormatter txtFmt = AccessorMsgTextFormatter.getInstance();
   private String exportDir;
   private String exportFileNamePrefix;
   private String exportFileNameSuffix;
   private SimpleDateFormat dateFormatter;
   private PrintWriter output;
   private AtomicInteger fileNameCounter;

   private HarvestedDataCSVWriter(String outputDir, String fileName, String dateTimePattern) {
      this.exportFileNameSuffix = ".csv";
      this.fileNameCounter = new AtomicInteger(0);
      this.exportDir = outputDir;
      this.exportFileNamePrefix = fileName;
      int endIndex = this.exportFileNamePrefix.lastIndexOf(".");
      if (endIndex > 0) {
         this.exportFileNamePrefix = fileName.substring(0, endIndex);
         this.exportFileNameSuffix = fileName.substring(endIndex);
      }

      this.dateFormatter = new SimpleDateFormat(dateTimePattern);
   }

   public HarvestedDataCSVWriter(File exportFile, String dateTimePattern) {
      this(exportFile.getParent(), exportFile.getName(), dateTimePattern);
   }

   public void writeTimeSeriesData(Iterator dataRecords) throws IOException, MalformedObjectNameException {
      long previousTimestamp = -1L;
      SortedMap currentSnapshot = new TreeMap();

      Object previousHeader;
      long timestamp;
      for(previousHeader = new ArrayList(); dataRecords.hasNext(); previousTimestamp = timestamp) {
         DataRecord dataRec = (DataRecord)dataRecords.next();
         Object[] row = dataRec.getValues();
         timestamp = (Long)row[1];
         String objNameStr = (String)row[5];
         ObjectName objName = new ObjectName(objNameStr);
         String attrName = (String)row[6];
         Object attrValue = row[8];
         if (previousTimestamp > 0L && timestamp != previousTimestamp) {
            List header = this.buildHeaderColumns(currentSnapshot);
            this.writeTimeSeriesData(previousTimestamp, currentSnapshot, header, (List)previousHeader);
            currentSnapshot = new TreeMap();
            previousHeader = header;
         }

         AttributeList attrList = (AttributeList)currentSnapshot.get(objName);
         if (attrList == null) {
            attrList = new AttributeList();
            currentSnapshot.put(objName, attrList);
         }

         attrList.add(new Attribute(attrName, attrValue));
      }

      List header = this.buildHeaderColumns(currentSnapshot);
      this.writeTimeSeriesData(previousTimestamp, currentSnapshot, header, (List)previousHeader);
   }

   public List buildHeaderColumns(SortedMap snapShot) {
      List headers = new ArrayList();
      Set keys = snapShot.keySet();
      headers.add(txtFmt.getDumpDiagonsticDataDateHeader());
      headers.add(txtFmt.getDumpDiagonsticDataTimestampHeader());
      Iterator var4 = keys.iterator();

      while(var4.hasNext()) {
         Object o = var4.next();
         String keyComponent = o.toString().replace(',', '%');
         AttributeList attributes = (AttributeList)snapShot.get(o);
         Iterator iterator = attributes.iterator();

         while(iterator.hasNext()) {
            Attribute attribute = (Attribute)iterator.next();
            headers.add(keyComponent + "::" + attribute.getName());
         }
      }

      return headers;
   }

   public void writeValues(List values) {
      StringBuffer buff = new StringBuffer();
      Iterator it = values.iterator();

      while(it.hasNext()) {
         buff.append((String)it.next());
         if (it.hasNext()) {
            buff.append(",");
         }
      }

      this.output.println(buff.toString());
      this.output.flush();
   }

   public void writeAttributeList(long timestampMillis, Map snapShot) {
      StringBuilder line = new StringBuilder();
      Date currentDateTime = new Date(timestampMillis);
      String formattedDate = this.dateFormatter.format(currentDateTime);
      line.append(formattedDate);
      line.append(",");
      line.append(Long.toString(timestampMillis));
      line.append(",");
      Iterator instanceIterator = snapShot.keySet().iterator();

      label27:
      while(instanceIterator.hasNext()) {
         ObjectName o = (ObjectName)instanceIterator.next();
         AttributeList attributes = (AttributeList)snapShot.get(o);
         Iterator attributeIt = attributes.iterator();

         while(true) {
            do {
               if (!attributeIt.hasNext()) {
                  continue label27;
               }

               Attribute attribute = (Attribute)attributeIt.next();
               line.append(attribute.getValue());
            } while(!attributeIt.hasNext() && !instanceIterator.hasNext());

            line.append(",");
         }
      }

      this.output.println(line.toString());
      this.output.flush();
   }

   public PrintWriter writeTimeSeriesData(long timestamp, SortedMap currentSnapshot, List header, List previousHeader) throws IOException {
      if (previousHeader.isEmpty()) {
         this.writeValues(header);
      } else if (!previousHeader.equals(header)) {
         this.openNext();
         this.writeValues(header);
      }

      this.writeAttributeList(timestamp, currentSnapshot);
      return this.output;
   }

   public void openNext() throws IOException {
      File newFile = new File(this.exportDir, this.exportFileNamePrefix + this.fileNameCounter.incrementAndGet() + this.exportFileNameSuffix);
      if (this.output != null) {
         this.output.close();
      }

      this.output = new PrintWriter(newFile);
      System.out.println(txtFmt.getDumpDiagnosticDataNewCaptureFile(newFile.getAbsolutePath()));
   }

   public void open() throws IOException {
      File newFile = new File(this.exportDir, this.exportFileNamePrefix + this.exportFileNameSuffix);
      this.output = new PrintWriter(newFile);
      System.out.println(txtFmt.getDumpDiagnosticDataNewCaptureFile(newFile.getAbsolutePath()));
   }

   public void close() {
      if (this.output != null) {
         this.output.close();
      }

   }
}
