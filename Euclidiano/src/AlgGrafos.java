import java.io.File;

public class AlgGrafos 
{
	public static void main(String[] args) 
	{
		Grafo grafo = new Grafo();
		
		Arquivo arq = new Arquivo();
		
		arq.leArquivo(grafo, "myfiles" + File.separator + "grafo01.txt");
		
		if (grafo.vertices.isEmpty())
			return;
		
		if (grafo.eEuleriano())
		{
			System.out.println("Um possível circuito euleriano para esse grafo é: ");
			grafo.imprimeEuleriano();
		}

		else
			System.out.println("O grafo de entrada não possui circuito euleriano.");
	}

}
