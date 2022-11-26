package com.bea.staxb.buildtime.internal.mbean;

import com.bea.staxb.buildtime.Java2SchemaTask;

public class MBeanJava2SchemaTask extends Java2SchemaTask {
   public MBeanJava2SchemaTask() {
      super(new MBeanJava2Schema());
   }
}
