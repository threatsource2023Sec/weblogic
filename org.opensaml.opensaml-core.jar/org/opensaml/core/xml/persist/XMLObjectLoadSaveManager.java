package org.opensaml.core.xml.persist;

import java.io.IOException;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import org.opensaml.core.xml.XMLObject;

public interface XMLObjectLoadSaveManager {
   @Nonnull
   @NonnullElements
   @NotLive
   @Unmodifiable
   Set listKeys() throws IOException;

   @Nonnull
   @NonnullElements
   Iterable listAll() throws IOException;

   boolean exists(@Nonnull @NotEmpty String var1) throws IOException;

   @Nullable
   XMLObject load(@Nonnull @NotEmpty String var1) throws IOException;

   void save(@Nonnull @NotEmpty String var1, @Nonnull XMLObject var2) throws IOException;

   void save(@Nonnull @NotEmpty String var1, @Nonnull XMLObject var2, boolean var3) throws IOException;

   boolean remove(@Nonnull @NotEmpty String var1) throws IOException;

   boolean updateKey(@Nonnull @NotEmpty String var1, @Nonnull @NotEmpty String var2) throws IOException;
}
