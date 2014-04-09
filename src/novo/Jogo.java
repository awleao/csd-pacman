package novo;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;

public class Jogo {

    public static final int TEMPO_ENERGIZADO = 20;

    private char[] mapa;
    private boolean morreu = false;
    private boolean isFraco = true;
    private int tempo = 0;

    private DIRECAO direcaoBola;
    private AudioClip sound;
    private AudioClip soundEnergizado;

    public enum DIRECAO {

        CIMA,
        BAIXO,
        ESQUERDA,
        DIREITA
    };

    public Jogo() {
        char[] mapa = new char[]{
            'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B',
            '\n', 'B', '<', ' ', ' ', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', ' ', ' ', 'B',
            '\n', 'B', ' ', ' ', ' ', '-', '-', '-', '-', 'B', '-', '-', '-', 'B', '-', '-', '-', '-', '-', ' ', ' ', 'B',
            '\n', 'B', ' ', ' ', '-', '-', '-', '-', '-', 'B', '-', '-', '-', 'B', '-', '-', '-', '-', '-', ' ', ' ', 'B',
            '\n', 'B', ' ', ' ', '-', '-', '-', '-', '-', 'B', '-', '-', '-', 'B', '-', '-', '-', '-', '-', ' ', ' ', 'B',
            '\n', 'B', ' ', ' ', '-', '-', '-', 'B', 'B', 'B', 'B', 'B', 'B', 'B', '-', '-', '-', '-', '-', ' ', ' ', 'B',
            '\n', 'B', '-', ' ', '-', '-', '-', '-', '-', 'B', '-', 'B', '-', '-', '-', '-', '-', '-', '-', ' ', '-', 'B',
            '\n', 'B', '-', 'B', 'B', 'B', 'B', '-', '-', 'B', '-', 'B', '-', 'B', '-', '-', '-', '-', '-', ' ', ' ', 'B',
            '\n', 'B', '-', ' ', ' ', '-', '-', '-', '-', 'B', '-', '-', '-', 'B', '-', '-', '-', '-', ' ', ' ', ' ', 'B',
            '\n', 'B', ' ', 'B', 'B', 'B', '-', '-', '-', 'B', '-', '-', '-', 'B', '-', '-', '-', '-', '-', ' ', ' ', 'B',
            '\n', 'B', ' ', '-', ' ', 'B', '-', '-', '-', ' ', '-', '-', '-', 'B', '-', '-', '-', '-', '-', '-', ' ', 'B',
            '\n', 'B', ' ', '-', ' ', 'B', '-', '-', '-', ' ', '-', '-', '-', 'B', 'B', 'B', 'B', '-', '-', '-', '-', 'B',
            '\n', 'B', ' ', 'B', 'B', 'B', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', 'B', '-', '-', ' ', '-', 'B',
            '\n', 'B', '*', 'B', ' ', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '*', 'B', '-', '-', ' ', '-', 'B',
            '\n', 'B', ' ', 'B', ' ', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', 'B', '-', '-', ' ', ' ', 'B',
            '\n', 'B', ' ', 'B', 'B', 'B', 'B', '-', '-', ' ', '-', '-', '-', '-', 'B', 'B', 'B', '-', '-', ' ', ' ', 'B',
            '\n', 'B', ' ', ' ', ' ', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', ' ', ' ', 'B',
            '\n', 'B', ' ', ' ', ' ', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', ' ', ' ', 'B',
            '\n', 'B', ' ', ' ', ' ', '-', '-', '-', '-', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', '*', ' ', 'B',
            '\n', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'
        };
        CriarMapa(mapa);
        direcaoBola = DIRECAO.ESQUERDA;
        try {
            sound = Applet.newAudioClip(new File("/pacman_eatghost.wav").toURL());
            soundEnergizado = Applet.newAudioClip(new File("/pacman_intermission.wav").toURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Jogo(char[] mapa, DIRECAO direcao) {
        CriarMapa(mapa);
        direcaoBola = direcao;
        try {
            sound = Applet.newAudioClip(new File("/pacman_eatghost.wav").toURL());
            soundEnergizado = Applet.newAudioClip(new File("/pacman_intermission.wav").toURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int encontrarBola(char[] mapa) {
        int posicao = 0;
        for (char c : mapa) {
            if ((c == '<') || (c == 'v') || (c == '^') || (c == '>')
                    || (c == 'O') || (c == 'K') || (c == 'L') || (c == 'Ç')) {
                return posicao;
            }

            posicao++;
        }
        return -1;
    }

    public int encontrarFantasma(char[] mapa) {
        int posicao = 0;
        for (char c : mapa) {
            if (c == '@') {
                return posicao;
            }

            posicao++;
        }
        return -1;
    }
    
    public void reviveFantasma() {
    	if (encontrarFantasma(mapa) == -1) {
    		mapa[primeiraPosicaoNaoBloqueada()] = '@';
    	}
    }
    
    public int primeiraPosicaoNaoBloqueada(){
    	int posicao = 0;
        for (char c : mapa) {
            if (c != 'B') {
                return posicao;
            }
            //FIXME: Mostrar o fantasma embaixo do pacman
            if (posicao == 20) {
            	posicao = 45; 
            } else {
            	posicao++;
            }
        }
        return -1;
    }

    private void CriarMapa(char[] mapa) {
        this.mapa = mapa;
    }

    public void esperar() {
        tempo++;
        if (!morreu) {
            int posicaoBola = encontrarBola(mapa);
            int posicaoAnterior = posicaoBola;

            switch (this.direcaoBola) {
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
        sound.stop();

        if (mapa[posicaoAtual] == 'B') {
            posicaoAtual = posicaoAnterior;
        } else {
        	mapa[posicaoAnterior] = ' ';	
        }

        if (mapa[posicaoAtual] == '*') {
            comerFruta();
        }

        if (mapa[posicaoAtual] == '@') {
        	if (isFraco) {
        		morreu = true;
        		mapa[posicaoAtual] = '@';
        	} else {
        		mapa[posicaoAtual] = aparencia();
        	}
        } else {
        	mapa[posicaoAtual] = aparencia();
        }
        
        if (posicaoAnterior != posicaoAtual) {
            if (isFraco) {
                sound.play();
            }
        }
    }

    private void comerFruta() {
        isFraco = false;
        sound.stop();
        soundEnergizado.play();
               
        final int quandoComeu = tempo;
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (quandoComeu + TEMPO_ENERGIZADO >= tempo) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                    }
                }
                isFraco = true;
                soundEnergizado.stop();
            }

        };
        thread.start();
    }

    private char aparencia() {
        if (!isFraco) {
            switch (this.direcaoBola) {
                case DIREITA:
                    return 'K';
                case ESQUERDA:
                    return 'Ç';
                case BAIXO:
                    return 'L';
                case CIMA:
                    return 'O';
                default:
                    return 'O';
            }
        }

        switch (this.direcaoBola) {
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
        this.direcaoBola = DIRECAO.ESQUERDA;
    }

    public void direita() {
        this.direcaoBola = DIRECAO.DIREITA;
    }

    public void cima() {
        this.direcaoBola = DIRECAO.CIMA;
    }

    public void baixo() {
        this.direcaoBola = DIRECAO.BAIXO;
    }

    public boolean isMorto() {
        return morreu;
    }
}
