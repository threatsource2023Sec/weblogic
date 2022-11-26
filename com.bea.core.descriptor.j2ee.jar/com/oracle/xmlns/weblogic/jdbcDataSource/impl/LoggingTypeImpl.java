package com.oracle.xmlns.weblogic.jdbcDataSource.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.jdbcDataSource.LoggingType;
import com.oracle.xmlns.weblogic.jdbcDataSource.TrueFalseType;
import com.sun.java.xml.ns.javaee.String;
import com.sun.java.xml.ns.javaee.XsdPositiveIntegerType;
import javax.xml.namespace.QName;

public class LoggingTypeImpl extends XmlComplexContentImpl implements LoggingType {
   private static final long serialVersionUID = 1L;
   private static final QName LOGFILENAME$0 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "log-filename");
   private static final QName LOGGINGENABLED$2 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "logging-enabled");
   private static final QName ROTATIONTYPE$4 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "rotation-type");
   private static final QName NUMBEROFFILESLIMITED$6 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "number-of-files-limited");
   private static final QName FILECOUNT$8 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "file-count");
   private static final QName FILESIZELIMIT$10 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "file-size-limit");
   private static final QName ROTATELOGONSTARTUP$12 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "rotate-log-on-startup");
   private static final QName LOGFILEROTATIONDIR$14 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "log-file-rotation-dir");
   private static final QName ROTATIONTIME$16 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "rotation-time");
   private static final QName FILETIMESPAN$18 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "file-time-span");
   private static final QName DATEFORMATPATTERN$20 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "date-format-pattern");
   private static final QName ID$22 = new QName("", "id");

   public LoggingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getLogFilename() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(LOGFILENAME$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLogFilename() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOGFILENAME$0) != 0;
      }
   }

   public void setLogFilename(String logFilename) {
      this.generatedSetterHelperImpl(logFilename, LOGFILENAME$0, 0, (short)1);
   }

   public String addNewLogFilename() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(LOGFILENAME$0);
         return target;
      }
   }

   public void unsetLogFilename() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOGFILENAME$0, 0);
      }
   }

   public TrueFalseType getLoggingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(LOGGINGENABLED$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLoggingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOGGINGENABLED$2) != 0;
      }
   }

   public void setLoggingEnabled(TrueFalseType loggingEnabled) {
      this.generatedSetterHelperImpl(loggingEnabled, LOGGINGENABLED$2, 0, (short)1);
   }

   public TrueFalseType addNewLoggingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(LOGGINGENABLED$2);
         return target;
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
         String target = null;
         target = (String)this.get_store().find_element_user(ROTATIONTYPE$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRotationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ROTATIONTYPE$4) != 0;
      }
   }

   public void setRotationType(String rotationType) {
      this.generatedSetterHelperImpl(rotationType, ROTATIONTYPE$4, 0, (short)1);
   }

   public String addNewRotationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(ROTATIONTYPE$4);
         return target;
      }
   }

   public void unsetRotationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ROTATIONTYPE$4, 0);
      }
   }

   public TrueFalseType getNumberOfFilesLimited() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(NUMBEROFFILESLIMITED$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetNumberOfFilesLimited() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NUMBEROFFILESLIMITED$6) != 0;
      }
   }

   public void setNumberOfFilesLimited(TrueFalseType numberOfFilesLimited) {
      this.generatedSetterHelperImpl(numberOfFilesLimited, NUMBEROFFILESLIMITED$6, 0, (short)1);
   }

   public TrueFalseType addNewNumberOfFilesLimited() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(NUMBEROFFILESLIMITED$6);
         return target;
      }
   }

   public void unsetNumberOfFilesLimited() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NUMBEROFFILESLIMITED$6, 0);
      }
   }

   public XsdPositiveIntegerType getFileCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdPositiveIntegerType target = null;
         target = (XsdPositiveIntegerType)this.get_store().find_element_user(FILECOUNT$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetFileCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FILECOUNT$8) != 0;
      }
   }

   public void setFileCount(XsdPositiveIntegerType fileCount) {
      this.generatedSetterHelperImpl(fileCount, FILECOUNT$8, 0, (short)1);
   }

   public XsdPositiveIntegerType addNewFileCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdPositiveIntegerType target = null;
         target = (XsdPositiveIntegerType)this.get_store().add_element_user(FILECOUNT$8);
         return target;
      }
   }

   public void unsetFileCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FILECOUNT$8, 0);
      }
   }

   public XsdPositiveIntegerType getFileSizeLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdPositiveIntegerType target = null;
         target = (XsdPositiveIntegerType)this.get_store().find_element_user(FILESIZELIMIT$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetFileSizeLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FILESIZELIMIT$10) != 0;
      }
   }

   public void setFileSizeLimit(XsdPositiveIntegerType fileSizeLimit) {
      this.generatedSetterHelperImpl(fileSizeLimit, FILESIZELIMIT$10, 0, (short)1);
   }

   public XsdPositiveIntegerType addNewFileSizeLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdPositiveIntegerType target = null;
         target = (XsdPositiveIntegerType)this.get_store().add_element_user(FILESIZELIMIT$10);
         return target;
      }
   }

   public void unsetFileSizeLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FILESIZELIMIT$10, 0);
      }
   }

   public TrueFalseType getRotateLogOnStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ROTATELOGONSTARTUP$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRotateLogOnStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ROTATELOGONSTARTUP$12) != 0;
      }
   }

   public void setRotateLogOnStartup(TrueFalseType rotateLogOnStartup) {
      this.generatedSetterHelperImpl(rotateLogOnStartup, ROTATELOGONSTARTUP$12, 0, (short)1);
   }

   public TrueFalseType addNewRotateLogOnStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ROTATELOGONSTARTUP$12);
         return target;
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
         String target = null;
         target = (String)this.get_store().find_element_user(LOGFILEROTATIONDIR$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLogFileRotationDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOGFILEROTATIONDIR$14) != 0;
      }
   }

   public void setLogFileRotationDir(String logFileRotationDir) {
      this.generatedSetterHelperImpl(logFileRotationDir, LOGFILEROTATIONDIR$14, 0, (short)1);
   }

   public String addNewLogFileRotationDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(LOGFILEROTATIONDIR$14);
         return target;
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
         String target = null;
         target = (String)this.get_store().find_element_user(ROTATIONTIME$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRotationTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ROTATIONTIME$16) != 0;
      }
   }

   public void setRotationTime(String rotationTime) {
      this.generatedSetterHelperImpl(rotationTime, ROTATIONTIME$16, 0, (short)1);
   }

   public String addNewRotationTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(ROTATIONTIME$16);
         return target;
      }
   }

   public void unsetRotationTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ROTATIONTIME$16, 0);
      }
   }

   public XsdPositiveIntegerType getFileTimeSpan() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdPositiveIntegerType target = null;
         target = (XsdPositiveIntegerType)this.get_store().find_element_user(FILETIMESPAN$18, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetFileTimeSpan() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FILETIMESPAN$18) != 0;
      }
   }

   public void setFileTimeSpan(XsdPositiveIntegerType fileTimeSpan) {
      this.generatedSetterHelperImpl(fileTimeSpan, FILETIMESPAN$18, 0, (short)1);
   }

   public XsdPositiveIntegerType addNewFileTimeSpan() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdPositiveIntegerType target = null;
         target = (XsdPositiveIntegerType)this.get_store().add_element_user(FILETIMESPAN$18);
         return target;
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
         String target = null;
         target = (String)this.get_store().find_element_user(DATEFORMATPATTERN$20, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDateFormatPattern() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DATEFORMATPATTERN$20) != 0;
      }
   }

   public void setDateFormatPattern(String dateFormatPattern) {
      this.generatedSetterHelperImpl(dateFormatPattern, DATEFORMATPATTERN$20, 0, (short)1);
   }

   public String addNewDateFormatPattern() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(DATEFORMATPATTERN$20);
         return target;
      }
   }

   public void unsetDateFormatPattern() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DATEFORMATPATTERN$20, 0);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$22);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$22);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$22) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$22);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$22);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$22);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$22);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$22);
      }
   }
}
