package com.bea.staxb.buildtime.internal.tylar;

import com.bea.staxb.buildtime.internal.bts.BindingFile;
import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.util.jam.JamClassLoader;
import com.bea.xbean.xb.xsdschema.SchemaDocument;
import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.XmlException;
import java.io.IOException;
import java.net.URL;

public interface Tylar {
   BindingFile[] getBindingFiles();

   SchemaDocument[] getSchemas();

   BindingLoader getBindingLoader();

   SchemaTypeLoader getSchemaTypeLoader() throws IOException, XmlException;

   JamClassLoader getJamClassLoader();

   String getDescription();

   URL[] getLocations();

   /** @deprecated */
   @Deprecated
   URL getLocation();

   void setExcludeLocations(URL[] var1);
}
