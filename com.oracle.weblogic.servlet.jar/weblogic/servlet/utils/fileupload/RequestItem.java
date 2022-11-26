package weblogic.servlet.utils.fileupload;

import java.io.IOException;
import java.io.InputStream;

interface RequestItem {
   InputStream openStream() throws IOException;

   void close() throws IOException;

   String getContentType();

   String getName();

   String getFieldName();

   boolean isFormField();

   PartHeaders getHeaders();
}
