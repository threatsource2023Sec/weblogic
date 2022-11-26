package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.Analyzer;
import org.jcp.xmlns.xml.ns.javaee.Collector;
import org.jcp.xmlns.xml.ns.javaee.Partition;
import org.jcp.xmlns.xml.ns.javaee.PartitionMapper;
import org.jcp.xmlns.xml.ns.javaee.PartitionPlan;
import org.jcp.xmlns.xml.ns.javaee.PartitionReducer;

public class PartitionImpl extends XmlComplexContentImpl implements Partition {
   private static final long serialVersionUID = 1L;
   private static final QName MAPPER$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "mapper");
   private static final QName PLAN$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "plan");
   private static final QName COLLECTOR$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "collector");
   private static final QName ANALYZER$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "analyzer");
   private static final QName REDUCER$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "reducer");

   public PartitionImpl(SchemaType sType) {
      super(sType);
   }

   public PartitionMapper getMapper() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PartitionMapper target = null;
         target = (PartitionMapper)this.get_store().find_element_user(MAPPER$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMapper() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAPPER$0) != 0;
      }
   }

   public void setMapper(PartitionMapper mapper) {
      this.generatedSetterHelperImpl(mapper, MAPPER$0, 0, (short)1);
   }

   public PartitionMapper addNewMapper() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PartitionMapper target = null;
         target = (PartitionMapper)this.get_store().add_element_user(MAPPER$0);
         return target;
      }
   }

   public void unsetMapper() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAPPER$0, 0);
      }
   }

   public PartitionPlan getPlan() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PartitionPlan target = null;
         target = (PartitionPlan)this.get_store().find_element_user(PLAN$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPlan() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PLAN$2) != 0;
      }
   }

   public void setPlan(PartitionPlan plan) {
      this.generatedSetterHelperImpl(plan, PLAN$2, 0, (short)1);
   }

   public PartitionPlan addNewPlan() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PartitionPlan target = null;
         target = (PartitionPlan)this.get_store().add_element_user(PLAN$2);
         return target;
      }
   }

   public void unsetPlan() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PLAN$2, 0);
      }
   }

   public Collector getCollector() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Collector target = null;
         target = (Collector)this.get_store().find_element_user(COLLECTOR$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCollector() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COLLECTOR$4) != 0;
      }
   }

   public void setCollector(Collector collector) {
      this.generatedSetterHelperImpl(collector, COLLECTOR$4, 0, (short)1);
   }

   public Collector addNewCollector() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Collector target = null;
         target = (Collector)this.get_store().add_element_user(COLLECTOR$4);
         return target;
      }
   }

   public void unsetCollector() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COLLECTOR$4, 0);
      }
   }

   public Analyzer getAnalyzer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Analyzer target = null;
         target = (Analyzer)this.get_store().find_element_user(ANALYZER$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAnalyzer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ANALYZER$6) != 0;
      }
   }

   public void setAnalyzer(Analyzer analyzer) {
      this.generatedSetterHelperImpl(analyzer, ANALYZER$6, 0, (short)1);
   }

   public Analyzer addNewAnalyzer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Analyzer target = null;
         target = (Analyzer)this.get_store().add_element_user(ANALYZER$6);
         return target;
      }
   }

   public void unsetAnalyzer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ANALYZER$6, 0);
      }
   }

   public PartitionReducer getReducer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PartitionReducer target = null;
         target = (PartitionReducer)this.get_store().find_element_user(REDUCER$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetReducer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REDUCER$8) != 0;
      }
   }

   public void setReducer(PartitionReducer reducer) {
      this.generatedSetterHelperImpl(reducer, REDUCER$8, 0, (short)1);
   }

   public PartitionReducer addNewReducer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PartitionReducer target = null;
         target = (PartitionReducer)this.get_store().add_element_user(REDUCER$8);
         return target;
      }
   }

   public void unsetReducer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REDUCER$8, 0);
      }
   }
}
