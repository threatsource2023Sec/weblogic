package weblogic.diagnostics.accessor;

public interface EditableAccessorConfiguration extends AccessorConfiguration {
   boolean isParticipantInSizeBasedDataRetirement();

   boolean isAgeBasedDataRetirementEnabled();

   int getRetirementTime();

   int getRetirementPeriod();

   int getRetirementAge();
}
