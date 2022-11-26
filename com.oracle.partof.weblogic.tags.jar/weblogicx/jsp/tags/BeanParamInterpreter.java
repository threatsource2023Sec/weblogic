package weblogicx.jsp.tags;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

public interface BeanParamInterpreter {
   void set(ServletRequest var1, HttpSession var2) throws Exception;

   Object get(String var1, HttpSession var2) throws Exception;

   void processNew(ServletRequest var1, HttpSession var2) throws Exception;

   void processDelete(ServletRequest var1, HttpSession var2) throws Exception;

   void processShift(ServletRequest var1, HttpSession var2) throws Exception;

   void processAll(ServletRequest var1, HttpSession var2) throws Exception;
}
