package br.com.lucaskauer.fileutilities.iservice;

public interface IFileService {
	
	void write(String path, String fileName, String... content);
	
	String reader(String path);
}
