package org.ShapeShifter420.ren.dataclasses;

import org.ShapeShifter420.ren.Interfaces.IFoundString;

public class FoundString implements IFoundString {
    private final long StartPos;
    private final int FindPos;
    private final long EndPos;
    private final byte[] PreString;
    public FoundString(long startpos, int findpos, long endpos){
        StartPos = startpos;
        FindPos = findpos;
        EndPos = endpos;
        PreString = null;
    }
    public FoundString(int findpos, byte[] preString){
        StartPos = 0;
        FindPos = findpos;
        EndPos = 0;
        PreString = preString;
    }

    public int getFindPos() {
        return FindPos;
    }

    public long getStartPos() {
        return StartPos;
    }

    public long getEndPos() {
        return EndPos;
    }

    public byte[] getPreString() {
        return PreString;
    }


}
