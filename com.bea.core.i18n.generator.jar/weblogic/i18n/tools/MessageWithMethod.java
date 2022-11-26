package weblogic.i18n.tools;

public interface MessageWithMethod {
   void setMethod(String var1);

   String getMethod();

   String getMethodName();

   String[] getArguments();

   String[] getArgNames();
}
