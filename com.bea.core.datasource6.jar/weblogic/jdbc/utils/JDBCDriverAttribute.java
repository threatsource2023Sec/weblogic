package weblogic.jdbc.utils;

import java.io.Serializable;

public class JDBCDriverAttribute implements Cloneable, Serializable {
   private String name;
   private String defaultValue;
   private String description;
   private String value;
   private String displayName;
   private String propertyName;
   private boolean inURL;
   private boolean isRequired;
   private MetaJDBCDriverInfo metaInfo;

   public JDBCDriverAttribute(MetaJDBCDriverInfo metaInfo) {
      this.metaInfo = metaInfo;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public void setDisplayName(String dispName) {
      this.displayName = dispName;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public void setPropertyName(String propName) {
      this.propertyName = propName;
   }

   public String getPropertyName() {
      return this.propertyName;
   }

   public void setDefaultValue(String defVal) {
      this.defaultValue = defVal;
   }

   public String getDefaultValue() {
      return this.defaultValue;
   }

   public void setInURL(String urlArg) {
      this.setInURL(Boolean.valueOf(urlArg));
   }

   public void setInURL(boolean urlArg) {
      this.inURL = urlArg;
   }

   public boolean isInURL() {
      return this.inURL;
   }

   public void setIsRequired(String reqArg) {
      this.setIsRequired(Boolean.valueOf(reqArg));
   }

   public void setIsRequired(boolean reqArg) {
      this.isRequired = reqArg;
   }

   public boolean isRequired() {
      return this.isRequired;
   }

   public void setDesription(String description) {
      this.description = description;
   }

   public String getDescription() {
      return this.description;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getValue() {
      return this.value;
   }

   public String toString() {
      StringBuffer buff = new StringBuffer();
      buff.append("For : " + this.metaInfo.toString() + " \n");
      buff.append("\nProperty Name    : " + this.getName());
      buff.append("\nProperty Value   : " + this.getValue());
      buff.append("\nDefault Value    : " + (this.getDefaultValue() == null ? "null" : this.getDefaultValue()));
      buff.append("\nRequired?        : " + this.isRequired());
      buff.append("\nGoes in URL      : " + Boolean.valueOf(this.isInURL()).toString());
      buff.append("\nDescription      : " + (this.getDescription() == null ? "null" : this.getDescription()));
      return buff.toString();
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }
}
