package com.bea.staxb.buildtime;

public class Namespace {
   private String uri;
   private String prefix;
   private String outputFile;
   private String schemaLocation;

   public void setUri(String tns) {
      this.uri = tns;
   }

   public void setPrefix(String pfx) {
      this.prefix = pfx;
   }

   public void setOutputFile(String file) {
      this.outputFile = file;
   }

   public void setSchemaLocation(String schemaLocation) {
      this.schemaLocation = schemaLocation;
   }

   public String getUri() {
      return this.uri;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public String getOutputFile() {
      return this.outputFile;
   }

   public String getSchemaLocation() {
      return this.schemaLocation;
   }
}
