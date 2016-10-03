package br.com.vt.mapek.bundles.resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class RandomGenerator {
	public static final String FILENAME = "src/main/resources/" + "input.csv";
	public static final int FILESIZE_77656_KB = 10000000; // 77,6 MB
	public static final int FILESIZE_6788_KB = 1000000; // 6,7 MB
	public static final int FILESIZE_584_KB = 100000; // 584 KB
	public static final int FILESIZE_56_KB = 10000; // 56 KB
	public static final int FILESIZE_3_KB = 1000; // 3,8 KB
	
	public static int SIZE = FILESIZE_3_KB;
	
	
	public static void main(String[] args) throws IOException {
		FileService fileService = new FileService(new LoggerService());
		
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<SIZE;i++) {
            list.add(i);
        }
        
        Collections.shuffle(list);
        
        
        OutputStream out = fileService.getOutputStream(FILENAME);
        PrintWriter writer = new PrintWriter(out);
        StringBuffer buffer= new StringBuffer();
        for (Iterator<Integer>it = list.iterator();it.hasNext();){
        	
        	buffer.append(it.next());
        	if (it.hasNext()){
        		buffer.append(",");
        	}
        	
        }
        writer.print(buffer.toString());
        writer.close();
    }
	
	
	
}
