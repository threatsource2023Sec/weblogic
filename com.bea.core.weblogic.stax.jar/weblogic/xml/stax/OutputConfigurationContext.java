package weblogic.xml.stax;

public class OutputConfigurationContext extends ConfigurationContextBase {
   private static String EVENT_FILTER = "RI_EVENT_FILTER";
   private static String STREAM_FILTER = "RI_STREAM_FILTER";

   public OutputConfigurationContext() {
      supportedFeatures.add("javax.xml.stream.isRepairingNamespaces");
      supportedFeatures.add("javax.xml.stream.isEscapingCharacters");
      supportedFeatures.add("weblogic.xml.stream.isEscapingCR");
      supportedFeatures.add("weblogic.xml.stream.isEscapingCRLFTAB");
      supportedFeatures.add("weblogic.xml.stream.isWritingDTD");
      supportedFeatures.add("javax.xml.stream.reporter");
      supportedFeatures.add("javax.xml.stream.resolver");
      supportedFeatures.add("javax.xml.stream.allocator");
      this.features.put("javax.xml.stream.isRepairingNamespaces", Boolean.FALSE);
      this.features.put("javax.xml.stream.isEscapingCharacters", Boolean.TRUE);
      this.features.put("weblogic.xml.stream.isEscapingCR", Boolean.FALSE);
      this.features.put("weblogic.xml.stream.isEscapingCRLFTAB", Boolean.FALSE);
      this.features.put("weblogic.xml.stream.isWritingDTD", Boolean.TRUE);
   }
}
