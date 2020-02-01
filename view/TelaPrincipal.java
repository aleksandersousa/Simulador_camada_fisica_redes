/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 23/01/2020*
Ultima alteracao: 01/02/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame {
  public static final int LARGURA_LABELS_ESQUERDO = 120;
  public static final int LARGURA_LABELS_DIREITO = 135;
  public static final int ALTURA_LABELS = 18;
  public static final int LARGURA_COMPONENTES = 280;
  public static final int ALTURA_COMPONENTES = 40;
  public static final int ASCII = 0;
  public static final int BIT_BRUTO = 5;
  public static final int ASCII_DECODIFICADO = 2;
  public static final int MENSAGEM_DECODIFICADA = 3;
  public static final int BIT_DECODIFICADO = 4;
  public static final int BIT_CODIFICADO = 1;
  public static final int BIT_RECEBIDO = 6;
  private static final int VELOCIDADE = 600;

  public static Canvas canvas;

  private JPanel painelBackground;
  private JPanel painelInferior;
  private PainelEsquerdo painelEsquerdo;
  private PainelDireito painelDireito;

  /* **************************************************************
  Metodo: TelaPrincipal*
  Funcao: Construtor da classe TelaPrincipal.*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  public TelaPrincipal(){
    TelaPrincipal.canvas = new Canvas();

    this.painelInferior = new JPanel();
    this.painelEsquerdo = new PainelEsquerdo();
    this.painelDireito = new PainelDireito();

    this.initGUIComponents();
  }

  /* **************************************************************
  Metodo: initGUIComponents*
  Funcao: inicializar os componentes da tela.*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  private void initGUIComponents() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(930, 450);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
    this.getRootPane().setDefaultButton(PainelEsquerdo.btnEnviar);
    this.setVisible(true);

    this.inicializarComponentes();
    this.add(painelBackground);
    this.add(painelInferior);
  }

  /* **************************************************************
  Metodo: inicializarComponentes*
  Funcao: inicializar os paineis que compoem a tela.*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  private void inicializarComponentes() {
    this.painelBackground = new JPanel(){
      @Override
      public Dimension getPreferredSize() {
        return new Dimension(0, 320);
      }
      {
        this.setLayout(new GridLayout(0, 2));
        this.add(painelEsquerdo);
        this.add(painelDireito);
      }
    };

    this.painelInferior = new JPanel(){
      JPanel painel1 = new JPanel();
      JLabel labelBarraDeVelocidade  = new JLabel("Barra de velocidade");
      JSlider barraDeVelocidade = new JSlider(){
        @Override
        public Dimension getPreferredSize() {
          return new Dimension(400, 50);
        }
        {
          this.setBackground(Color.CYAN);
          this.setMinimum(1);
          this.setValue(5);
          this.setMaximum(10);
          this.addChangeListener( e -> Canvas.velocidade = this.getValue());
        }
      };

      private void iniciarPainel1(){
        labelBarraDeVelocidade.setAlignmentX(Component.CENTER_ALIGNMENT);

        painel1.setLayout(new BoxLayout(painel1, BoxLayout.Y_AXIS));
        painel1.setBackground(Color.CYAN);
        painel1.add(labelBarraDeVelocidade);
        painel1.add(barraDeVelocidade);
      }

      @Override
      public Dimension getPreferredSize() {
        return new Dimension(0, 110);
      }
      {
        this.iniciarPainel1();
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(Color.CYAN);
        this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
        this.add(painel1);
        this.add(canvas);
      }
    };
  }

  /* **************************************************************
  Metodo: imprimirNaTela*
  Funcao: Imprimir as informacoes na tela*
  Parametros: String strMensagem: texto a ser impresso
              int tipoDeImpressao: em qual caixa de texto sera impresso*
  Retorno: void*
  *************************************************************** */
  public static void imprimirNaTela(String strMensagem, int tipoDeImpressao) {
    try {
      switch (tipoDeImpressao) {
        case 0: //imprime tabela ascii
          PainelEsquerdo.arrayCaixasDeTexto.get(0).setText(strMensagem);
          PainelEsquerdo.arrayCaixasDeTexto.get(0).update(PainelEsquerdo.arrayCaixasDeTexto.get(0).getGraphics());
          break;
        case 1: //imprime os bits codificados
          PainelEsquerdo.arrayCaixasDeTexto.get(3).setText(strMensagem);
          PainelEsquerdo.arrayCaixasDeTexto.get(3).update(PainelEsquerdo.arrayCaixasDeTexto.get(3).getGraphics());
          break;
        case 2: //imprime tabela ascii decodificada
          PainelDireito.arrayCaixasDeTexto.get(1).setText(strMensagem);
          PainelDireito.arrayCaixasDeTexto.get(1).update(PainelDireito.arrayCaixasDeTexto.get(1).getGraphics());
          break;
        case 3: //imprime mensagem decodificada
          PainelDireito.arrayCaixasDeTexto.get(4).setText(strMensagem);
          PainelDireito.arrayCaixasDeTexto.get(4).update(PainelDireito.arrayCaixasDeTexto.get(4).getGraphics());
          break;
        case 4: //imprime bits decodificados
          PainelDireito.arrayCaixasDeTexto.get(5).setText(strMensagem);
          PainelDireito.arrayCaixasDeTexto.get(5).update(PainelDireito.arrayCaixasDeTexto.get(5).getGraphics());
          break;
        case 5: //imprime bits brutos
          PainelEsquerdo.arrayCaixasDeTexto.get(2).setText(strMensagem);
          PainelEsquerdo.arrayCaixasDeTexto.get(2).update(PainelEsquerdo.arrayCaixasDeTexto.get(2).getGraphics());
          break;
        case 6: //imprime os bits recebidos
          PainelDireito.arrayCaixasDeTexto.get(6).setText(strMensagem);
          PainelDireito.arrayCaixasDeTexto.get(6).update(PainelDireito.arrayCaixasDeTexto.get(6).getGraphics());
          break;
      }

      Thread.sleep(VELOCIDADE);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /* **************************************************************
  Metodo: repintarPainel*
  Funcao: inicializa o thread que repinta o painel canvas*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  public static void repintarPainel() {
    try {
      TelaPrincipal.canvas.repintar();

      Thread.sleep(VELOCIDADE);
    } catch (Exception e) {
     e.printStackTrace();
    }
  }
}