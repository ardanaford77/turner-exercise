package com.turner.exercise.interfaces;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A Deduplicator instance will remember the last content that it saw for each path and
 * tell whether each subsequent FileContent instance it sees is a duplicate or not for that path.
 */
public interface Deduplicator
{
    //In-Memory Cache Structure
    public static Map<String,Object> cache = new HashMap<String,Object>();
    
    public boolean isDuplicate(FileContent fileContent) throws IOException;
}
