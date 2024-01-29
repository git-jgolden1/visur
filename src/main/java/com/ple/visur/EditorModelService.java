package com.ple.visur;

import io.vertx.rxjava3.core.shareddata.LocalMap;
import io.vertx.rxjava3.core.shareddata.SharedData;

import java.util.HashMap;

import static com.ple.visur.EditorModelKey.*;

public class EditorModelService {

  LocalMap<EditorModelKey, Object> editorModel;
  VariableMap gvm; //globalVariableMap

  private EditorModelService(SharedData sharedData) {
    this.editorModel = sharedData.getLocalMap("editorModel");
    gvm = (VariableMap)editorModel.get(globalVariableMap);
    gvm.put("contentX", new StringVisurVar("0"));
    gvm.put("contentY", new StringVisurVar("0"));
  }

  public static EditorModelService make(SharedData sharedData) {
    return new EditorModelService(sharedData);
  }

  //get contentLines variable using
  public String[] getEditorContentLines() {
    //typecasting is necessary because of the generic Object type of the value
    return (String[])editorModel.get(editorContentLines);
  }

  public int getCurrentContentLineLength() {
    final String[] editorContentLines = getEditorContentLines();
    final String currentContentLine = editorContentLines[getContentY()];
    return currentContentLine.length();
  }

  public String getCurrentContentLine() {
    String[] contentLines = getEditorContentLines();
    int currentContentLineIndex = getContentY();
    return contentLines[currentContentLineIndex];
  }

  public int getVirtualX() {
    return (int)editorModel.get(virtualX);
  }

  public boolean getVirtualXIsAtEndOfLine() {
    return (boolean)editorModel.get(virtualXIsAtEndOfLine);
  }

  public KeysPressed getKeyBuffer() {
    return (KeysPressed)editorModel.get(keyBuffer);
  }

  public int getCanvasWidth() {
    return (int)editorModel.get(canvasWidth);
  }

  public int getCanvasHeight() {
    return (int)editorModel.get(canvasHeight);
  }

  public EditorMode getEditorMode() {
    return (EditorMode)editorModel.get(editorMode);
  }

  public ModeToKeymap getKeymapMap() {
    return (ModeToKeymap)editorModel.get(modeToKeymap);
  }



  public KeysToOperatorHandler[] getKeyToOperatorHandlers(EditorMode mode) {
    return (KeysToOperatorHandler[])editorModel.get(mode);
  }

  public ModeToHandlerArray getModeToHandlerArray() {
    return (ModeToHandlerArray)editorModel.get(modeToHandlerArray);
  }

  public boolean getIsInCommandState() {
    return (boolean)editorModel.get(isInCommandState);
  }

  public String getCommandStateContent() {
    return (String)editorModel.get(commandStateContent);
  }

  public int getCommandCursor() {
    return (int)editorModel.get(commandCursor);
  }

// add/improve getters and setters for:
// Operator, KeyPressed, (not sure: KeysToOperator, OperatorService)

  //only getters, no setters
  public int getCanvasX() {
    int contentX = getContentX();
    int canvasX;
    int currentLineLength = getCurrentContentLineLength();
    int canvasWidth = getCanvasWidth();
    if(contentX == currentLineLength && contentX % canvasWidth == 0 && currentLineLength > 0) {
      canvasX = canvasWidth;
    } else {
      canvasX = contentX % canvasWidth;
    }
    return canvasX;
  }

  public int getCanvasY() {
    int canvasY = 0;
    canvasY += calculateCanvasYBeforeCurrentLine();
    canvasY += calculateCanvasYAtCurrentLine();
    return canvasY;
  }

  private int calculateCanvasYBeforeCurrentLine() {
    int canvasY = 0;
    for(int i = 0; i < getContentY(); i++) {
      String currentIteratedLine = getEditorContentLines()[i];
      canvasY += currentIteratedLine.length() / getCanvasWidth();
      if(currentIteratedLine.length() % getCanvasWidth() != 0 || currentIteratedLine.length() == 0) {
        canvasY++;
      }
    }
    return canvasY;
  }

  private int calculateCanvasYAtCurrentLine() {
    int contentX = getContentX();
    int canvasWidth = getCanvasWidth();
    int canvasY = 0;
    if(contentX != getCurrentContentLineLength() || contentX % canvasWidth != 0) {
      canvasY += contentX / canvasWidth;
    } else if(contentX > canvasWidth) {
      canvasY += contentX / canvasWidth - 1;
    }
    return canvasY;
  }

  public void putEditorContentLines(String[] contentLines) {
    editorModel.put(editorContentLines, contentLines);
  }

  public void putVirtualX(int x) {
    editorModel.put(virtualX, x);
    putVirtualXIsAtEndOfLine(false);
  }

  public void putVirtualXIsAtEndOfLine(boolean isAtEndOfLine) {
    editorModel.put(virtualXIsAtEndOfLine, isAtEndOfLine);
  }

  public void putKeyBuffer(KeysPressed buffer) {
    editorModel.put(keyBuffer, buffer);
  }

  public void putCanvasWidth(int width) {
    editorModel.put(canvasWidth, width);
  }

  public void putCanvasHeight(int height) {
    editorModel.put(canvasHeight, height);
  }

  public void putEditorMode(EditorMode mode) {
    editorModel.put(editorMode, mode);
  }

  public void putKeymapMap(ModeToKeymap keymapMap) {
    editorModel.put(modeToKeymap, keymapMap);
  }

  public void putOperatorToService(OperatorToService opToService) {
    editorModel.put(operatorToService, opToService);
  }

  public void putModeToHandlerArray(ModeToHandlerArray modeToHandlerArrayMap) {
    editorModel.put(modeToHandlerArray, modeToHandlerArrayMap);
  }

  public void putIsInCommandState(boolean inCommandState) {
    editorModel.put(isInCommandState, inCommandState);
  }

  public void putCommandStateContent(String content) {
    editorModel.put(commandStateContent, content);
  }

  public void putCommandCursor(int x) {
    editorModel.put(commandCursor, x);
  }

  public void reportError(String message) {
    System.out.println(message);
  }

}
