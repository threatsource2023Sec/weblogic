package weblogic.ejb.container.interfaces;

public interface EntityBeanQuery {
   String getDescription();

   String getMethodSignature();

   String getMethodName();

   String[] getMethodParams();

   String getQueryText();

   String getResultTypeMapping();
}
