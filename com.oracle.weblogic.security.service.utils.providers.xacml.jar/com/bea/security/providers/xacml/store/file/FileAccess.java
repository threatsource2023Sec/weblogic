package com.bea.security.providers.xacml.store.file;

import java.io.InputStream;
import java.io.OutputStream;

interface FileAccess {
   String getPolicyFileNamePrefix(boolean var1);

   String getPolicyFileNameSuffix();

   String getIndexFileName();

   boolean isAcceptableName(String var1);

   InputStream filterRead(InputStream var1);

   OutputStream filterWrite(OutputStream var1);
}
