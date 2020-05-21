package extraction;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class ZipExtraction {
	/**
	 * Extracts file from zip file to target directory
	 *
	 * @param file The project relative path to the .zip file
	 * @param outputDir The project relative path to the output folder
	 * @throws IOException If the file does not exist
	 */

	public static void Extract(String file, String outputDir) throws IOException {
		ZipFile zipFile = new ZipFile(file);

		// Generator holding the files
		Enumeration<? extends ZipEntry> entries = zipFile.entries();

		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			File entryDestination = new File(outputDir, entry.getName());

			if (entry.isDirectory()) {
				entryDestination.mkdirs();
			} else {
				entryDestination.getParentFile().mkdirs();

				// Writes the file to disk
				InputStream in = zipFile.getInputStream(entry);
				OutputStream out = new FileOutputStream(entryDestination);
				IOUtils.copy(in, out);
			}
		}
	}
}
