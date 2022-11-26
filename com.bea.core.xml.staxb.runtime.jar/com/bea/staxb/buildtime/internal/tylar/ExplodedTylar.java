package com.bea.staxb.buildtime.internal.tylar;

import java.io.File;
import java.io.IOException;

public interface ExplodedTylar extends Tylar {
   File getRootDir();

   File getSourceDir();

   File getClassDir();

   File getSchemaDir();

   Tylar toJar(File var1) throws IOException;
}
