@start rule: main
/*
 * This code was automatically generated at @time on @date
 * by @generator -- do not edit.
 */
@packageStatement

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.rmi.RemoteException;
import javax.ejb.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

// User imports
@importStatements
public class @simpleClassName
extends
TagSupport
{
  @varDeclaration
  @boolDeclaration
  private static final String HOME_JNDI_NAME = @homeJNDIName ;

  @returnAttribute

  public int doStartTag() throws JspException {
      try {
	  verifyAllAttributesSet();
          @homeType home = getHome();
	  @retDecl home.@invoke ;
	  @retSet
      } catch (RuntimeException e) {
          throw e;
      } catch (Exception e) {
          throw new JspException("error invoking bean: " + e);
      }
      return EVAL_PAGE;
  }

  public @homeType getHome() throws Exception {
       Context ctx = new InitialContext();
	try {
	    Object obj = ctx.lookup(HOME_JNDI_NAME);
	    @homeType home = (@homeType)PortableRemoteObject.narrow(
		obj, @homeType.class);
	    return home;
	} finally {
	    ctx.close();
	}
  }
      
  private void verifyAllAttributesSet() throws Exception {
    @verifyAttributes
  }

  public void release() {
     @releaseBody
  }

  @defaultMethods
  
  /* setter methods for all attributes */
  @setterMethods

}
@end rule: main
