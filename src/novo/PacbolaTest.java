package novo;

import static org.junit.Assert.*;
import novo.Jogo.DIRECAO;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class PacbolaTest {

	private Jogo jogo = new Jogo();
	
	@Before
	public void mapaDefault() {
		char[] mapa = new char[]{'B', '@', ' ', ' ', ' ', ' ', '-', '-', ' ', '<', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-', '*', 'B'};
		jogo = new Jogo(mapa, DIRECAO.DIREITA);
	}
	
	@Test
	public void DevePararAoEncontrarBloqueioDireita() {
		char[] mapa = new char[]{'<', ' ', '-', '-', '-', '-', '-', 'B', '-', '-', '-'};
		jogo = new Jogo(mapa, DIRECAO.DIREITA);
		tela("< -----B---");
		espera(7);
		tela("      <B---");
	}
	
	@Test
	public void FantasmaDeveAparecerSeEstiverNoMapa() {
		char[] mapa = new char[]{'<', ' ', '-', '-', '-', '-', '-', '-', '-', '-', '-'};
		jogo = new Jogo(mapa, DIRECAO.DIREITA);
		tela("< ---------");
		espera();
		tela(" <---------");
	}
	
	@Test
	public void PacmanAndarUmaPosicaoNoMapa(){
		char[] mapa = new char[]{' ', ' ', '<', '-', '-', '-', '-', '-', '-', '-', '-'};
		jogo = new Jogo(mapa, DIRECAO.DIREITA);
		tela("  <--------");
		espera();
		tela("   <-------");
	}
	

	@Test
	public void telaInicial() {
		tela("B@    -- < ---------*B");
	}

	@Test
	public void segundoFrame() {
		espera();
		tela("B @   --  <---------*B");
	}
	
	@Test
	public void segundoFrameSeEsquerda() {
		esquerda();
		espera();
		tela("B @   -->  ---------*B");
	}
	
	@Test
	public void deveComerTudoOQuePassar() {
		espera(11);
		tela("B     --    @       OB");
	}
	
	@Test
	public void deveMudarDirecaoParaEsquerda() {
		tela("B@    -- < ---------*B");
		espera();
		tela("B @   --  <---------*B");
		espera();
		tela("B  @  --   <--------*B");
		esquerda();
		espera();
		tela("B   @ --  > --------*B");
	}

	@Test
	public void deveMudarDirecaoParaDireita() {
		tela("B@    -- < ---------*B");
		espera(2);
		tela("B  @  --   <--------*B");
		esquerda();
		espera();
		tela("B   @ --  > --------*B");
		direita();
		espera(2);
		tela("B     @-    <-------*B");
	}
	
	@Test
	public void deveFantasmaNaoComerFrutinha() {
		espera(11);
		tela("B     --    @       OB");
	}
	
	@Test
	public void deveMorrerQuandoEncostaNoFantasma() {
		vivo();
		esquerda();
		espera();
		tela("B @   -->  ---------*B");
		espera(2);
		tela("B   @ >    ---------*B");
		espera();
		tela("B    @     ---------*B");
		morreu();
	}
	
	@Test
	public void deveMatarFantasmaAposComerFruta() {
		espera(19);
		tela("B     --            OB");
		vivo();
	}
	
	@Test
	@Ignore
	public void fantasmaDevePararNoFinal() {
		espera(18);
		esquerda();
		tela("B     --            @B");
	}
	
	@Test
	public void bolaDevePararNoFinalADireita() {
		espera(12);
		tela("B     --     @      OB");
		espera();
		tela("B     --      @     OB");
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

