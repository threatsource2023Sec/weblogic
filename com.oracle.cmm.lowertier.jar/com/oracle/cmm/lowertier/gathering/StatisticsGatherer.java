package com.oracle.cmm.lowertier.gathering;

public interface StatisticsGatherer {
   void initialize(String var1);

   String ELBeanName();

   void gatherStatistics();
}
