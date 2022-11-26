package org.opensaml.saml.common.assertion;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public enum ValidationResult {
   VALID,
   INDETERMINATE,
   INVALID;
}
