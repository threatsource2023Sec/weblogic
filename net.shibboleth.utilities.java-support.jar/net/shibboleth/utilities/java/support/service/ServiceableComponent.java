package net.shibboleth.utilities.java.support.service;

import javax.annotation.Nonnull;

public interface ServiceableComponent {
   @Nonnull
   Object getComponent();

   void pinComponent();

   void unpinComponent();

   void unloadComponent();
}
