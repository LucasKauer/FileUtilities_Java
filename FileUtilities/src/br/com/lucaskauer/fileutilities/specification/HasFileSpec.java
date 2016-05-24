package br.com.lucaskauer.fileutilities.specification;

import java.io.File;

public class HasFileSpec implements BaseSpecification<File> {

	@Override
	public boolean isSatisfactoryFor(File... files) {
		boolean exists = true;
		
		for (File file : files) {
			if (!isSatisfactoryFor(file)) {
				exists = false;
				break;
			}
		}
		
		return exists;
	}

	@Override
	public boolean isSatisfactoryFor(File file) {
		return file.isFile() && file.exists();
	}

}
