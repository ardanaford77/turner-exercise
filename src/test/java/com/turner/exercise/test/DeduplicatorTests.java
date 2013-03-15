package com.turner.exercise.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.turner.exercise.implementation.FileDeduplicator;
import com.turner.exercise.interfaces.Deduplicator;
import com.turner.exercise.interfaces.FileContent;

/**
 * This class contains JUnit test cases for Deduplicator implementations.  Make sure your submission
 * passes all the tests here.  Feel free to add new ones.  Other tests not found here will also be
 * run against your submission, so make sure you understand the requirements!
 */
public class DeduplicatorTests {
	private class FileContentBean implements FileContent
	{
		private String path = null;
		public String getFilePath() {return path;}
		public void setFilePath(String filePath) {path = filePath;}

		private byte[] content = null;
		public InputStream getData() {return new ByteArrayInputStream(content);}
		public void setData(InputStream data) throws IOException
		{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			os.write(data);
			content = os.toByteArray();
			os.close();
		}

		public FileContentBean(String filePath, byte[] data) {
			path = filePath;
			content = data;
		}
	}

	private Deduplicator deduper = null;

	@Before
	public void setup() {            
		Map<String, Deduplicator> beans = new ClassPathXmlApplicationContext("test-spring-context.xml").getBeansOfType(Deduplicator.class);
		if(beans.isEmpty())
			throw new RuntimeException("no deduplication beans found");
		else if (beans.size() > 1)
			throw new RuntimeException("can't decide which bean to use for testing");
		else
			deduper = beans.values().iterator().next();
	}

	@Test
	public void testDifferentContentSamePath() throws IOException {
		FileContent fc1 = new FileContentBean("/abc/abc", new byte[] {0, 1, 2, 3});
		FileContent fc2 = new FileContentBean("/abc/abc", new byte[] {0, 1, 4, 3});  // only one bit difference
		Assert.assertFalse(deduper.isDuplicate(fc1));
		Assert.assertFalse(deduper.isDuplicate(fc2));
	}

	@Test
	public void testSameContentDifferentPath() throws IOException {
		FileContent fc1 = new FileContentBean("/abc/def", new byte[] {0, 1, 2, 3});
		FileContent fc2 = new FileContentBean("/abc/def2", new byte[] {0, 1, 2, 3});		
		Assert.assertFalse(deduper.isDuplicate(fc1));
		Assert.assertFalse(deduper.isDuplicate(fc2));
	}

	@Test
	public void testSameContentSamePath() throws IOException {
		FileContent fc1 = new FileContentBean("/abc/xyz", new byte[] {0, 1, 2, 3});
		FileContent fc2 = new FileContentBean("/abc/xyz", new byte[] {0, 1, 2, 3});
		Assert.assertFalse(deduper.isDuplicate(fc1));
		Assert.assertTrue(deduper.isDuplicate(fc2));
	}
}
