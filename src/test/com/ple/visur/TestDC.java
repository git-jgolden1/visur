package com.ple.visur;

import CursorPositionDC.*;
import DataClass.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class TestDC {
  CursorPositionDCHolder cursorPositionDCHolder = CursorPositionDCHolder.make();
  CompoundDataClassBrick cursorPositionDCB;
  PrimitiveDataClassBrick niDCB;
  CompoundDataClassBrick cxcycaDCB;
  PrimitiveDataClassBrick caDCB;
  CompoundDataClassBrick cxcyDCB;
  PrimitiveDataClassBrick cxDCB;
  PrimitiveDataClassBrick cyDCB;


  @BeforeEach
  void setupDCBs() {
    cursorPositionDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick();
    ArrayList<Integer> newlineIndices = new ArrayList<>();
    newlineIndices.add(11);
    newlineIndices.add(24);
    niDCB = (PrimitiveDataClassBrick) cursorPositionDCB.getInner("ni");
    cxcycaDCB = (CompoundDataClassBrick) cursorPositionDCB.getInner("cxcyca");
    cxcyDCB = (CompoundDataClassBrick) cxcycaDCB.getInner("cxcy");
    cxDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cx");
    cyDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cy");
    caDCB = (PrimitiveDataClassBrick) cxcycaDCB.getInner("ca");

    Result r = niDCB.putSafe(newlineIndices);
    assertNull(r.getError());

    assertFalse(cursorPositionDCB.isComplete());
  }

  @Test void pdcbPutSafe() {
    //1 = cxcy can be set when ca is unset
    int cx = 4;
    int cy = 0;
    Result r = cxDCB.putSafe(cx);
    assertNull(r.getError());
    r = cyDCB.putSafe(cy);
    assertNull(r.getError());
    assertEquals(cx, cxDCB.get().getVal());
    assertEquals(cy, cyDCB.get().getVal());

    //2 = ni can be set always (because cursorPositionDC.minimumRequiredSetValues == 2)
    ArrayList<Integer> newlineIndices = new ArrayList<>();
    newlineIndices.add(11);
    newlineIndices.add(24);
    r = niDCB.putSafe(newlineIndices);
    assertNull(r.getError());
    assertEquals(newlineIndices, niDCB.get().getVal());

    //3 = ca can be set when cxcy is set and no conflicts exist
    int ca = 4;
    r = caDCB.putSafe(ca);
    assertNull(r.getError());
    assertEquals(ca, caDCB.get().getVal());

    //4 = ca CAN'T be set when cxcy is set and conflicts DO exist
    int previousCA = ca;
    ca = 14;
    r = caDCB.putSafe(ca);
    assertNotNull(r.getError());
    assertEquals(previousCA, caDCB.get().getVal());

    //5 = cxcy can be set when ca is set and no conflicts exist
    cxDCB.remove();
    cyDCB.remove();
    caDCB.putSafe(ca);
    cx = 2;
    cy = 1;
    r = cxDCB.putSafe(cx);
    assertNull(r.getError());
    assertTrue(cxDCB.isComplete());
    assertFalse(cyDCB.isComplete());
    assertFalse(cxcyDCB.isComplete());
    assertTrue(caDCB.isComplete());
    r = cyDCB.putSafe(cy);
    assertNull(r.getError());
    assertEquals(cx, cxDCB.get().getVal());
    assertEquals(cy, cyDCB.get().getVal());

    //6 = cxcy CAN'T be set when ca is set and conflicts DO exist
    int previousCX = cx;
    int previousCY = cy;
    cx = 1;
    cy = 0;
    r = cxDCB.putSafe(cx);
    assertNotNull(r.getError());
    r = cyDCB.putSafe(cy);
    assertNotNull(r.getError());
    assertEquals(previousCX, cxDCB.get().getVal());
    assertEquals(previousCY, cyDCB.get().getVal());

    //7 = ca can be set when cxcy is unset
    cxDCB.remove();
    cyDCB.remove();
    ca = 30;
    r = caDCB.putSafe(ca);
    assertNull(r.getError());
    assertEquals(ca, caDCB.get().getVal());

  }

  @Test void pdcbPutForce() {
    //1 = cxcy can be set when ca is unset
    int cx = 10;
    int cy = 0;
    cxDCB.putForce(cx);
    cyDCB.putForce(cy);
    assertEquals(cx, cxDCB.get().getVal());
    assertEquals(cy, cyDCB.get().getVal());

    //2 = ni can be set always (because cursorPositionDC.minimumRequiredSetValues == 2)
    ArrayList<Integer> newlineIndices = new ArrayList<>();
    newlineIndices.add(11);
    newlineIndices.add(24);
    niDCB.putForce(newlineIndices);
    assertEquals(newlineIndices, niDCB.get().getVal());

    //3 = ca can be set when cxcy is set and no conflicts exist
    int ca = 10;
    caDCB.putForce(ca);
    assertEquals(ca, caDCB.get().getVal());

    //4 = ca CAN be set when cxcy is set and conflicts DO exist, but cxcy needs to be UNSET
    ca = 14;
    caDCB.putForce(ca);
    assertFalse(cxcyDCB.isComplete());
    assertFalse(cxDCB.isComplete());
    assertFalse(cyDCB.isComplete());
    assertEquals(14, caDCB.get().getVal());

    //5 = cxcy can be set when ca is set and no conflicts exist
    cxDCB.remove();
    cyDCB.remove();
    caDCB.putForce(ca);
    cx = 2;
    cy = 1;
    cxDCB.putForce(cx);
    assertTrue(caDCB.isComplete());
    cyDCB.putForce(cy);
    assertEquals(cx, cxDCB.get().getVal());
    assertEquals(cy, cyDCB.get().getVal());
    assertTrue(caDCB.isComplete());

    //6 = cxcy CAN be set when ca is set and conflicts DO exist, but ca needs to be UNSET
    cx = 1;
    cy = 0;
    cxDCB.putForce(cx);
    assertTrue(cxcyDCB.isComplete());
    cyDCB.putForce(cy);
    assertTrue(cxcyDCB.isComplete());
    assertEquals(cx, cxDCB.get().getVal());
    assertEquals(cy, cyDCB.get().getVal());
    assertFalse(caDCB.isComplete());

    //7 = ca can be set when cxcy is unset
    cxDCB.remove();
    cyDCB.remove();
    ca = 30;
    caDCB.putForce(ca);
    assertEquals(ca, caDCB.get().getVal());

  }

  @Test void dcbGetOrCalc() {
    //1 = caDCB.getOrCalc when caDCB is set to 0
    int ca = 0;
    caDCB.putForce(ca);
    Result r = caDCB.getOrCalc();
    caDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(ca, caDCB.getVal());

    assertEquals(caDCB, cxDCB.getOuter().getOuter().getInner("ca"));

    //2 = caDCB.getOrCalc when cxcyDCB is set to (5, 0) [should be 5]
    int cx = 5;
    int cy = 0;

    assertTrue(caDCB.isComplete());
    cxDCB.putForce(cx);
    cyDCB.putForce(cy);
    assertTrue(cxcyDCB.isComplete());
    assertFalse(caDCB.isComplete());
    r = caDCB.getOrCalc();
    assertNull(r.getError());
    caDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(5, caDCB.get().getVal());

    //3 = cxcyDCB.getOrCalc when cxcyDCB is set to (5, 0)
    r = cxDCB.getOrCalc();
    assertNull(r.getError());
    cxDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(5, cxDCB.get().getVal());
    r = cyDCB.getOrCalc();
    assertNull(r.getError());
    cyDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(0, cyDCB.get().getVal());

    //4 = cxcyDCB.getOrCalc when caDCB is set to 12 [should be (0, 1)]
    caDCB.putForce(12);
    r = cxDCB.getOrCalc();
    assertNull(r.getError());
    cxDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(0, cxDCB.get().getVal());
    r = cyDCB.getOrCalc();
    assertNull(r.getError());
    cyDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(1, cyDCB.get().getVal());

    //5 = cxcyDCB.getOrCalc when caDCB is set to 13 [should be (1, 1)]
    caDCB.putForce(13);
    assertFalse(cxcyDCB.isComplete());
    r = cxDCB.getOrCalc();
    assertNull(r.getError());
    cxDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(1, cxDCB.get().getVal());
    r = cyDCB.getOrCalc();
    assertNull(r.getError());
    cyDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(1, cyDCB.get().getVal());

    //6 = cxcyDCB.getOrCalc when caDCB is set to 22 [should be (10, 1)]
    assertTrue(cxcyDCB.isComplete());
    caDCB.putForce(22);
    assertFalse(cxcyDCB.isComplete());
    r = cxDCB.getOrCalc();
    assertNull(r.getError());
    cxDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(10, cxDCB.get().getVal());
    r = cyDCB.getOrCalc();
    assertNull(r.getError());
    cyDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(1, cyDCB.get().getVal());

    //7 = caDCB.getOrCalc when cxcyDCB is set to (4, 2) [should be 29]
    cxDCB.putForce(4);
    cyDCB.putForce(2);
    assertTrue(cxcyDCB.isComplete());
    assertFalse(caDCB.isComplete());
    r = caDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(29, caDCB.get().getVal());

    //8 = cxcyDCB.getOrCalc when caDCB is set to 10 [should be (10, 0)]
    caDCB.putForce(10);
    assertTrue(caDCB.isComplete());
    assertFalse(cxDCB.isComplete());
    assertFalse(cyDCB.isComplete());
    assertFalse(cxcyDCB.isComplete());
    r = cxDCB.getOrCalc();
    assertNull(r.getError());
    r = cyDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(10, cxDCB.get().getVal());
    assertEquals(0, cyDCB.get().getVal());

    //9 = caDCB.getOrCalc when all values are unset [should be an error]
    r = cxcyDCB.remove();
    assertNull(r.getError());
    r = caDCB.remove();
    assertNull(r.getError());
    r = caDCB.getOrCalc();
    assertNotNull(r.getError());
    assertNull(r.getVal());

    //10 = cxcyDCB.getOrCalc when all values are unset [should be an error]
    r = cxDCB.getOrCalc();
    assertNotNull(r.getError());
    assertNull(r.getVal());
    r = cyDCB.getOrCalc();
    assertNotNull(r.getError());
    assertNull(r.getVal());

  }

}
