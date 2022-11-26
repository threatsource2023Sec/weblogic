package weblogic.diagnostics.archive;

import weblogic.management.ManagementException;
import weblogic.management.runtime.WLDFDataRetirementTaskRuntimeMBean;

public class WLDFDataRetirementByAgeTaskImpl extends DataRetirementByAgeTaskImpl implements WLDFDataRetirementTaskRuntimeMBean {
   public WLDFDataRetirementByAgeTaskImpl(EditableDataArchive archive, long timeLimit) throws ManagementException {
      super(archive, timeLimit);
   }
}
