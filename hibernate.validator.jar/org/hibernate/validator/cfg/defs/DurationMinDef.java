package org.hibernate.validator.cfg.defs;

import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.constraints.time.DurationMin;

public class DurationMinDef extends ConstraintDef {
   public DurationMinDef() {
      super(DurationMin.class);
   }

   public DurationMinDef days(long days) {
      this.addParameter("days", days);
      return this;
   }

   public DurationMinDef hours(long hours) {
      this.addParameter("hours", hours);
      return this;
   }

   public DurationMinDef minutes(long minutes) {
      this.addParameter("minutes", minutes);
      return this;
   }

   public DurationMinDef seconds(long seconds) {
      this.addParameter("seconds", seconds);
      return this;
   }

   public DurationMinDef millis(long millis) {
      this.addParameter("millis", millis);
      return this;
   }

   public DurationMinDef nanos(long nanos) {
      this.addParameter("nanos", nanos);
      return this;
   }

   public DurationMinDef inclusive(boolean inclusive) {
      this.addParameter("inclusive", inclusive);
      return this;
   }
}
