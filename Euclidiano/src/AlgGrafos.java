public class AlgGrafos 
{
	public static void main(String[] args) 
	{
		Grafo grafo = new Grafo();
		
		grafo.adicionaVertice(1);
		grafo.adicionaVertice(2);
		grafo.adicionaVertice(3);
		grafo.adicionaVertice(4);
		grafo.adicionaVertice(5);
		grafo.adicionaVertice(6);
		grafo.adicionaVertice(7);
		
		grafo.adicionaAresta(1, 2);
		grafo.adicionaAresta(1, 6);
		grafo.adicionaAresta(2, 3);
		grafo.adicionaAresta(2, 6);
		grafo.adicionaAresta(2, 7);
		grafo.adicionaAresta(3, 4);
		grafo.adicionaAresta(4, 5);
		grafo.adicionaAresta(5, 6);
		grafo.adicionaAresta(6, 7);
		
		
		grafo.verificaConexidade();
		grafo.imprimeGrafo();
		
		if (grafo.eEuleriano())
		{
			System.out.println("O circuito euleriano é: ");
			grafo.imprimeEuleriano();
		}
		else
			System.out.println("Não é euleriano.");
		
		

	}

}
