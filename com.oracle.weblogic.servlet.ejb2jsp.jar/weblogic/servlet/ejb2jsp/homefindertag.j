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
TagSupport implements @homeTagType
{
  @varDeclaration
  @boolDeclaration
  @remoteType theEJB;    
  private static final String HOME_JNDI_NAME = @homeJNDIName ;

  @returnAttribute

  public int doStartTag() throws JspException {
      verifyAllAttributesSet();
      try {
          @homeType home = getHome();
	  theEJB = home.@invoke ;
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
      
  public @remoteType getEJBFromHome() throws Exception {
      return theEJB;
  }

  private void verifyAllAttributesSet() throws JspException {
    @verifyAttributes
  }

  public void release() {
     @releaseBody
  }

  
  /* setter methods for all attributes */
  @setterMethods

}
@end rule: main
