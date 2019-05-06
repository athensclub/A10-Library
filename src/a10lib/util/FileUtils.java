package a10lib.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A utilities class for file manipulation.
 * 
 * @author Athensclub
 *
 */
public final class FileUtils {

    private FileUtils() {
    }

    /**
     * Crawl the given file and all sub directory of that file and repeat.Applying
     * each file to consumer,except for root file.
     * 
     * @param root
     * @param crawler
     * @throws IOException
     */
    public static void crawl(File root, Consumer<File> crawler) throws IOException {
	if (root.isDirectory()) {
	    for (File f : root.listFiles()) {
		crawler.accept(f);
		if (f.isDirectory()) {
		    crawl(f, crawler);
		}
	    }
	} else {
	    throw new IOException("Can not crawl non-directory file: " + root);
	}
    }

    /**
     * Replace the first occurrence of old string with replacement to the given
     * path.
     * 
     * @param path
     * @param old
     * @param replacement
     * @return
     * @throws IOException
     */
    public static void replaceFirst(File file, String old, String replacement) throws IOException {
	edit(file, str -> Strings.replaceFirst(str, old, replacement));
    }

    /**
     * Replace all occurence of the old string with replace to the given file.
     * 
     * @param file
     * @param old
     * @param replacement
     * @throws IOException
     */
    public static void replaceAll(File file, String old, String replacement) throws IOException {
	edit(file, str -> Strings.replaceAll(str, old, replacement));
    }

    /**
     * Edit the given path using the given function that take the current file
     * content and return the edited file content to be replace to that file.Return
     * true if edit successfully,false otherwise.
     * 
     * @param path
     * @param editor
     * @throws IOException
     */
    public static void edit(File file, Function<StringBuilder, CharSequence> editor) throws IOException {
	if (!file.isDirectory()) {
	    if (file.canRead()) {
		if (file.canWrite()) {
		    StringBuilder b = new StringBuilder();
		    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = null;
			boolean first = true;
			while ((line = br.readLine()) != null) {
			    if (first) {
				first = false;
			    } else {
				b.append('\n');
			    }
			    b.append(line);
			}
		    } catch (IOException e) {
			throw e;
		    }
		    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			bw.append(editor.apply(b));
		    } catch (IOException e) {
			throw e;
		    }
		} else {
		    throw new IOException("Can not edit unwritable file: " + file);
		}
	    } else {
		throw new IOException("Can not edit unreadable file: " + file);
	    }
	} else {
	    throw new IOException("Can not edit directory: " + file);
	}
    }

}
