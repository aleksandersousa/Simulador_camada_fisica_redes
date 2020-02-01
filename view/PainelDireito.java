/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 23/01/2020*
Ultima alteracao: 01/02/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */
package view;

import util.Formatacao;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class PainelDireito extends JPanel {
  public static ArrayList<JTextArea> arrayCaixasDeTexto;
  private ArrayList<JPanel> arrayPaineis;

  private JTextArea txtLabelMensagemDecodificada;
  private JTextArea txtLabelNumerosAciiDecodificados;
  private JTextArea txtLabelBitsDecodificados;
  private JTextArea txtLabelBitsRecebidos;

  /* **************************************************************
  Metodo: PainelDireito*
  Funcao: Construtor da classe PainelDireito.*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  public PainelDireito() {
    PainelDireito.arrayCaixasDeTexto = Formatacao.inicializarCaixasDeTexto();

    this.arrayPaineis = new ArrayList<>();
    this.txtLabelMensagemDecodificada = new JTextArea("Mensagem: ");
    this.txtLabelNumerosAciiDecodificados = new JTextArea("Numero Ascii: ");
    this.txtLabelBitsDecodificados = new JTextArea("Bits decodificados: ");
    this.txtLabelBitsRecebidos = new JTextArea("Bits recebidos: ");

    //Inicializando array de paineis
    for(int i=0; i<4; i++){
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
  Funcao: inicializa e adiciona os componentes ao painel.*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  private void adicionarComponentes() {
    //Inicializando labels de texto
    this.txtLabelMensagemDecodificada.setBackground(this.getBackground());
    this.txtLabelNumerosAciiDecodificados.setBackground(this.getBackground());
    this.txtLabelBitsDecodificados.setBackground(this.getBackground());
    this.txtLabelBitsRecebidos.setBackground(this.getBackground());

    Formatacao.inicializarLabelsDeTextos(
      txtLabelMensagemDecodificada, TelaPrincipal.LARGURA_LABELS_DIREITO, TelaPrincipal.ALTURA_LABELS);
    Formatacao.inicializarLabelsDeTextos(
      txtLabelNumerosAciiDecodificados, TelaPrincipal.LARGURA_LABELS_DIREITO, TelaPrincipal.ALTURA_LABELS);
    Formatacao.inicializarLabelsDeTextos(
      txtLabelBitsDecodificados, TelaPrincipal.LARGURA_LABELS_DIREITO, TelaPrincipal.ALTURA_LABELS);
    Formatacao.inicializarLabelsDeTextos(
      txtLabelBitsRecebidos, TelaPrincipal.LARGURA_LABELS_DIREITO, TelaPrincipal.ALTURA_LABELS);

    arrayPaineis.get(0).add(txtLabelMensagemDecodificada);
    arrayPaineis.get(0).add(
      Formatacao.inicializarBarraDeRolagem(
        PainelDireito.arrayCaixasDeTexto.get(4), TelaPrincipal.LARGURA_COMPONENTES, TelaPrincipal.ALTURA_COMPONENTES)); //Mensagem decodificada

    arrayPaineis.get(1).add(txtLabelNumerosAciiDecodificados);
    arrayPaineis.get(1).add(
      Formatacao.inicializarBarraDeRolagem(
        PainelDireito.arrayCaixasDeTexto.get(1), TelaPrincipal.LARGURA_COMPONENTES, TelaPrincipal.ALTURA_COMPONENTES*2)); //Numeros Ascii decodificados

    arrayPaineis.get(2).add(txtLabelBitsDecodificados);
    arrayPaineis.get(2).add(
      Formatacao.inicializarBarraDeRolagem(
        PainelDireito.arrayCaixasDeTexto.get(5), TelaPrincipal.LARGURA_COMPONENTES, TelaPrincipal.ALTURA_COMPONENTES));

    arrayPaineis.get(3).add(txtLabelBitsRecebidos);
    arrayPaineis.get(3).add(
      Formatacao.inicializarBarraDeRolagem(
        Formatacao.arrayCaixasDeTexto.get(6), TelaPrincipal.LARGURA_COMPONENTES, TelaPrincipal.ALTURA_COMPONENTES));

    //adicionando paineis
    this.add(arrayPaineis.get(0));
    this.add(arrayPaineis.get(1));
    this.add(arrayPaineis.get(2));
    this.add(arrayPaineis.get(3));
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
}