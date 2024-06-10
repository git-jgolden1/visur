package com.ple.visur;

import java.util.Stack;

public class RemoveSubmodeOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    Stack<EditorSubmode> editorSubmodeStack = emc.getEditorSubmodeStack();
    if(editorSubmodeStack.size() > 1) {
      editorSubmodeStack.pop();
      emc.putEditorSubmodeStack(editorSubmodeStack);
    }
  }
}
