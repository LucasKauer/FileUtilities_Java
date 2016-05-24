import br.com.lucaskauer.fileutilities.iservice.IFileService;
import br.com.lucaskauer.fileutilities.service.FileService;

public class Main {

	public static void main(String[] args) {
		IFileService fileService = FileService.GetFileService();
		
		fileService.write("/home/lucas/Documentos/Arquivos/", "Arquivo-Luks-2.txt" , "Testando escrita");
		String test = fileService.reader("/home/lucas/Documentos/Arquivos/");
	}

}
