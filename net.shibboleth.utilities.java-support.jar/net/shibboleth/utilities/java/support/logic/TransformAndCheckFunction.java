package net.shibboleth.utilities.java.support.logic;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class TransformAndCheckFunction implements Function {
   private final Function preprocessor;
   private final Predicate constraint;
   private final boolean failOnConstraintViolation;

   public TransformAndCheckFunction(@Nonnull Function inputPreprocessor, @Nonnull Predicate inputConstraint, boolean failOnInputConstraintViolation) {
      this.preprocessor = (Function)Constraint.isNotNull(inputPreprocessor, "Input preprocessor can not be null");
      this.constraint = (Predicate)Constraint.isNotNull(inputConstraint, "Input constraint can not be null");
      this.failOnConstraintViolation = failOnInputConstraintViolation;
   }

   public Optional apply(Object input) {
      Object processedValue = this.preprocessor.apply(input);
      boolean meetsCriteria = this.constraint.apply(processedValue);
      if (meetsCriteria) {
         return Optional.of(processedValue);
      } else if (this.failOnConstraintViolation) {
         throw new IllegalArgumentException(input + " does not meet constraint");
      } else {
         return Optional.absent();
      }
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (obj == this) {
         return true;
      } else if (!(obj instanceof TransformAndCheckFunction)) {
         return false;
      } else {
         TransformAndCheckFunction other = (TransformAndCheckFunction)obj;
         return Objects.equals(this.preprocessor, other.preprocessor) && Objects.equals(this.constraint, other.constraint) && Objects.equals(this.failOnConstraintViolation, other.failOnConstraintViolation);
      }
   }

   public int hashCode() {
      return com.google.common.base.Objects.hashCode(new Object[]{this.preprocessor, this.constraint, this.failOnConstraintViolation});
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).add("preprocessor", this.preprocessor).add("constraint", this.constraint).add("failOnConstraintViolation", this.failOnConstraintViolation).toString();
   }
}
