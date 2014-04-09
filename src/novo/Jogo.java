package novo;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;

public class Jogo {
	
	private char[] mapa;
	private boolean morreu = false;
	private boolean isFraco = true;
	private boolean matouFantasma = false;
	
	private DIRECAO direcao;
	private AudioClip sound;

	public enum DIRECAO {
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
		direcao =  DIRECAO.ESQUERDA;
		try {
			sound = Applet.newAudioClip( new File("pacman_eatghost.wav").toURL() );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Jogo(char[] mapa, DIRECAO direcaoBola) {
		CriarMapa(mapa);
		direcao = direcaoBola;
	}
	
	private int encontrarBola(char[] mapa){
		int posicao = 0;
		for (char c : mapa) {			
			if ((c=='<') || (c=='>') || (c=='v') || (c=='^')|| (c=='O'))				
				return posicao;
			
			posicao++;
		}
		return 0;
	}
	
	private int encontrarFantasma(char[] mapa){
		int posicao = 0;
		for (char c : mapa) {
			if (c=='@')				
				return posicao;
			
			posicao++;
		}
		return -1;
	}
	
	public void reviveFantasma(){
		if (encontrarFantasma(mapa) == -1)
			mapa[1] = '@';
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
					posicaoBola++;
				break;
			case ESQUERDA:
					posicaoBola--;
				break;
			case BAIXO:
					posicaoBola += 22;
				break;
			case CIMA:
					posicaoBola -= 22;
				break;
			}
			
			reviveFantasma();
			moverBola(posicaoAnterior, posicaoBola);			
		}
 	}

	private void moverBola(int posicaoAnterior, int posicaoAtual) {

		if(mapa[posicaoAtual] == 'B') {
			posicaoAtual = posicaoAnterior;
		} else {
			mapa[posicaoAnterior] = ' ';	
		}
		
		if(mapa[posicaoAtual] == '*')
			isFraco = false;
		
		if(mapa[posicaoAtual] == '@'){
			if (isFraco){
				morreu = true;
				mapa[posicaoAtual] = '@';
			} else {				
				mapa[posicaoAtual] = aparencia();
			}
		} else {
			mapa[posicaoAtual] = aparencia();
		}
		
		//FIXME: Ausencia do arquivo.
		//sound.play();
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