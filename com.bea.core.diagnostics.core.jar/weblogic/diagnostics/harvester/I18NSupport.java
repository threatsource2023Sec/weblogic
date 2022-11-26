package weblogic.diagnostics.harvester;

import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;

public final class I18NSupport {
   private static DiagnosticsTextTextFormatter i18NFormatter = DiagnosticsTextTextFormatter.getInstance();

   public static DiagnosticsTextTextFormatter formatter() {
      return i18NFormatter;
   }
}
