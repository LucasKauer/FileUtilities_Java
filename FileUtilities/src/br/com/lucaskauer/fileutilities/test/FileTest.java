package br.com.lucaskauer.fileutilities.test;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.lucaskauer.fileutilities.iservice.IFileService;
import br.com.lucaskauer.fileutilities.service.FileService;

public class FileTest {
	
	@Test
	public void test() {
		IFileService fileService = FileService.GetFileService();
		
		String path = "/home/lucas/Documentos/Arquivos/Teste", fileName = "Teste.txt", content = "Teste";
		
		fileService.write(path, fileName, content);
		assertEquals(content, fileService.reader(String.format("%s/%s", path, fileName)));
		assertEquals(true, fileService.delete(String.format("%s/%s", path, fileName)));
	}

}
