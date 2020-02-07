/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 23/01/2020*
Ultima alteracao: 01/02/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */
package view;

import camadas.CamadaDeAplicacaoTransmissora;
import util.Formatacao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PainelEsquerdo extends JPanel {
  public static ArrayList<JTextArea> arrayCaixasDeTexto;
  public static JComboBox<String> cmbListaDeCodificacao;
  private ArrayList<JPanel> arrayPaineis;

  private JTextArea txtLabelNumerosAscii, txtLabelBitsBrutos;
  private JTextArea txtLabelBitsCodificados;
  private JTextField txtMensagem;
  public static JButton btnEnviar;
  public static ReduzirPermissoes mutex; //semaforo que trava o combobox

  /* **************************************************************
  Metodo: PainelEsquerdo*
  Funcao: Construtor da classe PainelEsquerdo.*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  public PainelEsquerdo() {
    this.arrayPaineis = new ArrayList<>();
    this.txtLabelNumerosAscii = new JTextArea("Numeros Ascii: ");
    this.txtLabelBitsBrutos = new JTextArea("Bits Brutos: ");
    this.txtLabelBitsCodificados = new JTextArea("Bits Codificados: ");

    PainelEsquerdo.mutex = new ReduzirPermissoes(1);
    PainelEsquerdo.arrayCaixasDeTexto = Formatacao.inicializarCaixasDeTexto();

    //Inicializando array de paineis
    for(int i=0; i<5; i++){
      arrayPaineis.add(new JPanel());
    }

    this.initGUIComponents();
  }

  /* **************************************************************
  Metodo: initGUIComponents*
  Funcao: inicializar os componentes do painel.*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  private void initGUIComponents() {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.adicionarComponentes();
  }

  /* **************************************************************
  Metodo: adicionarComponentes*
  Funcao: inicializa e adiciona os componentes ao painel e tem o
          metodo aplicacao transmissora.*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  private void adicionarComponentes() {
    //Inicializando labels de texto
    txtLabelNumerosAscii.setBackground(this.getBackground());
    txtLabelBitsBrutos.setBackground(this.getBackground());
    txtLabelBitsCodificados.setBackground(this.getBackground());

    Formatacao.inicializarLabelsDeTextos(
      txtLabelNumerosAscii, TelaPrincipal.LARGURA_LABELS_ESQUERDO, TelaPrincipal.ALTURA_LABELS);
    Formatacao.inicializarLabelsDeTextos(
      txtLabelBitsBrutos, TelaPrincipal.LARGURA_LABELS_ESQUERDO, TelaPrincipal.ALTURA_LABELS);
    Formatacao.inicializarLabelsDeTextos(
      txtLabelBitsCodificados, TelaPrincipal.LARGURA_LABELS_ESQUERDO, TelaPrincipal.ALTURA_LABELS);

    this.aplicacaoTransmissora();

    //criando combobox
    String[] tiposDeCodificacao =
    {"Codificacao Binaria", "Codificacao Manchester", "Codificacao Manchester Diferencial"};

    cmbListaDeCodificacao = new JComboBox<>(tiposDeCodificacao);
    cmbListaDeCodificacao.setForeground(Color.RED);
    cmbListaDeCodificacao.setSelectedIndex(0);
    cmbListaDeCodificacao.setPreferredSize(new Dimension(406, TelaPrincipal.ALTURA_COMPONENTES));

    //adicionando componentes aos paineis
    arrayPaineis.get(0).add(txtMensagem);
    arrayPaineis.get(0).add(btnEnviar);

    arrayPaineis.get(1).add(txtLabelNumerosAscii);
    arrayPaineis.get(1).add(
      Formatacao.inicializarBarraDeRolagem(
        PainelEsquerdo.arrayCaixasDeTexto.get(0), TelaPrincipal.LARGURA_COMPONENTES, TelaPrincipal.ALTURA_COMPONENTES*2)); //Numeros Ascii

    arrayPaineis.get(2).add(txtLabelBitsBrutos);
    arrayPaineis.get(2).add(
      Formatacao.inicializarBarraDeRolagem(
        PainelEsquerdo.arrayCaixasDeTexto.get(2), TelaPrincipal.LARGURA_COMPONENTES, TelaPrincipal.ALTURA_COMPONENTES)); //Bits brutos

    arrayPaineis.get(3).add(txtLabelBitsCodificados);
    arrayPaineis.get(3).add(
      Formatacao.inicializarBarraDeRolagem(
        PainelEsquerdo.arrayCaixasDeTexto.get(3), TelaPrincipal.LARGURA_COMPONENTES, TelaPrincipal.ALTURA_COMPONENTES)); //Bits codificados

    arrayPaineis.get(4).add(cmbListaDeCodificacao);

    //adicionando paineis
    this.add(arrayPaineis.get(0));
    this.add(arrayPaineis.get(1));
    this.add(arrayPaineis.get(2));
    this.add(arrayPaineis.get(3));
    this.add(arrayPaineis.get(4));
  }

  /* **************************************************************
  Metodo: aplicacaoTransmissora*
  Funcao: inicializa a caixa de texto de entrada e o botao enviar e
          chama a camada de aplicacao transmissora.*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  private void aplicacaoTransmissora() {
    //criando text field
    txtMensagem = new JTextField("Digite sua mensagem"){
      @Override
      public Dimension getPreferredSize() {
        return new Dimension(308, TelaPrincipal.ALTURA_COMPONENTES);
      }
      {
        this.addFocusListener(new FocusListener() {//adicionando hint text
          @Override
          public void focusGained(FocusEvent e) {
            JTextField txt = (JTextField)e.getSource();
            txt.setText("");
            txt.setForeground(new Color(50, 50, 50));
          }

          @Override
          public void focusLost(FocusEvent e) {
            JTextField txt = (JTextField)e.getSource();
            if(txt.getText().length() == 0){
              txt.setText("Digite sua mensagem");
              txt.setForeground(new Color(150, 150, 150));
            }
          }
        });
      }
    };

    //criando botao Enviar
    btnEnviar = new JButton("Enviar"){
      @Override
      public Dimension getPreferredSize() {
        return new Dimension(90, TelaPrincipal.ALTURA_COMPONENTES);
      }
      {
        this.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            new Thread(new Runnable(){
              @Override
              public void run() {
                if(PainelEsquerdo.mutex.tryAcquire()){ //trava o combobox
                  PainelEsquerdo.cmbListaDeCodificacao.setEnabled(false); //desativa combobox

                  PainelEsquerdo.cmbListaDeCodificacao.update(
                    PainelEsquerdo.cmbListaDeCodificacao.getGraphics());

                  if(txtMensagem.getText().equals("")){
                    JOptionPane.showMessageDialog(
                      null, "Caixa de texto vazia!", "Alerta!", JOptionPane.ERROR_MESSAGE);

                    PainelEsquerdo.cmbListaDeCodificacao.setEnabled(true); //re ativa o combobox

                    PainelEsquerdo.mutex.release(); //libera o combobox
                  }else{
                    CamadaDeAplicacaoTransmissora.camadaDeAplicacaoTransmissora(txtMensagem.getText());
                    repaint();
                  }
                }else{
                  JOptionPane.showMessageDialog(
                    null, "Mensagem em andamento!", "Alerta!", JOptionPane.ERROR_MESSAGE);

                  //elimina os threads da fila de espera
                  PainelEsquerdo.mutex.callReducePermits(PainelEsquerdo.mutex.getQueueLength());
                }
              }
            }).start();
          }
        });

        this.addKeyListener(new KeyListener() {
          @Override
          public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
              if(txtMensagem.getText().equals("")){
                System.out.println("msg nula");
              }else{
                CamadaDeAplicacaoTransmissora.camadaDeAplicacaoTransmissora(txtMensagem.getText());
                repaint();
              }
            }
          }
          @Override
          public void keyTyped(KeyEvent e){}
          @Override
          public void keyReleased(KeyEvent e){}
        });
      }
    };
  }

  /* **************************************************************
  Metodo: getPreferredSize*
  Funcao: seta o tamanho deste painel.*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(600, 300);
  }

  //classe para reduzir o numero de threads na fila de enviar
  public class ReduzirPermissoes extends Semaphore {

    /* **************************************************************
    Metodo: ReduzirPermissoes*
    Funcao: Construtor da classe reduzirPermissoes.*
    Parametros: nulo*
    Retorno: void*
    *************************************************************** */
    public ReduzirPermissoes(int permits) { //mensagens
      super(permits);
    }

    /* **************************************************************
    Metodo: callReducePermits*
    Funcao: Chamar o metodo protegido da classe Semaphore reducePermits.*
    Parametros: nulo*
    Retorno: void*
    *************************************************************** */
    public void callReducePermits(int reduction) {
      this.reducePermits(reduction);
    }
  }
}