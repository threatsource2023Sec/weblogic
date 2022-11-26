package weblogic.deploy.api.internal;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class PlanGenTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public PlanGenTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.deploy.api.internal.PlanGenTextLocalizer", PlanGenTextFormatter.class.getClassLoader());
   }

   public PlanGenTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.deploy.api.internal.PlanGenTextLocalizer", PlanGenTextFormatter.class.getClassLoader());
   }

   public static PlanGenTextFormatter getInstance() {
      return new PlanGenTextFormatter();
   }

   public static PlanGenTextFormatter getInstance(Locale l) {
      return new PlanGenTextFormatter(l);
   }

   public String nullArg(String arg0) {
      String id = "260300";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badApp(String arg0) {
      String id = "260301";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String debug() {
      String id = "260302";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String plan() {
      String id = "260303";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String type() {
      String id = "260304";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String application() {
      String id = "260305";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noPlan() {
      String id = "260306";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noApp() {
      String id = "260307";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noType(String arg0) {
      String id = "260308";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badType(String arg0) {
      String id = "260309";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String export() {
      String id = "260310";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badExport(String arg0) {
      String id = "260311";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String processing(String arg0) {
      String id = "260312";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String processingModule(String arg0) {
      String id = "260313";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String saving(String arg0) {
      String id = "260314";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getKeyInput(String arg0, String arg1, String arg2, String arg3, String arg4) {
      String id = "260315";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoKeyInput(String arg0, String arg1, String arg2) {
      String id = "260316";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exporting() {
      String id = "260317";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String variables() {
      String id = "260318";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badVariables(String arg0) {
      String id = "260319";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String skipDTD() {
      String id = "260320";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String dependencies() {
      String id = "260321";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String declarations() {
      String id = "260322";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String configurables() {
      String id = "260323";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String dynamics() {
      String id = "260324";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String none() {
      String id = "260325";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String all() {
      String id = "260326";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String useplan() {
      String id = "260327";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usage() {
      String id = "260328";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String genning(String arg0) {
      String id = "260329";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String genningWithPlan(String arg0, String arg1) {
      String id = "260330";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noexit() {
      String id = "260331";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String libraries() {
      String id = "260332";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String libraryDir() {
      String id = "260333";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exportOptions(String arg0) {
      String id = "260334";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String merging() {
      String id = "260335";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String libName(String arg0) {
      String id = "260336";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String libSpec(String arg0) {
      String id = "260337";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String libImpl(String arg0) {
      String id = "260338";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String libLocation(String arg0) {
      String id = "260339";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String any() {
      String id = "260340";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String standard() {
      String id = "260341";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String module() {
      String id = "260342";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String notEar(String arg0) {
      String id = "260343";
      String subsystem = "J2EE Deployment SPI";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
