package weblogic.descriptor.conflict;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class BeangenDescriptorTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public BeangenDescriptorTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.descriptor.conflict.BeangenDescriptorTextLocalizer", BeangenDescriptorTextFormatter.class.getClassLoader());
   }

   public BeangenDescriptorTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.descriptor.conflict.BeangenDescriptorTextLocalizer", BeangenDescriptorTextFormatter.class.getClassLoader());
   }

   public static BeangenDescriptorTextFormatter getInstance() {
      return new BeangenDescriptorTextFormatter();
   }

   public static BeangenDescriptorTextFormatter getInstance(Locale l) {
      return new BeangenDescriptorTextFormatter(l);
   }

   public String getAddSameBeanDiffConflictString(String arg0, String arg1) {
      String id = "AddSameBeanDiffConflictString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAddSameBeanDiffConflictResolve(String arg0, String arg1) {
      String id = "AddSameBeanDiffConflictResolveString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditRemovedBeanDiffConflictString(String arg0) {
      String id = "EditRemovedBeanDiffConflictString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRemovedParent(String arg0) {
      String id = "RemovedParentString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditRemovedBeanDiffConflictResolve(String arg0) {
      String id = "EditRemovedBeanDiffConflictResolveString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExceptionDiffConflictString(String arg0, Throwable arg1) {
      String id = "ExceptionDiffConflictString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExceptionDiffConflictResolve() {
      String id = "ExceptionDiffConflictResolveString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPropertyValueChangeDiffConflictString(String arg0, String arg1, String arg2, String arg3, String arg4) {
      String id = "PropertyValueChangeDiffConflictString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPropertyValueChangeDiffConflictResolve(String arg0, String arg1) {
      String id = "PropertyValueChangeDiffConflictResolveString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getReferenceToRemovedDiffConflictString(String arg0, String arg1, String arg2) {
      String id = "ReferenceToRemovedDiffConflictString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getReferenceToRemovedDiffConflictResolve(String arg0, String arg1, String arg2) {
      String id = "ReferenceToRemovedDiffConflictResolveString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRemovedEditedBeanDiffConflictString(String arg0) {
      String id = "RemovedEditedBeanDiffConflictString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRemovedEditedBeanDiffConflictResolve(String arg0) {
      String id = "RemovedEditedBeanDiffConflictResolveString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoConflicts() {
      String id = "NoConflictsString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOneConflict() {
      String id = "OneConflictString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNConflicts(int arg0) {
      String id = "NConflictsString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConflictFullMessage(int arg0, String arg1, String arg2) {
      String id = "ConflictFullMessageString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoDiff() {
      String id = "NoDiffString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getChangeDiff(String arg0) {
      String id = "ChangeDiffString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAddDiff(String arg0) {
      String id = "AddDiffString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDestroyDiff(String arg0) {
      String id = "DestroyDiffString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRemoveReferenceChange(String arg0, String arg1, String arg2) {
      String id = "RemoveReferenceChangeString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getReferenceToRemoveInAddedDiffConflictResolve(String arg0, String arg1, String arg2) {
      String id = "ReferenceToRemoveInAddedDiffConflictResolveString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getReferenceToRemoveInAddedDiffConflictStringA(String arg0, String arg1, String arg2) {
      String id = "ReferenceToRemoveInAddedDiffConflictStringA";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getReferenceToRemoveInAddedDiffConflictStringB(String arg0, String arg1, String arg2) {
      String id = "ReferenceToRemoveInAddedDiffConflictStringB";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRemoveEditedExternalTreeDiffConflictString(String arg0) {
      String id = "RemoveEditedExternalTreeDiffConflictString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRemoveEditedExternalTreeDiffConflictResolve(String arg0) {
      String id = "RemoveEditedExternalTreeDiffConflictResolveString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditRemovedExternalTreeDiffConflictString(String arg0) {
      String id = "EditRemovedExternalTreeDiffConflictString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditRemovedExternalTreeDiffConflictResolve(String arg0) {
      String id = "EditRemovedExternalTreeDiffConflictResolveString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAddSameExternalTreeDiffConflictString(String arg0) {
      String id = "AddSameExternalTreeDiffConflictString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAddSameExternalTreeDiffConflictResolve() {
      String id = "AddSameExternalTreeDiffConflictResolveString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckInEnumString(String arg0, String arg1, String arg2) {
      String id = "CheckInEnumString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVoidCheckInRangeString(String arg0, String arg1, String arg2, String arg3) {
      String id = "CheckInRangeString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVoidCheckNonNullString(String arg0) {
      String id = "CheckNonNullString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVoidCheckNonEmptyStringString(String arg0) {
      String id = "CheckNonEmptyString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVoidCheckIsSetStringString(String arg0) {
      String id = "CheckIsSetString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
