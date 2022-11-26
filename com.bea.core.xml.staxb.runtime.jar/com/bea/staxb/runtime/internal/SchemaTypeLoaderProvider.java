package com.bea.staxb.runtime.internal;

import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.XmlException;

interface SchemaTypeLoaderProvider {
   SchemaTypeLoader getSchemaTypeLoader() throws XmlException;
}
