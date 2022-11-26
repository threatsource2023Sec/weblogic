package weblogic.diagnostics.accessor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.utils.PlatformConstants;
import weblogic.xml.stax.XMLStreamOutputFactory;

public final class XMLDataWriter implements DiagnosticDataWriter {
   private static final XMLOutputFactory xmlOutputFactory = XMLStreamOutputFactory.newInstance();
   private static final String DIAG_DATA = "DiagnosticData";
   private static final String DATA_INFO = "DataInfo";
   private static final String COLUMN_INFO = "ColumnInfo";
   private static final String COL_NAME = "Name";
   private static final String COL_TYPE = "Type";
   private static final String DATA_REC = "DataRecord";
   private static final String COL_DATA = "ColumnData";
   private static final String INDENT = "  ";
   static final String EXPORT_SCHEMA_NAME_SPACE = "http://www.bea.com/ns/weblogic/90/diagnostics/accessor/Export";
   static final String EXPORT_SCHEMA_URL = "http://www.bea.com/ns/weblogic/90/diagnostics/accessor/export.xsd export.xsd";
   private XMLStreamWriter writer;
   private boolean autoFlush;

   public XMLDataWriter(OutputStream out) throws XMLStreamException {
      this(out, true);
   }

   public XMLDataWriter(OutputStream out, boolean flush) throws XMLStreamException {
      this.writer = xmlOutputFactory.createXMLStreamWriter(out, "UTF-8");
      this.autoFlush = flush;
   }

   public void writeDiagnosticData(ColumnInfo[] cols, Iterator iter) throws IOException {
      try {
         this.writer.writeStartDocument();
         this.writer.writeCharacters(PlatformConstants.EOL);
         this.writer.writeStartElement("DiagnosticData");
         this.writer.writeAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
         this.writer.writeAttribute("xsi:schemaLocation", "http://www.bea.com/ns/weblogic/90/diagnostics/accessor/export.xsd export.xsd");
         this.writer.writeAttribute("xmlns", "http://www.bea.com/ns/weblogic/90/diagnostics/accessor/Export");
         this.writer.writeCharacters(PlatformConstants.EOL);
         this.writeColumnInfo(cols);
         this.writeData(cols.length, iter);
         this.writer.writeEndElement();
         this.writer.writeCharacters(PlatformConstants.EOL);
         this.writer.writeEndDocument();
         if (this.autoFlush) {
            this.writer.flush();
         }

      } catch (XMLStreamException var4) {
         throw new IOException(var4);
      }
   }

   private void writeColumnInfo(ColumnInfo[] columns) throws XMLStreamException {
      if (columns != null) {
         this.writer.writeCharacters("  ");
         this.writer.writeStartElement("DataInfo");
         this.writer.writeCharacters(PlatformConstants.EOL);

         for(int i = 0; i < columns.length; ++i) {
            ColumnInfo col = columns[i];
            this.writer.writeCharacters("    ");
            this.writer.writeStartElement("ColumnInfo");
            this.writeElement("Name", col.getColumnName());
            this.writeElement("Type", col.getColumnTypeName());
            this.writer.writeEndElement();
            this.writer.writeCharacters(PlatformConstants.EOL);
            if (this.autoFlush) {
               this.writer.flush();
            }
         }

         this.writer.writeCharacters("  ");
         this.writer.writeEndElement();
         this.writer.writeCharacters(PlatformConstants.EOL);
      }
   }

   private void writeData(int columns, Iterator iter) throws XMLStreamException {
      if (iter != null) {
         while(iter.hasNext()) {
            this.writer.writeCharacters("  ");
            this.writer.writeStartElement("DataRecord");
            DataRecord dataRecord = (DataRecord)iter.next();

            for(int i = 0; i < columns; ++i) {
               this.writer.writeStartElement("ColumnData");
               Object data = dataRecord.get(i);
               this.writer.writeCharacters(data == null ? "" : data.toString());
               this.writer.writeEndElement();
            }

            this.writer.writeEndElement();
            this.writer.writeCharacters(PlatformConstants.EOL);
            if (this.autoFlush) {
               this.writer.flush();
            }
         }

      }
   }

   private void writeElement(String elementName, String elementText) throws XMLStreamException {
      this.writer.writeStartElement(elementName);
      this.writer.writeCharacters(elementText);
      this.writer.writeEndElement();
   }

   public void close() throws IOException {
      try {
         this.writer.close();
      } catch (XMLStreamException var2) {
         throw new IOException();
      }
   }
}
