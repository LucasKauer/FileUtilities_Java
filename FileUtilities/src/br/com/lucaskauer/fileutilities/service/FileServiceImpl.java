package br.com.lucaskauer.fileutilities.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import br.com.lucaskauer.fileutilities.domain.Ref;
import br.com.lucaskauer.fileutilities.iservice.IFileService;
import br.com.lucaskauer.fileutilities.specification.HasDirectorySpec;
import br.com.lucaskauer.fileutilities.specification.HasFileSpec;

public class FileService implements IFileService {

	private static IFileService fileService;

	private static final String NEW_FILE_SEPARATOR = "******************************";
	
	final private HasDirectorySpec hasDirectory = new HasDirectorySpec();
	final private HasFileSpec hasFile = new HasFileSpec();
	
	private FileService() { }
	
	public static IFileService GetFileService() {
		if (fileService == null) {
			fileService = new FileService(); 
		}
		
		return fileService;
	}
	
	@Override
	public void write(String path, String fileName, String... content) {
		Ref<File> refFile = new Ref<File>(new File(path));
		
		createDirectory(refFile.get());
		
		combinePaths(refFile, fileName);
		
		createFile(refFile.get());
		
		try {
			FileWriter fileWriter = new FileWriter(refFile.get());
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			for (String string : content) {
				bufferedWriter.write(string);
				bufferedWriter.newLine();
			}
			
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	public String reader(String path) {
		File file = new File(path);
		
		if (!canReader(file)) {
			return "";
		}
		
		ArrayList<String> lines = new ArrayList<>();
		try {
			lines = getFilesLine(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		final String text = String.join("\n", lines);
		return text;
	}
	
	@Override
	public boolean delete(String path) {
		File file = new File(path);
		return file.delete();
	}
	
	private void combinePaths(Ref<File> path, String fileName)
	{
		File file = new File(path.get(), fileName);
	    path.set(file);
	}
	
	private boolean canReader(File file) {
		return hasDirectory(file) || hasFile(file);
	}
	
	private void createDirectory(File file) {
		if (!hasDirectory(file)) {
			file.mkdir();
		}
	}
	
	private boolean hasDirectory(File file)
	{
		return hasDirectory.isSatisfactoryFor(file);
	}
	
	private void createFile(File file) {
		if (!hasFile(file)) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean hasFile(File file)
	{
		return hasFile.isSatisfactoryFor(file);
	}
	
	private ArrayList<String> getFilesLine(File file) throws Exception {
		ArrayList<String> lines = new ArrayList<>();
		
		if (file.isFile()) {
			readerFile(file, lines);
		} else if (file.isDirectory()) {
			for (File processingFile : file.listFiles()) {
				readerFile(processingFile, lines);
				lines.add(NEW_FILE_SEPARATOR);
			}
		}
		return lines;
	}
	
	private void readerFile(File file, ArrayList<String> lines) throws Exception {
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		while (bufferedReader.ready()) {
			lines.add(bufferedReader.readLine());
		}
		
		bufferedReader.close();
		fileReader.close();
	}
}
