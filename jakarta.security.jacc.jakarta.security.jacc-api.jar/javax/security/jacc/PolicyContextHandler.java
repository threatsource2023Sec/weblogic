package javax.security.jacc;

public interface PolicyContextHandler {
   boolean supports(String var1) throws PolicyContextException;

   String[] getKeys() throws PolicyContextException;

   Object getContext(String var1, Object var2) throws PolicyContextException;
}
