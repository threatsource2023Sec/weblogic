package org.hibernate.validator.cfg.defs;

import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.constraints.time.DurationMax;

public class DurationMaxDef extends ConstraintDef {
   public DurationMaxDef() {
      super(DurationMax.class);
   }

   public DurationMaxDef days(long days) {
      this.addParameter("days", days);
      return this;
   }

   public DurationMaxDef hours(long hours) {
      this.addParameter("hours", hours);
      return this;
   }

   public DurationMaxDef minutes(long minutes) {
      this.addParameter("minutes", minutes);
      return this;
   }

   public DurationMaxDef seconds(long seconds) {
      this.addParameter("seconds", seconds);
      return this;
   }

   public DurationMaxDef millis(long millis) {
      this.addParameter("millis", millis);
      return this;
   }

   public DurationMaxDef nanos(long nanos) {
      this.addParameter("nanos", nanos);
      return this;
   }

   public DurationMaxDef inclusive(boolean inclusive) {
      this.addParameter("inclusive", inclusive);
      return this;
   }
}
