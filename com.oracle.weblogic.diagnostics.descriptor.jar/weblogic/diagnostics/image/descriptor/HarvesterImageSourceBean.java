package weblogic.diagnostics.image.descriptor;

public interface HarvesterImageSourceBean {
   HarvesterModuleBean[] getHarvesterModules();

   HarvesterModuleBean createHarvesterModule();

   HarvesterStatisticsBean getStatistics();

   HarvesterStatisticsBean createStatistics();
}
