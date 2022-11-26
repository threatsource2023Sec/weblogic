package weblogic.management.scripting.utils;

import java.util.ResourceBundle;

public class CommandDescription {
   String command = null;
   int commandId = 0;
   String syntax = null;
   String description = null;
   String example = null;
   String commandUse = null;
   String argumentDescription = null;
   String infoMessage = null;
   int genericType = 0;
   boolean expose = false;
   boolean common = false;
   boolean isOffline = false;
   boolean isOnline = false;
   String shortDescription = "";
   ResourceBundle resourceBundle = null;
   String descriptionHelpString = null;
   String syntaxHelpString = null;
   String exampleHelpString = null;

   public CommandDescription() {
   }

   public CommandDescription(String command, String syntax, String description, String example, int type, String message) {
      this.command = command;
      this.syntax = syntax;
      this.description = description;
      this.example = example;
      this.genericType = type;
      this.infoMessage = message;
   }

   public CommandDescription(String command, String description) {
      this.command = command;
      this.description = description;
   }

   public CommandDescription(String command, String description, String shortDescription) {
      this.command = command;
      this.description = description;
      this.shortDescription = shortDescription;
   }

   public void setShortDescription(String sDesc) {
      this.shortDescription = sDesc;
   }

   public String getShortDescription() {
      return this.shortDescription;
   }

   public String getCommand() {
      return this.command;
   }

   public void setCommand(String cmd) {
      this.command = cmd;
   }

   public int getCommandId() {
      return this.commandId;
   }

   public void setCommandId(int id) {
      this.commandId = id;
   }

   public String getSyntax() {
      return this.syntax;
   }

   public void setSyntax(String syn) {
      this.syntax = syn;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String descs) {
      this.description = descs;
   }

   public boolean isExpose() {
      return this.expose;
   }

   public void setExpose(boolean bool) {
      this.expose = bool;
   }

   public boolean isCommon() {
      return this.common;
   }

   public void setCommon(boolean bool) {
      this.common = bool;
   }

   public String getExample() {
      return this.example;
   }

   public void setExample(String ex) {
      this.example = ex;
   }

   public String getCommandUse() {
      return this.commandUse;
   }

   public void setCommandUse(String cmdUse) {
      this.commandUse = cmdUse;
   }

   public String getArgumentDescription() {
      return this.argumentDescription;
   }

   public void setArgumentDescription(String argDesc) {
      this.argumentDescription = argDesc;
   }

   public int getGenericType() {
      return this.genericType;
   }

   public void setGenericType(int gtype) {
      this.genericType = gtype;
   }

   public String getInfoMessage() {
      return this.infoMessage;
   }

   public void setInfoMessage(String infoMsg) {
      this.infoMessage = infoMsg;
   }

   public void setOffline(boolean bool) {
      this.isOffline = bool;
   }

   public void setOnline(boolean bool) {
      this.isOnline = bool;
   }

   public boolean isOnline() {
      return this.isOnline;
   }

   public boolean isOffline() {
      return this.isOffline;
   }

   public void setResourceBundle(ResourceBundle resourceBundle) {
      this.resourceBundle = resourceBundle;
   }

   public ResourceBundle getResourceBundle() {
      return this.resourceBundle;
   }

   public String getDescriptionHelpString() {
      return this.descriptionHelpString;
   }

   public void setDescriptionHelpString(String descriptionHelpString) {
      this.descriptionHelpString = descriptionHelpString;
   }

   public String getSyntaxHelpString() {
      return this.syntaxHelpString;
   }

   public void setSyntaxHelpString(String syntaxHelpString) {
      this.syntaxHelpString = syntaxHelpString;
   }

   public String getExampleHelpString() {
      return this.exampleHelpString;
   }

   public void setExampleHelpString(String exampleHelpString) {
      this.exampleHelpString = exampleHelpString;
   }
}
