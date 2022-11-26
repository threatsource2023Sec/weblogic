package com.bea.adaptive.harvester.jmx;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class HarvesterAdapterTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public HarvesterAdapterTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "com.bea.adaptive.harvester.jmx.HarvesterAdapterTextTextLocalizer", HarvesterAdapterTextTextFormatter.class.getClassLoader());
   }

   public HarvesterAdapterTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "com.bea.adaptive.harvester.jmx.HarvesterAdapterTextTextLocalizer", HarvesterAdapterTextTextFormatter.class.getClassLoader());
   }

   public static HarvesterAdapterTextTextFormatter getInstance() {
      return new HarvesterAdapterTextTextFormatter();
   }

   public static HarvesterAdapterTextTextFormatter getInstance(Locale l) {
      return new HarvesterAdapterTextTextFormatter(l);
   }

   public String getMetricNamespaceRejected(String arg0, String arg1) {
      String id = "MetricNamespaceRejected";
      String subsystem = "HarvesterAdaptor";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
