package weblogic.management.configuration;

public interface WLDFDataRetirementByAgeMBean extends WLDFDataRetirementMBean {
   int DEFAULT_RETIREMENT_AGE = 72;

   int getRetirementAge();

   void setRetirementAge(int var1);
}
