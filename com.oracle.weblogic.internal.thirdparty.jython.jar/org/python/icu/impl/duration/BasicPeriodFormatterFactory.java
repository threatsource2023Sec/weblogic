package org.python.icu.impl.duration;

import java.util.Locale;
import org.python.icu.impl.duration.impl.PeriodFormatterData;
import org.python.icu.impl.duration.impl.PeriodFormatterDataService;

public class BasicPeriodFormatterFactory implements PeriodFormatterFactory {
   private final PeriodFormatterDataService ds;
   private PeriodFormatterData data;
   private Customizations customizations;
   private boolean customizationsInUse;
   private String localeName;

   BasicPeriodFormatterFactory(PeriodFormatterDataService ds) {
      this.ds = ds;
      this.customizations = new Customizations();
      this.localeName = Locale.getDefault().toString();
   }

   public static BasicPeriodFormatterFactory getDefault() {
      return (BasicPeriodFormatterFactory)BasicPeriodFormatterService.getInstance().newPeriodFormatterFactory();
   }

   public PeriodFormatterFactory setLocale(String localeName) {
      this.data = null;
      this.localeName = localeName;
      return this;
   }

   public PeriodFormatterFactory setDisplayLimit(boolean display) {
      this.updateCustomizations().displayLimit = display;
      return this;
   }

   public boolean getDisplayLimit() {
      return this.customizations.displayLimit;
   }

   public PeriodFormatterFactory setDisplayPastFuture(boolean display) {
      this.updateCustomizations().displayDirection = display;
      return this;
   }

   public boolean getDisplayPastFuture() {
      return this.customizations.displayDirection;
   }

   public PeriodFormatterFactory setSeparatorVariant(int variant) {
      this.updateCustomizations().separatorVariant = (byte)variant;
      return this;
   }

   public int getSeparatorVariant() {
      return this.customizations.separatorVariant;
   }

   public PeriodFormatterFactory setUnitVariant(int variant) {
      this.updateCustomizations().unitVariant = (byte)variant;
      return this;
   }

   public int getUnitVariant() {
      return this.customizations.unitVariant;
   }

   public PeriodFormatterFactory setCountVariant(int variant) {
      this.updateCustomizations().countVariant = (byte)variant;
      return this;
   }

   public int getCountVariant() {
      return this.customizations.countVariant;
   }

   public PeriodFormatter getFormatter() {
      this.customizationsInUse = true;
      return new BasicPeriodFormatter(this, this.localeName, this.getData(), this.customizations);
   }

   private Customizations updateCustomizations() {
      if (this.customizationsInUse) {
         this.customizations = this.customizations.copy();
         this.customizationsInUse = false;
      }

      return this.customizations;
   }

   PeriodFormatterData getData() {
      if (this.data == null) {
         this.data = this.ds.get(this.localeName);
      }

      return this.data;
   }

   PeriodFormatterData getData(String locName) {
      return this.ds.get(locName);
   }

   static class Customizations {
      boolean displayLimit = true;
      boolean displayDirection = true;
      byte separatorVariant = 2;
      byte unitVariant = 0;
      byte countVariant = 0;

      public Customizations copy() {
         Customizations result = new Customizations();
         result.displayLimit = this.displayLimit;
         result.displayDirection = this.displayDirection;
         result.separatorVariant = this.separatorVariant;
         result.unitVariant = this.unitVariant;
         result.countVariant = this.countVariant;
         return result;
      }
   }
}
