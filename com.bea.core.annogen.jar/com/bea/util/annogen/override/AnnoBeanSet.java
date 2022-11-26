package com.bea.util.annogen.override;

public interface AnnoBeanSet {
   boolean containsBeanFor(Class var1);

   AnnoBean findOrCreateBeanFor(Class var1);

   AnnoBean removeBeanFor(Class var1);

   AnnoBean[] getAll();

   int size();

   void put(AnnoBean var1);
}
