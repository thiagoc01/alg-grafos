import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

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
			
		}		
	}
	
	private void adicionaVizinhos(Grafo g, String buffer, int id)
	{
		// Irá ignorar o id seguido de " = "
		
		ArrayList<String> vizinhos = new ArrayList<String>(Arrays.asList(buffer.split("[0-9]* = ")));
		
		for (String s : vizinhos)
		{
			if (s.isBlank()) // Não tem vizinhos
				continue;
			
			String[] vertices = s.split(" ");
			
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
					return;
				}
				
				if (!g.vertices.containsKey(vizinho))
				{
					g.adicionaVertice(vizinho);
					g.adicionaAresta(id, vizinho);
				}
				
				else
				{
					if (!g.vertices.get(vizinho).vizinhos.contains(g.vertices.get(id)))
					{
						g.adicionaAresta(id, vizinho);
					}
				}
					
			
			}
		}
		
	}
	

}
