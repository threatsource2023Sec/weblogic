package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.LoggingType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlInteger;
import com.bea.xml.XmlString;
import java.math.BigInteger;
import javax.xml.namespace.QName;

public class LoggingTypeImpl extends XmlComplexContentImpl implements LoggingType {
   private static final long serialVersionUID = 1L;
   private static final QName LOGFILENAME$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "log-filename");
   private static final QName LOGGINGENABLED$2 = new QName("http://www.bea.com/connector/monitoring1dot0", "logging-enabled");
   private static final QName ROTATIONTYPE$4 = new QName("http://www.bea.com/connector/monitoring1dot0", "rotation-type");
   private static final QName NUMBEROFFILESLIMITED$6 = new QName("http://www.bea.com/connector/monitoring1dot0", "number-of-files-limited");
   private static final QName FILECOUNT$8 = new QName("http://www.bea.com/connector/monitoring1dot0", "file-count");
   private static final QName FILESIZELIMIT$10 = new QName("http://www.bea.com/connector/monitoring1dot0", "file-size-limit");
   private static final QName ROTATELOGONSTARTUP$12 = new QName("http://www.bea.com/connector/monitoring1dot0", "rotate-log-on-startup");
   private static final QName LOGFILEROTATIONDIR$14 = new QName("http://www.bea.com/connector/monitoring1dot0", "log-file-rotation-dir");
   private static final QName ROTATIONTIME$16 = new QName("http://www.bea.com/connector/monitoring1dot0", "rotation-time");
   private static final QName FILETIMESPAN$18 = new QName("http://www.bea.com/connector/monitoring1dot0", "file-time-span");
   private static final QName DATEFORMATPATTERN$20 = new QName("http://www.bea.com/connector/monitoring1dot0", "date-format-pattern");

   public LoggingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getLogFilename() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOGFILENAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetLogFilename() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOGFILENAME$0, 0);
         return target;
      }
   }

   public boolean isSetLogFilename() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOGFILENAME$0) != 0;
      }
   }

   public void setLogFilename(String logFilename) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOGFILENAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LOGFILENAME$0);
         }

         target.setStringValue(logFilename);
      }
   }

   public void xsetLogFilename(XmlString logFilename) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOGFILENAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LOGFILENAME$0);
         }

         target.set(logFilename);
      }
   }

   public void unsetLogFilename() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOGFILENAME$0, 0);
      }
   }

   public boolean getLoggingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOGGINGENABLED$2, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetLoggingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(LOGGINGENABLED$2, 0);
         return target;
      }
   }

   public boolean isSetLoggingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOGGINGENABLED$2) != 0;
      }
   }

   public void setLoggingEnabled(boolean loggingEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOGGINGENABLED$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LOGGINGENABLED$2);
         }

         target.setBooleanValue(loggingEnabled);
      }
   }

   public void xsetLoggingEnabled(XmlBoolean loggingEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(LOGGINGENABLED$2, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(LOGGINGENABLED$2);
         }

         target.set(loggingEnabled);
      }
   }

   public void unsetLoggingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOGGINGENABLED$2, 0);
      }
   }

   public String getRotationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ROTATIONTYPE$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetRotationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ROTATIONTYPE$4, 0);
         return target;
      }
   }

   public boolean isSetRotationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ROTATIONTYPE$4) != 0;
      }
   }

   public void setRotationType(String rotationType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ROTATIONTYPE$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ROTATIONTYPE$4);
         }

         target.setStringValue(rotationType);
      }
   }

   public void xsetRotationType(XmlString rotationType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ROTATIONTYPE$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ROTATIONTYPE$4);
         }

         target.set(rotationType);
      }
   }

   public void unsetRotationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ROTATIONTYPE$4, 0);
      }
   }

   public boolean getNumberOfFilesLimited() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NUMBEROFFILESLIMITED$6, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetNumberOfFilesLimited() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(NUMBEROFFILESLIMITED$6, 0);
         return target;
      }
   }

   public boolean isSetNumberOfFilesLimited() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NUMBEROFFILESLIMITED$6) != 0;
      }
   }

   public void setNumberOfFilesLimited(boolean numberOfFilesLimited) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NUMBEROFFILESLIMITED$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NUMBEROFFILESLIMITED$6);
         }

         target.setBooleanValue(numberOfFilesLimited);
      }
   }

   public void xsetNumberOfFilesLimited(XmlBoolean numberOfFilesLimited) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(NUMBEROFFILESLIMITED$6, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(NUMBEROFFILESLIMITED$6);
         }

         target.set(numberOfFilesLimited);
      }
   }

   public void unsetNumberOfFilesLimited() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NUMBEROFFILESLIMITED$6, 0);
      }
   }

   public BigInteger getFileCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FILECOUNT$8, 0);
         return target == null ? null : target.getBigIntegerValue();
      }
   }

   public XmlInteger xgetFileCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(FILECOUNT$8, 0);
         return target;
      }
   }

   public boolean isSetFileCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FILECOUNT$8) != 0;
      }
   }

   public void setFileCount(BigInteger fileCount) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FILECOUNT$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FILECOUNT$8);
         }

         target.setBigIntegerValue(fileCount);
      }
   }

   public void xsetFileCount(XmlInteger fileCount) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(FILECOUNT$8, 0);
         if (target == null) {
            target = (XmlInteger)this.get_store().add_element_user(FILECOUNT$8);
         }

         target.set(fileCount);
      }
   }

   public void unsetFileCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FILECOUNT$8, 0);
      }
   }

   public BigInteger getFileSizeLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FILESIZELIMIT$10, 0);
         return target == null ? null : target.getBigIntegerValue();
      }
   }

   public XmlInteger xgetFileSizeLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(FILESIZELIMIT$10, 0);
         return target;
      }
   }

   public boolean isSetFileSizeLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FILESIZELIMIT$10) != 0;
      }
   }

   public void setFileSizeLimit(BigInteger fileSizeLimit) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FILESIZELIMIT$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FILESIZELIMIT$10);
         }

         target.setBigIntegerValue(fileSizeLimit);
      }
   }

   public void xsetFileSizeLimit(XmlInteger fileSizeLimit) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(FILESIZELIMIT$10, 0);
         if (target == null) {
            target = (XmlInteger)this.get_store().add_element_user(FILESIZELIMIT$10);
         }

         target.set(fileSizeLimit);
      }
   }

   public void unsetFileSizeLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FILESIZELIMIT$10, 0);
      }
   }

   public boolean getRotateLogOnStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ROTATELOGONSTARTUP$12, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetRotateLogOnStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ROTATELOGONSTARTUP$12, 0);
         return target;
      }
   }

   public boolean isSetRotateLogOnStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ROTATELOGONSTARTUP$12) != 0;
      }
   }

   public void setRotateLogOnStartup(boolean rotateLogOnStartup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ROTATELOGONSTARTUP$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ROTATELOGONSTARTUP$12);
         }

         target.setBooleanValue(rotateLogOnStartup);
      }
   }

   public void xsetRotateLogOnStartup(XmlBoolean rotateLogOnStartup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ROTATELOGONSTARTUP$12, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(ROTATELOGONSTARTUP$12);
         }

         target.set(rotateLogOnStartup);
      }
   }

   public void unsetRotateLogOnStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ROTATELOGONSTARTUP$12, 0);
      }
   }

   public String getLogFileRotationDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOGFILEROTATIONDIR$14, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetLogFileRotationDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOGFILEROTATIONDIR$14, 0);
         return target;
      }
   }

   public boolean isSetLogFileRotationDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOGFILEROTATIONDIR$14) != 0;
      }
   }

   public void setLogFileRotationDir(String logFileRotationDir) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOGFILEROTATIONDIR$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LOGFILEROTATIONDIR$14);
         }

         target.setStringValue(logFileRotationDir);
      }
   }

   public void xsetLogFileRotationDir(XmlString logFileRotationDir) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOGFILEROTATIONDIR$14, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LOGFILEROTATIONDIR$14);
         }

         target.set(logFileRotationDir);
      }
   }

   public void unsetLogFileRotationDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOGFILEROTATIONDIR$14, 0);
      }
   }

   public String getRotationTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ROTATIONTIME$16, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetRotationTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ROTATIONTIME$16, 0);
         return target;
      }
   }

   public boolean isSetRotationTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ROTATIONTIME$16) != 0;
      }
   }

   public void setRotationTime(String rotationTime) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ROTATIONTIME$16, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ROTATIONTIME$16);
         }

         target.setStringValue(rotationTime);
      }
   }

   public void xsetRotationTime(XmlString rotationTime) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ROTATIONTIME$16, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ROTATIONTIME$16);
         }

         target.set(rotationTime);
      }
   }

   public void unsetRotationTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ROTATIONTIME$16, 0);
      }
   }

   public BigInteger getFileTimeSpan() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FILETIMESPAN$18, 0);
         return target == null ? null : target.getBigIntegerValue();
      }
   }

   public XmlInteger xgetFileTimeSpan() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(FILETIMESPAN$18, 0);
         return target;
      }
   }

   public boolean isSetFileTimeSpan() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FILETIMESPAN$18) != 0;
      }
   }

   public void setFileTimeSpan(BigInteger fileTimeSpan) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FILETIMESPAN$18, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FILETIMESPAN$18);
         }

         target.setBigIntegerValue(fileTimeSpan);
      }
   }

   public void xsetFileTimeSpan(XmlInteger fileTimeSpan) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(FILETIMESPAN$18, 0);
         if (target == null) {
            target = (XmlInteger)this.get_store().add_element_user(FILETIMESPAN$18);
         }

         target.set(fileTimeSpan);
      }
   }

   public void unsetFileTimeSpan() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FILETIMESPAN$18, 0);
      }
   }

   public String getDateFormatPattern() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DATEFORMATPATTERN$20, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDateFormatPattern() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DATEFORMATPATTERN$20, 0);
         return target;
      }
   }

   public boolean isSetDateFormatPattern() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DATEFORMATPATTERN$20) != 0;
      }
   }

   public void setDateFormatPattern(String dateFormatPattern) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DATEFORMATPATTERN$20, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DATEFORMATPATTERN$20);
         }

         target.setStringValue(dateFormatPattern);
      }
   }

   public void xsetDateFormatPattern(XmlString dateFormatPattern) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DATEFORMATPATTERN$20, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DATEFORMATPATTERN$20);
         }

         target.set(dateFormatPattern);
      }
   }

   public void unsetDateFormatPattern() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DATEFORMATPATTERN$20, 0);
      }
   }
}
