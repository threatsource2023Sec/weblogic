package org.opensaml.saml.common.profile;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;

public interface FormatSpecificNameIdentifierGenerator extends NameIdentifierGenerator {
   @Nonnull
   @NotEmpty
   String getFormat();
}
