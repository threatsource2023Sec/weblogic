package javax.faces.event;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PhaseId implements Comparable {
   private final int ordinal;
   private String phaseName;
   private static int nextOrdinal = 0;
   private static final String ANY_PHASE_NAME = "ANY";
   public static final PhaseId ANY_PHASE = new PhaseId("ANY");
   private static final String RESTORE_VIEW_NAME = "RESTORE_VIEW";
   public static final PhaseId RESTORE_VIEW = new PhaseId("RESTORE_VIEW");
   private static final String APPLY_REQUEST_VALUES_NAME = "APPLY_REQUEST_VALUES";
   public static final PhaseId APPLY_REQUEST_VALUES = new PhaseId("APPLY_REQUEST_VALUES");
   private static final String PROCESS_VALIDATIONS_NAME = "PROCESS_VALIDATIONS";
   public static final PhaseId PROCESS_VALIDATIONS = new PhaseId("PROCESS_VALIDATIONS");
   private static final String UPDATE_MODEL_VALUES_NAME = "UPDATE_MODEL_VALUES";
   public static final PhaseId UPDATE_MODEL_VALUES = new PhaseId("UPDATE_MODEL_VALUES");
   private static final String INVOKE_APPLICATION_NAME = "INVOKE_APPLICATION";
   public static final PhaseId INVOKE_APPLICATION = new PhaseId("INVOKE_APPLICATION");
   private static final String RENDER_RESPONSE_NAME = "RENDER_RESPONSE";
   public static final PhaseId RENDER_RESPONSE = new PhaseId("RENDER_RESPONSE");
   private static final PhaseId[] values;
   public static final List VALUES;

   private PhaseId(String newPhaseName) {
      this.ordinal = nextOrdinal++;
      this.phaseName = null;
      this.phaseName = newPhaseName;
   }

   public int compareTo(Object other) {
      return this.ordinal - ((PhaseId)other).ordinal;
   }

   public int getOrdinal() {
      return this.ordinal;
   }

   public String toString() {
      return null == this.phaseName ? String.valueOf(this.ordinal) : this.phaseName + ' ' + this.ordinal;
   }

   static {
      values = new PhaseId[]{ANY_PHASE, RESTORE_VIEW, APPLY_REQUEST_VALUES, PROCESS_VALIDATIONS, UPDATE_MODEL_VALUES, INVOKE_APPLICATION, RENDER_RESPONSE};
      VALUES = Collections.unmodifiableList(Arrays.asList(values));
   }
}
