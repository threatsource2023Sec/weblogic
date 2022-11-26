package weblogic.management.rest.lib.bean.utils;

import org.codehaus.jettison.json.JSONObject;
import weblogic.management.rest.lib.utils.ConfigurationTransaction;

public class BeanConfigurationTransaction extends ConfigurationTransaction {
   public static ConfigurationTransaction.Result doTransaction(InvocationContext ic, JSONObject model, ConfigurationTransaction.TransactionContents contents, boolean transactional, boolean saveChanges) throws Exception {
      return doTransaction(ic, model, contents, transactional, saveChanges, false);
   }

   public static ConfigurationTransaction.Result doTransaction(InvocationContext ic, JSONObject model, ConfigurationTransaction.TransactionContents contents, boolean transactional, boolean saveChanges, boolean warnOnPropertyExceptions) throws Exception {
      return doTransaction(new BeanTransactionHelper(ic), model, contents, transactional, saveChanges, warnOnPropertyExceptions);
   }
}
