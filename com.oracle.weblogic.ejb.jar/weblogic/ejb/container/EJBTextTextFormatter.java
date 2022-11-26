package weblogic.ejb.container;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class EJBTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public EJBTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.ejb.container.EJBTextTextLocalizer", EJBTextTextFormatter.class.getClassLoader());
   }

   public EJBTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.ejb.container.EJBTextTextLocalizer", EJBTextTextFormatter.class.getClassLoader());
   }

   public static EJBTextTextFormatter getInstance() {
      return new EJBTextTextFormatter();
   }

   public static EJBTextTextFormatter getInstance(Locale l) {
      return new EJBTextTextFormatter(l);
   }

   public String finderNotFoundMessage(String arg0) {
      String id = "finderNotFoundMessage";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String whileTryingToProcess(String arg0) {
      String id = "whileTryingToProcess";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidIdInExpression(String arg0, String arg1) {
      String id = "invalidIdInExpression";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidOp(String arg0, String arg1) {
      String id = "invalidOp";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String couldNotParse(String arg0, String arg1) {
      String id = "couldNotParse";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String warning(String arg0) {
      String id = "warning";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorEncountered(String arg0) {
      String id = "errorEncountered";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String nullName(String arg0) {
      String id = "nullName";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String emptyName(String arg0) {
      String id = "emptyName";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidNamePrefix(String arg0) {
      String id = "invalidNamePrefix";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String nullQuery(String arg0) {
      String id = "nullQuery";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidExpressionNumber(String arg0) {
      String id = "invalidExpressionNumber";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String emptyExpressionText(String arg0) {
      String id = "emptyExpressionText";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String emptyExpressionType(String arg0) {
      String id = "emptyExpressionType";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidQuery(String arg0) {
      String id = "invalidQuery";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noLocalResource(String arg0) {
      String id = "noLocalResource";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String twoInstalledSameIds() {
      String id = "twoInstalledSameIds";
      String subsystem = "EJB Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String firstLoadedFrom(String arg0) {
      String id = "firstLoadedFrom";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String secondLoadedFrom(String arg0) {
      String id = "secondLoadedFrom";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String hasErrors(String arg0) {
      String id = "hasErrors";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_PUBLIC_ID(String arg0) {
      String id = "INVALID_PUBLIC_ID";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String USE_VALID_ID(String arg0) {
      String id = "USE_VALID_ID";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ejbDeploymentError(String arg0, String arg1) {
      String id = "ejbDeploymentError";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cmpGeneratedBeanClassNotFound(String arg0) {
      String id = "cmpGeneratedBeanClassNotFound";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String expressionNullTerm(String arg0, String arg1) {
      String id = "expressionNullTerm";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String idExpectedToBeCmpField(String arg0) {
      String id = "idExpectedToBeCmpField";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String idExpectedToBeCmpField2(String arg0) {
      String id = "idExpectedToBeCmpField2";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String finderSelectTargetNoRVD(String arg0) {
      String id = "finderSelectTargetNoRVD";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String finderFunctionArgWrongType(String arg0, String arg1) {
      String id = "finderFunctionArgWrongType";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String finderExpectedListOfIDs(String arg0, String arg1) {
      String id = "finderExpectedListOfIDs";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String finderRemoteNotNullOnWrongType() {
      String id = "finderRemoteNotNullOnWrongType";
      String subsystem = "EJB Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String couldNotFindRDBMSFinder(String arg0, String arg1, String[] arg2) {
      String id = "couldNotFindRDBMSFinder";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String tableName() {
      String id = "tableName";
      String subsystem = "EJB Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String refPathExpression() {
      String id = "refPathExpression";
      String subsystem = "EJB Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String caseOperatorUsedOnNonStringField(String arg0, String arg1) {
      String id = "caseOperatorUsedOnNonStringField";
      String subsystem = "EJB Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
