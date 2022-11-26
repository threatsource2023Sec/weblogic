package weblogic.tools.ui;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class MarathonTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public MarathonTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.tools.ui.MarathonTextLocalizer", MarathonTextFormatter.class.getClassLoader());
   }

   public MarathonTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.tools.ui.MarathonTextLocalizer", MarathonTextFormatter.class.getClassLoader());
   }

   public static MarathonTextFormatter getInstance() {
      return new MarathonTextFormatter();
   }

   public static MarathonTextFormatter getInstance(Locale l) {
      return new MarathonTextFormatter(l);
   }

   public String getOpeningModule(String arg0) {
      String id = "2530000";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExecDDInit() {
      String id = "2530001";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatedDescriptor(String arg0, String arg1) {
      String id = "2530002";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getModuleSaveFailed(String arg0) {
      String id = "2530003";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getModuleValidationFailed(String arg0) {
      String id = "2530004";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getModuleValidationSuccess(String arg0) {
      String id = "2530005";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getModuleValidating(String arg0) {
      String id = "2530006";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBadPath(String arg0) {
      String id = "2530007";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBadPathTitle() {
      String id = "2530008";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSelect() {
      String id = "2530009";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotDetermineType(String arg0) {
      String id = "2530010";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorOpeningTitle(String arg0) {
      String id = "2530011";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRunDDInit() {
      String id = "2530012";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCantFindDD() {
      String id = "2530013";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCantLoadDD() {
      String id = "2530014";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnexpectedError() {
      String id = "2530015";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoJ2EEComponentsFound(String arg0) {
      String id = "2530016";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoComponentFilesFound(String arg0) {
      String id = "2530017";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSearchingForComponents() {
      String id = "2530018";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFoundComponents(String arg0) {
      String id = "2530019";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWritingDesc() {
      String id = "2530020";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProcessingRelations() {
      String id = "2530021";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProcessingEntities() {
      String id = "2530022";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProcessingSessions() {
      String id = "2530023";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProcessingMDBs() {
      String id = "2530024";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDescLoadSuccess(String arg0) {
      String id = "2530025";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDescLoadFailure(String arg0) {
      String id = "2530026";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRenderingUIForModule(String arg0) {
      String id = "2530027";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOpen() {
      String id = "2530028";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDontAskAgain() {
      String id = "2530029";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSaveChangesValidation() {
      String id = "2530030";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getYes() {
      String id = "2530031";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNo() {
      String id = "2530032";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getQuestion() {
      String id = "2530033";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBrowse() {
      String id = "2530034";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBCCompiler() {
      String id = "2530035";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getArchive() {
      String id = "2530036";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSaveAs() {
      String id = "2530037";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSave() {
      String id = "2530038";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExistsOverwrite(String arg0) {
      String id = "2530040";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorSavingFile(String arg0) {
      String id = "2530041";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSavingFile(String arg0) {
      String id = "2530042";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInspectInputModule(String arg0) {
      String id = "2530043";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRecent() {
      String id = "2530044";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRecentFileMissing(String arg0) {
      String id = "2530045";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getError() {
      String id = "2530046";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOpenModule() {
      String id = "2530047";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPath() {
      String id = "2530048";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSelectFile() {
      String id = "2530049";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getValidate() {
      String id = "2530050";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOpenArchive() {
      String id = "2530051";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOpenDirectory() {
      String id = "2530052";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getClose() {
      String id = "2530053";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeploy() {
      String id = "2530054";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrors() {
      String id = "2530055";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMessages() {
      String id = "2530056";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSaveChanges(String arg0, String arg1) {
      String id = "2530057";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeployment() {
      String id = "2530058";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCantLoadEAR() {
      String id = "2530059";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIsWebappInEar(String arg0) {
      String id = "2530060";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableToDetermineType() {
      String id = "2530061";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSearchForEARModules() {
      String id = "2530062";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableToOpenModuleRoot(String arg0) {
      String id = "2530063";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSearchModuleForComponents(String arg0, String arg1) {
      String id = "2530064";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFoundModule(String arg0) {
      String id = "2530065";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJB() {
      String id = "2530066";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWeb() {
      String id = "2530067";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getApplication() {
      String id = "2530068";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFoundEARModule(int arg0) {
      String id = "2530069";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getModule() {
      String id = "2530070";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDiscoveredModule(String arg0) {
      String id = "2530071";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSearchingForClasses() {
      String id = "2530072";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getXMLFrameTitle() {
      String id = "2530073";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAskBEA() {
      String id = "2530074";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeployFailed(String arg0) {
      String id = "2530075";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeployFailedTitle() {
      String id = "2530076";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBeanIsType(String arg0, String arg1) {
      String id = "2530077";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOpening() {
      String id = "2530078";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getValidating() {
      String id = "2530079";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInitializingDesc() {
      String id = "2530080";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSavingAs(String arg0) {
      String id = "2530081";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSaving() {
      String id = "2530082";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectingToServer() {
      String id = "2530083";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectingToServerTitle() {
      String id = "2530084";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCopy() {
      String id = "2530085";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCut() {
      String id = "2530086";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPaste() {
      String id = "2530087";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEdit() {
      String id = "2530088";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getView() {
      String id = "2530089";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExit() {
      String id = "2530090";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFile() {
      String id = "2530091";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidEntry() {
      String id = "2530092";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFieldNotEmpty(String arg0) {
      String id = "2530093";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAllRequiredFieldsNotEmpty() {
      String id = "2530094";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRequiredFieldEmpty(String arg0) {
      String id = "2530095";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getModuleCloseFailed(String arg0) {
      String id = "2530096";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOptions() {
      String id = "2530097";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHelp() {
      String id = "2530098";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAbout(String arg0) {
      String id = "2530099";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getXMLSource() {
      String id = "2530100";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBrowseServer() {
      String id = "2530101";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectToServer() {
      String id = "2530102";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTools() {
      String id = "2530103";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectToServerDialogTitle() {
      String id = "2530104";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getApplicationPath() {
      String id = "2530105";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getApplicationName() {
      String id = "2530106";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStatus() {
      String id = "2530107";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotDeployed() {
      String id = "2530108";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInspectingServerApps() {
      String id = "2530109";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeployed() {
      String id = "2530110";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPresentNotDeployed() {
      String id = "2530111";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAwaitingNotification() {
      String id = "2530112";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeployCancelled() {
      String id = "2530113";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getModuleDeployedSuccess() {
      String id = "2530114";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeployFailed() {
      String id = "2530115";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUndeploying(String arg0) {
      String id = "2530116";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPreviousAppUndeployed() {
      String id = "2530117";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeploying() {
      String id = "2530118";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUploading() {
      String id = "2530119";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getManageDeployments() {
      String id = "2530120";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getApplications() {
      String id = "2530121";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnknownSuffix() {
      String id = "2530122";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBSuffix() {
      String id = "2530123";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSuffix() {
      String id = "2530124";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUndeployTitle() {
      String id = "2530125";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeployTitle() {
      String id = "2530126";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFromAppsDir() {
      String id = "2530127";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeployedSuffix() {
      String id = "2530128";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotDeployedSuffix() {
      String id = "2530129";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectedToServer(String arg0, String arg1, String arg2) {
      String id = "2530130";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDisconnectedFromServer() {
      String id = "2530131";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectingToServer(String arg0) {
      String id = "2530132";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getModifiedSuffix() {
      String id = "2530133";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRefreshingModule() {
      String id = "2530134";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRefreshModule() {
      String id = "2530135";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFileIsReadOnly(String arg0) {
      String id = "2530136";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExportDDEllipsis() {
      String id = "2530137";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExportDDToDir() {
      String id = "2530138";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotSaveDD() {
      String id = "2530139";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSavingDDs(String arg0, String arg1) {
      String id = "2530140";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWrote(String arg0) {
      String id = "2530141";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExceptionOccurred() {
      String id = "2530142";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAutoRefresh() {
      String id = "2530143";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRevert() {
      String id = "2530144";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getClasspath() {
      String id = "2530145";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidManifestEntry(String arg0) {
      String id = "2530146";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBName() {
      String id = "2500001";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJNDIName() {
      String id = "2500002";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTxTimeout() {
      String id = "2500003";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getApply() {
      String id = "2500004";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCancel() {
      String id = "2500005";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHome() {
      String id = "2500006";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRemote() {
      String id = "2500007";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocalObject() {
      String id = "2500008";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocalHome() {
      String id = "2500009";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getClassNameTT() {
      String id = "2500010";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRemoteHomeTT() {
      String id = "2500011";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocalHomeTT() {
      String id = "2500012";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocalObjectTT() {
      String id = "2500013";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRemoteObjectTT() {
      String id = "2500014";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBMP() {
      String id = "2500015";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCMP() {
      String id = "2500016";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get1X() {
      String id = "2500017";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get2X() {
      String id = "2500018";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWeblogic5() {
      String id = "2500019";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWeblogic6() {
      String id = "2500020";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOther() {
      String id = "2500021";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDatasourceName() {
      String id = "2500022";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTableName() {
      String id = "2500023";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPrimaryKeyClass() {
      String id = "2500024";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCMPVersion() {
      String id = "2500025";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPersistenceType() {
      String id = "2500026";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTableNameTT() {
      String id = "2500027";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDatasourceNameTT() {
      String id = "2500028";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPrimaryKeyClassTT() {
      String id = "2500029";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAbstractSchemaName() {
      String id = "2500030";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDelayDatabaseInsert() {
      String id = "2500031";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidationTargetEJB() {
      String id = "2500032";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPassivationStrategy() {
      String id = "2500033";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getReentrant() {
      String id = "2500034";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFindersLoadBeans() {
      String id = "2500035";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnableCallByReference() {
      String id = "2500036";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDelayUpdatesUntil() {
      String id = "2500037";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDBIsShared() {
      String id = "2500038";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreateTables() {
      String id = "2500039";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBSettings() {
      String id = "2500040";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDatabaseSettings() {
      String id = "2500041";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGeneral() {
      String id = "2500042";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getClasses() {
      String id = "2500043";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAdvanced() {
      String id = "2500045";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStateless() {
      String id = "2500046";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStateful() {
      String id = "2500047";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPersistentStoreDirectory() {
      String id = "2500048";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAllowConcurrentCalls() {
      String id = "2500050";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMethodsAreIdempotent() {
      String id = "2500051";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBean() {
      String id = "2500052";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDurable() {
      String id = "2500053";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDestinationJNDIName() {
      String id = "2500054";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDestinationType() {
      String id = "2500055";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTransactionType() {
      String id = "2500056";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMessageSelector() {
      String id = "2500057";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAcknowledgeMode() {
      String id = "2500058";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInitialContextFactory() {
      String id = "2500059";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectionFactoryJNDIName() {
      String id = "2500060";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSPollingInterval() {
      String id = "2500061";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSClientID() {
      String id = "2500062";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProviderURL() {
      String id = "2500063";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTransactionTypeTT() {
      String id = "2500064";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMessageSelectorTT() {
      String id = "2500065";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAcknowledgeModeTT() {
      String id = "2500066";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInitialContextFactoryTT() {
      String id = "2500067";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectionFactoryJNDINameTT() {
      String id = "2500068";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSPollingIntervalTT() {
      String id = "2500069";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSClientIDTT() {
      String id = "2500070";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProviderURLTT() {
      String id = "2500071";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getForeignProvider() {
      String id = "2500072";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getForeignProviderTab() {
      String id = "2500073";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUseForeignProvider() {
      String id = "2500074";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBRefNameTT() {
      String id = "2500075";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBRefType() {
      String id = "2500076";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBRefTypeTT() {
      String id = "2500077";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBRefLink() {
      String id = "2500078";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBRefLinkTT() {
      String id = "2500079";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBRefJNDIName() {
      String id = "2500080";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBRefJNDINameTT() {
      String id = "2500081";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBRefHome() {
      String id = "2500082";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBRefHomeTT() {
      String id = "2500083";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBRefRemote() {
      String id = "2500084";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBRefRemoteTT() {
      String id = "2500085";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBRefName() {
      String id = "2500086";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBRefLocalHome() {
      String id = "2500087";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBRefLocalHomeTT() {
      String id = "2500088";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBRefLocal() {
      String id = "2500089";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBRefLocalTT() {
      String id = "2500090";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTabEnvironment() {
      String id = "2500091";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTabEJBRefs() {
      String id = "2500092";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTabEJBLocalRefs() {
      String id = "2500093";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNodeResources() {
      String id = "2500094";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResRefName() {
      String id = "2500095";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResRefNameTT() {
      String id = "2500096";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResType() {
      String id = "2500097";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResTypeTT() {
      String id = "2500098";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResAuth() {
      String id = "2500099";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResAuthTT() {
      String id = "2500100";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResSharingScope() {
      String id = "2500101";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResSharingScopeTT() {
      String id = "2500102";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResJNDIName() {
      String id = "2500103";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResJNDINameTT() {
      String id = "2500104";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTabResourceRefs() {
      String id = "2500105";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTuning() {
      String id = "2500106";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCache() {
      String id = "2500107";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCluster() {
      String id = "2500108";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPool() {
      String id = "2500109";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMaxBeansInCache() {
      String id = "2500110";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMaxBeansInCacheTT() {
      String id = "2500111";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPassivationStrategyTT() {
      String id = "2500113";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getReadTimeoutSeconds() {
      String id = "2500114";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getReadTimeoutSecondsTT() {
      String id = "2500115";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConcurrencyStrategy() {
      String id = "2500116";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConcurrencyStrategyTT() {
      String id = "2500117";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHomeIsClusterable() {
      String id = "2500118";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHomeIsClusterableTT() {
      String id = "2500119";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHomeCallRouterClassName() {
      String id = "2500120";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHomeCallRouterClassNameTT() {
      String id = "2500121";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getReplicationType() {
      String id = "2500122";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getReplicationTypeTT() {
      String id = "2500123";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMaxBeansInFreePool() {
      String id = "2500124";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMaxBeansInFreePoolTT() {
      String id = "2500125";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInitialBeansInFreePool() {
      String id = "2500126";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInitialBeansInFreePoolTT() {
      String id = "2500127";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCacheTabTT() {
      String id = "2500128";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getClusterTabTT() {
      String id = "2500129";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPoolTabTT() {
      String id = "2500130";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHomeLoadAlgorithm() {
      String id = "2500131";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHomeLoadAlgorithmTT() {
      String id = "2500132";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIdleTimeoutSeconds() {
      String id = "2500133";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIdleTimeoutSecondsTT() {
      String id = "2500134";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMethods() {
      String id = "2500135";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTransactions() {
      String id = "2500136";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPermissions() {
      String id = "2500137";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDefaultTransaction() {
      String id = "2500138";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMethodName() {
      String id = "2500139";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRoles() {
      String id = "2500140";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTransactionAttribute() {
      String id = "2500141";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPersistence() {
      String id = "2500142";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMethod() {
      String id = "2500143";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBQl() {
      String id = "2500144";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getQuerySettings() {
      String id = "2500145";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResultTypeMapping() {
      String id = "2500146";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGroupName() {
      String id = "2500147";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMaxElements() {
      String id = "2500148";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIncludeUpdates() {
      String id = "2500149";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFinders() {
      String id = "2500150";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCMPFields() {
      String id = "2500151";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFieldName() {
      String id = "2500152";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDBMSColumn() {
      String id = "2500153";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDBMSColumnType() {
      String id = "2500154";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRelations() {
      String id = "2500155";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBs() {
      String id = "2500156";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRelationName() {
      String id = "2500157";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBetween() {
      String id = "2500158";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAnd() {
      String id = "2500159";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRelation1() {
      String id = "2500160";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRelation2() {
      String id = "2500161";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRelation3() {
      String id = "2500162";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNameAndEJBsInvolved() {
      String id = "2500163";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRelationNameTT() {
      String id = "2500164";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCMRField() {
      String id = "2500165";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnidirectional() {
      String id = "2500167";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBidirectional() {
      String id = "2500168";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDirection() {
      String id = "2500169";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getForBidirectional() {
      String id = "2500170";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFinish() {
      String id = "2500171";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRelation() {
      String id = "2500172";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCMRFieldFor() {
      String id = "2500173";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditCMRFieldFor() {
      String id = "2500174";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCMRFieldType() {
      String id = "2500175";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPKFieldsFor() {
      String id = "2500176";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getColumnsFor() {
      String id = "2500177";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getColumnsIn() {
      String id = "2500178";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectionPool() {
      String id = "2500179";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJDBCHost() {
      String id = "2500180";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectionPoolName() {
      String id = "2500181";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJDBCDriver() {
      String id = "2500182";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJDBCURL() {
      String id = "2500183";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUserName() {
      String id = "2500184";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJDBCPassword() {
      String id = "2500185";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSelectConnectionPoolTT() {
      String id = "2500186";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnect() {
      String id = "2500187";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTablePattern() {
      String id = "2500188";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGetTables() {
      String id = "2500189";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOK() {
      String id = "2500190";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDisplaySchema() {
      String id = "2500191";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerURL() {
      String id = "2500192";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerName() {
      String id = "2500193";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSystemPassword() {
      String id = "2500194";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectUsing() {
      String id = "2500195";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProtocol() {
      String id = "2500196";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHost() {
      String id = "2500197";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPort() {
      String id = "2500198";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSystemPassword(String arg0) {
      String id = "2500199";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorConnectingToServer() {
      String id = "2500200";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSystemPasswordIsIncorrect() {
      String id = "2500201";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotConnected() {
      String id = "2500202";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnected() {
      String id = "2500203";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectOnStartup() {
      String id = "2500204";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDirectory() {
      String id = "2500205";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getModuleIsModified(String arg0) {
      String id = "2500206";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getModuleWasSuccessfullySaved(String arg0) {
      String id = "2500207";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConfirmExit(String arg0) {
      String id = "2500208";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocalJNDIName() {
      String id = "2500209";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJNDINameColumnTitle() {
      String id = "2500210";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResourceReferenceNameColumnTitle() {
      String id = "2500211";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResourceReferenceTypeColumnTitle() {
      String id = "2500212";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnvEntryName() {
      String id = "2500213";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnvEntryNameTT() {
      String id = "2500214";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnvEntryType() {
      String id = "2500215";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnvEntryTypeTT() {
      String id = "2500216";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnvEntryValue() {
      String id = "2500217";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnvEntryValueTT() {
      String id = "2500218";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSelectTable() {
      String id = "2500219";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIncorrectConnectionPool(String arg0) {
      String id = "2500220";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorCannotFindConnectionPool() {
      String id = "2500221";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIncorrectDataSource(String arg0) {
      String id = "2500222";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorCannotFindDataSource() {
      String id = "2500223";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTablesThatMatch() {
      String id = "2500224";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSystemUserPasswordTT() {
      String id = "2500225";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFindersAndCMPFields() {
      String id = "2500226";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAddRelationMenuTitle() {
      String id = "2500227";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAddRelationMenuTT() {
      String id = "2500228";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getManyManyOnly() {
      String id = "2500229";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRelationsPopupMenuTitle() {
      String id = "2500230";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeleteRelationMenuTitle() {
      String id = "2500231";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeleteRelationMenuTitleTT() {
      String id = "2500232";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditRelationMenuTitle() {
      String id = "2500233";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditRelationMenuTitleTT() {
      String id = "2500234";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPrimaryKeyField() {
      String id = "2500235";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPrimaryKeyFieldTT() {
      String id = "25000236";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSelectThisTable() {
      String id = "25000237";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCacheBetweenTransactions() {
      String id = "25000238";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEntityCacheName() {
      String id = "25000239";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEstimatedBeanSize() {
      String id = "25000240";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCacheRef() {
      String id = "25000241";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalSetting() {
      String id = "25000242";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCantSetCacheBetweenTransactions() {
      String id = "25000243";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUseAutomaticKeyGeneration() {
      String id = "25000244";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGeneratorType() {
      String id = "25000245";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGeneratorName() {
      String id = "25000246";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getKeyCacheSize() {
      String id = "25000247";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGeneratorTypeTT() {
      String id = "25000248";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGeneratorNameTT() {
      String id = "25000249";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getKeyCacheSizeTT() {
      String id = "25000250";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAutomaticKeyGeneration() {
      String id = "25000251";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIdempotentMethods() {
      String id = "25000252";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getForeignKeyTable() {
      String id = "25000253";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRoleName() {
      String id = "25000254";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckExistsOnMethod() {
      String id = "25000255";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOptimisticConcurrency() {
      String id = "25000256";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckExistsOnMethodTT() {
      String id = "25000257";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOptimisticConcurrencyTT() {
      String id = "25000258";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWlEJBQl() {
      String id = "2500259";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAddCMPFieldTitle() {
      String id = "2500260";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAddCMPFieldTitleTT() {
      String id = "2500261";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAddFinderTitle() {
      String id = "2500262";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAddFinderTitleTT() {
      String id = "2500263";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeleteFinderTitle() {
      String id = "2500264";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeleteFinderTitleTT() {
      String id = "2500265";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeleteCMPFieldTitle() {
      String id = "2500266";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeleteCMPFieldTitleTT() {
      String id = "2500267";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorNoTableSelected() {
      String id = "2500268";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoTableSelected() {
      String id = "2500269";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSelectDataSource() {
      String id = "2500270";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAvailableDataSources() {
      String id = "2500271";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAvailableConnectionPools() {
      String id = "2500272";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPickTopic() {
      String id = "2500273";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPickQueue() {
      String id = "2500274";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPickColumn(String arg0) {
      String id = "2500275";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIncorrectTable(String arg0, String arg1) {
      String id = "2500276";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorCannotFindTable() {
      String id = "2500277";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSystemUser() {
      String id = "2500278";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAuthenticationException(String arg0, String arg1) {
      String id = "2500279";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNodeSecurity() {
      String id = "2500280";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPrincipals() {
      String id = "2500281";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCantFindWeblogicEJBJar() {
      String id = "2500282";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRunDDInitWithEJBJar() {
      String id = "2500283";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPrincipalsMappedToRole(String arg0) {
      String id = "2500284";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPrincipalsTT() {
      String id = "2500285";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNameNotFoundException(String arg0) {
      String id = "2500286";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEntityBean() {
      String id = "2500287";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSessionBean() {
      String id = "2500288";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMessageDrivenBean() {
      String id = "2500289";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRelationNameTable() {
      String id = "2500290";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMultiplicityTable() {
      String id = "2500291";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBNameTitle() {
      String id = "2500292";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldntLoadClass(String arg0) {
      String id = "2500293";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getContainerManagedPersistence() {
      String id = "2500294";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBClientJar() {
      String id = "2500295";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStatelessBeanLoadAlgorithm() {
      String id = "2500296";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStatelessBeanCallRouterClassName() {
      String id = "2500297";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStatelessBeanIsClusterable() {
      String id = "2500298";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStatelessBeanMethodsAreIdempotent() {
      String id = "2500299";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGroupNames() {
      String id = "2500300";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDefaultGroupName() {
      String id = "2500301";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGroups() {
      String id = "2500302";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCacheType() {
      String id = "2500303";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVerifyColumn() {
      String id = "2500304";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConfigureConcurrency() {
      String id = "2500305";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOptimisticColumn() {
      String id = "2500306";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConfigureConcurrencyStrategyTitle() {
      String id = "2500307";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCantAddPermissionsMessage() {
      String id = "2500308";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCantAddPermissionsTitle() {
      String id = "2500309";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getType() {
      String id = "2500310";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getValue() {
      String id = "2500311";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIncompleteSettings() {
      String id = "2500312";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBrowseEllipsis() {
      String id = "2500313";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoDataSources() {
      String id = "2500314";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorDataSources() {
      String id = "2500315";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getReverseEJBGen() {
      String id = "2500316";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOutputDirectory() {
      String id = "2500317";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGenerate() {
      String id = "2500318";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOutput() {
      String id = "2500319";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getReverseEJBGenTitle() {
      String id = "2500320";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getClassPrefix() {
      String id = "2500321";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVerbose() {
      String id = "2500322";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnvironmentEntry() {
      String id = "2500323";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResourceRef() {
      String id = "2500324";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBRef() {
      String id = "2500325";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBLocalRef() {
      String id = "2500326";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCMPField() {
      String id = "2500327";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFinder() {
      String id = "2500328";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnablePerformanceSettings() {
      String id = "2500329";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUseSelectForUpdate() {
      String id = "2500330";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCantRemoveLastCMPFieldTitle() {
      String id = "2500331";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCantRemoveLastCMPField() {
      String id = "2500332";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRelationshipCachingName() {
      String id = "2500333";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRelationshipCaching() {
      String id = "2500334";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAddRelationshipCachingElement() {
      String id = "2500335";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeleteLastCachingElement() {
      String id = "2500336";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnableBatchOperations() {
      String id = "2500337";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOrderDatabaseOperations() {
      String id = "2500338";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDDLFileName() {
      String id = "2500339";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnableBeanClassBeanRedeploy() {
      String id = "2500340";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCascadeDelete() {
      String id = "2500342";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDatabaseType() {
      String id = "2500341";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUseCallerIdentity() {
      String id = "2500343";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnableDynamicQueries() {
      String id = "2500344";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getValidateDBSchemaWith() {
      String id = "2500345";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebEnvEntryName() {
      String id = "256001";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionTimeoutTT() {
      String id = "256002";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionTrackingEnabledTT() {
      String id = "256003";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebParamValue() {
      String id = "256004";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebHomeInterfaceName() {
      String id = "256005";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHttpErrorCode() {
      String id = "256006";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebPersistentStoreCookieName() {
      String id = "256007";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServletURLPatternTT() {
      String id = "256008";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagRtexprTT() {
      String id = "256009";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebCookieDomainTT() {
      String id = "256010";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebRedirectContent() {
      String id = "256011";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHttpErrorCodeTT() {
      String id = "256012";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServletName() {
      String id = "256013";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebValidatorClassname() {
      String id = "256014";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebCharsetResourcePath() {
      String id = "256015";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspCompileCommand() {
      String id = "256016";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebURLRewritingEnabled() {
      String id = "256017";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebFilterUrlPatternTT() {
      String id = "256018";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getURLPattern() {
      String id = "256019";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebRoleRefDescriptionTT() {
      String id = "256020";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspPrecompileTT() {
      String id = "256021";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspCompilerSupportsEncoding() {
      String id = "256022";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebRoleMappingPrincipalNames() {
      String id = "256023";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebEnvEntryNameTT() {
      String id = "256024";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDescriptionTT() {
      String id = "256025";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCookieMaxAgeSecsTT() {
      String id = "256026";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagExtraInfoClassname() {
      String id = "256028";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagAttributeName() {
      String id = "256029";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebParamValueTT() {
      String id = "256030";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebRoleNameTT() {
      String id = "256031";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebEJBRefName() {
      String id = "256032";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRedirectContentType() {
      String id = "256033";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspNoTryBlocks() {
      String id = "256034";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServletJspFileTT() {
      String id = "256035";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTaglibUriTT() {
      String id = "256036";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJavaCharsetName() {
      String id = "256037";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebListenerClassName() {
      String id = "256038";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionCookieNameTT() {
      String id = "256039";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebResourceReference() {
      String id = "256040";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRedirectContentTypeTT() {
      String id = "256041";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPersistentStoreCookieNameTT() {
      String id = "256042";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspTagNameTT() {
      String id = "256043";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionInvalidationIntervalSecsTT() {
      String id = "256044";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagVarNameTT() {
      String id = "256045";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionPersistentStoreDirTT() {
      String id = "256046";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionCookiesEnabled() {
      String id = "256047";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTaglibLocationTT() {
      String id = "256048";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTaglibValidatorClassnameTT() {
      String id = "256049";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebLoginAuthMethodTT() {
      String id = "256050";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebLocalInterfaceNameTT() {
      String id = "256051";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspWorkingDir() {
      String id = "256052";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagExtraInfoClassnameTT() {
      String id = "256053";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionTimeoutSecs() {
      String id = "256054";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckAuthOnForwardEnabledTT() {
      String id = "256055";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebFilterClassTT() {
      String id = "256056";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspVerboseTT() {
      String id = "256057";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebLoginErrorPageTT() {
      String id = "256058";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDescription() {
      String id = "256059";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagVarNameFromAttribute() {
      String id = "256060";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getParamName() {
      String id = "256061";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebLoginRealmName() {
      String id = "256062";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSecurityConstraintDisplayNameTT() {
      String id = "256063";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTaglibJspVersionTT() {
      String id = "256065";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebRunAsTT() {
      String id = "256066";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLargeIconFileName() {
      String id = "256067";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDisplayName() {
      String id = "256068";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspKeepgenerated() {
      String id = "256069";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebHomeInterfaceNameTT() {
      String id = "256070";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebResourceRefDescriptionTT() {
      String id = "256072";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagVarNameFromAttributeTT() {
      String id = "256073";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionPersistentStoreTypeTT() {
      String id = "256074";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getName() {
      String id = "256075";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getURLRewritingEnabledTT() {
      String id = "256076";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspSuperclassTT() {
      String id = "256077";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTransportGuarantee() {
      String id = "256079";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagVarScopeStrTT() {
      String id = "256080";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagVarDeclare() {
      String id = "256081";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFilterNameTT() {
      String id = "256082";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebResourceEnvRefNameTT() {
      String id = "256083";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspCompilerClass() {
      String id = "256084";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspPageCheckSeconds() {
      String id = "256085";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMimeExtensionTT() {
      String id = "256086";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagAttRtexpr() {
      String id = "256087";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagVarTypeTT() {
      String id = "256088";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebEnvEntryTypeTT() {
      String id = "256089";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTaglibVersionTT() {
      String id = "256090";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServletClass() {
      String id = "256091";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspCompileFlagsTT() {
      String id = "256092";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServletClassTT() {
      String id = "256094";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebEnvEntryType() {
      String id = "256095";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServletNameTT() {
      String id = "256097";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVirtualDirsLocalPathTT() {
      String id = "256098";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebResourceRefAuthTT() {
      String id = "256099";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResourcePathTT() {
      String id = "256100";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagClassnameTT() {
      String id = "256101";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVirtualDirsURLPatternsTT() {
      String id = "256102";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebEJBRefType() {
      String id = "256103";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionJDBCConnectionTimeoutSecs() {
      String id = "256104";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRole() {
      String id = "256107";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionCookiesEnabledTT() {
      String id = "256108";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspVerbose() {
      String id = "256109";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagAttRequiredTT() {
      String id = "256110";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebEJBRefNameTT() {
      String id = "256111";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebLoginAuthMethod() {
      String id = "256112";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebResourceRefSharingScope() {
      String id = "256113";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebLocalInterfaceName() {
      String id = "256114";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionConsoleMainAttributeTT() {
      String id = "256115";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebRefName() {
      String id = "256116";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspSuperclass() {
      String id = "256117";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionTimeoutSecsTT() {
      String id = "256118";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFilter() {
      String id = "256120";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagAttType() {
      String id = "256121";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagClassname() {
      String id = "256122";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTaglibTLDUriTT() {
      String id = "256123";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMimeTypeTT() {
      String id = "256124";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspWorkingDirTT() {
      String id = "256125";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebResourceReferenceTT() {
      String id = "256126";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebLoginPageTT() {
      String id = "256127";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSecurityRoleNameTT() {
      String id = "256128";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTaglibLocation() {
      String id = "256129";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebResourceMappingJndiName() {
      String id = "256130";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebEJBLinkNameTT() {
      String id = "256131";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebRemoteInterfaceNameTT() {
      String id = "256133";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionIDLengthTT() {
      String id = "256134";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspFile() {
      String id = "256135";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionCookieName() {
      String id = "256137";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionPersistentStorePool() {
      String id = "256138";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebDistributableTT() {
      String id = "256139";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebLoginRealmNameTT() {
      String id = "256140";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebEnvEntryDescriptionTT() {
      String id = "256141";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebEjbRefJndiNameTT() {
      String id = "256142";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspPackagePrefixTT() {
      String id = "256143";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagVarType() {
      String id = "256144";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionCookieMaxAgeSecs() {
      String id = "256145";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspEncoding() {
      String id = "256146";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebEjbReference() {
      String id = "256147";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDisplayNameTT() {
      String id = "256148";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebRefTypeTT() {
      String id = "256149";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebLoginPage() {
      String id = "256150";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspKeepgeneratedTT() {
      String id = "256151";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagAttRequired() {
      String id = "256153";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMappingServletTT() {
      String id = "256154";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebEJBLinkName() {
      String id = "256155";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionPersistentStorePoolTT() {
      String id = "256156";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebExceptionType() {
      String id = "256157";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUrlPattern() {
      String id = "256158";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPrincipalNamesTT() {
      String id = "256159";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCookieComment() {
      String id = "256160";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSmallIconFileNameTT() {
      String id = "256161";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebLoginErrorPage() {
      String id = "256162";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionTimeout() {
      String id = "256163";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspPackagePrefix() {
      String id = "256164";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebEjbRefDescriptionTT() {
      String id = "256165";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionTrackingEnabled() {
      String id = "256166";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionIDLength() {
      String id = "256167";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspPageCheckSecondsTT() {
      String id = "256168";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspPrecompile() {
      String id = "256169";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspNoTryBlocksTT() {
      String id = "256170";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTaglibUri() {
      String id = "256171";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIANACharsetName() {
      String id = "256172";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebDistributable() {
      String id = "256173";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebRemoteInterfaceName() {
      String id = "256174";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebJndiNameTT() {
      String id = "256175";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTLDDescriptionTT() {
      String id = "256176";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebPersistentStoreType() {
      String id = "256177";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebInvalidationIntervalSecs() {
      String id = "256178";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebEjbReferenceTT() {
      String id = "256179";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMappingJavaCharsetNameTT() {
      String id = "256180";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagAttNameTT() {
      String id = "256181";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebResourceEnvRefDescriptionTT() {
      String id = "256185";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTransportGuaranteeTT() {
      String id = "256186";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionPersistentStoreDir() {
      String id = "256187";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspEncodingTT() {
      String id = "256188";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagDescriptionTT() {
      String id = "256189";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebEJBRefTypeTT() {
      String id = "256191";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVirtualDirsLocalPath() {
      String id = "256192";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspCompilerClassTT() {
      String id = "256193";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMimeType() {
      String id = "256195";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebEnvEntryValueTT() {
      String id = "256196";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLargeIconFileNameTT() {
      String id = "256197";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRefType() {
      String id = "256198";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspCompileFlags() {
      String id = "256200";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionJDBCConnectionTimeoutSecsTT() {
      String id = "256201";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getURI() {
      String id = "256202";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebResourceRefNameTT() {
      String id = "256203";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExceptionTypeTT() {
      String id = "256204";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebLoadOnStartupTT() {
      String id = "256205";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRedirectContentTT() {
      String id = "256206";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebErrorPageLocationTT() {
      String id = "256207";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRedirectWithAbsoluteURLEnabledTT() {
      String id = "256208";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRoleAssignmentTT() {
      String id = "256209";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTaglibVersion() {
      String id = "256210";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagName() {
      String id = "256211";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFilterClass() {
      String id = "256212";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCookieCommentTT() {
      String id = "256213";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebRunAs() {
      String id = "256214";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspBodyContentTT() {
      String id = "256215";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspCompileCommandTT() {
      String id = "256216";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMimeExtension() {
      String id = "256217";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCookiePathTT() {
      String id = "256218";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCookiePath() {
      String id = "256219";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getListenerClassNameTT() {
      String id = "256220";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJavaCharsetNameTT() {
      String id = "256221";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorPageLocation() {
      String id = "256222";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagAttTypeTT() {
      String id = "256223";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJspCompilerSupportsEncodingTT() {
      String id = "256224";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckAuthOnForwardEnabled() {
      String id = "256225";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionConsoleMainAttribute() {
      String id = "256226";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebEnvEntryValue() {
      String id = "256227";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSmallIconFileName() {
      String id = "256228";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getParamNameTT() {
      String id = "256229";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagBodyContent() {
      String id = "256230";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServlet() {
      String id = "256231";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebResourceAuth() {
      String id = "256232";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagVarScopeStr() {
      String id = "256233";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFilterTT() {
      String id = "256234";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSessionCookieDomain() {
      String id = "256236";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTaglibJspVersion() {
      String id = "256237";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebJndiName() {
      String id = "256238";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLoadOnStartup() {
      String id = "256239";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebSharingScopeTT() {
      String id = "256240";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVirtualDirsURLPatterns() {
      String id = "256241";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagVarDeclareTT() {
      String id = "256242";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFilterName() {
      String id = "256244";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRedirectWithAbsoluteURLEnabled() {
      String id = "256245";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIANACharsetNameTT() {
      String id = "256246";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getContextPath() {
      String id = "256247";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getContextPathTT() {
      String id = "256248";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebResourceName() {
      String id = "256249";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getURLPatterns() {
      String id = "256250";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHTTPMethods() {
      String id = "256251";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServletCode() {
      String id = "256252";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getURLMappings() {
      String id = "256253";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAllowedRoles() {
      String id = "256254";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDisallowedRoles() {
      String id = "256255";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getURLMatchMapClass() {
      String id = "256256";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWelcomeFiles() {
      String id = "256257";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorPages() {
      String id = "256258";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLogin() {
      String id = "256259";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMimeTypes() {
      String id = "256260";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getContextPathTab() {
      String id = "256261";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getContextParams() {
      String id = "256262";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDisplay() {
      String id = "256263";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServlets() {
      String id = "256264";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInitParams() {
      String id = "256265";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServletMappings() {
      String id = "256266";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFilterMappings() {
      String id = "256267";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnvEntries() {
      String id = "256268";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFilters() {
      String id = "256269";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getListeners() {
      String id = "256270";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServletFilterMappings() {
      String id = "256271";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJ2EERefs() {
      String id = "256272";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResourcesPages() {
      String id = "256273";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityConstraints() {
      String id = "256274";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSessionSettings() {
      String id = "256275";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJSPSettings() {
      String id = "256276";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMiscellaneous() {
      String id = "256277";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagLibraries() {
      String id = "256278";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTabResourceEnvRefs() {
      String id = "256279";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTabRoles() {
      String id = "256280";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSSLMisc() {
      String id = "256281";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHTTPCookieSettings() {
      String id = "256282";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJavaCompiler() {
      String id = "256283";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTabDeployment() {
      String id = "256284";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCodeGen() {
      String id = "256285";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getContainerSettings() {
      String id = "256286";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVirtualDirs() {
      String id = "256287";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIANAMappings() {
      String id = "256288";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPathCharsetMappings() {
      String id = "256289";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTaglibNaming() {
      String id = "256290";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTLDInfo() {
      String id = "256291";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getValidator() {
      String id = "256292";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTagSettings() {
      String id = "256293";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttributes() {
      String id = "256294";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVariables() {
      String id = "256295";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAddTaglib() {
      String id = "256296";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnterTaglibURI() {
      String id = "256297";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getURIRequired() {
      String id = "256298";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDuplicateURIString() {
      String id = "256299";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getURIsUnique() {
      String id = "256300";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocationMissingMsg() {
      String id = "256301";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocationMissingTitle() {
      String id = "256302";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTLDFileMsg() {
      String id = "256303";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTLDJarMsg() {
      String id = "256304";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTLDURILabel() {
      String id = "256305";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTLDLocationLabel() {
      String id = "256306";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTags() {
      String id = "256307";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getContinue() {
      String id = "256308";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShowDetails() {
      String id = "256309";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHideDetails() {
      String id = "256310";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMoveUp() {
      String id = "256311";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMoveDown() {
      String id = "256312";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAdd() {
      String id = "256313";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDelete() {
      String id = "256314";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAddEllipsis() {
      String id = "256315";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditEllipsis() {
      String id = "256316";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalEntry() {
      String id = "256317";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEntryAlreadyExists() {
      String id = "256318";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCopyTT() {
      String id = "256319";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSelectAll() {
      String id = "256320";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSelectAllTT() {
      String id = "256321";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFindEllipsis() {
      String id = "256322";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFindTT() {
      String id = "256323";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotAddMapping() {
      String id = "256324";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoServletsMsg() {
      String id = "256325";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoFiltersMsg() {
      String id = "256326";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRoleRef() {
      String id = "256327";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRoleRefTT() {
      String id = "256328";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFilterMappingServletTT() {
      String id = "256329";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFilterNotSelected() {
      String id = "256330";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getURLPatternEmpty() {
      String id = "256331";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServletNotSelected() {
      String id = "256332";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIndexDirectoryEnabled() {
      String id = "256333";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIndexDirectorySortBy() {
      String id = "256334";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServletReloadCheckSecs() {
      String id = "256335";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServletReloadCheckSecsTT() {
      String id = "256336";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSingleThreadedServletPoolSize() {
      String id = "256337";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSessionMonitoringEnabled() {
      String id = "256338";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPreferWebInfClasses() {
      String id = "256339";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExecuteQueue() {
      String id = "256340";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExecuteQueueTT() {
      String id = "256341";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRunAsPrincipal() {
      String id = "25632";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInitAsPrincipal() {
      String id = "256343";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDestroyAsPrincipal() {
      String id = "256344";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExecution() {
      String id = "256345";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCacheSizeTT() {
      String id = "256346";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPrintNulls() {
      String id = "256347";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPrintNullsTT() {
      String id = "256348";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWebAppDispatchPolicyTT() {
      String id = "256349";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartMsgDrivenBeansWithApp() {
      String id = "257000";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCachingStrategy() {
      String id = "257001";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShortMaxBeansInCache() {
      String id = "257002";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEntityBeanCache() {
      String id = "257003";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMaxCacheSize() {
      String id = "257004";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCacheSizeBytes() {
      String id = "257005";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCacheSizeMB() {
      String id = "257006";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRequireFieldGreaterThanZero(String arg0) {
      String id = "257007";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNewConnForCommitEnabled() {
      String id = "257008";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTxContextOnCloseNeeded() {
      String id = "257009";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDebugLevel() {
      String id = "257010";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPstmtCacheSize() {
      String id = "257011";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocalTxSupported() {
      String id = "257012";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getKeepConnUntilTxComplete() {
      String id = "257013";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEndOnlyOnceEnabled() {
      String id = "257014";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getKeepLogicalConnOnRelease() {
      String id = "257015";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResourceHealthMonitorEnabled() {
      String id = "257016";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRecoverOnlyOnceEnabled() {
      String id = "257017";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDriverParams() {
      String id = "257018";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStatementParams() {
      String id = "257019";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPreparedStatementParams() {
      String id = "257020";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRowPrefetch() {
      String id = "257021";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStreamChunkSize() {
      String id = "257022";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRowPrefetchSize() {
      String id = "257023";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProfilingEnabled() {
      String id = "257024";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getParameterLoggingEnabled() {
      String id = "257025";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMaxParameterLength() {
      String id = "257026";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCacheProfilingThreshold() {
      String id = "257027";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCacheSize() {
      String id = "257028";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLeakProfilingEnabled() {
      String id = "257029";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLoginDelaySecs() {
      String id = "257030";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMaxCapacity() {
      String id = "257031";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPoolSizeSettings() {
      String id = "257032";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnCheckSettings() {
      String id = "257033";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInitialCapacity() {
      String id = "257034";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCapacityIncrement() {
      String id = "257035";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShrinkEnabled() {
      String id = "257036";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckOnReserveEnabled() {
      String id = "257037";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckTableName() {
      String id = "257038";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckOnReleaseEnabled() {
      String id = "257039";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRefreshMinutes() {
      String id = "257040";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnSettings() {
      String id = "257041";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnParams() {
      String id = "257042";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDriverURL() {
      String id = "257043";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDriverClassName() {
      String id = "257044";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDBMSUserName() {
      String id = "257045";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJDBCAclName() {
      String id = "257046";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJDBCFactoryName() {
      String id = "257047";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnection() {
      String id = "257048";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getXaSettings() {
      String id = "257049";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDriver() {
      String id = "257050";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMiscSettings() {
      String id = "257051";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShrinkPeriodMins() {
      String id = "257052";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnDurationTime() {
      String id = "257053";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnCleanupFrequency() {
      String id = "257054";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getXMLParserFactory() {
      String id = "257070";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getXMLEntityMappings() {
      String id = "257071";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityRealm() {
      String id = "257072";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getApplicationParams() {
      String id = "257073";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDocumentBuilderFactory() {
      String id = "257074";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTransformerFactory() {
      String id = "257075";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSAXParserFactory() {
      String id = "257076";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSystemID() {
      String id = "257077";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEntityMappingName() {
      String id = "257078";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPublicID() {
      String id = "257079";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEntityURI() {
      String id = "257080";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWhenToCache() {
      String id = "257081";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCacheTimeoutInterval() {
      String id = "257082";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRealmName() {
      String id = "257083";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWLAppSettings() {
      String id = "257084";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEntityCache() {
      String id = "257085";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJDBCDatasources() {
      String id = "257087";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJ2EEApp() {
      String id = "257088";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityRoles() {
      String id = "257089";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUISettings() {
      String id = "257090";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeploymentOrder() {
      String id = "257091";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInactiveConnTimeoutSecs() {
      String id = "257092";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnReserveTimeoutSecs() {
      String id = "257093";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTestFrequencySecs() {
      String id = "257094";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnRetryFrequencySecs() {
      String id = "257095";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShrinkFrequencySecs() {
      String id = "257096";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHighestNumWaiters() {
      String id = "257097";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHighestNumUnavailable() {
      String id = "257098";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPrincipalMappings() {
      String id = "257500";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResourceUsername() {
      String id = "257501";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResourceUserPassword() {
      String id = "257502";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInitiatingPrincipals() {
      String id = "257503";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAuthMechs() {
      String id = "257504";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAuthMechType() {
      String id = "257505";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCredentialInterface() {
      String id = "257506";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConfigProps() {
      String id = "257507";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConfigPropName() {
      String id = "257508";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConfigPropType() {
      String id = "257509";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConfigPropValue() {
      String id = "257510";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSettings() {
      String id = "257511";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWLSettings() {
      String id = "257512";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRAConnC() {
      String id = "257513";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRAConnFactoryI() {
      String id = "257514";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRAManagedConnFactoryC() {
      String id = "257515";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRAConnI() {
      String id = "257516";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRAConnFactoryC() {
      String id = "257517";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectorEISType() {
      String id = "257518";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLicenseRequired() {
      String id = "257519";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLicenseDescription() {
      String id = "257520";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectorVersion() {
      String id = "257521";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTxSupport() {
      String id = "257522";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnSpecVersion() {
      String id = "257523";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getReAuthSupport() {
      String id = "257524";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVendorName() {
      String id = "257525";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityPermissionSpec() {
      String id = "257526";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnPooling() {
      String id = "257527";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProfAndLogging() {
      String id = "257528";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnFactoryName() {
      String id = "257529";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnMaxIdleTime() {
      String id = "257530";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLoggingEnabled() {
      String id = "257531";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLogfileName() {
      String id = "257532";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRALinkRef() {
      String id = "257533";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNativeLibDir() {
      String id = "257534";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWLConfigProps() {
      String id = "257535";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMustDefinePrincipal() {
      String id = "257536";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAppName() {
      String id = "257537";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAllowRemoveDuringTx() {
      String id = "257538";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckOnCreateEnabled() {
      String id = "257539";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMatchConnectionsSupported() {
      String id = "257540";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVersion() {
      String id = "257541";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getClassesNotFoundWarning(String arg0, String arg1) {
      String id = "257542";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getClassNotFoundException(String arg0) {
      String id = "257543";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatingWLSpecificDescriptors(String arg0) {
      String id = "257544";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFinishedOpeningMsg(String arg0) {
      String id = "257545";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMethodPermissionsSelectedRolesLabel() {
      String id = "257546";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMethodPermissionsSelectedMethodsLabel() {
      String id = "257547";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMethodPermissionsAvailableRolesLabel() {
      String id = "257548";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMethodPermissionsAvailableMethodsLabel() {
      String id = "257549";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMethodPermissionsRoleErrorMessage() {
      String id = "257550";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMethodPermissionsMethodsErrorMessage() {
      String id = "257551";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotDeleteRoleInUseMessage(String arg0) {
      String id = "257552";
      String subsystem = "Tools";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecondsToTrustAnIdlePoolConnection() {
      String id = "257553";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExternallyDefined() {
      String id = "257554";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getValidationErrorMessage() {
      String id = "257555";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResourceEnvReferenceNameColumnTitle() {
      String id = "257556";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResourceEnvReferenceTypeColumnTitle() {
      String id = "257557";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResEnvRefName() {
      String id = "257558";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResEnvRefNameTT() {
      String id = "257559";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResEnvRefType() {
      String id = "257560";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResEnvRefTypeTT() {
      String id = "257561";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResourceEnvRef() {
      String id = "257562";
      String subsystem = "Tools";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
