package oracle.jsp.provider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public interface JspResourceProvider {
   void init(String var1, String var2, String var3, ServletContext var4, HttpServletRequest var5, Hashtable var6) throws IllegalStateException;

   String getRepositoryRoot();

   InputStream fromStream(String var1) throws FileNotFoundException, IOException;

   InputStreamReader fromReader(String var1, String var2) throws FileNotFoundException, IOException, UnsupportedEncodingException;

   OutputStream toStream(String var1) throws FileNotFoundException, IOException;

   PrintWriter toPrintWriter(String var1) throws FileNotFoundException, IOException;

   PrintWriter toPrintWriter(String var1, String var2) throws FileNotFoundException, IOException, UnsupportedEncodingException;

   OutputStreamWriter toWriter(String var1) throws FileNotFoundException, IOException;

   OutputStreamWriter toWriter(String var1, String var2) throws FileNotFoundException, IOException, UnsupportedEncodingException;

   long getLastModified(String var1) throws FileNotFoundException, IOException;

   long getLastModified(String var1, HttpServletRequest var2) throws FileNotFoundException, IOException;

   String[] listClassRepositories(String var1);

   String translateToContextPath(String var1);

   String translateToAbsolutePath(String var1);

   String getAbsolutePath(String var1);

   String getProviderURI(String var1);
}
