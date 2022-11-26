package org.opensaml.core.xml;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;

public interface XMLObjectBuilder {
   @Nonnull
   XMLObject buildObject(@Nonnull QName var1);

   @Nonnull
   XMLObject buildObject(@Nonnull QName var1, @Nullable QName var2);

   @Nonnull
   XMLObject buildObject(@Nullable String var1, @Nonnull String var2, @Nullable String var3);

   @Nonnull
   XMLObject buildObject(@Nullable String var1, @Nonnull String var2, @Nullable String var3, @Nullable QName var4);

   @Nonnull
   XMLObject buildObject(@Nonnull Element var1);
}
