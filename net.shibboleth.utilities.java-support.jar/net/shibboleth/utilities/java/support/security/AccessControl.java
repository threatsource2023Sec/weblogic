package net.shibboleth.utilities.java.support.security;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.ServletRequest;

public interface AccessControl {
   boolean checkAccess(@Nonnull ServletRequest var1, @Nullable String var2, @Nullable String var3);
}
