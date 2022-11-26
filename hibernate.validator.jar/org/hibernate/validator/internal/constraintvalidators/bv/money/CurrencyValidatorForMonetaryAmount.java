package org.hibernate.validator.internal.constraintvalidators.bv.money;

import java.util.ArrayList;
import java.util.List;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraints.Currency;
import org.hibernate.validator.internal.util.CollectionHelper;

public class CurrencyValidatorForMonetaryAmount implements ConstraintValidator {
   private List acceptedCurrencies;

   public void initialize(Currency currency) {
      List acceptedCurrencies = new ArrayList();
      String[] var3 = currency.value();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String currencyCode = var3[var5];
         acceptedCurrencies.add(Monetary.getCurrency(currencyCode, new String[0]));
      }

      this.acceptedCurrencies = CollectionHelper.toImmutableList(acceptedCurrencies);
   }

   public boolean isValid(MonetaryAmount value, ConstraintValidatorContext context) {
      return value == null ? true : this.acceptedCurrencies.contains(value.getCurrency());
   }
}
