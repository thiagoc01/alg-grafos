import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;


public class Grafo 
{
	private HashMap<Integer, Vertice> vertices;
	private Vertice raizAtual;
	
	Grafo ()
	{
		vertices = new HashMap<Integer, Vertice>();
		raizAtual = null;
	}
	
	/* Construtor de cópia para funções de análise */
	
	private Grafo(Grafo g)
	{
		vertices = new HashMap<Integer, Vertice>(g.vertices);
		raizAtual = new Vertice(g.raizAtual);
	}
	
	public void imprimeGrafo()
	{
		if (raizAtual == null)
			System.out.println("Nenhum vértice é uma raiz.\n");
		
		else
			System.out.println("A raiz atual é: " + raizAtual.id + "\n");
		
		if (vertices != null)
			for (Vertice v : vertices.values())
			{
				v.imprimeVertice();
			}
	}
	
	public void adicionaVertice(int id)
	{
		if (id <= 0 || vertices.containsKey(id))
			System.out.println("ID inválido ou em uso por outro vértice.");

		else
		{
			Vertice v = new Vertice(id);
			vertices.put(id, v);
			
			for (Vertice u : vertices.values())
				u.reset();
		}
	}
	
	public void deletaVertice(int id)
	{
		Vertice d = vertices.get(id);
		
		if (d == null)
		{
			System.out.println("Vértice não existe.");
			return;
		}
		
		for (Vertice v : vertices.values())
			v.vizinhos.remove(id);
		
		if (d == raizAtual)
			raizAtual = null;
		
		vertices.remove(id);
		
		for (Vertice v: vertices.values())
			v.reset();
	}
	
	public void adicionaAresta(int id1, int id2)
	{
		Vertice v = vertices.get(id1);
		Vertice w = vertices.get(id2);
		
		if (v == null || w == null)
		{
			System.out.println("Pelo menos um dos vértices não existe.");
			return;
		}
		
		v.adicionaVizinho(w);
		w.adicionaVizinho(v);
		
		for (Vertice u : vertices.values())
		{
			u.reset();
		}
	}
	
	
	
	public void removeAresta(int id1, int id2)
	{
		Vertice v = vertices.get(id1);
		Vertice u = vertices.get(id2);
		
		if (v == null|| u == null)
		{
			System.out.println("Um dos vértices não existe.");
			return;
		}
		
		v.vizinhos.remove(id2);
		u.vizinhos.remove(id1);
		
		for (Vertice w : vertices.values())
		{
			w.reset();
		}
	}
	
	public void DFS(int id)
	{
		for (Vertice v : vertices.values())
			v.reset();
		
		Vertice v = vertices.get(id); // Vértice de partida da busca
		
		if (v == null)
		{
			System.out.println("Vértice não existe. Impossível realizar busca.");
			return;
		}
		
		v.visitado = true; // Marca o vértice
		
		raizAtual = v; // Marca a raiz atual do grafo
		
		visitadorDFS(v);
	}
	
	public void visitadorDFS(Vertice v)
	{
		for (Vertice u : v.vizinhos.values()) // Percorre os vizinhos do vértice
		{
			if (u.visitado == false) // Vértice não marcado
			{
				u.visitado = true;
				visitadorDFS(u); // A recursão faz com que a busca seja de profundidade.
			}
		}
		
	}
	
	public boolean verificaConexidade()
	{
		if (vertices == null || vertices.isEmpty())
			return false;
		
		int idVerticeInicial;
		
		if (raizAtual == null) // Se verdadeiro, não houve busca no grafo.
		{
			Iterator<Map.Entry<Integer, Vertice>> i = vertices.entrySet().iterator();
			idVerticeInicial = i.next().getKey(); // Retorna o primeiro vértice da lista.
		}
		else
			idVerticeInicial = raizAtual.id; // Começa da raiz atual.
		
		DFS(idVerticeInicial);
		
		for (Vertice v : vertices.values())
			if (v.visitado == false)
				return false; // Se o vértice não foi encontrado na busca, o grafo é desconexo.
		
		return true;
	}
	
	
	
