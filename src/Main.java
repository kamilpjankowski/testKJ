import pl.kj.document.DocumentDao;

import java.io.IOException;

public class Main {
	
	public static void main(String[] args) throws IOException {
		DocumentDao documentDao = new DocumentDao();
		ProgrammerService programmerService = new ProgrammerService();
		programmerService.execute(documentDao);

	}
}
