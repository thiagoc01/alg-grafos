import java.io.InputStreamReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Arquivo 
{
	public void leArquivo(Grafo g, String nomeArquivo)
	{
		String buffer;
		int idVertice;
		
		try
		{
			FileInputStream f = new FileInputStream(nomeArquivo);
			InputStreamReader is = new InputStreamReader(f);
			BufferedReader leitor = new BufferedReader(is);
			
			while (leitor.ready())
			{
				buffer = leitor.readLine();
				
				try
				{
					idVertice = Integer.parseInt(buffer.split(" = ")[0].trim());
				}
				catch (NumberFormatException e)
				{
					System.out.println("O arquivo contém uma linha que não é do formato id = <vizinhos>");
					g.vertices.clear();
					leitor.close();
					return;
				}
				
				if (!g.vertices.containsKey(idVertice))
					g.adicionaVertice(idVertice);
				
				adicionaVizinhos(g, buffer, idVertice);				
			}
			
			leitor.close();
			
			
		}
		
		catch (FileNotFoundException e)
		{
			System.out.println("Erro de abertura do arquivo.");
			return;
			
		}
		
		catch(IOException e)
		{
			System.out.println("Erro de leitura do arquivo.");
			return;
		}		
	}
	
	private void adicionaVizinhos(Grafo g, String buffer, int id)
	{
		// Irá ignorar o id seguido de " = "
		// Se a entrada estiver correta, devemos ter vizinhos = {"", <vizinhos>}
		
		String[] vizinhos = buffer.split("[0-9]* = ");

		if (vizinhos.length == 0) // Não tem vizinhos
			return;
			
		String[] vertices = vizinhos[1].trim().split(" ");
			
		for (String v : vertices)
		{
			int vizinho;
				
			try
			{
				vizinho = Integer.parseInt(v);
			}
				
			catch (NumberFormatException e)
			{
				System.out.println("O arquivo contém uma linha que não é do formato id = <vizinhos>");
				g.vertices.clear();
				System.exit(255);
				return;
			}
				
			if (!g.vertices.containsKey(vizinho))
				g.adicionaVertice(vizinho);
			
			g.vertices.get(id).adicionaVizinho(g.vertices.get(vizinho));

			if (vizinho == id)
				g.vertices.get(id).adicionaVizinho(g.vertices.get(id));	// Um laço é uma aresta, mas contribui em 2 no grau de um vértice.
		}
		
	}
	

}