	public boolean eEuleriano()
	{
		if (vertices == null || vertices.isEmpty())
			return false;
		
		Grafo h = new Grafo(this);
		int grauMaximo = -1;
		
		for (Vertice v : h.vertices.values())
		{
			if (v.retornaGrauVertice() % 2 == 1) 
				return false; // Grafo com vértice de grau ímpar
			
			if (grauMaximo < v.retornaGrauVertice())
				grauMaximo = v.retornaGrauVertice();
			
			if (v.retornaGrauVertice() == 0)
				h.deletaVertice(v.id); // Vértices de grau 0 não atrapalham o circuito. Podemos remover.
		}
		
		if (grauMaximo == 0)
			return false; // Grafo totalmente desconexo.
		
		/* Todos os vértices de grau 0 foram removidos. Se houver desconexão, serão componentes de vértices de grau != 0*/
		if (!h.verificaConexidade()) 
			return false; // Grafo desconexo
			
		
		return true;
	}
	
	/*
	 * Utiliza o algoritmo de Hierholzer como implementação.
	 * O princípio desse algoritmo está em partir de um vértice e ir caminhando pelo grafo,
	 * até percorremos todas as arestas.
	 * Como a condição de existência de um caminho fechado euleriano é que todos os vértices
	 * tenham grau par, ao percorremos um número ímpar de vezes um vértice, é garantido haver
	 * esse número percorrido de arestas para volta através desse vértice.
	 * Se chegarmos ao vértice inicial novamente temos duas opções:
	 * ou obtemos o circuito completo ou ele não possui mais arestas.
	 * No segundo caso, ainda podem haver arestas no grafo.
	 * Logo, a implementação de pilha assegura que iremos percorrer todas as arestas,
	 * pois, sempre haverá um vértice na pilha que conterá arestas, já que o grafo é conexo
	 * e a cada chegada em um vértice, adicionamo-lo na pilha.
	 * Como o circuito está implementado como pilha também, o primeiro da pilha é o último a entrar,
	 * o segundo é o penúltimo e assim por diante. Com isso, será impresso o circuito do começo ao fim.
	 */
	
	public void imprimeEuleriano()
	{
		Grafo h = new Grafo(this);
		Vertice verticeMaisRecente;
		
		Stack<Vertice> pilhaAuxiliar = new Stack<Vertice>(); // Pilha para percorrer o grafo parcialmente
		Stack<Vertice> pilhaCircuito = new Stack<Vertice>(); // Pilha contendo o circuito final
		
		Iterator<Map.Entry<Integer, Vertice>> i = h.vertices.entrySet().iterator();
		
		verticeMaisRecente = i.next().getValue(); // Retorna o primeiro vértice da lista para começar.
		
		pilhaAuxiliar.push(verticeMaisRecente);
		
		while (!pilhaAuxiliar.empty())
		{
			verticeMaisRecente = pilhaAuxiliar.peek();
			
			if (verticeMaisRecente.retornaGrauVertice() == 0)
			{
				pilhaCircuito.push(verticeMaisRecente);
				pilhaAuxiliar.pop();
			}
			
			else
			{
				Iterator<Map.Entry<Integer, Vertice>> primeiroVizinho = verticeMaisRecente.vizinhos.entrySet().iterator();				
				
				Vertice proxVertice = primeiroVizinho.next().getValue(); // Pega o primeiro vizinho adjacente a esse vértice.
				
				pilhaAuxiliar.push(proxVertice);
				h.removeAresta(verticeMaisRecente.id, proxVertice.id);			
			}
		}
		
		while (!pilhaCircuito.empty()) 
		{
			System.out.print(pilhaCircuito.peek().id + " ");
			pilhaCircuito.pop();
		}
		
		
	}
	
	
	

}
