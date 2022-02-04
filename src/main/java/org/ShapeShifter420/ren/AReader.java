package org.ShapeShifter420.ren;

import org.ShapeShifter420.ren.Interfaces.IAutomate;
import org.ShapeShifter420.ren.Interfaces.IReader;
import org.ShapeShifter420.ren.Interfaces.IFoundString;
import org.ShapeShifter420.ren.dataclasses.FoundString;
import org.ShapeShifter420.ren.enums.ReturnSignalsEnum;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AReader implements IReader {
    FileChannel fileChannel; String word; int column;
    public AReader(FileChannel fileChannel, String word, int column){
        this.fileChannel = fileChannel;
        this.word = word;
        this.column = column;
    }
    public List<IFoundString> run() {
        List<IFoundString> result = new ArrayList<>();
        int bufferSize = 4096;
        try {
            if (bufferSize > fileChannel.size()) {
                bufferSize = (int) fileChannel.size();
            }
        ByteBuffer buff = ByteBuffer.allocate(bufferSize);
        IAutomate automate = new FinderAutomate(word.getBytes(StandardCharsets.UTF_8),column);
        byte[] buffarray;
        long offset = 0;
        ReturnSignalsEnum value;
        long startline;
        long endfind = -1;
        long endline = 0;
        while (fileChannel.read(buff) > 0) {
            buffarray = buff.array();
            for(int pos=0;pos < bufferSize;pos++ )
            {
                value = automate.read(buffarray[pos]);
                if (value == ReturnSignalsEnum.NewLineSignal) {
                    startline = endline;
                    endline = pos + offset;

                    if (endfind != -1){
                        result.add(new FoundString(startline,(int)(endfind-startline-word.length()),pos+offset));
                        endfind = -1;
                    }

                }
                else if (value == ReturnSignalsEnum.FoundSignal){
                    endfind = pos+offset;
                }
            }
            offset += buff.position();
            buff.clear();
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }
}
