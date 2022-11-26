package org.opensaml.saml.saml2.profile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.saml.common.SAMLException;
import org.opensaml.saml.common.profile.NameIdentifierGenerator;
import org.opensaml.saml.saml2.core.NameID;

public interface SAML2NameIDGenerator extends NameIdentifierGenerator {
   @Nullable
   NameID generate(@Nonnull ProfileRequestContext var1, @Nonnull @NotEmpty String var2) throws SAMLException;
}
