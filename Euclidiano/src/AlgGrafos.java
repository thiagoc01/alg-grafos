public class AlgGrafos 
{
	public static void main(String[] args) 
	{
		Grafo grafo = new Grafo();
		
		Arquivo arq = new Arquivo();
		
		arq.leArquivo(grafo, "src\\myfiles\\in.txt");
		
		if (grafo.vertices.isEmpty())
			return;
		
		
		
		else
		{
			grafo.imprimeGrafo();
			if (grafo.eEuleriano())
			{
				System.out.println("Um circuito euleriano é: ");
				grafo.imprimeEuleriano();
			}
			grafo.DFS(3);
			grafo.imprimeGrafo();
		}
		
		

	}

}
