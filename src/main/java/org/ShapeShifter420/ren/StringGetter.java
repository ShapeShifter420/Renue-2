package org.ShapeShifter420.ren;

import org.ShapeShifter420.ren.Interfaces.IFoundString;
import org.ShapeShifter420.ren.Interfaces.IStringGetter;
import org.ShapeShifter420.ren.dataclasses.FoundString;
import org.ShapeShifter420.ren.dataclasses.RawResult;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class StringGetter implements IStringGetter {
    FileChannel Channel;
    public StringGetter(FileChannel channel){
        Channel = channel;
    }
    public RawResult getString(IFoundString fs) throws IOException {
        if (fs.getEndPos() == 0) return new RawResult(fs.getPreString(), fs.getFindPos() );

        ByteBuffer buff = ByteBuffer.allocate((int)(fs.getEndPos()- fs.getStartPos()));
        Channel.read(buff,fs.getStartPos());
        return new RawResult(buff.array(), fs.getFindPos() );

    }
}
