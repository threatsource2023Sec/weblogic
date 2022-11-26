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
          @remoteType ejb = getEJB();
          @retDecl ejb.@invoke ;
	  @retSet
          @evalOut
      } catch (RuntimeException e) {
          throw e;
      } catch (Exception e) {
          throw new JspException("error invoking bean: " + e);
      }
      return EVAL_PAGE;
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

       /* the getEJB() method */
     @getEJB
}
@end rule: main

@start rule: entityBeanGetEJB
  private @remoteType getEJB() throws Exception {
       @remoteType ret = null;
       Tag p = null;
       while ((p = getParent()) != null) {
	   if (p instanceof @homeTagInterfaceName) {
	       ret = ((@homeTagInterfaceName)p).getEJBFromHome();
	   }
	   return ret;
       }
       throw new JspException("this tag must be enclosed in a _home tag for this EJB type");
}
@end rule: entityBeanGetEJB

@start rule: sessionBeanGetEJB
  private @remoteType getEJB() throws Exception {
       @remoteType ret = null;
       Context ctx = new InitialContext();
	try {
	    Object obj = ctx.lookup(HOME_JNDI_NAME);
	    @homeType home = (@homeType)PortableRemoteObject.narrow(
		obj, @homeType.class);
	    ret = home.@createInvoke ;
	    return ret;
	} finally {
	    ctx.close();
	}
  }
@end rule: sessionBeanGetEJB
