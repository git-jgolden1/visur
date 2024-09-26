package com.ple.visur;

import CursorPositionDC.EditorContentService;
import io.vertx.rxjava3.core.shareddata.LocalMap;
import io.vertx.rxjava3.core.shareddata.SharedData;

import java.util.ArrayList;
import java.util.Stack;

import static com.ple.visur.EditorModelKey.*;

public class EditorModelCoupler {

  LocalMap<EditorModelKey, Object> editorModel;
  private EditorContentService ecs = ServiceHolder.editorContentService;

  private EditorModelCoupler(SharedData sharedData) {
    this.editorModel = sharedData.getLocalMap("editorModel");
  }

  public static EditorModelCoupler make(SharedData sharedData) {
    return new EditorModelCoupler(sharedData);
  }

  public String getStrToMatch(boolean searchForwards, int startingPoint) {
    String editorContent = getEditorContent();
    String strToMatch = "";
    if(searchForwards && startingPoint < editorContent.length()) {
      strToMatch = editorContent.substring(startingPoint, startingPoint + 1);
    } else if(startingPoint > 0) {
      strToMatch = editorContent.substring(startingPoint - 1, startingPoint);
    }
    return strToMatch;
  }

  public String getEditorContent() {
    return ecs.getEditorContent(editorModel);
  }

  public VisurVar getGlobalVar(String varName) {
    return ecs.getGlobalVar(varName, editorModel);
  }

  public VariableMap getGlobalVarMap() {
    return ecs.getGlobalVarMap(editorModel);
  }

  public LineWrapping getLineWrapping() {
    return (LineWrapping) editorModel.get(lineWrapping);
  }

  public int getVirtualCX() {
    return (int)editorModel.get(virtualCX);
  }

  public int getCA() {
    BrickVisurVar caBVV = (BrickVisurVar) getGlobalVar("ca");
    return (int)caBVV.getVal();
  }

  public int getCX() {
    BrickVisurVar cxBVV = (BrickVisurVar) getGlobalVar("cx");
    return (int)cxBVV.getVal();
  }

  public int getCY() {
    BrickVisurVar cyBVV = (BrickVisurVar) getGlobalVar("cy");
    return (int)cyBVV.getVal();
  }

  public ArrayList<Integer> getNextLineIndices() {
    return ecs.getNextLineIndices(editorModel);
  }

  public int getCanvasWidth() {
    return ecs.getCanvasWidth(editorModel);
  }

  public int getCanvasHeight() {
    return ecs.getCanvasHeight(editorModel);
  }

  public int getCanvasStart() {
    return (int)editorModel.get(canvasStart);
  }

  public int getFY() {
    return (int)editorModel.get(fy);
  }

  public int getCanvasEnd() {
    int canvasHeight = getCanvasHeight() - 1; //because last line on actual canvas isn't used for content
    int canvasWidth = getCanvasWidth();
    int rowsAdded = 0;
    int canvasEnd = getCanvasStart();
    int fy = getFY();
    ArrayList<Integer> nl = getNextLineIndices();
    while(rowsAdded < canvasHeight && fy < nl.size()) {
      int nextLineStart = nl.get(fy);
      if(canvasEnd + canvasWidth < nextLineStart) {
        canvasEnd += canvasWidth;
      } else {
        canvasEnd = fy < nl.size() - 1 ? nextLineStart - 1 : nextLineStart;
        fy++;
      }
      rowsAdded++;
    }
    return canvasEnd;
  }

  public EditorMode getEditorMode() {
    return (EditorMode)editorModel.get(editorMode);
  }

  public EditorSubmode getEditorSubmode() {
    Stack<EditorSubmode> submodeStack = getEditorSubmodeStack();
    return submodeStack.peek();
  }

  public Stack<EditorSubmode> getEditorSubmodeStack() {
    return (Stack<EditorSubmode>) editorModel.get(editorSubmodeStack);
  }

