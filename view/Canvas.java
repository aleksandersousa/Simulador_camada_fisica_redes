/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 23/01/2020*
Ultima alteracao: 29/01/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */
package view;

import img.Images;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Canvas extends JPanel {
  private final int Y = 10;
  private final int LARGURA = 60;
  private final int ALTURA = 60;
  private final int ESPACAMENTO = 58;

  public static int[] fluxoDeBits;
  public static boolean flag;

  private int x;

  private ArrayList<Image> linhas;
  private Images imagens;

  /* **************************************************************
  Metodo: Canvas*
  Funcao: Construtor da classe Canvas.*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  public Canvas() {
    this.initGUIComponents();

    this.imagens = new Images();
    this.linhas = imagens.getLinhas();
  }

  /* **************************************************************
  Metodo: initGUIComponents*
  Funcao: inicializar os componentes do painel.*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  private void initGUIComponents() {
    this.setLayout(null);
    this.setBorder(BorderFactory.createLineBorder(Color.RED));
    this.setBackground(Color.gray);
  }

  /* **************************************************************
  Metodo: criarLinhaTracejada*
  Funcao: desenha uma linha tracejada no meio do painel.*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  private void criarLinhaTracejada(Graphics g) {
    Graphics2D g2D = (Graphics2D) g.create();

    Stroke linhaTracejada =
    new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);

    g2D.setStroke(linhaTracejada);
    g2D.drawLine(0, this.getHeight()/2, this.getWidth(), this.getHeight()/2);
    g2D.dispose();
  }

  /* **************************************************************
  Metodo: paintComponent*
  Funcao: desenhar as ondas que representam os bits pintando as
          respectivas imagens na tela.*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    this.criarLinhaTracejada(g);

    if(flag){
      if(fluxoDeBits[0] == 0){
        g.drawImage(linhas.get(0), x, Y, LARGURA, ALTURA, null); //linha inicial bit0
        x += 30;
      }
      else{
        g.drawImage(linhas.get(1), x, Y, LARGURA, ALTURA, null); //linha inicial bit1
        x += 30;
      }

      for(int i=0; i<fluxoDeBits.length-1; i++){
        if(fluxoDeBits[i] == 0){
          if(fluxoDeBits[i+1] == 1){
            g.drawImage(linhas.get(2), x, Y, LARGURA, ALTURA, null); //linha bit0
            x += ESPACAMENTO;
          }
          else{
            g.drawImage(linhas.get(3), x, Y, LARGURA, ALTURA, null); //linha bit0_2
            x += ESPACAMENTO;
          }
        }else{
          if(fluxoDeBits[i+1] == 0){
            g.drawImage(linhas.get(4), x, Y, LARGURA, ALTURA, null); //linha bit1
            x += ESPACAMENTO;
          }else{
            g.drawImage(linhas.get(5), x, Y, LARGURA, ALTURA, null); //linha bit1_2
            x += ESPACAMENTO;
          }
        }
      }

      if(fluxoDeBits[fluxoDeBits.length-1] == 0){
        g.drawImage(linhas.get(2), x, Y, LARGURA, ALTURA, null); //linha bit0
        x += ESPACAMENTO/2;
        g.drawImage(linhas.get(6), x, Y, LARGURA, ALTURA, null); //linha final bit0
      }else{
        g.drawImage(linhas.get(4), x, Y, LARGURA, ALTURA, null); //linha bit1
        x += ESPACAMENTO/2;
        g.drawImage(linhas.get(7), x, Y, LARGURA, ALTURA, null); //linha final bit1
      }

      x = 0;
    }
  }

  /* **************************************************************
  Metodo: getPreferredSize*
  Funcao: setar tamanho do painel.*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(40895, 0);
  }
}