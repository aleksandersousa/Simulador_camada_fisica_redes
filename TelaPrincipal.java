import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame {
  public static final int ASCII = 0;
  public static final int BIT_BRUTO = 1;
  public static final int ASCII_DECODIFICADO = 2;
  public static final int MENSAGEM_DECODIFICADA = 3;
  public static final int BIT_DECODIFICADO = 4;
  public static final int BIT_CODIFICADO = 5;
  public static final int BIT_RECEBIDO = 6;
  private static final int VELOCIDADE = 500;

  private static ArrayList<JTextArea> arrayCaixasDeTexto;
  private static Canvas canvas;
  public static JComboBox<String> cmbListaDeCodificacao;
  private JScrollPane barraDeRolagem;

  private JTextArea txtLabelMensagemDecodificada, txtLabelTabelaAscii, txtLabelBitsRecebidos;
  private JTextArea txtLabelBitsBrutos, txtLabelTabelaAsciiDecodificada, txtLabelBitsDecodificados;
  private JTextArea  txtLabelBitsCodificados;

   /* **************************************************************
  Metodo: TelaPrincipal*
  Funcao: Construtor da classe TelaPrincipal.*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  public TelaPrincipal() {
    this.initGUIComponents();

    TelaPrincipal.canvas = new Canvas();

    this.txtLabelTabelaAscii = new JTextArea("Tabela Ascii: ");
    this.txtLabelBitsBrutos = new JTextArea("Bits brutos: ");
    this.txtLabelBitsDecodificados = new JTextArea("Bits decodificados: ");
    this.txtLabelTabelaAsciiDecodificada = new JTextArea("Ascii decodificada: ");
    this.txtLabelMensagemDecodificada = new JTextArea("Mensagem: ");
    this.txtLabelBitsCodificados = new JTextArea("Bits codificados: ");
    this.txtLabelBitsRecebidos = new JTextArea("Bits recebidos: ");

    this.adicionarComponentes();

    this.add(txtLabelTabelaAscii);
    this.add(txtLabelBitsBrutos);
    this.add(txtLabelBitsDecodificados);
    this.add(txtLabelMensagemDecodificada);
    this.add(txtLabelTabelaAsciiDecodificada);
    this.add(txtLabelBitsCodificados);
    this.add(txtLabelBitsRecebidos);
  }

  /* **************************************************************
  Metodo: initGUIComponents*
  Funcao: inicializar os componentes da tela initGUIComponents.*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  private void initGUIComponents() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(930, 450);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setLayout(null);
    this.setVisible(true);
  }

  /* **************************************************************
  Metodo: formatarLabelDeTexto*
  Funcao: Formatar o labels de texto*
  Parametros: JTextArea txtLabel: label a ser formatado
                      Font fonte: fonte utilizada
                      int x: localizacao x
                      int y: localizacap y
                      int largura: largura
                      int altura: altura*
  Retorno: void*
  *************************************************************** */
  private void formatarLabelDeTexto( JTextArea txtLabel, Font fonte, int x, int y, int largura, int altura) {
     txtLabel.setBounds(x, y, largura, altura);
     txtLabel.setFont(new Font("txt", Font.BOLD, 12));
     txtLabel.setEditable(false);
     txtLabel.setBackground(this.getBackground());
  }

  /* **************************************************************
  Metodo: criarCaixasDeTexto*
  Funcao: Criar as caixas de texto que exibem as informacoes em runtime*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  private void criarCaixasDeTexto() {
    arrayCaixasDeTexto = new ArrayList<JTextArea>();
    Font fonte = new Font("txt", Font.BOLD, 12);
    JTextArea txtCaixaDeTexto = null;

    for(int i=0; i<7; i++){
      txtCaixaDeTexto = new JTextArea();
      txtCaixaDeTexto.setFont(fonte);
      txtCaixaDeTexto.setEditable(false);

      if(i>=2 && i<=3){ //adiciona a quebra de linha somente as caixas de texto ascii
        txtCaixaDeTexto.setLineWrap(true);
      }
      arrayCaixasDeTexto.add(txtCaixaDeTexto);
    }
  }

   /* **************************************************************
  Metodo: criarBarraDeRolagem*
  Funcao: Criar as barras de rolagem*
  Parametros: JComponent component: component que se quer adicionar a barra
                      int x: localizacao x
                      int y: localizacap y
                      int largura: largura
                      int altura: altura*
  Retorno: void*
  *************************************************************** */
  private void criarBarraDeRolagem(JComponent component, int x, int y, int largura, int altura) {
      barraDeRolagem = new JScrollPane(component);
      barraDeRolagem.setBounds(x, y, largura, altura);

      this.add(barraDeRolagem);
  }

  /* **************************************************************
  Metodo: adicionarComponentes*
  Funcao: Adicionar os componentes a tela*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  private void adicionarComponentes() {
    String[] tiposDeCodificacao = {"Codficacao Binaria", "Codificacao Manchester", "Codificacao Manchester Diferencial"};

    cmbListaDeCodificacao = new JComboBox<>(tiposDeCodificacao);
    cmbListaDeCodificacao.setSelectedIndex(0);
    cmbListaDeCodificacao.setBounds(10, 250, 400, 40);

    JTextField txtMensagem = new JTextField();
    txtMensagem.setBounds(10, 10, 300, 40);
    txtMensagem.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) { //Limitando input de texto
        if(txtMensagem.getText().length() >= 23){
          e.consume();
        }
      }
    });

    JButton btnEnviar = new JButton("Enviar"){
      ActionListener acaoEnviar = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
          if(!arrayCaixasDeTexto.get(2).getText().equals(null)){
            arrayCaixasDeTexto.get(2).setText(null);
          }
          CamadaDeAplicacaoTransmissora.camadaDeAplicacaoTransmissora(txtMensagem.getText());
          repaint();
        }
      };

      {
        this.setBounds(320, 10, 90, 40);
        this.setFont(new Font("btnEnviar", Font.BOLD, 10));
        this.addActionListener(acaoEnviar);
      }
    };

    this.add(txtMensagem);
    this.add(btnEnviar);
    this.add(cmbListaDeCodificacao);

    Font fonte = new Font("txt", Font.BOLD, 12);
    this.criarCaixasDeTexto();

    this.formatarLabelDeTexto(this.txtLabelTabelaAscii, fonte, 10, 88, 120, 18);
    this.formatarLabelDeTexto(this.txtLabelBitsBrutos, fonte, 10, 160, 120, 18);
    this.formatarLabelDeTexto(this.txtLabelBitsCodificados, fonte, 10, 210, 120, 18);
    this.formatarLabelDeTexto(this.txtLabelBitsRecebidos, fonte, 500, 210, 135, 18);
    this.formatarLabelDeTexto(this.txtLabelBitsDecodificados, fonte, 500, 160, 135, 18);
    this.formatarLabelDeTexto(this.txtLabelTabelaAsciiDecodificada, fonte, 500, 88, 135, 18);
    this.formatarLabelDeTexto(this.txtLabelMensagemDecodificada, fonte, 500, 20, 135, 18);

    this.criarBarraDeRolagem(TelaPrincipal.canvas, 10, 300, 905,  100);
    this.criarBarraDeRolagem(TelaPrincipal.arrayCaixasDeTexto.get(0), 130, 150, 280, 40); //bits brutos
    this.criarBarraDeRolagem(TelaPrincipal.arrayCaixasDeTexto.get(1), 635, 150, 280, 40); //bits decodificados
    this.criarBarraDeRolagem(TelaPrincipal.arrayCaixasDeTexto.get(2), 130, 60, 280, 80); //tabela ascii
    this.criarBarraDeRolagem(TelaPrincipal.arrayCaixasDeTexto.get(3), 635, 60, 280, 80); //tabela ascii decodificada
    this.criarBarraDeRolagem(TelaPrincipal.arrayCaixasDeTexto.get(4), 635, 10, 280, 40); //mensagem decodificada
    this.criarBarraDeRolagem(TelaPrincipal.arrayCaixasDeTexto.get(5), 635, 200, 280, 40); //bits recebidos
    this.criarBarraDeRolagem(TelaPrincipal.arrayCaixasDeTexto.get(6), 130, 200, 280, 40); //bitis codificados
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
          TelaPrincipal.arrayCaixasDeTexto.get(2).setText(strMensagem);
          TelaPrincipal.arrayCaixasDeTexto.get(2).update(arrayCaixasDeTexto.get(2).getGraphics());
          break;
        case 1: //imprime os bits codificados
          TelaPrincipal.arrayCaixasDeTexto.get(0).setText(strMensagem);
          TelaPrincipal.arrayCaixasDeTexto.get(0).update(arrayCaixasDeTexto.get(0).getGraphics());
          break;
        case 2: //imprime tabela ascii decodificada
          TelaPrincipal.arrayCaixasDeTexto.get(3).setText(strMensagem);
          TelaPrincipal.arrayCaixasDeTexto.get(3).update(arrayCaixasDeTexto.get(3).getGraphics());
          break;
        case 3: //imprime mensagem decodificada
          TelaPrincipal.arrayCaixasDeTexto.get(4).setText(strMensagem);
          TelaPrincipal.arrayCaixasDeTexto.get(4).update(arrayCaixasDeTexto.get(4).getGraphics());
          break;
        case 4: //imprime bits decodificados
          TelaPrincipal.arrayCaixasDeTexto.get(1).setText(strMensagem);
          TelaPrincipal.arrayCaixasDeTexto.get(1).update(arrayCaixasDeTexto.get(1).getGraphics());
          break;
        case 5: //imprime bits antes de decodificar
          TelaPrincipal.arrayCaixasDeTexto.get(6).setText(strMensagem);
          TelaPrincipal.arrayCaixasDeTexto.get(6).update(arrayCaixasDeTexto.get(6).getGraphics());
          break;
        case 6: //imprime os bits recebidos
          TelaPrincipal.arrayCaixasDeTexto.get(5).setText(strMensagem);
          TelaPrincipal.arrayCaixasDeTexto.get(5).update(arrayCaixasDeTexto.get(5).getGraphics());
          break;
      }

      Thread.sleep(TelaPrincipal.VELOCIDADE);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

   /* **************************************************************
  Metodo: repintarPainel*
  Funcao: Repintar o painel canvas*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  public static void repintarPainel() {
    try {
      TelaPrincipal.canvas.repaint();
      TelaPrincipal.canvas.update(canvas.getGraphics());

      Thread.sleep(TelaPrincipal.VELOCIDADE);
    } catch (Exception e) {
     e.printStackTrace();
    }
  }
}