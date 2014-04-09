package novo;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;

public class Jogo {
	
	private char[] mapa;
	private char coisaEmbaixoDoFantasma = ' ';
	private boolean morreu = false;
	private boolean isFraco = true;
	private boolean matouFantasma = false;
	
	private DIRECAO direcao = DIRECAO.ESQUERDA;
	private AudioClip sound;

	private enum DIRECAO {
		CIMA,
		BAIXO,
		ESQUERDA,
		DIREITA
	};

	public Jogo() {
		char [] mapa = new char[]{' ', ' ', ' ', ' ', ' ', '-', '-', ' ', '<', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', ' ',
                '\n', '-', ' ', ' ', ' ', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', ' ', ' ', ' ',
                '\n', '-', ' ', ' ', ' ', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', ' ', ' ', ' ',
                '\n', '-', ' ', ' ', '-', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', ' ', ' ', ' ',
                '\n', '-', ' ', ' ', '-', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', ' ', ' ', ' ',
                '\n', '-', ' ', ' ', '-', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', ' ', ' ', ' ',
                '\n', '-', '-', ' ', '-', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', ' ', '-', ' ',
                '\n', '-', '-', ' ', ' ', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', ' ', ' ', ' ',
                '\n', '-', '-', ' ', ' ', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', '-', '-', ' ', ' ', ' ', ' ',
                '\n', '-', ' ', '-', ' ', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', ' ', ' ', ' ',
                '\n', '-', ' ', '-', ' ', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', ' ', ' ',
                '\n', '-', ' ', '-', ' ', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', ' ',
                '\n', '-', ' ', ' ', ' ', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', ' ', '-', ' ',
                '\n', '-', '-', ' ', ' ', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', ' ', '-', ' ',
                '\n', '-', ' ', ' ', ' ', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', ' ', ' ', ' ',
                '\n', '-', ' ', ' ', ' ', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', ' ', ' ', ' ',
                '\n', '-', ' ', ' ', ' ', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', ' ', ' ', ' ',
                '\n', '-', ' ', ' ', ' ', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', ' ', ' ', ' ',
                '\n', '-', ' ', ' ', ' ', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', ' ', ' ', ' ',
                '\n', ' ', ' ', ' ', ' ', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', ' ', ' '
            };
		CriarMapa(mapa);
		try {
			sound = Applet.newAudioClip( new File("pacman_eatghost.wav").toURL() );
		} catch (Exception e) {
			e.printStackTrace();
		}
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

			switch (this.direcao) {
			case DIREITA:
				if (posicaoBola < finalDaLinha()) {
					posicaoBola++;
				}
				break;
			case ESQUERDA:
				if (posicaoBola > 0) {
					posicaoBola--;
				}
				break;
			case BAIXO:
				if (posicaoBola > 0) {
					posicaoBola += 22;
				}
				break;
			case CIMA:
				if (posicaoBola > 0) {
					posicaoBola -= 22;
				}
				break;
			}

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
		sound.play();
	}
	
	private int finalDaLinha() {
		return mapa.length - 1;
	}

	private char aparencia() {
        if (!isFraco) {
            return 'O';
        }

        switch (this.direcao) {
            case DIREITA:
                return '<';
            case ESQUERDA:
                return '>';
            case BAIXO:
                return 'v';
            case CIMA:
                return '^';
            default:
                return 'O';
        }
	}

	public String getTela() {
		StringBuilder tela = new StringBuilder();
		return tela.append(mapa).toString();
	}

    public void esquerda() {
        this.direcao = DIRECAO.ESQUERDA;
    }

    public void direita() {
        this.direcao = DIRECAO.DIREITA;
    }

    public void cima() {
        this.direcao = DIRECAO.CIMA;
    }

    public void baixo() {
        this.direcao = DIRECAO.BAIXO;
    }
    
    public boolean isMorto() { return morreu; }
}