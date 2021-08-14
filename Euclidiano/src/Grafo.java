import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Stack;


public class Grafo 
{
	protected HashMap<Integer, Vertice> vertices;
	protected Vertice raizAtual;
	
	Grafo ()
	{
		vertices = new HashMap<Integer, Vertice>();
		raizAtual = null;
	}
	
	/* Construtor de cópia profunda para funções de análise */
	
	private Grafo(Grafo g)
	{
		HashMap <Integer, Vertice> temp = new HashMap<Integer, Vertice>();
		
		/* Para cada ID nos vértices do grafo a ser copiado, adicionar no grafo cópia. */
		
		for (Vertice v : g.vertices.values())
			temp.put(v.id, new Vertice(v.id));
		
		/* Para cada ID nos vértices do grafo a ser copiado,
		 * devemos iterar nos vizinhos dele para adicioná-los nos
		 * vizinhos do vértice no grafo cópia.
		 */
		
		for (Vertice v : g.vertices.values())
		{
			Vertice atual = temp.get(v.id); // Referência no grafo cópia
			
			for (Vertice w : v.vizinhos)
				atual.vizinhos.add(temp.get(w.id)); // Coloca a referência do vizinho na lista dos vizinhos
		}
			
		this.vertices = temp;
		
		if (g.raizAtual != null)
			this.raizAtual = this.vertices.get(g.raizAtual.id);
		else
			this.raizAtual = null;
	}
	
	public void imprimeGrafo()
	{
		System.out.println("Dados do grafo\n----------------");
		if (this.vertices.isEmpty())
		{
			System.out.println("Grafo sem vértices.");
			return;
		}
		
		if (raizAtual == null)
			System.out.println("Nenhum vértice é uma raiz.\n");
		
		else
			System.out.println("A raiz atual é: " + raizAtual.id + "\n");
		
		if (vertices != null)
		{
			System.out.println("----------------");
			
			for (Vertice v : vertices.values())
			{
				v.imprimeVertice();
			}
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
			
			raizAtual = null;
		}
	}
	
	public void deletaVertice(int id)
	{
		Vertice d = vertices.get(id);
		
		if (d == null)
		{
			System.out.println("Vértice não existe.\n");
			return;
		}
		
		for (Vertice v : vertices.values())
			v.vizinhos.remove(id);
		
		if (d == raizAtual)
			raizAtual = null;
		
		vertices.remove(id);
		
		for (Vertice v: vertices.values())
			v.reset();
		
		raizAtual = null;
	}
	
	public void adicionaAresta(int id1, int id2)
	{
		Vertice v = vertices.get(id1);
		Vertice w = vertices.get(id2);
		
		if (v == null || w == null)
		{
			System.out.println("Pelo menos um dos vértices não existe.\n");
			return;
		}
		
		if (v == w)
			v.vizinhos.add(w);
		
		else
		{
			v.adicionaVizinho(w);
			w.adicionaVizinho(v);
		}
		
		for (Vertice u : vertices.values())
			u.reset();
		
		raizAtual = null;
	}
	
	
	
	public void removeAresta(int id1, int id2)
	{
		Vertice v = vertices.get(id1);
		Vertice u = vertices.get(id2);
		
		if (v == null|| u == null)
		{
			System.out.println("Um dos vértices não existe.\n");
			return;
		}
		
		if (v == u) // Laço
			v.vizinhos.remove(u);
			
		else
		{
			v.vizinhos.remove(u);
			u.vizinhos.remove(v);
		}
		
		for (Vertice w : vertices.values())
			w.reset();
		
		raizAtual = null;
			
	}
	
	public void DFS(int id)
	{
		Vertice v = vertices.get(id); // Vértice de partida da busca
		
		if (v == null)
		{
			System.out.println("Vértice não existe. Impossível realizar busca.\n");
			return;
		}
		
		for (Vertice u : vertices.values())
			u.reset();
		
		v.visitado = true; // Marca o vértice
		
		raizAtual = v; // Marca a raiz atual do grafo
		
		visitadorDFS(v);
	}
	
