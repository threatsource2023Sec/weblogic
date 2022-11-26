package weblogic.connector.configuration.meta;

class ConfigPropertyModel {
   private final String name;
   private String type;
   private String[] discriptions;
   private String defaultValue;
   private boolean supportsDynamicUpdates = false;
   private boolean isConfidential = false;
   private boolean igore = false;

   public boolean isIgore() {
      return this.igore;
   }

   public void setIgore(boolean igore) {
      this.igore = igore;
   }

   public boolean isSupportsDynamicUpdates() {
      return this.supportsDynamicUpdates;
   }

   public void setSupportsDynamicUpdates(boolean supportsDynamicUpdates) {
      this.supportsDynamicUpdates = supportsDynamicUpdates;
   }

   public boolean isConfidential() {
      return this.isConfidential;
   }

   public void setConfidential(boolean isConfidential) {
      this.isConfidential = isConfidential;
   }

   public ConfigPropertyModel(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String[] getDiscriptions() {
      return this.discriptions;
   }

   public void setDiscriptions(String[] discription) {
      this.discriptions = discription;
   }

   public String getDefaultValue() {
      return this.defaultValue;
   }

   public void setDefaultValue(String defaultValue) {
      this.defaultValue = defaultValue;
   }
}
