package br.com.lucaskauer.fileutilities.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import br.com.lucaskauer.fileutilities.iservice.IFileService;
import br.com.lucaskauer.fileutilities.specification.HasDirectorySpec;
import br.com.lucaskauer.fileutilities.specification.HasFileSpec;

public class FileService implements IFileService {

	private static IFileService fileService;

	private static final String NEW_FILE_SEPARATOR = "******************************";
	
	private FileService() { }
	
	public static IFileService GetFileService() {
		if (fileService == null) {
			fileService = new FileService(); 
		}
		
		return fileService;
	}
	
	@Override
	public void write(String path, String fileName, String... content) {
		createDirectory(path);
		
		String pathAndName = String.format("%s%s", path, fileName);
		File file = new File(pathAndName);
		createFile(file);
		
		try {
			FileWriter fileWriter = new FileWriter(file);
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
		
		createDirectory(file);
		
		ArrayList<String> lines = new ArrayList<String>();
		try {
			for (File processingFile : file.listFiles()) {
				FileReader fileReader = new FileReader(processingFile);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				while (bufferedReader.ready()) {
					lines.add(bufferedReader.readLine());
				}

				lines.add(NEW_FILE_SEPARATOR);
				
				bufferedReader.close();
				fileReader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		final String text = String.join("\n", lines);
		return text;
	}
	
	private void createDirectory(String path)
	{
		File file = new File(path);
		createDirectory(file);
	}
	
	private void createDirectory(File file) {
		HasDirectorySpec hasDirectory = new HasDirectorySpec();
		if (!hasDirectory.isSatisfactoryFor(file)) {
			file.mkdir();
		}
	}
	
	private void createFile(File file) {
		HasFileSpec hasFile = new HasFileSpec();
		if (!hasFile.isSatisfactoryFor(file)) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
