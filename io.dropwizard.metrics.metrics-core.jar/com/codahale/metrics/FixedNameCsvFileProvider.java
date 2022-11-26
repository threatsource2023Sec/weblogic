package com.codahale.metrics;

import java.io.File;

public class FixedNameCsvFileProvider implements CsvFileProvider {
   public File getFile(File directory, String metricName) {
      return new File(directory, this.sanitize(metricName) + ".csv");
   }

   protected String sanitize(String metricName) {
      return metricName.replaceFirst("^/", "").replaceAll("/", ".");
   }
}
