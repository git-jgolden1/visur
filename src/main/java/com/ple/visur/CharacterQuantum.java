package com.ple.visur;

import java.util.ArrayList;

public class CharacterQuantum extends Quantum {
  EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
  @Override
  public int[] getBoundaries(int ca, ArrayList<Integer> nextLineIndices, int span, boolean includeTail) {
    int lastNewlineIndex = nextLineIndices.get(nextLineIndices.size() - 1);
    if(ca < lastNewlineIndex) {
      return new int[]{ca, ca + 1};
    } else {
      return new int[]{ca, ca};
    }
  }

  @Override
  public int move(String editorContent, ArrayList<Integer> nextLineIndices, MovementVector mv) {
    int realCA = emc.getCA();
    int span = emc.getSpan();
    if(mv.dx != 0) {
      if(mv.dx > 0) {
        realCA = moveRight(nextLineIndices, span);
      } else {
        realCA = moveLeft();
      }
    }
    if(mv.dy != 0) {
      if(mv.dy > 0) {
        realCA = moveDown(editorContent, nextLineIndices, span);
      } else {
        realCA = moveUp(editorContent, nextLineIndices, span);
      }
    }
    return realCA;
  }

  private int moveRight(ArrayList<Integer> nl, int span) {
    int cx = emc.getCX();
    int cy = emc.getCY();
    if(cy >= nl.size() - 1) {
      int previousLineStart = cy > 0 ? nl.get(cy - 1) : 0;
      if(span > 0 ? cx + previousLineStart < nl.get(cy) - 1 : cx + previousLineStart < nl.get(cy)) {
        cx++;
      }
    } else {
      cx++;
    }
    emc.putCX(cx);
    emc.putVirtualCX(cx);
    return emc.getCA();
  }

  private int moveLeft() {
    int cx = emc.getCX();
    int cy = emc.getCY();
    if(cy > 0 || cx > 0) {
      cx--;
    }
    emc.putCX(cx);
    emc.putVirtualCX(cx);
    return emc.getCA();
  }

  private int moveDown(String editorContent, ArrayList<Integer> nextLineIndices, int span) {
    int cx = emc.getCX();
    int cy = emc.getCY();
    int canvasWidth = emc.getCanvasWidth();
    int[] shortBounds = RelativeLineBoundCalculator.getShort(cx, cy, nextLineIndices);
    int vcx = emc.getVirtualCX();
    boolean isOnLastShortLineInLongLine = shortBounds[1] - shortBounds[0] < canvasWidth;
    boolean isAtEndOfEditorContent = cy == nextLineIndices.size() - 1 && isOnLastShortLineInLongLine;
    boolean shouldIncrementCY = isOnLastShortLineInLongLine && !isAtEndOfEditorContent;
    if (shouldIncrementCY) {
      emc.putCY(++cy);
      vcx = vcx % canvasWidth;

    } else if (!isAtEndOfEditorContent) {
      vcx += canvasWidth;
    }
    int longLineLength = RelativeLineBoundCalculator.getLongLineLength(cy, nextLineIndices);
    int longLineLimit = span > 0 ? longLineLength - 1 : longLineLength;
    cx = Math.min(vcx, longLineLimit);
    emc.putCX(cx);
    emc.putVirtualCX(vcx);
    return emc.getCA();
  }

  private int moveUp(String editorContent, ArrayList<Integer> nextLineIndices, int span) {
    int cx = emc.getCX();
    int cy = emc.getCY();
    int canvasWidth = emc.getCanvasWidth();
    int vcx = emc.getVirtualCX();
    boolean isOnFirstLongLine = cy == 0;
    boolean isOnFirstShortLine = cx < canvasWidth;
    boolean shouldDecrementCY = !isOnFirstLongLine && isOnFirstShortLine;
    if(shouldDecrementCY) {
      emc.putCY(--cy);
      int previousLongLineLength = RelativeLineBoundCalculator.getLongLineLength(cy, nextLineIndices);
      int[] shortBoundsOfLastShortInPreviousLine = RelativeLineBoundCalculator.getShort(previousLongLineLength, cy, nextLineIndices);
      vcx = vcx % canvasWidth + shortBoundsOfLastShortInPreviousLine[0];
      cx = Math.min(vcx, previousLongLineLength);
    } else if(!isOnFirstShortLine) {
      vcx -= canvasWidth;
      cx = vcx;
    }
    emc.putCX(cx);
    emc.putVirtualCX(vcx);
    return emc.getCA();
  }

  @Override
  public String getName() {
    return "character";
  }

}
