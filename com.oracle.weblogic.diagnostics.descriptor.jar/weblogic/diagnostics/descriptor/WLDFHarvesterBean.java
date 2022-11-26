package weblogic.diagnostics.descriptor;

public interface WLDFHarvesterBean extends WLDFBean {
   boolean isEnabled();

   void setEnabled(boolean var1);

   long getSamplePeriod();

   void setSamplePeriod(long var1) throws IllegalArgumentException;

   WLDFHarvestedTypeBean[] getHarvestedTypes();

   WLDFHarvestedTypeBean createHarvestedType(String var1);

   void destroyHarvestedType(WLDFHarvestedTypeBean var1);

   WLDFHarvestedTypeBean lookupHarvestedType(String var1);
}
