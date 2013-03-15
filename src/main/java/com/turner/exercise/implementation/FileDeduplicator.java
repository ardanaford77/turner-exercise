package com.turner.exercise.implementation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.io.IOUtils;
import com.turner.exercise.interfaces.Deduplicator;
import com.turner.exercise.interfaces.FileContent;

/**
 *
 * @author Ardana Ford
 */
public class FileDeduplicator implements Deduplicator {   
    @Override
    public boolean isDuplicate(FileContent fc) throws IOException {
        if (cache.isEmpty() || !cache.containsKey(fc.getFilePath())) {
            cache.put(fc.getFilePath(), fc);
            return false;
        }        
        
        //retrieve cached content from map
        FileContent cc = (FileContent) cache.get(fc.getFilePath());
        
        //compare content
        if (Arrays.equals(IOUtils.toByteArray(cc.getData()), IOUtils.toByteArray(fc.getData())))
            return true;
        else
            return false;
    }
}