  public boolean getPreviousSearchDirectionWasForward() {
    return (boolean) editorModel.get(previousSearchDirectionWasForward);
  }

  public KeymapMap getKeymapMap() {
    return (KeymapMap) editorModel.get(modeToKeymap);
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

  public ExecutionDataStack getExecutionDataStack() {
    return (ExecutionDataStack)editorModel.get(executionData);
  }

  public QuantumNameToQuantum getQuantumNameToQuantum() {
    return (QuantumNameToQuantum)editorModel.get(quantumNameToQuantum);
  }

  public KeyToQuantumName getKeyToQuantumName() {
    return (KeyToQuantumName) editorModel.get(keyToQuantumName);
  }

  public Quantum getCursorQuantum() {
    return (Quantum) getGlobalVar("cursorQuantum").getVal();
  }

  public int getCursorQuantumStart() {
    return (int)editorModel.get(quantumStart);
  }
  public int getCursorQuantumEnd() {
    return (int)editorModel.get(quantumEnd);
  }

  public boolean getIsAtQuantumStart() {
    return (boolean)editorModel.get(isAtQuantumStart);
  }

  public Quantum getScopeQuantum() {
    return (Quantum) getGlobalVar("scopeQuantum").getVal();
  }

  public int getSpan() {
    ObjectVisurVar spanVV = (ObjectVisurVar) getGlobalVar("span");
    return (int)spanVV.getVal();
  }

  public void initializeEditorContent(String content) {
    ecs.initializeEditorContent(content, editorModel);
  }

  public void putEditorContent(String content) {
    ecs.putEditorContent(content, editorModel);
  }

  public void putGlobalVar(String globalVarName, VisurVar globalVarValue) {
    ecs.putGlobalVar(globalVarName, globalVarValue, editorModel);
  }

  public void putLineWrapping(LineWrapping lw) {
    editorModel.put(lineWrapping, lw);
  }

  public void putVirtualCX(int vcx) {
    editorModel.put(virtualCX, vcx);
  }

  public void putCA(int ca) {
    BrickVisurVar caBVV = (BrickVisurVar) getGlobalVar("ca");
    caBVV.putVal(ca);
    putGlobalVar("ca", caBVV);
  }

  public void putCX(int cx) {
    BrickVisurVar cxBVV = (BrickVisurVar) getGlobalVar("cx");
    cxBVV.putVal(cx);
    putGlobalVar("cx", cxBVV);
  }

  public void putCY(int cy) {
    BrickVisurVar cyBVV = (BrickVisurVar) getGlobalVar("cy");
    cyBVV.putVal(cy);
    putGlobalVar("cy", cyBVV);
  }

  public void updateNextLineIndices() {
    ecs.updateNextLineIndices(editorModel);
  }

  public void putCanvasWidth(int width) {
    ecs.putCanvasWidth(width, editorModel);
  }

  public void putCanvasHeight(int height) {
    ecs.putCanvasHeight(height, editorModel);
  }

  public void putCanvasStart(int cs) {
    editorModel.put(canvasStart, cs);
  }

  public void putFY(int focusY) {
    editorModel.put(fy, focusY);
  }

  public void putEditorMode(EditorMode mode) {
    editorModel.put(editorMode, mode);
  }

  public void putEditorSubmode(EditorSubmode submode) {
    Stack<EditorSubmode> editorSubmodeStack = getEditorSubmodeStack();
    editorSubmodeStack.push(submode);
    putEditorSubmodeStack(editorSubmodeStack);
  }

  public void putEditorSubmodeStack(Stack<EditorSubmode> submodeStack) {
    editorModel.put(editorSubmodeStack, submodeStack);
  }

  public void putPreviousSearchTarget(String pst) {
    ObjectVisurVar ovv = ObjectVisurVar.make(pst);
    putGlobalVar("previousSearchTarget", ovv);
  }

  public void putPreviousSearchDirectionWasForward(boolean prevSearchWasForward) {
    editorModel.put(previousSearchDirectionWasForward, prevSearchWasForward);
  }

  public void putKeymapMap(KeymapMap keymapMap) {
    editorModel.put(modeToKeymap, keymapMap);
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

  public void putOnExecutionDataStack(Object element) {
    ExecutionDataStack eds = (ExecutionDataStack) editorModel.get(executionData);
    eds.push(element);
    putExecutionDataStack(eds);
  }

  public void putExecutionDataStack(ExecutionDataStack stack) {
    editorModel.put(executionData, stack);
  }

  public void putQuantumNameToQuantum(QuantumNameToQuantum qNameToQuantum) {
    editorModel.put(quantumNameToQuantum, qNameToQuantum);
  }

  public void putKeyToQuantumName(KeyToQuantumName keyToQName) {
    editorModel.put(keyToQuantumName, keyToQName);
  }

  public void putCursorQuantum(Quantum cq) {
    ObjectVisurVar cursorQuantumVV = ObjectVisurVar.make(cq);
    putGlobalVar("cursorQuantum", cursorQuantumVV);
  }

  private void putCursorQuantumStart(int startBound) {
    editorModel.put(quantumStart, startBound);
  }

  public void putCursorQuantumStartAndScroll(int startBound) {
    putCursorQuantumStart(startBound);
    while (getCanvasStart() > startBound) {
      decrementCanvasStart();
    }
  }

  void putCursorQuantumEnd(int endBound) {
    editorModel.put(quantumEnd, endBound);
  }

  public void putCursorQuantumEndAndScroll(int endBound) {
    putCursorQuantumEnd(endBound);
    int startBound = getCursorQuantumStart();
    String cursorQuantumName = getCursorQuantum().getName();
    int limit = cursorQuantumName.equals("character") ? startBound : endBound;
    while (getCanvasEnd() < limit) {
      incrementCanvasStart();
    }
  }

  public void putIsAtQuantumStart(boolean isAtQStart) {
    editorModel.put(isAtQuantumStart, isAtQStart);
  }

  public void putScopeQuantum(Quantum q) {
    ObjectVisurVar scopeQuantumVV = ObjectVisurVar.make(q);
    putGlobalVar("scopeQuantum", scopeQuantumVV);
  }

  public void putSpan(int s) {
    ObjectVisurVar spanVV = ObjectVisurVar.make(s);
    putGlobalVar("span", spanVV);
  }

  public void incrementCanvasStart() {
    int canvasStart = getCanvasStart();
    int canvasWidth = getCanvasWidth();
    int fy = getFY();
    ArrayList<Integer> nl = getNextLineIndices();
    int nextLineStart = nl.get(fy);
    if(canvasStart + canvasWidth < nextLineStart) {
      canvasStart += canvasWidth;
    } else {
      canvasStart = nextLineStart;
      putFY(++fy);
    }
    putCanvasStart(canvasStart);
  }

  public void decrementCanvasStart() {
    int canvasStart = getCanvasStart();
    ArrayList<Integer> nl = getNextLineIndices();
    int fy = getFY();
    fy = Math.min(fy, nl.size() - 1);
    int currentLineStart = fy > 0 ? nl.get(fy - 1) : 0;
    if(nl.get(fy) >= canvasStart) {
      int canvasWidth = getCanvasWidth();
      if (canvasStart > currentLineStart) {
        canvasStart -= canvasWidth;
      } else {
        putFY(--fy);
        int prevLineStart = fy > 0 ? nl.get(fy - 1) : 0;
        int prevLineLength = currentLineStart - prevLineStart - 1;
        if (prevLineLength - 1 <= canvasWidth) {
          canvasStart = prevLineStart;
        } else {
          canvasStart = currentLineStart - (prevLineLength % canvasWidth) - 1;
        }
      }
      putCanvasStart(canvasStart);
    } else {
      putCanvasStart(currentLineStart);
      putFY(fy);
    }
  }

  public void reportError(String message) {
    System.out.println(message);
  }

}
