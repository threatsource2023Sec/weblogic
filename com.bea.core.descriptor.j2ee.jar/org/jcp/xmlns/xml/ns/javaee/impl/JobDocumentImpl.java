package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.Job;
import org.jcp.xmlns.xml.ns.javaee.JobDocument;

public class JobDocumentImpl extends XmlComplexContentImpl implements JobDocument {
   private static final long serialVersionUID = 1L;
   private static final QName JOB$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "job");

   public JobDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public Job getJob() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Job target = null;
         target = (Job)this.get_store().find_element_user(JOB$0, 0);
         return target == null ? null : target;
      }
   }

   public void setJob(Job job) {
      this.generatedSetterHelperImpl(job, JOB$0, 0, (short)1);
   }

   public Job addNewJob() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Job target = null;
         target = (Job)this.get_store().add_element_user(JOB$0);
         return target;
      }
   }
}
