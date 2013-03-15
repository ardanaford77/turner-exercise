package com.turner.exercise.interfaces;

import java.io.IOException;
import java.io.InputStream;

/**
 * This interface defines content with a bytes payload and a String path,
 * like a file.  An enterprise application might have a similar content
 * interface, but with a lot more fields.
 */
public interface FileContent
{
	/**
	 * @return String the path of the FileContent instance.
	 */
	public String getFilePath();

	/**
	 * @param path String the path of the FileContent instance.
	 * @throws IOException
	 */
	public void setFilePath(String path) throws IOException;

	/**
	 * It is left to the implementation whether it is valid to use the result of this method more than once.
	 * @return InputStream a stream of the complete content.
	 * @throws IOException
	 */
	public InputStream getData() throws IOException;

	/**
	 * It is left to the implementation whether the given InputStream is buffered or not.
	 * @param data InputStream
	 * @throws IOException
	 */
	public void setData(InputStream data) throws IOException;
}