	public void visitadorDFS(Vertice v)
	{
		for (Vertice u : v.vizinhos) // Percorre os vizinhos do vértice
		{
			if (u.visitado == false) // Vértice não marcado
			{
				u.visitado = true;
				visitadorDFS(u); // A recursão faz com que a busca seja de profundidade.
			}
		}
		
	}
	
	/*
	 * Aqui, vamos considerar que vértices de grau 0 serão ignorados, pois essa função
	 * está sendo utilizada para encontrar o circuito euleriano, que não se preocupa com vértices de grau 0.
	 * Logo, um grafo com vértices de grau 0 será considerado conexo se esse não for o grau máximo.
	 */
	
	public boolean verificaConexidade()
	{
		if (vertices == null || vertices.isEmpty())
			return false;
		
		Integer idVerticeInicial = 0;
		
		if (raizAtual == null) // Se verdadeiro, não houve busca no grafo.
		{
			// Não podemos começar a busca de um vértice de grau 0.
			// Por isso, devemos iterar até encontrar um vértice de grau diferente de 0.
			
			Iterator<Map.Entry<Integer, Vertice>> i = vertices.entrySet().iterator();
			
			while (i.hasNext())
			{
				Vertice v = i.next().getValue();
				
				idVerticeInicial = v.id;
				
				if (v.retornaGrauVertice() != 0) 
					break;				
			}
		}
		else
			idVerticeInicial = raizAtual.id; // Começa da raiz atual.	
		
		DFS(idVerticeInicial);
		
		for (Vertice v : vertices.values())
			if (v.visitado == false && v.retornaGrauVertice() != 0)
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
			
		}
		
		if (grauMaximo == 0)
			return false; // Grafo totalmente desconexo.
		
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
	 * pois sempre haverá um vértice na pilha que conterá arestas, já que o grafo é conexo
	 * e, a cada chegada em um vértice, adicionamo-lo na pilha.
	 * Como o circuito está implementado como pilha LIFO também, o primeiro da pilha é o último a entrar,
	 * o segundo é o penúltimo e assim por diante. Com isso, será impresso o circuito na ordem
	 * que o grafo foi percorrido.
	 */
	
	public void imprimeEuleriano()
	{
		Grafo h = new Grafo(this);
		Vertice verticeMaisRecente;
		
		Stack<Vertice> pilhaAuxiliar = new Stack<Vertice>(); // Pilha para percorrer o grafo parcialmente
		Stack<Vertice> pilhaCircuito = new Stack<Vertice>(); // Pilha contendo o circuito final
		
		ArrayList<Vertice> hashParaArray = new ArrayList<Vertice>(h.vertices.values());
		Random r = new Random();
		int posicaoAleatoria = r.nextInt(hashParaArray.size());
		
		verticeMaisRecente = hashParaArray.get(posicaoAleatoria); // Retorna um vértice da lista para começar.
		
		 // Se o vértice tem grau 0, precisamos procurar outro para começar o circuito.		
		while (verticeMaisRecente.retornaGrauVertice() == 0)
		{
			posicaoAleatoria = r.nextInt(hashParaArray.size());
			verticeMaisRecente = hashParaArray.get(posicaoAleatoria);
		}
		
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
				hashParaArray = new ArrayList<Vertice>(verticeMaisRecente.vizinhos);
				
				posicaoAleatoria = r.nextInt(hashParaArray.size());
				
				Vertice proxVertice = hashParaArray.get(posicaoAleatoria);// Pega um vizinho adjacente a esse vértice.
				
				pilhaAuxiliar.push(proxVertice);
				h.removeAresta(verticeMaisRecente.id, proxVertice.id);			
			}
		}
		
		while (!pilhaCircuito.empty()) 
		{
			System.out.print(pilhaCircuito.peek().id + " ");
			pilhaCircuito.pop();
		}
		System.out.println("\n");
		
		
	}
	
	
	

}
