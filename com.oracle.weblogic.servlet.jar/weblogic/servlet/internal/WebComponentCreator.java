package weblogic.servlet.internal;

import java.util.EventListener;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import weblogic.management.DeploymentException;

public interface WebComponentCreator extends WebComponentCreatorInternal {
   void initialize(WebAppServletContext var1) throws DeploymentException;

   Servlet createServletInstance(String var1) throws IllegalAccessException, ClassNotFoundException, InstantiationException;

   Filter createFilterInstance(String var1) throws IllegalAccessException, ClassNotFoundException, InstantiationException;

   EventListener createListenerInstance(String var1) throws IllegalAccessException, ClassNotFoundException, InstantiationException;

   Object createInstance(Class var1) throws IllegalAccessException, ClassNotFoundException, InstantiationException, ClassCastException;
}
