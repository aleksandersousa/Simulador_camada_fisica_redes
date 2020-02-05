/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 23/01/2020*
Ultima alteracao: 01/02/2020*
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
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Canvas extends JPanel {
  private final int Y = 18;
  private final int LARGURA = 57;
  private final int ALTURA = 60;
  private final int ESPACAMENTO = 54;

  public static boolean flag; //flag que indica se o painel pode ser repintado
  private static int x;
  public static int velocidade = 2;

  public static Thread atualizar = new Thread();
  private static ArrayList<Image> linhas;
  public static ArrayList<Integer> fluxoDeBits;
  private static LinkedList<Image> tempLinhas;
  public static Semaphore trava;
  private Images imagens;

  /* **************************************************************
  Metodo: Canvas*
  Funcao: Construtor da classe Canvas*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  public Canvas() {
    this.setBackground(Color.CYAN);
    this.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));
    this.imagens = new Images();

    Canvas.linhas = imagens.getLinhas();
    Canvas.fluxoDeBits = new ArrayList<>();
    Canvas.tempLinhas = new LinkedList<>();
    Canvas.trava = new Semaphore(0); //semaforo para travar as camadas decodificadoras
                                    // ate a animacao ser concluida
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
  Metodo: iniciarListaDeImagens*
  Funcao: inicia uma lista linkada que representa as ondas do fluxo
          de bits*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  public static void iniciarListaDeImagens() {
    for(int i=0; i<fluxoDeBits.size()-1; i++){
      if(fluxoDeBits.get(i) == 0){
        if(fluxoDeBits.get(i+1) == 1){
          tempLinhas.add(linhas.get(0)); //linha bit0
        }
        else{
          tempLinhas.add(linhas.get(1)); //linha bit0_2
        }
      }else{
        if(fluxoDeBits.get(i+1) == 0){
          tempLinhas.add(linhas.get(2)); //linha bit1
        }else{
          tempLinhas.add(linhas.get(3)); //linha bit1_2
        }
      }
    }

    if(fluxoDeBits.get(fluxoDeBits.size()-1) == 0){
      tempLinhas.add(linhas.get(1)); //linha bit0_2
    }else{
      tempLinhas.add(linhas.get(3)); //linha bit1_2
    }

    tempLinhas.add(null); //imagem nula para identificar final do array
    fluxoDeBits.clear(); //apos criar a lista com as imagens, reseta o array de bits
  }

  /* **************************************************************
  Metodo: repintar*
  Funcao: inicia um thread que fica repintando o painel. Quando o
          thread eh interrompido, reseta os elementos para nova
          animacao*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  public void repintar() {
    atualizar = new Thread(new Runnable(){
      @Override
      public void run() {
        try {
          while(true){
            repaint();
            Thread.sleep(1000/velocidade);
          }
        } catch (InterruptedException e) {
          Canvas.flag = false;
          Canvas.tempLinhas.clear();
          Canvas.trava.release();
          Canvas.x = 0;
        }
      }
    });
    atualizar.start();
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
      int i=0;
      for(; i<9; i++){ //desenha 8 imagens, representando 8 bits
        if(tempLinhas.get(i) == null){ //acabaram as imagens
          break;
        }
        g.drawImage(tempLinhas.get(i), x, Y, LARGURA, ALTURA, null);
        x += ESPACAMENTO;
      }

      if(i+1 < tempLinhas.size()){ //verifica se tem mais 8 bits para representar
        tempLinhas.removeFirst();
        x = 0;
      }else{ //termina a animacao
        atualizar.interrupt();
      }
    }
  }

  /* **************************************************************
  Metodo: getPreferredSize*
  Funcao: seta o tamanho deste painel.*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(460, 0);
  }
}