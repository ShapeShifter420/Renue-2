package org.ShapeShifter420.ren.dataclasses;

public class RawResult implements Comparable<RawResult>{
    final byte[] rawString;
    final int pos;
    public RawResult(byte[] rawString,int pos){
        this.rawString = rawString;
        this.pos = pos;
    }

    @Override
    public String toString() {
        return new String(rawString);
    }

    @Override
    public int compareTo(RawResult o) {
        int offset = 0;
        int minlen = Math.min(o.rawString.length,rawString.length);
        while(rawString[pos+offset] == o.rawString[o.pos+offset] && rawString[pos+offset] != ',')
        {
            offset+=1;
            if(pos+offset == minlen || o.pos + offset == minlen) return 0;
        }
        return rawString[pos+offset] - o.rawString[o.pos+offset];
    }
}
