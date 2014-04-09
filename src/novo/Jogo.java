package novo;

public class Jogo {
	
	private char[] mapa;
	private char coisaEmbaixoDoFantasma = ' ';
	private boolean isDireita = true;
	private boolean morreu = false;
	private boolean isFraco = true;
	private boolean matouFantasma = false;
	
	public Jogo() {
		CriarMapa(new char[]{'@', ' ', ' ', ' ', ' ', '-', '-', ' ', '<', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', '*'});
	}
	
	public Jogo(char[] mapa) {
		CriarMapa(mapa);
	}
	
	public int encontrarBola(char[] mapa){
		int posicao = 0;
		for (char c : mapa) {			
			if ((c=='<') || (c=='>') || (c=='O'))				
				return posicao;
			
			posicao++;
		}
		return 0;
	}
	
	public int encontrarFantasma(char[] mapa){
		int posicao = 0;
		for (char c : mapa) {
			if (c=='@')				
				return posicao;
			
			posicao++;
		}
		return -1;
	}

	private void CriarMapa(char[] mapa) {
		this.mapa = mapa;
	}
	
	public void esperar() {
		if(!morreu) {
			int posicaoBola = encontrarBola(mapa);
			int posicaoAnterior = posicaoBola;
			if (isDireita) {
				if (posicaoBola < finalDaLinha()) posicaoBola++;
			} else
				if (posicaoBola > 0) posicaoBola--;
			
			moverBola(posicaoAnterior, posicaoBola);
			moverFantasma();
		}
 	}

	private void moverFantasma() {
		int posicaoFantasma = encontrarFantasma(mapa);
		
		if (posicaoFantasma == -1)
			return;
					
		mapa[posicaoFantasma] = coisaEmbaixoDoFantasma;
		if(posicaoFantasma < finalDaLinha())
			posicaoFantasma++;
		if(posicaoFantasma == encontrarBola(mapa)) {
			if(isFraco) {
				morreu = true;
			} else {
				matouFantasma = true;
			}
		}
		coisaEmbaixoDoFantasma = mapa[posicaoFantasma];
		if(!matouFantasma) {
			mapa[posicaoFantasma] = '@';
		}
	}

	private void moverBola(int posicaoAnterior, int posicaoAtual) {
		mapa[posicaoAnterior] = ' ';

		if(mapa[posicaoAtual] == 'B')
			posicaoAtual = posicaoAnterior;
		
		if(mapa[posicaoAtual] == '*')
			isFraco = false;
		
		mapa[posicaoAtual] = aparencia();
	}
	
	private int finalDaLinha() {
		return mapa.length - 1;
	}

	private char aparencia() {
		return isFraco ? (isDireita ? '<' : '>') : 'O';
	}

	public String getTela() {
		StringBuilder tela = new StringBuilder();
		return "|" + tela.append(mapa).toString() + "|";
	}

	public void esquerda()   { isDireita = false; }
	public void direita()    { isDireita = true; }
	public boolean isMorto() { return morreu; }
}