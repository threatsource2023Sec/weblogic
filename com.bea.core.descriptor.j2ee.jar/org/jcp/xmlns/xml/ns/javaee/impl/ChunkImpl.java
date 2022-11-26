package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.CheckpointAlgorithm;
import org.jcp.xmlns.xml.ns.javaee.Chunk;
import org.jcp.xmlns.xml.ns.javaee.ExceptionClassFilter;
import org.jcp.xmlns.xml.ns.javaee.ItemProcessor;
import org.jcp.xmlns.xml.ns.javaee.ItemReader;
import org.jcp.xmlns.xml.ns.javaee.ItemWriter;

public class ChunkImpl extends XmlComplexContentImpl implements Chunk {
   private static final long serialVersionUID = 1L;
   private static final QName READER$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "reader");
   private static final QName PROCESSOR$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "processor");
   private static final QName WRITER$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "writer");
   private static final QName CHECKPOINTALGORITHM$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "checkpoint-algorithm");
   private static final QName SKIPPABLEEXCEPTIONCLASSES$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "skippable-exception-classes");
   private static final QName RETRYABLEEXCEPTIONCLASSES$10 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "retryable-exception-classes");
   private static final QName NOROLLBACKEXCEPTIONCLASSES$12 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "no-rollback-exception-classes");
   private static final QName CHECKPOINTPOLICY$14 = new QName("", "checkpoint-policy");
   private static final QName ITEMCOUNT$16 = new QName("", "item-count");
   private static final QName TIMELIMIT$18 = new QName("", "time-limit");
   private static final QName SKIPLIMIT$20 = new QName("", "skip-limit");
   private static final QName RETRYLIMIT$22 = new QName("", "retry-limit");

   public ChunkImpl(SchemaType sType) {
      super(sType);
   }

   public ItemReader getReader() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ItemReader target = null;
         target = (ItemReader)this.get_store().find_element_user(READER$0, 0);
         return target == null ? null : target;
      }
   }

   public void setReader(ItemReader reader) {
      this.generatedSetterHelperImpl(reader, READER$0, 0, (short)1);
   }

   public ItemReader addNewReader() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ItemReader target = null;
         target = (ItemReader)this.get_store().add_element_user(READER$0);
         return target;
      }
   }

   public ItemProcessor getProcessor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ItemProcessor target = null;
         target = (ItemProcessor)this.get_store().find_element_user(PROCESSOR$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetProcessor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROCESSOR$2) != 0;
      }
   }

   public void setProcessor(ItemProcessor processor) {
      this.generatedSetterHelperImpl(processor, PROCESSOR$2, 0, (short)1);
   }

   public ItemProcessor addNewProcessor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ItemProcessor target = null;
         target = (ItemProcessor)this.get_store().add_element_user(PROCESSOR$2);
         return target;
      }
   }

   public void unsetProcessor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROCESSOR$2, 0);
      }
   }

   public ItemWriter getWriter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ItemWriter target = null;
         target = (ItemWriter)this.get_store().find_element_user(WRITER$4, 0);
         return target == null ? null : target;
      }
   }

   public void setWriter(ItemWriter writer) {
      this.generatedSetterHelperImpl(writer, WRITER$4, 0, (short)1);
   }

   public ItemWriter addNewWriter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ItemWriter target = null;
         target = (ItemWriter)this.get_store().add_element_user(WRITER$4);
         return target;
      }
   }

   public CheckpointAlgorithm getCheckpointAlgorithm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CheckpointAlgorithm target = null;
         target = (CheckpointAlgorithm)this.get_store().find_element_user(CHECKPOINTALGORITHM$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCheckpointAlgorithm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CHECKPOINTALGORITHM$6) != 0;
      }
   }

   public void setCheckpointAlgorithm(CheckpointAlgorithm checkpointAlgorithm) {
      this.generatedSetterHelperImpl(checkpointAlgorithm, CHECKPOINTALGORITHM$6, 0, (short)1);
   }

   public CheckpointAlgorithm addNewCheckpointAlgorithm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CheckpointAlgorithm target = null;
         target = (CheckpointAlgorithm)this.get_store().add_element_user(CHECKPOINTALGORITHM$6);
         return target;
      }
   }

   public void unsetCheckpointAlgorithm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CHECKPOINTALGORITHM$6, 0);
      }
   }

   public ExceptionClassFilter getSkippableExceptionClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExceptionClassFilter target = null;
         target = (ExceptionClassFilter)this.get_store().find_element_user(SKIPPABLEEXCEPTIONCLASSES$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSkippableExceptionClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SKIPPABLEEXCEPTIONCLASSES$8) != 0;
      }
   }

   public void setSkippableExceptionClasses(ExceptionClassFilter skippableExceptionClasses) {
      this.generatedSetterHelperImpl(skippableExceptionClasses, SKIPPABLEEXCEPTIONCLASSES$8, 0, (short)1);
   }

   public ExceptionClassFilter addNewSkippableExceptionClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExceptionClassFilter target = null;
         target = (ExceptionClassFilter)this.get_store().add_element_user(SKIPPABLEEXCEPTIONCLASSES$8);
         return target;
      }
   }

   public void unsetSkippableExceptionClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SKIPPABLEEXCEPTIONCLASSES$8, 0);
      }
   }

   public ExceptionClassFilter getRetryableExceptionClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExceptionClassFilter target = null;
         target = (ExceptionClassFilter)this.get_store().find_element_user(RETRYABLEEXCEPTIONCLASSES$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRetryableExceptionClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RETRYABLEEXCEPTIONCLASSES$10) != 0;
      }
   }

   public void setRetryableExceptionClasses(ExceptionClassFilter retryableExceptionClasses) {
      this.generatedSetterHelperImpl(retryableExceptionClasses, RETRYABLEEXCEPTIONCLASSES$10, 0, (short)1);
   }

   public ExceptionClassFilter addNewRetryableExceptionClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExceptionClassFilter target = null;
         target = (ExceptionClassFilter)this.get_store().add_element_user(RETRYABLEEXCEPTIONCLASSES$10);
         return target;
      }
   }

   public void unsetRetryableExceptionClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RETRYABLEEXCEPTIONCLASSES$10, 0);
      }
   }

   public ExceptionClassFilter getNoRollbackExceptionClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExceptionClassFilter target = null;
         target = (ExceptionClassFilter)this.get_store().find_element_user(NOROLLBACKEXCEPTIONCLASSES$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetNoRollbackExceptionClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NOROLLBACKEXCEPTIONCLASSES$12) != 0;
      }
   }

   public void setNoRollbackExceptionClasses(ExceptionClassFilter noRollbackExceptionClasses) {
      this.generatedSetterHelperImpl(noRollbackExceptionClasses, NOROLLBACKEXCEPTIONCLASSES$12, 0, (short)1);
   }

   public ExceptionClassFilter addNewNoRollbackExceptionClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExceptionClassFilter target = null;
         target = (ExceptionClassFilter)this.get_store().add_element_user(NOROLLBACKEXCEPTIONCLASSES$12);
         return target;
      }
   }

   public void unsetNoRollbackExceptionClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NOROLLBACKEXCEPTIONCLASSES$12, 0);
      }
   }

   public String getCheckpointPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(CHECKPOINTPOLICY$14);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCheckpointPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(CHECKPOINTPOLICY$14);
         return target;
      }
   }

   public boolean isSetCheckpointPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(CHECKPOINTPOLICY$14) != null;
      }
   }

   public void setCheckpointPolicy(String checkpointPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(CHECKPOINTPOLICY$14);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(CHECKPOINTPOLICY$14);
         }

         target.setStringValue(checkpointPolicy);
      }
   }

   public void xsetCheckpointPolicy(XmlString checkpointPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(CHECKPOINTPOLICY$14);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(CHECKPOINTPOLICY$14);
         }

         target.set(checkpointPolicy);
      }
   }

   public void unsetCheckpointPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(CHECKPOINTPOLICY$14);
      }
   }

   public String getItemCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ITEMCOUNT$16);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetItemCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(ITEMCOUNT$16);
         return target;
      }
   }

   public boolean isSetItemCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ITEMCOUNT$16) != null;
      }
   }

   public void setItemCount(String itemCount) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ITEMCOUNT$16);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ITEMCOUNT$16);
         }

         target.setStringValue(itemCount);
      }
   }

   public void xsetItemCount(XmlString itemCount) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(ITEMCOUNT$16);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(ITEMCOUNT$16);
         }

         target.set(itemCount);
      }
   }

   public void unsetItemCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ITEMCOUNT$16);
      }
   }

   public String getTimeLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(TIMELIMIT$18);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTimeLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(TIMELIMIT$18);
         return target;
      }
   }

   public boolean isSetTimeLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(TIMELIMIT$18) != null;
      }
   }

   public void setTimeLimit(String timeLimit) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(TIMELIMIT$18);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(TIMELIMIT$18);
         }

         target.setStringValue(timeLimit);
      }
   }

   public void xsetTimeLimit(XmlString timeLimit) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(TIMELIMIT$18);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(TIMELIMIT$18);
         }

         target.set(timeLimit);
      }
   }

   public void unsetTimeLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(TIMELIMIT$18);
      }
   }

   public String getSkipLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(SKIPLIMIT$20);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSkipLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(SKIPLIMIT$20);
         return target;
      }
   }

   public boolean isSetSkipLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(SKIPLIMIT$20) != null;
      }
   }

   public void setSkipLimit(String skipLimit) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(SKIPLIMIT$20);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(SKIPLIMIT$20);
         }

         target.setStringValue(skipLimit);
      }
   }

   public void xsetSkipLimit(XmlString skipLimit) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(SKIPLIMIT$20);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(SKIPLIMIT$20);
         }

         target.set(skipLimit);
      }
   }

   public void unsetSkipLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(SKIPLIMIT$20);
      }
   }

   public String getRetryLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(RETRYLIMIT$22);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetRetryLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(RETRYLIMIT$22);
         return target;
      }
   }

   public boolean isSetRetryLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(RETRYLIMIT$22) != null;
      }
   }

   public void setRetryLimit(String retryLimit) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(RETRYLIMIT$22);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(RETRYLIMIT$22);
         }

         target.setStringValue(retryLimit);
      }
   }

   public void xsetRetryLimit(XmlString retryLimit) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(RETRYLIMIT$22);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(RETRYLIMIT$22);
         }

         target.set(retryLimit);
      }
   }

   public void unsetRetryLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(RETRYLIMIT$22);
      }
   }
}
