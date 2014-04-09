package novo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class JogoCanvas extends JFrame {
	private String[] _tela;
	private static final long serialVersionUID = 1L;
	private final Map<Character, BufferedImage> _sprites = new HashMap<Character, BufferedImage>();;

	public JogoCanvas(){
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(710, 710));
		setBackground(Color.BLACK);
		
		try {
			_sprites.put('>', ImageIO.read(JogoCanvas.class.getResourceAsStream("pacman-inverso.gif")));
			_sprites.put('<', ImageIO.read(JogoCanvas.class.getResourceAsStream("pacman.gif")));		
			_sprites.put('v', ImageIO.read(JogoCanvas.class.getResourceAsStream("pacman.gif")));		
			_sprites.put('^', ImageIO.read(JogoCanvas.class.getResourceAsStream("pacman.gif")));		
			_sprites.put('O', ImageIO.read(JogoCanvas.class.getResourceAsStream("pacman-inverso-power.gif")));	
			_sprites.put('@', ImageIO.read(JogoCanvas.class.getResourceAsStream("fantasma.png")));
			_sprites.put('*', ImageIO.read(JogoCanvas.class.getResourceAsStream("fruta.jpg")));
			_sprites.put('-', ImageIO.read(JogoCanvas.class.getResourceAsStream("moeda.jpg")));
			_sprites.put(' ', ImageIO.read(JogoCanvas.class.getResourceAsStream("vazio.jpg")));
			_sprites.put('|', ImageIO.read(JogoCanvas.class.getResourceAsStream("vazio.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public void atualizaTela(String[] pTela) {
		_tela = pTela;
		repaint();
	}

	public void paint(Graphics g) {
		if (_tela == null) return; 
		
		Graphics2D g2d = (Graphics2D) g;
		for (int linha = 0; linha < _tela.length; linha++) {
			String conteudoLinha = _tela[linha];
			for (int coluna = 0; coluna < conteudoLinha.length(); coluna++) {
				RenderedImage bufferedImage = getSprite(coluna, conteudoLinha);
				g2d.drawRenderedImage(bufferedImage, AffineTransform.getTranslateInstance(coluna*32, linha*32 + 30));
			}
		}
	}

	private RenderedImage getSprite(int coluna, String conteudoLinha) {
		char caractere = conteudoLinha.charAt(coluna);
		RenderedImage bufferedImage = _sprites.get(caractere);
		if (bufferedImage == null) System.out.println("Sprite nao registrada para caracter: '" + caractere +"'");
		return bufferedImage;
	}

}