package weblogic.i18ntools.gui;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class MsgEditorTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public MsgEditorTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.i18ntools.gui.MsgEditorTextLocalizer", MsgEditorTextFormatter.class.getClassLoader());
   }

   public MsgEditorTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.i18ntools.gui.MsgEditorTextLocalizer", MsgEditorTextFormatter.class.getClassLoader());
   }

   public static MsgEditorTextFormatter getInstance() {
      return new MsgEditorTextFormatter();
   }

   public static MsgEditorTextFormatter getInstance(Locale l) {
      return new MsgEditorTextFormatter(l);
   }

   public String usage() {
      String id = "USAGE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageL10n() {
      String id = "LOC_USAGE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noDir(String arg0) {
      String id = "NODIR";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String logMessageTitle() {
      String id = "LOG_MESSAGE_TITLE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String simpleMessageTitle() {
      String id = "SIMPLE_MESSAGE_TITLE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badArgs(String arg0) {
      String id = "BAD_ARGS";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String titleEditor() {
      String id = "TITLE_EDITOR";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelEditor() {
      String id = "LABEL_EDITOR";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelServerCats() {
      String id = "LABEL_SERVER_CATS";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelNonServerCats() {
      String id = "LABEL_NONSERVER_CATS";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelMsgCat() {
      String id = "LABEL_MSG_CAT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String buttonBrowse() {
      String id = "BUTTON_BROWSE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelI18nPkg() {
      String id = "LABEL_I18N_PKG";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelL10nPkg() {
      String id = "LABEL_L10N_PKG";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelSubsystem() {
      String id = "LABEL_SUBSYSTEM";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelVersion() {
      String id = "LABEL_VERSION";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelBaseId() {
      String id = "LABEL_BASEID";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelEndId() {
      String id = "LABEL_ENDID";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelLoggables() {
      String id = "LABEL_LOGGABLES";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String buttonAdd() {
      String id = "BUTTON_ADD";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String buttonClear() {
      String id = "BUTTON_CLEAR";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelLastUpdated() {
      String id = "LABEL_LAST_UPDATED";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String tipLastUpdated() {
      String id = "TIP_LAST_UPDATED";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelMessageId() {
      String id = "LABEL_MESSAGE_ID";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String buttonNextId() {
      String id = "BUTTON_NEXTID";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelComment() {
      String id = "LABEL_COMMENT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelMethod() {
      String id = "LABEL_METHOD";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelMethodType() {
      String id = "LABEL_METHOD_TYPE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelSeverity() {
      String id = "LABEL_SEVERITY";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelBody() {
      String id = "LABEL_BODY";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelDetail() {
      String id = "LABEL_DETAIL";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelCause() {
      String id = "LABEL_CAUSE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelAction() {
      String id = "LABEL_ACTION";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelStack() {
      String id = "LABEL_STACK";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelRetired() {
      String id = "LABEL_RETIRED";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String tipComment() {
      String id = "TIP_COMMENT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgChooseCat() {
      String id = "MSG_COOSE_CAT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgSearchLog() {
      String id = "MSG_SEARCH_LOG";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgSearchSimple() {
      String id = "MSG_SEARCH_SIMPLE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgBadParseL10n(String arg0, String arg1) {
      String id = "MSG_BAD_PARSE_L10N";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgBadParse(String arg0, String arg1) {
      String id = "MSG_BAD_PARSE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgCreateCat() {
      String id = "MSG_CREATE_CAT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgBlankItem(String arg0) {
      String id = "MSG_BLANK_ITEM";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgBadMethod() {
      String id = "MSG_BAD_METHOD";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgUniqueType(String arg0) {
      String id = "MSG_UNIQUE_TYPE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgJavaId(String arg0) {
      String id = "MSG_JAVAID";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgBlankBody() {
      String id = "MSG_BLANK_BODY";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgWrongTypeX() {
      String id = "MSG_WRONG_TYPE_X";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgNoMsg() {
      String id = "MSG_NOMSG";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgNotLogCat() {
      String id = "MSG_NOT_LOG_CAT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgDupId() {
      String id = "MSG_DUPID";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgAddFailed(String arg0) {
      String id = "MSG_ADD_FAILED";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgAddFailedInternalError(String arg0) {
      String id = "MSG_ADD_FAILED_INTERR";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgEnterId() {
      String id = "MSG_ENTER_ID";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgNotSimpleCat() {
      String id = "MSG_NOT_SIMPLE_CAT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInternalError(String arg0) {
      String id = "MSG_INTERNAL_ERROR";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgNoUniqueId() {
      String id = "MSG_NO_UNIQUE_ID";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInvalidType(String arg0) {
      String id = "MSG_INVALID_TYPE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInvalidSeverity(String arg0) {
      String id = "MSG_INVALID_SEVERITY";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgOpenFailed(String arg0) {
      String id = "MSG_OPEN_FAILED";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String buttonUpdate() {
      String id = "BUTTON_UPDATE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String menuCopy() {
      String id = "MENU_COPY";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String menuCut() {
      String id = "MENU_CUT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String menuPaste() {
      String id = "MENU_PASTE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelCatType() {
      String id = "LABEL_CAT_TYPE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String buttonCreate() {
      String id = "BUTTON_CREATE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String buttonCancel() {
      String id = "BUTTON_CANCEL";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgMustBeFile() {
      String id = "MSG_MUST_BE_FILE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgMustBeXMLFile() {
      String id = "MSG_MUST_BE_XML";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgFileExists() {
      String id = "MSG_FILE_EXISTS";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgEnterPath() {
      String id = "MSG_ENTER_PATH";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgEnterSubsystem() {
      String id = "MSG_ENTER_SUB";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgEnterVersion() {
      String id = "MSG_ENTER_VERSION";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgEnterBaseId() {
      String id = "MSG_ENTER_BASEID";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgEnterEndId() {
      String id = "MSG_ENTER_ENDID";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInvalidServerBase(int arg0) {
      String id = "MSG_INVALID_SERVER_BASE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInvalidNonServerBase(int arg0) {
      String id = "MSG_INVALID_NONSERVER_BASE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInvalidBase() {
      String id = "MSG_INVALID_BASE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgNotIntBase() {
      String id = "MSG_NOT_INT_BASE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgNotIntEnd() {
      String id = "MSG_NOT_INT_END";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgBadEndId() {
      String id = "MSG_BAD_ENDID";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String tagError() {
      String id = "TAG_ERROR";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String tagFatal() {
      String id = "TAG_FATAL";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String tagQuote() {
      String id = "TAG_QUOTE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgDblQuote() {
      String id = "MSG_DBL_QUOTE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String menuFile() {
      String id = "MENU_FILE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String menuNewCatalog() {
      String id = "MENU_NEW_CAT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String menuSave() {
      String id = "MENU_SAVE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String menuExit() {
      String id = "MENU_EXIT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String menuEdit() {
      String id = "MENU_EDIT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String menuSearch() {
      String id = "MENU_SEARCH";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String menuView() {
      String id = "MENU_VIEW";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String menuAllMsgs() {
      String id = "MENU_ALL_MSGS";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String menuOptions() {
      String id = "MENU_OPTIONS";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String menuVarFont() {
      String id = "MENU_VAR_FONT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String menuFixedFont() {
      String id = "MENU_FIXED_FONT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String tipNewCatalog() {
      String id = "TIP_NEW_CAT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String titleId() {
      String id = "TITLE_ID";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String titleMethod() {
      String id = "TITLE_METHOD";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String titleSeverity() {
      String id = "TITLE_SEVERITY";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String titleMethodType() {
      String id = "TITLE_METHOD_TYPE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String titleComment() {
      String id = "TITLE_COMMENT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String titleBody() {
      String id = "TITLE_BODY";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String titleDetail() {
      String id = "TITLE_DETAIL";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String titleAction() {
      String id = "TITLE_ACTION";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String titleCause() {
      String id = "TITLE_CAUSE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String titleRetired() {
      String id = "TITLE_RETIRED";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String titleStatus() {
      String id = "TITLE_STATUS";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String titleMsgViewer() {
      String id = "TITLE_MSG_VIEWER";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgChooseCatToView() {
      String id = "MSG_CHOOSE_CAT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgEmptyCat(String arg0) {
      String id = "MSG_EMPTY_CAT";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelCatName(String arg0) {
      String id = "LABEL_CAT_NAME";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelUpToDate() {
      String id = "LABEL_UPTODATE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelStale() {
      String id = "LABEL_STALE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelNoLocaleMsg() {
      String id = "LABEL_NO_LOCALE_MSG";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String tagExists() {
      String id = "TAG_EXISTS";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelMsgSearch() {
      String id = "LABEL_MSG_SEARCH";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String buttonFindFirst() {
      String id = "BUTTON_FIND_FIRST";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String buttonFindNext() {
      String id = "BUTTON_FIND_NEXT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String tagWarn() {
      String id = "TAG_WARN";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgNoCat() {
      String id = "MSG_NOCAT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String tipSave() {
      String id = "TIP_SAVE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String buttonMasterText() {
      String id = "BUTTON_MASTER_TEXT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String buttonLeaveBlank() {
      String id = "BUTTON_LEAVE_BLANK";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String iconMsgOK() {
      String id = "ICON_MSG_OK";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String iconMsgStale() {
      String id = "ICON_MSG_STALE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String iconNoMsg() {
      String id = "ICON_NO_MSG";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgNotExist(String arg0) {
      String id = "MSG_NOT_EXIST";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String titleL10n() {
      String id = "TITLE_L10N";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelMasterCat() {
      String id = "LABEL_MASTER_CAT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelLocale() {
      String id = "LABEL_LOCALE";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelLocaleCat() {
      String id = "LABEL_LOCALE_CAT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String tipMessageId() {
      String id = "TIP_MSG_ID";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgEndEdit() {
      String id = "MSG_END_EDIT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgChooseMaster() {
      String id = "MSG_CHOOSE_MASTER";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgChooseL10N() {
      String id = "MSG_CHOOSE_L10N";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String tagNoMsg() {
      String id = "TAG_NO_MSG";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgBadWrite(String arg0) {
      String id = "MSG_BAD_WRITE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelNA() {
      String id = "LABEL_NA";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String labelPrefix() {
      String id = "LABEL_PREFIX";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgOutOfRange(String arg0, String arg1) {
      String id = "MSG_OUT_OF_RANGE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
