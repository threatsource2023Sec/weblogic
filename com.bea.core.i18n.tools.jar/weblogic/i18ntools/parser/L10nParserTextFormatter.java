package weblogic.i18ntools.parser;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class L10nParserTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public L10nParserTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.i18ntools.parser.L10nParserTextLocalizer", L10nParserTextFormatter.class.getClassLoader());
   }

   public L10nParserTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.i18ntools.parser.L10nParserTextLocalizer", L10nParserTextFormatter.class.getClassLoader());
   }

   public static L10nParserTextFormatter getInstance() {
      return new L10nParserTextFormatter();
   }

   public static L10nParserTextFormatter getInstance(Locale l) {
      return new L10nParserTextFormatter(l);
   }

   public String buildingDTD() {
      String id = "BUILDING_DTD";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String tryingDTDPath(String arg0) {
      String id = "TRYING_DTD_PATH";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String parsingCatalog(String arg0) {
      String id = "PARSING_CATALOG";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String docStart() {
      String id = "DOC_START";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String receiveElement(String arg0) {
      String id = "RECEIVE_ELEMENT";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String addCatalog() {
      String id = "ADD_CATALOG";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String setAttr(String arg0, String arg1) {
      String id = "SET_ATTR";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String addLogMessage() {
      String id = "ADD_LOG_MESSAGE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String addBody() {
      String id = "ADD_BODY";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String addDetail() {
      String id = "ADD_DETAIIL";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String addCause() {
      String id = "ADD_CAUSE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String dupMessage(String arg0, String arg1) {
      String id = "DUP_MESSAGE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String linkLogMessage(String arg0) {
      String id = "LINK_LOG_MESSAGE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ignoreDup(String arg0) {
      String id = "IGNORE_DUP";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String wrongException() {
      String id = "WRONG_EXCEPTION";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String linkLogBody(String arg0) {
      String id = "LINK_LOG_BODY";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String linkMsgBody(String arg0) {
      String id = "LINK_MSG_BODY";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String linkDetail(String arg0) {
      String id = "LINK_DETAIL";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String linkCause(String arg0) {
      String id = "LINK_CAUSE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String linkAction(String arg0) {
      String id = "LINK_ACTION";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String linkMessage(String arg0) {
      String id = "LINK_MESSAGE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String receivedChars(String arg0) {
      String id = "RECEIVED_CHARS";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ignoreSpaces() {
      String id = "IGNORE_SPACES";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String editString(String arg0) {
      String id = "EDIT_STRING";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String addToBody() {
      String id = "ADD_TO_BODY";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String addToDetail() {
      String id = "ADD_TO_DETAIL";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String addToCause() {
      String id = "ADD_TO_CAUSE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String addToAction() {
      String id = "ADD_TO_ACTION";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ignoring() {
      String id = "IGNORING";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String resolving(String arg0, String arg1) {
      String id = "RESOLVING";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String checkingPath(String arg0) {
      String id = "CHECKING_PATH";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String returningPath(String arg0) {
      String id = "RETURNING_PATH";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String resolvedPath(String arg0) {
      String id = "RESOLVED_PATH";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String unableToRead(String arg0) {
      String id = "UNABLE_TO_READ";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String nullId() {
      String id = "NULL_ID";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noDefaultDef(String arg0) {
      String id = "NO_DEFAULT_DEF";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noPackageInfo(String arg0) {
      String id = "NO_PACKAGE_INFO";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String nonNumericId(String arg0) {
      String id = "NON_NUMERIC_ID";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String nullBody(String arg0) {
      String id = "NULL_BODY";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noDetail(String arg0) {
      String id = "NO_DETAIL";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noCause(String arg0) {
      String id = "NO_CAUSE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noAction(String arg0) {
      String id = "NO_ACTION";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String TBD() {
      String id = "TBD";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String validatingMsg() {
      String id = "VALIDATING_MSG";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String logVector() {
      String id = "LOG_VECTOR";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String element(String arg0) {
      String id = "ELEMENT";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgVector() {
      String id = "MSG_VECTOR";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String nullVersion(String arg0) {
      String id = "NULL_VERSION";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noMessages(String arg0) {
      String id = "NO_MESSAGES";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String validateSimple() {
      String id = "VALIDATE_SIMPLE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String language() {
      String id = "LANGUAGE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ISOLanguage() {
      String id = "ISO_LANGUAGE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String country() {
      String id = "COUNTRY";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ISOCountry() {
      String id = "ISO_COUNTRY";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String variant() {
      String id = "VARIANT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String localeVariant() {
      String id = "LOCALE_VARIANT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ignoreErrors() {
      String id = "IGNORE_ERRORS";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String verbose() {
      String id = "VERBOSE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String debug() {
      String id = "DEBUG";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String filelist() {
      String id = "FILELIST";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String options() {
      String id = "OPTIONS";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ignoreOpt(boolean arg0) {
      String id = "IGNORE_OPT";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String verboseOpt(boolean arg0) {
      String id = "VERBOSE_OPT";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String debugOpt(boolean arg0) {
      String id = "DEBUG_OPT";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String langOpt(String arg0) {
      String id = "LANG_OPT";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String countryOpt(String arg0) {
      String id = "COUNTRY_OPT";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String variantOpt(String arg0) {
      String id = "VARIANT_OPT";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String buildingLang(String arg0, String arg1) {
      String id = "BUILDING_LANG";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String langVal(String arg0) {
      String id = "LANG_VAL";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String parsing(String arg0) {
      String id = "PARSING";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String currLocale(String arg0, String arg1, String arg2) {
      String id = "CURR_LOCALE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String unableToParse(String arg0) {
      String id = "UNABLE_TO_PARSE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String inputFile(String arg0) {
      String id = "INPUT_FILE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String outName(String arg0) {
      String id = "OUT_NAME";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String outFile(String arg0) {
      String id = "OUT_FILE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String nothingToDo() {
      String id = "NOTHING_TO_DO";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noPath(String arg0) {
      String id = "NO_PATH";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noFile(String arg0) {
      String id = "NO_FILE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noPackage(String arg0) {
      String id = "NO_PACKAGE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String incorrectPackage(String arg0, String arg1, String arg2) {
      String id = "INCORRECT_PACKAGE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidPackage(String arg0, String arg1) {
      String id = "INVALID_PACKAGE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noEntity(String arg0, String arg1) {
      String id = "NO_ENTITY";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String wrongExceptionCaught(String arg0) {
      String id = "WRONG_EXCEPTION_CAUGHT";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noSuchMessage(String arg0, String arg1) {
      String id = "NO_SUCH_MESSAGE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String addAction() {
      String id = "ADD_ACTION";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidCatalog(String arg0, String arg1) {
      String id = "INVALID_CATALOG";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidDocument(String arg0, String arg1) {
      String id = "INVALID_DOCUMENT";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String multipleElements(String arg0) {
      String id = "MULT_ELEMS";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String unexpectedElement(String arg0) {
      String id = "UNEXPECTED_ELEM";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String locator(int arg0, int arg1) {
      String id = "LOCATOR";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String unrecognizedElement(String arg0) {
      String id = "UNREC_ELEMENT";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String unexpectedChars(String arg0) {
      String id = "UNEXPECTED_CHARS";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String writeError(String arg0) {
      String id = "WRITE_ERROR";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
