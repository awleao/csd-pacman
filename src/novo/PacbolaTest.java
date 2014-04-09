package novo;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class PacbolaTest {

	private Jogo jogo = new Jogo();
	
	@Test
	public void DevePararAoEncontrarBloqueioDireita() {
		char[] mapa = new char[]{'<', ' ', '-', '-', '-', '-', '-', 'B', '-', '-', '-'};
		jogo = new Jogo(mapa);
		tela("|< -----B---|");
		espera(7);
		tela("|      <B---|");
	}
	
	@Test
	public void FantasmaDeveAparecerSeEstiverNoMapa() {
		char[] mapa = new char[]{'<', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-'};
		jogo = new Jogo(mapa);
		tela("|< ---------|");
		espera();
		tela("| <---------|");
	}
	
	@Test
	public void PacmanAndarUmaPosicaoNoMapa(){
		char[] mapa = new char[]{' ', ' ', '<', '-', '-', '-', '-', '-', '-', '-', '-'};
		jogo = new Jogo(mapa);
		tela("|  <--------|");
		espera();
		tela("|   <-------|");
	}
	

	@Test
	public void telaInicial() {
		tela("|@    -- < ---------*|");
	}

	@Test
	public void segundoFrame() {
		espera();
		tela("| @   --  <---------*|");
	}
	
	@Test
	public void segundoFrameSeEsquerda() {
		esquerda();
		espera();
		tela("| @   -->  ---------*|");
	}
	
	@Test
	public void deveComerTudoOQuePassar() {
		espera(11);
		tela("|     --    @       O|");
	}
	
	@Test
	public void deveMudarDirecaoParaEsquerda() {
		tela("|@    -- < ---------*|");
		espera();
		tela("| @   --  <---------*|");
		espera();
		tela("|  @  --   <--------*|");
		esquerda();
		espera();
		tela("|   @ --  > --------*|");
	}

	@Test
	public void deveMudarDirecaoParaDireita() {
		tela("|@    -- < ---------*|");
		espera(2);
		tela("|  @  --   <--------*|");
		esquerda();
		espera();
		tela("|   @ --  > --------*|");
		direita();
		espera(2);
		tela("|     @-    <-------*|");
	}
	
	@Test
	public void deveFantasmaNaoComerFrutinha() {
		espera(11);
		tela("|     --    @       O|");
	}
	
	@Test
	public void deveMorrerQuandoEncostaNoFantasma() {
		vivo();
		esquerda();
		espera();
		tela("| @   -->  ---------*|");
		espera(2);
		tela("|   @ >    ---------*|");
		espera();
		tela("|    @     ---------*|");
		morreu();
	}
	
	@Test
	public void deveMatarFantasmaAposComerFruta() {
		espera(19);
		tela("|     --            O|");
		vivo();
	}
	
	@Test
	@Ignore
	public void fantasmaDevePararNoFinal() {
		espera(18);
		esquerda();
		tela("|     --            @|");
	}
	
	@Test
	public void pacmanDevePararNoFinalADireita() {
		espera(12);
		tela("|     --     @      O|");
		espera();
		tela("|     --      @     O|");
	}
	
	
	private void vivo() {
		assertFalse(jogo.isMorto());
	}

	private void morreu() {
		assertTrue(jogo.isMorto());
	}

	private void tela(String esperada) {
		assertEquals(esperada, jogo.getTela());
	}
	
	private void esquerda() {
		jogo.esquerda();
	}
	
	private void espera(int vezes) {
		while (vezes-- != 0)
			espera();
	}
	
	private void direita() {
		jogo.direita();
	}
	
	private void espera() {
		jogo.esperar();
	}
}

